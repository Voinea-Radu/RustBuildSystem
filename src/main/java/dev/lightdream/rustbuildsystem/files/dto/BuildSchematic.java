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
