package got.common.entity.essos.yiti;

import got.common.database.GOTRegistry;
import got.common.item.other.GOTItemRobes;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYiTiLevyman extends GOTEntityYiTiMan {
	public static ItemStack[] levyWeapons = { new ItemStack(GOTRegistry.yitiDagger), new ItemStack(GOTRegistry.yitiDaggerPoisoned), new ItemStack(GOTRegistry.ironDagger), new ItemStack(GOTRegistry.bronzeDagger), new ItemStack(GOTRegistry.yitiBattleaxe), new ItemStack(Items.iron_sword), new ItemStack(GOTRegistry.bronzeSword), new ItemStack(GOTRegistry.ironBattleaxe), new ItemStack(GOTRegistry.bronzeBattleaxe), new ItemStack(GOTRegistry.yitiSpear), new ItemStack(GOTRegistry.ironSpear), new ItemStack(GOTRegistry.bronzeSpear) };
	public static ItemStack[] levySpears = { new ItemStack(GOTRegistry.yitiSpear), new ItemStack(GOTRegistry.ironSpear), new ItemStack(GOTRegistry.bronzeSpear) };
	public static ItemStack[] levyBodies = { new ItemStack(Items.leather_chestplate), new ItemStack(GOTRegistry.bronzeChestplate) };
	public static ItemStack[] levyLegs = { new ItemStack(Items.leather_leggings), new ItemStack(GOTRegistry.bronzeLeggings) };
	public static ItemStack[] levyBoots = { new ItemStack(Items.leather_boots), new ItemStack(GOTRegistry.bronzeBoots) };
	public static int[] kaftanColors = { 14823729, 11862016, 5512477, 14196753, 11374145, 7366222 };

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
				return "essos/yiti/warrior/hired";
			}
			return "essos/yiti/warrior/friendly";
		}
		return "essos/yiti/warrior/hostile";
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
			ItemStack kaftan = new ItemStack(GOTRegistry.kaftanChestplate);
			int kaftanColor = kaftanColors[rand.nextInt(kaftanColors.length)];
			GOTItemRobes.setRobesColor(kaftan, kaftanColor);
			setCurrentItemOrArmor(3, kaftan);
			if (rand.nextBoolean()) {
				ItemStack kaftanLegs = new ItemStack(GOTRegistry.kaftanLeggings);
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
