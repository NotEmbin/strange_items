package embin.strangeitems.util;

import embin.strangeitems.StrangeItemsComponents;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.StatFormatter;
import net.minecraft.text.Text;
import net.minecraft.util.*;

import java.util.List;

@Deprecated
public class ComponentTracker {
    public void appendTrackerTooltip(ItemStack stack, List<Text> tooltip, ComponentType<Integer> component) {
        if (stack.contains(component)) {
            int count = stack.getOrDefault(component, 0);
            String stat = StatFormatter.DEFAULT.format(count);
            if (component == StrangeItemsComponents.TIME_FLOWN_WITH_ELYTRA) {
                stat = StatFormatter.TIME.format(count * 20);
            }
            String component_translate_key = Util.createTranslationKey("component", Identifier.of(component.toString()));
            Text stat_text = Text.literal(stat).formatted(Formatting.YELLOW);
            Text tooltip_text = Text.translatable(component_translate_key).append(": ").formatted(Formatting.GRAY);
            tooltip.add(Text.literal(" ").append(tooltip_text).append(stat_text));
        }
    }

    public void appendTracker(ItemStack stack, ComponentType<Integer> component) {
        if (component != null) {
            if (stack.contains(component)) {
                int count = stack.getOrDefault(component, 0);
                stack.set(component, ++count);
            }
        }
    }

    public static ItemStack applyDefaultTrackers(ItemStack stack) {
        stack.set(StrangeItemsComponents.BLOCKS_MINED, 0);
        stack.set(StrangeItemsComponents.TIMES_DROPPED, 0);
        stack.set(StrangeItemsComponents.MOBS_HIT, 0);
        return stack;
    }

    public static ItemStack applyDefaultAxeTrackers(ItemStack stack) {
        applyDefaultTrackers(stack);
        stack.set(StrangeItemsComponents.LOGS_STRIPPED, 0);
        return stack;
    }

    public static ItemStack applyDefaultShovelTrackers(ItemStack stack) {
        applyDefaultTrackers(stack);
        stack.set(StrangeItemsComponents.PATHS_CREATED, 0);
        stack.set(StrangeItemsComponents.CAMPFIRES_PUT_OUT, 0);
        return stack;
    }

    public static ItemStack applyDefaultHoeTrackers(ItemStack stack) {
        applyDefaultTrackers(stack);
        stack.set(StrangeItemsComponents.FARMLAND_CREATED, 0);
        return stack;
    }

    public static ItemStack applyDefaultElytraTrackers(ItemStack stack) {
        applyDefaultTrackers(stack);
        stack.set(StrangeItemsComponents.TIME_FLOWN_WITH_ELYTRA, 0);
        return stack;
    }

    public static ItemStack applyDefaultBowTrackers(ItemStack stack) {
        stack.set(StrangeItemsComponents.SHOTS_FIRED, 0);
        stack.set(StrangeItemsComponents.SHOT_HIT, 0);
        stack.set(StrangeItemsComponents.BLOCKS_MINED, 0);
        stack.set(StrangeItemsComponents.TIMES_DROPPED, 0);
        return stack;
    }

    public static ItemStack applyDefaultMaceTrackers(ItemStack stack) {
        stack.set(StrangeItemsComponents.BLOCKS_MINED, 0);
        stack.set(StrangeItemsComponents.TIMES_DROPPED, 0);
        stack.set(StrangeItemsComponents.MOBS_HIT, 0);
        return stack;
    }

    public static ItemStack applyDefaultShearsTrackers(ItemStack stack) {
        applyDefaultTrackers(stack);
        stack.set(StrangeItemsComponents.SHEEP_SHEARED, 0);
        stack.set(StrangeItemsComponents.PLANTS_TRIMMED, 0);
        return stack;
    }

    public static ItemStack applyDefaultFlintAndSteelTrackers(ItemStack stack) {
        applyDefaultTrackers(stack);
        stack.set(StrangeItemsComponents.FIRES_IGNITED, 0);
        stack.set(StrangeItemsComponents.CAMPFIRES_LIT, 0);
        return stack;
    }
}
