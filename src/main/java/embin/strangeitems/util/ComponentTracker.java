package embin.strangeitems.util;

import embin.strangeitems.StrangeItemsComponents;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.StatFormatter;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;

import java.util.List;

public class ComponentTracker {
    public void appendTrackerTooltip(ItemStack stack, List<Text> tooltip, ComponentType component, String string) {
        if (stack.contains(component)) {
            int count = stack.getOrDefault(component, 0);
            String stg = " " + string + StatFormatter.DEFAULT.format(count);
            if (component == StrangeItemsComponents.TIME_FLOWN_WITH_ELYTRA) {
                stg = " " + string + StatFormatter.TIME.format(count * 20);
            }
            //tooltip.add(Text.literal(stg).withColor(13593138));
            tooltip.add(Text.literal(stg).formatted(Formatting.GRAY));
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

    public static ItemStack applyDefaultTrackers(ItemStack stack) {
        stack.set(StrangeItemsComponents.BLOCKS_MINED, 0);
        stack.set(StrangeItemsComponents.TIMES_DROPPED, 0);
        stack.set(StrangeItemsComponents.MOBS_HIT, 0);
        stack.set(DataComponentTypes.RARITY, Rarity.RARE);
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
        stack.set(StrangeItemsComponents.TIME_FLOWN_WITH_ELYTRA, 0);
        stack.set(StrangeItemsComponents.TIMES_DROPPED, 0);
        stack.set(DataComponentTypes.RARITY, Rarity.RARE);
        return stack;
    }

    public static ItemStack applyDefaultBowTrackers(ItemStack stack) {
        stack.set(StrangeItemsComponents.SHOTS_FIRED, 0);
        stack.set(StrangeItemsComponents.SHOT_HIT, 0);
        stack.set(StrangeItemsComponents.BLOCKS_MINED, 0);
        stack.set(StrangeItemsComponents.TIMES_DROPPED, 0);
        stack.set(DataComponentTypes.RARITY, Rarity.RARE);
        return stack;
    }

    public static ItemStack applyDefaultMaceTrackers(ItemStack stack) {
        stack.set(StrangeItemsComponents.BLOCKS_MINED, 0);
        stack.set(StrangeItemsComponents.TIMES_DROPPED, 0);
        stack.set(StrangeItemsComponents.MOBS_HIT, 0);
        return stack;
    }
}
