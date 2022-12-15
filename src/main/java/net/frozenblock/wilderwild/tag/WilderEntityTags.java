package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public final class WilderEntityTags {
	private WilderEntityTags() {
		throw new UnsupportedOperationException("WilderEntityTags contains only static declarations.");
	}

    public static final TagKey<EntityType<?>> CAN_SWIM_IN_ALGAE = bind("can_swim_in_algae");
    public static final TagKey<EntityType<?>> CAN_SWIM_IN_MESOGLEA = bind("can_swim_in_mesoglea");
    public static final TagKey<EntityType<?>> ANCIENT_HORN_IMMUNE = bind("ancient_horn_immune");
    public static final TagKey<EntityType<?>> JELLYFISH_CANT_STING = bind("jellyfish_cant_sting");
	public static final TagKey<EntityType<?>> COCONUT_CANT_BONK = bind("coconut_cant_bonk");
	public static final TagKey<EntityType<?>> COCONUT_CANT_SPLIT = bind("coconut_cant_split");

    private static TagKey<EntityType<?>> bind(String path) {
        return TagKey.create(Registries.ENTITY_TYPE, WilderSharedConstants.id(path));
    }
}
