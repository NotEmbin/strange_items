package embin.strangeitems.mixin;

import embin.strangeitems.tracker.Trackers;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerMixin {
    @Inject(method = "dropPlayerItem", at = @At(value = "RETURN"))
    public void dropItemMixin(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        if (!stack.isEmpty()) {
            Trackers.times_dropped.append_tracker(stack);
        }
    }
}
