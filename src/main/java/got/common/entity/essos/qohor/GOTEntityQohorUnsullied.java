package got.common.entity.essos.qohor;

import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityQohorUnsullied extends GOTEntityQohorLevyman {
	public GOTEntityQohorUnsullied(World world) {
		super(world);
		canBeMarried = false;
		npcCape = GOTCapes.UNSULLIED;
		npcShield = GOTShields.UNSULLIED;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
		getEntityAttribute(npcAttackDamage).setBaseValue(40.0);
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killUnsullied;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData data1 = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.essosPike));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.unsulliedBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.unsulliedLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.unsulliedChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.unsulliedHelmet));
		return data1;
	}

}
