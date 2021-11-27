package got.common.entity.dragon;

public class GOTDragonParticleHelper extends GOTDragonHelper {

	public GOTDragonParticleHelper(GOTEntityDragon dragon) {
		super(dragon);
	}

	@Override
	public void onDeathUpdate() {
		if (dragon.isClient() && !dragon.isEgg() && dragon.deathTime < dragon.getMaxDeathTime() - 20) {
			spawnBodyParticles("cloud", 4);
		}
	}

	public void spawnBodyParticle(String effect) {
		double ox, oy, oz;
		float s = dragon.getScale() * 1.2f;
		if ("explode".equals(effect)) {
			ox = rand.nextGaussian() * s;
			oy = rand.nextGaussian() * s;
			oz = rand.nextGaussian() * s;
		} else if ("cloud".equals(effect)) {
			ox = (rand.nextDouble() - 0.5) * 0.1;
			oy = rand.nextDouble() * 0.2;
			oz = (rand.nextDouble() - 0.5) * 0.1;
		} else if ("reddust".equals(effect)) {
			ox = 0.8;
			oy = 0;
			oz = 0.8;
		} else {
			ox = 0;
			oy = 0;
			oz = 0;
		}

		double x = dragon.posX + (rand.nextDouble() - 0.5) * dragon.width * s;
		double y = dragon.posY + (rand.nextDouble() - 0.5) * dragon.height * s;
		double z = dragon.posZ + (rand.nextDouble() - 0.5) * dragon.width * s;
		dragon.worldObj.spawnParticle(effect, x, y, z, ox, oy, oz);
	}

	public void spawnBodyParticles(String effect) {
		spawnBodyParticles(effect, 32);
	}

	public void spawnBodyParticles(String effect, int baseAmount) {
		int amount = (int) (baseAmount * dragon.getScale());
		for (int i = 0; i < amount; i++) {
			spawnBodyParticle(effect);
		}
	}
}
