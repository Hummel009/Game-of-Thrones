package got.common.entity.essos.ghiscar;

import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityGhiscarGuard extends GOTEntityGhiscarLevyman {
	public GOTEntityGhiscarGuard(World world) {
		super(world);
		npcCape = GOTCapes.GHISCAR;
		npcShield = GOTShields.GHISCAR;
		addTargetTasks(false);
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killThePolice;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData data1 = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(GOTItems.essosPolearm));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.essosPolearm));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.gemsbokBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.gemsbokLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.gemsbokChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.gemsbokHelmet));
		return data1;
	}
}
