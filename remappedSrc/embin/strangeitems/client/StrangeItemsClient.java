package embin.strangeitems.client;

import embin.strangeitems.client.config.StrangeConfig;
import embin.strangeitems.client.debug.TrackerListScreen;
import embin.strangeitems.util.Id;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrangeItemsClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("strangeitems/client");
    public static final KeyBinding.Category STRANGEKEYS = KeyBinding.Category.create(Id.of("strangeitems"));
    public static final KeyBinding.Category STRANGEKEYS_DEBUG = KeyBinding.Category.create(Id.of("strangeitems_debug"));

    private static KeyBinding keybind(String translation, int key) {
        return new KeyBinding(
            "key.strangeitems." + translation,
            InputUtil.Type.KEYSYM,
            key,
                STRANGEKEYS
        );
    }

    private static KeyBinding vanillaKeybind(String translation, int key) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.strangeitems." + translation,
                InputUtil.Type.KEYSYM,
                key,
                STRANGEKEYS
        ));
    }

    private static KeyBinding debugKeybind(String translation, int key) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.strangeitems." + translation,
                InputUtil.Type.KEYSYM,
                key,
                STRANGEKEYS_DEBUG
        ));
    }

    public static KeyBinding show_blocks_mined = keybind("show_blocks_mined", GLFW.GLFW_KEY_Z);
    public static KeyBinding show_times_dropped = keybind("show_times_dropped", GLFW.GLFW_KEY_RIGHT_ALT);
    public static KeyBinding show_mobs_killed = keybind("show_mobs_killed", GLFW.GLFW_KEY_LEFT_ALT);
    public static KeyBinding show_time_in_dimensions = keybind("show_time_in_dimension", GLFW.GLFW_KEY_GRAVE_ACCENT);
    public static KeyBinding show_tracker_ids = keybind("show_tracker_ids", GLFW.GLFW_KEY_COMMA);

    public static KeyBinding DEBUG_LIST_TRACKERS = debugKeybind("debug_list_trackers", GLFW.GLFW_KEY_KP_DIVIDE);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Reading config...");
        StrangeConfig.read_json();

        ClientTickEvents.END_CLIENT_TICK.register(Id.of("debug_list_trackers"), client -> {
            while (DEBUG_LIST_TRACKERS.wasPressed()) {
                client.setScreen(new TrackerListScreen(client.currentScreen));
            }
        });
    }
}
