package got.common.entity.animal;

import got.common.database.GOTRegistry;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.MathHelper;
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

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(boundingBox.minY);
			int k = MathHelper.floor_double(posZ);
			if (j > 62 && j < 140 && worldObj.getBlock(i, j - 1, k) == worldObj.getBiomeGenForCoords(i, k).topBlock) {
				return true;
			}
		}
		return false;
	}
}