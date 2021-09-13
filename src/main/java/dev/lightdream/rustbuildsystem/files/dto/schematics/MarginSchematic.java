package dev.lightdream.rustbuildsystem.files.dto.schematics;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.Position;
import dev.lightdream.api.files.dto.XMaterial;
import dev.lightdream.rustbuildsystem.files.dto.BuildSchematic;
import dev.lightdream.rustbuildsystem.files.dto.BuildSession;
import dev.lightdream.rustbuildsystem.files.dto.Cost;
import dev.lightdream.rustbuildsystem.files.dto.sessions.MarginSession;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

public class MarginSchematic extends BuildSchematic {

    public MarginSchematic(String type, Position rootOffset, HashMap<Position, List<XMaterial>> offsets, List<Cost> cost, List<Integer> heath, int canCollideUnder) {
        super(type, rootOffset, offsets, cost, heath, canCollideUnder);
    }

    public MarginSchematic() {
    }

    @Override
    public boolean isRoof() {
        return false;
    }

    @Override
    public boolean isFoundation() {
        return false;
    }

    @Override
    public boolean isMargin() {
        return true;
    }

    @Override
    public boolean isPlaceable() {
        return true;
    }

    @Override
    public BuildSession getBuildSession(User user) {
        return new MarginSession(user, this);
    }
}
