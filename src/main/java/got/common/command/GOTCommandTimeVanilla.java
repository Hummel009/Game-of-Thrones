package got.common.command;

import got.common.world.GOTWorldProvider;
import net.minecraft.command.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class GOTCommandTimeVanilla extends CommandTime {
	@Override
	public void addTime(ICommandSender sender, int time) {
		for (WorldServer world : MinecraftServer.getServer().worldServers) {
			if (world.provider instanceof GOTWorldProvider) {
				continue;
			}
			world.setWorldTime(world.getWorldTime() + time);
		}
	}

	@Override
	public void setTime(ICommandSender sender, int time) {
		for (WorldServer world : MinecraftServer.getServer().worldServers) {
			if (world.provider instanceof GOTWorldProvider) {
				continue;
			}
			world.setWorldTime(time);
		}
	}
}
