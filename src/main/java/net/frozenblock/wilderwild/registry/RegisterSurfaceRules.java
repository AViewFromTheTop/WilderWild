package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.FrozenBools;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.world.generation.WilderSharedWorldgen;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.frozenblock.worldgen.surface_rule.api.SurfaceRuleContext;
import org.quiltmc.qsl.frozenblock.worldgen.surface_rule.api.SurfaceRuleEvents;

public final class RegisterSurfaceRules implements SurfaceRuleEvents.OverworldModifierCallback {

	@Override
	public void modifyOverworldRules(SurfaceRuleContext.@NotNull Overworld context) {
		if (!FrozenBools.HAS_TERRABLENDER) {
			context.ruleSources().add(0, WilderSharedWorldgen.surfaceRules());
			context.ruleSources().add(WilderSharedWorldgen.surfaceRules());
			WilderSharedConstants.log("Wilder Wild's Overworld Surface Rules have been added!", WilderSharedConstants.UNSTABLE_LOGGING);
		}
	}

	// SPONGEBOB
    /*@Override
    public void modifyNetherRules(SurfaceRuleContext.@NotNull Nether context) {
        /*context.materialRules().clear();
        context.materialRules().add(0, FrozenSurfaceRules.makeStateRule(Blocks.SPONGE));
        WilderSharedConstants.log("SPONGEBOB", WilderSharedConstants.UNSTABLE_LOGGING);
    }*/
}
