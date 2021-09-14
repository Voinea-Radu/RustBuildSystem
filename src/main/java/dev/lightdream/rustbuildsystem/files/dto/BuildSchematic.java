package dev.lightdream.rustbuildsystem.files.dto;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.Position;
import dev.lightdream.api.files.dto.XMaterial;
import dev.lightdream.libs.fasterxml.annotation.JsonIgnore;
import dev.lightdream.libs.fasterxml.databind.annotation.JsonDeserialize;
import dev.lightdream.rustbuildsystem.files.dto.schematics.FoundationSchematic;

import java.util.HashMap;
import java.util.List;

@JsonDeserialize(as = FoundationSchematic.class)
public interface BuildSchematic {

    @JsonIgnore
    boolean isRoof();

    @JsonIgnore
    boolean isFoundation();

    @JsonIgnore
    boolean isMargin();

    @JsonIgnore
    boolean isPlaceable();

    @JsonIgnore
    BuildSession getBuildSession(User user);

    @JsonIgnore
    String getType();

    @JsonIgnore
    Position getRootOffsets();

    @JsonIgnore
    HashMap<Position, List<XMaterial>> getOffsets();

    @JsonIgnore
    List<Cost> getCost();

    @JsonIgnore
    List<Integer> getHeath();

    @JsonIgnore
    int getCanCollideUnder();

}
