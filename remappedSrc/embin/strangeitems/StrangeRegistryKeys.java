package embin.strangeitems;

import embin.strangeitems.tracker.MapTracker;
import embin.strangeitems.tracker.TimestampTracker;
import embin.strangeitems.tracker.Tracker;
import embin.strangeitems.util.Id;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class StrangeRegistryKeys {
    /**
     * Registry key for the tracker registry.
     */
    public static final RegistryKey<Registry<Tracker>> TRACKER = RegistryKey.ofRegistry(Id.of("tracker"));

    @Deprecated(forRemoval = true)
    public static final RegistryKey<Registry<TimestampTracker>> TIMESTAMP_TRACKER = RegistryKey.ofRegistry(Id.of("timestamp_tracker"));

    @Deprecated(forRemoval = true)
    public static final RegistryKey<Registry<MapTracker>> MAP_TRACKER = RegistryKey.ofRegistry(Id.of("map_tracker"));
}
