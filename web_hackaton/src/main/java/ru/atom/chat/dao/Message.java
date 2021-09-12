package ru.atom.chat.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
@Scope("singleton")
public class Message {
    private Queue<String> messages = new ConcurrentLinkedQueue<>();

    public Queue<String> getMessages() {
        return messages;
    }

    public void setMessages(Queue<String> messages) {
        this.messages = messages;
    }
}
