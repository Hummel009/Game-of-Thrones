package got.common.entity.westeros.hillmen;

import got.common.database.GOTRegistry;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityHillmanBerserker extends GOTEntityHillmanWarrior {
	public GOTEntityHillmanBerserker(World world) {
		super(world);
		canBeMarried = false;
		npcShield = null;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
	}

	@Override
	public EntityAIBase getHillmanAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.4, false);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(2);
		if (i == 0) {
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.ironBattleaxe));
		} else if (i == 1) {
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.bronzeBattleaxe));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.furBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.furLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.boneChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.hillmenHelmet));
		return data;
	}
}
