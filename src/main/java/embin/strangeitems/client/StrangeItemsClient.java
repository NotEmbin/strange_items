package embin.strangeitems.client;

import embin.strangeitems.config.StrangeConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrangeItemsClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("strangeitems/client");
    private static KeyBinding register_keybind(String translation, int key) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.strangeitems." + translation,
            InputUtil.Type.KEYSYM,
            key,
            "category.strangeitems.keys"
        ));
    }
    public static KeyBinding show_blocks_mined = register_keybind("show_blocks_mined", GLFW.GLFW_KEY_Z);
    public static KeyBinding show_times_dropped = register_keybind("show_times_dropped", GLFW.GLFW_KEY_RIGHT_ALT);
    public static KeyBinding show_mobs_killed = register_keybind("show_mobs_killed", GLFW.GLFW_KEY_LEFT_ALT);
    @Override
    public void onInitializeClient() {
        StrangeConfig.read_json();
    }
}
