package embin.strangeitems.mixin;

import embin.strangeitems.StrangeItemsComponents;
import embin.strangeitems.StrangeRegistryKeys;
import embin.strangeitems.tracker.*;
import embin.strangeitems.util.TrackerUtil;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemMixin {
    @Inject(at = @At(value = "HEAD"), method = "postMine")
    public void postMineMixin(World world, BlockState state, BlockPos pos, PlayerEntity miner, CallbackInfo ci) {
        ItemStack stack = (ItemStack)(Object) this;
        Trackers.BLOCKS_MINED.appendTracker(stack, Registries.BLOCK.getId(state.getBlock()).toString());
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;appendComponentTooltip(Lnet/minecraft/component/ComponentType;Lnet/minecraft/item/Item$TooltipContext;Lnet/minecraft/component/type/TooltipDisplayComponent;Ljava/util/function/Consumer;Lnet/minecraft/item/tooltip/TooltipType;)V",
        ordinal = 0, shift = At.Shift.BEFORE), method = "appendTooltip", locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void appendTooltipMixin(Item.TooltipContext context, TooltipDisplayComponent displayComponent, PlayerEntity player, TooltipType type, Consumer<Text> list, CallbackInfo ci) {
        ItemStack stack = (ItemStack)(Object) this;
        if (stack.isIn(TrackerItemTags.CAN_TRACK_STATS) || stack.contains(StrangeItemsComponents.HAS_ALL_TRACKERS)) {
            for (RegistryEntry<Tracker> registryEntry : TrackerUtil.getTooltipOrder(context.getRegistryLookup(), StrangeRegistryKeys.TRACKER, TrackerTags.HAS_SPECIAL_TOOLTIP)) {
                if (registryEntry.value() instanceof MapTracker mapTracker) {
                    if (mapTracker.shouldShowTooltip(stack)) {
                        mapTracker.appendTooltipMap(stack, list, ci, type);
                        return;
                    }
                }
                if (registryEntry.value() instanceof TimestampTracker tsTracker) {
                    if (tsTracker.should_show_tooltip(stack)) {
                        tsTracker.append_tooltip_map(stack, list, ci, type);
                        return;
                    }
                }
            }
            TrackerUtil.addAllTrackerTooltips(context, list, stack);
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0, shift = At.Shift.AFTER), method = "getTooltip", locals = LocalCapture.CAPTURE_FAILHARD)
    public void nameColorMixin(Item.TooltipContext context, PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir, TooltipDisplayComponent tooltipDisplayComponent, List<Text> list) {
        ItemStack stack = (ItemStack)(Object) this;
        if (stack.contains(StrangeItemsComponents.COLLECTORS_ITEM)) {
            list.removeLast();
            MutableText item_name = (MutableText) stack.getName();
            MutableText name = Text.empty();
            name.append(item_name);
            if (stack.contains(DataComponentTypes.CUSTOM_NAME)) {
                name.formatted(Formatting.ITALIC);
            }
            name.formatted(Formatting.DARK_RED);
            list.add(name);
            if (stack.contains(DataComponentTypes.CUSTOM_NAME)) {
                ItemStack stack2 = stack.copy();
                stack2.remove(DataComponentTypes.CUSTOM_NAME);

                MutableText name2 = Text.empty().append(stack2.getName());
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

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;appendComponentTooltip(Lnet/minecraft/component/ComponentType;Lnet/minecraft/item/Item$TooltipContext;Lnet/minecraft/component/type/TooltipDisplayComponent;Ljava/util/function/Consumer;Lnet/minecraft/item/tooltip/TooltipType;)V",
        ordinal = 15, shift = At.Shift.BEFORE), method = "appendTooltip", locals = LocalCapture.CAPTURE_FAILHARD)
    public void enchantTooltipMixin(Item.TooltipContext context, TooltipDisplayComponent displayComponent, PlayerEntity player, TooltipType type, Consumer<Text> textConsumer, CallbackInfo ci) {
        ItemStack stack = (ItemStack)(Object) this;
        if (stack.hasEnchantments() || !stack.getOrDefault(DataComponentTypes.STORED_ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT).isEmpty()) {
            textConsumer.accept(Text.translatable("tooltip.strangeitems.enchantments").append(":").formatted(Formatting.GRAY));
        }
    }

    /**
     * @author Embin
     * @reason bleh
     */
    @Overwrite()
    public Text getName() {
        ItemStack stack = (ItemStack)(Object) this;

        Text text = stack.getCustomName();
        if (text != null) {
            return text;
        }
        if (stack.contains(StrangeItemsComponents.COLLECTORS_ITEM)) {
            return Text.translatable("tooltip.strangeitems.collectors_item.item_name", stack.getItemName());
        }
        return stack.getItemName();
    }
}
