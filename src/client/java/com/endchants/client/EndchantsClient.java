package com.endchants.client;

import com.endchants.ModEntities;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class EndchantsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRenderers.register(ModEntities.SHOCKWAVE,
				context -> new GeoEntityRenderer<>(context, ModEntities.SHOCKWAVE));
		EntityRenderers.register(ModEntities.WHIRLWIND,
				context -> new GeoEntityRenderer<>(context, ModEntities.WHIRLWIND));
		EntityRenderers.register(ModEntities.CUSTOM_AOE_CLOUD, NoopRenderer::new);
	}
}