package dev.lightdream.rustbuildsystem.managers.serealizer;

import dev.lightdream.libs.fasterxml.core.JsonGenerator;
import dev.lightdream.libs.fasterxml.databind.JavaType;
import dev.lightdream.libs.fasterxml.databind.JsonSerializer;
import dev.lightdream.libs.fasterxml.databind.SerializerProvider;
import dev.lightdream.libs.fasterxml.databind.ser.std.StdSerializer;
import dev.lightdream.rustbuildsystem.files.dto.BuildSchematic;
import dev.lightdream.rustbuildsystem.files.dto.schematics.FoundationSchematic;

import java.io.IOException;

public class BuildSchematicSerializer extends JsonSerializer<BuildSchematic> {

    @Override
    public void serialize(BuildSchematic foundationSchematic, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type", foundationSchematic.getType());
        jsonGenerator.writeObjectField("rootOffset", foundationSchematic.getRootOffset());
        jsonGenerator.writeObjectField("offsets", foundationSchematic.getOffsets());
        jsonGenerator.writeObjectField("cost", foundationSchematic.getCost());
        jsonGenerator.writeObjectField("heath", foundationSchematic.getHeath());
        jsonGenerator.writeNumberField("canCollideUnder", foundationSchematic.getCanCollideUnder());
        jsonGenerator.writeEndObject();
    }
}
/*
    private String type;
    private Position rootOffset;
    private HashMap<Position, List<XMaterial>> offsets;
    private List<Cost> cost;
    private List<Integer> heath;
    private int canCollideUnder;
 */
