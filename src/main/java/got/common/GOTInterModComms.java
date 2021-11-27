package got.common;

import com.google.common.collect.ImmutableList;

import cpw.mods.fml.common.event.FMLInterModComms;
import got.GOT;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class GOTInterModComms {
	public static void update() {
		ImmutableList<FMLInterModComms.IMCMessage> messages = FMLInterModComms.fetchRuntimeMessages(GOT.instance);
		if (!messages.isEmpty()) {
			for (FMLInterModComms.IMCMessage message : messages) {
				if (!"SIEGE_ACTIVE".equals(message.key)) {
					continue;
				}
				String playerName = message.getStringValue();
				EntityPlayerMP entityplayer = MinecraftServer.getServer().getConfigurationManager().func_152612_a(playerName);
				if (entityplayer == null) {
					continue;
				}
				int duration = 20;
				GOTLevelData.getData(entityplayer).setSiegeActive(duration);
			}
		}
	}
}
