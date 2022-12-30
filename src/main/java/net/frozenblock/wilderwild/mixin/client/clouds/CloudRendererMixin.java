package net.frozenblock.wilderwild.mixin.client.clouds;

import com.mojang.blaze3d.vertex.PoseStack;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = LevelRenderer.class, priority = 10000)
public class CloudRendererMixin {

	@Unique
	private float wilderWild$cloudHeight;

	@ModifyVariable(method = "renderClouds", at = @At(value = "STORE"), ordinal = 1)
	private float getCloudHeight(float original) {
		this.wilderWild$cloudHeight = original;
		return original;
	}

	@ModifyVariable(method = "renderClouds", at = @At(value = "STORE"), ordinal = 5)
	private double modifyX(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX) {
		return ClientWindManager.shouldUseWind() && ClothConfigInteractionHandler.cloudMovement()
				? (camX / 12.0) - ClientWindManager.getCloudX(partialTick)
				: original;
	}

	@ModifyVariable(method = "renderClouds", at = @At("STORE"), ordinal = 6)
	private double modifyY(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX, double camY) {
		return ClientWindManager.shouldUseWind() && ClothConfigInteractionHandler.cloudMovement()
				? this.wilderWild$cloudHeight - camY + 0.33D + Mth.clamp(ClientWindManager.getCloudY(partialTick), -10, 10)
				: original;
	}

	@ModifyVariable(method = "renderClouds", at = @At("STORE"), ordinal = 7)
	private double modifyZ(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX, double camY, double camZ) {
		return ClientWindManager.shouldUseWind() && ClothConfigInteractionHandler.cloudMovement()
				? (camZ / 12.0D + 0.33000001311302185D) - ClientWindManager.getCloudZ(partialTick)
				: original;
	}
}
