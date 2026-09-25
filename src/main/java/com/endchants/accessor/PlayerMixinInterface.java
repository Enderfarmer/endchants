package com.endchants.accessor;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface PlayerMixinInterface {
    int endchants$getEchoCd();

    void endchants$tickEchoCd(CallbackInfo ci);

    void endchants$setEchoCd(int seconds);
}
