package net.frozenblock.wilderwild.misc;

import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.*;
import static net.frozenblock.wilderwild.registry.RegisterBlocks.BAOBAB_LEAVES;
import static net.frozenblock.wilderwild.registry.RegisterBlocks.CYPRESS_LEAVES;
import static net.minecraft.block.Blocks.*;


public class BlockSoundGroupOverwrites {

    public static void init() {
        addBlock(WITHER_ROSE, BlockSoundGroup.SWEET_BERRY_BUSH);
        addBlock(DEAD_BUSH, BlockSoundGroup.NETHER_SPROUTS);
        addBlock(CACTUS, BlockSoundGroup.SWEET_BERRY_BUSH);
        addBlock(PODZOL, BlockSoundGroup.ROOTED_DIRT);

        addBlocks(new Block[]{ACACIA_LEAVES, BIRCH_LEAVES, DARK_OAK_LEAVES, JUNGLE_LEAVES, MANGROVE_LEAVES, OAK_LEAVES, SPRUCE_LEAVES, BAOBAB_LEAVES, CYPRESS_LEAVES}, LEAVES);
        addBlocks(new Block[]{DANDELION, POPPY, BLUE_ORCHID, ALLIUM, AZURE_BLUET, RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP, OXEYE_DAISY, CORNFLOWER, LILY_OF_THE_VALLEY}, FLOWER);
        addBlocks(new Block[]{RED_MUSHROOM, BROWN_MUSHROOM}, MUSHROOM);
        addBlocks(new Block[]{RED_MUSHROOM_BLOCK, BROWN_MUSHROOM_BLOCK, MUSHROOM_STEM}, MUSHROOM_BLOCK);
        addBlocks(new Block[]{FROSTED_ICE}, ICE_BLOCKS);
        addBlock(COBWEB, WEB);
        addBlock(REINFORCED_DEEPSLATE, REINFORCEDDEEPSLATE);
        addBlock(LILY_PAD, LILYPAD);
        addBlock(SUGAR_CANE, SUGARCANE);
        addBlock(COARSE_DIRT, COARSEDIRT);

    }

    /**
     * You can add any block by either adding its registry (Blocks.STONE) or its ID ("stone").
     * If you want to add a modded block, make sure to put the nameSpaceID (wilderwild) in the first field, then the ID and soundGroup.
     * Or you could just be normal and add the block itself instead of the ID.
     * You can also add a LIST of blocks (IDs not allowed,) by using new Block[]{block1, block2}.
     */

    public static void addBlock(String id, BlockSoundGroup sounds) {
        ids.add(new Identifier(id));
        soundGroups.add(sounds);
    }

    public static void addBlock(String nameSpace, String id, BlockSoundGroup sounds) {
        ids.add(new Identifier(nameSpace, id));
        soundGroups.add(sounds);
    }

    public static void addBlock(Block block, BlockSoundGroup sounds) {
        ids.add(Registry.BLOCK.getId(block));
        soundGroups.add(sounds);
    }

    public static void addBlocks(Block[] blocks, BlockSoundGroup sounds) {
        for (Block block : blocks) {
            ids.add(Registry.BLOCK.getId(block));
            soundGroups.add(sounds);
        }
    }

    public static void addNamespace(String nameSpace, BlockSoundGroup sounds) {
        namespaces.add(nameSpace);
        namespaceSoundGroups.add(sounds);
    }

    public static List<Identifier> ids = new ArrayList<>();
    public static List<BlockSoundGroup> soundGroups = new ArrayList<>();
    public static List<String> namespaces = new ArrayList<>();
    public static List<BlockSoundGroup> namespaceSoundGroups = new ArrayList<>();
}
