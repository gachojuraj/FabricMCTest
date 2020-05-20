package sk.jurij.fabrictest;

import net.minecraft.client.util.SmoothUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.util.math.Box;

public class AutoAim {
    //TODO: fix little glitches
    public static boolean ABToggled = false;
    private static SmoothUtil YawSmoother = new SmoothUtil();
    private static SmoothUtil PitchSmoother = new SmoothUtil();

    public static void ToggleAB(){
        ABToggled = !ABToggled;
    }
    public static LivingEntity getClosest(Entity p){
        try {
            return p.getEntityWorld().
                    getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT,
                            (LivingEntity) p, p.getX(), p.getY(), p.getZ(),
                            new Box(p.getX()-10, p.getY()-10,p.getZ()-10, p.getX()+10, p.getY()+10, p.getZ()+10));
        }
        catch (Exception ignored){
            return null;
        }
    }
    public static float calculateYaw(Entity target, Entity player){
        double x1 = target.getX() - player.getX();
        double z1 = target.getZ() - player.getZ();
        double a = Math.asin(x1/Math.sqrt(Math.pow(x1, 2) + Math.pow(z1, 2)))*180/Math.PI;
        a = YawSmoother.smooth(a, 0.35);
        if (z1 < 0) return -(180 - (float)a);
        return (float)-a;
    }
    public static float calculatePitch(Entity target, Entity player){
        double x1 = target.getX() - player.getX();
        double z1 = target.getZ() - player.getZ();
        double y1 = (target.getEyeY() - player.getEyeY());
        double b1 = Math.sqrt(Math.pow(x1, 2) + Math.pow(z1, 2));
        double a = Math.asin(y1/Math.sqrt(Math.pow(y1, 2) + Math.pow(b1, 2)))*180/Math.PI;
        a = PitchSmoother.smooth(a, 0.35);
        return (float)-a;
    }
}
