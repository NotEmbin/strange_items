package embin.strangeitems.mixin;

import embin.strangeitems.tracker.Trackers;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingBobberEntity.class)
public class FishingBobberMixin {
    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ItemEntity;setVelocity(DDD)V"))
    public void bobberMixin(ItemStack usedItem, CallbackInfoReturnable<Integer> cir) {
        Trackers.TIMES_FISHING_ROD_CAUGHT_SOMETHING.appendTracker(usedItem);
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;increaseStat(Lnet/minecraft/util/Identifier;I)V"))
    public void fishCaughtMixin(ItemStack usedItem, CallbackInfoReturnable<Integer> cir) {
        Trackers.FISH_CAUGHT.appendTracker(usedItem);
    }
}
