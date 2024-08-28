package embin.strangeitems.util;

import net.minecraft.block.Block;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class Tags {

    static ConvertNamespace cn = new ConvertNamespace();
    public static class Blocks {
        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, cn.convertNamespace(name));
        }

    }

    public static class Items {
        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, cn.convertNamespace(name));
        }
    }

    public static class Paintings {
        //public static final TagKey<PaintingVariant> PENGUINMOD_PAINTINGS = createTag("penguinmod_paintings");
        private static TagKey<PaintingVariant> createTag(String name) {
            return TagKey.of(RegistryKeys.PAINTING_VARIANT, cn.convertNamespace(name));
        }
    }
}
