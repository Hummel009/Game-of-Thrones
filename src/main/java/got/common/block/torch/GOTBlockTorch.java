package got.common.block.torch;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.BlockTorch;
import net.minecraft.world.World;

import java.util.Random;

public abstract class GOTBlockTorch extends BlockTorch {
	protected GOTBlockTorch() {
		setCreativeTab(GOTCreativeTabs.TAB_DECO);
	}

	protected abstract TorchParticle createTorchParticle(Random var1);

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		int meta = world.getBlockMetadata(i, j, k);
		double d = i + 0.5;
		double d1 = j + 0.7;
		double d2 = k + 0.5;
		double particleY = 0.2;
		double particleX = 0.27;
		TorchParticle particle = createTorchParticle(random);
		if (particle != null) {
			switch (meta) {
				case 1:
					particle.spawn(d - particleX, d1 + particleY, d2);
					break;
				case 2:
					particle.spawn(d + particleX, d1 + particleY, d2);
					break;
				case 3:
					particle.spawn(d, d1 + particleY, d2 - particleX);
					break;
				case 4:
					particle.spawn(d, d1 + particleY, d2 + particleX);
					break;
				default:
					particle.spawn(d, d1, d2);
					break;
			}
		}
	}

	public static class TorchParticle {
		protected String name;
		protected double posX;
		protected double posY;
		protected double posZ;
		protected double motionX;
		protected double motionY;
		protected double motionZ;

		protected TorchParticle(String s, double x, double y, double z, double mx, double my, double mz) {
			name = s;
			posX = x;
			posY = y;
			posZ = z;
			motionX = mx;
			motionY = my;
			motionZ = mz;
		}

		protected void spawn(double x, double y, double z) {
			GOT.proxy.spawnParticle(name, x + posX, y + posY, z + posZ, motionX, motionY, motionZ);
		}
	}

}
