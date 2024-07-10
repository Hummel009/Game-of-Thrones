package got.common.entity.essos.ghiscar;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetPatriot;
import got.common.entity.other.utils.GOTEntityUtils;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityGhiscarHarpy extends GOTEntityGhiscarMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityGhiscarHarpy(World world) {
		super(world);
		addTargetTasks(true, GOTEntityAINearestAttackableTargetPatriot.class);
	}

	@Override
	public boolean isSpawnsInDarkness() {
		return true;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killGhiscarHarpy;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.ironDaggerPoisoned));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		GOTEntityUtils.setupLeathermanArmorNoHelmet(this, rand);

		setCurrentItemOrArmor(4, new ItemStack(GOTItems.harpy));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}