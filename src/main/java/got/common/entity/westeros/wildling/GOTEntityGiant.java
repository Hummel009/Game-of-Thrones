package got.common.entity.westeros.wildling;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityGiantBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityGiant extends GOTEntityGiantBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityGiant(World world) {
		super(world);
		faction = GOTFaction.WILDLING;
		alignmentBonus = 10.0f;
		killAchievement = GOTAchievement.killGiant;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTItems.fur, 10);
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		if (isFriendly(entityPlayer)) {
			return "special/giant_friendly";
		}
		return "special/giant_hostile";
	}
}