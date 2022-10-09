package net.frozenblock.wilderwild.fabric.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.frozenblock.wilderwild.fabric.WilderWildFabric;
import net.frozenblock.wilderwild.fabric.WilderWildClientFabric;
import net.frozenblock.wilderwild.fabric.block.StoneChestBlock;
import net.frozenblock.wilderwild.fabric.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.fabric.registry.RegisterProperties;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.jetbrains.annotations.NotNull;

public class StoneChestBlockEntityRenderer<T extends StoneChestBlockEntity & LidBlockEntity> extends ChestRenderer<T> {

    private static final String BASE = "bottom";
    private static final String LID = "lid";
    private final ModelPart singleChestLid;
    private final ModelPart singleChestBase;
    private final ModelPart doubleChestLeftLid;
    private final ModelPart doubleChestLeftBase;
    private final ModelPart doubleChestRightLid;
    private final ModelPart doubleChestRightBase;

    public static final Material STONE = getChestTextureId("stone");
    public static final Material STONE_LEFT = getChestTextureId("stone_left");
    public static final Material STONE_RIGHT = getChestTextureId("stone_right");
    public static final Material STONE_SCULK = getChestTextureId("ancient");
    public static final Material STONE_LEFT_SCULK = getChestTextureId("ancient_left");
    public static final Material STONE_RIGHT_SCULK = getChestTextureId("ancient_right");

    public StoneChestBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);

        ModelPart modelPart = ctx.bakeLayer(WilderWildClientFabric.STONE_CHEST);
        this.singleChestBase = modelPart.getChild(BASE);
        this.singleChestLid = modelPart.getChild(LID);
        ModelPart modelPart2 = ctx.bakeLayer(WilderWildClientFabric.DOUBLE_STONE_CHEST_LEFT);
        this.doubleChestLeftBase = modelPart2.getChild(BASE);
        this.doubleChestLeftLid = modelPart2.getChild(LID);
        ModelPart modelPart3 = ctx.bakeLayer(WilderWildClientFabric.DOUBLE_STONE_CHEST_RIGHT);
        this.doubleChestRightBase = modelPart3.getChild(BASE);
        this.doubleChestRightLid = modelPart3.getChild(LID);
    }

    public static LayerDefinition createSingleBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 17).addBox(1.0F, 0.0F, 1.0F, 14.0F, 12.0F, 14.0F), PartPose.ZERO);
        modelPartData.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, 0.0F, 0.0F, 14.0F, 3.0F, 14.0F), PartPose.offset(0.0F, 11.0F, 1.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    public static LayerDefinition createDoubleBodyRightLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 17).addBox(1.0F, 0.0F, 1.0F, 15.0F, 12.0F, 14.0F), PartPose.ZERO);
        modelPartData.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, 0.0F, 0.0F, 15.0F, 3.0F, 14.0F), PartPose.offset(0.0F, 11.0F, 1.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    public static LayerDefinition createDoubleBodyLeftLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 17).addBox(0.0F, 0.0F, 1.0F, 15.0F, 12.0F, 14.0F), PartPose.ZERO);
        modelPartData.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 15.0F, 3.0F, 14.0F), PartPose.offset(0.0F, 11.0F, 1.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void render(T entity, float tickDelta, @NotNull PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light, int overlay) {
        Level level = entity.getLevel();
        boolean bl = level != null;
        BlockState blockState = bl ? entity.getBlockState() : Blocks.CHEST.defaultBlockState().setValue(StoneChestBlock.FACING, Direction.SOUTH);
        ChestType chestType = blockState.hasProperty(StoneChestBlock.TYPE) ? blockState.getValue(StoneChestBlock.TYPE) : ChestType.SINGLE;
        Block block = blockState.getBlock();
        if (block instanceof AbstractChestBlock<?> abstractStoneChestBlock) {
            boolean bl2 = chestType != ChestType.SINGLE;
            matrices.pushPose();
            float f = blockState.getValue(StoneChestBlock.FACING).toYRot();
            matrices.translate(0.5, 0.5, 0.5);
            matrices.mulPose(Vector3f.YP.rotationDegrees(-f));
            matrices.translate(-0.5, -0.5, -0.5);
            DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> propertySource;
            if (bl) {
                propertySource = abstractStoneChestBlock.combine(blockState, level, entity.getBlockPos(), true);
            } else {
                propertySource = DoubleBlockCombiner.Combiner::acceptNone;
            }

            float openProg = entity.getOpenProgress(tickDelta);
            openProg = 1.0f - openProg;
            openProg = 1.0f - openProg * openProg * openProg;
            int i = propertySource.apply(new BrightnessCombiner<>()).applyAsInt(light);
            Material spriteIdentifier = getChestTexture(chestType, entity.getBlockState().getValue(RegisterProperties.HAS_SCULK));
            VertexConsumer vertexConsumer = spriteIdentifier.buffer(vertexConsumers, RenderType::entityCutout);
            if (bl2) {
                if (chestType == ChestType.LEFT) {
                    this.render(matrices, vertexConsumer, this.doubleChestLeftLid, this.doubleChestLeftBase, openProg, i, overlay);
                } else {
                    this.render(matrices, vertexConsumer, this.doubleChestRightLid, this.doubleChestRightBase, openProg, i, overlay);
                }
            } else {
                this.render(matrices, vertexConsumer, this.singleChestLid, this.singleChestBase, openProg, i, overlay);
            }

            matrices.popPose();
        }
    }

    private void render(PoseStack matrices, VertexConsumer vertices, ModelPart lid, ModelPart base, float openFactor, int light, int overlay) {
        lid.xRot = -(openFactor * 1.5707964f);
        lid.render(matrices, vertices, light, overlay);
        base.render(matrices, vertices, light, overlay);
    }

    public static Material getChestTexture(ChestType type, boolean sculk) {
        return !sculk ? getChestTexture(type, STONE, STONE_LEFT, STONE_RIGHT) : getChestTexture(type, STONE_SCULK, STONE_LEFT_SCULK, STONE_RIGHT_SCULK);
    }

    private static Material getChestTexture(ChestType type, Material single, Material left, Material right) {
        switch (type) {
            case LEFT:
                return left;
            case RIGHT:
                return right;
            case SINGLE:
            default:
                return single;
        }
    }

    public static Material getChestTextureId(String variant) {
        return new Material(Sheets.CHEST_SHEET, WilderWildFabric.id("entity/stone_chest/" + variant));
    }
}
