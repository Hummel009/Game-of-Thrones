package got.common;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.network.GOTPacketEnvironmentOverlay;
import got.common.network.GOTPacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;

public class GOTDamage {
	public static final DamageSource FROST = new DamageSource("got.frost").setDamageBypassesArmor();
	public static final DamageSource POISON_DRINK = new DamageSource("got.poisonDrink").setDamageBypassesArmor().setMagicDamage();
	public static final DamageSource PLANT_HURT = new DamageSource("got.plantHurt").setDamageBypassesArmor();

	private GOTDamage() {
	}

	public static void doBurnDamage(EntityPlayerMP entityplayer) {
		IMessage packet = new GOTPacketEnvironmentOverlay(GOTPacketEnvironmentOverlay.Overlay.BURN);
		GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, entityplayer);
	}

	public static void doFrostDamage(EntityPlayerMP entityplayer) {
		IMessage packet = new GOTPacketEnvironmentOverlay(GOTPacketEnvironmentOverlay.Overlay.FROST);
		GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, entityplayer);
	}
}