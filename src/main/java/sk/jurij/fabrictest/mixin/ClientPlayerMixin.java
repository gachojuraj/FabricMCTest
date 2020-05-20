package sk.jurij.fabrictest.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sk.jurij.fabrictest.SerialPort;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerMixin extends LivingEntity{
	protected ClientPlayerMixin(EntityType<? extends LivingEntity> type, World world) {
		super(type, world);
	}
	@Inject(at = @At("RETURN"), method = "updateHealth(F)V")
	public void updateHealth(float f, CallbackInfo info) {
		SerialPort.sendSerial((int)Math.ceil(super.getHealth())+10);
	}
}
