package com.dev.bookly.realtime.model;

public record WsEnvelope<T>(
        String channel,
        String type,
        T payload
) {}
