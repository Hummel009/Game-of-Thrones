package got.client.effect;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class GOTEntityLeafFX extends EntityFX {
	private final int[] texIndices;

	public GOTEntityLeafFX(World world, double d, double d1, double d2, double d3, double d4, double d5, int[] tex) {
		super(world, d, d1, d2, d3, d4, d5);
		motionX = d3;
		motionY = d4;
		motionZ = d5;
		particleScale = 0.15f + rand.nextFloat() * 0.5f;
		texIndices = tex;
		particleMaxAge = 600;
	}

	public GOTEntityLeafFX(World world, double d, double d1, double d2, double d3, double d4, double d5, int[] tex, int age) {
		this(world, d, d1, d2, d3, d4, d5, tex);
		particleMaxAge = age;
	}

	@Override
	public int getFXLayer() {
		return 1;
	}

	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		moveEntity(motionX, motionY, motionZ);
		++particleAge;
		setParticleTextureIndex(texIndices[particleAge / 4 % texIndices.length]);
		if (onGround || particleAge >= particleMaxAge || posY < 0.0) {
			setDead();
		}
	}

	@Override
	public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
		float uMin = particleTextureIndexX / 8.0f;
		float uMax = uMin + 0.125f;
		float vMin = particleTextureIndexY / 8.0f;
		float vMax = vMin + 0.125f;
		float scale = 0.25f * particleScale;
		float x = (float) (prevPosX + (posX - prevPosX) * f - interpPosX);
		float y = (float) (prevPosY + (posY - prevPosY) * f - interpPosY);
		float z = (float) (prevPosZ + (posZ - prevPosZ) * f - interpPosZ);
		tessellator.setColorRGBA_F(particleRed, particleGreen, particleBlue, particleAlpha);
		tessellator.addVertexWithUV(x - f1 * scale - f4 * scale, y - f2 * scale, z - f3 * scale - f5 * scale, uMax, vMax);
		tessellator.addVertexWithUV(x - f1 * scale + f4 * scale, y + f2 * scale, z - f3 * scale + f5 * scale, uMax, vMin);
		tessellator.addVertexWithUV(x + f1 * scale + f4 * scale, y + f2 * scale, z + f3 * scale + f5 * scale, uMin, vMin);
		tessellator.addVertexWithUV(x + f1 * scale - f4 * scale, y - f2 * scale, z + f3 * scale - f5 * scale, uMin, vMax);
	}

	@Override
	public void setParticleTextureIndex(int i) {
		particleTextureIndexX = i % 8;
		particleTextureIndexY = i / 8;
	}
}
