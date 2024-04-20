package got.common.entity.westeros.gift;

import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.westeros.legendary.warrior.GOTEntityAlliserThorne;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityGiftGuard extends GOTEntityGiftMan {
	public static ItemStack[] militiaWeapons = {new ItemStack(GOTItems.westerosSword), new ItemStack(GOTItems.westerosHammer), new ItemStack(GOTItems.westerosPike), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(GOTItems.ironBattleaxe), new ItemStack(GOTItems.ironPike), new ItemStack(GOTItems.bronzeSword), new ItemStack(GOTItems.bronzeAxe), new ItemStack(GOTItems.bronzeBattleaxe)};

	public GOTEntityGiftGuard(World world) {
		super(world);
		canBeMarried = false;
		tasks.addTask(1, new EntityAIAvoidEntity(this, GOTEntityAlliserThorne.class, 5.0f, 1.0, 1.0));
		npcCape = GOTCapes.NIGHT;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killNightWatchGuard;
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
	public void onAttackModeChange(GOTEntityNPC.AttackMode mode, boolean mounted) {
		if (mode == GOTEntityNPC.AttackMode.IDLE) {
			if (mounted) {
				setCurrentItemOrArmor(0, npcItemsInv.getIdleItemMounted());
			} else {
				setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
			}
		} else if (mounted) {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeaponMounted());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(militiaWeapons.length);
		npcItemsInv.setMeleeWeapon(militiaWeapons[i].copy());
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.giftBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.giftLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.giftChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.giftHelmet));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		npcItemsInv.setIdleItemMounted(npcItemsInv.getMeleeWeaponMounted());
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}