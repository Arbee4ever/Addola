package net.arbee.addola.mixins;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.awt.*;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {
    @ModifyArgs(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)I", ordinal = 1))
    public void draw (Args args, T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        float healthPercent = ((LivingEntity)entity).getHealth() / ((LivingEntity)entity).getMaxHealth();
        args.set(3, getCol(healthPercent));
    }

    public int getCol(float percent) {
        int p = (int)(255 * percent);
        return new Color(255, p, p).hashCode();
    }
}