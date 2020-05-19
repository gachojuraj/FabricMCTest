package sk.jurij.fabrictest;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.util.math.Box;

public class AutoAim {
    //TODO: smooth moves
    public static boolean ABToggled = false;
    public static void ToggleAB(){
        ABToggled = !ABToggled;
    }
    public static LivingEntity getClosest(Entity p){
        try {
            return p.getEntityWorld().
                    getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT,
                            (LivingEntity) p, p.getX(), p.getY(), p.getZ(),
                            new Box(-100, -100,-100, 100, 100, 100));
        }
        catch (Exception ignored){
            return null;
        }
    }
    public static float calculateYaw(Entity target, Entity player){
        double x1 = target.getX() - player.getX();
        double z1 = target.getZ() - player.getZ();
        double a = Math.asin(x1/Math.sqrt(Math.pow(x1, 2) + Math.pow(z1, 2)))*180/Math.PI;
        if (z1 < 0) return -(180 - (float)a);
        return (float)-a;
    }
    public static float calculatePitch(Entity target, Entity player){
        double x1 = target.getX() - player.getX();
        double z1 = target.getZ() - player.getZ();
        double y1 = (target.getEyeY() - player.getEyeY());
        double b1 = Math.sqrt(Math.pow(x1, 2) + Math.pow(z1, 2));
        double a = Math.asin(y1/Math.sqrt(Math.pow(y1, 2) + Math.pow(b1, 2)))*180/Math.PI;
        return (float)-a;
    }
}
