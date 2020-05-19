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
import sk.jurij.fabrictest.AutoAim;
import sk.jurij.fabrictest.SerialPort;

@Mixin(ClientPlayerEntity.class)

public abstract class ClientPlayerMixin extends LivingEntity{
	protected ClientPlayerMixin(EntityType<? extends LivingEntity> type, World world) {
		super(type, world);
	}

	@Shadow @Final protected MinecraftClient client;

	@Inject(at = @At("RETURN"), method = "updateHealth(F)V")
	public void updateHealth(float f, CallbackInfo info) {
		SerialPort.sendSerial((int)Math.ceil(super.getHealth())+10);
	}

	//TODO: make moves smoother (move to another class)
	@Inject(at = @At("HEAD"), method = "tick()V")
	public void tick(CallbackInfo info) {
		PlayerEntity player = this.client.player;
		if (AutoAim.ABToggled && AutoAim.getClosest(player) != null) {
			player.yaw = AutoAim.calculateYaw(AutoAim.getClosest(player), player);
			player.pitch = AutoAim.calculatePitch(AutoAim.getClosest(player), player);
		}
	}
}
