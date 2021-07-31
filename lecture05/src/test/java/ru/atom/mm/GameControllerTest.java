package ru.atom.mm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.junit4.SpringRunner;
import ru.atom.mm.controller.ConnectionController;
import ru.atom.mm.controller.GameController;
import ru.atom.mm.model.Connection;
import ru.atom.mm.model.GameSession;
import ru.atom.mm.service.ConnectionQueue;
import ru.atom.mm.service.GameRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class GameControllerTest {

    @Mock
    ConnectionQueue connectionQueue;

    @Mock
    BlockingQueue<Connection> queue;

    @Mock
    GameRepository gameRepository;

    @InjectMocks
    GameController gameHandler  = new GameController();

    @InjectMocks
    ConnectionController connectionHandler  = new ConnectionController();

    @Test
    public void list() {
        List<Connection> candidates =  new ArrayList<>();
        GameRepository tmpRepository = new GameRepository();
        when(gameRepository.getAll()).thenReturn(tmpRepository.getAll());
        // to put connects
        when(connectionQueue.getQueue()).thenReturn(queue);
        when(queue.offer(any())).thenAnswer(
            (Answer) invocation -> {
                Object[] args = invocation.getArguments();
                Connection connection = (Connection) args[0];
                candidates.add(connection);
                if (candidates.size() == GameSession.PLAYERS_IN_GAME) {
                    GameSession session = new GameSession(candidates.toArray(new Connection[0]));
                    tmpRepository.put(session);
                    candidates.clear();
                }
                return true;
            });
        assertEquals("[]", gameHandler.list());

        connectionHandler.connect(1, "a");
        connectionHandler.connect(2, "b");
        connectionHandler.connect(3, "c");
        connectionHandler.connect(4, "d");

        assertEquals("[GameSession{connections=[" +
                "Connection{playerId=1, name='a'}, " +
                "Connection{playerId=2, name='b'}, " +
                "Connection{playerId=3, name='c'}, " +
                "Connection{playerId=4, name='d'}], id=0}]", gameHandler.list());
    }

}