package dev.lightdream.rustbuildsystem;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;

public class Utils extends dev.lightdream.api.utils.Utils {

    public static @Nullable Block getTargetBlock(Player player, int range, boolean useGlass) {
        BlockIterator iter = new BlockIterator(player, range);
        Block output = null;
        while (iter.hasNext()) {
            Block block = iter.next();
            if (block.getType() == Material.AIR) {
                continue;
            }
            if (!useGlass) {
                if (block.getType() == Material.STAINED_GLASS) {
                    continue;
                }
                if (block.getType() == Material.GLASS) {
                    continue;
                }
                if (block.getType() == Material.STAINED_GLASS_PANE) {
                    continue;
                }
                if (block.getType() == Material.THIN_GLASS) {
                    continue;
                }
            }
            output = block;
            break;
        }
        return output;
    }

}
