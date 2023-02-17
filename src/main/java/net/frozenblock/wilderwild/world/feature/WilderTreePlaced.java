package net.frozenblock.wilderwild.world.feature;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.init.WWBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

import static net.minecraft.data.worldgen.placement.TreePlacements.SNOW_TREE_FILTER_DECORATOR;

public final class WilderTreePlaced {
    //BIRCH
    public static final Holder<PlacedFeature> BIRCH_CHECKED = register("birch_checked", WilderTreeConfigured.BIRCH_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> BIRCH_BEES_0004 = register("birch_bees_0004", WilderTreeConfigured.BIRCH_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> DYING_BIRCH = register("dying_birch", WilderTreeConfigured.DYING_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> SHORT_BIRCH = register("short_birch", WilderTreeConfigured.SHORT_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> DYING_SHORT_BIRCH = register("dying_short_birch", WilderTreeConfigured.SHORT_DYING_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> SHORT_BIRCH_BEES_0004 = register("short_birch_bees_0004", WilderTreeConfigured.SHORT_BIRCH_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> DYING_SUPER_BIRCH = register("dying_super_birch", WilderTreeConfigured.DYING_SUPER_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> SUPER_BIRCH_BEES_0004 = register("super_birch_bees_0004", WilderTreeConfigured.SUPER_BIRCH_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> SUPER_BIRCH_BEES = register("super_birch_bees", WilderTreeConfigured.SUPER_BIRCH_BEES, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> FALLEN_BIRCH_CHECKED = register("fallen_birch_checked", WilderTreeConfigured.FALLEN_BIRCH_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));

    //OAK
    public static final Holder<PlacedFeature> OAK_CHECKED = register("oak_checked", WilderTreeConfigured.OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> DYING_OAK_CHECKED = register("dying_oak_checked", WilderTreeConfigured.DYING_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> OAK_BEES_00004 = register("oak_bees_00004", WilderTreeConfigured.OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> SHORT_OAK_CHECKED = register("short_oak_checked", WilderTreeConfigured.SHORT_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> FANCY_OAK_CHECKED = register("fancy_oak_checked", WilderTreeConfigured.FANCY_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> DYING_FANCY_OAK_CHECKED = register("dying_fancy_oak_checked", WilderTreeConfigured.FANCY_DYING_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> DYING_FANCY_OAK_BEES_0004 = register("dying_fancy_oak_bees_0004", WilderTreeConfigured.FANCY_DYING_OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> FANCY_OAK_BEES_0004 = register("fancy_oak_bees_0004", WilderTreeConfigured.FANCY_OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> FANCY_OAK_BEES = register("fancy_oak_bees", WilderTreeConfigured.FANCY_OAK_BEES, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> FALLEN_OAK_CHECKED = register("fallen_oak_checked", WilderTreeConfigured.FALLEN_OAK_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

    //DARK OAK
    public static final Holder<PlacedFeature> TALL_DARK_OAK_CHECKED = register("tall_dark_oak_checked", WilderTreeConfigured.TALL_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));
    public static final Holder<PlacedFeature> DYING_TALL_DARK_OAK_CHECKED = register("dying_tall_dark_oak_checked", WilderTreeConfigured.DYING_TALL_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));
    public static final Holder<PlacedFeature> DYING_DARK_OAK_CHECKED = register("dying_dark_oak_checked", WilderTreeConfigured.DYING_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));

    //SWAMP TREE
    public static final Holder<PlacedFeature> SWAMP_TREE_CHECKED = register("swamp_tree_checked", WilderTreeConfigured.SWAMP_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE));

    //SPRUCE
    public static final Holder<PlacedFeature> SPRUCE_CHECKED = register("spruce_checked", WilderTreeConfigured.SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> SPRUCE_ON_SNOW = register("spruce_on_snow", WilderTreeConfigured.SPRUCE, SNOW_TREE_FILTER_DECORATOR);
    public static final Holder<PlacedFeature> SPRUCE_SHORT_CHECKED = register("spruce_short_checked", WilderTreeConfigured.SPRUCE_SHORT, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> FUNGUS_PINE_CHECKED = register("fungus_pine_checked", WilderTreeConfigured.FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> DYING_FUNGUS_PINE_CHECKED = register("dying_fungus_pine_checked", WilderTreeConfigured.DYING_FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> FUNGUS_PINE_ON_SNOW = register("fungus_pine_on_snow", WilderTreeConfigured.FUNGUS_PINE, SNOW_TREE_FILTER_DECORATOR);
    public static final Holder<PlacedFeature> MEGA_FUNGUS_SPRUCE_CHECKED = register("mega_fungus_spruce_checked", WilderTreeConfigured.MEGA_FUNGUS_SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> MEGA_FUNGUS_PINE_CHECKED = register("mega_fungus_pine_checked", WilderTreeConfigured.MEGA_FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> DYING_MEGA_FUNGUS_PINE_CHECKED = register("dying_mega_fungus_pine_checked", WilderTreeConfigured.DYING_MEGA_FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> FALLEN_SPRUCE_CHECKED = register("fallen_spruce_checked", WilderTreeConfigured.FALLEN_SPRUCE_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));

    //BAOBAB
    public static final Holder<PlacedFeature> BAOBAB = register("baobab", WilderTreeConfigured.BAOBAB, PlacementUtils.filteredByBlockSurvival(WWBlocks.BAOBAB_NUT.get()));
    public static final Holder<PlacedFeature> BAOBAB_TALL = register("baobab_tall", WilderTreeConfigured.BAOBAB_TALL, PlacementUtils.filteredByBlockSurvival(WWBlocks.BAOBAB_NUT.get()));

    //CYPRESS
    public static final Holder<PlacedFeature> CYPRESS = register("cypress", WilderTreeConfigured.CYPRESS, PlacementUtils.filteredByBlockSurvival(WWBlocks.CYPRESS_SAPLING.get()));
    public static final Holder<PlacedFeature> FUNGUS_CYPRESS = register("fungus_cypress", WilderTreeConfigured.FUNGUS_CYPRESS, PlacementUtils.filteredByBlockSurvival(WWBlocks.CYPRESS_SAPLING.get()));
    public static final Holder<PlacedFeature> SHORT_CYPRESS = register("short_cypress", WilderTreeConfigured.SHORT_CYPRESS, PlacementUtils.filteredByBlockSurvival(WWBlocks.CYPRESS_SAPLING.get()));
    public static final Holder<PlacedFeature> SHORT_FUNGUS_CYPRESS = register("short_fungus_cypress", WilderTreeConfigured.SHORT_FUNGUS_CYPRESS, PlacementUtils.filteredByBlockSurvival(WWBlocks.CYPRESS_SAPLING.get()));
    public static final Holder<PlacedFeature> SWAMP_CYPRESS = register("swamp_cypress", WilderTreeConfigured.SWAMP_CYPRESS, PlacementUtils.filteredByBlockSurvival(WWBlocks.CYPRESS_SAPLING.get()));
    public static final Holder<PlacedFeature> FALLEN_CYPRESS_CHECKED = register("fallen_cypress_checked", WilderTreeConfigured.FALLEN_CYPRESS_TREE, PlacementUtils.filteredByBlockSurvival(WWBlocks.CYPRESS_SAPLING.get()));

    private static Holder<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> feature, List<PlacementModifier> placement) {
        return PlacementUtils.register(WilderWild.string(name), feature, placement);
    }

    private static Holder<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> feature, PlacementModifier... placement) {
        return PlacementUtils.register(WilderWild.string(name), feature, placement);
    }

    public static void init() { }
}
