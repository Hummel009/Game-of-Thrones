package got.common.entity.essos.ghiscar;

import got.common.database.*;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityGhiscarUnsullied extends GOTEntityGhiscarLevyman {
	public GOTEntityGhiscarUnsullied(World world) {
		super(world);
		canBeMarried = false;
		spawnRidingHorse = false;
		npcCape = GOTCapes.UNSULLIED;
		npcShield = GOTShields.UNSULLIED;
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
		return GOTAchievement.KILL_UNSULLIED;
	}

	@Override
	public int getTotalArmorValue() {
		return 15;
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
