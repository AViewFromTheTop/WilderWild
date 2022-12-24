package net.frozenblock.wilderwild.misc;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.frozenblock.lib.datagen.api.FrozenBiomeTagProvider;
import net.frozenblock.lib.feature_flag.api.FrozenFeatureFlags;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterStructures;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.additions.feature.WilderFeatureBootstrap;
import net.frozenblock.wilderwild.world.generation.noise.WilderNoise;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

public class WilderWildDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
		WilderFeatureFlags.init();
		FrozenFeatureFlags.rebuild();
		final FabricDataGenerator.Pack pack = dataGenerator.createPack();
		pack.addProvider(WilderBlockLootProvider::new);
		pack.addProvider(WilderWorldgenProvider::new);
		pack.addProvider(WilderBiomeTagProvider::new);
		pack.addProvider(WilderBlockTagProvider::new);
		/*final FabricDataGenerator.Pack experimentalPack = dataGenerator.createBuiltinResourcePack(WilderSharedConstants.id("update_1_20_additions"));
		experimentalPack.addProvider((FabricDataGenerator.Pack.Factory<ExperimentRecipeProvider>) ExperimentRecipeProvider::new);
		experimentalPack.addProvider(ExperimentBlockLootTableProvider::new);
		experimentalPack.addProvider(ExperimentBlockTagProvider::new);
		experimentalPack.addProvider(
				(FabricDataGenerator.Pack.Factory<PackMetadataGenerator>) packOutput -> PackMetadataGenerator.forFeaturePack(
						packOutput, Component.translatable("dataPack.wilderwild.update_1_20_additions.description"), FeatureFlagSet.of(WilderFeatureFlags.UPDATE_1_20_ADDITIONS)
				)
		);*/
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		WilderSharedConstants.logWild("Registering Biomes for", WilderSharedConstants.UNSTABLE_LOGGING);

		registryBuilder.add(Registries.CONFIGURED_FEATURE, WilderFeatureBootstrap::bootstrapConfigured);
		registryBuilder.add(Registries.PLACED_FEATURE, WilderFeatureBootstrap::bootstrapPlaced);
		registryBuilder.add(Registries.BIOME, RegisterWorldgen::bootstrap);
		registryBuilder.add(Registries.NOISE, WilderNoise::bootstrap);
		registryBuilder.add(Registries.PROCESSOR_LIST, RegisterStructures::bootstrapProcessor);
		registryBuilder.add(Registries.TEMPLATE_POOL, RegisterStructures::bootstrapTemplatePool);
		registryBuilder.add(Registries.STRUCTURE, RegisterStructures::bootstrap);
		registryBuilder.add(Registries.STRUCTURE_SET, RegisterStructures::bootstrapStructureSet);
	}

	private static class WilderWorldgenProvider extends FabricDynamicRegistryProvider {

		public WilderWorldgenProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		protected void configure(HolderLookup.Provider registries, Entries entries) {
			WilderFeatureBootstrap.bootstrap(entries);
		}

		@Override
		public String getName() {
			return "Wilder Wild Dynamic Registries";
		}
	}

	private static class WilderBiomeTagProvider extends FrozenBiomeTagProvider {

		public WilderBiomeTagProvider(FabricDataOutput output, CompletableFuture registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			this.generateBiomeTags();
			this.generateClimateAndVegetationTags();
			this.generateUtilityTags();
			this.generateFeatureTags();
			this.generateStructureTags();
		}

		private void generateBiomeTags() {
			this.getOrCreateTagBuilder(WilderBiomeTags.BIRCH_FOREST)
					.add(Biomes.BIRCH_FOREST)
					.add(Biomes.OLD_GROWTH_BIRCH_FOREST);

			this.getOrCreateTagBuilder(ConventionalBiomeTags.CAVES)
					.addOptional(RegisterWorldgen.JELLYFISH_CAVES);

			this.getOrCreateTagBuilder(WilderBiomeTags.DARK_FOREST)
					.add(Biomes.DARK_FOREST);

			this.getOrCreateTagBuilder(WilderBiomeTags.GROVE)
					.add(Biomes.GROVE);

			this.getOrCreateTagBuilder(WilderBiomeTags.MEADOW)
					.add(Biomes.MEADOW);

			this.getOrCreateTagBuilder(WilderBiomeTags.NON_FROZEN_PLAINS)
					.add(Biomes.PLAINS)
					.add(Biomes.SUNFLOWER_PLAINS);

			this.getOrCreateTagBuilder(WilderBiomeTags.NORMAL_SAVANNA)
					.add(Biomes.SAVANNA)
					.add(Biomes.SAVANNA_PLATEAU);

			this.getOrCreateTagBuilder(WilderBiomeTags.SHORT_TAIGA)
					.add(Biomes.TAIGA)
					.add(Biomes.SNOWY_TAIGA);

			this.getOrCreateTagBuilder(WilderBiomeTags.SNOWY_PLAINS)
					.add(Biomes.SNOWY_PLAINS);

			this.getOrCreateTagBuilder(WilderBiomeTags.TALL_PINE_TAIGA)
					.add(Biomes.OLD_GROWTH_PINE_TAIGA);

			this.getOrCreateTagBuilder(WilderBiomeTags.TALL_SPRUCE_TAIGA)
					.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA);

			this.getOrCreateTagBuilder(WilderBiomeTags.WINDSWEPT_FOREST)
					.add(Biomes.WINDSWEPT_FOREST);

			this.getOrCreateTagBuilder(WilderBiomeTags.WINDSWEPT_HILLS)
					.add(Biomes.WINDSWEPT_HILLS)
					.add(Biomes.WINDSWEPT_GRAVELLY_HILLS);

			this.getOrCreateTagBuilder(WilderBiomeTags.WINDSWEPT_SAVANNA)
					.add(Biomes.WINDSWEPT_SAVANNA);
		}

		private void generateClimateAndVegetationTags() {

			this.getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_TEMPERATE)
					.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
					.addOptional(RegisterWorldgen.MIXED_FOREST);

			this.getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_WET)
					.addOptional(RegisterWorldgen.CYPRESS_WETLANDS);

			this.getOrCreateTagBuilder(ConventionalBiomeTags.TREE_CONIFEROUS)
					.addOptional(RegisterWorldgen.MIXED_FOREST);

			this.getOrCreateTagBuilder(ConventionalBiomeTags.TREE_DECIDUOUS)
					.addOptional(RegisterWorldgen.MIXED_FOREST);
		}

		private void generateUtilityTags() {
			this.getOrCreateTagBuilder(WilderBiomeTags.FIREFLY_SPAWNABLE)
					.add(Biomes.JUNGLE)
					.add(Biomes.SPARSE_JUNGLE)
					.add(Biomes.BAMBOO_JUNGLE);

			this.getOrCreateTagBuilder(WilderBiomeTags.FIREFLY_SPAWNABLE_CAVE);

			this.getOrCreateTagBuilder(WilderBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY)
					.add(Biomes.SWAMP)
					.add(Biomes.MANGROVE_SWAMP);

			this.getOrCreateTagBuilder(WilderBiomeTags.HAS_JELLYFISH)
					.add(Biomes.WARM_OCEAN)
					.addOptional(RegisterWorldgen.JELLYFISH_CAVES);

			this.getOrCreateTagBuilder(WilderBiomeTags.NO_POOLS)
					.addOptional(Biomes.DEEP_DARK);

			this.getOrCreateTagBuilder(WilderBiomeTags.PEARLESCENT_JELLYFISH)
					.addOptional(RegisterWorldgen.JELLYFISH_CAVES);

			this.getOrCreateTagBuilder(WilderBiomeTags.SWAMP_TREES)
					.add(Biomes.SWAMP);

			this.getOrCreateTagBuilder(WilderBiomeTags.HAS_TUMBLEWEED_PLANT)
					.add(Biomes.DESERT)
					.add(Biomes.BADLANDS)
					.add(Biomes.ERODED_BADLANDS)
					.add(Biomes.WOODED_BADLANDS)
					.add(Biomes.WINDSWEPT_SAVANNA)
					.add(Biomes.SAVANNA_PLATEAU);

			this.getOrCreateTagBuilder(WilderBiomeTags.HAS_TUMBLEWEED_ENTITY)
					.add(Biomes.DESERT)
					.add(Biomes.BADLANDS)
					.add(Biomes.ERODED_BADLANDS)
					.add(Biomes.WOODED_BADLANDS)
					.add(Biomes.WINDSWEPT_SAVANNA);
		}

		private void generateFeatureTags() {
			this.getOrCreateTagBuilder(WilderBiomeTags.FOREST_GRASS)
					.add(Biomes.FOREST)
					.add(Biomes.FLOWER_FOREST)
					.add(Biomes.BIRCH_FOREST)
					.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
					.add(Biomes.DARK_FOREST)
					.add(Biomes.TAIGA);
		}

		private void generateStructureTags() {
			this.getOrCreateTagBuilder(WilderBiomeTags.ABANDONED_CABIN_HAS_STRUCTURE)
					.addOptionalTag(BiomeTags.IS_OCEAN)
					.addOptionalTag(BiomeTags.IS_RIVER)
					.addOptionalTag(BiomeTags.IS_MOUNTAIN)
					.addOptionalTag(BiomeTags.IS_HILL)
					.addOptionalTag(BiomeTags.IS_TAIGA)
					.addOptionalTag(BiomeTags.IS_JUNGLE)
					.addOptionalTag(BiomeTags.IS_FOREST)
					.add(Biomes.STONY_SHORE)
					.add(Biomes.MUSHROOM_FIELDS)
					.add(Biomes.ICE_SPIKES)
					.add(Biomes.WINDSWEPT_SAVANNA)
					.add(Biomes.DESERT)
					.add(Biomes.SAVANNA)
					.add(Biomes.SNOWY_PLAINS)
					.add(Biomes.PLAINS)
					.add(Biomes.SUNFLOWER_PLAINS)
					.add(Biomes.SWAMP)
					.add(Biomes.MANGROVE_SWAMP)
					.add(Biomes.SAVANNA_PLATEAU)
					.add(Biomes.DRIPSTONE_CAVES)
					.add(Biomes.DEEP_DARK)
					.addOptional(RegisterWorldgen.JELLYFISH_CAVES)
					.addOptional(RegisterWorldgen.OASIS);

		}
	}

	private static final class WilderBlockModelProvider extends FabricModelProvider {
		public WilderBlockModelProvider(FabricDataOutput output) {
			super(output);
		}

		@Override
		public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
			blockStateModelGenerator.createHangingSign(RegisterBlocks.STRIPPED_BAOBAB_LOG, RegisterBlocks.BAOBAB_HANGING_SIGN, RegisterBlocks.BAOBAB_WALL_HANGING_SIGN);
			blockStateModelGenerator.createHangingSign(RegisterBlocks.STRIPPED_CYPRESS_LOG, RegisterBlocks.CYPRESS_HANGING_SIGN, RegisterBlocks.CYPRESS_WALL_HANGING_SIGN);
			blockStateModelGenerator.createHangingSign(RegisterBlocks.STRIPPED_PALM_LOG, RegisterBlocks.PALM_HANGING_SIGN, RegisterBlocks.PALM_WALL_HANGING_SIGN);
		}

		@Override
		public void generateItemModels(ItemModelGenerators itemModelGenerator) {
		}
	}

	private static final class WilderBlockLootProvider extends FabricBlockLootTableProvider {

		private WilderBlockLootProvider(FabricDataOutput dataOutput) {
			super(dataOutput);
		}

		@Override
		public void generate() {
			this.add(RegisterBlocks.BAOBAB_HANGING_SIGN, noDrop());
			this.add(RegisterBlocks.CYPRESS_HANGING_SIGN, noDrop());
			this.add(RegisterBlocks.PALM_HANGING_SIGN, noDrop());
		}

		@Override
		public void accept(BiConsumer<ResourceLocation, LootTable.Builder> resourceLocationBuilderBiConsumer) {

		}
	}

	private static final class WilderBlockTagProvider extends FabricTagProvider.BlockTagProvider {
		public WilderBlockTagProvider(FabricDataOutput output, CompletableFuture completableFuture) {
			super(output, completableFuture);
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			this.generateDeepDark();
			this.generateHollowedAndTermites();
		}

		private void generateDeepDark() {
			this.getOrCreateTagBuilder(WilderBlockTags.ANCIENT_CITY_BLOCKS)
					.add(Blocks.COBBLED_DEEPSLATE)
					.add(Blocks.COBBLED_DEEPSLATE_STAIRS)
					.add(Blocks.COBBLED_DEEPSLATE_SLAB)
					.add(Blocks.COBBLED_DEEPSLATE_WALL)
					.add(Blocks.POLISHED_DEEPSLATE)
					.add(Blocks.POLISHED_DEEPSLATE_STAIRS)
					.add(Blocks.POLISHED_DEEPSLATE_SLAB)
					.add(Blocks.POLISHED_DEEPSLATE_WALL)
					.add(Blocks.DEEPSLATE_BRICKS)
					.add(Blocks.DEEPSLATE_BRICK_STAIRS)
					.add(Blocks.DEEPSLATE_BRICK_SLAB)
					.add(Blocks.DEEPSLATE_BRICK_WALL)
					.add(Blocks.CRACKED_DEEPSLATE_BRICKS)
					.add(Blocks.DEEPSLATE_TILES)
					.add(Blocks.DEEPSLATE_TILE_STAIRS)
					.add(Blocks.CHISELED_DEEPSLATE)
					.add(Blocks.REINFORCED_DEEPSLATE)
					.add(Blocks.POLISHED_BASALT)
					.add(Blocks.SMOOTH_BASALT)
					.add(Blocks.DARK_OAK_LOG)
					.add(Blocks.DARK_OAK_PLANKS)
					.add(Blocks.DARK_OAK_FENCE)
					.add(Blocks.LIGHT_BLUE_CARPET)
					.add(Blocks.BLUE_CARPET)
					.add(Blocks.LIGHT_BLUE_WOOL)
					.add(Blocks.GRAY_WOOL)
					.add(Blocks.CHEST)
					.add(Blocks.LADDER)
					.add(Blocks.CANDLE)
					.add(Blocks.WHITE_CANDLE)
					.add(Blocks.SOUL_LANTERN)
					.add(Blocks.SOUL_FIRE)
					.add(Blocks.SOUL_SAND);

			this.getOrCreateTagBuilder(WilderBlockTags.ANCIENT_HORN_NON_COLLIDE)
					.add(Blocks.SCULK)
					.add(RegisterBlocks.OSSEOUS_SCULK)
					.addOptionalTag(ConventionalBlockTags.GLASS_BLOCKS)
					.addOptionalTag(ConventionalBlockTags.GLASS_PANES)
					.addOptionalTag(BlockTags.LEAVES)
					.add(Blocks.BELL)
					.add(Blocks.POINTED_DRIPSTONE)
					.add(Blocks.BAMBOO)
					.add(Blocks.ICE)
					.add(RegisterBlocks.SCULK_STAIRS)
					.add(RegisterBlocks.SCULK_SLAB)
					.add(RegisterBlocks.SCULK_WALL);

			this.getOrCreateTagBuilder(WilderBlockTags.SCULK_SLAB_REPLACEABLE)
					.add(Blocks.STONE_SLAB)
					.add(Blocks.GRANITE_SLAB)
					.add(Blocks.DIORITE_SLAB)
					.add(Blocks.ANDESITE_SLAB)
					.add(Blocks.BLACKSTONE_SLAB);

			this.getOrCreateTagBuilder(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)
					.add(Blocks.COBBLED_DEEPSLATE_SLAB)
					.add(Blocks.POLISHED_DEEPSLATE_SLAB)
					.add(Blocks.DEEPSLATE_BRICK_SLAB)
					.add(Blocks.DEEPSLATE_TILE_SLAB)
					.addOptionalTag(WilderBlockTags.SCULK_SLAB_REPLACEABLE);

			this.getOrCreateTagBuilder(WilderBlockTags.SCULK_STAIR_REPLACEABLE)
					.add(Blocks.STONE_STAIRS)
					.add(Blocks.GRANITE_STAIRS)
					.add(Blocks.DIORITE_STAIRS)
					.add(Blocks.ANDESITE_STAIRS)
					.add(Blocks.BLACKSTONE_STAIRS);

			this.getOrCreateTagBuilder(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN)
					.add(Blocks.COBBLED_DEEPSLATE_STAIRS)
					.add(Blocks.POLISHED_DEEPSLATE_STAIRS)
					.add(Blocks.DEEPSLATE_BRICK_STAIRS)
					.add(Blocks.DEEPSLATE_TILE_STAIRS)
					.addOptionalTag(WilderBlockTags.SCULK_STAIR_REPLACEABLE);

			this.getOrCreateTagBuilder(WilderBlockTags.SCULK_VEIN_REMOVE)
					.add(Blocks.SCULK)
					.add(RegisterBlocks.SCULK_WALL)
					.add(RegisterBlocks.SCULK_SLAB)
					.add(RegisterBlocks.SCULK_STAIRS);

			this.getOrCreateTagBuilder(WilderBlockTags.SCULK_WALL_REPLACEABLE)
					.add(Blocks.COBBLESTONE_WALL)
					.add(Blocks.GRANITE_WALL)
					.add(Blocks.DIORITE_WALL)
					.add(Blocks.ANDESITE_WALL)
					.add(Blocks.BLACKSTONE_WALL);

			this.getOrCreateTagBuilder(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN)
					.add(Blocks.COBBLED_DEEPSLATE_WALL)
					.add(Blocks.POLISHED_DEEPSLATE_WALL)
					.add(Blocks.DEEPSLATE_BRICK_WALL)
					.add(Blocks.DEEPSLATE_TILE_WALL)
					.addOptionalTag(WilderBlockTags.SCULK_WALL_REPLACEABLE);
		}

		private void generateHollowedAndTermites() {
			this.getOrCreateTagBuilder(WilderBlockTags.BLOCKS_TERMITE)
					.addOptionalTag(ConventionalBlockTags.GLASS_BLOCKS)
					.addOptionalTag(ConventionalBlockTags.GLASS_PANES)
					.add(RegisterBlocks.ECHO_GLASS);

			this.getOrCreateTagBuilder(WilderBlockTags.HOLLOWED_LOGS)
					.add(RegisterBlocks.HOLLOWED_ACACIA_LOG)
					.add(RegisterBlocks.HOLLOWED_BIRCH_LOG)
					.add(RegisterBlocks.HOLLOWED_DARK_OAK_LOG)
					.add(RegisterBlocks.HOLLOWED_OAK_LOG)
					.add(RegisterBlocks.HOLLOWED_JUNGLE_LOG)
					.add(RegisterBlocks.HOLLOWED_MANGROVE_LOG)
					.add(RegisterBlocks.HOLLOWED_SPRUCE_LOG)
					.add(RegisterBlocks.HOLLOWED_CYPRESS_LOG)
					.add(RegisterBlocks.HOLLOWED_BAOBAB_LOG)
					.add(RegisterBlocks.HOLLOWED_PALM_LOG);

			this.getOrCreateTagBuilder(WilderBlockTags.KILLS_TERMITE)
					.add(Blocks.POWDER_SNOW)
					.add(Blocks.WATER_CAULDRON)
					.add(Blocks.LAVA_CAULDRON)
					.add(Blocks.POWDER_SNOW_CAULDRON)
					.add(Blocks.CRIMSON_ROOTS)
					.add(Blocks.CRIMSON_PLANKS)
					.add(Blocks.WARPED_PLANKS)
					.add(Blocks.WEEPING_VINES)
					.add(Blocks.WEEPING_VINES_PLANT)
					.add(Blocks.TWISTING_VINES)
					.add(Blocks.TWISTING_VINES_PLANT)
					.add(Blocks.CRIMSON_SLAB)
					.add(Blocks.WARPED_SLAB)
					.add(Blocks.CRIMSON_PRESSURE_PLATE)
					.add(Blocks.WARPED_PRESSURE_PLATE)
					.add(Blocks.CRIMSON_FENCE)
					.add(Blocks.WARPED_FENCE)
					.add(Blocks.CRIMSON_TRAPDOOR)
					.add(Blocks.WARPED_TRAPDOOR)
					.add(Blocks.CRIMSON_FENCE_GATE)
					.add(Blocks.WARPED_FENCE_GATE)
					.add(Blocks.CRIMSON_STAIRS)
					.add(Blocks.WARPED_STAIRS)
					.add(Blocks.CRIMSON_BUTTON)
					.add(Blocks.WARPED_BUTTON)
					.add(Blocks.CRIMSON_DOOR)
					.add(Blocks.WARPED_DOOR)
					.add(Blocks.CRIMSON_SIGN)
					.add(Blocks.WARPED_SIGN)
					.add(Blocks.CRIMSON_WALL_SIGN)
					.add(Blocks.WARPED_WALL_SIGN)
					.add(Blocks.CRIMSON_STEM)
					.add(Blocks.WARPED_STEM)
					.add(Blocks.STRIPPED_WARPED_STEM)
					.add(Blocks.STRIPPED_WARPED_HYPHAE)
					.add(Blocks.WARPED_NYLIUM)
					.add(Blocks.WARPED_FUNGUS)
					.add(Blocks.STRIPPED_CRIMSON_STEM)
					.add(Blocks.STRIPPED_CRIMSON_HYPHAE)
					.add(Blocks.CRIMSON_NYLIUM)
					.add(Blocks.CRIMSON_FUNGUS)
					.add(Blocks.REDSTONE_WIRE)
					.add(Blocks.REDSTONE_BLOCK)
					.add(Blocks.REDSTONE_TORCH)
					.add(Blocks.REDSTONE_WALL_TORCH);

			this.getOrCreateTagBuilder(WilderBlockTags.TERMITE_BREAKABLE)
					.addOptionalTag(BlockTags.LEAVES)
					.addOptionalTag(WilderBlockTags.HOLLOWED_LOGS)
					.add(Blocks.BAMBOO)
					.addOptional(
							ResourceKey.create(
									Registries.BLOCK,
									new ResourceLocation("immersive_weathering", "leaf_piles")
							)
					);

		}
	}

	private static class ExperimentBlockLootTableProvider extends FabricBlockLootTableProvider {
		protected ExperimentBlockLootTableProvider(FabricDataOutput dataOutput) {
			super(dataOutput);
		}

		@Override
		public void generate() {
			this.dropSelf(RegisterBlocks.BAOBAB_HANGING_SIGN);
			this.dropSelf(RegisterBlocks.CYPRESS_HANGING_SIGN);
			this.dropSelf(RegisterBlocks.PALM_HANGING_SIGN);
		}

		@Override
		public void accept(BiConsumer<ResourceLocation, LootTable.Builder> resourceLocationBuilderBiConsumer) {

		}
	}

	private static class ExperimentBlockTagProvider extends FabricTagProvider.BlockTagProvider {

		public ExperimentBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			this.tag(BlockTags.CEILING_HANGING_SIGNS)
					.add(key(RegisterBlocks.BAOBAB_HANGING_SIGN))
					.add(key(RegisterBlocks.CYPRESS_HANGING_SIGN))
					.add(key(RegisterBlocks.PALM_HANGING_SIGN));

			this.tag(BlockTags.WALL_HANGING_SIGNS)
					.add(key(RegisterBlocks.BAOBAB_WALL_HANGING_SIGN))
					.add(key(RegisterBlocks.CYPRESS_WALL_HANGING_SIGN))
					.add(key(RegisterBlocks.PALM_WALL_HANGING_SIGN));
		}

		private static ResourceKey<Block> key(Block block) {
			return BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
		}
	}

	private static class ExperimentRecipeProvider extends RecipeProvider {

		public ExperimentRecipeProvider(PackOutput packOutput) {
			super(packOutput);
		}

		@Override
		public void buildRecipes(final @NotNull Consumer<FinishedRecipe> consumer) {
			generateForEnabledBlockFamilies(consumer, FeatureFlagSet.of(WilderFeatureFlags.UPDATE_1_20_ADDITIONS));
			hangingSign(consumer, RegisterItems.BAOBAB_HANGING_SIGN, RegisterBlocks.STRIPPED_BAOBAB_LOG);
			hangingSign(consumer, RegisterItems.CYPRESS_HANGING_SIGN, RegisterBlocks.STRIPPED_CYPRESS_LOG);
			hangingSign(consumer, RegisterItems.PALM_HANGING_SIGN, RegisterBlocks.STRIPPED_PALM_LOG);
		}
	}
}
