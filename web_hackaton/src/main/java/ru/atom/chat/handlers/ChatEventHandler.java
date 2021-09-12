package ru.atom.chat.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.atom.chat.dao.Message;

import java.util.Queue;
import java.util.stream.Collectors;

@Component
public class ChatEventHandler extends TextWebSocketHandler implements WebSocketHandler {

    @Autowired
    Message messages;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("Chat socket Connected: " + session);
        // TODO: PUT WHILE IN NEW THREAD
        while (true) {
            Queue<String> dump = messages.getMessages();
            String response = "No messages";
            if (!dump.isEmpty()) {
                response = dump.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n"));
            }
            session.sendMessage(new TextMessage(response));
            Thread.sleep(3000);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Chat received: " + message.toString());
        Queue<String> dump = messages.getMessages();
        String response = dump.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
        session.sendMessage(new TextMessage(response));
        System.out.println("Send dump to client");
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Chat socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        super.afterConnectionClosed(session, closeStatus);
    }

}
