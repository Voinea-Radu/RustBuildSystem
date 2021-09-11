package dev.lightdream.rustbuildsystem.managers;

import dev.lightdream.libs.fasterxml.databind.DeserializationContext;
import dev.lightdream.libs.fasterxml.databind.KeyDeserializer;

public class KeyDeserializerManager extends KeyDeserializer {
    @Override
    public Object deserializeKey(String key, DeserializationContext ctxt) {
        System.out.println(key);
        return null;
    }
}


