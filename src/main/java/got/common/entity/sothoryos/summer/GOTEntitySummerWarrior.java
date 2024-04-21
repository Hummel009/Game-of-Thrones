package got.common.entity.sothoryos.summer;

import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.item.other.GOTItemRobes;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntitySummerWarrior extends GOTEntitySummerMan {
	private static final ItemStack[] WEAPONS_BRONZE = {new ItemStack(GOTItems.summerSword), new ItemStack(GOTItems.summerSword), new ItemStack(GOTItems.summerSword), new ItemStack(GOTItems.summerDagger), new ItemStack(GOTItems.summerDaggerPoisoned), new ItemStack(GOTItems.summerPike)};
	private static final int[] TURBAN_COLORS = {1643539, 6309443, 7014914, 7809314, 5978155};

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntitySummerWarrior(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
		shield = GOTShields.SUMMER;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(NPC_RANGED_ACCURACY).setBaseValue(1.0);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hireableInfo.getHiringPlayer() == entityplayer) {
				return "standard/civilized/hired_soldier";
			}
			return "standard/civilized/usual_friendly";
		}
		return "standard/civilized/usual_hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData data1 = super.onSpawnWithEgg(data);
		int i = rand.nextInt(WEAPONS_BRONZE.length);
		npcItemsInv.setMeleeWeapon(WEAPONS_BRONZE[i].copy());
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
			int robeColor = TURBAN_COLORS[rand.nextInt(TURBAN_COLORS.length)];
			GOTItemRobes.setRobesColor(turban, robeColor);
			setCurrentItemOrArmor(4, turban);
		} else {
			setCurrentItemOrArmor(4, new ItemStack(GOTItems.summerHelmet));
		}
		return data1;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}