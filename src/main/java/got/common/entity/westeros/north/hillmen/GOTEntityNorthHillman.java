package got.common.entity.westeros.north.hillmen;

import got.common.database.GOTChestContents;
import got.common.database.GOTItems;
import got.common.database.GOTNames;
import got.common.entity.animal.GOTEntityWoolyRhino;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.iface.GOTNPCMount;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuestFactory;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityNorthHillman extends GOTEntityHumanBase implements GOTBiome.ImmuneToFrost {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityNorthHillman(World world) {
		super(world);
	}

	@Override
	public GOTNPCMount createMountToRide() {
		GOTEntityWoolyRhino rhino = new GOTEntityWoolyRhino(worldObj);
		rhino.setMountArmor(new ItemStack(GOTItems.rhinoArmor));
		return rhino;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(Items.bone, 1);
		}
		if (rand.nextInt(5) == 0) {
			dropChestContents(GOTChestContents.BEYOND_WALL, 1, 2 + i);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	@Override
	public GOTMiniQuestFactory getMiniQuestFactory() {
		return GOTMiniQuestFactory.NORTH;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.NORTH;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.ironDagger));
		npcItemsInv.setIdleItem(null);

		return entityData;
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(GOTNames.getWildName(rand, familyInfo.isMale()));
	}
}