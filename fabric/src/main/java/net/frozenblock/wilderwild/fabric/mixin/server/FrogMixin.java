package net.frozenblock.wilderwild.fabric.mixin.server;

import net.frozenblock.wilderwild.fabric.registry.RegisterSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.frog.Frog;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Frog.class)
public class FrogMixin {
    @Inject(at = @At("RETURN"), method = "getDeathSound", cancellable = true)
    public void newDeath(CallbackInfoReturnable<SoundEvent> cir) {
        String string = ChatFormatting.stripFormatting(Frog.class.cast(this).getName().getString());
        if (Objects.equals(string, "Xfrtrex")) {
            cir.setReturnValue(RegisterSounds.ENTITY_FROG_SUS_DEATH);
        }
    }
}
