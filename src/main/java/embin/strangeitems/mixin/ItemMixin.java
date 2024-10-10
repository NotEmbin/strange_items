package embin.strangeitems.mixin;

import embin.strangeitems.StrangeItemsComponents;
import embin.strangeitems.client.StrangeItemsClient;
import embin.strangeitems.config.StrangeConfig;
import embin.strangeitems.tracker.Trackers;
import embin.strangeitems.tracker.TrackerTags;
import embin.strangeitems.util.TrackerUtil;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.stream.Stream;

@Mixin(ItemStack.class)
public abstract class ItemMixin {
    @Shadow public abstract Stream<TagKey<Item>> streamTags();

    @Inject(at = @At(value = "HEAD"), method = "postMine")
    public void postMineMixin(World world, BlockState state, BlockPos pos, PlayerEntity miner, CallbackInfo ci) {
        ItemStack stack = (ItemStack)(Object) this;
        Trackers.blocks_mined.append_tracker(stack, Registries.BLOCK.getId(state.getBlock()).toString());
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;appendTooltip(Lnet/minecraft/component/ComponentType;Lnet/minecraft/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/item/tooltip/TooltipType;)V",
        ordinal = 0, shift = At.Shift.BEFORE), method = "getTooltip", locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void appendTooltipMixin(Item.TooltipContext context, PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir, List<Text> list) {
        ItemStack stack = (ItemStack)(Object) this;
        if (Trackers.blocks_mined.should_show_tooltip(stack)) {
            Trackers.blocks_mined.append_tooltip_map(stack, list, cir, type);
            return;
        }
        if (Trackers.mobs_killed.should_show_tooltip(stack)) {
            Trackers.mobs_killed.append_tooltip_map(stack, list, cir, type);
            return;
        }
        if (Trackers.times_dropped.should_show_tooltip(stack)) {
            Trackers.times_dropped.append_tooltip_map(stack, list, cir, type);
            return;
        }
        if (stack.isIn(TrackerTags.CAN_TRACK_STATS) || stack.getComponents().toString().contains("strangeitems:")) {
            list.add(Text.translatable("tooltip.strangeitems.strange_trackers").append(":").formatted(Formatting.GRAY));
            //tooltip.add(Text.literal("").withColor(13593138));
        }
        Trackers.time_flown_with_elytra.append_tooltip(stack, list);
        Trackers.shots_fired.append_tooltip(stack, list);
        Trackers.shots_hit.append_tooltip(stack, list);
        Trackers.fires_lit.append_tooltip(stack, list);
        Trackers.campfires_lit.append_tooltip(stack, list);
        Trackers.sheep_sheared.append_tooltip(stack, list);
        Trackers.fish_caught.append_tooltip(stack, list);
        Trackers.times_fishing_rod_caught_something.append_tooltip(stack, list);
        Trackers.times_fishing_rod_cast.append_tooltip(stack, list);
        Trackers.times_fishing_rod_reeled_in.append_tooltip(stack, list);
        Trackers.blocks_brushed.append_tooltip(stack, list);
        Trackers.armadillos_brushed.append_tooltip(stack, list);
        Trackers.damage_taken.append_tooltip(stack, list);
        Trackers.times_equipped.append_tooltip(stack, list);
        Trackers.damage_dealt.append_tooltip(stack, list);
        Trackers.mobs_killed.append_tooltip(stack, list);
        Trackers.trident_thrown.append_tooltip(stack, list);
        Trackers.blocks_mined.append_tooltip(stack, list);
        Trackers.mobs_hit.append_tooltip(stack, list);
        Trackers.dirt_tilled.append_tooltip(stack, list);
        Trackers.logs_stripped.append_tooltip(stack, list);
        Trackers.paths_created.append_tooltip(stack, list);
        Trackers.campfires_put_out.append_tooltip(stack, list);
        Trackers.plants_trimmed.append_tooltip(stack, list);
        Trackers.times_dropped.append_tooltip(stack, list);
    }

    @Inject(at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0, shift = At.Shift.AFTER), method = "getTooltip", locals = LocalCapture.CAPTURE_FAILHARD)
    public void nameColorMixin(Item.TooltipContext context, PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir, List<Text> list) {
        ItemStack stack = (ItemStack)(Object) this;
        if (stack.contains(StrangeItemsComponents.COLLECTORS_ITEM)) {
            list.removeLast();
            MutableText item_name = (MutableText) stack.getName();
            /*
            if (type.isAdvanced()) {
                item_name.append(" (#");
                item_name.append(String.valueOf(Item.getRawId(stack.getItem())));
                item_name.append(")");
            }
             */
            MutableText name = Text.empty();
            if (stack.contains(DataComponentTypes.CUSTOM_NAME)) {
                name.append(item_name);
                name.formatted(Formatting.ITALIC);
            } else {
                name.append(Text.translatable("tooltip.strangeitems.collectors_item")).append(" ").append(item_name);
            }
            name.formatted(Formatting.DARK_RED);
            list.add(name);
            if (stack.contains(DataComponentTypes.CUSTOM_NAME)) {
                MutableText name2 = Text.empty();
                name2.append(Text.translatable("tooltip.strangeitems.collectors_item")).append(" ");
                name2.append(Text.translatable(stack.getTranslationKey()));
                name2.formatted(Formatting.DARK_RED);
                list.add(name2);
            }
        } /*else {
            if (stack.getComponents().toString().contains("strangeitems:")) {
                list.removeLast();
                Text item_name = stack.getName();
                MutableText name = Text.empty();
                if (stack.contains(DataComponentTypes.CUSTOM_NAME)) {
                    name.append(item_name);
                    name.formatted(Formatting.ITALIC);
                } else {
                    name.append(Text.translatable("tooltip.strangeitems.strange")).append(" ").append(item_name);
                }
                name.withColor(13593138);
                list.add(name);
                if (stack.contains(DataComponentTypes.CUSTOM_NAME)) {
                    MutableText name2 = Text.empty();
                    name2.append(Text.translatable("tooltip.strangeitems.strange")).append(" ");
                    name2.append(Text.translatable(stack.getTranslationKey()));
                    name2.withColor(13593138);
                    list.add(name2);
                }
            }
        }
        */
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;appendTooltip(Lnet/minecraft/component/ComponentType;Lnet/minecraft/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/item/tooltip/TooltipType;)V",
        ordinal = 3, shift = At.Shift.BEFORE), method = "getTooltip", locals = LocalCapture.CAPTURE_FAILHARD)
    public void enchantTooltipMixin(Item.TooltipContext context, PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir, List<Text> list) {
        ItemStack stack = (ItemStack)(Object) this;
        if (stack.hasEnchantments()) {
            list.add(Text.translatable("tooltip.strangeitems.enchantments").append(":").formatted(Formatting.GRAY));
        }
    }

    @Inject(method = "inventoryTick", at = @At(value = "HEAD"))
    public void legacyTrackerFix(World world, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        ItemStack stack = (ItemStack)(Object) this;
        if (stack.isIn(TrackerTags.CAN_TRACK_STATS)) {
            Trackers.blocks_mined.convert_legacy_tracker(stack, StrangeItemsComponents.BLOCKS_MINED);
            Trackers.times_dropped.convert_legacy_tracker(stack, StrangeItemsComponents.TIMES_DROPPED);
            Trackers.mobs_hit.convert_legacy_tracker(stack, StrangeItemsComponents.MOBS_HIT);
            Trackers.time_flown_with_elytra.convert_legacy_tracker(stack, StrangeItemsComponents.TIME_FLOWN_WITH_ELYTRA);
            Trackers.dirt_tilled.convert_legacy_tracker(stack, StrangeItemsComponents.FARMLAND_CREATED);
            Trackers.logs_stripped.convert_legacy_tracker(stack, StrangeItemsComponents.LOGS_STRIPPED);
            Trackers.paths_created.convert_legacy_tracker(stack, StrangeItemsComponents.PATHS_CREATED);
            Trackers.campfires_put_out.convert_legacy_tracker(stack, StrangeItemsComponents.CAMPFIRES_PUT_OUT);
            Trackers.sheep_sheared.convert_legacy_tracker(stack, StrangeItemsComponents.SHEEP_SHEARED);
            Trackers.plants_trimmed.convert_legacy_tracker(stack, StrangeItemsComponents.PLANTS_TRIMMED);
            Trackers.fires_lit.convert_legacy_tracker(stack, StrangeItemsComponents.FIRES_IGNITED);
            Trackers.campfires_lit.convert_legacy_tracker(stack, StrangeItemsComponents.CAMPFIRES_LIT);
            Trackers.shots_hit.convert_legacy_tracker(stack, StrangeItemsComponents.SHOT_HIT);
            Trackers.shots_fired.convert_legacy_tracker(stack, StrangeItemsComponents.SHOTS_FIRED);
        }
    }
}
