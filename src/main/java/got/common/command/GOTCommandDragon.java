package got.common.command;

import got.common.entity.dragon.GOTDragonLifeStage;
import got.common.entity.dragon.GOTEntityDragon;
import net.minecraft.command.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.WorldServer;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class GOTCommandDragon extends CommandBase {
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		switch (args.length) {
			case 1:
				return getListOfStringsMatchingLastWord(args, "stage", "tame");
			case 2:
				if ("stage".equals(args[0])) {
					List<String> list = GOTDragonLifeStage.getLifeStageNames();
					return getListOfStringsMatchingLastWord(args, list.toArray(new String[0]));
				}
		}
		return Collections.emptyList();
	}

	private void appyModifier(ICommandSender sender, EntityModifier modifier, boolean global) {
		if (!global && sender instanceof EntityPlayerMP) {
			EntityPlayerMP player = getCommandSenderAsPlayer(sender);
			double range = 64;
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(player.posX - 1, player.posY - 1, player.posZ - 1, player.posX + 1, player.posY + 1, player.posZ + 1);
			aabb = aabb.expand(range, range, range);
			List<GOTEntityDragon> entities = player.worldObj.getEntitiesWithinAABB(GOTEntityDragon.class, aabb);

			GOTEntityDragon closestEntity = null;
			float minPlayerDist = Float.MAX_VALUE;

			for (GOTEntityDragon entity : entities) {
				float playerDist = entity.getDistanceToEntity(player);
				if (entity.getDistanceToEntity(player) < minPlayerDist) {
					closestEntity = entity;
					minPlayerDist = playerDist;
				}
			}

			if (closestEntity == null) {
				throw new CommandException("got.command.dragon.nodragons");
			}
			modifier.modify(closestEntity);
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
		String stages = StringUtils.join(GOTDragonLifeStage.values(), '|').toLowerCase(Locale.ROOT);
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
			String parameter = params[1].toUpperCase(Locale.ROOT);

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

	private interface EntityModifier {
		void modify(GOTEntityDragon dragon);
	}

	private static class LifeStageModifier implements EntityModifier {
		private final GOTDragonLifeStage lifeStage;

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

	private static class TameModifier implements EntityModifier {
		private final EntityPlayerMP player;

		TameModifier(EntityPlayerMP player) {
			this.player = player;
		}

		@Override
		public void modify(GOTEntityDragon dragon) {
			dragon.tamedFor(player, true);
		}
	}
}