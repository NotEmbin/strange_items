package embin.strangeitems.mixin;

import embin.strangeitems.tracker.Trackers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PersistentProjectileEntity.class)
public abstract class AbstractArrowMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;ceil(D)I", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD, method = "onEntityHit")
    public void onHitMixin(EntityHitResult entityHitResult, CallbackInfo ci, Entity entity2) {
        PersistentProjectileEntity ppe = (PersistentProjectileEntity)(Object) this;
        if (ppe.getOwner() != null) {
            if (ppe.getOwner().getWeaponStack() != null) {
                Trackers.shots_hit.append_tracker(ppe.getOwner().getWeaponStack());
            }
        }
    }
}
