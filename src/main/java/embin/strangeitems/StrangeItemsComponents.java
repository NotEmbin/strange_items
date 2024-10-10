package embin.strangeitems;

import com.mojang.serialization.Codec;
import embin.strangeitems.util.ConvertNamespace;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Unit;

public class StrangeItemsComponents {
    static ConvertNamespace cn = new ConvertNamespace();

    @Deprecated
    public static final ComponentType<Integer> BLOCKS_MINED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("strangeitems:blocks_mined"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    @Deprecated
    public static final ComponentType<Integer> LOGS_STRIPPED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("strangeitems:logs_stripped"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    @Deprecated
    public static final ComponentType<Integer> PATHS_CREATED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("strangeitems:paths_created"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    @Deprecated
    public static final ComponentType<Integer> CAMPFIRES_PUT_OUT = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("strangeitems:campfires_put_out"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    @Deprecated
    public static final ComponentType<Integer> FARMLAND_CREATED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("strangeitems:farmland_created"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    @Deprecated
    public static final ComponentType<Integer> MOBS_HIT = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("strangeitems:mobs_hit"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    @Deprecated
    public static final ComponentType<Integer> MOBS_KILLED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("strangeitems:mobs_killed"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    @Deprecated
    public static final ComponentType<Integer> TIMES_DROPPED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("strangeitems:times_dropped"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    @Deprecated
    public static final ComponentType<Integer> TIME_FLOWN_WITH_ELYTRA = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("strangeitems:time_flown_with_elytra"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    @Deprecated
    public static final ComponentType<Integer> SHOT_HIT = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("strangeitems:shot_hit"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    @Deprecated
    public static final ComponentType<Integer> SHOTS_FIRED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("strangeitems:shots_fired"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Unit> COLLECTORS_ITEM = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("strangeitems:collectors_item"),
        ComponentType.<Unit>builder().codec(Unit.CODEC).build()
    );

    @Deprecated
    public static final ComponentType<Integer> PLANTS_TRIMMED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("strangeitems:plants_trimmed"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    @Deprecated
    public static final ComponentType<Integer> SHEEP_SHEARED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("strangeitems:sheep_sheared"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    @Deprecated
    public static final ComponentType<Integer> FIRES_IGNITED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("strangeitems:fires_ignited"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    @Deprecated
    public static final ComponentType<Integer> CAMPFIRES_LIT = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("strangeitems:campfires_lit"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Unit> HAS_ALL_TRACKERS = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convertNamespace("strangeitems:has_all_trackers"),
        ComponentType.<Unit>builder().codec(Unit.CODEC).build()
    );

    protected static void init() {
        StrangeItems.LOGGER.info("Loading components and legacy components for Strange Items");
    }
}
