package embin.strangeitems.mixin;

import embin.strangeitems.tracker.Trackers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TridentItem.class)
public class TridentMixin {
    @Inject(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getEffect(Lnet/minecraft/item/ItemStack;Lnet/minecraft/component/ComponentType;)Ljava/util/Optional;"))
    public void tridentMixin(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {
        Trackers.trident_thrown.append_tracker(stack);
    }
}
