package got.common.entity.essos.gold;

import got.common.database.GOTItems;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityGoldenWarrior extends GOTEntityGoldenMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityGoldenWarrior(World world) {
		super(world);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.goldBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.goldLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.goldChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.goldHelmet));
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}