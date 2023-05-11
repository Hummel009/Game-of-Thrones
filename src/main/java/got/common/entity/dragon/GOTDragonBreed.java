package got.common.entity.dragon;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.util.DamageSource;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.HashSet;
import java.util.Set;

public class GOTDragonBreed {

	public String name;
	public String skin;
	public int color;
	public Set<String> immunities = new HashSet<>();
	public Set<Block> breedBlocks = new HashSet<>();
	public Set<BiomeGenBase> biomes = new HashSet<>();

	public GOTDragonBreed(String name, String skin, int color) {
		this.name = name;
		this.skin = skin;
		this.color = color;
		addImmunity(DamageSource.drown);
		addImmunity(DamageSource.inWall);
		addImmunity(DamageSource.cactus);
	}

	public void addHabitatBiome(BiomeGenBase biome) {
		biomes.add(biome);
	}

	public void addHabitatBlock(Block block) {
		breedBlocks.add(block);
	}

	public void addImmunity(DamageSource dmg) {
		immunities.add(dmg.damageType);
	}

	public int getColor() {
		return color;
	}

	public float getColorB() {
		return (color & 0xFF) / 255f;
	}

	public float getColorG() {
		return (color >> 8 & 0xFF) / 255f;
	}

	public float getColorR() {
		return (color >> 16 & 0xFF) / 255f;
	}

	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEFINED;
	}

	public String getDeathSound(GOTEntityDragon dragon) {
		return "got.mob.enderdragon.death";
	}

	public String getHurtSound(GOTEntityDragon dragon) {
		return "mob.enderdragon.hit";
	}

	public String getLivingSound(GOTEntityDragon dragon) {
		if (dragon.getRNG().nextInt(3) == 0) {
			return "mob.enderdragon.growl";
		}
		return "got.mob.enderdragon.breathe";
	}

	public String getName() {
		return name;
	}

	public String getSkin() {
		return skin;
	}

	public boolean isHabitatBiome(BiomeGenBase biome) {
		return biomes.contains(biome);
	}

	public boolean isHabitatBlock(Block block) {
		return breedBlocks.contains(block);
	}

	public boolean isHabitatEnvironment(GOTEntityDragon dragon) {
		return false;
	}

	public boolean isImmuneToDamage(DamageSource dmg) {
		if (immunities.isEmpty()) {
			return false;
		}

		return immunities.contains(dmg.damageType);
	}

	public void onDeath(GOTEntityDragon dragon) {
	}

	public void onDisable(GOTEntityDragon dragon) {
	}

	public void onEnable(GOTEntityDragon dragon) {
	}

	public void onUpdate(GOTEntityDragon dragon) {
	}

	@Override
	public String toString() {
		return name;
	}
}
