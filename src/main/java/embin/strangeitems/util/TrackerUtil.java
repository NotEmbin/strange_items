package embin.strangeitems.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.stat.StatFormatter;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

import java.util.List;
import java.util.Objects;

public class TrackerUtil {
    public static final TrackerUtil instance = new TrackerUtil();
    private static final ConvertNamespace cn = new ConvertNamespace();
    public void appendTooltip(ItemStack stack, List<Text> tooltip, String tracker) {
        String tracker_id = cn.convertNamespace(tracker).toString();
        if (stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().contains(tracker_id)) {
            NbtCompound stacknbt = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt();
            int tracker_value = stacknbt.getInt(tracker_id);

            String stat = StatFormatter.DEFAULT.format(tracker_value);
            if (Objects.equals(tracker_id, "strangeitems:time_flown_with_elytra")) {
                stat = StatFormatter.TIME.format(tracker_value * 20);
            }
            String component_translate_key = Util.createTranslationKey("tracker", cn.convertNamespace(tracker));
            Text stat_text = Text.literal(stat).formatted(Formatting.YELLOW);
            Text tooltip_text = Text.translatable(component_translate_key).append(": ").formatted(Formatting.GRAY);
            tooltip.add(Text.literal(" ").append(tooltip_text).append(stat_text));
        }
    }

    public void tracker(ItemStack stack, String tracker) {
        int tracker_count;
        NbtComponent itemnbt = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
        if (itemnbt.contains(cn.convertNamespace(tracker).toString())) {
            tracker_count = itemnbt.copyNbt().getInt(cn.convertNamespace(tracker).toString());
        } else {
            tracker_count = 0;
        }
        stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(currentnbt -> currentnbt.putInt(tracker, tracker_count + 1)));
    }
}
