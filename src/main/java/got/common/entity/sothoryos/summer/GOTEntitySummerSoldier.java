package got.common.entity.sothoryos.summer;

import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntitySummerSoldier extends GOTEntitySummerMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntitySummerSoldier(World world) {
		super(world);
		addTargetTasks(true);
	}

	@Override
	public GOTShields getShield() {
		return GOTShields.SUMMER;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, true);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.summerBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.summerLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.summerChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.summerHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}