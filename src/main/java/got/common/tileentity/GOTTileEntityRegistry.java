package got.common.tileentity;

import cpw.mods.fml.common.registry.GameRegistry;

public class GOTTileEntityRegistry {

	public static void onInit() {
		GameRegistry.registerTileEntity(GOTTileEntityBeacon.class, "GOTBeacon");
		GameRegistry.registerTileEntity(GOTTileEntityOven.class, "GOTOven");
		GameRegistry.registerTileEntity(GOTTileEntityPlate.class, "GOTPlate");
		GameRegistry.registerTileEntity(GOTTileEntityFlowerPot.class, "GOTFlowerPot");
		GameRegistry.registerTileEntity(GOTTileEntitySpawnerChest.class, "GOTSpawnerChest");
		GameRegistry.registerTileEntity(GOTTileEntityBarrel.class, "GOTBarrel");
		GameRegistry.registerTileEntity(GOTTileEntityArmorStand.class, "GOTArmorStand");
		GameRegistry.registerTileEntity(GOTTileEntityMug.class, "GOTMug");
		GameRegistry.registerTileEntity(GOTTileEntityCommandTable.class, "GOTCommandTable");
		GameRegistry.registerTileEntity(GOTTileEntityAnimalJar.class, "GOTButterflyJar");
		GameRegistry.registerTileEntity(GOTTileEntityUnsmeltery.class, "GOTUnsmeltery");
		GameRegistry.registerTileEntity(GOTTileEntityAlloyForge.class, "GOTAlloyForge");
		GameRegistry.registerTileEntity(GOTTileEntitySarbacaneTrap.class, "GOTSarbacaneTrap");
		GameRegistry.registerTileEntity(GOTTileEntityChest.class, "GOTChest");
		GameRegistry.registerTileEntity(GOTTileEntityWeaponRack.class, "GOTWeaponRack");
		GameRegistry.registerTileEntity(GOTTileEntityKebabStand.class, "GOTKebabStand");
		GameRegistry.registerTileEntity(GOTTileEntitySignCarved.class, "GOTSignCarved");
		GameRegistry.registerTileEntity(GOTTileEntitySignCarvedValyrian.class, "GOTSignCarvedValyrian");
		GameRegistry.registerTileEntity(GOTTileEntityMillstone.class, "GOTMillstone");
		GameRegistry.registerTileEntity(GOTTileEntityBookshelf.class, "GOTBookshelf");
	}
}
