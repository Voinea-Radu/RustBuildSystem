package dev.lightdream.rustbuildsystem.files.dto.schematics;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.Position;
import dev.lightdream.api.files.dto.XMaterial;
import dev.lightdream.libs.fasterxml.databind.annotation.JsonDeserialize;
import dev.lightdream.rustbuildsystem.files.dto.BuildSchematic;
import dev.lightdream.rustbuildsystem.files.dto.BuildSession;
import dev.lightdream.rustbuildsystem.files.dto.Cost;
import dev.lightdream.rustbuildsystem.files.dto.sessions.PlaceableSession;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

public class RoofSchematic extends BuildSchematic {
    @Override
    public boolean isRoof() {
        return true;
    }

    public RoofSchematic() {
    }

    public RoofSchematic(String type, Position rootOffset, HashMap<Position, List<XMaterial>> offsets, List<Cost> cost, List<Integer> heath, int canCollideUnder) {
        super(type, rootOffset, offsets, cost, heath, canCollideUnder);
    }

    @Override
    public boolean isFoundation() {
        return false;
    }

    @Override
    public boolean isMargin() {
        return false;
    }

    @Override
    public boolean isPlaceable() {
        return true;
    }

    @Override
    public BuildSession getBuildSession(User user) {
        return new PlaceableSession(user, this);
    }
}
