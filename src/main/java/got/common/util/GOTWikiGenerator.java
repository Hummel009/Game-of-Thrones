package got.common.util;

import got.common.GOTAchievementRank;
import got.common.GOTDate;
import got.common.block.other.GOTBlockOreGem;
import got.common.block.other.GOTBlockRock;
import got.common.database.*;
import got.common.entity.GOTEntityRegistry;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.iface.*;
import got.common.entity.other.utils.GOTEntityUtils;
import got.common.entity.other.utils.GOTTradeEntry;
import got.common.entity.other.utils.GOTUnitTradeEntry;
import got.common.entity.westeros.legendary.captain.*;
import got.common.entity.westeros.legendary.deco.GOTEntityMyrcellaBaratheon;
import got.common.entity.westeros.legendary.deco.GOTEntityTommenBaratheon;
import got.common.entity.westeros.legendary.mercenary.GOTEntityBronn;
import got.common.entity.westeros.legendary.mercenary.GOTEntityVargoHoat;
import got.common.entity.westeros.legendary.quest.GOTEntityCerseiLannister;
import got.common.entity.westeros.legendary.quest.GOTEntitySamwellTarly;
import got.common.entity.westeros.legendary.quest.GOTEntityTyrionLannister;
import got.common.entity.westeros.legendary.quest.GOTEntityVarys;
import got.common.entity.westeros.legendary.reborn.GOTEntityJonSnow;
import got.common.entity.westeros.legendary.reborn.GOTEntityLancelLannister;
import got.common.entity.westeros.legendary.trader.*;
import got.common.entity.westeros.legendary.warrior.*;
import got.common.faction.GOTFaction;
import got.common.faction.GOTFactionRank;
import got.common.item.other.GOTItemBanner;
import got.common.item.weapon.GOTItemSword;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.GOTBiomeDecorator;
import got.common.world.biome.GOTClimateType;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.biome.variant.GOTBiomeVariantList;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTAbstractWaypoint;
import got.common.world.map.GOTFixer;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTFactionContainer;
import got.common.world.spawning.GOTSpawnEntry;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.GOTStructureRegistry;
import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.other.GOTStructureBaseSettlement;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("ConstantValue")
public class GOTWikiGenerator {
	private static final Map<Class<? extends Entity>, Entity> ENTITY_CLASS_TO_ENTITY = new HashMap<>();
	private static final Map<Class<? extends Entity>, GOTWaypoint> ENTITY_CLASS_TO_WP = new HashMap<>();
	private static final Map<Class<? extends WorldGenerator>, GOTStructureBase> STRUCTURE_CLASS_TO_STRUCTURE = new HashMap<>();

	private static final Map<GOTFaction, String> FACTION_TO_PAGENAME = new EnumMap<>(GOTFaction.class);
	private static final Map<Class<? extends Entity>, String> ENTITY_CLASS_TO_PAGENAME = new HashMap<>();
	private static final Map<GOTBiome, String> BIOME_TO_PAGENAME = new HashMap<>();

	private static final Iterable<Item> ITEMS = new ArrayList<>(GOTItems.CONTENT);
	private static final Iterable<GOTUnitTradeEntries> UNIT_TRADE_ENTRIES = new ArrayList<>(GOTUnitTradeEntries.CONTENT);
	private static final Iterable<Class<? extends Entity>> ENTITY_CLASSES = new HashSet<>(GOTEntityRegistry.CONTENT);
	private static final Iterable<GOTAchievement> ACHIEVEMENTS = new ArrayList<>(GOTAchievement.CONTENT);

	private static final Collection<GOTBiome> BIOMES = new HashSet<>(GOTBiome.CONTENT);

	private static final Iterable<GOTFaction> FACTIONS = EnumSet.allOf(GOTFaction.class);
	private static final Iterable<GOTTreeType> TREES = EnumSet.allOf(GOTTreeType.class);
	private static final Iterable<GOTWaypoint> WAYPOINTS = EnumSet.allOf(GOTWaypoint.class);
	private static final Iterable<GOTCapes> CAPES = EnumSet.allOf(GOTCapes.class);
	private static final Iterable<GOTShields> SHIELDS = EnumSet.allOf(GOTShields.class);

	private static final Collection<String> MINERALS = new HashSet<>();
	private static final Collection<Class<? extends WorldGenerator>> STRUCTURE_CLASSES = new HashSet<>();

	private static final String BEGIN = "</title>\n<ns>10</ns>\n<revision>\n<text>&lt;includeonly&gt;{{#switch: {{{1}}}";
	private static final String END = "\n}}&lt;/includeonly&gt;&lt;noinclude&gt;[[" + Lang.CATEGORY + "]]&lt;/noinclude&gt;</text>\n</revision>\n</page>\n";
	private static final String TITLE = "<page>\n<title>";
	private static final String TITLE_SINGLE = "<page><title>";
	private static final String PAGE_LEFT = "</title><revision><text>";
	private static final String PAGE_RIGHT = "</text></revision></page>\n";
	private static final String UTF_8 = "UTF-8";
	private static final String TEMPLATE = "Template:";
	private static final String NL = "\n";
	private static final String TRUE = "True";
	private static final String FALSE = "False";
	private static final String N_A = "N/A";

	private static final String NAMESPACE = "";
	private static final String NAMESPACE_NAME = "";

	static {
		BIOMES.remove(GOTBiome.ocean1);
		BIOMES.remove(GOTBiome.ocean2);
		BIOMES.remove(GOTBiome.ocean3);
		BIOMES.remove(GOTBiome.beach);
		BIOMES.remove(GOTBiome.beachGravel);
		BIOMES.remove(GOTBiome.beachWhite);
		BIOMES.remove(GOTBiome.lake);
		BIOMES.remove(GOTBiome.river);
		BIOMES.remove(GOTBiome.island);
		BIOMES.remove(GOTBiome.kingSpears);
		BIOMES.remove(GOTBiome.bleedingBeach);
		BIOMES.remove(GOTBiome.valyriaSea1);
		BIOMES.remove(GOTBiome.valyriaSea2);

		ENTITY_CLASS_TO_WP.put(GOTEntityYgritte.class, GOTWaypoint.HARDHOME);
		ENTITY_CLASS_TO_WP.put(GOTEntityTormund.class, GOTWaypoint.HARDHOME);
		ENTITY_CLASS_TO_WP.put(GOTEntityManceRayder.class, GOTWaypoint.HARDHOME);
		ENTITY_CLASS_TO_WP.put(GOTEntityCraster.class, GOTWaypoint.CRASTERS_KEEP);
		ENTITY_CLASS_TO_WP.put(GOTEntityVictarionGreyjoy.class, GOTWaypoint.VICTARION_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityLancelLannister.LancelLannisterNormal.class, GOTWaypoint.KINGS_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityTobhoMott.class, GOTWaypoint.KINGS_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityTyrionLannister.class, GOTWaypoint.KINGS_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityGendryBaratheon.class, GOTWaypoint.KINGS_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityPetyrBaelish.class, GOTWaypoint.KINGS_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityBronn.class, GOTWaypoint.KINGS_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityPodrickPayne.class, GOTWaypoint.KINGS_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityHotPie.class, GOTWaypoint.CROSSROADS_INN);
		ENTITY_CLASS_TO_WP.put(GOTEntityVargoHoat.class, GOTWaypoint.CROSSROADS_INN);
		ENTITY_CLASS_TO_WP.put(GOTEntitySandorClegane.class, GOTWaypoint.KINGS_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityJoffreyBaratheon.class, GOTWaypoint.KINGS_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityCerseiLannister.class, GOTWaypoint.KINGS_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityJaimeLannister.class, GOTWaypoint.KINGS_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityPycelle.class, GOTWaypoint.KINGS_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityJanosSlynt.class, GOTWaypoint.KINGS_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityVarys.class, GOTWaypoint.KINGS_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityIlynPayne.class, GOTWaypoint.KINGS_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityHighSepton.class, GOTWaypoint.KINGS_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityTommenBaratheon.class, GOTWaypoint.KINGS_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityMyrcellaBaratheon.class, GOTWaypoint.KINGS_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityMerynTrant.class, GOTWaypoint.KINGS_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityBarristanSelmy.class, GOTWaypoint.KINGS_LANDING);
		ENTITY_CLASS_TO_WP.put(GOTEntityJeorMormont.class, GOTWaypoint.CASTLE_BLACK);
		ENTITY_CLASS_TO_WP.put(GOTEntityJonSnow.JonSnowLife1.class, GOTWaypoint.CASTLE_BLACK);
		ENTITY_CLASS_TO_WP.put(GOTEntityAemonTargaryen.class, GOTWaypoint.CASTLE_BLACK);
		ENTITY_CLASS_TO_WP.put(GOTEntityAlliserThorne.class, GOTWaypoint.CASTLE_BLACK);
		ENTITY_CLASS_TO_WP.put(GOTEntityEdd.class, GOTWaypoint.CASTLE_BLACK);
		ENTITY_CLASS_TO_WP.put(GOTEntitySamwellTarly.class, GOTWaypoint.CASTLE_BLACK);
		ENTITY_CLASS_TO_WP.put(GOTEntityCotterPyke.class, GOTWaypoint.EASTWATCH);
		ENTITY_CLASS_TO_WP.put(GOTEntityHarmune.class, GOTWaypoint.EASTWATCH);
		ENTITY_CLASS_TO_WP.put(GOTEntityDenysMallister.class, GOTWaypoint.SHADOW_TOWER);
		ENTITY_CLASS_TO_WP.put(GOTEntityMullin.class, GOTWaypoint.SHADOW_TOWER);
	}

	private GOTWikiGenerator() {
	}

	@SuppressWarnings({"JoinDeclarationAndAssignmentJava", "StreamToLoop"})
	public static void generate(Type type, World world, EntityPlayer entityPlayer) {
		long time = System.nanoTime();

		try {
			Files.createDirectories(Paths.get("hummel"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		switch (type) {
			case TABLES:
				Collection<Runnable> tableGens = new HashSet<>();

				tableGens.add(GOTWikiGenerator::genTableShields);
				tableGens.add(GOTWikiGenerator::genTableCapes);
				tableGens.add(GOTWikiGenerator::genTableArmor);
				tableGens.add(GOTWikiGenerator::genTableWeapons);
				tableGens.add(GOTWikiGenerator::genTableFood);
				tableGens.add(() -> genTableAchievements(entityPlayer));

				tableGens.parallelStream().forEach(Runnable::run);

				break;
			case XML:
				try (PrintWriter printWriter = new PrintWriter("hummel/import.xml", UTF_8)) {
					StringBuilder sb = new StringBuilder();

					GOTDate.Season season = GOTDate.AegonCalendar.getSeason();

					sb.append("<mediawiki xmlns=\"http://www.mediawiki.org/xml/export-0.11/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.mediawiki.org/xml/export-0.11/ http://www.mediawiki.org/xml/export-0.11.xsd\" version=\"0.11\" xml:lang=\"ru\">\n");

					Collection<Runnable> runnables = new HashSet<>();

					runnables.add(GOTWikiGenerator::searchForMinerals);
					runnables.add(GOTWikiGenerator::searchForPagenamesEntity);
					runnables.add(GOTWikiGenerator::searchForPagenamesBiome);
					runnables.add(GOTWikiGenerator::searchForPagenamesFaction);
					runnables.add(() -> searchForEntities(world));
					runnables.add(() -> searchForStructures(world));

					runnables.forEach(Runnable::run);

					Collection<Supplier<StringBuilder>> suppliers = new HashSet<>();
					List<StringBuilder> results;

					Set<String> existingPages = getExistingPages();
					Collection<String> neededPages = new HashSet<>();

					suppliers.add(() -> addPagesMinerals(neededPages, existingPages));
					suppliers.add(() -> addPagesEntities(neededPages, existingPages));
					suppliers.add(() -> addPagesBiomes(neededPages, existingPages));
					suppliers.add(() -> addPagesFactions(neededPages, existingPages));
					suppliers.add(() -> addPagesTrees(neededPages, existingPages));
					suppliers.add(() -> addPagesStructures(neededPages, existingPages));

					results = suppliers.stream().map(Supplier::get).collect(Collectors.toList());
					suppliers.clear();

					for (StringBuilder stringBuilder : results) {
						sb.append(stringBuilder);
					}

					results.clear();

					markPagesForRemoval(neededPages, existingPages);

					suppliers.add(GOTWikiGenerator::genTemplateMineralBiomes);
					suppliers.add(GOTWikiGenerator::genTemplateTreeBiomes);

					suppliers.add(GOTWikiGenerator::genTemplateStructureBiomes);
					suppliers.add(GOTWikiGenerator::genTemplateStructureEntities);

					suppliers.add(GOTWikiGenerator::genTemplateBiomeAnimals);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeBandits);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeClimate);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeConquestFactions);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeInvasionFactions);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeMinerals);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeMusic);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeNPCs);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeName);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeRainfall);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeStructures);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeTemperature);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeTrees);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeVariants);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeVisitAchievement);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeWaypoints);

					suppliers.add(GOTWikiGenerator::genTemplateFactionBanners);
					suppliers.add(GOTWikiGenerator::genTemplateFactionCharacters);
					suppliers.add(GOTWikiGenerator::genTemplateFactionCodename);
					suppliers.add(GOTWikiGenerator::genTemplateFactionConquestBiomes);
					suppliers.add(GOTWikiGenerator::genTemplateFactionEnemies);
					suppliers.add(GOTWikiGenerator::genTemplateFactionFriends);
					suppliers.add(GOTWikiGenerator::genTemplateFactionInvasionBiomes);
					suppliers.add(GOTWikiGenerator::genTemplateFactionNPCs);
					suppliers.add(GOTWikiGenerator::genTemplateFactionName);
					suppliers.add(GOTWikiGenerator::genTemplateFactionOathRank);
					suppliers.add(GOTWikiGenerator::genTemplateFactionRanks);
					suppliers.add(GOTWikiGenerator::genTemplateFactionRegion);
					suppliers.add(GOTWikiGenerator::genTemplateFactionShieldsCapes);
					suppliers.add(GOTWikiGenerator::genTemplateFactionSpawnBiomes);
					suppliers.add(GOTWikiGenerator::genTemplateFactionWarCrimes);
					suppliers.add(GOTWikiGenerator::genTemplateFactionWaypoints);

					suppliers.add(GOTWikiGenerator::genTemplateEntityBannerBearer);
					suppliers.add(GOTWikiGenerator::genTemplateEntityBiomes);
					suppliers.add(GOTWikiGenerator::genTemplateEntityBuysPool);
					suppliers.add(GOTWikiGenerator::genTemplateEntityCharacter);
					suppliers.add(GOTWikiGenerator::genTemplateEntityFaction);
					suppliers.add(GOTWikiGenerator::genTemplateEntityFarmhand);
					suppliers.add(GOTWikiGenerator::genTemplateEntityHealth);
					suppliers.add(GOTWikiGenerator::genTemplateEntityHireReputation);
					suppliers.add(GOTWikiGenerator::genTemplateEntityHirePrice);
					suppliers.add(GOTWikiGenerator::genTemplateEntityHirePriceOath);
					suppliers.add(GOTWikiGenerator::genTemplateEntityHireable);
					suppliers.add(GOTWikiGenerator::genTemplateEntityImmuneToFire);
					suppliers.add(GOTWikiGenerator::genTemplateEntityImmuneToFrost);
					suppliers.add(GOTWikiGenerator::genTemplateEntityImmuneToHeat);
					suppliers.add(GOTWikiGenerator::genTemplateEntityKillAchievement);
					suppliers.add(GOTWikiGenerator::genTemplateEntityKillReputation);
					suppliers.add(GOTWikiGenerator::genTemplateEntityLegendaryDrop);
					suppliers.add(GOTWikiGenerator::genTemplateEntityMarriage);
					suppliers.add(GOTWikiGenerator::genTemplateEntityMercenary);
					suppliers.add(GOTWikiGenerator::genTemplateEntityNPC);
					suppliers.add(GOTWikiGenerator::genTemplateEntityOwners);
					suppliers.add(GOTWikiGenerator::genTemplateEntityRideableAnimal);
					suppliers.add(GOTWikiGenerator::genTemplateEntityRideableNPC);
					suppliers.add(GOTWikiGenerator::genTemplateEntitySellsPool);
					suppliers.add(GOTWikiGenerator::genTemplateEntitySellsUnitPool);
					suppliers.add(GOTWikiGenerator::genTemplateEntitySmith);
					suppliers.add(GOTWikiGenerator::genTemplateEntitySpawnsInDarkness);
					suppliers.add(GOTWikiGenerator::genTemplateEntityTargetSeeker);
					suppliers.add(GOTWikiGenerator::genTemplateEntityTradeable);
					suppliers.add(GOTWikiGenerator::genTemplateEntityUnitTradeable);
					suppliers.add(() -> genTemplateEntityWaypoint(world));

					results = suppliers.parallelStream().map(Supplier::get).collect(Collectors.toList());
					suppliers.clear();

					for (StringBuilder stringBuilder : results) {
						sb.append(stringBuilder);
					}

					results.clear();

					sb.append("</mediawiki>");

					GOTDate.AegonCalendar.getDate().getMonth().setSeason(season);

					printWriter.write(sb.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}

				break;
		}

		long newTime = System.nanoTime();

		IChatComponent component = new ChatComponentText("Generated in " + (newTime - time) / 1.0E6 + " milliseconds");
		entityPlayer.addChatMessage(component);
	}

	@SuppressWarnings("StringBufferReplaceableByString")
	private static void genTableAchievements(EntityPlayer entityPlayer) {
		Collection<String> data = new ArrayList<>();

		for (GOTAchievement achievement : ACHIEVEMENTS) {
			if (!(achievement instanceof GOTAchievementRank)) {
				StringBuilder sb = new StringBuilder();

				sb.append(NL).append("| ");
				sb.append(achievement.getTitle(entityPlayer));
				sb.append(" || ").append(achievement.getDescription());
				sb.append(NL).append("|-");

				data.add(sb.toString());
			}
		}

		StringBuilder sb = new StringBuilder();

		for (String datum : data) {
			sb.append(datum);
		}

		try (PrintWriter printWriter = new PrintWriter("hummel/achievements.txt", UTF_8)) {
			printWriter.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void genTableArmor() {
		Collection<String> data = new ArrayList<>();

		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
		symbols.setDecimalSeparator('.');
		DecimalFormat decimalFormat = new DecimalFormat("#.#", symbols);

		for (Item item : ITEMS) {
			if (item instanceof ItemArmor) {
				StringBuilder sb = new StringBuilder();

				float damage = ((ItemArmor) item).damageReduceAmount;
				ItemArmor.ArmorMaterial material = ((ItemArmor) item).getArmorMaterial();

				sb.append(NL).append("| ");
				sb.append(getItemName(item));
				sb.append(" || ").append(getItemFilename(item));
				sb.append(" || ").append(decimalFormat.format(item.getMaxDamage()));
				sb.append(" || ").append("{{Bar Armor|").append(decimalFormat.format(damage)).append("}}");

				sb.append(" || ");
				if (material == null || material.customCraftingMaterial == null) {
					sb.append(N_A);
				} else {
					sb.append(getItemName(material.customCraftingMaterial));
				}

				sb.append(NL).append("|-");

				data.add(sb.toString());
			}
		}

		StringBuilder sb = new StringBuilder();

		for (String datum : data) {
			sb.append(datum);
		}

		try (PrintWriter printWriter = new PrintWriter("hummel/armor.txt", UTF_8)) {
			printWriter.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("StringBufferReplaceableByString")
	private static void genTableCapes() {
		Collection<String> data = new ArrayList<>();

		for (GOTCapes cape : CAPES) {
			StringBuilder sb = new StringBuilder();

			sb.append(NL).append("| ");
			sb.append(cape.getCapeName());
			sb.append(" || ").append(cape.getCapeDesc());
			sb.append(" || ").append(getCapeFilename(cape));
			sb.append(NL).append("|-");

			data.add(sb.toString());
		}

		StringBuilder sb = new StringBuilder();

		for (String datum : data) {
			sb.append(datum);
		}

		try (PrintWriter printWriter = new PrintWriter("hummel/capes.txt", UTF_8)) {
			printWriter.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private static void genTableFood() {
		Collection<String> data = new TreeSet<>();

		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
		symbols.setDecimalSeparator('.');
		DecimalFormat decimalFormat = new DecimalFormat("#.#", symbols);

		for (Item item : ITEMS) {
			if (item instanceof ItemFood) {
				StringBuilder sb = new StringBuilder();

				int heal = ((ItemFood) item).func_150905_g(null);
				float saturation = ((ItemFood) item).func_150906_h(null);

				sb.append(NL).append("| ");
				sb.append(getItemName(item));
				sb.append(" || ").append(getItemFilename(item));
				sb.append(" || ").append("{{Bar Bread|").append(decimalFormat.format(saturation * heal * 2)).append("}}");
				sb.append(" || ").append("{{Bar Food|").append(decimalFormat.format(heal)).append("}}");
				sb.append(" || ").append(decimalFormat.format(item.getItemStackLimit()));
				sb.append(NL).append("|-");

				data.add(sb.toString());
			}
		}

		StringBuilder sb = new StringBuilder();

		for (String datum : data) {
			sb.append(datum);
		}

		try (PrintWriter printWriter = new PrintWriter("hummel/food.txt", UTF_8)) {
			printWriter.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("StringBufferReplaceableByString")
	private static void genTableShields() {
		Collection<String> data = new ArrayList<>();

		for (GOTShields shield : SHIELDS) {
			StringBuilder sb = new StringBuilder();

			sb.append(NL).append("| ");
			sb.append(shield.getShieldName());
			sb.append(" || ").append(shield.getShieldDesc());
			sb.append(" || ").append(getShieldFilename(shield));
			sb.append(NL).append("|-");

			data.add(sb.toString());
		}

		StringBuilder sb = new StringBuilder();

		for (String datum : data) {
			sb.append(datum);
		}

		try (PrintWriter printWriter = new PrintWriter("hummel/shields.txt", UTF_8)) {
			printWriter.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void genTableWeapons() {
		Collection<String> data = new ArrayList<>();

		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
		symbols.setDecimalSeparator('.');
		DecimalFormat decimalFormat = new DecimalFormat("#.#", symbols);

		for (Item item : ITEMS) {
			if (item instanceof ItemSword) {
				StringBuilder sb = new StringBuilder();

				float damage;

				if (item instanceof GOTItemSword) {
					GOTItemSword gotItemSword = (GOTItemSword) item;
					damage = gotItemSword.getGotWeaponDamage();
				} else {
					damage = GOTReflection.getDamageAmount(item);
				}

				Item.ToolMaterial material = GOTReflection.getToolMaterial(item);

				sb.append(NL).append("| ");
				sb.append(getItemName(item));
				sb.append(" || ").append(getItemFilename(item));
				sb.append(" || ").append(decimalFormat.format(item.getMaxDamage()));
				sb.append(" || ").append("{{Bar Hearts|").append(decimalFormat.format(damage)).append("}}");

				sb.append(" || ");
				if (material == null || material.getRepairItemStack() == null) {
					sb.append(N_A);
				} else {
					sb.append(getItemName(material.getRepairItemStack().getItem()));
				}

				sb.append(NL).append("|-");

				data.add(sb.toString());
			}
		}

		StringBuilder sb = new StringBuilder();

		for (String datum : data) {
			sb.append(datum);
		}

		try (PrintWriter printWriter = new PrintWriter("hummel/weapon.txt", UTF_8)) {
			printWriter.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static StringBuilder genTemplateBiomeAnimals() {
		Map<GOTBiome, Set<String>> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			Collection<BiomeGenBase.SpawnListEntry> spawnListEntries = new HashSet<>(biome.getSpawnableList(EnumCreatureType.ambient));
			spawnListEntries.addAll(biome.getSpawnableList(EnumCreatureType.waterCreature));
			spawnListEntries.addAll(biome.getSpawnableList(EnumCreatureType.creature));
			spawnListEntries.addAll(biome.getSpawnableList(EnumCreatureType.monster));
			spawnListEntries.addAll(biome.getSpawnableList(GOTBiome.CREATURE_TYPE_GOT_AMBIENT));

			for (BiomeGenBase.SpawnListEntry spawnListEntry : spawnListEntries) {
				data.computeIfAbsent(biome, s -> new TreeSet<>());
				data.get(biome).add(getEntityLink(spawnListEntry.entityClass));
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBBiome-Animals");
		sb.append(BEGIN);

		appendDefault(sb, Lang.BIOME_NO_ANIMALS.toString());

		for (Map.Entry<GOTBiome, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(entry.getKey())).append(" = ");
			sb.append(Lang.BIOME_HAS_ANIMALS);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeBandits() {
		Map<GOTBiome, String> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			data.put(biome, biome.getBanditChance().toString());
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBBiome-Bandits");
		sb.append(BEGIN);

		appendDefault(sb, "UNKNOWN");

		for (Map.Entry<GOTBiome, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeClimate() {
		Map<GOTBiome, String> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			if (biome.getClimateType() != null) {
				switch (biome.getClimateType()) {
					case COLD:
						data.put(biome, Lang.CLIMATE_COLD.toString());
						break;
					case COLD_AZ:
						data.put(biome, Lang.CLIMATE_COLD_AZ.toString());
						break;
					case NORMAL:
						data.put(biome, Lang.CLIMATE_NORMAL.toString());
						break;
					case NORMAL_AZ:
						data.put(biome, Lang.CLIMATE_NORMAL_AZ.toString());
						break;
					case SUMMER:
						data.put(biome, Lang.CLIMATE_SUMMER.toString());
						break;
					case SUMMER_AZ:
						data.put(biome, Lang.CLIMATE_SUMMER_AZ.toString());
						break;
					case WINTER:
						data.put(biome, Lang.CLIMATE_WINTER.toString());
						break;
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBBiome-Climate");
		sb.append(BEGIN);

		appendDefault(sb, Lang.CLIMATE_NULL.toString());

		for (Map.Entry<GOTBiome, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeConquestFactions() {
		Map<GOTBiome, Set<String>> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			for (GOTFactionContainer factionContainer : biome.getNPCSpawnList().getFactionContainers()) {
				if (factionContainer.getBaseWeight() <= 0) {
					for (GOTSpawnListContainer spawnListContainer : factionContainer.getSpawnListContainers()) {
						for (GOTSpawnEntry spawnEntry : spawnListContainer.getSpawnList().getSpawnEntries()) {
							Entity entity = ENTITY_CLASS_TO_ENTITY.get(spawnEntry.entityClass);
							if (entity instanceof GOTEntityNPC) {
								GOTFaction faction = ((GOTEntityNPC) entity).getFaction();
								data.computeIfAbsent(biome, s -> new TreeSet<>());
								data.get(biome).add(getFactionLink(faction));
								break;
							}
						}
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBBiome-ConquestFactions");
		sb.append(BEGIN);

		appendDefault(sb, Lang.BIOME_NO_CONQUEST_FACTIONS.toString());

		for (Map.Entry<GOTBiome, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(entry.getKey())).append(" = ");
			sb.append(Lang.BIOME_HAS_CONQUEST_FACTIONS);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeInvasionFactions() {
		Map<GOTBiome, Set<String>> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			for (GOTInvasions invasion : biome.getInvasionSpawns().getRegisteredInvasions()) {
				for (GOTInvasions.InvasionSpawnEntry invasionSpawnEntry : invasion.getInvasionMobs()) {
					Entity entity = ENTITY_CLASS_TO_ENTITY.get(invasionSpawnEntry.getEntityClass());
					if (entity instanceof GOTEntityNPC) {
						GOTFaction faction = ((GOTEntityNPC) entity).getFaction();
						data.computeIfAbsent(biome, s -> new TreeSet<>());
						data.get(biome).add(getFactionLink(faction));
						break;
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBBiome-InvasionFactions");
		sb.append(BEGIN);

		appendDefault(sb, Lang.BIOME_NO_INVASION_FACTIONS.toString());

		for (Map.Entry<GOTBiome, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(entry.getKey())).append(" = ");
			sb.append(Lang.BIOME_HAS_INVASION_FACTIONS);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeMinerals() {
		Map<GOTBiome, Set<MineralInfo>> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			Collection<GOTBiomeDecorator.OreGenerant> oreGenerants = new HashSet<>(biome.getDecorator().getBiomeSoils());
			oreGenerants.addAll(biome.getDecorator().getBiomeOres());
			oreGenerants.addAll(biome.getDecorator().getBiomeGems());

			for (GOTBiomeDecorator.OreGenerant oreGenerant : oreGenerants) {
				Block block = GOTReflection.getOreGenBlock(oreGenerant.getOreGen());
				int meta = GOTReflection.getOreGenMeta(oreGenerant.getOreGen());

				String blockLink;
				if (block instanceof GOTBlockOreGem || block instanceof BlockDirt || block instanceof GOTBlockRock) {
					blockLink = getMineralLink(block, meta);
				} else {
					blockLink = getMineralLink(block);
				}

				String stats = "(" + oreGenerant.getOreChance() + "%; Y: " + oreGenerant.getMinHeight() + '-' + oreGenerant.getMaxHeight() + ')';

				data.computeIfAbsent(biome, s -> new TreeSet<>());
				data.get(biome).add(new MineralInfo(blockLink, ' ' + stats));
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBBiome-Minerals");
		sb.append(BEGIN);

		appendDefault(sb, Lang.BIOME_NO_MINERALS.toString());

		for (Map.Entry<GOTBiome, Set<MineralInfo>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(entry.getKey())).append(" = ");
			sb.append(Lang.BIOME_HAS_MINERALS);

			appendSection(sb, MineralInfo.toStringSet(entry.getValue()));
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeMusic() {
		Map<GOTBiome, String> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			if (biome.getBiomeMusic() != null) {
				data.put(biome, biome.getBiomeMusic().getSubregion());
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBBiome-Music");
		sb.append(BEGIN);

		appendDefault(sb, N_A);

		for (Map.Entry<GOTBiome, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeName() {
		Map<GOTBiome, String> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			data.put(biome, getBiomeName(biome));
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBBiome-Name");
		sb.append(BEGIN);

		appendDefault(sb, "UNKNOWN");

		for (Map.Entry<GOTBiome, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeNPCs() {
		Map<GOTBiome, Set<String>> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			for (GOTFactionContainer factionContainer : biome.getNPCSpawnList().getFactionContainers()) {
				if (factionContainer.getBaseWeight() > 0) {
					for (GOTSpawnListContainer spawnListContainer : factionContainer.getSpawnListContainers()) {
						for (GOTSpawnEntry spawnEntry : spawnListContainer.getSpawnList().getSpawnEntries()) {
							data.computeIfAbsent(biome, s -> new TreeSet<>());
							data.get(biome).add(getEntityLink(spawnEntry.entityClass));
						}
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBBiome-NPCs");
		sb.append(BEGIN);

		appendDefault(sb, Lang.BIOME_NO_NPCS.toString());

		for (Map.Entry<GOTBiome, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(entry.getKey())).append(" = ");
			sb.append(Lang.BIOME_HAS_NPCS);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeRainfall() {
		Map<GOTBiome, String> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			StringBuilder sb = new StringBuilder();

			GOTDate.AegonCalendar.getDate().getMonth().setSeason(GOTDate.Season.WINTER);
			GOTClimateType.performSeasonalChangesServerSide();
			sb.append(Lang.SEASON_WINTER).append(": ").append(biome.rainfall);

			GOTDate.AegonCalendar.getDate().getMonth().setSeason(GOTDate.Season.SPRING);
			GOTClimateType.performSeasonalChangesServerSide();
			sb.append("&lt;br&gt;");
			sb.append(Lang.SEASON_SPRING).append(": ").append(biome.rainfall);

			GOTDate.AegonCalendar.getDate().getMonth().setSeason(GOTDate.Season.SUMMER);
			GOTClimateType.performSeasonalChangesServerSide();
			sb.append("&lt;br&gt;");
			sb.append(Lang.SEASON_SUMMER).append(": ").append(biome.rainfall);

			GOTDate.AegonCalendar.getDate().getMonth().setSeason(GOTDate.Season.AUTUMN);
			GOTClimateType.performSeasonalChangesServerSide();
			sb.append("&lt;br&gt;");
			sb.append(Lang.SEASON_AUTUMN).append(": ").append(biome.rainfall);

			data.put(biome, sb.toString());
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBBiome-Rainfall");
		sb.append(BEGIN);

		for (Map.Entry<GOTBiome, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeStructures() {
		Map<GOTBiome, Set<String>> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			for (GOTBiomeDecorator.Structure structure : biome.getDecorator().getStructures()) {
				data.computeIfAbsent(biome, s -> new TreeSet<>());
				data.get(biome).add(getStructureLink(structure.getStructureGen().getClass()));
			}

			for (GOTStructureBaseSettlement settlement : biome.getDecorator().getSettlements()) {
				if (settlement.getSpawnChance() != 0.0f) {
					data.computeIfAbsent(biome, s -> new TreeSet<>());
					data.get(biome).add(getSettlementName(settlement.getClass()));
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBBiome-Structures");
		sb.append(BEGIN);

		appendDefault(sb, Lang.BIOME_NO_STRUCTURES.toString());

		for (Map.Entry<GOTBiome, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(entry.getKey())).append(" = ");
			sb.append(Lang.BIOME_HAS_STRUCTURES);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeTemperature() {
		Map<GOTBiome, String> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			StringBuilder sb = new StringBuilder();

			GOTDate.AegonCalendar.getDate().getMonth().setSeason(GOTDate.Season.WINTER);
			GOTClimateType.performSeasonalChangesServerSide();
			sb.append(Lang.SEASON_WINTER).append(": ").append(biome.temperature);

			GOTDate.AegonCalendar.getDate().getMonth().setSeason(GOTDate.Season.SPRING);
			GOTClimateType.performSeasonalChangesServerSide();
			sb.append("&lt;br&gt;");
			sb.append(Lang.SEASON_SPRING).append(": ").append(biome.temperature);

			GOTDate.AegonCalendar.getDate().getMonth().setSeason(GOTDate.Season.SUMMER);
			GOTClimateType.performSeasonalChangesServerSide();
			sb.append("&lt;br&gt;");
			sb.append(Lang.SEASON_SUMMER).append(": ").append(biome.temperature);

			GOTDate.AegonCalendar.getDate().getMonth().setSeason(GOTDate.Season.AUTUMN);
			GOTClimateType.performSeasonalChangesServerSide();
			sb.append("&lt;br&gt;");
			sb.append(Lang.SEASON_AUTUMN).append(": ").append(biome.temperature);

			data.put(biome, sb.toString());
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBBiome-Temperature");
		sb.append(BEGIN);

		for (Map.Entry<GOTBiome, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeTrees() {
		Map<GOTBiome, Set<String>> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			Collection<GOTTreeType.WeightedTreeType> weightedTreeTypes = biome.getDecorator().getTreeTypes();

			Collection<GOTTreeType> excludedTreeTypes = EnumSet.noneOf(GOTTreeType.class);

			for (GOTTreeType.WeightedTreeType weightedTreeType : weightedTreeTypes) {
				GOTTreeType treeType = weightedTreeType.getTreeType();

				data.computeIfAbsent(biome, s -> new TreeSet<>());
				data.get(biome).add(getTreeLink(treeType));

				excludedTreeTypes.add(treeType);
			}

			for (GOTBiomeVariantList.VariantBucket variantBucket : biome.getBiomeVariants().getVariantList()) {
				for (GOTTreeType.WeightedTreeType weightedTreeType : variantBucket.getVariant().getTreeTypes()) {
					GOTTreeType treeType = weightedTreeType.getTreeType();

					if (!excludedTreeTypes.contains(treeType)) {
						data.computeIfAbsent(biome, s -> new TreeSet<>());
						data.get(biome).add(getTreeLink(treeType) + " (" + getBiomeVariantName(variantBucket.getVariant()).toLowerCase(Locale.ROOT) + ')');
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBBiome-Trees");
		sb.append(BEGIN);

		appendDefault(sb, Lang.BIOME_NO_TREES.toString());

		for (Map.Entry<GOTBiome, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(entry.getKey())).append(" = ");
			sb.append(Lang.BIOME_HAS_TREES);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeVariants() {
		Map<GOTBiome, Set<String>> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			for (GOTBiomeVariantList.VariantBucket variantBucket : biome.getBiomeVariants().getVariantList()) {
				data.computeIfAbsent(biome, s -> new TreeSet<>());
				data.get(biome).add(getBiomeVariantName(variantBucket.getVariant()));
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBBiome-Variants");
		sb.append(BEGIN);

		appendDefault(sb, Lang.BIOME_NO_VARIANTS.toString());

		for (Map.Entry<GOTBiome, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(entry.getKey())).append(" = ");
			sb.append(Lang.BIOME_HAS_VARIANTS);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeVisitAchievement() {
		Map<GOTBiome, String> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			GOTAchievement achievement = biome.getBiomeAchievement();

			if (achievement != null) {
				data.put(biome, '«' + achievement.getTitle() + '»');
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBBiome-VisitAchievement");
		sb.append(BEGIN);

		appendDefault(sb, N_A);

		for (Map.Entry<GOTBiome, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeWaypoints() {
		Map<GOTBiome, Set<String>> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			GOTWaypoint.Region region = biome.getBiomeWaypoints();

			if (region != null) {
				for (GOTWaypoint wp : region.getWaypoints()) {
					data.computeIfAbsent(biome, s -> new TreeSet<>());
					data.get(biome).add(wp.getDisplayName() + " (" + getFactionLink(wp.getFaction()) + ')');
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBBiome-Waypoints");
		sb.append(BEGIN);

		appendDefault(sb, Lang.BIOME_NO_WAYPOINTS.toString());

		for (Map.Entry<GOTBiome, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(entry.getKey())).append(" = ");
			sb.append(Lang.BIOME_HAS_WAYPOINTS);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityBannerBearer() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTBannerBearer) {
				data.put(entityEntry.getKey(), TRUE);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-BannerBearer");
		sb.append(BEGIN);

		appendDefault(sb, FALSE);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityBiomes() {
		Map<Class<? extends Entity>, Set<String>> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			Collection<BiomeGenBase.SpawnListEntry> spawnListEntries = new HashSet<>();
			Collection<Class<? extends Entity>> conquestEntityClasses = new HashSet<>();
			Collection<Class<? extends Entity>> invasionEntityClasses = new HashSet<>();

			spawnListEntries.addAll(biome.getSpawnableList(EnumCreatureType.ambient));
			spawnListEntries.addAll(biome.getSpawnableList(EnumCreatureType.waterCreature));
			spawnListEntries.addAll(biome.getSpawnableList(EnumCreatureType.creature));
			spawnListEntries.addAll(biome.getSpawnableList(EnumCreatureType.monster));
			spawnListEntries.addAll(biome.getSpawnableGOTAmbientList());

			for (GOTFactionContainer factionContainer : biome.getNPCSpawnList().getFactionContainers()) {
				if (factionContainer.getBaseWeight() > 0) {
					for (GOTSpawnListContainer spawnListContainer : factionContainer.getSpawnListContainers()) {
						spawnListEntries.addAll(spawnListContainer.getSpawnList().getSpawnEntries());
					}
				} else {
					for (GOTSpawnListContainer spawnListContainer : factionContainer.getSpawnListContainers()) {
						for (BiomeGenBase.SpawnListEntry spawnListEntry : spawnListContainer.getSpawnList().getSpawnEntries()) {
							conquestEntityClasses.add(spawnListEntry.entityClass);
						}
					}
				}
			}

			for (GOTInvasions invasion : biome.getInvasionSpawns().getRegisteredInvasions()) {
				for (GOTInvasions.InvasionSpawnEntry invasionSpawnEntry : invasion.getInvasionMobs()) {
					invasionEntityClasses.add(invasionSpawnEntry.getEntityClass());
				}
			}

			Collection<Class<? extends Entity>> bothConquestInvasion = new HashSet<>(conquestEntityClasses);
			bothConquestInvasion.retainAll(invasionEntityClasses);

			conquestEntityClasses.removeAll(bothConquestInvasion);
			invasionEntityClasses.removeAll(bothConquestInvasion);

			for (BiomeGenBase.SpawnListEntry entry : spawnListEntries) {
				data.computeIfAbsent(entry.entityClass, s -> new TreeSet<>());
				data.get(entry.entityClass).add(getBiomeLink(biome));
			}

			for (Class<? extends Entity> entityClass : conquestEntityClasses) {
				data.computeIfAbsent(entityClass, s -> new TreeSet<>());
				data.get(entityClass).add(getBiomeLink(biome) + ' ' + Lang.ENTITY_CONQUEST);
			}

			for (Class<? extends Entity> entityClass : invasionEntityClasses) {
				data.computeIfAbsent(entityClass, s -> new TreeSet<>());
				data.get(entityClass).add(getBiomeLink(biome) + ' ' + Lang.ENTITY_INVASION);
			}

			for (Class<? extends Entity> entityClass : bothConquestInvasion) {
				data.computeIfAbsent(entityClass, s -> new TreeSet<>());
				data.get(entityClass).add(getBiomeLink(biome) + ' ' + Lang.ENTITY_CONQUEST_INVASION);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-Biomes");
		sb.append(BEGIN);

		appendDefault(sb, Lang.ENTITY_NO_BIOMES.toString());

		for (Map.Entry<Class<? extends Entity>, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");
			sb.append(Lang.ENTITY_HAS_BIOMES);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityBuysPool() {
		Map<Class<? extends Entity>, Set<String>> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTTradeable) {
				GOTTradeable tradeable = (GOTTradeable) entityEntry.getValue();

				for (GOTTradeEntry entry : tradeable.getBuysPool().getTradeEntries()) {
					data.computeIfAbsent(entityEntry.getKey(), s -> new TreeSet<>());
					data.get(entityEntry.getKey()).add(entry.getTradeItem().getDisplayName() + ": {{Bar Coins|" + entry.getCost() + "}}");
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-BuysPool");
		sb.append(BEGIN);

		appendDefault(sb, Lang.ENTITY_NO_BUYS_POOL.toString());

		for (Map.Entry<Class<? extends Entity>, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");
			sb.append(Lang.ENTITY_HAS_BUYS_POOL);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityCharacter() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTEntityNPC && ((GOTEntityNPC) entityEntry.getValue()).isLegendary()) {
				data.put(entityEntry.getKey(), TRUE);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-Character");
		sb.append(BEGIN);

		appendDefault(sb, FALSE);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityFaction() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) entityEntry.getValue();
				data.put(entityEntry.getKey(), getFactionLink(npc.getFaction()));
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-Faction");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityFarmhand() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTFarmhand) {
				data.put(entityEntry.getKey(), TRUE);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-Farmhand");
		sb.append(BEGIN);

		appendDefault(sb, FALSE);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityHealth() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			EntityLivingBase entity = (EntityLivingBase) entityEntry.getValue();
			data.put(entityEntry.getKey(), String.valueOf((int) entity.getMaxHealth()));
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-Health");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityHireable() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (GOTUnitTradeEntries entries : UNIT_TRADE_ENTRIES) {
			for (GOTUnitTradeEntry entry : entries.getTradeEntries()) {
				data.put(entry.getEntityClass(), TRUE);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-Hireable");
		sb.append(BEGIN);

		appendDefault(sb, FALSE);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityHireReputation() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (GOTUnitTradeEntries entries : UNIT_TRADE_ENTRIES) {
			for (GOTUnitTradeEntry entry : entries.getTradeEntries()) {
				int reputation = (int) entry.getReputationRequired();

				if (entry.getOathType() == GOTUnitTradeEntry.OathType.NONE) {
					data.put(entry.getEntityClass(), String.valueOf(reputation));
				} else {
					data.put(entry.getEntityClass(), String.valueOf(Math.max(reputation, 100)));
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-HireReputation");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityHirePrice() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (GOTUnitTradeEntries entries : UNIT_TRADE_ENTRIES) {
			for (GOTUnitTradeEntry entry : entries.getTradeEntries()) {
				int cost = entry.getInitialCost();

				if (entry.getOathType() == GOTUnitTradeEntry.OathType.NONE) {
					data.put(entry.getEntityClass(), "{{Bar Coins|" + cost * 2 + "}}");
				} else {
					data.put(entry.getEntityClass(), N_A);
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-HirePrice");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityHirePriceOath() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (GOTUnitTradeEntries entries : UNIT_TRADE_ENTRIES) {
			for (GOTUnitTradeEntry entry : entries.getTradeEntries()) {
				int cost = entry.getInitialCost();

				data.put(entry.getEntityClass(), "{{Bar Coins|" + cost + "}}");
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-HirePriceOath");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityImmuneToFire() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue().isImmuneToFire()) {
				data.put(entityEntry.getKey(), TRUE);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-ImmuneToFire");
		sb.append(BEGIN);

		appendDefault(sb, FALSE);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityImmuneToFrost() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTEntityNPC && entityEntry.getValue() instanceof GOTBiome.ImmuneToFrost) {
				data.put(entityEntry.getKey(), TRUE);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-ImmuneToFrost");
		sb.append(BEGIN);

		appendDefault(sb, FALSE);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityImmuneToHeat() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTBiome.ImmuneToHeat) {
				data.put(entityEntry.getKey(), TRUE);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-ImmuneToHeat");
		sb.append(BEGIN);

		appendDefault(sb, FALSE);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityKillAchievement() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) entityEntry.getValue();
				GOTAchievement achievement = npc.getKillAchievement();
				if (achievement != null) {
					data.put(entityEntry.getKey(), '«' + achievement.getTitle() + '»');
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-KillAchievement");
		sb.append(BEGIN);

		appendDefault(sb, N_A);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityKillReputation() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) entityEntry.getValue();
				data.put(entityEntry.getKey(), String.valueOf((int) npc.getReputationBonus()));
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-KillReputation");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityLegendaryDrop() {
		Map<Class<? extends Entity>, Set<String>> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTEntityNPC && ((GOTEntityNPC) entityEntry.getValue()).isLegendary()) {
				GOTEntityNPC npc = (GOTEntityNPC) entityEntry.getValue();

				npc.dropFewItems(true, 999);

				for (Object obj : npc.getDrops()) {
					if (obj instanceof Item) {
						ItemStack itemStack = new ItemStack((Item) obj);
						data.computeIfAbsent(entityEntry.getKey(), s -> new TreeSet<>());
						data.get(entityEntry.getKey()).add(itemStack.getDisplayName());
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-LegendaryDrop");
		sb.append(BEGIN);

		appendDefault(sb, Lang.ENTITY_NO_LEGENDARY_DROP.toString());

		for (Map.Entry<Class<? extends Entity>, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");
			sb.append(Lang.ENTITY_HAS_LEGENDARY_DROP);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityMarriage() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTEntityNPC && GOTEntityUtils.canBeMarried((GOTEntityNPC) entityEntry.getValue())) {
				data.put(entityEntry.getKey(), TRUE);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-Marriage");
		sb.append(BEGIN);

		appendDefault(sb, FALSE);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityMercenary() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTMercenary) {
				data.put(entityEntry.getKey(), TRUE);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-Mercenary");
		sb.append(BEGIN);

		appendDefault(sb, FALSE);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityNPC() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTEntityNPC) {
				data.put(entityEntry.getKey(), TRUE);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-NPC");
		sb.append(BEGIN);

		appendDefault(sb, FALSE);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityOwners() {
		Map<Class<? extends Entity>, Set<String>> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTUnitTradeable) {
				GOTUnitTradeable tradeable = (GOTUnitTradeable) entityEntry.getValue();
				for (GOTUnitTradeEntry entry : tradeable.getUnits().getTradeEntries()) {
					data.computeIfAbsent(entry.getEntityClass(), s -> new TreeSet<>());
					data.get(entry.getEntityClass()).add(getEntityLink(entityEntry.getKey()));
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(NAMESPACE_NAME).append("DBEntity-Owners");
		sb.append(BEGIN);

		appendDefault(sb, Lang.ENTITY_NO_OWNERS.toString());

		for (Map.Entry<Class<? extends Entity>, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");
			sb.append(Lang.ENTITY_HAS_OWNERS);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityRideableAnimal() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTNPCMount && !(entityEntry.getValue() instanceof GOTEntityNPC)) {
				data.put(entityEntry.getKey(), TRUE);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-RideableAnimal");
		sb.append(BEGIN);

		appendDefault(sb, FALSE);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityRideableNPC() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTNPCMount && entityEntry.getValue() instanceof GOTEntityNPC) {
				data.put(entityEntry.getKey(), TRUE);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-RideableNPC");
		sb.append(BEGIN);

		appendDefault(sb, FALSE);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntitySellsPool() {
		Map<Class<? extends Entity>, Set<String>> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTTradeable) {
				GOTTradeable tradeable = (GOTTradeable) entityEntry.getValue();

				for (GOTTradeEntry entry : tradeable.getSellsPool().getTradeEntries()) {
					data.computeIfAbsent(entityEntry.getKey(), s -> new TreeSet<>());
					data.get(entityEntry.getKey()).add(entry.getTradeItem().getDisplayName() + ": {{Bar Coins|" + entry.getCost() + "}}");
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-SellsPool");
		sb.append(BEGIN);

		appendDefault(sb, Lang.ENTITY_NO_SELLS_POOL.toString());

		for (Map.Entry<Class<? extends Entity>, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");
			sb.append(Lang.ENTITY_HAS_SELLS_POOL);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntitySellsUnitPool() {
		Map<Class<? extends Entity>, List<String>> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTUnitTradeable) {
				GOTUnitTradeable tradeable = (GOTUnitTradeable) entityEntry.getValue();

				for (GOTUnitTradeEntry entry : tradeable.getUnits().getTradeEntries()) {
					StringBuilder sb = new StringBuilder();

					sb.append(getEntityLink(entry.getEntityClass()));
					if (entry.getMountClass() != null) {
						sb.append(Lang.RIDER);
					}
					sb.append(": ");

					int cost = entry.getInitialCost();
					int reputation = (int) entry.getReputationRequired();

					if (entry.getOathType() == GOTUnitTradeEntry.OathType.NONE) {
						sb.append("{{Bar Coins|").append(cost * 2).append("}} ").append(Lang.NO_OATH).append(", ");
						sb.append("{{Bar Coins|").append(cost).append("}} ").append(Lang.NEED_OATH).append("; ");
						sb.append(reputation).append(' ').append(Lang.REPUTATION);
					} else {
						sb.append("N/A ").append(Lang.NO_OATH).append(", ");
						sb.append("{{Bar Coins|").append(cost).append("}} ").append(Lang.NEED_OATH).append("; ");
						sb.append(Math.max(reputation, 100)).append(' ').append(Lang.REPUTATION);
					}

					data.computeIfAbsent(entityEntry.getKey(), s -> new ArrayList<>());
					data.get(entityEntry.getKey()).add(sb.toString());
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-SellsUnitPool");
		sb.append(BEGIN);

		appendDefault(sb, Lang.ENTITY_NO_SELLS_UNIT_POOL.toString());

		for (Map.Entry<Class<? extends Entity>, List<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");
			sb.append(Lang.ENTITY_HAS_SELLS_UNIT_POOL);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntitySmith() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTSmith) {
				data.put(entityEntry.getKey(), TRUE);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-Smith");
		sb.append(BEGIN);

		appendDefault(sb, FALSE);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntitySpawnsInDarkness() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTEntityNPC && ((GOTEntityNPC) entityEntry.getValue()).isSpawnsInDarkness()) {
				data.put(entityEntry.getKey(), TRUE);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-SpawnsInDarkness");
		sb.append(BEGIN);

		appendDefault(sb, FALSE);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityTargetSeeker() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTEntityNPC && ((GOTEntityNPC) entityEntry.getValue()).isTargetSeeker()) {
				data.put(entityEntry.getKey(), TRUE);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-TargetSeeker");
		sb.append(BEGIN);

		appendDefault(sb, FALSE);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityTradeable() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTTradeable && !(entityEntry.getValue() instanceof GOTSmith)) {
				data.put(entityEntry.getKey(), TRUE);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-Tradeable");
		sb.append(BEGIN);

		appendDefault(sb, FALSE);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityUnitTradeable() {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTUnitTradeable) {
				data.put(entityEntry.getKey(), TRUE);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-UnitTradeable");
		sb.append(BEGIN);

		appendDefault(sb, FALSE);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityWaypoint(World world) {
		Map<Class<? extends Entity>, String> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, GOTWaypoint> entityEntry : ENTITY_CLASS_TO_WP.entrySet()) {
			data.put(entityEntry.getKey(), entityEntry.getValue().getDisplayName());
		}

		for (Map.Entry<GOTAbstractWaypoint, GOTStructureBaseSettlement> spawnerEntry : GOTFixer.SPAWNERS.entrySet()) {
			GOTStructureBaseSettlement spawner = spawnerEntry.getValue();
			spawner.getLegendaryNPCs(world);
			for (GOTFixer.SpawnInfo info : spawner.getLegendaryNPCs(world)) {
				data.put(info.getNPC().getClass(), spawnerEntry.getKey().getDisplayName());
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBEntity-Waypoint");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends Entity>, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionBanners() {
		Map<GOTFaction, Set<String>> data = new EnumMap<>(GOTFaction.class);

		for (GOTFaction faction : FACTIONS) {
			for (GOTItemBanner.BannerType banner : faction.getFactionBanners()) {
				data.computeIfAbsent(faction, s -> new TreeSet<>());
				data.get(faction).add(getBannerName(banner));
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBFaction-Banners");
		sb.append(BEGIN);

		appendDefault(sb, Lang.FACTION_NO_BANNERS.toString());

		for (Map.Entry<GOTFaction, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");
			sb.append(Lang.FACTION_HAS_BANNERS);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionCharacters() {
		Map<GOTFaction, Set<String>> data = new EnumMap<>(GOTFaction.class);

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			Entity entity = entityEntry.getValue();
			if (entity instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) entity;
				if (npc.isLegendary()) {
					data.computeIfAbsent(npc.getFaction(), s -> new TreeSet<>());
					data.get(npc.getFaction()).add(getEntityLink(entityEntry.getKey()));
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBFaction-Characters");
		sb.append(BEGIN);

		appendDefault(sb, Lang.FACTION_NO_CHARACTERS.toString());

		for (Map.Entry<GOTFaction, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");
			sb.append(Lang.FACTION_HAS_CHARACTERS);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionCodename() {
		Map<GOTFaction, String> data = new EnumMap<>(GOTFaction.class);

		for (GOTFaction faction : FACTIONS) {
			data.put(faction, faction.codeName());
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBFaction-Codename");
		sb.append(BEGIN);

		for (Map.Entry<GOTFaction, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionConquestBiomes() {
		Map<GOTFaction, Set<String>> data = new EnumMap<>(GOTFaction.class);

		for (GOTBiome biome : BIOMES) {
			for (GOTFactionContainer factionContainer : biome.getNPCSpawnList().getFactionContainers()) {
				if (factionContainer.getBaseWeight() <= 0) {
					for (GOTSpawnListContainer spawnListContainer : factionContainer.getSpawnListContainers()) {
						for (GOTSpawnEntry spawnEntry : spawnListContainer.getSpawnList().getSpawnEntries()) {
							Entity entity = ENTITY_CLASS_TO_ENTITY.get(spawnEntry.entityClass);
							if (entity instanceof GOTEntityNPC) {
								GOTFaction faction = ((GOTEntityNPC) entity).getFaction();
								data.computeIfAbsent(faction, s -> new TreeSet<>());
								data.get(faction).add(getBiomeLink(biome));
								break;
							}
						}
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBFaction-ConquestBiomes");
		sb.append(BEGIN);

		appendDefault(sb, Lang.FACTION_NO_CONQUEST_BIOMES.toString());

		for (Map.Entry<GOTFaction, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");
			sb.append(Lang.FACTION_HAS_CONQUEST_BIOMES);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionEnemies() {
		Map<GOTFaction, String> data = new EnumMap<>(GOTFaction.class);

		for (GOTFaction faction : FACTIONS) {
			StringJoiner sj = new StringJoiner(" • ");

			for (GOTFaction otherFaction : FACTIONS) {
				if (faction.isBadRelation(otherFaction) && faction != otherFaction) {
					sj.add(getFactionLink(otherFaction));
				}
			}

			if (sj.length() > 0) {
				data.put(faction, sj.toString());
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBFaction-Enemies");
		sb.append(BEGIN);

		appendDefault(sb, Lang.FACTION_NO_ENEMIES.toString());

		for (Map.Entry<GOTFaction, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionFriends() {
		Map<GOTFaction, String> data = new EnumMap<>(GOTFaction.class);

		for (GOTFaction faction : FACTIONS) {
			StringJoiner sj = new StringJoiner(" • ");

			for (GOTFaction otherFaction : FACTIONS) {
				if (faction.isGoodRelation(otherFaction) && faction != otherFaction) {
					sj.add(getFactionLink(otherFaction));
				}
			}

			if (sj.length() > 0) {
				data.put(faction, sj.toString());
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBFaction-Friends");
		sb.append(BEGIN);

		appendDefault(sb, Lang.FACTION_NO_FRIENDS.toString());

		for (Map.Entry<GOTFaction, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionInvasionBiomes() {
		Map<GOTFaction, Set<String>> data = new EnumMap<>(GOTFaction.class);

		for (GOTBiome biome : BIOMES) {
			for (GOTInvasions invasion : biome.getInvasionSpawns().getRegisteredInvasions()) {
				for (GOTInvasions.InvasionSpawnEntry invasionSpawnEntry : invasion.getInvasionMobs()) {
					Entity entity = ENTITY_CLASS_TO_ENTITY.get(invasionSpawnEntry.getEntityClass());
					if (entity instanceof GOTEntityNPC) {
						GOTFaction faction = ((GOTEntityNPC) entity).getFaction();
						data.computeIfAbsent(faction, s -> new TreeSet<>());
						data.get(faction).add(getBiomeLink(biome));
						break;
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBFaction-InvasionBiomes");
		sb.append(BEGIN);

		appendDefault(sb, Lang.FACTION_NO_INVASION_BIOMES.toString());

		for (Map.Entry<GOTFaction, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");
			sb.append(Lang.FACTION_HAS_INVASION_BIOMES);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionName() {
		Map<GOTFaction, String> data = new EnumMap<>(GOTFaction.class);

		for (GOTFaction faction : FACTIONS) {
			data.put(faction, getFactionName(faction));
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBFaction-Name");
		sb.append(BEGIN);

		appendDefault(sb, "UNKNOWN");

		for (Map.Entry<GOTFaction, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionNPCs() {
		Map<GOTFaction, Set<String>> data = new EnumMap<>(GOTFaction.class);

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			Entity entity = entityEntry.getValue();
			if (entity instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) entity;
				if (!npc.isLegendary()) {
					data.computeIfAbsent(npc.getFaction(), s -> new TreeSet<>());
					data.get(npc.getFaction()).add(getEntityLink(entityEntry.getKey()));
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBFaction-NPCs");
		sb.append(BEGIN);

		appendDefault(sb, Lang.FACTION_NO_NPCS.toString());

		for (Map.Entry<GOTFaction, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");
			sb.append(Lang.FACTION_HAS_NPCS);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionOathRank() {
		Map<GOTFaction, String> data = new EnumMap<>(GOTFaction.class);

		for (GOTFaction faction : FACTIONS) {
			GOTFactionRank rank = faction.getOathRank();

			if (rank != null) {
				StringBuilder sb = new StringBuilder();

				sb.append(rank.getDisplayName());

				String femRank = rank.getDisplayNameFem();
				if (!femRank.contains("got")) {
					sb.append('/').append(femRank);
				}

				sb.append(" (").append((int) faction.getOathReputation()).append(')');

				data.put(faction, sb.toString());
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBFaction-OathRank");
		sb.append(BEGIN);

		appendDefault(sb, N_A);

		for (Map.Entry<GOTFaction, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionRanks() {
		Map<GOTFaction, List<String>> data = new EnumMap<>(GOTFaction.class);

		for (GOTFaction faction : FACTIONS) {
			for (GOTFactionRank rank : faction.getRanksSortedDescending()) {
				StringBuilder sb = new StringBuilder();

				sb.append(rank.getDisplayFullName());

				String femRank = rank.getDisplayFullNameFem();
				if (!femRank.contains("got")) {
					sb.append('/').append(femRank);
				}

				sb.append(" (").append((int) rank.getReputation()).append(')');

				data.computeIfAbsent(faction, s -> new ArrayList<>());
				data.get(faction).add(sb.toString());
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBFaction-Ranks");
		sb.append(BEGIN);

		appendDefault(sb, Lang.FACTION_NO_RANKS.toString());

		for (Map.Entry<GOTFaction, List<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");
			sb.append(Lang.FACTION_HAS_RANKS);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionRegion() {
		Map<GOTFaction, String> data = new EnumMap<>(GOTFaction.class);

		for (GOTFaction faction : FACTIONS) {
			if (faction.getFactionRegion() != null) {
				data.put(faction, faction.getFactionRegion().getRegionName());
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBFaction-Region");
		sb.append(BEGIN);

		appendDefault(sb, N_A);

		for (Map.Entry<GOTFaction, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionShieldsCapes() {
		Map<GOTFaction, String> data = new EnumMap<>(GOTFaction.class);

		for (GOTFaction faction : FACTIONS) {
			StringBuilder sb = new StringBuilder();

			boolean save = false;

			sb.append(NL).append("&lt;table class=\"wikitable shields-capes\"&gt;");

			for (GOTShields shield : SHIELDS) {
				if (shield.getReputationFaction() == faction) {
					save = true;
					sb.append(NL + "&lt;tr&gt;&lt;td&gt;").append(shield.getShieldName()).append("&lt;/td&gt;&lt;td&gt;").append(shield.getShieldDesc()).append("&lt;/td&gt;&lt;td&gt;").append(getShieldFilename(shield)).append("&lt;/td&gt;&lt;/tr&gt;");
				}
			}

			for (GOTCapes cape : CAPES) {
				if (cape.getReputationFaction() == faction) {
					save = true;
					sb.append(NL + "&lt;tr&gt;&lt;td&gt;").append(cape.getCapeName()).append("&lt;/td&gt;&lt;td&gt;").append(cape.getCapeDesc()).append("&lt;/td&gt;&lt;td&gt;").append(getCapeFilename(cape)).append("&lt;/td&gt;&lt;/tr&gt;");
				}
			}

			sb.append(NL).append("&lt;table&gt;");

			if (save) {
				data.put(faction, sb.toString());
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBFaction-ShieldsCapes");
		sb.append(BEGIN);

		appendDefault(sb, Lang.FACTION_NO_ATTRIBUTES.toString());

		for (Map.Entry<GOTFaction, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");
			sb.append(Lang.FACTION_HAS_ATTRIBUTES);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionSpawnBiomes() {
		Map<GOTFaction, Set<String>> data = new EnumMap<>(GOTFaction.class);

		for (GOTBiome biome : BIOMES) {
			for (GOTFactionContainer factionContainer : biome.getNPCSpawnList().getFactionContainers()) {
				if (factionContainer.getBaseWeight() > 0) {
					for (GOTSpawnListContainer spawnListContainer : factionContainer.getSpawnListContainers()) {
						for (GOTSpawnEntry spawnEntry : spawnListContainer.getSpawnList().getSpawnEntries()) {
							Entity entity = ENTITY_CLASS_TO_ENTITY.get(spawnEntry.entityClass);
							if (entity instanceof GOTEntityNPC) {
								GOTFaction faction = ((GOTEntityNPC) entity).getFaction();
								data.computeIfAbsent(faction, s -> new TreeSet<>());
								data.get(faction).add(getBiomeLink(biome));
								break;
							}
						}
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBFaction-SpawnBiomes");
		sb.append(BEGIN);

		appendDefault(sb, Lang.FACTION_NO_SPAWN_BIOMES.toString());

		for (Map.Entry<GOTFaction, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");
			sb.append(Lang.FACTION_HAS_SPAWN_BIOMES);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionWarCrimes() {
		Map<GOTFaction, String> data = new EnumMap<>(GOTFaction.class);

		for (GOTFaction faction : FACTIONS) {
			if (!faction.isApprovesWarCrimes()) {
				data.put(faction, Lang.FACTION_NO_WAR_CRIMES.toString());
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBFaction-WarCrimes");
		sb.append(BEGIN);

		appendDefault(sb, Lang.FACTION_HAS_WAR_CRIMES.toString());

		for (Map.Entry<GOTFaction, String> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionWaypoints() {
		Map<GOTFaction, Set<String>> data = new EnumMap<>(GOTFaction.class);

		for (GOTWaypoint wp : WAYPOINTS) {
			data.computeIfAbsent(wp.getFaction(), s -> new TreeSet<>());
			data.get(wp.getFaction()).add(wp.getDisplayName());
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBFaction-Waypoints");
		sb.append(BEGIN);

		appendDefault(sb, Lang.FACTION_NO_WAYPOINTS.toString());

		for (Map.Entry<GOTFaction, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");
			sb.append(Lang.FACTION_HAS_WAYPOINTS);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateMineralBiomes() {
		Map<String, Set<MineralInfo>> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			Collection<GOTBiomeDecorator.OreGenerant> oreGenerants = new HashSet<>(biome.getDecorator().getBiomeSoils());
			oreGenerants.addAll(biome.getDecorator().getBiomeOres());
			oreGenerants.addAll(biome.getDecorator().getBiomeGems());

			for (GOTBiomeDecorator.OreGenerant oreGenerant : oreGenerants) {
				Block block = GOTReflection.getOreGenBlock(oreGenerant.getOreGen());
				int meta = GOTReflection.getOreGenMeta(oreGenerant.getOreGen());

				String blockLink;
				if (block instanceof GOTBlockOreGem || block instanceof BlockDirt || block instanceof GOTBlockRock) {
					blockLink = getMineralName(block, meta);
				} else {
					blockLink = getMineralName(block);
				}

				String stats = "(" + oreGenerant.getOreChance() + "%; Y: " + oreGenerant.getMinHeight() + '-' + oreGenerant.getMaxHeight() + ')';

				data.computeIfAbsent(blockLink, s -> new TreeSet<>());
				data.get(blockLink).add(new MineralInfo(getBiomeLink(biome), ' ' + stats));
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBMineral-Biomes");
		sb.append(BEGIN);

		appendDefault(sb, Lang.MINERAL_NO_BIOMES.toString());

		for (Map.Entry<String, Set<MineralInfo>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(entry.getKey()).append(" = ");
			sb.append(Lang.MINERAL_HAS_BIOMES);

			appendSection(sb, MineralInfo.toStringSet(entry.getValue()));
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateStructureBiomes() {
		Map<Class<? extends WorldGenerator>, Set<String>> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			for (GOTBiomeDecorator.Structure structure : biome.getDecorator().getStructures()) {
				data.computeIfAbsent(structure.getStructureGen().getClass(), s -> new TreeSet<>());
				data.get(structure.getStructureGen().getClass()).add(getBiomeLink(biome));
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBStructure-Biomes");
		sb.append(BEGIN);

		appendDefault(sb, Lang.STRUCTURE_NO_BIOMES.toString());

		for (Map.Entry<Class<? extends WorldGenerator>, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getStructurePagename(entry.getKey())).append(" = ");
			sb.append(Lang.STRUCTURE_HAS_BIOMES);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateStructureEntities() {
		StringBuilder sb = new StringBuilder();

		Map<Class<? extends WorldGenerator>, Set<String>> data = new HashMap<>();

		for (Map.Entry<Class<? extends WorldGenerator>, GOTStructureBase> structureEntry : STRUCTURE_CLASS_TO_STRUCTURE.entrySet()) {
			Class<? extends WorldGenerator> structureClass = structureEntry.getKey();
			GOTStructureBase structure = structureEntry.getValue();

			if (structure != null) {
				Set<Class<? extends Entity>> entityClasses = structure.getEntityClasses();
				for (Class<? extends Entity> entityClass : entityClasses) {
					data.computeIfAbsent(structureClass, s -> new TreeSet<>());
					data.get(structureClass).add(getEntityLink(entityClass));
				}
			}
		}

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBStructure-Entities");
		sb.append(BEGIN);

		appendDefault(sb, Lang.STRUCTURE_NO_ENTITIES.toString());

		for (Map.Entry<Class<? extends WorldGenerator>, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getStructurePagename(entry.getKey())).append(" = ");
			sb.append(Lang.STRUCTURE_HAS_ENTITIES);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateTreeBiomes() {
		Map<GOTTreeType, Set<String>> data = new EnumMap<>(GOTTreeType.class);

		for (GOTBiome biome : BIOMES) {
			Collection<GOTTreeType.WeightedTreeType> weightedTreeTypes = biome.getDecorator().getTreeTypes();

			Collection<GOTTreeType> excludedTreeTypes = EnumSet.noneOf(GOTTreeType.class);

			for (GOTTreeType.WeightedTreeType weightedTreeType : weightedTreeTypes) {
				GOTTreeType treeType = weightedTreeType.getTreeType();

				data.computeIfAbsent(treeType, s -> new TreeSet<>());
				data.get(treeType).add(getBiomeLink(biome));

				excludedTreeTypes.add(treeType);
			}

			for (GOTBiomeVariantList.VariantBucket variantBucket : biome.getBiomeVariants().getVariantList()) {
				for (GOTTreeType.WeightedTreeType weightedTreeType : variantBucket.getVariant().getTreeTypes()) {
					GOTTreeType treeType = weightedTreeType.getTreeType();

					if (!excludedTreeTypes.contains(treeType)) {
						data.computeIfAbsent(treeType, s -> new TreeSet<>());
						data.get(treeType).add(getBiomeLink(biome) + " (" + getBiomeVariantName(variantBucket.getVariant()) + ')');
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(NAMESPACE_NAME).append("DBTree-Biomes");
		sb.append(BEGIN);

		appendDefault(sb, Lang.TREE_NO_BIOMES.toString());

		for (Map.Entry<GOTTreeType, Set<String>> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getTreePagename(entry.getKey())).append(" = ");
			sb.append(Lang.TREE_HAS_BIOMES);

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder addPagesBiomes(Collection<String> neededPages, Collection<String> existingPages) {
		StringBuilder sb = new StringBuilder();

		for (GOTBiome biome : BIOMES) {
			String pageName = getBiomePagename(biome);
			neededPages.add(NAMESPACE + pageName);
			if (!existingPages.contains(NAMESPACE + pageName)) {
				sb.append(TITLE_SINGLE).append(NAMESPACE).append(pageName);
				sb.append(PAGE_LEFT).append("{{").append(NAMESPACE_NAME).append(" Статья Биом}}").append(PAGE_RIGHT);
			}
		}

		return sb;
	}

	private static StringBuilder addPagesEntities(Collection<String> neededPages, Collection<String> existingPages) {
		StringBuilder sb = new StringBuilder();

		for (Class<? extends Entity> entityClass : ENTITY_CLASSES) {
			String pageName = getEntityPagename(entityClass);
			neededPages.add(NAMESPACE + pageName);
			if (!existingPages.contains(NAMESPACE + pageName)) {
				sb.append(TITLE_SINGLE).append(NAMESPACE).append(pageName);
				sb.append(PAGE_LEFT).append("{{").append(NAMESPACE_NAME).append(" Статья Моб}}").append(PAGE_RIGHT);
			}
		}

		return sb;
	}

	private static StringBuilder addPagesFactions(Collection<String> neededPages, Collection<String> existingPages) {
		StringBuilder sb = new StringBuilder();

		for (GOTFaction faction : FACTIONS) {
			String pageName = getFactionPagename(faction);
			neededPages.add(NAMESPACE + pageName);
			if (!existingPages.contains(NAMESPACE + pageName)) {
				sb.append(TITLE_SINGLE).append(NAMESPACE).append(pageName);
				sb.append(PAGE_LEFT).append("{{").append(NAMESPACE_NAME).append(" Статья Фракция}}").append(PAGE_RIGHT);
			}
		}

		return sb;
	}

	private static StringBuilder addPagesMinerals(Collection<String> neededPages, Collection<String> existingPages) {
		StringBuilder sb = new StringBuilder();

		for (String pageName : MINERALS) {
			neededPages.add(NAMESPACE + pageName);
			if (!existingPages.contains(NAMESPACE + pageName)) {
				sb.append(TITLE_SINGLE).append(NAMESPACE).append(pageName);
				sb.append(PAGE_LEFT).append("{{").append(NAMESPACE_NAME).append(" Статья Ископаемое}}").append(PAGE_RIGHT);
			}
		}

		return sb;
	}

	private static StringBuilder addPagesStructures(Collection<String> neededPages, Collection<String> existingPages) {
		StringBuilder sb = new StringBuilder();

		for (Class<? extends WorldGenerator> strClass : STRUCTURE_CLASSES) {
			String pageName = getStructurePagename(strClass);
			neededPages.add(NAMESPACE + pageName);
			if (!existingPages.contains(NAMESPACE + pageName)) {
				sb.append(TITLE_SINGLE).append(NAMESPACE).append(pageName);
				sb.append(PAGE_LEFT).append("{{").append(NAMESPACE_NAME).append(" Статья Структура}}").append(PAGE_RIGHT);
			}
		}

		return sb;
	}

	private static StringBuilder addPagesTrees(Collection<String> neededPages, Collection<String> existingPages) {
		StringBuilder sb = new StringBuilder();

		for (GOTTreeType tree : TREES) {
			String pageName = getTreePagename(tree);
			neededPages.add(NAMESPACE + pageName);
			if (!existingPages.contains(NAMESPACE + pageName)) {
				sb.append(TITLE_SINGLE).append(NAMESPACE).append(pageName);
				sb.append(PAGE_LEFT).append("{{").append(NAMESPACE_NAME).append(" Статья Дерево}}").append(PAGE_RIGHT);
			}
		}

		return sb;
	}

	private static Set<String> getExistingPages() {
		try {
			File file = new File("hummel/sitemap.txt");
			if (!file.exists()) {
				boolean created = file.createNewFile();
				if (!created) {
					GOTLog.getLogger().info("DatabaseGenerator: file wasn't created");
				}
			}
			try (Stream<String> lines = Files.lines(Paths.get("hummel/sitemap.txt"))) {
				return lines.collect(Collectors.toSet());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Collections.emptySet();
	}

	private static void markPagesForRemoval(Collection<String> neededPages, Iterable<String> existingPages) {
		try (PrintWriter printWriter = new PrintWriter("hummel/removal.txt", UTF_8)) {
			StringBuilder sb = new StringBuilder();

			for (String existing : existingPages) {
				if (!neededPages.contains(existing)) {
					sb.append(existing).append('\n');
				}
			}
			printWriter.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void searchForEntities(World world) {
		for (Class<? extends Entity> entityClass : ENTITY_CLASSES) {
			ENTITY_CLASS_TO_ENTITY.put(entityClass, GOTReflection.newEntity(entityClass, world));
		}
	}

	private static void searchForStructures(World world) {
		for (GOTBiome biome : BIOMES) {
			for (GOTBiomeDecorator.Structure structure : biome.getDecorator().getStructures()) {
				STRUCTURE_CLASSES.add(structure.getStructureGen().getClass());
			}
		}

		for (Class<? extends WorldGenerator> structureClass : STRUCTURE_CLASSES) {
			WorldGenerator generator = null;
			try {
				generator = structureClass.getConstructor(Boolean.TYPE).newInstance(true);
			} catch (Exception ignored) {
			}

			if (generator instanceof GOTStructureBase) {
				GOTStructureBase structure = (GOTStructureBase) generator;
				structure.setRestrictions(false);
				structure.setWikiGen(true);
				structure.generate(world, world.rand, 0, 0, 0, 0);

				STRUCTURE_CLASS_TO_STRUCTURE.put(structureClass, structure);
			}
		}
	}

	private static void searchForMinerals() {
		for (GOTBiome biome : BIOMES) {
			Collection<GOTBiomeDecorator.OreGenerant> oreGenerants = new HashSet<>(biome.getDecorator().getBiomeSoils());
			oreGenerants.addAll(biome.getDecorator().getBiomeOres());
			oreGenerants.addAll(biome.getDecorator().getBiomeGems());

			for (GOTBiomeDecorator.OreGenerant oreGenerant : oreGenerants) {
				Block block = GOTReflection.getOreGenBlock(oreGenerant.getOreGen());
				int meta = GOTReflection.getOreGenMeta(oreGenerant.getOreGen());

				if (block instanceof GOTBlockOreGem || block instanceof BlockDirt || block instanceof GOTBlockRock) {
					MINERALS.add(getMineralName(block, meta));
				} else {
					MINERALS.add(getMineralName(block));
				}
			}
		}
	}

	private static void searchForPagenamesBiome() {
		next:
		for (GOTBiome biome : BIOMES) {
			String preName = getBiomeName(biome);
			for (GOTFaction faction : FACTIONS) {
				if (preName.equals(getFactionName(faction))) {
					BIOME_TO_PAGENAME.put(biome, preName + " (" + Lang.PAGE_BIOME + ')');
					continue next;
				}
			}
			for (Class<? extends Entity> entityClass : ENTITY_CLASSES) {
				if (preName.equals(getEntityName(entityClass))) {
					BIOME_TO_PAGENAME.put(biome, preName + " (" + Lang.PAGE_BIOME + ')');
					continue next;
				}
			}
			BIOME_TO_PAGENAME.put(biome, preName);
		}
	}

	private static void searchForPagenamesEntity() {
		next:
		for (Class<? extends Entity> entityClass : ENTITY_CLASSES) {
			String preName = getEntityName(entityClass);
			for (GOTBiome biome : BIOMES) {
				if (preName.equals(getBiomeName(biome))) {
					ENTITY_CLASS_TO_PAGENAME.put(entityClass, preName + " (" + Lang.PAGE_ENTITY + ')');
					continue next;
				}
			}
			for (GOTFaction faction : FACTIONS) {
				if (preName.equals(getFactionName(faction))) {
					ENTITY_CLASS_TO_PAGENAME.put(entityClass, preName + " (" + Lang.PAGE_ENTITY + ')');
					continue next;
				}
			}
			ENTITY_CLASS_TO_PAGENAME.put(entityClass, preName);
		}
	}

	private static void searchForPagenamesFaction() {
		next:
		for (GOTFaction faction : FACTIONS) {
			String preName = getFactionName(faction);
			for (GOTBiome biome : BIOMES) {
				if (preName.equals(getBiomeName(biome))) {
					FACTION_TO_PAGENAME.put(faction, preName + " (" + Lang.PAGE_FACTION + ')');
					continue next;
				}
			}
			for (Class<? extends Entity> entityClass : ENTITY_CLASSES) {
				if (preName.equals(getEntityName(entityClass))) {
					FACTION_TO_PAGENAME.put(faction, preName + " (" + Lang.PAGE_FACTION + ')');
					continue next;
				}
			}
			FACTION_TO_PAGENAME.put(faction, preName);
		}
	}

	private static String getBannerName(GOTItemBanner.BannerType banner) {
		return StatCollector.translateToLocal("item.got:banner." + banner.getBannerName() + ".name");
	}

	private static String getBiomeLink(GOTBiome biome) {
		String name = getBiomeName(biome);
		String pagename = getBiomePagename(biome);
		if (name.equals(pagename)) {
			if (NAMESPACE.isEmpty()) {
				return "[[" + name + "]]";
			}
			return "[[" + NAMESPACE + name + '|' + name + "]]";
		}
		return "[[" + NAMESPACE + pagename + '|' + name + "]]";
	}

	private static String getBiomeName(GOTBiome biome) {
		return StatCollector.translateToLocal("got.biome." + biome.biomeName + ".name");
	}

	private static String getBiomePagename(GOTBiome biome) {
		return BIOME_TO_PAGENAME.get(biome);
	}

	private static String getBiomeVariantName(GOTBiomeVariant variant) {
		return StatCollector.translateToLocal("got.variant." + variant.getVariantName() + ".name");
	}

	private static String getMineralLink(Block block, int meta) {
		String name = StatCollector.translateToLocal(block.getUnlocalizedName() + '.' + meta + ".name");
		if (NAMESPACE.isEmpty()) {
			return "[[" + name + "]]";
		}
		return "[[" + NAMESPACE + name + '|' + name + "]]";
	}

	private static String getMineralLink(Block block) {
		String name = StatCollector.translateToLocal(block.getUnlocalizedName() + ".name");
		if (NAMESPACE.isEmpty()) {
			return "[[" + name + "]]";
		}
		return "[[" + NAMESPACE + name + '|' + name + "]]";
	}

	private static String getMineralName(Block block, int meta) {
		return StatCollector.translateToLocal(block.getUnlocalizedName() + '.' + meta + ".name");
	}

	private static String getMineralName(Block block) {
		return StatCollector.translateToLocal(block.getUnlocalizedName() + ".name");
	}

	private static String getCapeFilename(GOTCapes cape) {
		return "[[File:Cape " + cape.name().toLowerCase(Locale.ROOT) + ".png]]";
	}

	private static String getEntityLink(Class<? extends Entity> entityClass) {
		String name = getEntityName(entityClass);
		if (name.contains("null")) {
			return StatCollector.translateToLocal("entity." + EntityList.classToStringMapping.get(entityClass) + ".name");
		}

		String pagename = getEntityPagename(entityClass);
		if (name.equals(pagename)) {
			if (NAMESPACE.isEmpty()) {
				return "[[" + name + "]]";
			}
			return "[[" + NAMESPACE + name + '|' + name + "]]";
		}
		return "[[" + NAMESPACE + pagename + '|' + name + "]]";
	}

	private static String getEntityName(Class<? extends Entity> entityClass) {
		return StatCollector.translateToLocal("entity.got." + GOTEntityRegistry.ENTITY_CLASS_TO_NAME.get(entityClass) + ".name");
	}

	private static String getEntityPagename(Class<? extends Entity> entityClass) {
		return ENTITY_CLASS_TO_PAGENAME.get(entityClass);
	}

	private static String getFactionLink(GOTFaction fac) {
		String name = getFactionName(fac);
		String pagename = getFactionPagename(fac);
		if (name.equals(pagename)) {
			if (NAMESPACE.isEmpty()) {
				return "[[" + name + "]]";
			}
			return "[[" + NAMESPACE + name + '|' + name + "]]";
		}
		return "[[" + NAMESPACE + pagename + '|' + name + "]]";
	}

	private static String getFactionName(GOTFaction fac) {
		return StatCollector.translateToLocal("got.faction." + fac.codeName() + ".name");
	}

	private static String getFactionPagename(GOTFaction fac) {
		return FACTION_TO_PAGENAME.get(fac);
	}

	private static String getItemFilename(Item item) {
		return "[[File:" + item.getUnlocalizedName().substring("item.got:".length()) + ".png|32px|link=]]";
	}

	private static String getItemName(Item item) {
		return StatCollector.translateToLocal(item.getUnlocalizedName() + ".name");
	}

	private static String getShieldFilename(GOTShields shield) {
		return "[[File:Shield " + shield.name().toLowerCase(Locale.ROOT) + ".png]]";
	}

	private static String getStructureLink(Class<? extends WorldGenerator> structureClass) {
		String name = StatCollector.translateToLocal("got.structure." + GOTStructureRegistry.CLASS_TO_NAME_MAPPING.get(structureClass) + ".name");
		if (NAMESPACE.isEmpty()) {
			return "[[" + name + "]]";
		}
		return "[[" + NAMESPACE + name + '|' + name + "]]";
	}

	private static String getStructurePagename(Class<? extends WorldGenerator> structureClass) {
		return StatCollector.translateToLocal("got.structure." + GOTStructureRegistry.CLASS_TO_NAME_MAPPING.get(structureClass) + ".name");
	}

	private static String getSettlementName(Class<? extends GOTStructureBaseSettlement> clazz) {
		return StatCollector.translateToLocal("got.db." + clazz.getSimpleName().replace("GOTStructure", "") + ".name");
	}

	private static String getTreePagename(GOTTreeType tree) {
		return StatCollector.translateToLocal("got.tree." + tree.name().toLowerCase(Locale.ROOT) + ".name");
	}

	private static String getTreeLink(GOTTreeType tree) {
		String name = StatCollector.translateToLocal("got.tree." + tree.name().toLowerCase(Locale.ROOT) + ".name");
		if (NAMESPACE.isEmpty()) {
			return "[[" + name + "]]";
		}
		return "[[" + NAMESPACE + name + '|' + name + "]]";
	}

	private static void appendSection(StringBuilder sb, Collection<String> section) {
		for (String item : section) {
			sb.append(NL).append("* ").append(item).append(';');
		}

		section.clear();
	}

	private static void appendSection(StringBuilder sb, String value) {
		sb.append(value);
	}

	private static void appendDefault(StringBuilder sb, String value) {
		sb.append(NL).append("| #default = ").append(value);
	}

	public enum Lang {
		BIOME_HAS_ANIMALS, BIOME_HAS_CONQUEST_FACTIONS, BIOME_HAS_INVASION_FACTIONS, BIOME_HAS_MINERALS, BIOME_HAS_NPCS, BIOME_HAS_STRUCTURES, BIOME_HAS_TREES, BIOME_HAS_VARIANTS, BIOME_HAS_WAYPOINTS, BIOME_NO_ANIMALS, BIOME_NO_CONQUEST_FACTIONS, BIOME_NO_INVASION_FACTIONS, BIOME_NO_MINERALS, BIOME_NO_NPCS, BIOME_NO_STRUCTURES, BIOME_NO_TREES, BIOME_NO_VARIANTS, BIOME_NO_WAYPOINTS, CATEGORY, CLIMATE_COLD, CLIMATE_COLD_AZ, CLIMATE_NORMAL, CLIMATE_NORMAL_AZ, CLIMATE_NULL, CLIMATE_SUMMER, CLIMATE_SUMMER_AZ, CLIMATE_WINTER, ENTITY_CONQUEST, ENTITY_CONQUEST_INVASION, ENTITY_HAS_BIOMES, ENTITY_HAS_BUYS_POOL, ENTITY_HAS_LEGENDARY_DROP, ENTITY_HAS_OWNERS, ENTITY_HAS_SELLS_UNIT_POOL, ENTITY_INVASION, ENTITY_NO_BIOMES, ENTITY_NO_BUYS_POOL, ENTITY_NO_LEGENDARY_DROP, ENTITY_NO_OWNERS, ENTITY_NO_SELLS_UNIT_POOL, FACTION_HAS_BANNERS, FACTION_HAS_CHARACTERS, FACTION_HAS_CONQUEST_BIOMES, FACTION_HAS_INVASION_BIOMES, FACTION_HAS_NPCS, FACTION_HAS_RANKS, FACTION_HAS_SPAWN_BIOMES, FACTION_HAS_WAR_CRIMES, FACTION_HAS_WAYPOINTS, FACTION_NO_ATTRIBUTES, FACTION_NO_BANNERS, FACTION_NO_CHARACTERS, FACTION_NO_CONQUEST_BIOMES, FACTION_NO_INVASION_BIOMES, FACTION_NO_NPCS, FACTION_NO_RANKS, FACTION_NO_SPAWN_BIOMES, FACTION_NO_WAR_CRIMES, FACTION_NO_WAYPOINTS, MINERAL_HAS_BIOMES, MINERAL_NO_BIOMES, NEED_OATH, NO_OATH, PAGE_BIOME, PAGE_ENTITY, PAGE_FACTION, REPUTATION, RIDER, SEASON_AUTUMN, SEASON_SPRING, SEASON_SUMMER, SEASON_WINTER, STRUCTURE_HAS_BIOMES, STRUCTURE_HAS_ENTITIES, STRUCTURE_NO_BIOMES, STRUCTURE_NO_ENTITIES, TREE_HAS_BIOMES, TREE_NO_BIOMES, FACTION_NO_ENEMIES, FACTION_NO_FRIENDS, FACTION_HAS_ATTRIBUTES, ENTITY_HAS_SELLS_POOL, ENTITY_NO_SELLS_POOL;

		@Override
		public String toString() {
			return StatCollector.translateToLocal("got.db." + name() + ".name");
		}
	}

	public enum Type {
		XML("xml"), TABLES("tables");

		private final String codeName;

		Type(String name) {
			codeName = name;
		}

		public static Type forName(String name) {
			for (Type db : values()) {
				if (db.codeName.equals(name)) {
					return db;
				}
			}
			return null;
		}

		public static Set<String> getNames() {
			Set<String> names = new HashSet<>();
			for (Type db : values()) {
				names.add(db.codeName);
			}
			return names;
		}
	}

	private static class MineralInfo implements Comparable<MineralInfo> {
		private final String valuableData;
		private final String skippableData;

		private MineralInfo(String valuableData, String skippableData) {
			this.valuableData = valuableData;
			this.skippableData = skippableData;
		}

		private static Collection<String> toStringSet(Iterable<MineralInfo> value) {
			Collection<String> set = new TreeSet<>();
			for (MineralInfo mineralInfo : value) {
				set.add(mineralInfo.toString());
			}
			return set;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			MineralInfo that = (MineralInfo) o;
			return Objects.equals(valuableData, that.valuableData);
		}

		@Override
		public int hashCode() {
			return Objects.hash(valuableData);
		}

		@Override
		public String toString() {
			return valuableData + skippableData;
		}

		@Override
		public int compareTo(MineralInfo o) {
			return valuableData.compareTo(o.valuableData);
		}
	}
}