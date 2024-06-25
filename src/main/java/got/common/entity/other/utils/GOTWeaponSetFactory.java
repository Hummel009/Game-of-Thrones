package got.common.entity.other.utils;

import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GOTWeaponSetFactory {
	private static final Map<Item, Integer> WESTEROS_WEAPON_PERCENTS = new HashMap<>();
	private static final Map<Item, Integer> ESSOS_WEAPON_PERCENTS = new HashMap<>();
	private static final Map<Item, Integer> PRIMITIVE_WEAPON_PERCENTS = new HashMap<>();
	private static final Map<Item, Integer> WILDLING_WEAPON_PERCENTS = new HashMap<>();
	private static final Map<Item, Integer> ASSHAI_WEAPON_PERCENTS = new HashMap<>();
	private static final Map<Item, Integer> SOTHORYOS_WEAPON_PERCENTS = new HashMap<>();
	private static final Map<Item, Integer> SUMMER_WEAPON_PERCENTS = new HashMap<>();
	private static final Map<Item, Integer> NOMAD_WEAPON_PERCENTS = new HashMap<>();
	private static final Map<Item, Integer> YI_TI_WEAPON_PERCENTS = new HashMap<>();

	static {
		WESTEROS_WEAPON_PERCENTS.put(GOTItems.westerosSword, 50);
		WESTEROS_WEAPON_PERCENTS.put(GOTItems.westerosHammer, 25);
		WESTEROS_WEAPON_PERCENTS.put(GOTItems.westerosPike, 10);
		WESTEROS_WEAPON_PERCENTS.put(GOTItems.westerosLongsword, 10);
		WESTEROS_WEAPON_PERCENTS.put(GOTItems.westerosGreatsword, 5);

		ESSOS_WEAPON_PERCENTS.put(GOTItems.essosSword, 50);
		ESSOS_WEAPON_PERCENTS.put(GOTItems.essosHammer, 25);
		ESSOS_WEAPON_PERCENTS.put(GOTItems.essosPike, 15);
		ESSOS_WEAPON_PERCENTS.put(GOTItems.essosPolearm, 10);

		PRIMITIVE_WEAPON_PERCENTS.put(GOTItems.ironBattleaxe, 30);
		PRIMITIVE_WEAPON_PERCENTS.put(GOTItems.trident, 25);
		PRIMITIVE_WEAPON_PERCENTS.put(GOTItems.harpoon, 20);
		PRIMITIVE_WEAPON_PERCENTS.put(GOTItems.club, 15);
		PRIMITIVE_WEAPON_PERCENTS.put(GOTItems.ironPike, 10);

		WILDLING_WEAPON_PERCENTS.put(GOTItems.wildlingSword, 40);
		WILDLING_WEAPON_PERCENTS.put(GOTItems.wildlingBattleaxe, 30);
		WILDLING_WEAPON_PERCENTS.put(GOTItems.wildlingHammer, 20);
		WILDLING_WEAPON_PERCENTS.put(GOTItems.wildlingPolearm, 10);

		ASSHAI_WEAPON_PERCENTS.put(GOTItems.asshaiSword, 50);
		ASSHAI_WEAPON_PERCENTS.put(GOTItems.asshaiBattleaxe, 30);
		ASSHAI_WEAPON_PERCENTS.put(GOTItems.asshaiHammer, 20);

		SOTHORYOS_WEAPON_PERCENTS.put(GOTItems.sothoryosHammer, 35);
		SOTHORYOS_WEAPON_PERCENTS.put(GOTItems.sothoryosSword, 30);
		SOTHORYOS_WEAPON_PERCENTS.put(GOTItems.sothoryosBattleaxe, 20);
		SOTHORYOS_WEAPON_PERCENTS.put(GOTItems.sothoryosPike, 15);

		SUMMER_WEAPON_PERCENTS.put(GOTItems.summerSword, 70);
		SUMMER_WEAPON_PERCENTS.put(GOTItems.summerPike, 30);

		NOMAD_WEAPON_PERCENTS.put(GOTItems.nomadSword, 70);
		NOMAD_WEAPON_PERCENTS.put(GOTItems.nomadBattleaxe, 30);

		YI_TI_WEAPON_PERCENTS.put(GOTItems.yiTiBattleaxe, 40);
		YI_TI_WEAPON_PERCENTS.put(GOTItems.yiTiPike, 30);
		YI_TI_WEAPON_PERCENTS.put(GOTItems.yiTiPolearm, 30);
		YI_TI_WEAPON_PERCENTS.put(GOTItems.yiTiSword, 30);
	}

	private GOTWeaponSetFactory() {
	}

	public static void setupEssosWeaponSet(GOTEntityNPC npc, Random rand) {
		GOTInventoryNPCItems npcItemsInv = npc.getNpcItemsInv();

		setupRandomWeapon(rand, ESSOS_WEAPON_PERCENTS, npcItemsInv);
		setupSpearWithChance(rand, npcItemsInv, GOTItems.essosSpear);
		setupIdleItems(npcItemsInv);
	}

	public static void setupYiTiWeaponSet(GOTEntityNPC npc, Random rand) {
		GOTInventoryNPCItems npcItemsInv = npc.getNpcItemsInv();

		setupRandomWeapon(rand, YI_TI_WEAPON_PERCENTS, npcItemsInv);
		setupSpearWithChance(rand, npcItemsInv, GOTItems.yiTiSpear);
		setupIdleItems(npcItemsInv);
	}

	public static void setupNomadWeaponSet(GOTEntityNPC npc, Random rand) {
		GOTInventoryNPCItems npcItemsInv = npc.getNpcItemsInv();

		setupRandomWeapon(rand, NOMAD_WEAPON_PERCENTS, npcItemsInv);
		setupSpearWithChance(rand, npcItemsInv, GOTItems.summerSpear);
		setupIdleItems(npcItemsInv);
	}

	public static void setupSummerWeaponSet(GOTEntityNPC npc, Random rand) {
		GOTInventoryNPCItems npcItemsInv = npc.getNpcItemsInv();

		setupRandomWeapon(rand, SUMMER_WEAPON_PERCENTS, npcItemsInv);
		setupSpearWithChance(rand, npcItemsInv, GOTItems.summerSpear);
		setupIdleItems(npcItemsInv);
	}

	public static void setupSothoryosWeaponSet(GOTEntityNPC npc, Random rand) {
		GOTInventoryNPCItems npcItemsInv = npc.getNpcItemsInv();

		setupRandomWeapon(rand, SOTHORYOS_WEAPON_PERCENTS, npcItemsInv);
		setupSpearWithChance(rand, npcItemsInv, GOTItems.sothoryosSpear);
		setupIdleItems(npcItemsInv);
	}

	public static void setupAsshaiWeaponSet(GOTEntityNPC npc, Random rand) {
		GOTInventoryNPCItems npcItemsInv = npc.getNpcItemsInv();

		setupRandomWeapon(rand, ASSHAI_WEAPON_PERCENTS, npcItemsInv);
		setupSpearWithChance(rand, npcItemsInv, GOTItems.asshaiSpear);
		setupIdleItems(npcItemsInv);
	}

	public static void setupWildlingWeaponSet(GOTEntityNPC npc, Random rand) {
		GOTInventoryNPCItems npcItemsInv = npc.getNpcItemsInv();

		setupRandomWeapon(rand, WILDLING_WEAPON_PERCENTS, npcItemsInv);
		setupSpearWithChance(rand, npcItemsInv, GOTItems.wildlingSpear);
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
	}

	public static void setupPrimitiveWeaponSet(GOTEntityNPC npc, Random rand) {
		GOTInventoryNPCItems npcItemsInv = npc.getNpcItemsInv();

		setupRandomWeapon(rand, PRIMITIVE_WEAPON_PERCENTS, npcItemsInv);
		setupSpearWithChance(rand, npcItemsInv, GOTItems.ironSpear);
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
	}

	public static void setupWesterosWeaponSet(GOTEntityNPC npc, Random rand) {
		GOTInventoryNPCItems npcItemsInv = npc.getNpcItemsInv();

		setupRandomWeapon(rand, WESTEROS_WEAPON_PERCENTS, npcItemsInv);
		setupSpearWithChance(rand, npcItemsInv, GOTItems.westerosSpear);
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