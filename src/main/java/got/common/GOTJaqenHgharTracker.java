package got.common;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.Event;
import got.common.entity.essos.legendary.quest.GOTEntityJaqenHghar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.*;

public class GOTJaqenHgharTracker {
	private static final Map<UUID, Integer> ACTIVE_JAQEN_HGHARS = new HashMap<>();
	private static int spawnCooldown;

	private GOTJaqenHgharTracker() {
	}

	public static void addNewJaqenHghar(UUID id) {
		ACTIVE_JAQEN_HGHARS.put(id, 3600);
		markDirty();
	}

	public static boolean isJaqenHgharActive(UUID id) {
		return ACTIVE_JAQEN_HGHARS.containsKey(id) && ACTIVE_JAQEN_HGHARS.get(id) > 0;
	}

	public static void load(NBTTagCompound levelData) {
		ACTIVE_JAQEN_HGHARS.clear();
		NBTTagList jaqenHgharsTags = levelData.getTagList("JaqenHghars", 10);
		for (int i = 0; i < jaqenHgharsTags.tagCount(); ++i) {
			NBTTagCompound nbt = jaqenHgharsTags.getCompoundTagAt(i);
			try {
				UUID id = UUID.fromString(nbt.getString("ID"));
				int cd = nbt.getInteger("CD");
				ACTIVE_JAQEN_HGHARS.put(id, cd);
			} catch (Exception e) {
				FMLLog.severe("Error loading GOT data: invalid Jaqen Hghar");
				e.printStackTrace();
			}
		}
		if (levelData.hasKey("JHSpawnTick")) {
			spawnCooldown = levelData.getInteger("JHSpawnTick");
		} else {
			spawnCooldown = 2400;
		}
	}

	private static void markDirty() {
		GOTLevelData.markDirty();
	}

	public static void performSpawning(World world) {
		if (!ACTIVE_JAQEN_HGHARS.isEmpty()) {
			return;
		}
		if (!world.playerEntities.isEmpty()) {
			--spawnCooldown;
			if (spawnCooldown <= 0) {
				spawnCooldown = 2400;
				List<EntityPlayer> players = new ArrayList<EntityPlayer>(world.playerEntities);
				Collections.shuffle(players);
				Random rand = world.rand;
				block0:
				for (EntityPlayer entityplayer : players) {
					if (!GOTLevelData.getData(entityplayer).hasAnyJHQuest()) {
						for (int attempts = 0; attempts < 32; ++attempts) {
							int k;
							float angle = rand.nextFloat() * 3.1415927f * 2.0f;
							int r = MathHelper.getRandomIntegerInRange(rand, 4, 16);
							int i = MathHelper.floor_double(entityplayer.posX + r * MathHelper.cos(angle));
							int j = world.getHeightValue(i, k = MathHelper.floor_double(entityplayer.posZ + r * MathHelper.sin(angle)));
							if (j > 62 && world.getBlock(i, j - 1, k).isOpaqueCube() && !world.getBlock(i, j, k).isNormalCube() && !world.getBlock(i, j + 1, k).isNormalCube()) {
								GOTEntityJaqenHghar jaqenHghar = new GOTEntityJaqenHghar(world);
								jaqenHghar.setLocationAndAngles(i + 0.5, j, k + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
								jaqenHghar.setLiftSpawnRestrictions(true);
								jaqenHghar.setLiftBannerRestrictions(true);
								Event.Result canSpawn = ForgeEventFactory.canEntitySpawn(jaqenHghar, world, (float) jaqenHghar.posX, (float) jaqenHghar.posY, (float) jaqenHghar.posZ);
								if (canSpawn == Event.Result.ALLOW || canSpawn == Event.Result.DEFAULT && jaqenHghar.getCanSpawnHere()) {
									jaqenHghar.setLiftSpawnRestrictions(false);
									jaqenHghar.setLiftBannerRestrictions(false);
									world.spawnEntityInWorld(jaqenHghar);
									jaqenHghar.onSpawnWithEgg(null);
									addNewJaqenHghar(jaqenHghar.getUniqueID());
									jaqenHghar.arriveAt(entityplayer);
									break block0;
								}
							}
						}
					}
				}
			}
		}
	}

	public static void save(NBTTagCompound levelData) {
		NBTTagList jaqenHgharTags = new NBTTagList();
		for (Map.Entry<UUID, Integer> e : ACTIVE_JAQEN_HGHARS.entrySet()) {
			UUID id = e.getKey();
			int cd = e.getValue();
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("ID", id.toString());
			nbt.setInteger("CD", cd);
			jaqenHgharTags.appendTag(nbt);
		}
		levelData.setTag("JaqenHghars", jaqenHgharTags);
		levelData.setInteger("JHSpawnTick", spawnCooldown);
	}

	public static void setJaqenHgharActive(UUID id) {
		ACTIVE_JAQEN_HGHARS.computeIfPresent(id, (key, value) -> {
			markDirty();
			return 3600;
		});
	}

	public static void updateCooldowns() {
		Collection<UUID> removes = new HashSet<>();
		for (Map.Entry<UUID, Integer> id : ACTIVE_JAQEN_HGHARS.entrySet()) {
			int cd = id.getValue();
			cd--;
			ACTIVE_JAQEN_HGHARS.put(id.getKey(), cd);
			if (cd <= 0) {
				removes.add(id.getKey());
			}
		}
		if (!removes.isEmpty()) {
			for (UUID id : removes) {
				ACTIVE_JAQEN_HGHARS.remove(id);
			}
			markDirty();
		}
	}
}