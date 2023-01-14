package net.frozenblock.wilderwild.mixin.client.general;

import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.misc.client.WilderDripSuspendedParticleInterface;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SuspendedParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Particle.class)
public abstract class ParticleMixin {

	@Shadow @Final
	protected ClientLevel level;
	@Shadow
	protected double xd;
	@Shadow
	protected double yd;
	@Shadow
	protected double zd;
	@Shadow
	protected double x;
	@Shadow
	protected double y;
	@Shadow
	protected double z;

	@Inject(method = "tick", at = @At("TAIL"))
	public void tick(CallbackInfo info) {
		if (Particle.class.cast(this) instanceof DripParticle dripParticle) {
			if (((WilderDripSuspendedParticleInterface)dripParticle).usesWind()) {
				Vec3 wind = ClientWindManager.getWindMovement(this.level, new BlockPos(this.x, this.y, this.z), 1.5, 1);
				this.xd += wind.x * 0.0005;
				this.yd += wind.y * 0.00000025;
				this.zd += wind.z * 0.0005;
			}
		}
		if (Particle.class.cast(this) instanceof SuspendedParticle suspendedParticle) {
			if (((WilderDripSuspendedParticleInterface)suspendedParticle).usesWind()) {
				Vec3 wind = ClientWindManager.getWindMovement(this.level, new BlockPos(this.x, this.y, this.z), 1.5, 1);
				this.xd += wind.x * 0.0005;
				this.yd += wind.y * 0.00000025;
				this.zd += wind.z * 0.0005;
			}
		}
	}
}