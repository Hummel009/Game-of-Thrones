package got.coremod;

import cpw.mods.fml.common.network.internal.FMLMessage;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.common.network.internal.FMLRuntimeCodec;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.GOTConfig;
import got.common.GOTLevelData;
import got.common.database.GOTBlocks;
import got.common.enchant.GOTEnchantmentHelper;
import got.common.entity.other.GOTEntityBanner;
import got.common.entity.other.GOTMountFunctions;
import got.common.item.GOTWeaponStats;
import got.common.util.GOTCommonIcons;
import got.common.util.GOTLog;
import got.common.util.GOTReflection;
import got.common.world.biome.GOTBiome;
import got.common.world.spawning.GOTSpawnerAnimals;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.command.IEntitySelector;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Random;

public class GOTReplacedMethods {

	public static class Anvil {
		public static AxisAlignedBB getCollisionBoundingBoxFromPool(Block block, World world, int i, int j, int k) {
			block.setBlockBoundsBasedOnState(world, i, j, k);
			return AxisAlignedBB.getBoundingBox(i + block.getBlockBoundsMinX(), j + block.getBlockBoundsMinY(), k + block.getBlockBoundsMinZ(), i + block.getBlockBoundsMaxX(), j + block.getBlockBoundsMaxY(), k + block.getBlockBoundsMaxZ());
		}
	}

	public static class Cauldron {
		public static int getRenderType() {
			if (GOT.proxy == null) {
				return 24;
			}
			return GOT.proxy.getVCauldronRenderID();
		}
	}

	public static class ClientPlayer {
		public static void horseJump(EntityClientPlayerMP entityplayer) {
			int jump = (int) (entityplayer.getHorseJumpPower() * 100.0f);
			Entity mount = entityplayer.ridingEntity;
			if (mount instanceof EntityHorse) {
				((EntityHorse) mount).setJumpPower(jump);
			}
			entityplayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(entityplayer, 6, jump));
		}
	}

	public static class Dirt {
		public static String nameIndex1 = "coarse";

		public static ItemStack createStackedBlock(Block thisBlock, int i) {
			Item item = Item.getItemFromBlock(thisBlock);
			return new ItemStack(item, 1, i);
		}

		public static int damageDropped(int i) {
			if (i == 1) {
				return 1;
			}
			return 0;
		}

		public static int getDamageValue(World world, int i, int j, int k) {
			return world.getBlockMetadata(i, j, k);
		}

		public static void getSubBlocks(Block thisBlock, Item item, CreativeTabs tab, List<ItemStack> list) {
			list.add(new ItemStack(thisBlock, 1, 0));
			list.add(new ItemStack(thisBlock, 1, 1));
			list.add(new ItemStack(thisBlock, 1, 2));
		}
	}

	public static class Enchants {
		public static boolean attemptDamageItem(ItemStack itemstack, int damages, Random random) {
			if (!itemstack.isItemStackDamageable()) {
				return false;
			}
			if (damages > 0) {
				int l;
				int unbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemstack);
				int negated = 0;
				if (unbreaking > 0) {
					for (l = 0; l < damages; ++l) {
						if (EnchantmentDurability.negateDamage(itemstack, unbreaking, random)) {
							++negated;
						}
					}
				}
				for (l = 0; l < damages; ++l) {
					if (GOTEnchantmentHelper.negateDamage(itemstack, random)) {
						++negated;
					}
				}
				damages -= negated;
				if (damages <= 0) {
					return false;
				}
			}
			itemstack.setItemDamage(itemstack.getItemDamage() + damages);
			return itemstack.getItemDamage() > itemstack.getMaxDamage();
		}

		public static int c_attemptDamageItem(int unmodified, ItemStack stack, int damages, Random random, EntityLivingBase elb) {
			int ret = unmodified;
			for (int i = 0; i < damages; ++i) {
				if (GOTEnchantmentHelper.negateDamage(stack, random)) {
					--ret;
				}
			}
			return ret;
		}

		public static float func_152377_a(float base, ItemStack itemstack, EnumCreatureAttribute creatureAttribute) {
			return base + GOTEnchantmentHelper.calcBaseMeleeDamageBoost(itemstack);
		}

		public static int getDamageReduceAmount(ItemStack itemstack) {
			return GOTWeaponStats.getArmorProtection(itemstack);
		}

		public static float getEnchantmentModifierLiving(float base, EntityLivingBase attacker, EntityLivingBase target) {
			return base + GOTEnchantmentHelper.calcEntitySpecificDamage(attacker.getHeldItem(), target);
		}

		public static int getFireAspectModifier(int base, EntityLivingBase entity) {
			return base + GOTEnchantmentHelper.calcFireAspectForMelee(entity.getHeldItem());
		}

		public static int getFortuneModifier(int base, EntityLivingBase entity) {
			return base + GOTEnchantmentHelper.calcLootingLevel(entity.getHeldItem());
		}

		public static int getKnockbackModifier(int base, EntityLivingBase attacker, EntityLivingBase target) {
			int i = base;
			i += GOTWeaponStats.getBaseExtraKnockback(attacker.getHeldItem());
			return i + GOTEnchantmentHelper.calcExtraKnockback(attacker.getHeldItem());
		}

		public static int getLootingotifier(int base, EntityLivingBase entity) {
			return base + GOTEnchantmentHelper.calcLootingLevel(entity.getHeldItem());
		}

		public static int getMaxFireProtectionLevel(int base, Entity entity) {
			return Math.max(base, GOTEnchantmentHelper.getMaxFireProtectionLevel(entity.getLastActiveItems()));
		}

		public static boolean getSilkTouchModifier(boolean base, EntityLivingBase entity) {
			boolean flag = base;
			if (GOTEnchantmentHelper.isSilkTouch(entity.getHeldItem())) {
				flag = true;
			}
			return flag;
		}

		public static int getSpecialArmorProtection(int base, ItemStack[] armor, DamageSource source) {
			int i = base;
			i += GOTEnchantmentHelper.calcSpecialArmorSetProtection(armor, source);
			return MathHelper.clamp_int(i, 0, 25);
		}

		public static boolean isPlayerMeleeKill(Entity entity, DamageSource source) {
			return entity instanceof EntityPlayer && source.getSourceOfDamage() == entity;
		}
	}

	public static class EntityPackets {
		public static Packet getMobSpawnPacket(Entity entity) {
			EntityRegistry.EntityRegistration er = EntityRegistry.instance().lookupModSpawn(entity.getClass(), false);
			if (er == null || er.usesVanillaSpawning()) {
				return null;
			}
			FMLMessage.EntitySpawnMessage msg = new FMLMessage.EntitySpawnMessage(er, entity, er.getContainer());
			ByteBuf data = Unpooled.buffer();
			data.writeByte(2);
			try {
				new FMLRuntimeCodec().encodeInto(null, msg, data);
			} catch (Exception e) {
				GOTLog.logger.error("**********************************************");
				GOTLog.logger.error("Hummel009: ERROR sending mob spawn packet to client!");
				GOTLog.logger.error("**********************************************");
			}
			return new FMLProxyPacket(data, "FML");
		}
	}

	public static class Fence {
		public static boolean canConnectFenceTo(IBlockAccess world, int i, int j, int k) {
			Block block = world.getBlock(i, j, k);
			if (block instanceof BlockFence || block instanceof BlockFenceGate || block instanceof BlockWall) {
				return true;
			}
			return block.getMaterial().isOpaque() && block.renderAsNormalBlock() && block.getMaterial() != Material.gourd;
		}

		public static boolean canPlacePressurePlate(Block block) {
			return block instanceof BlockFence;
		}
	}

	public static class Food {
		public static float getExhaustionFactor() {
			if (GOTConfig.changedHunger) {
				return 0.3f;
			}
			return 1.0f;
		}
	}

	public static class Grass {
		public static int MIN_GRASS_LIGHT = 4;
		public static int MAX_GRASS_OPACITY = 2;
		public static int MIN_SPREAD_LIGHT = 9;

		public static void updateTick(World world, int i, int j, int k, Random random) {
			if (!world.isRemote) {
				int checkRange = 1;
				if (!world.checkChunksExist(i - checkRange, j - checkRange, k - checkRange, i + checkRange, j + checkRange, k + checkRange)) {
					return;
				}
				if (world.getBlockLightValue(i, j + 1, k) < 4 && world.getBlockLightOpacity(i, j + 1, k) > 2) {
					Block block = world.getBlock(i, j, k);
					if (block == Blocks.grass) {
						world.setBlock(i, j, k, Blocks.dirt);
					}
					if (block == GOTBlocks.mudGrass) {
						world.setBlock(i, j, k, GOTBlocks.mud);
					}
				} else if (world.getBlockLightValue(i, j + 1, k) >= 9) {
					for (int l = 0; l < 4; ++l) {
						int j1;
						int k1;
						int i1 = i + random.nextInt(3) - 1;
						if (world.blockExists(i1, j1 = j + random.nextInt(5) - 3, k1 = k + random.nextInt(3) - 1) && world.checkChunksExist(i1 - checkRange, j1 - checkRange, k1 - checkRange, i1 + checkRange, j1 + checkRange, k1 + checkRange) && world.getBlockLightValue(i1, j1 + 1, k1) >= 4 && world.getBlockLightOpacity(i1, j1 + 1, k1) <= 2) {
							Block block = world.getBlock(i1, j1, k1);
							int meta = world.getBlockMetadata(i1, j1, k1);
							if (block == Blocks.dirt && meta == 0) {
								world.setBlock(i1, j1, k1, Blocks.grass, 0, 3);
							}
							if (block == GOTBlocks.mud && meta == 0) {
								world.setBlock(i1, j1, k1, GOTBlocks.mudGrass, 0, 3);
							}
						}
					}
				}
			}
		}
	}

	public static class MountFunctions {

		public static boolean canRiderControl_elseNoMotion(EntityLiving entity) {
			boolean flag = GOTMountFunctions.canRiderControl(entity);
			if (!flag && entity.riddenByEntity instanceof EntityPlayer && GOTMountFunctions.isMountControllable(entity)) {
				entity.motionX = 0.0;
				entity.motionY = 0.0;
				entity.motionZ = 0.0;
			}
			return flag;
		}
	}

	public static class NetHandlerClient {
		public static void handleEntityMovement(NetHandlerPlayClient handler, S14PacketEntity packet) {
			World world = GOT.proxy.getClientWorld();
			Entity entity = packet.func_149065_a(world);
			if (entity != null) {
				entity.serverPosX += packet.func_149062_c();
				entity.serverPosY += packet.func_149061_d();
				entity.serverPosZ += packet.func_149064_e();
				if (!GOTMountFunctions.isPlayerControlledMount(entity)) {
					double d0 = entity.serverPosX / 32.0;
					double d1 = entity.serverPosY / 32.0;
					double d2 = entity.serverPosZ / 32.0;
					float f = packet.func_149060_h() ? packet.func_149066_f() * 360 / 256.0f : entity.rotationYaw;
					float f1 = packet.func_149060_h() ? packet.func_149063_g() * 360 / 256.0f : entity.rotationPitch;
					entity.setPositionAndRotation2(d0, d1, d2, f, f1, 3);
				}
			}
		}

		public static void handleEntityTeleport(NetHandlerPlayClient handler, S18PacketEntityTeleport packet) {
			World world = GOT.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.func_149451_c());
			if (entity != null) {
				entity.serverPosX = packet.func_149449_d();
				entity.serverPosY = packet.func_149448_e();
				entity.serverPosZ = packet.func_149446_f();
				if (!GOTMountFunctions.isPlayerControlledMount(entity)) {
					double d0 = entity.serverPosX / 32.0;
					double d1 = entity.serverPosY / 32.0 + 0.015625;
					double d2 = entity.serverPosZ / 32.0;
					float f = packet.func_149450_g() * 360 / 256.0f;
					float f1 = packet.func_149447_h() * 360 / 256.0f;
					entity.setPositionAndRotation2(d0, d1, d2, f, f1, 3);
				}
			}
		}
	}

	public static class PathFinder {
		public static boolean isFenceGate(Block block) {
			return block instanceof BlockFenceGate;
		}

		public static boolean isWoodenDoor(Block block) {
			return block instanceof BlockDoor && block.getMaterial() == Material.wood;
		}
	}

	public static class Piston {
		public static boolean canPushBlock(Block block, World world, int i, int j, int k, boolean flag) {
			AxisAlignedBB bannerSearchBox = AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 4, k + 1);
			List<GOTEntityBanner> banners = world.selectEntitiesWithinAABB(GOTEntityBanner.class, bannerSearchBox, new IEntitySelector() {

				@Override
				public boolean isEntityApplicable(Entity entity) {
					GOTEntityBanner banner = (GOTEntityBanner) entity;
					return !banner.isDead && banner.isProtectingTerritory();
				}
			});
			if (!banners.isEmpty()) {
				return false;
			}
			return GOTReflection.canPistonPushBlock(block, world, i, j, k, flag);
		}

	}

	public static class Player {
		public static boolean canEat(EntityPlayer entityplayer, boolean forced) {
			if (entityplayer.capabilities.disableDamage) {
				return false;
			}
			if (forced || entityplayer.getFoodStats().needFood()) {
				return true;
			}
			boolean feastMode = GOTConfig.canAlwaysEat;
			if (entityplayer.worldObj.isRemote) {
				feastMode = GOTLevelData.clientside_thisServer_feastMode;
			}
			return feastMode && entityplayer.ridingEntity == null;
		}
	}

	public static class Potions {
		public static double getStrengthModifier(Potion thisPotion, int level, AttributeModifier modifier) {
			if (thisPotion.id == Potion.weakness.id) {
				return -0.5 * (level + 1);
			}
			return 0.5 * (level + 1);
		}
	}

	public static class Spawner {
		public static int performSpawning_optimised(WorldServer world, boolean hostiles, boolean peacefuls, boolean rareTick) {
			return GOTSpawnerAnimals.performSpawning(world, hostiles, peacefuls, rareTick);
		}
	}

	public static class StaticLiquid {
		public static boolean isFlammable(World world, int i, int j, int k) {
			return world.blockExists(i, j, k) && world.getBlock(i, j, k).getMaterial().getCanBurn();
		}

		public static void updateTick(Block thisBlock, World world, int i, int j, int k, Random random) {
			if (thisBlock.getMaterial() == Material.lava) {
				int tries = random.nextInt(3);
				for (int l = 0; l < tries; ++l) {
					if (world.blockExists(i += random.nextInt(3) - 1, ++j, k += random.nextInt(3) - 1)) {
						Block block = world.getBlock(i, j, k);
						if (block.getMaterial() == Material.air) {
							if (!isFlammable(world, i - 1, j, k) && !isFlammable(world, i + 1, j, k) && !isFlammable(world, i, j, k - 1) && !isFlammable(world, i, j, k + 1) && !isFlammable(world, i, j - 1, k) && !isFlammable(world, i, j + 1, k)) {
								continue;
							}
							world.setBlock(i, j, k, Blocks.fire);
							return;
						}
						if (!block.getMaterial().blocksMovement()) {
							continue;
						}
					}
					return;
				}
				if (tries == 0) {
					int i1 = i;
					int k1 = k;
					for (int l = 0; l < 3; ++l) {
						i = i1 + random.nextInt(3) - 1;
						if (world.blockExists(i, j, k = k1 + random.nextInt(3) - 1) && world.isAirBlock(i, j + 1, k) && isFlammable(world, i, j, k)) {
							world.setBlock(i, j + 1, k, Blocks.fire);
						}
					}
				}
			}
		}
	}

	public static class Stone {
		public static IIcon getIconSideMeta(Block block, IIcon defaultIcon, int side, int meta) {
			if (block == Blocks.stone && meta == 1000) {
				if (side == 1) {
					return Blocks.snow.getIcon(1, 0);
				}
				if (side != 0) {
					return GOTCommonIcons.iconStoneSnow;
				}
			}
			return defaultIcon;
		}

		public static IIcon getIconWorld(Block block, IBlockAccess world, int i, int j, int k, int side) {
			Material aboveMat;
			if (block == Blocks.stone && side != 0 && side != 1 && ((aboveMat = world.getBlock(i, j + 1, k).getMaterial()) == Material.snow || aboveMat == Material.craftedSnow)) {
				return GOTCommonIcons.iconStoneSnow;
			}
			return block.getIcon(side, world.getBlockMetadata(i, j, k));
		}
	}

	public static class Trapdoor {
		public static boolean canPlaceBlockOnSide(World world, int i, int j, int k, int side) {
			return true;
		}

		public static int getRenderType(Block block) {
			if (GOT.proxy != null) {
				return GOT.proxy.getTrapdoorRenderID();
			}
			return 0;
		}

		public static boolean isValidSupportBlock(Block block) {
			return true;
		}
	}

	public static class Wall {
		public static boolean canConnectWallTo(IBlockAccess world, int i, int j, int k) {
			return Fence.canConnectFenceTo(world, i, j, k);
		}
	}

}
