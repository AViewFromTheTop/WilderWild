package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.frozenblock.lib.config.FrozenConfig;
import net.frozenblock.wilderwild.WilderWild;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.text;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.tooltip;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@Config(name = "block")
public final class BlockConfig implements ConfigData {
    //public static final EnumConfigOption<ModMenuConfig.ModsButtonStyle> MODS_BUTTON_STYLE = new EnumConfigOption<>("mods_button_style", ModMenuConfig.ModsButtonStyle.CLASSIC);

	@ConfigEntry.Gui.CollapsibleObject
	public StoneChestConfig stoneChest = new StoneChestConfig();

	public static class StoneChestConfig {
		public int stoneChestTimer = 100;
	}

    public boolean mcLiveSensorTendrils = true;
    public boolean shriekerGargling = true;
    public boolean soulFireSounds = true;

    @ClientOnly
    static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
        var config = WilderWildConfig.get().block;
		var stoneChest = config.stoneChest;
        category.setBackground(WilderWild.id("textures/config/block.png"));
        var mcLiveSensorTendrils = category.addEntry(entryBuilder.startBooleanToggle(text("mc_live_sensor_tendrils"), config.mcLiveSensorTendrils)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> config.mcLiveSensorTendrils = newValue)
                .setYesNoTextSupplier(bool -> text("mc_live_sensor_tendrils." + bool))
                .setTooltip(tooltip("mc_live_sensor_tendrils"))
                .build()
        );
        var shriekerGargling = category.addEntry(entryBuilder.startBooleanToggle(text("shrieker_gargling"), config.shriekerGargling)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.shriekerGargling = newValue)
                .setTooltip(tooltip("shrieker_gargling"))
                .build()
        );
        var soulFireSounds = category.addEntry(entryBuilder.startBooleanToggle(text("soul_fire_sounds"), config.soulFireSounds)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.soulFireSounds = newValue)
                .setTooltip(tooltip("soul_fire_sounds"))
                .build()
        );

        /*var displayLanternCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("display_lantern"),
                false,
                tooltip("display_lantern")

        );*/

		var stoneChestTimer = entryBuilder.startIntSlider(text("stone_chest_timer"), stoneChest.stoneChestTimer, 50, 200)
				.setDefaultValue(100)
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

    //public static final StringSetConfigOption HIDDEN_MODS = new StringSetConfigOption("hidden_mods", new HashSet<>());
    /*public static BlockConfig get() {
        AutoConfig.register(BlockConfig.class, GsonConfigSerializer::new);
        return AutoConfig.getConfigHolder(BlockConfig.class).getConfig();
    }*/
}
