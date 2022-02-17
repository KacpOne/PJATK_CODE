import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class ClientRequestHandler extends Thread {
    private Socket socket;
    private NetworkNode networkNode;

    ClientRequestHandler(NetworkNode networkNode, Socket socket) {
        this.networkNode = networkNode;
        this.socket = socket;

        this.start();
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String request = in.readLine();
            switch (request) {
                case NetworkNode.registration: {
                    //new network node registration
                    networkNode.addNode(in.readLine());
                    //response with local node data to create bidirectional relationship
                    out.println(networkNode.getId() + ":" + socket.getLocalAddress().toString().substring(1) + ":" + networkNode.getLocalPort());
                    break;
                }
                case NetworkNode.termination:
                    //termination from client
                case NetworkNode.internalTermination: {
                    //termination from another node
                    if (request.equals(NetworkNode.internalTermination)) {
                        //remove requestor from list of nodes to avoid circular reference
                        networkNode.getNodes().remove(Integer.parseInt(in.readLine()));
                    }
                    for (Integer id : networkNode.getNodes().keySet()) {
                        try {
                            String address = networkNode.getNodes().get(id);
                            String ip = address.substring(0, address.indexOf(':'));
                            int port = Integer.parseInt(address.substring(address.indexOf(':') + 1));
                            socket = new Socket(ip, port);
                            PrintWriter out1 = new PrintWriter(socket.getOutputStream(), true);
                            //send termination
                            out1.println(NetworkNode.internalTermination);
                            out1.println(networkNode.getId());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        System.out.println("Node " + networkNode.getId() + " ended.");
                    }
                    networkNode.terminate();
                    break;
                }
                case NetworkNode.rollback: {
                    //reverse reservations
                    request = in.readLine();
                    StringTokenizer rolbackST = new StringTokenizer(request, ":");
                    int clientId = Integer.parseInt(rolbackST.nextToken());
                    char r = rolbackST.nextToken().charAt(0);
                    int q = Integer.parseInt(rolbackST.nextToken());
                    networkNode.release(clientId, r, q);
                    break;
                }
                case NetworkNode.internalReservation:
                    //reservation request forwarded by other node
                default: {
                    boolean intReserv = request.equals(NetworkNode.internalReservation);
                    boolean rollback = false;
                    Integer requestorId = null;
                    if (intReserv) {
                        request = in.readLine();
                    }
                    //reservation
                    Map<Character, Integer> tempStorage = new LinkedHashMap<>();
                    List<String> responses = new ArrayList<>();
                    StringTokenizer st = new StringTokenizer(request);
                    if (intReserv) {
                        requestorId = Integer.parseInt(st.nextToken());
                    }
                    //st = new StringTokenizer(request);
                    int clientId = Integer.parseInt(st.nextToken());
                    while (st.hasMoreTokens() && !rollback) {
                        String[] tmp1 = st.nextToken().split(":");
                        String response;
                        //check local storage
                        Character resourceKey = tmp1[0].charAt(0);
                        int quantity = Integer.parseInt(tmp1[1]);
                        tempStorage.put(resourceKey, quantity);
                        int reserved = networkNode.reserve(clientId, resourceKey, quantity);
                        if (reserved > 0) {
                            tempStorage.replace(resourceKey, tempStorage.get(resourceKey) - reserved);
                            response = resourceKey + ":" + reserved + ":" + networkNode.getLocalIP() + ":" + networkNode.getLocalPort();
                            responses.add(response);
                        }
                        if (tempStorage.get(resourceKey) > 0) {
                            //try other nodes
                            Set<Integer> nodeKeys = networkNode.getNodes().keySet();
                            Iterator<Integer> i = nodeKeys.iterator();
                            while (tempStorage.get(resourceKey) > 0 && i.hasNext()) {
                                Integer nodeKey = i.next();
                                if (requestorId == null || requestorId != nodeKey) {
                                    String nodeAddress = networkNode.getNodes().get(nodeKey);
                                    String nodeIP = nodeAddress.substring(0, nodeAddress.indexOf(":"));
                                    int nodePort = Integer.parseInt(nodeAddress.substring(nodeAddress.indexOf(":") + 1));
                                    //sending request to node
                                    Socket nodeSocket = new Socket(nodeIP, nodePort);
                                    PrintWriter nOut = new PrintWriter(nodeSocket.getOutputStream(), true);
                                    BufferedReader nIn = new BufferedReader(new InputStreamReader(nodeSocket.getInputStream()));
                                    nOut.println(NetworkNode.internalReservation);
                                    nOut.println(networkNode.getId() + " " + clientId + " " + resourceKey + ":" + tempStorage.get(resourceKey));
                                    String nResp = nIn.readLine();
                                    while (nResp != null) {
                                        StringTokenizer rspST = new StringTokenizer(nResp, ":");
                                        Character rspKey = rspST.nextToken().charAt(0);
                                        Integer rspAlocated = Integer.parseInt(rspST.nextToken());
                                        tempStorage.replace(rspKey, tempStorage.get(rspKey) - rspAlocated);
                                        responses.add(nResp);
                                        nResp = nIn.readLine();
                                    }
                                    nodeSocket.close();
                                }
                            }
                            //all or none
                            if (!intReserv && tempStorage.get(resourceKey) > 0) {
                                //failed to reserve resource
                                rollback = true;
                                for (String rsp : responses) {
                                    StringTokenizer rollbackST = new StringTokenizer(rsp, ":");
                                    char key = rollbackST.nextToken().charAt(0);
                                    int q = Integer.parseInt(rollbackST.nextToken());
                                    String ip = rollbackST.nextToken();
                                    int port = Integer.parseInt(rollbackST.nextToken());
                                    Socket s = new Socket(ip, port);
                                    PrintWriter sOut = new PrintWriter(s.getOutputStream(), true);
                                    BufferedReader sIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
                                    sOut.println(NetworkNode.rollback);
                                    sOut.println(clientId + ":" + key + ":" + q);
                                    s.close();
                                }
                                responses.clear();
                                responses.add(NetworkNode.failed);
                            }
                        }
                    }
                    if (!intReserv) {
                        //prepare client response
                        if (!rollback){
                            out.println(NetworkNode.allocated);
                        }
                        for (String response : responses) {
                            out.println(response);
                        }
                    } else {
                        //internal response not for client
                        for (String response : responses) {
                            out.println(response);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}
