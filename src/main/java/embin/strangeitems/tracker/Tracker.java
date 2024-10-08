package embin.strangeitems.tracker;

import embin.strangeitems.StrangeItems;
import embin.strangeitems.config.StrangeConfig;
import embin.strangeitems.util.TrackerUtil;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.stat.StatFormatter;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class Tracker {
    public Identifier id;
    public StatFormatter stat_formatter= StatFormatter.DEFAULT;
    public int formatted_value_multiplier = 1;
    public int default_value = 0;
    public int max_maps_shown = 8;
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

    public void append_tracker_nbt(ItemStack stack, String key, int add_amount) {
        if (stack.isIn(this.item_tag)) {
            if (StrangeConfig.in_depth_tracking) {
                int tracker_count = this.get_tracker_value_nbt(stack).getInt(key) + add_amount;
                NbtCompound nbt = this.get_tracker_value_nbt(stack).copy();
                nbt.putInt(key, tracker_count);
                this.set_tracker_value_nbt(stack, nbt);
            }
        }
    }

    public void append_tracker_time(ItemStack stack, Tracker inherited_tracker) {
        if (stack.isIn(this.item_tag)) {
            if (StrangeConfig.in_depth_tracking) {
                int base_value = inherited_tracker.get_tracker_value_int(stack);
                NbtCompound nbt = this.get_tracker_value_nbt(stack).copy();
                nbt.putLong(String.valueOf(base_value), Instant.now().getEpochSecond());
                this.set_tracker_value_nbt(stack, nbt);
            }
        }
    }

    public void append_tracker(ItemStack stack, int add_amount) {
        if (stack.isIn(this.item_tag)) {
            int tracker_count = this.get_tracker_value_int(stack) + add_amount;
            this.set_tracker_value_int(stack, tracker_count);
        }
    }

    public void append_tracker(ItemStack stack) {
        this.append_tracker(stack, 1);
    }

    public String get_formatted_tracker_value(ItemStack stack) {
        return this.get_stat_formatter().format(this.get_tracker_value_int(stack) * this.formatted_value_multiplier);
    }

    public String get_formatted_tracker_value_nbt(ItemStack stack, String key) {
        return this.get_stat_formatter().format(this.get_tracker_value_nbt(stack).getInt(key) * this.formatted_value_multiplier);
    }

    public void append_tooltip(ItemStack stack, List<Text> tooltip) {
        if (stack.isIn(this.item_tag)) {
            Text stat_text = Text.literal(this.get_formatted_tracker_value(stack)).formatted(Formatting.YELLOW);
            Text tooltip_text = Text.translatable(this.get_translation_key()).append(": ").formatted(Formatting.GRAY);
            tooltip.add(Text.literal(" ").append(tooltip_text).append(stat_text));
        }
    }

    public void append_tooltip(ItemStack stack, List<Text> tooltip, String control) {
        if (stack.isIn(this.item_tag)) {
            if (this.stack_has_tracker(stack) && StrangeConfig.in_depth_tracking) {
                Text stat_text = Text.literal(this.get_formatted_tracker_value(stack)).formatted(Formatting.YELLOW);
                Text tooltip_text = Text.translatable(this.get_translation_key()).append(": ").formatted(Formatting.GRAY);
                Text control_text = Text.literal(" [" + control + "]").formatted(Formatting.DARK_GRAY, Formatting.ITALIC);
                tooltip.add(Text.literal(" ").append(tooltip_text).append(stat_text).append(control_text));
            } else {
                this.append_tooltip(stack, tooltip);
            }
        }
    }

    public void append_tooltip_no_space(ItemStack stack, List<Text> tooltip) {
        if (stack.isIn(this.item_tag)) {
            Text stat_text = Text.literal(this.get_formatted_tracker_value(stack)).formatted(Formatting.YELLOW);
            MutableText tooltip_text = Text.translatable(this.get_translation_key()).append(": ").formatted(Formatting.GRAY);
            tooltip.add(tooltip_text.append(stat_text));
        }
    }

    public void append_map_tooltip(ItemStack stack, List<Text> tooltip, String translate_prefix) {
        if (stack.isIn(this.item_tag)) {
            NbtCompound nbtCompound = this.get_tracker_value_nbt(stack);
            int index = 1;
            for (String key : TrackerUtil.get_sorted_keys(nbtCompound)) {
                if (index <= this.max_maps_shown || is_tooltip_scroll_installed()) {
                    Text stat_text = Text.literal(this.get_formatted_tracker_value_nbt(stack, key)).formatted(Formatting.YELLOW);
                    MutableText tooltip_text = Text.translatable(Identifier.of(key).toTranslationKey(translate_prefix)).append(": ").formatted(Formatting.GRAY);
                    tooltip.add(Text.literal(" ").append(tooltip_text).append(stat_text));
                }
                index++;
            }
            if (index > (this.max_maps_shown + 1) && !is_tooltip_scroll_installed()) {
                tooltip.add(Text.translatable("tooltip.strangeitems.map_cutoff", index - (this.max_maps_shown + 1)));
            }
        }
    }

    public void append_time_map_tooltip(ItemStack stack, List<Text> tooltip, Tracker inherited_tracker) {
        if (stack.isIn(this.item_tag)) {
            int size = inherited_tracker.get_tracker_value_int(stack) - 1;
            for (int i = size; i >= 0; i--) {
                String key = String.valueOf(i + 1);
                if ((size - this.max_maps_shown) <= i || is_tooltip_scroll_installed()) {
                    Text stat_text = Text.translatable("tooltip.strangeitems.unknown_value").formatted(Formatting.DARK_GRAY);
                    if (this.get_tracker_value_nbt(stack).contains(key)) {
                        long tracker_value = this.get_tracker_value_nbt(stack).getLong(key);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
                        Instant time = Instant.ofEpochSecond(tracker_value);
                        stat_text = Text.literal(format.format(Date.from(time))).formatted(Formatting.GRAY);
                    }
                    MutableText tooltip_text = Text.literal(key).append(": ").formatted(Formatting.YELLOW);
                    tooltip.add(Text.literal(" ").append(tooltip_text).append(stat_text));
                }
            }
            if (size >= (this.max_maps_shown + 1) && !is_tooltip_scroll_installed()) {
                tooltip.add(Text.translatable("tooltip.strangeitems.map_cutoff", size - (this.max_maps_shown + 1)));
            }
        }
    }

    public void convert_legacy_tracker(ItemStack stack, ComponentType<Integer> legacy_component) {
        if (stack.contains(legacy_component)) {
            int legacy_data = stack.getOrDefault(legacy_component, 0);
            this.set_tracker_value_int(stack, legacy_data);
            stack.remove(legacy_component);
        }
    }

    public boolean is_tooltip_scroll_installed() {
        boolean result = false;
        if (StrangeConfig.check_for_tooltipscroll) {
            result = StrangeItems.tooltipscroll_installed;
        }
        if (StrangeConfig.invert_tooltipscroll_check_value) {
            return !result;
        }
        return result;
    }
}
