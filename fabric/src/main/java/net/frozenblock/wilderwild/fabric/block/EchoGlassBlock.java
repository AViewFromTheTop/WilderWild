package net.frozenblock.wilderwild.fabric.block;

import net.frozenblock.wilderwild.common.WilderWild;
import net.frozenblock.wilderwild.fabric.WilderWildFabric;
import net.frozenblock.wilderwild.fabric.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.fabric.registry.RegisterProperties;
import net.frozenblock.wilderwild.fabric.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.TintedGlassBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class EchoGlassBlock extends TintedGlassBlock {
    public static final IntegerProperty DAMAGE = RegisterProperties.DAMAGE;

    public EchoGlassBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(DAMAGE, 0));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DAMAGE);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int light = getLightLevel(level, pos);
        if (light <= 7) {
            if (random.nextBoolean()) {
                heal(level, pos);
            }
        } else {
            damage(level, pos);
        }
    }

    public static void damage(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        WilderWildFabric.log("Echo Glass Damaged @ " + pos, WilderWild.UNSTABLE_LOGGING);
        if (state.getValue(DAMAGE) < 3) {
            level.setBlockAndUpdate(pos, state.setValue(DAMAGE, state.getValue(DAMAGE) + 1));
            level.playSound(null, pos, RegisterSounds.BLOCK_ECHO_GLASS_CRACK, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(null, LevelEvent.PARTICLES_DESTROY_BLOCK, pos, getId(state));
        } else {
            level.destroyBlock(pos, false);
        }
    }

    public static void heal(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.getValue(DAMAGE) > 0) {
            WilderWildFabric.log("Echo Glass Healed @ " + pos, WilderWild.UNSTABLE_LOGGING);
            level.setBlockAndUpdate(pos, state.setValue(DAMAGE, state.getValue(DAMAGE) - 1));
            level.playSound(
                    null,
                    pos,
                    RegisterSounds.BLOCK_ECHO_GLASS_REPAIR,
                    SoundSource.BLOCKS,
                    1.0F,
                    level.random.nextFloat() * 0.1F + 0.9F
            );
        }
    }

    public static int getLightLevel(Level level, BlockPos blockPos) {
        int finalLight = 0;
        for (Direction direction : UPDATE_SHAPE_ORDER) {
            BlockPos pos = blockPos.relative(direction);
            int skyLight = 0;
            int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
            if (level.isDay() && !level.isRaining()) {
                skyLight = level.getBrightness(LightLayer.SKY, pos);
            }
            finalLight = Math.max(finalLight, Math.max(skyLight, blockLight));
        }
        return finalLight;
    }

    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        player.causeFoodExhaustion(0.005F);
        if (state.getValue(DAMAGE) < 3 && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, player.getMainHandItem()) < 1 && !player.isCreative()) {
            level.setBlockAndUpdate(pos, state.setValue(DAMAGE, state.getValue(DAMAGE) + 1));
        } else {
            player.awardStat(Stats.BLOCK_MINED.get(this));
            dropResources(state, level, pos, blockEntity, player, stack);
            level.playSound(
                    null,
                    pos,
                    SoundEvents.GLASS_BREAK,
                    SoundSource.BLOCKS,
                    1.3F,
                    level.random.nextFloat() * 0.1F + 0.8F
            );
        }
    }

    @Deprecated
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        ResourceLocation identifier = this.getLootTable();
        if (builder.getOptionalParameter(LootContextParams.TOOL) != null) {
            ItemStack stack = builder.getParameter(LootContextParams.TOOL);
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) != 0) {
                if (state.getValue(DAMAGE) == 0) {
                    identifier = WilderWildFabric.id("blocks/echo_glass_full");
                }
            }
        }
        if (identifier == BuiltInLootTables.EMPTY) {
            return Collections.emptyList();
        } else {
            LootContext lootContext = builder.withParameter(LootContextParams.BLOCK_STATE, state).create(LootContextParamSets.BLOCK);
            ServerLevel serverLevel = lootContext.getLevel();
            LootTable lootTable = serverLevel.getServer().getLootTables().get(identifier);
            return lootTable.getRandomItems(lootContext);
        }
    }

    @Override
    public void onProjectileHit(@NotNull Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (projectile instanceof AncientHornProjectile) {
            damage(level, hit.getBlockPos());
        }
        super.onProjectileHit(level, state, hit, projectile);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }
}
