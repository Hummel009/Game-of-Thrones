package got.common.item.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.GOTBannerProtection;
import got.common.GOTConfig;
import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTCreativeTabs;
import got.common.entity.other.inanimate.GOTEntityBanner;
import got.common.entity.other.inanimate.GOTEntityBannerWall;
import got.common.faction.GOTFaction;
import got.common.faction.GOTReputationValues;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.*;

public class GOTItemBanner extends Item {
	@SideOnly(Side.CLIENT)
	private IIcon iconBase;

	@SideOnly(Side.CLIENT)
	private IIcon iconOverlay;

	public GOTItemBanner() {
		setCreativeTab(GOTCreativeTabs.TAB_BANNER);
		setMaxStackSize(64);
		setMaxDamage(0);
		setHasSubtypes(true);
		setFull3D();
	}

	private static BannerType getBannerType(int i) {
		return BannerType.forID(i);
	}

	public static BannerType getBannerType(ItemStack itemstack) {
		if (itemstack.getItem() instanceof GOTItemBanner) {
			return getBannerType(itemstack.getItemDamage());
		}
		return null;
	}

	public static NBTTagCompound getProtectionData(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("GOTBannerData")) {
			return itemstack.getTagCompound().getCompoundTag("GOTBannerData");
		}
		return null;
	}

	public static boolean hasChoiceToKeepOriginalOwner(EntityPlayer entityplayer) {
		return entityplayer.capabilities.isCreativeMode;
	}

	public static boolean isHoldingBannerWithExistingProtection(EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.getHeldItem();
		if (itemstack != null && itemstack.getItem() instanceof GOTItemBanner) {
			NBTTagCompound protectData = getProtectionData(itemstack);
			return protectData != null && !protectData.hasNoTags();
		}
		return false;
	}

	public static void setProtectionData(ItemStack itemstack, NBTTagCompound data) {
		if (data == null) {
			if (itemstack.getTagCompound() != null) {
				itemstack.getTagCompound().removeTag("GOTBannerData");
			}
		} else {
			if (itemstack.getTagCompound() == null) {
				itemstack.setTagCompound(new NBTTagCompound());
			}
			itemstack.getTagCompound().setTag("GOTBannerData", data);
		}
	}

	private static boolean shouldKeepOriginalOwnerOnPlacement(EntityPlayer entityplayer) {
		return hasChoiceToKeepOriginalOwner(entityplayer) && entityplayer.isSneaking();
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("rawtypes")
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		NBTTagCompound protectData = getProtectionData(itemstack);
		if (protectData != null) {
			list.add(StatCollector.translateToLocal("item.got.banner.protect"));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack itemstack, int pass) {
		EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
		if (pass == 0) {
			return 0x816641;
		}
		if (itemstack == entityplayer.getHeldItem()) {
			return 0xffffff;
		}
		return getBannerType(itemstack.getItemDamage()).getFaction().getEggColor();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamageForRenderPass(int i, int pass) {
		if (pass == 0) {
			return iconBase;
		}
		return iconOverlay;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("rawtypes")
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (BannerType type : BannerType.BANNER_TYPES) {
			list.add(new ItemStack(item, 1, type.getBannerID()));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + '.' + getBannerType(itemstack).getBannerName();
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		int side1 = side;
		int j1 = j;
		BannerType bannerType = getBannerType(itemstack);
		NBTTagCompound protectData = getProtectionData(itemstack);
		if (world.getBlock(i, j1, k).isReplaceable(world, i, j1, k)) {
			side1 = 1;
		} else if (side1 == 1) {
			++j1;
		}
		if (side1 == 0) {
			return false;
		}
		if (side1 == 1) {
			if (!entityplayer.canPlayerEdit(i, j1, k, 1, itemstack)) {
				return false;
			}
			Block block = world.getBlock(i, j1 - 1, k);
			int meta = world.getBlockMetadata(i, j1 - 1, k);
			if (block.isSideSolid(world, i, j1 - 1, k, ForgeDirection.UP)) {
				int protectRange;
				if (GOTConfig.allowBannerProtection && !entityplayer.capabilities.isCreativeMode && (protectRange = GOTBannerProtection.getProtectionRange(block, meta)) > 0) {
					GOTFaction faction = bannerType.getFaction();
					if (GOTLevelData.getData(entityplayer).getReputation(faction) < 1.0f) {
						if (!world.isRemote) {
							GOTReputationValues.notifyReputationNotHighEnough(entityplayer, 1.0f, faction);
						}
						return false;
					}
					if (!world.isRemote && GOTBannerProtection.isProtected(world, i, j1, k, GOTBannerProtection.forPlayer(entityplayer, GOTBannerProtection.Permission.FULL), false, protectRange)) {
						entityplayer.addChatMessage(new ChatComponentTranslation("got.chat.alreadyProtected"));
						return false;
					}
				}
				if (!world.isRemote) {
					GOTEntityBanner banner = new GOTEntityBanner(world);
					banner.setLocationAndAngles(i + 0.5, j1, k + 0.5, 90.0f * (MathHelper.floor_double(entityplayer.rotationYaw * 4.0f / 360.0f + 0.5) & 3), 0.0f);
					if (world.checkNoEntityCollision(banner.boundingBox) && world.getCollidingBoundingBoxes(banner, banner.boundingBox).isEmpty() && !world.isAnyLiquid(banner.boundingBox) && world.getEntitiesWithinAABB(GOTEntityBanner.class, banner.boundingBox).isEmpty()) {
						banner.setBannerType(bannerType);
						if (protectData != null) {
							banner.readProtectionFromNBT(protectData);
						}
						if (banner.getPlacingPlayer() == null || !shouldKeepOriginalOwnerOnPlacement(entityplayer)) {
							banner.setPlacingPlayer(entityplayer);
						}
						world.spawnEntityInWorld(banner);
						if (banner.isProtectingTerritory()) {
							GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.useBanner);
						}
						world.playSoundAtEntity(banner, Blocks.planks.stepSound.func_150496_b(), (Blocks.planks.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.planks.stepSound.getPitch() * 0.8f);
						--itemstack.stackSize;
						return true;
					}
					banner.setDead();
				}
			}
		} else {
			if (!entityplayer.canPlayerEdit(i, j1, k, side1, itemstack)) {
				return false;
			}
			if (!world.isRemote) {
				int l = Direction.facingToDirection[side1];
				GOTEntityBannerWall banner = new GOTEntityBannerWall(world, i, j1, k, l);
				if (banner.onValidSurface()) {
					banner.setBannerType(bannerType);
					if (protectData != null) {
						banner.setProtectData((NBTTagCompound) protectData.copy());
					}
					world.spawnEntityInWorld(banner);
					world.playSoundAtEntity(banner, Blocks.planks.stepSound.func_150496_b(), (Blocks.planks.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.planks.stepSound.getPitch() * 0.8f);
					--itemstack.stackSize;
					return true;
				}
				banner.setDead();
			}
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		iconBase = iconregister.registerIcon(getIconString() + "_base");
		iconOverlay = iconregister.registerIcon(getIconString() + "_overlay");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	public enum BannerType {
		WALKER(613, "walker", GOTFaction.WHITE_WALKER), WILDLING(495, "wildling", GOTFaction.WILDLING), THENN(496, "thenn", GOTFaction.WILDLING), REDBEARD(497, "redbeard", GOTFaction.WILDLING), NIGHT(499, "night", GOTFaction.NIGHT_WATCH), ROBB(1, "robb", GOTFaction.NORTH), EDDARD(0, "eddard", GOTFaction.NORTH), ROBBBOOK(2, "robbBook", GOTFaction.NORTH), UMBER(3, "umber", GOTFaction.NORTH), UMBERBOOK(4, "umberBook", GOTFaction.NORTH), BOLTON(5, "bolton", GOTFaction.NORTH), BOLTONBOOK(6, "boltonbook", GOTFaction.NORTH), DUSTIN(7, "dustin", GOTFaction.NORTH), BARBREY(8, "barbrey", GOTFaction.NORTH), KARSTARK(9, "karstark", GOTFaction.NORTH), KARSTARKBOOK(10, "karstarkBook", GOTFaction.NORTH), LOCKE(11, "locke", GOTFaction.NORTH), MANDERLY(12, "manderly", GOTFaction.NORTH), MANDERLYBOOK(13, "manderlyBook", GOTFaction.NORTH), MORMONT(14, "mormont", GOTFaction.NORTH), MORMONTBOOK(15, "mormontBook", GOTFaction.NORTH), REED(16, "reed", GOTFaction.NORTH), REEDBOOK(17, "reedBook", GOTFaction.NORTH), REEDHBO(18, "reedHBO", GOTFaction.NORTH), RYSWELL(19, "ryswell", GOTFaction.NORTH), RODRIKRYSWELL(20, "rodrikryswell", GOTFaction.NORTH), CERWYN(21, "cerwyn", GOTFaction.NORTH), WHITEHILL(22, "whitehill", GOTFaction.NORTH), WHITEHILLBOOK(23, "whitehillBook", GOTFaction.NORTH), FLINTWW(24, "flintWW", GOTFaction.NORTH), FLINTFF(25, "flintFF", GOTFaction.NORTH), HORNWOOD(26, "hornwood", GOTFaction.NORTH), HORNWOODBOOK(27, "hornwoodBook", GOTFaction.NORTH), GLOVER(28, "glover", GOTFaction.NORTH), TALLHART(29, "tallhart", GOTFaction.NORTH), WOOLFIELD(30, "woolfield", GOTFaction.NORTH), GLENMORE(31, "glenmore", GOTFaction.NORTH), DORMUND(32, "dormund", GOTFaction.NORTH), CASSEL(33, "cassel", GOTFaction.NORTH), CONDON(34, "condon", GOTFaction.NORTH), MARSH(35, "marsh", GOTFaction.NORTH), MAZIN(36, "mazin", GOTFaction.NORTH), MOLLEN(37, "mollen", GOTFaction.NORTH), POOLE(38, "poole", GOTFaction.NORTH), STOUT(39, "stout", GOTFaction.NORTH), SLATE(40, "slate", GOTFaction.NORTH), WELLS(41, "wells", GOTFaction.NORTH), FENN(42, "fenn", GOTFaction.NORTH), BURLEY(43, "burley", GOTFaction.NORTH), WULL(44, "wull", GOTFaction.NORTH), LIDDLE(45, "liddle", GOTFaction.NORTH), NORREY(46, "norrey", GOTFaction.NORTH), KNOTT(47, "knott", GOTFaction.NORTH), FIRSTFLINT(48, "firstFlint", GOTFaction.NORTH), HARCLAY(49, "harclay", GOTFaction.NORTH), FORRESTER(50, "forrester", GOTFaction.NORTH), FORRESTERBOOK(51, "forresterBook", GOTFaction.NORTH), CROWL(52, "crowl", GOTFaction.NORTH), MAGNAR(53, "magnar", GOTFaction.NORTH), STANE(54, "stane", GOTFaction.NORTH), ASHWOOD(55, "ashwood", GOTFaction.NORTH), BOLE(56, "bole", GOTFaction.NORTH), BRANCH(57, "branch", GOTFaction.NORTH), BRANFIELD(58, "branfield", GOTFaction.NORTH), DEGORE(59, "degore", GOTFaction.NORTH), IRONSMITH(60, "ironsmith", GOTFaction.NORTH), LAKE(61, "lake", GOTFaction.NORTH), LIGHTFOOT(62, "lightfoot", GOTFaction.NORTH), MOSS(63, "moss", GOTFaction.NORTH), RYDER(64, "ryder", GOTFaction.NORTH), TOWERSN(65, "towersN", GOTFaction.NORTH), WATERMAN(66, "waterman", GOTFaction.NORTH), WOODS(67, "woods", GOTFaction.NORTH), JONSNOW(68, "jonsnow", GOTFaction.NORTH), JONKING(69, "jonking", GOTFaction.NORTH), STARKD(70, "starkD", GOTFaction.NORTH), GREYSTARK(550, "greystark", GOTFaction.NORTH), FISHERSS(551, "fisherSS", GOTFaction.NORTH), FLINTBH(552, "flintBH", GOTFaction.NORTH), FROST(553, "frost", GOTFaction.NORTH), WOODFOOT(554, "woodfoot", GOTFaction.NORTH), FIRSTMEN(555, "firstmen", GOTFaction.NORTH), LAUGHINGTREE(600, "laughingTree", GOTFaction.NORTH), GREYJOY(157, "greyjoy", GOTFaction.IRONBORN), GREYJOYBOOK(158, "greyjoyBook", GOTFaction.IRONBORN), EURON(159, "euron", GOTFaction.IRONBORN), EURONBOOK(160, "euronBook", GOTFaction.IRONBORN), VICTARION(161, "victarion", GOTFaction.IRONBORN), BLACKTYDE(162, "blacktyde", GOTFaction.IRONBORN), BOTLEY(163, "botley", GOTFaction.IRONBORN), WYNCH(164, "wynch", GOTFaction.IRONBORN), VOLMARK(165, "volmark", GOTFaction.IRONBORN), GOODBROTHER(166, "goodbrother", GOTFaction.IRONBORN), GOODBROTHERCL(167, "goodbrotherCL", GOTFaction.IRONBORN), GOODBROTHERCSK(168, "goodbrotherCSK", GOTFaction.IRONBORN), GOODBROTHERD(169, "goodbrotherD", GOTFaction.IRONBORN), GOODBROTHERO(170, "goodbrotherO", GOTFaction.IRONBORN), GOODBROTHERS(171, "goodbrotherS", GOTFaction.IRONBORN), DRUMM(172, "drumm", GOTFaction.IRONBORN), MERLYN(173, "merlyn", GOTFaction.IRONBORN), ORKWOOD(174, "orkwood", GOTFaction.IRONBORN), SALTCLIFFE(175, "saltcliffe", GOTFaction.IRONBORN), SUNDERLY(176, "sunderly", GOTFaction.IRONBORN), TAWNEY(177, "tawney", GOTFaction.IRONBORN), FARWYND(178, "farwynd", GOTFaction.IRONBORN), FARWYNDLL(179, "farwyndLL", GOTFaction.IRONBORN), HARLAW(180, "harlaw", GOTFaction.IRONBORN), KENNINGH(181, "kenningH", GOTFaction.IRONBORN), CODD(182, "codd", GOTFaction.IRONBORN), MYRE(183, "myre", GOTFaction.IRONBORN), IRONMAKER(184, "ironmaker", GOTFaction.IRONBORN), SPARR(185, "sparr", GOTFaction.IRONBORN), STONETREE(186, "stonetree", GOTFaction.IRONBORN), STONEHOUSE(187, "stonehouse", GOTFaction.IRONBORN), HARLAWTG(188, "harlawTG", GOTFaction.IRONBORN), HARLAWHH(189, "harlawHH", GOTFaction.IRONBORN), HARLAWGG(190, "harlawGG", GOTFaction.IRONBORN), HARLAWHALL(191, "harlawHall", GOTFaction.IRONBORN), GORMONDGOODBROTHER(192, "gormondgoodbrother", GOTFaction.IRONBORN), GRANGOODBROTHER(193, "grangoodbrother", GOTFaction.IRONBORN), GREYIRON(569, "greyiron", GOTFaction.IRONBORN), HOARE(570, "hoare", GOTFaction.IRONBORN), DALTON(571, "dalton", GOTFaction.IRONBORN), LANNISTER(194, "lannister", GOTFaction.WESTERLANDS), PRESTER(210, "prester", GOTFaction.WESTERLANDS), LANNISTERB(195, "lannisterB", GOTFaction.WESTERLANDS), LANNISTERC(196, "lannisterC", GOTFaction.WESTERLANDS), LANNISTERBOOK(197, "lannisterBook", GOTFaction.WESTERLANDS), BANEFORT(198, "banefort", GOTFaction.WESTERLANDS), BANEFORTBOOK(199, "banefortBook", GOTFaction.WESTERLANDS), BRAX(200, "brax", GOTFaction.WESTERLANDS), WESTERLING(201, "westerling", GOTFaction.WESTERLANDS), JAST(202, "jast", GOTFaction.WESTERLANDS), KENNINGK(203, "kenningK", GOTFaction.WESTERLANDS), CRAKEHALL(204, "crakehall", GOTFaction.WESTERLANDS), LEFFORD(205, "lefford", GOTFaction.WESTERLANDS), LYDDEN(206, "lydden", GOTFaction.WESTERLANDS), MARBRAND(207, "marbrand", GOTFaction.WESTERLANDS), MORELAND(208, "moreland", GOTFaction.WESTERLANDS), PLUMM(209, "plumm", GOTFaction.WESTERLANDS), SARWYCK(211, "sarwyck", GOTFaction.WESTERLANDS), SERRETT(212, "serrett", GOTFaction.WESTERLANDS), SERRETTBOOK(213, "serrettBook", GOTFaction.WESTERLANDS), STACKSPEAR(214, "stackspear", GOTFaction.WESTERLANDS), FARMAN(215, "farman", GOTFaction.WESTERLANDS), FALWELL(216, "falwell", GOTFaction.WESTERLANDS), ESTREN(217, "estren", GOTFaction.WESTERLANDS), WESTFORD(218, "westford", GOTFaction.WESTERLANDS), VIKARY(219, "vikary", GOTFaction.WESTERLANDS), GREENFIELD(220, "greenfield", GOTFaction.WESTERLANDS), YEW(221, "yew", GOTFaction.WESTERLANDS), CLEGANE(222, "clegane", GOTFaction.WESTERLANDS), LORCH(223, "lorch", GOTFaction.WESTERLANDS), RUTTIGER(224, "ruttiger", GOTFaction.WESTERLANDS), SWYFT(225, "swyft", GOTFaction.WESTERLANDS), HETHERSPOON(226, "hetherspoon", GOTFaction.WESTERLANDS), BETTLEY(227, "bettley", GOTFaction.WESTERLANDS), BROOM(228, "broom", GOTFaction.WESTERLANDS), LANNISPORT(229, "lannisport", GOTFaction.WESTERLANDS), LANNISTERD(230, "lannisterD", GOTFaction.WESTERLANDS), LANNISTERTYGETT(231, "lannisterTygett", GOTFaction.WESTERLANDS), PAYNE(232, "payne", GOTFaction.WESTERLANDS), SARSFIELD(233, "sarsfield", GOTFaction.WESTERLANDS), TURNBERRY(234, "turnberry", GOTFaction.WESTERLANDS), FOOTE(235, "foote", GOTFaction.WESTERLANDS), YARWYCK(236, "yarwyck", GOTFaction.WESTERLANDS), SPICER(237, "spicer", GOTFaction.WESTERLANDS), ALGOOD(238, "algood", GOTFaction.WESTERLANDS), CLIFTON(239, "clifton", GOTFaction.WESTERLANDS), DOGGETT(240, "doggett", GOTFaction.WESTERLANDS), DROX(241, "drox", GOTFaction.WESTERLANDS), FERREN(242, "ferren", GOTFaction.WESTERLANDS), FREYRIVERRUN(243, "freyriverrun", GOTFaction.WESTERLANDS), HAWTHORNE(245, "hawthorne", GOTFaction.WESTERLANDS), KYNDALL(246, "kyndall", GOTFaction.WESTERLANDS), LANNETT(247, "lannett", GOTFaction.WESTERLANDS), LANNY(248, "lanny", GOTFaction.WESTERLANDS), LANTELL(249, "lantell", GOTFaction.WESTERLANDS), MYATT(250, "myatt", GOTFaction.WESTERLANDS), PECKLEDON(251, "peckledon", GOTFaction.WESTERLANDS), TYRION(252, "tyrion", GOTFaction.WESTERLANDS), AMORYLORCH(253, "amorylorch", GOTFaction.WESTERLANDS), CASTERLY(572, "casterly", GOTFaction.WESTERLANDS), PARREN(573, "parren", GOTFaction.WESTERLANDS), REYNE(574, "reyne", GOTFaction.WESTERLANDS), REYNEBOOK(575, "reyneBook", GOTFaction.WESTERLANDS), TARBECK(576, "tarbeck", GOTFaction.WESTERLANDS), TYBOLT(577, "tybolt", GOTFaction.WESTERLANDS), TULLY(71, "tully", GOTFaction.RIVERLANDS), TULLYBOOK(72, "tullyBook", GOTFaction.RIVERLANDS), BUTTERWELL(73, "butterwell", GOTFaction.RIVERLANDS), BLACKWOOD(74, "blackwood", GOTFaction.RIVERLANDS), BLACKWOODBOOK(75, "blackwoodBook", GOTFaction.RIVERLANDS), BRACKEN(76, "bracken", GOTFaction.RIVERLANDS), BRACKENBOOK(77, "brackenBook", GOTFaction.RIVERLANDS), VYPREN(78, "vypren", GOTFaction.RIVERLANDS), VANCEWR(79, "vanceWR", GOTFaction.RIVERLANDS), VANCEA(80, "vanceA", GOTFaction.RIVERLANDS), GOODBROOK(81, "goodbrook", GOTFaction.RIVERLANDS), DARRY(82, "darry", GOTFaction.RIVERLANDS), LYCHESTER(83, "lychester", GOTFaction.RIVERLANDS), MALLISTER(84, "mallister", GOTFaction.RIVERLANDS), JAREMYMALLISTER(85, "jaremymallister", GOTFaction.RIVERLANDS), MOOTON(86, "mooton", GOTFaction.RIVERLANDS), PIPER(87, "piper", GOTFaction.RIVERLANDS), RYGER(88, "ryger", GOTFaction.RIVERLANDS), ROOTE(89, "roote", GOTFaction.RIVERLANDS), SMALLWOOD(90, "smallwood", GOTFaction.RIVERLANDS), WHENT(91, "whent", GOTFaction.RIVERLANDS), FREY(92, "frey", GOTFaction.RIVERLANDS), FREYBOOK(93, "freyBook", GOTFaction.RIVERLANDS), AEMONRIVERS(94, "aemonrivers", GOTFaction.RIVERLANDS), BENFREY(95, "benfrey", GOTFaction.RIVERLANDS),
		BIGWALDER(96, "bigwalder", GOTFaction.RIVERLANDS), LITTLEWALDER(97, "littlewalder", GOTFaction.RIVERLANDS), WALDER(98, "walder", GOTFaction.RIVERLANDS), CHARLTON(99, "charlton", GOTFaction.RIVERLANDS), COX(100, "cox", GOTFaction.RIVERLANDS), PAEGE(101, "paege", GOTFaction.RIVERLANDS), WODE(102, "wode", GOTFaction.RIVERLANDS), HEDDLE(103, "heddle", GOTFaction.RIVERLANDS), HAIGH(104, "haigh", GOTFaction.RIVERLANDS), ERENFORD(105, "erenford", GOTFaction.RIVERLANDS), GRELL(106, "grell", GOTFaction.RIVERLANDS), WAYN(107, "wayn", GOTFaction.RIVERLANDS), HARLTON(108, "harlton", GOTFaction.RIVERLANDS), LOTHSTON(109, "lothston", GOTFaction.RIVERLANDS), STRONG(110, "strong", GOTFaction.RIVERLANDS), BLANETREE(111, "blanetree", GOTFaction.RIVERLANDS), HAWICK(112, "hawick", GOTFaction.RIVERLANDS), LOLLISTON(113, "lolliston", GOTFaction.RIVERLANDS), SHAWNEY(114, "shawney", GOTFaction.RIVERLANDS), BLACKFISH(115, "blackfish", GOTFaction.RIVERLANDS), BLACKFISHBOOK(116, "blackfishBook", GOTFaction.RIVERLANDS), JUSTMAN(556, "justman", GOTFaction.RIVERLANDS), QOHERYS(557, "qoherys", GOTFaction.RIVERLANDS), TOWERS(558, "towers", GOTFaction.RIVERLANDS), TEAGUE(559, "teague", GOTFaction.RIVERLANDS), FISHER(560, "fisher", GOTFaction.RIVERLANDS), HARROWAY(561, "harroway", GOTFaction.RIVERLANDS), MUDD(562, "mudd", GOTFaction.RIVERLANDS), ARLAN(563, "arlan", GOTFaction.RIVERLANDS), ROBERTBLACKWOOD(564, "robertblackwood", GOTFaction.RIVERLANDS), ROLANDBLACKWOOD(565, "rolandblackwood", GOTFaction.RIVERLANDS), ROGERBLACKWOOD(566, "rogerblackwood", GOTFaction.RIVERLANDS), HILLMEN(498, "hillmen", GOTFaction.HILL_TRIBES), ARRYN(117, "arryn", GOTFaction.ARRYN), ARRYNBOOK(118, "arrynBook", GOTFaction.ARRYN), BELMORE(119, "belmore", GOTFaction.ARRYN), GRAFTON(120, "grafton", GOTFaction.ARRYN), EGEN(121, "egen", GOTFaction.ARRYN), ELESHAM(122, "elesham", GOTFaction.ARRYN), CORBRAY(123, "corbray", GOTFaction.ARRYN), CORBRAYBOOK(124, "corbrayBook", GOTFaction.ARRYN), COLDWATER(125, "coldwater", GOTFaction.ARRYN), LYNDERLY(126, "lynderly", GOTFaction.ARRYN), MELCOLM(127, "melcolm", GOTFaction.ARRYN), REDFORT(128, "redfort", GOTFaction.ARRYN), ROYCE(129, "royce", GOTFaction.ARRYN), ROYCEBOOK(130, "royceBook", GOTFaction.ARRYN), ROYCEGM(131, "royceGM", GOTFaction.ARRYN), TOLLETT(132, "tollett", GOTFaction.ARRYN), WAYNWOOD(133, "waynwood", GOTFaction.ARRYN), HUNTER(134, "hunter", GOTFaction.ARRYN), HERSY(135, "hersy", GOTFaction.ARRYN), WAXLEY(136, "waxley", GOTFaction.ARRYN), MOORE(137, "moore", GOTFaction.ARRYN), TEMPLETON(138, "templeton", GOTFaction.ARRYN), HARDYNG(139, "hardyng", GOTFaction.ARRYN), HARROLDHARDYNG(140, "harroldhardyng", GOTFaction.ARRYN), SHETTGTOWN(141, "shettgtown", GOTFaction.ARRYN), SHETTGTOWER(142, "shettgtower", GOTFaction.ARRYN), SUNDERLAND(143, "sunderland", GOTFaction.ARRYN), BORRELL(144, "borrell", GOTFaction.ARRYN), TORRENT(145, "torrent", GOTFaction.ARRYN), UPCLIFFE(146, "upcliff", GOTFaction.ARRYN), ARRYNGT(147, "arrynGT", GOTFaction.ARRYN), BAELISHOLD(148, "baelishold", GOTFaction.ARRYN), BREAKSTONE(149, "breakstone", GOTFaction.ARRYN), DONNIGER(150, "donniger", GOTFaction.ARRYN), PRYOR(151, "pryor", GOTFaction.ARRYN), LIPPS(152, "lipps", GOTFaction.ARRYN), LONGTORPE(153, "longtorpe", GOTFaction.ARRYN), RUTHERMONT(154, "ruthermont", GOTFaction.ARRYN), WYDMAN(155, "wydman", GOTFaction.ARRYN), HUGH(156, "hugh", GOTFaction.ARRYN), SHELL(567, "shell", GOTFaction.ARRYN), BRIGHTSTONE(568, "brightstone", GOTFaction.ARRYN), STANNIS(294, "stannis", GOTFaction.DRAGONSTONE), STANNISBOOK(295, "stannisBook", GOTFaction.DRAGONSTONE), VELARYON(296, "velaryon", GOTFaction.DRAGONSTONE), VELARYONBOOK(297, "velaryonBook", GOTFaction.DRAGONSTONE), BAREMMON(298, "baremmon", GOTFaction.DRAGONSTONE), SUNGLASS(299, "sunglass", GOTFaction.DRAGONSTONE), CELTIGAR(300, "celtigar", GOTFaction.DRAGONSTONE), MASSEY(301, "massey", GOTFaction.DRAGONSTONE), FLORENT(302, "florent", GOTFaction.DRAGONSTONE), FLORENTBOOK(303, "florentBook", GOTFaction.DRAGONSTONE), STAUNTON(304, "staunton", GOTFaction.DRAGONSTONE), FOLLARD(305, "follard", GOTFaction.DRAGONSTONE), CHYTTERING(306, "chyttering", GOTFaction.DRAGONSTONE), SEAWORTH(307, "seaworth", GOTFaction.DRAGONSTONE), FARRING(308, "farring", GOTFaction.DRAGONSTONE), PENDRAGON(309, "pendragon", GOTFaction.DRAGONSTONE), ALQALIS(310, "alqalis", GOTFaction.DRAGONSTONE), RAMBTON(311, "rambton", GOTFaction.DRAGONSTONE), HORPE(312, "horpe", GOTFaction.DRAGONSTONE), GOWER(313, "gower", GOTFaction.DRAGONSTONE), PATREK(314, "patrek", GOTFaction.DRAGONSTONE), SUGGS(315, "suggs", GOTFaction.DRAGONSTONE), ROLLANDSTORM(316, "rollandStorm", GOTFaction.DRAGONSTONE), AURANEWATERS(317, "auranewaters", GOTFaction.DRAGONSTONE), SHADRICH(318, "shadrich", GOTFaction.DRAGONSTONE), OAKENFIST(578, "oakenfist", GOTFaction.DRAGONSTONE), SEASMOKE(579, "seasmoke", GOTFaction.DRAGONSTONE), ROBERT(319, "robert", GOTFaction.CROWNLANDS), JOFFREY(254, "joffrey", GOTFaction.CROWNLANDS), HAND(255, "hand", GOTFaction.CROWNLANDS), BAELISH(256, "baelish", GOTFaction.CROWNLANDS), BAELISHBOOK(257, "baelishBook", GOTFaction.CROWNLANDS), BUCKWELL(258, "buckwell", GOTFaction.CROWNLANDS), BYRCH(259, "byrch", GOTFaction.CROWNLANDS), WENDWATER(260, "wendwater", GOTFaction.CROWNLANDS), MALLERY(261, "mallery", GOTFaction.CROWNLANDS), MANNING(262, "manning", GOTFaction.CROWNLANDS), RIKKER(263, "rikker", GOTFaction.CROWNLANDS), ROSBY(264, "rosby", GOTFaction.CROWNLANDS), STOKEWORTH(265, "stokeworth", GOTFaction.CROWNLANDS), HARTE(266, "harte", GOTFaction.CROWNLANDS), HAYFORD(267, "hayford", GOTFaction.CROWNLANDS), CHELSTED(268, "chelsted", GOTFaction.CROWNLANDS), BRUNEB(269, "bruneB", GOTFaction.CROWNLANDS), BRUNEDD(270, "bruneDD", GOTFaction.CROWNLANDS), BYWATER(271, "bywater", GOTFaction.CROWNLANDS), BLOUNT(272, "blount", GOTFaction.CROWNLANDS), GAUNT(273, "gaunt", GOTFaction.CROWNLANDS), SLYNT(274, "slynt", GOTFaction.CROWNLANDS), THORNE(275, "thorne", GOTFaction.CROWNLANDS), KETTLEBLACK(276, "kettleblack", GOTFaction.CROWNLANDS), BOGGS(277, "boggs", GOTFaction.CROWNLANDS), CAVE(278, "cave", GOTFaction.CROWNLANDS), CRESSEY(279, "cressey", GOTFaction.CROWNLANDS), DARGOOD(280, "dargood", GOTFaction.CROWNLANDS), DARKE(281, "darke", GOTFaction.CROWNLANDS), DARKWOOD(282, "darkwood", GOTFaction.CROWNLANDS), HARDY(283, "hardy", GOTFaction.CROWNLANDS), HOGG(284, "hogg", GOTFaction.CROWNLANDS), LANGWARD(285, "langward", GOTFaction.CROWNLANDS), LONGWATERS(286, "longwaters", GOTFaction.CROWNLANDS), PYLE(287, "pyle", GOTFaction.CROWNLANDS), PYNE(288, "pyne", GOTFaction.CROWNLANDS), ROLLINGFORD(289, "rollingford", GOTFaction.CROWNLANDS), BRONNBOOK(290, "bronnBook", GOTFaction.CROWNLANDS), TALLAD(291, "tallad", GOTFaction.CROWNLANDS), LOTHOR(292, "lothor", GOTFaction.CROWNLANDS), MORROS(293, "morros", GOTFaction.CROWNLANDS), KINGSGUARD(512, "kingsguard", GOTFaction.CROWNLANDS), DARKLYN(546, "darklyn", GOTFaction.CROWNLANDS), HOLLARD(547, "hollard", GOTFaction.CROWNLANDS), CARGYLE(548, "cargyle", GOTFaction.CROWNLANDS), CRABB(549, "crabb", GOTFaction.CROWNLANDS), DUNCAN(599, "duncan", GOTFaction.CROWNLANDS), RENLY(321, "renly", GOTFaction.STORMLANDS), ROBERTB(320, "robertB", GOTFaction.STORMLANDS), RENLYBOOK(322, "renlyBook", GOTFaction.STORMLANDS), BUCKLER(323, "buckler", GOTFaction.STORMLANDS), GRANDISON(324, "grandison", GOTFaction.STORMLANDS), CAFFEREN(325, "cafferen", GOTFaction.STORMLANDS), MERTYNS(326, "mertyns", GOTFaction.STORMLANDS), MORRIGEN(327, "morrigen", GOTFaction.STORMLANDS), PENROSE(328, "penrose", GOTFaction.STORMLANDS), STAEDMON(329, "staedmon", GOTFaction.STORMLANDS), TARTH(330, "tarth", GOTFaction.STORMLANDS), TARTHBOOK(331, "tarthBook", GOTFaction.STORMLANDS), WYLDE(332, "wylde", GOTFaction.STORMLANDS), FELL(333, "fell", GOTFaction.STORMLANDS), ERROL(334, "errol", GOTFaction.STORMLANDS), ESTERMONT(335, "estermont", GOTFaction.STORMLANDS), DONDARRION(336, "dondarrion", GOTFaction.STORMLANDS), DONDARRIONBOOK(337, "dondarrionBook", GOTFaction.STORMLANDS), CARON(338, "caron", GOTFaction.STORMLANDS), SWANN(339, "swann", GOTFaction.STORMLANDS), SWANNBOOK(340, "swannBook", GOTFaction.STORMLANDS), SELMY(341, "selmy", GOTFaction.STORMLANDS), BOLLING(342, "bolling", GOTFaction.STORMLANDS), CONNINGTON(343, "connington", GOTFaction.STORMLANDS), HASTY(344, "hasty", GOTFaction.STORMLANDS), WAGSTAFF(345, "wagstaff", GOTFaction.STORMLANDS), WENSINGTON(346, "wensington", GOTFaction.STORMLANDS), PEASEBURY(347, "peasebury", GOTFaction.STORMLANDS), ROGERS(348, "rogers", GOTFaction.STORMLANDS), FOOTEN(349, "footeN", GOTFaction.STORMLANDS), TRANT(350, "trant", GOTFaction.STORMLANDS), HERSTON(351, "herston", GOTFaction.STORMLANDS), KELLINGTON(352, "kellington", GOTFaction.STORMLANDS), LONMOUTH(353, "lonmouth", GOTFaction.STORMLANDS), MUSGOOD(354, "musgood", GOTFaction.STORMLANDS), SWYGERT(355, "swygert", GOTFaction.STORMLANDS), TUDBURY(356, "tudbury", GOTFaction.STORMLANDS), WHITEHEAD(357, "whitehead", GOTFaction.STORMLANDS), PEARSE(358, "pearse", GOTFaction.STORMLANDS), EDRICSTORM(359, "edricstorm", GOTFaction.STORMLANDS), RENLYGUARD(513, "renlyguard", GOTFaction.STORMLANDS), DURRANDON(580, "durrandon", GOTFaction.STORMLANDS), COLE(581, "cole", GOTFaction.STORMLANDS), TOYNE(582, "toyne", GOTFaction.STORMLANDS), TYRELL(360, "tyrell", GOTFaction.REACH), DURWELL(415, "durwell", GOTFaction.REACH), AMBROSE(361, "ambrose", GOTFaction.REACH), BLACKBAR(362, "blackbar", GOTFaction.REACH), VARNER(363, "varner", GOTFaction.REACH), VYRWEL(364, "vyrwel", GOTFaction.REACH), CASWELL(365, "caswell", GOTFaction.REACH), CORDWAYNER(366, "cordwayner", GOTFaction.REACH), COCKSHAW(367, "cockshaw", GOTFaction.REACH), CRANE(368, "crane", GOTFaction.REACH), MEADOWS(369, "meadows", GOTFaction.REACH), MERRYWEATHER(370, "merryweather", GOTFaction.REACH), OAKHEART(371, "oakheart", GOTFaction.REACH), PEAKE(372, "peake", GOTFaction.REACH), REDWYNE(373, "redwyne", GOTFaction.REACH), REDWYNEBOOK(374, "redwyneBook", GOTFaction.REACH), ROWAN(375, "rowan", GOTFaction.REACH),
		TARLY(376, "tarly", GOTFaction.REACH), TARLY_BOOK(377, "tarlyBook", GOTFaction.REACH), TYRELLBK(378, "tyrellBK", GOTFaction.REACH), TYRELLL(379, "tyrelll", GOTFaction.REACH), FOSSOWAYCH(380, "fossowayCH", GOTFaction.REACH), FOOTLY(381, "footly", GOTFaction.REACH), HIGHTOWER(382, "hightower", GOTFaction.REACH), HIGHTOWERBOOK(383, "hightowerBook", GOTFaction.REACH), SHERMER(384, "shermer", GOTFaction.REACH), APPLETON(385, "appleton", GOTFaction.REACH), ASHFORD(386, "ashford", GOTFaction.REACH), BEESBURY(387, "beesbury", GOTFaction.REACH), BULLWER(388, "bullwer", GOTFaction.REACH), COSTAYNE(389, "costayne", GOTFaction.REACH), CUY(390, "cuy", GOTFaction.REACH), MULLENDORE(391, "mullendore", GOTFaction.REACH), GRIMM(392, "grimm", GOTFaction.REACH), SERRY(393, "serry", GOTFaction.REACH), HEWETT(394, "hewett", GOTFaction.REACH), CHESTER(395, "chester", GOTFaction.REACH), FOSSOWAYNB(396, "fossowayNB", GOTFaction.REACH), OSGREY(397, "osgrey", GOTFaction.REACH), OSGREYLL(398, "osgreyll", GOTFaction.REACH), BALL(399, "ball", GOTFaction.REACH), WEBBER(400, "webber", GOTFaction.REACH), WILLUM(401, "willum", GOTFaction.REACH), WOODWRIGHT(402, "woodwright", GOTFaction.REACH), GRACEFORD(403, "graceford", GOTFaction.REACH), INCHFIELD(404, "inchfield", GOTFaction.REACH), LEYGOOD(405, "leygood", GOTFaction.REACH), KIDWELL(406, "kidwell", GOTFaction.REACH), NORCROSS(407, "norcross", GOTFaction.REACH), RISLEY(408, "risley", GOTFaction.REACH), ROXTON(409, "roxton", GOTFaction.REACH), WYTHERS(410, "wythers", GOTFaction.REACH), HUNT(411, "hunt", GOTFaction.REACH), BRIDGES(412, "bridges", GOTFaction.REACH), BUSHY(413, "bushy", GOTFaction.REACH), DUNN(414, "dunn", GOTFaction.REACH), HASTWYCK(416, "hastwyck", GOTFaction.REACH), HUTCHESON(417, "hutcheson", GOTFaction.REACH), LOWTHER(418, "lowther", GOTFaction.REACH), LYBERR(419, "lyberr", GOTFaction.REACH), MORGRYN(420, "morgryn", GOTFaction.REACH), NORRIDGE(421, "norridge", GOTFaction.REACH), ORME(423, "orme", GOTFaction.REACH), OVERTON(424, "overton", GOTFaction.REACH), POMMINGHAM(425, "pommingham", GOTFaction.REACH), RHYSLING(426, "rhysling", GOTFaction.REACH), SLOANE(427, "sloane", GOTFaction.REACH), STACKHOUSE(428, "stackhouse", GOTFaction.REACH), WESTBROOK(430, "westbrook", GOTFaction.REACH), WHITEGROVE(431, "whitegrove", GOTFaction.REACH), BRONN(432, "bronn", GOTFaction.REACH), GARDENER(583, "gardener", GOTFaction.REACH), GARDENERBOOK(584, "gardenerBook", GOTFaction.REACH), FIREBALL(585, "fireball", GOTFaction.REACH), STRICKLAND(586, "strickland", GOTFaction.REACH), ROBERTASHFORD(587, "robertashford", GOTFaction.REACH), BENNIS(588, "bennis", GOTFaction.REACH), DERRICKFOSSOWAY(589, "derrickfossoway", GOTFaction.REACH), MARTELL(433, "martell", GOTFaction.DORNE), MARTELLBOOK(434, "martellBook", GOTFaction.DORNE), OBERYN(435, "oberyn", GOTFaction.DORNE), SANDSNAKES(436, "sandsnakes", GOTFaction.DORNE), YRONWOOD(437, "yronwood", GOTFaction.DORNE), ALLYRION(438, "allyrion", GOTFaction.DORNE), BLACKMONT(439, "blackmont", GOTFaction.DORNE), VAITH(440, "vaith", GOTFaction.DORNE), WYL(441, "wyl", GOTFaction.DORNE), GARGALEN(442, "gargalen", GOTFaction.DORNE), DAYNE(443, "dayne", GOTFaction.DORNE), JORDAYNE(444, "jordayne", GOTFaction.DORNE), QORGILE(445, "qorgile", GOTFaction.DORNE), MANWOODY(446, "manwoody", GOTFaction.DORNE), TOLAND(447, "toland", GOTFaction.DORNE), ULLER(448, "uller", GOTFaction.DORNE), FOWLER(449, "fowler", GOTFaction.DORNE), FOWLERBOOK(450, "fowlerBook", GOTFaction.DORNE), DALT(451, "dalt", GOTFaction.DORNE), DAYNEHH(452, "dayneHH", GOTFaction.DORNE), SANTAGAR(453, "santagar", GOTFaction.DORNE), DRINKWATER(454, "drinkwater", GOTFaction.DORNE), LADYBRIGHT(455, "ladybright", GOTFaction.DORNE), TARWICK(456, "tarwick", GOTFaction.DORNE), WELLSD(457, "wellsD", GOTFaction.DORNE), MORS(590, "mors", GOTFaction.DORNE), MORSBOOK(591, "morsBook", GOTFaction.DORNE), NYMERIA(592, "nymeria", GOTFaction.DORNE), DRYLAND(593, "dryland", GOTFaction.DORNE), VULTUREKING(594, "vultureking", GOTFaction.DORNE), BRAAVOS(458, "braavos", GOTFaction.BRAAVOS), IRON_BANK(459, "iron_bank", GOTFaction.BRAAVOS), VOLANTIS(469, "volantis", GOTFaction.VOLANTIS), TIGERS(470, "tigers", GOTFaction.VOLANTIS), ELEPHANTS(471, "elephants", GOTFaction.VOLANTIS), PENTOS(460, "pentos", GOTFaction.PENTOS), MOPATIS(461, "mopatis", GOTFaction.PENTOS), NORVOS(467, "norvos", GOTFaction.NORVOS), LORATH(462, "lorath", GOTFaction.LORATH), QOHOR(466, "qohor", GOTFaction.QOHOR), LYS(464, "lys", GOTFaction.LYS), SAAN(465, "saan", GOTFaction.LYS), MYR(463, "myr", GOTFaction.MYR), TYROSH(468, "tyrosh", GOTFaction.TYROSH), GHISCAR(473, "ghiscar", GOTFaction.GHISCAR), ASTAPOR(474, "astapor", GOTFaction.GHISCAR), YUNKAI(475, "yunkai", GOTFaction.GHISCAR), MEEREEN(476, "meereen", GOTFaction.GHISCAR), NEWGHIS(477, "newghis", GOTFaction.GHISCAR), LORAQ(478, "loraq", GOTFaction.GHISCAR), QARTH(479, "qarth", GOTFaction.QARTH), LHAZAR(472, "lhazar", GOTFaction.LHAZAR), IBBEN(480, "ibben", GOTFaction.IBBEN), JOGOS_NHAI(616, "jogos_nhai", GOTFaction.JOGOS_NHAI), MOSSOVY(615, "mossovy", GOTFaction.MOSSOVY), YI_TI(482, "yi_ti", GOTFaction.YI_TI), YI_TI_BU(483, "yi_ti_bu", GOTFaction.YI_TI), YI_TI_CHOQ(484, "yi_ti_choq", GOTFaction.YI_TI), YI_TI_HAR(485, "yi_ti_har", GOTFaction.YI_TI), YI_TI_JAR(486, "yi_ti_jar", GOTFaction.YI_TI), YI_TI_LO(487, "yi_ti_lo", GOTFaction.YI_TI), YI_TI_MAROON(488, "yi_ti_maroon", GOTFaction.YI_TI), YI_TI_MENGO(489, "yi_ti_mengo", GOTFaction.YI_TI), YI_TI_PEARL(490, "yi_ti_pearl", GOTFaction.YI_TI), YI_TI_PURPLE(491, "yi_ti_purple", GOTFaction.YI_TI), YI_TI_QO(492, "yi_ti_qo", GOTFaction.YI_TI), LENG(493, "leng", GOTFaction.YI_TI), ASSHAI(614, "asshai", GOTFaction.ASSHAI), SUMMER(481, "summer", GOTFaction.SUMMER_ISLANDS), SOTHORYOS(494, "sothoryos", GOTFaction.SOTHORYOS), CLEOS(244, "cleos", GOTFaction.NEUTRAL), SPARROW(500, "sparrow", GOTFaction.NEUTRAL), MAESTERS(501, "maesters", GOTFaction.NEUTRAL), SECONDSONS(502, "secondsons", GOTFaction.NEUTRAL), GOLDEN_COMPANY(503, "golden_company", GOTFaction.NEUTRAL), WINDBLOWN(504, "windblown", GOTFaction.NEUTRAL), STORMCROWS(505, "stormcrows", GOTFaction.NEUTRAL), BRAVECOMPANIONS(506, "bravecompanions", GOTFaction.NEUTRAL), WARRIORSSONS(507, "warriorsSons", GOTFaction.NEUTRAL), POORFELLOWS(508, "poorFellows", GOTFaction.NEUTRAL), ANDAL(509, "andal", GOTFaction.NEUTRAL), SEPT(510, "sept", GOTFaction.NEUTRAL), FACELESS(511, "faceless", GOTFaction.NEUTRAL), FELLOWSHIP(514, "fellowship", GOTFaction.NEUTRAL), RHLLOR(515, "rhllor", GOTFaction.NEUTRAL), WHITE(516, "white", GOTFaction.NEUTRAL), TARGARYEN(517, "targaryen", GOTFaction.NEUTRAL), TARGARYENBOOK(518, "targaryenBook", GOTFaction.NEUTRAL), BLACKFYRE(519, "blackfyre", GOTFaction.NEUTRAL), BLACKFYREBOOK(520, "blackfyreBook", GOTFaction.NEUTRAL), BLACKS(521, "blacks", GOTFaction.NEUTRAL), BLACKSBOOK(522, "blacksBook", GOTFaction.NEUTRAL), GREENS(523, "greens", GOTFaction.NEUTRAL), GREENSBOOK(524, "greensBook", GOTFaction.NEUTRAL), AEMON(525, "aemon", GOTFaction.NEUTRAL), AEMONBOOK(526, "aemonBook", GOTFaction.NEUTRAL), AERION(527, "aerion", GOTFaction.NEUTRAL), AERIONBOOK(528, "aerionBook", GOTFaction.NEUTRAL), VALARR(529, "valarr", GOTFaction.NEUTRAL), VALARRBOOK(530, "valarrBook", GOTFaction.NEUTRAL), BITTERSTEEL(531, "bittersteel", GOTFaction.NEUTRAL), BLOODRAVEN(532, "bloodraven", GOTFaction.NEUTRAL), BLOODRAVENBOOK(533, "bloodravenBook", GOTFaction.NEUTRAL), SEASTAR(534, "seastar", GOTFaction.NEUTRAL), MAEKAR(535, "maekar", GOTFaction.NEUTRAL), MAEKARBOOK(536, "maekarBook", GOTFaction.NEUTRAL), DAEMON(537, "daemon", GOTFaction.NEUTRAL), TRUEFYRE(538, "truefyre", GOTFaction.NEUTRAL), DAERON(539, "daeron", GOTFaction.NEUTRAL), DAERONBOOK(540, "daeronBook", GOTFaction.NEUTRAL), RHAEGAR(541, "rhaegar", GOTFaction.NEUTRAL), YOUNGGRIFF(542, "youngGriff", GOTFaction.NEUTRAL), JONCONNINGTON(543, "jonconnington", GOTFaction.NEUTRAL), UNDERLEAF(544, "underleaf", GOTFaction.NEUTRAL), ROSSART(545, "rossart", GOTFaction.NEUTRAL), BELAERYS(595, "belaerys", GOTFaction.NEUTRAL), GALTRY(596, "galtry", GOTFaction.NEUTRAL), ILLIFER(597, "illifer", GOTFaction.NEUTRAL), CREIGHTON(598, "creighton", GOTFaction.NEUTRAL), LEATHER(601, "leather", GOTFaction.NEUTRAL), NULL(617, "null", GOTFaction.NEUTRAL);

		public static final Collection<BannerType> BANNER_TYPES = new ArrayList<>();
		public static final Map<Integer, BannerType> BANNER_FOR_ID = new HashMap<>();

		static {
			for (BannerType t : values()) {
				BANNER_TYPES.add(t);
				BANNER_FOR_ID.put(t.bannerID, t);
			}
		}

		private final int bannerID;
		private final String bannerName;

		private final GOTFaction faction;

		BannerType(int i, String s, GOTFaction f) {
			bannerID = i;
			bannerName = s;
			faction = f;
			faction.getFactionBanners().add(this);
		}

		public static BannerType forID(int ID) {
			if (BANNER_FOR_ID.get(ID) == null) {
				return NULL;
			}
			return BANNER_FOR_ID.get(ID);
		}

		public GOTFaction getFaction() {
			return faction;
		}

		public int getBannerID() {
			return bannerID;
		}

		public String getBannerName() {
			return bannerName;
		}
	}
}