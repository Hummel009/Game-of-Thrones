package got.common;

import com.mojang.authlib.GameProfile;
import got.GOT;
import got.common.database.GOTBlocks;
import got.common.entity.other.inanimate.GOTEntityBanner;
import got.common.entity.other.inanimate.GOTEntityInvasionSpawner;
import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class GOTBannerProtection {
	private static final Map<Pair<Block, Integer>, Integer> PROTECTION_BLOCKS = new HashMap<>();
	private static final Map<UUID, Integer> LAST_WARNING_TIMES = new HashMap<>();

	static {
		Pair<Block, Integer> bronze = Pair.of(GOTBlocks.blockMetal1, 2);
		Pair<Block, Integer> silver = Pair.of(GOTBlocks.blockMetal1, 3);
		Pair<Block, Integer> golden = Pair.of(Blocks.gold_block, 0);
		Pair<Block, Integer> valyrian = Pair.of(GOTBlocks.blockMetal1, 4);
		PROTECTION_BLOCKS.put(bronze, 8);
		PROTECTION_BLOCKS.put(silver, 16);
		PROTECTION_BLOCKS.put(golden, 32);
		PROTECTION_BLOCKS.put(valyrian, 64);
	}

	private GOTBannerProtection() {
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
			@SuppressWarnings("EmptyMethod")
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
				if (banner.getBannerType().getFaction().isBadRelation(theFaction)) {
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
		return forFaction(spawner.getInvasionType().getInvasionFaction());
	}

	public static IFilter forNPC(EntityLiving entity) {
		return new IFilter() {

			@Override
			public ProtectType protects(GOTEntityBanner banner) {
				if (banner.isStructureProtection()) {
					return ProtectType.STRUCTURE;
				}
				if (banner.getBannerType().getFaction().isBadRelation(GOT.getNPCFaction(entity))) {
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
		return forPlayer(entityplayer, Permission.FULL);
	}

	public static IFilter forPlayer(EntityPlayer entityplayer, Permission perm) {
		return new FilterForPlayer(entityplayer, perm);
	}

	public static IFilter forPlayer_returnMessage(EntityPlayer entityplayer, Permission perm, IChatComponent[] protectionMessage) {
		return new IFilter() {
			private final IFilter internalPlayerFilter = forPlayer(entityplayer, perm);

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
					return forPlayer((EntityPlayer) thrower, Permission.FULL).protects(banner);
				}
				if (thrower instanceof EntityLiving) {
					return forNPC((EntityLiving) thrower).protects(banner);
				}
				return ProtectType.NONE;
			}

			@Override
			public void warnProtection(IChatComponent message) {
			}
		};
	}

	public static IFilter forDragonFireball(EntityPlayer dragonRider) {
		return new IFilter() {

			@Override
			public ProtectType protects(GOTEntityBanner banner) {
				if (banner.isStructureProtection()) {
					return ProtectType.STRUCTURE;
				}
				if (dragonRider == null) {
					return ProtectType.FACTION;
				}
				return forPlayer(dragonRider, Permission.FULL).protects(banner);
			}

			@Override
			@SuppressWarnings("EmptyMethod")
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
					return forPlayer((EntityPlayer) bomber, Permission.FULL).protects(banner);
				}
				if (bomber instanceof EntityLiving) {
					return forNPC((EntityLiving) bomber).protects(banner);
				}
				return ProtectType.NONE;
			}

			@Override
			@SuppressWarnings("EmptyMethod")
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
			@SuppressWarnings("EmptyMethod")
			public void warnProtection(IChatComponent message) {
			}
		};
	}

	public static int getProtectionRange(Block block, int meta) {
		Integer i = PROTECTION_BLOCKS.get(Pair.of((Object) block, (Object) meta));
		if (i == null) {
			return 0;
		}
		return i;
	}

	public static boolean isProtected(World world, Entity entity, IFilter protectFilter, boolean sendMessage) {
		int i = MathHelper.floor_double(entity.posX);
		int j = MathHelper.floor_double(entity.boundingBox.minY);
		int k = MathHelper.floor_double(entity.posZ);
		return isProtected(world, i, j, k, protectFilter, sendMessage);
	}

	public static boolean isProtected(World world, int i, int j, int k, IFilter protectFilter, boolean sendMessage) {
		return isProtected(world, i, j, k, protectFilter, sendMessage, 0.0);
	}

	public static boolean isProtected(World world, int i, int j, int k, IFilter protectFilter, boolean sendMessage, double searchExtra) {
		if (!GOTConfig.allowBannerProtection) {
			return false;
		}
		String protectorName = null;
		AxisAlignedBB originCube = AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1).expand(searchExtra, searchExtra, searchExtra);
		AxisAlignedBB searchCube = originCube.expand(64.0, 64.0, 64.0);
		List<GOTEntityBanner> banners = world.getEntitiesWithinAABB(GOTEntityBanner.class, searchCube);
		if (!banners.isEmpty()) {
			for (GOTEntityBanner banner : banners) {
				ProtectType result = protectFilter.protects(banner);
				AxisAlignedBB protectionCube = banner.createProtectionCube();
				if (banner.isProtectingTerritory() && protectionCube.intersectsWith(searchCube) && protectionCube.intersectsWith(originCube) && result != ProtectType.NONE) {

					if (result == ProtectType.FACTION) {
						protectorName = banner.getBannerType().getFaction().factionName();
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
					if (result == ProtectType.STRUCTURE) {
						protectorName = StatCollector.translateToLocal("got.chat.protectedStructure");
						break;
					}
				}
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

	public static void updateWarningCooldowns() {
		Collection<UUID> removes = new HashSet<>();
		for (Map.Entry<UUID, Integer> e : LAST_WARNING_TIMES.entrySet()) {
			UUID player = e.getKey();
			int time = e.getValue();
			time--;
			e.setValue(time);
			if (time <= 0) {
				removes.add(player);
			}
		}
		for (UUID player : removes) {
			LAST_WARNING_TIMES.remove(player);
		}
	}

	public enum Permission {
		FULL, DOORS, TABLES, CONTAINERS, PERSONAL_CONTAINERS, FOOD, BEDS, SWITCHES;

		private final int bitFlag = 1 << ordinal();
		private final String codeName = name();

		public static Permission forName(String s) {
			for (Permission p : values()) {
				if (p.codeName.equals(s)) {
					return p;
				}
			}
			return null;
		}

		public int getBitFlag() {
			return bitFlag;
		}

		public String getCodeName() {
			return codeName;
		}
	}

	public enum ProtectType {
		NONE, FACTION, PLAYER_SPECIFIC, STRUCTURE
	}

	public interface IFilter {
		ProtectType protects(GOTEntityBanner var1);

		void warnProtection(IChatComponent var1);
	}

	public static class FilterForPlayer implements IFilter {
		private final EntityPlayer thePlayer;
		private final Permission thePerm;

		private boolean ignoreCreativeMode;

		public FilterForPlayer(EntityPlayer p, Permission perm) {
			thePlayer = p;
			thePerm = perm;
		}

		private static boolean hasWarningCooldown(Entity entityplayer) {
			return LAST_WARNING_TIMES.containsKey(entityplayer.getUniqueID());
		}

		private static void setWarningCooldown(Entity entityplayer) {
			LAST_WARNING_TIMES.put(entityplayer.getUniqueID(), GOTConfig.bannerWarningCooldown);
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
				if (!hasWarningCooldown(entityplayermp)) {
					entityplayermp.addChatMessage(message);
					setWarningCooldown(entityplayermp);
				}
			}
		}
	}
}