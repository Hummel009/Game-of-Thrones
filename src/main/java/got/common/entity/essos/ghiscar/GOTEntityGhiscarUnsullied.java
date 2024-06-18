package got.common.entity.essos.ghiscar;

import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityGhiscarUnsullied extends GOTEntityGhiscarMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityGhiscarUnsullied(World world) {
		super(world);
		addTargetTasks(true);
		cape = GOTCapes.UNSULLIED;
		shield = GOTShields.UNSULLIED;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0);
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killUnsullied;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.essosPike));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.unsulliedBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.unsulliedLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.unsulliedChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.unsulliedHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}