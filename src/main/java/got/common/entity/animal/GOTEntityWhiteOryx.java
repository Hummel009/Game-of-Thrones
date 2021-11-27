package got.common.entity.animal;

import java.util.UUID;

import got.common.database.GOTRegistry;
import got.common.entity.other.GOTRandomSkinEntity;
import net.minecraft.entity.*;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public class GOTEntityWhiteOryx extends GOTEntityGemsbok implements GOTRandomSkinEntity {
	public static float ORYX_SCALE = 0.9f;

	public GOTEntityWhiteOryx(World world) {
		super(world);
		setSize(width * 0.9f, height * 0.9f);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entity) {
		return new GOTEntityWhiteOryx(worldObj);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int hide = rand.nextInt(3) + rand.nextInt(1 + i);
		for (int l = 0; l < hide; ++l) {
			dropItem(Items.leather, 1);
		}
		int meat = rand.nextInt(3) + rand.nextInt(1 + i);
		for (int l = 0; l < meat; ++l) {
			if (isBurning()) {
				dropItem(GOTRegistry.deerCooked, 1);
				continue;
			}
			dropItem(GOTRegistry.deerRaw, 1);
		}
	}

	@Override
	public float getGemsbokSoundPitch() {
		return 0.9f;
	}

	@Override
	public void setUniqueID(UUID uuid) {
		entityUniqueID = uuid;
	}
}
