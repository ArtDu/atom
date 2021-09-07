package ru.atom.lecture07.server.service;

import org.apache.tomcat.jni.Time;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atom.lecture07.server.controller.ChatController;
import ru.atom.lecture07.server.dao.MessageDao;
import ru.atom.lecture07.server.dao.UserDao;
import ru.atom.lecture07.server.model.Message;
import ru.atom.lecture07.server.model.User;

import java.sql.Timestamp;
import java.util.Date;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageDao messageDao;

    @Nullable
    @Transactional
    public User getLoggedIn(@NotNull String name) {
        return userDao.getByLogin(name);
    }

    @Transactional
    public void login(@NotNull String login) {
        User user = new User();
        userDao.save(user.setLogin(login));
        log.info("[" + login + "] logged in");
    }

    @Transactional
    public void logout(@NotNull User user) {
        userDao.delete(user);
        log.info("[" + user.getLogin() + "] logged out");
    }

    @NotNull
    @Transactional
    public List<User> getOnlineUsers() {
        return new ArrayList<>(userDao.findAll());
    }

    @Nullable
    @Transactional
    public List<Message> getMessages() {
        return new ArrayList<>(messageDao.findAll());
    }

    @Transactional
    public void sendMessage(@NotNull User user, @NotNull String msg) {
        Message message = new Message();
        message.setValue(msg);
        message.setUser(user);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        message.setTime(timestamp);
        messageDao.save(message);
    }
}
