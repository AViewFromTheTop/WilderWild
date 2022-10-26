package net.frozenblock.wilderwild.mixin.server.reinforced_deepslate_pillar;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public final class BlocksMixin {

    @Redirect(method = "<clinit>", at = @At(value = "NEW", target = "net/minecraft/world/level/block/Block", ordinal = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=reinforced_deepslate")))
    private static Block newReinforced(BlockBehaviour.Properties properties) {
        return new RotatedPillarBlock(properties);
    }

}
