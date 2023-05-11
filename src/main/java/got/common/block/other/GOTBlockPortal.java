package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.GOTConfig;
import got.common.GOTDimension;
import got.common.GOTLevelData;
import got.common.faction.GOTFaction;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import java.util.List;
import java.util.Random;

public abstract class GOTBlockPortal extends BlockContainer {
	public GOTFaction[] portalFactions;
	public Class teleporterClass;

	protected GOTBlockPortal(GOTFaction[] factions, Class c) {
		super(Material.portal);
		portalFactions = factions;
		teleporterClass = c;
	}

	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB aabb, List list, Entity entity) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return Blocks.portal.getIcon(i, j);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getItem(World world, int i, int j, int k) {
		return Item.getItemById(0);
	}

	public Teleporter getPortalTeleporter(WorldServer world) {
		for (Teleporter obj : world.customTeleporters) {
			if (!teleporterClass.isInstance(obj)) {
				continue;
			}
			return obj;
		}
		Teleporter teleporter = null;
		try {
			teleporter = (Teleporter) teleporterClass.getConstructor(WorldServer.class).newInstance(world);
		} catch (Exception e) {
			e.printStackTrace();
		}
		world.customTeleporters.add(teleporter);
		return teleporter;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	public abstract boolean isValidPortalLocation(World var1, int var2, int var3, int var4, boolean var5);

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		if (world.provider.dimensionId != 0 && world.provider.dimensionId != GOTDimension.GAME_OF_THRONES.dimensionID) {
			world.setBlockToAir(i, j, k);
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		if (!GOTConfig.enablePortals) {
			return;
		}
		if (entity instanceof EntityPlayer) {
			for (GOTFaction faction : portalFactions) {
				if (GOTLevelData.getData((EntityPlayer) entity).getAlignment(faction) < 1.0f) {
					continue;
				}
				if (entity.ridingEntity == null && entity.riddenByEntity == null) {
					setPlayerInPortal((EntityPlayer) entity);
				}
				return;
			}
		} else {
			for (GOTFaction faction : portalFactions) {
				if (GOT.getNPCFaction(entity).isBadRelation(faction)) {
					continue;
				}
				if (entity.ridingEntity == null && entity.riddenByEntity == null && entity.timeUntilPortal == 0) {
					transferEntity(entity, world);
				}
				return;
			}
		}
		if (!world.isRemote) {
			entity.setFire(4);
			entity.attackEntityFrom(DamageSource.inFire, 2.0f);
			world.playSoundAtEntity(entity, "random.fizz", 0.5f, 1.5f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.5f);
		}
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (random.nextInt(100) == 0) {
			world.playSound(i + 0.5, j + 0.5, k + 0.5, "portal.portal", 0.5f, random.nextFloat() * 0.4f + 0.8f, false);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k) {
		float f = 0.0625f;
		setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, f, 1.0f);
	}

	public abstract void setPlayerInPortal(EntityPlayer var1);

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int i, int j, int k, int side) {
		return side == 0 && super.shouldSideBeRendered(world, i, j, k, side);
	}

	public void transferEntity(Entity entity, World world) {
		if (!world.isRemote) {
			int dimension = 0;
			if (entity.dimension == 0) {
				dimension = GOTDimension.GAME_OF_THRONES.dimensionID;
			} else if (entity.dimension == GOTDimension.GAME_OF_THRONES.dimensionID) {
				dimension = 0;
			}
			GOT.transferEntityToDimension(entity, dimension, getPortalTeleporter(DimensionManager.getWorld(dimension)));
		}
	}
}
