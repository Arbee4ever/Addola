package net.arbee.addola.mixins;

import net.arbee.addola.client.render.AddolaCapeFeatureRender;
import net.arbee.addola.client.render.AddolaElytraFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("rawtypes")
@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer {

	@SuppressWarnings("unchecked")
	public PlayerEntityRendererMixin(EntityRendererFactory.Context dispatcher, PlayerEntityModel<AbstractClientPlayerEntity> model, float f) {
        super(dispatcher, model, f);
    }

	@SuppressWarnings("unchecked")
	@Inject(method = "<init>", at = @At("RETURN"))
	public void init(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
	    this.addFeature(new AddolaCapeFeatureRender(this));
	    this.addFeature(new AddolaElytraFeatureRenderer(this, ctx.getModelLoader()));
    }
}