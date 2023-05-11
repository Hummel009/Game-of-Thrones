package got.common.entity.essos.qohor;

import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTRegistry;
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
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.essosPike));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.unsulliedBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.unsulliedLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.unsulliedChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.unsulliedHelmet));
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
