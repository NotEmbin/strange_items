package embin.strangeitems.tracker;

import embin.strangeitems.StrangeItems;
import embin.strangeitems.StrangeItemsComponents;
import embin.strangeitems.config.StrangeConfig;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.stat.StatFormatter;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import java.util.List;

/**
 * Base Tracker class
 */
public class Tracker {
    public Identifier id;

    /**
     * The StatFormatter used when displaying a tracker's value in its tooltip.
     * @see StatFormatter
     */
    public StatFormatter stat_formatter = StatFormatter.DEFAULT;

    /**
     * The multiplier applied to the value shown on the tracker tooltip.
     */
    public int formatted_value_multiplier = 1;
    public int default_value = 0;

    /**
     * Maximum number of entries that can be shown for in-depth trackers.
     * Ignored if certain conditions are met.
     * @see Tracker#is_tooltip_scroll_installed()
     */
    public int max_maps_shown = 8;

    /**
     * The item tag that controls whether an item should have a certain tracker.
     */
    public TagKey<Item> item_tag = TrackerTags.CAN_TRACK_STATS;

    public Tracker(Identifier id, TagKey<Item> tag, StatFormatter stat_formatter, int formatted_value_multiplier) {
        this.id = id;
        this.stat_formatter = stat_formatter;
        this.formatted_value_multiplier = formatted_value_multiplier;
        this.item_tag = tag;
    }
    public Tracker(Identifier id, TagKey<Item> tag) {
        this.id = id;
        this.item_tag = tag;
    }
    public Tracker(Identifier id) {
        this.id = id;
    }

    public void set_tracker_value_int(ItemStack stack, int value) {
        stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(currentnbt -> currentnbt.putInt(this.to_string(), value)));
    }

    public void set_tracker_value_nbt(ItemStack stack, NbtElement value) {
        stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(currentnbt -> currentnbt.put(this.to_string(), value)));
    }

    public StatFormatter get_stat_formatter() {
        return this.stat_formatter;
    }

    public String toString() {
        return this.id.toString();
    }

    public String to_string() {
        return this.id.toString();
    }

    public Identifier get_id() {
        return this.id;
    }

    public String get_translation_key() {
        return Util.createTranslationKey("tracker", this.id);
    }

    /**
     * @param stack The item stack to check for.
     * @return <code>true</code> if the stack has the tracker;
     * <code>false</code> if it doesn't
     */
    public boolean stack_has_tracker(ItemStack stack) {
        return stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).contains(this.toString());
    }

    public int get_tracker_value_int(ItemStack stack) {
        if (!this.stack_has_tracker(stack)) {
            return this.default_value;
        }
        return stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getInt(this.toString());
    }

    public NbtCompound get_tracker_value_nbt(ItemStack stack) {
        if (!this.stack_has_tracker(stack)) {
            return new NbtCompound();
        }
        return stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getCompound(this.toString());
    }

    /**
     * Increments the tracker for an item stack by a specified amount when called.
     * @param stack Item stack to increment the tracker on.
     * @param add_amount Amount to add.
     */
    public void append_tracker(ItemStack stack, int add_amount) {
        if (this.should_track(stack)) {
            int tracker_count = this.get_tracker_value_int(stack) + add_amount;
            this.set_tracker_value_int(stack, tracker_count);
        }
    }

    /**
     * Increments the tracker for an item stack by 1 when called.
     * @param stack Item stack to increment the tracker on.
     */
    public void append_tracker(ItemStack stack) {
        this.append_tracker(stack, 1);
    }

    public String get_formatted_tracker_value(ItemStack stack) {
        return this.get_stat_formatter().format(this.get_tracker_value_int(stack) * this.formatted_value_multiplier);
    }

    public void append_tooltip(ItemStack stack, List<Text> tooltip) {
        if (this.should_track(stack)) {
            Text stat_text = Text.literal(this.get_formatted_tracker_value(stack)).formatted(Formatting.YELLOW);
            Text tooltip_text = Text.translatable(this.get_translation_key()).append(": ").formatted(Formatting.GRAY);
            tooltip.add(Text.literal(" ").append(tooltip_text).append(stat_text));
        }
    }

    public void append_tooltip(ItemStack stack, List<Text> tooltip, Text control) {
        if (this.should_track(stack)) {
            if (this.stack_has_tracker(stack) && StrangeConfig.in_depth_tracking) {
                Text stat_text = Text.literal(this.get_formatted_tracker_value(stack)).formatted(Formatting.YELLOW);
                Text tooltip_text = Text.translatable(this.get_translation_key()).append(": ").formatted(Formatting.GRAY);
                Text control_text = Text.literal(" [").append(control).append("]").formatted(Formatting.DARK_GRAY, Formatting.ITALIC);
                tooltip.add(Text.literal(" ").append(tooltip_text).append(stat_text).append(control_text));
            } else {
                this.append_tooltip(stack, tooltip);
            }
        }
    }

    public void append_tooltip_no_space(ItemStack stack, List<Text> tooltip) {
        if (this.should_track(stack)) {
            Text stat_text = Text.literal(this.get_formatted_tracker_value(stack)).formatted(Formatting.YELLOW);
            MutableText tooltip_text = Text.translatable(this.get_translation_key()).append(": ").formatted(Formatting.GRAY);
            tooltip.add(tooltip_text.append(stat_text));
        }
    }

    public void convert_legacy_tracker(ItemStack stack, ComponentType<Integer> legacy_component) {
        if (stack.contains(legacy_component)) {
            int legacy_data = stack.getOrDefault(legacy_component, 0);
            this.set_tracker_value_int(stack, this.get_tracker_value_int(stack) + legacy_data);
            stack.remove(legacy_component);
        }
    }

    public static boolean is_tooltip_scroll_installed() {
        boolean result = false;
        if (StrangeConfig.check_for_tooltipscroll) {
            result = StrangeItems.tooltipscroll_installed;
        }
        if (StrangeConfig.invert_tooltipscroll_check_value) {
            return !result;
        }
        return result;
    }

    public boolean should_track(ItemStack stack) {
        return stack.isIn(this.item_tag) || this.stack_has_tracker(stack) || stack.contains(StrangeItemsComponents.HAS_ALL_TRACKERS);
    }
}
