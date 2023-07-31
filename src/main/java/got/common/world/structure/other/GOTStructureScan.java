package got.common.world.structure.other;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;
import got.GOT;
import got.common.util.GOTLog;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.DimensionManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class GOTStructureScan {
	public static String strscanFormat = ".strscan";
	public static Map<String, GOTStructureScan> allLoadedScans = new HashMap<>();
	public String scanName;
	public List<ScanStepBase> scanSteps = new ArrayList<>();
	public List<GOTScanAlias> aliases = new ArrayList<>();

	public GOTStructureScan(String name) {
		scanName = name;
	}

	public static GOTStructureScan getScanByName(String name) {
		return allLoadedScans.get(name);
	}

	public static void onInit() {
		allLoadedScans.clear();
		HashMap<String, BufferedReader> scanNamesAndReaders = new HashMap<>();
		ZipFile zip = null;
		try {
			ModContainer mc = GOT.getModContainer();
			if (mc.getSource().isFile()) {
				zip = new ZipFile(mc.getSource());
				Enumeration<? extends ZipEntry> entries = zip.entries();
				while (entries.hasMoreElements()) {
					String path;
					ZipEntry entry = entries.nextElement();
					String s = entry.getName();
					if (!s.startsWith(path = "assets/got/strscan/") || !s.endsWith(strscanFormat)) {
						continue;
					}
					s = s.substring(path.length());
					int i = s.indexOf(strscanFormat);
					try {
						s = s.substring(0, i);
						BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(zip.getInputStream(entry)), Charsets.UTF_8));
						scanNamesAndReaders.put(s, reader);
					} catch (IOException e) {
						throw new RuntimeException(e);
					} catch (Exception e) {
						FMLLog.severe("Failed to onInit GOT structure scan " + s + "from zip file");
						e.printStackTrace();
					}
				}
			} else {
				File scanDir = new File(Objects.requireNonNull(GOT.class.getResource("/assets/got/strscan")).toURI());
				Collection<File> subfiles = FileUtils.listFiles(scanDir, null, true);
				for (File subfile : subfiles) {
					String s = subfile.getPath();
					s = s.substring(scanDir.getPath().length() + 1);
					int i = (s = s.replace(File.separator, "/")).indexOf(strscanFormat);
					if (i < 0) {
						FMLLog.severe("Failed to onInit GOT structure scan " + s + " from MCP folder - not in " + strscanFormat + " format");
						continue;
					}
					try {
						s = s.substring(0, i);
						BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(Files.newInputStream(subfile.toPath())), Charsets.UTF_8));
						scanNamesAndReaders.put(s, reader);
					} catch (IOException e) {
						throw new RuntimeException(e);
					} catch (Exception e) {
						FMLLog.severe("Failed to onInit GOT structure scan " + s + " from MCP folder");
						e.printStackTrace();
					}
				}
			}
		} catch (ZipException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			FMLLog.severe("Failed to onInit GOT structure scans");
			e.printStackTrace();
		}
		for (Entry<String, BufferedReader> strName : scanNamesAndReaders.entrySet()) {
			BufferedReader reader = strName.getValue();
			int curLine = 0;
			try {
				String nextLine;
				ArrayList<String> lines = new ArrayList<>();
				while ((nextLine = reader.readLine()) != null) {
					lines.add(nextLine);
				}
				reader.close();
				if (lines.isEmpty()) {
					FMLLog.severe("GOT structure scans " + strName.getKey() + " is empty!");
					continue;
				}
				GOTStructureScan scan = new GOTStructureScan(strName.getKey());
				for (String line : lines) {
					String alias;
					String s1;
					++curLine;
					if (line.isEmpty()) {
						continue;
					}
					if (line.charAt(0) == GOTScanAlias.Type.BLOCK.typeCode) {
						s1 = line.substring(1, line.length() - 1);
						scan.aliases.add(new GOTScanAlias(s1, GOTScanAlias.Type.BLOCK));
						continue;
					}
					if (line.charAt(0) == GOTScanAlias.Type.BLOCK_META.typeCode) {
						s1 = line.substring(1, line.length() - 1);
						scan.aliases.add(new GOTScanAlias(s1, GOTScanAlias.Type.BLOCK_META));
						continue;
					}
					int i = 0;
					int j = line.indexOf('.');
					String s12 = line.substring(i, j);
					int x = Integer.parseInt(s12);
					ScanStepBase step = null;
					boolean fillDown = false;
					boolean findLowest = false;
					i = j + 1;
					j = line.indexOf('.', i);
					s12 = line.substring(i, j);
					if (!s12.isEmpty() && s12.charAt(s12.length() - 1) == 'v') {
						fillDown = true;
						s12 = s12.substring(0, s12.length() - 1);
					} else if (!s12.isEmpty() && s12.charAt(s12.length() - 1) == '_') {
						findLowest = true;
						s12 = s12.substring(0, s12.length() - 1);
					}
					int y = Integer.parseInt(s12);
					i = j + 1;
					j = line.indexOf('.', i);
					s12 = line.substring(i, j);
					int z = Integer.parseInt(s12);
					i = j + 1;
					char c = line.charAt(i);
					if (c == '\"') {
						j = line.indexOf('"', i + 1);
						s12 = line.substring(i, j + 1);
						String blockID = s12.substring(1, s12.length() - 1);
						Block block = Block.getBlockFromName(blockID);
						if (block == null) {
							FMLLog.severe("GOTStrScan: Block " + blockID + " does not exist!");
							block = Blocks.stone;
						}
						i = j + 2;
						j = line.length();
						s12 = line.substring(i, j);
						int meta = Integer.parseInt(s12);
						step = new ScanStep(x, y, z, block, meta);
					} else if (c == GOTScanAlias.Type.BLOCK.typeCode) {
						j = line.indexOf(GOTScanAlias.Type.BLOCK.typeCode, i + 1);
						s12 = line.substring(i, j + 1);
						alias = s12.substring(1, s12.length() - 1);
						i = j + 2;
						j = line.length();
						s12 = line.substring(i, j);
						int meta = Integer.parseInt(s12);
						step = new ScanStepBlockAlias(x, y, z, alias, meta);
					} else if (c == GOTScanAlias.Type.BLOCK_META.typeCode) {
						j = line.indexOf(GOTScanAlias.Type.BLOCK_META.typeCode, i + 1);
						s12 = line.substring(i, j + 1);
						alias = s12.substring(1, s12.length() - 1);
						step = new ScanStepBlockMetaAlias(x, y, z, alias);
					} else if (c == '/') {
						j = line.indexOf('/', i + 1);
						s12 = line.substring(i, j + 1);
						String code = s12.substring(1, s12.length() - 1);
						if ("SKULL".equals(code)) {
							step = new ScanStepSkull(x, y, z);
						}
					}
					if (step != null) {
						step.fillDown = fillDown;
						step.findLowest = findLowest;
						step.lineNumber = curLine;
						scan.addScanStep(step);
						continue;
					}
					throw new IllegalArgumentException("Invalid scan instruction on line " + curLine);
				}
				allLoadedScans.put(scan.scanName, scan);
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (Exception e) {
				FMLLog.severe("Failed to onInit GOT structure scan " + strName.getKey() + ": error on line " + curLine);
				e.printStackTrace();
			}
		}
		if (zip != null) {
			try {
				zip.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean writeScanToFile(GOTStructureScan scan) {
		File dir = new File(DimensionManager.getCurrentSaveRootDirectory(), "got_str_scans");
		if (!dir.exists()) {
			boolean created = dir.mkdirs();
			if (!created) {
				GOTLog.logger.info("GOTStructureScan: directory wasn't created");
			}
		}
		File scanFile = new File(dir, scan.scanName + strscanFormat);
		try {
			if (!scanFile.exists()) {
				boolean created = scanFile.createNewFile();
				if (!created) {
					GOTLog.logger.info("GOTStructureScan: file wasn't created");
				}
			}
			PrintStream writer = new PrintStream(Files.newOutputStream(scanFile.toPath()), false, StandardCharsets.UTF_8.name());
			if (!scan.aliases.isEmpty()) {
				for (GOTScanAlias alias : scan.aliases) {
					writer.println(alias.getFullCode());
				}
				writer.println();
			}
			for (ScanStepBase e : scan.scanSteps) {
				writer.print(e.x);
				writer.print(".");
				writer.print(e.y);
				if (e.fillDown) {
					writer.print("v");
				}
				writer.print(".");
				writer.print(e.z);
				writer.print(".");
				if (e instanceof ScanStep) {
					ScanStep step = (ScanStep) e;
					writer.print("\"");
					String blockName = Block.blockRegistry.getNameForObject(step.block);
					if (blockName.startsWith("minecraft:")) {
						blockName = blockName.substring("minecraft:".length());
					}
					writer.print(blockName);
					writer.print("\"");
					writer.print(".");
					writer.print(step.meta);
					writer.println();
					continue;
				}
				if (e instanceof ScanStepBlockAlias) {
					ScanStepBlockAlias step = (ScanStepBlockAlias) e;
					writer.print("#");
					writer.print(step.alias);
					writer.print("#");
					writer.print(".");
					writer.print(step.meta);
					writer.println();
					continue;
				}
				if (!(e instanceof ScanStepBlockMetaAlias)) {
					continue;
				}
				ScanStepBlockMetaAlias step = (ScanStepBlockMetaAlias) e;
				writer.print("~");
				writer.print(step.alias);
				writer.print("~");
				writer.println();
			}
			writer.close();
			return true;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			GOTLog.logger.error("Error saving strscan file {}", scan.scanName);
			e.printStackTrace();
			return false;
		}
	}

	public void addScanStep(ScanStepBase e) {
		scanSteps.add(e);
	}

	public void includeAlias(GOTScanAlias alias) {
		for (GOTScanAlias existingAlias : aliases) {
			if (!existingAlias.name.equals(alias.name)) {
				continue;
			}
			return;
		}
		aliases.add(alias);
	}

	public void includeAlias(String alias, GOTScanAlias.Type type) {
		includeAlias(new GOTScanAlias(alias, type));
	}

	public static class ScanStep extends ScanStepBase {
		public Block block;
		public int meta;

		public ScanStep(int _x, int _y, int _z, Block _block, int _meta) {
			super(_x, _y, _z);
			block = _block;
			meta = _meta;
		}

		@Override
		public String getAlias() {
			return null;
		}

		@Override
		public Block getBlock(Block aliasBlock) {
			return block;
		}

		@Override
		public int getMeta(int aliasMeta) {
			return meta;
		}

		@Override
		public boolean hasAlias() {
			return false;
		}
	}

	public abstract static class ScanStepBase {
		public int x;
		public int y;
		public int z;
		public boolean fillDown;
		public boolean findLowest;
		public int lineNumber;

		protected ScanStepBase(int _x, int _y, int _z) {
			x = _x;
			y = _y;
			z = _z;
		}

		public abstract String getAlias();

		public abstract Block getBlock(Block var1);

		public abstract int getMeta(int var1);

		public abstract boolean hasAlias();
	}

	public static class ScanStepBlockAlias extends ScanStepBase {
		public String alias;
		public int meta;

		public ScanStepBlockAlias(int _x, int _y, int _z, String _alias, int _meta) {
			super(_x, _y, _z);
			alias = _alias;
			meta = _meta;
		}

		@Override
		public String getAlias() {
			return alias;
		}

		@Override
		public Block getBlock(Block aliasBlock) {
			return aliasBlock;
		}

		@Override
		public int getMeta(int aliasMeta) {
			return meta;
		}

		@Override
		public boolean hasAlias() {
			return true;
		}
	}

	public static class ScanStepBlockMetaAlias extends ScanStepBase {
		public String alias;

		public ScanStepBlockMetaAlias(int _x, int _y, int _z, String _alias) {
			super(_x, _y, _z);
			alias = _alias;
		}

		@Override
		public String getAlias() {
			return alias;
		}

		@Override
		public Block getBlock(Block aliasBlock) {
			return aliasBlock;
		}

		@Override
		public int getMeta(int aliasMeta) {
			return aliasMeta;
		}

		@Override
		public boolean hasAlias() {
			return true;
		}
	}

	public static class ScanStepSkull extends ScanStepBase {
		public ScanStepSkull(int _x, int _y, int _z) {
			super(_x, _y, _z);
		}

		@Override
		public String getAlias() {
			return null;
		}

		@Override
		public Block getBlock(Block aliasBlock) {
			return Blocks.skull;
		}

		@Override
		public int getMeta(int aliasMeta) {
			return 1;
		}

		@Override
		public boolean hasAlias() {
			return false;
		}
	}

}
