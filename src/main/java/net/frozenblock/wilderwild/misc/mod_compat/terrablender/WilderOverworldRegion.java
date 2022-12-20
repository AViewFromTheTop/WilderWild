package net.frozenblock.wilderwild.misc.mod_compat.terrablender;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Consumer;
import net.frozenblock.lib.worldgen.biome.api.parameters.Continentalness;
import net.frozenblock.lib.worldgen.biome.api.parameters.Depth;
import net.frozenblock.lib.worldgen.biome.api.parameters.Erosion;
import net.frozenblock.lib.worldgen.biome.api.parameters.Humidity;
import net.frozenblock.lib.worldgen.biome.api.parameters.Temperature;
import net.frozenblock.lib.worldgen.biome.api.parameters.Weirdness;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.additions.gen.WilderSharedWorldgen;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;

public class WilderOverworldRegion extends Region {
    public WilderOverworldRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addModifiedVanillaOverworldBiomes(mapper, builder -> {
			// This will not grant the exact parameters as defined in SharedWorldgen. Instead, it will replace a part of old growth birch forests with mixed forests.
			builder.replaceBiome(Biomes.OLD_GROWTH_BIRCH_FOREST, RegisterWorldgen.MIXED_FOREST);

			// DON'T CHANGE THESE PARAMETERS. THESE ARE THE PARAMETERS OF SWAMPS
			List<Climate.ParameterPoint> swampPointsCypress = new ParameterUtils.ParameterPointListBuilder()
					.temperature(Climate.Parameter.span(Temperature.COOL, Temperature.NEUTRAL))
					.humidity(Humidity.FULL_RANGE)
					.continentalness(Climate.Parameter.span(Continentalness.NEAR_INLAND, Continentalness.FAR_INLAND))
					.erosion(Erosion.EROSION_6)
					.depth(Depth.SURFACE, Depth.FLOOR)
					.weirdness(Weirdness.VALLEY, Weirdness.LOW_SLICE_NORMAL_DESCENDING, Weirdness.LOW_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING, Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING)
					.offset(0.0F)
					.build();

			swampPointsCypress.forEach(point -> {
				builder.replaceParameter(point,
						Climate.parameters(
								WilderSharedWorldgen.CypressWetlands.TEMPERATURE,
								WilderSharedWorldgen.CypressWetlands.HUMIDITY,
								WilderSharedWorldgen.CypressWetlands.CONTINENTALNESS,
								WilderSharedWorldgen.CypressWetlands.EROSION,
								point.depth(),
								point.weirdness(),
								WilderSharedWorldgen.CypressWetlands.OFFSET
						)
				);

				builder.replaceBiome(point, RegisterWorldgen.CYPRESS_WETLANDS);
			});

			// DON'T CHANGE THESE PARAMETERS. THESE ARE THE PARAMETERS OF MANGROVE SWAMPS
			List<Climate.ParameterPoint> mangroveSwampPointsCypress = new ParameterUtils.ParameterPointListBuilder()
					.temperature(Climate.Parameter.span(Temperature.WARM, Temperature.HOT))
					.humidity(Humidity.FULL_RANGE)
					.continentalness(Climate.Parameter.span(Continentalness.NEAR_INLAND, Continentalness.FAR_INLAND))
					.erosion(Erosion.EROSION_6)
					.depth(Depth.SURFACE, Depth.FLOOR)
					.weirdness(Weirdness.VALLEY, Weirdness.LOW_SLICE_NORMAL_DESCENDING, Weirdness.LOW_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING, Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING)
					.offset(0.0F)
					.build();

			mangroveSwampPointsCypress.forEach(point -> {
				builder.replaceParameter(point,
						Climate.parameters(
								WilderSharedWorldgen.CypressWetlands.TEMPERATURE,
								WilderSharedWorldgen.CypressWetlands.HUMIDITY,
								WilderSharedWorldgen.CypressWetlands.CONTINENTALNESS,
								WilderSharedWorldgen.CypressWetlands.EROSION,
								point.depth(),
								point.weirdness(),
								WilderSharedWorldgen.CypressWetlands.OFFSET
						)
				);

				builder.replaceBiome(point, RegisterWorldgen.CYPRESS_WETLANDS);
			});

			// DON'T CHANGE THESE PARAMETERS. THESE ARE THE PARAMETERS OF DRIPSTONE CAVES
			List<Climate.ParameterPoint> dripstoneCavesPoints = new ParameterUtils.ParameterPointListBuilder()
					.temperature(Temperature.FULL_RANGE)
					.humidity(Humidity.FULL_RANGE)
					.continentalness(Climate.Parameter.span(0.8F, 1.0F))
					.erosion(Erosion.FULL_RANGE)
					.depth(Climate.Parameter.span(0.2F, 0.9F))
					.weirdness(Weirdness.FULL_RANGE)
					.offset(0.0F)
					.build();

			dripstoneCavesPoints.forEach(point -> {
				builder.replaceParameter(point,
						WilderSharedWorldgen.semiDeepParameters(
								WilderSharedWorldgen.JellyfishCaves.TEMPERATURE,
								WilderSharedWorldgen.JellyfishCaves.HUMIDITY,
								WilderSharedWorldgen.JellyfishCaves.CONTINENTALNESS,
								WilderSharedWorldgen.JellyfishCaves.EROSION,
								WilderSharedWorldgen.JellyfishCaves.WEIRDNESS,
								WilderSharedWorldgen.JellyfishCaves.OFFSET
						)
				);

				builder.replaceBiome(point, RegisterWorldgen.JELLYFISH_CAVES);
			});

			// DON'T CHANGE THESE PARAMETERS. THESE ARE THE PARAMETERS OF DESERTS
			List<Climate.ParameterPoint> desertPoints = new ParameterUtils.ParameterPointListBuilder()
					.temperature(Temperature.FULL_RANGE)
					.humidity(Humidity.FULL_RANGE)
					.continentalness(Climate.Parameter.span(0.8F, 1.0F))
					.erosion(Erosion.FULL_RANGE)
					.depth(Depth.SURFACE, Depth.FLOOR)
					.weirdness(Weirdness.FULL_RANGE)
					.offset(0.0F)
					.build();

			desertPoints.forEach(point -> {
				builder.replaceParameter(point,
						Climate.parameters(
								WilderSharedWorldgen.Oasis.WARM_RANGE,
								WilderSharedWorldgen.Oasis.HUMIDITY_DRY,
								WilderSharedWorldgen.Oasis.CONTINENTALNESS,
								WilderSharedWorldgen.Oasis.EROSION,
								point.depth(),
								point.weirdness(),
								WilderSharedWorldgen.Oasis.OFFSET
						)
				);

				builder.replaceBiome(point, RegisterWorldgen.OASIS);
			});

			// DON'T CHANGE THESE PARAMETERS. THESE ARE THE PARAMETERS OF RIVERS
			List<Climate.ParameterPoint> riverPoints = new ParameterUtils.ParameterPointListBuilder()
					.temperature(Climate.Parameter.span(-0.45F, 1.0F))
					.humidity(Humidity.FULL_RANGE)
					.continentalness(Climate.Parameter.span(-0.19F, 0.03F))
					.erosion(Erosion.FULL_RANGE)
					.depth(Climate.Parameter.span(0.0F, 1.0F))
					.weirdness(Climate.Parameter.span(-0.05F, 0.05F))
					.offset(0.0F)
					.build();

			riverPoints.forEach(point -> {
				builder.replaceParameter(point,
						Climate.parameters(
								WilderSharedWorldgen.WarmRiver.WARM_RANGE,
								WilderSharedWorldgen.WarmRiver.HUMIDITY,
								point.continentalness(),
								point.erosion(),
								point.depth(),
								point.weirdness(),
								point.offset()
						)
				);

				builder.replaceBiome(point, RegisterWorldgen.WARM_RIVER);
			});

            if (ClothConfigInteractionHandler.modifyMangroveSwampPlacement()) {
                // DON'T CHANGE THESE PARAMETERS. THESE ARE THE PARAMETERS OF MANGROVE SWAMPS
                List<Climate.ParameterPoint> mangroveSwampPoints = new ParameterUtils.ParameterPointListBuilder()
                        .temperature(Climate.Parameter.span(Temperature.WARM, Temperature.HOT))
                        .humidity(Humidity.FULL_RANGE)
                        .continentalness(Climate.Parameter.span(Continentalness.NEAR_INLAND, Continentalness.FAR_INLAND))
                        .erosion(Erosion.EROSION_6)
                        .depth(Depth.SURFACE, Depth.FLOOR)
                        .weirdness(Weirdness.VALLEY, Weirdness.LOW_SLICE_NORMAL_DESCENDING, Weirdness.LOW_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING, Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING)
                        .offset(0.0F)
                        .build();

                mangroveSwampPoints.forEach(point ->
                    builder.replaceParameter(point,
                            new Climate.ParameterPoint(
                                    WilderSharedWorldgen.MangroveSwamp.TEMPERATURE,
                                    WilderSharedWorldgen.MangroveSwamp.HUMIDITY,
                                    point.continentalness(),
                                    point.erosion(),
                                    point.depth(),
                                    point.weirdness(),
                                    point.offset()
                            )
                    )
                );
            }

            if (ClothConfigInteractionHandler.modifySwampPlacement()) {
                // DON'T CHANGE THESE PARAMETERS. THESE ARE THE PARAMETERS OF SWAMPS
                List<Climate.ParameterPoint> swampPoints = new ParameterUtils.ParameterPointListBuilder()
                        .temperature(Climate.Parameter.span(Temperature.COOL, Temperature.NEUTRAL))
                        .humidity(Humidity.FULL_RANGE)
                        .continentalness(Climate.Parameter.span(Continentalness.NEAR_INLAND, Continentalness.FAR_INLAND))
                        .erosion(Erosion.EROSION_6)
                        .depth(Depth.SURFACE, Depth.FLOOR)
                        .weirdness(Weirdness.VALLEY, Weirdness.LOW_SLICE_NORMAL_DESCENDING, Weirdness.LOW_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING, Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING)
                        .offset(0.0F)
                        .build();

                swampPoints.forEach(point ->
                    builder.replaceParameter(point,
                            new Climate.ParameterPoint(
                                    WilderSharedWorldgen.Swamp.TEMPERATURE,
                                    WilderSharedWorldgen.Swamp.HUMIDITY,
                                    point.continentalness(),
                                    point.erosion(),
                                    point.depth(),
                                    point.weirdness(),
                                    point.offset()
                            )
                    )
                );
            }
        });
    }
}
