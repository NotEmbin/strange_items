package embin.strangeitems.tracker;

import embin.strangeitems.config.StrangeConfig;
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
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class TimestampTracker extends Tracker {
    public String map_id;
    public int max_entries_shown = 8;
    public TimestampTracker(Identifier id) {
        super(id);
        this.map_id = this.get_id().toString() + "_map";
    }
    public TimestampTracker(Identifier id, TagKey<Item> tag) {
        super(id, tag);
        this.map_id = this.get_id().toString() + "_map";
    }

    @Override
    public void append_tracker(ItemStack stack) {
        super.append_tracker(stack, 1);
        if (this.should_track(stack) || this.stack_has_tracker(stack)) {
            if (StrangeConfig.in_depth_tracking) {
                int base_value = this.get_tracker_value_int(stack);
                NbtCompound nbt = this.get_tracker_value_nbt(stack).copy();
                nbt.putLong(String.valueOf(base_value), Instant.now().getEpochSecond());
                this.set_tracker_value_nbt(stack, nbt);
            }
        }
    }

    @Override
    public void set_tracker_value_nbt(ItemStack stack, NbtElement value) {
        stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(currentnbt -> currentnbt.put(this.map_id, value)));
    }

    public void append_tooltip_map(ItemStack stack, List<Text> tooltip, CallbackInfoReturnable<List<Text>> cir, TooltipType type) {
        if (this.should_track(stack) && should_show_tooltip(stack)) {
            this.append_tooltip_no_space(stack, tooltip);
            int size = this.get_tracker_value_int(stack) - 1;
            for (int i = size; i >= 0; i--) {
                String key = String.valueOf(i + 1);
                if ((size - this.max_entries_shown) <= i || TrackerUtil.is_tooltip_scroll_installed()) {
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
            if (size >= (this.max_entries_shown + 1) && !TrackerUtil.is_tooltip_scroll_installed()) {
                tooltip.add(Text.translatable("tooltip.strangeitems.map_cutoff", size - (this.max_entries_shown + 1)).formatted(Formatting.ITALIC));
            }
            TrackerUtil.add_item_id_to_tooltip(stack, tooltip, type);
            cir.setReturnValue(tooltip);
        }
    }

    public boolean should_show_tooltip(ItemStack stack) {
        return this.stack_has_tracker(stack) && TrackerUtil.is_key_down(this.get_key()) && StrangeConfig.in_depth_tracking && this.stack_has_map_tracker(stack);
    }

    @Override
    public NbtCompound get_tracker_value_nbt(ItemStack stack) {
        if (!this.stack_has_tracker(stack)) {
            return new NbtCompound();
        }
        return stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getCompound(this.map_id);
    }

    public boolean stack_has_map_tracker(ItemStack stack) {
        return stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).contains(this.map_id);
    }

    @Override
    public void append_tooltip(ItemStack stack, List<Text> tooltip) {
        if (this.should_track(stack)) {
            if (this.stack_has_tracker(stack) && StrangeConfig.in_depth_tracking && this.stack_has_map_tracker(stack)) {
                Text stat_text = Text.literal(this.get_formatted_tracker_value(stack)).formatted(Formatting.YELLOW);
                Text tooltip_text = Text.translatable(this.get_translation_key()).append(": ").formatted(Formatting.GRAY);
                Text control_text = Text.literal(" [").append(this.get_key().getBoundKeyLocalizedText()).append("]").formatted(Formatting.DARK_GRAY, Formatting.ITALIC);
                tooltip.add(Text.literal(" ").append(tooltip_text).append(stat_text).append(control_text));
            } else {
                super.append_tooltip(stack, tooltip);
            }
        }
    }

    public KeyBinding get_key() {
        return TrackerKeybindings.get_timestamp_keybind(this);
    }
}
