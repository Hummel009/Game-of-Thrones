package got.common.entity.essos.legendary.mercenary;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTSpeech;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.iface.GOTMercenary;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityDaarioNaharis extends GOTEntityHumanBase implements GOTMercenary {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityDaarioNaharis(World world) {
		super(world);
		addTargetTasks(true);
		setupLegendaryNPC(true);
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killDaarioNaharis;
	}

	@Override
	public float getMercAlignmentRequired() {
		return 0.0f;
	}

	@Override
	public int getMercBaseCost() {
		return GOTUnitTradeEntries.SOLDIER * 5;
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		if (isFriendlyAndAligned(entityPlayer)) {
			return "legendary/daario_friendly";
		}
		return GOTSpeech.HOSTILE;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.alloySteelScimitar));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}