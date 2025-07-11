package com.aidcompass.aggregator.system.models;

public record KafkaMessage<T> (
        EventType type,
        T payload
) { }
