package net.frozenblock.wilderwild.registry;

import java.util.List;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.frozenblock.lib.block.api.FrozenCeilingHangingSignBlock;
import net.frozenblock.lib.block.api.FrozenSignBlock;
import net.frozenblock.lib.block.api.FrozenWallHangingSignBlock;
import net.frozenblock.lib.block.api.FrozenWallSignBlock;
import net.frozenblock.lib.block.api.FrozenWoodTypes;
import net.frozenblock.lib.impl.BonemealBehaviors;
import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.frozenblock.wilderwild.block.AlgaeBlock;
import net.frozenblock.wilderwild.block.BaobabLeaves;
import net.frozenblock.wilderwild.block.BaobabNutBlock;
import net.frozenblock.wilderwild.block.BushBlock;
import net.frozenblock.wilderwild.block.CoconutBlock;
import net.frozenblock.wilderwild.block.DisplayLanternBlock;
import net.frozenblock.wilderwild.block.EchoGlassBlock;
import net.frozenblock.wilderwild.block.FlowerLichenBlock;
import net.frozenblock.wilderwild.block.FloweringLilyPadBlock;
import net.frozenblock.wilderwild.block.GloryOfTheSnowBlock;
import net.frozenblock.wilderwild.block.HangingTendrilBlock;
import net.frozenblock.wilderwild.block.HollowedLogBlock;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.block.MilkweedBlock;
import net.frozenblock.wilderwild.block.NematocystBlock;
import net.frozenblock.wilderwild.block.OsseousSculkBlock;
import net.frozenblock.wilderwild.block.PalmCrownBlock;
import net.frozenblock.wilderwild.block.PalmLeavesBlock;
import net.frozenblock.wilderwild.block.PollenBlock;
import net.frozenblock.wilderwild.block.SculkSlabBlock;
import net.frozenblock.wilderwild.block.SculkStairsBlock;
import net.frozenblock.wilderwild.block.SculkWallBlock;
import net.frozenblock.wilderwild.block.SeedingDandelionBlock;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.block.StoneChestBlock;
import net.frozenblock.wilderwild.block.TermiteMound;
import net.frozenblock.wilderwild.block.TumbleweedBlock;
import net.frozenblock.wilderwild.block.TumbleweedPlantBlock;
import net.frozenblock.wilderwild.block.WaterloggableSaplingBlock;
import net.frozenblock.wilderwild.block.WaterloggableTallFlowerBlock;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.entity.CoconutProjectile;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.misc.FlowerColor;
import net.frozenblock.wilderwild.misc.WilderFeatureFlags;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.world.generation.sapling.CypressSaplingGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.PositionImpl;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public final class RegisterBlocks {
	private RegisterBlocks() {
		throw new UnsupportedOperationException("RegisterBlockEntities contains only static declarations.");
	}

    private static final MaterialColor BAOBAB_PLANKS_COLOR = MaterialColor.COLOR_ORANGE;
    private static final MaterialColor BAOBAB_BARK_COLOR = MaterialColor.COLOR_BROWN;
    private static final MaterialColor CYPRESS_PLANKS_COLOR = MaterialColor.COLOR_LIGHT_GRAY;
    private static final MaterialColor CYPRESS_BARK_COLOR = MaterialColor.STONE;
	private static final MaterialColor PALM_PLANKS_COLOR = MaterialColor.TERRACOTTA_WHITE;
	private static final MaterialColor PALM_BARK_COLOR = MaterialColor.COLOR_LIGHT_GRAY;

    // OTHER (BUILDING BLOCKS)
    public static final Block CHISELED_MUD_BRICKS = new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(1.5F).requiresTool().sounds(SoundType.MUD_BRICKS));

    public static void registerOtherBB() {
        registerBlockAfter(true, Items.MUD_BRICKS,"chiseled_mud_bricks", CHISELED_MUD_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
    }

    // WOOD
    public static final Block BAOBAB_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));
    public static final Block CYPRESS_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));
	public static final Block PALM_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD, PALM_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));

    public static final Block BAOBAB_LOG = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final Block CYPRESS_LOG = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
	public static final Block PALM_LOG = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? PALM_PLANKS_COLOR : PALM_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
	public static final Block PALM_CROWN = new PalmCrownBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? PALM_PLANKS_COLOR : PALM_BARK_COLOR).strength(2.0F).sound(RegisterBlockSoundGroups.PALM_CROWN));

    public static final Block STRIPPED_BAOBAB_LOG = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final Block STRIPPED_CYPRESS_LOG = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
	public static final Block STRIPPED_PALM_LOG = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? PALM_PLANKS_COLOR : PALM_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));

    public static final Block STRIPPED_BAOBAB_WOOD = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final Block STRIPPED_CYPRESS_WOOD = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
	public static final Block STRIPPED_PALM_WOOD = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? PALM_PLANKS_COLOR : PALM_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));

    public static final Block BAOBAB_WOOD = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final Block CYPRESS_WOOD = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
	public static final Block PALM_WOOD = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? PALM_PLANKS_COLOR : PALM_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));

    public static final Block BAOBAB_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));
    public static final Block CYPRESS_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));
	public static final Block PALM_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD, PALM_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));

    public static final Block BAOBAB_STAIRS = new StairBlock(BAOBAB_PLANKS.defaultBlockState(), FabricBlockSettings.copyOf(BAOBAB_PLANKS));
    public static final Block CYPRESS_STAIRS = new StairBlock(CYPRESS_PLANKS.defaultBlockState(), FabricBlockSettings.copyOf(CYPRESS_PLANKS));
	public static final Block PALM_STAIRS = new StairBlock(PALM_PLANKS.defaultBlockState(), FabricBlockSettings.copyOf(PALM_PLANKS));

    public static final Block BAOBAB_BUTTON = new ButtonBlock(
			FabricBlockSettings.copyOf(Blocks.OAK_BUTTON).color(BAOBAB_PLANKS_COLOR),
			30, true,
			SoundEvents.WOODEN_BUTTON_CLICK_OFF,
			SoundEvents.WOODEN_BUTTON_CLICK_ON
	);
    public static final Block CYPRESS_BUTTON = new ButtonBlock(
			FabricBlockSettings.copyOf(Blocks.OAK_BUTTON).color(CYPRESS_PLANKS_COLOR),
			30, true,
			SoundEvents.WOODEN_BUTTON_CLICK_OFF,
			SoundEvents.WOODEN_BUTTON_CLICK_ON
	);
	public static final Block PALM_BUTTON = new ButtonBlock(
			FabricBlockSettings.copyOf(Blocks.OAK_BUTTON).color(PALM_PLANKS_COLOR),
			30, true,
			SoundEvents.WOODEN_BUTTON_CLICK_OFF,
			SoundEvents.WOODEN_BUTTON_CLICK_ON
	);
    public static final Block BAOBAB_PRESSURE_PLATE = new PressurePlateBlock(
			PressurePlateBlock.Sensitivity.EVERYTHING,
			FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).noCollision().strength(0.5F).sounds(SoundType.WOOD),
			SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF,
			SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON
	);
    public static final Block CYPRESS_PRESSURE_PLATE = new PressurePlateBlock(
			PressurePlateBlock.Sensitivity.EVERYTHING,
			FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).noCollision().strength(0.5F).sounds(SoundType.WOOD),
			SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF,
			SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON
	);
	public static final Block PALM_PRESSURE_PLATE = new PressurePlateBlock(
			PressurePlateBlock.Sensitivity.EVERYTHING,
			FabricBlockSettings.of(Material.WOOD, PALM_PLANKS_COLOR).noCollision().strength(0.5F).sounds(SoundType.WOOD),
			SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF,
			SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON
	);

    public static final Block BAOBAB_DOOR = new DoorBlock(
			FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(3.0F).sounds(SoundType.WOOD).nonOpaque(),
			SoundEvents.WOODEN_DOOR_CLOSE,
			SoundEvents.WOODEN_DOOR_OPEN
	);
    public static final Block CYPRESS_DOOR = new DoorBlock(
			FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(3.0F).sounds(SoundType.WOOD).nonOpaque(),
			SoundEvents.WOODEN_DOOR_CLOSE,
			SoundEvents.WOODEN_DOOR_OPEN
	);
	public static final Block PALM_DOOR = new DoorBlock(
			FabricBlockSettings.of(Material.WOOD, PALM_PLANKS_COLOR).strength(3.0F).sounds(SoundType.WOOD).nonOpaque(),
			SoundEvents.WOODEN_DOOR_CLOSE,
			SoundEvents.WOODEN_DOOR_OPEN
	);

    public static final Block BAOBAB_TRAPDOOR = new TrapDoorBlock(
			FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(3.0F).sounds(SoundType.WOOD).nonOpaque().allowsSpawning(RegisterBlocks::never),
			SoundEvents.WOODEN_TRAPDOOR_CLOSE,
			SoundEvents.WOODEN_TRAPDOOR_OPEN
	);
    public static final Block CYPRESS_TRAPDOOR = new TrapDoorBlock(
			FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(3.0F).sounds(SoundType.WOOD).nonOpaque().allowsSpawning(RegisterBlocks::never),
			SoundEvents.WOODEN_TRAPDOOR_CLOSE,
			SoundEvents.WOODEN_TRAPDOOR_OPEN
	);
	public static final Block PALM_TRAPDOOR = new TrapDoorBlock(
			FabricBlockSettings.of(Material.WOOD, PALM_PLANKS_COLOR).strength(3.0F).sounds(SoundType.WOOD).nonOpaque().allowsSpawning(RegisterBlocks::never),
			SoundEvents.WOODEN_TRAPDOOR_CLOSE,
			SoundEvents.WOODEN_TRAPDOOR_OPEN
	);

    public static final Block BAOBAB_FENCE_GATE = new FenceGateBlock(
			FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS.defaultMaterialColor()).strength(2.0F, 3.0F).sounds(SoundType.WOOD),
			SoundEvents.FENCE_GATE_CLOSE,
			SoundEvents.FENCE_GATE_OPEN
	);
    public static final Block CYPRESS_FENCE_GATE = new FenceGateBlock(
			FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS.defaultMaterialColor()).strength(2.0F, 3.0F).sounds(SoundType.WOOD),
			SoundEvents.FENCE_GATE_CLOSE,
			SoundEvents.FENCE_GATE_OPEN
	);
	public static final Block PALM_FENCE_GATE = new FenceGateBlock(
			FabricBlockSettings.of(Material.WOOD, PALM_PLANKS.defaultMaterialColor()).strength(2.0F, 3.0F).sounds(SoundType.WOOD),
			SoundEvents.FENCE_GATE_CLOSE,
			SoundEvents.FENCE_GATE_OPEN
	);

    public static final Block BAOBAB_NUT = new BaobabNutBlock(FabricBlockSettings.of(Material.PLANT).ticksRandomly().breakInstantly().sounds(RegisterBlockSoundGroups.BAOBAB_NUT).offsetType(BlockBehaviour.OffsetType.XZ));
    public static final Block POTTED_BAOBAB_NUT = new FlowerPotBlock(BAOBAB_NUT, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    //public static final Block PRICKLY_PEAR_CACTUS = new PricklyPearCactusBlock(FabricBlockSettings.of(Material.CACTUS).ticksRandomly().strength(0.4F).sounds(SoundType.SWEET_BERRY_BUSH).nonOpaque());
    public static final Block CYPRESS_SAPLING = new WaterloggableSaplingBlock(new CypressSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.BIRCH_SAPLING));
    public static final Block POTTED_CYPRESS_SAPLING = new FlowerPotBlock(CYPRESS_SAPLING, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block COCONUT = new CoconutBlock(FabricBlockSettings.of(Material.PLANT).breakInstantly().ticksRandomly().sounds(RegisterBlockSoundGroups.COCONUT));
	public static final Block POTTED_COCONUT = new FlowerPotBlock(RegisterBlocks.COCONUT, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());

    public static final Block BAOBAB_LEAVES = new BaobabLeaves(FabricBlockSettings.of(Material.LEAVES, MaterialColor.COLOR_GREEN).strength(0.2F).ticksRandomly().sounds(SoundType.GRASS).nonOpaque().allowsSpawning(RegisterBlocks::canSpawnOnLeaves).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));
    public static final Block CYPRESS_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.LEAVES, MaterialColor.COLOR_GREEN).strength(0.2F).ticksRandomly().sounds(SoundType.GRASS).nonOpaque().allowsSpawning(RegisterBlocks::canSpawnOnLeaves).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));
	public static final Block PALM_LEAVES = new PalmLeavesBlock(FabricBlockSettings.of(Material.LEAVES, MaterialColor.COLOR_GREEN).strength(0.2F).ticksRandomly().sounds(RegisterBlockSoundGroups.LEAVES).nonOpaque().allowsSpawning(RegisterBlocks::canSpawnOnLeaves).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));

    public static final Block BAOBAB_FENCE = new FenceBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));
    public static final Block CYPRESS_FENCE = new FenceBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));
	public static final Block PALM_FENCE = new FenceBlock(FabricBlockSettings.of(Material.WOOD, PALM_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));

    public static final WoodType BAOBAB_WOOD_TYPE = FrozenWoodTypes.newType("wilderwildbaobab");
    public static final Block BAOBAB_SIGN_BLOCK = new FrozenSignBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_LOG.defaultMaterialColor()).noCollision().strength(1.0F).sounds(SoundType.WOOD), BAOBAB_WOOD_TYPE, WilderSharedConstants.id("blocks/baobab_sign"));
    public static final Block BAOBAB_WALL_SIGN = new FrozenWallSignBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_LOG.defaultMaterialColor()).noCollision().strength(1.0F).sounds(SoundType.WOOD).dropsLike(BAOBAB_SIGN_BLOCK), BAOBAB_WOOD_TYPE, WilderSharedConstants.id("blocks/baobab_sign"));
	public static final Block BAOBAB_HANGING_SIGN = new FrozenCeilingHangingSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_HANGING_SIGN).color(BAOBAB_LOG.defaultMaterialColor()).requiredFeatures(FeatureFlags.UPDATE_1_20, WilderFeatureFlags.UPDATE_1_20_ADDITIONS), BAOBAB_WOOD_TYPE, WilderSharedConstants.id("blocks/baobab_hanging_sign"));
	public static final Block BAOBAB_WALL_HANGING_SIGN = new FrozenCeilingHangingSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_WALL_HANGING_SIGN).color(BAOBAB_LOG.defaultMaterialColor()).requiredFeatures(FeatureFlags.UPDATE_1_20, WilderFeatureFlags.UPDATE_1_20_ADDITIONS).dropsLike(BAOBAB_HANGING_SIGN), BAOBAB_WOOD_TYPE, WilderSharedConstants.id("blocks/baobab_hanging_sign"));

    public static final WoodType CYPRESS_WOOD_TYPE = FrozenWoodTypes.newType("wilderwildcypress");
    public static final Block CYPRESS_SIGN_BLOCK = new FrozenSignBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_LOG.defaultMaterialColor()).noCollision().strength(1.0F).sounds(SoundType.WOOD), CYPRESS_WOOD_TYPE, WilderSharedConstants.id("blocks/cypress_sign"));
    public static final Block CYPRESS_WALL_SIGN = new FrozenWallSignBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_LOG.defaultMaterialColor()).noCollision().strength(1.0F).sounds(SoundType.WOOD).dropsLike(CYPRESS_SIGN_BLOCK), CYPRESS_WOOD_TYPE, WilderSharedConstants.id("blocks/cypress_sign"));
	public static final Block CYPRESS_HANGING_SIGN = new FrozenCeilingHangingSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_HANGING_SIGN).color(CYPRESS_LOG.defaultMaterialColor()).requiredFeatures(FeatureFlags.UPDATE_1_20, WilderFeatureFlags.UPDATE_1_20_ADDITIONS), CYPRESS_WOOD_TYPE, WilderSharedConstants.id("blocks/cypress_hanging_sign"));
	public static final Block CYPRESS_WALL_HANGING_SIGN = new FrozenWallHangingSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_WALL_HANGING_SIGN).color(CYPRESS_LOG.defaultMaterialColor()).requiredFeatures(FeatureFlags.UPDATE_1_20, WilderFeatureFlags.UPDATE_1_20_ADDITIONS).dropsLike(CYPRESS_HANGING_SIGN), CYPRESS_WOOD_TYPE, WilderSharedConstants.id("blocks/cypress_hanging_sign"));

	public static final WoodType PALM_WOOD_TYPE = FrozenWoodTypes.newType("wilderwildpalm");
	public static final Block PALM_SIGN_BLOCK = new FrozenSignBlock(FabricBlockSettings.of(Material.WOOD, PALM_LOG.defaultMaterialColor()).noCollision().strength(1.0F).sounds(SoundType.WOOD), PALM_WOOD_TYPE, WilderSharedConstants.id("blocks/palm_sign"));
	public static final Block PALM_WALL_SIGN = new FrozenWallSignBlock(FabricBlockSettings.of(Material.WOOD, PALM_LOG.defaultMaterialColor()).noCollision().strength(1.0F).sounds(SoundType.WOOD).dropsLike(PALM_SIGN_BLOCK), PALM_WOOD_TYPE, WilderSharedConstants.id("blocks/palm_sign"));
	public static final Block PALM_HANGING_SIGN = new FrozenCeilingHangingSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_HANGING_SIGN).color(PALM_LOG.defaultMaterialColor()).requiredFeatures(FeatureFlags.UPDATE_1_20, WilderFeatureFlags.UPDATE_1_20_ADDITIONS), PALM_WOOD_TYPE, WilderSharedConstants.id("blocks/palm_hanging_sign"));
	public static final Block PALM_WALL_HANGING_SIGN = new FrozenWallHangingSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_WALL_HANGING_SIGN).color(PALM_LOG.defaultMaterialColor()).requiredFeatures(FeatureFlags.UPDATE_1_20, WilderFeatureFlags.UPDATE_1_20_ADDITIONS).dropsLike(PALM_HANGING_SIGN), PALM_WOOD_TYPE, WilderSharedConstants.id("blocks/palm_hanging_sign"));

	public static void registerWoods() {
        String baobab = "baobab";
        String cypress = "cypress";
		String palm = "palm";

        FrozenWoodTypes.register(BAOBAB_WOOD_TYPE);
        FrozenWoodTypes.register(CYPRESS_WOOD_TYPE);
		FrozenWoodTypes.register(PALM_WOOD_TYPE);

		String wood = "baobab";
		//BAOBAB IN BUILDING BLOCKS
		registerBlockAfter(true, Items.MANGROVE_BUTTON,wood + "_log", BAOBAB_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, BAOBAB_LOG,"hollowed_" + wood + "_log", HOLLOWED_BAOBAB_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, HOLLOWED_BAOBAB_LOG,wood + "_wood", BAOBAB_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, BAOBAB_WOOD,"stripped_" + wood + "_log", STRIPPED_BAOBAB_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, STRIPPED_BAOBAB_LOG,"stripped_" + wood + "_wood", STRIPPED_BAOBAB_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, STRIPPED_BAOBAB_WOOD, wood + "_planks", BAOBAB_PLANKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, BAOBAB_PLANKS,wood + "_stairs", BAOBAB_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, BAOBAB_STAIRS,wood + "_slab", BAOBAB_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, BAOBAB_SLAB,wood + "_fence", BAOBAB_FENCE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, BAOBAB_FENCE,wood + "_fence_gate", BAOBAB_FENCE_GATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, BAOBAB_FENCE_GATE,wood + "_door", BAOBAB_DOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, BAOBAB_DOOR,wood + "_trapdoor", BAOBAB_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, BAOBAB_TRAPDOOR,wood + "_pressure_plate", BAOBAB_PRESSURE_PLATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, BAOBAB_PRESSURE_PLATE,wood + "_button", BAOBAB_BUTTON, CreativeModeTabs.BUILDING_BLOCKS);
		//BAOBAB IN NATURE
		registerBlockBefore(true, Items.CRIMSON_STEM,wood + "_log", BAOBAB_LOG, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.CRIMSON_STEM,"hollowed_" + wood + "_log", HOLLOWED_BAOBAB_LOG, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.AZALEA_LEAVES,wood + "_leaves", BAOBAB_LEAVES, CreativeModeTabs.NATURAL_BLOCKS);

		wood = "cypress";
		//CYPRESS IN BUILDING BLOCKS
		registerBlockAfter(true, BAOBAB_BUTTON,wood + "_log", CYPRESS_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, CYPRESS_LOG,"hollowed_" + wood + "_log", HOLLOWED_CYPRESS_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, HOLLOWED_CYPRESS_LOG, wood + "_wood", CYPRESS_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, CYPRESS_WOOD,"stripped_" + wood + "_log", STRIPPED_CYPRESS_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, STRIPPED_CYPRESS_LOG,"stripped_" + wood + "_wood", STRIPPED_CYPRESS_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, STRIPPED_CYPRESS_WOOD, wood + "_planks", CYPRESS_PLANKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, CYPRESS_PLANKS,wood + "_stairs", CYPRESS_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, CYPRESS_STAIRS,wood + "_slab", CYPRESS_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, CYPRESS_SLAB,wood + "_fence", CYPRESS_FENCE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, CYPRESS_FENCE,wood + "_fence_gate", CYPRESS_FENCE_GATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, CYPRESS_FENCE_GATE,wood + "_door", CYPRESS_DOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, CYPRESS_DOOR,wood + "_trapdoor", CYPRESS_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, CYPRESS_TRAPDOOR,wood + "_pressure_plate", CYPRESS_PRESSURE_PLATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, CYPRESS_PRESSURE_PLATE,wood + "_button", CYPRESS_BUTTON, CreativeModeTabs.BUILDING_BLOCKS);
		//CYPRESS IN NATURE
		registerBlockBefore(true, Items.CRIMSON_STEM,wood + "_log", CYPRESS_LOG, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.CRIMSON_STEM,"hollowed_" + wood + "_log", HOLLOWED_CYPRESS_LOG, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.AZALEA_LEAVES,wood + "_leaves", CYPRESS_LEAVES, CreativeModeTabs.NATURAL_BLOCKS);

		wood = "palm";
		//PALM IN BUILDING BLOCKS
		registerBlockAfter(true, CYPRESS_BUTTON ,wood + "_log", PALM_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, PALM_LOG,"hollowed_" + wood + "_log", HOLLOWED_PALM_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, HOLLOWED_PALM_LOG,wood + "_crown", PALM_CROWN, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, PALM_CROWN, wood + "_wood", PALM_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, PALM_WOOD,"stripped_" + wood + "_log", STRIPPED_PALM_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, STRIPPED_PALM_LOG,"stripped_" + wood + "_wood", STRIPPED_PALM_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, STRIPPED_PALM_WOOD, wood + "_planks", PALM_PLANKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, PALM_PLANKS,wood + "_stairs", PALM_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, PALM_STAIRS,wood + "_slab", PALM_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, PALM_SLAB,wood + "_fence", PALM_FENCE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, PALM_FENCE,wood + "_fence_gate", PALM_FENCE_GATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, PALM_FENCE_GATE,wood + "_door", PALM_DOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, PALM_DOOR,wood + "_trapdoor", PALM_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, PALM_TRAPDOOR,wood + "_pressure_plate", PALM_PRESSURE_PLATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, PALM_PRESSURE_PLATE,wood + "_button", PALM_BUTTON, CreativeModeTabs.BUILDING_BLOCKS);
		//PALM IN NATURE
		registerBlockBefore(true, Items.CRIMSON_STEM,wood + "_log", PALM_LOG, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.CRIMSON_STEM,wood + "_crown", PALM_CROWN, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.CRIMSON_STEM,"hollowed_" + wood + "_log", HOLLOWED_PALM_LOG, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.AZALEA_LEAVES,wood + "_leaves", PALM_LEAVES, CreativeModeTabs.NATURAL_BLOCKS);

		registerBlock(false, baobab + "_nut", BAOBAB_NUT);
		registerBlock(false, "potted_" + baobab + "_nut", POTTED_BAOBAB_NUT);

		registerBlockAfter(true, Items.MANGROVE_PROPAGULE,cypress + "_sapling", CYPRESS_SAPLING, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock(false, "potted_" + cypress + "_sapling", POTTED_CYPRESS_SAPLING);

		registerBlock(false, "coconut", COCONUT);
		registerBlock(false, "potted_coconut", POTTED_COCONUT);

        registerBlock(false, baobab + "_sign", BAOBAB_SIGN_BLOCK);
        registerBlock(false, baobab + "_wall_sign", BAOBAB_WALL_SIGN);
		registerBlock(false, baobab + "_hanging_sign", BAOBAB_HANGING_SIGN);
		registerBlock(false, baobab + "_wall_hanging_sign", BAOBAB_WALL_HANGING_SIGN);
        registerBlock(false, cypress + "_sign", CYPRESS_SIGN_BLOCK);
        registerBlock(false, cypress + "_wall_sign", CYPRESS_WALL_SIGN);
		registerBlock(false, cypress + "_hanging_sign", CYPRESS_HANGING_SIGN);
		registerBlock(false, cypress + "_wall_hanging_sign", CYPRESS_WALL_HANGING_SIGN);
		registerBlock(false, palm + "_sign", PALM_SIGN_BLOCK);
		registerBlock(false, palm + "_wall_sign", PALM_WALL_SIGN);
		registerBlock(false, palm + "_hanging_sign", PALM_HANGING_SIGN);
		registerBlock(false, palm + "_wall_hanging_sign", PALM_WALL_HANGING_SIGN);
    }

    // HOLLOWED LOGS

    public static final Block HOLLOWED_OAK_LOG = createHollowedLogBlock(MaterialColor.WOOD, MaterialColor.PODZOL);
    public static final Block HOLLOWED_SPRUCE_LOG = createHollowedLogBlock(MaterialColor.PODZOL, MaterialColor.COLOR_BROWN);
    public static final Block HOLLOWED_BIRCH_LOG = createHollowedLogBlock(MaterialColor.SAND, MaterialColor.QUARTZ);
    public static final Block HOLLOWED_JUNGLE_LOG = createHollowedLogBlock(MaterialColor.DIRT, MaterialColor.PODZOL);
    public static final Block HOLLOWED_ACACIA_LOG = createHollowedLogBlock(MaterialColor.COLOR_ORANGE, MaterialColor.STONE);
    public static final Block HOLLOWED_DARK_OAK_LOG = createHollowedLogBlock(MaterialColor.COLOR_BROWN, MaterialColor.COLOR_BROWN);
    public static final Block HOLLOWED_MANGROVE_LOG = createHollowedLogBlock(MaterialColor.COLOR_RED, MaterialColor.PODZOL);
    public static final Block HOLLOWED_BAOBAB_LOG = createHollowedLogBlock(MaterialColor.COLOR_ORANGE, MaterialColor.COLOR_BROWN);
    public static final Block HOLLOWED_CYPRESS_LOG = createHollowedLogBlock(MaterialColor.COLOR_LIGHT_GRAY, MaterialColor.STONE);
	public static final Block HOLLOWED_PALM_LOG = createHollowedLogBlock(PALM_PLANKS_COLOR, PALM_BARK_COLOR);

    public static void registerHollowedLogs() {
		registerBlockAfter(true, Items.OAK_LOG, "hollowed_oak_log", HOLLOWED_OAK_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, Items.SPRUCE_LOG, "hollowed_spruce_log", HOLLOWED_SPRUCE_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, Items.BIRCH_LOG, "hollowed_birch_log", HOLLOWED_BIRCH_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, Items.JUNGLE_LOG, "hollowed_jungle_log", HOLLOWED_JUNGLE_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, Items.ACACIA_LOG, "hollowed_acacia_log", HOLLOWED_ACACIA_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, Items.DARK_OAK_LOG, "hollowed_dark_oak_log", HOLLOWED_DARK_OAK_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, Items.MANGROVE_LOG, "hollowed_mangrove_log", HOLLOWED_MANGROVE_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlock(true, "hollowed_baobab_log", HOLLOWED_BAOBAB_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlock(true, "hollowed_cypress_log", HOLLOWED_CYPRESS_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlock(true, "hollowed_palm_log", HOLLOWED_PALM_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
    }

    // SCULK
    public static final Block SCULK_STAIRS = new SculkStairsBlock(Blocks.SCULK.defaultBlockState(), FabricBlockSettings.of(Material.SCULK).strength(0.2F).sounds(SoundType.SCULK).dropsLike(Blocks.SCULK));
    public static final Block SCULK_SLAB = new SculkSlabBlock(FabricBlockSettings.of(Material.SCULK).strength(0.2F).sounds(SoundType.SCULK).dropsLike(Blocks.SCULK));
    public static final Block SCULK_WALL = new SculkWallBlock(FabricBlockSettings.of(Material.SCULK).strength(0.2F).sounds(SoundType.SCULK).dropsLike(Blocks.SCULK));
    public static final Block OSSEOUS_SCULK = new OsseousSculkBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.SAND).requiresTool().strength(2.0F).sounds(RegisterBlockSoundGroups.OSSEOUS_SCULK));
    public static final Block HANGING_TENDRIL = new HangingTendrilBlock(FabricBlockSettings.copyOf(Blocks.SCULK_SENSOR).strength(0.7F).collidable(false).luminance((state) -> 1)
            .sounds(RegisterBlockSoundGroups.HANGING_TENDRIL).emissiveLighting((state, level, pos) -> HangingTendrilBlock.shouldHavePogLighting(state)), 4);
    public static final Block ECHO_GLASS = new EchoGlassBlock(FabricBlockSettings.of(Material.GLASS, MaterialColor.COLOR_CYAN).strength(0.3F).nonOpaque().sounds(RegisterBlockSoundGroups.ECHO_GLASS));

    public static void registerDeepDark() {
		registerBlockAfter(true, Items.DEEPSLATE_TILE_WALL,"sculk_wall", SCULK_WALL, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, Items.DEEPSLATE_TILE_WALL,"sculk_slab", SCULK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, Items.DEEPSLATE_TILE_WALL,"sculk_stairs", SCULK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, Items.DEEPSLATE_TILE_WALL,"osseous_sculk", OSSEOUS_SCULK, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(true, Items.SCULK,"osseous_sculk", OSSEOUS_SCULK, CreativeModeTabs.NATURAL_BLOCKS);
        registerBlockAfter(true, Items.SCULK_SENSOR,"hanging_tendril", HANGING_TENDRIL, CreativeModeTabs.NATURAL_BLOCKS);
        registerBlockAfter(true, Items.TINTED_GLASS, "echo_glass", ECHO_GLASS, CreativeModeTabs.FUNCTIONAL_BLOCKS);
    }

    private static boolean always(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return true;
    }

    // MISC
    private static final Material ALGAE_MATERIAL = new FabricMaterialBuilder(MaterialColor.PLANT)
            .allowsMovement()
            .lightPassesThrough()
            .notSolid()
            .destroyedByPiston()
            .build();

    // PLANTS
    public static final Block POTTED_BIG_DRIPLEAF = new FlowerPotBlock(Blocks.BIG_DRIPLEAF, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_SMALL_DRIPLEAF = new FlowerPotBlock(Blocks.SMALL_DRIPLEAF, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block POTTED_GRASS = new FlowerPotBlock(Blocks.GRASS, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block SEEDING_DANDELION = new SeedingDandelionBlock(MobEffects.SLOW_FALLING, 12, FabricBlockSettings.copyOf(Blocks.DANDELION).sounds(RegisterBlockSoundGroups.FLOWER).strength(0.0F).nonOpaque());
	public static final Block POTTED_SEEDING_DANDELION = new FlowerPotBlock(SEEDING_DANDELION, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block CARNATION = new FlowerBlock(MobEffects.REGENERATION, 12, FabricBlockSettings.copyOf(Blocks.DANDELION).sounds(RegisterBlockSoundGroups.FLOWER).strength(0.0F).nonOpaque());
	public static final Block POTTED_CARNATION = new FlowerPotBlock(CARNATION, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block DATURA = new TallFlowerBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
	public static final Block GLORY_OF_THE_SNOW = new GloryOfTheSnowBlock(FabricBlockSettings.copyOf(Blocks.DANDELION).sounds(RegisterBlockSoundGroups.FLOWER).strength(0.0F).nonOpaque().ticksRandomly(), List.of(FlowerColor.BLUE, FlowerColor.PINK, FlowerColor.PURPLE, FlowerColor.WHITE));
	public static final Block BLUE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.COLOR_BLUE).sound(SoundType.VINE));
	public static final Block PINK_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.CRIMSON_STEM).sound(SoundType.VINE));
	public static final Block PURPLE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.COLOR_PURPLE).sound(SoundType.VINE));
	public static final Block WHITE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.QUARTZ).sound(SoundType.VINE));
	public static final Block CATTAIL = new WaterloggableTallFlowerBlock(FabricBlockSettings.copyOf(Blocks.ROSE_BUSH).sounds(SoundType.WET_GRASS).strength(0.0F).nonOpaque());
	public static final Block MILKWEED = new MilkweedBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
	public static final Block BUSH = new BushBlock(FabricBlockSettings.of(Material.PLANT).breakInstantly().nonOpaque().noCollision().sounds(SoundType.GRASS));
	public static final Block TUMBLEWEED_PLANT = new TumbleweedPlantBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().sounds(RegisterBlockSoundGroups.TUMBLEWEED_PLANT).randomTicks());
	public static final Block TUMBLEWEED = new TumbleweedBlock(FabricBlockSettings.of(Material.PLANT).breakInstantly().nonOpaque().sounds(RegisterBlockSoundGroups.TUMBLEWEED_PLANT).randomTicks());

	public static void registerPlants() {
        registerBlock(false, "potted_big_dripleaf", POTTED_BIG_DRIPLEAF);
        registerBlock(false, "potted_small_dripleaf", POTTED_SMALL_DRIPLEAF);
        registerBlock(false, "potted_grass", POTTED_GRASS);
        registerBlockAfter(true, Items.DANDELION,"seeding_dandelion", SEEDING_DANDELION, CreativeModeTabs.NATURAL_BLOCKS);
        registerBlock(false, "potted_seeding_dandelion", POTTED_SEEDING_DANDELION);
        registerBlockAfter(true, Items.CORNFLOWER, "carnation", CARNATION, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(true, Items.CORNFLOWER, "datura", DATURA, CreativeModeTabs.NATURAL_BLOCKS);
        registerBlock(false, "potted_carnation", POTTED_CARNATION);
        registerBlockBefore(true, Items.WITHER_ROSE, "glory_of_the_snow", GLORY_OF_THE_SNOW, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.WITHER_ROSE,"blue_giant_glory_of_the_snow", BLUE_GLORY_OF_THE_SNOW, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.WITHER_ROSE,"pink_giant_glory_of_the_snow", PINK_GLORY_OF_THE_SNOW, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.WITHER_ROSE,"violet_beauty_glory_of_the_snow", PURPLE_GLORY_OF_THE_SNOW, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.WITHER_ROSE,"alba_glory_of_the_snow", WHITE_GLORY_OF_THE_SNOW, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(true, Items.PEONY, "cattail", CATTAIL, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(true, Items.PEONY, "milkweed", MILKWEED, CreativeModeTabs.NATURAL_BLOCKS);
        registerBlockAfter(true, Items.CACTUS, "bush", BUSH, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(true, BUSH, "tumbleweed_plant", TUMBLEWEED_PLANT, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(true, TUMBLEWEED_PLANT, "tumbleweed", TUMBLEWEED, CreativeModeTabs.NATURAL_BLOCKS);
    }

	public static final Block POLLEN_BLOCK = new PollenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.SAND).sound(SoundType.VINE));
	public static final Block RED_SHELF_FUNGUS = new ShelfFungusBlock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM_BLOCK).collidable(false).nonOpaque().sounds(RegisterBlockSoundGroups.MUSHROOM));
	public static final Block BROWN_SHELF_FUNGUS = new ShelfFungusBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM_BLOCK).luminance(1).collidable(false).nonOpaque().sounds(RegisterBlockSoundGroups.MUSHROOM));
	public static final Block ALGAE = new AlgaeBlock(FabricBlockSettings.of(ALGAE_MATERIAL).breakInstantly().nonOpaque().noCollision().sounds(RegisterBlockSoundGroups.ALGAE));
	public static final Block FLOWERING_LILY_PAD = new FloweringLilyPadBlock(FabricBlockSettings.copyOf(Blocks.LILY_PAD).sounds(RegisterBlockSoundGroups.LILYPAD));

    public static void registerNotSoPlants() {
        registerBlock(false, "pollen", POLLEN_BLOCK);
		registerBlockAfter(true, Items.RED_MUSHROOM, "red_shelf_fungus", RED_SHELF_FUNGUS, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(true, Items.RED_MUSHROOM, "brown_shelf_fungus", BROWN_SHELF_FUNGUS, CreativeModeTabs.NATURAL_BLOCKS);
		Registry.register(BuiltInRegistries.BLOCK, WilderSharedConstants.id("algae"), ALGAE);
		Registry.register(BuiltInRegistries.BLOCK, WilderSharedConstants.id("flowering_lily_pad"), FLOWERING_LILY_PAD);
    }

	public static final Block TERMITE_MOUND = new TermiteMound(FabricBlockSettings.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(0.3F).sounds(RegisterBlockSoundGroups.COARSEDIRT));
	public static final Block NULL_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.STONE).sounds(RegisterBlockSoundGroups.NULL_BLOCK));
	public static final Block STONE_CHEST = new StoneChestBlock(FabricBlockSettings.copyOf(Blocks.CHEST).sounds(SoundType.DEEPSLATE).strength(35.0F, 12.0F), () -> RegisterBlockEntities.STONE_CHEST);
	public static final Block DISPLAY_LANTERN = new DisplayLanternBlock(FabricBlockSettings.of(Material.METAL).strength(3.5F).sounds(SoundType.LANTERN).luminance((state) -> state.getValue(RegisterProperties.DISPLAY_LIGHT)));

	public static final Material MESOGLEA_MATERIAL = new FabricMaterialBuilder(MaterialColor.CLAY)
			.lightPassesThrough()
			.notSolid()
			.destroyedByPiston()
			.build();

	public static final Material NEMATOCYST_MATERIAL = new FabricMaterialBuilder(MaterialColor.CLAY)
			.allowsMovement()
			.lightPassesThrough()
			.notSolid()
			.destroyedByPiston()
			.build();

	// Mesoglea
	public static final Block BLUE_PEARLESCENT_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.QUARTZ).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundGroups.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));
	public static final Block PURPLE_PEARLESCENT_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.COLOR_PURPLE).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundGroups.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));
	public static final Block YELLOW_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.COLOR_YELLOW).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundGroups.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));
	public static final Block BLUE_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.COLOR_LIGHT_BLUE).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundGroups.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));
	public static final Block LIME_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.COLOR_LIGHT_GREEN).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundGroups.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));
	public static final Block RED_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.COLOR_RED).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundGroups.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));
	public static final Block PINK_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.COLOR_PINK).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundGroups.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));

	public static final Block BLUE_PEARLESCENT_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.QUARTZ).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundGroups.NEMATOCYST));
	public static final Block PURPLE_PEARLESCENT_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.COLOR_PURPLE).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundGroups.NEMATOCYST));
	public static final Block YELLOW_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.COLOR_YELLOW).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundGroups.NEMATOCYST));
	public static final Block BLUE_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.COLOR_LIGHT_BLUE).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundGroups.NEMATOCYST));
	public static final Block LIME_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.COLOR_LIGHT_GREEN).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundGroups.NEMATOCYST));
	public static final Block RED_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.COLOR_RED).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundGroups.NEMATOCYST));
	public static final Block PINK_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.COLOR_PINK).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundGroups.NEMATOCYST));

	public static void registerMisc() {
        registerBlockBefore(true, Items.BEE_NEST, "termite_mound", TERMITE_MOUND, CreativeModeTabs.NATURAL_BLOCKS);
        registerBlockBefore(true, Items.GLASS, "null_block", NULL_BLOCK, CreativeModeTabs.BUILDING_BLOCKS);
        registerBlockAfter(true, Items.CHEST, "stone_chest", STONE_CHEST, CreativeModeTabs.FUNCTIONAL_BLOCKS);
        registerBlockAfter(true, Items.SOUL_LANTERN, "display_lantern", DISPLAY_LANTERN, CreativeModeTabs.FUNCTIONAL_BLOCKS);

        registerBlockBefore(true, Items.SPONGE, "blue_pearlescent_mesoglea", BLUE_PEARLESCENT_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.SPONGE, "purple_pearlescent_mesoglea", PURPLE_PEARLESCENT_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.SPONGE, "blue_mesoglea", BLUE_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.SPONGE, "pink_mesoglea", PINK_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.SPONGE, "red_mesoglea", RED_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.SPONGE, "yellow_mesoglea", YELLOW_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.SPONGE, "lime_mesoglea", LIME_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);

		registerBlockBefore(true, Items.SPONGE, "blue_pearlescent_nematocyst", BLUE_PEARLESCENT_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.SPONGE, "purple_pearlescent_nematocyst", PURPLE_PEARLESCENT_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.SPONGE, "blue_nematocyst", BLUE_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.SPONGE, "pink_nematocyst", PINK_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.SPONGE, "red_nematocyst", RED_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.SPONGE, "yellow_nematocyst", YELLOW_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(true, Items.SPONGE, "lime_nematocyst", LIME_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);
    }

    public static void registerBlocks() {
        WilderSharedConstants.logWild("Registering Blocks for", WilderSharedConstants.UNSTABLE_LOGGING);

        registerOtherBB();
        registerWoods();
        registerHollowedLogs();
        registerDeepDark();
        registerPlants();
        registerNotSoPlants();
        registerMisc();
        registerBlockProperties();

		DispenserBlock.registerBehavior(RegisterItems.COCONUT, new AbstractProjectileDispenseBehavior() {
			protected Projectile getProjectile(@NotNull Level level, @NotNull Position position, @NotNull ItemStack stack) {
				return new CoconutProjectile(level, position.x(), position.y(), position.z());
			}
			protected float getUncertainty() {
				return 9.0F;
			}
			protected float getPower() {
				return 0.75F;
			}
		});

		DispenserBlock.registerBehavior(RegisterBlocks.TUMBLEWEED, new DefaultDispenseItemBehavior() {
			public ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
				Level level = source.getLevel();
				Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
				double d = source.x() + (double) direction.getStepX();
				double e = source.y() + (double) direction.getStepY();
				double f = source.z() + (double) direction.getStepZ();
				Position position = new PositionImpl(d, e, f);
				Tumbleweed tumbleweed = new Tumbleweed(RegisterEntities.TUMBLEWEED, level);
				Vec3 vec3 = (new Vec3(direction.getStepX(), direction.getStepY() + 0.1, direction.getStepZ())).normalize().add(level.random.triangle(0.0D, 0.0172275D * (double)6), level.random.triangle(0.0D, 0.0172275D * (double)6), level.random.triangle(0.0D, 0.0172275D * (double)6)).scale(1.1);
				tumbleweed.setDeltaMovement(vec3);
				tumbleweed.setPos(position.x(), position.y(), position.z());
				level.addFreshEntity(tumbleweed);
				stack.shrink(1);
				return stack;
			}
		});
    }

    private static void registerBlock(boolean registerBlockItem, String path, Block block, CreativeModeTab... tabs) {
		if (registerBlockItem) {
			registerBlockItem(path, block, tabs);
		}
        actualRegisterBlock(path, block);
    }

	private static void registerBlockBefore(boolean registerBlockItem, ItemLike comparedItem, String path, Block block, CreativeModeTab... tabs) {
		if (registerBlockItem) {
			registerBlockItemBefore(comparedItem, path, block, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
		}
		actualRegisterBlock(path, block);
	}

	private static void registerBlockAfter(boolean registerBlockItem, ItemLike comparedItem, String path, Block block, CreativeModeTab... tabs) {
		if (registerBlockItem) {
			registerBlockItemAfter(comparedItem, path, block, tabs);
		}
		actualRegisterBlock(path, block);
	}

    private static void registerBlockItem(String path, Block block, CreativeModeTab... tabs) {
		actualRegisterBlockItem(path, block);
		FrozenCreativeTabs.add(block, tabs);
    }

	private static void registerBlockItemBefore(ItemLike comparedItem, String name, Block block, CreativeModeTab... tabs) {
		registerBlockItemBefore(comparedItem, name, block, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}

	private static void registerBlockItemBefore(ItemLike comparedItem, String path, Block block, CreativeModeTab.TabVisibility tabVisibility, CreativeModeTab... tabs) {
		actualRegisterBlockItem(path, block);
		FrozenCreativeTabs.addBefore(comparedItem, block, path, tabVisibility, tabs);
	}

	private static void registerBlockItemAfter(ItemLike comparedItem, String name, Block block, CreativeModeTab... tabs) {
		registerBlockItemAfter(comparedItem, name, block, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}

	private static void registerBlockItemAfter(ItemLike comparedItem, String path, Block block, CreativeModeTab.TabVisibility visibility, CreativeModeTab... tabs) {
		actualRegisterBlockItem(path, block);
		FrozenCreativeTabs.addAfter(comparedItem, block, path, visibility, tabs);
	}

	private static void actualRegisterBlock(String path, Block block) {
		if (BuiltInRegistries.BLOCK.getOptional(WilderSharedConstants.id(path)).isEmpty()) {
			Registry.register(BuiltInRegistries.BLOCK, WilderSharedConstants.id(path), block);
		}
	}

	private static void actualRegisterBlockItem(String path, Block block) {
		if (BuiltInRegistries.ITEM.getOptional(WilderSharedConstants.id(path)).isEmpty()) {
			Registry.register(BuiltInRegistries.ITEM, WilderSharedConstants.id(path), new BlockItem(block, new FabricItemSettings()));
		}
	}

    private static HollowedLogBlock createHollowedLogBlock(MaterialColor topMapColor, MaterialColor sideMapColor) {
        return new HollowedLogBlock(FabricBlockSettings.of(Material.WOOD,
                        (state) -> state.getValue(HollowedLogBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
                .strength(2.0F).sound(RegisterBlockSoundGroups.HOLLOWED_LOG));
    }

    public static void registerBlockProperties() {
        TermiteMoundBlockEntity.Termite.addDegradable(BAOBAB_LOG, HOLLOWED_BAOBAB_LOG);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_BAOBAB_LOG, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addDegradable(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_BAOBAB_WOOD, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);

        TermiteMoundBlockEntity.Termite.addDegradable(CYPRESS_LOG, HOLLOWED_CYPRESS_LOG);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_CYPRESS_LOG, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addDegradable(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_CYPRESS_WOOD, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);

		TermiteMoundBlockEntity.Termite.addDegradable(PALM_CROWN, PALM_LOG);
		TermiteMoundBlockEntity.Termite.addDegradable(PALM_LOG, HOLLOWED_PALM_LOG);
		TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_PALM_LOG, Blocks.AIR);
		TermiteMoundBlockEntity.Termite.addDegradable(PALM_WOOD, STRIPPED_PALM_WOOD);
		TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_PALM_WOOD, Blocks.AIR);
		TermiteMoundBlockEntity.Termite.addNaturalDegradable(PALM_LOG, STRIPPED_PALM_LOG);
		TermiteMoundBlockEntity.Termite.addNaturalDegradable(PALM_WOOD, STRIPPED_PALM_WOOD);
		TermiteMoundBlockEntity.Termite.addNaturalDegradable(PALM_CROWN, PALM_LOG);
        registerStrippable();
        registerComposting();
        registerFlammability();
        registerFuels();
        registerBonemeal();
    }

    private static boolean never(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    private static boolean never(BlockState state, BlockGetter blockView, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }

    private static Boolean canSpawnOnLeaves(BlockState state, BlockGetter level, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    private static void registerStrippable() {
        StrippableBlockRegistry.register(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
        StrippableBlockRegistry.register(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);
        StrippableBlockRegistry.register(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
        StrippableBlockRegistry.register(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);
		StrippableBlockRegistry.register(PALM_LOG, STRIPPED_PALM_LOG);
		StrippableBlockRegistry.register(PALM_WOOD, STRIPPED_PALM_WOOD);
    }

    private static void registerComposting() {
        CompostingChanceRegistry.INSTANCE.add(CARNATION, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(CATTAIL, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(DATURA, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(MILKWEED, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(RegisterItems.MILKWEED_POD, 0.25F);
        CompostingChanceRegistry.INSTANCE.add(SEEDING_DANDELION, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(FLOWERING_LILY_PAD, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(BROWN_SHELF_FUNGUS, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(RED_SHELF_FUNGUS, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(CYPRESS_LEAVES, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(BAOBAB_LEAVES, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(RegisterItems.BAOBAB_NUT, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(CYPRESS_SAPLING, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(BLUE_GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(WHITE_GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(PINK_GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(PURPLE_GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ALGAE, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(TUMBLEWEED_PLANT, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(TUMBLEWEED, 0.1F);
    }

    private static void registerFlammability() {
        WilderSharedConstants.logWild("Registering Flammability for", WilderSharedConstants.UNSTABLE_LOGGING);
        FlammableBlockRegistry.getDefaultInstance().add(POLLEN_BLOCK, 100, 60);
		FlammableBlockRegistry.getDefaultInstance().add(TUMBLEWEED, 100, 60);
		FlammableBlockRegistry.getDefaultInstance().add(TUMBLEWEED_PLANT, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(SEEDING_DANDELION, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(CARNATION, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(CATTAIL, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(DATURA, 100, 20);
        FlammableBlockRegistry.getDefaultInstance().add(MILKWEED, 100, 20);

        FlammableBlockRegistry.getDefaultInstance().add(HOLLOWED_BIRCH_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(HOLLOWED_OAK_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(HOLLOWED_ACACIA_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(HOLLOWED_JUNGLE_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(HOLLOWED_DARK_OAK_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(HOLLOWED_MANGROVE_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(HOLLOWED_SPRUCE_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(HOLLOWED_BAOBAB_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_BAOBAB_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_BAOBAB_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_PLANKS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_STAIRS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_DOOR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_FENCE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_SLAB, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_FENCE_GATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_PRESSURE_PLATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_TRAPDOOR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_LEAVES, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_BUTTON, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_SIGN_BLOCK, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(BAOBAB_WALL_SIGN, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(HOLLOWED_CYPRESS_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(CYPRESS_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_CYPRESS_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(CYPRESS_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_CYPRESS_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(CYPRESS_PLANKS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(CYPRESS_STAIRS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(CYPRESS_DOOR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(CYPRESS_FENCE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(CYPRESS_SLAB, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(CYPRESS_FENCE_GATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(CYPRESS_PRESSURE_PLATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(CYPRESS_TRAPDOOR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(CYPRESS_LEAVES, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(CYPRESS_BUTTON, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(CYPRESS_SIGN_BLOCK, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(CYPRESS_WALL_SIGN, 5, 20);

		FlammableBlockRegistry.getDefaultInstance().add(HOLLOWED_PALM_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(PALM_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_PALM_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(PALM_CROWN, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(PALM_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_PALM_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(PALM_PLANKS, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(PALM_STAIRS, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(PALM_DOOR, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(PALM_FENCE, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(PALM_SLAB, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(PALM_FENCE_GATE, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(PALM_PRESSURE_PLATE, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(PALM_TRAPDOOR, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(PALM_LEAVES, 100, 60);
		FlammableBlockRegistry.getDefaultInstance().add(PALM_BUTTON, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(PALM_SIGN_BLOCK, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(PALM_WALL_SIGN, 5, 20);
    }

    private static void registerFuels() {
        WilderSharedConstants.logWild("Registering Fuels for", WilderSharedConstants.UNSTABLE_LOGGING);
        FuelRegistry registry = FuelRegistry.INSTANCE;

        registry.add(BAOBAB_FENCE.asItem(), 300);
        registry.add(BAOBAB_FENCE_GATE.asItem(), 300);
        registry.add(CYPRESS_FENCE.asItem(), 300);
        registry.add(CYPRESS_FENCE_GATE.asItem(), 300);
		registry.add(PALM_FENCE.asItem(), 300);
		registry.add(PALM_FENCE_GATE.asItem(), 300);
		registry.add(TUMBLEWEED.asItem(), 150);
		registry.add(TUMBLEWEED_PLANT.asItem(), 150);
    }

    private static void registerBonemeal() {
        BonemealBehaviors.BONEMEAL_BEHAVIORS.put(Blocks.LILY_PAD, (context, level, pos, state, face, horizontal) -> {
            WilderSharedConstants.log(Blocks.LILY_PAD, pos, "Bonemeal", WilderSharedConstants.DEV_LOGGING);
            if (!level.isClientSide) {
                level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
                level.setBlockAndUpdate(pos, FLOWERING_LILY_PAD.defaultBlockState());
                return true;
            }
            return false;
        });
		BonemealBehaviors.BONEMEAL_BEHAVIORS.put(BROWN_SHELF_FUNGUS, (context, world, pos, state, face, horizontal) -> {
			if (state.getValue(RegisterProperties.FUNGUS_STAGE) < 4) {
				WilderSharedConstants.log("Shelf Fungus Bonemealed @ " + pos + " with FungusStage of " + state.getValue(RegisterProperties.FUNGUS_STAGE), WilderSharedConstants.DEV_LOGGING);
				if (!world.isClientSide) {
					world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
					world.setBlockAndUpdate(pos, state.setValue(RegisterProperties.FUNGUS_STAGE, state.getValue(RegisterProperties.FUNGUS_STAGE) + 1));
					return true;
				}
			}
			return false;
		});
		BonemealBehaviors.BONEMEAL_BEHAVIORS.put(RED_SHELF_FUNGUS, (context, world, pos, state, face, horizontal) -> {
			if (state.getValue(RegisterProperties.FUNGUS_STAGE) < 4) {
				WilderSharedConstants.log("Shelf Fungus Bonemealed @ " + pos + " with FungusStage of " + state.getValue(RegisterProperties.FUNGUS_STAGE), WilderSharedConstants.DEV_LOGGING);
				if (!world.isClientSide) {
					world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
					world.setBlockAndUpdate(pos, state.setValue(RegisterProperties.FUNGUS_STAGE, state.getValue(RegisterProperties.FUNGUS_STAGE) + 1));
					return true;
				}
			}
			return false;
		});
		BonemealBehaviors.BONEMEAL_BEHAVIORS.put(ALGAE, (context, world, pos, state, face, horizontal) -> {
			WilderSharedConstants.log("Algae Bonemealed @ " + pos, WilderSharedConstants.DEV_LOGGING);
			if (!world.isClientSide) {
				for (Direction offset : AlgaeBlock.shuffleOffsets(world.getRandom())) {
					BlockPos blockPos = pos.relative(offset);
					if (world.getBlockState(blockPos).isAir() && state.getBlock().canSurvive(state, world, blockPos)) {
						world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
						world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, blockPos, 0);
						world.setBlockAndUpdate(blockPos, state);
						return true;
					}
				}
			}
			return false;
		});
    }

	// BLOCK FAMILIES
	public static final BlockFamily BAOBAB = BlockFamilies.familyBuilder(BAOBAB_PLANKS)
			.button(BAOBAB_BUTTON)
			.slab(BAOBAB_SLAB)
			.stairs(BAOBAB_STAIRS)
			.fence(BAOBAB_FENCE)
			.fenceGate(BAOBAB_FENCE_GATE)
			.pressurePlate(BAOBAB_PRESSURE_PLATE)
			.sign(BAOBAB_SIGN_BLOCK, BAOBAB_WALL_SIGN)
			.door(BAOBAB_DOOR)
			.trapdoor(BAOBAB_TRAPDOOR)
			.recipeGroupPrefix("wooden")
			.recipeUnlockedBy("has_planks")
			.getFamily();


	public static final BlockFamily CYPRESS = BlockFamilies.familyBuilder(CYPRESS_PLANKS)
			.button(CYPRESS_BUTTON)
			.slab(CYPRESS_SLAB)
			.stairs(CYPRESS_STAIRS)
			.fence(CYPRESS_FENCE)
			.fenceGate(CYPRESS_FENCE_GATE)
			.pressurePlate(CYPRESS_PRESSURE_PLATE)
			.sign(CYPRESS_SIGN_BLOCK, CYPRESS_WALL_SIGN)
			.door(CYPRESS_DOOR)
			.trapdoor(CYPRESS_TRAPDOOR)
			.recipeGroupPrefix("wooden")
			.recipeUnlockedBy("has_planks")
			.getFamily();

	public static final BlockFamily PALM = BlockFamilies.familyBuilder(PALM_PLANKS)
			.button(PALM_BUTTON)
			.slab(PALM_SLAB)
			.stairs(PALM_STAIRS)
			.fence(PALM_FENCE)
			.fenceGate(PALM_FENCE_GATE)
			.pressurePlate(PALM_PRESSURE_PLATE)
			.sign(PALM_SIGN_BLOCK, PALM_WALL_SIGN)
			.door(PALM_DOOR)
			.trapdoor(PALM_TRAPDOOR)
			.recipeGroupPrefix("wooden")
			.recipeUnlockedBy("has_planks")
			.getFamily();
}
