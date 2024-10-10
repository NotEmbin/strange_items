package embin.strangeitems.mixin;

import embin.strangeitems.tracker.Trackers;
import embin.strangeitems.util.TrackerUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Equipment.class)
public interface EquipmentMixin {
    @Inject(method = "equipAndSwap", at = @At(value = "HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
    default void equipMixin(Item item, World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (TrackerUtil.can_swap(user, hand)) {
            Trackers.times_equipped.append_tracker(user.getStackInHand(hand), 1);
        }
    }
}
