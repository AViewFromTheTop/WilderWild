package net.frozenblock.wilderwild.world.additions.feature;

import net.frozenblock.wilderwild.misc.FlowerColor;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public final class WilderConfiguredFeatures  {
	private WilderConfiguredFeatures() {
		throw new UnsupportedOperationException("WilderConfiguredFeatures contains only static declarations.");
	}

	public static final SimpleWeightedRandomList<BlockState> GLORY_OF_THE_SNOW_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.BLUE), 3).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.PURPLE), 3).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.PINK), 2).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.WHITE), 1).build();
	public static final SimpleWeightedRandomList<BlockState> LARGE_FERN_AND_GRASS_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.TALL_GRASS.defaultBlockState(), 3).add(Blocks.LARGE_FERN.defaultBlockState(), 3).build();
	public static final SimpleWeightedRandomList<BlockState> OASIS_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.DEAD_BUSH.defaultBlockState(), 8).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 0), 1).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 1), 2).build();
	public static final SimpleWeightedRandomList<BlockState> OASIS_GRASS_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.TALL_GRASS.defaultBlockState(), 1).add(Blocks.GRASS.defaultBlockState(), 6).build();
	public static final SimpleWeightedRandomList<BlockState> TUMBLEWEED_PLANT_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), 1).add(RegisterBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 2), 1).add(RegisterBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 1), 1).add(RegisterBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 0), 1).build();

	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_TREES_MIXED = key("fallen_trees_mixed");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_BIRCH = key("fallen_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_SPRUCE = key("fallen_spruce");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_SPRUCE_AND_OAK = key("fallen_spruce_and_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_BIRCH_AND_OAK = key("fallen_birch_and_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_CYPRESS_AND_OAK = key("fallen_cypress_and_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_PLAINS = key("trees_plains");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIRCH_AND_OAK = key("trees_birch_and_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIRCH = key("trees_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIRCH_TALL = key("trees_birch_tall");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_FLOWER_FOREST = key("trees_flower_forest");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MIXED_TREES = key("mixed_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_FOREST_VEGETATION = key("dark_forest_vegetation");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_TAIGA = key("trees_taiga");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_TREES_TAIGA = key("short_trees_taiga");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_OLD_GROWTH_PINE_TAIGA = key("trees_old_growth_pine_taiga");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_OLD_GROWTH_SPRUCE_TAIGA = key("trees_old_growth_spruce_taiga");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_GROVE = key("trees_grove");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_WINDSWEPT_HILLS = key("trees_windswept_hills");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MEADOW_TREES = key("meadow_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SAVANNA_TREES = key("savanna_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> WINDSWEPT_SAVANNA_TREES = key("windswept_savanna_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS_WETLANDS_TREES = key("cypress_wetlands_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS_WETLANDS_TREES_SAPLING = key("cypress_wetlands_trees_sapling");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS_WETLANDS_TREES_WATER = key("cypress_wetlands_trees_water");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_SHRUBS = key("big_shrubs");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PALMS = key("palms");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PALMS_OASIS = key("palms_oasis");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SEEDING_DANDELION = key("seeding_dandelion");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CARNATION = key("carnation");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DATURA = key("datura");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_PLAINS = key("flower_plains");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MILKWEED = key("milkweed");
	public static final ResourceKey<ConfiguredFeature<?, ?>> GLORY_OF_THE_SNOW = key("glory_of_the_snow");
	public static final ResourceKey<ConfiguredFeature<?, ?>> OASIS_GRASS = key("oasis_grass");
	public static final ResourceKey<ConfiguredFeature<?, ?>> OASIS_BUSH = key("oasis_bush");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CACTUS_OASIS = key("patch_cactus_oasis");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_FERN_AND_GRASS = key("large_fern_and_grass");
	public static final ResourceKey<ConfiguredFeature<?, ?>> POLLEN = key("pollen");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BROWN_SHELF_FUNGUS = key("brown_shelf_fungus");
	public static final ResourceKey<ConfiguredFeature<?, ?>> RED_SHELF_FUNGUS = key("red_shelf_fungus");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CATTAIL = key("cattail");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FLOWERED_WATERLILY = key("patch_flowered_waterlily");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_ALGAE = key("patch_algae");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TERMITE = key("termite_mound_baobab");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TUMBLEWEED = key("tumbleweed");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_MESOGLEA = key("mesoglea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_MESOGLEA_POOL = key("mesoglea_pool");
	public static final ResourceKey<ConfiguredFeature<?, ?>> JELLYFISH_CAVES_BLUE_MESOGLEA = key("jellyfish_caves_blue_mesoglea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UPSIDE_DOWN_BLUE_MESOGLEA = key("upside_down_blue_mesoglea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_MESOGLEA = key("mesoglea_with_dripleaves");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_MESOGLEA_POOL = key("purple_mesoglea_pool");
	public static final ResourceKey<ConfiguredFeature<?, ?>> JELLYFISH_CAVES_PURPLE_MESOGLEA = key("jellyfish_caves_purple_mesoglea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UPSIDE_DOWN_PURPLE_MESOGLEA = key("upside_down_purple_mesoglea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> NEMATOCYST = key("nematocyst");
	public static final ResourceKey<ConfiguredFeature<?, ?>> NEMATOCYST_PURPLE = key("nematocyst_purple");

	public static ResourceKey<ConfiguredFeature<?, ?>> key(String path) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(WilderSharedConstants.MOD_ID, path));
	}

}
