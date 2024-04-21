package got.common.entity.westeros.north.hillmen;

import got.common.entity.ai.GOTEntityAINearestAttackableTargetPatriot;
import net.minecraft.world.World;

public class GOTEntityNorthHillmanCannibal extends GOTEntityNorthHillman {
	public GOTEntityNorthHillmanCannibal(World world) {
		super(world);
		addTargetTasks(true, GOTEntityAINearestAttackableTargetPatriot.class);
	}

	@Override
	public int getTotalArmorValue() {
		return 12;
	}
}