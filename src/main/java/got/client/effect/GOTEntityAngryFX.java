package got.client.effect;

import cpw.mods.fml.relauncher.*;
import got.common.util.GOTFunctions;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityAngryFX
extends EntityFX {
    private float angryScale;

    public GOTEntityAngryFX(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.motionX = xSpeed;
        this.motionY = ySpeed;
        this.motionZ = zSpeed;
        this.particleScale = 7.5f;
        this.angryScale = 7.5f;
        this.particleMaxAge = 40 + this.rand.nextInt(20);
        this.particleGravity = 0.0f;
        this.noClip = false;
    }

    public void onUpdate() {
        super.onUpdate();
        this.setParticleTextureIndex(12 + this.particleAge / 4 % 4);
        float fade = 0.8f;
        float ageF = (float)this.particleAge / (float)this.particleMaxAge;
        if (ageF >= fade) {
            this.particleAlpha = 1.0f - (ageF - fade) / (1.0f - fade);
            if (this.particleAlpha <= 0.0f) {
                this.setDead();
            }
        }
    }

    public void setParticleTextureIndex(int i) {
        this.particleTextureIndexX = i % 4;
        this.particleTextureIndexY = i / 4;
    }

    @SideOnly(value=Side.CLIENT)
    public int getBrightnessForRender(float f) {
        return 15728880;
    }

    public float getBrightness(float f) {
        return 1.0f;
    }

    public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        float modScale = GOTFunctions.normalisedCos(((float)this.particleAge + f) / 12.0f);
        modScale = MathHelper.clamp_float((float)modScale, (float)0.0f, (float)1.0f);
        this.particleScale = this.angryScale * (0.7f + modScale * 0.3f);
        float uMin = (float)this.particleTextureIndexX / 4.0f;
        float uMax = uMin + 0.25f;
        float vMin = (float)this.particleTextureIndexY / 4.0f;
        float vMax = vMin + 0.25f;
        float scale = 0.125f * this.particleScale;
        float x = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)f - interpPosX);
        float y = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)f - interpPosY);
        float z = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)f - interpPosZ);
        tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        tessellator.addVertexWithUV((double)(x - f1 * scale - f4 * scale), (double)(y - f2 * scale), (double)(z - f3 * scale - f5 * scale), (double)uMax, (double)vMax);
        tessellator.addVertexWithUV((double)(x - f1 * scale + f4 * scale), (double)(y + f2 * scale), (double)(z - f3 * scale + f5 * scale), (double)uMax, (double)vMin);
        tessellator.addVertexWithUV((double)(x + f1 * scale + f4 * scale), (double)(y + f2 * scale), (double)(z + f3 * scale + f5 * scale), (double)uMin, (double)vMin);
        tessellator.addVertexWithUV((double)(x + f1 * scale - f4 * scale), (double)(y - f2 * scale), (double)(z + f3 * scale - f5 * scale), (double)uMin, (double)vMax);
    }
}

