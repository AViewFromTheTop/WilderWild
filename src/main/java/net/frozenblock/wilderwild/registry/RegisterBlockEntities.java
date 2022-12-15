package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.block.entity.PalmCrownBlockEntity;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class RegisterBlockEntities {
	private RegisterBlockEntities() {
		throw new UnsupportedOperationException("RegisterBlockEntities contains only static declarations.");
	}

    public static final BlockEntityType<HangingTendrilBlockEntity> HANGING_TENDRIL = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, WilderSharedConstants.id("hanging_tendril"), FabricBlockEntityTypeBuilder.create(HangingTendrilBlockEntity::new, RegisterBlocks.HANGING_TENDRIL).build(null));
    public static final BlockEntityType<TermiteMoundBlockEntity> TERMITE_MOUND = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, WilderSharedConstants.id("termite_mound"), FabricBlockEntityTypeBuilder.create(TermiteMoundBlockEntity::new, RegisterBlocks.TERMITE_MOUND).build(null));
    public static final BlockEntityType<DisplayLanternBlockEntity> DISPLAY_LANTERN = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, WilderSharedConstants.id("display_lantern"), FabricBlockEntityTypeBuilder.create(DisplayLanternBlockEntity::new, RegisterBlocks.DISPLAY_LANTERN).build(null));
    public static final BlockEntityType<StoneChestBlockEntity> STONE_CHEST = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, WilderSharedConstants.id("stone_chest"), FabricBlockEntityTypeBuilder.create(StoneChestBlockEntity::new, RegisterBlocks.STONE_CHEST).build(null));
	public static final BlockEntityType<PalmCrownBlockEntity> PALM_CROWN = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, WilderSharedConstants.id("palm_crown"), FabricBlockEntityTypeBuilder.create(PalmCrownBlockEntity::new, RegisterBlocks.PALM_CROWN).build(null));

    public static void register() {
        WilderSharedConstants.logWild("Registering BlockEntities for", WilderSharedConstants.UNSTABLE_LOGGING);
    }
}
