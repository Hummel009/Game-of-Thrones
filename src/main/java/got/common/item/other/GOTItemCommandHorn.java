package got.common.item.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.GOTSquadrons;
import got.common.database.GOTCreativeTabs;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class GOTItemCommandHorn extends Item implements GOTSquadrons.SquadronItem {
	public GOTItemCommandHorn() {
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(1);
		setCreativeTab(GOTCreativeTabs.tabCombat);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 40;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int j = 0; j <= 3; ++j) {
			list.add(new ItemStack(item, 1, j));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		switch (itemstack.getItemDamage()) {
			case 1:
				return "item.got.commandHorn.halt";
			case 2:
				return "item.got.commandHorn.ready";
			case 3:
				return "item.got.commandHorn.summon";
			default:
				return super.getUnlocalizedName(itemstack);
		}
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!world.isRemote) {
			List<? extends Entity> entities = world.loadedEntityList;
			for (Entity entity : entities) {
				if (entity instanceof GOTEntityNPC) {
					GOTEntityNPC npc = (GOTEntityNPC) entity;
					if (!npc.hiredNPCInfo.isActive || npc.hiredNPCInfo.getHiringPlayer() != entityplayer || !GOTSquadrons.areSquadronsCompatible(npc, itemstack)) {
						continue;
					}
					if (itemstack.getItemDamage() == 1 && npc.hiredNPCInfo.getObeyHornHaltReady()) {
						npc.hiredNPCInfo.halt();
						continue;
					}
					if (itemstack.getItemDamage() == 2 && npc.hiredNPCInfo.getObeyHornHaltReady()) {
						npc.hiredNPCInfo.ready();
						continue;
					}
					if (itemstack.getItemDamage() != 3 || !npc.hiredNPCInfo.getObeyHornSummon()) {
						continue;
					}
					npc.hiredNPCInfo.tryTeleportToHiringPlayer(true);
				}
			}
		}
		if (itemstack.getItemDamage() == 1) {
			itemstack.setItemDamage(2);
		} else if (itemstack.getItemDamage() == 2) {
			itemstack.setItemDamage(1);
		}
		world.playSoundAtEntity(entityplayer, "got:item.horn", 4.0f, 1.0f);
		return itemstack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (itemstack.getItemDamage() == 0) {
			entityplayer.openGui(GOT.instance, 9, world, 0, 0, 0);
		} else {
			entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		}
		return itemstack;
	}
}
