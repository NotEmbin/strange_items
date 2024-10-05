package embin.strangeitems;

import embin.strangeitems.items.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrangeItems implements ModInitializer {
	public static final String MOD_ID = "strangeitems";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		StrangeItemsComponents.init();
		ModItems.init();

		LOGGER.info("These items... they're strange...");
	}
}