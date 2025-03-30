package embin.strangeitems.tracker;

import embin.strangeitems.StrangeRegistries;
import embin.strangeitems.util.ConvertNamespace;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.stat.StatFormatter;

public class Trackers {

    public static final MapTracker blocks_mined = registerMap("blocks_mined", "block");
    public static final Tracker time_flown_with_elytra = register("time_flown_with_elytra", TrackerItemTags.TRACKER_TIME_FLOWN, StatFormatter.TIME, 20);
    public static final TimestampTracker times_dropped = registerTimestamp("times_dropped");
    public static final Tracker mobs_hit = register("mobs_hit");
    public static final Tracker logs_stripped = register("logs_stripped", TrackerItemTags.TRACKER_LOGS_STRIPPED);
    public static final Tracker dirt_tilled = register("dirt_tilled", TrackerItemTags.TRACKER_DIRT_TILLED);
    public static final Tracker paths_created = register("paths_created", TrackerItemTags.TRACKER_PATHS_CREATED);
    public static final Tracker campfires_put_out = register("campfires_put_out", TrackerItemTags.TRACKER_CAMPFIRES_PUT_OUT);
    public static final Tracker campfires_lit = register("campfires_lit", TrackerItemTags.TRACKER_CAMPFIRES_LIT);
    public static final Tracker fires_lit = register("fires_lit", TrackerItemTags.TRACKER_FIRES_LIT);
    public static final Tracker sheep_sheared = register("sheep_sheared", TrackerItemTags.TRACKER_SHEEP_SHEARED);
    public static final Tracker plants_trimmed = register("plants_trimmed", TrackerItemTags.TRACKER_PLANTS_TRIMMED);
    public static final Tracker shots_fired = register("shots_fired", TrackerItemTags.TRACKER_SHOTS_FIRED);
    public static final Tracker shots_hit = register("shots_hit", TrackerItemTags.TRACKER_SHOTS_HIT);
    public static final Tracker damage_dealt = register("damage_dealt", TrackerItemTags.TRACKER_DAMAGE_DEALT, StatFormatter.DIVIDE_BY_TEN, 1);
    public static final Tracker trident_thrown = register("trident_thrown", TrackerItemTags.TRACKER_TRIDENT_THROWN);
    public static final Tracker blocks_brushed = register("blocks_brushed", TrackerItemTags.TRACKER_BLOCKS_BRUSHED);
    public static final Tracker armadillos_brushed = register("armadillos_brushed", TrackerItemTags.TRACKER_ARMADILLOS_BRUSHED);
    public static final MapTracker mobs_killed = registerMap("mobs_killed", "entity", TrackerItemTags.TRACKER_MOBS_KILLED);
    public static final Tracker fish_caught = register("fish_caught", TrackerItemTags.TRACKER_FISH_CAUGHT);
    public static final Tracker damage_taken = register("damage_taken", TrackerItemTags.TRACKER_DAMAGE_TAKEN, StatFormatter.DIVIDE_BY_TEN, 1);
    public static final Tracker times_equipped = register("times_equipped", TrackerItemTags.TRACKER_TIMES_EQUIPPED);
    public static final Tracker times_fishing_rod_reeled_in = register("times_fishing_rod_reeled_in", TrackerItemTags.TRACKER_TIMES_FISHING_ROD_REELED_IN);
    public static final Tracker times_fishing_rod_cast = register("times_fishing_rod_cast", TrackerItemTags.TRACKER_TIMES_FISHING_ROD_CAST);
    public static final Tracker times_fishing_rod_caught_something = register("times_fishing_rod_caught_something", TrackerItemTags.TRACKER_TIMES_FISHING_CAUGHT_SOMETHING);
    public static final Tracker time_underwater = register("time_underwater", TrackerItemTags.TRACKER_TIME_UNDERWATER, StatFormatter.TIME, 1);
    public static final Tracker time_sneaking = register("time_sneaking", TrackerItemTags.TRACKER_TIME_SNEAKING, StatFormatter.TIME, 1);
    public static final Tracker distance_fallen = register("distance_fallen", TrackerItemTags.TRACKER_DISTANCE_FALLEN, StatFormatter.DISTANCE, 1);
    public static final Tracker time_in_lava = register("time_in_lava", TrackerItemTags.TRACKER_TIME_IN_LAVA, StatFormatter.TIME, 1);
    public static final MapTracker time_in_dimensions = registerMap("time_in_dimensions", "dimension", TrackerItemTags.TRACKER_TIME_IN_DIMENSIONS, StatFormatter.TIME);

    public static Tracker register(String id) {
        return Registry.register(StrangeRegistries.TRACKER, ConvertNamespace.convert(id), new Tracker(id, TrackerItemTags.CAN_TRACK_STATS));
    }

    public static Tracker register(String id, TagKey<Item> tag) {
        return Registry.register(StrangeRegistries.TRACKER, ConvertNamespace.convert(id), new Tracker(id, tag));
    }

    public static Tracker register(String id, TagKey<Item> tag, StatFormatter stat_formatter, int m) {
        return Registry.register(StrangeRegistries.TRACKER, ConvertNamespace.convert(id), new Tracker(id, tag, stat_formatter, m));
    }

    public static TimestampTracker registerTimestamp(String id) {
        return Registry.register(StrangeRegistries.TRACKER, ConvertNamespace.convert(id), new TimestampTracker(id));
    }

    public static TimestampTracker registerTimestamp(String id, TagKey<Item> tag) {
        return Registry.register(StrangeRegistries.TRACKER, ConvertNamespace.convert(id), new TimestampTracker(id, tag));
    }

    public static MapTracker registerMap(String id, String prefix) {
        return Registry.register(StrangeRegistries.TRACKER, ConvertNamespace.convert(id), new MapTracker(id, prefix));
    }

    public static MapTracker registerMap(String id, String prefix, TagKey<Item> tag) {
        return Registry.register(StrangeRegistries.TRACKER, ConvertNamespace.convert(id), new MapTracker(id, prefix, tag));
    }

    public static MapTracker registerMap(String id, String prefix, TagKey<Item> tag, StatFormatter stat_formatter) {
        return Registry.register(StrangeRegistries.TRACKER, ConvertNamespace.convert(id), new MapTracker(id, prefix, tag, stat_formatter));
    }

    public static void init() {}
}
