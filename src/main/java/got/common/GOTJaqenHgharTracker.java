package got.common;

import java.util.*;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.Event;
import got.common.entity.essos.legendary.quest.GOTEntityJaqenHghar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class GOTJaqenHgharTracker {
	private static Map<UUID, Integer> activeJaqenHghars = new HashMap<>();
	private static int spawnCooldown;

	public static void addNewFaceless(UUID id) {
		activeJaqenHghars.put(id, 3600);
		GOTJaqenHgharTracker.markDirty();
	}

	public static boolean isFacelessActive(UUID id) {
		return activeJaqenHghars.containsKey(id) && activeJaqenHghars.get(id) > 0;
	}

	public static void load(NBTTagCompound levelData) {
		activeJaqenHghars.clear();
		NBTTagList greyWandererTags = levelData.getTagList("JaqenHghars", 10);
		for (int i = 0; i < greyWandererTags.tagCount(); ++i) {
			NBTTagCompound nbt = greyWandererTags.getCompoundTagAt(i);
			try {
				UUID id = UUID.fromString(nbt.getString("ID"));
				int cd = nbt.getInteger("CD");
				activeJaqenHghars.put(id, cd);
				continue;
			} catch (Exception e) {
				FMLLog.severe("Error loading GOT data: invalid Jaqen Hghar");
				e.printStackTrace();
			}
		}
		spawnCooldown = levelData.hasKey("JHSpawnTick") ? levelData.getInteger("JHSpawnTick") : 2400;
	}

	private static void markDirty() {
		GOTLevelData.markDirty();
	}

	public static void performSpawning(World world) {
		if (!activeJaqenHghars.isEmpty()) {
			return;
		}
		if (!world.playerEntities.isEmpty() && --spawnCooldown <= 0) {
			spawnCooldown = 2400;
			ArrayList players = new ArrayList(world.playerEntities);
			Collections.shuffle(players);
			Random rand = world.rand;
			block0: for (Object obj : players) {
				EntityPlayer entityplayer = (EntityPlayer) obj;
				if (GOTLevelData.getData(entityplayer).hasAnyJHQuest()) {
					continue;
				}
				for (int attempts = 0; attempts < 32; ++attempts) {
					int k;
					float angle = rand.nextFloat() * 3.1415927f * 2.0f;
					int r = MathHelper.getRandomIntegerInRange(rand, 4, 16);
					int i = MathHelper.floor_double(entityplayer.posX + r * MathHelper.cos(angle));
					int j = world.getHeightValue(i, k = MathHelper.floor_double(entityplayer.posZ + r * MathHelper.sin(angle)));
					if (j <= 62 || !world.getBlock(i, j - 1, k).isOpaqueCube() || world.getBlock(i, j, k).isNormalCube() || world.getBlock(i, j + 1, k).isNormalCube()) {
						continue;
					}
					GOTEntityJaqenHghar wanderer = new GOTEntityJaqenHghar(world);
					wanderer.setLocationAndAngles(i + 0.5, j, k + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
					wanderer.liftSpawnRestrictions = true;
					wanderer.liftBannerRestrictions = true;
					Event.Result canSpawn = ForgeEventFactory.canEntitySpawn(wanderer, world, (float) wanderer.posX, (float) wanderer.posY, (float) wanderer.posZ);
					if (canSpawn != Event.Result.ALLOW && (canSpawn != Event.Result.DEFAULT || !wanderer.getCanSpawnHere())) {
						continue;
					}
					wanderer.liftSpawnRestrictions = false;
					wanderer.liftBannerRestrictions = false;
					world.spawnEntityInWorld(wanderer);
					wanderer.onSpawnWithEgg(null);
					GOTJaqenHgharTracker.addNewFaceless(wanderer.getUniqueID());
					wanderer.arriveAt(entityplayer);
					break block0;
				}
			}
		}

	}

	public static void save(NBTTagCompound levelData) {
		NBTTagList greyWandererTags = new NBTTagList();
		for (Map.Entry<UUID, Integer> e : activeJaqenHghars.entrySet()) {
			UUID id = e.getKey();
			int cd = e.getValue();
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("ID", id.toString());
			nbt.setInteger("CD", cd);
			greyWandererTags.appendTag(nbt);
		}
		levelData.setTag("JaqenHghars", greyWandererTags);
		levelData.setInteger("JHSpawnTick", spawnCooldown);
	}

	public static void setFacelessActive(UUID id) {
		if (activeJaqenHghars.containsKey(id)) {
			activeJaqenHghars.put(id, 3600);
			GOTJaqenHgharTracker.markDirty();
		}
	}

	public static void updateCooldowns() {
		HashSet<UUID> removes = new HashSet<>();
		for (UUID id : activeJaqenHghars.keySet()) {
			int cd = activeJaqenHghars.get(id);
			cd--;
			activeJaqenHghars.put(id, cd);
			if (cd > 0) {
				continue;
			}
			removes.add(id);
		}
		if (!removes.isEmpty()) {
			for (UUID id : removes) {
				activeJaqenHghars.remove(id);
			}
			GOTJaqenHgharTracker.markDirty();
		}
	}
}
