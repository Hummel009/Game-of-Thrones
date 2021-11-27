package got.common.entity.essos.yiti;

import got.common.database.*;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYiTiSamurai extends GOTEntityYiTiSoldier {
	public GOTEntityYiTiSamurai(World world) {
		super(world);
		canBeMarried = false;
		addTargetTasks(true);
		spawnRidingHorse = false;
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
		return GOTAchievement.KILL_SAMURAI;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.yitiSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.yitiBootsSamurai));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.yitiLeggingsSamurai));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.yitiChestplateSamurai));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.yitiHelmetSamurai));
		return data;
	}
}
