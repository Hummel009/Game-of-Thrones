package got.common.world.genlayer;

import com.google.common.math.IntMath;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;
import got.GOT;
import got.common.GOTDimension;
import got.common.world.biome.GOTBiome;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import org.apache.logging.log4j.Level;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class GOTGenLayerWorld extends GOTGenLayer {
	public static int scalePower = 7;
	public static byte[] biomeImageData;
	public static int originX = 810;
	public static int originZ = 730;
	public static int scale = IntMath.pow(2, 7);
	public static int imageWidth;
	public static int imageHeight;
	public static String imageName;

	public GOTGenLayerWorld() {
		super(0L);
		if (!loadedBiomeImage()) {
			try {
				BufferedImage biomeImage = null;
				imageName = "assets/got/textures/map/map.png";
				ModContainer mc = GOT.getModContainer();
				if (mc.getSource().isFile()) {
					ZipFile zip = new ZipFile(mc.getSource());
					Enumeration<? extends ZipEntry> entries = zip.entries();
					while (entries.hasMoreElements()) {
						ZipEntry entry = entries.nextElement();
						if (!entry.getName().equals(imageName)) {
							continue;
						}
						biomeImage = ImageIO.read(zip.getInputStream(entry));
					}
					zip.close();
				} else {
					File file = new File(Objects.requireNonNull(GOT.class.getResource("/" + imageName)).toURI());
					biomeImage = ImageIO.read(Files.newInputStream(file.toPath()));
				}
				if (biomeImage == null) {
					throw new RuntimeException("Could not onInit GOT biome map image");
				}
				imageWidth = biomeImage.getWidth();
				imageHeight = biomeImage.getHeight();
				int[] colors = biomeImage.getRGB(0, 0, imageWidth, imageHeight, null, 0, imageWidth);
				biomeImageData = new byte[imageWidth * imageHeight];
				for (int i = 0; i < colors.length; ++i) {
					int color = colors[i];
					Integer biomeID = GOTDimension.GAME_OF_THRONES.colorsToBiomeIDs.get(color);
					if (biomeID != null) {
						biomeImageData[i] = biomeID.byteValue();
						continue;
					}
					FMLLog.log(Level.ERROR, "Found unknown biome on map " + Integer.toHexString(color));
					biomeImageData[i] = (byte) GOTBiome.ocean.biomeID;
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
	}

	public static GOTGenLayer[] createWorld(GOTDimension dim, WorldType worldType) {
		int i;

		GOTGenLayer rivers = new GOTGenLayerRiverInit(100L);
		rivers = GOTGenLayerZoom.magnify(1000L, rivers, 10);
		rivers = new GOTGenLayerRiver(1L, rivers);
		rivers = new GOTGenLayerSmooth(1000L, rivers);
		rivers = GOTGenLayerZoom.magnify(1000L, rivers, 1);
		GOTGenLayer biomeSubtypes = new GOTGenLayerBiomeSubtypesInit(3000L);
		biomeSubtypes = GOTGenLayerZoom.magnify(3000L, biomeSubtypes, 2);
		GOTGenLayer biomes = new GOTGenLayerWorld();
		if (worldType == GOT.worldTypeGOTClassic) {
			GOTGenLayer oceans = new GOTGenLayerClassicOcean(2012L);
			oceans = GOTGenLayerZoom.magnify(200L, oceans, 3);
			oceans = new GOTGenLayerClassicRemoveOcean(400L, oceans);
			biomes = new GOTGenLayerClassicBiomes(2013L, oceans, dim);
			biomes = GOTGenLayerZoom.magnify(300L, biomes, 2);
		}
		GOTGenLayer mapRivers = new GOTGenLayerExtractMapRivers(5000L, biomes);
		biomes = new GOTGenLayerRemoveMapRivers(1000L, biomes, dim);
		biomes = new GOTGenLayerBiomeSubtypes(1000L, biomes, biomeSubtypes);
		biomes = GOTGenLayerZoom.magnify(1000L, biomes, 1);
		biomes = new GOTGenLayerBeach(1000L, biomes, dim, GOTBiome.ocean);
		biomes = new GOTGenLayerRedBeach(1000L, biomes, dim, GOTBiome.bleedingSea);
		biomes = GOTGenLayerZoom.magnify(1000L, biomes, 2);
		biomes = GOTGenLayerZoom.magnify(1000L, biomes, 2);
		biomes = new GOTGenLayerSmooth(1000L, biomes);
		GOTGenLayer variants = new GOTGenLayerBiomeVariants(200L);
		variants = GOTGenLayerZoom.magnify(200L, variants, 8);
		GOTGenLayer variantsSmall = new GOTGenLayerBiomeVariants(300L);
		variantsSmall = GOTGenLayerZoom.magnify(300L, variantsSmall, 6);
		GOTGenLayer lakes = new GOTGenLayerBiomeVariantsLake(100L, null, 0).setLakeFlags(1);
		for (i = 1; i <= 5; ++i) {
			lakes = new GOTGenLayerZoom(200L + i, lakes);
			if (i <= 2) {
				lakes = new GOTGenLayerBiomeVariantsLake(300L + i, lakes, i).setLakeFlags(1);
			}
			if (i != 3) {
				continue;
			}
			lakes = new GOTGenLayerBiomeVariantsLake(500L, lakes, i).setLakeFlags(2, 4);
		}
		for (i = 0; i < 4; ++i) {
			mapRivers = new GOTGenLayerMapRiverZoom(4000L + i, mapRivers);
		}
		mapRivers = new GOTGenLayerNarrowRivers(3000L, mapRivers, 6);
		mapRivers = GOTGenLayerZoom.magnify(4000L, mapRivers, 1);
		rivers = new GOTGenLayerIncludeMapRivers(5000L, rivers, mapRivers);
		return new GOTGenLayer[]{biomes, variants, variantsSmall, lakes, rivers};
	}

	public static int getBiomeImageID(int x, int z) {
		int index = z * imageWidth + x;
		return biomeImageData[index] & 0xFF;
	}

	public static GOTBiome getBiomeOrOcean(int mapX, int mapZ) {
		int biomeID = mapX >= 0 && mapX < imageWidth && mapZ >= 0 && mapZ < imageHeight ? getBiomeImageID(mapX, mapZ) : GOTBiome.ocean.biomeID;
		return GOTDimension.GAME_OF_THRONES.biomeList[biomeID];
	}

	public static boolean loadedBiomeImage() {
		return biomeImageData != null;
	}

	@Override
	public int[] getInts(World world, int i, int k, int xSize, int zSize) {
		int[] intArray = GOTIntCache.get(world).getIntArray(xSize * zSize);
		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				int i2 = i + i1 + 810;
				int k2 = k + k1 + 730;
				intArray[i1 + k1 * xSize] = i2 < 0 || i2 >= imageWidth || k2 < 0 || k2 >= imageHeight ? GOTBiome.ocean.biomeID : getBiomeImageID(i2, k2);
			}
		}
		return intArray;
	}
}
