package embin.strangeitems.mixin;

import embin.strangeitems.tracker.Trackers;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;)V"),
        method = "tickGliding", locals = LocalCapture.CAPTURE_FAILHARD)
    public void elytraMixin(CallbackInfo ci, int i, int j, List<EquipmentSlot> list, EquipmentSlot equipmentSlot) {
        LivingEntity livingentity = (LivingEntity)(Object) this;
        Trackers.TIME_FLOWN_WITH_ELYTRA.appendTracker(livingentity.getEquippedStack(equipmentSlot));
    }

    //@Inject(method = "onEquipStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/registry/entry/RegistryEntry;Lnet/minecraft/sound/SoundCategory;FFJ)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void equipMixin(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack, CallbackInfo ci) {
        Trackers.TIMES_EQUIPPED.appendTracker(newStack, 1);
    }
}
