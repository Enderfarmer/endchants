package com.endchants.effects;

import com.endchants.Endchants;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class Rampaging extends MobEffect {
    public Rampaging() {
        super(MobEffectCategory.BENEFICIAL, 0xed2e11);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, Endchants.id("rampaging"), 2.0d, Operation.ADD_VALUE);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int i, int j) {
        return true;
    }
}
