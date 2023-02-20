package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.world.feature.WilderTreeConfigured;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.MangroveTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MangroveTreeGrower.class, priority = 69420)
public class MangroveTreeGrowerMixin {

    @Inject(method = "getConfiguredFeature", at = @At("RETURN"), cancellable = true)
    public void getConfiguredFeature(RandomSource randomSource, boolean bl, CallbackInfoReturnable<Holder<? extends ConfiguredFeature<?, ?>>> cir) {
        if (randomSource.nextFloat() < 0.1F) {
            cir.setReturnValue(WilderTreeConfigured.SWAMP_TREE);
        }
    }

}
