package got.client.effect;

import got.common.faction.GOTAlignmentBonusMap;
import got.common.faction.GOTFaction;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GOTEntityAlignmentBonus extends Entity {
	private final String name;
	private final GOTFaction mainFaction;
	private final float prevMainAlignment;
	private final GOTAlignmentBonusMap factionBonusMap;
	private final float conquestBonus;

	private int particleAge;
	private int particleMaxAge;

	public GOTEntityAlignmentBonus(World world, double d, double d1, double d2, String s, GOTFaction f, float pre, GOTAlignmentBonusMap fMap, float conqBonus) {
		super(world);
		setSize(0.5f, 0.5f);
		yOffset = height / 2.0f;
		setPosition(d, d1, d2);
		particleAge = 0;
		name = s;
		mainFaction = f;
		prevMainAlignment = pre;
		factionBonusMap = fMap;
		conquestBonus = conqBonus;
		calcMaxAge();
	}

	private void calcMaxAge() {
		float highestBonus = 0.0f;
		for (GOTFaction fac : factionBonusMap.getChangedFactions()) {
			float bonus = Math.abs(factionBonusMap.get(fac));
			if (bonus > highestBonus) {
				highestBonus = bonus;
			}
		}
		float conq = Math.abs(conquestBonus);
		if (conq > highestBonus) {
			highestBonus = conq;
		}
		particleMaxAge = 80;
		int extra = (int) (Math.min(1.0f, highestBonus / 50.0f) * 220.0f);
		particleMaxAge += extra;
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

	public int getParticleAge() {
		return particleAge;
	}

	public String getName() {
		return name;
	}

	public GOTFaction getMainFaction() {
		return mainFaction;
	}

	public float getPrevMainAlignment() {
		return prevMainAlignment;
	}

	public GOTAlignmentBonusMap getFactionBonusMap() {
		return factionBonusMap;
	}

	public float getConquestBonus() {
		return conquestBonus;
	}
}
