package embin.strangeitems.mixin;

import embin.strangeitems.tracker.Trackers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingRodItem.class)
public class FishingRodMixin {
    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;)V"))
    public void reelInRodMixin(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        Trackers.TIMES_FISHING_ROD_REELED_IN.appendTracker(user.getStackInHand(hand));
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;)V"))
    public void castRodMixin(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        Trackers.TIMES_FISHING_ROD_CAST.appendTracker(user.getStackInHand(hand));
    }
}
