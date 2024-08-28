package embin.strangeitems.items;

import embin.strangeitems.StrangeItems;
import embin.strangeitems.StrangeItemsComponents;
import embin.strangeitems.util.ConvertNamespace;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;

public class ModItems {
    private ModItems() {}
    static ConvertNamespace cn = new ConvertNamespace();
    static Rarity common_rarity = Rarity.RARE;
    static Text test = Text.translatable("block.minecraft.campfire");

    // .attributeModifiers(Items.DIAMOND_PICKAXE.getAttributeModifiers())

    public static ToolMaterials convertMaterial(String material) {
        return switch (material) {
            case "wooden" -> ToolMaterials.WOOD;
            case "stone" -> ToolMaterials.STONE;
            case "iron" -> ToolMaterials.IRON;
            case "golden" -> ToolMaterials.GOLD;
            case "diamond" -> ToolMaterials.DIAMOND;
            case "netherite" -> ToolMaterials.NETHERITE;
            default -> ToolMaterials.DIAMOND;
        };
    }

    public static net.minecraft.component.type.AttributeModifiersComponent pickaxeAttributes(String material) {
        return PickaxeItem.createAttributeModifiers(convertMaterial(material), 1.0F, -2.8F);
    }

    public static net.minecraft.component.type.AttributeModifiersComponent axeAttributes(String material) {
        if (material == "wooden") {
            return AxeItem.createAttributeModifiers(convertMaterial(material), 6.0F, -3.2F);
        }
        if (material == "golden") {
            return AxeItem.createAttributeModifiers(convertMaterial(material), 6.0F, -3.0F);
        }
        if (material == "stone") {
            return AxeItem.createAttributeModifiers(convertMaterial(material), 7.0F, -3.2F);
        }
        if (material == "iron") {
            return AxeItem.createAttributeModifiers(convertMaterial(material), 6.0F, -3.1F);
        }
        return AxeItem.createAttributeModifiers(convertMaterial(material), 5.0F, -3.0F);
    }

    public static net.minecraft.component.type.AttributeModifiersComponent shovelAttributes(String material) {
        return ShovelItem.createAttributeModifiers(convertMaterial(material), 1.5F, -3.0F);
    }

    public static net.minecraft.component.type.AttributeModifiersComponent hoeAttributes(String material) {
        if (material == "wooden" || material == "golden") {
            return HoeItem.createAttributeModifiers(convertMaterial(material), 0.0F, -3.0F);
        }
        if (material == "stone") {
            return HoeItem.createAttributeModifiers(convertMaterial(material), -1.0F, -2.0F);
        }
        if (material == "iron") {
            return HoeItem.createAttributeModifiers(convertMaterial(material), -2.0F, -1.0F);
        }
        if (material == "netherite") {
            return HoeItem.createAttributeModifiers(convertMaterial(material), -4.0F, 0.0F);
        }
        return HoeItem.createAttributeModifiers(convertMaterial(material), -3.0F, 0.0F); // diamond
    }

    public static StrangePickaxe newStrangePickaxe(String material) {
        return new StrangePickaxe(convertMaterial(material), new Item.Settings()
            .component(StrangeItemsComponents.BLOCKS_MINED, 0)
            .attributeModifiers(pickaxeAttributes(material))
            .rarity(common_rarity));
    }

    public static StrangeAxe newStrangeAxe(String material) {
        return new StrangeAxe(convertMaterial(material), new Item.Settings()
            .component(StrangeItemsComponents.BLOCKS_MINED, 0)
            .component(StrangeItemsComponents.LOGS_STRIPPED, 0)
            .attributeModifiers(axeAttributes(material))
            .rarity(common_rarity));
    }

    public static StrangeShovel newStrangeShovel(String material) {
        return new StrangeShovel(convertMaterial(material), new Item.Settings()
            .component(StrangeItemsComponents.BLOCKS_MINED, 0)
            .component(StrangeItemsComponents.PATHS_CREATED, 0)
            .component(StrangeItemsComponents.CAMPFIRES_PUT_OUT, 0)
            .attributeModifiers(shovelAttributes(material))
            .rarity(common_rarity));
    }

    public static StrangeHoe newStrangeHoe(String material) {
        return new StrangeHoe(convertMaterial(material), new Item.Settings()
            .component(StrangeItemsComponents.BLOCKS_MINED, 0)
            .component(StrangeItemsComponents.FARMLAND_CREATED, 0)
            .attributeModifiers(hoeAttributes(material))
            .rarity(common_rarity));
    }

    public static final Item DIAMOND_PICKAXE = register("diamond_pickaxe", newStrangePickaxe("diamond"));
    public static final Item NETHERITE_PICKAXE = register("netherite_pickaxe", newStrangePickaxe("netherite"));
    public static final Item GOLDEN_PICKAXE = register("golden_pickaxe", newStrangePickaxe("golden"));
    public static final Item IRON_PICKAXE = register("iron_pickaxe", newStrangePickaxe("iron"));
    public static final Item STONE_PICKAXE = register("stone_pickaxe", newStrangePickaxe("stone"));
    public static final Item WOODEN_PICKAXE = register("wooden_pickaxe", newStrangePickaxe("wooden"));

    public static final Item NETHERITE_AXE = register("netherite_axe", newStrangeAxe("netherite"));
    public static final Item DIAMOND_AXE = register("diamond_axe", newStrangeAxe("diamond"));
    public static final Item GOLDEN_AXE = register("golden_axe", newStrangeAxe("golden"));
    public static final Item IRON_AXE = register("iron_axe", newStrangeAxe("iron"));
    public static final Item STONE_AXE = register("stone_axe", newStrangeAxe("stone"));
    public static final Item WOODEN_AXE = register("wooden_axe", newStrangeAxe("wooden"));

    public static final Item NETHERITE_SHOVEL = register("netherite_shovel", newStrangeShovel("netherite"));
    public static final Item DIAMOND_SHOVEL = register("diamond_shovel", newStrangeShovel("diamond"));
    public static final Item GOLDEN_SHOVEL = register("golden_shovel", newStrangeShovel("golden"));
    public static final Item IRON_SHOVEL = register("iron_shovel", newStrangeShovel("iron"));
    public static final Item STONE_SHOVEL = register("stone_shovel", newStrangeShovel("stone"));
    public static final Item WOODEN_SHOVEL = register("wooden_shovel", newStrangeShovel("wooden"));

    public static final Item NETHERITE_HOE = register("netherite_hoe", newStrangeHoe("netherite"));
    public static final Item DIAMOND_HOE = register("diamond_hoe", newStrangeHoe("diamond"));
    public static final Item GOLDEN_HOE = register("golden_hoe", newStrangeHoe("golden"));
    public static final Item IRON_HOE = register("iron_hoe", newStrangeHoe("iron"));
    public static final Item STONE_HOE = register("stone_hoe", newStrangeHoe("stone"));
    public static final Item WOODEN_HOE = register("wooden_hoe", newStrangeHoe("wooden"));


    public static <T extends Item> T register(String path, T item) {
        return Registry.register(Registries.ITEM, cn.convertNamespace(path), item);
    }

    public static void init() {
        StrangeItems.LOGGER.info("Creating items for the Strange Items mod");
    }
}
