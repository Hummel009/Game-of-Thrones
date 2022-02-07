package got.common.command;

import got.common.GOTConfig;
import net.minecraft.command.server.CommandMessage;

public class GOTCommandMessageFixed extends CommandMessage {
	@Override
	public boolean isUsernameIndex(String[] args, int i) {
		if (GOTConfig.isPreventMessageExploit()) {
			return false;
		}
		return super.isUsernameIndex(args, i);
	}
}
