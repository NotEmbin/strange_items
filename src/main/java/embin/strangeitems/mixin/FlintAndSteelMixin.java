package embin.strangeitems.mixin;

import embin.strangeitems.StrangeItemsComponents;
import embin.strangeitems.tracker.Trackers;
import embin.strangeitems.util.ComponentTracker;
import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FlintAndSteelItem.class)
public abstract class FlintAndSteelMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;)V", ordinal = 1),
        method = "useOnBlock", locals = LocalCapture.CAPTURE_FAILHARD)
    public void igniteFireMixin(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, PlayerEntity playerEntity, World world, BlockPos blockPos, BlockState blockState) {
        Trackers.fires_lit.append_tracker(context.getStack());
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;)V", ordinal = 0),
        method = "useOnBlock", locals = LocalCapture.CAPTURE_FAILHARD)
    public void igniteCampfireMixin(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, PlayerEntity playerEntity, World world, BlockPos blockPos, BlockState blockState) {
        Trackers.campfires_lit.append_tracker(context.getStack());
    }
}
