package got.common.tileentity;

import got.common.fellowship.GOTFellowship;
import got.common.fellowship.GOTFellowshipData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.chunk.Chunk;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class GOTTileEntityBeacon extends TileEntity {
	public int ticksExisted;
	public boolean isLit;
	public int litCounter;
	public int unlitCounter;
	public long stateChangeTime = -1L;
	public String beaconName;
	public UUID beaconFellowshipID;
	public List<EntityPlayer> editingPlayers = new ArrayList<>();

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound data = new NBTTagCompound();
		writeToNBT(data);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, data);
	}

	public boolean isFullyLit() {
		return isLit && litCounter == 100;
	}

	public boolean isLit() {
		return isLit;
	}

	public void setLit(boolean flag) {
		boolean wasLit = isLit;
		isLit = flag;
		if (!isLit) {
			litCounter = 0;
		} else {
			unlitCounter = 0;
		}
		updateLight();
		stateChangeTime = worldObj.getTotalWorldTime();
		if (wasLit && !isLit) {
			sendFellowshipMessage(false);
		}
	}

	@Override
	public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
		NBTTagCompound data = packet.func_148857_g();
		readFromNBT(data);
		updateLight();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		isLit = nbt.getBoolean("IsLit");
		litCounter = nbt.getByte("LitCounter");
		unlitCounter = nbt.getByte("UnlitCounter");
		stateChangeTime = nbt.getLong("StateChangeTime");
		beaconName = nbt.hasKey("BeaconName") ? nbt.getString("BeaconName") : null;
		beaconFellowshipID = nbt.hasKey("BeaconFellowship") ? UUID.fromString(nbt.getString("BeaconFellowship")) : null;
	}

	public void sendFellowshipMessage(boolean lit) {
		GOTFellowship fs;
		if (beaconFellowshipID != null && (fs = GOTFellowshipData.getFellowship(beaconFellowshipID)) != null && !fs.isDisbanded()) {
			String beaconMessageName = beaconName;
			if (StringUtils.isBlank(beaconMessageName)) {
				beaconMessageName = fs.getName();
			}
			ChatComponentTranslation message = new ChatComponentTranslation(lit ? "got.container.beacon.lit" : "got.container.beacon.unlit", beaconMessageName);
			message.getChatStyle().setColor(EnumChatFormatting.YELLOW);
			for (UUID player : fs.getAllPlayerUUIDs()) {
				EntityPlayer entityplayer = worldObj.func_152378_a(player);
				if (entityplayer == null) {
					continue;
				}
				entityplayer.addChatMessage(message);
			}
		}
	}

	@Override
	public void updateEntity() {
		++ticksExisted;
		if (!worldObj.isRemote) {
			if (isLit && litCounter < 100) {
				++litCounter;
				if (litCounter == 100) {
					updateLight();
					sendFellowshipMessage(true);
				}
			}
			if (!isLit && unlitCounter < 100) {
				++unlitCounter;
				if (unlitCounter == 100) {
					updateLight();
				}
			}
			if (ticksExisted % 10 == 0) {
				boolean spreadUnlit;
				boolean spreadLit = isLit && litCounter >= 100;
				spreadUnlit = !isLit && unlitCounter >= 100;
				if (spreadLit || spreadUnlit) {
					ArrayList<GOTTileEntityBeacon> nearbyTiles = new ArrayList<>();
					int range = 88;
					int chunkRange = range >> 4;
					int chunkX = xCoord >> 4;
					int chunkZ = zCoord >> 4;
					ChunkCoordinates coordsThis = new ChunkCoordinates(xCoord, yCoord, zCoord);
					for (int i1 = -chunkRange; i1 <= chunkRange; ++i1) {
						for (int k1 = -chunkRange; k1 <= chunkRange; ++k1) {
							Chunk chunk;
							int i2 = chunkX + i1;
							int k2 = chunkZ + k1;
							if (!worldObj.getChunkProvider().chunkExists(i2, k2) || (chunk = worldObj.getChunkFromChunkCoords(i2, k2)) == null) {
								continue;
							}
							for (Object obj : chunk.chunkTileEntityMap.values()) {
								TileEntity te = (TileEntity) obj;
								if (te.isInvalid() || !(te instanceof GOTTileEntityBeacon)) {
									continue;
								}
								GOTTileEntityBeacon beacon = (GOTTileEntityBeacon) te;
								if (coordsThis.getDistanceSquared(beacon.xCoord, beacon.yCoord, beacon.zCoord) > 6400.0f) {
									continue;
								}
								nearbyTiles.add(beacon);
							}
						}
					}
					if (spreadLit) {
						for (GOTTileEntityBeacon other : nearbyTiles) {
							if (other.isLit || stateChangeTime <= other.stateChangeTime) {
								continue;
							}
							other.setLit(true);
						}
					}
					if (spreadUnlit) {
						for (GOTTileEntityBeacon other : nearbyTiles) {
							if (!other.isLit || stateChangeTime <= other.stateChangeTime) {
								continue;
							}
							other.setLit(false);
						}
					}
				}
			}
		}
		HashSet<EntityPlayer> removePlayers = new HashSet<>();
		for (EntityPlayer entityplayer : editingPlayers) {
			if (!entityplayer.isDead) {
				continue;
			}
			removePlayers.add(entityplayer);
		}
		editingPlayers.removeAll(removePlayers);
	}

	public void updateLight() {
		worldObj.updateLightByType(EnumSkyBlock.Block, xCoord, yCoord, zCoord);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("IsLit", isLit);
		nbt.setByte("LitCounter", (byte) litCounter);
		nbt.setByte("UnlitCounter", (byte) unlitCounter);
		nbt.setLong("StateChangeTime", stateChangeTime);
		if (beaconName != null) {
			nbt.setString("BeaconName", beaconName);
		}
		if (beaconFellowshipID != null) {
			nbt.setString("BeaconFellowship", beaconFellowshipID.toString());
		}
	}
}
