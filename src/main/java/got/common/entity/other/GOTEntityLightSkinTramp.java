package got.common.entity.other;

import got.common.database.GOTItems;
import got.common.database.GOTNames;
import got.common.item.other.GOTItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.awt.*;

public class GOTEntityLightSkinTramp extends GOTEntityTrampBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityLightSkinTramp(World world) {
		super(world);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		int weapon = rand.nextInt(4);
		if (weapon == 0 || weapon == 1 || weapon == 2) {
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.ironDagger));
		} else {
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.bronzeDagger));
		}
		npcItemsInv.setIdleItem(null);
		ItemStack hat = new ItemStack(GOTItems.leatherHat);
		float h = 0.06111111f;
		float s = MathHelper.randomFloatClamp(rand, 0.0f, 0.5f);
		float b = MathHelper.randomFloatClamp(rand, 0.0f, 0.5f);
		int hatColor = Color.HSBtoRGB(h, s, b) & 0xFFFFFF;
		GOTItemLeatherHat.setHatColor(hat, hatColor);
		if (rand.nextInt(3) == 0) {
			h = rand.nextFloat();
			s = MathHelper.randomFloatClamp(rand, 0.7f, 0.9f);
			b = MathHelper.randomFloatClamp(rand, 0.8f, 1.0f);
		} else {
			h = 0.0f;
			s = 0.0f;
			b = rand.nextFloat();
		}
		int featherColor = Color.HSBtoRGB(h, s, b) & 0xFFFFFF;
		GOTItemLeatherHat.setFeatherColor(hat, featherColor);
		setCurrentItemOrArmor(4, hat);
		return entityData;
	}

	@Override
	public void setupNPCName() {
		int i = rand.nextInt(4);
		switch (i) {
			case 0:
				familyInfo.setName(GOTNames.getWesterosName(rand, true));
				break;
			case 2:
				familyInfo.setName(GOTNames.getEssosName(rand, true));
				break;
			case 3:
				familyInfo.setName(GOTNames.getQarthName(rand, true));
				break;
			default:
				familyInfo.setName(GOTNames.getWildName(rand, true));
		}
	}
}