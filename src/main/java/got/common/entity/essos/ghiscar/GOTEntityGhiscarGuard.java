package got.common.entity.essos.ghiscar;

import got.common.database.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityGhiscarGuard extends GOTEntityGhiscarLevyman {
	public GOTEntityGhiscarGuard(World world) {
		super(world);
		canBeMarried = false;
		spawnRidingHorse = false;
		npcCape = GOTCapes.GHISCAR;
		npcShield = GOTShields.GHISCAR;
		addTargetTasks(false);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(GOTRegistry.essosPolearm));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.essosPolearm));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.gemsbokBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.gemsbokLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.gemsbokChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.gemsbokHelmet));
		return data;
	}
}
