package got.common.block.torch;

import java.util.Random;

public class GOTBlockAsshaiTorch extends GOTBlockTorch {
	@Override
	public GOTBlockTorch.TorchParticle createTorchParticle(Random random) {
		double d3 = -0.05 + random.nextFloat() * 0.1;
		double d4 = 0.1 + random.nextFloat() * 0.1;
		double d5 = -0.05 + random.nextFloat() * 0.1;
		return new GOTBlockTorch.TorchParticle("asshaiTorch", 0.0, 0.0, 0.0, d3, d4, d5);
	}
}