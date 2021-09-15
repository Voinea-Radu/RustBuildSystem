package dev.lightdream.rustbuildsystem.files.dto;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.Position;
import dev.lightdream.api.files.dto.XMaterial;
import dev.lightdream.libs.fasterxml.annotation.JsonIgnore;
import dev.lightdream.libs.fasterxml.annotation.JsonSubTypes;
import dev.lightdream.libs.fasterxml.annotation.JsonTypeInfo;
import dev.lightdream.libs.fasterxml.databind.annotation.JsonDeserialize;
import dev.lightdream.rustbuildsystem.files.dto.schematics.FoundationSchematic;
import dev.lightdream.rustbuildsystem.files.dto.schematics.MarginSchematic;
import dev.lightdream.rustbuildsystem.files.dto.schematics.PlaceableSchematic;
import dev.lightdream.rustbuildsystem.files.dto.schematics.RoofSchematic;

import java.util.HashMap;
import java.util.List;

@JsonTypeInfo( use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = FoundationSchematic.class, name = "FoundationSchematic"),
        @JsonSubTypes.Type(value = MarginSchematic.class, name = "MarginSchematic"),
        @JsonSubTypes.Type(value = PlaceableSchematic.class, name = "PlaceableSchematic"),
        @JsonSubTypes.Type(value = RoofSchematic.class, name = "RoofSchematic")
})
public interface BuildSchematic {

    
    boolean isRoof();

    
    boolean isFoundation();

    
    boolean isMargin();
    
    boolean isPlaceable();
    

    BuildSession getBuildSession(User user);
    

    String getType();
    

    Position getRootOffset();
    

    HashMap<Position, List<XMaterial>> getOffsets();
    

    List<Cost> getCost();
    

    List<Integer> getHeath();

    int getCanCollideUnder();

}
