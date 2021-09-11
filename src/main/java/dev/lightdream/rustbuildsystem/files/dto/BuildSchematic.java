package dev.lightdream.rustbuildsystem.files.dto;

import dev.lightdream.api.files.dto.Position;
import dev.lightdream.api.files.dto.XMaterial;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class BuildSchematic {

    public String type;
    public Position rootOffset;
    public HashMap<Position, List<XMaterial>> offsets;
    public List<Cost> cost;
    public boolean placedOnGround;
    public boolean placeOnMargin;
    public List<Integer> heath;

    @Override
    public String toString() {
        return "BuildSchematic{" +
                "type='" + type + '\'' +
                ", rootOffset=" + rootOffset +
                ", offsets=" + offsets +
                ", cost=" + cost +
                ", placedOnGround=" + placedOnGround +
                ", placeOnMargin=" + placeOnMargin +
                ", heath=" + heath +
                '}';
    }
}
