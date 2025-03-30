package embin.strangeitems.tracker;

import embin.strangeitems.StrangeItemsComponents;
import embin.strangeitems.StrangeRegistries;
import embin.strangeitems.util.ConvertNamespace;
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
import java.util.function.Consumer;

/**
 * Base Tracker class
 */
public class Tracker {
    public String id;

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
     * The item tag that controls whether an item should have a certain tracker.
     */
    public TagKey<Item> item_tag;

    public Tracker(String id, TagKey<Item> tag, StatFormatter stat_formatter, int formatted_value_multiplier) {
        this.stat_formatter = stat_formatter;
        this.formatted_value_multiplier = formatted_value_multiplier;
        this.item_tag = tag;
        this.id = ConvertNamespace.convert(id).toString();
    }
    public Tracker(String id, TagKey<Item> tag) {
        this.item_tag = tag;
        this.id = ConvertNamespace.convert(id).toString();
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
        return this.get_id().toString();
    }

    public String to_string() {
        return this.get_id().toString();
    }

    public Identifier get_id() {
        Identifier id = StrangeRegistries.TRACKER.getId(this);
        if (id != null) return id;
        return ConvertNamespace.convert(this.id);
    }

    public String get_translation_key() {
        return Util.createTranslationKey("tracker", this.get_id());
    }

    /**
     * Checks if the given stack has any tracker data on it.
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
        return stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getInt(this.toString()).orElse(0);
    }

    public NbtCompound get_tracker_value_nbt(ItemStack stack) {
        if (!this.stack_has_tracker(stack)) {
            return new NbtCompound();
        }
        return stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getCompound(this.toString()).orElse(new NbtCompound());
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

    public void append_tooltip(ItemStack stack, Consumer<Text> tooltip) {
        if (this.should_track(stack)) {
            Text stat_text = Text.literal(this.get_formatted_tracker_value(stack)).formatted(Formatting.YELLOW);
            Text tooltip_text = Text.translatable(this.get_translation_key()).append(": ").formatted(Formatting.GRAY);
            tooltip.accept(Text.literal(" ").append(tooltip_text).append(stat_text));
        }
    }

    public void append_tooltip_no_space(ItemStack stack, Consumer<Text> tooltip) {
        if (this.should_track(stack)) {
            Text stat_text = Text.literal(this.get_formatted_tracker_value(stack)).formatted(Formatting.YELLOW);
            MutableText tooltip_text = Text.translatable(this.get_translation_key()).append(": ").formatted(Formatting.GRAY);
            tooltip.accept(tooltip_text.append(stat_text));
        }
    }

    @Deprecated
    public void convert_legacy_tracker(ItemStack stack, ComponentType<Integer> legacy_component, boolean rarity_fix) {
        if (stack.contains(legacy_component)) {
            if (rarity_fix) {
                stack.set(DataComponentTypes.RARITY, stack.getItem().getComponents().get(DataComponentTypes.RARITY));
            }
            int legacy_data = stack.getOrDefault(legacy_component, 0);
            this.set_tracker_value_int(stack, this.get_tracker_value_int(stack) + legacy_data);
            stack.remove(legacy_component);
        }
    }

    /**
     * Converts the data of a specified legacy tracker component to the new data format, if the specified stack has legacy data.
     * @param stack Item stack to check for.
     * @param legacy_component The tracker component to convert.
     */
    @Deprecated
    public void convert_legacy_tracker(ItemStack stack, ComponentType<Integer> legacy_component) {
        this.convert_legacy_tracker(stack, legacy_component, false);
    }

    public boolean should_track(ItemStack stack) {
        return stack.isIn(this.item_tag) || this.stack_has_tracker(stack) || stack.contains(StrangeItemsComponents.HAS_ALL_TRACKERS);
    }

    public boolean is_in(TagKey<Tracker> tag) {
        return StrangeRegistries.TRACKER.getEntry(this).isIn(tag);
    }
}
