package dev.lightdream.rustbuildsystem.files.dto;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.XMaterial;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

import java.util.HashMap;

@AllArgsConstructor
public class Cost {

    public HashMap<XMaterial, Integer> materials;

    public boolean has(Player player){
        return false;
    }

    public void take(Player player){

    }

}
