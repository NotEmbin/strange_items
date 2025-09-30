package embin.strangeitems.util;

import embin.strangeitems.StrangeItems;
import net.minecraft.util.Identifier;

public class Id {
    @Deprecated
    public Identifier convertNamespace(String namespace) {
        String[] splitted = namespace.split(":");
        if (splitted.length == 1) {
            return Identifier.of(StrangeItems.MOD_ID, namespace);
        }
        return Identifier.of(splitted[0],splitted[1]);
    }

    public static Identifier of(String namespace) {
        String[] splitted = namespace.split(":");
        if (splitted.length == 1) {
            return Identifier.of(StrangeItems.MOD_ID, namespace);
        }
        return Identifier.of(splitted[0],splitted[1]);
    }
}
