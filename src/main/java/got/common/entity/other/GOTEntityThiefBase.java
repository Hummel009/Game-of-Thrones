package got.common.entity.other;

import got.common.database.GOTItems;
import got.common.database.GOTSpeech;
import got.common.item.other.GOTItemMug;
import got.common.quest.GOTMiniQuestFactory;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public abstract class GOTEntityThiefBase extends GOTEntityHumanBase implements GOTBiome.ImmuneToHeat, GOTBiome.ImmuneToFrost {
	protected GOTEntityThiefBase(World world) {
		super(world);
		questInfo.setOfferChance(1);
	}

	@Override
	public GOTMiniQuestFactory getMiniQuestFactory() {
		return GOTMiniQuestFactory.CRIMINAL;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(Items.bone, 1);
		}
		int coins = 10 + rand.nextInt(10) + rand.nextInt((i + 1) * 10);
		for (int l = 0; l < coins; ++l) {
			dropItem(GOTItems.coin, 1);
		}
		if (rand.nextInt(5) == 0) {
			entityDropItem(GOTItemMug.Vessel.SKULL.getEmptyVessel(), 0.0f);
		}
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		return GOTSpeech.getCriminalSpeech(npc, entityPlayer);
	}

	@Override
	public int getTotalArmorValue() {
		return 10;
	}
}