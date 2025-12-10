package embin.strangeitems;

import embin.strangeitems.tracker.MapTracker;
import embin.strangeitems.tracker.TimestampTracker;
import embin.strangeitems.tracker.Tracker;
import embin.strangeitems.util.Id;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class StrangeRegistryKeys {
    /**
     * Registry key for the tracker registry.
     */
    public static final ResourceKey<Registry<Tracker>> TRACKER = ResourceKey.createRegistryKey(Id.of("tracker"));

    @Deprecated(forRemoval = true)
    public static final ResourceKey<Registry<TimestampTracker>> TIMESTAMP_TRACKER = ResourceKey.createRegistryKey(Id.of("timestamp_tracker"));

    @Deprecated(forRemoval = true)
    public static final ResourceKey<Registry<MapTracker>> MAP_TRACKER = ResourceKey.createRegistryKey(Id.of("map_tracker"));
}
