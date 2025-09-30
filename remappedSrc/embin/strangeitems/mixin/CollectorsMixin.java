package embin.strangeitems.mixin;

import embin.strangeitems.StrangeItemsComponents;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGameHud.class)
public class CollectorsMixin {
	@Shadow private ItemStack currentStack;

	@Inject(at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/font/TextRenderer;getWidth(Lnet/minecraft/text/StringVisitable;)I",
			shift = At.Shift.BEFORE
	), method = "renderHeldItemTooltip", locals = LocalCapture.CAPTURE_FAILHARD)
	private void adjust_color_for_collectors(DrawContext context, CallbackInfo ci, MutableText mutableText) {
		if (this.currentStack.contains(StrangeItemsComponents.COLLECTORS_ITEM)) {
			mutableText.formatted(Formatting.DARK_RED);
		}
	}
}