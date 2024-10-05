package embin.strangeitems.mixin;

import embin.strangeitems.tracker.Trackers;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;)V"),
        method = "tickFallFlying")
    public void elytraMixin(CallbackInfo ci) {
        LivingEntity livingentity = (LivingEntity)(Object) this;
        Trackers.time_flown_with_elytra.append_tracker(this.getEquippedStack(EquipmentSlot.CHEST));
    }
}
