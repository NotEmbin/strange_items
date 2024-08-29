package embin.strangeitems.mixin;

import embin.strangeitems.StrangeItemsComponents;
import embin.strangeitems.util.ComponentTracker;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public abstract class AbstractArrowMixin {

    @Shadow public abstract ItemStack getWeaponStack();

    @Inject(at = @At(value = "TAIL"), method = "onEntityHit")
    public void onHitMixin(EntityHitResult entityHitResult, CallbackInfo ci) {
        PersistentProjectileEntity ppe = (PersistentProjectileEntity)(Object) this;
        new ComponentTracker().appendTracker(this.getWeaponStack(), StrangeItemsComponents.SHOT_HIT);
    }
}
