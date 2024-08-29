package embin.strangeitems.mixin;

import embin.strangeitems.StrangeItemsComponents;
import embin.strangeitems.util.ComponentTracker;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(at = @At("HEAD"), method = "postMine")
    public void postMineMixin(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir) {
        new ComponentTracker().appendTracker(stack, StrangeItemsComponents.BLOCKS_MINED);
    }

    @Inject(at = @At("HEAD"), method = "appendTooltip")
    public void appendTooltipMixin(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, CallbackInfo ci) {
        new ComponentTracker().appendTrackerTooltip(stack, tooltip, StrangeItemsComponents.TIME_FLOWN_WITH_ELYTRA, "Time Spent Flying: ");
        new ComponentTracker().appendTrackerTooltip(stack, tooltip, StrangeItemsComponents.SHOTS_FIRED, "Shots Fired: ");
        new ComponentTracker().appendTrackerTooltip(stack, tooltip, StrangeItemsComponents.SHOT_HIT, "Shots Hit: ");
        new ComponentTracker().appendTrackerTooltip(stack, tooltip, StrangeItemsComponents.BLOCKS_MINED, "Blocks Mined: ");
        new ComponentTracker().appendTrackerTooltip(stack, tooltip, StrangeItemsComponents.MOBS_HIT, "Mobs Hit: ");
        new ComponentTracker().appendTrackerTooltip(stack, tooltip, StrangeItemsComponents.FARMLAND_CREATED, "Dirt Tilled: ");
        new ComponentTracker().appendTrackerTooltip(stack, tooltip, StrangeItemsComponents.LOGS_STRIPPED, "Blocks Stripped: ");
        new ComponentTracker().appendTrackerTooltip(stack, tooltip, StrangeItemsComponents.PATHS_CREATED, "Paths Created: ");
        new ComponentTracker().appendTrackerTooltip(stack, tooltip, StrangeItemsComponents.CAMPFIRES_PUT_OUT, "Campfires Put Out: ");
        new ComponentTracker().appendTrackerTooltip(stack, tooltip, StrangeItemsComponents.TIMES_DROPPED, "Times Dropped: ");
    }
}
