package got.common.controller;

import got.GOT;
import got.common.entity.other.GOTEntityGrapplingArrow;
import got.common.network.*;
import got.common.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTControllerGrabble {
	public int arrowId;
	public int entityId;
	public World world;
	public GOTVec pos;
	public GOTEntityGrapplingArrow arrow;
	public Entity entity;
	public boolean attached = true;
	public double r;
	public GOTVec motion;
	public double playerforward = 0;
	public double playerstrafe = 0;
	public boolean playerjump = false;
	public boolean waitingonplayerjump = false;
	public GOTVec playermovement_unrotated = new GOTVec(0, 0, 0);
	public GOTVec playermovement = new GOTVec(0, 0, 0);
	public int ongroundtimer = 0;
	public int maxongroundtimer = 3;
	public int maxlen;
	public int controllerid;
	public double playermovementmult = 0.5;

	public GOTControllerGrabble(int arrowId, int entityId, World world, GOTVec pos, int maxlen, int controllerid) {
		this.arrowId = arrowId;
		this.entityId = entityId;
		this.world = world;
		this.pos = pos;
		this.maxlen = maxlen;
		this.controllerid = controllerid;

		entity = world.getEntityByID(entityId);
		r = this.pos.sub(GOTVec.positionvec(entity)).length();
		motion = GOTVec.motionvec(entity);
		ongroundtimer = 0;
		GOTGrappleHelper.registerController(this.entityId, this);
		if (arrowId != -1) {
			Entity arrowentity = world.getEntityByID(arrowId);
			if (arrowentity != null && !arrowentity.isDead && arrowentity instanceof GOTEntityGrapplingArrow) {
				arrow = (GOTEntityGrapplingArrow) arrowentity;
			}
		}
	}

	public void applyAirFriction() {
		double vel = motion.length();
		double dragforce = vel * vel / 200;

		GOTVec airfric = new GOTVec(motion.x, motion.y, motion.z);
		airfric.changelen_ip(-dragforce);
		motion.add_ip(airfric);
	}

	public void applyPlayerMovement() {
		motion.add_ip(playermovement.changelen(0.015 + motion.length() * 0.01));

	}

	public void calctaut(double dist) {
		if (arrow != null) {
			if (dist < r) {
				double taut = 1 - (r - dist) / 5;
				if (taut < 0) {
					taut = 0;
				}
				arrow.taut = taut;
			} else {
				arrow.taut = 1;
			}
		}
	}

	public GOTVec dampenmotion(GOTVec motion, GOTVec forward) {
		GOTVec newmotion = motion.proj(forward);
		double dampening = 0.05;
		return new GOTVec(newmotion.x * dampening + motion.x * (1 - dampening), newmotion.y * dampening + motion.y * (1 - dampening), newmotion.z * dampening + motion.z * (1 - dampening));
	}

	public void doClientTick() {
		if (attached) {
			if (entity == null || entity.isDead) {
				unattach();
			} else {
				GOT.proxy.getplayermovement(this, entityId);
				updatePlayerPos();
			}
		}
	}

	public void dojump(Entity player, double jumppower) {
		double maxjump = 1;
		if (ongroundtimer > 0) {
			ongroundtimer = 20;
			return;
		}
		if (player.onGround) {
			jumppower = 0;
		}
		if (player.isCollided) {
			jumppower = maxjump;
		}
		if (jumppower < 0) {
			jumppower = 0;
		}

		unattach();

		if (jumppower > 0) {
			if (jumppower > player.motionY + jumppower) {
				player.motionY = jumppower;
			} else {
				player.motionY += jumppower;
			}
		}

		updateServerPos();
	}

	public void dojump(Entity player, GOTVec spherevec) {
		double maxjump = 1;
		GOTVec jump = new GOTVec(0, maxjump, 0);
		if (spherevec != null) {
			jump = jump.proj(spherevec);
		}
		double jumppower = jump.y;

		if (spherevec != null && spherevec.y > 0) {
			jumppower = 0;
		}
		if (arrow != null && r < 1 && player.posY < arrow.posY) {
			jumppower = maxjump;
		}

		this.dojump(player, jumppower);
	}

	public GOTEntityGrapplingArrow getArrow() {
		return (GOTEntityGrapplingArrow) world.getEntityByID(arrowId);
	}

	public boolean isjumping() {
		if (playerjump && waitingonplayerjump) {
			waitingonplayerjump = false;
			return true;
		}
		return false;
	}

	public void normalCollisions() {

		if (entity.isCollidedHorizontally) {
			if (Math.abs(entity.motionX) < 0.1) {
				motion.x = 0;
			}
			if (Math.abs(entity.motionZ) < 0.1) {
				motion.z = 0;
			}
		}
		if (entity.isCollidedVertically && Math.abs(entity.motionY) < 0.1) {
			motion.y = 0;
		}
	}

	public void normalGround() {
		if (entity.onGround) {
			ongroundtimer = maxongroundtimer;
			if (motion.y < 0) {
				motion.y = 0;
			}
		} else if (ongroundtimer > 0) {
			ongroundtimer--;
		}
		if (ongroundtimer > 0 && !GOT.proxy.isSneaking(entity)) {
			motion = GOTVec.motionvec(entity);
		}
	}

	public void receiveEnderLaunch(double x, double y, double z) {
		motion.add_ip(x, y, z);
		entity.motionX = motion.x;
		entity.motionY = motion.y;
		entity.motionZ = motion.z;
	}

	public void receiveGrappleClick(boolean leftclick) {
		if (!leftclick) {
			unattach();
		}
	}

	public void receivePlayerMovementMessage(float strafe, float forward, boolean jump) {
		playerforward = forward;
		playerstrafe = strafe;
		if (!jump) {
			playerjump = false;
		} else if (jump && !playerjump) {
			playerjump = true;
			waitingonplayerjump = true;
		}
		playermovement_unrotated = new GOTVec(strafe, 0, forward);
		playermovement = playermovement_unrotated.rotate_yaw((float) (entity.rotationYaw * (Math.PI / 180.0)));
	}

	public void unattach() {
		if (GOTGrappleHelper.controllers.containsValue(this)) {
			attached = false;
			GOTGrappleHelper.unregisterController(entityId);
			if (controllerid != GOTGrappleHelper.AIRID) {
				GOTPacketHandler.networkWrapper.sendToServer(new GOTPacketGrappleEnd(entityId, arrowId));
				GOTGrappleHelper.createControl(GOTGrappleHelper.AIRID, -1, entityId, entity.worldObj, new GOTVec(0, 0, 0), 0, null);
			}
		}
	}

	public void updatePlayerPos() {
		Entity entity = this.entity;

		if (attached && entity != null && true) {
			normalGround();
			normalCollisions();
			GOTVec arrowpos = pos;
			GOTVec playerpos = GOTVec.positionvec(entity);
			GOTVec oldspherevec = playerpos.sub(arrowpos);
			GOTVec spherevec = oldspherevec.changelen(r);
			GOTVec spherechange = spherevec.sub(oldspherevec);
			GOTVec additionalmotion;
			if (arrowpos.sub(playerpos).length() < r) {
				additionalmotion = new GOTVec(0, 0, 0);
			} else {
				additionalmotion = spherechange;
			}

			double dist = oldspherevec.length();
			calctaut(dist);

			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				if (isjumping()) {
					this.dojump(player, spherevec);
					return;
				}
				if (GOT.proxy.isSneaking(entity)) {
					if (arrowpos.y > playerpos.y) {

						GOTVec motiontorwards = spherevec.changelen(-0.1);
						motiontorwards = new GOTVec(motiontorwards.x, 0, motiontorwards.z);
						if (motion.dot(motiontorwards) < 0) {
							motion.add_ip(motiontorwards);
						}

						GOTVec newmotion = dampenmotion(motion, motiontorwards);
						motion = new GOTVec(newmotion.x, motion.y, newmotion.z);

						if (playerforward != 0 && (dist < maxlen || playerforward > 0 || maxlen == 0)) {

							additionalmotion = new GOTVec(0, playerforward, 0);

							r = dist;
							r -= playerforward * 0.3;
							if (r < 0) {
								r = dist;
							}
						}
					}
				} else {
					applyPlayerMovement();
				}
			}

			if (ongroundtimer <= 0) {
				motion.add_ip(0, -0.05, 0);
			}

			GOTVec newmotion = motion.add(additionalmotion);

			if (arrowpos.sub(playerpos.add(motion)).length() > r) {

				motion = motion.removealong(spherevec);
			}

			entity.motionX = newmotion.x;
			entity.motionY = newmotion.y;
			entity.motionZ = newmotion.z;

			updateServerPos();
		}
	}

	public void updateServerPos() {
		GOTPacketHandler.networkWrapper.sendToServer(new GOTPacketPlayerMovement(entityId, entity.posX, entity.posY, entity.posZ, entity.motionX, entity.motionY, entity.motionZ));
	}
}
