package got.common;

import got.common.network.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;

public class GOTDamage {
	private static DamageSource frost = new DamageSource("got.frost").setDamageBypassesArmor();
	private static DamageSource poisonDrink = new DamageSource("got.poisonDrink").setDamageBypassesArmor().setMagicDamage();
	private static DamageSource plantHurt = new DamageSource("got.plantHurt").setDamageBypassesArmor();

	public static void doBurnDamage(EntityPlayerMP entityplayer) {
		GOTPacketEnvironmentOverlay packet = new GOTPacketEnvironmentOverlay(GOTPacketEnvironmentOverlay.Overlay.BURN);
		GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public static void doFrostDamage(EntityPlayerMP entityplayer) {
		GOTPacketEnvironmentOverlay packet = new GOTPacketEnvironmentOverlay(GOTPacketEnvironmentOverlay.Overlay.FROST);
		GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public static DamageSource getFrost() {
		return frost;
	}

	public static DamageSource getPlantHurt() {
		return plantHurt;
	}

	public static DamageSource getPoisonDrink() {
		return poisonDrink;
	}

	public static void setFrost(DamageSource frost) {
		GOTDamage.frost = frost;
	}

	public static void setPlantHurt(DamageSource plantHurt) {
		GOTDamage.plantHurt = plantHurt;
	}

	public static void setPoisonDrink(DamageSource poisonDrink) {
		GOTDamage.poisonDrink = poisonDrink;
	}
}
