package embin.strangeitems.items;

import embin.strangeitems.StrangeItemsComponents;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

@Deprecated
public class StrangeShovel extends ShovelItem {
    public StrangeShovel(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (stack.contains(StrangeItemsComponents.BLOCKS_MINED)) {
            int count = stack.getOrDefault(StrangeItemsComponents.BLOCKS_MINED, 0);
            tooltip.add(Text.translatable("component.strangeitems.blocks_mined.info", count).withColor(13593138));
        }
        if (stack.contains(StrangeItemsComponents.PATHS_CREATED)) {
            int count = stack.getOrDefault(StrangeItemsComponents.PATHS_CREATED, 0);
            tooltip.add(Text.translatable("component.strangeitems.paths_created.info", count).withColor(13593138));
        }
        if (stack.contains(StrangeItemsComponents.CAMPFIRES_PUT_OUT)) {
            int count = stack.getOrDefault(StrangeItemsComponents.CAMPFIRES_PUT_OUT, 0);
            tooltip.add(Text.translatable("component.strangeitems.campfires_put_out.info", count).withColor(13593138));
        }
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
        BlockState blockstate = world.getBlockState(blockpos);
        ItemStack stack = context.getStack();
        if (!(context.getSide() == Direction.DOWN)) {
            BlockState blockstate2 = (BlockState) PATH_STATES.get(blockstate.getBlock());
            BlockState blockstate3 = null;
            if (blockstate2 != null && world.getBlockState(blockpos.up()).isAir()) {
                if (stack.contains(StrangeItemsComponents.PATHS_CREATED)) {
                    int count = stack.getOrDefault(StrangeItemsComponents.PATHS_CREATED, 0);
                    stack.set(StrangeItemsComponents.PATHS_CREATED, ++count);
                }
            } else if (blockstate.getBlock() instanceof CampfireBlock && (boolean) blockstate.get(CampfireBlock.LIT)) {
                if (stack.contains(StrangeItemsComponents.CAMPFIRES_PUT_OUT)) {
                    int count = stack.getOrDefault(StrangeItemsComponents.CAMPFIRES_PUT_OUT, 0);
                    stack.set(StrangeItemsComponents.CAMPFIRES_PUT_OUT, ++count);
                }
            }
        }
        return super.useOnBlock(context);
    }
}
