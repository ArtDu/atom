package ru.atom.chat;

import okhttp3.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
public class ChatClientTest {
    private static final Logger log = LoggerFactory.getLogger(ChatClientTest.class);

    private static String MY_NAME_IN_CHAT = "I_AM_STUPID";
    private static String MY_MESSAGE_TO_CHAT = "KILL_ME_SOMEONE";

    @Test
    public void login() throws IOException {
        Response response = ChatClient.login(MY_NAME_IN_CHAT);
        log.info("[" + response + "]");
        String body = response.body().string();
        log.info(body);
        Assert.assertTrue(response.code() == 200 || body.equals("Already logged in:("));
    }

    @Test
    public void viewChat() throws IOException {
        Response response = ChatClient.viewChat();
        log.info("[" + response + "]");
        log.info(response.body().string());
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void viewOnline() throws IOException {
        Response response = ChatClient.viewOnline();
        log.info("[" + response + "]");
        log.info(response.body().toString());
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void say() throws IOException {
        login();
        Response response = ChatClient.say(MY_NAME_IN_CHAT, MY_MESSAGE_TO_CHAT);
        log.info("[" + response + "]");
        log.info(response.body().string());
        Assert.assertEquals(200, response.code());
    }
}
