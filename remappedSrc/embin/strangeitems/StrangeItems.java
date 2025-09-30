package embin.strangeitems;

import embin.strangeitems.tracker.Trackers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrangeItems implements ModInitializer {
	public static final String MOD_ID = "strangeitems";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	/**
	 * Check if Tooltip Scroll is installed.
	 * Does not respect the users settings on how it should handle this check.
	 */
	public static final boolean tooltipscroll_installed = FabricLoader.getInstance().isModLoaded("tooltipscroll");
	public static final boolean componentless_installed = FabricLoader.getInstance().isModLoaded("componentless");

	@Override
	public void onInitialize() {
		StrangeRegistries.acknowledgeRegistries();
		StrangeItemsComponents.init();
		Trackers.init();

		LOGGER.info("These items... they're strange...");
	}
}