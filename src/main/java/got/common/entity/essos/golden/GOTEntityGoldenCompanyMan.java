package got.common.entity.essos.golden;

import got.common.database.GOTChestContents;
import got.common.database.GOTItems;
import got.common.database.GOTNames;
import got.common.database.GOTShields;
import got.common.entity.animal.GOTEntityHorse;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.iface.GOTNPCMount;
import got.common.world.biome.GOTBiome;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class GOTEntityGoldenCompanyMan extends GOTEntityHumanBase implements GOTBiome.ImmuneToHeat {

	@SuppressWarnings({"WeakerAccess", "unused"})
	protected GOTEntityGoldenCompanyMan(World world) {
		super(world);
	}

	@Override
	public GOTShields getShield() {
		return GOTShields.GOLDEN_COMPANY;
	}

	@Override
	public GOTNPCMount createMountToRide() {
		GOTEntityHorse horse = new GOTEntityHorse(worldObj);

		horse.setMountArmor(new ItemStack(GOTItems.ironHorseArmor));

		return horse;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(Items.bone, 1);
		}
		if (rand.nextInt(6) == 0) {
			dropChestContents(GOTChestContents.GOLDEN_COMPANY, 1, 2 + i);
		}
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(GOTNames.getEssosName(rand, familyInfo.isMale()));
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}