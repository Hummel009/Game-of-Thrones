package got.client.effect;

import net.minecraft.world.World;

public class GOTEntityPickpocketFailFX
extends GOTEntityPickpocketFX {
    public GOTEntityPickpocketFailFX(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);
        this.setParticleTextureIndex(176 + this.rand.nextInt(6));
        this.particleGravity = 0.6f;
        this.bounciness = 0.5f;
    }

    @Override
    public void updatePickpocketIcon() {
    }
}

