package got.common.entity.westeros.north.hillmen;

import got.common.entity.ai.GOTEntityAINearestAttackableTargetPatriot;
import net.minecraft.world.World;

public class GOTEntityNorthHillmanCannibal extends GOTEntityNorthHillman {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityNorthHillmanCannibal(World world) {
		super(world);
		addTargetTasks(true, GOTEntityAINearestAttackableTargetPatriot.class);
	}

	@Override
	public int getTotalArmorValue() {
		return 12;
	}
}