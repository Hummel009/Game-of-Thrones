package got.common.entity.animal;

import got.common.database.GOTRegistry;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.world.World;

public class GOTEntityWhiteBison extends GOTEntityBison {
	public GOTEntityWhiteBison(World world) {
		super(world);
	}

	@Override
	public EntityCow createChild(EntityAgeable entity) {
		return new GOTEntityWhiteBison(worldObj);
	}

	@Override
	public void dropHornItem(boolean flag, int i) {
		dropItem(GOTRegistry.whiteBisonHorn, 1);
	}
}