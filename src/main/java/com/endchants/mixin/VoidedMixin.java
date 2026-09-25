package com.endchants.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.endchants.accessor.LivingEntityMixinInterface;

import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntity.class)
public class VoidedMixin implements LivingEntityMixinInterface {
    @Unique
    float voidedCoef = 1;

    @Override
    public float endchants$getVoidedEff() {

        return voidedCoef;
    }

    @Override
    public void endchants$incrementVoidedEff(int maxLevel) {
        if (voidedCoef >= maxLevel)
            voidedCoef = 1;
        else
            voidedCoef += .5f;

    }

    @ModifyVariable(method = "actuallyHurt", at = @At("HEAD"), ordinal = 0)
    public float voidedMultiplied(float amount) {
        return amount * voidedCoef;
    }

}
