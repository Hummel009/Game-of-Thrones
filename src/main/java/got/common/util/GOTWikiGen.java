package got.common.util;

import got.common.GOTDate;
import got.common.block.other.GOTBlockOreGem;
import got.common.block.other.GOTBlockRock;
import got.common.command.GOTCommandWikiGen;
import got.common.database.*;
import got.common.entity.other.*;
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
import got.common.world.structure.other.GOTStructureBaseSettlement;
import got.common.world.structure.other.GOTStructureRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GOTWikiGen {
	private static final Map<Class<? extends Entity>, Entity> CLASS_TO_OBJ = new HashMap<>();
	private static final Map<Class<? extends Entity>, GOTWaypoint> CLASS_TO_WP = new HashMap<>();
	private static final Map<String, String> FAC_TO_PAGE = new HashMap<>();
	private static final Map<String, String> ENTITY_TO_PAGE = new HashMap<>();
	private static final Map<String, String> BIOME_TO_PAGE = new HashMap<>();
	private static final Iterable<GOTUnitTradeEntries> UNITS = new HashSet<>(GOTUnitTradeEntries.CONTENT);
	private static final Iterable<GOTAchievement> ACHIEVEMENTS = new HashSet<>(GOTAchievement.CONTENT);
	private static final Collection<GOTBiome> BIOMES = new HashSet<>(GOTBiome.CONTENT);
	private static final Set<GOTFaction> FACTIONS = EnumSet.allOf(GOTFaction.class);
	private static final Set<GOTTreeType> TREES = EnumSet.allOf(GOTTreeType.class);
	private static final Set<GOTWaypoint> WAYPOINTS = EnumSet.allOf(GOTWaypoint.class);
	private static final Set<GOTCapes> CAPES = EnumSet.allOf(GOTCapes.class);
	private static final Set<GOTShields> SHIELDS = EnumSet.allOf(GOTShields.class);
	private static final Collection<String> MINERALS = new HashSet<>();
	private static final Collection<Class<? extends WorldGenerator>> STRUCTURES = new HashSet<>();
	private static final Collection<Class<? extends Entity>> HIREABLE = new HashSet<>();
	private static final String BEGIN = "\n</title><ns>10</ns><revision><text>&lt;includeonly&gt;{{#switch: {{{1}}}";
	private static final String END = "\n}}&lt;/includeonly&gt;&lt;noinclude&gt;[[" + Lang.CATEGORY + "]]&lt;/noinclude&gt;</text></revision></page>";
	private static final String TITLE = "<page><title>";

	static {
		BIOMES.remove(GOTBiome.ocean1);
		BIOMES.remove(GOTBiome.ocean2);
		BIOMES.remove(GOTBiome.ocean3);
		BIOMES.remove(GOTBiome.beachGravel);
		BIOMES.remove(GOTBiome.beachWhite);
		BIOMES.remove(GOTBiome.bleedingBeach);
		CLASS_TO_WP.put(GOTEntityYgritte.class, GOTWaypoint.HARDHOME);
		CLASS_TO_WP.put(GOTEntityTormund.class, GOTWaypoint.HARDHOME);
		CLASS_TO_WP.put(GOTEntityManceRayder.class, GOTWaypoint.HARDHOME);
		CLASS_TO_WP.put(GOTEntityCraster.class, GOTWaypoint.CRASTERS_KEEP);
		CLASS_TO_WP.put(GOTEntityVictarionGreyjoy.class, GOTWaypoint.VICTARION_LANDING);
		CLASS_TO_WP.put(GOTEntityLancelLannister.LancelLannisterNormal.class, GOTWaypoint.KINGS_LANDING);
		CLASS_TO_WP.put(GOTEntityTobhoMott.class, GOTWaypoint.KINGS_LANDING);
		CLASS_TO_WP.put(GOTEntityTyrionLannister.class, GOTWaypoint.KINGS_LANDING);
		CLASS_TO_WP.put(GOTEntityGendryBaratheon.class, GOTWaypoint.KINGS_LANDING);
		CLASS_TO_WP.put(GOTEntityPetyrBaelish.class, GOTWaypoint.KINGS_LANDING);
		CLASS_TO_WP.put(GOTEntityBronn.class, GOTWaypoint.KINGS_LANDING);
		CLASS_TO_WP.put(GOTEntityPodrickPayne.class, GOTWaypoint.KINGS_LANDING);
		CLASS_TO_WP.put(GOTEntityHotPie.class, GOTWaypoint.CROSSROADS_INN);
		CLASS_TO_WP.put(GOTEntityVargoHoat.class, GOTWaypoint.CROSSROADS_INN);
		CLASS_TO_WP.put(GOTEntitySandorClegane.class, GOTWaypoint.KINGS_LANDING);
		CLASS_TO_WP.put(GOTEntityJoffreyBaratheon.class, GOTWaypoint.KINGS_LANDING);
		CLASS_TO_WP.put(GOTEntityCerseiLannister.class, GOTWaypoint.KINGS_LANDING);
		CLASS_TO_WP.put(GOTEntityJaimeLannister.class, GOTWaypoint.KINGS_LANDING);
		CLASS_TO_WP.put(GOTEntityPycelle.class, GOTWaypoint.KINGS_LANDING);
		CLASS_TO_WP.put(GOTEntityJanosSlynt.class, GOTWaypoint.KINGS_LANDING);
		CLASS_TO_WP.put(GOTEntityVarys.class, GOTWaypoint.KINGS_LANDING);
		CLASS_TO_WP.put(GOTEntityIlynPayne.class, GOTWaypoint.KINGS_LANDING);
		CLASS_TO_WP.put(GOTEntityHighSepton.class, GOTWaypoint.KINGS_LANDING);
		CLASS_TO_WP.put(GOTEntityTommenBaratheon.class, GOTWaypoint.KINGS_LANDING);
		CLASS_TO_WP.put(GOTEntityMyrcellaBaratheon.class, GOTWaypoint.KINGS_LANDING);
		CLASS_TO_WP.put(GOTEntityMerynTrant.class, GOTWaypoint.KINGS_LANDING);
		CLASS_TO_WP.put(GOTEntityBarristanSelmy.class, GOTWaypoint.KINGS_LANDING);
		CLASS_TO_WP.put(GOTEntityJeorMormont.class, GOTWaypoint.CASTLE_BLACK);
		CLASS_TO_WP.put(GOTEntityJonSnow.JonSnowLife1.class, GOTWaypoint.CASTLE_BLACK);
		CLASS_TO_WP.put(GOTEntityAemonTargaryen.class, GOTWaypoint.CASTLE_BLACK);
		CLASS_TO_WP.put(GOTEntityAlliserThorne.class, GOTWaypoint.CASTLE_BLACK);
		CLASS_TO_WP.put(GOTEntityEdd.class, GOTWaypoint.CASTLE_BLACK);
		CLASS_TO_WP.put(GOTEntitySamwellTarly.class, GOTWaypoint.CASTLE_BLACK);
		CLASS_TO_WP.put(GOTEntityCotterPyke.class, GOTWaypoint.EASTWATCH);
		CLASS_TO_WP.put(GOTEntityHarmune.class, GOTWaypoint.EASTWATCH);
		CLASS_TO_WP.put(GOTEntityDenysMallister.class, GOTWaypoint.SHADOW_TOWER);
		CLASS_TO_WP.put(GOTEntityMullin.class, GOTWaypoint.SHADOW_TOWER);
	}

	private GOTWikiGen() {
	}

	@SuppressWarnings("deprecation")
	public static void generate(World world, EntityPlayer player) {
		long time = System.nanoTime();
		try {
			searchForEntities(world);
			searchForMinerals(BIOMES, MINERALS);
			searchForStructures(BIOMES, STRUCTURES);
			searchForHireable(HIREABLE, UNITS);
			searchForPagenamesEntity(BIOMES, FACTIONS);
			searchForPagenamesBiome(BIOMES, FACTIONS);
			searchForPagenamesFaction(BIOMES, FACTIONS);
			Files.createDirectories(Paths.get("hummel"));
			if ("tables".equals(GOTCommandWikiGen.getType())) {

				StringBuilder sb = new StringBuilder();
				for (GOTAchievement ach : ACHIEVEMENTS) {
					sb.append("\n| ").append(getAchievementTitle(ach)).append(" || ").append(getAchievementDesc(ach)).append("\n|-");
				}
				PrintWriter fAchievements = new PrintWriter("hummel/achievements.txt", "UTF-8");
				fAchievements.write(sb.toString());
				fAchievements.close();

				sb = new StringBuilder();
				for (GOTShields shield : SHIELDS) {
					sb.append("\n| ").append(shield.getShieldName()).append(" || ").append(shield.getShieldDesc()).append(" || ").append(getShieldFilename(shield)).append("\n|-");
				}
				PrintWriter fShields = new PrintWriter("hummel/shields.txt", "UTF-8");
				fShields.write(sb.toString());
				fShields.close();

				sb = new StringBuilder();
				for (GOTCapes cape : CAPES) {
					sb.append("\n| ").append(cape.getCapeName()).append(" || ").append(cape.getCapeDesc()).append(" || ").append(getCapeFilename(cape)).append("\n|-");
				}
				PrintWriter fCapes = new PrintWriter("hummel/capes.txt", "UTF-8");
				fCapes.write(sb.toString());
				fCapes.close();

				sb = new StringBuilder();
				for (GOTUnitTradeEntries unitTradeEntries : UNITS) {
					for (GOTUnitTradeEntry entry : unitTradeEntries.getTradeEntries()) {
						if (entry != null) {
							sb.append("\n| ").append(getEntityLink(entry.entityClass));
							if (entry.getPledgeType() == GOTUnitTradeEntry.PledgeType.NONE) {
								if (entry.mountClass == null) {
									sb.append(" || {{Coins|").append(entry.initialCost * 2).append("}} || {{Coins|").append(entry.initialCost).append("}} || +").append(entry.alignmentRequired).append(" || -");
								} else {
									sb.append(" || {{Coins|").append(entry.initialCost * 2).append("}} (").append(Lang.RIDER).append(") || {{Coins|").append(entry.initialCost).append("}} || +").append(entry.alignmentRequired).append(" || -");
								}
							} else if (entry.mountClass == null) {
								if (entry.alignmentRequired < 101.0f) {
									sb.append(" || N/A || {{Coins|").append(entry.initialCost).append("}} || +100.0 || +");
								} else {
									sb.append(" || N/A || {{Coins|").append(entry.initialCost).append("}} || +").append(entry.alignmentRequired).append(" || +");
								}
							} else if (entry.alignmentRequired < 101.0f) {
								sb.append(" || N/A || {{Coins|").append(entry.initialCost).append("}} (").append(Lang.RIDER).append(") || +100.0 || +");
							} else {
								sb.append(" || N/A || {{Coins|").append(entry.initialCost).append("}} (").append(Lang.RIDER).append(") || +").append(entry.alignmentRequired).append(" || +");
							}
							sb.append("\n|-");
						}
					}
				}
				PrintWriter fUnits = new PrintWriter("hummel/units.txt", "UTF-8");
				fUnits.write(sb.toString());
				fUnits.close();

				sb = new StringBuilder();
				for (GOTWaypoint wp : WAYPOINTS) {
					sb.append("\n| ").append(wp.getDisplayName()).append(" || ").append(wp.getLoreText(player)).append("\n|-");
				}
				PrintWriter fWaypoints = new PrintWriter("hummel/waypoints.txt", "UTF-8");
				fWaypoints.write(sb.toString());
				fWaypoints.close();

				sb = new StringBuilder();
				for (Item item : GOTItems.CONTENT) {
					if (item instanceof ItemArmor) {
						float damage = ((ItemArmor) item).damageReduceAmount;
						ItemArmor.ArmorMaterial material = ((ItemArmor) item).getArmorMaterial();
						sb.append("\n| ").append(getItemName(item)).append(" || ").append(getItemFilename(item)).append(" || ").append(item.getMaxDamage()).append(" || ").append(damage).append(" || ");
						if (material == null || material.customCraftingMaterial == null) {
							sb.append("N/A");
						} else {
							sb.append(getItemName(material.customCraftingMaterial));
						}
						sb.append("\n|-");
					}
				}
				PrintWriter fArmor = new PrintWriter("hummel/armor.txt", "UTF-8");
				fArmor.write(sb.toString());
				fArmor.close();

				sb = new StringBuilder();
				for (Item item : GOTItems.CONTENT) {
					if (item instanceof ItemSword) {
						float damage = GOTReflection.getDamageAmount(item);
						Item.ToolMaterial material = GOTReflection.getToolMaterial(item);
						sb.append("\n| ").append(getItemName(item)).append(" || ").append(getItemFilename(item)).append(" || ").append(item.getMaxDamage()).append(" || ").append(damage).append(" || ");
						if (material == null || material.getRepairItemStack() == null) {
							sb.append("N/A");
						} else {
							sb.append(getItemName(material.getRepairItemStack().getItem()));
						}
						sb.append("\n|-");
					}
				}
				PrintWriter fWeapon = new PrintWriter("hummel/weapon.txt", "UTF-8");
				fWeapon.write(sb.toString());
				fWeapon.close();

				sb = new StringBuilder();
				for (Item item : GOTItems.CONTENT) {
					if (item instanceof ItemFood) {
						int heal = ((ItemFood) item).func_150905_g(null);
						float saturation = ((ItemFood) item).func_150906_h(null);
						sb.append("\n| ").append(getItemName(item)).append(" || ").append(getItemFilename(item)).append(" || ").append("{{Bar|bread|").append(new DecimalFormat("#.##").format(saturation * heal * 2)).append("}} || {{Bar|food|").append(heal).append("}} || ").append(item.getItemStackLimit()).append("\n|-");
					}
				}
				PrintWriter fFood = new PrintWriter("hummel/food.txt", "UTF-8");
				fFood.write(sb.toString());
				fFood.close();

			} else if ("xml".equals(GOTCommandWikiGen.getType())) {
				StringBuilder sb = new StringBuilder();

				sb.append("\n<mediawiki xmlns=\"http://www.mediawiki.org/xml/export-0.11/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.mediawiki.org/xml/export-0.11/ http://www.mediawiki.org/xml/export-0.11.xsd\" version=\"0.11\" xml:lang=\"ru\">");

				/* ALL PAGES */

				File file = new File("hummel/sitemap.txt");
				if (!file.exists()) {
					boolean created = file.createNewFile();
					if (!created) {
						GOTLog.getLogger().info("DatabaseGenerator: file wasn't created");
					}
				}
				Set<String> sitemap;
				Collection<String> neededPages = new HashSet<>();
				try (Stream<String> lines = Files.lines(Paths.get("hummel/sitemap.txt"))) {
					sitemap = lines.collect(Collectors.toSet());
				}

				for (String pageName : MINERALS) {
					neededPages.add(pageName);
					if (!sitemap.contains(pageName)) {
						String s2 = "</title><revision><text>{{Статья Ископаемое}}</text></revision></page>\n";
						sb.append(TITLE).append(pageName).append(s2);
					}
				}

				for (Class<? extends Entity> entityClass : CLASS_TO_OBJ.keySet()) {
					String pageName = getEntityPagename(entityClass);
					neededPages.add(pageName);
					if (!sitemap.contains(pageName)) {
						String s2 = "</title><revision><text>{{Статья Моб}}</text></revision></page>\n";
						sb.append(TITLE).append(pageName).append(s2);
					}
				}

				for (GOTBiome biome : BIOMES) {
					String pageName = getBiomePagename(biome);
					neededPages.add(pageName);
					if (!sitemap.contains(pageName)) {
						String s2 = "</title><revision><text>{{Статья Биом}}</text></revision></page>\n";
						sb.append(TITLE).append(pageName).append(s2);
					}
				}

				for (GOTFaction fac : FACTIONS) {
					String pageName = getFactionPagename(fac);
					neededPages.add(pageName);
					if (!sitemap.contains(pageName)) {
						String s2 = "</title><revision><text>{{Статья Фракция}}</text></revision></page>\n";
						sb.append(TITLE).append(pageName).append(s2);
					}
				}

				for (GOTTreeType tree : TREES) {
					String pageName = getTreeName(tree);
					neededPages.add(pageName);
					if (!sitemap.contains(pageName)) {
						String s2 = "</title><revision><text>{{Статья Дерево}}</text></revision></page>\n";
						sb.append(TITLE).append(pageName).append(s2);
					}
				}

				for (Class<? extends WorldGenerator> strClass : STRUCTURES) {
					String pageName = getStructureName(strClass);
					neededPages.add(pageName);
					if (!sitemap.contains(pageName)) {
						String s2 = "</title><revision><text>{{Статья Структура}}</text></revision></page>\n";
						sb.append(TITLE).append(pageName).append(s2);
					}
				}

				StringBuilder del = new StringBuilder();
				for (String existing : sitemap) {
					if (!neededPages.contains(existing)) {
						del.append(existing).append('\n');
					}
				}
				PrintWriter removal = new PrintWriter("hummel/removal.txt", "UTF-8");
				removal.write(del.toString());
				removal.close();

				/* STRUCTURES */

				sb.append(TITLE).append("Template:DB Structure-Biomes");
				sb.append(BEGIN);
				for (Class<? extends WorldGenerator> strClass : STRUCTURES) {
					sb.append("\n| ").append(getStructureName(strClass)).append(" = ").append(Lang.STRUCTURE_BIOMES);
					next:
					for (GOTBiome biome : BIOMES) {
						for (GOTBiomeDecorator.Structure structure : biome.getDecorator().getStructures()) {
							if (structure.getStructureGen().getClass() == strClass) {
								sb.append("\n* ").append(getBiomeLink(biome)).append(';');
								continue next;
							}
						}
					}
				}
				sb.append(END);

				/* MINERALS */

				sb.append(TITLE).append("Template:DB Mineral-Biomes");
				sb.append(BEGIN);
				for (String mineral : MINERALS) {
					sb.append("\n| ").append(mineral).append(" = ").append(Lang.MINERAL_BIOMES);
					next:
					for (GOTBiome biome : BIOMES) {
						Collection<GOTBiomeDecorator.OreGenerant> oreGenerants = new ArrayList<>(biome.getDecorator().getBiomeSoils());
						oreGenerants.addAll(biome.getDecorator().getBiomeOres());
						oreGenerants.addAll(biome.getDecorator().getBiomeGems());
						for (GOTBiomeDecorator.OreGenerant oreGenerant : oreGenerants) {
							Block block = GOTReflection.getOreBlock(oreGenerant.getOreGen());
							int meta = GOTReflection.getOreMeta(oreGenerant.getOreGen());
							if (getBlockMetaName(block, meta).equals(mineral) || getBlockName(block).equals(mineral)) {
								sb.append("\n* ").append(getBiomeLink(biome)).append(" (").append(oreGenerant.getOreChance()).append("%; Y: ").append(oreGenerant.getMinHeight()).append('-').append(oreGenerant.getMaxHeight()).append(");");
								continue next;
							}
						}
					}
				}
				sb.append(END);

				/* TREES */

				sb.append(TITLE).append("Template:DB Tree-Biomes");
				sb.append(BEGIN);
				for (GOTTreeType tree : TREES) {
					Collection<GOTBiome> biomesTree = new HashSet<>();
					Collection<GOTBiome> biomesVariantTree = new HashSet<>();
					next:
					for (GOTBiome biome : BIOMES) {
						for (GOTTreeType.WeightedTreeType weightedTreeType : biome.getDecorator().getTreeTypes()) {
							if (weightedTreeType.getTreeType() == tree) {
								biomesTree.add(biome);
								continue next;
							}
						}
						for (GOTBiomeVariantList.VariantBucket variantBucket : biome.getBiomeVariants().getVariantList()) {
							for (GOTTreeType.WeightedTreeType weightedTreeType : variantBucket.getVariant().getTreeTypes()) {
								if (weightedTreeType.getTreeType() == tree) {
									biomesVariantTree.add(biome);
									continue next;
								}
							}
						}
					}
					sb.append("\n| ").append(getTreeName(tree)).append(" = ");
					if (biomesTree.isEmpty() && biomesVariantTree.isEmpty()) {
						sb.append(Lang.TREE_NO_BIOMES);
					} else {
						sb.append(Lang.TREE_HAS_BIOMES);
					}
					for (GOTBiome biome : biomesTree) {
						sb.append("\n* ").append(getBiomeLink(biome)).append(';');
					}
					for (GOTBiome biome : biomesVariantTree) {
						sb.append("\n* ").append(getBiomeLink(biome)).append(" (").append(Lang.TREE_VARIANT_ONLY).append(");");
					}
				}
				sb.append(END);

				/* BIOMES */

				sb.append(TITLE).append("Template:DB Biome-SpawnNPC");
				sb.append(BEGIN);
				for (GOTBiome biome : BIOMES) {
					List<GOTFactionContainer> facContainers = biome.getNpcSpawnList().getFactionContainers();
					if (facContainers.isEmpty()) {
						sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(Lang.BIOME_NO_SPAWN);
					} else {
						Collection<GOTFactionContainer> spawnContainers = new ArrayList<>();
						for (GOTFactionContainer facContainer : facContainers) {
							if (facContainer.getBaseWeight() > 0) {
								spawnContainers.add(facContainer);
							}
						}
						sb.append("\n| ").append(getBiomePagename(biome)).append(" = ");
						if (spawnContainers.isEmpty()) {
							sb.append(Lang.BIOME_CONQUEST_ONLY);
						} else {
							sb.append(Lang.BIOME_HAS_SPAWN);
							for (GOTFactionContainer facContainer : spawnContainers) {
								for (GOTSpawnListContainer container : facContainer.getSpawnLists()) {
									for (GOTSpawnEntry entry : container.getSpawnList().getSpawnEntries()) {
										sb.append("\n* ").append(getEntityLink(entry.entityClass)).append("; ");
									}
								}
							}
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Biome-ConquestNPC");
				sb.append(BEGIN);
				for (GOTBiome biome : BIOMES) {
					List<GOTFactionContainer> facContainers = biome.getNpcSpawnList().getFactionContainers();
					if (facContainers.isEmpty()) {
						sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(Lang.BIOME_NO_CONQUEST);
					} else {
						Collection<GOTFactionContainer> conqestContainers = new ArrayList<>();
						for (GOTFactionContainer facContainer : facContainers) {
							if (facContainer.getBaseWeight() <= 0) {
								conqestContainers.add(facContainer);
							}
						}
						sb.append("\n| ").append(getBiomePagename(biome)).append(" = ");
						if (conqestContainers.isEmpty()) {
							sb.append(Lang.BIOME_SPAWN_ONLY);
						} else {
							sb.append(Lang.BIOME_HAS_CONQUEST);
							EnumSet<GOTFaction> conquestFactions = EnumSet.noneOf(GOTFaction.class);
							for (GOTFactionContainer facContainer : conqestContainers) {
								next:
								for (GOTSpawnListContainer container : facContainer.getSpawnLists()) {
									for (GOTSpawnEntry entry : container.getSpawnList().getSpawnEntries()) {
										Entity entity = CLASS_TO_OBJ.get(entry.entityClass);
										if (entity instanceof GOTEntityNPC) {
											GOTFaction fac = ((GOTEntityNPC) entity).getFaction();
											conquestFactions.add(fac);
											continue next;
										}
									}
								}
							}
							for (GOTFaction fac : conquestFactions) {
								sb.append("\n* ").append(getFactionLink(fac)).append("; ");
							}
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Biome-Temperature");
				sb.append(BEGIN);
				GOTDate.Season season = GOTDate.AegonCalendar.getSeason();
				for (GOTBiome biome : BIOMES) {
					sb.append("\n| ").append(getBiomePagename(biome)).append(" = ");
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

				sb.append(TITLE).append("Template:DB Biome-Rainfall");
				sb.append(BEGIN);
				for (GOTBiome biome : BIOMES) {
					sb.append("\n| ").append(getBiomePagename(biome)).append(" = ");
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
				GOTDate.AegonCalendar.getDate().getMonth().setSeason(season);
				sb.append(END);

				sb.append(TITLE).append("Template:DB Biome-Climate");
				sb.append(BEGIN);
				for (GOTBiome biome : BIOMES) {
					sb.append("\n| ").append(getBiomePagename(biome)).append(" = ");
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

				sb.append(TITLE).append("Template:DB Biome-Variants");
				sb.append(BEGIN);
				for (GOTBiome biome : BIOMES) {
					sb.append("\n| ").append(getBiomePagename(biome)).append(" = ");
					if (biome.getBiomeVariants().getVariantList().isEmpty()) {
						sb.append(Lang.BIOME_NO_VARIANTS);
					} else {
						for (GOTBiomeVariantList.VariantBucket variantBucket : biome.getBiomeVariants().getVariantList()) {
							sb.append("\n* ").append(getBiomeVariantName(variantBucket.getVariant())).append(';');
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Biome-Invasions");
				sb.append(BEGIN);
				for (GOTBiome biome : BIOMES) {
					sb.append("\n| ").append(getBiomePagename(biome)).append(" = ");
					if (biome.getInvasionSpawns().getRegisteredInvasions().isEmpty()) {
						sb.append(Lang.BIOME_NO_INVASIONS);
					} else {
						sb.append(Lang.BIOME_HAS_INVASIONS);
						EnumSet<GOTFaction> invasionFactions = EnumSet.noneOf(GOTFaction.class);
						next:
						for (GOTInvasions invasion : biome.getInvasionSpawns().getRegisteredInvasions()) {
							for (GOTInvasions.InvasionSpawnEntry entry : invasion.getInvasionMobs()) {
								Entity entity = CLASS_TO_OBJ.get(entry.getEntityClass());
								if (entity instanceof GOTEntityNPC) {
									GOTFaction fac = ((GOTEntityNPC) entity).getFaction();
									invasionFactions.add(fac);
									continue next;
								}
							}
						}
						for (GOTFaction fac : invasionFactions) {
							sb.append("\n* ").append(getFactionLink(fac)).append(';');
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Biome-Waypoints");
				sb.append(BEGIN);
				for (GOTBiome biome : BIOMES) {
					GOTWaypoint.Region region = biome.getBiomeWaypoints();
					sb.append("\n| ").append(getBiomePagename(biome)).append(" = ");
					if (region == null) {
						sb.append(Lang.BIOME_NO_WAYPOINTS);
					} else {
						sb.append(Lang.BIOME_HAS_WAYPOINTS);
						for (GOTWaypoint wp : WAYPOINTS) {
							if (wp.getRegion() == region) {
								sb.append("\n* ").append(wp.getDisplayName()).append(" (").append(getFactionLink(wp.getFaction())).append(");");
							}
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Biome-Achievement");
				sb.append(BEGIN);
				for (GOTBiome biome : BIOMES) {
					GOTAchievement ach = biome.getBiomeAchievement();
					sb.append("\n| ").append(getBiomePagename(biome)).append(" = ");
					if (ach == null) {
						sb.append(Lang.BIOME_NO_ACHIEVEMENT);
					} else {
						sb.append('"').append(getAchievementTitle(ach)).append('"');
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Biome-Trees");
				sb.append(BEGIN);
				for (GOTBiome biome : BIOMES) {
					EnumSet<GOTTreeType> trees = EnumSet.noneOf(GOTTreeType.class);
					Map<GOTTreeType, GOTBiomeVariant> additionalTrees = new EnumMap<>(GOTTreeType.class);
					for (GOTTreeType.WeightedTreeType weightedTreeType : biome.getDecorator().getTreeTypes()) {
						trees.add(weightedTreeType.getTreeType());
					}
					for (GOTBiomeVariantList.VariantBucket variantBucket : biome.getBiomeVariants().getVariantList()) {
						for (GOTTreeType.WeightedTreeType weightedTreeType : variantBucket.getVariant().getTreeTypes()) {
							if (!trees.contains(weightedTreeType.getTreeType())) {
								additionalTrees.put(weightedTreeType.getTreeType(), variantBucket.getVariant());
							}
						}
					}
					sb.append("\n| ").append(getBiomePagename(biome)).append(" = ");
					if (trees.isEmpty() && additionalTrees.isEmpty()) {
						sb.append(Lang.BIOME_NO_TREES);
					} else {
						if (additionalTrees.isEmpty()) {
							sb.append(Lang.BIOME_HAS_TREES_BIOME_ONLY);
						} else {
							sb.append(Lang.BIOME_HAS_TREES);
						}
						for (GOTTreeType tree : trees) {
							sb.append("\n* [[").append(getTreeName(tree)).append("]];");
						}
						for (Map.Entry<GOTTreeType, GOTBiomeVariant> tree : additionalTrees.entrySet()) {
							sb.append("\n* [[").append(getTreeName(tree.getKey())).append("]] (").append(getBiomeVariantName(tree.getValue()).toLowerCase(Locale.ROOT)).append(')').append(';');
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Biome-Mobs");
				sb.append(BEGIN);
				for (GOTBiome biome : BIOMES) {
					Collection<BiomeGenBase.SpawnListEntry> entries = new ArrayList<>(biome.getSpawnableList(EnumCreatureType.ambient));
					entries.addAll(biome.getSpawnableList(EnumCreatureType.waterCreature));
					entries.addAll(biome.getSpawnableList(EnumCreatureType.creature));
					entries.addAll(biome.getSpawnableList(EnumCreatureType.monster));
					entries.addAll(biome.getSpawnableList(GOTBiome.CREATURE_TYPE_GOT_AMBIENT));
					sb.append("\n| ").append(getBiomePagename(biome)).append(" = ");
					if (entries.isEmpty()) {
						sb.append(Lang.BIOME_NO_ANIMALS);
					} else {
						sb.append(Lang.BIOME_HAS_ANIMALS);
						for (BiomeGenBase.SpawnListEntry entry : entries) {
							if (GOTEntityRegistry.CLASS_TO_NAME_MAPPING.containsKey(entry.entityClass)) {
								sb.append("\n* ").append(getEntityLink(entry.entityClass)).append(';');
							} else {
								sb.append("\n* ").append(getEntityVanillaName(entry.entityClass)).append(';');
							}
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Biome-Minerals");
				sb.append(BEGIN);
				for (GOTBiome biome : BIOMES) {
					sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(Lang.BIOME_HAS_MINERALS);
					Collection<GOTBiomeDecorator.OreGenerant> oreGenerants = new ArrayList<>(biome.getDecorator().getBiomeSoils());
					oreGenerants.addAll(biome.getDecorator().getBiomeOres());
					oreGenerants.addAll(biome.getDecorator().getBiomeGems());
					for (GOTBiomeDecorator.OreGenerant oreGenerant : oreGenerants) {
						Block block = GOTReflection.getOreBlock(oreGenerant.getOreGen());
						int meta = GOTReflection.getOreMeta(oreGenerant.getOreGen());
						if (block instanceof GOTBlockOreGem || block instanceof BlockDirt || block instanceof GOTBlockRock) {
							sb.append("\n* [[").append(getBlockMetaName(block, meta)).append("]] (").append(oreGenerant.getOreChance()).append("%; Y: ").append(oreGenerant.getMinHeight()).append('-').append(oreGenerant.getMaxHeight()).append(");");
						} else {
							sb.append("\n* [[").append(getBlockName(block)).append("]] (").append(oreGenerant.getOreChance()).append("%; Y: ").append(oreGenerant.getMinHeight()).append('-').append(oreGenerant.getMaxHeight()).append(");");
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Biome-Music");
				sb.append(BEGIN);
				for (GOTBiome biome : BIOMES) {
					sb.append("\n| ").append(getBiomePagename(biome)).append(" = ");
					if (biome.getBiomeMusic() == null) {
						sb.append("N/A");
					} else {
						sb.append(biome.getBiomeMusic().getSubregion());
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Biome-Structures");
				sb.append(BEGIN);
				for (GOTBiome biome : BIOMES) {
					sb.append("\n| ").append(getBiomePagename(biome)).append(" = ");
					if (biome.getDecorator().getStructures().isEmpty()) {
						sb.append(Lang.BIOME_NO_STRUCTURES);
					} else {
						sb.append(Lang.BIOME_HAS_STRUCTURES);
						for (GOTBiomeDecorator.Structure structure : biome.getDecorator().getStructures()) {
							sb.append("\n* [[").append(getStructureName(structure.getStructureGen().getClass())).append("]];");
						}
					}
				}
				sb.append(END);

				/* FACTIONS */

				sb.append(TITLE).append("Template:DB Faction-NPC");
				sb.append(BEGIN);
				for (GOTFaction fac : FACTIONS) {
					sb.append("\n| ").append(getFactionPagename(fac)).append(" = ");
					for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
						Entity entity = entityEntry.getValue();
						if (entity instanceof GOTEntityNPC && ((GOTEntityNPC) entity).getFaction() == fac && !((GOTEntityNPC) entity).isLegendaryNPC()) {
							sb.append("\n* ").append(getEntityLink(entityEntry.getKey())).append(';');
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Faction-Invasions");
				sb.append(BEGIN);
				for (GOTFaction fac : FACTIONS) {
					Collection<GOTBiome> invasionBiomes = new HashSet<>();
					next:
					for (GOTBiome biome : BIOMES) {
						for (GOTInvasions invasion : biome.getInvasionSpawns().getRegisteredInvasions()) {
							for (GOTInvasions.InvasionSpawnEntry entry : invasion.getInvasionMobs()) {
								Entity entity = CLASS_TO_OBJ.get(entry.getEntityClass());
								if (entity instanceof GOTEntityNPC && fac == ((GOTEntityNPC) entity).getFaction()) {
									invasionBiomes.add(biome);
									continue next;
								}
							}
						}
					}
					sb.append("\n| ").append(getFactionPagename(fac)).append(" = ");
					if (invasionBiomes.isEmpty()) {
						sb.append(Lang.FACTION_NO_INVASIONS);
					} else {
						sb.append(Lang.FACTION_HAS_INVASION);
						for (GOTBiome biome : invasionBiomes) {
							sb.append("\n* ").append(getBiomeLink(biome)).append(';');
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Faction-Spawn");
				sb.append(BEGIN);
				for (GOTFaction fac : FACTIONS) {
					Collection<GOTBiome> spawnBiomes = new HashSet<>();
					next:
					for (GOTBiome biome : BIOMES) {
						List<GOTFactionContainer> facContainers = biome.getNpcSpawnList().getFactionContainers();
						if (!facContainers.isEmpty()) {
							Collection<GOTFactionContainer> spawnContainers = new ArrayList<>();
							for (GOTFactionContainer facContainer : facContainers) {
								if (facContainer.getBaseWeight() > 0) {
									spawnContainers.add(facContainer);
								}
							}
							if (!spawnContainers.isEmpty()) {
								for (GOTFactionContainer facContainer : spawnContainers) {
									for (GOTSpawnListContainer container : facContainer.getSpawnLists()) {
										for (GOTSpawnEntry entry : container.getSpawnList().getSpawnEntries()) {
											Entity entity = CLASS_TO_OBJ.get(entry.entityClass);
											if (entity instanceof GOTEntityNPC && ((GOTEntityNPC) entity).getFaction() == fac) {
												spawnBiomes.add(biome);
												continue next;
											}
										}
									}
								}
							}
						}
					}
					sb.append("\n| ").append(getFactionPagename(fac)).append(" = ");
					if (spawnBiomes.isEmpty()) {
						sb.append(Lang.FACTION_NO_SPAWN);
					} else {
						sb.append(Lang.FACTION_HAS_SPAWN);
						for (GOTBiome biome : spawnBiomes) {
							sb.append("\n* ").append(getBiomeLink(biome)).append(';');
						}
					}

				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Faction-Conquest");
				sb.append(BEGIN);
				for (GOTFaction fac : FACTIONS) {
					Collection<GOTBiome> conquestBiomes = new HashSet<>();
					next:
					for (GOTBiome biome : BIOMES) {
						List<GOTFactionContainer> facContainers = biome.getNpcSpawnList().getFactionContainers();
						if (!facContainers.isEmpty()) {
							Collection<GOTFactionContainer> conquestContainers = new ArrayList<>();
							for (GOTFactionContainer facContainer : facContainers) {
								if (facContainer.getBaseWeight() <= 0) {
									conquestContainers.add(facContainer);
								}
							}
							if (!conquestContainers.isEmpty()) {
								for (GOTFactionContainer facContainer : conquestContainers) {
									for (GOTSpawnListContainer container : facContainer.getSpawnLists()) {
										for (GOTSpawnEntry entry : container.getSpawnList().getSpawnEntries()) {
											Entity entity = CLASS_TO_OBJ.get(entry.entityClass);
											if (entity instanceof GOTEntityNPC && ((GOTEntityNPC) entity).getFaction() == fac) {
												conquestBiomes.add(biome);
												continue next;
											}
										}
									}
								}
							}
						}
					}
					sb.append("\n| ").append(getFactionPagename(fac)).append(" = ");
					if (conquestBiomes.isEmpty()) {
						sb.append(Lang.FACTION_NO_CONQUEST);
					} else {
						sb.append(Lang.FACTION_HAS_CONQUEST);
						for (GOTBiome biome : conquestBiomes) {
							sb.append("\n* ").append(getBiomeLink(biome)).append(';');
						}
					}

				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Faction-Ranks");
				sb.append(BEGIN);
				for (GOTFaction fac : FACTIONS) {
					sb.append("\n| ").append(getFactionPagename(fac)).append(" = ");
					if (fac.getRanksSortedDescending().isEmpty()) {
						sb.append(Lang.FACTION_NO_RANKS);
					} else {
						sb.append(Lang.FACTION_HAS_RANKS);
						for (GOTFactionRank rank : fac.getRanksSortedDescending()) {
							sb.append("\n* ").append(rank.getDisplayFullName()).append('/').append(rank.getDisplayFullNameFem()).append(" (+").append(rank.getAlignment()).append(");");
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Faction-Banners");
				sb.append(BEGIN);
				for (GOTFaction fac : FACTIONS) {
					sb.append("\n| ").append(getFactionPagename(fac)).append(" = ");
					if (fac.getFactionBanners().isEmpty()) {
						sb.append(Lang.FACTION_NO_BANNERS);
					} else {
						sb.append(Lang.FACTION_HAS_BANNERS);
						for (GOTItemBanner.BannerType banner : fac.getFactionBanners()) {
							sb.append("\n* ").append(getBannerName(banner)).append(';');
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Faction-Waypoints");
				sb.append(BEGIN);
				for (GOTFaction fac : FACTIONS) {
					Collection<GOTWaypoint> facWaypoints = new ArrayList<>();
					for (GOTWaypoint wp : WAYPOINTS) {
						if (wp.getFaction() == fac) {
							facWaypoints.add(wp);
						}
					}
					sb.append("\n| ").append(getFactionPagename(fac)).append(" = ");
					if (facWaypoints.isEmpty()) {
						sb.append(Lang.FACTION_NO_WAYPOINTS);
					} else {
						sb.append(Lang.FACTION_HAS_WAYPOINTS);
						for (GOTWaypoint wp : facWaypoints) {
							sb.append("\n* ").append(wp.getDisplayName()).append(';');
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Faction-Attr");
				sb.append(BEGIN);
				for (GOTFaction fac : FACTIONS) {
					Collection<GOTCapes> facCapes = new ArrayList<>();
					Collection<GOTShields> facShields = new ArrayList<>();
					for (GOTCapes cape : CAPES) {
						if (cape.getAlignmentFaction() == fac) {
							facCapes.add(cape);
						}
					}
					for (GOTShields shield : SHIELDS) {
						if (shield.getAlignmentFaction() == fac) {
							facShields.add(shield);
						}
					}

					sb.append("\n| ").append(getFactionPagename(fac)).append(" = ");
					if (facCapes.isEmpty() && facShields.isEmpty()) {
						sb.append(Lang.FACTION_NO_ATTRIBUTES);
					} else {
						sb.append("\n&lt;table class=\"wikitable shields\"&gt;");
						for (GOTCapes cape : facCapes) {
							sb.append("\n&lt;tr&gt;&lt;td&gt;").append(cape.getCapeName()).append("&lt;/td&gt;&lt;td&gt;").append(cape.getCapeDesc()).append("&lt;/td&gt;&lt;td&gt;").append(getCapeFilename(cape)).append("&lt;/td&gt;&lt;/tr&gt;");
						}
						for (GOTShields shield : facShields) {
							sb.append("\n&lt;tr&gt;&lt;td&gt;").append(shield.getShieldName()).append("&lt;/td&gt;&lt;td&gt;").append(shield.getShieldDesc()).append("&lt;/td&gt;&lt;td&gt;").append(getShieldFilename(shield)).append("&lt;/td&gt;&lt;/tr&gt;");
						}
						sb.append("\n&lt;table&gt;");
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Faction-Pledge");
				sb.append(BEGIN);
				for (GOTFaction fac : FACTIONS) {
					if (fac.getPledgeRank() != null) {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(fac.getPledgeRank().getDisplayName()).append('/').append(fac.getPledgeRank().getDisplayNameFem()).append(" (+").append(fac.getPledgeAlignment()).append(')');
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Faction-Chars");
				sb.append(BEGIN);
				for (GOTFaction fac : FACTIONS) {
					Collection<Class<? extends Entity>> facCharacters = new ArrayList<>();
					for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
						Entity entity = entityEntry.getValue();
						if (entity instanceof GOTEntityNPC && ((GOTEntityNPC) entity).getFaction() == fac && ((GOTEntityNPC) entity).isLegendaryNPC()) {
							facCharacters.add(entityEntry.getKey());
						}
					}
					sb.append("\n| ").append(getFactionPagename(fac)).append(" = ");
					if (facCharacters.isEmpty()) {
						sb.append(Lang.FACTION_NO_CHARS);
					} else {
						sb.append(Lang.FACTION_HAS_CHARS);
						for (Class<? extends Entity> entityClass : facCharacters) {
							sb.append("\n* ").append(getEntityLink(entityClass)).append(';');
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Faction-Enemies");
				sb.append(BEGIN);
				for (GOTFaction fac1 : FACTIONS) {
					Collection<GOTFaction> facEnemies = new ArrayList<>();
					for (GOTFaction fac2 : FACTIONS) {
						if (fac1.isBadRelation(fac2) && fac1 != fac2) {
							facEnemies.add(fac2);
						}
					}
					sb.append("\n| ").append(getFactionPagename(fac1)).append(" = ");
					if (facEnemies.isEmpty()) {
						sb.append(Lang.FACTION_NO_ENEMIES);
					} else {
						boolean first = true;
						for (GOTFaction fac : facEnemies) {
							if (first) {
								first = false;
							} else {
								sb.append(" • ");
							}
							sb.append(getFactionLink(fac));
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Faction-Friends");
				sb.append(BEGIN);
				for (GOTFaction fac1 : FACTIONS) {
					Collection<GOTFaction> facFriends = new ArrayList<>();
					for (GOTFaction fac2 : FACTIONS) {
						if (fac1.isGoodRelation(fac2) && fac1 != fac2) {
							facFriends.add(fac2);
						}
					}
					sb.append("\n| ").append(getFactionPagename(fac1)).append(" = ");
					if (facFriends.isEmpty()) {
						sb.append(Lang.FACTION_NO_FRIENDS);
					} else {
						boolean first = true;
						for (GOTFaction fac : facFriends) {
							if (first) {
								first = false;
							} else {
								sb.append(" • ");
							}
							sb.append(getFactionLink(fac));
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Faction-WarCrimes");
				sb.append(BEGIN);
				sb.append("\n| #default = ").append(Lang.FACTION_NO_WAR_CRIMES);
				for (GOTFaction fac : FACTIONS) {
					if (fac.isApprovesWarCrimes()) {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(Lang.FACTION_HAS_WAR_CRIMES);
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Faction-Codename");
				sb.append(BEGIN);
				for (GOTFaction fac : FACTIONS) {
					sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(fac.codeName());
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Faction-Name");
				sb.append(BEGIN);
				for (GOTFaction fac : FACTIONS) {
					sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(getFactionName(fac));
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Faction-Region");
				sb.append(BEGIN);
				for (GOTFaction fac : FACTIONS) {
					sb.append("\n| ").append(getFactionPagename(fac)).append(" = ");
					if (fac.getFactionRegion() == null) {
						sb.append("N/A");
					} else {
						sb.append(fac.getFactionRegion().getRegionName());
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Faction-Structures");
				sb.append(BEGIN);
				for (GOTFaction fac : FACTIONS) {
					Collection<Class<? extends WorldGenerator>> facStructures = new ArrayList<>();
					for (Map.Entry<Class<? extends WorldGenerator>, GOTFaction> entry : GOTStructureRegistry.CLASS_TO_FACTION_MAPPING.entrySet()) {
						if (entry.getValue() == fac) {
							facStructures.add(entry.getKey());
						}
					}
					sb.append("\n| ").append(getFactionPagename(fac)).append(" = ");
					if (facStructures.isEmpty()) {
						sb.append(Lang.FACTION_NO_STRUCTURES);
					} else {
						for (Class<? extends WorldGenerator> str : facStructures) {
							sb.append("\n* ").append(getStructureName(str)).append(';');
						}
					}
				}
				sb.append(END);

				/* MOBS */

				sb.append(TITLE).append("Template:DB Mob-Spawn");
				sb.append(BEGIN);
				for (Class<? extends Entity> entityClass : CLASS_TO_OBJ.keySet()) {
					Collection<GOTBiome> spawnBiomes = new HashSet<>();
					Collection<GOTBiome> conquestBiomes = new HashSet<>();
					Collection<GOTBiome> invasionBiomes = new HashSet<>();
					Collection<GOTBiome> unnaturalBiomes = new HashSet<>();
					next:
					for (GOTBiome biome : BIOMES) {
						Collection<BiomeGenBase.SpawnListEntry> spawnEntries = new ArrayList<>();
						Collection<BiomeGenBase.SpawnListEntry> conquestEntries = new ArrayList<>();
						Collection<GOTInvasions.InvasionSpawnEntry> invasionEntries = new ArrayList<>();
						spawnEntries.addAll(biome.getSpawnableList(EnumCreatureType.ambient));
						spawnEntries.addAll(biome.getSpawnableList(EnumCreatureType.waterCreature));
						spawnEntries.addAll(biome.getSpawnableList(EnumCreatureType.creature));
						spawnEntries.addAll(biome.getSpawnableList(EnumCreatureType.monster));
						spawnEntries.addAll(biome.getSpawnableGOTAmbientList());
						for (GOTFactionContainer facContainer : biome.getNpcSpawnList().getFactionContainers()) {
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
							if (entry.entityClass == entityClass) {
								spawnBiomes.add(biome);
								continue next;
							}
						}
						for (BiomeGenBase.SpawnListEntry entry : conquestEntries) {
							if (entry.entityClass == entityClass) {
								conquestBiomes.add(biome);
								unnaturalBiomes.add(biome);
								break;
							}
						}
						for (GOTInvasions.InvasionSpawnEntry entry : invasionEntries) {
							if (entry.getEntityClass() == entityClass) {
								invasionBiomes.add(biome);
								unnaturalBiomes.add(biome);
								break;
							}
						}
					}
					sb.append("\n| ").append(getEntityPagename(entityClass)).append(" = ");
					//noinspection StreamToLoop
					if (Stream.of(spawnBiomes, conquestBiomes, invasionBiomes).allMatch(Collection::isEmpty)) {
						sb.append(Lang.ENTITY_NO_BIOMES);
					} else {
						sb.append(Lang.ENTITY_HAS_BIOMES);
						for (GOTBiome biome : spawnBiomes) {
							sb.append("\n* ").append(getBiomeLink(biome)).append(';');
						}
						for (GOTBiome biome : conquestBiomes) {
							if (!invasionBiomes.contains(biome)) {
								sb.append("\n* ").append(getBiomeLink(biome)).append(' ').append(Lang.ENTITY_CONQUEST).append(';');
							}
						}
						for (GOTBiome biome : invasionBiomes) {
							if (!conquestBiomes.contains(biome)) {
								sb.append("\n* ").append(getBiomeLink(biome)).append(' ').append(Lang.ENTITY_INVASION).append(';');
							}
						}
						for (GOTBiome biome : unnaturalBiomes) {
							if (conquestBiomes.contains(biome) && invasionBiomes.contains(biome)) {
								sb.append("\n* ").append(getBiomeLink(biome)).append(' ').append(Lang.ENTITY_CONQUEST_INVASION).append(';');
							}
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("DB Mob-Owner");
				sb.append(BEGIN);
				for (Class<? extends Entity> entityClass : HIREABLE) {
					HashMap<Class<? extends Entity>, Class<? extends Entity>> owners = new HashMap<>();
					loop:
					for (Map.Entry<Class<? extends Entity>, Entity> ownerEntry : CLASS_TO_OBJ.entrySet()) {
						if (ownerEntry.getValue() instanceof GOTUnitTradeable) {
							GOTUnitTradeEntries entries = ((GOTUnitTradeable) ownerEntry.getValue()).getUnits();
							if (!((GOTEntityNPC) ownerEntry.getValue()).isLegendaryNPC()) {
								for (GOTUnitTradeEntry entry : entries.getTradeEntries()) {
									if (entry.entityClass == entityClass) {
										owners.put(entityClass, ownerEntry.getKey());
										break loop;
									}
								}
							}
						}
					}
					if (owners.isEmpty()) {
						loop:
						for (Map.Entry<Class<? extends Entity>, Entity> ownerEntry : CLASS_TO_OBJ.entrySet()) {
							if (ownerEntry.getValue() instanceof GOTUnitTradeable) {
								GOTUnitTradeEntries entries = ((GOTUnitTradeable) ownerEntry.getValue()).getUnits();
								if (((GOTEntityNPC) ownerEntry.getValue()).isLegendaryNPC()) {
									for (GOTUnitTradeEntry entry : entries.getTradeEntries()) {
										if (entry.entityClass == entityClass) {
											owners.put(entityClass, ownerEntry.getKey());
											break loop;
										}
									}
								}
							}
						}
					}
					if (!owners.isEmpty()) {
						sb.append("\n| ").append(getEntityPagename(entityClass)).append(" = ").append(getEntityLink(owners.get(entityClass)));
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-Health");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
					EntityLivingBase entity = (EntityLivingBase) entityEntry.getValue();
					sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = ").append(entity.getMaxHealth());
				}
				sb.append(END);

				sb.append(TITLE).append("Template: DB Mob-Rideable1");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPCRideable) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-Rideable2");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
					if (entityEntry.getValue() instanceof GOTNPCMount) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-BannerBearer");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
					if (entityEntry.getValue() instanceof GOTBannerBearer) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-UnitTradeable");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
					if (entityEntry.getValue() instanceof GOTUnitTradeable) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-Tradeable");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
					if (entityEntry.getValue() instanceof GOTTradeable) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-Smith");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
					if (entityEntry.getValue() instanceof GOTTradeable.Smith) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-Mercenary");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
					if (entityEntry.getValue() instanceof GOTMercenary) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-Farmhand");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
					if (entityEntry.getValue() instanceof GOTFarmhand) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-Buys");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
					if (entityEntry.getValue() instanceof GOTTradeable) {
						GOTTradeEntries entries = ((GOTTradeable) entityEntry.getValue()).getSellPool();
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = ");
						for (GOTTradeEntry entry : entries.getTradeEntries()) {
							sb.append("\n* ").append(entry.tradeItem.getDisplayName()).append(": {{Coins|").append(entry.getCost()).append("}};");
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-Sells");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
					if (entityEntry.getValue() instanceof GOTTradeable) {
						GOTTradeEntries entries = ((GOTTradeable) entityEntry.getValue()).getBuyPool();
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = ");
						for (GOTTradeEntry entry : entries.getTradeEntries()) {
							sb.append("\n* ").append(entry.tradeItem.getDisplayName()).append(": {{Coins|").append(entry.getCost()).append("}};");
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-Character");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC && ((GOTEntityNPC) entityEntry.getValue()).isLegendaryNPC()) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-ImmuneToFrost");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC && ((GOTEntityNPC) entityEntry.getValue()).isImmuneToFrost || entityEntry.getValue() instanceof GOTBiome.ImmuneToFrost) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-ImmuneToFire");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
					if (entityEntry.getValue().isImmuneToFire()) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-ImmuneToHeat");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
					if (entityEntry.getValue() instanceof GOTBiome.ImmuneToHeat) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-SpawnInDarkness");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC && ((GOTEntityNPC) entityEntry.getValue()).spawnsInDarkness) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-Alignment");
				sb.append(BEGIN);
				next:
				for (Class<? extends Entity> entityClass : HIREABLE) {
					for (GOTUnitTradeEntries entries : UNITS) {
						for (GOTUnitTradeEntry entry : entries.getTradeEntries()) {
							if (entry.entityClass == entityClass) {
								sb.append("\n| ").append(getEntityPagename(entityClass)).append(" = ");
								if (entry.getPledgeType() == GOTUnitTradeEntry.PledgeType.NONE || entry.alignmentRequired >= 101.0f) {
									sb.append(entry.alignmentRequired);
								} else {
									sb.append('+').append(100.0);
								}
								continue next;
							}
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-Marriage");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC && ((GOTEntityNPC) entityEntry.getValue()).canBeMarried) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-Faction");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC) {
						GOTFaction fac = ((GOTEntityNPC) entityEntry.getValue()).getFaction();
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = ").append(getFactionLink(fac));
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-Achievement");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC) {
						GOTAchievement ach = ((GOTEntityNPC) entityEntry.getValue()).getKillAchievement();
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = ");
						if (ach == null) {
							sb.append("N/A");
						} else {
							sb.append('"').append(getAchievementTitle(ach)).append('"');
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-Bonus");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> entityEntry : CLASS_TO_OBJ.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC) {
						float bonus = ((GOTEntityNPC) entityEntry.getValue()).getAlignmentBonus();
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = +").append(bonus);
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-Price");
				sb.append(BEGIN);
				for (GOTUnitTradeEntries entries : UNITS) {
					for (GOTUnitTradeEntry entry : entries.getTradeEntries()) {
						sb.append("\n| ").append(getEntityPagename(entry.entityClass)).append(" = ");
						if (entry.getPledgeType() == GOTUnitTradeEntry.PledgeType.NONE) {
							sb.append("{{Coins|").append(entry.initialCost * 2).append("}}");
						} else {
							sb.append("N/A");
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-PricePledge");
				sb.append(BEGIN);
				for (GOTUnitTradeEntries entries : UNITS) {
					for (GOTUnitTradeEntry entry : entries.getTradeEntries()) {
						sb.append("\n| ").append(getEntityPagename(entry.entityClass)).append(" = {{Coins|").append(entry.initialCost).append("}}");
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-Units");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, Entity> ownerEntry : CLASS_TO_OBJ.entrySet()) {
					if (ownerEntry.getValue() instanceof GOTUnitTradeable) {
						GOTUnitTradeEntries entries = ((GOTUnitTradeable) ownerEntry.getValue()).getUnits();
						sb.append("\n| ").append(getEntityPagename(ownerEntry.getKey())).append(" = ");
						for (GOTUnitTradeEntry entry : entries.getTradeEntries()) {
							if (entry.mountClass == null) {
								sb.append("\n* ").append(getEntityLink(entry.entityClass));
								if (entry.getPledgeType() == GOTUnitTradeEntry.PledgeType.NONE) {
									sb.append(": {{Coins|").append(entry.initialCost * 2).append("}} ").append(Lang.NO_PLEDGE).append(", {{Coins|").append(entry.initialCost).append("}} ").append(Lang.NEED_PLEDGE).append("; ").append(entry.alignmentRequired).append("+ ").append(Lang.REPUTATION).append(';');
								} else if (entry.alignmentRequired < 101.0f) {
									sb.append(": N/A ").append(Lang.NO_PLEDGE).append(", {{Coins|").append(entry.initialCost).append("}} ").append(Lang.NEED_PLEDGE).append("; +").append(100.0).append("+ ").append(Lang.REPUTATION).append(';');
								} else {
									sb.append(": N/A ").append(Lang.NO_PLEDGE).append(", {{Coins|").append(entry.initialCost).append("}} ").append(Lang.NEED_PLEDGE).append("; +").append(entry.alignmentRequired).append("+ ").append(Lang.REPUTATION).append(';');
								}
							}
						}
					}
				}
				sb.append(END);

				sb.append(TITLE).append("Template:DB Mob-Waypoint");
				sb.append(BEGIN);
				for (Map.Entry<Class<? extends Entity>, GOTWaypoint> entityEntry : CLASS_TO_WP.entrySet()) {
					sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = ").append(entityEntry.getValue().getDisplayName());
				}
				for (Map.Entry<GOTAbstractWaypoint, GOTStructureBaseSettlement> entry : GOTFixer.SPAWNERS.entrySet()) {
					GOTStructureBaseSettlement spawner = entry.getValue();
					spawner.getLegendaryNPCs(world);
					for (GOTFixer.SpawnInfo info : spawner.getLegendaryNPCs(world)) {
						sb.append("\n| ").append(getEntityPagename(info.getNPC().getClass())).append(" = ").append(entry.getKey().getDisplayName());
					}
				}
				sb.append(END);
				sb.append("\n</mediawiki>");

				PrintWriter xml = new PrintWriter("hummel/import.xml", "UTF-8");
				xml.write(sb.toString());
				xml.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		long newTime = System.nanoTime();
		IChatComponent chatComponentTranslation = new ChatComponentText("Generated databases in " + (newTime - time) / 1.0E9 + 's');
		player.addChatMessage(chatComponentTranslation);
	}

	private static String getAchievementDesc(GOTAchievement ach) {
		return StatCollector.translateToLocal("got.achievement." + ach.getCodeName() + ".desc");
	}

	private static String getAchievementTitle(GOTAchievement ach) {
		return StatCollector.translateToLocal("got.achievement." + ach.getCodeName() + ".title");
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
		return BIOME_TO_PAGE.get(getBiomeName(biome));
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

	private static String getEntityPagename(Class<? extends Entity> entityClass) {
		return ENTITY_TO_PAGE.get(getEntityName(entityClass));
	}

	private static String getEntityVanillaName(Class<? extends Entity> entityClass) {
		return StatCollector.translateToLocal("entity." + EntityList.classToStringMapping.get(entityClass) + ".name");
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
		return FAC_TO_PAGE.get(getFactionName(fac));
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

	private static String getStructureName(Class<? extends WorldGenerator> structureClass) {
		return StatCollector.translateToLocal("got.structure." + GOTStructureRegistry.CLASS_TO_NAME_MAPPING.get(structureClass) + ".name");
	}

	private static String getTreeName(GOTTreeType tree) {
		return StatCollector.translateToLocal("got.tree." + tree.name().toLowerCase(Locale.ROOT) + ".name");
	}

	private static void searchForEntities(World world) {
		for (Class<? extends Entity> entityClass : GOTEntityRegistry.CONTENT) {
			CLASS_TO_OBJ.put(entityClass, GOTReflection.newEntity(entityClass, world));
		}
	}

	private static void searchForHireable(Collection<Class<? extends Entity>> hireable, Iterable<GOTUnitTradeEntries> units) {
		for (GOTUnitTradeEntries entries : units) {
			for (GOTUnitTradeEntry entry : entries.getTradeEntries()) {
				hireable.add(entry.entityClass);
			}
		}
	}

	private static void searchForMinerals(Iterable<GOTBiome> biomes, Collection<String> minerals) {
		for (GOTBiome biome : biomes) {
			Collection<GOTBiomeDecorator.OreGenerant> oreGenerants = new ArrayList<>(biome.getDecorator().getBiomeSoils());
			oreGenerants.addAll(biome.getDecorator().getBiomeOres());
			oreGenerants.addAll(biome.getDecorator().getBiomeGems());
			for (GOTBiomeDecorator.OreGenerant oreGenerant : oreGenerants) {
				WorldGenMinable gen = oreGenerant.getOreGen();
				Block block = GOTReflection.getOreBlock(gen);
				int meta = GOTReflection.getOreMeta(gen);
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
					BIOME_TO_PAGE.put(preName, preName + " (" + Lang.PAGE_BIOME + ')');
					continue next;
				}
			}
			for (Class<? extends Entity> entityClass : GOTEntityRegistry.CONTENT) {
				if (preName.equals(getEntityName(entityClass))) {
					BIOME_TO_PAGE.put(preName, preName + " (" + Lang.PAGE_BIOME + ')');
					continue next;
				}
			}
			BIOME_TO_PAGE.put(preName, preName);
		}
	}

	private static void searchForPagenamesEntity(Iterable<GOTBiome> biomes, Iterable<GOTFaction> factions) {
		next:
		for (Class<? extends Entity> entityClass : GOTEntityRegistry.CONTENT) {
			String preName = getEntityName(entityClass);
			for (GOTBiome biome : biomes) {
				if (preName.equals(getBiomeName(biome))) {
					ENTITY_TO_PAGE.put(preName, preName + " (" + Lang.PAGE_ENTITY + ')');
					continue next;
				}
			}
			for (GOTFaction fac : factions) {
				if (preName.equals(getFactionName(fac))) {
					ENTITY_TO_PAGE.put(preName, preName + " (" + Lang.PAGE_ENTITY + ')');
					continue next;
				}
			}
			ENTITY_TO_PAGE.put(preName, preName);
		}
	}

	private static void searchForPagenamesFaction(Iterable<GOTBiome> biomes, Iterable<GOTFaction> factions) {
		next:
		for (GOTFaction fac : factions) {
			String preName = getFactionName(fac);
			for (GOTBiome biome : biomes) {
				if (preName.equals(getBiomeName(biome))) {
					FAC_TO_PAGE.put(preName, preName + " (" + Lang.PAGE_FACTION + ')');
					continue next;
				}
			}
			for (Class<? extends Entity> entityClass : GOTEntityRegistry.CONTENT) {
				if (preName.equals(getEntityName(entityClass))) {
					FAC_TO_PAGE.put(preName, preName + " (" + Lang.PAGE_FACTION + ')');
					continue next;
				}
			}
			FAC_TO_PAGE.put(preName, preName);
		}
	}

	private static void searchForStructures(Iterable<GOTBiome> biomes, Collection<Class<? extends WorldGenerator>> structures) {
		for (GOTBiome biome : biomes) {
			for (GOTBiomeDecorator.Structure structure : biome.getDecorator().getStructures()) {
				structures.add(structure.getStructureGen().getClass());
			}
		}
	}

	public enum Database {
		XML("xml"), TABLES("tables");

		private final String codeName;

		Database(String name) {
			codeName = name;
		}

		public static Database forName(String name) {
			for (Database db : values()) {
				if (db.codeName.equals(name)) {
					return db;
				}
			}
			return null;
		}

		public static List<String> getNames() {
			List<String> names = new ArrayList<>();
			for (Database db : values()) {
				names.add(db.codeName);
			}
			return names;
		}
	}

	public enum Lang {
		PAGE_BIOME, PAGE_FACTION, PAGE_ENTITY, BIOME_HAS_ANIMALS, BIOME_HAS_CONQUEST, BIOME_HAS_INVASIONS, BIOME_HAS_SPAWN, BIOME_HAS_STRUCTURES, BIOME_HAS_TREES, BIOME_HAS_TREES_BIOME_ONLY, BIOME_HAS_WAYPOINTS, BIOME_NO_ACHIEVEMENT, BIOME_NO_ANIMALS, BIOME_NO_CONQUEST, BIOME_NO_INVASIONS, BIOME_NO_SPAWN, BIOME_NO_STRUCTURES, BIOME_NO_TREES, BIOME_NO_VARIANTS, BIOME_NO_WAYPOINTS, BIOME_HAS_MINERALS, BIOME_CONQUEST_ONLY, BIOME_SPAWN_ONLY, FACTION_HAS_BANNERS, FACTION_HAS_CHARS, FACTION_HAS_CONQUEST, FACTION_HAS_INVASION, FACTION_HAS_RANKS, FACTION_HAS_SPAWN, FACTION_HAS_WAR_CRIMES, FACTION_HAS_WAYPOINTS, FACTION_NO_ATTRIBUTES, FACTION_NO_BANNERS, FACTION_NO_CHARS, FACTION_NO_CONQUEST, FACTION_NO_ENEMIES, FACTION_NO_FRIENDS, FACTION_NO_INVASIONS, FACTION_NO_RANKS, FACTION_NO_SPAWN, FACTION_NO_STRUCTURES, FACTION_NO_WAR_CRIMES, FACTION_NO_WAYPOINTS, TREE_HAS_BIOMES, TREE_NO_BIOMES, TREE_VARIANT_ONLY, RIDER, NO_PLEDGE, NEED_PLEDGE, REPUTATION, MINERAL_BIOMES, STRUCTURE_BIOMES, ENTITY_NO_BIOMES, ENTITY_HAS_BIOMES, ENTITY_CONQUEST, ENTITY_INVASION, ENTITY_CONQUEST_INVASION, CATEGORY, CLIMATE_COLD, CLIMATE_COLD_AZ, CLIMATE_NORMAL, CLIMATE_NORMAL_AZ, CLIMATE_SUMMER, CLIMATE_SUMMER_AZ, CLIMATE_WINTER, CLIMATE_NULL, SEASON_WINTER, SEASON_AUTUMN, SEASON_SUMMER, SEASON_SPRING;

		@Override
		public String toString() {
			return StatCollector.translateToLocal("got.db." + name() + ".name");
		}
	}
}