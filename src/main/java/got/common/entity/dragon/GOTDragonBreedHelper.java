package got.common.entity.dragon;

import got.common.util.GOTLog;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class GOTDragonBreedHelper extends GOTDragonHelper {
	private static final String NBT_BREED = "Breed";
	private static final String NBT_BREED_POINTS = "breedPoints";
	private static final String DEFAULT_BREED = "body";
	private final GOTDragonBreedRegistry registry = GOTDragonBreedRegistry.getInstance();
	private final int dataIndex;
	private final Map<GOTDragonBreed, AtomicInteger> breedPoints = new HashMap<>();

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
			return registry.getBreedByName(DEFAULT_BREED);
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

		dataWatcher.updateObject(dataIndex, newBreed.getName());
	}

	public void inheritBreed(GOTEntityDragon parent1, GOTEntityDragon parent2) {
		breedPoints.get(parent1.getBreed()).addAndGet(1800 + rand.nextInt(1800));
		breedPoints.get(parent2.getBreed()).addAndGet(1800 + rand.nextInt(1800));
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
