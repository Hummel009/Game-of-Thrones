package got.common.entity.westeros.arryn;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityArrynGuard extends GOTEntityArrynMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityArrynGuard(World world) {
		super(world);
		shield = GOTShields.ARRYNGUARD;
		cape = GOTCapes.ARRYNGUARD;
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

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.arrynguardBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.arrynguardLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.arrynguardChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.arrynguardHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}