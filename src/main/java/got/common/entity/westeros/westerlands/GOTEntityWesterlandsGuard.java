package got.common.entity.westeros.westerlands;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityWesterlandsGuard extends GOTEntityWesterlandsMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityWesterlandsGuard(World world) {
		super(world);
		shield = GOTShields.WESTERLANDSGUARD;
		cape = GOTCapes.WESTERLANDS;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosPike));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.westerlandsguardBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.westerlandsguardLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.westerlandsguardChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.westerlandsguardHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
