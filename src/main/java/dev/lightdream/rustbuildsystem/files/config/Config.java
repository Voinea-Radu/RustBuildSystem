package dev.lightdream.rustbuildsystem.files.config;

import dev.lightdream.api.files.dto.Position;
import dev.lightdream.api.files.dto.XMaterial;
import dev.lightdream.rustbuildsystem.files.dto.BuildSchematic;
import dev.lightdream.rustbuildsystem.files.dto.Cost;
import dev.lightdream.rustbuildsystem.files.dto.schematics.FoundationSchematic;
import dev.lightdream.rustbuildsystem.files.dto.schematics.MarginSchematic;
import dev.lightdream.rustbuildsystem.files.dto.schematics.RoofSchematic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Config extends dev.lightdream.api.files.config.Config {

    public HashMap<String, BuildSchematic> builds = new HashMap<String, BuildSchematic>() {{
        put("foundation", new FoundationSchematic(
                "foundation",
                new Position(0, 1, 0),
                new HashMap<Position, List<XMaterial>>() {{
                    //Platform
                    put(new Position(-2, 0, -2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(-2, 0, -1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(-2, 0, 0), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(-2, 0, 1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(-2, 0, 2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));

                    put(new Position(-1, 0, -2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(-1, 0, -1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(-1, 0, 0), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(-1, 0, 1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(-1, 0, 2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));

                    put(new Position(0, 0, -2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 0, -1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 0, 0), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 0, 1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 0, 2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));

                    put(new Position(1, 0, -2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(1, 0, -1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(1, 0, 0), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(1, 0, 1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(1, 0, 2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));

                    put(new Position(2, 0, -2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(2, 0, -1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(2, 0, 0), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(2, 0, 1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(2, 0, 2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));

                    //Legs
                    put(new Position(-2, -1, -2), Arrays.asList(XMaterial.OAK_LOG, XMaterial.OAK_LOG, XMaterial.OAK_LOG));
                    put(new Position(-2, -1, 2), Arrays.asList(XMaterial.OAK_LOG, XMaterial.OAK_LOG, XMaterial.OAK_LOG));
                    put(new Position(2, -1, -2), Arrays.asList(XMaterial.OAK_LOG, XMaterial.OAK_LOG, XMaterial.OAK_LOG));
                    put(new Position(2, -1, 2), Arrays.asList(XMaterial.OAK_LOG, XMaterial.OAK_LOG, XMaterial.OAK_LOG));

                    put(new Position(-2, -2, -2), Arrays.asList(XMaterial.OAK_LOG, XMaterial.OAK_LOG, XMaterial.OAK_LOG));
                    put(new Position(-2, -2, 2), Arrays.asList(XMaterial.OAK_LOG, XMaterial.OAK_LOG, XMaterial.OAK_LOG));
                    put(new Position(2, -2, -2), Arrays.asList(XMaterial.OAK_LOG, XMaterial.OAK_LOG, XMaterial.OAK_LOG));
                    put(new Position(2, -2, 2), Arrays.asList(XMaterial.OAK_LOG, XMaterial.OAK_LOG, XMaterial.OAK_LOG));
                }},
                Arrays.asList(
                        new Cost(new HashMap<XMaterial, Integer>() {{
                            put(XMaterial.OAK_PLANKS, 25);
                            put(XMaterial.OAK_LOG, 4);
                        }}),
                        new Cost(new HashMap<XMaterial, Integer>() {{
                            put(XMaterial.COBBLESTONE, 25);
                        }}),
                        new Cost(new HashMap<XMaterial, Integer>() {{
                            put(XMaterial.OBSIDIAN, 25);
                        }})
                ),
                Arrays.asList(100, 1000, 10000),
                -1
        ));
        put("wall", new MarginSchematic(
                "wall",
                new Position(0, 1, 0),
                new HashMap<Position, List<XMaterial>>() {{
                    put(new Position(0, 0, -2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 0, -1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 0, 0), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 0, 1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 0, 2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));

                    put(new Position(0, 1, -2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 1, -1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 1, 0), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 1, 1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 1, 2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));

                    put(new Position(0, 2, -2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 2, -1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 2, 0), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 2, 1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 2, 2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));

                    put(new Position(0, 3, -2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 3, -1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 3, 0), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 3, 1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 3, 2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                }},
                Arrays.asList(
                        new Cost(new HashMap<XMaterial, Integer>() {{
                            put(XMaterial.OAK_PLANKS, 20);
                        }}),
                        new Cost(new HashMap<XMaterial, Integer>() {{
                            put(XMaterial.COBBLESTONE, 20);
                        }}),
                        new Cost(new HashMap<XMaterial, Integer>() {{
                            put(XMaterial.OBSIDIAN, 20);
                        }})
                ),
                Arrays.asList(100, 1000, 10000),
                -1
        ));
        put("roof", new RoofSchematic(
                "roof",
                new Position(0, 5, 0),
                new HashMap<Position, List<XMaterial>>() {{
                    put(new Position(-2, 0, -2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(-2, 0, -1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(-2, 0, 0), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(-2, 0, 1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(-2, 0, 2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));

                    put(new Position(-1, 0, -2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(-1, 0, -1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(-1, 0, 0), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(-1, 0, 1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(-1, 0, 2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));

                    put(new Position(0, 0, -2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 0, -1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 0, 0), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 0, 1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(0, 0, 2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));

                    put(new Position(1, 0, -2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(1, 0, -1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(1, 0, 0), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(1, 0, 1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(1, 0, 2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));

                    put(new Position(2, 0, -2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(2, 0, -1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(2, 0, 0), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(2, 0, 1), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                    put(new Position(2, 0, 2), Arrays.asList(XMaterial.OAK_PLANKS, XMaterial.COBBLESTONE, XMaterial.OBSIDIAN));
                }},
                Arrays.asList(
                        new Cost(new HashMap<XMaterial, Integer>() {{
                            put(XMaterial.OAK_PLANKS, 25);
                        }}),
                        new Cost(new HashMap<XMaterial, Integer>() {{
                            put(XMaterial.COBBLESTONE, 25);
                        }}),
                        new Cost(new HashMap<XMaterial, Integer>() {{
                            put(XMaterial.OBSIDIAN, 25);
                        }})
                ),
                Arrays.asList(100, 1000, 10000),
                -1
        ));
    }};

    public int maxY = 255;
    public int stepsForUpdate = 0;
    public int minBuildOnGroundDistance = 2;

}