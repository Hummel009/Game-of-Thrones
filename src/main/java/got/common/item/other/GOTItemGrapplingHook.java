package got.common.item.other;

import java.util.*;

import cpw.mods.fml.common.FMLCommonHandler;
import got.GOT;
import got.common.GOTCommonProxy;
import got.common.entity.other.GOTEntityGrapplingArrow;
import got.common.network.GOTPacketGrappleClick;
import got.common.util.GOTGrappleHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class GOTItemGrapplingHook extends Item {
	public static HashMap<Entity, GOTEntityGrapplingArrow> grapplearrows = new HashMap<>();

	public GOTItemGrapplingHook() {
		maxStackSize = 1;
		setFull3D();
		setMaxDamage(500);
		setCreativeTab(CreativeTabs.tabTransport);
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add("A basic grappling hook for swinging");
		list.add("");
		list.add(GOT.proxy.getkeyname(GOTCommonProxy.keys.keyBindUseItem) + " - Throw grappling hook");
		list.add(GOT.proxy.getkeyname(GOTCommonProxy.keys.keyBindUseItem) + " again - Release");
		list.add("Double-" + GOT.proxy.getkeyname(GOTCommonProxy.keys.keyBindUseItem) + " - Release and throw again");
		list.add(GOT.proxy.getkeyname(GOTCommonProxy.keys.keyBindForward) + ", " + GOT.proxy.getkeyname(GOTCommonProxy.keys.keyBindLeft) + ", " + GOT.proxy.getkeyname(GOTCommonProxy.keys.keyBindBack) + ", " + GOT.proxy.getkeyname(GOTCommonProxy.keys.keyBindRight) + " - Swing");
		list.add(GOT.proxy.getkeyname(GOTCommonProxy.keys.keyBindJump) + " - Release and jump (while in midair)");
		list.add(GOT.proxy.getkeyname(GOTCommonProxy.keys.keyBindSneak) + " - Stop swinging");
		list.add(GOT.proxy.getkeyname(GOTCommonProxy.keys.keyBindSneak) + " + " + GOT.proxy.getkeyname(GOTCommonProxy.keys.keyBindForward) + " - Climb up");
		list.add(GOT.proxy.getkeyname(GOTCommonProxy.keys.keyBindSneak) + " + " + GOT.proxy.getkeyname(GOTCommonProxy.keys.keyBindBack) + " - Climb down");
	}

	public GOTEntityGrapplingArrow createarrow(ItemStack stack, World worldIn, EntityLivingBase entityLiving, boolean righthand) {
		return new GOTEntityGrapplingArrow(worldIn, entityLiving, righthand);
	}

	public void dorightclick(ItemStack stack, World worldIn, EntityLivingBase entityLiving, boolean righthand) {
		if (!worldIn.isRemote) {
			GOTEntityGrapplingArrow entityarrow = getArrow(entityLiving, worldIn);

			if (entityarrow != null) {
				int id = entityarrow.shootingEntityID;
				if (!GOTGrappleHelper.attached.contains(id)) {
					setArrow(entityLiving, stack, null);

					if (!entityarrow.isDead) {
						entityarrow.removeServer();
						return;
					}

					entityarrow = null;
				}
			}

			float f = 2.0F;
			if (entityarrow == null) {
				entityarrow = createarrow(stack, worldIn, entityLiving, righthand);

				setArrow(entityLiving, stack, entityarrow);

				stack.damageItem(1, entityLiving);
				worldIn.playSoundAtEntity(entityLiving, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

				worldIn.spawnEntityInWorld(entityarrow);
			} else {
				GOTGrappleHelper.sendtocorrectclient(new GOTPacketGrappleClick(entityarrow.shootingEntityID, false), entityarrow.shootingEntityID, entityarrow.worldObj);
				GOTGrappleHelper.attached.remove(Integer.valueOf(entityarrow.shootingEntityID));
				setArrow(entityLiving, stack, null);
			}
		}
	}

	public GOTEntityGrapplingArrow getArrow(Entity entity, World world) {
		if (GOTItemGrapplingHook.grapplearrows.containsKey(entity)) {
			GOTEntityGrapplingArrow arrow = GOTItemGrapplingHook.grapplearrows.get(entity);
			if (arrow != null && !arrow.isDead) {
				return arrow;
			}
		}
		return null;
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		ItemStack mat = new ItemStack(Items.leather, 1);
		if (mat != null && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) {
			return true;
		}
		return super.getIsRepairable(toRepair, repair);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.none;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		return true;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player) {
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer entityLiving) {
		if (!worldIn.isRemote) {
			dorightclick(stack, worldIn, entityLiving, true);
		}
		return stack;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		return true;
	}

	public void setArrow(Entity entity, ItemStack stack, GOTEntityGrapplingArrow arrow) {
		GOTItemGrapplingHook.grapplearrows.put(entity, arrow);
	}
}
