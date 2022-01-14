package got.common.entity.westeros.crownlands;

import got.common.database.*;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityCrownlandsGuard extends GOTEntityCrownlandsLevyman {
	public GOTEntityCrownlandsGuard(World world) {
		super(world);
		spawnRidingHorse = false;
		npcShield = GOTShields.CROWNLANDS;
		npcCape = GOTCapes.CROWNLANDS;
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
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.crownlandsBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.crownlandsLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.crownlandsChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.crownlandsHelmet));
		return data;
	}
}
