package got.client.effect;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GOTEntitySwordCommandMarker extends Entity {
	private final int particleMaxAge;

	private int particleAge;

	public GOTEntitySwordCommandMarker(World world, double d, double d1, double d2) {
		super(world);
		setSize(0.5f, 0.5f);
		yOffset = height / 2.0f;
		setPosition(d, d1, d2);
		particleAge = 0;
		particleMaxAge = 30;
	}

	@Override
	public boolean canTriggerWalking() {
		return false;
	}

	@Override
	public void entityInit() {
	}

	@Override
	public boolean isEntityInvulnerable() {
		return true;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		posY -= 0.35;
		++particleAge;
		if (particleAge >= particleMaxAge) {
			setDead();
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
	}
}