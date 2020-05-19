package sk.jurij.fabrictest.mixin;


import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sk.jurij.fabrictest.FabricTest;


@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow @Final private EntityType<?> type;

    @Shadow public abstract World getEntityWorld();

    @Shadow private int entityId;

    @Inject(at = @At("HEAD"), method = "isGlowing()Z", cancellable = true)
    public void isGlowing(CallbackInfoReturnable<Boolean> info){
        if (this.type == Registry.ENTITY_TYPE.get(FabricTest.index)) info.setReturnValue(true);
        if (this.getEntityWorld().getEntityById(this.entityId).equals(FabricTest.getClosest(MinecraftClient.getInstance().player))) info.setReturnValue(true);
    }
    @Inject(at = @At("HEAD"), method = "getTeamColorValue()I", cancellable = true)
    public void getTeamColorValue(CallbackInfoReturnable<Integer> info){
        if (this.type == Registry.ENTITY_TYPE.get(FabricTest.index)) info.setReturnValue(16733525);
        if (this.getEntityWorld().getEntityById(this.entityId).equals(FabricTest.getClosest(MinecraftClient.getInstance().player))) info.setReturnValue(5636095);
    }
}

