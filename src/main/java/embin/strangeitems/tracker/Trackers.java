package embin.strangeitems.tracker;

import embin.strangeitems.util.ConvertNamespace;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.stat.StatFormatter;

public class Trackers {
    private static final ConvertNamespace cn = new ConvertNamespace();
    public static Tracker register(String id, TagKey<Item> tag, StatFormatter stat_formatter, int m) {
        return new Tracker(cn.convertNamespace(id), tag, stat_formatter, m);
    }
    public static Tracker register(String id, TagKey<Item> tag) {
        return new Tracker(cn.convertNamespace(id), tag);
    }
    public static Tracker register(String id) {
        return new Tracker(cn.convertNamespace(id));
    }

    public static final Tracker blocks_mined = register("blocks_mined", TrackerTags.CAN_TRACK_STATS);
    public static final Tracker time_flown_with_elytra = register("time_flown_with_elytra", TrackerTags.TRACKER_TIME_FLOWN, StatFormatter.TIME, 20);
    public static final Tracker times_dropped = register("times_dropped", TrackerTags.CAN_TRACK_STATS);
    public static final Tracker mobs_hit = register("mobs_hit", TrackerTags.CAN_TRACK_STATS);
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
    public static final Tracker blocks_mined_map = register("blocks_mined_map");
    public static final Tracker times_dropped_map = register("times_dropped_map");
    public static final Tracker mobs_killed = register("mobs_killed", TrackerTags.TRACKER_MOBS_KILLED);
    public static final Tracker mobs_killed_map = register("mobs_killed_map", TrackerTags.TRACKER_MOBS_KILLED);
}
