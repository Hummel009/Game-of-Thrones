package got.common.entity.dragon;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.util.DamageSource;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Collection;
import java.util.HashSet;

public class GOTDragonBreed {
	public String name;
	public String skin;
	public int color;
	public Collection<String> immunities = new HashSet<>();
	public Collection<Block> breedBlocks = new HashSet<>();
	public Collection<BiomeGenBase> biomes = new HashSet<>();

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
		return (color & 0xFF) / 255.0f;
	}

	public float getColorG() {
		return (color >> 8 & 0xFF) / 255.0f;
	}

	public float getColorR() {
		return (color >> 16 & 0xFF) / 255.0f;
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
		return !immunities.isEmpty() && immunities.contains(dmg.damageType);

	}

	@SuppressWarnings("all")
	public void onDeath(GOTEntityDragon dragon) {
	}

	@SuppressWarnings("all")
	public void onDisable(GOTEntityDragon dragon) {
	}

	@SuppressWarnings("all")
	public void onEnable(GOTEntityDragon dragon) {
	}

	@SuppressWarnings("all")
	public void onUpdate(GOTEntityDragon dragon) {
	}

	@Override
	public String toString() {
		return name;
	}
}
