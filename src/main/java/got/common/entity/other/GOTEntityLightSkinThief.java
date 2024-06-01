package got.common.entity.other;

import got.common.database.GOTItems;
import got.common.database.GOTNames;
import got.common.item.other.GOTItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityLightSkinThief extends GOTEntityThiefBase {
	private static final ItemStack[] WEAPONS = {new ItemStack(GOTItems.ironDagger), new ItemStack(GOTItems.bronzeDagger), new ItemStack(Items.iron_axe), new ItemStack(GOTItems.bronzeAxe), new ItemStack(Items.stone_axe)};

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityLightSkinThief(World world) {
		super(world);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		int i = rand.nextInt(WEAPONS.length);
		npcItemsInv.setMeleeWeapon(WEAPONS[i].copy());
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		if (rand.nextInt(3) == 0) {
			ItemStack hat = new ItemStack(GOTItems.leatherHat);
			GOTItemLeatherHat.setHatColor(hat, 0);
			GOTItemLeatherHat.setFeatherColor(hat, 16777215);
			setCurrentItemOrArmor(4, hat);
		}
		return entityData;
	}

	@Override
	public void setupNPCName() {
		int i = rand.nextInt(4);
		switch (i) {
			case 0:
				familyInfo.setName(GOTNames.getWesterosName(rand, true));
				break;
			case 1:
				familyInfo.setName(GOTNames.getEssosName(rand, true));
				break;
			case 2:
				familyInfo.setName(GOTNames.getQarthName(rand, true));
				break;
			case 3:
				familyInfo.setName(GOTNames.getWildName(rand, true));
		}
	}
}