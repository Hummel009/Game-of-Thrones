package got.common.entity.essos.asshai;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityAsshaiWarrior extends GOTEntityAsshaiMan {
	public GOTEntityAsshaiWarrior(World world) {
		super(world);
		canBeMarried = false;
		npcCape = GOTCapes.ASSHAI;
		npcShield = GOTShields.ASSHAI;
		addTargetTasks(true);
	}

	@Override
	public EntityAIBase createAsshaiAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.4, true);
	}

	@Override
	public float getAlignmentBonus() {
		return 3.0f;
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
		int i = rand.nextInt(5);
		switch (i) {
			case 0:
			case 1:
			case 2:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.asshaiSword));
				break;
			case 3:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.asshaiBattleaxe));
				break;
			case 4:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.asshaiHammer));
				break;
			default:
				break;
		}
		if (rand.nextInt(6) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.asshaiSpear));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.asshaiBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.asshaiLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.asshaiChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.asshaiHelmet));
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
