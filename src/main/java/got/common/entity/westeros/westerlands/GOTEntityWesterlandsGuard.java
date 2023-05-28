package got.common.entity.westeros.westerlands;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityWesterlandsGuard extends GOTEntityWesterlandsSoldier {
	public GOTEntityWesterlandsGuard(World world) {
		super(world);
		spawnRidingHorse = false;
		addTargetTasks(false);
		npcShield = GOTShields.WESTERLANDSGUARD;
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
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(GOTItems.westerosPike));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosPike));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.westerlandsguardBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.westerlandsguardLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.westerlandsguardChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.westerlandsguardHelmet));
		return data;
	}
}
