package embin.strangeitems.util;

import embin.strangeitems.StrangeItemsComponents;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.StatFormatter;
import net.minecraft.text.Text;

import java.util.List;

public class ComponentTracker {
    public void appendTrackerTooltip(ItemStack stack, List<Text> tooltip, ComponentType component, String string) {
        if (stack.contains(component)) {
            int count = stack.getOrDefault(component, 0);
            String stg = string + StatFormatter.DEFAULT.format(count);
            if (component == StrangeItemsComponents.TIME_FLOWN_WITH_ELYTRA) {
                stg = string + StatFormatter.TIME.format(count * 20);
            }
            tooltip.add(Text.literal(stg).withColor(13593138));
        }
    }

    public void appendTracker(ItemStack stack, ComponentType component) {
        if (component != null) {
            if (stack.contains(component)) {
                int count = stack.getOrDefault(component, 0);
                stack.set(component, ++count);
            }
        }
    }
}
