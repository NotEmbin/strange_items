package embin.strangeitems;

import embin.strangeitems.tracker.MapTracker;
import embin.strangeitems.tracker.TimestampTracker;
import embin.strangeitems.tracker.Tracker;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;

public class StrangeRegistries {

    public static final Registry<Tracker> TRACKER = FabricRegistryBuilder.createSimple(
            StrangeRegistryKeys.TRACKER
    ).attribute(RegistryAttribute.OPTIONAL).buildAndRegister();

    //public static final Registry<TimestampTracker> TIMESTAMP_TRACKER = FabricRegistryBuilder.createSimple(
    //        StrangeRegistryKeys.TIMESTAMP_TRACKER
    //).attribute(RegistryAttribute.OPTIONAL).buildAndRegister();
    //
    //public static final Registry<MapTracker> MAP_TRACKER = FabricRegistryBuilder.createSimple(
    //        StrangeRegistryKeys.MAP_TRACKER
    //).attribute(RegistryAttribute.OPTIONAL).buildAndRegister();

    public static void acknowledgeRegistries() {
        StrangeItems.LOGGER.info("Creating registry \"strangeitems:tracker\"");
    }
}
