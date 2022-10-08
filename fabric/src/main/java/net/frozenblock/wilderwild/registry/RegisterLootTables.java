package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.tag.WilderItemTags;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public final class RegisterLootTables {

    public static void init() {
        WilderWild.logWild("Registering Loot Table Modifications for", WilderWild.UNSTABLE_LOGGING);
        //ANCIENT HORN FRAGMENT
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (BuiltInLootTables.ANCIENT_CITY.equals(id) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.lootPool()
                        .add(LootItem.lootTableItem(RegisterItems.ANCIENT_HORN_FRAGMENT).setWeight(2).setQuality(Rarity.EPIC.ordinal() + 1)).
                        apply(SetItemCountFunction.setCount(UniformGenerator.between(-0.5F, 1.15F)))/*.apply(SetInstrumentFunction.setInstrumentOptions(WilderInstrumentTags.ANCIENT_HORNS))*/;

                tableBuilder.withPool(pool);
            }
        });
        //ALGAE
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (BuiltInLootTables.SHIPWRECK_SUPPLY.equals(id) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.lootPool()
                        .add(LootItem.lootTableItem(RegisterBlocks.ALGAE.asItem()).setWeight(5).setQuality(Rarity.COMMON.ordinal() + 1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 1.0F)));

                tableBuilder.withPool(pool);
            }
        });
        //BAOBAB NUT
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (BuiltInLootTables.VILLAGE_SAVANNA_HOUSE.equals(id) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.lootPool()
                        .add(LootItem.lootTableItem(RegisterItems.BAOBAB_NUT).setWeight(2).setQuality(Rarity.COMMON.ordinal() + 1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 1.0F)));

                tableBuilder.withPool(pool);
            }
        });
        //BAOBAB LOG
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (BuiltInLootTables.VILLAGE_SAVANNA_HOUSE.equals(id) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.lootPool()
                        .add(LootItem.lootTableItem(RegisterBlocks.BAOBAB_LOG.asItem()).setWeight(2).setQuality(Rarity.COMMON.ordinal() + 1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 1.0F)));

                tableBuilder.withPool(pool);
            }
        });
        //GOAT
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (EntityType.GOAT.getDefaultLootTable().equals(id) && source.isBuiltin()) {
                var pool = LootPool.lootPool().add(TagEntry.expandTag(WilderItemTags.GOAT_DROP_MUSIC_DISCS)).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.KILLER, EntityPredicate.Builder.entity().of(EntityTypeTags.SKELETONS)));

                tableBuilder.withPool(pool);
            }
        });
    }
}
