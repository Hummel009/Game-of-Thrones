package got.common.entity.animal;

import got.common.world.biome.GOTBiome;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class GOTEntityGorcrow extends GOTEntityBird implements GOTBiome.ImmuneToFrost {
	public static float GORCROW_SCALE = 1.4f;

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
		data = super.onSpawnWithEgg(data);
		this.setBirdType(GOTEntityBird.BirdType.CROW);
		return data;
	}
}
