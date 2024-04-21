package got.common.entity.westeros.legendary.warrior;

import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityHumanBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityThreeEyedRaven extends GOTEntityHumanBase {
	public GOTEntityThreeEyedRaven(World world) {
		super(world);
		addTargetTasks(true);
		setupLegendaryNPC(true);
		setSize(0.6f, 1.8f);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.1);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTItems.blackfyre, 1);
		dropItem(GOTItems.brightroar, 1);
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		return "legendary/ter";
	}

	@Override
	public int getTotalArmorValue() {
		return 15;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}