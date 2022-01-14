package got.common.entity.westeros.westerlands;

import got.common.database.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityWesterlandsGuard extends GOTEntityWesterlandsSoldier {
	public GOTEntityWesterlandsGuard(World world) {
		super(world);
		spawnRidingHorse = false;
		addTargetTasks(false);
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.BANDIT;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(GOTRegistry.westerosPike));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.westerosPike));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.westerlandsguardBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.westerlandsguardLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.westerlandsguardChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.westerlandsguardHelmet));
		return data;
	}
}
