package embin.strangeitems.util;

import embin.strangeitems.StrangeItems;
import embin.strangeitems.config.StrangeConfig;
import embin.strangeitems.mixin.KeyBindAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

import java.util.List;

public class TrackerUtil {

    /**
     * Gets the keys from an NBT Compound, sorted from the highest to lowest value.
     * @param nbtCompound An NBT Compound where each value is an integer.
     * @return The sorted keys of the given NBT Compound.
     */
    public static List<String> get_sorted_keys(NbtCompound nbtCompound) {
        List<String> sorted = new java.util.ArrayList<>(List.of());
        List<String> unsorted = nbtCompound.getKeys().stream().toList();
        for (String key : unsorted) {
           sorted.add(key);
           int value = nbtCompound.getInt(key);
           if (sorted.size() > 1) {
               while (true) {
                   int index = sorted.indexOf(key);
                   String key_ahead;
                   try {
                       key_ahead = sorted.get(index - 1);
                   } catch (IndexOutOfBoundsException e) {
                       break;
                   }
                   int value_ahead = nbtCompound.getInt(key_ahead);
                   if (value > value_ahead) {
                       sorted.remove(index);
                       sorted.add(index - 1, key);
                   } else {
                       break;
                   }
               }
           }
        }
        return sorted;
    }

    /**
     * Check if the specified keybinding is currently being pressed or not.
     * @param key The keybinding to check for.
     * @return <code>true</code> if the given key is currently held down;
     * <code>false</code> if it isn't.
     */
    public static boolean is_key_down(KeyBinding key) {
        long handle = MinecraftClient.getInstance().getWindow().getHandle();
        int key_code = ((KeyBindAccessor)key).getBoundKey().getCode();
        return InputUtil.isKeyPressed(handle, key_code);
    }

    public static void add_item_id_to_tooltip(ItemStack stack, List<Text> tooltip, TooltipType type) {
        if (type.isAdvanced()) {
            tooltip.add(Text.literal(Registries.ITEM.getId(stack.getItem()).toString()).formatted(Formatting.DARK_GRAY));
        }
    }

    public static boolean can_swap(ItemStack stack, ItemStack stack_wearing, PlayerEntity player) {
        return (
        !EnchantmentHelper.hasAnyEnchantmentsWith(stack_wearing, EnchantmentEffectComponentTypes.PREVENT_ARMOR_CHANGE)
            || player.isCreative()
        ) && !ItemStack.areItemsAndComponentsEqual(stack, stack_wearing);
    }

    public static boolean is_tooltip_scroll_installed() {
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
