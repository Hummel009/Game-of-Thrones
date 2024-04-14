package got.common.entity.animal;

import got.common.database.GOTItems;
import got.common.entity.other.GOTRandomSkinEntity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.world.World;

import java.util.UUID;

public class GOTEntityWhiteOryx extends GOTEntityGemsbok implements GOTRandomSkinEntity {
	private GOTEntityWhiteOryx(World world) {
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
				dropItem(GOTItems.deerCooked, 1);
				continue;
			}
			dropItem(GOTItems.deerRaw, 1);
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