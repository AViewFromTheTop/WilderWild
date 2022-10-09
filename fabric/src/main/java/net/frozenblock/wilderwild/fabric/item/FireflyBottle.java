package net.frozenblock.wilderwild.fabric.item;

import net.frozenblock.wilderwild.common.WilderWild;
import net.frozenblock.wilderwild.fabric.WilderWildFabric;
import net.frozenblock.wilderwild.fabric.entity.Firefly;
import net.frozenblock.wilderwild.fabric.entity.ai.FireflyAi;
import net.frozenblock.wilderwild.fabric.registry.RegisterEntities;
import net.frozenblock.wilderwild.fabric.registry.RegisterSounds;
import net.frozenblock.wilderwild.fabric.misc.FireflyColor;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class FireflyBottle extends Item {

    public final FireflyColor color;

    public FireflyBottle(Properties settings, FireflyColor color) {
        super(settings);
        this.color = color;
    }

    @Override
    public ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity user) {
        Player playerEntity = user instanceof Player ? (Player) user : null;
        if (playerEntity instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) playerEntity, stack);
        }

        if (playerEntity != null) {
            playerEntity.awardStat(Stats.ITEM_USED.get(this));
            if (!playerEntity.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        if (playerEntity == null || !playerEntity.getAbilities().instabuild) {
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (playerEntity != null) {
                playerEntity.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        user.gameEvent(GameEvent.ENTITY_PLACE);
        return stack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
        WilderWildFabric.log(player, "Used Firefly Bottle", WilderWild.DEV_LOGGING);
        if (level instanceof ServerLevel server) {
            float pitch = player.getXRot();
            float yaw = player.getYRot();
            float roll = 0.0F;
            float f = -Mth.sin(yaw * 0.017453292F) * Mth.cos(pitch * 0.017453292F);
            float g = -Mth.sin((pitch + roll) * 0.017453292F);
            float h = Mth.cos(yaw * 0.017453292F) * Mth.cos(pitch * 0.017453292F);
            ItemStack stack = player.getItemInHand(usedHand);
            if (player.getAbilities().mayBuild) {
                Firefly entity = RegisterEntities.FIREFLY.create(server);
                if (entity != null) {
                    entity.setDeltaMovement(f * 0.7, g * 0.7, h * 0.7);
                    entity.moveTo(player.getX(), player.getEyeY(), player.getZ(), player.getXRot(), player.getYRot());
                    entity.setFromBottle(true);
                    boolean spawned = server.addFreshEntity(entity);
                    if (spawned) {
                        entity.playSound(RegisterSounds.ITEM_BOTTLE_RELEASE_FIREFLY, 1.0F, level.random.nextFloat() * 0.2f + 0.9f);
                        entity.hasHome = true;
                        FireflyAi.rememberHome(entity, entity.blockPosition());
                        entity.setColor(this.color);
                        if (stack.hasCustomHoverName()) {
                            entity.setCustomName(stack.getHoverName());
                        }
                    } else {
                        WilderWildFabric.log("Couldn't spawn Firefly from bottle @ " + player.blockPosition().toShortString(), WilderWild.UNSTABLE_LOGGING);
                    }
                }
            }
        }
        player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        return ItemUtils.startUsingInstantly(level, player, usedHand);
    }

    @Override
    public UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.NONE; //sus funny funny funny among us sus funny all
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 1;
    }
}
