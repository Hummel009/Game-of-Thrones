package got.common.entity.essos.yiti;

import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYiTiSamurai extends GOTEntityYiTiSoldier {
	public GOTEntityYiTiSamurai(World world) {
		super(world);
		canBeMarried = false;
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
		npcShield = GOTShields.YITI_SAMURAI;
		npcCape = GOTCapes.YITI_SAMURAI;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
		getEntityAttribute(npcRangedAccuracy).setBaseValue(1.0);
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killSamurai;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData data1 = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.yitiSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.yitiBootsSamurai));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.yitiLeggingsSamurai));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.yitiChestplateSamurai));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.yitiHelmetSamurai));
		return data1;
	}
}
