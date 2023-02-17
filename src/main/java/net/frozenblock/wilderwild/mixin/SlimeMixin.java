package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.init.WWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@Mixin(Slime.class)
public abstract class SlimeMixin extends Mob {

    private SlimeMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "checkSlimeSpawnRules", at = @At("HEAD"), cancellable = true)
    private static void spawnInAlgae(EntityType<Slime> type, LevelAccessor level, MobSpawnType spawnReason, BlockPos pos, RandomSource random, CallbackInfoReturnable<Boolean> info) {
        if (level.getDifficulty() != Difficulty.PEACEFUL) {
            if (level.getBrightness(LightLayer.BLOCK, pos) < random.nextInt(8)) {
                boolean test = spawnReason == MobSpawnType.SPAWNER || random.nextInt(5) == 0;
                if (test && isAlgaeNearby(level, pos, 1)) {
                    info.setReturnValue(true);
                    info.cancel();
                }
            }
        }
    }

	@Unique
    private static boolean isAlgaeNearby(LevelAccessor level, BlockPos blockPos, int x) {
        Iterator<BlockPos> iterator = BlockPos.betweenClosed(blockPos.offset(-x, -x, -x), blockPos.offset(x, x, x)).iterator();
        int count = 0;
        BlockPos pos;
        do {
            if (!iterator.hasNext()) {
                return false;
            }
            pos = iterator.next();
            if (level.getBlockState(pos).is(WWBlocks.ALGAE.get())) {
                count = count + 1;
            }
        } while (count < 3);
        return true;
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader level) {
        return (!level.containsAnyLiquid(this.getBoundingBox()) || isAlgaeNearby(this.getLevel(), this.blockPosition(), 1)) && level.isUnobstructed(this);
    }

}
