package dev.lightdream.rustbuildsystem.files.dto;

import dev.lightdream.api.files.dto.XMaterial;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

@SuppressWarnings("unchecked")
@AllArgsConstructor
@NoArgsConstructor
public class Cost {

    public HashMap<XMaterial, Integer> materials;

    public boolean has(Player player) {
        if (player == null) {
            return false;
        }

        HashMap<XMaterial, Integer> has = new HashMap<>();
        for (XMaterial material : materials.keySet()) {
            has.put(material, 0);
        }

        for (ItemStack item : player.getInventory()) {
            if (item == null) {
                continue;
            }
            XMaterial xMaterial = XMaterial.matchXMaterial(item);
            if (has.containsKey(xMaterial)) {
                has.put(xMaterial, has.get(xMaterial) + item.getAmount());
            }
        }

        for (XMaterial material : materials.keySet()) {
            if (has.get(material) < materials.get(material)) {
                return false;
            }
        }

        return true;
    }

    public void take(Player player) {
        if (player == null) {
            return;
        }

        HashMap<XMaterial, Integer> take = (HashMap<XMaterial, Integer>) materials.clone();
        for (ItemStack item : player.getInventory()) {
            if (item == null) {
                continue;
            }
            XMaterial xMaterial = XMaterial.matchXMaterial(item);
            if (!take.containsKey(xMaterial)) {
                continue;
            }
            int currentToTake = take.get(xMaterial);
            int amount = Math.min(item.getAmount(), currentToTake);
            take.put(xMaterial, currentToTake - amount);
            item.setAmount(item.getAmount() - amount);
        }
    }

}
