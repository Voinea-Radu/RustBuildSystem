package dev.lightdream.rustbuildsystem.files.dto;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.Position;
import dev.lightdream.api.files.dto.XMaterial;

import java.util.HashMap;
import java.util.List;

public interface BuildSchematic {

    boolean isRoof();

    boolean isFoundation();

    boolean isMargin();

    boolean isPlaceable();

    BuildSession getBuildSession(User user);

    String getType();

    Position getRootOffsets();

    HashMap<Position, List<XMaterial>> getOffsets();

    List<Cost> getCost();

    List<Integer> getHeath();

    int getCanCollideUnder();

}
