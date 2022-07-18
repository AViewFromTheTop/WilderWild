package net.frozenblock.wilderwild.block;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class HollowedLogBlock extends PillarBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    protected static final VoxelShape X_SHAPE = VoxelShapes.union(Block.createCuboidShape(0, 0, 0, 16, 16, 3), Block.createCuboidShape(0, 13, 0, 16, 16, 16), Block.createCuboidShape(0, 0, 13, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 3, 16));
    protected static final VoxelShape Y_SHAPE = VoxelShapes.union(Block.createCuboidShape(0, 0, 0, 16, 16, 3), Block.createCuboidShape(0, 0, 0, 3, 16, 16), Block.createCuboidShape(0, 0, 13, 16, 16, 16), Block.createCuboidShape(13, 0, 0, 16, 16, 16));
    protected static final VoxelShape Z_SHAPE = VoxelShapes.union(Block.createCuboidShape(13, 0, 0, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 3, 16, 16), Block.createCuboidShape(0, 13, 0, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 3, 16));
    protected static final VoxelShape RAYCAST_SHAPE = VoxelShapes.fullCube();
    //public static final IntProperty LEVEL = IntProperty.of("level", 0, 9);
    //public static final DirectionProperty FACING = Properties.FACING;

    // CLASS's BASE METHODS
    public HollowedLogBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(AXIS, Direction.Axis.Y));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(AXIS)) {
            default -> X_SHAPE;
            case Y -> Y_SHAPE;
            case Z -> Z_SHAPE;
        };
    }

    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return RAYCAST_SHAPE;
    }

    /*@Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        updateWaterCompatibility(world, state, pos);
        world.createAndScheduleBlockTick(pos, this, 1);
        super.onPlaced(world, pos, state, placer, itemStack);
    }*/

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(AXIS, ctx.getSide().getAxis()).with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        world.createAndScheduleBlockTick(pos, this, 1);
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }


    /*public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        updateWaterCompatibility(world, world.getBlockState(pos), pos);
    }

    private void updateWaterCompatibility(WorldAccess world, BlockState state, BlockPos pos) {
        Direction dir = state.get(FACING);
        if (state.get(LEVEL) != 9) { // Check for not persistent
            Block getBlock = world.getBlockState(pos.offset(dir.getOpposite())).getBlock();
            Fluid getFluid = world.getFluidState(pos.offset(dir.getOpposite())).getFluid();
            if (dir != Direction.UP) { // Up can not transport water
                if (getBlock instanceof HollowedLogBlock) {
                    if (world.getBlockState(pos.offset(dir.getOpposite())).get(FACING) == dir
                            && world.getBlockState(pos.offset(dir.getOpposite())).get(WATERLOGGED)) {
                        if (world.getBlockState(pos.offset(dir.getOpposite())).get(LEVEL) < 8) {
                            int waterlevel = world.getBlockState(pos.offset(dir.getOpposite())).get(LEVEL) + 1;
                            tryFillWithFluid(world, pos, state, Fluids.WATER.getDefaultState(), waterlevel);
                        } else if (world.getBlockState(pos.offset(dir.getOpposite())).get(LEVEL) == 9) {
                            tryFillWithFluid(world, pos, state, Fluids.WATER.getDefaultState(), 1);
                        }
                    } else {
                        tryDrainFluid(world, pos, state);
                    }
                } else if (getFluid == Fluids.WATER || getFluid == Fluids.FLOWING_WATER) {
                    int waterlevel;
                    if (getBlock == Blocks.WATER) {
                        if (world.getBlockState(pos.offset(dir.getOpposite())).get(FluidBlock.LEVEL) < 8) {
                            waterlevel = world.getBlockState(pos.offset(dir.getOpposite())).get(FluidBlock.LEVEL) + 1;
                            tryFillWithFluid(world, pos, state, Fluids.WATER.getDefaultState(), waterlevel);
                        }
                    } else {
                        tryFillWithFluid(world, pos, state, Fluids.WATER.getDefaultState(), 1);
                    }
                } else {
                    tryDrainFluid(world, pos, state);
                }
            }
        }
    }

    private boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState, int level) {
        if (!(Boolean) state.get(Properties.WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
            if (!world.isClient()) {
                world.setBlockState(pos, state.with(Properties.WATERLOGGED, true).with(LEVEL, level), 3);
                world.createAndScheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState defaultState) {
        return tryFillWithFluid(world, pos, state, defaultState, 9);
    }

    public ItemStack tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state, int level) {
        if (state.get(Properties.WATERLOGGED)) {
            int getLevel = world.getBlockState(pos).get(LEVEL);
            if (getLevel != 9) {
                world.setBlockState(pos, state.with(Properties.WATERLOGGED, false).with(LEVEL, level), 3);
                if (!state.canPlaceAt(world, pos)) {
                    world.breakBlock(pos, true);
                }
            }

            if (getLevel != 9) {
                return ItemStack.EMPTY;
            } else {
                return new ItemStack(Items.WATER_BUCKET);
            }
        } else {
            return ItemStack.EMPTY;
        }
    }

    public ItemStack tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state) {
        return tryDrainFluid(world, pos, state, 0);
    }*/

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED);
    }

    // RENDERING
    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return !(Boolean) state.get(WATERLOGGED);
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

}