package ru.atom.mm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.junit4.SpringRunner;
import ru.atom.mm.controller.ConnectionController;
import ru.atom.mm.model.Connection;
import ru.atom.mm.service.ConnectionQueue;
import ru.atom.mm.service.MatchMaker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ConnectionControllerTest {

    @Mock
    MatchMaker matchMaker;

    @Mock
    ConnectionQueue connectionQueue;

    @Mock
    BlockingQueue<Connection> queue;

    @InjectMocks
    ConnectionController connectionHandler = new ConnectionController();

    @Test
    public void connect() {
        List<Connection> candidates =  new ArrayList<>();
        when(matchMaker.getCandidates()).thenReturn(candidates);
        when(connectionQueue.getQueue()).thenReturn(queue);
        when(queue.offer(any())).thenAnswer(
            (Answer) invocation -> {
                Object[] args = invocation.getArguments();
                Connection connection = (Connection) args[0];
                candidates.add(connection);
                return true;
            });
        assertEquals("[]", connectionHandler.list());

        connectionHandler.connect(1, "a");
        connectionHandler.connect(2, "b");
        connectionHandler.connect(3, "c");

        assertFalse(connectionHandler.list().isEmpty());
    }

    @Test
    public void list() {
        List<Connection> candidates =  new ArrayList<>();
        when(matchMaker.getCandidates()).thenReturn(candidates);
        when(connectionQueue.getQueue()).thenReturn(queue);
        when(queue.offer(any())).thenAnswer(
            (Answer) invocation -> {
                Object[] args = invocation.getArguments();
                Connection connection = (Connection) args[0];
                candidates.add(connection);
                return true;
            });
        assertEquals("[]", connectionHandler.list());

        connectionHandler.connect(1, "a");
        connectionHandler.connect(2, "b");
        connectionHandler.connect(3, "c");

        assertEquals(
                "[Connection{playerId=1, name='a'}, " +
                "Connection{playerId=2, name='b'}, " +
                "Connection{playerId=3, name='c'}]",
                connectionHandler.list());
    }

}