package com.endchants.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.endchants.accessor.PlayerMixinInterface;

import net.minecraft.server.level.ServerPlayer;

@Mixin(ServerPlayer.class)
public abstract class PlayerMixin implements PlayerMixinInterface {
    @Unique
    private int echoCd = 0;

    public int endchants$getEchoCd() {
        return echoCd;
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void endchants$tickEchoCd(CallbackInfo ci) {
        if (echoCd > 0)
            echoCd--;
    }

    public void endchants$setEchoCd(int seconds) {
        echoCd = seconds * 20;
    }

}
