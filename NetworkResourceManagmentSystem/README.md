ENGLISH BELOW:

Sposób organizacji sieci:
System składa się z niezależnych węzłów z kórych każdy może komunikować się z conajmniej jednym innym węzłem.
Sieć zorganizowana jest za pomocą obiektów klasy NetworkNode będących serverami czekającymi na połączenie od klienta.
Przy uruchomieniu każdego obiektu NetworkNode(poza pierwszym) łączy się on z obiektem NetworkNode pod adresem IP i portem
podanym w argumencie uruchomienia programu "gateway" i wysyła do niego komunikat "REGISTER".
W odpowiedzi węzeł "gateway" odsyła swoje dane rejestracyjne (ip, port) co pozwala na utworzenie dwukierunkowego kanału
wymiany danych (każdy z węzłów może zainicjować połączenie)

Komunikacja:
Komunikacja systemu opiera się na protokole tcp.
Na potrzeby tej komunikacji utworzono prosty protokół kumunikacyjny składający się z siedmiu typów komunikatów:
1. REGISTER - komunikat pozwalający na przesłanie danych o położeniu węzła sieci (dodanie nowego węzła do sieci).
W ramach komunikatu wysyłany jest identyfikator węzła, adres ip oraz numer portu na którym nasłuchuje węzeł.
przykład komunikatu: REGISTER\nID:NODE_IP:PORT
2. TERMINATE - komunikat odbierany od klienta (klienta sieci) skutkujący zakończeniem pracy sieci.
przykład komunikatu: TERMINATE
3. INTTERMINATE - komunikat rozsyłany pomiędzy węzłami sieci (wewnętrzny komunikat sieci) skutkujący wyłączeniem kolejnych
węzłów.
przykład kominikatu: INTTERMINATE\nNODE_ID
4. INTRESERV - komunikat rezerwujący zasoby na poszczególnuch węzłach sieci. W komunikacie przekazywany jest identyfikator
zasobu oraz żądana ilość do rezerwacji.
przykład komunikatu: INTRESERV\nREQUEST_NODE_ID CLIEN_ID RESOURCE_ID:QUANTITY
5. ALLOCATED - komunikat potwierdzający pomyślną alokację zasobów odsyłany do klienta. W ramach komunikatu odsyłane
są informacje o szczegółach rezerwacji na poszczególnych węzłach sieci.
przykład komunikatu: ALLOCATED\nRESOURCE_ID:QUANTITY:NODE_IP:PORT
6. FAILED - komunikat informujący o niepowodzeniu alokacji zasobów odsyłany do klienta.
przykład komunikatu: FAILED
7. ROLLBACK - komunikat wewnętrzny sieci pozwalający na zwolnienie zasobów w przypadku nieudanej alokacji.
przykład komunikatu: ROLLBACK\nCLIENT_ID:RESOURCE_ID:QUANTITY

Implementacja:
System zaimplementowano w dwóch klasach java.
Klasa NetworkNode jest głóœną klasą servera w której tworzone jest gniazdo serwera.
Obiek tej klasy odbiera połąćzenia sieciowe i uruchamia ich obsługę w osobnych wątkach.
Obsługa połączenia realizowana jest w klasie ClientRequestHandler która to kalasy dzidziczy po klasie Thread.
Klasa NetworkNode przechowuje:
    liste dostęnych zasobów w postaci mapy.
    listę węzłów z którymi ma nawiązaną bezpośrednią komunikację.

Uruchomienie:
Komenda uruchomieniowa węzła sieci:
java NetworkNode -ident <identyfikator> -tcpport <numer portu TCP>
-gateway <adres>:<port> <lista zasobów>
gdzie:
• -ident <identyfikator> określa identyfikator danego węzła (liczba naturalna, unikalna
w obrębie sieci).
• -tcpport <numer portu TCP> określa numer portu TCP na którym dany węzeł sieci
oczekuje na połączenia od klientów.
• -gateway <adres>:<port> oznacza adres IP oraz numer portu TCP, na którym oczekuje
jeden z już uruchomionych węzłów sieci. Dla pierwszego węzła sieci parametru tego nie
podaje się.
• <lista zasobów> oznacza niepustą listę zasobów jakimi dysponuje dany węzeł w postaci
par: <typ zasobu>:<liczba>, gdzie <typ zasobu> to jednoliterowy identyfikator typu
zasobu a <liczba> określa liczbę sztuk tego zasobu przypisanych do danego węzła.

przykład komendy:
java NetworkNode -ident 1 -tcpport 5001 -gateway localhost:5000 A:6 B:1 C:4

  

How the network is organized:
The system consists of independent nodes each of which can communicate with at least one other node.
The network is organized by means of NetworkNode class objects which are servers waiting for connection from the client.
When each NetworkNode object (except the first one) is started, it connects to the NetworkNode object at IP address and port
given in the argument to start the "gateway" program and sends the message "REGISTER" to it.
In response, the "gateway" node sends back its registration data (ip, port), which allows for the creation of a bidirectional channel
data exchange (each node can initiate a connection)
  
Communication:
The system communication is based on the TCP protocol.
For the purpose of this communication, a simple communication protocol was created, consisting of seven types of messages:
1. REGISTER - a message that allows sending data about the location of a network node (adding a new node to the network).
The message includes the node identifier, the ip address and the port number on which the node is listening.
message example: REGISTER\nID:NODE_IP:PORT
2. TERMINATE - a message received from the client (network client), ending the network operation.
message example: TERMINATE
3. INTTERMINATE - message sent between network nodes (internal network message), resulting in disabling the next knots.
message example: INTTERMINATE\nNODE_ID
4. INTRESERV - a message reserving resources on individual network nodes. An identifier is provided in the message
resource and the requested quantity to be booked.
message example: INTRESERV\nREQUEST_NODE_ID CLIEN_ID RESOURCE_ID:QUANTITY
5. ALLOCATED - a message confirming the successful allocation of resources sent to the client. As part of the message sent back
there is information about booking details on individual network nodes.
6. FAILED - a message about a failure to allocate resources sent to the client.
message example: FAILED
7. ROLLBACK - internal network message that allows for the release of resources in the event of an unsuccessful allocation.
message example: ROLLBACK\nCLIENT_ID: RESOURCE_ID:QUANTITY
  
Implementation:
The system is implemented in two java classes.
The NetworkNode class is the main server class in which the server socket is created.
An object of this class receives network connections and starts handling them in separate threads.
The connection is handled in the ClientRequestHandler class which extends the Thread class.
The NetworkNode class stores:
    a list of available resources in the form of a map.
    list of nodes with which direct communication is established.
  
Activation:
Network node startup command:
java NetworkNode -ident <identifier> -tcpport <TCP port number>
-gateway <address>: <port> <inventory list>
where:
• -ident <identifier> specifies the identifier of the given node (natural, unique number
within the network).
• -tcpport <TCP port number> specifies the number of TCP port on which the network node
awaits calls from clients.
• -gateway <address>: <port> stands for the IP address and TCP port number on which one of the network nodes is already running.
For the first network node, this parameter is not given.
• <resource list> means a non-empty list of resources that a given node has in the form of 
pairs: <resource type>: <number>, where <resource type> is the single letter type identifining the
resource and <number> specifies the number of pieces of this resource assigned to the node.

command example:
java NetworkNode -ident 1 -tcpport 5001 -gateway localhost: 5000 A: 6 B: 1 C: 4
