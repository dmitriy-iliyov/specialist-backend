package com.aidcompass.specialistdirectory.utils.deserilisers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.f4b6a3.uuid.exception.InvalidUuidException;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

public class UuidDeserializer extends JsonDeserializer<UUID> {

    private final Pattern pattern = Pattern.compile(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-7[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$"
    );

    @Override
    public UUID deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String uuidAsString = jsonParser.getValueAsString();
        if (pattern.matcher(uuidAsString).matches()) {
            return UUID.fromString(uuidAsString);
        }
        throw new InvalidUuidException(jsonParser.currentName() + ":Invalid UUID format.");
    }
}
