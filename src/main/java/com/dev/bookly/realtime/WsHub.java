package com.dev.bookly.realtime;

import com.dev.bookly.realtime.model.WsEnvelope;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WsHub {

    private final SimpMessagingTemplate template;

    public WsHub(SimpMessagingTemplate template) {
        this.template = template;
    }

    // global send
    public <T> void publish(String channel, String type, T payload) {
        template.convertAndSend(channel, new WsEnvelope<>(channel, type, payload));
    }

    // to one user after the auth ane connect with spring security
    public <T> void sendToUser(String userId, String queue, String type, T payload) {
        template.convertAndSendToUser(userId, queue, new WsEnvelope<>(queue, type, payload));
    }
}
