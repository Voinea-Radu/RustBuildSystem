package dev.lightdream.rustbuildsystem.files.dto;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.Position;
import dev.lightdream.api.files.dto.XMaterial;
import dev.lightdream.libs.fasterxml.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.NotImplementedException;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class BuildSchematic {

    public String type;
    public Position rootOffset;
    public HashMap<Position, List<XMaterial>> offsets;
    public List<Cost> cost;
    //public boolean placedOnGround;
    //public boolean placeOnMargin;
    public List<Integer> heath;
    public int canCollideUnder;

    @Override
    public String toString() {
        return "BuildSchematic{" +
                "type='" + type + '\'' +
                ", rootOffset=" + rootOffset +
                ", offsets=" + offsets +
                ", cost=" + cost +
                //", placedOnGround=" + placedOnGround +
                //", placeOnMargin=" + placeOnMargin +
                ", heath=" + heath +
                '}';
    }

    @JsonIgnore
    public boolean isRoof(){
        throw new NotImplementedException();
    }

    @JsonIgnore
    public boolean isFoundation(){
        throw new NotImplementedException();
    }

    @JsonIgnore
    public boolean isMargin(){
        throw new NotImplementedException();
    }

    @JsonIgnore
    public boolean isPlaceable(){
        throw new NotImplementedException();
    }

    @JsonIgnore
    public BuildSession getBuildSession(User user){
        throw new NotImplementedException();
    }
}
