package got.common.entity.westeros.riverlands;

import got.common.database.GOTChestContents;
import got.common.database.GOTItems;
import got.common.database.GOTNames;
import got.common.entity.animal.GOTEntityHorse;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.iface.GOTNPCMount;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuestFactory;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityRiverlandsMan extends GOTEntityHumanBase implements GOTBiome.ImmuneToFrost {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityRiverlandsMan(World world) {
		super(world);
	}

	@Override
	public GOTNPCMount createMountToRide() {
		GOTEntityHorse horse = new GOTEntityHorse(worldObj);
		horse.setMountArmor(new ItemStack(GOTItems.westerosHorseArmor));
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
			dropChestContents(GOTChestContents.RIVERLANDS, 1, 2 + i);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	@Override
	public GOTMiniQuestFactory getMiniQuestFactory() {
		return GOTMiniQuestFactory.RIVERLANDS;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.RIVERLANDS;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosDagger));
		npcItemsInv.setIdleItem(null);

		return entityData;
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(GOTNames.getWesterosName(rand, familyInfo.isMale()));
	}
}
