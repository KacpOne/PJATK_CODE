import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedHashMap;
import java.util.Map;

public class NetworkNode {
    public static final String registration = "REGISTER";
    public static final String termination = "TERMINATE";
    public static final String internalTermination = "INTTERMINATE";
    public static final String internalReservation = "INTRESERV";
    public static final String allocated = "ALLOCATED";
    public static final String failed = "FAILED";
    public static final String rollback = "ROLLBACK";

    private int id;
    private int localPort;
    private String localIP;
    private String gateway;
    private Map<Character, Integer> resources;
    private Map<Integer, String> nodes;
    private Map<Integer, String> transactionLog;

    private boolean terminate = false;

    private NetworkNode(int id, int localPort, String localhost, String gateway, Map<Character, Integer> resources) {
        this.id = id;
        this.localPort = localPort;
        this.localIP = localhost;
        this.gateway = gateway;
        this.resources = resources;

        nodes = new LinkedHashMap<>();
        transactionLog = new LinkedHashMap<>();
    }

    private void regiser() {
        Socket socket = null;
        try {
            String adress = gateway.substring(0, gateway.indexOf(':'));
            int port = Integer.parseInt(gateway.substring(gateway.indexOf(':') + 1));
            socket = new Socket(adress, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //send registration
            out.println(NetworkNode.registration);
            out.println(id + ":" + socket.getLocalAddress().toString().substring(1) + ":" + localPort);
            //read response
            addNode(in.readLine());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
        }
    }

    private void go() {
        if (gateway != null) {
            regiser();
        }
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(localPort, 10, InetAddress.getByName(localIP));
            serverSocket.setSoTimeout(1000);
            System.out.println("Node ready");
            while (!terminate) {
                try {
                    Socket socket = serverSocket.accept();
                    new ClientRequestHandler(this, socket);
                } catch (SocketTimeoutException ignored) {
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
        }
    }

    public void addNode(String nodeData) {
        Integer id = Integer.parseInt(nodeData.substring(0, nodeData.indexOf(':')));
        String address = nodeData.substring(nodeData.indexOf(':') + 1);
        System.out.println("Node added - " + id + " : " + address);
        nodes.put(id, address);
    }

    public Map<Integer, String> getNodes() {
        return nodes;
    }

    public int getId() {
        return this.id;
    }

    public int getLocalPort() {
        return this.localPort;
    }

    public String getLocalIP() {
        return this.localIP;
    }

    public void addTransaction(int client, String trx) {
        transactionLog.put(client, trx);
        System.out.println("Transaction registered: " + client + " - " + trx);
    }

    public void terminate() {
        this.terminate = true;
    }

    public int reserve(int clientId, char resourceType, int reservation) {
        int rc = 0;
        if (resources.containsKey(resourceType) && resources.get(resourceType)>0) {
            if (resources.get(resourceType) >= reservation) {
                rc = reservation;
            } else {
                rc = resources.get(resourceType);
            }
            resources.replace(resourceType, resources.get(resourceType) - rc);
            addTransaction(clientId, "reserve:" + resourceType + ":" + rc);
        }
        return rc;
    }

    public void release(int clientId, char resourceType, int quantity) {
        if (resources.containsKey(resourceType)) {
            resources.replace(resourceType, resources.get(resourceType) + quantity);
            addTransaction(clientId, "release:" + resourceType + ":" + quantity);
        }
    }

    public static void main(String[] args) {
        int id = -1;
        String gateway = null;
        int localPort = -1;
        String localIP = "localhost";
        Map<Character, Integer> resources = new LinkedHashMap<>();
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-ident":
                    id = Integer.parseInt(args[++i]);
                    break;
                case "-gateway":
                    gateway = args[++i];
                    break;
                case "-tcpport":
                    localPort = Integer.parseInt(args[++i]);
                    break;
                case "-localhost":
                    localIP = args[++i];
                default:
                    String[] tmp1 = args[i].split(":");
                    resources.put(tmp1[0].charAt(0), Integer.valueOf(tmp1[1]));
            }
        }

        NetworkNode networkNode = new NetworkNode(id, localPort, localIP, gateway, resources);
        networkNode.go();
        System.exit(0);
    }
}
