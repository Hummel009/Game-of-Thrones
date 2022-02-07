package got.common.block.other;

import cpw.mods.fml.relauncher.*;
import got.GOT;
import got.common.GOTSquadrons;
import got.common.database.GOTCreativeTabs;
import got.common.tileentity.GOTTileEntityCommandTable;
import got.common.world.map.GOTConquestGrid;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class GOTBlockCommandTable extends BlockContainer {
	@SideOnly(value = Side.CLIENT)
	private IIcon topIcon;
	@SideOnly(value = Side.CLIENT)
	private IIcon sideIcon;

	public GOTBlockCommandTable() {
		super(Material.iron);
		setCreativeTab(GOTCreativeTabs.tabUtil);
		setHardness(2.5f);
		setStepSound(Block.soundTypeMetal);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new GOTTileEntityCommandTable();
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (i == 1 || i == 0) {
			return topIcon;
		}
		return sideIcon;
	}

	@Override
	public int getRenderType() {
		return GOT.getProxy().getCommandTableRenderID();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		GOTTileEntityCommandTable table;
		if (entityplayer.isSneaking() && (table = (GOTTileEntityCommandTable) world.getTileEntity(i, j, k)) != null) {
			if (!world.isRemote) {
				table.toggleZoomExp();
			}
			return true;
		}
		ItemStack itemstack = entityplayer.getCurrentEquippedItem();
		if (itemstack != null && itemstack.getItem() instanceof GOTSquadrons.SquadronItem) {
			entityplayer.openGui(GOT.getInstance(), 33, world, 0, 0, 0);
			if (!world.isRemote) {
				world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, stepSound.getBreakSound(), (stepSound.getVolume() + 1.0f) / 2.0f, stepSound.getPitch() * 0.5f);
			}
			return true;
		}
		if (GOTConquestGrid.conquestEnabled(world)) {
			entityplayer.openGui(GOT.getInstance(), 60, world, 0, 0, 0);
			if (!world.isRemote) {
				world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, stepSound.getBreakSound(), (stepSound.getVolume() + 1.0f) / 2.0f, stepSound.getPitch() * 0.5f);
			}
			return true;
		}
		return false;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		sideIcon = iconregister.registerIcon(getTextureName() + "_side");
		topIcon = iconregister.registerIcon(getTextureName() + "_top");
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
