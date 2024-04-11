package got.common.entity.dragon;

import got.common.util.GOTCrashHandler;
import got.common.util.GOTLog;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

public class GOTDragonBreedHelper extends GOTDragonHelper {
	public static int BLOCK_RANGE = 2;
	public static String NBT_BREED = "Breed";
	public static String NBT_BREED_POINTS = "breedPoints";
	public static String DEFAULT_BREED = "body";
	public GOTDragonBreedRegistry registry = GOTDragonBreedRegistry.getInstance();
	public int dataIndex;
	public Map<GOTDragonBreed, AtomicInteger> breedPoints = new HashMap<>();

	public GOTDragonBreedHelper(GOTEntityDragon dragon, int dataIndex) {
		super(dragon);

		this.dataIndex = dataIndex;
		if (dragon.isServer()) {

			for (GOTDragonBreed breed : registry.getBreeds()) {
				breedPoints.put(breed, new AtomicInteger());
			}
		}

		dataWatcher.addObject(dataIndex, DEFAULT_BREED);
	}

	public GOTDragonBreed getBreed() {
		String breedName = dataWatcher.getWatchableObjectString(dataIndex);

		GOTDragonBreed breed = registry.getBreedByName(breedName);
		if (breed == null) {
			breed = registry.getBreedByName(DEFAULT_BREED);
		}

		return breed;
	}

	public void setBreed(GOTDragonBreed newBreed) {
		GOTLog.getLogger().trace("setBreed({})", newBreed);

		if (newBreed == null) {
			throw new NullPointerException();
		}

		if (dragon.isClient()) {
			return;
		}

		GOTDragonBreed oldBreed = getBreed();
		if (oldBreed == newBreed) {
			return;
		}

		oldBreed.onDisable(dragon);
		newBreed.onEnable(dragon);

		dataWatcher.updateObject(dataIndex, newBreed.getName());
	}

	public void inheritBreed(GOTEntityDragon parent1, GOTEntityDragon parent2) {
		breedPoints.get(parent1.getBreed()).addAndGet(1800 + rand.nextInt(1800));
		breedPoints.get(parent2.getBreed()).addAndGet(1800 + rand.nextInt(1800));
	}

	@Override
	public void onDeath() {
		getBreed().onDeath(dragon);
	}

	@Override
	public void onLivingUpdate() {
		GOTDragonBreed currentBreed = getBreed();

		if (dragon.isEgg()) {

			if (dragon.isClient() && dragon.ticksExisted % 2 == 0 && !currentBreed.getName().equals(DEFAULT_BREED)) {
				double px = dragon.posX + (rand.nextDouble() - 0.5);
				double py = dragon.posY + (rand.nextDouble() - 0.5);
				double pz = dragon.posZ + (rand.nextDouble() - 0.5);
				dragon.worldObj.spawnParticle("reddust", px, py + 1, pz, currentBreed.getColorR(), currentBreed.getColorG(), currentBreed.getColorB());
			}

			if (dragon.isServer() && dragon.ticksExisted % 20 == 0) {

				int bx = MathHelper.floor_double(dragon.posX);
				int by = MathHelper.floor_double(dragon.posY);
				int bz = MathHelper.floor_double(dragon.posZ);

				for (int xn = -BLOCK_RANGE; xn <= BLOCK_RANGE; xn++) {
					for (int zn = -BLOCK_RANGE; zn <= BLOCK_RANGE; zn++) {
						for (int yn = -BLOCK_RANGE; yn <= BLOCK_RANGE; yn++) {
							Block block = dragon.worldObj.getBlock(bx + xn, by + yn, bz + zn);
							for (Entry<GOTDragonBreed, AtomicInteger> breed : breedPoints.entrySet()) {
								if (breed.getKey().isHabitatBlock(block)) {
									breed.getValue().incrementAndGet();
								}
							}
						}
					}
				}
				BiomeGenBase biome = GOTCrashHandler.getBiomeGenForCoords(dragon.worldObj, bx, bz);
				for (Entry<GOTDragonBreed, AtomicInteger> breed : breedPoints.entrySet()) {
					if (breed.getKey().isHabitatBiome(biome)) {
						breed.getValue().incrementAndGet();
					}
					if (breed.getKey().isHabitatEnvironment(dragon)) {
						breed.getValue().addAndGet(3);
					}
				}
				GOTDragonBreed newBreed = null;
				int maxPoints = 0;
				for (Map.Entry<GOTDragonBreed, AtomicInteger> breedPoint : breedPoints.entrySet()) {
					int points = breedPoint.getValue().get();
					if (points > maxPoints) {
						newBreed = breedPoint.getKey();
						maxPoints = points;
					}
				}
				if (newBreed != null) {
					setBreed(newBreed);
				}
			}
		}

		currentBreed.onUpdate(dragon);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {

		String breedName = nbt.getString(NBT_BREED);
		GOTDragonBreed newBreed = registry.getBreedByName(breedName);
		if (newBreed == null) {
			GOTLog.getLogger().warn("Dragon {} loaded with invalid breed type {}, using {} instead", dragon.getEntityId(), breedName, DEFAULT_BREED);
			newBreed = registry.getBreedByName(DEFAULT_BREED);
		}

		setBreed(newBreed);

		NBTTagCompound breedPointTag = nbt.getCompoundTag(NBT_BREED_POINTS);
		for (Map.Entry<GOTDragonBreed, AtomicInteger> breedPoint : breedPoints.entrySet()) {
			breedPoint.getValue().set(breedPointTag.getInteger(breedPoint.getKey().getName()));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString(NBT_BREED, getBreed().getName());

		NBTTagCompound breedPointTag = new NBTTagCompound();
		for (Map.Entry<GOTDragonBreed, AtomicInteger> breedPoint : breedPoints.entrySet()) {
			breedPointTag.setInteger(breedPoint.getKey().getName(), breedPoint.getValue().get());
		}
		nbt.setTag(NBT_BREED_POINTS, breedPointTag);
	}
}
