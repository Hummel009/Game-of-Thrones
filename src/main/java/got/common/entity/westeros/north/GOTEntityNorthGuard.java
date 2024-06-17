package got.common.entity.westeros.north;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityNorthGuard extends GOTEntityNorthMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityNorthGuard(World world) {
		super(world);
		shield = GOTShields.NORTHGUARD;
		cape = GOTCapes.NORTHGUARD;
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

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.northguardBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.northguardLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.northguardChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.northguardHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
