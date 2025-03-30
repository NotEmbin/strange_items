package embin.strangeitems;

import com.mojang.serialization.Codec;
import embin.strangeitems.util.ConvertNamespace;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Unit;

public class StrangeItemsComponents {
    @Deprecated(forRemoval = true)
    public static final ComponentType<Integer> BLOCKS_MINED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        ConvertNamespace.convert("strangeitems:blocks_mined"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Unit> COLLECTORS_ITEM = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        ConvertNamespace.convert("strangeitems:collectors_item"),
        ComponentType.<Unit>builder().codec(Unit.CODEC).build()
    );

    public static final ComponentType<Unit> HAS_ALL_TRACKERS = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        ConvertNamespace.convert("strangeitems:has_all_trackers"),
        ComponentType.<Unit>builder().codec(Unit.CODEC).build()
    );

    protected static void init() {
        //StrangeItems.LOGGER.info("Loading components and legacy components for Strange Items");
    }
}
