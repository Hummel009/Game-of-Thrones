package got.common.entity.essos.yi_ti;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYiTiSoldier extends GOTEntityYiTiMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityYiTiSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
	}

	@Override
	public GOTShields getShield() {
		return GOTShields.YI_TI;
	}

	@Override
	public GOTCapes getCape() {
		return GOTCapes.YI_TI;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, false);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.yiTiBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.yiTiLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.yiTiChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.yiTiHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}