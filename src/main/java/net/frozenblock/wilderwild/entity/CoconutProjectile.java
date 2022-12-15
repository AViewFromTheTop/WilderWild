package net.frozenblock.wilderwild.entity;

import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.tag.WilderEntityTags;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class CoconutProjectile extends ThrowableItemProjectile {

	public CoconutProjectile(EntityType<? extends CoconutProjectile> entityType, Level level) {
		super(entityType, level);
	}

	public CoconutProjectile(Level level, LivingEntity shooter) {
		super(RegisterEntities.COCONUT, shooter, level);
	}

	public CoconutProjectile(Level level, double x, double y, double z) {
		super(RegisterEntities.COCONUT, x, y, z, level);
	}

	@Override
	@NotNull
	protected Item getDefaultItem() {
		return RegisterItems.COCONUT;
	}


	@Override
	public void handleEntityEvent(byte id) {
		if (id == 3) {
			for (int i = 0; i < 8; ++i) {
				this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(this.getDefaultItem())), this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
			}
		}
	}

	@Override
	protected void onHit(@NotNull HitResult result) {
		super.onHit(result);
		this.level.playSound(null, this.getX(), this.getY(), this.getZ(), RegisterSounds.ITEM_COCONUT_LAND, SoundSource.BLOCKS, 0.4F, 0.9F + (this.level.random.nextFloat() * 0.2F));
	}

	@Override
	protected void onHitEntity(@NotNull EntityHitResult result) {
		super.onHitEntity(result);
		Entity entity = result.getEntity();
		entity.hurt(DamageSource.thrown(this, this.getOwner()), 2F);
		if (this.position().y() > entity.getEyeY() && !entity.getType().is(WilderEntityTags.COCONUT_CANT_BONK)) {
			this.level.playSound(null, this.getX(), this.getY(), this.getZ(), RegisterSounds.ITEM_COCONUT_HIT_HEAD, SoundSource.BLOCKS, 1.0F, 0.9F + (this.level.random.nextFloat() * 0.2F));
		}
		if (!entity.getType().is(WilderEntityTags.COCONUT_CANT_SPLIT) && entity.getBoundingBox().getSize() > this.getBoundingBox().getSize() && this.random.nextDouble() < 0.7) {
			this.level.broadcastEntityEvent(this, (byte)3);
			this.level.playSound(null, this.getX(), this.getY(), this.getZ(), RegisterSounds.ITEM_COCONUT_LAND_BREAK, SoundSource.BLOCKS, 1.0F, 0.9F + 0.2F * this.level.random.nextFloat());
			this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(RegisterItems.SPLIT_COCONUT)));
			this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(RegisterItems.SPLIT_COCONUT)));
			this.discard();
		}
	}

	@Override
	protected void onHitBlock(@NotNull BlockHitResult result) {
		super.onHitBlock(result);
		Block block = this.level.getBlockState(result.getBlockPos()).getBlock();
		if (block.getExplosionResistance() >= 3.0F && block.defaultDestroyTime() >= 1.0F) {
			this.level.broadcastEntityEvent(this, (byte)3);
			this.level.playSound(null, this.getX(), this.getY(), this.getZ(), RegisterSounds.ITEM_COCONUT_LAND_BREAK, SoundSource.BLOCKS, 1.0F, 0.9F + 0.2F * this.level.random.nextFloat());
			this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(RegisterItems.SPLIT_COCONUT)));
			this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(RegisterItems.SPLIT_COCONUT)));
			this.discard();
			return;
		}
		this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), this.getItem()));
		this.discard();
	}

}
