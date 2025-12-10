package embin.strangeitems.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(World.class)
public class ExampleMixin {

	/**
	 * @author Embin
	 * @reason Test
	 */
	@Overwrite
	private static boolean isValidHorizontally(BlockPos pos) {
		return true;
	}

	/**
	 * @author Embin
	 * @reason Test
	 */
	@Overwrite
	private static boolean isInvalidVertically(int y) {
		return false;
	}
}