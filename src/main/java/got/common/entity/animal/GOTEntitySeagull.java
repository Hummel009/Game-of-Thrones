package got.common.entity.animal;

import net.minecraft.block.material.Material;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class GOTEntitySeagull extends GOTEntityBird {
	public static float SEAGULL_SCALE = 1.4f;

	public GOTEntitySeagull(World world) {
		super(world);
		setSize(width * SEAGULL_SCALE, height * SEAGULL_SCALE);
	}

	@Override
	public boolean canBirdSpawnHere() {
		if (GOTAmbientSpawnChecks.canSpawn(this, 8, 4, 40, 4, Material.leaves, Material.sand)) {
			double range = 16.0;
			List<GOTEntitySeagull> nearbyGulls = worldObj.getEntitiesWithinAABB(GOTEntitySeagull.class, boundingBox.expand(range, range, range));
			return nearbyGulls.size() < 2;
		}
		return false;
	}

	@Override
	public boolean canStealItems() {
		return true;
	}

	@Override
	public String getBirdTextureDir() {
		return "seagull";
	}

	@Override
	public String getDeathSound() {
		return "got:bird.seagull.hurt";
	}

	@Override
	public String getHurtSound() {
		return "got:bird.seagull.hurt";
	}

	@Override
	public String getLivingSound() {
		return "got:bird.seagull.say";
	}

	@Override
	public boolean isStealable(ItemStack itemstack) {
		Item item = itemstack.getItem();
		return item == Items.fish || item == Items.cooked_fished || super.isStealable(itemstack);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData data1 = super.onSpawnWithEgg(data);
		setBirdType(GOTEntityBird.BirdType.COMMON);
		return data1;
	}
}
