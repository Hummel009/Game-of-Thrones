package got.common.entity.westeros.crownlands;

import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityCrownlandsGuard extends GOTEntityCrownlandsLevyman {
	public GOTEntityCrownlandsGuard(World world) {
		super(world);
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
		return GOTAchievement.killThePolice;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(GOTItems.westerosPike));
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosPike));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.crownlandsBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.crownlandsLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.crownlandsChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.crownlandsHelmet));
		return data;
	}
}
