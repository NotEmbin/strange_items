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

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin {
    @Shadow @NotNull public abstract ItemStack getWeaponStack();

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;increaseStat(Lnet/minecraft/util/Identifier;I)V"),
        method = "attack", locals = LocalCapture.CAPTURE_FAILHARD)
    public void hitMobMixin(Entity target, CallbackInfo ci, float n) {
        Trackers.mobs_hit.append_tracker(this.getWeaponStack());
        Trackers.damage_dealt.append_tracker(this.getWeaponStack(), Math.round(n * 10.0F));
    }

    @Inject(method = "onKilledOther", at = @At(value = "HEAD"))
    public void killOtherMobMixin(ServerWorld world, LivingEntity other, CallbackInfoReturnable<Boolean> cir) {
        Trackers.mobs_killed.append_tracker(this.getWeaponStack(), Registries.ENTITY_TYPE.getId(other.getType()).toString());
    }

    @Inject(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;increaseStat(Lnet/minecraft/util/Identifier;I)V", ordinal = 1))
    public void damageMixin(ServerWorld world, DamageSource source, float amount, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object) this;
        for (ItemStack stack : player.getArmorItems()) {
            if (!stack.isEmpty()) {
                Trackers.damage_taken.append_tracker(stack, Math.round(amount * 10.0F));
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
                Trackers.time_underwater.append_tracker(head_stack);
            }
        }
        if (player.isInLava()) {
            if (!chest_stack.isEmpty()) {
                Trackers.time_in_lava.append_tracker(chest_stack);
            }
        }
        if (!leg_stack.isEmpty()) {
            Trackers.time_in_dimensions.append_tracker(leg_stack, dimension);
        }

    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;incrementStat(Lnet/minecraft/util/Identifier;)V", ordinal = 3))
    public void whenSneaking(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object) this;
        ItemStack legs_stack = player.getEquippedStack(EquipmentSlot.LEGS);
        if (!legs_stack.isEmpty()) {
            Trackers.time_sneaking.append_tracker(legs_stack);
        }
    }

    @Inject(method = "handleFallDamage", at = @At(value = "HEAD"))
    public void whenFallen(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity)(Object) this;
        ItemStack feet_stack = player.getEquippedStack(EquipmentSlot.FEET);
        if (!feet_stack.isEmpty()) {
            Trackers.distance_fallen.append_tracker(feet_stack, (int)Math.round((double)fallDistance * 100.0));
        }
    }
}
