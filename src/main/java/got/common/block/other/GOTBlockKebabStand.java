package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTCreativeTabs;
import got.common.item.other.GOTItemKebabStand;
import got.common.tileentity.GOTTileEntityKebabStand;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.ArrayList;

public class GOTBlockKebabStand extends BlockContainer {
	private final String standTextureName;

	public GOTBlockKebabStand(String s) {
		super(Material.circuits);
		standTextureName = s;
		setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
		setHardness(0.0f);
		setResistance(1.0f);
		setStepSound(soundTypeWood);
		setCreativeTab(GOTCreativeTabs.tabUtil);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new GOTTileEntityKebabStand();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<>();
		if ((meta & 8) == 0) {
			ItemStack itemstack = getKebabStandDrop(world, i, j, k);
			GOTTileEntityKebabStand kebabStand = (GOTTileEntityKebabStand) world.getTileEntity(i, j, k);
			if (kebabStand != null) {
				drops.add(itemstack);
			}
		}
		return drops;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return Blocks.planks.getIcon(i, 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getItemIconName() {
		return getTextureName();
	}

	private ItemStack getKebabStandDrop(World world, int i, int j, int k) {
		ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
		GOTTileEntityKebabStand kebabStand = (GOTTileEntityKebabStand) world.getTileEntity(i, j, k);
		if (kebabStand != null) {
			GOTItemKebabStand.setKebabData(itemstack, kebabStand);
		}
		return itemstack;
	}

	@Override
	@SuppressWarnings("deprecation")
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k) {
		world.markBlockForUpdate(i, j, k);
		return getKebabStandDrop(world, i, j, k);
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	public String getStandTextureName() {
		return standTextureName;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity instanceof GOTTileEntityKebabStand) {
			GOTTileEntityKebabStand stand = (GOTTileEntityKebabStand) tileentity;
			ItemStack heldItem = entityplayer.getHeldItem();
			if (!stand.isCooked() && stand.isMeat(heldItem)) {
				if (stand.hasEmptyMeatSlot()) {
					if (!world.isRemote && stand.addMeat(heldItem) && !entityplayer.capabilities.isCreativeMode) {
						--heldItem.stackSize;
					}
					return true;
				}
			} else if (stand.getMeatAmount() > 0) {
				if (!world.isRemote) {
					boolean wasCooked = stand.isCooked();
					ItemStack meat = stand.removeFirstMeat();
					if (meat != null) {
						if (!entityplayer.inventory.addItemStackToInventory(meat)) {
							dropBlockAsItem(world, i, j, k, meat);
						}
						entityplayer.inventoryContainer.detectAndSendChanges();
						world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "random.pop", 0.5f, 0.5f + world.rand.nextFloat() * 0.5f);
						if (wasCooked) {
							GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.cookKebab);
						}
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void onBlockHarvested(World world, int i, int j, int k, int meta, EntityPlayer entityplayer) {
		int meta1 = meta;
		if (entityplayer.capabilities.isCreativeMode) {
			world.setBlockMetadataWithNotify(i, j, k, meta1 |= 8, 4);
		}
		dropBlockAsItem(world, i, j, k, meta1, 0);
		super.onBlockHarvested(world, i, j, k, meta1, entityplayer);
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack) {
		int rotation = MathHelper.floor_double(entity.rotationYaw * 4.0f / 360.0f + 0.5) & 3;
		int meta = 0;
		switch (rotation) {
			case 0:
				meta = 2;
				break;
			case 1:
				meta = 5;
				break;
			case 2:
				meta = 3;
				break;
			case 3:
				meta = 4;
				break;
			default:
				break;
		}
		world.setBlockMetadataWithNotify(i, j, k, meta, 2);
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity instanceof GOTTileEntityKebabStand) {
			GOTTileEntityKebabStand kebabStand = (GOTTileEntityKebabStand) tileentity;
			GOTItemKebabStand.loadKebabData(itemstack, kebabStand);
			kebabStand.onReplaced();
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		if (!canBlockStay(world, i, j, k)) {
			int meta = world.getBlockMetadata(i, j, k);
			dropBlockAsItem(world, i, j, k, meta, 0);
			world.setBlockToAir(i, j, k);
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
}
