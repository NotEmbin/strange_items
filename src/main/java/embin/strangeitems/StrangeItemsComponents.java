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

    public static final ComponentType<Integer> MOBS_HIT = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("mobs_hit"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Integer> MOBS_KILLED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("mobs_killed"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Integer> TIMES_DROPPED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("times_dropped"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Integer> TIME_FLOWN_WITH_ELYTRA = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("time_flown_with_elytra"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Integer> SHOT_HIT = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("shot_hit"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Integer> SHOTS_FIRED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("shots_fired"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Boolean> COLLECTORS_ITEM = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("collectors_item"),
        ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    protected static void init() {
        StrangeItems.LOGGER.info("Loading components for Strange Items");
    }
}
