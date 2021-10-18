package dev.lightdream.rustbuildsystem.inventory;

import dev.lightdream.rustbuildsystem.Main;
import dev.lightdream.rustbuildsystem.helpers.InventoryHelper;
import dev.lightdream.rustbuildsystem.helpers.ItemBuilder;
import dev.lightdream.rustbuildsystem.interfaces.impl.BuildAction;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * @Author: Matthew 'delix'
 * @Created 26.09.2021
 * @Class: BuildInventory
 **/

public class BuildInventory {

    public static InventoryHelper openMain() {
        InventoryHelper inv = new InventoryHelper(Main.instance, "&aMenu budowania", 6);
        ItemBuilder air1 = new ItemBuilder(Material.getMaterial(160), 1, (short)12).setName("&8#");
        ItemBuilder air2 = new ItemBuilder(Material.getMaterial(160), 1, (short)7).setName("&8#");
        inv.setItem(0, air1.toItemStack(), null);
        inv.setItem(1, air1.toItemStack(), null);
        inv.setItem(2, new ItemBuilder(Material.SPRUCE_WOOD_STAIRS).setName("&5&lSCHODY SPIRALNE").setLore("&7xyz", "", "", "&dInformacje", "&7Koszt wybudowania:", "&7\u25CF &a4 drewna", "", "&7Kliknij PPM, aby zaczac &dBUDOWAC").toItemStack(), new BuildAction("spiral"));
        inv.setItem(3, new ItemBuilder(Material.EMPTY_MAP).setName("&5&lERROR").setLore("&7xyz", "", "", "&dInformacje", "&7Koszt wybudowania:", "&7\u25CF &axyz", "", "&7Kliknij PPM, aby zaczac &dBUDOWAC").toItemStack(), new BuildAction("error"));
        inv.setItem(4, new ItemBuilder(Material.LOG).setName("&5&lFUNDAMENT").setLore("&7xyz", "", "", "&dInformacje", "&7Koszt wybudowania:", "&7\u25CF &a4 drewna", "", "&7Kliknij PPM, aby zaczac &dBUDOWAC").toItemStack(), new BuildAction("foundation"));
        inv.setItem(5, new ItemBuilder(Material.LOG, 1,(short)1).setName("&5&lSKOSNY FUNDAMENT").setLore("&7xyz", "", "", "&dInformacje", "&7Koszt wybudowania:", "&7\u25CF &a4 drewna", "", "&7Kliknij PPM, aby zaczac &dBUDOWAC").toItemStack(), new BuildAction("oblique_foundation"));
        inv.setItem(6, new ItemBuilder(Material.WOOD).setName("&5&lSCIANA").setLore("&7xyz", "", "", "&dInformacje", "&7Koszt wybudowania:", "&7\u25CF &a4 drewna", "", "&7Kliknij PPM, aby zaczac &dBUDOWAC").toItemStack(), new BuildAction("wall"));
        inv.setItem(7, air1.toItemStack(), null);
        inv.setItem(8, air1.toItemStack(), null);

        inv.setItem(9, air2.toItemStack(), null);
        inv.setItem(10, new ItemBuilder(Material.EMPTY_MAP).setName("&5&lERROR").setLore("&7xyz", "", "", "&dInformacje", "&7Koszt wybudowania:", "&7\u25CF &axyz", "", "&7Kliknij PPM, aby zaczac &dBUDOWAC").toItemStack(), new BuildAction("error"));
        inv.setItem(11, air2.toItemStack(), null);
        inv.setItem(12, air2.toItemStack(), null);
        inv.setItem(13, air2.toItemStack(), null);
        inv.setItem(14, air2.toItemStack(), null);
        inv.setItem(15, air2.toItemStack(), null);
        inv.setItem(16, new ItemBuilder(Material.GLASS).setName("&5&lSCIANA Z POJEDYNCZYM OKNEM").setLore("&7xyz", "", "", "&dInformacje", "&7Koszt wybudowania:", "&7\u25CF &a4 drewna", "", "&7Kliknij PPM, aby zaczac &dBUDOWAC").toItemStack(), new BuildAction("wall-onewindow"));
        inv.setItem(17, air2.toItemStack(), null);

        inv.setItem(19, new ItemBuilder(Material.EMPTY_MAP).setName("&5&lERROR").setLore("&7xyz", "", "", "&dInformacje", "&7Koszt wybudowania:", "&7\u25CF &axyz", "", "&7Kliknij PPM, aby zaczac &dBUDOWAC").toItemStack(), new BuildAction("error"));
        inv.setItem(20, air2.toItemStack(), null);
        inv.setItem(24, air2.toItemStack(), null);
        inv.setItem(25, new ItemBuilder(Material.GLASS, 3).setName("&5&lSCIANA Z POTROJNYM OKNEM").setLore("&7xyz", "", "", "&dInformacje", "&7Koszt wybudowania:", "&7\u25CF &a4 drewna", "", "&7Kliknij PPM, aby zaczac &dBUDOWAC").toItemStack(), new BuildAction("wall-triplewindow"));

        inv.setItem(28, new ItemBuilder(Material.IRON_TRAPDOOR, 4).setName("&5&lSUFIT Z CZTEREMA OTWORAMI").setLore("&7xyz", "", "", "&dInformacje", "&7Koszt wybudowania:", "&7\u25CF &a4 drewna", "", "&7Kliknij PPM, aby zaczac &dBUDOWAC").toItemStack(), new BuildAction("roof-four"));
        inv.setItem(29, air2.toItemStack(), null);
        inv.setItem(33, air2.toItemStack(), null);
        inv.setItem(34, new ItemBuilder(Material.WOOD, 1, (short)1).setName("&5&lSKOSNA SCIANA").setLore("&7xyz", "", "", "&dInformacje", "&7Koszt wybudowania:", "&7\u25CF &a4 drewna", "", "&7Kliknij PPM, aby zaczac &dBUDOWAC").toItemStack(), new BuildAction("oblique-wall"));

        inv.setItem(36, air2.toItemStack(), null);
        inv.setItem(37, new ItemBuilder(Material.IRON_TRAPDOOR).setName("&5&lSUFIT Z JEDNYM OTWOREM").setLore("&7xyz", "", "", "&dInformacje", "&7Koszt wybudowania:", "&7\u25CF &a4 drewna", "", "&7Kliknij PPM, aby zaczac &dBUDOWAC").toItemStack(), new BuildAction("roof-one"));
        inv.setItem(38, air2.toItemStack(), null);
        inv.setItem(39, air2.toItemStack(), null);
        inv.setItem(40, air2.toItemStack(), null);
        inv.setItem(41, air2.toItemStack(), null);
        inv.setItem(42, air2.toItemStack(), null);
        inv.setItem(43, new ItemBuilder(Material.THIN_GLASS).setName("&5&lSKOSNA SCIANA Z POJEDYNCZYM OKNEM").setLore("&7xyz", "", "", "&dInformacje", "&7Koszt wybudowania:", "&7\u25CF &a4 drewna", "", "&7Kliknij PPM, aby zaczac &dBUDOWAC").toItemStack(), new BuildAction("oblique-wall2"));
        inv.setItem(44, air2.toItemStack(), null);

        inv.setItem(45, air1.toItemStack(), null);
        inv.setItem(46, air1.toItemStack(), null);
        inv.setItem(47, new ItemBuilder(Material.WOOD_PLATE).setName("&5&lSUFIT").setLore("&7xyz", "", "", "&dInformacje", "&7Koszt wybudowania:", "&7\u25CF &a4 drewna", "", "&7Kliknij PPM, aby zaczac &dBUDOWAC").toItemStack(), new BuildAction("roof"));;
        inv.setItem(48, new ItemBuilder(Material.IRON_DOOR).setName("&5&lSCIANA Z MIEJSCEM NA DRZWI").setLore("&7xyz", "", "", "&dInformacje", "&7Koszt wybudowania:", "&7\u25CF &a4 drewna", "", "&7Kliknij PPM, aby zaczac &dBUDOWAC").toItemStack(), new BuildAction("door"));
        inv.setItem(49, new ItemBuilder(Material.EMPTY_MAP).setName("&5&lERROR").setLore("&7xyz", "", "", "&dInformacje", "&7Koszt wybudowania:", "&7\u25CF &axyz", "", "&7Kliknij PPM, aby zaczac &dBUDOWAC").toItemStack(), new BuildAction("error"));;
        inv.setItem(50, new ItemBuilder(Material.FENCE_GATE, 3).setName("&5&lSCIANA Z POTROJNYM MIEJSCE NA DRZWI").setLore("&7xyz", "", "", "&dInformacje", "&7Koszt wybudowania:", "&7\u25CF &a4 drewna", "", "&7Kliknij PPM, aby zaczac &dBUDOWAC").toItemStack(), new BuildAction("wall-one"));
        inv.setItem(51, new ItemBuilder(Material.THIN_GLASS, 3).setName("&5&lSKOSNA SCIANA Z POTROJNYM OKNEM").setLore("&7xyz", "", "", "&dInformacje", "&7Koszt wybudowania:", "&7\u25CF &a4 drewna", "", "&7Kliknij PPM, aby zaczac &dBUDOWAC").toItemStack(), new BuildAction("wall-duo"));
        inv.setItem(52, air1.toItemStack(), null);
        inv.setItem(53, air1.toItemStack(), null);
        return inv;
    }
}
