package got.common.command;

import got.common.GOTSpawnDamping;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GOTCommandSpawnDamping extends CommandBase {
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "set", "calc", "reset");
		}
		if (args.length == 2 && ("set".equals(args[0]) || "calc".equals(args[0]))) {
			ArrayList<String> types = new ArrayList<>();
			for (EnumCreatureType type : EnumCreatureType.values()) {
				types.add(type.name());
			}
			types.add(GOTSpawnDamping.TYPE_NPC);
			return getListOfStringsMatchingLastWord(args, types.toArray(new String[0]));
		}
		return Collections.emptyList();
	}

	@Override
	public String getCommandName() {
		return "spawnDamping";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "got.command.spawnDamping.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length >= 1) {
			String option = args[0];
			if ("reset".equals(option)) {
				GOTSpawnDamping.resetAll();
				func_152373_a(sender, this, "got.command.spawnDamping.reset");
				return;
			}
			if (args.length >= 2) {
				String type = args[1];
				if (!type.equals(GOTSpawnDamping.TYPE_NPC)) {
					throw new WrongUsageException("got.command.spawnDamping.noType", type);
				}
				if ("set".equals(option) && args.length >= 3) {
					float damping = (float) parseDoubleBounded(sender, args[2], 0.0D, 1.0D);
					GOTSpawnDamping.setSpawnDamping(type, damping);
					func_152373_a(sender, this, "got.command.spawnDamping.set", damping);
					return;
				}
				if ("calc".equals(option)) {
					World world = sender.getEntityWorld();
					int dim = world.provider.dimensionId;
					String dimName = world.provider.getDimensionName();
					float damping = GOTSpawnDamping.getSpawnDamping(type);
					int players = world.playerEntities.size();
					int expectedChunks = 196;
					int baseCap = GOTSpawnDamping.getBaseSpawnCapForInfo(type);
					int cap = GOTSpawnDamping.getSpawnCap(type, baseCap, players);
					int capXPlayers = cap * players;
					IChatComponent chatComponentTranslation = new ChatComponentTranslation("got.command.spawnDamping.calc", dim, dimName, type, damping, players, expectedChunks, cap, baseCap, capXPlayers);
					chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.GREEN);
					sender.addChatMessage(chatComponentTranslation);
					return;
				}
			}
		}
		throw new WrongUsageException(getCommandUsage(sender));
	}
}
