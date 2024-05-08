package got.common.util;

import got.common.GOTAchievementRank;
import got.common.GOTDate;
import got.common.block.other.GOTBlockOreGem;
import got.common.block.other.GOTBlockRock;
import got.common.database.*;
import got.common.entity.GOTEntityRegistry;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTEntitySpiderBase;
import got.common.entity.other.iface.*;
import got.common.entity.other.utils.GOTEntityUtils;
import got.common.entity.other.utils.GOTTradeEntry;
import got.common.entity.other.utils.GOTUnitTradeEntry;
import got.common.entity.westeros.legendary.captain.*;
import got.common.entity.westeros.legendary.deco.GOTEntityMyrcellaBaratheon;
import got.common.entity.westeros.legendary.deco.GOTEntityTommenBaratheon;
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
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GOTWikiGenerator {
	private static final Map<Class<? extends Entity>, Entity> ENTITY_CLASS_TO_ENTITY = new HashMap<>();
	private static final Map<Class<? extends Entity>, GOTWaypoint> ENTITY_CLASS_TO_WP = new HashMap<>();

	private static final Map<String, String> FACTION_TO_PAGENAME = new HashMap<>();
	private static final Map<String, String> ENTITY_TO_PAGENAME = new HashMap<>();
	private static final Map<String, String> BIOME_TO_PAGENAME = new HashMap<>();

	private static final Iterable<Item> ITEMS = new ArrayList<>(GOTItems.CONTENT);
	private static final Iterable<GOTUnitTradeEntries> UNIT_TRADE_ENTRIES = new ArrayList<>(GOTUnitTradeEntries.CONTENT);
	private static final Iterable<Class<? extends Entity>> ENTITY_CLASSES = new HashSet<>(GOTEntityRegistry.CONTENT);
	private static final Iterable<GOTAchievement> ACHIEVEMENTS = new HashSet<>(GOTAchievement.CONTENT);

	private static final Collection<GOTBiome> BIOMES = new HashSet<>(GOTBiome.CONTENT);

	private static final Iterable<GOTFaction> FACTIONS = EnumSet.allOf(GOTFaction.class);
	private static final Iterable<GOTTreeType> TREES = EnumSet.allOf(GOTTreeType.class);
	private static final Iterable<GOTWaypoint> WAYPOINTS = EnumSet.allOf(GOTWaypoint.class);
	private static final Iterable<GOTCapes> CAPES = EnumSet.allOf(GOTCapes.class);
	private static final Iterable<GOTShields> SHIELDS = EnumSet.allOf(GOTShields.class);

	private static final Collection<String> MINERALS = new HashSet<>();
	private static final Collection<Class<? extends Entity>> UNIT_CLASSES = new HashSet<>();

	private static final Iterable<Class<? extends WorldGenerator>> STRUCTURE_CLASSES = new HashSet<>(GOTStructureRegistry.CONTENT);

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

	public static void generate(Type type, World world, EntityPlayer entityPlayer) {
		long time = System.nanoTime();

		try {
			Files.createDirectories(Paths.get("hummel"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Collection<Runnable> pRunnables = new HashSet<>();

		pRunnables.add(() -> searchForEntities(world));
		pRunnables.add(() -> searchForMinerals(BIOMES, MINERALS));
		pRunnables.add(() -> searchForUnitClasses(UNIT_CLASSES, UNIT_TRADE_ENTRIES));
		pRunnables.add(() -> searchForPagenamesEntity(BIOMES, FACTIONS));
		pRunnables.add(() -> searchForPagenamesBiome(BIOMES, FACTIONS));
		pRunnables.add(() -> searchForPagenamesFaction(BIOMES, FACTIONS));

		pRunnables.parallelStream().forEach(Runnable::run);

		switch (type) {
			case TABLES:
				Collection<Runnable> runnables = new HashSet<>();

				runnables.add(GOTWikiGenerator::genTableShields);
				runnables.add(GOTWikiGenerator::genTableCapes);
				runnables.add(GOTWikiGenerator::genTableUnits);
				runnables.add(GOTWikiGenerator::genTableArmor);
				runnables.add(GOTWikiGenerator::genTableWeapons);
				runnables.add(GOTWikiGenerator::genTableFood);
				runnables.add(() -> genTableAchievements(entityPlayer));
				runnables.add(() -> genTableWaypoints(entityPlayer));

				runnables.parallelStream().forEach(Runnable::run);

				break;
			case XML:
				try (PrintWriter printWriter = new PrintWriter("hummel/import.xml", UTF_8)) {
					StringBuilder sb = new StringBuilder();

					GOTDate.Season season = GOTDate.AegonCalendar.getSeason();

					sb.append("<mediawiki xmlns=\"http://www.mediawiki.org/xml/export-0.11/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.mediawiki.org/xml/export-0.11/ http://www.mediawiki.org/xml/export-0.11.xsd\" version=\"0.11\" xml:lang=\"ru\">");

					Collection<Supplier<StringBuilder>> suppliers = new HashSet<>();

					Set<String> existingPages = getExistingPages();
					Collection<String> neededPages = new HashSet<>();

					suppliers.add(() -> addPagesMinerals(neededPages, existingPages));
					suppliers.add(() -> addPagesEntities(neededPages, existingPages));
					suppliers.add(() -> addPagesBiomes(neededPages, existingPages));
					suppliers.add(() -> addPagesFactions(neededPages, existingPages));
					suppliers.add(() -> addPagesTrees(neededPages, existingPages));
					suppliers.add(() -> addPagesStructures(neededPages, existingPages));

					suppliers.parallelStream().map(Supplier::get).forEach(sb::append);
					suppliers.clear();

					markPagesForRemoval(neededPages, existingPages);

					suppliers.add(GOTWikiGenerator::genTemplateMineralBiomes);
					suppliers.add(GOTWikiGenerator::genTemplateTreeBiomes);
					suppliers.add(GOTWikiGenerator::genTemplateStructureBiomes);

					suppliers.add(GOTWikiGenerator::genTemplateBiomeBandits);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeClimate);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeInvasionFactions);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeMinerals);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeMobs);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeMusic);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeName);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeNPCs);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeRainfall);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeStructuresSettlements);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeTemperature);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeTrees);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeVariants);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeVisitAchievement);
					suppliers.add(GOTWikiGenerator::genTemplateBiomeWaypoints);
					suppliers.add(() -> genTemplateBiomeConquestFactions(world));

					suppliers.add(GOTWikiGenerator::genTemplateFactionBanners);
					suppliers.add(GOTWikiGenerator::genTemplateFactionCharacters);
					suppliers.add(GOTWikiGenerator::genTemplateFactionCodename);
					suppliers.add(GOTWikiGenerator::genTemplateFactionEnemies);
					suppliers.add(GOTWikiGenerator::genTemplateFactionFriends);
					suppliers.add(GOTWikiGenerator::genTemplateFactionInvasionBiomes);
					suppliers.add(GOTWikiGenerator::genTemplateFactionNPCs);
					suppliers.add(GOTWikiGenerator::genTemplateFactionName);
					suppliers.add(GOTWikiGenerator::genTemplateFactionPledgeRank);
					suppliers.add(GOTWikiGenerator::genTemplateFactionRanks);
					suppliers.add(GOTWikiGenerator::genTemplateFactionRegion);
					suppliers.add(GOTWikiGenerator::genTemplateFactionShieldsCapes);
					suppliers.add(GOTWikiGenerator::genTemplateFactionStructures);
					suppliers.add(GOTWikiGenerator::genTemplateFactionWarCrimes);
					suppliers.add(GOTWikiGenerator::genTemplateFactionWaypoints);
					suppliers.add(() -> genTemplateFactionConquestBiomes(world));
					suppliers.add(() -> genTemplateFactionSpawnBiomes(world));

					suppliers.add(GOTWikiGenerator::genTemplateEntityBannerBearer);
					suppliers.add(GOTWikiGenerator::genTemplateEntityBuys);
					suppliers.add(GOTWikiGenerator::genTemplateEntityCharacter);
					suppliers.add(GOTWikiGenerator::genTemplateEntityLegendaryDrop);
					suppliers.add(GOTWikiGenerator::genTemplateEntityFaction);
					suppliers.add(GOTWikiGenerator::genTemplateEntityFarmhand);
					suppliers.add(GOTWikiGenerator::genTemplateEntityHealth);
					suppliers.add(GOTWikiGenerator::genTemplateEntityHireAlignment);
					suppliers.add(GOTWikiGenerator::genTemplateEntityHirePrice);
					suppliers.add(GOTWikiGenerator::genTemplateEntityHirePricePledge);
					suppliers.add(GOTWikiGenerator::genTemplateEntityImmuneToFire);
					suppliers.add(GOTWikiGenerator::genTemplateEntityImmuneToFrost);
					suppliers.add(GOTWikiGenerator::genTemplateEntityImmuneToHeat);
					suppliers.add(GOTWikiGenerator::genTemplateEntityKillAchievement);
					suppliers.add(GOTWikiGenerator::genTemplateEntityKillAlignment);
					suppliers.add(GOTWikiGenerator::genTemplateEntityMarriage);
					suppliers.add(GOTWikiGenerator::genTemplateEntityMercenary);
					suppliers.add(GOTWikiGenerator::genTemplateEntityOwner);
					suppliers.add(GOTWikiGenerator::genTemplateEntityRideableMob);
					suppliers.add(GOTWikiGenerator::genTemplateEntityRideableNPC);
					suppliers.add(GOTWikiGenerator::genTemplateEntitySells);
					suppliers.add(GOTWikiGenerator::genTemplateEntitySellsUnits);
					suppliers.add(GOTWikiGenerator::genTemplateEntitySmith);
					suppliers.add(GOTWikiGenerator::genTemplateEntityBiomes);
					suppliers.add(GOTWikiGenerator::genTemplateEntitySpawnsInDarkness);
					suppliers.add(GOTWikiGenerator::genTemplateEntityTargetSeeker);
					suppliers.add(GOTWikiGenerator::genTemplateEntityTradeable);
					suppliers.add(GOTWikiGenerator::genTemplateEntityUnitTradeable);
					suppliers.add(() -> genTemplateEntityWaypoint(world));

					suppliers.parallelStream().map(Supplier::get).forEach(sb::append);
					suppliers.clear();

					sb.append(genTemplateMtmEntitiesStructures(world));

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

	private static void genTableAchievements(EntityPlayer entityPlayer) {
		StringBuilder sb = new StringBuilder();

		Collection<String> content = new TreeSet<>();

		for (GOTAchievement ach : ACHIEVEMENTS) {
			if (!(ach instanceof GOTAchievementRank)) {
				content.add(NL + "| " + ach.getTitle(entityPlayer) + " || " + ach.getDescription() + NL + "|-");
			}
		}

		for (String s : content) {
			sb.append(s);
		}

		try (PrintWriter printWriter = new PrintWriter("hummel/achievements.txt", UTF_8)) {
			printWriter.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void genTableArmor() {
		StringBuilder sb = new StringBuilder();

		for (Item item : ITEMS) {
			if (item instanceof ItemArmor) {
				float damage = ((ItemArmor) item).damageReduceAmount;
				ItemArmor.ArmorMaterial material = ((ItemArmor) item).getArmorMaterial();

				sb.append(NL).append("| ");
				sb.append(getItemName(item)).append(" || ").append(getItemFilename(item)).append(" || ").append(item.getMaxDamage()).append(" || ").append(damage).append(" || ");
				if (material == null || material.customCraftingMaterial == null) {
					sb.append("N/A");
				} else {
					sb.append(getItemName(material.customCraftingMaterial));
				}
				sb.append(NL).append("|-");
			}
		}

		try (PrintWriter printWriter = new PrintWriter("hummel/armor.txt", UTF_8)) {
			printWriter.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void genTableCapes() {
		StringBuilder sb = new StringBuilder();

		for (GOTCapes cape : CAPES) {
			sb.append(NL).append("| ");
			sb.append(cape.getCapeName()).append(" || ").append(cape.getCapeDesc()).append(" || ").append(getCapeFilename(cape)).append(NL).append("|-");
		}

		try (PrintWriter printWriter = new PrintWriter("hummel/capes.txt", UTF_8)) {
			printWriter.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private static void genTableFood() {
		StringBuilder sb = new StringBuilder();

		Collection<String> content = new TreeSet<>();

		for (Item item : ITEMS) {
			if (item instanceof ItemFood) {
				StringBuilder localSb = new StringBuilder();

				int heal = ((ItemFood) item).func_150905_g(null);
				float saturation = ((ItemFood) item).func_150906_h(null);
				localSb.append(NL).append("| ");
				localSb.append(getItemName(item));
				localSb.append(" || ").append(getItemFilename(item));
				localSb.append(" || ").append("{{Bar|bread|").append(new DecimalFormat("#.##").format(saturation * heal * 2)).append("}}");
				localSb.append(" || ").append("{{Bar|food|").append(heal).append("}}");
				localSb.append(" || ").append(item.getItemStackLimit());
				localSb.append(NL).append("|-");

				content.add(localSb.toString());
			}
		}

		for (String s : content) {
			sb.append(s);
		}

		try (PrintWriter printWriter = new PrintWriter("hummel/food.txt", UTF_8)) {
			printWriter.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void genTableShields() {
		StringBuilder sb = new StringBuilder();

		for (GOTShields shield : SHIELDS) {
			sb.append(NL).append("| ");
			sb.append(shield.getShieldName()).append(" || ").append(shield.getShieldDesc()).append(" || ").append(getShieldFilename(shield)).append(NL).append("|-");
		}

		try (PrintWriter printWriter = new PrintWriter("hummel/shields.txt", UTF_8)) {
			printWriter.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void genTableUnits() {
		StringBuilder sb = new StringBuilder();

		for (GOTUnitTradeEntries unitTradeEntries : UNIT_TRADE_ENTRIES) {
			for (GOTUnitTradeEntry entry : unitTradeEntries.getTradeEntries()) {
				if (entry != null) {
					sb.append(NL).append("| ");
					sb.append(getEntityLink(entry.getEntityClass()));
					if (entry.getMountClass() != null) {
						sb.append(Lang.RIDER);
					}

					int cost = entry.getInitialCost();
					int alignment = (int) entry.getAlignmentRequired();

					if (entry.getPledgeType() == GOTUnitTradeEntry.PledgeType.NONE) {
						sb.append(" || ").append("{{Coins|").append(cost * 2).append("}}");
						sb.append(" || ").append("{{Coins|").append(cost).append("}}");
						sb.append(" || ").append('+').append(alignment);
						sb.append(" || ").append('-');
					} else {
						sb.append(" || ").append("N/A");
						sb.append(" || ").append("{{Coins|").append(cost).append("}}");
						sb.append(" || ").append('+').append(Math.max(alignment, 100));
						sb.append(" || ").append('+');
					}
					sb.append(NL).append("|-");
				}
			}
		}

		try (PrintWriter printWriter = new PrintWriter("hummel/units.txt", UTF_8)) {
			printWriter.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void genTableWaypoints(EntityPlayer entityPlayer) {
		StringBuilder sb = new StringBuilder();

		Collection<String> content = new TreeSet<>();

		for (GOTWaypoint wp : WAYPOINTS) {
			content.add(NL + "| " + wp.getDisplayName() + " || " + wp.getLoreText(entityPlayer) + NL + "|-");
		}

		for (String s : content) {
			sb.append(s);
		}

		try (PrintWriter printWriter = new PrintWriter("hummel/waypoints.txt", UTF_8)) {
			printWriter.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void genTableWeapons() {
		StringBuilder sb = new StringBuilder();

		Collection<String> content = new TreeSet<>();

		for (Item item : ITEMS) {
			if (item instanceof ItemSword) {
				StringBuilder localSb = new StringBuilder();

				float damage = GOTReflection.getDamageAmount(item);
				Item.ToolMaterial material = GOTReflection.getToolMaterial(item);

				localSb.append(NL).append("| ");
				localSb.append(getItemName(item)).append(" || ").append(getItemFilename(item)).append(" || ").append(item.getMaxDamage()).append(" || ").append(damage).append(" || ");
				if (material == null || material.getRepairItemStack() == null) {
					localSb.append("N/A");
				} else {
					localSb.append(getItemName(material.getRepairItemStack().getItem()));
				}
				localSb.append(NL).append("|-");

				content.add(localSb.toString());
			}
		}

		for (String s : content) {
			sb.append(s);
		}

		try (PrintWriter printWriter = new PrintWriter("hummel/weapon.txt", UTF_8)) {
			printWriter.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static StringBuilder genTemplateBiomeBandits() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Biome-Bandits");
		sb.append(BEGIN);

		for (GOTBiome biome : BIOMES) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(biome)).append(" = ");
			sb.append(biome.getBanditChance());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeClimate() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Biome-Climate");
		sb.append(BEGIN);

		for (GOTBiome biome : BIOMES) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(biome)).append(" = ");

			if (biome.getClimateType() != null) {
				switch (biome.getClimateType()) {
					case COLD:
						sb.append(Lang.CLIMATE_COLD);
						break;
					case COLD_AZ:
						sb.append(Lang.CLIMATE_COLD_AZ);
						break;
					case NORMAL:
						sb.append(Lang.CLIMATE_NORMAL);
						break;
					case NORMAL_AZ:
						sb.append(Lang.CLIMATE_NORMAL_AZ);
						break;
					case SUMMER:
						sb.append(Lang.CLIMATE_SUMMER);
						break;
					case SUMMER_AZ:
						sb.append(Lang.CLIMATE_SUMMER_AZ);
						break;
					case WINTER:
						sb.append(Lang.CLIMATE_WINTER);
						break;
				}
			} else {
				sb.append(Lang.CLIMATE_NULL);
			}
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeConquestFactions(World world) {
		Map<GOTBiome, Section> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			data.put(biome, new Section());

			List<GOTFactionContainer> facContainers = biome.getNPCSpawnList().getFactionContainers();

			if (facContainers.isEmpty()) {
				data.get(biome).setDesc(Lang.BIOME_NO_CONQUEST.toString());
			} else {
				Collection<GOTFactionContainer> conquestContainers = new HashSet<>();

				for (GOTFactionContainer facContainer : facContainers) {
					if (facContainer.getBaseWeight() <= 0) {
						conquestContainers.add(facContainer);
					}
				}

				if (conquestContainers.isEmpty()) {
					data.get(biome).setDesc(Lang.BIOME_SPAWN_ONLY.toString());
				} else {
					data.get(biome).setDesc(Lang.BIOME_HAS_CONQUEST.toString());

					for (GOTFactionContainer facContainer : conquestContainers) {
						for (GOTSpawnListContainer container : facContainer.getSpawnLists()) {
							for (GOTSpawnEntry entry : container.getSpawnList().getSpawnEntries()) {
								Entity entity = GOTReflection.newEntity(entry.entityClass, world);
								if (entity instanceof GOTEntityNPC) {
									GOTFaction fac = ((GOTEntityNPC) entity).getFaction();
									data.get(biome).getItems().add(NL + "* " + getFactionLink(fac) + ';');
									break;
								}
							}
						}
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Biome-ConquestNPC");
		sb.append(BEGIN);

		for (Map.Entry<GOTBiome, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomeName(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeInvasionFactions() {
		Map<GOTBiome, Section> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			data.put(biome, new Section());

			Collection<GOTInvasions> invasions = biome.getInvasionSpawns().getRegisteredInvasions();

			if (invasions.isEmpty()) {
				data.get(biome).setDesc(Lang.BIOME_NO_INVASIONS.toString());
			} else {
				data.get(biome).setDesc(Lang.BIOME_HAS_INVASIONS.toString());

				for (GOTInvasions invasion : invasions) {
					for (GOTInvasions.InvasionSpawnEntry entry : invasion.getInvasionMobs()) {
						Entity entity = ENTITY_CLASS_TO_ENTITY.get(entry.getEntityClass());
						if (entity instanceof GOTEntityNPC) {
							GOTFaction fac = ((GOTEntityNPC) entity).getFaction();
							data.get(biome).getItems().add(NL + "* " + getFactionLink(fac) + ';');
							break;
						}
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Biome-Invasions");
		sb.append(BEGIN);

		for (Map.Entry<GOTBiome, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomeName(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeMinerals() {
		Map<GOTBiome, Section> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			data.put(biome, new Section());

			data.get(biome).setDesc(Lang.BIOME_HAS_MINERALS.toString());

			Collection<GOTBiomeDecorator.OreGenerant> oreGenerants = new HashSet<>(biome.getDecorator().getBiomeSoils());
			oreGenerants.addAll(biome.getDecorator().getBiomeOres());
			oreGenerants.addAll(biome.getDecorator().getBiomeGems());

			for (GOTBiomeDecorator.OreGenerant oreGenerant : oreGenerants) {
				Block block = GOTReflection.getOreGenBlock(oreGenerant.getOreGen());
				int meta = GOTReflection.getOreGenMeta(oreGenerant.getOreGen());

				String stats = " (" + oreGenerant.getOreChance() + "%; Y: " + oreGenerant.getMinHeight() + '-' + oreGenerant.getMaxHeight() + ");";

				if (block instanceof GOTBlockOreGem || block instanceof BlockDirt || block instanceof GOTBlockRock) {
					data.get(biome).getItems().add(NL + "* [[" + getBlockMetaName(block, meta) + "]]" + stats);
				} else {
					data.get(biome).getItems().add(NL + "* [[" + getBlockName(block) + "]]" + stats);
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Biome-Minerals");
		sb.append(BEGIN);

		for (Map.Entry<GOTBiome, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomeName(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeMobs() {
		Map<GOTBiome, Section> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			data.put(biome, new Section());

			Collection<BiomeGenBase.SpawnListEntry> entries = new HashSet<>(biome.getSpawnableList(EnumCreatureType.ambient));
			entries.addAll(biome.getSpawnableList(EnumCreatureType.waterCreature));
			entries.addAll(biome.getSpawnableList(EnumCreatureType.creature));
			entries.addAll(biome.getSpawnableList(EnumCreatureType.monster));
			entries.addAll(biome.getSpawnableList(GOTBiome.CREATURE_TYPE_GOT_AMBIENT));

			if (entries.isEmpty()) {
				data.get(biome).setDesc(Lang.BIOME_NO_ANIMALS.toString());
			} else {
				data.get(biome).setDesc(Lang.BIOME_HAS_ANIMALS.toString());
				for (BiomeGenBase.SpawnListEntry entry : entries) {
					data.get(biome).getItems().add(NL + "* " + getEntityLink(entry.entityClass) + ';');
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Biome-Mobs");
		sb.append(BEGIN);

		for (Map.Entry<GOTBiome, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomeName(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeMusic() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Biome-Music");
		sb.append(BEGIN);

		for (GOTBiome biome : BIOMES) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(biome)).append(" = ");
			if (biome.getBiomeMusic() == null) {
				sb.append("N/A");
			} else {
				sb.append(biome.getBiomeMusic().getSubregion());
			}
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeName() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Biome-Name");
		sb.append(BEGIN);

		for (GOTBiome biome : BIOMES) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(biome)).append(" = ");
			sb.append(getBiomeName(biome));
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeRainfall() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Biome-Rainfall");
		sb.append(BEGIN);

		for (GOTBiome biome : BIOMES) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(biome)).append(" = ");

			GOTDate.AegonCalendar.getDate().getMonth().setSeason(GOTDate.Season.WINTER);
			GOTClimateType.performSeasonalChangesServerSide();
			sb.append(Lang.SEASON_WINTER).append(": ").append(biome.rainfall);
			GOTDate.AegonCalendar.getDate().getMonth().setSeason(GOTDate.Season.SPRING);
			GOTClimateType.performSeasonalChangesServerSide();
			sb.append("&lt;br&gt;").append(Lang.SEASON_SPRING).append(": ").append(biome.rainfall);
			GOTDate.AegonCalendar.getDate().getMonth().setSeason(GOTDate.Season.SUMMER);
			GOTClimateType.performSeasonalChangesServerSide();
			sb.append("&lt;br&gt;").append(Lang.SEASON_SUMMER).append(": ").append(biome.rainfall);
			GOTDate.AegonCalendar.getDate().getMonth().setSeason(GOTDate.Season.AUTUMN);
			GOTClimateType.performSeasonalChangesServerSide();
			sb.append("&lt;br&gt;").append(Lang.SEASON_AUTUMN).append(": ").append(biome.rainfall);
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeNPCs() {
		Map<GOTBiome, Section> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			data.put(biome, new Section());

			List<GOTFactionContainer> facContainers = biome.getNPCSpawnList().getFactionContainers();

			if (facContainers.isEmpty()) {
				data.get(biome).setDesc(Lang.BIOME_NO_SPAWN.toString());
			} else {
				Collection<GOTFactionContainer> spawnContainers = new HashSet<>();

				for (GOTFactionContainer facContainer : facContainers) {
					if (facContainer.getBaseWeight() > 0) {
						spawnContainers.add(facContainer);
					}
				}

				if (spawnContainers.isEmpty()) {
					data.get(biome).setDesc(Lang.BIOME_CONQUEST_ONLY.toString());
				} else {
					data.get(biome).setDesc(Lang.BIOME_HAS_SPAWN.toString());

					for (GOTFactionContainer facContainer : spawnContainers) {
						for (GOTSpawnListContainer container : facContainer.getSpawnLists()) {
							for (GOTSpawnEntry entry : container.getSpawnList().getSpawnEntries()) {
								data.get(biome).getItems().add(NL + "* " + getEntityLink(entry.entityClass) + ';');
							}
						}
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Biome-SpawnNPC");
		sb.append(BEGIN);

		for (Map.Entry<GOTBiome, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomeName(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeStructuresSettlements() {
		Map<GOTBiome, Section> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			data.put(biome, new Section());

			if (biome.getDecorator().getStructures().isEmpty()) {
				data.get(biome).setDesc(Lang.BIOME_NO_STRUCTURES.toString());
			} else {
				data.get(biome).setDesc(Lang.BIOME_HAS_STRUCTURES.toString());

				for (GOTBiomeDecorator.Structure structure : biome.getDecorator().getStructures()) {
					data.get(biome).getItems().add(NL + "* " + getStructureLink(structure.getStructureGen().getClass()) + ';');
				}

				for (GOTStructureBaseSettlement settlement : biome.getDecorator().getSettlements()) {
					if (settlement.getSpawnChance() != 0.0f) {
						Set<String> names = GOTStructureRegistry.S_CLASS_TO_NAME_MAPPING.get(settlement.getClass());
						if (names != null) {
							for (String name : names) {
								data.get(biome).getItems().add(NL + "* " + getSettlementName(name) + ';');
							}
						}
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Biome-Structures");
		sb.append(BEGIN);

		for (Map.Entry<GOTBiome, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomeName(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeTemperature() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Biome-Temperature");
		sb.append(BEGIN);

		for (GOTBiome biome : BIOMES) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(biome)).append(" = ");

			GOTDate.AegonCalendar.getDate().getMonth().setSeason(GOTDate.Season.WINTER);
			GOTClimateType.performSeasonalChangesServerSide();
			sb.append(Lang.SEASON_WINTER).append(": ").append(biome.temperature);
			GOTDate.AegonCalendar.getDate().getMonth().setSeason(GOTDate.Season.SPRING);
			GOTClimateType.performSeasonalChangesServerSide();
			sb.append("&lt;br&gt;").append(Lang.SEASON_SPRING).append(": ").append(biome.temperature);
			GOTDate.AegonCalendar.getDate().getMonth().setSeason(GOTDate.Season.SUMMER);
			GOTClimateType.performSeasonalChangesServerSide();
			sb.append("&lt;br&gt;").append(Lang.SEASON_SUMMER).append(": ").append(biome.temperature);
			GOTDate.AegonCalendar.getDate().getMonth().setSeason(GOTDate.Season.AUTUMN);
			GOTClimateType.performSeasonalChangesServerSide();
			sb.append("&lt;br&gt;").append(Lang.SEASON_AUTUMN).append(": ").append(biome.temperature);
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeTrees() {
		Map<GOTBiome, Section> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			data.put(biome, new Section());

			Collection<GOTTreeType> biomeTrees = EnumSet.noneOf(GOTTreeType.class);
			Map<GOTTreeType, GOTBiomeVariant> biomeVariantTrees = new EnumMap<>(GOTTreeType.class);

			for (GOTTreeType.WeightedTreeType weightedTreeType : biome.getDecorator().getTreeTypes()) {
				biomeTrees.add(weightedTreeType.getTreeType());
			}

			for (GOTBiomeVariantList.VariantBucket variantBucket : biome.getBiomeVariants().getVariantList()) {
				for (GOTTreeType.WeightedTreeType weightedTreeType : variantBucket.getVariant().getTreeTypes()) {
					if (!biomeTrees.contains(weightedTreeType.getTreeType())) {
						biomeVariantTrees.put(weightedTreeType.getTreeType(), variantBucket.getVariant());
					}
				}
			}

			if (biomeTrees.isEmpty() && biomeVariantTrees.isEmpty()) {
				data.get(biome).setDesc(Lang.BIOME_NO_TREES.toString());
			} else {
				if (biomeVariantTrees.isEmpty()) {
					data.get(biome).setDesc(Lang.BIOME_HAS_TREES_BIOME_ONLY.toString());
				} else {
					data.get(biome).setDesc(Lang.BIOME_HAS_TREES.toString());
				}

				for (GOTTreeType tree : biomeTrees) {
					data.get(biome).getItems().add(NL + "* [[" + getTreeName(tree) + "]];");
				}

				for (Map.Entry<GOTTreeType, GOTBiomeVariant> tree : biomeVariantTrees.entrySet()) {
					data.get(biome).getItems().add(NL + "* [[" + getTreeName(tree.getKey()) + "]] (" + getBiomeVariantName(tree.getValue()).toLowerCase(Locale.ROOT) + ");");
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Biome-Trees");
		sb.append(BEGIN);

		for (Map.Entry<GOTBiome, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomeName(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeVariants() {
		Map<GOTBiome, Section> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			data.put(biome, new Section());

			if (biome.getBiomeVariants().getVariantList().isEmpty()) {
				data.get(biome).setDesc(Lang.BIOME_NO_VARIANTS.toString());
			} else {
				for (GOTBiomeVariantList.VariantBucket variantBucket : biome.getBiomeVariants().getVariantList()) {
					data.get(biome).getItems().add(NL + "* " + getBiomeVariantName(variantBucket.getVariant()) + ';');
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Biome-Variants");
		sb.append(BEGIN);

		for (Map.Entry<GOTBiome, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomeName(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeVisitAchievement() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Biome-Achievement");
		sb.append(BEGIN);

		for (GOTBiome biome : BIOMES) {
			sb.append(NL).append("| ");
			sb.append(getBiomePagename(biome)).append(" = ");

			GOTAchievement ach = biome.getBiomeAchievement();

			if (ach == null) {
				sb.append(Lang.BIOME_NO_ACHIEVEMENT);
			} else {
				sb.append('«').append(ach.getTitle()).append('»');
			}
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateBiomeWaypoints() {
		Map<GOTBiome, Section> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			data.put(biome, new Section());

			GOTWaypoint.Region region = biome.getBiomeWaypoints();

			if (region == null) {
				data.get(biome).setDesc(Lang.BIOME_NO_WAYPOINTS.toString());
			} else {
				data.get(biome).setDesc(Lang.BIOME_HAS_WAYPOINTS.toString());

				for (GOTWaypoint wp : region.getWaypoints()) {
					data.get(biome).getItems().add(NL + "* " + wp.getDisplayName() + " (" + getFactionLink(wp.getFaction()) + ");");
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Biome-Waypoints");
		sb.append(BEGIN);

		for (Map.Entry<GOTBiome, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getBiomeName(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityBannerBearer() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-BannerBearer");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTBannerBearer) {
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entityEntry.getKey())).append(" = ");
				sb.append(TRUE);
			}
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityBuys() {
		Map<Class<? extends Entity>, Section> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTTradeable) {
				data.put(entityEntry.getKey(), new Section());

				GOTTradeEntries entries = ((GOTTradeable) entityEntry.getValue()).getSellPool();

				for (GOTTradeEntry entry : entries.getTradeEntries()) {
					data.get(entityEntry.getKey()).getItems().add(NL + "* " + entry.getTradeItem().getDisplayName() + ": {{Coins|" + entry.getCost() + "}};");
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-Buys");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends Entity>, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityCharacter() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-Character");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTEntityNPC && ((GOTEntityNPC) entityEntry.getValue()).isLegendaryNPC()) {
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entityEntry.getKey())).append(" = ");
				sb.append(TRUE);
			}
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityLegendaryDrop() {
		Map<Class<? extends Entity>, Section> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTEntityNPC && ((GOTEntityNPC) entityEntry.getValue()).isLegendaryNPC()) {
				data.put(entityEntry.getKey(), new Section());

				GOTEntityNPC npc = (GOTEntityNPC) entityEntry.getValue();

				npc.dropFewItems(true, 999);

				Collection<Object> drops = npc.getDrops();
				Collection<ItemStack> itemStacks = new HashSet<>();

				for (Object obj : drops) {
					if (obj instanceof Item) {
						ItemStack itemStack = new ItemStack((Item) obj);
						itemStacks.add(itemStack);
					}
				}

				if (itemStacks.isEmpty()) {
					data.get(entityEntry.getKey()).setDesc(Lang.MOB_NO_LEGENDARY_DROP.toString());
				} else {
					for (ItemStack itemStack : itemStacks) {
						data.get(entityEntry.getKey()).getItems().add(NL + "* " + itemStack.getDisplayName() + ';');
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-LegendaryDrop");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends Entity>, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityFaction() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-Faction");
		sb.append(BEGIN);
		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTEntityNPC) {
				GOTFaction fac = ((GOTEntityNPC) entityEntry.getValue()).getFaction();
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entityEntry.getKey())).append(" = ").append(getFactionLink(fac));
			}
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityFarmhand() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-Farmhand");
		sb.append(BEGIN);
		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTFarmhand) {
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entityEntry.getKey())).append(" = ").append(TRUE);
			}
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityHealth() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-Health");
		sb.append(BEGIN);
		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			EntityLivingBase entity = (EntityLivingBase) entityEntry.getValue();
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entityEntry.getKey())).append(" = ").append((int) entity.getMaxHealth());
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityHireAlignment() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-Alignment");
		sb.append(BEGIN);
		next:
		for (Class<? extends Entity> entityClass : UNIT_CLASSES) {
			for (GOTUnitTradeEntries entries : UNIT_TRADE_ENTRIES) {
				for (GOTUnitTradeEntry entry : entries.getTradeEntries()) {
					if (entry.getEntityClass() == entityClass) {
						sb.append(NL).append("| ");
						sb.append(getEntityPagename(entityClass)).append(" = ");

						int alignment = (int) entry.getAlignmentRequired();

						if (entry.getPledgeType() == GOTUnitTradeEntry.PledgeType.NONE) {
							sb.append('+').append(alignment);
						} else {
							sb.append('+').append(Math.max(alignment, 100));
						}
						continue next;
					}
				}
			}
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityHirePrice() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-Price");
		sb.append(BEGIN);
		for (GOTUnitTradeEntries entries : UNIT_TRADE_ENTRIES) {
			for (GOTUnitTradeEntry entry : entries.getTradeEntries()) {
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entry.getEntityClass())).append(" = ");

				int cost = entry.getInitialCost();

				if (entry.getPledgeType() == GOTUnitTradeEntry.PledgeType.NONE) {
					sb.append("{{Coins|").append(cost * 2).append("}}");
				} else {
					sb.append("N/A");
				}
			}
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityHirePricePledge() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-PricePledge");
		sb.append(BEGIN);
		for (GOTUnitTradeEntries entries : UNIT_TRADE_ENTRIES) {
			for (GOTUnitTradeEntry entry : entries.getTradeEntries()) {
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entry.getEntityClass())).append(" = ");

				int cost = entry.getInitialCost();

				sb.append("{{Coins|").append(cost).append("}}");
			}
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityImmuneToFire() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-ImmuneToFire");
		sb.append(BEGIN);
		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue().isImmuneToFire()) {
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entityEntry.getKey())).append(" = ").append(TRUE);
			}
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityImmuneToFrost() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-ImmuneToFrost");
		sb.append(BEGIN);
		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTEntityNPC && entityEntry.getValue() instanceof GOTBiome.ImmuneToFrost) {
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entityEntry.getKey())).append(" = ").append(TRUE);
			}
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityImmuneToHeat() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-ImmuneToHeat");
		sb.append(BEGIN);
		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTBiome.ImmuneToHeat) {
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entityEntry.getKey())).append(" = ").append(TRUE);
			}
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityKillAchievement() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-Achievement");
		sb.append(BEGIN);
		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTEntityNPC) {
				GOTAchievement ach = ((GOTEntityNPC) entityEntry.getValue()).getKillAchievement();
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entityEntry.getKey())).append(" = ");
				if (ach == null) {
					sb.append("N/A");
				} else {
					sb.append('«').append(ach.getTitle()).append('»');
				}
			}
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityKillAlignment() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-Bonus");
		sb.append(BEGIN);
		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTEntityNPC) {
				int bonus = (int) ((GOTEntityNPC) entityEntry.getValue()).getAlignmentBonus();
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entityEntry.getKey())).append(" = ").append('+').append(bonus);
			}
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityMarriage() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-Marriage");
		sb.append(BEGIN);
		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTEntityNPC && GOTEntityUtils.canBeMarried((GOTEntityNPC) entityEntry.getValue())) {
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entityEntry.getKey())).append(" = ").append(TRUE);
			}
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityMercenary() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-Mercenary");
		sb.append(BEGIN);
		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTMercenary) {
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entityEntry.getKey())).append(" = ").append(TRUE);
			}
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityOwner() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append("DB Mob-Owner");
		sb.append(BEGIN);
		for (Class<? extends Entity> entityClass : UNIT_CLASSES) {
			Map<Class<? extends Entity>, Class<? extends Entity>> owners = new HashMap<>();
			loop:
			for (Map.Entry<Class<? extends Entity>, Entity> ownerEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
				if (ownerEntry.getValue() instanceof GOTUnitTradeable) {
					GOTUnitTradeEntries entries = ((GOTUnitTradeable) ownerEntry.getValue()).getUnits();
					if (!((GOTEntityNPC) ownerEntry.getValue()).isLegendaryNPC()) {
						for (GOTUnitTradeEntry entry : entries.getTradeEntries()) {
							if (entry.getEntityClass() == entityClass) {
								owners.put(entityClass, ownerEntry.getKey());
								break loop;
							}
						}
					}
				}
			}
			if (owners.isEmpty()) {
				loop:
				for (Map.Entry<Class<? extends Entity>, Entity> ownerEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
					if (ownerEntry.getValue() instanceof GOTUnitTradeable) {
						GOTUnitTradeEntries entries = ((GOTUnitTradeable) ownerEntry.getValue()).getUnits();
						if (((GOTEntityNPC) ownerEntry.getValue()).isLegendaryNPC()) {
							for (GOTUnitTradeEntry entry : entries.getTradeEntries()) {
								if (entry.getEntityClass() == entityClass) {
									owners.put(entityClass, ownerEntry.getKey());
									break loop;
								}
							}
						}
					}
				}
			}
			if (!owners.isEmpty()) {
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entityClass)).append(" = ").append(getEntityLink(owners.get(entityClass)));
			}
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityRideableMob() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-Rideable2");
		sb.append(BEGIN);
		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTNPCMount) {
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entityEntry.getKey())).append(" = ").append(TRUE);
			}
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityRideableNPC() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append(" DB Mob-Rideable1");
		sb.append(BEGIN);
		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTEntitySpiderBase) {
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entityEntry.getKey())).append(" = ").append(TRUE);
			}
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntitySells() {
		Map<Class<? extends Entity>, Section> data = new HashMap<>();

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTTradeable) {
				data.put(entityEntry.getKey(), new Section());

				GOTTradeEntries entries = ((GOTTradeable) entityEntry.getValue()).getBuyPool();

				for (GOTTradeEntry entry : entries.getTradeEntries()) {
					data.get(entityEntry.getKey()).getItems().add(NL + "* " + entry.getTradeItem().getDisplayName() + ": {{Coins|" + entry.getCost() + "}};");
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-Sells");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends Entity>, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntitySellsUnits() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-Units");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends Entity>, Entity> ownerEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (ownerEntry.getValue() instanceof GOTUnitTradeable) {
				GOTUnitTradeEntries entries = ((GOTUnitTradeable) ownerEntry.getValue()).getUnits();

				sb.append(NL).append("| ");
				sb.append(getEntityPagename(ownerEntry.getKey())).append(" = ");

				for (GOTUnitTradeEntry entry : entries.getTradeEntries()) {
					sb.append(NL).append("* ").append(getEntityLink(entry.getEntityClass()));
					if (entry.getMountClass() != null) {
						sb.append(Lang.RIDER);
					}
					sb.append(": ");

					int cost = entry.getInitialCost();
					int alignment = (int) entry.getAlignmentRequired();

					if (entry.getPledgeType() == GOTUnitTradeEntry.PledgeType.NONE) {
						sb.append("{{Coins|").append(cost * 2).append("}} ").append(Lang.NO_PLEDGE).append(", ");
						sb.append("{{Coins|").append(cost).append("}} ").append(Lang.NEED_PLEDGE).append("; ");
						sb.append('+').append(alignment).append(Lang.REPUTATION).append(';');
					} else {
						sb.append("N/A ").append(Lang.NO_PLEDGE).append(", ");
						sb.append("{{Coins|").append(cost).append("}} ").append(Lang.NEED_PLEDGE).append("; ");
						sb.append('+').append(Math.max(alignment, 100)).append(Lang.REPUTATION).append(';');
					}
				}
			}
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntitySmith() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-Smith");
		sb.append(BEGIN);
		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTTradeable.Smith) {
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entityEntry.getKey())).append(" = ").append(TRUE);
			}
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityBiomes() {
		Map<Class<? extends Entity>, Section> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			Collection<BiomeGenBase.SpawnListEntry> spawnEntries = new HashSet<>();
			Collection<BiomeGenBase.SpawnListEntry> conquestEntries = new HashSet<>();
			Collection<GOTInvasions.InvasionSpawnEntry> invasionEntries = new HashSet<>();
			spawnEntries.addAll(biome.getSpawnableList(EnumCreatureType.ambient));
			spawnEntries.addAll(biome.getSpawnableList(EnumCreatureType.waterCreature));
			spawnEntries.addAll(biome.getSpawnableList(EnumCreatureType.creature));
			spawnEntries.addAll(biome.getSpawnableList(EnumCreatureType.monster));
			spawnEntries.addAll(biome.getSpawnableGOTAmbientList());

			for (GOTFactionContainer facContainer : biome.getNPCSpawnList().getFactionContainers()) {
				if (facContainer.getBaseWeight() > 0) {
					for (GOTSpawnListContainer container : facContainer.getSpawnLists()) {
						spawnEntries.addAll(container.getSpawnList().getSpawnEntries());
					}
				} else {
					for (GOTSpawnListContainer container : facContainer.getSpawnLists()) {
						conquestEntries.addAll(container.getSpawnList().getSpawnEntries());
					}
				}
			}

			for (GOTInvasions invasion : biome.getInvasionSpawns().getRegisteredInvasions()) {
				invasionEntries.addAll(invasion.getInvasionMobs());
			}

			for (BiomeGenBase.SpawnListEntry entry : spawnEntries) {
				data.computeIfAbsent(entry.entityClass, s -> new Section());
				data.get(entry.entityClass).getItems().add(NL + "* " + getBiomeLink(biome) + ';');
			}

			for (BiomeGenBase.SpawnListEntry entry : conquestEntries) {
				data.computeIfAbsent(entry.entityClass, s -> new Section());
				data.get(entry.entityClass).getItems().add(NL + "* " + getBiomeLink(biome) + ' ' + Lang.ENTITY_CONQUEST + ';');
			}

			for (GOTInvasions.InvasionSpawnEntry entry : invasionEntries) {
				data.computeIfAbsent(entry.getEntityClass(), s -> new Section());
				data.get(entry.getEntityClass()).getItems().add(NL + "* " + getBiomeLink(biome) + ' ' + Lang.ENTITY_INVASION + ';');
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Biome-Mobs");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends Entity>, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntitySpawnsInDarkness() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-SpawnsInDarkness");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTEntityNPC && ((GOTEntityNPC) entityEntry.getValue()).isSpawnsInDarkness()) {
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entityEntry.getKey())).append(" = ");
				sb.append(TRUE);
			}
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityTargetSeeker() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-TargetSeeker");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTEntityNPC && ((GOTEntityNPC) entityEntry.getValue()).isTargetSeeker()) {
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entityEntry.getKey())).append(" = ");
				sb.append(TRUE);
			}
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityTradeable() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-Tradeable");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTTradeable) {
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entityEntry.getKey())).append(" = ");
				sb.append(TRUE);
			}
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityUnitTradeable() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-UnitTradeable");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			if (entityEntry.getValue() instanceof GOTUnitTradeable) {
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(entityEntry.getKey())).append(" = ");
				sb.append(TRUE);
			}
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateEntityWaypoint(World world) {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-Waypoint");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends Entity>, GOTWaypoint> entityEntry : ENTITY_CLASS_TO_WP.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entityEntry.getKey())).append(" = ");
			sb.append(entityEntry.getValue().getDisplayName());
		}

		for (Map.Entry<GOTAbstractWaypoint, GOTStructureBaseSettlement> entry : GOTFixer.SPAWNERS.entrySet()) {
			GOTStructureBaseSettlement spawner = entry.getValue();
			spawner.getLegendaryNPCs(world);
			for (GOTFixer.SpawnInfo info : spawner.getLegendaryNPCs(world)) {
				sb.append(NL).append("| ");
				sb.append(getEntityPagename(info.getNPC().getClass())).append(" = ");
				sb.append(entry.getKey().getDisplayName());
			}
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionBanners() {
		Map<GOTFaction, Section> data = new EnumMap<>(GOTFaction.class);

		for (GOTFaction faction : FACTIONS) {
			data.put(faction, new Section());

			Collection<GOTItemBanner.BannerType> bannerTypes = faction.getFactionBanners();

			if (bannerTypes.isEmpty()) {
				data.get(faction).setDesc(Lang.FACTION_NO_BANNERS.toString());
			} else {
				data.get(faction).setDesc(Lang.FACTION_HAS_BANNERS.toString());
				for (GOTItemBanner.BannerType banner : bannerTypes) {
					data.get(faction).getItems().add(NL + "* " + getBannerName(banner) + ';');
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Faction-Banners");
		sb.append(BEGIN);

		for (Map.Entry<GOTFaction, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionCharacters() {
		Map<GOTFaction, Section> data = new EnumMap<>(GOTFaction.class);

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			Entity entity = entityEntry.getValue();
			if (entity instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) entity;
				if (npc.isLegendaryNPC()) {
					data.computeIfAbsent(npc.getFaction(), s -> new Section());
					data.get(npc.getFaction()).getItems().add(NL + "* " + getEntityLink(entityEntry.getKey()) + ';');
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Faction-Chars");
		sb.append(BEGIN);

		for (Map.Entry<GOTFaction, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionCodename() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Faction-Codename");
		sb.append(BEGIN);

		for (GOTFaction faction : FACTIONS) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(faction)).append(" = ");
			sb.append(faction.codeName());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionConquestBiomes(World world) {
		Map<GOTFaction, Section> data = new EnumMap<>(GOTFaction.class);

		for (GOTBiome biome : BIOMES) {
			List<GOTFactionContainer> facContainers = biome.getNPCSpawnList().getFactionContainers();

			Collection<GOTFactionContainer> conquestContainers = new HashSet<>();

			for (GOTFactionContainer facContainer : facContainers) {
				if (facContainer.getBaseWeight() <= 0) {
					conquestContainers.add(facContainer);
				}
			}

			for (GOTFactionContainer facContainer : conquestContainers) {
				for (GOTSpawnListContainer container : facContainer.getSpawnLists()) {
					for (GOTSpawnEntry entry : container.getSpawnList().getSpawnEntries()) {
						Entity entity = GOTReflection.newEntity(entry.entityClass, world);
						if (entity instanceof GOTEntityNPC) {
							GOTFaction faction = ((GOTEntityNPC) entity).getFaction();
							data.computeIfAbsent(faction, s -> new Section());
							data.get(faction).getItems().add(NL + "* " + getBiomeLink(biome) + ';');
							break;
						}
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Faction-Conquest");
		sb.append(BEGIN);

		for (Map.Entry<GOTFaction, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionEnemies() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Faction-Enemies");
		sb.append(BEGIN);

		for (GOTFaction faction1 : FACTIONS) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(faction1)).append(" = ");

			Collection<GOTFaction> facEnemies = EnumSet.noneOf(GOTFaction.class);
			for (GOTFaction faction2 : FACTIONS) {
				if (faction1.isBadRelation(faction2) && faction1 != faction2) {
					facEnemies.add(faction2);
				}
			}

			if (facEnemies.isEmpty()) {
				sb.append(Lang.FACTION_NO_ENEMIES);
			} else {
				Collection<String> content = new TreeSet<>();

				for (GOTFaction faction : facEnemies) {
					content.add(getFactionLink(faction));
				}

				StringJoiner sj = new StringJoiner(" • ");
				for (String item : content) {
					sj.add(item);
				}

				sb.append(sj);
			}
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionFriends() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Faction-Friends");
		sb.append(BEGIN);

		for (GOTFaction faction1 : FACTIONS) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(faction1)).append(" = ");

			Collection<GOTFaction> facFriends = EnumSet.noneOf(GOTFaction.class);
			for (GOTFaction faction2 : FACTIONS) {
				if (faction1.isGoodRelation(faction2) && faction1 != faction2) {
					facFriends.add(faction2);
				}
			}

			if (facFriends.isEmpty()) {
				sb.append(Lang.FACTION_NO_FRIENDS);
			} else {
				Collection<String> content = new TreeSet<>();

				for (GOTFaction faction : facFriends) {
					content.add(getFactionLink(faction));
				}

				StringJoiner sj = new StringJoiner(" • ");
				for (String item : content) {
					sj.add(item);
				}

				sb.append(sj);
			}
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionInvasionBiomes() {
		Map<GOTFaction, Section> data = new EnumMap<>(GOTFaction.class);

		for (GOTBiome biome : BIOMES) {
			Collection<GOTInvasions> invasions = biome.getInvasionSpawns().getRegisteredInvasions();

			for (GOTInvasions invasion : invasions) {
				for (GOTInvasions.InvasionSpawnEntry entry : invasion.getInvasionMobs()) {
					Entity entity = ENTITY_CLASS_TO_ENTITY.get(entry.getEntityClass());
					if (entity instanceof GOTEntityNPC) {
						GOTFaction faction = ((GOTEntityNPC) entity).getFaction();
						data.computeIfAbsent(faction, s -> new Section());
						data.get(faction).getItems().add(NL + "* " + getBiomeLink(biome) + ';');
						break;
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Faction-Invasions");
		sb.append(BEGIN);

		for (Map.Entry<GOTFaction, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionName() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Faction-Name");
		sb.append(BEGIN);
		for (GOTFaction faction : FACTIONS) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(faction)).append(" = ");
			sb.append(getFactionName(faction));
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionNPCs() {
		Map<GOTFaction, Section> data = new EnumMap<>(GOTFaction.class);

		for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : ENTITY_CLASS_TO_ENTITY.entrySet()) {
			Entity entity = entityEntry.getValue();
			if (entity instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) entity;
				if (!npc.isLegendaryNPC()) {
					data.computeIfAbsent(npc.getFaction(), s -> new Section());
					data.get(npc.getFaction()).getItems().add(NL + "* " + getEntityLink(entityEntry.getKey()) + ';');
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Faction-NPC");
		sb.append(BEGIN);

		for (Map.Entry<GOTFaction, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionPledgeRank() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Faction-Pledge");
		sb.append(BEGIN);

		for (GOTFaction faction : FACTIONS) {
			GOTFactionRank rank = faction.getPledgeRank();

			if (rank != null) {
				sb.append(NL).append("| ");
				sb.append(getFactionPagename(faction)).append(" = ").append(rank.getDisplayName());

				String femRank = rank.getDisplayFullNameFem();
				if (!femRank.contains("got")) {
					sb.append('/').append(femRank);
				}

				sb.append(" (+").append((int) faction.getPledgeAlignment()).append(')');
			}
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionRanks() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Faction-Ranks");
		sb.append(BEGIN);

		for (GOTFaction fac : FACTIONS) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(fac)).append(" = ");
			if (fac.getRanksSortedDescending().isEmpty()) {
				sb.append(Lang.FACTION_NO_RANKS);
			} else {
				sb.append(Lang.FACTION_HAS_RANKS);
				for (GOTFactionRank rank : fac.getRanksSortedDescending()) {
					sb.append(NL).append("* ").append(rank.getDisplayFullName());

					String femRank = rank.getDisplayFullNameFem();
					if (!femRank.contains("got")) {
						sb.append('/').append(femRank);
					}

					sb.append(" (+").append((int) rank.getAlignment()).append(");");
				}
			}
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionRegion() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Faction-Region");
		sb.append(BEGIN);
		for (GOTFaction fac : FACTIONS) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(fac)).append(" = ");
			if (fac.getFactionRegion() == null) {
				sb.append("N/A");
			} else {
				sb.append(fac.getFactionRegion().getRegionName());
			}
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionShieldsCapes() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Faction-ShieldsCapes");
		sb.append(BEGIN);
		for (GOTFaction fac : FACTIONS) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(fac)).append(" = ");

			Collection<GOTShields> facShields = EnumSet.noneOf(GOTShields.class);
			for (GOTShields shield : SHIELDS) {
				if (shield.getAlignmentFaction() == fac) {
					facShields.add(shield);
				}
			}

			Collection<GOTCapes> facCapes = EnumSet.noneOf(GOTCapes.class);
			for (GOTCapes cape : CAPES) {
				if (cape.getAlignmentFaction() == fac) {
					facCapes.add(cape);
				}
			}

			if (facShields.isEmpty() && facCapes.isEmpty()) {
				sb.append(Lang.FACTION_NO_ATTRIBUTES);
			} else {
				Collection<String> content = new TreeSet<>();

				sb.append(NL).append("&lt;table class=\"wikitable shields-capes\"&gt;");

				for (GOTShields shield : facShields) {
					content.add(NL + "&lt;tr&gt;&lt;td&gt;" + shield.getShieldName() + "&lt;/td&gt;&lt;td&gt;" + shield.getShieldDesc() + "&lt;/td&gt;&lt;td&gt;" + getShieldFilename(shield) + "&lt;/td&gt;&lt;/tr&gt;");
				}

				for (String s : content) {
					sb.append(s);
				}

				for (GOTCapes cape : facCapes) {
					content.add(NL + "&lt;tr&gt;&lt;td&gt;" + cape.getCapeName() + "&lt;/td&gt;&lt;td&gt;" + cape.getCapeDesc() + "&lt;/td&gt;&lt;td&gt;" + getCapeFilename(cape) + "&lt;/td&gt;&lt;/tr&gt;");
				}

				for (String s : content) {
					sb.append(s);
				}

				sb.append(NL).append("&lt;table&gt;");
			}
		}
		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionSpawnBiomes(World world) {
		Map<GOTFaction, Section> data = new EnumMap<>(GOTFaction.class);

		for (GOTBiome biome : BIOMES) {
			List<GOTFactionContainer> facContainers = biome.getNPCSpawnList().getFactionContainers();

			Collection<GOTFactionContainer> spawnContainers = new HashSet<>();

			for (GOTFactionContainer facContainer : facContainers) {
				if (facContainer.getBaseWeight() > 0) {
					spawnContainers.add(facContainer);
				}
			}

			for (GOTFactionContainer facContainer : spawnContainers) {
				for (GOTSpawnListContainer container : facContainer.getSpawnLists()) {
					for (GOTSpawnEntry entry : container.getSpawnList().getSpawnEntries()) {
						Entity entity = GOTReflection.newEntity(entry.entityClass, world);
						if (entity instanceof GOTEntityNPC) {
							GOTFaction faction = ((GOTEntityNPC) entity).getFaction();
							data.computeIfAbsent(faction, s -> new Section());
							data.get(faction).getItems().add(NL + "* " + getBiomeLink(biome) + ';');
							break;
						}
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Faction-Spawn");
		sb.append(BEGIN);

		for (Map.Entry<GOTFaction, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionStructures() {
		Map<GOTFaction, Section> data = new EnumMap<>(GOTFaction.class);

		for (Map.Entry<Class<? extends WorldGenerator>, GOTFaction> entry : GOTStructureRegistry.CLASS_TO_FACTION_MAPPING.entrySet()) {
			data.computeIfAbsent(entry.getValue(), s -> new Section());
			data.get(entry.getValue()).getItems().add(NL + "* " + getStructureLink(entry.getKey()) + ';');
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Faction-Structures");
		sb.append(BEGIN);

		for (Map.Entry<GOTFaction, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionWarCrimes() {
		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Faction-WarCrimes");
		sb.append(BEGIN);

		for (GOTFaction fac : FACTIONS) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(fac)).append(" = ");

			if (fac.isApprovesWarCrimes()) {
				sb.append(Lang.FACTION_HAS_WAR_CRIMES);
			} else {
				sb.append(Lang.FACTION_NO_WAR_CRIMES);
			}
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateFactionWaypoints() {
		Map<GOTFaction, Section> data = new EnumMap<>(GOTFaction.class);

		for (GOTWaypoint wp : WAYPOINTS) {
			data.computeIfAbsent(wp.getFaction(), s -> new Section());
			data.get(wp.getFaction()).getItems().add(NL + "* " + wp.getDisplayName() + ';');
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Faction-Waypoints");
		sb.append(BEGIN);

		for (Map.Entry<GOTFaction, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getFactionPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateMineralBiomes() {
		Map<String, Section> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			Collection<GOTBiomeDecorator.OreGenerant> oreGenerants = new HashSet<>(biome.getDecorator().getBiomeSoils());
			oreGenerants.addAll(biome.getDecorator().getBiomeOres());
			oreGenerants.addAll(biome.getDecorator().getBiomeGems());

			for (GOTBiomeDecorator.OreGenerant oreGenerant : oreGenerants) {
				Block block = GOTReflection.getOreGenBlock(oreGenerant.getOreGen());
				int meta = GOTReflection.getOreGenMeta(oreGenerant.getOreGen());

				String stats = " (" + oreGenerant.getOreChance() + "%; Y: " + oreGenerant.getMinHeight() + '-' + oreGenerant.getMaxHeight() + ");";

				if (block instanceof GOTBlockOreGem || block instanceof BlockDirt || block instanceof GOTBlockRock) {
					data.computeIfAbsent(getBlockMetaName(block, meta), s -> new Section());
					data.get(getBlockMetaName(block, meta)).getItems().add(NL + "* " + getBiomeLink(biome) + stats);
				} else {
					data.computeIfAbsent(getBlockName(block), s -> new Section());
					data.get(getBlockName(block)).getItems().add(NL + "* " + getBiomeLink(biome) + stats);
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Mineral-Biomes");
		sb.append(BEGIN);

		for (Map.Entry<String, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(entry.getKey()).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateMtmEntitiesStructures(World world) {
		StringBuilder sb = new StringBuilder();

		Map<Class<? extends WorldGenerator>, Section> dataES = new HashMap<>();
		Map<Class<? extends Entity>, Section> dataSE = new HashMap<>();

		for (Class<? extends WorldGenerator> strClass : STRUCTURE_CLASSES) {
			dataES.put(strClass, new Section());

			WorldGenerator generator = null;
			try {
				generator = strClass.getConstructor(Boolean.TYPE).newInstance(true);
			} catch (Exception ignored) {
			}

			if (generator instanceof GOTStructureBase) {
				GOTStructureBase structure = (GOTStructureBase) generator;
				structure.setRestrictions(false);
				structure.setWikiGen(true);
				structure.generate(world, world.rand, 0, 0, 0, 0);

				Set<Class<? extends Entity>> entityClasses = structure.getEntityClasses();
				for (Class<? extends Entity> entityClass : entityClasses) {
					dataES.get(strClass).getItems().add(NL + "* " + getEntityLink(entityClass) + ';');
					dataSE.computeIfAbsent(entityClass, s -> new Section());
					dataSE.get(entityClass).getItems().add(NL + "* " + getStructureLink(strClass) + ';');
				}
			}
		}

		sb.append(TITLE).append(TEMPLATE).append("DB Mob-Structures");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends WorldGenerator>, Section> entry : dataES.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getStructureName(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		sb.append(TITLE).append(TEMPLATE).append("DB Structure-Mobs");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends Entity>, Section> entry : dataSE.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getEntityPagename(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateStructureBiomes() {
		Map<Class<? extends WorldGenerator>, Section> data = new HashMap<>();

		for (GOTBiome biome : BIOMES) {
			for (GOTBiomeDecorator.Structure structure : biome.getDecorator().getStructures()) {
				data.computeIfAbsent(structure.getStructureGen().getClass(), s -> new Section());
				data.get(structure.getStructureGen().getClass()).getItems().add(NL + "* " + getBiomeLink(biome) + ';');
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Structure-Biomes");
		sb.append(BEGIN);

		for (Map.Entry<Class<? extends WorldGenerator>, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getStructureName(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder genTemplateTreeBiomes() {
		Map<GOTTreeType, Section> data = new EnumMap<>(GOTTreeType.class);

		for (GOTBiome biome : BIOMES) {
			Collection<GOTTreeType> biomeTrees = EnumSet.noneOf(GOTTreeType.class);
			Map<GOTTreeType, GOTBiomeVariant> biomeVariantTrees = new EnumMap<>(GOTTreeType.class);

			for (GOTTreeType.WeightedTreeType weightedTreeType : biome.getDecorator().getTreeTypes()) {
				biomeTrees.add(weightedTreeType.getTreeType());
			}

			for (GOTBiomeVariantList.VariantBucket variantBucket : biome.getBiomeVariants().getVariantList()) {
				for (GOTTreeType.WeightedTreeType weightedTreeType : variantBucket.getVariant().getTreeTypes()) {
					if (!biomeTrees.contains(weightedTreeType.getTreeType())) {
						biomeVariantTrees.put(weightedTreeType.getTreeType(), variantBucket.getVariant());
					}
				}
			}

			for (GOTTreeType tree : biomeTrees) {
				data.computeIfAbsent(tree, s -> new Section());
				data.get(tree).getItems().add(NL + "* " + getBiomeLink(biome) + ';');
			}

			for (Map.Entry<GOTTreeType, GOTBiomeVariant> treeEntry : biomeVariantTrees.entrySet()) {
				data.computeIfAbsent(treeEntry.getKey(), s -> new Section());
				data.get(treeEntry.getKey()).getItems().add(NL + "* " + getBiomeLink(biome) + " (" + Lang.TREE_VARIANT_ONLY + ");");
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(TITLE).append(TEMPLATE).append("DB Tree-Biomes");
		sb.append(BEGIN);

		for (Map.Entry<GOTTreeType, Section> entry : data.entrySet()) {
			sb.append(NL).append("| ");
			sb.append(getTreeName(entry.getKey())).append(" = ");

			appendSection(sb, entry.getValue());
		}

		sb.append(END);

		return sb;
	}

	private static StringBuilder addPagesBiomes(Collection<String> neededPages, Collection<String> existingPages) {
		StringBuilder sb = new StringBuilder();

		for (GOTBiome biome : BIOMES) {
			String pageName = getBiomePagename(biome);
			neededPages.add(pageName);
			if (!existingPages.contains(pageName)) {
				sb.append(TITLE_SINGLE).append(pageName);
				sb.append(PAGE_LEFT).append("{{Статья Биом}}").append(PAGE_RIGHT);
			}
		}

		return sb;
	}

	private static StringBuilder addPagesEntities(Collection<String> neededPages, Collection<String> existingPages) {
		StringBuilder sb = new StringBuilder();

		for (Class<? extends Entity> entityClass : ENTITY_CLASSES) {
			String pageName = getEntityPagename(entityClass);
			neededPages.add(pageName);
			if (!existingPages.contains(pageName)) {
				sb.append(TITLE_SINGLE).append(pageName);
				sb.append(PAGE_LEFT).append("{{Статья Моб}}").append(PAGE_RIGHT);
			}
		}

		return sb;
	}

	private static StringBuilder addPagesFactions(Collection<String> neededPages, Collection<String> existingPages) {
		StringBuilder sb = new StringBuilder();

		for (GOTFaction faction : FACTIONS) {
			String pageName = getFactionPagename(faction);
			neededPages.add(pageName);
			if (!existingPages.contains(pageName)) {
				sb.append(TITLE_SINGLE).append(pageName);
				sb.append(PAGE_LEFT).append("{{Статья Фракция}}").append(PAGE_RIGHT);
			}
		}

		return sb;
	}

	private static StringBuilder addPagesMinerals(Collection<String> neededPages, Collection<String> existingPages) {
		StringBuilder sb = new StringBuilder();

		for (String pageName : MINERALS) {
			neededPages.add(pageName);
			if (!existingPages.contains(pageName)) {
				sb.append(TITLE_SINGLE).append(pageName);
				sb.append(PAGE_LEFT).append("{{Статья Ископаемое}}").append(PAGE_RIGHT);
			}
		}

		return sb;
	}

	private static StringBuilder addPagesStructures(Collection<String> neededPages, Collection<String> existingPages) {
		StringBuilder sb = new StringBuilder();

		for (Class<? extends WorldGenerator> strClass : STRUCTURE_CLASSES) {
			String pageName = getStructureName(strClass);
			neededPages.add(pageName);
			if (!existingPages.contains(pageName)) {
				sb.append(TITLE_SINGLE).append(pageName);
				sb.append(PAGE_LEFT).append("{{Статья Структура}}").append(PAGE_RIGHT);
			}
		}

		return sb;
	}

	private static StringBuilder addPagesTrees(Collection<String> neededPages, Collection<String> existingPages) {
		StringBuilder sb = new StringBuilder();

		for (GOTTreeType tree : TREES) {
			String pageName = getTreeName(tree);
			neededPages.add(pageName);
			if (!existingPages.contains(pageName)) {
				sb.append(TITLE_SINGLE).append(pageName);
				sb.append(PAGE_LEFT).append("{{Статья Дерево}}").append(PAGE_RIGHT);
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

	private static void searchForUnitClasses(Collection<Class<? extends Entity>> hireable, Iterable<GOTUnitTradeEntries> units) {
		for (GOTUnitTradeEntries entries : units) {
			for (GOTUnitTradeEntry entry : entries.getTradeEntries()) {
				hireable.add(entry.getEntityClass());
			}
		}
	}

	private static void searchForMinerals(Iterable<GOTBiome> biomes, Collection<String> minerals) {
		for (GOTBiome biome : biomes) {
			Collection<GOTBiomeDecorator.OreGenerant> oreGenerants = new HashSet<>(biome.getDecorator().getBiomeSoils());
			oreGenerants.addAll(biome.getDecorator().getBiomeOres());
			oreGenerants.addAll(biome.getDecorator().getBiomeGems());
			for (GOTBiomeDecorator.OreGenerant oreGenerant : oreGenerants) {
				WorldGenMinable gen = oreGenerant.getOreGen();
				Block block = GOTReflection.getOreGenBlock(gen);
				int meta = GOTReflection.getOreGenMeta(gen);
				if (block instanceof GOTBlockOreGem || block instanceof BlockDirt || block instanceof GOTBlockRock) {
					minerals.add(getBlockMetaName(block, meta));
				} else {
					minerals.add(getBlockName(block));
				}
			}
		}
	}

	private static void searchForPagenamesBiome(Iterable<GOTBiome> biomes, Iterable<GOTFaction> factions) {
		next:
		for (GOTBiome biome : biomes) {
			String preName = getBiomeName(biome);
			for (GOTFaction fac : factions) {
				if (preName.equals(getFactionName(fac))) {
					BIOME_TO_PAGENAME.put(preName, preName + " (" + Lang.PAGE_BIOME + ')');
					continue next;
				}
			}
			for (Class<? extends Entity> entityClass : ENTITY_CLASSES) {
				if (preName.equals(getEntityName(entityClass))) {
					BIOME_TO_PAGENAME.put(preName, preName + " (" + Lang.PAGE_BIOME + ')');
					continue next;
				}
			}
			BIOME_TO_PAGENAME.put(preName, preName);
		}
	}

	private static void searchForPagenamesEntity(Iterable<GOTBiome> biomes, Iterable<GOTFaction> factions) {
		next:
		for (Class<? extends Entity> entityClass : ENTITY_CLASSES) {
			String preName = getEntityName(entityClass);
			for (GOTBiome biome : biomes) {
				if (preName.equals(getBiomeName(biome))) {
					ENTITY_TO_PAGENAME.put(preName, preName + " (" + Lang.PAGE_ENTITY + ')');
					continue next;
				}
			}
			for (GOTFaction fac : factions) {
				if (preName.equals(getFactionName(fac))) {
					ENTITY_TO_PAGENAME.put(preName, preName + " (" + Lang.PAGE_ENTITY + ')');
					continue next;
				}
			}
			ENTITY_TO_PAGENAME.put(preName, preName);
		}
	}

	private static void searchForPagenamesFaction(Iterable<GOTBiome> biomes, Iterable<GOTFaction> factions) {
		next:
		for (GOTFaction fac : factions) {
			String preName = getFactionName(fac);
			for (GOTBiome biome : biomes) {
				if (preName.equals(getBiomeName(biome))) {
					FACTION_TO_PAGENAME.put(preName, preName + " (" + Lang.PAGE_FACTION + ')');
					continue next;
				}
			}
			for (Class<? extends Entity> entityClass : ENTITY_CLASSES) {
				if (preName.equals(getEntityName(entityClass))) {
					FACTION_TO_PAGENAME.put(preName, preName + " (" + Lang.PAGE_FACTION + ')');
					continue next;
				}
			}
			FACTION_TO_PAGENAME.put(preName, preName);
		}
	}

	public enum Lang {
		MOB_NO_LEGENDARY_DROP, PAGE_BIOME, PAGE_FACTION, PAGE_ENTITY, BIOME_HAS_ANIMALS, BIOME_HAS_CONQUEST, BIOME_HAS_INVASIONS, BIOME_HAS_SPAWN, BIOME_HAS_STRUCTURES, BIOME_HAS_TREES, BIOME_HAS_TREES_BIOME_ONLY, BIOME_HAS_WAYPOINTS, BIOME_NO_ACHIEVEMENT, BIOME_NO_ANIMALS, BIOME_NO_CONQUEST, BIOME_NO_INVASIONS, BIOME_NO_SPAWN, BIOME_NO_STRUCTURES, BIOME_NO_TREES, BIOME_NO_VARIANTS, BIOME_NO_WAYPOINTS, BIOME_HAS_MINERALS, BIOME_CONQUEST_ONLY, BIOME_SPAWN_ONLY, FACTION_HAS_BANNERS, FACTION_HAS_CHARS, FACTION_HAS_CONQUEST, FACTION_HAS_INVASION, FACTION_HAS_RANKS, FACTION_HAS_SPAWN, FACTION_HAS_WAR_CRIMES, FACTION_HAS_WAYPOINTS, FACTION_NO_ATTRIBUTES, FACTION_NO_BANNERS, FACTION_NO_CHARS, FACTION_NO_CONQUEST, FACTION_NO_ENEMIES, FACTION_NO_FRIENDS, FACTION_NO_INVASIONS, FACTION_NO_RANKS, FACTION_NO_SPAWN, FACTION_NO_STRUCTURES, FACTION_NO_WAR_CRIMES, FACTION_NO_WAYPOINTS, TREE_HAS_BIOMES, TREE_NO_BIOMES, TREE_VARIANT_ONLY, RIDER, NO_PLEDGE, NEED_PLEDGE, REPUTATION, MINERAL_BIOMES, STRUCTURE_BIOMES, ENTITY_NO_BIOMES, ENTITY_HAS_BIOMES, ENTITY_CONQUEST, ENTITY_INVASION, ENTITY_CONQUEST_INVASION, CATEGORY, CLIMATE_COLD, CLIMATE_COLD_AZ, CLIMATE_NORMAL, CLIMATE_NORMAL_AZ, CLIMATE_SUMMER, CLIMATE_SUMMER_AZ, CLIMATE_WINTER, CLIMATE_NULL, SEASON_WINTER, SEASON_AUTUMN, SEASON_SUMMER, SEASON_SPRING;

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

	private static class Section {
		private final Set<String> items = new TreeSet<>();
		private String desc = "";

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public Set<String> getItems() {
			return items;
		}
	}

	private static String getBannerName(GOTItemBanner.BannerType banner) {
		return StatCollector.translateToLocal("item.got:banner." + banner.getBannerName() + ".name");
	}

	private static String getBiomeLink(GOTBiome biome) {
		String biomeName = getBiomeName(biome);
		String biomePagename = getBiomePagename(biome);
		if (biomeName.equals(biomePagename)) {
			return "[[" + biomeName + "]]";
		}
		return "[[" + biomePagename + '|' + biomeName + "]]";
	}

	private static String getBiomeName(GOTBiome biome) {
		return StatCollector.translateToLocal("got.biome." + biome.biomeName + ".name");
	}

	private static String getBiomePagename(GOTBiome biome) {
		return BIOME_TO_PAGENAME.get(getBiomeName(biome));
	}

	private static String getBiomeVariantName(GOTBiomeVariant variant) {
		return StatCollector.translateToLocal("got.variant." + variant.getVariantName() + ".name");
	}

	private static String getBlockMetaName(Block block, int meta) {
		return StatCollector.translateToLocal(block.getUnlocalizedName() + '.' + meta + ".name");
	}

	private static String getBlockName(Block block) {
		return StatCollector.translateToLocal(block.getUnlocalizedName() + ".name");
	}

	private static String getCapeFilename(GOTCapes cape) {
		return "[[File:Cape " + cape.name().toLowerCase(Locale.ROOT) + ".png]]";
	}

	private static String getEntityLink(Class<? extends Entity> entityClass) {
		if (!GOTEntityRegistry.CLASS_TO_NAME_MAPPING.containsKey(entityClass)) {
			return getEntityVanillaName(entityClass);
		}
		String entityName = getEntityName(entityClass);
		String entityPagename = getEntityPagename(entityClass);
		if (entityName.equals(entityPagename)) {
			return "[[" + entityPagename + "]]";
		}
		return "[[" + entityPagename + '|' + entityName + "]]";
	}

	private static String getEntityName(Class<? extends Entity> entityClass) {
		return StatCollector.translateToLocal("entity.got." + GOTEntityRegistry.CLASS_TO_NAME_MAPPING.get(entityClass) + ".name");
	}

	private static String getEntityVanillaName(Class<? extends Entity> entityClass) {
		return StatCollector.translateToLocal("entity." + EntityList.classToStringMapping.get(entityClass) + ".name");
	}

	private static String getEntityPagename(Class<? extends Entity> entityClass) {
		return ENTITY_TO_PAGENAME.get(getEntityName(entityClass));
	}

	private static String getFactionLink(GOTFaction fac) {
		String facName = getFactionName(fac);
		String facPagename = getFactionPagename(fac);
		if (facName.equals(facPagename)) {
			return "[[" + facPagename + "]]";
		}
		return "[[" + facPagename + '|' + facName + "]]";
	}

	private static String getFactionName(GOTFaction fac) {
		return StatCollector.translateToLocal("got.faction." + fac.codeName() + ".name");
	}

	private static String getFactionPagename(GOTFaction fac) {
		return FACTION_TO_PAGENAME.get(getFactionName(fac));
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
		return "[[" + StatCollector.translateToLocal("got.structure." + GOTStructureRegistry.CLASS_TO_NAME_MAPPING.get(structureClass) + ".name") + "]]";
	}

	private static String getStructureName(Class<? extends WorldGenerator> structureClass) {
		return StatCollector.translateToLocal("got.structure." + GOTStructureRegistry.CLASS_TO_NAME_MAPPING.get(structureClass) + ".name");
	}

	private static String getSettlementName(String nameAlias) {
		return StatCollector.translateToLocal("got.structure." + nameAlias + ".name");
	}

	private static String getTreeName(GOTTreeType tree) {
		return StatCollector.translateToLocal("got.tree." + tree.name().toLowerCase(Locale.ROOT) + ".name");
	}

	private static void appendSection(StringBuilder sb, Section section) {
		sb.append(section.getDesc());

		for (String item : section.getItems()) {
			sb.append(item);
		}

		section.getItems().clear();
	}
}