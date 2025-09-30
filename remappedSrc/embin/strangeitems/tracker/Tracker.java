package embin.strangeitems.tracker;

import embin.strangeitems.StrangeItemsComponents;
import embin.strangeitems.StrangeRegistries;
import embin.strangeitems.client.StrangeItemsClient;
import embin.strangeitems.util.Id;
import embin.strangeitems.util.TrackerUtil;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.stat.StatFormatter;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * Base Tracker class
 */
public class Tracker {
    private static final Logger LOGGER = LoggerFactory.getLogger(Tracker.class);
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
        this.id = Id.of(id).toString();
    }
    public Tracker(String id, TagKey<Item> tag) {
        this.item_tag = tag;
        this.id = Id.of(id).toString();
    }

    public void setTrackerValueInt(ItemStack stack, int value) {
        stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(currentnbt -> currentnbt.putInt(this.toString(), value)));
    }

    public void setTrackerValueNbt(ItemStack stack, NbtElement value) {
        stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(currentnbt -> currentnbt.put(this.toString(), value)));
    }

    public StatFormatter getStatFormatter() {
        return this.stat_formatter;
    }

    public String toString() {
        return this.getId().toString();
    }

    @Deprecated(forRemoval = true)
    public String to_string() {
        return this.toString();
    }

    public Identifier getId() {
        Identifier id = StrangeRegistries.TRACKER.getId(this);
        if (id != null) return id;
        return Id.of(this.id);
    }

    public String getTranslationKey() {
        return Util.createTranslationKey("tracker", this.getId());
    }

    /**
     * Checks if the given stack has any tracker data on it.
     * @param stack The item stack to check for.
     * @return <code>true</code> if the stack has the tracker;
     * <code>false</code> if it doesn't
     */
    public boolean stackHasTracker(ItemStack stack) {
        return stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().contains(this.toString());
    }

    public int getTrackerValueInt(ItemStack stack) {
        if (!this.stackHasTracker(stack)) {
            return this.default_value;
        }
        return stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getInt(this.toString()).orElse(0);
    }

    public NbtCompound getTrackerValueNbt(ItemStack stack) {
        if (!this.stackHasTracker(stack)) {
            return new NbtCompound();
        }
        return stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getCompound(this.toString()).orElse(new NbtCompound());
    }

    /**
     * Increments the tracker for an item stack by a specified amount when called.
     * @param stack Item stack to increment the tracker on.
     * @param add_amount Amount to add.
     */
    public void appendTracker(ItemStack stack, int add_amount) {
        if (this.shouldTrack(stack)) {
            int tracker_count = this.getTrackerValueInt(stack) + add_amount;
            this.setTrackerValueInt(stack, tracker_count);
        }
    }

    /**
     * Increments the tracker for an item stack by 1 when called.
     * @param stack Item stack to increment the tracker on.
     */
    public void appendTracker(ItemStack stack) {
        this.appendTracker(stack, 1);
    }

    public String getFormattedTrackerValue(ItemStack stack) {
        return this.getStatFormatter().format(this.getTrackerValueInt(stack) * this.formatted_value_multiplier);
    }

    public void appendTooltip(ItemStack stack, Consumer<Text> tooltip) {
        if (this.shouldTrack(stack)) {
            Text stat_text = Text.literal(this.getFormattedTrackerValue(stack)).formatted(Formatting.YELLOW);
            Text tooltip_text = this.getNameForTooltip().append(Text.literal(": ").formatted(Formatting.GRAY));
            tooltip.accept(Text.literal(" ").append(tooltip_text).append(stat_text));
        }
    }

    protected MutableText getNameForTooltip() {
        if (TrackerUtil.isKeyDown(StrangeItemsClient.show_tracker_ids)) {
            Identifier id = StrangeRegistries.TRACKER.getId(this);
            if (id != null) {
                return Text.literal(id.toString()).formatted(Formatting.DARK_GRAY);
            } else {
                return Text.translatable(this.getTranslationKey()).formatted(Formatting.GRAY);
            }
        } else {
            return Text.translatable(this.getTranslationKey()).formatted(Formatting.GRAY);
        }
    }

    public void appendTooltipNoSpace(ItemStack stack, Consumer<Text> tooltip, TooltipType type) {
        if (this.shouldTrack(stack)) {
            Text stat_text = Text.literal(this.getFormattedTrackerValue(stack)).formatted(Formatting.YELLOW);
            MutableText tooltip_text = Text.translatable(this.getTranslationKey()).append(": ").formatted(Formatting.GRAY);
            //MutableText tooltip_text = this.get_name_for_tooltip().append(Text.literal(": ").formatted(Formatting.GRAY));
            tooltip.accept(tooltip_text.append(stat_text));
        }
    }

    @Deprecated(forRemoval = true)
    public void convert_legacy_tracker(ItemStack stack, ComponentType<Integer> legacy_component, boolean rarity_fix) {
        if (stack.contains(legacy_component)) {
            if (rarity_fix) {
                stack.set(DataComponentTypes.RARITY, stack.getItem().getComponents().get(DataComponentTypes.RARITY));
            }
            int legacy_data = stack.getOrDefault(legacy_component, 0);
            this.setTrackerValueInt(stack, this.getTrackerValueInt(stack) + legacy_data);
            stack.remove(legacy_component);
        }
    }

    /**
     * Converts the data of a specified legacy tracker component to the new data format, if the specified stack has legacy data.
     * @param stack Item stack to check for.
     * @param legacy_component The tracker component to convert.
     */
    @Deprecated(forRemoval = true)
    public void convert_legacy_tracker(ItemStack stack, ComponentType<Integer> legacy_component) {
        this.convert_legacy_tracker(stack, legacy_component, false);
    }

    public boolean shouldTrack(ItemStack stack) {
        return stack.isIn(this.item_tag) || this.stackHasTracker(stack) || stack.contains(StrangeItemsComponents.HAS_ALL_TRACKERS);
    }

    public boolean isIn(TagKey<Tracker> tag) {
        return StrangeRegistries.TRACKER.getEntry(this).isIn(tag);
    }
}
