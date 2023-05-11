package got.common.entity.westeros.wildling.thenn;

import got.common.database.GOTAchievement;
import got.common.database.GOTRegistry;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityThennBerserker extends GOTEntityThenn {
	public GOTEntityThennBerserker(World world) {
		super(world);
		canBeMarried = false;
		setSize(0.6f * 1.1f, 1.8f * 1.1f);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killThennBerserker;
	}

	@Override
	public EntityAIBase getThennAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.4, false);
	}

	@Override
	public int getTotalArmorValue() {
		return 15;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(4);
		if (i == 0 || i == 1 || i == 2) {
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.wildlingSword));
		} else {
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.wildlingBattleaxe));
		}
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.boneChestplate));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(4, null);
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
