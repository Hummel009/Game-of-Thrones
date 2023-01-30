package got.common.database;

import java.util.*;

import got.common.entity.essos.braavos.*;
import got.common.entity.essos.dothraki.*;
import got.common.entity.essos.ghiscar.*;
import got.common.entity.essos.ibben.*;
import got.common.entity.essos.jogos.*;
import got.common.entity.essos.lys.*;
import got.common.entity.essos.myr.*;
import got.common.entity.essos.norvos.*;
import got.common.entity.essos.pentos.*;
import got.common.entity.essos.tyrosh.*;
import got.common.entity.essos.volantis.*;
import got.common.entity.essos.yiti.*;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.westeros.arryn.*;
import got.common.entity.westeros.dorne.*;
import got.common.entity.westeros.dragonstone.*;
import got.common.entity.westeros.hillmen.*;
import got.common.entity.westeros.ironborn.*;
import got.common.entity.westeros.north.*;
import got.common.entity.westeros.reach.*;
import got.common.entity.westeros.riverlands.*;
import got.common.entity.westeros.stormlands.*;
import got.common.entity.westeros.westerlands.*;
import got.common.entity.westeros.wildling.*;
import got.common.entity.westeros.wildling.thenn.*;
import got.common.faction.GOTFaction;
import got.common.item.other.GOTItemConquestHorn;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.*;

public enum GOTInvasions {
	DOTHRAKI(GOTFaction.DOTHRAKI), JOGOS(GOTFaction.JOGOS), VOLANTIS(GOTFaction.VOLANTIS), BRAAVOS(GOTFaction.BRAAVOS), PENTOS(GOTFaction.PENTOS), TYROSH(GOTFaction.TYROSH), MYR(GOTFaction.MYR), LYS(GOTFaction.LYS), NORVOS(GOTFaction.NORVOS), IRONBORN(GOTFaction.IRONBORN), WESTERLANDS(GOTFaction.WESTERLANDS), RIVERLANDS(GOTFaction.RIVERLANDS), NORTH(GOTFaction.NORTH), THENN(GOTFaction.WILDLING, "THENN"), WILDLING(GOTFaction.WILDLING), GIANT(GOTFaction.WILDLING, "GIANT"), ARRYN(GOTFaction.ARRYN), DRAGONSTONE(GOTFaction.DRAGONSTONE), STORMLANDS(GOTFaction.STORMLANDS), REACH(GOTFaction.REACH), DORNE(GOTFaction.DORNE), YI_TI(GOTFaction.YI_TI), GHISCAR(GOTFaction.GHISCAR), HILL_TRIBES(GOTFaction.HILL_TRIBES), IBBEN(GOTFaction.IBBEN);

	public GOTFaction invasionFaction;
	public String subfaction;
	public List<InvasionSpawnEntry> invasionMobs = new ArrayList<>();
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
		ItemStack horn = new ItemStack(GOTRegistry.conquestHorn);
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
		for (GOTInvasions i : GOTInvasions.values()) {
			if (i.ordinal() != ID) {
				continue;
			}
			return i;
		}
		return null;
	}

	public static GOTInvasions forName(String name) {
		for (GOTInvasions i : GOTInvasions.values()) {
			if (!i.codeName().equals(name)) {
				continue;
			}
			return i;
		}
		return null;
	}

	public static String[] listInvasionNames() {
		String[] names = new String[GOTInvasions.values().length];
		for (int i = 0; i < names.length; ++i) {
			names[i] = GOTInvasions.values()[i].codeName();
		}
		return names;
	}

	public static void preInit() {
		GOTInvasions.JOGOS.invasionIcon = GOTRegistry.nomadSword;
		GOTInvasions.DOTHRAKI.invasionIcon = GOTRegistry.nomadSword;
		GOTInvasions.VOLANTIS.invasionIcon = GOTRegistry.summerSword;
		GOTInvasions.BRAAVOS.invasionIcon = GOTRegistry.summerSword;
		GOTInvasions.NORVOS.invasionIcon = GOTRegistry.summerSword;
		GOTInvasions.PENTOS.invasionIcon = GOTRegistry.summerSword;
		GOTInvasions.TYROSH.invasionIcon = GOTRegistry.summerSword;
		GOTInvasions.GHISCAR.invasionIcon = GOTRegistry.summerSword;
		GOTInvasions.MYR.invasionIcon = GOTRegistry.summerSword;
		GOTInvasions.LYS.invasionIcon = GOTRegistry.summerSword;
		GOTInvasions.IRONBORN.invasionIcon = GOTRegistry.westerosSword;
		GOTInvasions.IBBEN.invasionIcon = GOTRegistry.flintSpear;
		GOTInvasions.WESTERLANDS.invasionIcon = GOTRegistry.westerosSword;
		GOTInvasions.RIVERLANDS.invasionIcon = GOTRegistry.westerosSword;
		GOTInvasions.ARRYN.invasionIcon = GOTRegistry.westerosSword;
		GOTInvasions.DRAGONSTONE.invasionIcon = GOTRegistry.westerosSword;
		GOTInvasions.STORMLANDS.invasionIcon = GOTRegistry.westerosSword;
		GOTInvasions.REACH.invasionIcon = GOTRegistry.westerosSword;
		GOTInvasions.DORNE.invasionIcon = GOTRegistry.westerosSword;
		GOTInvasions.NORTH.invasionIcon = GOTRegistry.westerosSword;
		GOTInvasions.THENN.invasionIcon = GOTRegistry.wildlingSword;
		GOTInvasions.WILDLING.invasionIcon = GOTRegistry.wildlingSword;
		GOTInvasions.GIANT.invasionIcon = GOTRegistry.club;
		GOTInvasions.YI_TI.invasionIcon = GOTRegistry.yitiSword;
		GOTInvasions.HILL_TRIBES.invasionIcon = GOTRegistry.trident;
		GOTInvasions.HILL_TRIBES.invasionMobs.add(new InvasionSpawnEntry(GOTEntityHillmanWarrior.class, 10));
		GOTInvasions.HILL_TRIBES.invasionMobs.add(new InvasionSpawnEntry(GOTEntityHillmanArcher.class, 5));
		GOTInvasions.HILL_TRIBES.invasionMobs.add(new InvasionSpawnEntry(GOTEntityHillmanBerserker.class, 2));
		GOTInvasions.HILL_TRIBES.invasionMobs.add(new InvasionSpawnEntry(GOTEntityHillmanBannerBearer.class, 2));
		GOTInvasions.DOTHRAKI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDothraki.class, 10));
		GOTInvasions.DOTHRAKI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDothrakiArcher.class, 5));
		GOTInvasions.IBBEN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityIbbenWarrior.class, 10));
		GOTInvasions.IBBEN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityIbbenArcher.class, 5));
		GOTInvasions.IBBEN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityIbbenBannerBearer.class, 2));
		GOTInvasions.JOGOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityJogos.class, 10));
		GOTInvasions.JOGOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityJogosArcher.class, 5));
		GOTInvasions.GIANT.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWildling.class, 10));
		GOTInvasions.GIANT.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWildlingArcher.class, 5));
		GOTInvasions.GIANT.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWildlingBannerBearer.class, 2));
		GOTInvasions.GIANT.invasionMobs.add(new InvasionSpawnEntry(GOTEntityGiant.class, 1));
		GOTInvasions.WILDLING.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWildling.class, 10));
		GOTInvasions.WILDLING.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWildlingArcher.class, 5));
		GOTInvasions.WILDLING.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWildlingBannerBearer.class, 2));
		GOTInvasions.THENN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityThenn.class, 10));
		GOTInvasions.THENN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityThennArcher.class, 5));
		GOTInvasions.THENN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityThennBerserker.class, 3));
		GOTInvasions.THENN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityThennBannerBearer.class, 2));

		GOTInvasions.YI_TI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityYiTiSoldier.class, 10));
		GOTInvasions.YI_TI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityYiTiSoldierCrossbower.class, 5));
		GOTInvasions.YI_TI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityYiTiSamurai.class, 3));
		GOTInvasions.YI_TI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityYiTiFireThrower.class, 2));
		GOTInvasions.YI_TI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityYiTiBombardier.class, 1));
		GOTInvasions.YI_TI.invasionMobs.add(new InvasionSpawnEntry(GOTEntityYiTiBannerBearer.class, 2));

		GOTInvasions.VOLANTIS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityVolantisSoldier.class, 10));
		GOTInvasions.VOLANTIS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityVolantisSoldierArcher.class, 5));
		GOTInvasions.VOLANTIS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityVolantisBannerBearer.class, 2));

		GOTInvasions.GHISCAR.invasionMobs.add(new InvasionSpawnEntry(GOTEntityGhiscarCorsair.class, 10));
		GOTInvasions.GHISCAR.invasionMobs.add(new InvasionSpawnEntry(GOTEntityGhiscarLevyman.class, 5));
		GOTInvasions.GHISCAR.invasionMobs.add(new InvasionSpawnEntry(GOTEntityGhiscarLevymanArcher.class, 3));

		GOTInvasions.BRAAVOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityBraavosSoldier.class, 10));
		GOTInvasions.BRAAVOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityBraavosSoldierArcher.class, 5));
		GOTInvasions.BRAAVOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityBraavosBannerBearer.class, 2));

		GOTInvasions.NORVOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityNorvosLevyman.class, 10));
		GOTInvasions.NORVOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityNorvosLevymanArcher.class, 5));
		GOTInvasions.NORVOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityNorvosBannerBearer.class, 2));

		GOTInvasions.TYROSH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityTyroshSoldier.class, 10));
		GOTInvasions.TYROSH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityTyroshSoldierArcher.class, 5));
		GOTInvasions.TYROSH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityTyroshBannerBearer.class, 2));

		GOTInvasions.MYR.invasionMobs.add(new InvasionSpawnEntry(GOTEntityMyrSoldier.class, 10));
		GOTInvasions.MYR.invasionMobs.add(new InvasionSpawnEntry(GOTEntityMyrSoldier.class, 5));
		GOTInvasions.MYR.invasionMobs.add(new InvasionSpawnEntry(GOTEntityMyrBannerBearer.class, 2));

		GOTInvasions.LYS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityLysSoldier.class, 10));
		GOTInvasions.LYS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityLysSoldierArcher.class, 5));
		GOTInvasions.LYS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityLysBannerBearer.class, 2));

		GOTInvasions.PENTOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityPentosLevyman.class, 10));
		GOTInvasions.PENTOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityPentosLevymanArcher.class, 5));
		GOTInvasions.PENTOS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityPentosBannerBearer.class, 2));

		GOTInvasions.IRONBORN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityIronbornSoldier.class, 10));
		GOTInvasions.IRONBORN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityIronbornSoldierArcher.class, 5));
		GOTInvasions.IRONBORN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityIronbornBannerBearer.class, 2));

		GOTInvasions.WESTERLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWesterlandsSoldier.class, 10));
		GOTInvasions.WESTERLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWesterlandsSoldierArcher.class, 5));
		GOTInvasions.WESTERLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityWesterlandsBannerBearer.class, 2));

		GOTInvasions.RIVERLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityRiverlandsSoldier.class, 10));
		GOTInvasions.RIVERLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityRiverlandsSoldierArcher.class, 5));
		GOTInvasions.RIVERLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityRiverlandsBannerBearer.class, 2));

		GOTInvasions.ARRYN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityArrynSoldier.class, 10));
		GOTInvasions.ARRYN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityArrynSoldierArcher.class, 5));
		GOTInvasions.ARRYN.invasionMobs.add(new InvasionSpawnEntry(GOTEntityArrynBannerBearer.class, 2));

		GOTInvasions.DRAGONSTONE.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDragonstoneSoldier.class, 10));
		GOTInvasions.DRAGONSTONE.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDragonstoneSoldierArcher.class, 5));
		GOTInvasions.DRAGONSTONE.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDragonstoneBannerBearer.class, 2));

		GOTInvasions.STORMLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityStormlandsSoldier.class, 10));
		GOTInvasions.STORMLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityStormlandsSoldierArcher.class, 5));
		GOTInvasions.STORMLANDS.invasionMobs.add(new InvasionSpawnEntry(GOTEntityStormlandsBannerBearer.class, 2));

		GOTInvasions.REACH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityReachSoldier.class, 10));
		GOTInvasions.REACH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityReachSoldierArcher.class, 5));
		GOTInvasions.REACH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityReachBannerBearer.class, 2));

		GOTInvasions.DORNE.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDorneSoldier.class, 10));
		GOTInvasions.DORNE.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDorneSoldierArcher.class, 5));
		GOTInvasions.DORNE.invasionMobs.add(new InvasionSpawnEntry(GOTEntityDorneBannerBearer.class, 2));

		GOTInvasions.NORTH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityNorthSoldier.class, 10));
		GOTInvasions.NORTH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityNorthSoldierArcher.class, 5));
		GOTInvasions.NORTH.invasionMobs.add(new InvasionSpawnEntry(GOTEntityNorthBannerBearer.class, 2));
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
