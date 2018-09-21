package project.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;

import project.game_model.Game;

import java.io.IOException;

@ServerEndpoint("/game")
public class WebSocket {
    private static SessionHandler sessionHandler = new SessionHandler();
    private static Game game = new Game();
    private static int playerId = 0;

    @OnOpen
    public void open(Session session) throws IOException {
        System.out.println("open socket");
        sessionHandler.addSession(session);
        System.out.println("addSession");


        String answer = "{\"action\":\"addPlayer\",\"id\":\"" + playerId + "\"}";
        session.getBasicRemote().sendText(answer);
        playerId++;
    }

    @OnClose
    public void close(Session session) {
    }

    @OnError
    public void onError(Throwable error) {
    }

    @OnMessage
    public void handleMessage(String message) throws IOException {
        String[] data = message.split(":");

        System.out.println(data[0]);

        if(data[0].equals("makeTurn")){
            int player = Integer.parseInt(data[1]);
            int cell = Integer.parseInt(data[2]);
            game.makeTurn(player, cell);
            String answer = String.format("{\"action\":\"makeTurn\",\"cell\":\"%s\",\"over\":\"%s\"}",cell,game.over);
            sessionHandler.sendToAllConnectedSessions(answer);
        }
        if(data[0].equals("startGame")) {
            String answer = "{\"action\":\"startGame\"}";
            sessionHandler.sendToAllConnectedSessions(answer);
        }
    }
}

