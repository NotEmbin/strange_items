package embin.strangeitems.mixin;

import embin.strangeitems.StrangeItemsComponents;
import embin.strangeitems.util.ComponentTracker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin {
    @Shadow @NotNull public abstract ItemStack getWeaponStack();

    @Inject(at = @At("RETURN"), method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;")
    public void dropItemMixin(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        new ComponentTracker().appendTracker(stack, StrangeItemsComponents.TIMES_DROPPED);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;increaseStat(Lnet/minecraft/util/Identifier;I)V"),
        method = "attack")
    public void hitMobMixin(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object) this;
        new ComponentTracker().appendTracker(this.getWeaponStack(), StrangeItemsComponents.MOBS_HIT);
    }
}
