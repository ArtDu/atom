package ru.atom.chat;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import ru.atom.chat.handlers.ClientEventHandler;

import java.io.IOException;
import java.util.Scanner;

public class EventClient {

    private static final OkHttpClient httpClient = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";

    public static void main(String[] args) {
        // connection url
        String uri = "ws://localhost:8080/chat";

        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketSession session = null;
        try {
            // The socket that receives events
            ClientEventHandler socket = new ClientEventHandler();
            // Make a handshake with server
            ListenableFuture<WebSocketSession> fut = client.doHandshake(socket, uri);
            // Wait for Connect
            session = fut.get();
            // Send a message
            Scanner S = new Scanner(System.in);
            while (true) {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("1) Send message                 ");
                System.out.println("0) Exit                         ");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                String line = S.nextLine();
                if (line.equals("1")) {
                    System.out.println("Print message:");
                    String msg = S.nextLine();
                    session.sendMessage(new TextMessage("msg"));
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    Request request = new Request.Builder()
                            .post(RequestBody.create(mediaType, "msg=" + msg))
                            .url(PROTOCOL + HOST + PORT + "/chat/say?name=" + "name")
                            .build();

                    httpClient.newCall(request).execute();
                }
                else if (line.equals("0")) {
                    break;
                }
            }
            // Close session
            session.close();

        } catch (Throwable t) {
            t.printStackTrace(System.err);
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (IOException ignored) {
            }
        }
    }
}
