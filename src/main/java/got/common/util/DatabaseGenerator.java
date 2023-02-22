package got.common.util;

import java.io.*;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.*;

import got.common.block.other.*;
import got.common.database.*;
import got.common.database.GOTInvasions.InvasionSpawnEntry;
import got.common.entity.other.*;
import got.common.entity.other.GOTUnitTradeEntry.PledgeType;
import got.common.entity.westeros.legendary.captain.*;
import got.common.entity.westeros.legendary.deco.*;
import got.common.entity.westeros.legendary.quest.*;
import got.common.entity.westeros.legendary.reborn.GOTEntityJonSnow.JonSnowLife1;
import got.common.entity.westeros.legendary.reborn.GOTEntityLancelLannister.LancelLannisterNormal;
import got.common.entity.westeros.legendary.trader.*;
import got.common.entity.westeros.legendary.warrior.*;
import got.common.faction.*;
import got.common.item.other.GOTItemBanner.BannerType;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.GOTBiomeDecorator.*;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.biome.variant.GOTBiomeVariantList.VariantBucket;
import got.common.world.feature.GOTTreeType;
import got.common.world.feature.GOTTreeType.WeightedTreeType;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList.*;
import got.common.world.spawning.GOTSpawnEntry;
import got.common.world.structure.other.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.*;

public class DatabaseGenerator extends GOTStructureBase {
	public static String display = "null";
	public static Map<Class<? extends Entity>, Entity> classToObjectMapping = new HashMap<>();
	public static Map<Class<? extends Entity>, GOTWaypoint> classToWaypointMapping = new HashMap<>();
	public static Map<String, String> factionPageMapping = new HashMap<>();
	public static Map<String, String> entityPageMapping = new HashMap<>();
	public static Map<String, String> biomePageMapping = new HashMap<>();
	public static Set<Item> items = new HashSet<>(GOTAPI.getObjectFieldsOfType(GOTRegistry.class, Item.class));
	public static Set<GOTUnitTradeEntries> units = new HashSet<>(GOTAPI.getObjectFieldsOfType(GOTUnitTradeEntries.class, GOTUnitTradeEntries.class));
	public static Set<GOTAchievement> achievements = new HashSet<>(GOTAPI.getObjectFieldsOfType(GOTAchievement.class, GOTAchievement.class));
	public static Set<GOTBiome> biomes = new HashSet<>(GOTAPI.getObjectFieldsOfType(GOTBiome.class, GOTBiome.class));
	public static Set<GOTFaction> factions = EnumSet.allOf(GOTFaction.class);
	public static Set<GOTTreeType> trees = EnumSet.allOf(GOTTreeType.class);
	public static Set<GOTWaypoint> waypoints = EnumSet.allOf(GOTWaypoint.class);
	public static Set<GOTCapes> capes = EnumSet.allOf(GOTCapes.class);
	public static Set<GOTShields> shields = EnumSet.allOf(GOTShields.class);
	public static Set<String> minerals = new HashSet<>();
	public static Set<Class<? extends WorldGenerator>> structures = new HashSet<>();
	public static Set<Class<? extends Entity>> hireable = new HashSet<>();
	public static String biomePage = StatCollector.translateToLocal("got.db.biomeLoc.name");
	public static String factionPage = StatCollector.translateToLocal("got.db.factionLoc.name");
	public static String entityPage = StatCollector.translateToLocal("got.db.entityLoc.name");
	public static String biomeHasAnimals = StatCollector.translateToLocal("got.db.biomeHasAnimals.name");
	public static String biomeHasConquest = StatCollector.translateToLocal("got.db.biomeHasConquest.name");
	public static String biomeHasInvasions = StatCollector.translateToLocal("got.db.biomeHasInvasions.name");
	public static String biomeHasSpawn = StatCollector.translateToLocal("got.db.biomeHasSpawn.name");
	public static String biomeHasStructures = StatCollector.translateToLocal("got.db.biomeHasStructures.name");
	public static String biomeHasTreesBiomeAndVariant = StatCollector.translateToLocal("got.db.biomeHasTrees2.name");
	public static String biomeHasTreesBiomeOnly = StatCollector.translateToLocal("got.db.biomeHasTrees1.name");
	public static String biomeHasWaypoints = StatCollector.translateToLocal("got.db.biomeHasWaypoints.name");
	public static String biomeNoAchievement = StatCollector.translateToLocal("got.db.biomeNoAchievement.name");
	public static String biomeNoAnimals = StatCollector.translateToLocal("got.db.biomeNoAnimals.name");
	public static String biomeNoConquest = StatCollector.translateToLocal("got.db.biomeNoConquest.name");
	public static String biomeNoInvasions = StatCollector.translateToLocal("got.db.biomeNoInvasions.name");
	public static String biomeNoSpawn = StatCollector.translateToLocal("got.db.biomeNoSpawn.name");
	public static String biomeNoStructures = StatCollector.translateToLocal("got.db.biomeNoStructures.name");
	public static String biomeNoTrees = StatCollector.translateToLocal("got.db.biomeNoTrees.name");
	public static String biomeNoVariants = StatCollector.translateToLocal("got.db.biomeNoVariants.name");
	public static String biomeNoWaypoints = StatCollector.translateToLocal("got.db.biomeNoWaypoints.name");
	public static String biomeMinerals = StatCollector.translateToLocal("got.db.biomeMinerals.name");
	public static String biomeConquestOnly = StatCollector.translateToLocal("got.db.biomeConquestOnly.name");
	public static String biomeSpawnOnly = StatCollector.translateToLocal("got.db.biomeSpawnOnly.name");
	public static String factionHasBanners = StatCollector.translateToLocal("got.db.factionHasBanners.name");
	public static String factionHasCharacters = StatCollector.translateToLocal("got.db.factionHasCharacters.name");
	public static String factionHasConquest = StatCollector.translateToLocal("got.db.factionHasConquest.name");
	public static String factionHasInvasion = StatCollector.translateToLocal("got.db.factionHasInvasion.name");
	public static String factionHasRanks = StatCollector.translateToLocal("got.db.factionHasRanks.name");
	public static String factionHasSpawn = StatCollector.translateToLocal("got.db.factionHasSpawn.name");
	public static String factionHasWarCrimes = StatCollector.translateToLocal("got.db.factionIsViolent.name");
	public static String factionHasWaypoints = StatCollector.translateToLocal("got.db.factionHasWaypoints.name");
	public static String factionNoAttr = StatCollector.translateToLocal("got.db.factionNoAttr.name");
	public static String factionNoBanners = StatCollector.translateToLocal("got.db.factionNoBanners.name");
	public static String factionNoCharacters = StatCollector.translateToLocal("got.db.factionNoCharacters.name");
	public static String factionNoConquest = StatCollector.translateToLocal("got.db.factionNoConquest.name");
	public static String factionNoEnemies = StatCollector.translateToLocal("got.db.factionNoEnemies.name");
	public static String factionNoFriends = StatCollector.translateToLocal("got.db.factionNoFriends.name");
	public static String factionNoInvasion = StatCollector.translateToLocal("got.db.factionNoInvasion.name");
	public static String factionNoRanks = StatCollector.translateToLocal("got.db.factionNoRanks.name");
	public static String factionNoSpawn = StatCollector.translateToLocal("got.db.factionNoSpawn.name");
	public static String factionNoStructures = StatCollector.translateToLocal("got.db.factionNoStructures.name");
	public static String factionNoWarCrimes = StatCollector.translateToLocal("got.db.factionNotViolent.name");
	public static String factionNoWaypoints = StatCollector.translateToLocal("got.db.factionNoWaypoints.name");
	public static String treeHasBiomes = StatCollector.translateToLocal("got.db.treeHasBiomes.name");
	public static String treeNoBiomes = StatCollector.translateToLocal("got.db.treeNoBiomes.name");
	public static String treeVariantOnly = StatCollector.translateToLocal("got.db.treeVariantOnly.name");
	public static String rider = StatCollector.translateToLocal("got.db.rider.name");
	public static String noPledge = StatCollector.translateToLocal("got.db.noPledge.name");
	public static String yesPledge = StatCollector.translateToLocal("got.db.yesPledge.name");
	public static String rep = StatCollector.translateToLocal("got.db.rep.name");
	public static String mineralBiomes = StatCollector.translateToLocal("got.db.mineralBiomes.name");
	public static String structureBiomes = StatCollector.translateToLocal("got.db.structureBiomes.name");
	public static String entityNoBiomes = StatCollector.translateToLocal("got.db.entityNoBiomes.name");
	public static String entityHasBiomes = StatCollector.translateToLocal("got.db.entityHasBiomes.name");
	public static String entityConquestOnly = StatCollector.translateToLocal("got.db.entityConquestOnly.name");
	public static String entityInvasionOnly = StatCollector.translateToLocal("got.db.entityInvasionOnly.name");
	public static String entityConquestInvasion = StatCollector.translateToLocal("got.db.entityConquestInvasion.name");
	public static String begin = "</title><ns>10</ns><revision><text>&lt;includeonly&gt;{{#switch: {{{1}}}";
	public static String end = "}}&lt;/includeonly&gt;&lt;noinclude&gt;[[" + StatCollector.translateToLocal("got.db.categoryTemplates.name") + "]]&lt;/noinclude&gt;</text></revision></page>";

	static {
		classToWaypointMapping.put(GOTEntityYgritte.class, GOTWaypoint.Hardhome);
		classToWaypointMapping.put(GOTEntityTormund.class, GOTWaypoint.Hardhome);
		classToWaypointMapping.put(GOTEntityManceRayder.class, GOTWaypoint.Hardhome);
		classToWaypointMapping.put(GOTEntityCraster.class, GOTWaypoint.CrastersKeep);
		classToWaypointMapping.put(GOTEntityVictarionGreyjoy.class, GOTWaypoint.VictarionLanding);
		classToWaypointMapping.put(LancelLannisterNormal.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityTobhoMott.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityTyrionLannister.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityGendryBaratheon.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityPetyrBaelish.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityBronn.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityPodrickPayne.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityPetyrBaelish.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityHotPie.class, GOTWaypoint.CrossroadsInn);
		classToWaypointMapping.put(GOTEntityVargoHoat.class, GOTWaypoint.CrossroadsInn);
		classToWaypointMapping.put(GOTEntitySandorClegane.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityJoffreyBaratheon.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityCerseiLannister.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityJaimeLannister.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityPycelle.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityJanosSlynt.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityVarys.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityIlynPayne.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityHighSepton.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityTommenBaratheon.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityMyrcellaBaratheon.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityMerynTrant.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityBarristanSelmy.class, GOTWaypoint.KingsLanding);
		classToWaypointMapping.put(GOTEntityJeorMormont.class, GOTWaypoint.CastleBlack);
		classToWaypointMapping.put(JonSnowLife1.class, GOTWaypoint.CastleBlack);
		classToWaypointMapping.put(GOTEntityAemonTargaryen.class, GOTWaypoint.CastleBlack);
		classToWaypointMapping.put(GOTEntityAlliserThorne.class, GOTWaypoint.CastleBlack);
		classToWaypointMapping.put(GOTEntityEdd.class, GOTWaypoint.CastleBlack);
		classToWaypointMapping.put(GOTEntitySamwellTarly.class, GOTWaypoint.CastleBlack);
		classToWaypointMapping.put(GOTEntityCotterPyke.class, GOTWaypoint.EastWatch);
		classToWaypointMapping.put(GOTEntityHarmune.class, GOTWaypoint.EastWatch);
		classToWaypointMapping.put(GOTEntityDenysMallister.class, GOTWaypoint.ShadowTower);
		classToWaypointMapping.put(GOTEntityMullin.class, GOTWaypoint.ShadowTower);
		searchForMinerals(biomes, minerals);
		searchForStructures(biomes, structures);
		searchForHireable(hireable, units);
		searchForPagenamesEntity(biomes, factions);
		searchForPagenamesBiome(biomes, factions);
		searchForPagenamesFaction(biomes, factions);
		biomes.remove(GOTBiome.ocean1);
		biomes.remove(GOTBiome.ocean2);
		biomes.remove(GOTBiome.ocean3);
		biomes.remove(GOTBiome.beachGravel);
		biomes.remove(GOTBiome.beachWhite);
		biomes.remove(GOTBiome.beachRed);
	}

	public DatabaseGenerator(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int y, int j, int k, int rotation) {
		long time = System.nanoTime();
		try {
			searchForEntities(world);
			Files.createDirectories(Paths.get("hummel"));
			if ("tables".equals(display)) {
				StringBuilder sb;

				sb = new StringBuilder();
				for (GOTAchievement ach : achievements) {
					if (ach != null) {
						sb.append("\n| ").append(getAchievementTitle(ach)).append(" || ").append(getAchievementDesc(ach)).append("\n|-");
					}
				}
				PrintWriter fAchievements = new PrintWriter("hummel/achievements.txt", "UTF-8");
				fAchievements.write(sb.toString());
				fAchievements.close();

				sb = new StringBuilder();
				for (GOTShields shield : shields) {
					sb.append("\n| ").append(shield.getShieldName()).append(" || ").append(shield.getShieldDesc()).append(" || ").append(getShieldFilename(shield)).append("\n|-");
				}
				PrintWriter fShields = new PrintWriter("hummel/shields.txt", "UTF-8");
				fShields.write(sb.toString());
				fShields.close();

				sb = new StringBuilder();
				for (GOTCapes cape : capes) {
					sb.append("\n| ").append(cape.getCapeName()).append(" || ").append(cape.getCapeDesc()).append(" || ").append(getCapeFilename(cape)).append("\n|-");
				}
				PrintWriter fCapes = new PrintWriter("hummel/capes.txt", "UTF-8");
				fCapes.write(sb.toString());
				fCapes.close();

				sb = new StringBuilder();
				for (GOTUnitTradeEntries unitTradeEntries : units) {
					if (unitTradeEntries != null) {
						for (GOTUnitTradeEntry entry : unitTradeEntries.tradeEntries) {
							if (entry != null) {
								sb.append("\n| ").append(getEntityLink(entry.entityClass));
								if (entry.getPledgeType() == PledgeType.NONE) {
									if (entry.mountClass == null) {
										sb.append(" || {{Coins|").append(entry.initialCost * 2).append("}} || {{Coins|").append(entry.initialCost).append("}} || +").append(entry.alignmentRequired).append(" || -");
									} else {
										sb.append(" || {{Coins|").append(entry.initialCost * 2).append("}} (").append(rider).append(") || {{Coins|").append(entry.initialCost).append("}} || +").append(entry.alignmentRequired).append(" || -");
									}
								} else if (entry.mountClass == null) {
									if (entry.alignmentRequired < 101.0f) {
										sb.append(" || N/A || {{Coins|").append(entry.initialCost).append("}} || +100.0 || +");
									} else {
										sb.append(" || N/A || {{Coins|").append(entry.initialCost).append("}} || +").append(entry.alignmentRequired).append(" || +");
									}
								} else if (entry.alignmentRequired < 101.0f) {
									sb.append(" || N/A || {{Coins|").append(entry.initialCost).append("}} (").append(rider).append(") || +100.0 || +");
								} else {
									sb.append(" || N/A || {{Coins|").append(entry.initialCost).append("}} (").append(rider).append(") || +").append(entry.alignmentRequired).append(" || +");
								}
								sb.append("\n|-");
							}
						}
					}
				}
				PrintWriter fUnits = new PrintWriter("hummel/units.txt", "UTF-8");
				fUnits.write(sb.toString());
				fUnits.close();

				sb = new StringBuilder();
				for (GOTWaypoint wp : waypoints) {
					sb.append("\n| ").append(wp.getDisplayName()).append(" || ").append(wp.getLoreText(usingPlayer)).append("\n|-");
				}
				PrintWriter fWaypoints = new PrintWriter("hummel/waypoints.txt", "UTF-8");
				fWaypoints.write(sb.toString());
				fWaypoints.close();

				sb = new StringBuilder();
				for (Item item : items) {
					if (item instanceof ItemArmor) {
						float damage = ((ItemArmor) item).damageReduceAmount;
						ArmorMaterial material = ((ItemArmor) item).getArmorMaterial();
						sb.append("\n| ").append(getItemName(item)).append(" || ").append(getItemFilename(item)).append(" || ").append(item.getMaxDamage()).append(" || ").append(damage).append(" || ");
						if (material != null && material.customCraftingMaterial != null) {
							sb.append(getItemName(material.customCraftingMaterial));
						} else {
							sb.append("N/A");
						}
						sb.append("\n|-");
					}
				}
				PrintWriter fArmor = new PrintWriter("hummel/armor.txt", "UTF-8");
				fArmor.write(sb.toString());
				fArmor.close();

				sb = new StringBuilder();
				for (Item item : items) {
					if (item instanceof ItemSword) {
						float damage = GOTReflection.getDamageAmount(item);
						ToolMaterial material = GOTReflection.getToolMaterial(item);
						sb.append("\n| ").append(getItemName(item)).append(" || ").append(getItemFilename(item)).append(" || ").append(item.getMaxDamage()).append(" || ").append(damage).append(" || ");
						if (material.getRepairItemStack() != null) {
							sb.append(getItemName(material.getRepairItemStack().getItem()));
						} else {
							sb.append("N/A");
						}
						sb.append("\n|-");
					}
				}
				PrintWriter fWeapon = new PrintWriter("hummel/weapon.txt", "UTF-8");
				fWeapon.write(sb.toString());
				fWeapon.close();

				sb = new StringBuilder();
				for (Item item : items) {
					if (item instanceof ItemFood) {
						int heal = ((ItemFood) item).func_150905_g(null);
						float saturation = ((ItemFood) item).func_150906_h(null);
						sb.append("\n| ").append(getItemName(item)).append(" || ").append(getItemFilename(item)).append(" || ").append("{{Bar|bread|").append(new DecimalFormat("#.##").format(saturation * heal * 2)).append("}} || {{Bar|food|").append(heal).append("}} || ").append(item.getItemStackLimit()).append("\n|-");
					}
				}
				PrintWriter fFood = new PrintWriter("hummel/food.txt", "UTF-8");
				fFood.write(sb.toString());
				fFood.close();

			} else if ("xml".equals(display)) {
				StringBuilder sb = new StringBuilder();

				sb.append("\n<mediawiki xmlns=\"http://www.mediawiki.org/xml/export-0.11/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.mediawiki.org/xml/export-0.11/ http://www.mediawiki.org/xml/export-0.11.xsd\" version=\"0.11\" xml:lang=\"ru\">");

				/* ALL PAGES */

				File file = new File("hummel/sitemap.txt");
				if (!file.exists()) {
					file.createNewFile();
				}
				List<String> sitemap;
				try (Stream<String> lines = Files.lines(Paths.get("hummel/sitemap.txt"))) {
					sitemap = lines.collect(Collectors.toList());
				}

				String s1 = "<page><title>";

				for (String str : minerals) {
					if (!sitemap.contains(str)) {
						String s2 = "</title><revision><text>{{\u0421\u0442\u0430\u0442\u044C\u044F \u0418\u0441\u043A\u043E\u043F\u0430\u0435\u043C\u043E\u0435}}</text></revision></page>";
						sb.append(s1).append(str).append(s2);
						sb.append("\n");
					}
				}

				for (Class<? extends Entity> entityClass : classToObjectMapping.keySet()) {
					String pageName = getEntityPagename(entityClass);
					if (!sitemap.contains(pageName)) {
						String s2 = "</title><revision><text>{{\u0421\u0442\u0430\u0442\u044C\u044F \u041C\u043E\u0431}}</text></revision></page>";
						sb.append(s1).append(pageName).append(s2);
						sb.append("\n");
					}
				}

				for (GOTBiome biome : biomes) {
					if (biome != null) {
						String pageName = getBiomePagename(biome);
						if (!sitemap.contains(pageName)) {
							String s2 = "</title><revision><text>{{\u0421\u0442\u0430\u0442\u044C\u044F \u0411\u0438\u043E\u043C}}</text></revision></page>";
							sb.append(s1).append(pageName).append(s2);
							sb.append("\n");
						}
					}
				}

				for (GOTFaction fac : factions) {
					String pageName = getFactionPagename(fac);
					if (!sitemap.contains(pageName)) {
						String s2 = "</title><revision><text>{{\u0421\u0442\u0430\u0442\u044C\u044F \u0424\u0440\u0430\u043A\u0446\u0438\u044F}}</text></revision></page>";
						sb.append(s1).append(pageName).append(s2);
						sb.append("\n");
					}
				}

				for (GOTTreeType tree : trees) {
					String pageName = getTreeName(tree);
					if (!sitemap.contains(pageName)) {
						String s2 = "</title><revision><text>{{\u0421\u0442\u0430\u0442\u044C\u044F \u0414\u0435\u0440\u0435\u0432\u043E}}</text></revision></page>";
						sb.append(s1).append(pageName).append(s2);
						sb.append("\n");
					}
				}

				for (Class<? extends WorldGenerator> strClass : structures) {
					String pageName = getStructureName(strClass);
					if (!sitemap.contains(pageName)) {
						String s2 = "</title><revision><text>{{\u0421\u0442\u0430\u0442\u044C\u044F \u0421\u0442\u0440\u0443\u043A\u0442\u0443\u0440\u0430}}</text></revision></page>";
						sb.append(s1).append(pageName).append(s2);
						sb.append("\n");
					}
				}

				/* STRUCTURES */

				sb.append("<page><title>Template:DB Structure-Biomes");
				sb.append("\n").append(begin);
				for (Class<? extends WorldGenerator> strClass : structures) {
					sb.append("\n| ").append(getStructureName(strClass)).append(" = ").append(structureBiomes);
					next: for (GOTBiome biome : biomes) {
						if (biome != null && !biome.decorator.randomStructures.isEmpty()) {
							for (RandomStructure structure : biome.decorator.randomStructures) {
								if (structure.structureGen.getClass() == strClass) {
									sb.append("\n* ").append(getBiomeLink(biome)).append(";");
									continue next;
								}
							}
						}
					}
				}
				sb.append("\n").append(end);

				/* MINERALS */

				sb.append("<page><title>Template:DB Mineral-Biomes");
				sb.append("\n").append(begin);
				for (String mineral : minerals) {
					sb.append("\n| ").append(mineral).append(" = ").append(mineralBiomes);
					next: for (GOTBiome biome : biomes) {
						if (biome != null) {
							List<OreGenerant> oreGenerants = new ArrayList<>(biome.decorator.biomeSoils);
							oreGenerants.addAll(biome.decorator.biomeOres);
							oreGenerants.addAll(biome.decorator.biomeGems);
							for (OreGenerant oreGenerant : oreGenerants) {
								Block block = GOTReflection.getOreBlock(oreGenerant.oreGen);
								int meta = GOTReflection.getOreMeta(oreGenerant.oreGen);
								if (getBlockMetaName(block, meta).equals(mineral) || getBlockName(block).equals(mineral)) {
									sb.append("\n* ").append(getBiomeLink(biome)).append(" (").append(oreGenerant.oreChance).append("%; Y: ").append(oreGenerant.minHeight).append("-").append(oreGenerant.maxHeight).append(");");
									continue next;
								}
							}
						}
					}
				}
				sb.append("\n").append(end);

				/* TREES */

				sb.append("<page><title>Template:DB Tree-Biomes");
				sb.append("\n").append(begin);
				for (GOTTreeType tree : trees) {
					HashSet<GOTBiome> biomesTree = new HashSet<>();
					HashSet<GOTBiome> biomesVariantTree = new HashSet<>();
					next: for (GOTBiome biome : biomes) {
						if (biome != null) {
							for (WeightedTreeType weightedTreeType : biome.decorator.treeTypes) {
								if (weightedTreeType.treeType == tree) {
									biomesTree.add(biome);
									continue next;
								}
							}
							for (VariantBucket variantBucket : biome.getBiomeVariantsSmall().variantList) {
								for (WeightedTreeType weightedTreeType : variantBucket.variant.treeTypes) {
									if (weightedTreeType.treeType == tree) {
										biomesVariantTree.add(biome);
										continue next;
									}
								}
							}
						}
					}
					if (biomesTree.isEmpty() && biomesVariantTree.isEmpty()) {
						sb.append("\n| ").append(getTreeName(tree)).append(" = ").append(treeNoBiomes);
					} else {
						sb.append("\n| ").append(getTreeName(tree)).append(" = ").append(treeHasBiomes);
					}
					for (GOTBiome biome : biomesTree) {
						sb.append("\n* ").append(getBiomeLink(biome)).append(";");
					}
					for (GOTBiome biome : biomesVariantTree) {
						sb.append("\n* ").append(getBiomeLink(biome)).append(" (").append(treeVariantOnly).append(");");
					}
				}
				sb.append("\n").append(end);

				/* BIOMES */

				sb.append("<page><title>Template:DB Biome-SpawnNPC");
				sb.append("\n").append(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						List<FactionContainer> facContainers = biome.getNPCSpawnList().factionContainers;
						if (facContainers.isEmpty()) {
							sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biomeNoSpawn);
						} else {
							ArrayList<FactionContainer> spawnContainers = new ArrayList<>();
							for (FactionContainer facContainer : facContainers) {
								if (facContainer.baseWeight > 0) {
									spawnContainers.add(facContainer);
								}
							}
							if (spawnContainers.isEmpty()) {
								sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biomeConquestOnly);
							} else {
								sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biomeHasSpawn);
								for (FactionContainer facContainer : spawnContainers) {
									for (SpawnListContainer container : facContainer.spawnLists) {
										for (GOTSpawnEntry entry : container.spawnList.spawnList) {
											sb.append("\n* ").append(getEntityLink(entry.entityClass)).append("; ");
										}
									}
								}
							}
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Biome-ConquestNPC");
				sb.append("\n").append(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						List<FactionContainer> facContainers = biome.getNPCSpawnList().factionContainers;
						if (facContainers.isEmpty()) {
							sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biomeNoConquest);
						} else {
							ArrayList<FactionContainer> conqestContainers = new ArrayList<>();
							for (FactionContainer facContainer : facContainers) {
								if (facContainer.baseWeight <= 0) {
									conqestContainers.add(facContainer);
								}
							}
							if (conqestContainers.isEmpty()) {
								sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biomeSpawnOnly);
							} else {
								sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biomeHasConquest);
								EnumSet<GOTFaction> conquestFactions = EnumSet.noneOf(GOTFaction.class);
								for (FactionContainer facContainer : conqestContainers) {
									next: for (SpawnListContainer container : facContainer.spawnLists) {
										for (GOTSpawnEntry entry : container.spawnList.spawnList) {
											Entity entity = classToObjectMapping.get(entry.entityClass);
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
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Biome-Bandits");
				sb.append("\n").append(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biome.getBanditChance());
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Biome-Name");
				sb.append("\n").append(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(getBiomeName(biome));
					}
				}
				sb.append("\n").append(end);

				boolean lotrStyle = false;
				if (lotrStyle) {
					sb.append("<page><title>Template:DB Biome-Rainfall");
					sb.append("\n").append(begin);
					for (GOTBiome biome : biomes) {
						if (biome != null) {
							sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biome.rainfall);
						}
					}
					sb.append("\n").append(end);

					sb.append("<page><title>Template:DB Biome-Temperature");
					sb.append("\n").append(begin);
					for (GOTBiome biome : biomes) {
						if (biome != null) {
							sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biome.temperature);
						}
					}
				} else {
					sb.append("<page><title>Template:DB Biome-Climat");
					sb.append("\n").append(begin);
					for (GOTBiome biome : biomes) {
						if (biome != null) {
							sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biome.getClimateType());
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Biome-Variants");
				sb.append("\n").append(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						if (biome.getBiomeVariantsSmall().variantList.isEmpty()) {
							sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biomeNoVariants);
						} else {
							sb.append("\n| ").append(getBiomePagename(biome)).append(" = ");
							for (VariantBucket variantBucket : biome.getBiomeVariantsSmall().variantList) {
								sb.append("\n* ").append(getBiomeVariantName(variantBucket.variant)).append(";");
							}
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Biome-Invasions");
				sb.append("\n").append(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						if (biome.getInvasionSpawns().registeredInvasions.isEmpty()) {
							sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biomeNoInvasions);
						} else {
							sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biomeHasInvasions);
							EnumSet<GOTFaction> invasionFactions = EnumSet.noneOf(GOTFaction.class);
							next: for (GOTInvasions invasion : biome.getInvasionSpawns().registeredInvasions) {
								for (InvasionSpawnEntry entry : invasion.invasionMobs) {
									Entity entity = classToObjectMapping.get(entry.entityClass);
									if (entity instanceof GOTEntityNPC) {
										GOTFaction fac = ((GOTEntityNPC) entity).getFaction();
										invasionFactions.add(fac);
										continue next;
									}
								}
							}
							for (GOTFaction fac : invasionFactions) {
								sb.append("\n* ").append(getFactionLink(fac)).append(";");
							}
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Biome-Waypoints");
				sb.append("\n").append(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						Region region = biome.getBiomeWaypoints();
						if (region == null) {
							sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biomeNoWaypoints);
						} else {
							sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biomeHasWaypoints);
							for (GOTWaypoint wp : waypoints) {
								if (wp.region == region) {
									sb.append("\n* ").append(wp.getDisplayName()).append(" (").append(getFactionLink(wp.faction)).append(");");
								}
							}
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Biome-Achievement");
				sb.append("\n").append(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						GOTAchievement ach = biome.getBiomeAchievement();
						if (ach == null) {
							sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biomeNoAchievement);
						} else {
							sb.append("\n| ").append(getBiomePagename(biome)).append(" = \"").append(getAchievementTitle(ach)).append("\"");
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Biome-Trees");
				sb.append("\n").append(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						EnumSet<GOTTreeType> treesBiome = EnumSet.noneOf(GOTTreeType.class);
						EnumMap<GOTTreeType, GOTBiomeVariant> treesVariant = new EnumMap<>(GOTTreeType.class);
						for (WeightedTreeType weightedTreeType : biome.decorator.treeTypes) {
							treesBiome.add(weightedTreeType.treeType);
						}
						for (VariantBucket variantBucket : biome.getBiomeVariantsSmall().variantList) {
							for (WeightedTreeType weightedTreeType : variantBucket.variant.treeTypes) {
								treesVariant.put(weightedTreeType.treeType, variantBucket.variant);
							}
						}
						if (treesBiome.isEmpty() && treesVariant.isEmpty()) {
							sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biomeNoTrees);
						} else {
							EnumMap<GOTTreeType, GOTBiomeVariant> treesVariantOnly = new EnumMap<>(GOTTreeType.class);

							if (treesVariantOnly.isEmpty()) {
								sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biomeHasTreesBiomeOnly);
							} else {
								sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biomeHasTreesBiomeAndVariant);
							}
							if (!treesBiome.isEmpty()) {
								for (GOTTreeType tree : treesBiome) {
									sb.append("\n* [[").append(getTreeName(tree)).append("]];");
								}
							}
							if (!treesVariantOnly.isEmpty()) {
								for (Entry<GOTTreeType, GOTBiomeVariant> tree : treesVariantOnly.entrySet()) {
									if (!treesBiome.contains(tree.getKey())) {
										sb.append("\n* [[").append(getTreeName(tree.getKey())).append("]] (").append(getBiomeVariantName(tree.getValue()).toLowerCase()).append(")").append(";");
									}
								}
							}
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Biome-Mobs");
				sb.append("\n").append(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						List<SpawnListEntry> entries = new ArrayList<>(biome.getSpawnableList(EnumCreatureType.ambient));
						entries.addAll(biome.getSpawnableList(EnumCreatureType.waterCreature));
						entries.addAll(biome.getSpawnableList(EnumCreatureType.creature));
						entries.addAll(biome.getSpawnableList(EnumCreatureType.monster));
						entries.addAll(biome.getSpawnableList(GOTBiome.creatureType_GOTAmbient));
						if (entries.isEmpty()) {
							sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biomeNoAnimals);
						} else {
							sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biomeHasAnimals);
							for (SpawnListEntry entry : entries) {
								if (GOTEntityRegistry.classToNameMapping.containsKey(entry.entityClass)) {
									sb.append("\n* ").append(getEntityLink(entry.entityClass)).append(";");
								} else {
									sb.append("\n* ").append(getEntityVanillaName(entry.entityClass)).append(";");
								}
							}
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Biome-Minerals");
				sb.append("\n").append(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biomeMinerals);
						List<OreGenerant> oreGenerants = new ArrayList<>(biome.decorator.biomeSoils);
						oreGenerants.addAll(biome.decorator.biomeOres);
						oreGenerants.addAll(biome.decorator.biomeGems);
						for (OreGenerant oreGenerant : oreGenerants) {
							Block block = GOTReflection.getOreBlock(oreGenerant.oreGen);
							int meta = GOTReflection.getOreMeta(oreGenerant.oreGen);

							if (block instanceof GOTBlockOreGem || block instanceof BlockDirt || block instanceof GOTBlockRock) {
								sb.append("\n* [[").append(getBlockMetaName(block, meta)).append("]] (").append(oreGenerant.oreChance).append("%; Y: ").append(oreGenerant.minHeight).append("-").append(oreGenerant.maxHeight).append(");");
							} else {
								sb.append("\n* [[").append(getBlockName(block)).append("]] (").append(oreGenerant.oreChance).append("%; Y: ").append(oreGenerant.minHeight).append("-").append(oreGenerant.maxHeight).append(");");
							}
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Biome-Music");
				sb.append("\n").append(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						if (biome.getBiomeMusic() != null) {
							sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biome.getBiomeMusic().subregion);
						} else {
							sb.append("\n| ").append(getBiomePagename(biome)).append(" = N/A");
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Biome-Structures");
				sb.append("\n").append(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						if (biome.decorator.randomStructures.isEmpty()) {
							sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biomeNoStructures);
						} else {
							sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biomeHasStructures);
							for (RandomStructure structure : biome.decorator.randomStructures) {
								sb.append("\n* [[").append(getStructureName(structure.structureGen.getClass())).append("]];");
							}
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Biome-Photo");
				sb.append("\n").append(begin);
				for (GOTBiome biome : biomes) {
					if (biome != null) {
						sb.append("\n| ").append(getBiomePagename(biome)).append(" = ").append(biome.biomeName).append(" (biome).png");
					}
				}
				sb.append("\n").append(end);

				/* FACTIONS */

				sb.append("<page><title>Template:DB Faction-NPC");
				sb.append("\n").append(begin);
				for (GOTFaction fac : factions) {
					sb.append("\n| ").append(getFactionPagename(fac)).append(" = ");
					for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
						Entity entity = entityEntry.getValue();
						if (entity instanceof GOTEntityNPC && ((GOTEntityNPC) entity).getFaction() == fac && !((GOTEntityNPC) entity).isLegendaryNPC()) {
							sb.append("\n* ").append(getEntityLink(entityEntry.getKey())).append(";");
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Faction-Invasions");
				sb.append("\n").append(begin);
				for (GOTFaction fac : factions) {
					HashSet<GOTBiome> invasionBiomes = new HashSet<>();
					next: for (GOTBiome biome : biomes) {
						if (biome != null && !biome.getInvasionSpawns().registeredInvasions.isEmpty()) {
							for (GOTInvasions invasion : biome.getInvasionSpawns().registeredInvasions) {
								for (InvasionSpawnEntry entry : invasion.invasionMobs) {
									Entity entity = classToObjectMapping.get(entry.entityClass);
									if (entity instanceof GOTEntityNPC && fac == ((GOTEntityNPC) entity).getFaction()) {
										invasionBiomes.add(biome);
										continue next;
									}
								}
							}
						}
					}
					if (invasionBiomes.isEmpty()) {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(factionNoInvasion);
					} else {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(factionHasInvasion);
						for (GOTBiome biome : invasionBiomes) {
							sb.append("\n* ").append(getBiomeLink(biome)).append(";");
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Faction-Spawn");
				sb.append("\n").append(begin);
				for (GOTFaction fac : factions) {
					HashSet<GOTBiome> spawnBiomes = new HashSet<>();
					next: for (GOTBiome biome : biomes) {
						if (biome != null) {
							List<FactionContainer> facContainers = biome.getNPCSpawnList().factionContainers;
							if (!facContainers.isEmpty()) {
								ArrayList<FactionContainer> spawnContainers = new ArrayList<>();
								for (FactionContainer facContainer : facContainers) {
									if (facContainer.baseWeight > 0) {
										spawnContainers.add(facContainer);
									}
								}
								if (!spawnContainers.isEmpty()) {
									for (FactionContainer facContainer : spawnContainers) {
										for (SpawnListContainer container : facContainer.spawnLists) {
											for (GOTSpawnEntry entry : container.spawnList.spawnList) {
												Entity entity = classToObjectMapping.get(entry.entityClass);
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
					}
					if (spawnBiomes.isEmpty()) {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(factionNoSpawn);
					} else {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(factionHasSpawn);
						for (GOTBiome biome : spawnBiomes) {
							sb.append("\n* ").append(getBiomeLink(biome)).append(";");
						}
					}

				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Faction-Conquest");
				sb.append("\n").append(begin);
				for (GOTFaction fac : factions) {
					HashSet<GOTBiome> conquestBiomes = new HashSet<>();
					next: for (GOTBiome biome : biomes) {
						if (biome != null) {
							List<FactionContainer> facContainers = biome.getNPCSpawnList().factionContainers;
							if (!facContainers.isEmpty()) {
								ArrayList<FactionContainer> conquestContainers = new ArrayList<>();
								for (FactionContainer facContainer : facContainers) {
									if (facContainer.baseWeight <= 0) {
										conquestContainers.add(facContainer);
									}
								}
								if (!conquestContainers.isEmpty()) {
									for (FactionContainer facContainer : conquestContainers) {
										for (SpawnListContainer container : facContainer.spawnLists) {
											for (GOTSpawnEntry entry : container.spawnList.spawnList) {
												Entity entity = classToObjectMapping.get(entry.entityClass);
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
					}
					if (conquestBiomes.isEmpty()) {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(factionNoConquest);
					} else {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(factionHasConquest);
						for (GOTBiome biome : conquestBiomes) {
							sb.append("\n* ").append(getBiomeLink(biome)).append(";");
						}
					}

				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Faction-Ranks");
				sb.append("\n").append(begin);
				for (GOTFaction fac : factions) {
					if (fac.ranksSortedDescending.isEmpty()) {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(factionNoRanks);
					} else {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(factionHasRanks);
						for (GOTFactionRank rank : fac.ranksSortedDescending) {
							sb.append("\n* ").append(rank.getDisplayFullName()).append("/").append(rank.getDisplayFullNameFem()).append(" (+").append(rank.alignment).append(");");
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Faction-Banners");
				sb.append("\n").append(begin);
				for (GOTFaction fac : factions) {
					if (fac.factionBanners.isEmpty()) {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(factionNoBanners);
					} else {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(factionHasBanners);
						for (BannerType banner : fac.factionBanners) {
							sb.append("\n* ").append(getBannerName(banner)).append(";");
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Faction-Waypoints");
				sb.append("\n").append(begin);
				for (GOTFaction fac : factions) {
					ArrayList<GOTWaypoint> facWaypoints = new ArrayList<>();
					for (GOTWaypoint wp : waypoints) {
						if (wp.faction == fac) {
							facWaypoints.add(wp);
						}
					}
					if (facWaypoints.isEmpty()) {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(factionNoWaypoints);
					} else {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(factionHasWaypoints);
						for (GOTWaypoint wp : facWaypoints) {
							sb.append("\n* ").append(wp.getDisplayName()).append(";");
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Faction-Attr");
				sb.append("\n").append(begin);
				for (GOTFaction fac : factions) {
					ArrayList<GOTCapes> facCapes = new ArrayList<>();
					ArrayList<GOTShields> facShields = new ArrayList<>();
					for (GOTCapes cape : capes) {
						if (cape.alignmentFaction == fac) {
							facCapes.add(cape);
						}
					}
					for (GOTShields shield : shields) {
						if (shield.alignmentFaction == fac) {
							facShields.add(shield);
						}
					}

					if (facCapes.isEmpty() && facShields.isEmpty()) {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(factionNoAttr);
					} else {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ");
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
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Faction-Pledge");
				sb.append("\n").append(begin);
				for (GOTFaction fac : factions) {
					if (fac.getPledgeRank() != null) {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(fac.getPledgeRank().getDisplayName()).append("/").append(fac.getPledgeRank().getDisplayNameFem()).append(" (+").append(fac.getPledgeAlignment()).append(")");
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Faction-Chars");
				sb.append("\n").append(begin);
				for (GOTFaction fac : factions) {
					ArrayList<Class<? extends Entity>> facCharacters = new ArrayList<>();
					for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
						Entity entity = entityEntry.getValue();
						if (entity instanceof GOTEntityNPC && ((GOTEntityNPC) entity).getFaction() == fac && ((GOTEntityNPC) entity).isLegendaryNPC()) {
							facCharacters.add(entityEntry.getKey());
						}
					}
					if (facCharacters.isEmpty()) {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(factionNoCharacters);
					} else {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(factionHasCharacters);
						for (Class<? extends Entity> entityClass : facCharacters) {
							sb.append("\n* ").append(getEntityLink(entityClass)).append(";");
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Faction-Enemies");
				sb.append("\n").append(begin);
				for (GOTFaction fac1 : factions) {
					ArrayList<GOTFaction> facEnemies = new ArrayList<>();
					for (GOTFaction fac2 : factions) {
						if (fac1.isBadRelation(fac2) && fac1 != fac2) {
							facEnemies.add(fac2);
						}
					}
					sb.append("\n| ").append(getFactionPagename(fac1)).append(" = ");
					if (facEnemies.isEmpty()) {
						sb.append(factionNoEnemies);
					} else {
						boolean first = true;
						for (GOTFaction fac : facEnemies) {
							if (first) {
								first = false;
							} else {
								sb.append(" \u2022 ");
							}
							sb.append(getFactionLink(fac));
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Faction-Friends");
				sb.append("\n").append(begin);
				for (GOTFaction fac1 : factions) {
					ArrayList<GOTFaction> facFriends = new ArrayList<>();
					for (GOTFaction fac2 : factions) {
						if (fac1.isGoodRelation(fac2) && fac1 != fac2) {
							facFriends.add(fac2);
						}
					}
					sb.append("\n| ").append(getFactionPagename(fac1)).append(" = ");
					if (facFriends.isEmpty()) {
						sb.append(factionNoFriends);
					} else {
						boolean first = true;
						for (GOTFaction fac : facFriends) {
							if (first) {
								first = false;
							} else {
								sb.append(" \u2022 ");
							}
							sb.append(getFactionLink(fac));
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Faction-WarCrimes");
				sb.append("\n").append(begin);
				sb.append("\n| #default = ").append(factionNoWarCrimes);
				for (GOTFaction fac : factions) {
					if (fac.approvesWarCrimes) {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(factionHasWarCrimes);
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Faction-Codename");
				sb.append("\n").append(begin);
				for (GOTFaction fac : factions) {
					sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(fac.codeName());
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Faction-Name");
				sb.append("\n").append(begin);
				for (GOTFaction fac : factions) {
					sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(getFactionName(fac));
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Faction-Region");
				sb.append("\n").append(begin);
				for (GOTFaction fac : factions) {
					sb.append("\n| ").append(getFactionPagename(fac)).append(" = ");
					if (fac.factionRegion != null) {
						sb.append(fac.factionRegion.getRegionName());
					} else {
						sb.append("N/A");
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Faction-Structures");
				sb.append("\n").append(begin);
				for (GOTFaction fac : factions) {
					ArrayList<Class<? extends WorldGenerator>> facStructures = new ArrayList<>();
					for (Class<? extends WorldGenerator> strClass : GOTStructureRegistry.classToFactionMapping.keySet()) {
						if (GOTStructureRegistry.classToFactionMapping.get(strClass) == fac) {
							facStructures.add(strClass);
						}
					}
					if (facStructures.isEmpty()) {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ").append(factionNoStructures);
					} else {
						sb.append("\n| ").append(getFactionPagename(fac)).append(" = ");
						for (Class<? extends WorldGenerator> str : facStructures) {
							sb.append("\n* ").append(getStructureName(str)).append(";");
						}
					}
				}
				sb.append("\n").append(end);

				/* MOBS */

				sb.append("<page><title>Template:DB Mob-Spawn");
				sb.append("\n").append(begin);
				for (Class<? extends Entity> entityClass : classToObjectMapping.keySet()) {
					HashSet<GOTBiome> spawnBiomes = new HashSet<>();
					HashSet<GOTBiome> conquestBiomes = new HashSet<>();
					HashSet<GOTBiome> invasionBiomes = new HashSet<>();
					HashSet<GOTBiome> unnaturalBiomes = new HashSet<>();
					next: for (GOTBiome biome : biomes) {
						if (biome != null) {
							List<SpawnListEntry> spawnEntries = new ArrayList<>();
							List<SpawnListEntry> conquestEntries = new ArrayList<>();
							List<InvasionSpawnEntry> invasionEntries = new ArrayList<>();
							spawnEntries.addAll(biome.getSpawnableList(EnumCreatureType.ambient));
							spawnEntries.addAll(biome.getSpawnableList(EnumCreatureType.waterCreature));
							spawnEntries.addAll(biome.getSpawnableList(EnumCreatureType.creature));
							spawnEntries.addAll(biome.getSpawnableList(EnumCreatureType.monster));
							spawnEntries.addAll(biome.getSpawnableGOTAmbientList());
							for (FactionContainer facContainer : biome.getNPCSpawnList().factionContainers) {
								if (facContainer.baseWeight > 0) {
									for (SpawnListContainer container : facContainer.spawnLists) {
										spawnEntries.addAll(container.spawnList.spawnList);
									}
								} else {
									for (SpawnListContainer container : facContainer.spawnLists) {
										conquestEntries.addAll(container.spawnList.spawnList);
									}
								}
							}
							if (!biome.getInvasionSpawns().registeredInvasions.isEmpty()) {
								for (GOTInvasions invasion : biome.getInvasionSpawns().registeredInvasions) {
									invasionEntries.addAll(invasion.invasionMobs);
								}
							}
							for (SpawnListEntry entry : spawnEntries) {
								if (entry.entityClass == entityClass) {
									spawnBiomes.add(biome);
									continue next;
								}
							}
							for (SpawnListEntry entry : conquestEntries) {
								if (entry.entityClass == entityClass) {
									conquestBiomes.add(biome);
									break;
								}
							}
							for (InvasionSpawnEntry entry : invasionEntries) {
								if (entry.entityClass == entityClass) {
									invasionBiomes.add(biome);
									break;
								}
							}
						}
					}
					if (spawnBiomes.isEmpty() && conquestBiomes.isEmpty() && invasionBiomes.isEmpty()) {
						sb.append("\n| ").append(getEntityPagename(entityClass)).append(" = ").append(entityNoBiomes);
					} else {
						sb.append("\n| ").append(getEntityPagename(entityClass)).append(" = ").append(entityHasBiomes);
						for (GOTBiome biome : spawnBiomes) {
							sb.append("\n* ").append(getBiomeLink(biome)).append(";");
						}
						for (GOTBiome biome : conquestBiomes) {
							if (!invasionBiomes.contains(biome)) {
								sb.append("\n* ").append(getBiomeLink(biome)).append(" ").append(entityConquestOnly).append(";");
							}
						}
						for (GOTBiome biome : invasionBiomes) {
							if (!conquestBiomes.contains(biome)) {
								sb.append("\n* ").append(getBiomeLink(biome)).append(" ").append(entityInvasionOnly).append(";");
							}
						}
						unnaturalBiomes.addAll(conquestBiomes);
						unnaturalBiomes.addAll(invasionBiomes);
						for (GOTBiome biome : unnaturalBiomes) {
							if (conquestBiomes.contains(biome) && invasionBiomes.contains(biome)) {
								sb.append("\n* ").append(getBiomeLink(biome)).append(" ").append(entityConquestInvasion).append(";");
							}
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>DB Mob-Owner");
				sb.append("\n").append(begin);
				for (Class<? extends Entity> entityClass : hireable) {
					HashMap<Class<? extends Entity>, Class<? extends Entity>> owners = new HashMap<>();
					loop: for (Entry<Class<? extends Entity>, Entity> ownerEntry : classToObjectMapping.entrySet()) {
						if (ownerEntry.getValue() instanceof GOTUnitTradeable) {
							GOTUnitTradeEntries entries = ((GOTUnitTradeable) ownerEntry.getValue()).getUnits();
							if (!((GOTEntityNPC) ownerEntry.getValue()).isLegendaryNPC()) {
								for (GOTUnitTradeEntry entry : entries.tradeEntries) {
									if (entry.entityClass == entityClass) {
										owners.put(entityClass, ownerEntry.getKey());
										break loop;
									}
								}
							}
						}
					}
					if (owners.isEmpty()) {
						loop: for (Entry<Class<? extends Entity>, Entity> ownerEntry : classToObjectMapping.entrySet()) {
							if (ownerEntry.getValue() instanceof GOTUnitTradeable) {
								GOTUnitTradeEntries entries = ((GOTUnitTradeable) ownerEntry.getValue()).getUnits();
								if (((GOTEntityNPC) ownerEntry.getValue()).isLegendaryNPC()) {
									for (GOTUnitTradeEntry entry : entries.tradeEntries) {
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
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-Health");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					EntityLivingBase entity = (EntityLivingBase) entityEntry.getValue();
					sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = ").append(entity.getMaxHealth());
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template: DB Mob-Rideable1");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPCRideable) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-Rideable2");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTNPCMount) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-BannerBearer");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTBannerBearer) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-UnitTradeable");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTUnitTradeable) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-Tradeable");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTTradeable) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-Smith");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTTradeable.Smith) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-Mercenary");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTMercenary) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-Farmhand");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTFarmhand) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-Buys");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTTradeable) {
						GOTTradeEntries entries = ((GOTTradeable) entityEntry.getValue()).getSellPool();
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = ");
						for (GOTTradeEntry entry : entries.tradeEntries) {
							sb.append("\n* ").append(entry.tradeItem.getDisplayName()).append(": {{Coins|").append(entry.getCost()).append("}};");
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-Sells");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTTradeable) {
						GOTTradeEntries entries = ((GOTTradeable) entityEntry.getValue()).getBuyPool();
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = ");
						for (GOTTradeEntry entry : entries.tradeEntries) {
							sb.append("\n* ").append(entry.tradeItem.getDisplayName()).append(": {{Coins|").append(entry.getCost()).append("}};");
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-Character");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC && ((GOTEntityNPC) entityEntry.getValue()).isLegendaryNPC()) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-ImmuneToFrost");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC && ((GOTEntityNPC) entityEntry.getValue()).isImmuneToFrost || entityEntry.getValue() instanceof GOTBiome.ImmuneToFrost) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-ImmuneToFire");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue().isImmuneToFire()) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-ImmuneToHeat");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTBiome.ImmuneToHeat) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-SpawnInDarkness");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC && ((GOTEntityNPC) entityEntry.getValue()).spawnsInDarkness) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-Alignment");
				sb.append("\n").append(begin);
				next: for (Class<? extends Entity> entityClass : hireable) {
					for (GOTUnitTradeEntries entries : units) {
						for (GOTUnitTradeEntry entry : entries.tradeEntries) {
							if (entry.entityClass == entityClass) {
								if (entry.getPledgeType() == PledgeType.NONE || entry.alignmentRequired >= 101.0f) {
									sb.append("\n| ").append(getEntityPagename(entityClass)).append(" = ").append(entry.alignmentRequired);
								} else {
									sb.append("\n| ").append(getEntityPagename(entityClass)).append(" = +").append(100.0);
								}
								continue next;
							}
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-Marriage");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC && ((GOTEntityNPC) entityEntry.getValue()).canBeMarried) {
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = True");
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-Faction");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC) {
						GOTFaction fac = ((GOTEntityNPC) entityEntry.getValue()).getFaction();
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = ").append(getFactionLink(fac));
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-Achievement");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC) {
						GOTAchievement ach = ((GOTEntityNPC) entityEntry.getValue()).getKillAchievement();
						if (ach != null) {
							sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = \"").append(getAchievementTitle(ach)).append("\"");
						} else {
							sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = N/A");
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-Bonus");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> entityEntry : classToObjectMapping.entrySet()) {
					if (entityEntry.getValue() instanceof GOTEntityNPC) {
						float bonus = ((GOTEntityNPC) entityEntry.getValue()).getAlignmentBonus();
						sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = +").append(bonus);
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-Price");
				sb.append("\n").append(begin);
				for (GOTUnitTradeEntries entries : units) {
					for (GOTUnitTradeEntry entry : entries.tradeEntries) {
						if (entry.getPledgeType() == PledgeType.NONE) {
							sb.append("\n| ").append(getEntityPagename(entry.entityClass)).append(" = {{Coins|").append(entry.initialCost * 2).append("}}");
						} else {
							sb.append("\n| ").append(getEntityPagename(entry.entityClass)).append(" = N/A");
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-PricePledge");
				sb.append("\n").append(begin);
				for (GOTUnitTradeEntries entries : units) {
					for (GOTUnitTradeEntry entry : entries.tradeEntries) {
						sb.append("\n| ").append(getEntityPagename(entry.entityClass)).append(" = {{Coins|").append(entry.initialCost).append("}}");
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-Units");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, Entity> ownerEntry : classToObjectMapping.entrySet()) {
					if (ownerEntry.getValue() instanceof GOTUnitTradeable) {
						GOTUnitTradeEntries entries = ((GOTUnitTradeable) ownerEntry.getValue()).getUnits();
						sb.append("\n| ").append(getEntityPagename(ownerEntry.getKey())).append(" = ");
						for (GOTUnitTradeEntry entry : entries.tradeEntries) {
							if (entry.mountClass == null) {
								if (entry.getPledgeType() == PledgeType.NONE) {
									sb.append("\n* ").append(getEntityLink(entry.entityClass)).append(": {{Coins|").append(entry.initialCost * 2).append("}} ").append(noPledge).append(", {{Coins|").append(entry.initialCost).append("}} ").append(yesPledge).append("; ").append(entry.alignmentRequired).append("+ ").append(rep).append(";");
								} else if (entry.alignmentRequired < 101.0f) {
									sb.append("\n* ").append(getEntityLink(entry.entityClass)).append(": N/A ").append(noPledge).append(", {{Coins|").append(entry.initialCost).append("}} ").append(yesPledge).append("; +").append(100.0).append("+ ").append(rep).append(";");
								} else {
									sb.append("\n* ").append(getEntityLink(entry.entityClass)).append(": N/A ").append(noPledge).append(", {{Coins|").append(entry.initialCost).append("}} ").append(yesPledge).append("; +").append(entry.alignmentRequired).append("+ ").append(rep).append(";");
								}
							}
						}
					}
				}
				sb.append("\n").append(end);

				sb.append("<page><title>Template:DB Mob-Waypoint");
				sb.append("\n").append(begin);
				for (Entry<Class<? extends Entity>, GOTWaypoint> entityEntry : classToWaypointMapping.entrySet()) {
					sb.append("\n| ").append(getEntityPagename(entityEntry.getKey())).append(" = ").append(entityEntry.getValue().getDisplayName());
				}
				for (GOTWaypoint wp : GOTFixer.structures.keySet()) {
					GOTStructureBase str = GOTFixer.structures.get(wp);
					str.disable();
					str.generate(world, random, y, j, k);
					for (EntityCreature entity : GOTFixer.structures.get(wp).characters) {
						sb.append("\n| ").append(getEntityPagename(entity.getClass())).append(" = ").append(wp.getDisplayName());
					}
					str.clear();
				}
				sb.append("\n").append(end);
				sb.append("\n</mediawiki>");

				PrintWriter xml = new PrintWriter("hummel/import.xml", "UTF-8");
				xml.write(sb.toString());
				xml.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		long newTime = System.nanoTime();
		ChatComponentText chatComponentTranslation = new ChatComponentText("Generated databases in " + (newTime - time) / 1.0E9 + "s");
		usingPlayer.addChatMessage(chatComponentTranslation);
		return true;
	}

	public static String getAchievementDesc(GOTAchievement ach) {
		return StatCollector.translateToLocal("got.achievement." + ach.getCodeName() + ".desc");
	}

	public static String getAchievementTitle(GOTAchievement ach) {
		return StatCollector.translateToLocal("got.achievement." + ach.getCodeName() + ".title");
	}

	public static String getBannerName(BannerType banner) {
		return StatCollector.translateToLocal("item.got:banner." + banner.bannerName + ".name");
	}

	public static String getBiomeLink(GOTBiome biome) {
		String biomeName = getBiomeName(biome);
		String biomePagename = getBiomePagename(biome);
		if (biomeName.equals(biomePagename)) {
			return "[[" + biomeName + "]]";
		}
		return "[[" + biomePagename + "|" + biomeName + "]]";
	}

	public static String getBiomeName(GOTBiome biome) {
		return StatCollector.translateToLocal("got.biome." + biome.biomeName + ".name");
	}

	public static String getBiomePagename(GOTBiome biome) {
		return biomePageMapping.get(getBiomeName(biome));
	}

	public static String getBiomeVariantName(GOTBiomeVariant variant) {
		return StatCollector.translateToLocal("got.variant." + variant.variantName + ".name");
	}

	public static String getBlockMetaName(Block block, int meta) {
		return StatCollector.translateToLocal(block.getUnlocalizedName() + "." + meta + ".name");
	}

	public static String getBlockName(Block block) {
		return StatCollector.translateToLocal(block.getUnlocalizedName() + ".name");
	}

	public static String getCapeFilename(GOTCapes cape) {
		return "[[File:Cape " + cape.name().toLowerCase() + ".png]]";
	}

	public static String getEntityLink(Class<? extends Entity> entityClass) {
		String entityName = getEntityName(entityClass);
		String entityPagename = getEntityPagename(entityClass);
		if (entityName.equals(entityPagename)) {
			return "[[" + entityPagename + "]]";
		}
		return "[[" + entityPagename + "|" + entityName + "]]";
	}

	public static String getEntityName(Class<? extends Entity> entityClass) {
		return StatCollector.translateToLocal("entity.got." + GOTEntityRegistry.classToNameMapping.get(entityClass) + ".name");
	}

	public static String getEntityPagename(Class<? extends Entity> entityClass) {
		return entityPageMapping.get(getEntityName(entityClass));
	}

	public static String getEntityVanillaName(Class<? extends Entity> entityClass) {
		return StatCollector.translateToLocal("entity." + EntityList.classToStringMapping.get(entityClass) + ".name");
	}

	public static String getFactionLink(GOTFaction fac) {
		String facName = getFactionName(fac);
		String facPagename = getFactionPagename(fac);
		if (facName.equals(facPagename)) {
			return "[[" + facPagename + "]]";
		}
		return "[[" + facPagename + "|" + facName + "]]";
	}

	public static String getFactionName(GOTFaction fac) {
		return StatCollector.translateToLocal("got.faction." + fac.codeName() + ".name");
	}

	public static String getFactionPagename(GOTFaction fac) {
		return factionPageMapping.get(getFactionName(fac));
	}

	public static String getItemFilename(Item item) {
		return "[[File:" + item.getUnlocalizedName().substring("item.got:".length()) + ".png|32px|link=]]";
	}

	public static String getItemName(Item item) {
		return StatCollector.translateToLocal(item.getUnlocalizedName() + ".name");
	}

	public static String getShieldFilename(GOTShields shield) {
		return "[[File:Shield " + shield.name().toLowerCase() + ".png]]";
	}

	public static String getStructureName(Class<? extends WorldGenerator> structureClass) {
		return StatCollector.translateToLocal("got.structure." + GOTStructureRegistry.classToNameMapping.get(structureClass) + ".name");
	}

	public static String getTreeName(GOTTreeType tree) {
		return StatCollector.translateToLocal("got.tree." + tree.name().toLowerCase() + ".name");
	}

	public static void searchForEntities(World world) {
		for (Class<? extends Entity> entityClass : GOTEntityRegistry.entitySet) {
			classToObjectMapping.put(entityClass, GOTReflection.newEntity(entityClass, world));
		}
	}

	public static void searchForHireable(Collection<Class<? extends Entity>> hireable, Iterable<GOTUnitTradeEntries> units) {
		for (GOTUnitTradeEntries entries : units) {
			for (GOTUnitTradeEntry entry : entries.tradeEntries) {
				hireable.add(entry.entityClass);
			}
		}
	}

	public static void searchForMinerals(Iterable<GOTBiome> biomes, Collection<String> minerals) {
		for (GOTBiome biome : biomes) {
			if (biome != null) {
				List<OreGenerant> oreGenerants = new ArrayList<>(biome.decorator.biomeSoils);
				oreGenerants.addAll(biome.decorator.biomeOres);
				oreGenerants.addAll(biome.decorator.biomeGems);
				for (OreGenerant oreGenerant : oreGenerants) {
					WorldGenMinable gen = oreGenerant.oreGen;
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
	}

	public static void searchForPagenamesBiome(Iterable<GOTBiome> biomes, Iterable<GOTFaction> factions) {
		next: for (GOTBiome biome : biomes) {
			if (biome != null) {
				String preName = getBiomeName(biome);
				for (GOTFaction fac : factions) {
					if (preName.equals(getFactionName(fac))) {
						biomePageMapping.put(preName, preName + " (" + biomePage + ")");
						continue next;
					}
				}
				for (Class<? extends Entity> entityClass : GOTEntityRegistry.entitySet) {
					if (preName.equals(getEntityName(entityClass))) {
						biomePageMapping.put(preName, preName + " (" + biomePage + ")");
						continue next;
					}
				}
				biomePageMapping.put(preName, preName);
			}
		}
	}

	public static void searchForPagenamesEntity(Iterable<GOTBiome> biomes, Iterable<GOTFaction> factions) {
		next: for (Class<? extends Entity> entityClass : GOTEntityRegistry.entitySet) {
			String preName = getEntityName(entityClass);
			for (GOTBiome biome : biomes) {
				if (biome != null && preName.equals(getBiomeName(biome))) {
					entityPageMapping.put(preName, preName + " (" + entityPage + ")");
					continue next;
				}
			}
			for (GOTFaction fac : factions) {
				if (preName.equals(getFactionName(fac))) {
					entityPageMapping.put(preName, preName + " (" + entityPage + ")");
					continue next;
				}
			}
			entityPageMapping.put(preName, preName);
		}
	}

	public static void searchForPagenamesFaction(Iterable<GOTBiome> biomes, Iterable<GOTFaction> factions) {
		next: for (GOTFaction fac : factions) {
			String preName = getFactionName(fac);
			for (GOTBiome biome : biomes) {
				if (biome != null && preName.equals(getBiomeName(biome))) {
					factionPageMapping.put(preName, preName + " (" + factionPage + ")");
					continue next;
				}
			}
			for (Class<? extends Entity> entityClass : GOTEntityRegistry.entitySet) {
				if (preName.equals(getEntityName(entityClass))) {
					factionPageMapping.put(preName, preName + " (" + factionPage + ")");
					continue next;
				}
			}
			factionPageMapping.put(preName, preName);
		}
	}

	public static void searchForStructures(Iterable<GOTBiome> biomes, Collection<Class<? extends WorldGenerator>> structures) {
		for (GOTBiome biome : biomes) {
			if (biome != null && !biome.decorator.randomStructures.isEmpty()) {
				for (RandomStructure structure : biome.decorator.randomStructures) {
					structures.add(structure.structureGen.getClass());
				}
			}
		}
	}

	public enum Database {
		XML("xml"), TABLES("tables");

		public String codeName;

		Database(String name) {
			codeName = name;
		}

		public String getCodename() {
			return codeName;
		}

		public boolean matchesNameOrAlias(String name) {
			return codeName.equals(name);
		}

		public static Database forName(String name) {
			for (Database db : Database.values()) {
				if (db.matchesNameOrAlias(name)) {
					return db;
				}
			}
			return null;
		}

		public static List<String> getNames() {
			ArrayList<String> names = new ArrayList<>();
			for (Database db : Database.values()) {
				names.add(db.getCodename());
			}
			return names;
		}
	}
}