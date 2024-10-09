package embin.strangeitems.util;

import embin.strangeitems.mixin.KeyBindAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.nbt.NbtCompound;
import java.util.List;

public class TrackerUtil {

    public static List<String> get_sorted_keys(NbtCompound nbtCompound) {
        List<String> sorted = new java.util.ArrayList<>(List.of());
        List<String> unsorted = nbtCompound.getKeys().stream().toList();
        for (String key : unsorted) {
           sorted.add(key);
           int value = nbtCompound.getInt(key);
           if (sorted.size() > 1) {
               while (true) {
                   int index = sorted.indexOf(key);
                   String key_ahead;
                   try {
                       key_ahead = sorted.get(index - 1);
                   } catch (IndexOutOfBoundsException e) {
                       break;
                   }
                   int value_ahead = nbtCompound.getInt(key_ahead);
                   if (value > value_ahead) {
                       sorted.remove(index);
                       sorted.add(index - 1, key);
                   } else {
                       break;
                   }
               }
           }
        }
        return sorted;
    }

    public static boolean is_key_down(KeyBinding key) {
        long handle = MinecraftClient.getInstance().getWindow().getHandle();
        int key_code = ((KeyBindAccessor)key).getBoundKey().getCode();
        return InputUtil.isKeyPressed(handle, key_code);
    }
}
