package got.common.entity.dragon;

import net.minecraft.util.DamageSource;

import java.util.Collection;
import java.util.HashSet;

public class GOTDragonBreed {
	private final Collection<String> immunities = new HashSet<>();
	private final String name;
	private final String skin;
	private final int color;

	public GOTDragonBreed(String name, String skin, int color) {
		this.name = name;
		this.skin = skin;
		this.color = color;
		addImmunity(DamageSource.drown);
		addImmunity(DamageSource.inWall);
		addImmunity(DamageSource.cactus);
	}

	private void addImmunity(DamageSource dmg) {
		immunities.add(dmg.damageType);
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

	public String getName() {
		return name;
	}

	public String getSkin() {
		return skin;
	}

	public boolean isImmuneToDamage(DamageSource dmg) {
		return !immunities.isEmpty() && immunities.contains(dmg.damageType);
	}

	@Override
	public String toString() {
		return name;
	}
}