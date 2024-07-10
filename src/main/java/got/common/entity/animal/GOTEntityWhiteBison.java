package got.common.entity.animal;

import got.common.database.GOTItems;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.world.World;

public class GOTEntityWhiteBison extends GOTEntityBison {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityWhiteBison(World world) {
		super(world);
	}

	@Override
	public EntityCow createChild(EntityAgeable entity) {
		return new GOTEntityWhiteBison(worldObj);
	}

	@Override
	public void dropHornItem() {
		dropItem(GOTItems.whiteBisonHorn, 1);
	}
}