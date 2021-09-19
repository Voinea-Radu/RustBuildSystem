package dev.lightdream.rustbuildsystem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

public class Utils extends dev.lightdream.api.utils.Utils {

    public static Block getTargetBlock(Player player, int range, boolean useGlass, boolean getLast) {
        BlockIterator iter = new BlockIterator(player, range);
        Block output = null;
        while (iter.hasNext()) {
            Block block = iter.next();
            if (getLast) {
                output = block;
            }
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


    public static String colored(String message) {
        return ChatColor.translateAlternateColorCodes('&', message).replace(">>", "?");
    }

    public static String getProgressBar(int current, int max, int totalBars, String symbol, String completedColor, String notCompletedColor) {
        float percent = current / (float) max;
        int progressBars = (int) (totalBars * percent);
        int leftOver = totalBars - progressBars;
        StringBuilder sb = new StringBuilder();
        if (current > max) {
            sb.append(completedColor);
            for (int i = 0; i < totalBars; ++i) {
                sb.append(symbol);
            }
            return sb.toString();
        }
        sb.append(completedColor);
        for (int i = 0; i < progressBars; ++i) {
            sb.append(symbol);
        }
        sb.append(notCompletedColor);
        for (int i = 0; i < leftOver; ++i) {
            sb.append(symbol);
        }
        return sb.toString();
    }

}
