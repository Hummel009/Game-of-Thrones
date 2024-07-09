package got.common.entity.westeros.legendary.deco;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTSpeech;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntitySelyseBaratheon extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntitySelyseBaratheon(World world) {
		super(world);
		setupLegendaryNPC(true);
		faction = GOTFaction.DRAGONSTONE;
		alignmentBonus = 500.0f;
		killAchievement = GOTAchievement.killSelyseBaratheon;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.alloySteelDagger));
		npcItemsInv.setIdleItem(null);

		return entityData;
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		return GOTSpeech.getFatherGrigoriSpeech(npc, entityPlayer);
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(false);
	}
}