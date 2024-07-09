package got.common.entity.westeros.riverlands;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityRiverlandsSoldier extends GOTEntityRiverlandsMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityRiverlandsSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
		shield = GOTShields.RIVERLANDS;
		cape = GOTCapes.RIVERLANDS;
		alignmentBonus = 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, false);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.riverlandsBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.riverlandsLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.riverlandsChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.riverlandsHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
