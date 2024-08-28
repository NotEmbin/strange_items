package embin.strangeitems;

import com.mojang.serialization.Codec;
import embin.strangeitems.util.ConvertNamespace;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class StrangeItemsComponents {
    static ConvertNamespace cn = new ConvertNamespace();
    public static final ComponentType<Integer> BLOCKS_MINED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("blocks_mined"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Integer> LOGS_STRIPPED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("logs_stripped"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Integer> PATHS_CREATED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("paths_created"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Integer> CAMPFIRES_PUT_OUT = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("campfires_put_out"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Integer> FARMLAND_CREATED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("farmland_created"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    protected static void init() {
        StrangeItems.LOGGER.info("Loading components for Strange Items");
    }
}
