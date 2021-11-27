package got.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import got.common.entity.dragon.*;
import got.common.entity.other.*;
import got.common.network.*;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.*;

public class GOTTrackingEventHandler {
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent evt) {

		if ((FMLCommonHandler.instance().getEffectiveSide() != Side.SERVER) || (evt.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)) {
			return;
		}

		World world = evt.entity.worldObj;
		Block block = world.getBlock(evt.x, evt.y, evt.z);

		if (block != Blocks.dragon_egg) {
			return;
		}

		evt.useBlock = PlayerInteractEvent.Result.DENY;
		evt.useItem = PlayerInteractEvent.Result.DENY;

		world.setBlock(evt.x, evt.y, evt.z, Blocks.air);

		GOTEntityDragon dragon = new GOTEntityDragon(world);
		dragon.setPosition(evt.x + 0.5, evt.y + 0.5, evt.z + 0.5);
		dragon.getReproductionHelper().setBreederName(evt.entityPlayer.getCommandSenderName());
		dragon.getLifeStageHelper().setLifeStage(GOTDragonLifeStage.EGG);
		world.spawnEntityInWorld(dragon);
	}

	@SubscribeEvent
	public void onStartTracking(PlayerEvent.StartTracking event) {
		GOTEntityCart target;
		if (event.target instanceof GOTEntityCart && (target = (GOTEntityCart) event.target).getPulling() != null) {
			GOTPacketHandler.networkWrapper.sendTo((IMessage) new GOTPacketCargocartUpdate(target.getPulling().getEntityId(), target.getEntityId()), (EntityPlayerMP) event.entityPlayer);
		}
		if (event.target instanceof GOTEntityCargocart) {
			target = (GOTEntityCargocart) event.target;
			GOTPacketHandler.networkWrapper.sendTo((IMessage) new GOTPacketCargocart(((GOTEntityCargocart) target).getLoad(), target.getEntityId()), (EntityPlayerMP) event.entityPlayer);
		}
	}
}
