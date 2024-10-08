package embin.strangeitems.client;

import embin.strangeitems.config.StrangeConfig;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrangeItemsClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("strangeitems/client");
    @Override
    public void onInitializeClient() {
        StrangeConfig.read_json();
    }
}
