package net.frozenblock.wilderwild;

import net.frozenblock.lib.core.FrozenBools;
import net.frozenblock.lib.core.entrypoints.FrozenMainEntrypoint;
import net.frozenblock.lib.core.replacements_and_lists.BlockScheduledTicks;
import net.frozenblock.lib.core.replacements_and_lists.DripstoneDripWaterFrom;
import net.frozenblock.lib.core.replacements_and_lists.HopperUntouchableList;
import net.frozenblock.lib.core.replacements_and_lists.StructurePoolElementIdReplacements;
import net.frozenblock.lib.core.sound.SoundPredicate.SoundPredicate;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public final class FrozenLibIntegration implements FrozenMainEntrypoint {

    @Override
    public void init() {
        WilderWild.log("FrozenLib Main Entrypoint for WilderWild loaded.", WilderWild.UNSTABLE_LOGGING);
		SoundPredicate.register(WilderWild.id("instrument"), (SoundPredicate.LoopPredicate<Player>) entity -> {
            if (entity instanceof Player player) {
                return (player.getUseItem().getItem() instanceof InstrumentItem);
            }
            return false;
        });
        SoundPredicate.register(WilderWild.id("nectar"), (SoundPredicate.LoopPredicate<Firefly>) entity -> {
            if (entity.isSilent()) {
                return false;
            }
            if (entity.hasCustomName()) {
                Component name = entity.getCustomName();
                if (name != null) {
                    return name.getString().toLowerCase().contains("nectar");
                }
            }
            return false;
        });
		SoundPredicate.register(WilderWild.id("enderman_anger"), (SoundPredicate.LoopPredicate<EnderMan>) entity -> {
            if (entity.isSilent() || !entity.isAlive()) {
                return false;
            }
            return ((EnderMan) entity).isCreepy();
        });

        BlockScheduledTicks.ticks.put(Blocks.DIRT, (blockState, serverLevel, blockPos, randomSource) -> serverLevel.setBlock(blockPos, Blocks.MUD.defaultBlockState(), 3));
        HopperUntouchableList.blackListedTypes.add(RegisterBlockEntities.STONE_CHEST);
        //StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/city_center/city_center_1"), WilderWild.id("ancient_city/city_center/city_center_1"));
        //StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/city_center/city_center_2"), WilderWild.id("ancient_city/city_center/city_center_2"));
        FrozenBools.useNewDripstoneLiquid = true;
        DripstoneDripWaterFrom.map.put(Blocks.WET_SPONGE, (level, fluidInfo, blockPos) -> {
            BlockState blockState = Blocks.SPONGE.defaultBlockState();
            level.setBlockAndUpdate(fluidInfo.pos(), blockState);
            Block.pushEntitiesUp(fluidInfo.sourceState(), blockState, level, fluidInfo.pos());
            level.gameEvent(GameEvent.BLOCK_CHANGE, fluidInfo.pos(), GameEvent.Context.of(blockState));
            level.levelEvent(LevelEvent.DRIPSTONE_DRIP, blockPos, 0);
        });
        DripstoneDripWaterFrom.map.put(Blocks.MUD, (level, fluidInfo, blockPos) -> {
            BlockState blockState = Blocks.CLAY.defaultBlockState();
            level.setBlockAndUpdate(fluidInfo.pos(), blockState);
            Block.pushEntitiesUp(fluidInfo.sourceState(), blockState, level, fluidInfo.pos());
            level.gameEvent(GameEvent.BLOCK_CHANGE, fluidInfo.pos(), GameEvent.Context.of(blockState));
            level.levelEvent(LevelEvent.DRIPSTONE_DRIP, blockPos, 0);
        });

        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/structures/barracks"), WilderWild.id("ancient_city/structures/barracks"));
        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/structures/chamber_1"), WilderWild.id("ancient_city/structures/chamber_1"));
        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/structures/chamber_2"), WilderWild.id("ancient_city/structures/chamber_2"));
        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/structures/chamber_3"), WilderWild.id("ancient_city/structures/chamber_3"));
        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/structures/sauna_1"), WilderWild.id("ancient_city/structures/sauna_1"));
        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/structures/tall_ruin_1"), WilderWild.id("ancient_city/structures/tall_ruin_1"));
        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/structures/tall_ruin_2"), WilderWild.id("ancient_city/structures/tall_ruin_2"));
        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/structures/tall_ruin_3"), WilderWild.id("ancient_city/structures/tall_ruin_3"));
        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/structures/tall_ruin_4"), WilderWild.id("ancient_city/structures/tall_ruin_4"));
        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/structures/ice_box_1"), WilderWild.id("ancient_city/structures/ice_box_1"));
    }

    @Override
    public void initDevOnly() {

    }
}
