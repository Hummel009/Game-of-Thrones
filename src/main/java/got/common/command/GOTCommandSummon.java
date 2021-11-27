package got.common.command;

import got.common.entity.other.GOTEntityRegistry;
import net.minecraft.command.server.CommandSummon;

public class GOTCommandSummon extends CommandSummon {
	@Override
	public String[] func_147182_d() {
		return GOTEntityRegistry.getAllEntityNames().toArray(new String[0]);
	}

	@Override
	public String getCommandName() {
		return "got_summon";
	}
}
