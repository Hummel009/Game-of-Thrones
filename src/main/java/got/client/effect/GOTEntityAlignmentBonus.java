package got.client.effect;

import got.common.faction.*;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GOTEntityAlignmentBonus extends Entity {
	private int particleAge;
	private int particleMaxAge;
	private String name;
	private GOTFaction mainFaction;
	private float prevMainAlignment;
	private GOTAlignmentBonusMap factionBonusMap;
	private float conquestBonus;

	public GOTEntityAlignmentBonus(World world, double d, double d1, double d2, String s, GOTFaction f, float pre, GOTAlignmentBonusMap fMap, boolean kill, boolean hiredKill, float conqBonus) {
		super(world);
		setSize(0.5f, 0.5f);
		yOffset = height / 2.0f;
		setPosition(d, d1, d2);
		setParticleAge(0);
		setName(s);
		setMainFaction(f);
		setPrevMainAlignment(pre);
		setFactionBonusMap(fMap);
		setConquestBonus(conqBonus);
		calcMaxAge();
	}

	public void calcMaxAge() {
		float highestBonus = 0.0f;
		for (GOTFaction fac : getFactionBonusMap().getChangedFactions()) {
			float bonus = Math.abs(getFactionBonusMap().get(fac));
			if ((bonus <= highestBonus)) {
				continue;
			}
			highestBonus = bonus;
		}
		float conq = Math.abs(getConquestBonus());
		if (conq > highestBonus) {
			highestBonus = conq;
		}
		particleMaxAge = 80;
		int extra = (int) (Math.min(1.0f, highestBonus / 50.0f) * 220.0f);
		particleMaxAge += extra;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public boolean canTriggerWalking() {
		return false;
	}

	@Override
	public void entityInit() {
	}

	public float getConquestBonus() {
		return conquestBonus;
	}

	public GOTAlignmentBonusMap getFactionBonusMap() {
		return factionBonusMap;
	}

	public GOTFaction getMainFaction() {
		return mainFaction;
	}

	public String getName() {
		return name;
	}

	public int getParticleAge() {
		return particleAge;
	}

	public float getPrevMainAlignment() {
		return prevMainAlignment;
	}

	@Override
	public boolean isEntityInvulnerable() {
		return true;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		setParticleAge(getParticleAge() + 1);
		if (getParticleAge() >= particleMaxAge) {
			setDead();
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
	}

	public void setConquestBonus(float conquestBonus) {
		this.conquestBonus = conquestBonus;
	}

	public void setFactionBonusMap(GOTAlignmentBonusMap factionBonusMap) {
		this.factionBonusMap = factionBonusMap;
	}

	public void setMainFaction(GOTFaction mainFaction) {
		this.mainFaction = mainFaction;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParticleAge(int particleAge) {
		this.particleAge = particleAge;
	}

	public void setPrevMainAlignment(float prevMainAlignment) {
		this.prevMainAlignment = prevMainAlignment;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
	}
}
