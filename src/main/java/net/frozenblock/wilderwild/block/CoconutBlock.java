package net.frozenblock.wilderwild.block;

import java.util.Objects;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.world.generation.sapling.PalmSaplingGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoconutBlock extends FallingBlock implements BonemealableBlock {
	public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Shapes.or(Block.box(2, 9, 2, 14, 16, 14)),
            Shapes.or(Block.box(1, 8, 1, 15, 16, 15)),
            Shapes.or(Block.box(0, 5, 0, 16, 16, 15)),
            Block.box(2, 0, 2, 14, 12, 14)
    };
	private final AbstractTreeGrower treeGrower;
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;

    public CoconutBlock(Properties settings) {
        super(settings);
		this.treeGrower = new PalmSaplingGenerator();
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0).setValue(AGE, 0).setValue(HANGING, false));
    }

	public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
		if (state.getValue(STAGE) == 0) {
			level.setBlock(pos, state.cycle(STAGE), 4);
		} else {
			this.treeGrower.growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
		}
	}

	@Override
	public VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return !isHanging(state) ? Shapes.empty() : super.getCollisionShape(state, level, pos, context);
	}

	@Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STAGE, AGE, HANGING);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx) {
        return Objects.requireNonNull(super.getStateForPlacement(ctx)).setValue(AGE, 2);
    }

	@Override
	public BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
		if (!state.canSurvive(level, currentPos)) {
			if (!isHanging(state)) {
				return Blocks.AIR.defaultBlockState();
			} else {
				level.scheduleTick(currentPos, this, this.getDelayAfterPlace());
			}
		}
		return state;
	}

    @Override
    public VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Vec3 vec3d = state.getOffset(level, pos);
        VoxelShape voxelShape;
        if (!state.getValue(HANGING)) {
            voxelShape = SHAPES[3];
        } else {
            voxelShape = SHAPES[state.getValue(AGE)];
        }

        return voxelShape.move(vec3d.x, vec3d.y, vec3d.z);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
		BlockState stateAbove = level.getBlockState(pos.above());
        return isHanging(state) ? (stateAbove.is(RegisterBlocks.PALM_LEAVES) && (stateAbove.getValue(BlockStateProperties.DISTANCE) < 2 || PalmLeavesBlock.updateDistance(stateAbove, level, pos).getValue(BlockStateProperties.DISTANCE) <= 1 || stateAbove.getValue(BlockStateProperties.PERSISTENT))) : this.mayPlaceOn(level.getBlockState(pos.below()));
    }

	protected boolean mayPlaceOn(BlockState state) {
		return state.is(BlockTags.DIRT) || state.is(Blocks.FARMLAND) || state.is(BlockTags.SAND);
	}

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (level.getMaxLocalRawBrightness(pos.above()) >= 9) {
			if (!isHanging(state)) {
				if (random.nextInt(7) == 0) {
					this.advanceTree(level, pos, state, random);
				}
			} else {
				if (!isFullyGrown(state)) {
					level.setBlock(pos, state.cycle(AGE), 2);
				}
			}
		}
    }

	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader world, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient) {
		return !isHanging(state) || !isFullyGrown(state);
	}

	@Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return isHanging(state) ? !isFullyGrown(state) : (double)level.random.nextFloat() < 0.45;
    }

	@Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        if (isHanging(state) && !isFullyGrown(state)) {
            level.setBlock(pos, state.cycle(AGE), 2);
        } else {
			this.advanceTree(level, pos, state, random);
        }
    }

	@Override
	public boolean propagatesSkylightDown(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		return state.getFluidState().isEmpty();
	}

	@Override
	public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull PathComputationType type) {
		if (type == PathComputationType.AIR && !isHanging(state)) {
			return true;
		}
		return super.isPathfindable(state, level, pos, type);
	}

	@Override
	public void onProjectileHit(@NotNull Level level, @NotNull BlockState state, @NotNull BlockHitResult hit, @NotNull Projectile projectile) {
		if (isHanging(state)) {
			if (isFullyGrown(state)) {
				FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(level, hit.getBlockPos(), state);
				this.falling(fallingBlockEntity);
			} else {
				level.destroyBlock(hit.getBlockPos(), true);
			}
		}
	}

	@Override
	protected void falling(FallingBlockEntity entity) {
		entity.setHurtsEntities(0.5f, 3);
	}

	@Override
	public void tick(@NotNull BlockState state, ServerLevel level, BlockPos pos, @NotNull RandomSource random) {
		if (pos.getY() < level.getMinBuildHeight() || !isHanging(state)) {
			return;
		}
		if (isHanging(state) && !state.canSurvive(level, pos)) {
			if (isFullyGrown(state)) {
				FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(level, pos, state);
				this.falling(fallingBlockEntity);
			} else {
				level.destroyBlock(pos, true);
			}
		}
	}

	@Override
	public void onLand(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull BlockState replaceableState, @NotNull FallingBlockEntity fallingBlock) {
		if (!level.isClientSide) {
			level.setBlock(pos, replaceableState, 3);
		}
	}

	@Override
	public void onBrokenAfterFall(@NotNull Level level, @NotNull BlockPos pos, @NotNull FallingBlockEntity fallingBlock) {
		level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, new ItemStack(RegisterItems.COCONUT, 3)));
		level.playSound(null, pos, RegisterSounds.BLOCK_COCONUT_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
	}

    private static boolean isHanging(BlockState state) {
        return state.getValue(HANGING);
    }

    private static boolean isFullyGrown(BlockState state) {
        return state.getValue(AGE) == 2;
    }

    public static BlockState getDefaultHangingState() {
        return getHangingState(0);
    }

    public static BlockState getHangingState(int age) {
        return RegisterBlocks.COCONUT.defaultBlockState().setValue(HANGING, true).setValue(AGE, age);
    }
}
