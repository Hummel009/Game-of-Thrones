package got.common.util;

import java.util.*;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.registry.GameRegistry;
import got.common.controller.*;
import got.common.entity.other.GOTEntityGrapplingArrow;
import got.common.network.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class GOTGrappleHelper {
	public static HashMap<Integer, GOTControllerGrabble> controllers = new HashMap<>();
	public static HashMap<GOTBlockPos, GOTControllerGrabble> controllerpos = new HashMap<>();
	public static HashSet<Integer> attached = new HashSet<>();
	public static int controllerid = 0;
	public static int GRAPPLEID = controllerid++;
	public static int HOOKID = controllerid++;
	public static int AIRID = controllerid++;
	public static int grapplingLength = 0;

	public static boolean anyblocks = true;
	public static ArrayList<Block> grapplingblocks;
	public static boolean removeblocks = false;

	public static GOTControllerGrabble createControl(int id, int arrowid, int entityid, World world, GOTVec pos, int maxlen, GOTBlockPos blockpos) {
		Entity arrowentity = world.getEntityByID(arrowid);
		if (arrowentity instanceof GOTEntityGrapplingArrow) {
		}

		if (true) {
			GOTControllerGrabble currentcontroller = controllers.get(entityid);
			if (currentcontroller != null) {
				currentcontroller.unattach();
			}
		}

		GOTControllerGrabble control = null;
		if (id == GRAPPLEID) {
			control = new GOTControllerGrabble(arrowid, entityid, world, pos, maxlen, id);
		} else if (id == HOOKID) {
			control = new GOTControllerHook(arrowid, entityid, world, pos, maxlen, id);
		} else if (id == AIRID) {
			control = new GOTControllerAir(arrowid, entityid, world, pos, maxlen, id);
		}
		if (blockpos != null && control != null) {
			GOTGrappleHelper.controllerpos.put(blockpos, control);
		}
		return control;
	}

	public static void receiveEnderLaunch(int id, double x, double y, double z) {
		GOTControllerGrabble controller = controllers.get(id);
		if (controller != null) {
			controller.receiveEnderLaunch(x, y, z);
		} else {
		}
	}

	public static void receiveGrappleClick(int id, boolean leftclick) {
		GOTControllerGrabble controller = controllers.get(id);
		if (controller != null) {
			controller.receiveGrappleClick(leftclick);
		} else {
		}
	}

	public static void receiveGrappleEnd(int id, World world, int arrowid) {
		if (GOTGrappleHelper.attached.contains(id)) {
			GOTGrappleHelper.attached.remove(Integer.valueOf(id));
		} else {
		}

		if (arrowid != -1) {
			Entity grapple = world.getEntityByID(arrowid);
			if (grapple instanceof GOTEntityGrapplingArrow) {
				((GOTEntityGrapplingArrow) grapple).removeServer();
			} else {

			}
		}

		Entity entity = world.getEntityByID(id);
		if (entity != null) {
			entity.fallDistance = 0;
		}
	}

	public static void registerController(int entityId, GOTControllerGrabble controller) {
		if (controllers.containsKey(entityId)) {
			controllers.get(entityId).unattach();
		}

		controllers.put(entityId, controller);
	}

	public static void removesubarrow(int id) {
		GOTPacketHandler.networkWrapper.sendToServer(new GOTPacketGrappleEnd(-1, id));
	}

	public static void sendtocorrectclient(IMessage message, int playerid, World w) {
		Entity entity = w.getEntityByID(playerid);
		if (entity instanceof EntityPlayerMP) {
			GOTPacketHandler.networkWrapper.sendTo(message, (EntityPlayerMP) entity);
		} else {
		}
	}

	public static void unregisterController(int entityId) {
		controllers.remove(entityId);
	}

	public static void updateGrapplingBlocks(World world) {
		String s = MinecraftServer.getServer().worldServerForDimension(0).getGameRules().getGameRuleStringValue("grapplingBlocks");
		if ("any".equals(s) || "".equals(s)) {
			s = MinecraftServer.getServer().worldServerForDimension(0).getGameRules().getGameRuleStringValue("grapplingNonBlocks");
			if ("none".equals(s) || "".equals(s)) {
				anyblocks = true;
			} else {
				anyblocks = false;
				removeblocks = true;
			}
		} else {
			anyblocks = false;
			removeblocks = false;
		}

		if (!anyblocks) {
			String[] blockstr = s.split(",");

			grapplingblocks = new ArrayList<>();

			for (String str : blockstr) {
				str = str.trim();
				String modid;
				String name;
				if (str.contains(":")) {
					String[] splitstr = str.split(":");
					modid = splitstr[0];
					name = splitstr[1];
				} else {
					modid = "minecraft";
					name = str;
				}

				Block b = GameRegistry.findBlock(modid, name);

				grapplingblocks.add(b);
			}
		}
	}

	public static void updateMaxLen(World world) {
		String s = MinecraftServer.getServer().worldServerForDimension(0).getGameRules().getGameRuleStringValue("grapplingLength");
		if (!"".equals(s)) {
			GOTGrappleHelper.grapplingLength = Integer.parseInt(s);
		}
	}
}
