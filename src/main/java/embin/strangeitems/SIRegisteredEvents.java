package embin.strangeitems;

import embin.strangeitems.event.ServerPlayerEvents;
import embin.strangeitems.tracker.Trackers;
import embin.strangeitems.util.Id;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public final class SIRegisteredEvents {
    public static final Identifier BLOCK_MINED = Id.of("blocks_mined_tracker");
    public static final Identifier ENTITY_ATTACKED = Id.of("mobs_hit_tracker");
    public static final Identifier PLAYER_CROUCH = Id.of("time_sneaking_tracker");
    public static final Identifier PLAYER_TICK = Id.of("player_tick");

    public static void registerEvents() {
        PlayerBlockBreakEvents.AFTER.register(BLOCK_MINED, (level, player, blockPos, blockState, blockEntity) -> {
            ItemStack itemStack = player.getActiveItem();
            Trackers.BLOCKS_MINED.appendTracker(itemStack, BuiltInRegistries.BLOCK.getKey(blockState.getBlock()).toString());
        });

        ServerPlayerEvents.WHEN_CROUCHING.register(PLAYER_CROUCH, player -> {
            ItemStack legs_stack = player.getItemBySlot(EquipmentSlot.LEGS);
            if (!legs_stack.isEmpty()) {
                Trackers.TIME_SNEAKING.appendTracker(legs_stack);
            }
            return InteractionResult.PASS;
        });

        ServerPlayerEvents.ON_TICK.register(PLAYER_TICK, player -> {
            if (!player.isSpectator() || !player.touchingUnloadedChunk()) {
                ItemStack headStack = player.getItemBySlot(EquipmentSlot.HEAD);
                ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);
                ItemStack legStack = player.getItemBySlot(EquipmentSlot.LEGS);

                if (!headStack.isEmpty()) {
                    if (player.isEyeInFluid(FluidTags.WATER)) {
                        Trackers.TIME_UNDERWATER.appendTracker(headStack);
                    }
                }
                if (!chestStack.isEmpty()) {
                    if (player.isInLava()) {
                        Trackers.TIME_IN_LAVA.appendTracker(chestStack);
                    }
                }
                if (!legStack.isEmpty()) {
                    String dimension = player.level().dimensionTypeRegistration().getRegisteredName();
                    Trackers.TIME_IN_DIMENSIONS.appendTracker(legStack, dimension);
                }
            }
            return InteractionResult.PASS;
        });
    }
}
