package embin.strangeitems.tracker;

import embin.strangeitems.client.StrangeItemsClient;
import embin.strangeitems.client.config.StrangeConfig;
import embin.strangeitems.util.TrackerUtil;
import net.minecraft.client.option.KeyBinding;
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
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

public class MapTracker extends Tracker {
    public String map_id;
    public String translation_prefix;

    /**
     * Maximum number of entries that can be shown for in-depth trackers.
     * Ignored if certain conditions are met.
     * @see TrackerUtil#isTooltipScrollInstalled()
     */
    public int max_maps_shown = 8;

    public MapTracker(String id, String translate_prefix, TagKey<Item> tag, StatFormatter stat_formatter) {
        super(id, tag);
        this.map_id = this.getId().toString() + "_map";
        this.translation_prefix = translate_prefix;
        this.stat_formatter = stat_formatter;
    }

    public MapTracker(String id, String translate_prefix, TagKey<Item> tag) {
        super(id, tag);
        this.map_id = this.getId().toString() + "_map";
        this.translation_prefix = translate_prefix;
    }

    public MapTracker(String id, String translate_prefix) {
        super(id, TrackerItemTags.CAN_TRACK_STATS);
        this.map_id = this.getId().toString() + "_map";
        this.translation_prefix = translate_prefix;
    }

    public void appendTracker(ItemStack stack, String key) {
        super.appendTracker(stack, 1);
        if (this.shouldTrack(stack) || this.stackHasTracker(stack)) {
            if (StrangeConfig.in_depth_tracking) {
                int tracker_count = this.getTrackerValueNbt(stack).getInt(key).orElse(0) + 1;
                NbtCompound nbt = this.getTrackerValueNbt(stack).copy();
                nbt.putInt(key, tracker_count);
                this.setTrackerValueNbt(stack, nbt);
            }
        }
    }

    @Override
    public void setTrackerValueNbt(ItemStack stack, NbtElement value) {
        stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(currentnbt -> currentnbt.put(this.map_id, value)));
    }

    @Override
    public NbtCompound getTrackerValueNbt(ItemStack stack) {
        if (!this.stackHasTracker(stack)) {
            return new NbtCompound();
        }
        return stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getCompound(this.map_id).orElse(new NbtCompound());
    }

    public void appendTooltipMap(ItemStack stack, Consumer<Text> tooltip, CallbackInfo ci, TooltipType type) {
        if (this.shouldTrack(stack) && shouldShowTooltip(stack)) {
            this.appendTooltipNoSpace(stack, tooltip, type);
            NbtCompound nbtCompound = this.getTrackerValueNbt(stack);
            int index = 1;
            for (String key : TrackerUtil.getSortedKeys(nbtCompound)) {
                if (index <= this.max_maps_shown || TrackerUtil.isTooltipScrollInstalled()) {
                    String translation_key = Identifier.of(key).toTranslationKey(this.translation_prefix);
                    Text stat_text = Text.literal(this.getFormattedTrackerValueNbt(stack, key)).formatted(Formatting.YELLOW);
                    MutableText tooltip_text = Text.literal(key);
                    if (Language.getInstance().hasTranslation(translation_key) && !TrackerUtil.isKeyDown(StrangeItemsClient.show_tracker_ids)) {
                        tooltip_text = Text.translatable(translation_key).formatted(Formatting.GRAY);
                    }
                    if (TrackerUtil.isKeyDown(StrangeItemsClient.show_tracker_ids)) {
                        tooltip_text.formatted(Formatting.DARK_GRAY);
                    }
                    tooltip_text.append(Text.literal(": ").formatted(Formatting.GRAY));
                    tooltip.accept(Text.literal(" ").append(tooltip_text).append(stat_text));
                }
                index++;
            }
            if (index > (this.max_maps_shown + 1) && !TrackerUtil.isTooltipScrollInstalled()) {
                tooltip.accept(Text.translatable("tooltip.strangeitems.map_cutoff", index - (this.max_maps_shown + 1)).formatted(Formatting.ITALIC));
            }
            TrackerUtil.addItemIdToTooltip(stack, tooltip, type);
            ci.cancel();
        }
    }

    public String getFormattedTrackerValueNbt(ItemStack stack, String key) {
        return this.getStatFormatter().format(this.getTrackerValueNbt(stack).getInt(key).orElse(0) * this.formatted_value_multiplier);
    }

    public boolean shouldShowTooltip(ItemStack stack) {
        return this.stackHasTracker(stack) && TrackerUtil.isKeyDown(this.getKeybinding()) && StrangeConfig.in_depth_tracking && this.stackHasMapTracker(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, Consumer<Text> tooltip) {
        if (this.shouldTrack(stack)) {
            if (this.stackHasTracker(stack) && StrangeConfig.in_depth_tracking && this.stackHasMapTracker(stack)) {
                Text stat_text = Text.literal(this.getFormattedTrackerValue(stack)).formatted(Formatting.YELLOW);
                Text tooltip_text = this.getNameForTooltip().append(Text.literal(": ").formatted(Formatting.GRAY));
                Text control_text = Text.literal(" [").append(this.getKeybinding().getBoundKeyLocalizedText()).append("]").formatted(Formatting.DARK_GRAY, Formatting.ITALIC);
                tooltip.accept(Text.literal(" ").append(tooltip_text).append(stat_text).append(control_text));
            } else {
                super.appendTooltip(stack, tooltip);
            }
        }
    }

    public boolean stackHasMapTracker(ItemStack stack) {
        return stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().contains(this.map_id);
    }

    public KeyBinding getKeybinding() {
        return TrackerKeybindings.get_map_keybind(this);
    }
}
