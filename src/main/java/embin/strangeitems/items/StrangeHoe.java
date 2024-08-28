package embin.strangeitems.items;

import com.mojang.datafixers.util.Pair;
import embin.strangeitems.StrangeItemsComponents;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class StrangeHoe extends HoeItem {
    public StrangeHoe(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (stack.contains(StrangeItemsComponents.BLOCKS_MINED)) {
            int count = stack.getOrDefault(StrangeItemsComponents.BLOCKS_MINED, 0);
            tooltip.add(Text.translatable("component.strangeitems.blocks_mined.info", count).formatted(Formatting.GOLD));
        }
        if (stack.contains(StrangeItemsComponents.FARMLAND_CREATED)) {
            int count = stack.getOrDefault(StrangeItemsComponents.FARMLAND_CREATED, 0);
            tooltip.add(Text.translatable("component.strangeitems.farmland_created.info", count).formatted(Formatting.GOLD));
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
        ItemStack stack = context.getStack();
        Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>> pair = TILLING_ACTIONS.get(world.getBlockState(blockpos).getBlock());
        if (pair != null) {
            Predicate<ItemUsageContext> predicate = pair.getFirst();
            if (predicate.test(context)) {
                if (stack.contains(StrangeItemsComponents.FARMLAND_CREATED)) {
                    int count = stack.getOrDefault(StrangeItemsComponents.FARMLAND_CREATED, 0);
                    stack.set(StrangeItemsComponents.FARMLAND_CREATED, ++count);
                }
            }
        }
        return super.useOnBlock(context);
    }
}
