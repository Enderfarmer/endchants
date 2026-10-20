package com.endchants.client.renderer;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;

public class CustomArrowRenderer extends ArrowRenderer<AbstractArrow, ArrowRenderState> {
    private static final Identifier TEXTURE = Identifier.withDefaultNamespace("textures/entity/projectiles/arrow.png");

    public CustomArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    public Identifier getTextureLocation(ArrowRenderState state) {
        return TEXTURE;
    }

}