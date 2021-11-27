package got.common.controller;

import got.common.util.GOTVec;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class GOTControllerAir extends GOTControllerGrabble {
	public double playermovementmult = 0.5;

	public GOTControllerAir(int arrowId, int entityId, World world, GOTVec pos, int maxlen, int id) {
		super(arrowId, entityId, world, pos, maxlen, id);
	}

	@Override
	public void updatePlayerPos() {
		Entity entity = this.entity;
		if (attached) {
			normalGround();
			normalCollisions();
			applyAirFriction();
			if (entity.isCollided || entity.onGround) {
				unattach();
			}
			motion.add_ip(playermovement.changelen(0.01));
			GOTVec newmotion;
			newmotion = motion;
			entity.motionX = newmotion.x;
			entity.motionZ = newmotion.z;
			updateServerPos();
		}
	}
}