package got.common.entity.westeros.legendary.warrior;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityThreeEyedRaven extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityThreeEyedRaven(World world) {
		super(world);
		addTargetTasks(true);
		setupLegendaryNPC(true);
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killThreeEyedRaven;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTItems.blackfyre, 1);
		dropItem(GOTItems.brightroar, 1);
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		return "legendary/ter";
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}