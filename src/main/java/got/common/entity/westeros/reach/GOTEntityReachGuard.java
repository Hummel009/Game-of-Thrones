package got.common.entity.westeros.reach;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityReachGuard extends GOTEntityReachMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityReachGuard(World world) {
		super(world);
		shield = GOTShields.REACHGUARD;
		cape = GOTCapes.REACH;
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

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.reachguardBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.reachguardLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.reachguardChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.reachguardHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
