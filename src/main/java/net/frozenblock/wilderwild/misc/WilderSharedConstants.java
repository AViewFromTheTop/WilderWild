package net.frozenblock.wilderwild.misc;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class WilderSharedConstants {
	public static final String MOD_ID = "wilderwild";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static boolean DEV_LOGGING = false;
	/**
	 * Used for features that may be unstable and crash in public builds.
	 * <p>
	 * It's smart to use this for at least registries.
	 */
	public static boolean UNSTABLE_LOGGING = FabricLoader.getInstance().isDevelopmentEnvironment();
	public static final ModContainer MOD_CONTAINER = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow();
    public static boolean areConfigsInit = false;

	public static final int DATA_VERSION = 9;

	public static RandomSource random() {
		return RandomSource.create();
	}

	// LOGGING
	public static void log(String string, boolean shouldLog) {
		if (shouldLog) {
			WilderSharedConstants.LOGGER.info(string);
		}
	}

	public static void logInsane(String string, boolean shouldLog) {
		if (shouldLog) {
			for (int i = 0; i < Math.random() * 5; i++) {
				WilderSharedConstants.LOGGER.warn(string);
				WilderSharedConstants.LOGGER.error(string);
				WilderSharedConstants.LOGGER.warn(string);
				WilderSharedConstants.LOGGER.error(string);
				WilderSharedConstants.LOGGER.warn(string);
				WilderSharedConstants.LOGGER.error(string);
				WilderSharedConstants.LOGGER.warn(string);
				WilderSharedConstants.LOGGER.error(string);
			}
		}
	}

	public static void log(Entity entity, String string, boolean shouldLog) {
		if (shouldLog) {
			WilderSharedConstants.LOGGER.info(entity.toString() + " : " + string + " : " + entity.position());
		}
	}

	public static void log(Block block, String string, boolean shouldLog) {
		if (shouldLog) {
			WilderSharedConstants.LOGGER.info(block.toString() + " : " + string + " : ");
		}
	}

	public static void log(Block block, BlockPos pos, String string, boolean shouldLog) {
		if (shouldLog) {
			WilderSharedConstants.LOGGER.info(block.toString() + " : " + string + " : " + pos);
		}
	}

	public static void logWild(String string, boolean shouldLog) {
		if (shouldLog) {
			WilderSharedConstants.LOGGER.info(string + " " + WilderSharedConstants.MOD_ID);
		}
	}

	// MEASURING
	public static final Map<Object, Long> INSTANT_MAP = new HashMap<>();

	public static void startMeasuring(Object object) {
		long started = System.nanoTime();
		String name = object.getClass().getName();
		WilderSharedConstants.LOGGER.error("Started measuring {}", name.substring(name.lastIndexOf(".") + 1));
		INSTANT_MAP.put(object, started);
	}

	public static void stopMeasuring(Object object) {
		if (INSTANT_MAP.containsKey(object)) {
			String name = object.getClass().getName();
			WilderSharedConstants.LOGGER.error("{} took {} nanoseconds", name.substring(name.lastIndexOf(".") + 1), System.nanoTime() - INSTANT_MAP.get(object));
			INSTANT_MAP.remove(object);
		}
	}

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

	public static ResourceLocation vanillaId(String path) {
		return new ResourceLocation("minecraft", path);
	}

	public static String string(String path) {
		return WilderSharedConstants.id(path).toString();
	}
}
