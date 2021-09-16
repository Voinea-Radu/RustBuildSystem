package dev.lightdream.rustbuildsystem.files.dto.schematics;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.Position;
import dev.lightdream.api.files.dto.XMaterial;
import dev.lightdream.libs.fasterxml.databind.annotation.JsonSerialize;
import dev.lightdream.rustbuildsystem.files.dto.BuildSchematic;
import dev.lightdream.rustbuildsystem.files.dto.BuildSession;
import dev.lightdream.rustbuildsystem.files.dto.Cost;
import dev.lightdream.rustbuildsystem.files.dto.sessions.FoundationSession;
import dev.lightdream.rustbuildsystem.managers.serealizer.BuildSchematicSerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(using = BuildSchematicSerializer.class)
//@JsonTypeName("FoundationSchematic")
public class FoundationSchematic implements BuildSchematic {

    private String type;
    private Position rootOffset;
    private HashMap<Position, List<XMaterial>> offsets;
    private List<Cost> cost;
    private List<Integer> heath;
    private int canCollideUnder;

    @Override

    public boolean isRoof() {
        return false;
    }


    @Override
    public boolean isFoundation() {
        return true;
    }


    @Override

    public boolean isMargin() {
        return false;
    }


    @Override

    public boolean isPlaceable() {
        return false;
    }


    @Override

    public BuildSession getBuildSession(User user) {
        return new FoundationSession(user, this);
    }


    @Override

    public String getType() {
        return type;
    }


    @Override

    public Position getRootOffset() {
        return rootOffset;
    }


    @Override

    public HashMap<Position, List<XMaterial>> getOffsets() {
        return offsets;
    }


    @Override

    public List<Cost> getCost() {
        return cost;
    }


    @Override

    public List<Integer> getHeath() {
        return heath;
    }


    @Override

    public int getCanCollideUnder() {
        return canCollideUnder;
    }

    @Override
    public String toString() {
        return "FoundationSchematic{" +
                "type='" + type + '\'' +
                ", rootOffset=" + rootOffset +
                ", offsets=" + offsets +
                ", cost=" + cost +
                ", heath=" + heath +
                ", canCollideUnder=" + canCollideUnder +
                '}';
    }
}
