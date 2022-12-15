package net.frozenblock.wilderwild.world.generation.sapling;

import net.frozenblock.wilderwild.world.additions.feature.WilderConfiguredFeatures;
import net.frozenblock.wilderwild.world.additions.feature.WilderTreeConfigured;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class CypressSaplingGenerator extends AbstractTreeGrower {

    public CypressSaplingGenerator() {
    }

    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean bees) {
        if (random.nextFloat() > 0.4F) {
            return random.nextFloat() > 0.7F ? WilderTreeConfigured.CYPRESS : WilderTreeConfigured.FUNGUS_CYPRESS;
        }
        return WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES_SAPLING;
    }

}
