package embin.strangeitems.tracker;

import com.google.common.collect.Maps;
import embin.strangeitems.StrangeItems;
import embin.strangeitems.client.StrangeItemsClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Util;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrackerKeybindings {
    public static KeyBinding unknown_keybinding = new KeyBinding(
        "key.strangeitems.unknown",
        InputUtil.Type.KEYSYM,
        GLFW.GLFW_KEY_UNKNOWN,
        "category.strangeitems.keys"
    );

    public static final List<Tracker> warned_keybindings = new ArrayList<>(99);

    public static final Map<MapTracker, KeyBinding> map_tracker_keybindings = Util.make(Maps.newHashMap(), (map) -> {
        map.put(Trackers.blocks_mined, StrangeItemsClient.show_blocks_mined);
        map.put(Trackers.mobs_killed, StrangeItemsClient.show_mobs_killed);
        map.put(Trackers.time_in_dimensions, StrangeItemsClient.show_time_in_dimensions);
    });

    public static final Map<TimestampTracker, KeyBinding> timestamp_tracker_keybindings = Util.make(Maps.newHashMap(), (map) -> {
        map.put(Trackers.times_dropped, StrangeItemsClient.show_times_dropped);
    });


    public static void define_map_keybind(MapTracker tracker, KeyBinding key) {
        map_tracker_keybindings.put(tracker, key);
    }

    public static void define_timestamp_keybind(TimestampTracker tracker, KeyBinding key) {
        timestamp_tracker_keybindings.put(tracker, key);
    }

    public static KeyBinding get_map_keybind(MapTracker tracker) {
        if (map_tracker_keybindings.containsKey(tracker)) {
            return map_tracker_keybindings.get(tracker);
        }
        if (!warned_keybindings.contains(tracker)) {
            StrangeItems.LOGGER.warn("Tracker " + tracker.get_id().toString() + " does not have an assigned key!");
            warned_keybindings.add(tracker);
        }
        return unknown_keybinding;
    }

    public static KeyBinding get_timestamp_keybind(TimestampTracker tracker) {
        if (timestamp_tracker_keybindings.containsKey(tracker)) {
            return timestamp_tracker_keybindings.get(tracker);
        }
        if (!warned_keybindings.contains(tracker)) {
            StrangeItems.LOGGER.warn("Tracker " + tracker.get_id().toString() + " does not have an assigned key!");
            warned_keybindings.add(tracker);
        }
        return unknown_keybinding;
    }
}
