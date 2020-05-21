package sk.jurij.fabrictest.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class RenderMixin{
    private String getHealth(Entity entity){
        return String.valueOf((int)Math.ceil(Double.parseDouble(entity.getDataTracker().get(entity.getDataTracker().getAllEntries().get(8).getData()).toString())));
    }
    @Shadow protected void renderLabelIfPresent(Entity entity, String string, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int i){}
    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/entity/Entity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    public void render(Entity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {
        if (entity.isLiving()) this.renderLabelIfPresent(entity, getHealth(entity), matrices, vertexConsumers, light);
    }
}
