package got.common.entity.other.utils;

import got.common.database.GOTItems;
import got.common.entity.essos.asshai.GOTEntityAsshaiMan;
import got.common.entity.essos.braavos.GOTEntityBraavosMan;
import got.common.entity.essos.dothraki.GOTEntityDothraki;
import got.common.entity.essos.ghiscar.GOTEntityGhiscarMan;
import got.common.entity.essos.ibben.GOTEntityIbbenMason;
import got.common.entity.essos.jogos.GOTEntityJogos;
import got.common.entity.essos.lhazar.GOTEntityLhazarMan;
import got.common.entity.essos.lorath.GOTEntityLorathMan;
import got.common.entity.essos.lys.GOTEntityLysMan;
import got.common.entity.essos.mossovy.GOTEntityMossovyMan;
import got.common.entity.essos.myr.GOTEntityMyrMan;
import got.common.entity.essos.norvos.GOTEntityNorvosMan;
import got.common.entity.essos.pentos.GOTEntityPentosMan;
import got.common.entity.essos.qarth.GOTEntityQarthMan;
import got.common.entity.essos.qohor.GOTEntityQohorMan;
import got.common.entity.essos.tyrosh.GOTEntityTyroshMan;
import got.common.entity.essos.volantis.GOTEntityVolantisMan;
import got.common.entity.essos.yiti.GOTEntityYiTiMan;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.sothoryos.sothoryos.GOTEntitySothoryosMan;
import got.common.entity.westeros.arryn.GOTEntityArrynMan;
import got.common.entity.westeros.crownlands.GOTEntityCrownlandsMan;
import got.common.entity.westeros.dorne.GOTEntityDorneMan;
import got.common.entity.westeros.dragonstone.GOTEntityDragonstoneMan;
import got.common.entity.westeros.gift.GOTEntityGiftMan;
import got.common.entity.westeros.hillmen.GOTEntityHillman;
import got.common.entity.westeros.ironborn.GOTEntityIronbornMan;
import got.common.entity.westeros.legendary.GOTEntityCrasterWife;
import got.common.entity.westeros.legendary.trader.GOTEntityCraster;
import got.common.entity.westeros.north.GOTEntityNorthMan;
import got.common.entity.westeros.north.hillmen.GOTEntityNorthHillman;
import got.common.entity.westeros.reach.GOTEntityReachMan;
import got.common.entity.westeros.riverlands.GOTEntityRiverlandsMan;
import got.common.entity.westeros.stormlands.GOTEntityStormlandsMan;
import got.common.entity.westeros.westerlands.GOTEntityWesterlandsMan;
import got.common.entity.westeros.wildling.GOTEntityWildling;
import got.common.entity.westeros.wildling.thenn.GOTEntityThenn;
import got.common.item.other.GOTItemRobes;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

public class GOTEntityUtils {
	private static final int[] LEATHER_DYES = {10855845, 8026746, 5526612, 3684408, 8350297, 10388590, 4799795, 5330539, 4211801, 2632504};
	private static final int[] TURBAN_COLORS = {1643539, 6309443, 7014914, 7809314, 5978155};

	private static final Collection<Class<? extends GOTEntityHumanBase>> BASIC_NPC = new HashSet<>();
	private static final Collection<Class<? extends GOTEntityHumanBase>> CAN_BE_MARRIED = new HashSet<>();
	private static final Collection<Class<? extends GOTEntityHumanBase>> CAN_BE_ROBBED = new HashSet<>();
	private static final Collection<Class<? extends GOTEntityHumanBase>> CAN_SMOKE_DRINK = new HashSet<>();

	private static final Collection<Class<? extends GOTEntityHumanBase>> CRASTER_SAFESPACE = new HashSet<>();

	static {
		BASIC_NPC.add(GOTEntityArrynMan.class);
		BASIC_NPC.add(GOTEntityAsshaiMan.class);
		BASIC_NPC.add(GOTEntityBraavosMan.class);
		BASIC_NPC.add(GOTEntityCrownlandsMan.class);
		BASIC_NPC.add(GOTEntityDorneMan.class);
		BASIC_NPC.add(GOTEntityDothraki.class);
		BASIC_NPC.add(GOTEntityDragonstoneMan.class);
		BASIC_NPC.add(GOTEntityGhiscarMan.class);
		BASIC_NPC.add(GOTEntityGiftMan.class);
		BASIC_NPC.add(GOTEntityHillman.class);
		BASIC_NPC.add(GOTEntityIbbenMason.class);
		BASIC_NPC.add(GOTEntityIronbornMan.class);
		BASIC_NPC.add(GOTEntityJogos.class);
		BASIC_NPC.add(GOTEntityLhazarMan.class);
		BASIC_NPC.add(GOTEntityLorathMan.class);
		BASIC_NPC.add(GOTEntityLysMan.class);
		BASIC_NPC.add(GOTEntityMossovyMan.class);
		BASIC_NPC.add(GOTEntityMyrMan.class);
		BASIC_NPC.add(GOTEntityNorthHillman.class);
		BASIC_NPC.add(GOTEntityNorthMan.class);
		BASIC_NPC.add(GOTEntityNorvosMan.class);
		BASIC_NPC.add(GOTEntityPentosMan.class);
		BASIC_NPC.add(GOTEntityQarthMan.class);
		BASIC_NPC.add(GOTEntityQohorMan.class);
		BASIC_NPC.add(GOTEntityReachMan.class);
		BASIC_NPC.add(GOTEntityRiverlandsMan.class);
		BASIC_NPC.add(GOTEntitySothoryosMan.class);
		BASIC_NPC.add(GOTEntityStormlandsMan.class);
		BASIC_NPC.add(GOTEntityThenn.class);
		BASIC_NPC.add(GOTEntityTyroshMan.class);
		BASIC_NPC.add(GOTEntityVolantisMan.class);
		BASIC_NPC.add(GOTEntityWesterlandsMan.class);
		BASIC_NPC.add(GOTEntityWildling.class);
		BASIC_NPC.add(GOTEntityYiTiMan.class);

		CAN_BE_MARRIED.addAll(BASIC_NPC);
		CAN_BE_ROBBED.addAll(BASIC_NPC);

		CAN_SMOKE_DRINK.add(GOTEntityArrynMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityAsshaiMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityBraavosMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityCrownlandsMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityDorneMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityDragonstoneMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityGhiscarMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityGiftMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityIbbenMason.class);
		CAN_SMOKE_DRINK.add(GOTEntityIronbornMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityLhazarMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityLorathMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityLysMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityMossovyMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityMyrMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityNorthMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityNorvosMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityPentosMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityQarthMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityQohorMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityReachMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityRiverlandsMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityStormlandsMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityTyroshMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityVolantisMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityWesterlandsMan.class);
		CAN_SMOKE_DRINK.add(GOTEntityYiTiMan.class);

		CRASTER_SAFESPACE.add(GOTEntityCraster.class);
		CRASTER_SAFESPACE.add(GOTEntityCrasterWife.class);
	}

	private GOTEntityUtils() {
	}

	public static boolean isCrasterFamily(GOTEntityNPC npc) {
		return CRASTER_SAFESPACE.contains(npc.getClass());
	}

	public static boolean canBeMarried(GOTEntityNPC npc) {
		return npc.getFamilyInfo().getAge() >= 0 && CAN_BE_MARRIED.contains(npc.getClass());
	}

	public static boolean canBeRobbed(GOTEntityNPC npc) {
		return npc.getFamilyInfo().getAge() >= 0 && CAN_BE_ROBBED.contains(npc.getClass());
	}

	public static boolean canSmokeDrink(GOTEntityNPC npc) {
		return npc.getFamilyInfo().isMale() && npc.getFamilyInfo().getAge() >= 0 && CAN_SMOKE_DRINK.contains(npc.getClass());
	}

	private static ItemStack dyeLeather(ItemStack itemstack, Random rand) {
		int i = rand.nextInt(LEATHER_DYES.length);
		int color = LEATHER_DYES[i];
		((ItemArmor) itemstack.getItem()).func_82813_b(itemstack, color);
		return itemstack;
	}

	public static EntityAITasks.EntityAITaskEntry removeAITask(EntityCreature entity, Class<? extends EntityAIBase> taskClass) {
		int i;
		EntityAITasks.EntityAITaskEntry taskEntry;
		for (i = 0; i < entity.tasks.taskEntries.size(); ++i) {
			taskEntry = (EntityAITasks.EntityAITaskEntry) entity.tasks.taskEntries.get(i);
			if (!taskClass.isAssignableFrom(taskEntry.action.getClass())) {
				continue;
			}
			entity.tasks.removeTask(taskEntry.action);
			return taskEntry;
		}
		for (i = 0; i < entity.targetTasks.taskEntries.size(); ++i) {
			taskEntry = (EntityAITasks.EntityAITaskEntry) entity.targetTasks.taskEntries.get(i);
			if (!taskClass.isAssignableFrom(taskEntry.action.getClass())) {
				continue;
			}
			entity.targetTasks.removeTask(taskEntry.action);
			return taskEntry;
		}
		return null;
	}

	public static void setLevymanArmor(GOTEntityNPC npc, Random rand) {
		setLevymanArmor(npc, rand, false);
	}

	public static void setLevymanArmor(GOTEntityNPC npc, Random rand, boolean forceTurban) {
		boolean alreadyHasChain = false;
		npc.setCurrentItemOrArmor(1, dyeLeather(new ItemStack(Items.leather_boots), rand));

		int legs = rand.nextInt(10);
		if (legs == 0 || legs == 1) {
			npc.setCurrentItemOrArmor(2, new ItemStack(GOTItems.bronzeChainmailLeggings));
			alreadyHasChain = true;
		} else if (legs == 2 || legs == 3 || legs == 4) {
			npc.setCurrentItemOrArmor(2, new ItemStack(Items.chainmail_leggings));
			alreadyHasChain = true;
		} else {
			npc.setCurrentItemOrArmor(2, dyeLeather(new ItemStack(Items.leather_leggings), rand));
		}
		int body = rand.nextInt(10);
		if ((body == 0 || body == 1) && !alreadyHasChain) {
			npc.setCurrentItemOrArmor(3, new ItemStack(GOTItems.bronzeChainmailChestplate));
		} else if ((body == 2 || body == 3 || body == 4) && !alreadyHasChain) {
			npc.setCurrentItemOrArmor(3, new ItemStack(Items.chainmail_chestplate));
		} else {
			npc.setCurrentItemOrArmor(3, dyeLeather(new ItemStack(Items.leather_chestplate), rand));
		}
		if (forceTurban) {
			ItemStack turban = new ItemStack(GOTItems.robesHelmet);
			int robeColor = TURBAN_COLORS[rand.nextInt(TURBAN_COLORS.length)];
			GOTItemRobes.setRobesColor(turban, robeColor);
			npc.setCurrentItemOrArmor(4, turban);
		} else if (rand.nextInt(5) != 0) {
			npc.setCurrentItemOrArmor(4, dyeLeather(new ItemStack(Items.leather_helmet), rand));
		}
	}
}