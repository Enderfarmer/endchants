package com.endchants.accessor;

public interface LivingEntityMixinInterface {
    void endchants$incrementVoidedEff(int maxLevel);

    float endchants$getVoidedEff();

    float endchants$getShotNumber();

    void endchants$incrementShotNumber(int max);
}
