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
}
