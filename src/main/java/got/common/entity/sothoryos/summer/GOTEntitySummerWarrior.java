package got.common.entity.sothoryos.summer;

import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.item.other.GOTItemRobes;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntitySummerWarrior extends GOTEntitySummerMan {
	public static ItemStack[] weaponsBronze = {new ItemStack(GOTItems.summerSword), new ItemStack(GOTItems.summerSword), new ItemStack(GOTItems.summerSword), new ItemStack(GOTItems.summerDagger), new ItemStack(GOTItems.summerDaggerPoisoned), new ItemStack(GOTItems.summerPike)};
	public static int[] turbanColors = {1643539, 6309443, 7014914, 7809314, 5978155};

	public GOTEntitySummerWarrior(World world) {
		super(world);
		canBeMarried = false;
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
		npcShield = GOTShields.SUMMER;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(npcRangedAccuracy).setBaseValue(1.0);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "standard/civilized/hired_soldier";
			}
			return "standard/civilized/usual_friendly";
		}
		return "standard/civilized/usual_hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(weaponsBronze.length);
		npcItemsInv.setMeleeWeapon(weaponsBronze[i].copy());
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.summerSpear));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.summerBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.summerLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.summerChestplate));
		if (rand.nextInt(10) == 0) {
			setCurrentItemOrArmor(4, null);
		} else if (rand.nextInt(5) == 0) {
			ItemStack turban = new ItemStack(GOTItems.robesHelmet);
			int robeColor = turbanColors[rand.nextInt(turbanColors.length)];
			GOTItemRobes.setRobesColor(turban, robeColor);
			setCurrentItemOrArmor(4, turban);
		} else {
			setCurrentItemOrArmor(4, new ItemStack(GOTItems.summerHelmet));
		}
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}