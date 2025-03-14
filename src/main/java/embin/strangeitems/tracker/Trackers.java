package embin.strangeitems.tracker;

import embin.strangeitems.util.ConvertNamespace;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.stat.StatFormatter;

public class Trackers {
    private static final ConvertNamespace cn = new ConvertNamespace();

    public static final MapTracker blocks_mined = registerMap("blocks_mined", "block");
    public static final Tracker time_flown_with_elytra = register("time_flown_with_elytra", TrackerTags.TRACKER_TIME_FLOWN, StatFormatter.TIME, 20);
    public static final TimestampTracker times_dropped = registerTimestamp("times_dropped");
    public static final Tracker mobs_hit = register("mobs_hit");
    public static final Tracker logs_stripped = register("logs_stripped", TrackerTags.TRACKER_LOGS_STRIPPED);
    public static final Tracker dirt_tilled = register("dirt_tilled", TrackerTags.TRACKER_DIRT_TILLED);
    public static final Tracker paths_created = register("paths_created", TrackerTags.TRACKER_PATHS_CREATED);
    public static final Tracker campfires_put_out = register("campfires_put_out", TrackerTags.TRACKER_CAMPFIRES_PUT_OUT);
    public static final Tracker campfires_lit = register("campfires_lit", TrackerTags.TRACKER_CAMPFIRES_LIT);
    public static final Tracker fires_lit = register("fires_lit", TrackerTags.TRACKER_FIRES_LIT);
    public static final Tracker sheep_sheared = register("sheep_sheared", TrackerTags.TRACKER_SHEEP_SHEARED);
    public static final Tracker plants_trimmed = register("plants_trimmed", TrackerTags.TRACKER_PLANTS_TRIMMED);
    public static final Tracker shots_fired = register("shots_fired", TrackerTags.TRACKER_SHOTS_FIRED);
    public static final Tracker shots_hit = register("shots_hit", TrackerTags.TRACKER_SHOTS_HIT);
    public static final Tracker damage_dealt = register("damage_dealt", TrackerTags.TRACKER_DAMAGE_DEALT, StatFormatter.DIVIDE_BY_TEN, 1);
    public static final Tracker trident_thrown = register("trident_thrown", TrackerTags.TRACKER_TRIDENT_THROWN);
    public static final Tracker blocks_brushed = register("blocks_brushed", TrackerTags.TRACKER_BLOCKS_BRUSHED);
    public static final Tracker armadillos_brushed = register("armadillos_brushed", TrackerTags.TRACKER_ARMADILLOS_BRUSHED);
    public static final MapTracker mobs_killed = registerMap("mobs_killed", "entity", TrackerTags.TRACKER_MOBS_KILLED);
    public static final Tracker fish_caught = register("fish_caught", TrackerTags.TRACKER_FISH_CAUGHT);
    public static final Tracker damage_taken = register("damage_taken", TrackerTags.TRACKER_DAMAGE_TAKEN, StatFormatter.DIVIDE_BY_TEN, 1);
    public static final Tracker times_equipped = register("times_equipped", TrackerTags.TRACKER_TIMES_EQUIPPED);
    public static final Tracker times_fishing_rod_reeled_in = register("times_fishing_rod_reeled_in", TrackerTags.TRACKER_TIMES_FISHING_ROD_REELED_IN);
    public static final Tracker times_fishing_rod_cast = register("times_fishing_rod_cast", TrackerTags.TRACKER_TIMES_FISHING_ROD_CAST);
    public static final Tracker times_fishing_rod_caught_something = register("times_fishing_rod_caught_something", TrackerTags.TRACKER_TIMES_FISHING_CAUGHT_SOMETHING);
    public static final Tracker time_underwater = register("time_underwater", TrackerTags.TRACKER_TIME_UNDERWATER, StatFormatter.TIME, 1);
    public static final Tracker time_sneaking = register("time_sneaking", TrackerTags.TRACKER_TIME_SNEAKING, StatFormatter.TIME, 1);
    public static final Tracker distance_fallen = register("distance_fallen", TrackerTags.TRACKER_DISTANCE_FALLEN, StatFormatter.DISTANCE, 1);
    public static final Tracker time_in_lava = register("time_in_lava", TrackerTags.TRACKER_TIME_IN_LAVA, StatFormatter.TIME, 1);
    public static final MapTracker time_in_dimensions = registerMap("time_in_dimensions", "dimension", TrackerTags.TRACKER_TIME_IN_DIMENSIONS, StatFormatter.TIME);

    public static Tracker register(String id) {
        return new Tracker(cn.convertNamespace(id));
    }

    public static Tracker register(String id, TagKey<Item> tag) {
        return new Tracker(cn.convertNamespace(id), tag);
    }

    public static Tracker register(String id, TagKey<Item> tag, StatFormatter stat_formatter, int m) {
        return new Tracker(cn.convertNamespace(id), tag, stat_formatter, m);
    }

    public static TimestampTracker registerTimestamp(String id) {
        return new TimestampTracker(cn.convertNamespace(id));
    }

    public static TimestampTracker registerTimestamp(String id, TagKey<Item> tag) {
        return new TimestampTracker(cn.convertNamespace(id), tag);
    }

    public static MapTracker registerMap(String id, String prefix) {
        return new MapTracker(cn.convertNamespace(id), prefix);
    }

    public static MapTracker registerMap(String id, String prefix, TagKey<Item> tag) {
        return new MapTracker(cn.convertNamespace(id), prefix, tag);
    }

    public static MapTracker registerMap(String id, String prefix, TagKey<Item> tag, StatFormatter stat_formatter) {
        return new MapTracker(cn.convertNamespace(id), prefix, tag, stat_formatter);
    }
}
