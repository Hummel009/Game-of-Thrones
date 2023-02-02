package got.common.entity.other;

import java.util.Random;

import got.common.database.GOTRegistry;
import got.common.item.other.GOTItemRobes;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.init.Items;
import net.minecraft.item.*;

public class GOTEntityUtils {
	public static int[] leatherDyes = { 10855845, 8026746, 5526612, 3684408, 8350297, 10388590, 4799795, 5330539, 4211801, 2632504 };
	public static int[] turbanColors = { 1643539, 6309443, 7014914, 7809314, 5978155 };

	public static ItemStack dyeLeather(ItemStack itemstack, int color) {
		((ItemArmor) itemstack.getItem()).func_82813_b(itemstack, color);
		return itemstack;
	}

	public static ItemStack dyeLeather(ItemStack itemstack, Random rand) {
		int i = rand.nextInt(leatherDyes.length);
		int color = leatherDyes[i];
		((ItemArmor) itemstack.getItem()).func_82813_b(itemstack, color);
		return itemstack;
	}

	public static EntityAITasks.EntityAITaskEntry removeAITask(EntityCreature entity, Class taskClass) {
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
		if ((legs == 0 || legs == 1) && !alreadyHasChain) {
			npc.setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.bronzeChainmailLeggings));
			alreadyHasChain = true;
		} else if ((legs == 2 || legs == 3 || legs == 4) && !alreadyHasChain) {
			npc.setCurrentItemOrArmor(2, new ItemStack(Items.chainmail_leggings));
			alreadyHasChain = true;
		} else {
			npc.setCurrentItemOrArmor(2, dyeLeather(new ItemStack(Items.leather_leggings), rand));
		}

		int body = rand.nextInt(10);
		if ((body == 0 || body == 1) && !alreadyHasChain) {
			npc.setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.bronzeChainmailChestplate));
			alreadyHasChain = true;
		} else if ((body == 2 || body == 3 || body == 4) && !alreadyHasChain) {
			npc.setCurrentItemOrArmor(3, new ItemStack(Items.chainmail_chestplate));
			alreadyHasChain = true;
		} else {
			npc.setCurrentItemOrArmor(3, dyeLeather(new ItemStack(Items.leather_chestplate), rand));
		}

		if (forceTurban) {
			ItemStack turban = new ItemStack(GOTRegistry.robesHelmet);
			int robeColor = turbanColors[rand.nextInt(turbanColors.length)];
			GOTItemRobes.setRobesColor(turban, robeColor);
			npc.setCurrentItemOrArmor(4, turban);
		} else if (rand.nextInt(5) != 0) {
			npc.setCurrentItemOrArmor(4, dyeLeather(new ItemStack(Items.leather_helmet), rand));
		}
	}
}
