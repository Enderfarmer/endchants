package com.endchants.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.endchants.entity.CustomAoeCloudEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;

@Mixin(AreaEffectCloud.class)
public class CustomAoeCloudMixin {
    @WrapOperation(method = "serverTick", at = @At(value = "INVOKE", target = "Ljava/util/Map;containsKey(Ljava/lang/Object;)Z"))
    public boolean doesntContainOwner(Map<Entity, Integer> map, Object entity, Operation<Boolean> operation) {
        if ((Object) this instanceof CustomAoeCloudEntity aoeCloud) {
            if (aoeCloud.getOwner() != null && aoeCloud.getOwner().equals(entity)) {
                return true;
            }
        }
        return operation.call(map, entity);

    }
}
