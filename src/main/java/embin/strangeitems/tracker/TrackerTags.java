package embin.strangeitems.tracker;

import embin.strangeitems.util.ConvertNamespace;
import net.minecraft.block.Block;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class TrackerTags {
    static ConvertNamespace cn = new ConvertNamespace();
    private static TagKey<Item> createTag(String name) {
        return TagKey.of(RegistryKeys.ITEM, cn.convertNamespace(name));
    }

    public static final TagKey<Item> CAN_TRACK_STATS = createTag("can_track_stats");
    public static final TagKey<Item> TRACKER_SHEEP_SHEARED = createTag("trackers/sheep_sheared");
    public static final TagKey<Item> TRACKER_PLANTS_TRIMMED = createTag("trackers/plants_trimmed");
    public static final TagKey<Item> TRACKER_SHOTS_FIRED = createTag("trackers/shots_fired");
    public static final TagKey<Item> TRACKER_SHOTS_HIT = createTag("trackers/shots_hit");
    public static final TagKey<Item> TRACKER_TIME_FLOWN = createTag("trackers/time_flown_with_elytra");
    public static final TagKey<Item> TRACKER_LOGS_STRIPPED = createTag("trackers/logs_stripped");
    public static final TagKey<Item> TRACKER_DIRT_TILLED = createTag("trackers/dirt_tilled");
    public static final TagKey<Item> TRACKER_PATHS_CREATED = createTag("trackers/paths_created");
    public static final TagKey<Item> TRACKER_CAMPFIRES_PUT_OUT = createTag("trackers/campfires_put_out");
    public static final TagKey<Item> TRACKER_CAMPFIRES_LIT = createTag("trackers/campfires_lit");
    public static final TagKey<Item> TRACKER_FIRES_LIT = createTag("trackers/fires_lit");
    public static final TagKey<Item> TRACKER_DAMAGE_DEALT = createTag("trackers/damage_dealt");
    public static final TagKey<Item> TRACKER_TRIDENT_THROWN = createTag("trackers/trident_thrown");
    public static final TagKey<Item> TRACKER_BLOCKS_BRUSHED = createTag("trackers/blocks_brushed");
    public static final TagKey<Item> TRACKER_ARMADILLOS_BRUSHED = createTag("trackers/armadillos_brushed");

    public static class Blocks {
        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, cn.convertNamespace(name));
        }

    }

    public static class Paintings {
        //public static final TagKey<PaintingVariant> PENGUINMOD_PAINTINGS = createTag("penguinmod_paintings");
        private static TagKey<PaintingVariant> createTag(String name) {
            return TagKey.of(RegistryKeys.PAINTING_VARIANT, cn.convertNamespace(name));
        }
    }
}
