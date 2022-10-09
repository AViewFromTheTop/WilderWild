package net.frozenblock.wilderwild.fabric.mixin.server;

import net.frozenblock.wilderwild.fabric.entity.Jellyfish;
import net.frozenblock.wilderwild.fabric.entity.ai.TurtleNearestAttackableGoal;
import net.frozenblock.wilderwild.fabric.misc.TurtleCooldownInterface;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.animal.Turtle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Turtle.class)
public class TurtleMixin implements TurtleCooldownInterface {

    private int attackCooldown;

    @Inject(method = "registerGoals", at = @At("TAIL"))
    public void registerGoals(CallbackInfo info) {
        Turtle turtle = Turtle.class.cast(this);
        turtle.goalSelector.addGoal(3, new MeleeAttackGoal(turtle, 1.0, true));
        turtle.targetSelector.addGoal(10, new TurtleNearestAttackableGoal<>(turtle, Jellyfish.class, false));
    }

    @Inject(method = "createAttributes", at = @At("RETURN"), cancellable = true)
    private static void createAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> ci) {
        AttributeSupplier.Builder builder = ci.getReturnValue();
        builder.add(Attributes.ATTACK_DAMAGE, 3.0);
        ci.setReturnValue(builder);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo info) {
        compoundTag.putInt("AttackCooldown", this.attackCooldown);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo info) {
        this.attackCooldown = compoundTag.getInt("AttackCooldown");
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    public void aiStep(CallbackInfo info) {
        if (this.attackCooldown > 0) {
            this.attackCooldown = this.attackCooldown - 1;
        }
    }

    @Override
    public int getAttackCooldown() {
        return this.attackCooldown;
    }

    @Override
    public void setAttackCooldown(int i) {
        this.attackCooldown = i;
    }
}
