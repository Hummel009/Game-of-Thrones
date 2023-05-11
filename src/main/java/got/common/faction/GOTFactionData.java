package got.common.faction;

import got.common.GOTPlayerData;
import net.minecraft.nbt.NBTTagCompound;

public class GOTFactionData {
	public GOTPlayerData playerData;
	public GOTFaction theFaction;
	public int npcsKilled;
	public int enemiesKilled;
	public int tradeCount;
	public int hireCount;
	public int miniQuestsCompleted;
	public float conquestEarned;
	public boolean hasConquestHorn;

	public GOTFactionData(GOTPlayerData data, GOTFaction faction) {
		playerData = data;
		theFaction = faction;
	}

	public void addConquest(float f) {
		conquestEarned += f;
		updateFactionData();
	}

	public void addEnemyKill() {
		++enemiesKilled;
		updateFactionData();
	}

	public void addHire() {
		++hireCount;
		updateFactionData();
	}

	public void addNPCKill() {
		++npcsKilled;
		updateFactionData();
	}

	public void addTrade() {
		++tradeCount;
		updateFactionData();
	}

	public void completeMiniQuest() {
		++miniQuestsCompleted;
		updateFactionData();
	}

	public float getConquestEarned() {
		return conquestEarned;
	}

	public int getEnemiesKilled() {
		return enemiesKilled;
	}

	public int getHireCount() {
		return hireCount;
	}

	public int getMiniQuestsCompleted() {
		return miniQuestsCompleted;
	}

	public int getNPCsKilled() {
		return npcsKilled;
	}

	public int getTradeCount() {
		return tradeCount;
	}

	public void load(NBTTagCompound nbt) {
		npcsKilled = nbt.getInteger("NPCKill");
		enemiesKilled = nbt.getInteger("EnemyKill");
		tradeCount = nbt.getInteger("Trades");
		hireCount = nbt.getInteger("Hired");
		miniQuestsCompleted = nbt.getInteger("MiniQuests");
		conquestEarned = nbt.getFloat("Conquest");
		hasConquestHorn = nbt.getBoolean("ConquestHorn");
	}

	public void save(NBTTagCompound nbt) {
		nbt.setInteger("NPCKill", npcsKilled);
		nbt.setInteger("EnemyKill", enemiesKilled);
		nbt.setInteger("Trades", tradeCount);
		nbt.setInteger("Hired", hireCount);
		nbt.setInteger("MiniQuests", miniQuestsCompleted);
		if (conquestEarned != 0.0f) {
			nbt.setFloat("Conquest", conquestEarned);
		}
		nbt.setBoolean("ConquestHorn", hasConquestHorn);
	}

	public void takeConquestHorn() {
		hasConquestHorn = true;
		updateFactionData();
	}

	public void updateFactionData() {
		playerData.updateFactionData(theFaction, this);
	}
}
