package got.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.item.other.GOTItemBrandingIron;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

public class GOTPacketBrandingIron implements IMessage {
	private String brandName;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTPacketBrandingIron() {
	}

	public GOTPacketBrandingIron(String s) {
		brandName = s;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		int length = data.readInt();
		if (length > -1) {
			brandName = data.readBytes(length).toString(Charsets.UTF_8);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		if (StringUtils.isBlank(brandName)) {
			data.writeInt(-1);
		} else {
			byte[] brandBytes = brandName.getBytes(Charsets.UTF_8);
			data.writeInt(brandBytes.length);
			data.writeBytes(brandBytes);
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketBrandingIron, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBrandingIron packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			ItemStack itemstack = entityplayer.getCurrentEquippedItem();
			if (itemstack != null && itemstack.getItem() instanceof GOTItemBrandingIron) {
				String brandName = packet.brandName;
				if (!StringUtils.isBlank(brandName = GOTItemBrandingIron.trimAcceptableBrandName(brandName)) && !GOTItemBrandingIron.hasBrandName(itemstack)) {
					GOTItemBrandingIron.setBrandName(itemstack, brandName);
				}
			}
			return null;
		}
	}
}