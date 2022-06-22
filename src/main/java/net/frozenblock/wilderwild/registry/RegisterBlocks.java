package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.*;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.item.FloweredLilyPadItem;
import net.frozenblock.wilderwild.misc.FlowerColors;
import net.frozenblock.wilderwild.mixin.SignTypeAccessor;
import net.frozenblock.wilderwild.world.gen.sapling.BaobabSaplingGenerator;
import net.frozenblock.wilderwild.world.gen.sapling.CypressSaplingGenerator;
import net.minecraft.block.*;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;

import java.util.List;

public abstract class RegisterBlocks {
    private static final MapColor planksColor = MapColor.ORANGE;
    private static final MapColor barkColor = MapColor.BROWN;
    private static final MapColor cypressPlanksColor = MapColor.LIGHT_GRAY;
    private static final MapColor cypressBarkColor = MapColor.STONE_GRAY;
    // CHISELED PACKED MUD
    public static Block CHISELED_MUD_BRICKS = new Block(FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS).strength(1.5F).requiresTool().sounds(BlockSoundGroup.MUD_BRICKS));

    // FLOWERED LILY PAD
    public static final Block FLOWERED_LILY_PAD = new FloweredLilyPadBlock(
            FabricBlockSettings.copy(Blocks.LILY_PAD)
                    .sounds(RegisterBlockSoundGroups.LILYPAD)
    );

    // HOLLOW LOGS
    public static final Block HOLLOWED_OAK_LOG = createHollowedLogBlock(MapColor.OAK_TAN, MapColor.SPRUCE_BROWN);
    public static final Block HOLLOWED_SPRUCE_LOG = createHollowedLogBlock(MapColor.SPRUCE_BROWN, MapColor.BROWN);
    public static final Block HOLLOWED_BIRCH_LOG = createHollowedLogBlock(MapColor.PALE_YELLOW, MapColor.OFF_WHITE);
    public static final Block HOLLOWED_JUNGLE_LOG = createHollowedLogBlock(MapColor.DIRT_BROWN, MapColor.SPRUCE_BROWN);
    public static final Block HOLLOWED_ACACIA_LOG = createHollowedLogBlock(MapColor.ORANGE, MapColor.STONE_GRAY);
    public static final Block HOLLOWED_DARK_OAK_LOG = createHollowedLogBlock(MapColor.BROWN, MapColor.BROWN);
    public static final Block HOLLOWED_MANGROVE_LOG = createHollowedLogBlock(MapColor.RED, MapColor.SPRUCE_BROWN);
    public static final Block HOLLOWED_BAOBAB_LOG = createHollowedLogBlock(MapColor.ORANGE, MapColor.BROWN);
    public static final Block HOLLOWED_CYPRESS_LOG = createHollowedLogBlock(MapColor.LIGHT_GRAY, MapColor.STONE_GRAY);

    public static void registerHollowedLogs() {
        registerBlock("hollowed_oak_log", HOLLOWED_OAK_LOG, ItemGroup.DECORATIONS);
        registerBlock("hollowed_spruce_log", HOLLOWED_SPRUCE_LOG, ItemGroup.DECORATIONS);
        registerBlock("hollowed_birch_log", HOLLOWED_BIRCH_LOG, ItemGroup.DECORATIONS);
        registerBlock("hollowed_jungle_log", HOLLOWED_JUNGLE_LOG, ItemGroup.DECORATIONS);
        registerBlock("hollowed_acacia_log", HOLLOWED_ACACIA_LOG, ItemGroup.DECORATIONS);
        registerBlock("hollowed_dark_oak_log", HOLLOWED_DARK_OAK_LOG, ItemGroup.DECORATIONS);
        registerBlock("hollowed_mangrove_log", HOLLOWED_MANGROVE_LOG, ItemGroup.DECORATIONS);
        registerBlock("hollowed_baobab_log", HOLLOWED_BAOBAB_LOG, ItemGroup.DECORATIONS);
        registerBlock("hollowed_cypress_log", HOLLOWED_CYPRESS_LOG, ItemGroup.DECORATIONS);
    }

    // SCULK
    public static final Block SCULK_ECHOER = new SculkEchoerBlock(FabricBlockSettings.of(Material.SCULK, MapColor.CYAN).strength(3.0F, 3.0F).sounds(BlockSoundGroup.SCULK_CATALYST), 8);
    public static final Block SCULK_JAW = new SculkJawBlock(FabricBlockSettings.of(Material.SCULK).strength(0.6F).sounds(BlockSoundGroup.SCULK));
    public static final Block OSSEOUS_SCULK = new OsseousSculkBlock(FabricBlockSettings.of(Material.STONE, MapColor.PALE_YELLOW).requiresTool().strength(2.0F).sounds(RegisterBlockSoundGroups.OSSEOUS_SCULK));
    public static final Block HANGING_TENDRIL = new HangingTendrilBlock(FabricBlockSettings.copyOf(Blocks.SCULK_SENSOR).strength(0.7F).collidable(false).luminance((state) -> 1)
            .sounds(RegisterBlockSoundGroups.HANGING_TENDRIL).emissiveLighting((state, world, pos) -> HangingTendrilBlock.shouldHavePogLighting(state)), 4);
    public static final Block ECHO_GLASS = new EchoGlassBlock(FabricBlockSettings.of(Material.GLASS, MapColor.CYAN).strength(0.3F).nonOpaque().sounds(RegisterBlockSoundGroups.ECHO_GLASS));
    public static final Block SCULK_STAIRS = new SculkStairsBlock(Blocks.SCULK.getDefaultState(), FabricBlockSettings.copy(Blocks.SCULK));
    public static final Block SCULK_SLAB = new SculkSlabBlock(FabricBlockSettings.of(Material.SCULK).strength(0.2F).sounds(BlockSoundGroup.SCULK));

    public static void registerDeepDark() {
        registerBlock("sculk_echoer", SCULK_ECHOER, ItemGroup.DECORATIONS);
        registerBlock("sculk_jaw", SCULK_JAW, ItemGroup.DECORATIONS);
        registerBlock("osseous_sculk", OSSEOUS_SCULK, ItemGroup.DECORATIONS);
        registerBlock("hanging_tendril", HANGING_TENDRIL, ItemGroup.DECORATIONS);
        registerBlock("echo_glass", ECHO_GLASS, ItemGroup.DECORATIONS);
        registerBlock("sculk_stairs", SCULK_STAIRS, ItemGroup.DECORATIONS);
        registerBlock("sculk_slab", SCULK_SLAB, ItemGroup.DECORATIONS);
    }

    public static final Block TERMITE_MOUND = new TermiteMound(FabricBlockSettings.of(Material.WOOD, MapColor.BROWN).strength(0.3F).sounds(RegisterBlockSoundGroups.COARSEDIRT));

    // PLANTS
    public static final Block DATURA = new TallFlowerBlock(FabricBlockSettings.copy(Blocks.ROSE_BUSH).strength(0.0F).nonOpaque());
    public static final Block CATTAIL = new WaterloggableTallFlowerBlock(FabricBlockSettings.copy(Blocks.ROSE_BUSH).sounds(BlockSoundGroup.WET_GRASS).strength(0.0F).nonOpaque());
    public static final Block CARNATION = new FlowerBlock(StatusEffects.REGENERATION, 12, FabricBlockSettings.copy(Blocks.DANDELION).sounds(BlockSoundGroup.SPORE_BLOSSOM).strength(0.0F).nonOpaque());
    public static final Block WHITE_DANDELION = new WhiteDandelionBlock(StatusEffects.SLOW_FALLING, 12, FabricBlockSettings.copy(Blocks.DANDELION).sounds(BlockSoundGroup.SPORE_BLOSSOM).strength(0.0F).nonOpaque());
    public static final Block MILKWEED = new MilkweedBlock(FabricBlockSettings.copy(Blocks.ROSE_BUSH).strength(0.0F).nonOpaque());
    public static final Block GLORY_OF_THE_SNOW = new GloryOfTheSnowBlock(FabricBlockSettings.copy(Blocks.DANDELION).sounds(BlockSoundGroup.SPORE_BLOSSOM).strength(0.0F).nonOpaque().ticksRandomly(), List.of(FlowerColors.BLUE, FlowerColors.PINK, FlowerColors.PURPLE, FlowerColors.WHITE));

    public static final Block POTTED_CARNATION = new FlowerPotBlock(RegisterBlocks.CARNATION, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_WHITE_DANDELION = new FlowerPotBlock(RegisterBlocks.WHITE_DANDELION, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());

    public static final Block BLUE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(AbstractBlock.OffsetType.NONE).mapColor(MapColor.BLUE).sounds(BlockSoundGroup.VINE));
    public static final Block PINK_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(AbstractBlock.OffsetType.NONE).mapColor(MapColor.DULL_PINK).sounds(BlockSoundGroup.VINE));
    public static final Block PURPLE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(AbstractBlock.OffsetType.NONE).mapColor(MapColor.PURPLE).sounds(BlockSoundGroup.VINE));
    public static final Block WHITE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(AbstractBlock.OffsetType.NONE).mapColor(MapColor.OFF_WHITE).sounds(BlockSoundGroup.VINE));

    public static void registerPlants() {
        registerBlock("datura", DATURA, ItemGroup.DECORATIONS);
        registerBlock("cattail", CATTAIL, ItemGroup.DECORATIONS);
        registerBlock("carnation", CARNATION, ItemGroup.DECORATIONS);
        registerBlockWithoutBlockItem("potted_carnation", POTTED_CARNATION);
        registerBlock("white_dandelion", WHITE_DANDELION, ItemGroup.DECORATIONS);
        registerBlockWithoutBlockItem("potted_white_dandelion", POTTED_WHITE_DANDELION);
        registerBlock("milkweed", MILKWEED, ItemGroup.DECORATIONS);
        registerBlock("glory_of_the_snow", GLORY_OF_THE_SNOW, ItemGroup.DECORATIONS);
        registerBlockWithoutBlockItem("blue_giant_glory_of_the_snow", BLUE_GLORY_OF_THE_SNOW);
        registerBlockWithoutBlockItem("pink_giant_glory_of_the_snow", PINK_GLORY_OF_THE_SNOW);
        registerBlockWithoutBlockItem("violet_beauty_glory_of_the_snow", PURPLE_GLORY_OF_THE_SNOW);
        registerBlockWithoutBlockItem("alba_glory_of_the_snow", WHITE_GLORY_OF_THE_SNOW);
    }

    public static final Block POLLEN_BLOCK = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(AbstractBlock.OffsetType.NONE).mapColor(MapColor.PALE_YELLOW).sounds(BlockSoundGroup.VINE));
    public static final Block BROWN_SHELF_FUNGUS = new ShelfFungusBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM_BLOCK).luminance(1).collidable(false).nonOpaque().sounds(RegisterBlockSoundGroups.MUSHROOM));
    public static final Block RED_SHELF_FUNGUS = new ShelfFungusBlock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM_BLOCK).collidable(false).nonOpaque().sounds(RegisterBlockSoundGroups.MUSHROOM));

    public static void registerNotSoPlants() {
        registerBlockWithoutBlockItem("pollen", POLLEN_BLOCK);
        registerBlock("brown_shelf_fungus", BROWN_SHELF_FUNGUS, ItemGroup.DECORATIONS);
        registerBlock("red_shelf_fungus", RED_SHELF_FUNGUS, ItemGroup.DECORATIONS);
    }

    public static final SignType BAOBAB_SIGN_TYPE = SignTypeAccessor.newSignType("baobab");
    public static final Block BAOBAB_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD, planksColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block BAOBAB_LOG = new PillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? planksColor : barkColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block STRIPPED_BAOBAB_LOG = new PillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? planksColor : barkColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block STRIPPED_BAOBAB_WOOD = new PillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? planksColor : barkColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block BAOBAB_WOOD = new PillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? planksColor : barkColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block BAOBAB_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD, planksColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block BAOBAB_STAIRS = new StairsBlock(BAOBAB_PLANKS.getDefaultState(), FabricBlockSettings.copy(BAOBAB_PLANKS));
    public static final Block BAOBAB_DOOR = new DoorBlock(FabricBlockSettings.of(Material.WOOD, planksColor).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());
    public static final Block BAOBAB_TRAPDOOR = new TrapdoorBlock(FabricBlockSettings.of(Material.WOOD, planksColor).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().allowsSpawning(RegisterBlocks::never));
    public static final Block BAOBAB_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.LEAVES, MapColor.GREEN).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(RegisterBlocks::canSpawnOnLeaves).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));
    public static final Block BAOBAB_FENCE_GATE = new FenceGateBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS.getDefaultMapColor()).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block BAOBAB_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.of(Material.WOOD, planksColor).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));
    public static final Block BAOBAB_FENCE = new FenceBlock(FabricBlockSettings.of(Material.WOOD, planksColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block BAOBAB_BUTTON = new WoodenButtonBlock(FabricBlockSettings.copy(Blocks.OAK_BUTTON).mapColor(planksColor));
    public static final Block BAOBAB_SIGN_BLOCK = new WildSignBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), BAOBAB_SIGN_TYPE);
    public static final Block BAOBAB_WALL_SIGN = new WildWallSignBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD).dropsLike(BAOBAB_SIGN_BLOCK), BAOBAB_SIGN_TYPE);
    public static final Block BAOBAB_SAPLING = new SaplingBlock(new BaobabSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.BIRCH_SAPLING));
    // Not necessarily needed, but other mods could maybe utilize this?
    public static final BlockFamily BAOBAB = BlockFamilies.register(BAOBAB_PLANKS)
            .button(BAOBAB_BUTTON)
            .slab(BAOBAB_SLAB)
            .stairs(BAOBAB_STAIRS)
            .fence(BAOBAB_FENCE)
            .fenceGate(BAOBAB_FENCE_GATE)
            .pressurePlate(BAOBAB_PRESSURE_PLATE)
            .sign(BAOBAB_SIGN_BLOCK, BAOBAB_WALL_SIGN)
            .door(BAOBAB_DOOR)
            .trapdoor(BAOBAB_TRAPDOOR)
            .group("wooden")
            .unlockCriterionName("has_planks")
            .build();

    public static void registerBaobab() {
        String name = "baobab";
        SignTypeAccessor.registerNew(BAOBAB_SIGN_TYPE);
        registerBlock(name + "_planks", BAOBAB_PLANKS, ItemGroup.BUILDING_BLOCKS);
        registerBlock(name + "_log", BAOBAB_LOG, ItemGroup.BUILDING_BLOCKS);
        registerBlock("stripped_" + name + "_log", STRIPPED_BAOBAB_LOG, ItemGroup.BUILDING_BLOCKS);
        registerBlock(name + "_wood", BAOBAB_WOOD, ItemGroup.BUILDING_BLOCKS);
        registerBlock("stripped_" + name + "_wood", STRIPPED_BAOBAB_WOOD, ItemGroup.BUILDING_BLOCKS);
        registerBlock(name + "_slab", BAOBAB_SLAB, ItemGroup.BUILDING_BLOCKS);
        registerBlock(name + "_stairs", BAOBAB_STAIRS, ItemGroup.BUILDING_BLOCKS);
        registerBlock(name + "_door", BAOBAB_DOOR, ItemGroup.REDSTONE);
        registerBlock(name + "_trapdoor", BAOBAB_TRAPDOOR, ItemGroup.REDSTONE);
        registerBlock(name + "_fence", BAOBAB_FENCE, ItemGroup.DECORATIONS);
        registerBlock(name + "_fence_gate", BAOBAB_FENCE_GATE, ItemGroup.REDSTONE);
        registerBlock(name + "_pressure_plate", BAOBAB_PRESSURE_PLATE, ItemGroup.REDSTONE);
        registerBlock(name + "_leaves", BAOBAB_LEAVES, ItemGroup.DECORATIONS);
        registerBlock(name + "_button", BAOBAB_BUTTON, ItemGroup.REDSTONE);
        registerBlockWithoutBlockItem(name + "_sign", BAOBAB_SIGN_BLOCK);
        registerBlockWithoutBlockItem(name + "_wall_sign", BAOBAB_WALL_SIGN);
        registerBlock(name + "_sapling", BAOBAB_SAPLING, ItemGroup.BUILDING_BLOCKS);
    }

    public static final SignType CYPRESS_SIGN_TYPE = SignTypeAccessor.newSignType("cypress");
    public static final Block CYPRESS_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD, cypressPlanksColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block CYPRESS_LOG = new PillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? cypressPlanksColor : cypressBarkColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block STRIPPED_CYPRESS_LOG = new PillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? cypressPlanksColor : cypressBarkColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block STRIPPED_CYPRESS_WOOD = new PillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? cypressPlanksColor : cypressBarkColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block CYPRESS_WOOD = new PillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? cypressPlanksColor : cypressBarkColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block CYPRESS_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD, cypressPlanksColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block CYPRESS_STAIRS = new StairsBlock(CYPRESS_PLANKS.getDefaultState(), FabricBlockSettings.copy(CYPRESS_PLANKS));
    public static final Block CYPRESS_DOOR = new DoorBlock(FabricBlockSettings.of(Material.WOOD, cypressPlanksColor).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());
    public static final Block CYPRESS_TRAPDOOR = new TrapdoorBlock(FabricBlockSettings.of(Material.WOOD, cypressPlanksColor).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().allowsSpawning(RegisterBlocks::never));
    public static final Block CYPRESS_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.LEAVES, MapColor.GREEN).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(RegisterBlocks::canSpawnOnLeaves).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));
    public static final Block CYPRESS_FENCE_GATE = new FenceGateBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS.getDefaultMapColor()).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block CYPRESS_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.of(Material.WOOD, cypressPlanksColor).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));
    public static final Block CYPRESS_FENCE = new FenceBlock(FabricBlockSettings.of(Material.WOOD, cypressPlanksColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block CYPRESS_BUTTON = new WoodenButtonBlock(FabricBlockSettings.copy(Blocks.OAK_BUTTON).mapColor(cypressPlanksColor));
    public static final Block CYPRESS_SIGN_BLOCK = new WildSignBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), CYPRESS_SIGN_TYPE);
    public static final Block CYPRESS_WALL_SIGN = new WildWallSignBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD).dropsLike(CYPRESS_SIGN_BLOCK), CYPRESS_SIGN_TYPE);
    public static final Block CYPRESS_SAPLING = new WaterloggableSaplingBlock(new CypressSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.BIRCH_SAPLING));
    //public static final Block CYPRESS_ROOTS = new CypressRootsBlock(FabricBlockSettings.copyOf(Blocks.OAK_BUTTON));

    public static final BlockFamily CYPRESS = BlockFamilies.register(CYPRESS_PLANKS)
            .button(CYPRESS_BUTTON)
            .slab(CYPRESS_SLAB)
            .stairs(CYPRESS_STAIRS)
            .fence(CYPRESS_FENCE)
            .fenceGate(CYPRESS_FENCE_GATE)
            .pressurePlate(CYPRESS_PRESSURE_PLATE)
            .sign(CYPRESS_SIGN_BLOCK, CYPRESS_WALL_SIGN)
            .door(CYPRESS_DOOR)
            .trapdoor(CYPRESS_TRAPDOOR)
            .group("wooden")
            .unlockCriterionName("has_planks")
            .build();

    public static void registerCypress() {
        String name = "cypress";
        SignTypeAccessor.registerNew(CYPRESS_SIGN_TYPE);
        registerBlock(name + "_planks", CYPRESS_PLANKS, ItemGroup.BUILDING_BLOCKS);
        registerBlock(name + "_log", CYPRESS_LOG, ItemGroup.BUILDING_BLOCKS);
        registerBlock("stripped_" + name + "_log", STRIPPED_CYPRESS_LOG, ItemGroup.BUILDING_BLOCKS);
        registerBlock(name + "_wood", CYPRESS_WOOD, ItemGroup.BUILDING_BLOCKS);
        registerBlock("stripped_" + name + "_wood", STRIPPED_CYPRESS_WOOD, ItemGroup.BUILDING_BLOCKS);
        registerBlock(name + "_slab", CYPRESS_SLAB, ItemGroup.BUILDING_BLOCKS);
        registerBlock(name + "_stairs", CYPRESS_STAIRS, ItemGroup.BUILDING_BLOCKS);
        registerBlock(name + "_door", CYPRESS_DOOR, ItemGroup.REDSTONE);
        registerBlock(name + "_trapdoor", CYPRESS_TRAPDOOR, ItemGroup.REDSTONE);
        registerBlock(name + "_fence", CYPRESS_FENCE, ItemGroup.DECORATIONS);
        registerBlock(name + "_fence_gate", CYPRESS_FENCE_GATE, ItemGroup.REDSTONE);
        registerBlock(name + "_pressure_plate", CYPRESS_PRESSURE_PLATE, ItemGroup.REDSTONE);
        registerBlock(name + "_leaves", CYPRESS_LEAVES, ItemGroup.DECORATIONS);
        registerBlock(name + "_button", CYPRESS_BUTTON, ItemGroup.REDSTONE);
        registerBlockWithoutBlockItem(name + "_sign", CYPRESS_SIGN_BLOCK);
        registerBlockWithoutBlockItem(name + "_wall_sign", CYPRESS_WALL_SIGN);
        registerBlock(name + "_sapling", CYPRESS_SAPLING, ItemGroup.BUILDING_BLOCKS);
        //registerBlock(name + "_roots", CYPRESS_ROOTS, ItemGroup.DECORATIONS);
    }

    public static void registerBlocks() {
        WilderWild.logWild("Registering Blocks for", WilderWild.UNSTABLE_LOGGING);
        registerBlock("chiseled_mud_bricks", CHISELED_MUD_BRICKS, ItemGroup.BUILDING_BLOCKS);

        registerHollowedLogs();
        registerDeepDark();
        registerBlock("termite_mound", TERMITE_MOUND, ItemGroup.DECORATIONS);
        registerPlants();
        registerNotSoPlants();
        registerBaobab();
        registerCypress();

        Registry.register(Registry.BLOCK, new Identifier(WilderWild.MOD_ID, "flowered_lily_pad"), FLOWERED_LILY_PAD);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "flowered_lily_pad"), new FloweredLilyPadItem(FLOWERED_LILY_PAD, new FabricItemSettings().group(ItemGroup.DECORATIONS)));

        CompostingChanceRegistry.INSTANCE.add(CARNATION, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(CATTAIL, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(DATURA, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(MILKWEED, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(RegisterItems.MILKWEED_POD, 0.25F);
        CompostingChanceRegistry.INSTANCE.add(WHITE_DANDELION, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(FLOWERED_LILY_PAD, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(BROWN_SHELF_FUNGUS, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(RED_SHELF_FUNGUS, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(CYPRESS_LEAVES, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(BAOBAB_LEAVES, 0.3F);
    }

    private static Block registerBlockWithoutBlockItem(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(WilderWild.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(WilderWild.MOD_ID, name), block);
    }

    private static BlockItem registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }

    private static HollowedLogBlock createHollowedLogBlock(MapColor topMapColor, MapColor sideMapColor) {
        return new HollowedLogBlock(FabricBlockSettings.of(Material.WOOD,
                        (state) -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
                .strength(2.0F).sounds(BlockSoundGroup.WOOD));
    }

    public static void addBaobab() {
        StrippableBlockRegistry.register(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
        StrippableBlockRegistry.register(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);

        TermiteMoundBlockEntity.Termite.addDegradable(BAOBAB_LOG, HOLLOWED_BAOBAB_LOG);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_BAOBAB_LOG, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addDegradable(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_BAOBAB_WOOD, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);

        StrippableBlockRegistry.register(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
        StrippableBlockRegistry.register(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);

        TermiteMoundBlockEntity.Termite.addDegradable(CYPRESS_LOG, HOLLOWED_CYPRESS_LOG);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_CYPRESS_LOG, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addDegradable(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_CYPRESS_WOOD, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);
    }

    protected static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    private static boolean never(BlockState state, BlockView blockView, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }

    protected static Boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }
}
