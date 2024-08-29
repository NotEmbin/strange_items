package embin.strangeitems;

import embin.strangeitems.items.ModItems;
import embin.strangeitems.util.ConvertNamespace;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.*;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrangeItems implements ModInitializer {
	public static final String MOD_ID = "strangeitems";
	static ConvertNamespace cn = new ConvertNamespace();

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public final class StrangeItemGroup {
		public static final ItemGroup STRANGE_ITEMS_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(ModItems.GOLDEN_PICKAXE))
			.displayName(Text.translatable("itemGroup.strangeitems.strange_items_group"))
			.entries((context, entries) -> {
				entries.add(ModItems.STRANGE_UPGRADE_SMITHING_TEMPLATE);
				entries.add(ModItems.WOODEN_PICKAXE);
				entries.add(ModItems.WOODEN_AXE);
				entries.add(ModItems.WOODEN_SHOVEL);
				entries.add(ModItems.WOODEN_HOE);
				entries.add(ModItems.STONE_PICKAXE);
				entries.add(ModItems.STONE_AXE);
				entries.add(ModItems.STONE_SHOVEL);
				entries.add(ModItems.STONE_HOE);
				entries.add(ModItems.IRON_PICKAXE);
				entries.add(ModItems.IRON_AXE);
				entries.add(ModItems.IRON_SHOVEL);
				entries.add(ModItems.IRON_HOE);
				entries.add(ModItems.GOLDEN_PICKAXE);
				entries.add(ModItems.GOLDEN_AXE);
				entries.add(ModItems.GOLDEN_SHOVEL);
				entries.add(ModItems.GOLDEN_HOE);
				entries.add(ModItems.DIAMOND_PICKAXE);
				entries.add(ModItems.DIAMOND_AXE);
				entries.add(ModItems.DIAMOND_SHOVEL);
				entries.add(ModItems.DIAMOND_HOE);
				entries.add(ModItems.NETHERITE_PICKAXE);
				entries.add(ModItems.NETHERITE_AXE);
				entries.add(ModItems.NETHERITE_SHOVEL);
				entries.add(ModItems.NETHERITE_HOE);
			})
			.build();

		public static void init() {
			Registry.register(Registries.ITEM_GROUP, cn.convertNamespace("strange_items_group"), STRANGE_ITEMS_GROUP);
		}
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		StrangeItemsComponents.init();
		ModItems.init();

		StrangeItemGroup.init();

		LOGGER.info("Hello Fabric world!");
	}
}