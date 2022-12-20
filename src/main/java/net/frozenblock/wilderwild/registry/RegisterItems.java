package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.item.AlgaeItem;
import net.frozenblock.wilderwild.item.AncientHorn;
import net.frozenblock.wilderwild.item.CoconutItem;
import net.frozenblock.wilderwild.item.CopperHorn;
import net.frozenblock.wilderwild.item.FireflyBottle;
import net.frozenblock.wilderwild.item.FloweredLilyPadItem;
import net.frozenblock.wilderwild.item.MilkweedPod;
import net.frozenblock.wilderwild.misc.FireflyColor;
import net.frozenblock.wilderwild.misc.WilderBoats;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.tag.WilderInstrumentTags;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluids;

public final class RegisterItems {
	private RegisterItems() {
		throw new UnsupportedOperationException("RegisterItems contains only static declarations.");
	}

    public static final MilkweedPod MILKWEED_POD = new MilkweedPod(new FabricItemSettings().maxCount(64));
    public static final RecordItem MUSIC_DISC_BENEATH = new RecordItem(15, RegisterSounds.MUSIC_DISC_BENEATH, new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), 169);
    public static final RecordItem MUSIC_DISC_GOAT_HORN_SYMPHONY = new RecordItem(15, RegisterSounds.MUSIC_DISC_GOATHORN_SYMPHONY, new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), 144);
    public static final RecordItem MUSIC_DISC_BACK = new RecordItem(15, RegisterSounds.MUSIC_DISC_BACK, new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), 76);
    public static final Item FIREFLY_SPAWN_EGG = new SpawnEggItem(RegisterEntities.FIREFLY, Integer.parseInt("2A2E2B", 16), Integer.parseInt("AAF644", 16), new FabricItemSettings());
    public static final Item JELLYFISH_SPAWN_EGG = new SpawnEggItem(RegisterEntities.JELLYFISH, Integer.parseInt("E484E4", 16), Integer.parseInt("DF71DC", 16), new FabricItemSettings());
    public static final Item JELLYFISH_BUCKET = new MobBucketItem(RegisterEntities.JELLYFISH, Fluids.WATER, RegisterSounds.ITEM_BUCKET_EMPTY_JELLYFISH, new FabricItemSettings().maxCount(1));
	public static final Item TUMBLEWEED_SPAWN_EGG = new SpawnEggItem(RegisterEntities.TUMBLEWEED, Integer.parseInt("c7a065", 16), Integer.parseInt("755b44", 16), new FabricItemSettings());

    public static final Item BAOBAB_BOAT_ITEM = new BoatItem(false, WilderBoats.BAOBAB, new FabricItemSettings().maxCount(1));
    public static final Item BAOBAB_CHEST_BOAT_ITEM = new BoatItem(true, WilderBoats.BAOBAB, new FabricItemSettings().maxCount(1));
    public static final Item BAOBAB_SIGN = new SignItem(new FabricItemSettings().maxCount(16),
            RegisterBlocks.BAOBAB_SIGN_BLOCK, RegisterBlocks.BAOBAB_WALL_SIGN
	);
	public static final Item BAOBAB_HANGING_SIGN = new HangingSignItem(RegisterBlocks.BAOBAB_HANGING_SIGN, RegisterBlocks.BAOBAB_WALL_HANGING_SIGN,
			new FabricItemSettings().maxCount(16).requiredFeatures(FeatureFlags.UPDATE_1_20)
	);
    public static final Item CYPRESS_BOAT_ITEM = new BoatItem(false, WilderBoats.CYPRESS, new FabricItemSettings().maxCount(1));
    public static final Item CYPRESS_CHEST_BOAT_ITEM = new BoatItem(true, WilderBoats.CYPRESS, new FabricItemSettings().maxCount(1));
    public static final Item CYPRESS_SIGN = new SignItem(new FabricItemSettings().maxCount(16),
            RegisterBlocks.CYPRESS_SIGN_BLOCK, RegisterBlocks.CYPRESS_WALL_SIGN
	);
	public static final Item CYPRESS_HANGING_SIGN = new HangingSignItem(RegisterBlocks.CYPRESS_HANGING_SIGN, RegisterBlocks.CYPRESS_WALL_HANGING_SIGN,
			new FabricItemSettings().maxCount(16).requiredFeatures(FeatureFlags.UPDATE_1_20)
	);
	public static final Item PALM_BOAT_ITEM = new BoatItem(false, WilderBoats.PALM, new FabricItemSettings().maxCount(1));
	public static final Item PALM_CHEST_BOAT_ITEM = new BoatItem(true, WilderBoats.PALM, new FabricItemSettings().maxCount(1));
	public static final Item PALM_SIGN = new SignItem(new FabricItemSettings().maxCount(16),
			RegisterBlocks.PALM_SIGN_BLOCK, RegisterBlocks.PALM_WALL_SIGN
	);
	public static final Item PALM_HANGING_SIGN = new HangingSignItem(RegisterBlocks.PALM_HANGING_SIGN, RegisterBlocks.PALM_WALL_HANGING_SIGN,
			new FabricItemSettings().maxCount(16).requiredFeatures(FeatureFlags.UPDATE_1_20)
	);

	public static final Item COCONUT = new CoconutItem(RegisterBlocks.COCONUT, new FabricItemSettings());
    public static final Item POLLEN = new BlockItem(RegisterBlocks.POLLEN_BLOCK, new FabricItemSettings());

    public static final Item BAOBAB_NUT = new BlockItem(RegisterBlocks.BAOBAB_NUT, new FabricItemSettings().food(RegisterFood.BAOBAB_NUT));
	public static final Item SPLIT_COCONUT = new Item(new FabricItemSettings().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.4F).build()));
	//public static final Item PRICKLY_PEAR = new PrickOnUseBlockItem(RegisterBlocks.PRICKLY_PEAR_CACTUS, new FabricItemSettings().food(RegisterFood.PRICKLY_PEAR), 2F, RegisterSounds.PLAYER_HURT_CACTUS, "prickly_pear");
    //public static final Item PEELED_PRICKLY_PEAR = new Item(new FabricItemSettings().food(Foods.APPLE));

    public static final Item FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.ON);
    public static final Item BLACK_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.BLACK);
    public static final Item RED_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.RED);
    public static final Item GREEN_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.GREEN);
    public static final Item BROWN_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.BROWN);
    public static final Item BLUE_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.BLUE);
    public static final Item PURPLE_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.PURPLE);
    public static final Item CYAN_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.CYAN);
    public static final Item LIGHT_GRAY_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.LIGHT_GRAY);
    public static final Item GRAY_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.GRAY);
    public static final Item PINK_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.PINK);
    public static final Item LIME_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.LIME);
    public static final Item YELLOW_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.YELLOW);
    public static final Item LIGHT_BLUE_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.LIGHT_BLUE);
    public static final Item MAGENTA_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.MAGENTA);
    public static final Item ORANGE_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.ORANGE);
    public static final Item WHITE_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.WHITE);

    public static final Item ANCIENT_HORN_FRAGMENT = new Item(new FabricItemSettings().maxCount(64));

    public static final AncientHorn ANCIENT_HORN = new AncientHorn(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC), WilderInstrumentTags.ANCIENT_HORNS);
    public static final ResourceKey<Instrument> ANCIENT_HORN_INSTRUMENT = ResourceKey.create(Registries.INSTRUMENT, WilderSharedConstants.id("ancient_horn"));
    public static final CopperHorn COPPER_HORN = new CopperHorn(new FabricItemSettings().maxCount(1), WilderInstrumentTags.COPPER_HORNS, 0);
    public static final ResourceKey<Instrument> SAX_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WilderSharedConstants.id("sax_copper_horn"));
    public static final ResourceKey<Instrument> TUBA_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WilderSharedConstants.id("tuba_copper_horn"));
    public static final ResourceKey<Instrument> FLUTE_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WilderSharedConstants.id("flute_copper_horn"));
    public static final ResourceKey<Instrument> OBOE_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WilderSharedConstants.id("oboe_copper_horn"));
    public static final ResourceKey<Instrument> CLARINET_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WilderSharedConstants.id("clarinet_copper_horn"));
    public static final ResourceKey<Instrument> TRUMPET_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WilderSharedConstants.id("trumpet_copper_horn"));
    public static final ResourceKey<Instrument> TROMBONE_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WilderSharedConstants.id("trombone_copper_horn"));

    public static void registerItems() {
        WilderSharedConstants.logWild("Registering Items for", WilderSharedConstants.UNSTABLE_LOGGING);
		//BOATS
		registerItemAfter(Items.MANGROVE_CHEST_BOAT, PALM_CHEST_BOAT_ITEM, "palm_chest_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(Items.MANGROVE_CHEST_BOAT, PALM_BOAT_ITEM, "palm_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(Items.MANGROVE_CHEST_BOAT, CYPRESS_CHEST_BOAT_ITEM, "cypress_chest_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(Items.MANGROVE_CHEST_BOAT, CYPRESS_BOAT_ITEM, "cypress_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(Items.MANGROVE_CHEST_BOAT, BAOBAB_CHEST_BOAT_ITEM, "baobab_chest_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(Items.MANGROVE_CHEST_BOAT, BAOBAB_BOAT_ITEM, "baobab_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);
		//SIGNS
		registerItemAfter(Items.MANGROVE_SIGN, PALM_SIGN, "palm_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemAfter(Items.MANGROVE_SIGN, CYPRESS_SIGN, "cypress_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemAfter(Items.MANGROVE_SIGN, BAOBAB_SIGN, "baobab_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);

		registerItemBefore(Items.BAMBOO_HANGING_SIGN, BAOBAB_HANGING_SIGN, "baobab_hanging_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemBefore(Items.BAMBOO_HANGING_SIGN, CYPRESS_HANGING_SIGN, "cypress_hanging_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemBefore(Items.BAMBOO_HANGING_SIGN, PALM_HANGING_SIGN, "palm_hanging_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemAfter(Items.BEETROOT_SEEDS, MILKWEED_POD, "milkweed_pod", CreativeModeTabs.NATURAL_BLOCKS);
		registerItemBefore(Items.MUSIC_DISC_5, MUSIC_DISC_BENEATH, "music_disc_beneath", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemBefore(Items.MUSIC_DISC_OTHERSIDE, MUSIC_DISC_GOAT_HORN_SYMPHONY, "music_disc_goathorn_symphony", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(Items.MUSIC_DISC_5, MUSIC_DISC_BACK, "music_disc_back", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(Items.EVOKER_SPAWN_EGG, FIREFLY_SPAWN_EGG, "firefly_spawn_egg", CreativeModeTabs.SPAWN_EGGS);
		registerItemAfter(Items.HUSK_SPAWN_EGG, JELLYFISH_SPAWN_EGG, "jellyfish_spawn_egg", CreativeModeTabs.SPAWN_EGGS);
		registerItemAfter(Items.TURTLE_SPAWN_EGG, TUMBLEWEED_SPAWN_EGG, "tumbleweed_spawn_egg", CreativeModeTabs.SPAWN_EGGS);
		registerItemAfter(Items.AXOLOTL_BUCKET, JELLYFISH_BUCKET, "jellyfish_bucket", CreativeModeTabs.TOOLS_AND_UTILITIES);

        Registry.register(BuiltInRegistries.INSTRUMENT, ANCIENT_HORN_INSTRUMENT, new Instrument(RegisterSounds.ITEM_ANCIENT_HORN_CALL, 300, 256.0F));
        Registry.register(BuiltInRegistries.INSTRUMENT, SAX_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_SAX_LOOP, 32767, 64.0F));
        Registry.register(BuiltInRegistries.INSTRUMENT, TUBA_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_TUBA_LOOP, 32767, 64.0F));
        Registry.register(BuiltInRegistries.INSTRUMENT, FLUTE_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_FLUTE_LOOP, 32767, 64.0F));
        Registry.register(BuiltInRegistries.INSTRUMENT, OBOE_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_OBOE_LOOP, 32767, 64.0F));
        Registry.register(BuiltInRegistries.INSTRUMENT, CLARINET_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_CLARINET_LOOP, 32767, 64.0F));
        Registry.register(BuiltInRegistries.INSTRUMENT, TRUMPET_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_TRUMPET_LOOP, 32767, 64.0F));
        Registry.register(BuiltInRegistries.INSTRUMENT, TROMBONE_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_TROMBONE_LOOP, 32767, 64.0F));

		registerInstrumentBefore(Items.MUSIC_DISC_13, COPPER_HORN, "copper_horn", WilderInstrumentTags.COPPER_HORNS, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerInstrumentBefore(Items.MUSIC_DISC_13, ANCIENT_HORN, "ancient_horn", WilderInstrumentTags.ANCIENT_HORNS, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerInstrumentBefore(Items.BOW, ANCIENT_HORN, "ancient_horn", WilderInstrumentTags.ANCIENT_HORNS, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, CreativeModeTabs.COMBAT);

		registerItemAfter(Items.MANGROVE_PROPAGULE, BAOBAB_NUT, "baobab_nut", CreativeModeTabs.NATURAL_BLOCKS);
		registerItemAfter(Items.GLOW_BERRIES, SPLIT_COCONUT, "split_coconut", CreativeModeTabs.FOOD_AND_DRINKS);
		registerItemAfter(Items.GLOW_BERRIES, BAOBAB_NUT, "baobab_nut", CreativeModeTabs.FOOD_AND_DRINKS);
		registerItemAfter(RegisterBlocks.CYPRESS_SAPLING, COCONUT, "coconut", CreativeModeTabs.NATURAL_BLOCKS);

		registerItem(FIREFLY_BOTTLE, "firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(WHITE_FIREFLY_BOTTLE, "white_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(LIGHT_GRAY_FIREFLY_BOTTLE, "light_gray_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(GRAY_FIREFLY_BOTTLE, "gray_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(BLACK_FIREFLY_BOTTLE, "black_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(BROWN_FIREFLY_BOTTLE, "brown_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(RED_FIREFLY_BOTTLE, "red_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(ORANGE_FIREFLY_BOTTLE, "orange_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(YELLOW_FIREFLY_BOTTLE, "yellow_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(LIME_FIREFLY_BOTTLE, "lime_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(GREEN_FIREFLY_BOTTLE, "green_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(CYAN_FIREFLY_BOTTLE, "cyan_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(LIGHT_BLUE_FIREFLY_BOTTLE, "light_blue_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(BLUE_FIREFLY_BOTTLE, "blue_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(PURPLE_FIREFLY_BOTTLE, "purple_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(MAGENTA_FIREFLY_BOTTLE, "magenta_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(PINK_FIREFLY_BOTTLE, "pink_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);

		registerItemAfter(Items.GLOW_LICHEN, POLLEN, "pollen", CreativeModeTabs.NATURAL_BLOCKS);
		registerItemBefore(Items.LILY_PAD, new AlgaeItem(RegisterBlocks.ALGAE, new FabricItemSettings()), "algae", CreativeModeTabs.NATURAL_BLOCKS);
		registerItemAfter(Items.LILY_PAD, new FloweredLilyPadItem(RegisterBlocks.FLOWERING_LILY_PAD, new FabricItemSettings()), "flowering_lily_pad", CreativeModeTabs.NATURAL_BLOCKS);

		registerItemAfter(Items.ECHO_SHARD, ANCIENT_HORN_FRAGMENT, "ancient_horn_fragment", CreativeModeTabs.INGREDIENTS);

        TradeOfferHelper.registerWanderingTraderOffers(2, factories -> {
            factories.add(new VillagerTrades.ItemsForEmeralds(RegisterItems.BAOBAB_NUT, 5, 1, 8, 1));
            factories.add(new VillagerTrades.ItemsForEmeralds(RegisterBlocks.CYPRESS_SAPLING.asItem(), 5, 1, 8, 1));
			factories.add(new VillagerTrades.ItemsForEmeralds(RegisterItems.COCONUT, 5, 1, 8, 1));
        });
    }

	private static void registerInstrument(Item instrument, String path, TagKey<Instrument> tagKey, CreativeModeTab.TabVisibility tabVisibility, CreativeModeTab... tabs) {
		actualRegister(instrument, path);
		FrozenCreativeTabs.addInstrument(instrument, tagKey, tabVisibility, tabs);
	}

	private static void registerInstrumentBefore(Item comparedItem, Item instrument, String path, TagKey<Instrument> tagKey, CreativeModeTab.TabVisibility tabVisibility, CreativeModeTab... tabs) {
		WilderSharedConstants.log(path, WilderSharedConstants.DEV_LOGGING);
		actualRegister(instrument, path);
		FrozenCreativeTabs.addInstrumentBefore(comparedItem, instrument, tagKey, tabVisibility, tabs);
	}

	private static void registerItem(Item item, String path, CreativeModeTab... tabs) {
		actualRegister(item, path);
		FrozenCreativeTabs.add(item, tabs);
	}

	private static void registerItemBefore(ItemLike comparedItem, Item item, String path, CreativeModeTab... tabs) {
		registerItemBefore(comparedItem, item, path, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}

	private static void registerItemBefore(ItemLike comparedItem, Item item, String path, CreativeModeTab.TabVisibility tabVisibility, CreativeModeTab... tabs) {
		WilderSharedConstants.log(path, WilderSharedConstants.DEV_LOGGING);
		actualRegister(item, path);
		FrozenCreativeTabs.addBefore(comparedItem, item, tabVisibility, tabs);
	}

	private static void registerItemAfter(ItemLike comparedItem, Item item, String path, CreativeModeTab... tabs) {
		registerItemAfter(comparedItem, item, path, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}

	private static void registerItemAfter(ItemLike comparedItem, Item item, String path, CreativeModeTab.TabVisibility tabVisibility, CreativeModeTab... tabs) {
		WilderSharedConstants.log(path, WilderSharedConstants.DEV_LOGGING);
		actualRegister(item, path);
		FrozenCreativeTabs.addAfter(comparedItem, item, tabVisibility, tabs);
	}

	private static void actualRegister(Item item, String path) {
		if (BuiltInRegistries.ITEM.getOptional(WilderSharedConstants.id(path)).isEmpty()) {
			Registry.register(BuiltInRegistries.ITEM, WilderSharedConstants.id(path), item);
		}
	}
}
