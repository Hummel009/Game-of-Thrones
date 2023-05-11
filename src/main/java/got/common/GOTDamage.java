package got.common;

import got.common.network.GOTPacketEnvironmentOverlay;
import got.common.network.GOTPacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;

public class GOTDamage {
	public static DamageSource frost = new DamageSource("got.frost").setDamageBypassesArmor();
	public static DamageSource poisonDrink = new DamageSource("got.poisonDrink").setDamageBypassesArmor().setMagicDamage();
	public static DamageSource plantHurt = new DamageSource("got.plantHurt").setDamageBypassesArmor();

	public static void doBurnDamage(EntityPlayerMP entityplayer) {
		GOTPacketEnvironmentOverlay packet = new GOTPacketEnvironmentOverlay(GOTPacketEnvironmentOverlay.Overlay.BURN);
		GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public static void doFrostDamage(EntityPlayerMP entityplayer) {
		GOTPacketEnvironmentOverlay packet = new GOTPacketEnvironmentOverlay(GOTPacketEnvironmentOverlay.Overlay.FROST);
		GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}
}
