package net.frozenblock.wilderwild.fabric.mixin.server;

import net.frozenblock.wilderwild.fabric.registry.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SculkVeinBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkVeinBlock.SculkVeinSpreaderConfig.class)
public class SculkVeinSpreaderConfigMixin {

    @Inject(at = @At("RETURN"), method = "stateCanBeReplaced", cancellable = true)
    public void newBlocks(BlockGetter level, BlockPos pos, BlockPos growPos, Direction direction, BlockState state, CallbackInfoReturnable<Boolean> info) {
        BlockState blockState = level.getBlockState(growPos.relative(direction));
        if (blockState.is(RegisterBlocks.OSSEOUS_SCULK) || blockState.is(RegisterBlocks.SCULK_SLAB) || blockState.is(RegisterBlocks.SCULK_STAIRS) || blockState.is(RegisterBlocks.SCULK_WALL)) {
            info.setReturnValue(false);
            info.cancel();
        }
    }

}
