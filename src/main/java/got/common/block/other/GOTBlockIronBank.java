package got.common.block.other;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.database.GOTGuiId;
import got.common.database.GOTItems;
import got.common.item.other.GOTItemCoin;
import got.common.network.GOTPacketClientsideGUI;
import got.common.network.GOTPacketHandler;
import got.common.util.GOTItemStackMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Map;

public class GOTBlockIronBank extends GOTBlockForgeBase {
	public static final Map<ItemStack, Integer> BUY = new GOTItemStackMap<>();
	public static final Map<ItemStack, Integer> SELL = new GOTItemStackMap<>();

	public static void preInit() {
		for (int i = 0; i < GOTItemCoin.VALUES.length; ++i) {
			BUY.put(new ItemStack(GOTItems.coin, 1, i), GOTItemCoin.VALUES[i]);
			SELL.put(new ItemStack(GOTItems.coin, 1, i), GOTItemCoin.VALUES[i]);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		if (!world.isRemote) {
			IMessage packet = new GOTPacketClientsideGUI(GOTGuiId.IRON_BANK.ordinal(), i, j, k);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
		return true;
	}

	@Override
	public boolean useLargeSmoke() {
		return false;
	}
}