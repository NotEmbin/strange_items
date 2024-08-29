package embin.strangeitems;

import embin.strangeitems.items.ModItems;
import embin.strangeitems.util.ComponentTracker;
import embin.strangeitems.util.ConvertNamespace;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.*;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
	static ComponentTracker ct = new ComponentTracker();

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public final class StrangeItemGroup {
		public static final ItemGroup STRANGE_ITEMS_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(Items.GOLDEN_PICKAXE))
			.displayName(Text.translatable("itemGroup.strangeitems.strange_items_group"))
			.entries((context, entries) -> {
				entries.add(ModItems.STRANGE_UPGRADE_SMITHING_TEMPLATE);
				entries.add(ComponentTracker.applyDefaultTrackers(new ItemStack(Items.WOODEN_SWORD)));
				entries.add(ComponentTracker.applyDefaultTrackers(new ItemStack(Items.WOODEN_PICKAXE)));
				entries.add(ComponentTracker.applyDefaultAxeTrackers(new ItemStack(Items.WOODEN_AXE)));
				entries.add(ComponentTracker.applyDefaultShovelTrackers(new ItemStack(Items.WOODEN_SHOVEL)));
				entries.add(ComponentTracker.applyDefaultHoeTrackers(new ItemStack(Items.WOODEN_HOE)));
				entries.add(ComponentTracker.applyDefaultTrackers(new ItemStack(Items.STONE_SWORD)));
				entries.add(ComponentTracker.applyDefaultTrackers(new ItemStack(Items.STONE_PICKAXE)));
				entries.add(ComponentTracker.applyDefaultAxeTrackers(new ItemStack(Items.STONE_AXE)));
				entries.add(ComponentTracker.applyDefaultShovelTrackers(new ItemStack(Items.STONE_SHOVEL)));
				entries.add(ComponentTracker.applyDefaultHoeTrackers(new ItemStack(Items.STONE_HOE)));
				entries.add(ComponentTracker.applyDefaultTrackers(new ItemStack(Items.IRON_SWORD)));
				entries.add(ComponentTracker.applyDefaultTrackers(new ItemStack(Items.IRON_PICKAXE)));
				entries.add(ComponentTracker.applyDefaultAxeTrackers(new ItemStack(Items.IRON_AXE)));
				entries.add(ComponentTracker.applyDefaultShovelTrackers(new ItemStack(Items.IRON_SHOVEL)));
				entries.add(ComponentTracker.applyDefaultHoeTrackers(new ItemStack(Items.IRON_HOE)));
				entries.add(ComponentTracker.applyDefaultTrackers(new ItemStack(Items.GOLDEN_SWORD)));
				entries.add(ComponentTracker.applyDefaultTrackers(new ItemStack(Items.GOLDEN_PICKAXE)));
				entries.add(ComponentTracker.applyDefaultAxeTrackers(new ItemStack(Items.GOLDEN_AXE)));
				entries.add(ComponentTracker.applyDefaultShovelTrackers(new ItemStack(Items.GOLDEN_SHOVEL)));
				entries.add(ComponentTracker.applyDefaultHoeTrackers(new ItemStack(Items.GOLDEN_HOE)));
				entries.add(ComponentTracker.applyDefaultTrackers(new ItemStack(Items.DIAMOND_SWORD)));
				entries.add(ComponentTracker.applyDefaultTrackers(new ItemStack(Items.DIAMOND_PICKAXE)));
				entries.add(ComponentTracker.applyDefaultAxeTrackers(new ItemStack(Items.DIAMOND_AXE)));
				entries.add(ComponentTracker.applyDefaultShovelTrackers(new ItemStack(Items.DIAMOND_SHOVEL)));
				entries.add(ComponentTracker.applyDefaultHoeTrackers(new ItemStack(Items.DIAMOND_HOE)));
				entries.add(ComponentTracker.applyDefaultTrackers(new ItemStack(Items.NETHERITE_SWORD)));
				entries.add(ComponentTracker.applyDefaultTrackers(new ItemStack(Items.NETHERITE_PICKAXE)));
				entries.add(ComponentTracker.applyDefaultAxeTrackers(new ItemStack(Items.NETHERITE_AXE)));
				entries.add(ComponentTracker.applyDefaultShovelTrackers(new ItemStack(Items.NETHERITE_SHOVEL)));
				entries.add(ComponentTracker.applyDefaultHoeTrackers(new ItemStack(Items.NETHERITE_HOE)));
				entries.add(ComponentTracker.applyDefaultElytraTrackers(new ItemStack(Items.ELYTRA)));
				entries.add(ComponentTracker.applyDefaultBowTrackers(new ItemStack(Items.BOW)));
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