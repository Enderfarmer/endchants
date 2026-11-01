package com.endchants.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.endchants.Endchants;
import com.endchants.ModArmorEnchantments;
import com.endchants.ModEffects;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

@Mixin(Player.class)
public class NormalPlayerMixin {
    @WrapMethod(method = "blockUsingItem")
    public void endchants$blockDetection(ServerLevel serverLevel, LivingEntity livingEntity, Operation<Void> op) {
        ServerPlayer player = (ServerPlayer) (Object) this;
        ItemStack itemStack = player.getItemBlockingWith();
        if (Endchants.getLevel(ModArmorEnchantments.PUSHBACK, itemStack, serverLevel) > 0) {
            livingEntity.addEffect(new MobEffectInstance(ModEffects.STUNNED, 30, 0));
            Vec3 ownerPosition = player.position();
            Vec3 direction = ownerPosition.subtract(livingEntity.position()).normalize().reverse();

            livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(direction.scale(0.2)));
        }

    }
}
