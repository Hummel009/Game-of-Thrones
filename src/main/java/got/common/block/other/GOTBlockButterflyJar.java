package got.common.block.other;

import cpw.mods.fml.relauncher.*;
import got.GOT;
import got.common.entity.animal.GOTEntityButterfly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;

public class GOTBlockButterflyJar extends GOTBlockAnimalJar {
	@SideOnly(value = Side.CLIENT)
	private IIcon glassIcon;
	@SideOnly(value = Side.CLIENT)
	private IIcon lidIcon;

	public GOTBlockButterflyJar() {
		super(Material.glass);
		setBlockBounds(0.1875f, 0.0f, 0.1875f, 0.8125f, 0.75f, 0.8125f);
		setHardness(0.0f);
		setStepSound(Block.soundTypeGlass);
	}

	@Override
	public boolean canCapture(Entity entity) {
		return entity instanceof GOTEntityButterfly;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (i == -1) {
			return lidIcon;
		}
		return glassIcon;
	}

	@Override
	public float getJarEntityHeight() {
		return 0.25f;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public int getRenderType() {
		return GOT.getProxy().getButterflyJarRenderID();
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		glassIcon = iconregister.registerIcon(getTextureName() + "_glass");
		lidIcon = iconregister.registerIcon(getTextureName() + "_lid");
	}
}
