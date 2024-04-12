package got.common;

import net.minecraft.nbt.NBTTagCompound;

public class GOTPlayerQuestData {
	private GOTPlayerData playerData;
	private boolean givenFirstPouches;

	public GOTPlayerQuestData(GOTPlayerData pd) {
		playerData = pd;
	}

	public boolean getGivenFirstPouches() {
		return givenFirstPouches;
	}

	public void setGivenFirstPouches(boolean flag) {
		givenFirstPouches = flag;
		markDirty();
	}

	public void load(NBTTagCompound questData) {
		givenFirstPouches = questData.getBoolean("Pouches");
	}

	private void markDirty() {
		playerData.markDirty();
	}

	public void save(NBTTagCompound questData) {
		questData.setBoolean("Pouches", givenFirstPouches);
	}

	public GOTPlayerData getPlayerData() {
		return playerData;
	}

	public void setPlayerData(GOTPlayerData playerData) {
		this.playerData = playerData;
	}
}