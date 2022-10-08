package net.frozenblock.wilderwild.item;

import net.frozenblock.lib.interfaces.CooldownInterface;
import net.frozenblock.lib.sound.FrozenSoundPackets;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Optional;

public class AncientHorn extends InstrumentItem {
    private static final String TAG_INSTRUMENT = "instrument";
    private final TagKey<Instrument> instrumentTag;

    public static final int SHRIEKER_COOLDOWN = 900;
    public static final int SENSOR_COOLDOWN = 400;
    public static final int TENDRIL_COOLDOWN = 380;

    public AncientHorn(Properties settings, TagKey<Instrument> instrumentTag) {
        super(settings, instrumentTag);
        this.instrumentTag = instrumentTag;
    }

    @Override
    public void fillItemCategory(@NotNull CreativeModeTab creativeModeTab, @NotNull NonNullList<ItemStack> nonNullList) {
        if (this.allowedIn(creativeModeTab)) {
            for (Holder<Instrument> holder : Registry.INSTRUMENT.getTagOrEmpty(this.instrumentTag)) {
                nonNullList.add(create(RegisterItems.ANCIENT_HORN, holder));
            }
        }

    }

    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player user, @NotNull InteractionHand hand) {
        WilderWild.log(user, "Used Ancient Horn", WilderWild.DEV_LOGGING);
        ItemStack itemStack = user.getItemInHand(hand);
        Optional<Holder<Instrument>> optional = this.getInstrument(itemStack);
        if (optional.isPresent()) {
            Instrument instrument = optional.get().value();
            user.startUsingItem(hand);
            play(level, user, instrument);
            user.getCooldowns().addCooldown(RegisterItems.ANCIENT_HORN, getCooldown(user, 300));
            if (level instanceof ServerLevel server) {
                AncientHornProjectile projectileEntity = new AncientHornProjectile(level, user.getX(), user.getEyeY(), user.getZ());
                projectileEntity.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.0F, 0.0F);
                projectileEntity.shotByPlayer = true;
                server.addFreshEntity(projectileEntity);
                FrozenSoundPackets.createMovingRestrictionLoopingSound(server, projectileEntity, RegisterSounds.ENTITY_ANCIENT_HORN_PROJECTILE_LOOP, SoundSource.NEUTRAL, 1.0F, 1.0F, WilderWild.id("default"));
                ItemStack mainHand = user.getItemInHand(InteractionHand.MAIN_HAND);
                ItemStack offHand = user.getItemInHand(InteractionHand.OFF_HAND);
                if (mainHand.is(Items.WATER_BUCKET) || mainHand.is(Items.POTION) || offHand.is(Items.WATER_BUCKET) || offHand.is(Items.POTION)) {
                    projectileEntity.bubbles = level.random.nextIntBetweenInclusive(10, 25);
                    /*float yawNew = user.getYaw() * 0.017453292F;
                    float pitchNew = MathHelper.cos(user.getPitch() * 0.017453292F);
                    float f = -MathHelper.sin(yawNew) * pitchNew;
                    float h = MathHelper.cos(yawNew) * pitchNew;
                    for (int bubble=0; bubble < Math.random()*10; bubble++) {
                        EasyPacket.EasyFloatingSculkBubblePacket.createParticle(server, user.getEyePos(), Math.random() > 0.7 ? 1 : 0, 20 + (int)(Math.random()*40), 0.05, 1);
                    }*/
                }
                //FlyBySoundPacket.createFlybySound(level, projectileEntity, RegisterSounds.ANCIENT_HORN_VIBRATION_DISSIPATE, SoundCategory.PLAYERS, 1.0F, 0.7F);
            }
            return InteractionResultHolder.consume(itemStack);
        } else {
            WilderWild.LOGGER.error("Ancient Horn use failed");
            return InteractionResultHolder.fail(itemStack);
        }
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        Optional<Holder<Instrument>> optional = this.getInstrument(stack);
        return optional.map(instrumentRegistryEntry -> instrumentRegistryEntry.value().useDuration()).orElse(0);
    }

    @Override
    public Optional<Holder<Instrument>> getInstrument(ItemStack stack) {
        CompoundTag nbtCompound = stack.getTag();
        if (nbtCompound != null) {
            ResourceLocation resourceLocation = ResourceLocation.tryParse(nbtCompound.getString(TAG_INSTRUMENT));
            if (resourceLocation != null) {
                return Registry.INSTRUMENT.getHolder(ResourceKey.create(Registry.INSTRUMENT_REGISTRY, resourceLocation));
            }
        }

        Iterator<Holder<Instrument>> iterator = Registry.INSTRUMENT.getTagOrEmpty(this.instrumentTag).iterator();
        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
    }

    public static int getCooldown(@Nullable Entity entity, int cooldown) {
        if (entity != null) {
            if (entity instanceof Player player) {
                cooldown = player.isCreative() ? 5 : cooldown;
            }
        }
        return cooldown;
    }

    public static int decreaseCooldown(Player user, int time) {
        if (!user.isCreative()) {
            ItemCooldowns manager = user.getCooldowns();
            ItemCooldowns.CooldownInstance entry = manager.cooldowns.get(RegisterItems.ANCIENT_HORN);
            if (entry != null) {
                int between = entry.endTime - entry.startTime;
                if (between > 140 & between >= time) {
                    ((CooldownInterface) user.getCooldowns()).changeCooldown(RegisterItems.ANCIENT_HORN, -time);
                    return time;
                }
            }
        }
        return -1;
    }

    @Override
    public UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.TOOT_HORN;
    }

    private static void play(Level level, Player player, Instrument instrument) {
        SoundEvent soundEvent = instrument.soundEvent();
        float range = instrument.range() / 16.0F;
        level.playSound(player, player, soundEvent, SoundSource.RECORDS, range, 1.0F);
    }

}
