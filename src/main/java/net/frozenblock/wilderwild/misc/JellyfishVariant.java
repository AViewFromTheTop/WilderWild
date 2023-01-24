package net.frozenblock.wilderwild.misc;

import net.frozenblock.wilderwild.registry.WilderRegistry;
import net.minecraft.core.Registry;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceLocation;

public class JellyfishVariant {

    public static final EntityDataSerializer<JellyfishVariant> SERIALIZER = EntityDataSerializer.simpleId(WilderRegistry.JELLYFISH_VARIANT);

    public static final JellyfishVariant BLUE = register(WilderSharedConstants.id("blue"), WilderSharedConstants.id("textures/entity/jellyfish/blue.png"), false);
    public static final JellyfishVariant LIME = register(WilderSharedConstants.id("lime"), WilderSharedConstants.id("textures/entity/jellyfish/lime.png"), false);
    public static final JellyfishVariant PINK = register(WilderSharedConstants.id("pink"), WilderSharedConstants.id("textures/entity/jellyfish/pink.png"), false);
    public static final JellyfishVariant RED = register(WilderSharedConstants.id("red"), WilderSharedConstants.id("textures/entity/jellyfish/red.png"), false);
    public static final JellyfishVariant YELLOW = register(WilderSharedConstants.id("yellow"), WilderSharedConstants.id("textures/entity/jellyfish/yellow.png"), false);

    public static final JellyfishVariant PEARLESCENT_BLUE = register(WilderSharedConstants.id("pearlescent_blue"), WilderSharedConstants.id("textures/entity/jellyfish/pearlescent_blue.png"), true);
    public static final JellyfishVariant PEARLESCENT_PURPLE = register(WilderSharedConstants.id("pearlescent_purple"), WilderSharedConstants.id("textures/entity/jellyfish/pearlescent_purple.png"), true);

    private final ResourceLocation key;
    private final ResourceLocation texture;
    private final boolean pearlescent;

    public JellyfishVariant(ResourceLocation key, ResourceLocation texture, boolean pearlescent) {
        this.key = key;
        this.texture = texture;
        this.pearlescent = pearlescent;
    }

    public ResourceLocation getKey() {
        return this.key;
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }

    public boolean isNormal() {
        return !this.pearlescent;
    }

    public boolean isPearlescent() {
        return this.pearlescent;
    }

    public static JellyfishVariant register(ResourceLocation key, ResourceLocation texture, boolean pearlescent) {
        return Registry.register(WilderRegistry.JELLYFISH_VARIANT, key, new JellyfishVariant(key, texture, pearlescent));
    }

    static {
        EntityDataSerializers.registerSerializer(SERIALIZER);
    }
}
