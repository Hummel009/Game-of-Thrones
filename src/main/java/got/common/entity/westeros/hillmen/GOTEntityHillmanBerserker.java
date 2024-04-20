package got.common.entity.westeros.hillmen;

import got.common.database.GOTItems;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
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
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(4);
		if (i == 0 || i == 1 || i == 2) {
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.ironBattleaxe));
		} else {
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.bronzeBattleaxe));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.furBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.furLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.boneChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.hillmenHelmet));
		return data;
	}
}