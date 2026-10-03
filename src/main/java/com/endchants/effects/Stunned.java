package com.endchants.effects;

import com.endchants.Endchants;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class Stunned extends MobEffect {
    public Stunned() {
        super(MobEffectCategory.HARMFUL, 15848467);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, Endchants.id("effect.stunned"), -1.0d,
                Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.JUMP_STRENGTH, Endchants.id("effect.stunned"), -1.0d,
                Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, Endchants.id("effect.stunned"), -1.0d,
                Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.FOLLOW_RANGE, Endchants.id("effect.stunned"), -1.0d,
                Operation.ADD_MULTIPLIED_TOTAL);

    }
}
