# Java-based-chat-application
This Java-based chat application is designed to demonstrate network communication using Java's Socket API
The application is divided into two main components: the PooledChatServer, which handles incoming connections and broadcasts messages to all connected clients, and the ChatClient, which connects to the server, sends messages, and receives broadcasts from other clients. A key feature of this implementation is the use of a configuration file for specifying the server's address and port, enhancing flexibility and ease of configuration.
Design and Implementation
1. PooledChatServer
   - Listens for incoming client connections using ServerSocket on a specified port.
   - Utilizes a thread pool to efficiently manage multiple client connections.
   - Each client connection is handled by an individual thread that reads messages and broadcasts them to all connected clients.
   - Generates a config.properties file containing its address and port, which can be used by clients to establish a connection.

2. ChatClient
   - Reads server details (address and port) from the config.properties file.
   - Connects to the server using these details and handles input/output streams for communication.
   - Uses a separate thread to listen for and display messages from the server.
   - Allows users to input and send messages to the server, which are then broadcasted to all connected clients.
   - Supports a command (\q) for gracefully disconnecting from the server.

3. Networking and Concurrency
   - The Java Sockets API implements TCP/IP sockets for reliable communication between client and server.
   - Employs multi-threading to handle simultaneous client connections and asynchronous message handling.
Instructions for Running the Application
1. Server Setup
   - Compile the server: javac PooledChatServer.java.
   - Run the server: java PooledChatServer.
   - The server will generate a config.properties file and start listening for connections.

2. Client Setup
   - Ensure the config.properties file is accessible to the client (same directory or specify the path).
   - Compile the client: javac ChatClient.java.
   - Run the client: java ChatClient.
   - Enter messages into the console to send them to the server.

3. Exiting
   - Type \q in the client console to disconnect from the server.
References
- Java Network Programming (O’Reilly) source codes for PooledDaytimeServer and DaytimeClient
- Java Sockets and Networking: The official Java documentation (Oracle Java Docs) provided the foundation for implementing socket-based communication.
- Multi-threading in Java: Concepts and implementations were referenced from Oracle's Java tutorials on concurrency (Java Concurrency).
- Properties File Handling: Utilized information from Java's official Properties class documentation (Java Properties Class).
- Stack Overflow: Various threads on Stack Overflow were consulted for troubleshooting and understanding socket API automatic closure in Java.
- Networking Basics: "Computer Networking: A Top-Down Approach" by James F. Kurose and Keith W. Ross was a resource for understanding the fundamentals of network programming.
