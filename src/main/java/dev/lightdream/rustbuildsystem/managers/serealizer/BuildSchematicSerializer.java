package dev.lightdream.rustbuildsystem.managers.serealizer;

import dev.lightdream.libs.fasterxml.core.JsonGenerator;
import dev.lightdream.libs.fasterxml.databind.JsonSerializer;
import dev.lightdream.libs.fasterxml.databind.SerializerProvider;
import dev.lightdream.libs.fasterxml.databind.jsontype.TypeSerializer;
import dev.lightdream.rustbuildsystem.files.dto.BuildSchematic;

import java.io.IOException;

public class BuildSchematicSerializer extends JsonSerializer<BuildSchematic> {

    @Override
    public void serialize(BuildSchematic buildSchematic, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("class", buildSchematic.getClass());
        jsonGenerator.writeStringField("type", buildSchematic.getType());
        jsonGenerator.writeObjectField("rootOffset", buildSchematic.getRootOffset());
        jsonGenerator.writeObjectField("offsets", buildSchematic.getOffsets());
        jsonGenerator.writeObjectField("cost", buildSchematic.getCost());
        jsonGenerator.writeObjectField("heath", buildSchematic.getHeath());
        jsonGenerator.writeNumberField("canCollideUnder", buildSchematic.getCanCollideUnder());
        jsonGenerator.writeEndObject();
    }

    @Override
    public void serializeWithType(BuildSchematic value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(value, gen, serializers);
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
