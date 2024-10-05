package embin.strangeitems.tracker;

import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.stat.StatFormatter;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.List;

public class Tracker {
    public Identifier id;
    public StatFormatter stat_formatter;
    public int formatted_value_multiplier;
    public int default_value = 0;
    public TagKey<Item> item_tag;
    public Tracker(Identifier id, TagKey<Item> tag) {
        this.id = id;
        this.stat_formatter = StatFormatter.DEFAULT;
        this.formatted_value_multiplier = 1;
        this.item_tag = tag;
    }
    public Tracker(Identifier id, TagKey<Item> tag, StatFormatter stat_formatter, int formatted_value_multiplier) {
        this.id = id;
        this.stat_formatter = stat_formatter;
        this.formatted_value_multiplier = formatted_value_multiplier;
        this.item_tag = tag;
    }

    public void set_tracker_value(ItemStack stack, int value) {
        stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(currentnbt -> currentnbt.putInt(this.to_string(), value)));
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

    public int get_tracker_value(ItemStack stack) {
        if (!this.stack_has_tracker(stack)) {
            return this.default_value;
        }
        return stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getInt(this.toString());
    }

    public void append_tracker(ItemStack stack, int add_amount) {
        if (stack.isIn(this.item_tag)) {
            int tracker_count = this.get_tracker_value(stack) + add_amount;
            this.set_tracker_value(stack, tracker_count);
        }
    }

    public void append_tracker(ItemStack stack) {
        this.append_tracker(stack, 1);
    }

    public String get_formatted_tracker_value(ItemStack stack) {
        return this.get_stat_formatter().format(this.get_tracker_value(stack) * this.formatted_value_multiplier);
    }

    public void append_tooltip(ItemStack stack, List<Text> tooltip) {
        if (stack.isIn(this.item_tag)) {
            Text stat_text = Text.literal(this.get_formatted_tracker_value(stack)).formatted(Formatting.YELLOW);
            Text tooltip_text = Text.translatable(this.get_translation_key()).append(": ").formatted(Formatting.GRAY);
            tooltip.add(Text.literal(" ").append(tooltip_text).append(stat_text));
        }
    }

    public void convert_legacy_tracker(ItemStack stack, ComponentType<Integer> legacy_component) {
        if (stack.contains(legacy_component)) {
            int legacy_data = stack.getOrDefault(legacy_component, 0);
            this.set_tracker_value(stack, legacy_data);
            stack.remove(legacy_component);
        }
    }

}
