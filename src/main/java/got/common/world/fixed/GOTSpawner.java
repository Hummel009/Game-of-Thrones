package got.common.world.fixed;

import java.util.Random;

import got.common.entity.essos.legendary.GOTEntityMissandei;
import got.common.entity.essos.legendary.captain.*;
import got.common.entity.essos.legendary.quest.*;
import got.common.entity.essos.legendary.trader.*;
import got.common.entity.essos.legendary.warrior.*;
import got.common.entity.other.GOTEntityHummel009;
import got.common.entity.westeros.legendary.captain.*;
import got.common.entity.westeros.legendary.deco.*;
import got.common.entity.westeros.legendary.quest.*;
import got.common.entity.westeros.legendary.reborn.*;
import got.common.entity.westeros.legendary.trader.*;
import got.common.entity.westeros.legendary.warrior.*;
import got.common.world.map.*;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.world.World;

public class GOTSpawner {

	public static class AddamMarbrand extends GOTStructureBase {
		public AddamMarbrand(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityAddamMarbrand(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Ashemark);
		}
	}

	public static class AeronGreyjoy extends GOTStructureBase {
		public AeronGreyjoy(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityAeronGreyjoy(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.NaggaHill, 0, 0);
		}
	}

	public static class AndersYronwood extends GOTStructureBase {
		public AndersYronwood(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityAndersYronwood(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Yronwood, 1, 0);
		}
	}

	public static class AndrikTheUnsmilling extends GOTStructureBase {
		public AndrikTheUnsmilling(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityAndrikTheUnsmilling(world), world, -2, 1, -2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.DrummCastle);
		}
	}

	public static class AnyaWaynwood extends GOTStructureBase {
		public AnyaWaynwood(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityAnyaWaynwood(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.IronOak);
		}
	}

	public static class ArdrianCeltigar extends GOTStructureBase {
		public ArdrianCeltigar(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityArdrianCeltigar(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.ClawIsle);
		}
	}

	public static class Asshai extends GOTStructureBase {
		public Asshai(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityAsshaiArchmag(world), world, 0, 1, 0);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Asshai);
		}
	}

	public static class Astapor extends GOTStructureBase {
		public Astapor(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityKraznysMoNakloz(world), world, -1, 1, 0);
			spawnLegendaryNPC(new GOTEntityMissandei(world), world, -1, 1, -1);
			spawnLegendaryNPC(new GOTEntityGreyWorm(world), world, -1, 1, 1);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Astapor, -1, 0);
		}
	}

	public static class BaelorBlacktyde extends GOTStructureBase {
		public BaelorBlacktyde(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityBaelorBlacktyde(world), world, -2, 1, -2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Blacktyde);
		}
	}

	public static class BarbreyDustin extends GOTStructureBase {
		public BarbreyDustin(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityBarbreyDustin(world), world, 0, 1, 3);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Barrowtown, 0, 1);
		}
	}

	public static class BenedarBelmore extends GOTStructureBase {
		public BenedarBelmore(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityBenedarBelmore(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Strongsong);
		}
	}

	public static class BenjenStark extends GOTStructureBase {
		public BenjenStark(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityBenjenStark(world), world, 0, 1, 5);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.WhiteWood);
		}
	}

	public static class BericDayne extends GOTStructureBase {
		public BericDayne(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityBericDayne(world), world, -2, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Starfall);
		}
	}

	public static class BericDondarrion extends GOTStructureBase {
		public BericDondarrion(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityBericDondarrion.LifeStage1(world), world, 3, 1, 0);
			spawnLegendaryNPC(new GOTEntityThoros(world), world, 0, 1, 3);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.HollowHill);
		}
	}

	public static class BuGai extends GOTStructureBase {
		public BuGai(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityBuGai(world), world, 12, 1, 0);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Yin, 0, 1);
		}
	}

	public static class CasterlyRock extends GOTStructureBase {
		public CasterlyRock(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityTywinLannister(world), world, 2, 1, 0);
			spawnLegendaryNPC(new GOTEntityQyburn(world), world, -2, 1, 0);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.CasterlyRock, -1, 0);
		}
	}

	public static class ClementPiper extends GOTStructureBase {
		public ClementPiper(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityClementPiper(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.PinkmaidenCastle);
		}
	}

	public static class CleyCerwyn extends GOTStructureBase {
		public CleyCerwyn(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityCleyCerwyn(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.ServinsCastle, -1, 0);
		}
	}

	public static class DaenerysTargaryen extends GOTStructureBase {
		public DaenerysTargaryen(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityDaenerysTargaryen(world), world, 0, 5, 3);
			spawnLegendaryNPC(new GOTEntityJorahMormont(world), world, 0, 5, 3);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.VaesEfe);
		}
	}

	public static class Dagmer extends GOTStructureBase {
		public Dagmer(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityDagmer(world), world, 0, 1, 3);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Lordsport);
		}
	}

	public static class Dragonstone extends GOTStructureBase {
		public Dragonstone(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityStannisBaratheon(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityDavosSeaworth(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityMelisandra(world), world, -2, 1, 2);
			spawnLegendaryNPC(new GOTEntityShireenBaratheon(world), world, 2, 1, -2);
			spawnLegendaryNPC(new GOTEntitySelyseBaratheon(world), world, 0, 1, 2);
			spawnLegendaryNPC(new GOTEntityMatthosSeaworth(world), world, 0, 1, -2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Dragonstone);
		}
	}

	public static class Dreadfort extends GOTStructureBase {
		public Dreadfort(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityRamsayBolton(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityRooseBolton(world), world, -2, 1, -2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Dreadfort);
		}
	}

	public static class Driftmark extends GOTStructureBase {
		public Driftmark(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityMonfordVelaryon(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityAuraneWaters(world), world, 2, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Driftmark);
		}
	}

	public static class DunstanDrumm extends GOTStructureBase {
		public DunstanDrumm(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityDunstanDrumm(world), world, -2, 1, -2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.DrummCastle);
		}
	}

	public static class Ebrose extends GOTStructureBase {
		public Ebrose(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityEbrose(world), world, 0, 1, 5);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Oldtown, -1, 0);
		}
	}

	public static class EldonEstermont extends GOTStructureBase {
		public EldonEstermont(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityEldonEstermont(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Greenstone);
		}
	}

	public static class ErikIronmaker extends GOTStructureBase {
		public ErikIronmaker(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityErikIronmaker(world), world, -2, 1, -2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.RedHaven);
		}
	}

	public static class ForleyPrester extends GOTStructureBase {
		public ForleyPrester(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityForleyPrester(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Feastfires);
		}
	}

	public static class FranklynFowler extends GOTStructureBase {
		public FranklynFowler(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityFranklynFowler(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.SkyReach, 0, 1);
		}
	}

	public static class GarlanTyrell extends GOTStructureBase {
		public GarlanTyrell(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityGarlanTyrell(world), world, 2, 1, -2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.BrightwaterKeep);
		}
	}

	public static class GateOfTheMoon extends GOTStructureBase {
		public GateOfTheMoon(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityRobinArryn(world), world, 0, 1, 2);
			spawnLegendaryNPC(new GOTEntityLysaArryn(world), world, -2, 1, 0);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.GateOfTheMoon, 0, 1);
		}
	}

	public static class GeroldDayne extends GOTStructureBase {
		public GeroldDayne(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityGeroldDayne(world), world, 2, 1, -2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.HighHermitage);
		}
	}

	public static class GeroldGrafton extends GOTStructureBase {
		public GeroldGrafton(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityGeroldGrafton(world), world, 3, 1, 0);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Gulltown);
		}
	}

	public static class GilwoodHunter extends GOTStructureBase {
		public GilwoodHunter(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityGilwoodHunter(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.LongbowHall);
		}
	}

	public static class GoroldGoodbrother extends GOTStructureBase {
		public GoroldGoodbrother(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityGoroldGoodbrother(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Hammerhorn);
		}
	}

	public static class GregorClegane extends GOTStructureBase {
		public GregorClegane(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityGregorClegane.Alive(world), world, 2, 1, 0);
			spawnLegendaryNPC(new GOTEntityPolliver(world), world, -2, 1, 0);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.CleganesKeep);
		}
	}

	public static class GulianSwann extends GOTStructureBase {
		public GulianSwann(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityGulianSwann(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Stonehelm);
		}
	}

	public static class GylbertFarwynd extends GOTStructureBase {
		public GylbertFarwynd(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityGylbertFarwynd(world), world, -2, 1, -2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.LonelyLight);
		}
	}

	public static class HarmenUller extends GOTStructureBase {
		public HarmenUller(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityHarmenUller(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Hellholt);
		}
	}

	public static class HarrasHarlaw extends GOTStructureBase {
		public HarrasHarlaw(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityHarrasHarlaw(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.GreyGarden);
		}
	}

	public static class HarroldHardyng extends GOTStructureBase {
		public HarroldHardyng(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityHarroldHardyng(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.IronOak);
		}
	}

	public static class HarryStrickland extends GOTStructureBase {
		public HarryStrickland(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityHarryStrickland(world), world, -1, 1, -1);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Myr, -1, 0);
		}
	}

	public static class HarysSwyft extends GOTStructureBase {
		public HarysSwyft(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityHarysSwyft(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Cornfield);
		}
	}

	public static class HelmanTallhart extends GOTStructureBase {
		public HelmanTallhart(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityHelmanTallhart(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.TorhensSquare);
		}
	}

	public static class Highgarden extends GOTStructureBase {
		public Highgarden(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityMaceTyrell(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityOlennaTyrell(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityMargaeryTyrell(world), world, 2, 1, -2);
			spawnLegendaryNPC(new GOTEntityWillasTyrell(world), world, -2, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Highgarden, 0, -1);
		}
	}

	public static class HizdahrZoLoraq extends GOTStructureBase {
		public HizdahrZoLoraq(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityHizdahrZoLoraq(world), world, 0, 1, 1);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Meereen, 0, -1);
		}
	}

	public static class HortonRedfort extends GOTStructureBase {
		public HortonRedfort(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityHortonRedfort(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Redfort);
		}
	}

	public static class HowlandReed extends GOTStructureBase {
		public HowlandReed(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityHowlandReed(world), world, 0, 1, 5);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.GreywaterWatch);
		}
	}

	public static class Hummel009 extends GOTStructureBase {
		public Hummel009(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityHummel009(world), world, 0, 1, 0);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, 6861, 3785);
		}
	}

	public static class IllyrioMopatis extends GOTStructureBase {
		public IllyrioMopatis(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityIllyrioMopatis(world), world, 3, 1, 0);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Pentos, -1, 0);
		}
	}

	public static class JasonMallister extends GOTStructureBase {
		public JasonMallister(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityJasonMallister(world), world, 0, 1, 3);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Seagard, 0, -1);
		}
	}

	public static class JohnUmber extends GOTStructureBase {
		public JohnUmber(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityJohnUmber(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.LastHearth);
		}
	}

	public static class JonConnington extends GOTStructureBase {
		public JonConnington(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityJonConnington(world), world, 0, 1, -1);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Tyrosh);
		}
	}

	public static class JonosBracken extends GOTStructureBase {
		public JonosBracken(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityJonosBracken(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.StoneHedge, 0, 1);
		}
	}

	public static class KingsLanding extends GOTStructureBase {
		public KingsLanding(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntitySansaStark(world), world, 0, 1, 5);
			spawnLegendaryNPC(new GOTEntityShae(world), world, 0, 1, 6);
			spawnLegendaryNPC(new GOTEntityYoren(world), world, 0, 1, 4);
			return true;
		}
	}

	public static class Lannisport extends GOTStructureBase {
		public Lannisport(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityKevanLannister(world), world, 0, 1, 5);
			spawnLegendaryNPC(new GOTEntityDavenLannister(world), world, 0, 1, -5);
			spawnLegendaryNPC(new GOTEntityAmoryLorch(world), world, 5, 1, 0);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Lannisport, -1, 0);
		}
	}

	public static class LeoLefford extends GOTStructureBase {
		public LeoLefford(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityLeoLefford(world), world, 2, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.GoldenTooth, 0, 1);
		}
	}

	public static class LeytonHightower extends GOTStructureBase {
		public LeytonHightower(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityLeytonHightower(world), world, 0, 26, -5);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.HightowerLitehouse);
		}
	}

	public static class LyleCrakehall extends GOTStructureBase {
		public LyleCrakehall(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityLyleCrakehall(world), world, 2, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Crakehall, -1, 0);
		}
	}

	public static class LynCorbray extends GOTStructureBase {
		public LynCorbray(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityLynCorbray(world), world, 2, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.HeartsHome);
		}
	}

	public static class MaegeMormont extends GOTStructureBase {
		public MaegeMormont(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityMaegeMormont(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.MormontsKeep);
		}
	}

	public static class MaronVolmark extends GOTStructureBase {
		public MaronVolmark(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityMaronVolmark(world), world, -2, 1, -2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Volmark);
		}
	}

	public static class MathisRowan extends GOTStructureBase {
		public MathisRowan(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityMathisRowan(world), world, 2, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Goldengrove);
		}
	}

	public static class Mellario extends GOTStructureBase {
		public Mellario(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityMellario(world), world, 0, 1, 1);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Norvos, 0, -1);
		}
	}

	public static class Moqorro extends GOTStructureBase {
		public Moqorro(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityMoqorro(world), world, -1, 1, 0);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Volantis, -1, 0);
		}
	}

	public static class OrtonMerryweather extends GOTStructureBase {
		public OrtonMerryweather(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityOrtonMerryweather(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Longtable);
		}
	}

	public static class PaxterRedwyne extends GOTStructureBase {
		public PaxterRedwyne(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityPaxterRedwyne(world), world, 0, 1, 5);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.StarfishHarbor);
		}
	}

	public static class Pyke extends GOTStructureBase {
		public Pyke(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityBalonGreyjoy(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityYaraGreyjoy(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityTheonGreyjoy.Normal(world), world, -2, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Pyke);
		}
	}

	public static class QuennRoxton extends GOTStructureBase {
		public QuennRoxton(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityQuennRoxton(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Ring);
		}
	}

	public static class QuentenBanefort extends GOTStructureBase {
		public QuentenBanefort(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityQuentenBanefort(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Banefort);
		}
	}

	public static class QuentynMartell extends GOTStructureBase {
		public QuentynMartell(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityQuentynMartell(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Yronwood, 1, 0);
		}
	}

	public static class QuentynQorgyle extends GOTStructureBase {
		public QuentynQorgyle(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityQuentynQorgyle(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Sandstone);
		}
	}

	public static class RandyllTarly extends GOTStructureBase {
		public RandyllTarly(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityRandyllTarly(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.HornHill);
		}
	}

	public static class RickardKarstark extends GOTStructureBase {
		public RickardKarstark(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityRickardKarstark(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Karhold);
		}
	}

	public static class Riverrun extends GOTStructureBase {
		public Riverrun(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityBryndenTully(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityEdmureTully(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityHosterTully(world), world, 2, 1, -2);
			spawnLegendaryNPC(new GOTEntityRodrikCassel(world), world, -2, 1, 2);
			spawnLegendaryNPC(new GOTEntityCatelynStark(world), world, 2, 1, 0);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Riverrun, -1, 0);
		}
	}

	public static class RodrikHarlaw extends GOTStructureBase {
		public RodrikHarlaw(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityRodrikHarlaw(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.TenTowers);
		}
	}

	public static class RodrikRyswell extends GOTStructureBase {
		public RodrikRyswell(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityRodrikRyswell(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.RisvellsCastle, 0, 1);
		}
	}

	public static class SalladhorSaan extends GOTStructureBase {
		public SalladhorSaan(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntitySalladhorSaan(world), world, 0, 1, -1);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Lys);
		}
	}

	public static class SebastonFarman extends GOTStructureBase {
		public SebastonFarman(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntitySebastonFarman(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Faircastle);
		}
	}

	public static class SelwynTarth extends GOTStructureBase {
		public SelwynTarth(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntitySelwynTarth(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.EvenfallHall);
		}
	}

	public static class StormsEnd extends GOTStructureBase {
		public StormsEnd(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityRenlyBaratheon(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityLorasTyrell(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityBrienneTarth(world), world, -2, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.StormsEnd);
		}
	}

	public static class Sunspear extends GOTStructureBase {
		public Sunspear(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityOberynMartell(world), world, 3, 1, 0);
			spawnLegendaryNPC(new GOTEntityDoranMartell(world), world, 0, 1, 3);
			spawnLegendaryNPC(new GOTEntityEllaryaSand(world), world, 3, 1, 3);
			spawnLegendaryNPC(new GOTEntityAreoHotah(world), world, 0, 1, -3);
			spawnLegendaryNPC(new GOTEntityTrystaneMartell(world), world, -3, 1, 0);
			spawnLegendaryNPC(new GOTEntityArianneMartell(world), world, -3, 1, 3);
			spawnLegendaryNPC(new GOTEntityManfreyMartell(world), world, -3, 1, -3);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Sunspear);
		}
	}

	public static class SymondTempleton extends GOTStructureBase {
		public SymondTempleton(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntitySymondTempleton(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Ninestars);
		}
	}

	public static class ThreeEyedRaven extends GOTStructureBase {
		public ThreeEyedRaven(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityThreeEyedRaven(world), world, 0, 1, 0);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.ThreeEyedRavenCave);
		}
	}

	public static class TugarKhan extends GOTStructureBase {
		public TugarKhan(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityTugarKhan(world), world, 0, 5, 3);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Hojdbaatar);
		}
	}

	public static class TwinsLeft extends GOTStructureBase {
		public TwinsLeft(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityBlackWalderFrey(world), world, 5, 1, 0);
			spawnLegendaryNPC(new GOTEntityLotharFrey(world), world, 4, 1, 0);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.TwinsLeft, 1, 0);
		}
	}

	public static class TychoNestoris extends GOTStructureBase {
		public TychoNestoris(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityTychoNestoris(world), world, 0, 1, 1);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Braavos, 0, -1);
		}
	}

	public static class TytosBlackwood extends GOTStructureBase {
		public TytosBlackwood(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityTytosBlackwood(world), world, 2, 1, 0);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.RaventreeHall);
		}
	}

	public static class TytosBrax extends GOTStructureBase {
		public TytosBrax(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityTytosBrax(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Hornvale);
		}
	}

	public static class WalderFrey extends GOTStructureBase {
		public WalderFrey(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityWalderFrey(world), world, 5, 1, 0);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.TwinsRight, -2, 0);
		}
	}

	public static class WilliamMooton extends GOTStructureBase {
		public WilliamMooton(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityWilliamMooton(world), world, 0, 1, 2);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Maidenpool, 1, 0);
		}
	}

	public static class Winterfell extends GOTStructureBase {
		public Winterfell(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityRobbStark(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityHodor(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityAryaStark(world), world, 2, 1, -2);
			spawnLegendaryNPC(new GOTEntityBranStark(world), world, -2, 1, 2);
			spawnLegendaryNPC(new GOTEntityRickonStark(world), world, 0, 1, 2);
			spawnLegendaryNPC(new GOTEntityLuwin(world), world, 0, 1, -2);
			spawnLegendaryNPC(new GOTEntityOsha(world), world, 2, 1, 0);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Winterfell);
		}
	}

	public static class WymanManderly extends GOTStructureBase {
		public WymanManderly(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityWymanManderly(world), world, 0, 1, 5);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.WhiteHarbour);
		}
	}

	public static class XaroXhoanDaxos extends GOTStructureBase {
		public XaroXhoanDaxos(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityXaroXhoanDaxos(world), world, 3, 1, 0);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Qarth, 0, 1);
		}
	}

	public static class YohnRoyce extends GOTStructureBase {
		public YohnRoyce(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityYohnRoyce(world), world, 2, 1, 0);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Runestone);
		}
	}

	public static class YoungGriff extends GOTStructureBase {
		public YoungGriff(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityYoungGriff(world), world, 0, 1, -1);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Tyrosh);
		}
	}

	public static class Yunkai extends GOTStructureBase {
		public Yunkai(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityDaarioNaharis(world), world, -1, 1, 0);
			spawnLegendaryNPC(new GOTEntityRazdalMoEraz(world), world, -1, 1, 1);
			return true;
		}

		public static boolean fixedAt(int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Yunkai, -1, 0);
		}
	}
	
	public static GOTStructureBase getFixedStructure(int i, int k) {
		if (GOTSpawner.AddamMarbrand.fixedAt(i, k)) {
			return new GOTSpawner.AddamMarbrand(false);
		}
		if (GOTSpawner.AeronGreyjoy.fixedAt(i, k)) {
			return new GOTSpawner.AeronGreyjoy(false);
		}
		if (GOTSpawner.AndersYronwood.fixedAt(i, k)) {
			return new GOTSpawner.AndersYronwood(false);
		}
		if (GOTSpawner.AndrikTheUnsmilling.fixedAt(i, k)) {
			return new GOTSpawner.AndrikTheUnsmilling(false);
		}
		if (GOTSpawner.AnyaWaynwood.fixedAt(i, k)) {
			return new GOTSpawner.AnyaWaynwood(false);
		}
		if (GOTSpawner.ArdrianCeltigar.fixedAt(i, k)) {
			return new GOTSpawner.ArdrianCeltigar(false);
		}
		if (GOTSpawner.Asshai.fixedAt(i, k)) {
			return new GOTSpawner.Asshai(false);
		}
		if (GOTSpawner.Astapor.fixedAt(i, k)) {
			return new GOTSpawner.Astapor(false);
		}
		if (GOTSpawner.BaelorBlacktyde.fixedAt(i, k)) {
			return new GOTSpawner.BaelorBlacktyde(false);
		}
		if (GOTSpawner.BarbreyDustin.fixedAt(i, k)) {
			return new GOTSpawner.BarbreyDustin(false);
		}
		if (GOTSpawner.BenedarBelmore.fixedAt(i, k)) {
			return new GOTSpawner.BenedarBelmore(false);
		}
		if (GOTSpawner.BenjenStark.fixedAt(i, k)) {
			return new GOTSpawner.BenjenStark(false);
		}
		if (GOTSpawner.BericDayne.fixedAt(i, k)) {
			return new GOTSpawner.BericDayne(false);
		}
		if (GOTSpawner.BericDondarrion.fixedAt(i, k)) {
			return new GOTSpawner.BericDondarrion(false);
		}
		if (GOTSpawner.BuGai.fixedAt(i, k)) {
			return new GOTSpawner.BuGai(false);
		}
		if (GOTSpawner.CasterlyRock.fixedAt(i, k)) {
			return new GOTSpawner.CasterlyRock(false);
		}
		if (GOTSpawner.ClementPiper.fixedAt(i, k)) {
			return new GOTSpawner.ClementPiper(false);
		}
		if (GOTSpawner.CleyCerwyn.fixedAt(i, k)) {
			return new GOTSpawner.CleyCerwyn(false);
		}
		if (GOTSpawner.DaenerysTargaryen.fixedAt(i, k)) {
			return new GOTSpawner.DaenerysTargaryen(false);
		}
		if (GOTSpawner.Dagmer.fixedAt(i, k)) {
			return new GOTSpawner.Dagmer(false);
		}
		if (GOTSpawner.Dragonstone.fixedAt(i, k)) {
			return new GOTSpawner.Dragonstone(false);
		}
		if (GOTSpawner.Dreadfort.fixedAt(i, k)) {
			return new GOTSpawner.Dreadfort(false);
		}
		if (GOTSpawner.Driftmark.fixedAt(i, k)) {
			return new GOTSpawner.Driftmark(false);
		}
		if (GOTSpawner.DunstanDrumm.fixedAt(i, k)) {
			return new GOTSpawner.DunstanDrumm(false);
		}
		if (GOTSpawner.Ebrose.fixedAt(i, k)) {
			return new GOTSpawner.Ebrose(false);
		}
		if (GOTSpawner.EldonEstermont.fixedAt(i, k)) {
			return new GOTSpawner.EldonEstermont(false);
		}
		if (GOTSpawner.ErikIronmaker.fixedAt(i, k)) {
			return new GOTSpawner.ErikIronmaker(false);
		}
		if (GOTSpawner.ForleyPrester.fixedAt(i, k)) {
			return new GOTSpawner.ForleyPrester(false);
		}
		if (GOTSpawner.FranklynFowler.fixedAt(i, k)) {
			return new GOTSpawner.FranklynFowler(false);
		}
		if (GOTSpawner.GarlanTyrell.fixedAt(i, k)) {
			return new GOTSpawner.GarlanTyrell(false);
		}
		if (GOTSpawner.GateOfTheMoon.fixedAt(i, k)) {
			return new GOTSpawner.GateOfTheMoon(false);
		}
		if (GOTSpawner.GeroldDayne.fixedAt(i, k)) {
			return new GOTSpawner.GeroldDayne(false);
		}
		if (GOTSpawner.GeroldGrafton.fixedAt(i, k)) {
			return new GOTSpawner.GeroldGrafton(false);
		}
		if (GOTSpawner.GilwoodHunter.fixedAt(i, k)) {
			return new GOTSpawner.GilwoodHunter(false);
		}
		if (GOTSpawner.GoroldGoodbrother.fixedAt(i, k)) {
			return new GOTSpawner.GoroldGoodbrother(false);
		}
		if (GOTSpawner.GregorClegane.fixedAt(i, k)) {
			return new GOTSpawner.GregorClegane(false);
		}
		if (GOTSpawner.GulianSwann.fixedAt(i, k)) {
			return new GOTSpawner.GulianSwann(false);
		}
		if (GOTSpawner.GylbertFarwynd.fixedAt(i, k)) {
			return new GOTSpawner.GylbertFarwynd(false);
		}
		if (GOTSpawner.HarmenUller.fixedAt(i, k)) {
			return new GOTSpawner.HarmenUller(false);
		}
		if (GOTSpawner.HarrasHarlaw.fixedAt(i, k)) {
			return new GOTSpawner.HarrasHarlaw(false);
		}
		if (GOTSpawner.HarroldHardyng.fixedAt(i, k)) {
			return new GOTSpawner.HarroldHardyng(false);
		}
		if (GOTSpawner.HarryStrickland.fixedAt(i, k)) {
			return new GOTSpawner.HarryStrickland(false);
		}
		if (GOTSpawner.HarysSwyft.fixedAt(i, k)) {
			return new GOTSpawner.HarysSwyft(false);
		}
		if (GOTSpawner.HelmanTallhart.fixedAt(i, k)) {
			return new GOTSpawner.HelmanTallhart(false);
		}
		if (GOTSpawner.Highgarden.fixedAt(i, k)) {
			return new GOTSpawner.Highgarden(false);
		}
		if (GOTSpawner.HizdahrZoLoraq.fixedAt(i, k)) {
			return new GOTSpawner.HizdahrZoLoraq(false);
		}
		if (GOTSpawner.HortonRedfort.fixedAt(i, k)) {
			return new GOTSpawner.HortonRedfort(false);
		}
		if (GOTSpawner.HowlandReed.fixedAt(i, k)) {
			return new GOTSpawner.HowlandReed(false);
		}
		if (GOTSpawner.Hummel009.fixedAt(i, k)) {
			return new GOTSpawner.Hummel009(false);
		}
		if (GOTSpawner.IllyrioMopatis.fixedAt(i, k)) {
			return new GOTSpawner.IllyrioMopatis(false);
		}
		if (GOTSpawner.JasonMallister.fixedAt(i, k)) {
			return new GOTSpawner.JasonMallister(false);
		}
		if (GOTSpawner.JohnUmber.fixedAt(i, k)) {
			return new GOTSpawner.JohnUmber(false);
		}
		if (GOTSpawner.JonConnington.fixedAt(i, k)) {
			return new GOTSpawner.JonConnington(false);
		}
		if (GOTSpawner.JonosBracken.fixedAt(i, k)) {
			return new GOTSpawner.JonosBracken(false);
		}
		if (GOTSpawner.Lannisport.fixedAt(i, k)) {
			return new GOTSpawner.Lannisport(false);
		}
		if (GOTSpawner.LeoLefford.fixedAt(i, k)) {
			return new GOTSpawner.LeoLefford(false);
		}
		if (GOTSpawner.LeytonHightower.fixedAt(i, k)) {
			return new GOTSpawner.LeytonHightower(false);
		}
		if (GOTSpawner.LyleCrakehall.fixedAt(i, k)) {
			return new GOTSpawner.LyleCrakehall(false);
		}
		if (GOTSpawner.LynCorbray.fixedAt(i, k)) {
			return new GOTSpawner.LynCorbray(false);
		}
		if (GOTSpawner.MaegeMormont.fixedAt(i, k)) {
			return new GOTSpawner.MaegeMormont(false);
		}
		if (GOTSpawner.MaronVolmark.fixedAt(i, k)) {
			return new GOTSpawner.MaronVolmark(false);
		}
		if (GOTSpawner.MathisRowan.fixedAt(i, k)) {
			return new GOTSpawner.MathisRowan(false);
		}
		if (GOTSpawner.Mellario.fixedAt(i, k)) {
			return new GOTSpawner.Mellario(false);
		}
		if (GOTSpawner.Moqorro.fixedAt(i, k)) {
			return new GOTSpawner.Moqorro(false);
		}
		if (GOTSpawner.OrtonMerryweather.fixedAt(i, k)) {
			return new GOTSpawner.OrtonMerryweather(false);
		}
		if (GOTSpawner.PaxterRedwyne.fixedAt(i, k)) {
			return new GOTSpawner.PaxterRedwyne(false);
		}
		if (GOTSpawner.Pyke.fixedAt(i, k)) {
			return new GOTSpawner.Pyke(false);
		}
		if (GOTSpawner.QuennRoxton.fixedAt(i, k)) {
			return new GOTSpawner.QuennRoxton(false);
		}
		if (GOTSpawner.QuentenBanefort.fixedAt(i, k)) {
			return new GOTSpawner.QuentenBanefort(false);
		}
		if (GOTSpawner.QuentynMartell.fixedAt(i, k)) {
			return new GOTSpawner.QuentynMartell(false);
		}
		if (GOTSpawner.QuentynQorgyle.fixedAt(i, k)) {
			return new GOTSpawner.QuentynQorgyle(false);
		}
		if (GOTSpawner.RandyllTarly.fixedAt(i, k)) {
			return new GOTSpawner.RandyllTarly(false);
		}
		if (GOTSpawner.RickardKarstark.fixedAt(i, k)) {
			return new GOTSpawner.RickardKarstark(false);
		}
		if (GOTSpawner.Riverrun.fixedAt(i, k)) {
			return new GOTSpawner.Riverrun(false);
		}
		if (GOTSpawner.RodrikHarlaw.fixedAt(i, k)) {
			return new GOTSpawner.RodrikHarlaw(false);
		}
		if (GOTSpawner.RodrikRyswell.fixedAt(i, k)) {
			return new GOTSpawner.RodrikRyswell(false);
		}
		if (GOTSpawner.SalladhorSaan.fixedAt(i, k)) {
			return new GOTSpawner.SalladhorSaan(false);
		}
		if (GOTSpawner.SebastonFarman.fixedAt(i, k)) {
			return new GOTSpawner.SebastonFarman(false);
		}
		if (GOTSpawner.SelwynTarth.fixedAt(i, k)) {
			return new GOTSpawner.SelwynTarth(false);
		}
		if (GOTSpawner.StormsEnd.fixedAt(i, k)) {
			return new GOTSpawner.StormsEnd(false);
		}
		if (GOTSpawner.Sunspear.fixedAt(i, k)) {
			return new GOTSpawner.Sunspear(false);
		}
		if (GOTSpawner.SymondTempleton.fixedAt(i, k)) {
			return new GOTSpawner.SymondTempleton(false);
		}
		if (GOTSpawner.ThreeEyedRaven.fixedAt(i, k)) {
			return new GOTSpawner.ThreeEyedRaven(false);
		}
		if (GOTSpawner.TugarKhan.fixedAt(i, k)) {
			return new GOTSpawner.TugarKhan(false);
		}
		if (GOTSpawner.TwinsLeft.fixedAt(i, k)) {
			return new GOTSpawner.TwinsLeft(false);
		}
		if (GOTSpawner.TychoNestoris.fixedAt(i, k)) {
			return new GOTSpawner.TychoNestoris(false);
		}
		if (GOTSpawner.TytosBlackwood.fixedAt(i, k)) {
			return new GOTSpawner.TytosBlackwood(false);
		}
		if (GOTSpawner.TytosBrax.fixedAt(i, k)) {
			return new GOTSpawner.TytosBrax(false);
		}
		if (GOTSpawner.WalderFrey.fixedAt(i, k)) {
			return new GOTSpawner.WalderFrey(false);
		}
		if (GOTSpawner.WilliamMooton.fixedAt(i, k)) {
			return new GOTSpawner.WilliamMooton(false);
		}
		if (GOTSpawner.Winterfell.fixedAt(i, k)) {
			return new GOTSpawner.Winterfell(false);
		}
		if (GOTSpawner.WymanManderly.fixedAt(i, k)) {
			return new GOTSpawner.WymanManderly(false);
		}
		if (GOTSpawner.XaroXhoanDaxos.fixedAt(i, k)) {
			return new GOTSpawner.XaroXhoanDaxos(false);
		}
		if (GOTSpawner.YohnRoyce.fixedAt(i, k)) {
			return new GOTSpawner.YohnRoyce(false);
		}
		if (GOTSpawner.YoungGriff.fixedAt(i, k)) {
			return new GOTSpawner.YoungGriff(false);
		}
		if (GOTSpawner.Yunkai.fixedAt(i, k)) {
			return new GOTSpawner.Yunkai(false);
		}
		if (GOTStructureEuronShip.fixedAt(i, k)) {
			return new GOTStructureEuronShip(false);
		}
		return null;
	}
}