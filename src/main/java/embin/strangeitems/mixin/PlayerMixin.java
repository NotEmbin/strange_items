package embin.strangeitems.mixin;

import embin.strangeitems.tracker.Trackers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
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

    @Inject(at = @At("RETURN"), method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;")
    public void dropItemMixin(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        Trackers.times_dropped.append_tracker(stack);
        Trackers.times_dropped_map.append_tracker_time(stack, Trackers.times_dropped);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;increaseStat(Lnet/minecraft/util/Identifier;I)V"),
        method = "attack", locals = LocalCapture.CAPTURE_FAILHARD)
    public void hitMobMixin(Entity target, CallbackInfo ci, float n) {
        PlayerEntity player = (PlayerEntity)(Object) this;
        Trackers.mobs_hit.append_tracker(this.getWeaponStack());
        Trackers.damage_dealt.append_tracker(this.getWeaponStack(), Math.round(n * 10.0F));
    }

    @Inject(method = "onKilledOther", at = @At(value = "HEAD"))
    public void killOtherMobMixin(ServerWorld world, LivingEntity other, CallbackInfoReturnable<Boolean> cir) {
        Trackers.mobs_killed.append_tracker(this.getWeaponStack());
        Trackers.mobs_killed_map.append_tracker_nbt(this.getWeaponStack(), Registries.ENTITY_TYPE.getId(other.getType()).toString(), 1, Trackers.mobs_killed);
    }

    @Inject(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;increaseStat(Lnet/minecraft/util/Identifier;I)V", ordinal = 1))
    public void damageMixin(DamageSource source, float amount, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object) this;
        for (ItemStack stack : player.getArmorItems()) {
            if (!stack.isEmpty()) {
                Trackers.damage_taken.append_tracker(stack, Math.round(amount * 10.0F));
            }
        }
    }
}
