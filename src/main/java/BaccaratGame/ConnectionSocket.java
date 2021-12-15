package BaccaratGame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.lang.Thread;
import java.util.function.Consumer;

// create socket for connection to server
public class ConnectionSocket extends Thread {
    private Socket socket;
    private String ip;
    private int port;
    private Consumer<Serializable> callback;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    // constructor
    public ConnectionSocket(Consumer<Serializable> callback, String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.callback = callback;

        this.run();
    }

    // run method
    public void run() {
        try {
            this.socket = new Socket(ip, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            this.socket.setTcpNoDelay(true);
        } catch (IOException e) {
            System.out.printf("\n\nError, could not connect to server %s port %d\n", ip, port);
            return;
        }
        System.out.printf("\nConnected to %s server on port %d\n", socket.getLocalSocketAddress().toString(), socket.getLocalPort());
    }

    public void send(double bid, String hand) throws IOException {
        BaccaratInfo req = new BaccaratInfo(bid, hand);
        out.writeObject(req);
    }

    public BaccaratInfo receive() throws IOException, ClassNotFoundException {
        return (BaccaratInfo) in.readObject();
    }
}