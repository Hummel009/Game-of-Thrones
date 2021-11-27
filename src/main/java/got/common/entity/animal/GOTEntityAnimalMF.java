package got.common.entity.animal;

import got.common.database.GOTRegistry;
import got.common.entity.other.GOTEntityRegistry;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class GOTEntityAnimalMF extends EntityAnimal implements GOTBiome.ImmuneToFrost {
	public GOTEntityAnimalMF(World world) {
		super(world);
	}

	@Override
	public boolean canMateWith(EntityAnimal mate) {
		GOTEntityAnimalMF mfMate = (GOTEntityAnimalMF) mate;
		if (mate == this) {
			return false;
		}
		if (getAnimalMFBaseClass().equals(mfMate.getAnimalMFBaseClass()) && isInLove() && mate.isInLove()) {
			boolean thisMale = isMale();
			return thisMale != mfMate.isMale();
		}
		return false;
	}

	public abstract Class getAnimalMFBaseClass();

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(GOTRegistry.spawnEgg, 1, GOTEntityRegistry.getEntityID(this));
	}

	public abstract boolean isMale();
}
