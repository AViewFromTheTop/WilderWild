package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.item.AncientHornItem;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExperienceOrb.class)
public class ExperienceOrbMixin {

    @Inject(at = @At("RETURN"), method = "repairPlayerItems", cancellable = true)
    private void repairAncientHorn(Player player, int amount, CallbackInfoReturnable<Integer> info) {
        int hornCooldown = AncientHornItem.decreaseCooldown(player, amount * 8);
        if (hornCooldown != -1) {
            info.setReturnValue(0);
        }
    }

}
