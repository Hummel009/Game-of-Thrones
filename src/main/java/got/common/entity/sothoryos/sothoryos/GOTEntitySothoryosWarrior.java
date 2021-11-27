package got.common.entity.sothoryos.sothoryos;

import got.common.database.*;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntitySothoryosWarrior extends GOTEntitySothoryosMan {
	public GOTEntitySothoryosWarrior(World world) {
		super(world);
		canBeMarried = false;
		addTargetTasks(true);
		npcShield = GOTShields.SOTHORYOS;
	}

	@Override
	public EntityAIBase createSothoryosAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.4, true);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "sothoryos/sothoryos/warrior/hired";
			}
			return "sothoryos/sothoryos/warrior/friendly";
		}
		return "sothoryos/sothoryos/warrior/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(8);
		switch (i) {
		case 0:
		case 1:
		case 2:
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.sothoryosSword));
			break;
		case 3:
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.sothoryosDagger));
			break;
		case 4:
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.sothoryosDaggerPoisoned));
			break;
		case 5:
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.sothoryosHammer));
			break;
		case 6:
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.sothoryosBattleaxe));
			break;
		case 7:
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.sothoryosPike));
			break;
		default:
			break;
		}
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.sothoryosSpear));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.sothoryosBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.sothoryosLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.sothoryosChestplate));
		if (rand.nextInt(5) != 0) {
			setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.sothoryosHelmet));
		}
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
