package sk.jurij.fabrictest.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sk.jurij.fabrictest.FabricTest;


@Mixin(ClientPlayerEntity.class)

public abstract class HealthMixin extends LivingEntity{
	@Shadow @Final protected MinecraftClient client;

	protected HealthMixin(EntityType<? extends LivingEntity> type, World world) {
		super(type, world);
	}

	@Inject(at = @At("RETURN"), method = "updateHealth(F)V")
	public void updateHealth(float f, CallbackInfo info) {
		FabricTest.sendSerial((int)Math.ceil(super.getHealth())+10);
	}

	@Inject(at = @At("HEAD"), method = "method_3148(FF)V")
	public void method_3148(float f, float g, CallbackInfo info) {
		PlayerEntity player = this.client.player;
		if (FabricTest.ABToggled && FabricTest.getClosest(player) != null) {
			player.yaw = FabricTest.calculateYaw(FabricTest.getClosest(player), player);
			player.pitch = FabricTest.calculatePitch(FabricTest.getClosest(player), player);
		}
	}
}
