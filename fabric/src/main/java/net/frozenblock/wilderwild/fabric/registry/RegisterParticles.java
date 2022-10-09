package net.frozenblock.wilderwild.fabric.registry;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.frozenblock.wilderwild.common.WilderWild;
import net.frozenblock.wilderwild.fabric.WilderWildFabric;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Function;

public final class RegisterParticles {
    public static final SimpleParticleType POLLEN = FabricParticleTypes.simple();
    public static final SimpleParticleType DANDELION_SEED = FabricParticleTypes.simple();
    public static final SimpleParticleType CONTROLLED_DANDELION_SEED = FabricParticleTypes.simple();
    public static final SimpleParticleType MILKWEED_SEED = FabricParticleTypes.simple();
    public static final SimpleParticleType CONTROLLED_MILKWEED_SEED = FabricParticleTypes.simple();
    public static final SimpleParticleType FLOATING_SCULK_BUBBLE = FabricParticleTypes.simple();
    public static final SimpleParticleType TERMITE = FabricParticleTypes.simple();
    public static final SimpleParticleType BLUE_PEARLESCENT_HANGING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType BLUE_PEARLESCENT_FALLING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType BLUE_PEARLESCENT_LANDING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType PURPLE_PEARLESCENT_HANGING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType PURPLE_PEARLESCENT_FALLING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType PURPLE_PEARLESCENT_LANDING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType PINK_HANGING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType PINK_FALLING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType PINK_LANDING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType RED_HANGING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType RED_FALLING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType RED_LANDING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType YELLOW_HANGING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType YELLOW_FALLING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType YELLOW_LANDING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType LIME_HANGING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType LIME_FALLING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType LIME_LANDING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType BLUE_HANGING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType BLUE_FALLING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType BLUE_LANDING_MESOGLEA = FabricParticleTypes.simple();

    public static void registerParticles() {
        WilderWildFabric.logWild("Registering Particles for", WilderWild.UNSTABLE_LOGGING);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("pollen"), POLLEN);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("dandelion_seed"), DANDELION_SEED);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("controlled_dandelion_seed"), CONTROLLED_DANDELION_SEED);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("milkweed_seed"), MILKWEED_SEED);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("controlled_milkweed_seed"), CONTROLLED_MILKWEED_SEED);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("floating_sculk_bubble"), FLOATING_SCULK_BUBBLE);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("termite"), TERMITE);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("blue_pearlescent_hanging_mesoglea_drip"), BLUE_PEARLESCENT_HANGING_MESOGLEA);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("blue_pearlescent_falling_mesoglea_drip"), BLUE_PEARLESCENT_FALLING_MESOGLEA);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("blue_pearlescent_landing_mesoglea_drip"), BLUE_PEARLESCENT_LANDING_MESOGLEA);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("purple_pearlescent_hanging_mesoglea_drip"), PURPLE_PEARLESCENT_HANGING_MESOGLEA);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("purple_pearlescent_falling_mesoglea_drip"), PURPLE_PEARLESCENT_FALLING_MESOGLEA);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("purple_pearlescent_landing_mesoglea_drip"), PURPLE_PEARLESCENT_LANDING_MESOGLEA);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("pink_hanging_mesoglea_drip"), PINK_HANGING_MESOGLEA);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("pink_falling_mesoglea_drip"), PINK_FALLING_MESOGLEA);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("pink_landing_mesoglea_drip"), PINK_LANDING_MESOGLEA);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("red_hanging_mesoglea_drip"), RED_HANGING_MESOGLEA);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("red_falling_mesoglea_drip"), RED_FALLING_MESOGLEA);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("red_landing_mesoglea_drip"), RED_LANDING_MESOGLEA);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("yellow_hanging_mesoglea_drip"), YELLOW_HANGING_MESOGLEA);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("yellow_falling_mesoglea_drip"), YELLOW_FALLING_MESOGLEA);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("yellow_landing_mesoglea_drip"), YELLOW_LANDING_MESOGLEA);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("lime_hanging_mesoglea_drip"), LIME_HANGING_MESOGLEA);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("lime_falling_mesoglea_drip"), LIME_FALLING_MESOGLEA);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("lime_landing_mesoglea_drip"), LIME_LANDING_MESOGLEA);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("blue_hanging_mesoglea_drip"), BLUE_HANGING_MESOGLEA);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("blue_falling_mesoglea_drip"), BLUE_FALLING_MESOGLEA);
        Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id("blue_landing_mesoglea_drip"), BLUE_LANDING_MESOGLEA);
    }

    private static <T extends ParticleOptions> ParticleType<T> register(String name, boolean alwaysShow, ParticleOptions.Deserializer<T> factory, Function<ParticleType<T>, Codec<T>> codecGetter) {
        return Registry.register(Registry.PARTICLE_TYPE, WilderWildFabric.id(name), new ParticleType<T>(alwaysShow, factory) {
            @Override
            public Codec<T> codec() {
                return codecGetter.apply(this);
            }
        });
    }
}
