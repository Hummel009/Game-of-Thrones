package got.common.command;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import got.common.entity.dragon.*;
import java.util.Collections;
import net.minecraft.command.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.WorldServer;

public class GOTCommandDragon extends CommandBase {
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			return CommandBase.getListOfStringsMatchingLastWord(args, "stage adult", "tame");
		}
		return Collections.emptyList();
	}

	public void appyModifier(ICommandSender sender, EntityModifier modifier, boolean global) {
		if (!global && sender instanceof EntityPlayerMP) {
			EntityPlayerMP player = getCommandSenderAsPlayer(sender);
			double range = 64;
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(player.posX - 1, player.posY - 1, player.posZ - 1, player.posX + 1, player.posY + 1, player.posZ + 1);
			aabb = aabb.expand(range, range, range);
			List<Entity> entities = player.worldObj.getEntitiesWithinAABB(GOTEntityDragon.class, aabb);

			Entity closestEntity = null;
			float minPlayerDist = Float.MAX_VALUE;

			for (Entity entity : entities) {
				float playerDist = entity.getDistanceToEntity(player);
				if (entity.getDistanceToEntity(player) < minPlayerDist) {
					closestEntity = entity;
					minPlayerDist = playerDist;
				}
			}

			if (closestEntity == null) {
				throw new CommandException("got.command.dragon.nodragons");
			}
			modifier.modify((GOTEntityDragon) closestEntity);
		} else {

			MinecraftServer server = MinecraftServer.getServer();
			for (WorldServer worldServer : server.worldServers) {
				List<Entity> entities = worldServer.loadedEntityList;

				for (Entity entity : entities) {
					if (!(entity instanceof GOTEntityDragon)) {
						continue;
					}

					modifier.modify((GOTEntityDragon) entity);
				}
			}
		}
	}

	@Override
	public String getCommandName() {
		return "dragon";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		String stages = StringUtils.join(GOTDragonLifeStage.values(), '|').toLowerCase();
		return String.format("/got_dragon <stage <%s> [global]", stages);
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params) {
		if (params.length < 1 || params[0].isEmpty()) {
			throw new WrongUsageException(getCommandUsage(sender));
		}

		boolean global = "global".equalsIgnoreCase(params[params.length - 1]);

		String command = params[0];
		if ("stage".equals(command)) {
			if (params.length < 2) {
				throw new WrongUsageException(getCommandUsage(sender));
			}

			GOTDragonLifeStage lifeStage = null;
			String parameter = params[1].toUpperCase();

			if (!"ITEM".equals(parameter)) {
				try {
					lifeStage = GOTDragonLifeStage.valueOf(parameter);
				} catch (IllegalArgumentException ex) {
					throw new SyntaxErrorException();
				}
			}

			EntityModifier modifier = new LifeStageModifier(lifeStage);
			appyModifier(sender, modifier, global);
		} else if ("tame".equals(command)) {
			if (!(sender instanceof EntityPlayerMP)) {

				throw new CommandException("got.command.dragon.canttame");
			}
			EntityPlayerMP player = (EntityPlayerMP) sender;
			appyModifier(sender, new TameModifier(player), global);
		} else {
			throw new WrongUsageException(getCommandUsage(sender));
		}
	}

	public interface EntityModifier {
		void modify(GOTEntityDragon dragon);
	}

	public static class LifeStageModifier implements EntityModifier {

		public GOTDragonLifeStage lifeStage;

		LifeStageModifier(GOTDragonLifeStage lifeStage) {
			this.lifeStage = lifeStage;
		}

		@Override
		public void modify(GOTEntityDragon dragon) {
			if (lifeStage == null) {
				dragon.getLifeStageHelper().transformToEgg();
			} else {
				dragon.getLifeStageHelper().setLifeStage(lifeStage);
			}
		}
	}

	public static class TameModifier implements EntityModifier {

		public EntityPlayerMP player;

		TameModifier(EntityPlayerMP player) {
			this.player = player;
		}

		@Override
		public void modify(GOTEntityDragon dragon) {
			dragon.tamedFor(player, true);
		}
	}
}
