package embin.strangeitems.mixin;

import embin.strangeitems.StrangeItemsComponents;
import embin.strangeitems.util.ComponentTracker;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(HoeItem.class)
public class HoeMixin {
    @Inject(at = @At(value = "INVOKE",
        target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;)V"),
        locals = LocalCapture.CAPTURE_FAILHARD,
        method = "useOnBlock")
    public void tillDirtMixin(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        new ComponentTracker().appendTracker(context.getStack(), StrangeItemsComponents.FARMLAND_CREATED);
    }
}
