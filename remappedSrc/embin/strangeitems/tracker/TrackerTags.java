package embin.strangeitems.tracker;

import embin.strangeitems.StrangeRegistryKeys;
import embin.strangeitems.util.Id;
import net.minecraft.registry.tag.TagKey;

public class TrackerTags {
    private static TagKey<Tracker> createTag(String name) {
        return TagKey.of(StrangeRegistryKeys.TRACKER, Id.of(name));
    }

    public static final TagKey<Tracker> HAS_SPECIAL_TOOLTIP = createTag("has_special_tooltip");
    public static final TagKey<Tracker> MAP_TRACKERS = createTag("map_trackers");
    public static final TagKey<Tracker> TIMESTAMP_TRACKERS = createTag("timestamp_trackers");
    public static final TagKey<Tracker> TOOLTIP_ORDER = createTag("tooltip_order");
}
