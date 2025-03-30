package embin.strangeitems;

import embin.strangeitems.tracker.MapTracker;
import embin.strangeitems.tracker.TimestampTracker;
import embin.strangeitems.tracker.Tracker;
import embin.strangeitems.util.ConvertNamespace;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class StrangeRegistryKeys {
    public static final RegistryKey<Registry<Tracker>> TRACKER = RegistryKey.ofRegistry(ConvertNamespace.convert("tracker"));
    public static final RegistryKey<Registry<TimestampTracker>> TIMESTAMP_TRACKER = RegistryKey.ofRegistry(ConvertNamespace.convert("timestamp_tracker"));
    public static final RegistryKey<Registry<MapTracker>> MAP_TRACKER = RegistryKey.ofRegistry(ConvertNamespace.convert("map_tracker"));
}
