package net.frozenblock.wilderwild.world.additions.feature;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.frozenblock.lib.feature.FrozenFeatures;
import net.frozenblock.lib.feature.features.config.ColumnWithDiskFeatureConfig;
import net.frozenblock.lib.feature.features.config.PathFeatureConfig;
import net.frozenblock.lib.feature.features.config.PathSwapUnderWaterFeatureConfig;
import net.frozenblock.lib.feature.features.config.PillarFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.FrozenConfiguredFeatureUtils;
import net.frozenblock.lib.worldgen.feature.api.FrozenPlacementUtils;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.BaobabNutBlock;
import net.frozenblock.wilderwild.block.CoconutBlock;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import static net.frozenblock.wilderwild.world.additions.feature.WilderConfiguredFeatures.*;
import net.frozenblock.wilderwild.world.generation.features.config.ShelfFungusFeatureConfig;
import net.frozenblock.wilderwild.world.generation.treedecorators.LeavesAroundTopLogDecorator;
import net.frozenblock.wilderwild.world.generation.trunk.BaobabTrunkPlacer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import static net.minecraft.data.worldgen.placement.VegetationPlacements.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.PineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLeavesDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.UpwardsBranchingTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.Fluids;

public class WilderFeatureBootstrap {

	public static void bootstrapConfigured(BootstapContext<ConfiguredFeature<?, ?>> entries) {
		final var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		final var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);

		var placedFallenSpruceChecked = placedFeatures.getOrThrow(WilderTreePlaced.FALLEN_SPRUCE_CHECKED);

		WilderSharedConstants.logWild("Registering WilderTreeConfigured for", true);

		var birch = register(entries, WilderTreeConfigured.BIRCH, Feature.TREE, WilderTreeConfigured.new_birch().dirt(BlockStateProvider.simple(Blocks.DIRT)).decorators(ImmutableList.of(WilderTreeConfigured.SHELF_FUNGUS_007)).build());
		var birchBees0004 = register(entries, WilderTreeConfigured.BIRCH_BEES_0004, Feature.TREE, WilderTreeConfigured.new_birch().decorators(ImmutableList.of(WilderTreeConfigured.NEW_BEES_0004, WilderTreeConfigured.SHELF_FUNGUS_007)).ignoreVines().build());
		var dyingBirch = register(entries, WilderTreeConfigured.DYING_BIRCH, Feature.TREE, WilderTreeConfigured.new_birch().decorators(ImmutableList.of(WilderTreeConfigured.NEW_BEES_0004, WilderTreeConfigured.SHELF_FUNGUS_007)).ignoreVines().build());
		var shortBirchBees0004 = register(entries, WilderTreeConfigured.SHORT_BIRCH_BEES_0004, Feature.TREE, WilderTreeConfigured.new_short_birch().decorators(ImmutableList.of(WilderTreeConfigured.NEW_BEES_0004, WilderTreeConfigured.SHELF_FUNGUS_006)).ignoreVines().build());
		var superBirchBees0004 = register(entries, WilderTreeConfigured.SUPER_BIRCH_BEES_0004, Feature.TREE, WilderTreeConfigured.new_superBirch().decorators(ImmutableList.of(WilderTreeConfigured.NEW_BEES_0004, WilderTreeConfigured.SHELF_FUNGUS_007)).build());
		var dyingSuperBirch = register(entries, WilderTreeConfigured.DYING_SUPER_BIRCH, Feature.TREE, WilderTreeConfigured.new_superBirch().decorators(ImmutableList.of(WilderTreeConfigured.VINES_1_UNDER_260_05, WilderTreeConfigured.SHELF_FUNGUS_007)).build());
		var fallenBirchTree = register(entries, WilderTreeConfigured.FALLEN_BIRCH_TREE, Feature.TREE, WilderTreeConfigured.fallen_birch().decorators(List.of(WilderTreeConfigured.VINES_08_UNDER_260_075)).dirt(BlockStateProvider.simple(Blocks.DIRT)).build());
		var shortBirch = register(entries, WilderTreeConfigured.SHORT_BIRCH, Feature.TREE, WilderTreeConfigured.new_short_birch().decorators(ImmutableList.of(WilderTreeConfigured.SHELF_FUNGUS_006)).ignoreVines().build());
		var dyingShortBirch = register(entries, WilderTreeConfigured.DYING_SHORT_BIRCH, Feature.TREE, WilderTreeConfigured.new_short_birch().decorators(ImmutableList.of(WilderTreeConfigured.SHELF_FUNGUS_006, WilderTreeConfigured.VINES_1_UNDER_260_03)).ignoreVines().build());
		var superBirchBees = register(entries, WilderTreeConfigured.SUPER_BIRCH_BEES, Feature.TREE, WilderTreeConfigured.new_superBirch().decorators(ImmutableList.of(WilderTreeConfigured.NEW_BEES)).build());
		var oak = register(entries, WilderTreeConfigured.OAK, Feature.TREE, WilderTreeConfigured.new_oak().build());
		var shortOak = register(entries, WilderTreeConfigured.SHORT_OAK, Feature.TREE, WilderTreeConfigured.short_oak().build());
		var oakBees0004 = register(entries, WilderTreeConfigured.OAK_BEES_0004, Feature.TREE, WilderTreeConfigured.new_oak().decorators(ImmutableList.of(WilderTreeConfigured.NEW_BEES_0004, WilderTreeConfigured.SHELF_FUNGUS_006)).ignoreVines().build());
		var dyingOak = register(entries, WilderTreeConfigured.DYING_OAK, Feature.TREE, WilderTreeConfigured.new_oak().decorators(ImmutableList.of(WilderTreeConfigured.VINES_1_UNDER_260_03, WilderTreeConfigured.SHELF_FUNGUS_006)).ignoreVines().build());
		var fancyOak = register(entries, WilderTreeConfigured.FANCY_OAK, Feature.TREE, WilderTreeConfigured.new_fancyOak().build());
		var fancyDyingOak = register(entries, WilderTreeConfigured.FANCY_DYING_OAK, Feature.TREE, WilderTreeConfigured.new_fancyOak().decorators(List.of(WilderTreeConfigured.VINES_1_UNDER_260_05)).build());
		var fancyDyingOakBees0004 = register(entries, WilderTreeConfigured.FANCY_DYING_OAK_BEES_0004, Feature.TREE, WilderTreeConfigured.new_fancyOak().decorators(List.of(WilderTreeConfigured.NEW_BEES_0004, WilderTreeConfigured.VINES_1_UNDER_260_05)).build());
		var fancyOakBees0004 = register(entries, WilderTreeConfigured.FANCY_OAK_BEES_0004, Feature.TREE, WilderTreeConfigured.new_fancyOak().decorators(List.of(WilderTreeConfigured.NEW_BEES_0004)).build());
		var fallenOakTree = register(entries, WilderTreeConfigured.FALLEN_OAK_TREE, Feature.TREE, WilderTreeConfigured.fallen_oak().decorators(List.of(WilderTreeConfigured.VINES_08_UNDER_260_075)).dirt(BlockStateProvider.simple(Blocks.DIRT)).build());
		var fancyOakBees = register(entries, WilderTreeConfigured.FANCY_OAK_BEES, Feature.TREE, WilderTreeConfigured.new_fancyOak().decorators(List.of(WilderTreeConfigured.NEW_BEES)).build());
		var dyingDarkOak = register(entries, WilderTreeConfigured.DYING_DARK_OAK, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.DARK_OAK_LOG), new DarkOakTrunkPlacer(6, 2, 1), BlockStateProvider.simple(Blocks.DARK_OAK_LEAVES), new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)), new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()))).decorators(List.of(WilderTreeConfigured.VINES_1_UNDER_260_05)).ignoreVines().build());
		var tallDarkOak = register(entries, WilderTreeConfigured.TALL_DARK_OAK, Feature.TREE, WilderTreeConfigured.new_tall_dark_oak().ignoreVines().build());
		var dyingTallDarkOak = register(entries, WilderTreeConfigured.DYING_TALL_DARK_OAK, Feature.TREE, WilderTreeConfigured.new_tall_dark_oak().decorators(List.of(WilderTreeConfigured.VINES_1_UNDER_260_05)).ignoreVines().build());
		var swampTree = register(entries, WilderTreeConfigured.SWAMP_TREE, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.MANGROVE_LOG), new StraightTrunkPlacer(5, 2, 1), BlockStateProvider.simple(Blocks.MANGROVE_LEAVES), new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 3), Optional.of(new MangroveRootPlacer(UniformInt.of(1, 1), BlockStateProvider.simple(Blocks.MANGROVE_ROOTS), Optional.of(new AboveRootPlacement(BlockStateProvider.simple(Blocks.MOSS_CARPET), 0.45F)), new MangroveRootPlacement(BuiltInRegistries.BLOCK.getOrCreateTag(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH), HolderSet.direct(Block::builtInRegistryHolder, Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS), BlockStateProvider.simple(Blocks.MUDDY_MANGROVE_ROOTS), 8, 15, 0.2F))), new TwoLayersFeatureSize(2, 0, 2))).decorators(List.of(new LeaveVineDecorator(0.125F), new AttachedToLeavesDecorator(0.12F, 1, 0, new RandomizedIntStateProvider(BlockStateProvider.simple(Blocks.MANGROVE_PROPAGULE.defaultBlockState().setValue(MangrovePropaguleBlock.HANGING, true)), MangrovePropaguleBlock.AGE, UniformInt.of(0, 4)), 2, List.of(Direction.DOWN)))).ignoreVines().dirt(BlockStateProvider.simple(Blocks.MANGROVE_ROOTS)).build());
		var spruce = register(entries, WilderTreeConfigured.SPRUCE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new StraightTrunkPlacer(8, 4, 2), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(2, 3)), new TwoLayersFeatureSize(2, 0, 2)).decorators(ImmutableList.of(WilderTreeConfigured.SHELF_FUNGUS_006_ONLY_BROWN)).ignoreVines().build());
		var spruceShort = register(entries, WilderTreeConfigured.SPRUCE_SHORT, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new StraightTrunkPlacer(3, 1, 2), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new SpruceFoliagePlacer(UniformInt.of(1, 2), UniformInt.of(0, 2), UniformInt.of(2, 3)), new TwoLayersFeatureSize(2, 0, 2)).ignoreVines().build());
		var fungusPine = register(entries, WilderTreeConfigured.FUNGUS_PINE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new StraightTrunkPlacer(6, 4, 2), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new PineFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), UniformInt.of(3, 4)), new TwoLayersFeatureSize(2, 0, 2)).decorators(ImmutableList.of(WilderTreeConfigured.SHELF_FUNGUS_006_ONLY_BROWN)).ignoreVines().build());
		var dyingFungusPine = register(entries, WilderTreeConfigured.DYING_FUNGUS_PINE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new StraightTrunkPlacer(6, 4, 2), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new PineFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), UniformInt.of(3, 4)), new TwoLayersFeatureSize(2, 0, 2)).decorators(ImmutableList.of(WilderTreeConfigured.SHELF_FUNGUS_006_ONLY_BROWN, WilderTreeConfigured.VINES_1_UNDER_260_05)).ignoreVines().build());
		var megaFungusSpruce = register(entries, WilderTreeConfigured.MEGA_FUNGUS_SPRUCE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new GiantTrunkPlacer(13, 2, 14), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(13, 17)), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)), WilderTreeConfigured.SHELF_FUNGUS_006_ONLY_BROWN)).build());
		var megaFungusPine = register(entries, WilderTreeConfigured.MEGA_FUNGUS_PINE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new GiantTrunkPlacer(13, 2, 14), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(3, 7)), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)), WilderTreeConfigured.SHELF_FUNGUS_006_ONLY_BROWN)).build());
		var dyingMegaFungusPine = register(entries, WilderTreeConfigured.DYING_MEGA_FUNGUS_PINE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new GiantTrunkPlacer(13, 2, 14), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(3, 7)), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)), WilderTreeConfigured.SHELF_FUNGUS_006_ONLY_BROWN, WilderTreeConfigured.VINES_1_UNDER_260_075)).build());
		var fallenSpruceTree = register(entries, WilderTreeConfigured.FALLEN_SPRUCE_TREE, Feature.TREE, WilderTreeConfigured.fallen_spruce().decorators(List.of(WilderTreeConfigured.VINES_1_UNDER_260_075)).dirt(BlockStateProvider.simple(Blocks.DIRT)).build());
		var baobab = register(entries, WilderTreeConfigured.BAOBAB, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.BAOBAB_LOG), new BaobabTrunkPlacer(13, 3, 2), BlockStateProvider.simple(RegisterBlocks.BAOBAB_LEAVES), new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)), new TwoLayersFeatureSize(1, 0, 2)).decorators(List.of(new AttachedToLeavesDecorator(0.04F, 1, 0, new RandomizedIntStateProvider(BlockStateProvider.simple(RegisterBlocks.BAOBAB_NUT.defaultBlockState().setValue(MangrovePropaguleBlock.HANGING, true)), BaobabNutBlock.AGE, UniformInt.of(0, 2)), 4, List.of(Direction.DOWN)))).ignoreVines().build());
		var baobabTall = register(entries, WilderTreeConfigured.BAOBAB_TALL, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.BAOBAB_LOG), new BaobabTrunkPlacer(16, 4, 2), BlockStateProvider.simple(RegisterBlocks.BAOBAB_LEAVES), new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)), new TwoLayersFeatureSize(1, 0, 2)).decorators(List.of(new AttachedToLeavesDecorator(0.04F, 1, 0, new RandomizedIntStateProvider(BlockStateProvider.simple(RegisterBlocks.BAOBAB_NUT.defaultBlockState().setValue(MangrovePropaguleBlock.HANGING, true)), BaobabNutBlock.AGE, UniformInt.of(0, 2)), 4, List.of(Direction.DOWN)))).ignoreVines().build());
		var cypress = register(entries, WilderTreeConfigured.CYPRESS, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG), new StraightTrunkPlacer(6, 2, 3), BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES), new SpruceFoliagePlacer(ConstantInt.of(1), UniformInt.of(1, 3), UniformInt.of(4, 6)), new TwoLayersFeatureSize(2, 1, 2)).decorators(ImmutableList.of(WilderTreeConfigured.VINES_012_UNDER_76)).ignoreVines().build());
		var fallenCypressTree = register(entries, WilderTreeConfigured.FALLEN_CYPRESS_TREE, Feature.TREE, WilderTreeConfigured.fallen_cypress().decorators(List.of(WilderTreeConfigured.VINES_008_UNDER_82)).dirt(BlockStateProvider.simple(Blocks.DIRT)).build());
		var fungusCypress = register(entries, WilderTreeConfigured.FUNGUS_CYPRESS, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG), new StraightTrunkPlacer(8, 4, 3), BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES), new SpruceFoliagePlacer(ConstantInt.of(1), UniformInt.of(1, 3), UniformInt.of(6, 8)), new TwoLayersFeatureSize(2, 1, 2)).decorators(ImmutableList.of(WilderTreeConfigured.SHELF_FUNGUS_006_ONLY_BROWN, WilderTreeConfigured.VINES_008_UNDER_82)).ignoreVines().build());
		var shortCypress = register(entries, WilderTreeConfigured.SHORT_CYPRESS, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG), new StraightTrunkPlacer(3, 2, 3), BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES), new SpruceFoliagePlacer(ConstantInt.of(1), UniformInt.of(1, 3), UniformInt.of(4, 6)), new TwoLayersFeatureSize(2, 1, 2)).decorators(ImmutableList.of(WilderTreeConfigured.VINES_012_UNDER_76)).ignoreVines().build());
		var shortFungusCypress = register(entries, WilderTreeConfigured.SHORT_FUNGUS_CYPRESS, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG), new StraightTrunkPlacer(4, 3, 1), BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES), new SpruceFoliagePlacer(ConstantInt.of(1), UniformInt.of(1, 3), UniformInt.of(6, 8)), new TwoLayersFeatureSize(2, 1, 2)).decorators(ImmutableList.of(WilderTreeConfigured.SHELF_FUNGUS_006_ONLY_BROWN, WilderTreeConfigured.VINES_008_UNDER_82)).ignoreVines().build());
		var swampCypress = register(entries, WilderTreeConfigured.SWAMP_CYPRESS, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG), new UpwardsBranchingTrunkPlacer(15, 5, 2, UniformInt.of(4, 5), 0.2F, UniformInt.of(1, 3), BuiltInRegistries.BLOCK.getOrCreateTag(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)), BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES), new RandomSpreadFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), ConstantInt.of(2), 14), new TwoLayersFeatureSize(1, 0, 1))).decorators(ImmutableList.of(new LeaveVineDecorator(0.1F), WilderTreeConfigured.SHELF_FUNGUS_006_ONLY_BROWN, WilderTreeConfigured.VINES_008_UNDER_82)).build());
		var bigShrub = register(entries, WilderTreeConfigured.BIG_SHRUB, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new StraightTrunkPlacer(1, 0, 0), BlockStateProvider.simple(Blocks.OAK_LEAVES), new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2), new TwoLayersFeatureSize(0, 0, 0))).dirt(BlockStateProvider.simple(Blocks.COARSE_DIRT)).build());
		var palm = register(entries, WilderTreeConfigured.PALM, Feature.TREE, WilderTreeConfigured.palmBuilder(RegisterBlocks.PALM_LOG, RegisterBlocks.PALM_LEAVES, 6, 2, 1, 3, 5, 4, 9)
				.decorators(List.of(new LeavesAroundTopLogDecorator(0.45F, 0, 0, new RandomizedIntStateProvider(BlockStateProvider.simple(RegisterBlocks.COCONUT.defaultBlockState().setValue(BlockStateProperties.HANGING, true)), CoconutBlock.AGE, ConstantInt.of(0)), 4, List.of(Direction.DOWN)))).build());
		var tallPalm = register(entries, WilderTreeConfigured.TALL_PALM, Feature.TREE, WilderTreeConfigured.palmBuilder(RegisterBlocks.PALM_LOG, RegisterBlocks.PALM_LEAVES, 8, 3, 2, 3, 5, 5, 10)
				.decorators(List.of(new LeavesAroundTopLogDecorator(0.45F, 0, 0, new RandomizedIntStateProvider(BlockStateProvider.simple(RegisterBlocks.COCONUT.defaultBlockState().setValue(BlockStateProperties.HANGING, true)), CoconutBlock.AGE, ConstantInt.of(0)), 4, List.of(Direction.DOWN)))).build());
		var smallWinePalm = register(entries, WilderTreeConfigured.SMALL_WINE_PALM, Feature.TREE, WilderTreeConfigured.winePalmBuilder(RegisterBlocks.PALM_LOG, RegisterBlocks.PALM_LEAVES, 5, 1, 2, 3).build());
		var tallWinePalm = register(entries, WilderTreeConfigured.TALL_WINE_PALM, Feature.TREE, WilderTreeConfigured.winePalmBuilder(RegisterBlocks.PALM_LOG, RegisterBlocks.PALM_LEAVES, 10, 3, 3, 3)
				.decorators(List.of(new LeavesAroundTopLogDecorator(0.3F, 0, 0, new RandomizedIntStateProvider(BlockStateProvider.simple(RegisterBlocks.COCONUT.defaultBlockState().setValue(BlockStateProperties.HANGING, true)), CoconutBlock.AGE, ConstantInt.of(0)), 4, List.of(Direction.DOWN)))).build());

		WilderSharedConstants.logWild("Registering WilderMiscConfigured for", true);

		var blankShutUp = register(entries, WilderMiscConfigured.BLANK_SHUT_UP, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new SimpleStateProvider(Blocks.WATER.defaultBlockState()))))));
		var diskCoarseDirt = register(entries, WilderMiscConfigured.DISK_COARSE_DIRT, Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(Blocks.COARSE_DIRT), BlockPredicate.matchesBlocks(List.of(Blocks.GRASS_BLOCK, Blocks.COARSE_DIRT)), UniformInt.of(6, 8), 1));
		var diskMud = register(entries, WilderMiscConfigured.DISK_MUD, Feature.DISK, new DiskConfiguration(new RuleBasedBlockStateProvider(BlockStateProvider.simple(Blocks.MUD), List.of(new RuleBasedBlockStateProvider.Rule(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.solid(Direction.UP.getNormal()), BlockPredicate.matchesFluids(Direction.UP.getNormal(), Fluids.WATER))), BlockStateProvider.simple(Blocks.MUD)))), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, Blocks.GRASS_BLOCK)), UniformInt.of(2, 6), 2));
		var mudPath = register(entries, WilderMiscConfigured.MUD_PATH, FrozenFeatures.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.MUD), 11, 4, 0.1, 0.23, 1, false, false, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.CLAY.builtInRegistryHolder(), Blocks.SAND.builtInRegistryHolder())));
		var sandstonePath = register(entries, WilderMiscConfigured.SANDSTONE_PATH, FrozenFeatures.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.SANDSTONE), 10, 2, 0.2, 0.4, 1, true, true, HolderSet.direct(Blocks.SAND.builtInRegistryHolder())));
		var coarsePath = register(entries, WilderMiscConfigured.COARSE_PATH, FrozenFeatures.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.COARSE_DIRT), 11, 3, 0.12, -0.2, 0.3, false, false, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.PODZOL.builtInRegistryHolder())));
		var smallCoarsePath = register(entries, WilderMiscConfigured.SMALL_COARSE_PATH, FrozenFeatures.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.COARSE_DIRT), 8, 2, 0.15, 0.2, 1, true, true, HolderSet.direct(Blocks.RED_SAND.builtInRegistryHolder())));
		var mossPath = register(entries, WilderMiscConfigured.MOSS_PATH, FrozenFeatures.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.MOSS_BLOCK), 9, 1, 0.15, 0.18, 1, true, true, HolderSet.direct(Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.PODZOL.builtInRegistryHolder())));
		var sandPath = register(entries, WilderMiscConfigured.SAND_PATH, FrozenFeatures.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.SAND), 11, 3, 0.12, -0.2, 0.3, false, false, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder())));
		var packedMudPath = register(entries, WilderMiscConfigured.PACKED_MUD_PATH, FrozenFeatures.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.PACKED_MUD), 9, 1, 0.12, 0.20, 1, true, true, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.COARSE_DIRT.builtInRegistryHolder())));
		var badlandsMudPath = register(entries, WilderMiscConfigured.PACKED_MUD_PATH_BADLANDS, FrozenFeatures.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.PACKED_MUD), 4, 3, 0.7, 0.2, 1, true, true, HolderSet.direct(Blocks.TERRACOTTA.builtInRegistryHolder(),
				Blocks.RED_SAND.builtInRegistryHolder(), Blocks.RED_SANDSTONE.builtInRegistryHolder(), Blocks.TERRACOTTA.builtInRegistryHolder(),
				Blocks.WHITE_TERRACOTTA.builtInRegistryHolder(), Blocks.BROWN_TERRACOTTA.builtInRegistryHolder(), Blocks.RED_TERRACOTTA.builtInRegistryHolder(),
				Blocks.ORANGE_TERRACOTTA.builtInRegistryHolder(), Blocks.YELLOW_TERRACOTTA.builtInRegistryHolder(), Blocks.LIGHT_GRAY_TERRACOTTA.builtInRegistryHolder())));
		var underWaterSandPath = register(entries, WilderMiscConfigured.UNDER_WATER_SAND_PATH, FrozenFeatures.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.SAND), 16, 4, 0.05, 0.2, 0.54, true, true, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRAVEL.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder())));
		var underWaterGravelPath = register(entries, WilderMiscConfigured.UNDER_WATER_GRAVEL_PATH, FrozenFeatures.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.GRAVEL), 16, 1, 0.07, -0.7, -0.3, true, true, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.STONE.builtInRegistryHolder())));
		var underWaterClayPath = register(entries, WilderMiscConfigured.UNDER_WATER_CLAY_PATH, FrozenFeatures.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.CLAY), 16, 3, 0.07, 0.5, 0.85, true, true, HolderSet.direct(Blocks.DIRT.builtInRegistryHolder(), Blocks.GRAVEL.builtInRegistryHolder(), Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.STONE.builtInRegistryHolder())));
		var underWaterClayPathBeach = register(entries, WilderMiscConfigured.UNDER_WATER_CLAY_PATH_BEACH, FrozenFeatures.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.CLAY), 14, 2, 0.10, 0.5, 0.85, true, true, HolderSet.direct(Blocks.SAND.builtInRegistryHolder())));
		var underWaterGravelPathRiver = register(entries, WilderMiscConfigured.UNDER_WATER_GRAVEL_PATH_RIVER, FrozenFeatures.NOISE_PATH_UNDER_WATER_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.GRAVEL), 14, 2, 0.10, 0.5, 0.85, true, true, HolderSet.direct(Blocks.SAND.builtInRegistryHolder())));
		var orePackedMud = register(entries, WilderMiscConfigured.ORE_PACKED_MUD, Feature.ORE, new OreConfiguration(WilderMiscConfigured.PACKED_MUD_REPLACEABLE, Blocks.PACKED_MUD.defaultBlockState(), 40));
		var oreCalcite = register(entries, WilderMiscConfigured.ORE_CALCITE, Feature.ORE, new OreConfiguration(WilderMiscConfigured.NATURAL_STONE, Blocks.CALCITE.defaultBlockState(), 64));
		var deepslatePool = register(entries, WilderMiscConfigured.DEEPSLATE_POOL, FrozenFeatures.CIRCULAR_WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(Blocks.DEEPSLATE), PlacementUtils.inlinePlaced(blankShutUp), CaveSurface.FLOOR, ConstantInt.of(4), 0.8F, 2, 0.000F, UniformInt.of(12, 15), 0.7F));
		var stonePool = register(entries, WilderMiscConfigured.STONE_POOL, FrozenFeatures.CIRCULAR_WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(Blocks.STONE), PlacementUtils.inlinePlaced(blankShutUp), CaveSurface.FLOOR, ConstantInt.of(4), 0.8F, 2, 0.000F, UniformInt.of(12, 15), 0.7F));
		var mesogleaPillar = register(entries, WilderMiscConfigured.MESOGLEA_PILLAR, FrozenFeatures.UPWARDS_PILLAR_FEATURE, new PillarFeatureConfig(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), UniformInt.of(4, 12), HolderSet.direct(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(), RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(), Blocks.WATER.builtInRegistryHolder())));
		var purpleMesogleaPillar = register(entries, WilderMiscConfigured.PURPLE_MESOGLEA_PILLAR, FrozenFeatures.UPWARDS_PILLAR_FEATURE, new PillarFeatureConfig(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), UniformInt.of(4, 12), HolderSet.direct(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(), RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(), Blocks.WATER.builtInRegistryHolder())));
		var downwardsMesogleaPillar = register(entries, WilderMiscConfigured.DOWNWARDS_MESOGLEA_PILLAR, FrozenFeatures.DOWNWARDS_PILLAR_FEATURE, new PillarFeatureConfig(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), UniformInt.of(3, 10), HolderSet.direct(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(), RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(), Blocks.WATER.builtInRegistryHolder())));
		var downwardsPurpleMesogleaPillar = register(entries, WilderMiscConfigured.DOWNWARDS_PURPLE_MESOGLEA_PILLAR, FrozenFeatures.DOWNWARDS_PILLAR_FEATURE, new PillarFeatureConfig(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), UniformInt.of(3, 10), HolderSet.direct(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(), RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(), Blocks.WATER.builtInRegistryHolder())));
		var sandPool = register(entries, WilderMiscConfigured.SAND_POOL, FrozenFeatures.CIRCULAR_WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(WilderBlockTags.SAND_POOL_REPLACEABLE, BlockStateProvider.simple(Blocks.SAND), PlacementUtils.inlinePlaced(blankShutUp), CaveSurface.FLOOR, ConstantInt.of(5), 0.8F, 2, 0.000F, UniformInt.of(8, 14), 0.7F));
		var grassPath = register(entries, WilderMiscConfigured.GRASS_PATH, FrozenFeatures.NOISE_PATH_SWAP_UNDER_WATER_FEATURE, new PathSwapUnderWaterFeatureConfig(BlockStateProvider.simple(Blocks.GRASS_BLOCK), BlockStateProvider.simple(Blocks.DIRT), 11, 4, 0.15, 0.4, 1.0, false, false, HolderSet.direct(Blocks.SAND.builtInRegistryHolder(), Blocks.SANDSTONE.builtInRegistryHolder())));
		var mossPathOasis = register(entries, WilderMiscConfigured.MOSS_PATH_OASIS, FrozenFeatures.NOISE_PATH_FEATURE, new PathFeatureConfig(BlockStateProvider.simple(Blocks.MOSS_BLOCK), 9, 2, 0.10, 0.12, 1, true, true, HolderSet.direct(Blocks.SAND.builtInRegistryHolder())));

		WilderSharedConstants.logWild("Registering WilderConfiguredFeatures for", true);

		var placedFallenBirchChecked = placedFeatures.getOrThrow(WilderTreePlaced.FALLEN_BIRCH_CHECKED);
		var placedFallenOakChecked = placedFeatures.getOrThrow(WilderTreePlaced.FALLEN_OAK_CHECKED);
		var placedFallenCypressChecked = placedFeatures.getOrThrow(WilderTreePlaced.FALLEN_CYPRESS_CHECKED);
		var placedShortBirchBees0004 = placedFeatures.getOrThrow(WilderTreePlaced.SHORT_BIRCH_BEES_0004);
		var placedDyingShortBirch = placedFeatures.getOrThrow(WilderTreePlaced.DYING_SHORT_BIRCH);
		var placedFancyOakBees0004 = placedFeatures.getOrThrow(WilderTreePlaced.FANCY_OAK_BEES_0004);
		var placedDyingFancyOakBees0004 = placedFeatures.getOrThrow(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004);
		var placedDyingOakChecked = placedFeatures.getOrThrow(WilderTreePlaced.DYING_OAK_CHECKED);
		var placedShortOakChecked = placedFeatures.getOrThrow(WilderTreePlaced.SHORT_OAK_CHECKED);
		var placedOakBees0004 = placedFeatures.getOrThrow(WilderTreePlaced.OAK_BEES_0004);
		var placedDyingBirch = placedFeatures.getOrThrow(WilderTreePlaced.DYING_BIRCH);
		var placedBirchBees0004 = placedFeatures.getOrThrow(WilderTreePlaced.BIRCH_BEES_0004);
		var placedDyingSuperBirch = placedFeatures.getOrThrow(WilderTreePlaced.DYING_SUPER_BIRCH);
		var placedSuperBirchBees0004 = placedFeatures.getOrThrow(WilderTreePlaced.SUPER_BIRCH_BEES_0004);
		var placedSpruceChecked = placedFeatures.getOrThrow(WilderTreePlaced.SPRUCE_CHECKED);
		var placedFungusPineChecked = placedFeatures.getOrThrow(WilderTreePlaced.FUNGUS_PINE_CHECKED);
		var placedDyingFungusPineChecked = placedFeatures.getOrThrow(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED);
		var placedSpruceShortChecked = placedFeatures.getOrThrow(WilderTreePlaced.SPRUCE_SHORT_CHECKED);
		var placedShortBirch = placedFeatures.getOrThrow(WilderTreePlaced.SHORT_BIRCH);
		var placedOakChecked = placedFeatures.getOrThrow(WilderTreePlaced.OAK_CHECKED);
		var placedDyingDarkOakChecked = placedFeatures.getOrThrow(WilderTreePlaced.DYING_DARK_OAK_CHECKED);
		var placedTallDarkOakChecked = placedFeatures.getOrThrow(WilderTreePlaced.TALL_DARK_OAK_CHECKED);
		var placedDyingTallDarkOakChecked = placedFeatures.getOrThrow(WilderTreePlaced.DYING_TALL_DARK_OAK_CHECKED);
		var placedDyingFancyOakChecked = placedFeatures.getOrThrow(WilderTreePlaced.DYING_FANCY_OAK_CHECKED);
		var placedFancyOakChecked = placedFeatures.getOrThrow(WilderTreePlaced.FANCY_OAK_CHECKED);
		var placedMegaFungusSpruceChecked = placedFeatures.getOrThrow(WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED);
		var placedDyingMegaFungusPineChecked = placedFeatures.getOrThrow(WilderTreePlaced.DYING_MEGA_FUNGUS_PINE_CHECKED);
		var placedMegaFungusPineChecked = placedFeatures.getOrThrow(WilderTreePlaced.MEGA_FUNGUS_PINE_CHECKED);
		var placedFungusPineOnSnow = placedFeatures.getOrThrow(WilderTreePlaced.FUNGUS_PINE_ON_SNOW);
		var placedSpruceOnSnow = placedFeatures.getOrThrow(WilderTreePlaced.SPRUCE_ON_SNOW);
		var placedFancyOakBees = placedFeatures.getOrThrow(WilderTreePlaced.FANCY_OAK_BEES);
		var placedSuperBirchBees = placedFeatures.getOrThrow(WilderTreePlaced.SUPER_BIRCH_BEES);
		var placedBaobab = placedFeatures.getOrThrow(WilderTreePlaced.BAOBAB);
		var placedBaobabTall = placedFeatures.getOrThrow(WilderTreePlaced.BAOBAB_TALL);
		var placedCypress = placedFeatures.getOrThrow(WilderTreePlaced.CYPRESS);
		var placedShortCypress = placedFeatures.getOrThrow(WilderTreePlaced.SHORT_CYPRESS);
		var placedSwampCypress = placedFeatures.getOrThrow(WilderTreePlaced.SWAMP_CYPRESS);
		var placedFungusCypress = placedFeatures.getOrThrow(WilderTreePlaced.FUNGUS_CYPRESS);

		var fallenTreesMixed = register(entries, WilderConfiguredFeatures.FALLEN_TREES_MIXED, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of((new WeightedPlacedFeature(placedFallenSpruceChecked, 0.4F)), new WeightedPlacedFeature(placedFallenBirchChecked, 0.3F)), placedFallenOakChecked));
		var fallenBirch = register(entries, WilderConfiguredFeatures.FALLEN_BIRCH, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFallenBirchChecked, 1.0F)), placedFallenBirchChecked));
		var fallenSpruce = register(entries, WilderConfiguredFeatures.FALLEN_SPRUCE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFallenSpruceChecked, 1.0F)), placedFallenSpruceChecked));
		var fallenSpruceAndOak = register(entries, WilderConfiguredFeatures.FALLEN_SPRUCE_AND_OAK, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFallenSpruceChecked, 0.55F)), placedFallenOakChecked));
		var fallenBirchAndOak = register(entries, WilderConfiguredFeatures.FALLEN_BIRCH_AND_OAK, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFallenBirchChecked, 0.35F)), placedFallenOakChecked));
		var fallenCypressAndOak = register(entries, WilderConfiguredFeatures.FALLEN_CYPRESS_AND_OAK, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFallenOakChecked, 0.35F)), placedFallenCypressChecked));
		var treesPlains = register(entries, WilderConfiguredFeatures.TREES_PLAINS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(fancyOakBees0004), 0.33333334F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(fancyDyingOakBees0004), 0.035F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(shortOak), 0.169F)), PlacementUtils.inlinePlaced(oakBees0004)));
		var treesBirchAndOak = register(entries, WilderConfiguredFeatures.TREES_BIRCH_AND_OAK, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedShortBirchBees0004, 0.2F), new WeightedPlacedFeature(placedDyingShortBirch, 0.04F), new WeightedPlacedFeature(placedFancyOakBees0004, 0.26F), new WeightedPlacedFeature(placedDyingFancyOakBees0004, 0.055F), new WeightedPlacedFeature(placedDyingOakChecked, 0.04F), new WeightedPlacedFeature(placedShortOakChecked, 0.155F)), placedOakBees0004));
		var treesBirch = register(entries, WilderConfiguredFeatures.TREES_BIRCH, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedShortBirchBees0004, 0.065F), new WeightedPlacedFeature(placedDyingShortBirch, 0.012F), new WeightedPlacedFeature(placedDyingBirch, 0.035F)), placedBirchBees0004));
		var treesBirchTall = register(entries, WilderConfiguredFeatures.TREES_BIRCH_TALL, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedShortBirchBees0004, 0.002F), new WeightedPlacedFeature(placedDyingShortBirch, 0.0001F), new WeightedPlacedFeature(placedDyingSuperBirch, 0.032F), new WeightedPlacedFeature(placedBirchBees0004, 0.02F), new WeightedPlacedFeature(placedDyingBirch, 0.017F)), placedSuperBirchBees0004));
		var treesFlowerForest = register(entries, WilderConfiguredFeatures.TREES_FLOWER_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedShortBirchBees0004, 0.2F), new WeightedPlacedFeature(placedDyingShortBirch, 0.035F), new WeightedPlacedFeature(placedDyingOakChecked, 0.05F), new WeightedPlacedFeature(placedDyingFancyOakBees0004, 0.063F), new WeightedPlacedFeature(placedFancyOakBees0004, 0.205F), new WeightedPlacedFeature(placedShortOakChecked, 0.095F)), placedOakBees0004));
		var mixedTrees = register(entries, WilderConfiguredFeatures.MIXED_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedSpruceChecked, 0.39F), new WeightedPlacedFeature(placedFungusPineChecked, 0.086F), new WeightedPlacedFeature(placedDyingFungusPineChecked, 0.02F), new WeightedPlacedFeature(placedSpruceShortChecked, 0.13F), new WeightedPlacedFeature(placedFancyOakBees0004, 0.37F), new WeightedPlacedFeature(placedDyingFancyOakBees0004, 0.025F), new WeightedPlacedFeature(placedDyingOakChecked, 0.01F), new WeightedPlacedFeature(placedDyingShortBirch, 0.01F), new WeightedPlacedFeature(placedShortOakChecked, 0.13F), new WeightedPlacedFeature(placedShortBirch, 0.325F)), placedOakChecked));
		var darkForestVegetation = register(entries, WilderConfiguredFeatures.DARK_FOREST_VEGETATION, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.05F), new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.DARK_OAK_CHECKED), 0.55F), new WeightedPlacedFeature(placedDyingDarkOakChecked, 0.075F), new WeightedPlacedFeature(placedShortBirch, 0.2F), new WeightedPlacedFeature(placedDyingShortBirch, 0.015F), new WeightedPlacedFeature(placedTallDarkOakChecked, 0.35F), new WeightedPlacedFeature(placedDyingTallDarkOakChecked, 0.048F), new WeightedPlacedFeature(placedDyingFancyOakChecked, 0.02F), new WeightedPlacedFeature(placedDyingOakChecked, 0.012F), new WeightedPlacedFeature(placedFancyOakChecked, 0.185F)), placedOakChecked));
		var treesTaiga = register(entries, WilderConfiguredFeatures.TREES_TAIGA, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFungusPineChecked, 0.33333334F), new WeightedPlacedFeature(placedDyingFungusPineChecked, 0.075F)), placedSpruceChecked));
		var shortTreesTaiga = register(entries, WilderConfiguredFeatures.SHORT_TREES_TAIGA, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedSpruceShortChecked, 0.33333334F)), placedSpruceShortChecked));
		var treesOldGrowthPineTaiga = register(entries, WilderConfiguredFeatures.TREES_OLD_GROWTH_PINE_TAIGA, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedMegaFungusSpruceChecked, 0.025641026F), new WeightedPlacedFeature(placedDyingMegaFungusPineChecked, 0.028F), new WeightedPlacedFeature(placedMegaFungusPineChecked, 0.30769232F), new WeightedPlacedFeature(placedDyingFungusPineChecked, 0.045F), new WeightedPlacedFeature(placedFungusPineChecked, 0.33333334F)), placedSpruceChecked));
		var 	treesOldGrowthSpruceTaiga = register(entries, WilderConfiguredFeatures.TREES_OLD_GROWTH_SPRUCE_TAIGA, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedMegaFungusSpruceChecked, 0.33333334F), new WeightedPlacedFeature(placedDyingFungusPineChecked, 0.075F), new WeightedPlacedFeature(placedFungusPineChecked, 0.33333334F)), placedSpruceChecked));
		var treesGrove = register(entries, WilderConfiguredFeatures.TREES_GROVE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFungusPineOnSnow, 0.33333334F)), placedSpruceOnSnow));
		var treesWindsweptHills = register(entries, WilderConfiguredFeatures.TREES_WINDSWEPT_HILLS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedSpruceChecked, 0.666F), new WeightedPlacedFeature(placedDyingFancyOakChecked, 0.01F), new WeightedPlacedFeature(placedDyingOakChecked, 0.02F), new WeightedPlacedFeature(placedFancyOakChecked, 0.1F)), placedOakChecked));
		var meadowTrees = register(entries, WilderConfiguredFeatures.MEADOW_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFancyOakBees, 0.5F)), placedSuperBirchBees));
		var savannaTrees = register(entries, WilderConfiguredFeatures.SAVANNA_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F), new WeightedPlacedFeature(placedBaobab, 0.062F), new WeightedPlacedFeature(placedBaobabTall, 0.035F)), placedOakChecked));
		var windsweptSavannaTrees = register(entries, WilderConfiguredFeatures.WINDSWEPT_SAVANNA_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F)), placedOakChecked));
		var cypressWetlandsTrees = register(entries, WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedCypress, 0.37F), new WeightedPlacedFeature(placedShortCypress, 0.25F), new WeightedPlacedFeature(placedSwampCypress, 0.81F), new WeightedPlacedFeature(placedOakChecked, 0.1F)), placedFungusCypress));
		var cypressWetlandsTreesSapling = register(entries, WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES_SAPLING, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedCypress, 0.4F), new WeightedPlacedFeature(placedShortCypress, 0.15F), new WeightedPlacedFeature(placedSwampCypress, 0.81F)), placedFungusCypress));
		var cypressWetlandsTreesWater = register(entries, WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES_WATER, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedCypress, 0.2F), new WeightedPlacedFeature(placedShortCypress, 0.1F), new WeightedPlacedFeature(placedSwampCypress, 0.85F)), placedFungusCypress));
		var bigShrubs = register(entries, WilderConfiguredFeatures.BIG_SHRUBS, Feature.RANDOM_SELECTOR,
				new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(WilderTreePlaced.BIG_SHRUB_CHECKED), 1.0F)), placedFeatures.getOrThrow(WilderTreePlaced.BIG_SHRUB_CHECKED)));
		var palms = register(entries, WilderConfiguredFeatures.PALMS, Feature.RANDOM_SELECTOR,
				new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(WilderTreePlaced.TALL_WINE_PALM_CHECKED), 0.3F), new WeightedPlacedFeature(placedFeatures.getOrThrow(WilderTreePlaced.TALL_PALM_CHECKED), 0.4F)), placedFeatures.getOrThrow(WilderTreePlaced.PALM_CHECKED)));
		var palmsOasis = register(entries, WilderConfiguredFeatures.PALMS_OASIS, Feature.RANDOM_SELECTOR,
				new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(WilderTreePlaced.TALL_PALM_CHECKED), 0.5F), new WeightedPlacedFeature(placedFeatures.getOrThrow(WilderTreePlaced.TALL_WINE_PALM_CHECKED), 0.1F), new WeightedPlacedFeature(placedFeatures.getOrThrow(WilderTreePlaced.SMALL_WINE_PALM_CHECKED), 0.37F)), placedFeatures.getOrThrow(WilderTreePlaced.PALM_CHECKED)));
		var seedingDandelion = register(entries, WilderConfiguredFeatures.SEEDING_DANDELION, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(48, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.SEEDING_DANDELION)))));
		var carnation = register(entries, WilderConfiguredFeatures.CARNATION, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(48, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.CARNATION)))));
		var datura = register(entries, WilderConfiguredFeatures.DATURA, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(64, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.DATURA)))));
		var flowerPlains = register(entries, WilderConfiguredFeatures.FLOWER_PLAINS, Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new NoiseThresholdProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0), 0.005F, -0.8F, 0.33333334F, Blocks.DANDELION.defaultBlockState(), List.of(Blocks.ORANGE_TULIP.defaultBlockState(), Blocks.RED_TULIP.defaultBlockState(), Blocks.PINK_TULIP.defaultBlockState(), Blocks.WHITE_TULIP.defaultBlockState()), List.of(RegisterBlocks.SEEDING_DANDELION.defaultBlockState(), Blocks.POPPY.defaultBlockState(), Blocks.AZURE_BLUET.defaultBlockState(), Blocks.OXEYE_DAISY.defaultBlockState(), Blocks.CORNFLOWER.defaultBlockState()))))));
		var milkweed = register(entries, WilderConfiguredFeatures.MILKWEED, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(32, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.MILKWEED)))));
		var gloryOfTheSnow = register(entries, WilderConfiguredFeatures.GLORY_OF_THE_SNOW, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(64, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(WilderConfiguredFeatures.GLORY_OF_THE_SNOW_POOL)))));
		var oasisGrass = register(entries, WilderConfiguredFeatures.OASIS_GRASS, Feature.RANDOM_PATCH,
				FeatureUtils.simpleRandomPatchConfiguration(35, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
						new SimpleBlockConfiguration(new WeightedStateProvider(OASIS_GRASS_POOL)))));
		var oasisBush = register(entries, WilderConfiguredFeatures.OASIS_BUSH, Feature.RANDOM_PATCH,
				FeatureUtils.simpleRandomPatchConfiguration(23, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
						new SimpleBlockConfiguration(new WeightedStateProvider(OASIS_BUSH_POOL)))));
		var desertBush = register(entries, DESERT_BUSH, Feature.RANDOM_PATCH,
				FeatureUtils.simpleRandomPatchConfiguration(8, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
						new SimpleBlockConfiguration(new WeightedStateProvider(DESERT_BUSH_POOL)))));
		var oasisCactus = register(entries, WilderConfiguredFeatures.PATCH_CACTUS_OASIS, Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(10, PlacementUtils.inlinePlaced(Feature.BLOCK_COLUMN, BlockColumnConfiguration.simple(BiasedToBottomInt.of(3, 5), BlockStateProvider.simple(Blocks.CACTUS)), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO))))));
		var cactusTall = register(entries, PATCH_CACTUS_TALL, Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(8, PlacementUtils.inlinePlaced(Feature.BLOCK_COLUMN, BlockColumnConfiguration.simple(BiasedToBottomInt.of(4, 5), BlockStateProvider.simple(Blocks.CACTUS)), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO))))));
		var largeFernAndGrass = register(entries, WilderConfiguredFeatures.LARGE_FERN_AND_GRASS, Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(36, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(WilderConfiguredFeatures.LARGE_FERN_AND_GRASS_POOL)))));
		var pollen = register(entries, WilderConfiguredFeatures.POLLEN, Feature.MULTIFACE_GROWTH, new MultifaceGrowthConfiguration((MultifaceBlock) RegisterBlocks.POLLEN_BLOCK, 20, true, true, true, 0.5F, HolderSet.direct(Block::builtInRegistryHolder, Blocks.GRASS_BLOCK, Blocks.BIRCH_LEAVES, Blocks.OAK_LEAVES, Blocks.OAK_LOG)));
		var brownShelfFungus = register(entries, WilderConfiguredFeatures.BROWN_SHELF_FUNGUS, WilderWild.SHELF_FUNGUS_FEATURE, new ShelfFungusFeatureConfig((ShelfFungusBlock) RegisterBlocks.BROWN_SHELF_FUNGUS, 20, true, true, true, HolderSet.direct(Block::builtInRegistryHolder, Blocks.MANGROVE_LOG, Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG, RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.MYCELIUM, Blocks.MUSHROOM_STEM, RegisterBlocks.HOLLOWED_SPRUCE_LOG)));
		var redShelfFungus = register(entries, WilderConfiguredFeatures.RED_SHELF_FUNGUS, WilderWild.SHELF_FUNGUS_FEATURE, new ShelfFungusFeatureConfig((ShelfFungusBlock) RegisterBlocks.RED_SHELF_FUNGUS, 20, true, true, true, HolderSet.direct(Block::builtInRegistryHolder, Blocks.MANGROVE_LOG, Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG, RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.MYCELIUM, Blocks.MUSHROOM_STEM)));
		var cattail = register(entries, WilderConfiguredFeatures.CATTAIL, WilderWild.CATTAIL_FEATURE, new ProbabilityFeatureConfiguration(0.8F));
		var patchFloweredWaterlily = register(entries, WilderConfiguredFeatures.PATCH_FLOWERED_WATERLILY, Feature.RANDOM_PATCH, new RandomPatchConfiguration(10, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.FLOWERING_LILY_PAD)))));
		var patchAlgae = register(entries, WilderConfiguredFeatures.PATCH_ALGAE, WilderWild.ALGAE_FEATURE, new ProbabilityFeatureConfiguration(0.8F));
		var termite = register(entries, WilderConfiguredFeatures.TERMITE, FrozenFeatures.COLUMN_WITH_DISK_FEATURE, new ColumnWithDiskFeatureConfig(RegisterBlocks.TERMITE_MOUND.defaultBlockState().setValue(RegisterProperties.NATURAL, true), UniformInt.of(4, 9), UniformInt.of(3, 7), UniformInt.of(1, 3), HolderSet.direct(Block::builtInRegistryHolder, Blocks.GRASS_BLOCK, Blocks.STONE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.GRANITE), HolderSet.direct(Block::builtInRegistryHolder, Blocks.COARSE_DIRT, Blocks.SAND, Blocks.PACKED_MUD)));
		var tumbleweed = register(entries, WilderConfiguredFeatures.TUMBLEWEED, Feature.FLOWER,
				FeatureUtils.simpleRandomPatchConfiguration(5, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
						new SimpleBlockConfiguration(new WeightedStateProvider(TUMBLEWEED_PLANT_POOL)))));
		var blueMesoglea = register(entries, WilderConfiguredFeatures.BLUE_MESOGLEA, Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(CaveFeatures.DRIPLEAF)), CaveSurface.FLOOR, ConstantInt.of(3), 0.8F, 2, 0.04F, UniformInt.of(4, 14), 0.7F));
		var blueMesogleaPool = register(entries, WilderConfiguredFeatures.BLUE_MESOGLEA_POOL, Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(CaveFeatures.DRIPLEAF)), CaveSurface.FLOOR, ConstantInt.of(3), 0.8F, 5, 0.04F, UniformInt.of(4, 14), 0.7F));
		var jellyfishCavesBlueMesoglea = register(entries, WilderConfiguredFeatures.JELLYFISH_CAVES_BLUE_MESOGLEA, Feature.RANDOM_BOOLEAN_SELECTOR, new RandomBooleanFeatureConfiguration(PlacementUtils.inlinePlaced(blueMesoglea), PlacementUtils.inlinePlaced(blueMesogleaPool)));
		var upsideDownBlueMesoglea = register(entries, WilderConfiguredFeatures.UPSIDE_DOWN_BLUE_MESOGLEA, Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), PlacementUtils.inlinePlaced(downwardsMesogleaPillar), CaveSurface.CEILING, ConstantInt.of(3), 0.8F, 2, 0.08F, UniformInt.of(4, 14), 0.7F));
		var purpleMesoglea = register(entries, WilderConfiguredFeatures.PURPLE_MESOGLEA, Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(CaveFeatures.DRIPLEAF)), CaveSurface.FLOOR, ConstantInt.of(3), 0.8F, 2, 0.04F, UniformInt.of(4, 14), 0.7F));
		var purpleMesogleaPool = register(entries, WilderConfiguredFeatures.PURPLE_MESOGLEA_POOL, Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(CaveFeatures.DRIPLEAF)), CaveSurface.FLOOR, ConstantInt.of(3), 0.8F, 5, 0.04F, UniformInt.of(4, 14), 0.7F));
		var jellyfishCavesPurpleMesoglea = register(entries, WilderConfiguredFeatures.JELLYFISH_CAVES_PURPLE_MESOGLEA, Feature.RANDOM_BOOLEAN_SELECTOR, new RandomBooleanFeatureConfiguration(PlacementUtils.inlinePlaced(purpleMesoglea), PlacementUtils.inlinePlaced(purpleMesogleaPool)));
		var upsideDownPurpleMesoglea = register(entries, WilderConfiguredFeatures.UPSIDE_DOWN_PURPLE_MESOGLEA, Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), PlacementUtils.inlinePlaced(downwardsPurpleMesogleaPillar), CaveSurface.CEILING, ConstantInt.of(3), 0.8F, 2, 0.08F, UniformInt.of(4, 14), 0.7F));
		var nematocyst = register(entries, WilderConfiguredFeatures.NEMATOCYST,
				WilderWild.NEMATOCYST_FEATURE,
				new MultifaceGrowthConfiguration(
						(MultifaceBlock) RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST,
						20,
						true, true, true, 0.98F,
						HolderSet.direct(
								Block::builtInRegistryHolder,
								Blocks.CLAY,
								Blocks.STONE,
								Blocks.ANDESITE,
								Blocks.DIORITE,
								Blocks.GRANITE,
								Blocks.DRIPSTONE_BLOCK,
								Blocks.CALCITE,
								Blocks.TUFF,
								Blocks.DEEPSLATE,
								RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA
						)
				)
		);
		var nematocystPurple = register(entries, WilderConfiguredFeatures.NEMATOCYST_PURPLE,
				WilderWild.NEMATOCYST_FEATURE,
				new MultifaceGrowthConfiguration(
						(MultifaceBlock) RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST,
						20,
						true, true, true, 0.98F,
						HolderSet.direct(
								Block::builtInRegistryHolder,
								Blocks.CLAY,
								Blocks.STONE,
								Blocks.ANDESITE,
								Blocks.DIORITE,
								Blocks.GRANITE,
								Blocks.DRIPSTONE_BLOCK,
								Blocks.CALCITE,
								Blocks.TUFF,
								Blocks.DEEPSLATE,
								RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA
						)
				)
		);
	}

	public static void bootstrapPlaced(BootstapContext<PlacedFeature> entries) {
		final var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		final var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);

		WilderSharedConstants.logWild("Registering WilderTreePlaced for", true);
		var birch = configuredFeatures.getOrThrow(WilderTreeConfigured.BIRCH);
		var birchBees0004 = configuredFeatures.getOrThrow(WilderTreeConfigured.BIRCH_BEES_0004);
		var dyingBirch = configuredFeatures.getOrThrow(WilderTreeConfigured.DYING_BIRCH);
		var shortBirch = configuredFeatures.getOrThrow(WilderTreeConfigured.SHORT_BIRCH);
		var dyingShortBirch = configuredFeatures.getOrThrow(WilderTreeConfigured.DYING_SHORT_BIRCH);
		var shortBirchBees0004 = configuredFeatures.getOrThrow(WilderTreeConfigured.SHORT_BIRCH_BEES_0004);
		var dyingSuperBirch =  configuredFeatures.getOrThrow(WilderTreeConfigured.DYING_SUPER_BIRCH);
		var superBirchBees0004 = configuredFeatures.getOrThrow(WilderTreeConfigured.SUPER_BIRCH_BEES_0004);
		var superBirchBees = configuredFeatures.getOrThrow(WilderTreeConfigured.SUPER_BIRCH_BEES);
		var fallenBirchTree = configuredFeatures.getOrThrow(WilderTreeConfigured.FALLEN_BIRCH_TREE);
		var oak = configuredFeatures.getOrThrow(WilderTreeConfigured.OAK);
		var dyingOak = configuredFeatures.getOrThrow(WilderTreeConfigured.DYING_OAK);
		var oakBees0004 = configuredFeatures.getOrThrow(WilderTreeConfigured.OAK_BEES_0004);
		var shortOak = configuredFeatures.getOrThrow(WilderTreeConfigured.SHORT_OAK);
		var fancyOak = configuredFeatures.getOrThrow(WilderTreeConfigured.FANCY_OAK);
		var fancyDyingOak = configuredFeatures.getOrThrow(WilderTreeConfigured.FANCY_DYING_OAK);
		var fancyDyingOakBees0004 = configuredFeatures.getOrThrow(WilderTreeConfigured.FANCY_DYING_OAK_BEES_0004);
		var fancyOakBees0004 = configuredFeatures.getOrThrow(WilderTreeConfigured.FANCY_OAK_BEES_0004);
		var fancyOakBees = configuredFeatures.getOrThrow(WilderTreeConfigured.FANCY_OAK_BEES);
		var fallenOakTree = configuredFeatures.getOrThrow(WilderTreeConfigured.FALLEN_OAK_TREE);
		var tallDarkOak = configuredFeatures.getOrThrow(WilderTreeConfigured.TALL_DARK_OAK);
		var dyingTallDarkOak = configuredFeatures.getOrThrow(WilderTreeConfigured.DYING_TALL_DARK_OAK);
		var dyingDarkOak = configuredFeatures.getOrThrow(WilderTreeConfigured.DYING_DARK_OAK);
		var swampTree = configuredFeatures.getOrThrow(WilderTreeConfigured.SWAMP_TREE);
		var spruce = configuredFeatures.getOrThrow(WilderTreeConfigured.SPRUCE);
		var spruceShort = configuredFeatures.getOrThrow(WilderTreeConfigured.SPRUCE_SHORT);
		var fungusPine = configuredFeatures.getOrThrow(WilderTreeConfigured.FUNGUS_PINE);
		var dyingFungusPine = configuredFeatures.getOrThrow(WilderTreeConfigured.DYING_FUNGUS_PINE);
		var megaFungusSpruce = configuredFeatures.getOrThrow(WilderTreeConfigured.MEGA_FUNGUS_SPRUCE);
		var megaFungusPine = configuredFeatures.getOrThrow(WilderTreeConfigured.MEGA_FUNGUS_PINE);
		var dyingMegaFungusPine = configuredFeatures.getOrThrow(WilderTreeConfigured.DYING_MEGA_FUNGUS_PINE);
		var fallenSpruceTree = configuredFeatures.getOrThrow(WilderTreeConfigured.FALLEN_SPRUCE_TREE);
		var baobab = configuredFeatures.getOrThrow(WilderTreeConfigured.BAOBAB);
		var baobabTall = configuredFeatures.getOrThrow(WilderTreeConfigured.BAOBAB_TALL);
		var cypress = configuredFeatures.getOrThrow(WilderTreeConfigured.CYPRESS);
		var fungusCypress = configuredFeatures.getOrThrow(WilderTreeConfigured.FUNGUS_CYPRESS);
		var shortCypress = configuredFeatures.getOrThrow(WilderTreeConfigured.SHORT_CYPRESS);
		var shortFungusCypress = configuredFeatures.getOrThrow(WilderTreeConfigured.SHORT_FUNGUS_CYPRESS);
		var swampCypress = configuredFeatures.getOrThrow(WilderTreeConfigured.SWAMP_CYPRESS);
		var fallenCypressTree = configuredFeatures.getOrThrow(WilderTreeConfigured.FALLEN_CYPRESS_TREE);
		var bigShrub = configuredFeatures.getOrThrow(WilderTreeConfigured.BIG_SHRUB);
		var palm = configuredFeatures.getOrThrow(WilderTreeConfigured.PALM);
		var tallPalm = configuredFeatures.getOrThrow(WilderTreeConfigured.TALL_PALM);
		var smallWinePalm = configuredFeatures.getOrThrow(WilderTreeConfigured.SMALL_WINE_PALM);
		var tallWinePalm = configuredFeatures.getOrThrow(WilderTreeConfigured.TALL_WINE_PALM);
		var placedBirchChecked = register(entries, WilderTreePlaced.BIRCH_CHECKED, birch, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		var placedBirchBees0004 = register(entries, WilderTreePlaced.BIRCH_BEES_0004, birchBees0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		var placedDyingBirch = register(entries, WilderTreePlaced.DYING_BIRCH, dyingBirch, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		var placedShortBirch = register(entries, WilderTreePlaced.SHORT_BIRCH, shortBirch, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		var placedDyingShortBirch = register(entries, WilderTreePlaced.DYING_SHORT_BIRCH, dyingShortBirch, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		var placedShortBirchBees0004 = register(entries, WilderTreePlaced.SHORT_BIRCH_BEES_0004, shortBirchBees0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		var placedDyingSuperBirch = register(entries, WilderTreePlaced.DYING_SUPER_BIRCH, dyingSuperBirch, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		var placedSuperBirchBees0004 = register(entries, WilderTreePlaced.SUPER_BIRCH_BEES_0004, superBirchBees0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		var placedSuperBirchBees = register(entries, WilderTreePlaced.SUPER_BIRCH_BEES, superBirchBees, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		var placedFallenBirchChecked = register(entries, WilderTreePlaced.FALLEN_BIRCH_CHECKED, fallenBirchTree, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		var placedOakChecked = register(entries, WilderTreePlaced.OAK_CHECKED, oak, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		var placedDyingOakChecked = register(entries, WilderTreePlaced.DYING_OAK_CHECKED, dyingOak, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		var placedOakBees0004 = register(entries, WilderTreePlaced.OAK_BEES_0004, oakBees0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		var placedShortOakChecked = register(entries, WilderTreePlaced.SHORT_OAK_CHECKED, shortOak, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		var placedFancyOakChecked = register(entries, WilderTreePlaced.FANCY_OAK_CHECKED, fancyOak, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		var placedDyingFancyOakChecked = register(entries, WilderTreePlaced.DYING_FANCY_OAK_CHECKED, fancyDyingOak, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		var placedDyingFancyOakBees0004 = register(entries, WilderTreePlaced.DYING_FANCY_OAK_BEES_0004, fancyDyingOakBees0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		var placedFancyOakBees0004 = register(entries, WilderTreePlaced.FANCY_OAK_BEES_0004, fancyOakBees0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		var placedFancyOakBees = register(entries, WilderTreePlaced.FANCY_OAK_BEES, fancyOakBees, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		var placedFallenOakChecked = register(entries, WilderTreePlaced.FALLEN_OAK_CHECKED, fallenOakTree, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		var placedTallDarkOakChecked = register(entries, WilderTreePlaced.TALL_DARK_OAK_CHECKED, tallDarkOak, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));
		var placedDyingTallDarkOakChecked = register(entries, WilderTreePlaced.DYING_TALL_DARK_OAK_CHECKED, dyingTallDarkOak, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));
		var placedDyingDarkOakChecked = register(entries, WilderTreePlaced.DYING_DARK_OAK_CHECKED, dyingDarkOak, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));
		var placedSwampTreeChecked = register(entries, WilderTreePlaced.SWAMP_TREE_CHECKED, swampTree, PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE));
		var placedSpruceChecked = register(entries, WilderTreePlaced.SPRUCE_CHECKED, spruce, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
		var placedSpruceOnSnow = register(entries, WilderTreePlaced.SPRUCE_ON_SNOW, spruce, WilderTreePlaced.SNOW_TREE_FILTER_DECORATOR);
		var placedSpruceShortChecked = register(entries, WilderTreePlaced.SPRUCE_SHORT_CHECKED, spruceShort, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
		var placedFungusPineChecked = register(entries, WilderTreePlaced.FUNGUS_PINE_CHECKED, fungusPine, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
		var placedDyingFungusPineChecked = register(entries, WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED, dyingFungusPine, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
		var placedFungusPineOnSnow = register(entries, WilderTreePlaced.FUNGUS_PINE_ON_SNOW, fungusPine, WilderTreePlaced.SNOW_TREE_FILTER_DECORATOR);
		var placedMegaFungusSpruceChecked = register(entries, WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED, megaFungusSpruce, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
		var placedMegaFungusPineChecked = register(entries, WilderTreePlaced.MEGA_FUNGUS_PINE_CHECKED, megaFungusPine, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
		var placedDyingMegaFungusPineChecked = register(entries, WilderTreePlaced.DYING_MEGA_FUNGUS_PINE_CHECKED, dyingMegaFungusPine, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
		var placedFallenSpruceChecked = register(entries, WilderTreePlaced.FALLEN_SPRUCE_CHECKED, fallenSpruceTree, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
		var placedBaobab = register(entries, WilderTreePlaced.BAOBAB, baobab, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BAOBAB_NUT));
		var placedBaobabTall = register(entries, WilderTreePlaced.BAOBAB_TALL, baobabTall, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BAOBAB_NUT));
		var placedCypress = register(entries, WilderTreePlaced.CYPRESS, cypress, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
		var placedFungusCypress = register(entries, WilderTreePlaced.FUNGUS_CYPRESS, fungusCypress, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
		var placedShortCypress = register(entries, WilderTreePlaced.SHORT_CYPRESS, shortCypress, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
		var placedShortFungusCypress = register(entries, WilderTreePlaced.SHORT_FUNGUS_CYPRESS, shortFungusCypress, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
		var placedSwampCypress = register(entries, WilderTreePlaced.SWAMP_CYPRESS, swampCypress, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
		var placedFallenCypressChecked = register(entries, WilderTreePlaced.FALLEN_CYPRESS_CHECKED, fallenCypressTree, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
		var bigShrubChecked = register(entries, WilderTreePlaced.BIG_SHRUB_CHECKED, bigShrub, List.of(EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.SANDSTONE)), 8), BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.RED_SAND, Blocks.SAND))));
		var palmChecked = register(entries, WilderTreePlaced.PALM_CHECKED, palm, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.COCONUT));
		var tallPalmChecked = register(entries, WilderTreePlaced.TALL_PALM_CHECKED, tallPalm, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.COCONUT));
		var smallWinePalmChecked = register(entries, WilderTreePlaced.SMALL_WINE_PALM_CHECKED, smallWinePalm, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.COCONUT));
		var tallWinePalmChecked = register(entries, WilderTreePlaced.TALL_WINE_PALM_CHECKED, tallWinePalm, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.COCONUT));

		WilderSharedConstants.logWild("Registering WilderMiscPlaced for", true);
		var diskMud = configuredFeatures.getOrThrow(WilderMiscConfigured.DISK_MUD);
		var mudPath = configuredFeatures.getOrThrow(WilderMiscConfigured.MUD_PATH);
		var coarsePath = configuredFeatures.getOrThrow(WilderMiscConfigured.COARSE_PATH);
		var packedMudPathBadlands = configuredFeatures.getOrThrow(WilderMiscConfigured.PACKED_MUD_PATH_BADLANDS);
		var mossPath = configuredFeatures.getOrThrow(WilderMiscConfigured.MOSS_PATH);
		var sandPath = configuredFeatures.getOrThrow(WilderMiscConfigured.SAND_PATH);
		var packedMudPath = configuredFeatures.getOrThrow(WilderMiscConfigured.PACKED_MUD_PATH);
		var underWaterSandPath = configuredFeatures.getOrThrow(WilderMiscConfigured.UNDER_WATER_SAND_PATH);
		var underWaterGravelPath = configuredFeatures.getOrThrow(WilderMiscConfigured.UNDER_WATER_GRAVEL_PATH);
		var underWaterClayPath = configuredFeatures.getOrThrow(WilderMiscConfigured.UNDER_WATER_CLAY_PATH);
		var underWaterClayPathBeach = configuredFeatures.getOrThrow(WilderMiscConfigured.UNDER_WATER_CLAY_PATH_BEACH);
		var underWaterGravelPathRiver = configuredFeatures.getOrThrow(WilderMiscConfigured.UNDER_WATER_GRAVEL_PATH_RIVER);
		var orePackedMud = configuredFeatures.getOrThrow(WilderMiscConfigured.ORE_PACKED_MUD);
		var sandstonePath = configuredFeatures.getOrThrow(WilderMiscConfigured.SANDSTONE_PATH);
		var oreCalcite = configuredFeatures.getOrThrow(WilderMiscConfigured.ORE_CALCITE);
		var mesogleaPillar = configuredFeatures.getOrThrow(WilderMiscConfigured.MESOGLEA_PILLAR);
		var purpleMesogleaPillar = configuredFeatures.getOrThrow(WilderMiscConfigured.PURPLE_MESOGLEA_PILLAR);
		var deepslatePool = configuredFeatures.getOrThrow(WilderMiscConfigured.DEEPSLATE_POOL);
		var stonePool = configuredFeatures.getOrThrow(WilderMiscConfigured.STONE_POOL);
		var sandPool = configuredFeatures.getOrThrow(WilderMiscConfigured.SAND_POOL);
		var grassPath = configuredFeatures.getOrThrow(WilderMiscConfigured.GRASS_PATH);
		var oasisMossPath = configuredFeatures.getOrThrow(WilderMiscConfigured.MOSS_PATH_OASIS);
		var smallCoarsePath = configuredFeatures.getOrThrow(WilderMiscConfigured.SMALL_COARSE_PATH);

		var placedDiskMud = register(entries, WilderMiscPlaced.DISK_MUD, diskMud, CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.GRASS_BLOCK, Blocks.DIRT)), BiomeFilter.biome());
		var placedMudPath = register(entries, WilderMiscPlaced.MUD_PATH, mudPath, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		var placedCoarsePath = register(entries, WilderMiscPlaced.COARSE_PATH, coarsePath, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		var placedCoarsePath5 = register(entries, WilderMiscPlaced.COARSE_PATH_5, coarsePath, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		var placedMossPath = register(entries, WilderMiscPlaced.MOSS_PATH, mossPath, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		var placedSandPath = register(entries, WilderMiscPlaced.SAND_PATH, sandPath, RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		var placedPackedMudPath = register(entries, WilderMiscPlaced.PACKED_MUD_PATH, packedMudPath, RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		var placedUnderWaterSandPath = register(entries, WilderMiscPlaced.UNDER_WATER_SAND_PATH, underWaterSandPath, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
		var placedUnderWaterGravelPath = register(entries, WilderMiscPlaced.UNDER_WATER_GRAVEL_PATH, underWaterGravelPath, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
		var placedUnderWaterClayPath = register(entries, WilderMiscPlaced.UNDER_WATER_CLAY_PATH, underWaterClayPath, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
		var placedUnderWaterClayPathBeach = register(entries, WilderMiscPlaced.UNDER_WATER_CLAY_PATH_BEACH, underWaterClayPathBeach, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		var placedUnderWaterGravelPathRiver = register(entries, WilderMiscPlaced.UNDER_WATER_GRAVEL_PATH_RIVER, underWaterGravelPathRiver, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		var placedOrePackedMud = register(entries, WilderMiscPlaced.ORE_PACKED_MUD, orePackedMud, WilderMiscPlaced.modifiersWithCount(5, HeightRangePlacement.uniform(VerticalAnchor.absolute(42), VerticalAnchor.absolute(250))));
		var placedOreCalcite = register(entries, WilderMiscPlaced.ORE_CALCITE, oreCalcite, WilderMiscPlaced.modifiersWithCount(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-54), VerticalAnchor.absolute(64))));
		var placedMesogleaPillar = register(entries, WilderMiscPlaced.MESOGLEA_PILLAR, mesogleaPillar, CountPlacement.of(7), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), WilderMiscPlaced.ONLY_IN_WATER_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
		var placedPurpleMesogleaPillar = register(entries, WilderMiscPlaced.PURPLE_MESOGLEA_PILLAR, purpleMesogleaPillar, CountPlacement.of(7), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), WilderMiscPlaced.ONLY_IN_WATER_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
		var placedForestRockTaiga = register(entries, WilderMiscPlaced.FOREST_ROCK_TAIGA, MiscOverworldFeatures.FOREST_ROCK, RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		var placedExtraGlowLichen = register(entries, WilderMiscPlaced.EXTRA_GLOW_LICHEN, CaveFeatures.GLOW_LICHEN, CountPlacement.of(UniformInt.of(104, 157)), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13), BiomeFilter.biome());
		var placedDeepslatePool = register(entries, WilderMiscPlaced.DEEPSLATE_POOL, deepslatePool, RarityFilter.onAverageOnceEvery(13), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(5)), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
		var placedStonePool = register(entries, WilderMiscPlaced.STONE_POOL, stonePool, RarityFilter.onAverageOnceEvery(13), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(5), VerticalAnchor.aboveBottom(108)), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
		var placedJellyfishDeepslatePool = register(entries, WilderMiscPlaced.JELLYFISH_DEEPSLATE_POOL, deepslatePool, CountPlacement.of(30), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(67)), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
		var placedJellyfishStonePool = register(entries, WilderMiscPlaced.JELLYFISH_STONE_POOL, stonePool, CountPlacement.of(30), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(68), VerticalAnchor.top()), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
		var placedSandPool = register(entries, WilderMiscPlaced.SAND_POOL, sandPool, CountPlacement.of(2), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(63), VerticalAnchor.aboveBottom(256)), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
		var placedGrassPath = register(entries, WilderMiscPlaced.GRASS_PATH, grassPath, CountPlacement.of(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		var placedOasisMossPath = register(entries, WilderMiscPlaced.MOSS_PATH_OASIS, oasisMossPath, RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		var placedSandstonePath = register(entries, WilderMiscPlaced.SANDSTONE_PATH, sandstonePath, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		var placedBadlandsPackedMud = register(entries, WilderMiscPlaced.PACKED_MUD_PATH_BADLANDS, packedMudPathBadlands, RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		var placedSmallCoarsePath = register(entries, WilderMiscPlaced.SMALL_COARSE_PATH, smallCoarsePath, RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

		WilderSharedConstants.logWild("Registering WilderPlacedFeatures for", true);
		var fallenTreesMixed = configuredFeatures.getOrThrow(WilderConfiguredFeatures.FALLEN_TREES_MIXED);
		var fallenSpruceAndOak = configuredFeatures.getOrThrow(WilderConfiguredFeatures.FALLEN_SPRUCE_AND_OAK);
		var fallenBirchAndOak = configuredFeatures.getOrThrow(WilderConfiguredFeatures.FALLEN_BIRCH_AND_OAK);
		var fallenCypressAndOak = configuredFeatures.getOrThrow(WilderConfiguredFeatures.FALLEN_CYPRESS_AND_OAK);
		var fallenBirch = configuredFeatures.getOrThrow(WilderConfiguredFeatures.FALLEN_BIRCH);
		var fallenSpruce = configuredFeatures.getOrThrow(WilderConfiguredFeatures.FALLEN_SPRUCE);
		var treesPlains = configuredFeatures.getOrThrow(WilderConfiguredFeatures.TREES_PLAINS);
		var treesBirchAndOak = configuredFeatures.getOrThrow(WilderConfiguredFeatures.TREES_BIRCH_AND_OAK);
		var treesFlowerForest = configuredFeatures.getOrThrow(WilderConfiguredFeatures.TREES_FLOWER_FOREST);
		var darkForestVegetation = configuredFeatures.getOrThrow(WilderConfiguredFeatures.DARK_FOREST_VEGETATION);
		var treesBirch = configuredFeatures.getOrThrow(WilderConfiguredFeatures.TREES_BIRCH);
		var treesBirchTall = configuredFeatures.getOrThrow(WilderConfiguredFeatures.TREES_BIRCH_TALL);
		var treesTaiga = configuredFeatures.getOrThrow(WilderConfiguredFeatures.TREES_TAIGA);
		var shortTreesTaiga = configuredFeatures.getOrThrow(WilderConfiguredFeatures.SHORT_TREES_TAIGA);
		var treesOldGrowthPineTaiga = configuredFeatures.getOrThrow(WilderConfiguredFeatures.TREES_OLD_GROWTH_PINE_TAIGA);
		var treesOldGrowthSpruceTaiga = configuredFeatures.getOrThrow(WilderConfiguredFeatures.TREES_OLD_GROWTH_SPRUCE_TAIGA);
		var treesGrove = configuredFeatures.getOrThrow(WilderConfiguredFeatures.TREES_GROVE);
		var treesWindsweptHills = configuredFeatures.getOrThrow(WilderConfiguredFeatures.TREES_WINDSWEPT_HILLS);
		var meadowTrees = configuredFeatures.getOrThrow(WilderConfiguredFeatures.MEADOW_TREES);
		var windsweptSavannaTrees = configuredFeatures.getOrThrow(WilderConfiguredFeatures.WINDSWEPT_SAVANNA_TREES);
		var savannaTrees = configuredFeatures.getOrThrow(WilderConfiguredFeatures.SAVANNA_TREES);
		var mixedTrees = configuredFeatures.getOrThrow(WilderConfiguredFeatures.MIXED_TREES);
		var cypressWetlandsTrees = configuredFeatures.getOrThrow(WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES);
		var cypressWetlandsTreesWater = configuredFeatures.getOrThrow(WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES_WATER);
		var bigShrubs = configuredFeatures.getOrThrow(BIG_SHRUBS);
		var palms = configuredFeatures.getOrThrow(PALMS);
		var palmsOasis = configuredFeatures.getOrThrow(PALMS_OASIS);
		var brownShelfFungus = configuredFeatures.getOrThrow(WilderConfiguredFeatures.BROWN_SHELF_FUNGUS);
		var redShelfFungus = configuredFeatures.getOrThrow(WilderConfiguredFeatures.RED_SHELF_FUNGUS);
		var oasisGrass = configuredFeatures.getOrThrow(OASIS_GRASS);
		var oasisBush = configuredFeatures.getOrThrow(OASIS_BUSH);
		var desertBush = configuredFeatures.getOrThrow(DESERT_BUSH);
		var oasisCactus = configuredFeatures.getOrThrow(PATCH_CACTUS_OASIS);
		var cactusTall = configuredFeatures.getOrThrow(PATCH_CACTUS_TALL);
		var largeFernAndGrass = configuredFeatures.getOrThrow(WilderConfiguredFeatures.LARGE_FERN_AND_GRASS);
		var seedingDandelion = configuredFeatures.getOrThrow(WilderConfiguredFeatures.SEEDING_DANDELION);
		var carnation = configuredFeatures.getOrThrow(WilderConfiguredFeatures.CARNATION);
		var datura = configuredFeatures.getOrThrow(WilderConfiguredFeatures.DATURA);
		var flowerPlains = configuredFeatures.getOrThrow(WilderConfiguredFeatures.FLOWER_PLAINS);
		var milkweed = configuredFeatures.getOrThrow(WilderConfiguredFeatures.MILKWEED);
		var gloryOfTheSnow = configuredFeatures.getOrThrow(WilderConfiguredFeatures.GLORY_OF_THE_SNOW);
		var pollen = configuredFeatures.getOrThrow(WilderConfiguredFeatures.POLLEN);
		var cattail = configuredFeatures.getOrThrow(WilderConfiguredFeatures.CATTAIL);
		var patchFloweredWaterlily = configuredFeatures.getOrThrow(WilderConfiguredFeatures.PATCH_FLOWERED_WATERLILY);
		var patchAlgae = configuredFeatures.getOrThrow(WilderConfiguredFeatures.PATCH_ALGAE);
		var termite = configuredFeatures.getOrThrow(WilderConfiguredFeatures.TERMITE);
		var tumbleweed = configuredFeatures.getOrThrow(TUMBLEWEED);
		var jellyfishCavesBlueMesoglea = configuredFeatures.getOrThrow(WilderConfiguredFeatures.JELLYFISH_CAVES_BLUE_MESOGLEA);
		var upsideDownBlueMesoglea = configuredFeatures.getOrThrow(WilderConfiguredFeatures.UPSIDE_DOWN_BLUE_MESOGLEA);
		var jellyfishCavesPurpleMesoglea = configuredFeatures.getOrThrow(WilderConfiguredFeatures.JELLYFISH_CAVES_PURPLE_MESOGLEA);
		var upsideDownPurpleMesoglea = configuredFeatures.getOrThrow(WilderConfiguredFeatures.UPSIDE_DOWN_PURPLE_MESOGLEA);
		var nematocyst = configuredFeatures.getOrThrow(WilderConfiguredFeatures.NEMATOCYST);
		var nematocystPurple = configuredFeatures.getOrThrow(WilderConfiguredFeatures.NEMATOCYST_PURPLE);

		register(entries, WilderPlacedFeatures.FALLEN_TREES_MIXED_PLACED, fallenTreesMixed, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.FALLEN_OAK_AND_SPRUCE_PLACED,
				fallenSpruceAndOak, RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.FALLEN_OAK_AND_BIRCH_PLACED,
				fallenBirchAndOak, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.FALLEN_OAK_AND_CYPRESS_PLACED,
				fallenCypressAndOak, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.FALLEN_BIRCH_PLACED,
				fallenBirch, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.FALLEN_SPRUCE_PLACED,
				fallenSpruce, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.TREES_PLAINS,
				treesPlains,
				PlacementUtils.countExtra(0, 0.05F, 1), InSquarePlacement.spread(),
				TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
				BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)),
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.TREES_BIRCH_AND_OAK,
				treesBirchAndOak,
				treePlacement(PlacementUtils.countExtra(12, 0.1F, 1))
		);
		register(entries, WilderPlacedFeatures.TREES_FLOWER_FOREST,
				treesFlowerForest,
				treePlacement(PlacementUtils.countExtra(8, 0.1F, 1))
		);
		register(entries, WilderPlacedFeatures.DARK_FOREST_VEGETATION,
				darkForestVegetation,
				CountPlacement.of(16), InSquarePlacement.spread(),
				TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.TREES_BIRCH_PLACED,
				treesBirch,
				treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))
		);
		register(entries, WilderPlacedFeatures.BIRCH_TALL_PLACED,
				treesBirchTall,
				treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))
		);
		register(entries, WilderPlacedFeatures.SPRUCE_PLACED,
				treesTaiga,
				treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))
		);
		register(entries, WilderPlacedFeatures.SHORT_SPRUCE_PLACED,
				shortTreesTaiga,
				treePlacement(PlacementUtils.countExtra(5, 0.1F, 1))
		);
		register(entries, WilderPlacedFeatures.TREES_OLD_GROWTH_PINE_TAIGA,
				treesOldGrowthPineTaiga,
				treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))
		);
		register(entries, WilderPlacedFeatures.TREES_OLD_GROWTH_SPRUCE_TAIGA,
				treesOldGrowthSpruceTaiga,
				treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))
		);
		register(entries, WilderPlacedFeatures.TREES_SNOWY,
				spruce,
				treePlacement(PlacementUtils.countExtra(0, 0.1F, 1), Blocks.SPRUCE_SAPLING)
		);
		register(entries, WilderPlacedFeatures.TREES_GROVE,
				treesGrove,
				treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))
		);
		register(entries, WilderPlacedFeatures.TREES_WINDSWEPT_HILLS,
				treesWindsweptHills,
				treePlacement(PlacementUtils.countExtra(0, 0.1F, 1))
		);
		register(entries, WilderPlacedFeatures.TREES_WINDSWEPT_FOREST,
				treesWindsweptHills,
				treePlacement(PlacementUtils.countExtra(3, 0.1F, 1))
		);
		register(entries, WilderPlacedFeatures.TREES_MEADOW,
				meadowTrees,
				treePlacement(RarityFilter.onAverageOnceEvery(100))
		);
		register(entries, WilderPlacedFeatures.WINDSWEPT_SAVANNA_TREES,
				windsweptSavannaTrees,
				treePlacement(PlacementUtils.countExtra(2, 0.1F, 1))
		);
		register(entries, WilderPlacedFeatures.SAVANNA_TREES,
				savannaTrees,
				treePlacement(PlacementUtils.countExtra(1, 0.1F, 1))
		);
		register(entries, WilderPlacedFeatures.TREES_SWAMP,
				swampTree,
				PlacementUtils.countExtra(2, 0.1F, 1), InSquarePlacement.spread(),
				SurfaceWaterDepthFilter.forMaxDepth(4),
				PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
				BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.MANGROVE_PROPAGULE.defaultBlockState(), BlockPos.ZERO)),
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.MIXED_TREES,
				mixedTrees,
				treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))
		);
		register(entries, WilderPlacedFeatures.CYPRESS_WETLANDS_TREES,
				cypressWetlandsTrees,
				CountPlacement.of(28), InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
				BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(RegisterBlocks.CYPRESS_SAPLING.defaultBlockState(), BlockPos.ZERO)),
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.CYPRESS_WETLANDS_TREES_WATER,
				cypressWetlandsTreesWater,
				CountPlacement.of(20), SurfaceWaterDepthFilter.forMaxDepth(5),
				PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
				BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(RegisterBlocks.CYPRESS_SAPLING.defaultBlockState(), BlockPos.ZERO)),
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.BIG_SHRUB,
				bigShrubs,
				treePlacement(RarityFilter.onAverageOnceEvery(5))
		);
		register(entries, WilderPlacedFeatures.PALM,
				palms,
				treePlacement(RarityFilter.onAverageOnceEvery(4))
		);
		register(entries, WilderPlacedFeatures.PALM_OASIS,
				palmsOasis,
				treePlacement(RarityFilter.onAverageOnceEvery(3))
		);
		register(entries, WilderPlacedFeatures.PALM_RARE,
				palmsOasis,
				treePlacement(RarityFilter.onAverageOnceEvery(52))
		);
		register(entries, WilderPlacedFeatures.BROWN_SHELF_FUNGUS_PLACED,
				brownShelfFungus,
				RarityFilter.onAverageOnceEvery(1), CountPlacement.of(11),
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(),
				SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128),
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.RED_SHELF_FUNGUS_PLACED,
				redShelfFungus,
				RarityFilter.onAverageOnceEvery(1), CountPlacement.of(11),
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(),
				SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128),
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.BROWN_MUSHROOM_PLACED,
				VegetationFeatures.PATCH_BROWN_MUSHROOM,
				worldSurfaceSquaredWithCount(10)
		);
		register(entries, WilderPlacedFeatures.HUGE_RED_MUSHROOM_PLACED,
				TreeFeatures.HUGE_RED_MUSHROOM,
				RarityFilter.onAverageOnceEvery(90),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.HUGE_MUSHROOMS_SWAMP,
				VegetationFeatures.MUSHROOM_ISLAND_VEGETATION,
				RarityFilter.onAverageOnceEvery(5),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.MUSHROOM_PLACED,
				VegetationFeatures.MUSHROOM_ISLAND_VEGETATION,
				RarityFilter.onAverageOnceEvery(4),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.MIXED_MUSHROOMS_PLACED,
				VegetationFeatures.MUSHROOM_ISLAND_VEGETATION,
				RarityFilter.onAverageOnceEvery(75),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.OASIS_GRASS_PLACED,
				oasisGrass,
				worldSurfaceSquaredWithCount(19)
		);
		register(entries, WilderPlacedFeatures.OASIS_BUSH_PLACED,
				oasisBush,
				worldSurfaceSquaredWithCount(2)
		);
		register(entries, WilderPlacedFeatures.DESERT_BUSH_PLACED,
				desertBush,
				RarityFilter.onAverageOnceEvery(7),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.OASIS_CACTUS_PLACED,
				oasisCactus,
				RarityFilter.onAverageOnceEvery(2),
				InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.TALL_CACTUS_PLACED,
				cactusTall, RarityFilter.onAverageOnceEvery(8),
				InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.GRASS_PLACED,
				VegetationFeatures.PATCH_GRASS_JUNGLE,
				worldSurfaceSquaredWithCount(20)
		);
		register(entries, WilderPlacedFeatures.RARE_GRASS_PLACED,
				VegetationFeatures.PATCH_GRASS_JUNGLE,
				worldSurfaceSquaredWithCount(8)
		);
		register(entries, WilderPlacedFeatures.TALL_GRASS,
				VegetationFeatures.PATCH_TALL_GRASS,
				RarityFilter.onAverageOnceEvery(3),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.DENSE_TALL_GRASS_PLACED,
				VegetationFeatures.PATCH_TALL_GRASS,
				worldSurfaceSquaredWithCount(1)
		);
		register(entries, WilderPlacedFeatures.DENSE_FERN_PLACED,
				VegetationFeatures.PATCH_LARGE_FERN,
				worldSurfaceSquaredWithCount(1)
		);
		register(entries, WilderPlacedFeatures.SEAGRASS_CYPRESS,
				AquaticFeatures.SEAGRASS_MID,
				CountPlacement.of(56),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.LARGE_FERN_AND_GRASS,
				largeFernAndGrass,
				RarityFilter.onAverageOnceEvery(2),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.LARGE_FERN_AND_GRASS_RARE,
				largeFernAndGrass,
				RarityFilter.onAverageOnceEvery(4),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.SEEDING_DANDELION,
				seedingDandelion,
				RarityFilter.onAverageOnceEvery(9),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.SEEDING_DANDELION_MIXED,
				seedingDandelion,
				RarityFilter.onAverageOnceEvery(9),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.SEEDING_DANDELION_CYPRESS,
				seedingDandelion,
				RarityFilter.onAverageOnceEvery(9),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.CARNATION,
				carnation,
				RarityFilter.onAverageOnceEvery(7),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.DATURA_BIRCH,
				datura,
				RarityFilter.onAverageOnceEvery(9),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.FLOWER_PLAINS,
				flowerPlains,
				NoiseThresholdCountPlacement.of(-0.8, 15, 4),
				RarityFilter.onAverageOnceEvery(32),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.DENSE_FLOWER_PLACED,
				VegetationFeatures.FLOWER_DEFAULT,
				worldSurfaceSquaredWithCount(1)
		);
		register(entries, WilderPlacedFeatures.FLOWER_FOREST_FLOWERS,
				VegetationFeatures.FOREST_FLOWERS,
				CountPlacement.of(ClampedInt.of(UniformInt.of(-1, 3), 0, 3)),
				RarityFilter.onAverageOnceEvery(7),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.MILKWEED,
				milkweed,
				RarityFilter.onAverageOnceEvery(12),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.MILKWEED_CYPRESS,
				milkweed,
				RarityFilter.onAverageOnceEvery(12),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.GLORY_OF_THE_SNOW,
				gloryOfTheSnow,
				CountPlacement.of(2),
				RarityFilter.onAverageOnceEvery(11),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.POLLEN,
				pollen,
				CountPlacement.of(2),
				RarityFilter.onAverageOnceEvery(1),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				SurfaceRelativeThresholdFilter.of(Heightmap.Types.MOTION_BLOCKING, 0, 128),
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.PATCH_CATTAIL,
				cattail,
				RarityFilter.onAverageOnceEvery(4),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.PATCH_FLOWERED_WATERLILY,
				patchFloweredWaterlily,
				worldSurfaceSquaredWithCount(1)
		);
		register(entries, WilderPlacedFeatures.PATCH_ALGAE,
				patchAlgae,
				RarityFilter.onAverageOnceEvery(3),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.PATCH_ALGAE_5,
				patchAlgae,
				RarityFilter.onAverageOnceEvery(5),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.PATCH_BERRY_FOREST,
				VegetationFeatures.PATCH_BERRY_BUSH,
				RarityFilter.onAverageOnceEvery(28), InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.TERMITE,
				termite,
				CountPlacement.of(1),
				RarityFilter.onAverageOnceEvery(45),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
				SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128),
				BiomeFilter.biome()
		);

		register(entries, WilderPlacedFeatures.TUMBLEWEED,
				tumbleweed,
				RarityFilter.onAverageOnceEvery(9),
				CountPlacement.of(1),
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
				BiomeFilter.biome()
		);

		register(entries, WilderPlacedFeatures.JELLYFISH_CAVES_BLUE_MESOGLEA,
				jellyfishCavesBlueMesoglea,
				CountPlacement.of(9),
				InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
				RandomOffsetPlacement.vertical(ConstantInt.of(1)),
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.JELLYFISH_CAVES_UPSIDE_DOWN_BLUE_MESOGLEA,
				upsideDownBlueMesoglea,
				CountPlacement.of(9),
				InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
				RandomOffsetPlacement.vertical(ConstantInt.of(1)),
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.JELLYFISH_CAVES_PURPLE_MESOGLEA,
				jellyfishCavesPurpleMesoglea,
				CountPlacement.of(9),
				InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
				RandomOffsetPlacement.vertical(ConstantInt.of(1)),
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.JELLYFISH_CAVES_UPSIDE_DOWN_PURPLE_MESOGLEA,
				upsideDownPurpleMesoglea,
				CountPlacement.of(9),
				InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
				RandomOffsetPlacement.vertical(ConstantInt.of(1)),
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.NEMATOCYST,
				nematocyst,
				CountPlacement.of(ConstantInt.of(64)),
				InSquarePlacement.spread(),
				PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				BiomeFilter.biome()
		);
		register(entries, WilderPlacedFeatures.NEMATOCYST_PURPLE,
				nematocystPurple,
				CountPlacement.of(ConstantInt.of(64)),
				InSquarePlacement.spread(),
				PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				BiomeFilter.biome()
		);
	}

	public static void bootstrap(FabricDynamicRegistryProvider.Entries entries) {
		final var configuredFeatures = asLookup(entries.getLookup(Registries.CONFIGURED_FEATURE));
		final var placedFeatures = asLookup(entries.placedFeatures());
		final var biomes = asLookup(entries.getLookup(Registries.BIOME));
		final var noises = asLookup(entries.getLookup(Registries.NOISE));
		final var processorLists = asLookup(entries.getLookup(Registries.PROCESSOR_LIST));
		final var templatePools = asLookup(entries.getLookup(Registries.TEMPLATE_POOL));
		final var structures = asLookup(entries.getLookup(Registries.STRUCTURE));
		final var structureSets = asLookup(entries.getLookup(Registries.STRUCTURE_SET));

		entries.addAll(configuredFeatures);
		entries.addAll(placedFeatures);
		entries.addAll(biomes);
		entries.addAll(noises);
		entries.addAll(processorLists);
		entries.addAll(templatePools);
		entries.addAll(structures);
		entries.addAll(structureSets);
	}

	/**
	 * @param configuredResourceKey	MUST BE A VANILLA CONFIGURED FEATURE
	 */
	public static Holder<PlacedFeature> register(BootstapContext<PlacedFeature> entries, ResourceKey<PlacedFeature> resourceKey, ResourceKey<ConfiguredFeature<?, ?>> configuredResourceKey, PlacementModifier... modifiers) {
		return register(entries, resourceKey, configuredResourceKey, Arrays.asList(modifiers));
	}

	/**
	 * @param configuredResourceKey	MUST BE A VANILLA CONFIGURED FEATURE
	 */
	public static Holder<PlacedFeature> register(BootstapContext<PlacedFeature> entries, ResourceKey<PlacedFeature> resourceKey, ResourceKey<ConfiguredFeature<?, ?>> configuredResourceKey, List<PlacementModifier> modifiers) {
		return FrozenPlacementUtils.register(entries, resourceKey, entries.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(configuredResourceKey), modifiers);
	}


	public static Holder<PlacedFeature> register(BootstapContext<PlacedFeature> entries, ResourceKey<PlacedFeature> resourceKey, Holder<ConfiguredFeature<?, ?>> configuredHolder, PlacementModifier... modifiers) {
		return register(entries, resourceKey, configuredHolder, Arrays.asList(modifiers));
	}

	private static Holder<PlacedFeature> register(BootstapContext<PlacedFeature> entries, ResourceKey<PlacedFeature> resourceKey, Holder<ConfiguredFeature<?, ?>> configuredHolder, List<PlacementModifier> modifiers) {
		return FrozenPlacementUtils.register(entries, resourceKey, configuredHolder, modifiers);
	}

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<?, ?>> register(BootstapContext<ConfiguredFeature<?, ?>> entries, ResourceKey<ConfiguredFeature<?, ?>> resourceKey, F feature, FC featureConfiguration) {
		return FrozenConfiguredFeatureUtils.register(entries, resourceKey, feature, featureConfiguration);
	}

	public static <T> HolderLookup.RegistryLookup<T> asLookup(HolderGetter<T> getter) {
		return (HolderLookup.RegistryLookup<T>) getter;
	}
}
