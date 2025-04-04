package embin.strangeitems.mixin;

import embin.strangeitems.tracker.Trackers;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShovelItem.class)
public class ShovelMixin {
    @Inject(at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V"),
        method = "useOnBlock")
    public void pathCreationMixin(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        Trackers.paths_created.append_tracker(context.getStack());
    }

    @Inject(at = @At(value = "INVOKE",
        target = "Lnet/minecraft/block/CampfireBlock;extinguish(Lnet/minecraft/entity/Entity;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V"),
        method = "useOnBlock")
    public void putOutCampfire(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        Trackers.campfires_put_out.append_tracker(context.getStack());
    }
}
