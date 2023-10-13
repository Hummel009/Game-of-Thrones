package got.common.database;

import java.util.ArrayList;
import java.util.Collection;

import got.common.entity.essos.braavos.GOTEntityBraavosBannerBearer;
import got.common.entity.essos.braavos.GOTEntityBraavosSoldier;
import got.common.entity.essos.braavos.GOTEntityBraavosSoldierArcher;
import got.common.entity.essos.dothraki.GOTEntityDothraki;
import got.common.entity.essos.dothraki.GOTEntityDothrakiArcher;
import got.common.entity.essos.ghiscar.GOTEntityGhiscarCorsair;
import got.common.entity.essos.ghiscar.GOTEntityGhiscarLevyman;
import got.common.entity.essos.ghiscar.GOTEntityGhiscarLevymanArcher;
import got.common.entity.essos.ibben.GOTEntityIbbenArcher;
import got.common.entity.essos.ibben.GOTEntityIbbenBannerBearer;
import got.common.entity.essos.ibben.GOTEntityIbbenWarrior;
import got.common.entity.essos.jogos.GOTEntityJogos;
import got.common.entity.essos.jogos.GOTEntityJogosArcher;
import got.common.entity.essos.lys.GOTEntityLysBannerBearer;
import got.common.entity.essos.lys.GOTEntityLysSoldier;
import got.common.entity.essos.lys.GOTEntityLysSoldierArcher;
import got.common.entity.essos.myr.GOTEntityMyrBannerBearer;
import got.common.entity.essos.myr.GOTEntityMyrSoldier;
import got.common.entity.essos.norvos.GOTEntityNorvosBannerBearer;
import got.common.entity.essos.norvos.GOTEntityNorvosLevyman;
import got.common.entity.essos.norvos.GOTEntityNorvosLevymanArcher;
import got.common.entity.essos.pentos.GOTEntityPentosBannerBearer;
import got.common.entity.essos.pentos.GOTEntityPentosLevyman;
import got.common.entity.essos.pentos.GOTEntityPentosLevymanArcher;
import got.common.entity.essos.tyrosh.GOTEntityTyroshBannerBearer;
import got.common.entity.essos.tyrosh.GOTEntityTyroshSoldier;
import got.common.entity.essos.tyrosh.GOTEntityTyroshSoldierArcher;
import got.common.entity.essos.volantis.GOTEntityVolantisBannerBearer;
import got.common.entity.essos.volantis.GOTEntityVolantisSoldier;
import got.common.entity.essos.volantis.GOTEntityVolantisSoldierArcher;
import got.common.entity.essos.yiti.*;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.westeros.arryn.GOTEntityArrynBannerBearer;
import got.common.entity.westeros.arryn.GOTEntityArrynSoldier;
import got.common.entity.westeros.arryn.GOTEntityArrynSoldierArcher;
import got.common.entity.westeros.dorne.GOTEntityDorneBannerBearer;
import got.common.entity.westeros.dorne.GOTEntityDorneSoldier;
import got.common.entity.westeros.dorne.GOTEntityDorneSoldierArcher;
import got.common.entity.westeros.dragonstone.GOTEntityDragonstoneBannerBearer;
import got.common.entity.westeros.dragonstone.GOTEntityDragonstoneSoldier;
import got.common.entity.westeros.dragonstone.GOTEntityDragonstoneSoldierArcher;
import got.common.entity.westeros.hillmen.GOTEntityHillmanArcher;
import got.common.entity.westeros.hillmen.GOTEntityHillmanBannerBearer;
import got.common.entity.westeros.hillmen.GOTEntityHillmanBerserker;
import got.common.entity.westeros.hillmen.GOTEntityHillmanWarrior;
import got.common.entity.westeros.ironborn.GOTEntityIronbornBannerBearer;
import got.common.entity.westeros.ironborn.GOTEntityIronbornSoldier;
import got.common.entity.westeros.ironborn.GOTEntityIronbornSoldierArcher;
import got.common.entity.westeros.north.GOTEntityNorthBannerBearer;
import got.common.entity.westeros.north.GOTEntityNorthSoldier;
import got.common.entity.westeros.north.GOTEntityNorthSoldierArcher;
import got.common.entity.westeros.reach.GOTEntityReachBannerBearer;
import got.common.entity.westeros.reach.GOTEntityReachSoldier;
import got.common.entity.westeros.reach.GOTEntityReachSoldierArcher;
import got.common.entity.westeros.riverlands.GOTEntityRiverlandsBannerBearer;
import got.common.entity.westeros.riverlands.GOTEntityRiverlandsSoldier;
import got.common.entity.westeros.riverlands.GOTEntityRiverlandsSoldierArcher;
import got.common.entity.westeros.stormlands.GOTEntityStormlandsBannerBearer;
import got.common.entity.westeros.stormlands.GOTEntityStormlandsSoldier;
import got.common.entity.westeros.stormlands.GOTEntityStormlandsSoldierArcher;
import got.common.entity.westeros.westerlands.GOTEntityWesterlandsBannerBearer;
import got.common.entity.westeros.westerlands.GOTEntityWesterlandsSoldier;
import got.common.entity.westeros.westerlands.GOTEntityWesterlandsSoldierArcher;
import got.common.entity.westeros.wildling.GOTEntityGiant;
import got.common.entity.westeros.wildling.GOTEntityWildling;
import got.common.entity.westeros.wildling.GOTEntityWildlingArcher;
import got.common.entity.westeros.wildling.GOTEntityWildlingBannerBearer;
import got.common.entity.westeros.wildling.thenn.GOTEntityThenn;
import got.common.entity.westeros.wildling.thenn.GOTEntityThennArcher;
import got.common.entity.westeros.wildling.thenn.GOTEntityThennBannerBearer;
import got.common.entity.westeros.wildling.thenn.GOTEntityThennBerserker;
import got.common.faction.GOTFaction;
import got.common.item.other.GOTItemConquestHorn;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.util.WeightedRandom;

public enum GOTInvasions {
	DOTHRAKI(GOTFaction.DOTHRAKI), JOGOS(GOTFaction.JOGOS), VOLANTIS(GOTFaction.VOLANTIS), BRAAVOS(GOTFaction.BRAAVOS), PENTOS(GOTFaction.PENTOS), TYROSH(GOTFaction.TYROSH), MYR(GOTFaction.MYR), LYS(GOTFaction.LYS), NORVOS(GOTFaction.NORVOS), IRONBORN(GOTFaction.IRONBORN), WESTERLANDS(GOTFaction.WESTERLANDS), RIVERLANDS(GOTFaction.RIVERLANDS), NORTH(GOTFaction.NORTH), THENN(GOTFaction.WILDLING, "THENN"), WILDLING(GOTFaction.WILDLING), GIANT(GOTFaction.WILDLING, "GIANT"), ARRYN(GOTFaction.ARRYN), DRAGONSTONE(GOTFaction.DRAGONSTONE), STORMLANDS(GOTFaction.STORMLANDS), REACH(GOTFaction.REACH), DORNE(GOTFaction.DORNE), YI_TI(GOTFaction.YI_TI), GHISCAR(GOTFaction.GHISCAR), HILL_TRIBES(GOTFaction.HILL_TRIBES), IBBEN(GOTFaction.IBBEN);

	public GOTFaction invasionFaction;
	public String subfaction;
	public Collection<InvasionSpawnEntry> invasionMobs = new ArrayList<>();
	public Item invasionIcon;

	GOTInvasions(GOTFaction f) {
		this(f, null);
	}

	GOTInvasions(GOTFaction f, String s) {
		invasionFaction = f;
		subfaction = s;
	}

	public String codeName() {
		StringBuilder s = new StringBuilder(invasionFaction.codeName());
		if (subfaction != null) {
			s.append("_").append(subfaction);
		}
		return s.toString();
	}

	public String codeNameHorn() {
		return "got.invasion." + codeName() + ".horn";
	}

	public ItemStack createConquestHorn() {
		ItemStack horn = new ItemStack(GOTItems.conquestHorn);
		GOTItemConquestHorn.setInvasionType(horn, this);
		return horn;
	}

	public ItemStack getInvasionIcon() {
		Item sword = invasionIcon;
		if (sword == null) {
			sword = Items.iron_sword;
		}
		return new ItemStack(sword);
	}

	public String invasionName() {
		if (subfaction == null) {
			return invasionFaction.factionName();
		}
		return StatCollector.translateToLocal("got.invasion." + codeName());
	}

	public static GOTInvasions forID(int ID) {
		for (GOTInvasions i : values()) {
			if (i.ordinal() != ID) {
				continue;
			}
			return i;
		}
		return null;
	}

	public static GOTInvasions forName(String name) {
		for (GOTInvasions i : values()) {
			if (!i.codeName().equals(name)) {
				continue;
			}
			return i;
		}
		return null;
	}

	public static String[] listInvasionNames() {
		String[] names = new String[values().length];
		for (int i = 0; i < names.length; ++i) {
			names[i] = values()[i].codeName();
		}
		return names;
	}

	public static void preInit() {
		JOGOS.invasionIcon = GOTItems.nomadSword;
		DOTHRAKI.invasionIcon = GOTItems.nomadSword;
		VOLANTIS.invasionIcon = GOTItems.summerSword;
		BRAAVOS.invasionIcon = GOTItems.summerSword;
		NORVOS.invasionIcon = GOTItems.summerSword;
		PENTOS.invasionIcon = GOTItems.summerSword;
		TYROSH.invasionIcon = GOTItems.summerSword;
		GHISCAR.invasionIcon = GOTItems.summerSword;
		MYR.invasionIcon = GOTItems.summerSword;
		LYS.invasionIcon = GOTItems.summerSword;
		IRONBORN.invasionIcon = GOTItems.westerosSword;
		IBBEN.invasionIcon = GOTItems.flintSpear;
		WESTERLANDS.invasionIcon = GOTItems.westerosSword;
		RIVERLANDS.invasionIcon = GOTItems.westerosSword;
		ARRYN.invasionIcon = GOTItems.westerosSword;
		DRAGONSTONE.invasionIcon = GOTItems.westerosSword;
		STORMLANDS.invasionIcon = GOTItems.westerosSword;
		REACH.invasionIcon = GOTItems.westerosSword;
		DORNE.invasionIcon = GOTItems.westerosSword;
		NORTH.invasionIcon = GOTItems.westerosSword;
		THENN.invasionIcon = GOTItems.wildlingSword;
		WILDLING.invasionIcon = GOTItems.wildlingSword;
		GIANT.invasionIcon = GOTItems.club;
		YI_TI.invasionIcon = GOTItems.yitiSword;
		HILL_TRIBES.invasionIcon = GOTItems.trident;
		HILL_TRIBES.invasionMobs.add(new InvasionSpawnEntry(GOTEntityHillmanWarrior.class, 10));
		HILL_TRIBES.invasionMobs.add(new InvasionSpawnEntry(GOTEntityHillmanArcher.class, 5));
		HILL_TRIBES.invasionMobs.add(new InvasionSpawnEntry(GOTEntityHillmanBerserker.class, 2));
		HILL_TRIBES.invasionMobs.add(new InvasionSpawnEntry(GOTEntityHillmanBannerBearer.class, 2));
		DOTHRAKI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDothraki.class, 10));
		DOTHRAKI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDothrakiArcher.class, 5));
		IBBEN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityIbbenWarrior.class, 10));
		IBBEN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityIbbenArcher.class, 5));
		IBBEN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityIbbenBannerBearer.class, 2));
		JOGOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityJogos.class, 10));
		JOGOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityJogosArcher.class, 5));
		GIANT.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWildling.class, 10));
		GIANT.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWildlingArcher.class, 5));
		GIANT.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWildlingBannerBearer.class, 2));
		GIANT.invasionMobs.add(new InvasionSpawnEntry(GOTEntityGiant.class, 1));
		WILDLING.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWildling.class, 10));
		WILDLING.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWildlingArcher.class, 5));
		WILDLING.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWildlingBannerBearer.class, 2));
		THENN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityThenn.class, 10));
		THENN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityThennArcher.class, 5));
		THENN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityThennBerserker.class, 3));
		THENN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityThennBannerBearer.class, 2));

		YI_TI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityYiTiSoldier.class, 10));
		YI_TI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityYiTiSoldierCrossbower.class, 5));
		YI_TI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityYiTiSamurai.class, 3));
		YI_TI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityYiTiFireThrower.class, 2));
		YI_TI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityYiTiBombardier.class, 1));
		YI_TI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityYiTiBannerBearer.class, 2));

		VOLANTIS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityVolantisSoldier.class, 10));
		VOLANTIS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityVolantisSoldierArcher.class, 5));
		VOLANTIS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityVolantisBannerBearer.class, 2));

		GHISCAR.invasionMobs.add(new InvasionSpawnEntry(GOTEntityGhiscarCorsair.class, 10));
		GHISCAR.invasionMobs.add(new InvasionSpawnEntry(GOTEntityGhiscarLevyman.class, 5));
		GHISCAR.invasionMobs.add(new InvasionSpawnEntry(GOTEntityGhiscarLevymanArcher.class, 3));

		BRAAVOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityBraavosSoldier.class, 10));
		BRAAVOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityBraavosSoldierArcher.class, 5));
		BRAAVOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityBraavosBannerBearer.class, 2));

		NORVOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityNorvosLevyman.class, 10));
		NORVOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityNorvosLevymanArcher.class, 5));
		NORVOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityNorvosBannerBearer.class, 2));

		TYROSH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityTyroshSoldier.class, 10));
		TYROSH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityTyroshSoldierArcher.class, 5));
		TYROSH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityTyroshBannerBearer.class, 2));

		MYR.invasionMobs.add(new InvasionSpawnEntry(GOTEntityMyrSoldier.class, 10));
		MYR.invasionMobs.add(new InvasionSpawnEntry(GOTEntityMyrSoldier.class, 5));
		MYR.invasionMobs.add(new InvasionSpawnEntry(GOTEntityMyrBannerBearer.class, 2));

		LYS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityLysSoldier.class, 10));
		LYS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityLysSoldierArcher.class, 5));
		LYS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityLysBannerBearer.class, 2));

		PENTOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityPentosLevyman.class, 10));
		PENTOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityPentosLevymanArcher.class, 5));
		PENTOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityPentosBannerBearer.class, 2));

		IRONBORN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityIronbornSoldier.class, 10));
		IRONBORN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityIronbornSoldierArcher.class, 5));
		IRONBORN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityIronbornBannerBearer.class, 2));

		WESTERLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWesterlandsSoldier.class, 10));
		WESTERLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWesterlandsSoldierArcher.class, 5));
		WESTERLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWesterlandsBannerBearer.class, 2));

		RIVERLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityRiverlandsSoldier.class, 10));
		RIVERLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityRiverlandsSoldierArcher.class, 5));
		RIVERLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityRiverlandsBannerBearer.class, 2));

		ARRYN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityArrynSoldier.class, 10));
		ARRYN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityArrynSoldierArcher.class, 5));
		ARRYN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityArrynBannerBearer.class, 2));

		DRAGONSTONE.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDragonstoneSoldier.class, 10));
		DRAGONSTONE.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDragonstoneSoldierArcher.class, 5));
		DRAGONSTONE.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDragonstoneBannerBearer.class, 2));

		STORMLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityStormlandsSoldier.class, 10));
		STORMLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityStormlandsSoldierArcher.class, 5));
		STORMLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityStormlandsBannerBearer.class, 2));

		REACH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityReachSoldier.class, 10));
		REACH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityReachSoldierArcher.class, 5));
		REACH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityReachBannerBearer.class, 2));

		DORNE.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDorneSoldier.class, 10));
		DORNE.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDorneSoldierArcher.class, 5));
		DORNE.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDorneBannerBearer.class, 2));

		NORTH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityNorthSoldier.class, 10));
		NORTH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityNorthSoldierArcher.class, 5));
		NORTH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityNorthBannerBearer.class, 2));
	}

	public static class InvasionSpawnEntry extends WeightedRandom.Item {
		public Class<? extends GOTEntityNPC> entityClass;

		public InvasionSpawnEntry(Class<? extends GOTEntityNPC> c, int chance) {
			super(chance);
			entityClass = c;
		}

		public Class<? extends GOTEntityNPC> getEntityClass() {
			return entityClass;
		}
	}

}
