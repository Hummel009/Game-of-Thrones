package got.common.entity.essos.legendary.mercenary;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.iface.GOTMercenary;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityGreyWorm extends GOTEntityHumanBase implements GOTMercenary {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityGreyWorm(World world) {
		super(world);
		addTargetTasks(true);
		setupLegendaryNPC(true);
		shield = GOTShields.UNSULLIED;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killGreyWorm;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityPlayer) {
		return isFriendlyAndAligned(entityPlayer);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTItems.unsulliedBoots, 1);
		dropItem(GOTItems.unsulliedLeggings, 1);
		dropItem(GOTItems.unsulliedChestplate, 1);
		dropItem(GOTItems.unsulliedHelmet, 1);
	}

	@Override
	public float getMercAlignmentRequired() {
		return 0.0f;
	}

	@Override
	public int getMercBaseCost() {
		return GOTUnitTradeEntries.SOLDIER * 5;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.essosPike));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.unsulliedBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.unsulliedLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.unsulliedChestplate));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}