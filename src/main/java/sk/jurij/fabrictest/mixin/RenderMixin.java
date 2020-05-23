package sk.jurij.fabrictest.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
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
    @Shadow public TextRenderer getFontRenderer() {
        return null;
    }
    @Final @Shadow protected EntityRenderDispatcher renderManager;
    //TODO: fix duplicate tag on custom named entities
    @Inject(at = @At("RETURN"), method = "render(Lnet/minecraft/entity/Entity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    public void render(Entity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {
        if (entity.isLiving() && !entity.shouldRenderName()) {
            if (!entity.hasCustomName())
                this.renderLabelIfPresent(entity, entity.getType().getName().asString(), matrices, vertexConsumers, light);
            else this.renderLabelIfPresent(entity, entity.getDisplayName().asFormattedString(), matrices, vertexConsumers, light);
        }
    }
    //TODO: make tag method universal
    @Inject(at = @At("RETURN"), method = "renderLabelIfPresent(Lnet/minecraft/entity/Entity;Ljava/lang/String;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    public void renderLabelIfPresent(Entity entity, String string, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo info) {
        double d = this.renderManager.getSquaredDistanceToCamera(entity);
        if (d <= 4096.0D && entity.isLiving()) {
            int j = "deadmau5".equals(string) ? -20 : 0;
            matrices.push();
            matrices.translate(0.0D, (double)entity.getHeight() + 0.5F, 0.0D);
            matrices.multiply(this.renderManager.getRotation());
            matrices.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = matrices.peek().getModel();
            float g = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
            int k = (int)(g * 255.0F) << 24;
            TextRenderer textRenderer = this.getFontRenderer();
            float h = (float)(-textRenderer.getStringWidth(getHealth(entity)) / 2);
            textRenderer.draw(getHealth(entity), h, (float)j-10, 16733525, false, matrix4f, vertexConsumerProvider, false, k, i);
            matrices.pop();
        }
    }
}
