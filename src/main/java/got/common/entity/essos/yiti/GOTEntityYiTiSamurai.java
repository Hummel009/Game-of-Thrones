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
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityYiTiSamurai(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
		shield = GOTShields.YITI_SAMURAI;
		cape = GOTCapes.YITI_SAMURAI;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
		getEntityAttribute(NPC_RANGED_ACCURACY).setBaseValue(1.0);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.yitiSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.yitiBootsSamurai));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.yitiLeggingsSamurai));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.yitiChestplateSamurai));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.yitiHelmetSamurai));
		return entityData;
	}
}