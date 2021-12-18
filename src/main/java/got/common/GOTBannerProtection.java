package got.common;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.mojang.authlib.GameProfile;

import got.GOT;
import got.common.database.GOTRegistry;
import got.common.entity.other.*;
import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class GOTBannerProtection {
	public static int MAX_RANGE = 64;
	public static Map<Pair, Integer> protectionBlocks = new HashMap<>();
	public static Map<UUID, Integer> lastWarningTimes;

	static {
		Pair<Block, Integer> BRONZE = Pair.of(GOTRegistry.blockMetal1, 2);
		Pair<Block, Integer> SILVER = Pair.of(GOTRegistry.blockMetal1, 3);
		Pair<Block, Integer> GOLD = Pair.of(Blocks.gold_block, 0);
		Pair<Block, Integer> VALYRIAN = Pair.of(GOTRegistry.blockMetal1, 4);
		protectionBlocks.put(BRONZE, 8);
		protectionBlocks.put(SILVER, 16);
		protectionBlocks.put(GOLD, 32);
		protectionBlocks.put(VALYRIAN, 64);
		lastWarningTimes = new HashMap<>();
	}

	public static IFilter anyBanner() {
		return new IFilter() {

			@Override
			public ProtectType protects(GOTEntityBanner banner) {
				if (banner.isStructureProtection()) {
					return ProtectType.STRUCTURE;
				}
				return ProtectType.FACTION;
			}

			@Override
			public void warnProtection(IChatComponent message) {
			}
		};
	}

	public static IFilter forFaction(GOTFaction theFaction) {
		return new IFilter() {

			@Override
			public ProtectType protects(GOTEntityBanner banner) {
				if (banner.isStructureProtection()) {
					return ProtectType.STRUCTURE;
				}
				if (banner.getBannerType().faction.isBadRelation(theFaction)) {
					return ProtectType.FACTION;
				}
				return ProtectType.NONE;
			}

			@Override
			public void warnProtection(IChatComponent message) {
			}
		};
	}

	public static IFilter forInvasionSpawner(GOTEntityInvasionSpawner spawner) {
		return GOTBannerProtection.forFaction(spawner.getInvasionType().invasionFaction);
	}

	public static IFilter forNPC(EntityLiving entity) {
		return new IFilter() {

			@Override
			public ProtectType protects(GOTEntityBanner banner) {
				if (banner.isStructureProtection()) {
					return ProtectType.STRUCTURE;
				}
				if (banner.getBannerType().faction.isBadRelation(GOT.getNPCFaction(entity))) {
					return ProtectType.FACTION;
				}
				return ProtectType.NONE;
			}

			@Override
			public void warnProtection(IChatComponent message) {
			}
		};
	}

	public static IFilter forPlayer(EntityPlayer entityplayer) {
		return GOTBannerProtection.forPlayer(entityplayer, Permission.FULL);
	}

	public static IFilter forPlayer(EntityPlayer entityplayer, Permission perm) {
		return new FilterForPlayer(entityplayer, perm);
	}

	public static IFilter forPlayer_returnMessage(EntityPlayer entityplayer, Permission perm, IChatComponent[] protectionMessage) {
		return new IFilter() {
			public IFilter internalPlayerFilter;
			{
				internalPlayerFilter = GOTBannerProtection.forPlayer(entityplayer, perm);
			}

			@Override
			public ProtectType protects(GOTEntityBanner banner) {
				return internalPlayerFilter.protects(banner);
			}

			@Override
			public void warnProtection(IChatComponent message) {
				internalPlayerFilter.warnProtection(message);
				protectionMessage[0] = message;
			}
		};
	}

	public static IFilter forThrown(EntityThrowable throwable) {
		return new IFilter() {

			@Override
			public ProtectType protects(GOTEntityBanner banner) {
				if (banner.isStructureProtection()) {
					return ProtectType.STRUCTURE;
				}
				EntityLivingBase thrower = throwable.getThrower();
				if (thrower == null) {
					return ProtectType.FACTION;
				}
				if (thrower instanceof EntityPlayer) {
					return GOTBannerProtection.forPlayer((EntityPlayer) thrower, Permission.FULL).protects(banner);
				}
				if (thrower instanceof EntityLiving) {
					return GOTBannerProtection.forNPC((EntityLiving) thrower).protects(banner);
				}
				return ProtectType.NONE;
			}

			@Override
			public void warnProtection(IChatComponent message) {
			}
		};
	}

	public static IFilter forTNT(EntityTNTPrimed bomb) {
		return new IFilter() {

			@Override
			public ProtectType protects(GOTEntityBanner banner) {
				if (banner.isStructureProtection()) {
					return ProtectType.STRUCTURE;
				}
				EntityLivingBase bomber = bomb.getTntPlacedBy();
				if (bomber == null) {
					return ProtectType.FACTION;
				}
				if (bomber instanceof EntityPlayer) {
					return GOTBannerProtection.forPlayer((EntityPlayer) bomber, Permission.FULL).protects(banner);
				}
				if (bomber instanceof EntityLiving) {
					return GOTBannerProtection.forNPC((EntityLiving) bomber).protects(banner);
				}
				return ProtectType.NONE;
			}

			@Override
			public void warnProtection(IChatComponent message) {
			}
		};
	}

	public static IFilter forTNTMinecart() {
		return new IFilter() {

			@Override
			public ProtectType protects(GOTEntityBanner banner) {
				if (banner.isStructureProtection()) {
					return ProtectType.STRUCTURE;
				}
				return ProtectType.FACTION;
			}

			@Override
			public void warnProtection(IChatComponent message) {
			}
		};
	}

	public static int getProtectionRange(Block block, int meta) {
		Integer i = protectionBlocks.get(Pair.of((Object) block, (Object) meta));
		if (i == null) {
			return 0;
		}
		return i;
	}

	public static boolean hasWarningCooldown(EntityPlayer entityplayer) {
		return lastWarningTimes.containsKey(entityplayer.getUniqueID());
	}

	public static boolean isProtected(World world, Entity entity, IFilter protectFilter, boolean sendMessage) {
		int i = MathHelper.floor_double(entity.posX);
		int j = MathHelper.floor_double(entity.boundingBox.minY);
		int k = MathHelper.floor_double(entity.posZ);
		return GOTBannerProtection.isProtected(world, i, j, k, protectFilter, sendMessage);
	}

	public static boolean isProtected(World world, int i, int j, int k, IFilter protectFilter, boolean sendMessage) {
		return GOTBannerProtection.isProtected(world, i, j, k, protectFilter, sendMessage, 0.0);
	}

	public static boolean isProtected(World world, int i, int j, int k, IFilter protectFilter, boolean sendMessage, double searchExtra) {
		if (!GOTConfig.allowBannerProtection) {
			return false;
		}
		String protectorName = null;
		AxisAlignedBB originCube = AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1).expand(searchExtra, searchExtra, searchExtra);
		AxisAlignedBB searchCube = originCube.expand(64.0, 64.0, 64.0);
		List banners = world.getEntitiesWithinAABB(GOTEntityBanner.class, searchCube);
		if (!banners.isEmpty()) {
			for (Object banner2 : banners) {
				ProtectType result;
				GOTEntityBanner banner = (GOTEntityBanner) (banner2);
				AxisAlignedBB protectionCube = banner.createProtectionCube();
				if (!banner.isProtectingTerritory() || !protectionCube.intersectsWith(searchCube) || !protectionCube.intersectsWith(originCube) || (result = protectFilter.protects(banner)) == ProtectType.NONE) {
					continue;
				}
				if (result == ProtectType.FACTION) {
					protectorName = banner.getBannerType().faction.factionName();
					break;
				}
				if (result == ProtectType.PLAYER_SPECIFIC) {
					GameProfile placingPlayer = banner.getPlacingPlayer();
					if (placingPlayer != null) {
						if (StringUtils.isBlank(placingPlayer.getName())) {
							MinecraftServer.getServer().func_147130_as().fillProfileProperties(placingPlayer, true);
						}
						protectorName = placingPlayer.getName();
						break;
					}
					protectorName = "?";
					break;
				}
				if (result != ProtectType.STRUCTURE) {
					continue;
				}
				protectorName = StatCollector.translateToLocal("got.chat.protectedStructure");
				break;
			}
		}
		if (protectorName != null) {
			if (sendMessage) {
				protectFilter.warnProtection(new ChatComponentTranslation("got.chat.protectedLand", protectorName));
			}
			return true;
		}
		return false;
	}

	public static void setWarningCooldown(EntityPlayer entityplayer) {
		lastWarningTimes.put(entityplayer.getUniqueID(), GOTConfig.bannerWarningCooldown);
	}

	public static void updateWarningCooldowns() {
		HashSet<UUID> removes = new HashSet<>();
		for (Map.Entry<UUID, Integer> e : lastWarningTimes.entrySet()) {
			UUID player = e.getKey();
			int time = e.getValue();
			e.setValue(--time);
			if (time > 0) {
				continue;
			}
			removes.add(player);
		}
		for (UUID player : removes) {
			lastWarningTimes.remove(player);
		}
	}

	public static class FilterForPlayer implements IFilter {
		public EntityPlayer thePlayer;
		public Permission thePerm;
		public boolean ignoreCreativeMode = false;

		public FilterForPlayer(EntityPlayer p, Permission perm) {
			thePlayer = p;
			thePerm = perm;
		}

		public FilterForPlayer ignoreCreativeMode() {
			ignoreCreativeMode = true;
			return this;
		}

		@Override
		public ProtectType protects(GOTEntityBanner banner) {
			if (thePlayer.capabilities.isCreativeMode && !ignoreCreativeMode) {
				return ProtectType.NONE;
			}
			if (banner.isStructureProtection()) {
				return ProtectType.STRUCTURE;
			}
			if (banner.isPlayerSpecificProtection()) {
				if (!banner.isPlayerWhitelisted(thePlayer, thePerm)) {
					return ProtectType.PLAYER_SPECIFIC;
				}
				return ProtectType.NONE;
			}
			if (!banner.isPlayerAllowedByFaction(thePlayer, thePerm)) {
				return ProtectType.FACTION;
			}
			return ProtectType.NONE;
		}

		@Override
		public void warnProtection(IChatComponent message) {
			if (thePlayer instanceof FakePlayer) {
				return;
			}
			if (thePlayer instanceof EntityPlayerMP && !thePlayer.worldObj.isRemote) {
				EntityPlayerMP entityplayermp = (EntityPlayerMP) thePlayer;
				entityplayermp.sendContainerToPlayer(thePlayer.inventoryContainer);
				if (!GOTBannerProtection.hasWarningCooldown(entityplayermp)) {
					entityplayermp.addChatMessage(message);
					GOTBannerProtection.setWarningCooldown(entityplayermp);
				}
			}
		}
	}

	public interface IFilter {
		ProtectType protects(GOTEntityBanner var1);

		void warnProtection(IChatComponent var1);
	}

	public enum Permission {
		FULL, DOORS, TABLES, CONTAINERS, PERSONAL_CONTAINERS, FOOD, BEDS, SWITCHES;

		public int bitFlag = 1 << ordinal();
		public String codeName = name();

		public static Permission forName(String s) {
			for (Permission p : Permission.values()) {
				if (!p.codeName.equals(s)) {
					continue;
				}
				return p;
			}
			return null;
		}
	}

	public enum ProtectType {
		NONE, FACTION, PLAYER_SPECIFIC, STRUCTURE;
	}
}