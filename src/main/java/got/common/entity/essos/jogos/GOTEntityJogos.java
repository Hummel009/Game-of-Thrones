package got.common.entity.essos.jogos;

import got.common.database.GOTChestContents;
import got.common.database.GOTItems;
import got.common.database.GOTNames;
import got.common.entity.animal.GOTEntityZebra;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.iface.GOTNPCMount;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuestFactory;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityJogos extends GOTEntityHumanBase implements GOTBiome.ImmuneToHeat {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityJogos(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
	}

	@Override
	public GOTNPCMount createMountToRide() {
		return new GOTEntityZebra(worldObj);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(Items.bone, 1);
		}
		if (rand.nextInt(6) == 0) {
			dropChestContents(GOTChestContents.JOGOS, 1, 2 + i);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	@Override
	public GOTMiniQuestFactory getMiniQuestFactory() {
		return GOTMiniQuestFactory.JOGOS;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.JOGOS;
	}

	@Override
	public float getPoisonedArrowChance() {
		return 0.5f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupNomadWeaponSet(this, rand);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.jogosBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.jogosLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.jogosChestplate));

		return entityData;
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(GOTNames.getJogosName(rand, familyInfo.isMale()));
	}
}