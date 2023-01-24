package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.FrozenConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultBlockConfig;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.text;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.tooltip;

@Config(name = "block")
public final class BlockConfig implements ConfigData {

	@ConfigEntry.Gui.CollapsibleObject
	public final BlockSoundsConfig blockSounds = new BlockSoundsConfig();
	@ConfigEntry.Gui.CollapsibleObject
	public final StoneChestConfig stoneChest = new StoneChestConfig();

	protected static class BlockSoundsConfig {
		public boolean cactusSounds = DefaultBlockConfig.BlockSoundsConfig.CACTUS_SOUNDS;
		public boolean claySounds = DefaultBlockConfig.BlockSoundsConfig.CLAY_SOUNDS;
		public boolean coarseDirtSounds = DefaultBlockConfig.BlockSoundsConfig.COARSE_DIRT_SOUNDS;
		public boolean cobwebSounds = DefaultBlockConfig.BlockSoundsConfig.COBWEB_SOUNDS;
		public boolean deadBushSounds = DefaultBlockConfig.BlockSoundsConfig.DEAD_BUSH_SOUNDS;
		public boolean flowerSounds = DefaultBlockConfig.BlockSoundsConfig.FLOWER_SOUNDS;
		public boolean frostedIceSounds = DefaultBlockConfig.BlockSoundsConfig.FROSTED_ICE_SOUNDS;
		public boolean gravelSounds = DefaultBlockConfig.BlockSoundsConfig.GRAVEL_SOUNDS;
		public boolean leafSounds = DefaultBlockConfig.BlockSoundsConfig.LEAF_SOUNDS;
		public boolean lilyPadSounds = DefaultBlockConfig.BlockSoundsConfig.LILY_PAD_SOUNDS;
		public boolean mushroomBlockSounds = DefaultBlockConfig.BlockSoundsConfig.MUSHROOM_BLOCK_SOUNDS;
		public boolean podzolSounds = DefaultBlockConfig.BlockSoundsConfig.PODZOL_SOUNDS;
		public boolean reinforcedDeepslateSounds = DefaultBlockConfig.BlockSoundsConfig.REINFORCED_DEEPSLATE_SOUNDS;
		public boolean sugarCaneSounds = DefaultBlockConfig.BlockSoundsConfig.SUGAR_CANE_SOUNDS;
		public boolean witherRoseSounds = DefaultBlockConfig.BlockSoundsConfig.WITHER_ROSE_SOUNDS;
	}

	protected static class StoneChestConfig {
		public int stoneChestTimer = DefaultBlockConfig.StoneChestConfig.STONE_CHEST_TIMER;
	}

    public boolean mcLiveSensorTendrils = DefaultBlockConfig.MC_LIVE_SENSOR_TENDRILS;
    public boolean shriekerGargling = DefaultBlockConfig.SHRIEKER_GARGLING;
    public boolean soulFireSounds = DefaultBlockConfig.SOUL_FIRE_SOUNDS;

    @Environment(EnvType.CLIENT)
    static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
        var config = WilderWildConfig.get().block;
		var blockSounds = config.blockSounds;
		var stoneChest = config.stoneChest;
        category.setBackground(WilderSharedConstants.id("textures/config/block.png"));
        var mcLiveSensorTendrils = category.addEntry(entryBuilder.startBooleanToggle(text("mc_live_sensor_tendrils"), config.mcLiveSensorTendrils)
                .setDefaultValue(DefaultBlockConfig.MC_LIVE_SENSOR_TENDRILS)
                .setSaveConsumer(newValue -> config.mcLiveSensorTendrils = newValue)
                .setYesNoTextSupplier(bool -> text("mc_live_sensor_tendrils." + bool))
                .setTooltip(tooltip("mc_live_sensor_tendrils"))
                .build()
        );
        var shriekerGargling = category.addEntry(entryBuilder.startBooleanToggle(text("shrieker_gargling"), config.shriekerGargling)
                .setDefaultValue(DefaultBlockConfig.SHRIEKER_GARGLING)
                .setSaveConsumer(newValue -> config.shriekerGargling = newValue)
                .setTooltip(tooltip("shrieker_gargling"))
                .build()
        );
        var soulFireSounds = category.addEntry(entryBuilder.startBooleanToggle(text("soul_fire_sounds"), config.soulFireSounds)
                .setDefaultValue(DefaultBlockConfig.SOUL_FIRE_SOUNDS)
                .setSaveConsumer(newValue -> config.soulFireSounds = newValue)
                .setTooltip(tooltip("soul_fire_sounds"))
                .build()
        );

        /*var displayLanternCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("display_lantern"),
                false,
                tooltip("display_lantern")

        );*/

		var cactusSounds = entryBuilder.startBooleanToggle(text("cactus_sounds"), blockSounds.cactusSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.CACTUS_SOUNDS)
				.setSaveConsumer(newValue -> blockSounds.cactusSounds = newValue)
				.setTooltip(tooltip("cactus_sounds"))
				.requireRestart()
				.build();

		var claySounds = entryBuilder.startBooleanToggle(text("clay_sounds"), blockSounds.claySounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.CLAY_SOUNDS)
				.setSaveConsumer(newValue -> blockSounds.claySounds = newValue)
				.setTooltip(tooltip("clay_sounds"))
				.requireRestart()
				.build();

		var coarseDirtSounds = entryBuilder.startBooleanToggle(text("coarse_dirt_sounds"), blockSounds.coarseDirtSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.COARSE_DIRT_SOUNDS)
				.setSaveConsumer(newValue -> blockSounds.coarseDirtSounds = newValue)
				.setTooltip(tooltip("coarse_dirt_sounds"))
				.requireRestart()
				.build();

		var cobwebSounds = entryBuilder.startBooleanToggle(text("cobweb_sounds"), blockSounds.cobwebSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.COBWEB_SOUNDS)
				.setSaveConsumer(newValue -> blockSounds.cobwebSounds = newValue)
				.setTooltip(tooltip("cobweb_sounds"))
				.requireRestart()
				.build();

		var deadBushSounds = entryBuilder.startBooleanToggle(text("dead_bush_sounds"), blockSounds.deadBushSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.DEAD_BUSH_SOUNDS)
				.setSaveConsumer(newValue -> blockSounds.deadBushSounds = newValue)
				.setTooltip(tooltip("dead_bush_sounds"))
				.requireRestart()
				.build();

		var flowerSounds = entryBuilder.startBooleanToggle(text("flower_sounds"), blockSounds.flowerSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.FLOWER_SOUNDS)
				.setSaveConsumer(newValue -> blockSounds.flowerSounds = newValue)
				.setTooltip(tooltip("flower_sounds"))
				.requireRestart()
				.build();

		var gravelSounds = entryBuilder.startBooleanToggle(text("gravel_sounds"), blockSounds.gravelSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.GRAVEL_SOUNDS)
				.setSaveConsumer(newValue -> blockSounds.gravelSounds = newValue)
				.setTooltip(tooltip("gravel_sounds"))
				.requireRestart()
				.build();

		var frostedIceSounds = entryBuilder.startBooleanToggle(text("frosted_ice_sounds"), blockSounds.frostedIceSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.FROSTED_ICE_SOUNDS)
				.setSaveConsumer(newValue -> blockSounds.frostedIceSounds = newValue)
				.setTooltip(tooltip("frosted_ice_sounds"))
				.requireRestart()
				.build();

		var leafSounds = entryBuilder.startBooleanToggle(text("leaf_sounds"), blockSounds.leafSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.LEAF_SOUNDS)
				.setSaveConsumer(newValue -> blockSounds.leafSounds = newValue)
				.setTooltip(tooltip("leaf_sounds"))
				.requireRestart()
				.build();

		var lilyPadSounds = entryBuilder.startBooleanToggle(text("lily_pad_sounds"), blockSounds.lilyPadSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.LILY_PAD_SOUNDS)
				.setSaveConsumer(newValue -> blockSounds.lilyPadSounds = newValue)
				.setTooltip(tooltip("lily_pad_sounds"))
				.requireRestart()
				.build();

		var mushroomBlockSounds = entryBuilder.startBooleanToggle(text("mushroom_block_sounds"), blockSounds.mushroomBlockSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.MUSHROOM_BLOCK_SOUNDS)
				.setSaveConsumer(newValue -> blockSounds.mushroomBlockSounds = newValue)
				.setTooltip(tooltip("mushroom_block_sounds"))
				.requireRestart()
				.build();

		var podzolSounds = entryBuilder.startBooleanToggle(text("podzol_sounds"), blockSounds.podzolSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.PODZOL_SOUNDS)
				.setSaveConsumer(newValue -> blockSounds.podzolSounds = newValue)
				.setTooltip(tooltip("podzol_sounds"))
				.requireRestart()
				.build();

		var reinforcedDeepslateSounds = entryBuilder.startBooleanToggle(text("reinforced_deepslate_sounds"), blockSounds.reinforcedDeepslateSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.REINFORCED_DEEPSLATE_SOUNDS)
				.setSaveConsumer(newValue -> blockSounds.reinforcedDeepslateSounds = newValue)
				.setTooltip(tooltip("reinforced_deepslate_sounds"))
				.requireRestart()
				.build();

		var sugarCaneSounds = entryBuilder.startBooleanToggle(text("sugar_cane_sounds"), blockSounds.sugarCaneSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.SUGAR_CANE_SOUNDS)
				.setSaveConsumer(newValue -> blockSounds.sugarCaneSounds = newValue)
				.setTooltip(tooltip("sugar_cane_sounds"))
				.requireRestart()
				.build();

		var witherRoseSounds = entryBuilder.startBooleanToggle(text("wither_rose_sounds"), blockSounds.witherRoseSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.WITHER_ROSE_SOUNDS)
				.setSaveConsumer(newValue -> blockSounds.witherRoseSounds = newValue)
				.setTooltip(tooltip("wither_rose_sounds"))
				.requireRestart()
				.build();

		var blockSoundsCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("block_sounds"),
				false,
				tooltip("block_sounds"),
				cactusSounds, claySounds, coarseDirtSounds, cobwebSounds, deadBushSounds,
				flowerSounds, gravelSounds, frostedIceSounds, leafSounds, lilyPadSounds,
				mushroomBlockSounds, podzolSounds, reinforcedDeepslateSounds, sugarCaneSounds, witherRoseSounds
		);

		var stoneChestTimer = entryBuilder.startIntSlider(text("stone_chest_timer"), stoneChest.stoneChestTimer, 50, 200)
				.setDefaultValue(DefaultBlockConfig.StoneChestConfig.STONE_CHEST_TIMER)
				.setSaveConsumer(newValue -> stoneChest.stoneChestTimer = newValue)
				.setTooltip(tooltip("stone_chest_timer"))
				.build();

        var stoneChestCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("stone_chest"),
                false,
                tooltip("stone_chest"),
				stoneChestTimer
        );

        /*var termiteCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("termite"),
                false,
                tooltip("termite")

        );*/
    }
}
