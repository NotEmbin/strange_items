package embin.strangeitems.mixin;

import embin.strangeitems.tracker.Trackers;
import embin.strangeitems.util.TrackerUtil;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EquippableComponent.class)
public class EquippableComponentMixin {
    @Inject(method = "equip", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;"))
    public void equipMixin(ItemStack stack, PlayerEntity player, CallbackInfoReturnable<ActionResult> cir) {
        EquippableComponent ec = (EquippableComponent) (Object) this;
        if (TrackerUtil.canSwap(stack, player.getEquippedStack(ec.slot()), player)) {
            Trackers.TIMES_EQUIPPED.appendTracker(stack, 1);
        }
    }
}
