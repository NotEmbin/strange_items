package embin.strangeitems.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import org.jetbrains.annotations.NotNull;

public final class ServerPlayerEvents {
    private ServerPlayerEvents() {}

    public static final Event<@NotNull OnTick> ON_TICK = EventFactory.createArrayBacked(OnTick.class, listeners -> player -> {
        for (OnTick listener : listeners) {
            InteractionResult result = listener.tick(player);
            if (result != InteractionResult.PASS) {
                return result;
            }
        }
        return InteractionResult.PASS;
    });

    public static final Event<@NotNull WhenCrouching> WHEN_CROUCHING = EventFactory.createArrayBacked(WhenCrouching.class, listeners -> player -> {
        for (WhenCrouching listener : listeners) {
            InteractionResult result = listener.whenCrouching(player);
            if (result != InteractionResult.PASS) {
                return result;
            }
        }
        return InteractionResult.PASS;
    });

    public interface OnTick {
        InteractionResult tick(ServerPlayer player);
    }

    public interface WhenCrouching {
        InteractionResult whenCrouching(ServerPlayer player);
    }
}
