package embin.strangeitems;

import embin.strangeitems.tracker.Tracker;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;

public class StrangeRegistries {

    public static final Registry<Tracker> TRACKER = FabricRegistryBuilder.createSimple(
            StrangeRegistryKeys.TRACKER
    ).attribute(RegistryAttribute.OPTIONAL).buildAndRegister();

    public static void acknowledgeRegistries() {
        StrangeItems.LOGGER.info("Creating registry \"strangeitems:tracker\"");
    }
}
