package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.init.WWBlockStateProperties;
import net.frozenblock.wilderwild.init.WWBlockTags;
import net.frozenblock.wilderwild.init.WWBlocks;
import net.frozenblock.wilderwild.util.BooleanPropertySculkBehavior;
import net.frozenblock.wilderwild.util.SlabWallStairSculkBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.SculkVeinBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;

@Mixin(SculkSpreader.ChargeCursor.class)
public class SculkSpreaderChargeCursorMixin {

    //EDITS
    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/SculkSpreader$ChargeCursor;getBlockBehaviour(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/SculkBehaviour;"))
    private SculkBehaviour newSculkBehaviour(BlockState par1, LevelAccessor level, BlockPos pos, RandomSource random, SculkSpreader spreader, boolean spread) {
        return getBlockBehaviourNew(par1, spreader.isWorldGeneration());
    }

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/SculkSpreader$ChargeCursor;getValidMovementPos(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)Lnet/minecraft/core/BlockPos;"))
    private BlockPos newValidMovementPos(LevelAccessor par1, BlockPos par2, RandomSource par3, LevelAccessor level, BlockPos pos, RandomSource random, SculkSpreader spreader, boolean spread) {
        if (spreader.isWorldGeneration()) {
            return getValidMovementPosWorldgen(par1, par2, par3);
        } else {
            return getValidMovementPos(par1, par2, par3);
        }
    }

    @Inject(method = "isMovementUnobstructed", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;subtract(Lnet/minecraft/core/Vec3i;)Lnet/minecraft/core/BlockPos;", shift = At.Shift.BEFORE), cancellable = true)
    private static void isMovementUnobstructed(LevelAccessor level, BlockPos startPos, BlockPos spreadPos, CallbackInfoReturnable<Boolean> cir) {
        BlockState cheatState = level.getBlockState(spreadPos);
        if (cheatState.is(WWBlockTags.SCULK_STAIR_REPLACEABLE) || cheatState.is(WWBlockTags.SCULK_WALL_REPLACEABLE) || cheatState.is(WWBlockTags.SCULK_SLAB_REPLACEABLE)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getValidMovementPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelAccessor;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    private static void getValidMovementPos(LevelAccessor level, BlockPos pos, RandomSource random, CallbackInfoReturnable<BlockPos> cir, BlockPos.MutableBlockPos mutable, BlockPos.MutableBlockPos mutable2, Iterator<Vec3i> var5, Vec3i vec3i) {
        boolean canReturn = false;
        BlockState state = level.getBlockState(mutable2);
        boolean isInTags = state.is(WWBlockTags.SCULK_SLAB_REPLACEABLE) || state.is(WWBlockTags.SCULK_WALL_REPLACEABLE) || state.is(WWBlockTags.SCULK_STAIR_REPLACEABLE);
        if (isInTags && isMovementUnobstructed(level, pos, mutable2)) {
            mutable.set(mutable2);
            canReturn = true;
            if (SculkVeinBlock.hasSubstrateAccess(level, state, mutable2)) {
                cir.cancel();
            }
        }

        if (canReturn) {
            cir.setReturnValue(mutable.equals(pos) ? null : mutable);
        }
    }

    //NEW METHODS
    @Unique
    private static SculkBehaviour getBlockBehaviourNew(BlockState state, boolean isWorldGen) {
        if (isWorldGen) {
            if (state.is(WWBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || state.is(WWBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || state.is(WWBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN)) {
                return new SlabWallStairSculkBehavior();
            } else if (state.is(WWBlocks.STONE_CHEST.get())) {
                return new BooleanPropertySculkBehavior(WWBlockStateProperties.HAS_SCULK, true);
            }
        } else {
            if (state.is(WWBlockTags.SCULK_WALL_REPLACEABLE) || state.is(WWBlockTags.SCULK_SLAB_REPLACEABLE) || state.is(WWBlockTags.SCULK_STAIR_REPLACEABLE)) {
                return new SlabWallStairSculkBehavior();
            }
        }
        return getBlockBehaviour(state);
    }

    @Unique
    private static boolean isMovementUnobstructedWorldgen(LevelAccessor level, BlockPos fromPos, BlockPos toPos) {
        if (fromPos.distManhattan(toPos) == 1) {
            return true;
        }
        BlockState cheatState = level.getBlockState(toPos);
        if (cheatState.is(WWBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN) || cheatState.is(WWBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || cheatState.is(WWBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || (cheatState.is(WWBlocks.STONE_CHEST.get()) && !cheatState.getValue(WWBlockStateProperties.HAS_SCULK))) {
            return true;
        }
        BlockPos blockPos = toPos.subtract(fromPos);
        Direction direction = Direction.fromAxisAndDirection(Direction.Axis.X, blockPos.getX() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
        Direction direction2 = Direction.fromAxisAndDirection(Direction.Axis.Y, blockPos.getY() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
        Direction direction3 = Direction.fromAxisAndDirection(Direction.Axis.Z, blockPos.getZ() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
        if (blockPos.getX() == 0) {
            return isUnobstructed(level, fromPos, direction2) || isUnobstructed(level, fromPos, direction3);
        }
        if (blockPos.getY() == 0) {
            return isUnobstructed(level, fromPos, direction) || isUnobstructed(level, fromPos, direction3);
        }
        return isUnobstructed(level, fromPos, direction) || isUnobstructed(level, fromPos, direction2);
    }

    //SHADOWS
    @Shadow
    private static boolean isMovementUnobstructed(LevelAccessor level, BlockPos sourcePos, BlockPos targetPos) {
		throw new AssertionError("Mixin injection failed - WilderWild SculkSpreaderChargeCursorMixin.");
    }

    @Nullable
    @Unique
    private static BlockPos getValidMovementPosWorldgen(LevelAccessor level, BlockPos pos, RandomSource random) {
        BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
        BlockPos.MutableBlockPos mutableBlockPos2 = pos.mutable();
        for (Vec3i vec3i : getRandomizedNonCornerNeighbourOffsets(random)) {
            mutableBlockPos2.setWithOffset(pos, vec3i);
            BlockState blockState = level.getBlockState(mutableBlockPos2);
            boolean canReturn = false;
            BlockState state = level.getBlockState(mutableBlockPos2);
            boolean isInTags = state.is(WWBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || state.is(WWBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || state.is(WWBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN) || (state.is(WWBlocks.STONE_CHEST.get()) && !state.getValue(WWBlockStateProperties.HAS_SCULK));
            if (isInTags && isMovementUnobstructedWorldgen(level, pos, mutableBlockPos2)) {
                mutableBlockPos.set(mutableBlockPos2);
                canReturn = true;
                if (SculkVeinBlock.hasSubstrateAccess(level, state, mutableBlockPos2)) {
                    return mutableBlockPos.equals(pos) ? null : mutableBlockPos;
                }
            }

            if (canReturn) {
                return mutableBlockPos.equals(pos) ? null : mutableBlockPos;
            }
            if (!(blockState.getBlock() instanceof SculkBehaviour) || !isMovementUnobstructed(level, pos, mutableBlockPos2))
                continue;
            mutableBlockPos.set(mutableBlockPos2);
            if (!SculkVeinBlock.hasSubstrateAccess(level, blockState, mutableBlockPos2)) continue;
            break;
        }
        return mutableBlockPos.equals(pos) ? null : mutableBlockPos;
    }

    @Shadow
    private static SculkBehaviour getBlockBehaviour(BlockState state) {
		throw new AssertionError("Mixin injection failed - WilderWild SculkSpreaderChargeCursorMixin.");
    }

    @Shadow
    private static List<Vec3i> getRandomizedNonCornerNeighbourOffsets(RandomSource random) {
		throw new AssertionError("Mixin injection failed - WilderWild SculkSpreaderChargeCursorMixin.");
    }

    @Shadow
    private static boolean isUnobstructed(LevelAccessor level, BlockPos pos, Direction direction) {
		throw new AssertionError("Mixin injection failed - WilderWild SculkSpreaderChargeCursorMixin.");
    }

    @Shadow
    private static BlockPos getValidMovementPos(LevelAccessor level, BlockPos pos, RandomSource random) {
		throw new AssertionError("Mixin injection failed - WilderWild SculkSpreaderChargeCursorMixin.");
    }
}
