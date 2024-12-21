package com.example.velocitybackend.websocket;

import com.example.velocitybackend.models.MessageModel;
import com.example.velocitybackend.services.MessageService;
import com.example.velocitybackend.utils.MessageUtil;
import com.google.gson.Gson;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket/{userId}") // WebSocket endpoint with a userId path parameter
public class WebSocketServer {
    final MessageService messageService = new MessageService();

    // Map to store connected clients with their user IDs
    private static final ConcurrentHashMap<String, Session> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        // Associate the session with the userId
        clients.put(userId, session);
        System.out.println("User " + userId + " connected");

        // Send a welcome message
        try {
            session.getBasicRemote().sendText("{\"message\": \"Welcome to WebSocket server!\"}");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // convert the string to Gson, then assign it to a MessageModel object
        MessageModel messageData = new Gson().fromJson(message, MessageModel.class);

        try (final Response response = messageService.createMessage(messageData)) {
            clients.forEach((userId, clientSession) -> {
                // only send the message if the userId is equal to sender or receiver of messageData
                if (userId.equals(messageData.getSender()) || userId.equals(messageData.getReceiver())) {
                    try {
                        if (response.getStatus() == 200) {
                            MessageModel savedMessage = (MessageModel) response.getEntity();
                            clientSession.getBasicRemote().sendText("Success: " + new Gson().toJson(savedMessage));
                        } else {
                            clientSession.getBasicRemote().sendText("Error: " + response.getStatus());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            try {
                session.getBasicRemote().sendText("Error processing your message");
            } catch (Exception innerException) {
                innerException.printStackTrace();
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Connection closed: " + session.getId());
    }
}