package got.common.entity.animal;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class GOTEntityGorcrow extends GOTEntityBird {
	public static final float GORCROW_SCALE = 1.4f;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityGorcrow(World world) {
		super(world);
		setSize(width * GORCROW_SCALE, height * GORCROW_SCALE);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
	}

	@Override
	public String getBirdTextureDir() {
		return "gorcrow";
	}

	@Override
	public float getSoundPitch() {
		return super.getSoundPitch() * 0.75f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		setBirdType(GOTEntityBird.BirdType.CROW);
		return entityData;
	}
}