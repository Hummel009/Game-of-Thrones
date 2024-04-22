package got.common.item.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.GOTGuiHandler;
import got.common.block.other.GOTBlockChest;
import got.common.block.other.GOTBlockSpawnerChest;
import got.common.database.GOTCreativeTabs;
import got.common.database.GOTGuiId;
import got.common.inventory.GOTContainerChestWithPouch;
import got.common.inventory.GOTContainerPouch;
import got.common.inventory.GOTInventoryPouch;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class GOTItemPouch extends Item {
	private static final String[] POUCH_TYPES = {"small", "medium", "large"};

	@SideOnly(Side.CLIENT)
	private IIcon[] pouchIcons;

	@SideOnly(Side.CLIENT)
	private IIcon[] pouchIconsOpen;

	@SideOnly(Side.CLIENT)
	private IIcon[] overlayIcons;

	@SideOnly(Side.CLIENT)
	private IIcon[] overlayIconsOpen;

	public GOTItemPouch() {
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(1);
		setCreativeTab(GOTCreativeTabs.TAB_MISC);
	}

	public static int getCapacity(ItemStack itemstack) {
		return getCapacityForMeta(itemstack.getItemDamage());
	}

	public static int getCapacityForMeta(int i) {
		return (i + 1) * 9;
	}

	public static IInventory getChestInvAt(EntityPlayer entityplayer, World world, int i, int j, int k) {
		InventoryEnderChest enderInv;
		Block block = world.getBlock(i, j, k);
		TileEntity te = world.getTileEntity(i, j, k);
		if (block instanceof GOTBlockSpawnerChest) {
			return null;
		}
		if (block instanceof BlockChest) {
			return ((BlockChest) block).func_149951_m(world, i, j, k);
		}
		if (block instanceof GOTBlockChest) {
			return GOTBlockChest.getModChestAt(world, i, j, k);
		}
		if (block instanceof BlockEnderChest && !world.getBlock(i, j + 1, k).isNormalCube() && (enderInv = entityplayer.getInventoryEnderChest()) != null && te instanceof TileEntityEnderChest) {
			TileEntityEnderChest enderChest = (TileEntityEnderChest) te;
			if (!world.isRemote) {
				enderInv.func_146031_a(enderChest);
			}
			return enderInv;
		}
		return null;
	}

	public static int getMaxPouchCapacity() {
		return getCapacityForMeta(POUCH_TYPES.length - 1);
	}

	public static int getPouchColor(ItemStack itemstack) {
		int dye = getSavedDyeColor(itemstack);
		if (dye != -1) {
			return dye;
		}
		return 10841676;
	}

	public static int getRandomPouchSize(Random random) {
		float f = random.nextFloat();
		if (f < 0.6f) {
			return 0;
		}
		if (f < 0.9f) {
			return 1;
		}
		return 2;
	}

	private static int getSavedDyeColor(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("PouchColor")) {
			return itemstack.getTagCompound().getInteger("PouchColor");
		}
		return -1;
	}

	public static boolean isHoldingPouch(EntityPlayer entityplayer, int slot) {
		return entityplayer.inventory.getStackInSlot(slot) != null && entityplayer.inventory.getStackInSlot(slot).getItem() instanceof GOTItemPouch;
	}

	public static boolean isPouchDyed(ItemStack itemstack) {
		return getSavedDyeColor(itemstack) != -1;
	}

	public static void removePouchDye(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null) {
			itemstack.getTagCompound().removeTag("PouchColor");
		}
	}

	public static void setPouchColor(ItemStack itemstack, int i) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setInteger("PouchColor", i);
	}

	public static boolean tryAddItemToPouch(ItemStack pouch, ItemStack itemstack, boolean requireMatchInPouch) {
		if (itemstack != null && itemstack.stackSize > 0) {
			IInventory pouchInv = new GOTInventoryPouch(pouch);
			for (int i = 0; i < pouchInv.getSizeInventory() && itemstack.stackSize > 0; ++i) {
				ItemStack itemInSlot = pouchInv.getStackInSlot(i);
				if (itemInSlot != null ? itemInSlot.stackSize >= itemInSlot.getMaxStackSize() || itemInSlot.getItem() != itemstack.getItem() || !itemInSlot.isStackable() || itemInSlot.getHasSubtypes() && itemInSlot.getItemDamage() != itemstack.getItemDamage() || !ItemStack.areItemStackTagsEqual(itemInSlot, itemstack) : requireMatchInPouch) {
					continue;
				}
				if (itemInSlot == null) {
					pouchInv.setInventorySlotContents(i, itemstack);
					return true;
				}
				int maxStackSize = itemInSlot.getMaxStackSize();
				if (pouchInv.getInventoryStackLimit() < maxStackSize) {
					maxStackSize = pouchInv.getInventoryStackLimit();
				}
				int difference = maxStackSize - itemInSlot.stackSize;
				if (difference > itemstack.stackSize) {
					difference = itemstack.stackSize;
				}
				itemstack.stackSize -= difference;
				itemInSlot.stackSize += difference;
				pouchInv.setInventorySlotContents(i, itemInSlot);
				if (itemstack.stackSize > 0) {
					continue;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("rawtypes")
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		int slots = getCapacity(itemstack);
		int slotsFull = 0;
		IInventory pouchInv = new GOTInventoryPouch(itemstack);
		for (int i = 0; i < pouchInv.getSizeInventory(); ++i) {
			ItemStack slotItem = pouchInv.getStackInSlot(i);
			if (slotItem == null) {
				continue;
			}
			++slotsFull;
		}
		list.add(StatCollector.translateToLocalFormatted("item.got.pouch.slots", slotsFull, slots));
		if (isPouchDyed(itemstack)) {
			list.add(StatCollector.translateToLocal("item.got.pouch.dyed"));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack itemstack, int pass) {
		if (pass == 0) {
			return getPouchColor(itemstack);
		}
		return 16777215;
	}

	@Override
	public IIcon getIcon(ItemStack itemstack, int pass) {
		Container container;
		boolean open = false;
		EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
		if (entityplayer != null && ((container = entityplayer.openContainer) instanceof GOTContainerPouch || container instanceof GOTContainerChestWithPouch) && itemstack == entityplayer.getHeldItem()) {
			open = true;
		}
		int meta = itemstack.getItemDamage();
		if (meta >= pouchIcons.length) {
			meta = 0;
		}
		if (open) {
			return pass > 0 ? overlayIconsOpen[meta] : pouchIconsOpen[meta];
		}
		return pass > 0 ? overlayIcons[meta] : pouchIcons[meta];
	}

	@Override
	public int getRenderPasses(int meta) {
		return 2;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("rawtypes")
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < POUCH_TYPES.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + '.' + itemstack.getItemDamage();
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!world.isRemote) {
			entityplayer.openGui(GOT.instance, GOTGuiId.POUCH.ordinal(), world, entityplayer.inventory.currentItem, 0, 0);
		}
		return itemstack;
	}

	@Override
	public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ) {
		IInventory chest = getChestInvAt(entityplayer, world, i, j, k);
		if (chest != null) {
			GOTGuiHandler.usePouchOnChest(entityplayer, world, i, j, k, side, itemstack, entityplayer.inventory.currentItem);
			return true;
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconregister) {
		pouchIcons = new IIcon[POUCH_TYPES.length];
		pouchIconsOpen = new IIcon[POUCH_TYPES.length];
		overlayIcons = new IIcon[POUCH_TYPES.length];
		overlayIconsOpen = new IIcon[POUCH_TYPES.length];
		for (int i = 0; i < POUCH_TYPES.length; ++i) {
			pouchIcons[i] = iconregister.registerIcon(getIconString() + '_' + POUCH_TYPES[i]);
			pouchIconsOpen[i] = iconregister.registerIcon(getIconString() + '_' + POUCH_TYPES[i] + "_open");
			overlayIcons[i] = iconregister.registerIcon(getIconString() + '_' + POUCH_TYPES[i] + "_overlay");
			overlayIconsOpen[i] = iconregister.registerIcon(getIconString() + '_' + POUCH_TYPES[i] + "_open_overlay");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
}