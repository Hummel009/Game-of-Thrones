package got.common.entity.westeros.north;

import got.common.database.GOTRegistry;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class GOTEntityNorthLevyman extends GOTEntityNorthMan {
	public static ItemStack[] militiaWeapons = { new ItemStack(GOTRegistry.westerosSword), new ItemStack(GOTRegistry.westerosHammer), new ItemStack(GOTRegistry.westerosPike), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(GOTRegistry.ironBattleaxe), new ItemStack(GOTRegistry.ironPike), new ItemStack(GOTRegistry.bronzeSword), new ItemStack(GOTRegistry.bronzeAxe), new ItemStack(GOTRegistry.bronzeBattleaxe) };
	public static int[] leatherDyes = { 10855845, 8026746, 5526612, 3684408, 8350297, 10388590, 4799795, 5330539, 4211801, 2632504 };

	public GOTEntityNorthLevyman(World world) {
		super(world);
		canBeMarried = false;
		addTargetTasks(true);
	}

	@Override
	public EntityAIBase createNorthAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.4, true);
	}

	public ItemStack dyeLeather(ItemStack itemstack) {
		int i = rand.nextInt(leatherDyes.length);
		int color = leatherDyes[i];
		((ItemArmor) itemstack.getItem()).func_82813_b(itemstack, color);
		return itemstack;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "standart/civilized/hired_soldier";
			}
			return "standart/civilized/usual_friendly";
		}
		return "standart/civilized/usual_hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(militiaWeapons.length);
		npcItemsInv.setMeleeWeapon(militiaWeapons[i].copy());
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, dyeLeather(new ItemStack(Items.leather_boots)));
		setCurrentItemOrArmor(2, dyeLeather(new ItemStack(Items.leather_leggings)));
		setCurrentItemOrArmor(3, new ItemStack(Items.leather_chestplate));
		if (rand.nextInt(3) != 0) {
			setCurrentItemOrArmor(4, null);
		} else {
			i = rand.nextInt(3);
			switch (i) {
			case 0:
				setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.northHelmet));
				break;
			case 1:
				setCurrentItemOrArmor(4, new ItemStack(Items.iron_helmet));
				break;
			case 2:
				setCurrentItemOrArmor(4, dyeLeather(new ItemStack(Items.leather_helmet)));
				break;
			default:
				break;
			}
		}
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
