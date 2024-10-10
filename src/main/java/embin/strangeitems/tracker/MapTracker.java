package embin.strangeitems.tracker;

import embin.strangeitems.client.StrangeItemsClient;
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

import java.util.List;
import java.util.Objects;

public class MapTracker extends Tracker {
    public String map_id;
    public String translation_prefix;

    /**
     * Maximum number of entries that can be shown for in-depth trackers.
     * Ignored if certain conditions are met.
     * @see TrackerUtil#is_tooltip_scroll_installed()
     */
    public int max_maps_shown = 8;

    public MapTracker(Identifier id, String translate_prefix, TagKey<Item> tag) {
        super(id, tag);
        this.map_id = this.get_id().toString() + "_map";
        this.translation_prefix = translate_prefix;
    }

    public MapTracker(Identifier id, String translate_prefix) {
        super(id);
        this.map_id = this.get_id().toString() + "_map";
        this.translation_prefix = translate_prefix;
    }

    public void append_tracker(ItemStack stack, String key) {
        super.append_tracker(stack, 1);
        if (this.should_track(stack) || this.stack_has_tracker(stack)) {
            if (StrangeConfig.in_depth_tracking) {
                int tracker_count = this.get_tracker_value_nbt(stack).getInt(key) + 1;
                NbtCompound nbt = this.get_tracker_value_nbt(stack).copy();
                nbt.putInt(key, tracker_count);
                this.set_tracker_value_nbt(stack, nbt);
            }
        }
    }

    @Override
    public void set_tracker_value_nbt(ItemStack stack, NbtElement value) {
        stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(currentnbt -> currentnbt.put(this.map_id, value)));
    }

    @Override
    public NbtCompound get_tracker_value_nbt(ItemStack stack) {
        if (!this.stack_has_tracker(stack)) {
            return new NbtCompound();
        }
        return stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().getCompound(this.map_id);
    }

    public void append_tooltip_map(ItemStack stack, List<Text> tooltip, CallbackInfoReturnable<List<Text>> cir, TooltipType type) {
        if (this.should_track(stack) && should_show_tooltip(stack)) {
            this.append_tooltip_no_space(stack, tooltip);
            NbtCompound nbtCompound = this.get_tracker_value_nbt(stack);
            int index = 1;
            for (String key : TrackerUtil.get_sorted_keys(nbtCompound)) {
                if (index <= this.max_maps_shown || TrackerUtil.is_tooltip_scroll_installed()) {
                    Text stat_text = Text.literal(this.get_formatted_tracker_value_nbt(stack, key)).formatted(Formatting.YELLOW);
                    MutableText tooltip_text = Text.translatable(Identifier.of(key).toTranslationKey(this.translation_prefix)).append(": ").formatted(Formatting.GRAY);
                    tooltip.add(Text.literal(" ").append(tooltip_text).append(stat_text));
                }
                index++;
            }
            if (index > (this.max_maps_shown + 1) && !TrackerUtil.is_tooltip_scroll_installed()) {
                tooltip.add(Text.translatable("tooltip.strangeitems.map_cutoff", index - (this.max_maps_shown + 1)));
            }
            TrackerUtil.add_item_id_to_tooltip(stack, tooltip, type);
            cir.setReturnValue(tooltip);
        }
    }

    public String get_formatted_tracker_value_nbt(ItemStack stack, String key) {
        return this.get_stat_formatter().format(this.get_tracker_value_nbt(stack).getInt(key) * this.formatted_value_multiplier);
    }

    public boolean should_show_tooltip(ItemStack stack) {
        return this.stack_has_tracker(stack) && TrackerUtil.is_key_down(this.get_key()) && StrangeConfig.in_depth_tracking && this.stack_has_map_tracker(stack);
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

    public boolean stack_has_map_tracker(ItemStack stack) {
        return stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).contains(this.map_id);
    }

    public KeyBinding get_key() {
        if (Objects.equals(this.map_id, "strangeitems:blocks_mined_map")) {
            return StrangeItemsClient.show_blocks_mined;
        } else {
            return StrangeItemsClient.show_mobs_killed;
        }
    }
}
