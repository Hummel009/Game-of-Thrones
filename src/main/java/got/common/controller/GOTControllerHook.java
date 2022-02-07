package got.common.controller;

import got.common.util.GOTVec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTControllerHook extends GOTControllerGrabble {
	public double maxspeed = 4;

	public double acceleration = 0.2;
	public float oldstepheight;
	public double playermovementmult = 1;

	public GOTControllerHook(int arrowId, int entityId, World world, GOTVec pos, int maxlen, int id) {
		super(arrowId, entityId, world, pos, maxlen, id);
	}

	@Override
	public void updatePlayerPos() {

		Entity entity = this.entity;

		if (attached && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;

			if (true) {

				normalCollisions();

				GOTVec arrowpos = pos;
				GOTVec playerpos = GOTVec.positionvec(player);

				GOTVec oldspherevec = playerpos.sub(arrowpos);
				GOTVec spherevec = oldspherevec.changelen(r);

				double dist = oldspherevec.length();

				if (isjumping()) {
					this.dojump(player, spherevec);
					return;

				}
				applyPlayerMovement();

				GOTVec newmotion;

				if (dist < 4) {
					if (motion.length() > 0.3) {
						motion.mult_ip(0.6);
					}

					if (player.onGround) {
						entity.motionX = 0;
						entity.motionY = 0;
						entity.motionZ = 0;
						updateServerPos();

					}
				}

				motion.add_ip(arrowpos.sub(playerpos).changelen(acceleration));

				double speed = motion.proj(oldspherevec).length();

				if (speed > maxspeed) {
					motion.changelen_ip(maxspeed);
				}

				newmotion = motion;

				GOTVec motiontorwards = spherevec.changelen(-1);
				motion = dampenmotion(motion, motiontorwards);

				entity.motionX = newmotion.x;
				entity.motionY = newmotion.y;
				entity.motionZ = newmotion.z;

				player.fallDistance = 0;

				updateServerPos();
			}
		}
	}

}
