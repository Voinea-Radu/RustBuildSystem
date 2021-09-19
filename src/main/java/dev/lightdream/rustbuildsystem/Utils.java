package dev.lightdream.rustbuildsystem;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import java.util.List;
import java.util.stream.Collectors;

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


    public static void sendTitle(Player p, String title, String subttitle) {
        sendTitle(p, title, subttitle, 30, 40, 30);
    }

    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        if (title == null) {
            title = "";
        }
        if (subtitle == null) {
            subtitle = "";
        }
        CraftPlayer craftPlayer = (CraftPlayer) player;
        PacketPlayOutTitle packetTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn, stay, fadeOut);
        craftPlayer.getHandle().playerConnection.sendPacket(packetTimes);
        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + colored(title) + "\"}");
        PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        craftPlayer.getHandle().playerConnection.sendPacket(packetTitle);
        IChatBaseComponent chatSubtitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + colored(subtitle) + "\"}");
        PacketPlayOutTitle packetSubtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubtitle);
        craftPlayer.getHandle().playerConnection.sendPacket(packetSubtitle);
    }

    public static String colored(String message) {
        return ChatColor.translateAlternateColorCodes('&', message).replace(">>", "?");
    }

    public static String getProgressBar(int current, int max, int totalBars, String symbol, String completedColor, String notCompletedColor) {
        float percent = current / (float)max;
        int progressBars = (int)(totalBars * percent);
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
