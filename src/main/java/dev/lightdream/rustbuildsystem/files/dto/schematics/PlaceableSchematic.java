package dev.lightdream.rustbuildsystem.files.dto.schematics;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.Position;
import dev.lightdream.api.files.dto.XMaterial;
import dev.lightdream.libs.fasterxml.annotation.JsonIgnore;
import dev.lightdream.rustbuildsystem.files.dto.BuildSchematic;
import dev.lightdream.rustbuildsystem.files.dto.BuildSession;
import dev.lightdream.rustbuildsystem.files.dto.Cost;
import dev.lightdream.rustbuildsystem.files.dto.sessions.PlaceableSession;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class PlaceableSchematic implements BuildSchematic {

    private String type;
    private Position rootOffset;
    private HashMap<Position, List<XMaterial>> offsets;
    private List<Cost> cost;
    private List<Integer> heath;
    private int canCollideUnder;

    @JsonIgnore
    @Override
    public boolean isRoof() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isFoundation() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isMargin() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isPlaceable() {
        return true;
    }

    @JsonIgnore
    @Override
    public BuildSession getBuildSession(User user) {
        return new PlaceableSession(user, this);
    }

    @Override
    @JsonIgnore
    public String getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public Position getRootOffsets() {
        return rootOffset;
    }

    @JsonIgnore
    @Override
    public HashMap<Position, List<XMaterial>> getOffsets() {
        return offsets;
    }

    @JsonIgnore
    @Override
    public List<Cost> getCost() {
        return cost;
    }

    @JsonIgnore
    @Override
    public List<Integer> getHeath() {
        return heath;
    }

    @JsonIgnore
    @Override
    public int getCanCollideUnder() {
        return canCollideUnder;
    }
}
