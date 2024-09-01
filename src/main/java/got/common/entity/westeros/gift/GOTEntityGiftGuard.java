package got.common.entity.westeros.gift;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityGiftGuard extends GOTEntityGiftMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityGiftGuard(World world) {
		super(world);
		addTargetTasks(true);
	}

	@Override
	public GOTCapes getCape() {
		return GOTCapes.NIGHT;
	}

	@Override
	public float getReputationBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, false);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.giftBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.giftLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.giftChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.giftHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}