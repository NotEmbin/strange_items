package embin.strangeitems.mixin;

import embin.strangeitems.tracker.Trackers;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerMixin {
    @Inject(method = "dropItem", at = @At(value = "RETURN"))
    public void dropItemMixin(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        if (!stack.isEmpty()) {
            Trackers.TIMES_DROPPED.appendTracker(stack);
        }
    }

    @Inject(method = "playerTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;incrementStat(Lnet/minecraft/util/Identifier;)V", ordinal = 3))
    public void whenSneaking(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object) this;
        ItemStack legs_stack = player.getEquippedStack(EquipmentSlot.LEGS);
        if (!legs_stack.isEmpty()) {
            Trackers.TIME_SNEAKING.appendTracker(legs_stack);
        }
    }
}
