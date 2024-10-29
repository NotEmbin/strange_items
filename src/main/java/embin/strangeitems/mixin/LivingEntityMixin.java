package embin.strangeitems.mixin;

import embin.strangeitems.tracker.Trackers;
import embin.strangeitems.util.TrackerUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;)V"),
        method = "tickGliding", locals = LocalCapture.CAPTURE_FAILHARD)
    public void elytraMixin(CallbackInfo ci, int i, int j, List<EquipmentSlot> list, EquipmentSlot equipmentSlot) {
        LivingEntity livingentity = (LivingEntity)(Object) this;
        Trackers.time_flown_with_elytra.append_tracker(livingentity.getEquippedStack(equipmentSlot));
    }

    //@Inject(method = "onEquipStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/registry/entry/RegistryEntry;Lnet/minecraft/sound/SoundCategory;FFJ)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void equipMixin(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack, CallbackInfo ci) {
        Trackers.times_equipped.append_tracker(newStack, 1);
    }
}
