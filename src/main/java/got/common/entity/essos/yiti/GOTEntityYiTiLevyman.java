package got.common.entity.essos.yiti;

import got.common.database.GOTItems;
import got.common.item.other.GOTItemRobes;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYiTiLevyman extends GOTEntityYiTiMan {
	public static ItemStack[] levyWeapons = {new ItemStack(GOTItems.yitiDagger), new ItemStack(GOTItems.yitiDaggerPoisoned), new ItemStack(GOTItems.ironDagger), new ItemStack(GOTItems.bronzeDagger), new ItemStack(GOTItems.yitiBattleaxe), new ItemStack(Items.iron_sword), new ItemStack(GOTItems.bronzeSword), new ItemStack(GOTItems.ironBattleaxe), new ItemStack(GOTItems.bronzeBattleaxe), new ItemStack(GOTItems.yitiSpear), new ItemStack(GOTItems.ironSpear), new ItemStack(GOTItems.bronzeSpear)};
	public static ItemStack[] levySpears = {new ItemStack(GOTItems.yitiSpear), new ItemStack(GOTItems.ironSpear), new ItemStack(GOTItems.bronzeSpear)};
	public static ItemStack[] levyBodies = {new ItemStack(Items.leather_chestplate), new ItemStack(GOTItems.bronzeChestplate)};
	public static ItemStack[] levyLegs = {new ItemStack(Items.leather_leggings), new ItemStack(GOTItems.bronzeLeggings)};
	public static ItemStack[] levyBoots = {new ItemStack(Items.leather_boots), new ItemStack(GOTItems.bronzeBoots)};
	public static int[] kaftanColors = {14823729, 11862016, 5512477, 14196753, 11374145, 7366222};

	public GOTEntityYiTiLevyman(World world) {
		super(world);
		canBeMarried = false;
		addTargetTasks(true);
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
		int i = rand.nextInt(levyWeapons.length);
		npcItemsInv.setMeleeWeapon(levyWeapons[i].copy());
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			i = rand.nextInt(levySpears.length);
			npcItemsInv.setMeleeWeapon(levySpears[i].copy());
		}
		i = rand.nextInt(levyBoots.length);
		setCurrentItemOrArmor(1, levyBoots[i].copy());
		i = rand.nextInt(levyLegs.length);
		setCurrentItemOrArmor(2, levyLegs[i].copy());
		i = rand.nextInt(levyBodies.length);
		setCurrentItemOrArmor(3, levyBodies[i].copy());
		setCurrentItemOrArmor(4, null);
		if (rand.nextBoolean()) {
			ItemStack kaftan = new ItemStack(GOTItems.kaftanChestplate);
			int kaftanColor = kaftanColors[rand.nextInt(kaftanColors.length)];
			GOTItemRobes.setRobesColor(kaftan, kaftanColor);
			setCurrentItemOrArmor(3, kaftan);
			if (rand.nextBoolean()) {
				ItemStack kaftanLegs = new ItemStack(GOTItems.kaftanLeggings);
				GOTItemRobes.setRobesColor(kaftanLegs, kaftanColor);
				setCurrentItemOrArmor(2, kaftanLegs);
			}
		}
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
