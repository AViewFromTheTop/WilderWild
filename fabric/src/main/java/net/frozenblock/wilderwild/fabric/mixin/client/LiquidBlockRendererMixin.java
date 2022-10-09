package net.frozenblock.wilderwild.fabric.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.fabric.WilderWildFabric;
import net.frozenblock.wilderwild.fabric.block.MesogleaBlock;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(value = LiquidBlockRenderer.class, priority = 69420)
public class LiquidBlockRendererMixin {

    @Inject(method = "shouldRenderFace", at = @At(value = "HEAD"), cancellable = true)
    private static void shouldRenderFace(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, FluidState fluidState, BlockState blockState, Direction direction, FluidState fluidState2, CallbackInfoReturnable<Boolean> info) {
        if (blockState.getBlock() instanceof MesogleaBlock && direction != Direction.UP && !WilderWildFabric.hasSodium) {
            info.setReturnValue(false);
        }
    }

}
