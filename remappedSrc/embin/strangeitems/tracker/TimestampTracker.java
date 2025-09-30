package embin.strangeitems.tracker;

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
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.function.Consumer;

public class TimestampTracker extends Tracker {
    public String map_id;
    public int max_entries_shown = 8;
    public TimestampTracker(String id) {
        super(id, TrackerItemTags.CAN_TRACK_STATS);
        this.map_id = this.getId().toString() + "_map";
    }
    public TimestampTracker(String id, TagKey<Item> tag) {
        super(id, tag);
        this.map_id = this.getId().toString() + "_map";
    }

    @Override
    public void appendTracker(ItemStack stack) {
        super.appendTracker(stack, 1);
        if (this.shouldTrack(stack) || this.stackHasTracker(stack)) {
            if (StrangeConfig.in_depth_tracking) {
                int base_value = this.getTrackerValueInt(stack);
                NbtCompound nbt = this.getTrackerValueNbt(stack).copy();
                nbt.putLong(String.valueOf(base_value), Instant.now().getEpochSecond());
                this.setTrackerValueNbt(stack, nbt);
            }
        }
    }

    @Override
    public void setTrackerValueNbt(ItemStack stack, NbtElement value) {
        stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(currentnbt -> currentnbt.put(this.map_id, value)));
    }

    public void append_tooltip_map(ItemStack stack, Consumer<Text> tooltip, CallbackInfo ci, TooltipType type) {
        if (this.shouldTrack(stack) && should_show_tooltip(stack)) {
            this.appendTooltipNoSpace(stack, tooltip, type);
            int size = this.getTrackerValueInt(stack) - 1;
            for (int i = size; i >= 0; i--) {
                String key = String.valueOf(i + 1);
                if ((size - this.max_entries_shown) <= i || TrackerUtil.isTooltipScrollInstalled()) {
                    Text stat_text = Text.translatable("tooltip.strangeitems.unknown_value").formatted(Formatting.DARK_GRAY);
                    if (this.getTrackerValueNbt(stack).contains(key)) {
                        long tracker_value = this.getTrackerValueNbt(stack).getLong(key).orElse(0L);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMMM-dd HH:mm:ss");
                        Instant time = Instant.ofEpochSecond(tracker_value);
                        stat_text = Text.literal(format.format(Date.from(time))).formatted(Formatting.GRAY);
                    }
                    MutableText tooltip_text = Text.literal(key).append(": ").formatted(Formatting.YELLOW);
                    tooltip.accept(Text.literal(" ").append(tooltip_text).append(stat_text));
                }
            }
            if (size >= (this.max_entries_shown + 1) && !TrackerUtil.isTooltipScrollInstalled()) {
                tooltip.accept(Text.translatable("tooltip.strangeitems.map_cutoff", size - (this.max_entries_shown + 1)).formatted(Formatting.ITALIC));
            }
            TrackerUtil.addItemIdToTooltip(stack, tooltip, type);
            ci.cancel();
        }
    }

    public boolean should_show_tooltip(ItemStack stack) {
        return this.stackHasTracker(stack) && TrackerUtil.isKeyDown(this.get_key()) && StrangeConfig.in_depth_tracking && this.stack_has_map_tracker(stack);
    }

    @Override
    public NbtCompound getTrackerValueNbt(ItemStack stack) {
        if (!this.stackHasTracker(stack)) {
            return new NbtCompound();
        }
        return stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getCompound(this.map_id).orElse(new NbtCompound());
    }

    public boolean stack_has_map_tracker(ItemStack stack) {
        return stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().contains(this.map_id);
    }

    @Override
    public void appendTooltip(ItemStack stack, Consumer<Text> tooltip) {
        if (this.shouldTrack(stack)) {
            if (this.stackHasTracker(stack) && StrangeConfig.in_depth_tracking && this.stack_has_map_tracker(stack)) {
                Text stat_text = Text.literal(this.getFormattedTrackerValue(stack)).formatted(Formatting.YELLOW);
                Text tooltip_text = this.getNameForTooltip().append(Text.literal(": ").formatted(Formatting.GRAY));
                Text control_text = Text.literal(" [").append(this.get_key().getBoundKeyLocalizedText()).append("]").formatted(Formatting.DARK_GRAY, Formatting.ITALIC);
                tooltip.accept(Text.literal(" ").append(tooltip_text).append(stat_text).append(control_text));
            } else {
                super.appendTooltip(stack, tooltip);
            }
        }
    }

    public KeyBinding get_key() {
        return TrackerKeybindings.get_timestamp_keybind(this);
    }
}
