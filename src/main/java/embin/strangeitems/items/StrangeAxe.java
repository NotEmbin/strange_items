package embin.strangeitems.items;

import embin.strangeitems.StrangeItemsComponents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class StrangeAxe extends AxeItem {
    public StrangeAxe(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (stack.contains(StrangeItemsComponents.BLOCKS_MINED)) {
            int count = stack.getOrDefault(StrangeItemsComponents.BLOCKS_MINED, 0);
            tooltip.add(Text.translatable("component.strangeitems.blocks_mined.info", count).formatted(Formatting.GOLD));
        }
        if (stack.contains(StrangeItemsComponents.LOGS_STRIPPED)) {
            int count = stack.getOrDefault(StrangeItemsComponents.LOGS_STRIPPED, 0);
            tooltip.add(Text.translatable("component.strangeitems.logs_stripped.info", count).formatted(Formatting.GOLD));
        }
    }

    private static boolean shouldCancelStripAttempt(ItemUsageContext context) {
        PlayerEntity playerEntity = context.getPlayer();
        return context.getHand().equals(Hand.MAIN_HAND) && playerEntity.getOffHandStack().isOf(Items.SHIELD) && !playerEntity.shouldCancelInteraction();
    }

    //@Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (stack.contains(StrangeItemsComponents.BLOCKS_MINED)) {
            int count = stack.getOrDefault(StrangeItemsComponents.BLOCKS_MINED, 0);
            stack.set(StrangeItemsComponents.BLOCKS_MINED, ++count);
        }
        return super.postMine(stack, world, state, pos, miner);
    }


    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockpos = context.getBlockPos();
        PlayerEntity playerentity = context.getPlayer();
        if (!shouldCancelStripAttempt(context)) {
            Optional<BlockState> optional = this.tryStrip(world, blockpos, playerentity, world.getBlockState(blockpos));
            if (!optional.isEmpty()) {
                ItemStack stack = context.getStack();
                // i think this is the worst possible way of doing this
                if (stack.contains(StrangeItemsComponents.LOGS_STRIPPED)) {
                    int count = stack.getOrDefault(StrangeItemsComponents.LOGS_STRIPPED, 0);
                    stack.set(StrangeItemsComponents.LOGS_STRIPPED, ++count);
                }
                //return ActionResult.success(world.isClient);
            }
        }
        return super.useOnBlock(context);
    }

}
