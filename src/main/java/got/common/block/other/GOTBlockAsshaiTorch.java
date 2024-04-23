package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.BlockTorch;
import net.minecraft.world.World;

import java.util.Random;

public class GOTBlockAsshaiTorch extends BlockTorch {
	public GOTBlockAsshaiTorch() {
		setCreativeTab(GOTCreativeTabs.TAB_DECO);
	}

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

	private static TorchParticle createTorchParticle(Random random) {
		double d3 = -0.05 + random.nextFloat() * 0.1;
		double d4 = 0.1 + random.nextFloat() * 0.1;
		double d5 = -0.05 + random.nextFloat() * 0.1;
		return new TorchParticle("asshaiTorch", 0.0, 0.0, 0.0, d3, d4, d5);
	}

	private static class TorchParticle {
		private final String name;
		private final double posX;
		private final double posY;
		private final double posZ;
		private final double motionX;
		private final double motionY;
		private final double motionZ;

		private TorchParticle(String s, double x, double y, double z, double mx, double my, double mz) {
			name = s;
			posX = x;
			posY = y;
			posZ = z;
			motionX = mx;
			motionY = my;
			motionZ = mz;
		}

		private void spawn(double x, double y, double z) {
			GOT.proxy.spawnParticle(name, x + posX, y + posY, z + posZ, motionX, motionY, motionZ);
		}
	}
}