package embin.strangeitems.util;

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
}
