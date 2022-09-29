package net.frozenblock.wilderwild.world.structure;

import com.google.common.collect.ImmutableList;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

public class WilderStructureProcessors {

    public static final Holder<StructureProcessorList> ABANDONED_CABIN_PROCESSOR = registerProcessorList(
            "abandoned_cabin",
            ImmutableList.of(
                    new RuleProcessor(
                            ImmutableList.of(
                                    new ProcessorRule(
                                            new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICKS, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState()
                                    ),
                                    new ProcessorRule(
                                            new RandomBlockMatchTest(Blocks.DEEPSLATE_TILES, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.defaultBlockState()
                                    ),
                                    new ProcessorRule(new RandomBlockMatchTest(Blocks.SOUL_LANTERN, 0.05F), AlwaysTrueTest.INSTANCE, Blocks.AIR.defaultBlockState())
                            )
                    ),
                    new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
            )
    );

    private static Holder<StructureProcessorList> registerProcessorList(String id, ImmutableList<StructureProcessor> processorList) {
        ResourceLocation identifier = WilderWild.id(id);
        StructureProcessorList structureProcessorList = new StructureProcessorList(processorList);
        return BuiltinRegistries.register(BuiltinRegistries.PROCESSOR_LIST, identifier, structureProcessorList);
    }

    public static void init() {
    }
}
