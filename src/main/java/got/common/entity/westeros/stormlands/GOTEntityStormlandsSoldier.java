package got.common.entity.westeros.stormlands;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityStormlandsSoldier extends GOTEntityStormlandsMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityStormlandsSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
		shield = GOTShields.STORMLANDS;
		cape = GOTCapes.STORMLANDS;
		alignmentBonus = 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, false);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.stormlandsBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.stormlandsLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.stormlandsChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.stormlandsHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
