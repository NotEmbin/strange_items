package embin.strangeitems;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrangeItems implements ModInitializer {
	public static final String MOD_ID = "strangeitems";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final boolean tooltipscroll_installed = FabricLoader.getInstance().isModLoaded("tooltipscroll");

	@Override
	public void onInitialize() {
		StrangeItemsComponents.init();

		LOGGER.info("These items... they're strange...");
	}
}