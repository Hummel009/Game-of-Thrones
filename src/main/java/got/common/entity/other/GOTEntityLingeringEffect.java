package got.common.entity.other;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import got.common.database.GOTItems;
import got.common.item.weapon.GOTItemLingeringPotion;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import java.util.List;

public class GOTEntityLingeringEffect extends Entity implements IEntityAdditionalSpawnData {
	public static int TICKS_DATA_WATCHER = 10;
	public static int WIDTH_DATA_WATCHER = 11;
	public static int HEIGHT_DATA_WATCHER = 12;
	public EntityLivingBase thrower;
	public ItemStack stack;
	public int MAX_TICKS = 30 * 20;

	public GOTEntityLingeringEffect(World world) {
		super(world);
		yOffset = 0;
		setSize(1, 1);
	}

	public GOTEntityLingeringEffect(World world, GOTEntityLingeringPotion potion) {
		this(world, potion.getStack(), potion.getThrower());
		setPosition(potion.posX, potion.posY, potion.posZ);
	}

	public GOTEntityLingeringEffect(World world, ItemStack stack, EntityLivingBase thrower) {
		this(world);
		this.stack = stack;
		this.thrower = thrower;
	}

	@Override
	public void addVelocity(double x, double y, double z) {
	}

	@Override
	public void applyEntityCollision(Entity e) {
		if (!(e instanceof EntityLivingBase)) {
			return;
		}
		EntityLivingBase entity = (EntityLivingBase) e;
		List<PotionEffect> effects = ((GOTItemLingeringPotion) GOTItems.lingeringPotion).getEffects(stack);
		boolean addedEffect = false;
		for (PotionEffect effect : effects) {
			int effectID = effect.getPotionID();
			if (Potion.potionTypes[effectID].isInstant()) {
				Potion.potionTypes[effectID].affectEntity(thrower, entity, effect.getAmplifier(), 0.25);
				addedEffect = true;
			} else if (!entity.isPotionActive(effectID)) {
				entity.addPotionEffect(effect);
				addedEffect = true;
			}
		}
		if (addedEffect) {
			dataWatcher.getWatchableObjectInt(TICKS_DATA_WATCHER);
		}
	}

	@Override
	public boolean canBePushed() {
		return true;
	}

	@Override
	public void entityInit() {
		dataWatcher.addObject(TICKS_DATA_WATCHER, 0);
		dataWatcher.addObject(WIDTH_DATA_WATCHER, 6.0F);
		dataWatcher.addObject(HEIGHT_DATA_WATCHER, 0.5F);
	}

	@Override
	public void moveEntity(double x, double y, double z) {
	}

	@Override
	public void onUpdate() {
		int ticks = dataWatcher.getWatchableObjectInt(TICKS_DATA_WATCHER);
		if (worldObj.isRemote) {
			float w = dataWatcher.getWatchableObjectFloat(WIDTH_DATA_WATCHER);
			if (w != width) {
				width = w;
			}
			float h = dataWatcher.getWatchableObjectFloat(HEIGHT_DATA_WATCHER);
			if (h != height) {
				height = h;
			}
			if (ticksExisted % 5 == 0) {
				double radius = 3 * ((double) (MAX_TICKS - ticks) / MAX_TICKS);
				int colour = stack.getItem().getColorFromItemStack(stack, 0);
				float red = (colour >> 16 & 255) / 255.0F;
				float green = (colour >> 8 & 255) / 255.0F;
				float blue = (colour & 255) / 255.0F;
				for (int i = 0; i < 30; i++) {
					float variation = 0.75F + rand.nextFloat() * 0.25F;
					worldObj.spawnParticle("mobSpell", posX - radius + rand.nextFloat() * radius * 2, posY, posZ - radius + rand.nextFloat() * radius * 2, red * variation, green * variation, blue * variation);
				}
			}
			return;
		}
		ticks++;
		setTickCount(ticks);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		setTickCount(nbt.getInteger("Ticks"));
		stack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Potion"));
		if (stack == null) {
			setDead();
		}
	}

	@Override
	public void readSpawnData(ByteBuf buffer) {
		stack = ByteBufUtils.readItemStack(buffer);
	}

	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		dataWatcher.updateObject(WIDTH_DATA_WATCHER, this.width);
		dataWatcher.updateObject(HEIGHT_DATA_WATCHER, this.height);
	}

	public void setTickCount(int ticks) {
		dataWatcher.updateObject(TICKS_DATA_WATCHER, ticks);
		if (ticks >= MAX_TICKS) {
			setDead();
			return;
		}
		double radius = 3 * ((double) (MAX_TICKS - ticks) / MAX_TICKS);
		setSize((float) radius * 2, 0.5F);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("Ticks", dataWatcher.getWatchableObjectInt(TICKS_DATA_WATCHER));
		if (stack != null) {
			nbt.setTag("Potion", stack.writeToNBT(new NBTTagCompound()));
		}
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		ByteBufUtils.writeItemStack(buffer, stack);
	}
}