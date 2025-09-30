package embin.strangeitems.mixin;

import embin.strangeitems.tracker.Trackers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin {
    @Shadow @NotNull public abstract ItemStack getWeaponStack();

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;increaseStat(Lnet/minecraft/util/Identifier;I)V"),
        method = "attack", locals = LocalCapture.CAPTURE_FAILHARD)
    public void hitMobMixin(Entity target, CallbackInfo ci, float n) {
        Trackers.MOBS_HIT.appendTracker(this.getWeaponStack());
        Trackers.DAMAGE_DEALT.appendTracker(this.getWeaponStack(), Math.round(n * 10.0F));
    }

    @Inject(method = "onKilledOther", at = @At(value = "HEAD"))
    public void killOtherMobMixin(ServerWorld world, LivingEntity other, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        Trackers.MOBS_KILLED.appendTracker(this.getWeaponStack(), Registries.ENTITY_TYPE.getId(other.getType()).toString());
    }

    @Inject(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;increaseStat(Lnet/minecraft/util/Identifier;I)V", ordinal = 1))
    public void damageMixin(ServerWorld world, DamageSource source, float amount, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object) this;
        List<ItemStack> armorItems = List.of(
                player.getEquippedStack(EquipmentSlot.HEAD),
                player.getEquippedStack(EquipmentSlot.CHEST),
                player.getEquippedStack(EquipmentSlot.LEGS),
                player.getEquippedStack(EquipmentSlot.FEET)
        );
        for (ItemStack stack : armorItems) {
            if (!stack.isEmpty()) {
                Trackers.DAMAGE_TAKEN.appendTracker(stack, Math.round(amount * 10.0F));
            }
        }
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    public void onTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object) this;
        String dimension = player.getEntityWorld().getDimensionEntry().getIdAsString();

        ItemStack head_stack = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chest_stack = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack leg_stack = player.getEquippedStack(EquipmentSlot.LEGS);

        if (player.isSubmergedIn(FluidTags.WATER)) {
            if (!head_stack.isEmpty()) {
                Trackers.TIME_UNDERWATER.appendTracker(head_stack);
            }
        }
        if (player.isInLava()) {
            if (!chest_stack.isEmpty()) {
                Trackers.TIME_IN_LAVA.appendTracker(chest_stack);
            }
        }
        if (!leg_stack.isEmpty()) {
            Trackers.TIME_IN_DIMENSIONS.appendTracker(leg_stack, dimension);
        }

    }

    @Inject(method = "handleFallDamage", at = @At(value = "HEAD"))
    public void whenFallen(double fallDistance, float damagePerDistance, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity)(Object) this;
        ItemStack feet_stack = player.getEquippedStack(EquipmentSlot.FEET);
        if (!feet_stack.isEmpty()) {
            Trackers.DISTANCE_FALLEN.appendTracker(feet_stack, (int)Math.round((double)fallDistance * 100.0));
        }
    }
}
