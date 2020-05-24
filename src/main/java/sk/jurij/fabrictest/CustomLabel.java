package sk.jurij.fabrictest;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

import java.util.*;
//TODO: implement update label
public class CustomLabel {
    private static List<CustomLabel> list = new ArrayList<CustomLabel>();
    private Entity entity;
    private List<String> rows = new ArrayList<String>();

    public void addRow(String s){
        rows.add(s);
    }

    public void setRow(String s, int index){
        rows.set(index, s);
    }

    public CustomLabel(Entity entity){
        list.add(this);
        this.entity = entity;
    }


    public static boolean hasCustomLabel(Entity entity){
        return list.contains(getInstance(entity));
    }

    public static CustomLabel AddCustomLabel(Entity entity){
        if (!hasCustomLabel(entity)) return new CustomLabel(entity);
        return null;
    }

    public static void RemoveCustomLabel(Entity entity){
        list.remove(getInstance(entity));
    }

    public static void render(Entity entity, EntityRenderDispatcher renderManager, MatrixStack matrices, TextRenderer textRenderer, VertexConsumerProvider vertexConsumerProvider, int i){
        if (getInstance(entity) != null) getInstance(entity).renderA(entity, renderManager, matrices, textRenderer, vertexConsumerProvider, i);
    }
    public void renderA(Entity entity, EntityRenderDispatcher renderManager, MatrixStack matrices, TextRenderer textRenderer, VertexConsumerProvider vertexConsumerProvider, int i){
        for (int ix = 0; ix < rows.size(); ix++) {
            double d = renderManager.getSquaredDistanceToCamera(entity);
            if (d <= 4096.0D) {
                int j = "deadmau5".equals(rows.get(ix)) ? -10*ix : 0;
                matrices.push();
                matrices.translate(0.0D, (double) entity.getHeight() + 0.5F, 0.0D);
                matrices.multiply(renderManager.getRotation());
                matrices.scale(-0.025F, -0.025F, 0.025F);
                Matrix4f matrix4f = matrices.peek().getModel();
                float g = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
                int k = (int) (g * 255.0F) << 24;
                float h = (float) (-textRenderer.getStringWidth(rows.get(ix)) / 2);
                textRenderer.draw(rows.get(ix), h, (float) j - 10*ix, -1, false, matrix4f, vertexConsumerProvider, false, k, i);
                matrices.pop();
            }
        }
    }

    public static CustomLabel getInstance(Entity entity){
        for (CustomLabel label : list) if (label.entity.equals(entity)) return label;
        return null;
    }
}
