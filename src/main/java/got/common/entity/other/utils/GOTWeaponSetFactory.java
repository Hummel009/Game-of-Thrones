package got.common.entity.other.utils;

import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GOTWeaponSetFactory {
	private static final Map<Item, Integer> IRON_EASTERN = new HashMap<>();
	private static final Map<Item, Integer> IRON_WESTERN = new HashMap<>();

	private static final Map<Item, Integer> PRIMITIVE_BRONZE = new HashMap<>();
	private static final Map<Item, Integer> PRIMITIVE_IRON_HILLMEN = new HashMap<>();
	private static final Map<Item, Integer> PRIMITIVE_IRON_NOMAD = new HashMap<>();

	static {
		IRON_WESTERN.put(Items.iron_sword, 50);
		IRON_WESTERN.put(GOTItems.ironBattleaxe, 20);
		IRON_WESTERN.put(GOTItems.ironHammer, 20);
		IRON_WESTERN.put(GOTItems.ironPike, 10);

		IRON_EASTERN.put(GOTItems.ironScimitar, 60);
		IRON_EASTERN.put(GOTItems.ironBattleaxe, 20);
		IRON_EASTERN.put(GOTItems.ironHammer, 20);

		PRIMITIVE_BRONZE.put(GOTItems.bronzeScimitar, 30);
		PRIMITIVE_BRONZE.put(GOTItems.bronzeSword, 30);
		PRIMITIVE_BRONZE.put(GOTItems.bronzeBattleaxe, 20);
		PRIMITIVE_BRONZE.put(GOTItems.bronzeHammer, 20);

		PRIMITIVE_IRON_HILLMEN.put(GOTItems.ironScimitar, 30);
		PRIMITIVE_IRON_HILLMEN.put(Items.iron_sword, 25);
		PRIMITIVE_IRON_HILLMEN.put(GOTItems.ironBattleaxe, 15);
		PRIMITIVE_IRON_HILLMEN.put(GOTItems.ironHammer, 15);
		PRIMITIVE_IRON_HILLMEN.put(GOTItems.trident, 5);
		PRIMITIVE_IRON_HILLMEN.put(GOTItems.harpoon, 5);
		PRIMITIVE_IRON_HILLMEN.put(GOTItems.club, 5);

		PRIMITIVE_IRON_NOMAD.put(GOTItems.ironScimitar, 70);
		PRIMITIVE_IRON_NOMAD.put(GOTItems.ironBattleaxe, 30);
	}

	private GOTWeaponSetFactory() {
	}

	public static void setupPrimitiveBronzeWeaponSet(GOTEntityNPC npc, Random rand) {
		GOTInventoryNPCItems npcItemsInv = npc.getNpcItemsInv();

		setupRandomWeapon(rand, PRIMITIVE_BRONZE, npcItemsInv);
		setupSpearWithChance(rand, npcItemsInv, GOTItems.bronzeSpear);
		setupIdleItems(npcItemsInv);
	}

	public static void setupPrimitiveIronWeaponSet(GOTEntityNPC npc, Random rand, boolean nomad) {
		GOTInventoryNPCItems npcItemsInv = npc.getNpcItemsInv();

		setupRandomWeapon(rand, nomad ? PRIMITIVE_IRON_NOMAD : PRIMITIVE_IRON_HILLMEN, npcItemsInv);
		setupSpearWithChance(rand, npcItemsInv, GOTItems.ironSpear);
		setupIdleItems(npcItemsInv);
	}

	public static void setupIronWeaponSet(GOTEntityNPC npc, Random rand, boolean scimitar) {
		GOTInventoryNPCItems npcItemsInv = npc.getNpcItemsInv();

		setupRandomWeapon(rand, scimitar ? IRON_EASTERN : IRON_WESTERN, npcItemsInv);
		setupSpearWithChance(rand, npcItemsInv, GOTItems.ironSpear);
		setupIdleItems(npcItemsInv);
	}

	private static void setupRandomWeapon(Random rand, Map<Item, Integer> chances, GOTInventoryNPCItems npcItemsInv) {
		int randomNumber = rand.nextInt(100);
		Item saved = null;

		int cumulativeChance = 0;
		for (Map.Entry<Item, Integer> entry : chances.entrySet()) {
			cumulativeChance += entry.getValue();
			if (randomNumber < cumulativeChance) {
				saved = entry.getKey();
				break;
			}
		}

		npcItemsInv.setMeleeWeapon(new ItemStack(saved));
		npcItemsInv.setMeleeWeaponMounted(npcItemsInv.getMeleeWeapon());
	}

	private static void setupSpearWithChance(Random rand, GOTInventoryNPCItems npcItemsInv, Item summerSpear) {
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(summerSpear));
		}
	}

	private static void setupIdleItems(GOTInventoryNPCItems npcItemsInv) {
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		npcItemsInv.setIdleItemMounted(npcItemsInv.getMeleeWeaponMounted());
	}
}