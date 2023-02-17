package net.frozenblock.wilderwild.init;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class WWBlockTags {

    public static final TagKey<Block> ANCIENT_CITY_BLOCKS = bind("ancient_city_blocks");
    public static final TagKey<Block> SCULK_SLAB_REPLACEABLE_WORLDGEN = bind("sculk_slab_replaceable_worldgen");
    public static final TagKey<Block> SCULK_STAIR_REPLACEABLE_WORLDGEN = bind("sculk_stair_replaceable_worldgen");
    public static final TagKey<Block> SCULK_WALL_REPLACEABLE_WORLDGEN = bind("sculk_wall_replaceable_worldgen");
    public static final TagKey<Block> SCULK_SLAB_REPLACEABLE = bind("sculk_slab_replaceable");
    public static final TagKey<Block> SCULK_STAIR_REPLACEABLE = bind("sculk_stair_replaceable");
    public static final TagKey<Block> SCULK_WALL_REPLACEABLE = bind("sculk_wall_replaceable");
    public static final TagKey<Block> SCULK_VEIN_REMOVE = bind("sculk_vein_removed_on");
    public static final TagKey<Block> ANCIENT_HORN_NON_COLLIDE = bind("ancient_horn_vibration_non_collide");
    public static final TagKey<Block> KILLS_TERMITE = bind("kills_termite");
    public static final TagKey<Block> TERMITE_BREAKABLE = bind("termite_breakable");
    public static final TagKey<Block> BLOCKS_TERMITE = bind("blocks_termite");
    public static final TagKey<Block> FIREFLY_HIDEABLE_BLOCKS = bind("firefly_hideable_blocks");
    public static final TagKey<Block> PACKED_MUD_REPLACEABLE = bind("packed_mud_replaceable");
    public static final TagKey<Block> HOLLOWED_LOGS = bind("hollowed_logs");

    private static TagKey<Block> bind(String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(WilderWild.MOD_ID, path));
    }

}
