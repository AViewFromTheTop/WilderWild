package net.frozenblock.wilderwild.world.generation.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class HeightBasedVineTrunkDecorator extends TreeDecorator {
    public static final Codec<HeightBasedVineTrunkDecorator> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((treeDecorator) -> {
            return treeDecorator.probability;
        }), Codec.intRange(-63, 319).fieldOf("maxHeight").forGetter((treeDecorator) -> {
            return treeDecorator.maxHeight;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("vines_count").forGetter((treeDecorator) -> {
            return treeDecorator.vines_count;
        })).apply(instance, HeightBasedVineTrunkDecorator::new);
    });
    private final float probability;
    private final int maxHeight;
    private final float vines_count;

    public HeightBasedVineTrunkDecorator(float probability, int maxHeight, float vines_count) {
        this.probability = probability;
        this.maxHeight = maxHeight;
        this.vines_count = vines_count;
    }

    protected TreeDecoratorType<?> type() {
        return WilderTreeDecorators.HEIGHT_BASED_VINE_TRUNK_DECORATOR;
    }

    public void place(Context generator) {
        RandomSource abstractRandom = generator.random();
        if (abstractRandom.nextFloat() <= this.probability) {
            List<BlockPos> list = generator.logs();
            list.forEach((pos) -> {
                if (pos.getY() <= this.maxHeight) {
                    for (Direction direction : Direction.Plane.HORIZONTAL) {
                        if (abstractRandom.nextFloat() <= this.vines_count) {
                            BlockPos blockPos = pos.offset(direction.getStepX(), 0, direction.getStepZ());
                            if (generator.isAir(blockPos)) {
                                BooleanProperty dir = direction == Direction.NORTH ? VineBlock.SOUTH : direction == Direction.SOUTH ? VineBlock.NORTH : direction == Direction.WEST ? VineBlock.EAST : VineBlock.WEST;
                                generator.setBlock(blockPos, Blocks.VINE.defaultBlockState().setValue(dir, true));
                            }
                        }
                    }
                }
            });
        }
    }

}
