package embin.strangeitems.items;

import embin.strangeitems.StrangeItemsComponents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

@Deprecated
public class StrangePickaxe extends PickaxeItem {
    public StrangePickaxe(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (stack.contains(StrangeItemsComponents.BLOCKS_MINED)) {
            int count = stack.getOrDefault(StrangeItemsComponents.BLOCKS_MINED, 0);
            tooltip.add(Text.translatable("component.strangeitems.blocks_mined.info", count).withColor(13593138));
        }
    }

    /*
    //@Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world.isClient()) {
            return TypedActionResult.success(stack);
        }

        int count = stack.getOrDefault(StrangeItemsComponents.TIMES_USED, 0);
        stack.set(StrangeItemsComponents.TIMES_USED, ++count);

        return TypedActionResult.success(stack);

        //return super.use(world, user, hand);
    }
    */

    //@Override
    public boolean postMinep(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (stack.contains(StrangeItemsComponents.BLOCKS_MINED)) {
            int count = stack.getOrDefault(StrangeItemsComponents.BLOCKS_MINED, 0);
            stack.set(StrangeItemsComponents.BLOCKS_MINED, ++count);
        }
        return super.postMine(stack, world, state, pos, miner);
    }
}
