package got.common.entity.essos.yi_ti;

import got.common.database.GOTChestContents;
import got.common.database.GOTItems;
import got.common.database.GOTNames;
import got.common.entity.animal.GOTEntityHorse;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.iface.GOTNPCMount;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuestFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYiTiMan extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityYiTiMan(World world) {
		super(world);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.YI_TI;
	}

	@Override
	public GOTMiniQuestFactory getMiniQuestFactory() {
		return GOTMiniQuestFactory.YI_TI;
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
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
			dropChestContents(GOTChestContents.YI_TI, 1, 2 + i);
		}
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
		familyInfo.setName(GOTNames.getYiTiName(rand, familyInfo.isMale()));
	}
}