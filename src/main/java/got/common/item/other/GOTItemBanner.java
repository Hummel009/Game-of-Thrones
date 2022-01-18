package got.common.item.other;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import got.common.*;
import got.common.database.*;
import got.common.entity.other.*;
import got.common.faction.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class GOTItemBanner extends Item {
	public static int id = 0;
	@SideOnly(value = Side.CLIENT)
	public IIcon[] bannerIcons;

	public GOTItemBanner() {
		setCreativeTab(GOTCreativeTabs.tabBanner);
		setMaxStackSize(64);
		setMaxDamage(0);
		setHasSubtypes(true);
		setFull3D();
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		NBTTagCompound protectData = GOTItemBanner.getProtectionData(itemstack);
		if (protectData != null) {
			list.add(StatCollector.translateToLocal("item.got.banner.protect"));
		}
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public IIcon getIconFromDamage(int i) {
		if (i >= bannerIcons.length) {
			i = 0;
		}
		return bannerIcons[i];
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (BannerType type : BannerType.bannerTypes) {
			list.add(new ItemStack(item, 1, type.bannerID));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return super.getUnlocalizedName() + "." + GOTItemBanner.getBannerType(itemstack).bannerName;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		BannerType bannerType = GOTItemBanner.getBannerType(itemstack);
		NBTTagCompound protectData = GOTItemBanner.getProtectionData(itemstack);
		if (world.getBlock(i, j, k).isReplaceable(world, i, j, k)) {
			side = 1;
		} else if (side == 1) {
			++j;
		}
		if (side == 0) {
			return false;
		}
		if (side == 1) {
			if (!entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
				return false;
			}
			Block block = world.getBlock(i, j - 1, k);
			int meta = world.getBlockMetadata(i, j - 1, k);
			if (block.isSideSolid(world, i, j - 1, k, ForgeDirection.UP)) {
				int protectRange;
				if (GOTConfig.allowBannerProtection && !entityplayer.capabilities.isCreativeMode && (protectRange = GOTBannerProtection.getProtectionRange(block, meta)) > 0) {
					GOTFaction faction = bannerType.faction;
					if (GOTLevelData.getData(entityplayer).getAlignment(faction) < 1.0f) {
						if (!world.isRemote) {
							GOTAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 1.0f, faction);
						}
						return false;
					}
					if (!world.isRemote && GOTBannerProtection.isProtected(world, i, j, k, GOTBannerProtection.forPlayer(entityplayer, GOTBannerProtection.Permission.FULL), false, protectRange)) {
						entityplayer.addChatMessage(new ChatComponentTranslation("got.chat.alreadyProtected"));
						return false;
					}
				}
				if (!world.isRemote) {
					GOTEntityBanner banner = new GOTEntityBanner(world);
					banner.setLocationAndAngles(i + 0.5, j, k + 0.5, 90.0f * (MathHelper.floor_double(entityplayer.rotationYaw * 4.0f / 360.0f + 0.5) & 3), 0.0f);
					if (world.checkNoEntityCollision(banner.boundingBox) && world.getCollidingBoundingBoxes(banner, banner.boundingBox).isEmpty() && !world.isAnyLiquid(banner.boundingBox) && world.getEntitiesWithinAABB(GOTEntityBanner.class, banner.boundingBox).isEmpty()) {
						banner.setBannerType(bannerType);
						if (protectData != null) {
							banner.readProtectionFromNBT(protectData);
						}
						if (banner.getPlacingPlayer() == null || GOTItemBanner.shouldRepossessBannerOnPlacement(entityplayer, itemstack)) {
							banner.setPlacingPlayer(entityplayer);
						}
						world.spawnEntityInWorld(banner);
						if (banner.isProtectingTerritory()) {
							GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.BANNER_PROTECT);
						}
						world.playSoundAtEntity(banner, Blocks.planks.stepSound.func_150496_b(), (Blocks.planks.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.planks.stepSound.getPitch() * 0.8f);
						--itemstack.stackSize;
						return true;
					}
					banner.setDead();
				}
			}
		} else {
			if (!entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
				return false;
			}
			if (!world.isRemote) {
				int l = Direction.facingToDirection[side];
				GOTEntityBannerWall banner = new GOTEntityBannerWall(world, i, j, k, l);
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

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		bannerIcons = new IIcon[BannerType.bannerTypes.size()];
		for (int i = 0; i < bannerIcons.length; ++i) {
			bannerIcons[i] = iconregister.registerIcon("got:banner_" + BannerType.bannerTypes.get(i).bannerName);
		}
	}

	public static BannerType getBannerType(int i) {
		return BannerType.forID(i);
	}

	public static BannerType getBannerType(ItemStack itemstack) {
		if (itemstack.getItem() instanceof GOTItemBanner) {
			return GOTItemBanner.getBannerType(itemstack.getItemDamage());
		}
		return null;
	}

	public static NBTTagCompound getProtectionData(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("GOTBannerData")) {
			return itemstack.getTagCompound().getCompoundTag("GOTBannerData");
		}
		return null;
	}

	public static boolean hasChoiceToRepossessBanner(EntityPlayer entityplayer, ItemStack bannerItem) {
		return entityplayer.capabilities.isCreativeMode;
	}

	public static boolean isHoldingBannerWithExistingProtection(EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.getHeldItem();
		if (itemstack != null && itemstack.getItem() instanceof GOTItemBanner) {
			NBTTagCompound protectData = GOTItemBanner.getProtectionData(itemstack);
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

	public static boolean shouldRepossessBannerOnPlacement(EntityPlayer entityplayer, ItemStack bannerItem) {
		return !GOTItemBanner.hasChoiceToRepossessBanner(entityplayer, bannerItem) || !entityplayer.isSneaking();
	}

	public enum BannerType {
		EDDARD(id++, "eddard", GOTFaction.NORTH), ROBB(id++, "robb", GOTFaction.NORTH), ROBBBOOK(id++, "robbBook", GOTFaction.NORTH), UMBER(id++, "umber", GOTFaction.NORTH), UMBERBOOK(id++, "umberBook", GOTFaction.NORTH), BOLTON(id++, "bolton", GOTFaction.NORTH), BOLTONBOOK(id++, "boltonbook", GOTFaction.NORTH), DUSTIN(id++, "dustin", GOTFaction.NORTH), BARBREY(id++, "barbrey", GOTFaction.NORTH), KARSTARK(id++, "karstark", GOTFaction.NORTH), KARSTARKBOOK(id++, "karstarkBook", GOTFaction.NORTH), LOCKE(id++, "locke", GOTFaction.NORTH), MANDERLY(id++, "manderly", GOTFaction.NORTH), MANDERLYBOOK(id++, "manderlyBook", GOTFaction.NORTH), MORMONT(id++, "mormont", GOTFaction.NORTH), MORMONTBOOK(id++, "mormontBook", GOTFaction.NORTH), REED(id++, "reed", GOTFaction.NORTH), REEDBOOK(id++, "reedBook", GOTFaction.NORTH), REEDHBO(id++, "reedHBO", GOTFaction.NORTH), RYSWELL(id++, "ryswell", GOTFaction.NORTH), RODRIKRYSWELL(id++, "rodrikryswell", GOTFaction.NORTH), CERWYN(id++, "cerwyn", GOTFaction.NORTH), WHITEHILL(id++, "whitehill", GOTFaction.NORTH), WHITEHILLBOOK(id++, "whitehillBook", GOTFaction.NORTH), FLINTWW(id++, "flintWW", GOTFaction.NORTH), FLINTFF(id++, "flintFF", GOTFaction.NORTH), HORNWOOD(id++, "hornwood", GOTFaction.NORTH), HORNWOODBOOK(id++, "hornwoodBook", GOTFaction.NORTH), GLOVER(id++, "glover", GOTFaction.NORTH), TALLHART(id++, "tallhart", GOTFaction.NORTH), WOOLFIELD(id++, "woolfield", GOTFaction.NORTH), GLENMORE(id++, "glenmore", GOTFaction.NORTH), DORMUND(id++, "dormund", GOTFaction.NORTH), CASSEL(id++, "cassel", GOTFaction.NORTH), CONDON(id++, "condon", GOTFaction.NORTH), MARSH(id++, "marsh", GOTFaction.NORTH), MAZIN(id++, "mazin", GOTFaction.NORTH), MOLLEN(id++, "mollen", GOTFaction.NORTH), POOLE(id++, "poole", GOTFaction.NORTH), STOUT(id++, "stout", GOTFaction.NORTH), SLATE(id++, "slate", GOTFaction.NORTH), WELLS(id++, "wells", GOTFaction.NORTH), FENN(id++, "fenn", GOTFaction.NORTH), BURLEY(id++, "burley", GOTFaction.NORTH), WULL(id++, "wull", GOTFaction.NORTH), LIDDLE(id++, "liddle", GOTFaction.NORTH), NORREY(id++, "norrey", GOTFaction.NORTH), KNOTT(id++, "knott", GOTFaction.NORTH), FIRSTFLINT(id++, "firstFlint", GOTFaction.NORTH), HARCLAY(id++, "harclay", GOTFaction.NORTH), FORRESTER(id++, "forrester", GOTFaction.NORTH), FORRESTERBOOK(id++, "forresterBook", GOTFaction.NORTH), CROWL(id++, "crowl", GOTFaction.NORTH), MAGNAR(id++, "magnar", GOTFaction.NORTH), STANE(id++, "stane", GOTFaction.NORTH), ASHWOOD(id++, "ashwood", GOTFaction.NORTH), BOLE(id++, "bole", GOTFaction.NORTH), BRANCH(id++, "branch", GOTFaction.NORTH), BRANFIELD(id++, "branfield", GOTFaction.NORTH), DEGORE(id++, "degore", GOTFaction.NORTH), IRONSMITH(id++, "ironsmith", GOTFaction.NORTH), LAKE(id++, "lake", GOTFaction.NORTH), LIGHTFOOT(id++, "lightfoot", GOTFaction.NORTH), MOSS(id++, "moss", GOTFaction.NORTH), RYDER(id++, "ryder", GOTFaction.NORTH), TOWERSN(id++, "towersN", GOTFaction.NORTH), WATERMAN(id++, "waterman", GOTFaction.NORTH), WOODS(id++, "woods", GOTFaction.NORTH), JONSNOW(id++, "jonsnow", GOTFaction.NORTH), JONKING(id++, "jonking", GOTFaction.NORTH), STARKD(id++, "starkD", GOTFaction.NORTH), TULLY(id++, "tully", GOTFaction.RIVERLANDS), TULLYBOOK(id++, "tullyBook", GOTFaction.RIVERLANDS), BUTTERWELL(id++, "butterwell", GOTFaction.RIVERLANDS), BLACKWOOD(id++, "blackwood", GOTFaction.RIVERLANDS), BLACKWOODBOOK(id++, "blackwoodBook", GOTFaction.RIVERLANDS), BRACKEN(id++, "bracken", GOTFaction.RIVERLANDS), BRACKENBOOK(id++, "brackenBook", GOTFaction.RIVERLANDS), VYPREN(id++, "vypren", GOTFaction.RIVERLANDS), VANCEWR(id++, "vanceWR", GOTFaction.RIVERLANDS), VANCEA(id++, "vanceA", GOTFaction.RIVERLANDS), GOODBROOK(id++, "goodbrook", GOTFaction.RIVERLANDS), DARRY(id++, "darry", GOTFaction.RIVERLANDS), LYCHESTER(id++, "lychester", GOTFaction.RIVERLANDS), MALLISTER(id++, "mallister", GOTFaction.RIVERLANDS), JAREMYMALLISTER(id++, "jaremymallister", GOTFaction.RIVERLANDS), MOOTON(id++, "mooton", GOTFaction.RIVERLANDS), PIPER(id++, "piper", GOTFaction.RIVERLANDS), RYGER(id++, "ryger", GOTFaction.RIVERLANDS), ROOTE(id++, "roote", GOTFaction.RIVERLANDS), SMALLWOOD(id++, "smallwood", GOTFaction.RIVERLANDS), WHENT(id++, "whent", GOTFaction.RIVERLANDS), FREY(id++, "frey", GOTFaction.RIVERLANDS), FREYBOOK(id++, "freyBook", GOTFaction.RIVERLANDS), AEMONRIVERS(id++, "aemonrivers", GOTFaction.RIVERLANDS), BENFREY(id++, "benfrey", GOTFaction.RIVERLANDS), BIGWALDER(id++, "bigwalder", GOTFaction.RIVERLANDS), LITTLEWALDER(id++, "littlewalder", GOTFaction.RIVERLANDS), WALDER(id++, "walder", GOTFaction.RIVERLANDS), CHARLTON(id++, "charlton", GOTFaction.RIVERLANDS), COX(id++, "cox", GOTFaction.RIVERLANDS), PAEGE(id++, "paege", GOTFaction.RIVERLANDS), WODE(id++, "wode", GOTFaction.RIVERLANDS), HEDDLE(id++, "heddle", GOTFaction.RIVERLANDS), HAIGH(id++, "haigh", GOTFaction.RIVERLANDS), ERENFORD(id++, "erenford", GOTFaction.RIVERLANDS), GRELL(id++, "grell", GOTFaction.RIVERLANDS), WAYN(id++, "wayn", GOTFaction.RIVERLANDS), HARLTON(id++, "harlton", GOTFaction.RIVERLANDS), LOTHSTON(id++, "lothston", GOTFaction.RIVERLANDS), STRONG(id++, "strong", GOTFaction.RIVERLANDS), BLANETREE(id++, "blanetree", GOTFaction.RIVERLANDS), HAWICK(id++, "hawick", GOTFaction.RIVERLANDS), LOLLISTON(id++, "lolliston", GOTFaction.RIVERLANDS), SHAWNEY(id++, "shawney", GOTFaction.RIVERLANDS), BLACKFISH(id++, "blackfish", GOTFaction.RIVERLANDS), BLACKFISHBOOK(id++, "blackfishBook", GOTFaction.RIVERLANDS), ARRYN(id++, "arryn", GOTFaction.ARRYN), ARRYNBOOK(id++, "arrynBook", GOTFaction.ARRYN), BELMORE(id++, "belmore", GOTFaction.ARRYN), GRAFTON(id++, "grafton", GOTFaction.ARRYN), EGEN(id++, "egen", GOTFaction.ARRYN), ELESHAM(id++, "elesham", GOTFaction.ARRYN), CORBRAY(id++, "corbray", GOTFaction.ARRYN), CORBRAYBOOK(id++, "corbrayBook", GOTFaction.ARRYN), COLDWATER(id++, "coldwater", GOTFaction.ARRYN), LYNDERLY(id++, "lynderly", GOTFaction.ARRYN), MELCOLM(id++, "melcolm", GOTFaction.ARRYN), REDFORT(id++, "redfort", GOTFaction.ARRYN), ROYCE(id++, "royce", GOTFaction.ARRYN), ROYCEBOOK(id++, "royceBook", GOTFaction.ARRYN), ROYCEGM(id++, "royceGM", GOTFaction.ARRYN), TOLLETT(id++, "tollett", GOTFaction.ARRYN), WAYNWOOD(id++, "waynwood", GOTFaction.ARRYN), HUNTER(id++, "hunter", GOTFaction.ARRYN), HERSY(id++, "hersy", GOTFaction.ARRYN), WAXLEY(id++, "waxley", GOTFaction.ARRYN), MOORE(id++, "moore", GOTFaction.ARRYN), TEMPLETON(id++, "templeton", GOTFaction.ARRYN), HARDYNG(id++, "hardyng", GOTFaction.ARRYN), HARROLDHARDYNG(id++, "harroldhardyng", GOTFaction.ARRYN), SHETTGTOWN(id++, "shettgtown", GOTFaction.ARRYN), SHETTGTOWER(id++, "shettgtower", GOTFaction.ARRYN), SUNDERLAND(id++, "sunderland", GOTFaction.ARRYN), BORRELL(id++, "borrell", GOTFaction.ARRYN), TORRENT(id++, "torrent", GOTFaction.ARRYN), UPCLIFFE(id++, "upcliff", GOTFaction.ARRYN), ARRYNGT(id++, "arrynGT", GOTFaction.ARRYN), BAELISHOLD(id++, "baelishold", GOTFaction.ARRYN), BREAKSTONE(id++, "breakstone", GOTFaction.ARRYN), DONNIGER(id++, "donniger", GOTFaction.ARRYN), PRYOR(id++, "pryor", GOTFaction.ARRYN), LIPPS(id++, "lipps", GOTFaction.ARRYN), LONGTORPE(id++, "longtorpe", GOTFaction.ARRYN), RUTHERMONT(id++, "ruthermont", GOTFaction.ARRYN), WYDMAN(id++, "wydman", GOTFaction.ARRYN), HUGH(id++, "hugh", GOTFaction.ARRYN), GREYJOY(id++, "greyjoy", GOTFaction.IRONBORN), GREYJOYBOOK(id++, "greyjoyBook", GOTFaction.IRONBORN), EURON(id++, "euron", GOTFaction.IRONBORN), EURONBOOK(id++, "euronBook", GOTFaction.IRONBORN), VICTARION(id++, "victarion", GOTFaction.IRONBORN), BLACKTYDE(id++, "blacktyde", GOTFaction.IRONBORN), BOTLEY(id++, "botley", GOTFaction.IRONBORN), WYNCH(id++, "wynch", GOTFaction.IRONBORN), VOLMARK(id++, "volmark", GOTFaction.IRONBORN), GOODBROTHER(id++, "goodbrother", GOTFaction.IRONBORN), GOODBROTHERCL(id++, "goodbrotherCL", GOTFaction.IRONBORN), GOODBROTHERCSK(id++, "goodbrotherCSK", GOTFaction.IRONBORN), GOODBROTHERD(id++, "goodbrotherD", GOTFaction.IRONBORN), GOODBROTHERO(id++, "goodbrotherO", GOTFaction.IRONBORN), GOODBROTHERS(id++, "goodbrotherS", GOTFaction.IRONBORN), DRUMM(id++, "drumm", GOTFaction.IRONBORN), MERLYN(id++, "merlyn", GOTFaction.IRONBORN), ORKWOOD(id++, "orkwood", GOTFaction.IRONBORN), SALTCLIFFE(id++, "saltcliffe", GOTFaction.IRONBORN), SUNDERLY(id++, "sunderly", GOTFaction.IRONBORN), TAWNEY(id++, "tawney", GOTFaction.IRONBORN), FARWYND(id++, "farwynd", GOTFaction.IRONBORN), FARWYNDLL(id++, "farwyndLL", GOTFaction.IRONBORN), HARLAW(id++, "harlaw", GOTFaction.IRONBORN), KENNINGH(id++, "kenningH", GOTFaction.IRONBORN), CODD(id++, "codd", GOTFaction.IRONBORN), MYRE(id++, "myre", GOTFaction.IRONBORN), IRONMAKER(id++, "ironmaker", GOTFaction.IRONBORN), SPARR(id++, "sparr", GOTFaction.IRONBORN), STONETREE(id++, "stonetree", GOTFaction.IRONBORN), STONEHOUSE(id++, "stonehouse", GOTFaction.IRONBORN), HARLAWTG(id++, "harlawTG", GOTFaction.IRONBORN), HARLAWHH(id++, "harlawHH", GOTFaction.IRONBORN), HARLAWGG(id++, "harlawGG", GOTFaction.IRONBORN), HARLAWHALL(id++, "harlawHall", GOTFaction.IRONBORN), GORMONDGOODBROTHER(id++, "gormondgoodbrother", GOTFaction.IRONBORN), GRANGOODBROTHER(id++, "grangoodbrother", GOTFaction.IRONBORN), LANNISTER(id++, "lannister", GOTFaction.WESTERLANDS), LANNISTERB(id++, "lannisterB", GOTFaction.WESTERLANDS), LANNISTERC(id++, "lannisterC", GOTFaction.WESTERLANDS), LANNISTERBOOK(id++, "lannisterBook", GOTFaction.WESTERLANDS), BANEFORT(id++, "banefort", GOTFaction.WESTERLANDS), BANEFORTBOOK(id++, "banefortBook", GOTFaction.WESTERLANDS), BRAX(id++, "brax", GOTFaction.WESTERLANDS), WESTERLING(id++, "westerling", GOTFaction.WESTERLANDS), JAST(id++, "jast", GOTFaction.WESTERLANDS), KENNINGK(id++, "kenningK", GOTFaction.WESTERLANDS), CRAKEHALL(id++, "crakehall", GOTFaction.WESTERLANDS), LEFFORD(id++, "lefford", GOTFaction.WESTERLANDS), LYDDEN(id++, "lydden", GOTFaction.WESTERLANDS), MARBRAND(id++, "marbrand", GOTFaction.WESTERLANDS), MORELAND(id++, "moreland", GOTFaction.WESTERLANDS), PLUMM(id++, "plumm", GOTFaction.WESTERLANDS), PRESTER(id++, "prester", GOTFaction.WESTERLANDS),
		SARWYCK(id++, "sarwyck", GOTFaction.WESTERLANDS), SERRETT(id++, "serrett", GOTFaction.WESTERLANDS), SERRETTBOOK(id++, "serrettBook", GOTFaction.WESTERLANDS), STACKSPEAR(id++, "stackspear", GOTFaction.WESTERLANDS), FARMAN(id++, "farman", GOTFaction.WESTERLANDS), FALWELL(id++, "falwell", GOTFaction.WESTERLANDS), ESTREN(id++, "estren", GOTFaction.WESTERLANDS), WESTFORD(id++, "westford", GOTFaction.WESTERLANDS), VIKARY(id++, "vikary", GOTFaction.WESTERLANDS), GREENFIELD(id++, "greenfield", GOTFaction.WESTERLANDS), YEW(id++, "yew", GOTFaction.WESTERLANDS), CLEGANE(id++, "clegane", GOTFaction.WESTERLANDS), LORCH(id++, "lorch", GOTFaction.WESTERLANDS), RUTTIGER(id++, "ruttiger", GOTFaction.WESTERLANDS), SWYFT(id++, "swyft", GOTFaction.WESTERLANDS), HETHERSPOON(id++, "hetherspoon", GOTFaction.WESTERLANDS), BETTLEY(id++, "bettley", GOTFaction.WESTERLANDS), BROOM(id++, "broom", GOTFaction.WESTERLANDS), LANNISPORT(id++, "lannisport", GOTFaction.WESTERLANDS), LANNISTERD(id++, "lannisterD", GOTFaction.WESTERLANDS), LANNISTERTYGETT(id++, "lannisterTygett", GOTFaction.WESTERLANDS), PAYNE(id++, "payne", GOTFaction.WESTERLANDS), SARSFIELD(id++, "sarsfield", GOTFaction.WESTERLANDS), TURNBERRY(id++, "turnberry", GOTFaction.WESTERLANDS), FOOTE(id++, "foote", GOTFaction.WESTERLANDS), YARWYCK(id++, "yarwyck", GOTFaction.WESTERLANDS), SPICER(id++, "spicer", GOTFaction.WESTERLANDS), ALGOOD(id++, "algood", GOTFaction.WESTERLANDS), CLIFTON(id++, "clifton", GOTFaction.WESTERLANDS), DOGGETT(id++, "doggett", GOTFaction.WESTERLANDS), DROX(id++, "drox", GOTFaction.WESTERLANDS), FERREN(id++, "ferren", GOTFaction.WESTERLANDS), FREYRIVERRUN(id++, "freyriverrun", GOTFaction.WESTERLANDS), CLEOS(id++, "cleos", GOTFaction.UNALIGNED), HAWTHORNE(id++, "hawthorne", GOTFaction.WESTERLANDS), KYNDALL(id++, "kyndall", GOTFaction.WESTERLANDS), LANNETT(id++, "lannett", GOTFaction.WESTERLANDS), LANNY(id++, "lanny", GOTFaction.WESTERLANDS), LANTELL(id++, "lantell", GOTFaction.WESTERLANDS), MYATT(id++, "myatt", GOTFaction.WESTERLANDS), PECKLEDON(id++, "peckledon", GOTFaction.WESTERLANDS), TYRION(id++, "tyrion", GOTFaction.WESTERLANDS), AMORYLORCH(id++, "amorylorch", GOTFaction.WESTERLANDS), JOFFREY(id++, "joffrey", GOTFaction.CROWNLANDS), HAND(id++, "hand", GOTFaction.CROWNLANDS), BAELISH(id++, "baelish", GOTFaction.CROWNLANDS), BAELISHBOOK(id++, "baelishBook", GOTFaction.CROWNLANDS), BUCKWELL(id++, "buckwell", GOTFaction.CROWNLANDS), BYRCH(id++, "byrch", GOTFaction.CROWNLANDS), WENDWATER(id++, "wendwater", GOTFaction.CROWNLANDS), MALLERY(id++, "mallery", GOTFaction.CROWNLANDS), MANNING(id++, "manning", GOTFaction.CROWNLANDS), RIKKER(id++, "rikker", GOTFaction.CROWNLANDS), ROSBY(id++, "rosby", GOTFaction.CROWNLANDS), STOKEWORTH(id++, "stokeworth", GOTFaction.CROWNLANDS), HARTE(id++, "harte", GOTFaction.CROWNLANDS), HAYFORD(id++, "hayford", GOTFaction.CROWNLANDS), CHELSTED(id++, "chelsted", GOTFaction.CROWNLANDS), BRUNEB(id++, "bruneB", GOTFaction.CROWNLANDS), BRUNEDD(id++, "bruneDD", GOTFaction.CROWNLANDS), BYWATER(id++, "bywater", GOTFaction.CROWNLANDS), BLOUNT(id++, "blount", GOTFaction.CROWNLANDS), GAUNT(id++, "gaunt", GOTFaction.CROWNLANDS), SLYNT(id++, "slynt", GOTFaction.CROWNLANDS), THORNE(id++, "thorne", GOTFaction.CROWNLANDS), KETTLEBLACK(id++, "kettleblack", GOTFaction.CROWNLANDS), BOGGS(id++, "boggs", GOTFaction.CROWNLANDS), CAVE(id++, "cave", GOTFaction.CROWNLANDS), CRESSEY(id++, "cressey", GOTFaction.CROWNLANDS), DARGOOD(id++, "dargood", GOTFaction.CROWNLANDS), DARKE(id++, "darke", GOTFaction.CROWNLANDS), DARKWOOD(id++, "darkwood", GOTFaction.CROWNLANDS), HARDY(id++, "hardy", GOTFaction.CROWNLANDS), HOGG(id++, "hogg", GOTFaction.CROWNLANDS), LANGWARD(id++, "langward", GOTFaction.CROWNLANDS), LONGWATERS(id++, "longwaters", GOTFaction.CROWNLANDS), PYLE(id++, "pyle", GOTFaction.CROWNLANDS), PYNE(id++, "pyne", GOTFaction.CROWNLANDS), ROLLINGFORD(id++, "rollingford", GOTFaction.CROWNLANDS), BRONNBOOK(id++, "bronnBook", GOTFaction.CROWNLANDS), TALLAD(id++, "tallad", GOTFaction.CROWNLANDS), LOTHOR(id++, "lothor", GOTFaction.CROWNLANDS), MORROS(id++, "morros", GOTFaction.CROWNLANDS), STANNIS(id++, "stannis", GOTFaction.DRAGONSTONE), STANNISBOOK(id++, "stannisBook", GOTFaction.DRAGONSTONE), VELARYON(id++, "velaryon", GOTFaction.DRAGONSTONE), VELARYONBOOK(id++, "velaryonBook", GOTFaction.DRAGONSTONE), BAREMMON(id++, "baremmon", GOTFaction.DRAGONSTONE), SUNGLASS(id++, "sunglass", GOTFaction.DRAGONSTONE), CELTIGAR(id++, "celtigar", GOTFaction.DRAGONSTONE), MASSEY(id++, "massey", GOTFaction.DRAGONSTONE), FLORENT(id++, "florent", GOTFaction.DRAGONSTONE), FLORENTBOOK(id++, "florentBook", GOTFaction.DRAGONSTONE), STAUNTON(id++, "staunton", GOTFaction.DRAGONSTONE), FOLLARD(id++, "follard", GOTFaction.DRAGONSTONE), CHYTTERING(id++, "chyttering", GOTFaction.DRAGONSTONE), SEAWORTH(id++, "seaworth", GOTFaction.DRAGONSTONE), FARRING(id++, "farring", GOTFaction.DRAGONSTONE), PENDRAGON(id++, "pendragon", GOTFaction.DRAGONSTONE), ALQALIS(id++, "alqalis", GOTFaction.DRAGONSTONE), RAMBTON(id++, "rambton", GOTFaction.DRAGONSTONE), HORPE(id++, "horpe", GOTFaction.DRAGONSTONE), GOWER(id++, "gower", GOTFaction.DRAGONSTONE), PATREK(id++, "patrek", GOTFaction.DRAGONSTONE), SUGGS(id++, "suggs", GOTFaction.DRAGONSTONE), ROLLANDSTORM(id++, "rollandStorm", GOTFaction.DRAGONSTONE), AURANEWATERS(id++, "auranewaters", GOTFaction.DRAGONSTONE), SHADRICH(id++, "shadrich", GOTFaction.DRAGONSTONE), ROBERT(id++, "robert", GOTFaction.STORMLANDS), ROBERTB(id++, "robertB", GOTFaction.STORMLANDS), RENLY(id++, "renly", GOTFaction.STORMLANDS), RENLYBOOK(id++, "renlyBook", GOTFaction.STORMLANDS), BUCKLER(id++, "buckler", GOTFaction.STORMLANDS), GRANDISON(id++, "grandison", GOTFaction.STORMLANDS), CAFFEREN(id++, "cafferen", GOTFaction.STORMLANDS), MERTYNS(id++, "mertyns", GOTFaction.STORMLANDS), MORRIGEN(id++, "morrigen", GOTFaction.STORMLANDS), PENROSE(id++, "penrose", GOTFaction.STORMLANDS), STAEDMON(id++, "staedmon", GOTFaction.STORMLANDS), TARTH(id++, "tarth", GOTFaction.STORMLANDS), TARTHBOOK(id++, "tarthBook", GOTFaction.STORMLANDS), WYLDE(id++, "wylde", GOTFaction.STORMLANDS), FELL(id++, "fell", GOTFaction.STORMLANDS), ERROL(id++, "errol", GOTFaction.STORMLANDS), ESTERMONT(id++, "estermont", GOTFaction.STORMLANDS), DONDARRION(id++, "dondarrion", GOTFaction.STORMLANDS), DONDARRIONBOOK(id++, "dondarrionBook", GOTFaction.STORMLANDS), CARON(id++, "caron", GOTFaction.STORMLANDS), SWANN(id++, "swann", GOTFaction.STORMLANDS), SWANNBOOK(id++, "swannBook", GOTFaction.STORMLANDS), SELMY(id++, "selmy", GOTFaction.STORMLANDS), BOLLING(id++, "bolling", GOTFaction.STORMLANDS), CONNINGTON(id++, "connington", GOTFaction.STORMLANDS), HASTY(id++, "hasty", GOTFaction.STORMLANDS), WAGSTAFF(id++, "wagstaff", GOTFaction.STORMLANDS), WENSINGTON(id++, "wensington", GOTFaction.STORMLANDS), PEASEBURY(id++, "peasebury", GOTFaction.STORMLANDS), ROGERS(id++, "rogers", GOTFaction.STORMLANDS), FOOTEN(id++, "footeN", GOTFaction.STORMLANDS), TRANT(id++, "trant", GOTFaction.STORMLANDS), HERSTON(id++, "herston", GOTFaction.STORMLANDS), KELLINGTON(id++, "kellington", GOTFaction.STORMLANDS), LONMOUTH(id++, "lonmouth", GOTFaction.STORMLANDS), MUSGOOD(id++, "musgood", GOTFaction.STORMLANDS), SWYGERT(id++, "swygert", GOTFaction.STORMLANDS), TUDBURY(id++, "tudbury", GOTFaction.STORMLANDS), WHITEHEAD(id++, "whitehead", GOTFaction.STORMLANDS), PEARSE(id++, "whitehead", GOTFaction.STORMLANDS), EDRICSTORM(id++, "edricstorm", GOTFaction.STORMLANDS), TYRELL(id++, "tyrell", GOTFaction.REACH), AMBROSE(id++, "ambrose", GOTFaction.REACH), BLACKBAR(id++, "blackbar", GOTFaction.REACH), VARNER(id++, "varner", GOTFaction.REACH), VYRWEL(id++, "vyrwel", GOTFaction.REACH), CASWELL(id++, "caswell", GOTFaction.REACH), CORDWAYNER(id++, "cordwayner", GOTFaction.REACH), COCKSHAW(id++, "cockshaw", GOTFaction.REACH), CRANE(id++, "crane", GOTFaction.REACH), MEADOWS(id++, "meadows", GOTFaction.REACH), MERRYWEATHER(id++, "merryweather", GOTFaction.REACH), OAKHEART(id++, "oakheart", GOTFaction.REACH), PEAKE(id++, "peake", GOTFaction.REACH), REDWYNE(id++, "redwyne", GOTFaction.REACH), REDWYNEBOOK(id++, "redwyneBook", GOTFaction.REACH), ROWAN(id++, "rowan", GOTFaction.REACH), TARLY(id++, "tarly", GOTFaction.REACH), TARLY_BOOK(id++, "tarlyBook", GOTFaction.REACH), TYRELLBK(id++, "tyrellBK", GOTFaction.REACH), TYRELLL(id++, "tyrelll", GOTFaction.REACH), FOSSOWAYCH(id++, "fossowayCH", GOTFaction.REACH), FOOTLY(id++, "footly", GOTFaction.REACH), HIGHTOWER(id++, "hightower", GOTFaction.REACH), HIGHTOWERBOOK(id++, "hightowerBook", GOTFaction.REACH), SHERMER(id++, "shermer", GOTFaction.REACH), APPLETON(id++, "appleton", GOTFaction.REACH), ASHFORD(id++, "ashford", GOTFaction.REACH), BEESBURY(id++, "beesbury", GOTFaction.REACH), BULLWER(id++, "bullwer", GOTFaction.REACH), COSTAYNE(id++, "costayne", GOTFaction.REACH), CUY(id++, "cuy", GOTFaction.REACH), MULLENDORE(id++, "mullendore", GOTFaction.REACH), GRIMM(id++, "grimm", GOTFaction.REACH), SERRY(id++, "serry", GOTFaction.REACH), HEWETT(id++, "hewett", GOTFaction.REACH), CHESTER(id++, "chester", GOTFaction.REACH), FOSSOWAYNB(id++, "fossowayNB", GOTFaction.REACH), OSGREY(id++, "osgrey", GOTFaction.REACH), OSGREYLL(id++, "osgreyll", GOTFaction.REACH), BALL(id++, "ball", GOTFaction.REACH), WEBBER(id++, "webber", GOTFaction.REACH), WILLUM(id++, "willum", GOTFaction.REACH), WOODWRIGHT(id++, "woodwright", GOTFaction.REACH), GRACEFORD(id++, "graceford", GOTFaction.REACH), INCHFIELD(id++, "inchfield", GOTFaction.REACH), LEYGOOD(id++, "leygood", GOTFaction.REACH), KIDWELL(id++, "kidwell", GOTFaction.REACH), NORCROSS(id++, "norcross", GOTFaction.REACH), RISLEY(id++, "risley", GOTFaction.REACH), ROXTON(id++, "roxton", GOTFaction.REACH), WYTHERS(id++, "wythers", GOTFaction.REACH), HUNT(id++, "hunt", GOTFaction.REACH), BRIDGES(id++, "bridges", GOTFaction.REACH), BUSHY(id++, "bushy", GOTFaction.REACH), DUNN(id++, "dunn", GOTFaction.REACH), DURWELL(id++, "durwell", GOTFaction.REACH),
		HASTWYCK(id++, "hastwyck", GOTFaction.REACH), HUTCHESON(id++, "hutcheson", GOTFaction.REACH), LOWTHER(id++, "lowther", GOTFaction.REACH), LYBERR(id++, "lyberr", GOTFaction.REACH), MORGRYN(id++, "morgryn", GOTFaction.REACH), NORRIDGE(id++, "norridge", GOTFaction.REACH), OLDFLOWERS(id++, "oldflowers", GOTFaction.REACH), ORME(id++, "orme", GOTFaction.REACH), OVERTON(id++, "overton", GOTFaction.REACH), POMMINGHAM(id++, "pommingham", GOTFaction.REACH), RHYSLING(id++, "rhysling", GOTFaction.REACH), SLOANE(id++, "sloane", GOTFaction.REACH), STACKHOUSE(id++, "stackhouse", GOTFaction.REACH), UFFERING(id++, "uffering", GOTFaction.REACH), WESTBROOK(id++, "westbrook", GOTFaction.REACH), WHITEGROVE(id++, "whitegrove", GOTFaction.REACH), BRONN(id++, "bronn", GOTFaction.REACH), MARTELL(id++, "martell", GOTFaction.DORNE), MARTELLBOOK(id++, "martellBook", GOTFaction.DORNE), OBERYN(id++, "oberyn", GOTFaction.DORNE), SANDSNAKES(id++, "sandsnakes", GOTFaction.DORNE), YRONWOOD(id++, "yronwood", GOTFaction.DORNE), ALLYRION(id++, "allyrion", GOTFaction.DORNE), BLACKMONT(id++, "blackmont", GOTFaction.DORNE), VAITH(id++, "vaith", GOTFaction.DORNE), WYL(id++, "wyl", GOTFaction.DORNE), GARGALEN(id++, "gargalen", GOTFaction.DORNE), DAYNE(id++, "dayne", GOTFaction.DORNE), JORDAYNE(id++, "jordayne", GOTFaction.DORNE), QORGILE(id++, "qorgile", GOTFaction.DORNE), MANWOODY(id++, "manwoody", GOTFaction.DORNE), TOLAND(id++, "toland", GOTFaction.DORNE), ULLER(id++, "uller", GOTFaction.DORNE), FOWLER(id++, "fowler", GOTFaction.DORNE), FOWLERBOOK(id++, "fowlerBook", GOTFaction.DORNE), DALT(id++, "dalt", GOTFaction.DORNE), DAYNEHH(id++, "dayneHH", GOTFaction.DORNE), SANTAGAR(id++, "santagar", GOTFaction.DORNE), DRINKWATER(id++, "drinkwater", GOTFaction.DORNE), LADYBRIGHT(id++, "ladybright", GOTFaction.DORNE), TARWICK(id++, "tarwick", GOTFaction.DORNE), WELLSD(id++, "wellsD", GOTFaction.DORNE), BRAAVOS(id++, "braavos", GOTFaction.BRAAVOS), IRONBANK(id++, "ironbank", GOTFaction.BRAAVOS), PENTOS(id++, "pentos", GOTFaction.PENTOS), MOPATIS(id++, "mopatis", GOTFaction.PENTOS), LORATH(id++, "lorath", GOTFaction.LORATH), MYR(id++, "myr", GOTFaction.MYR), LYS(id++, "lys", GOTFaction.LYS), SAAN(id++, "saan", GOTFaction.LYS), QOHOR(id++, "qohor", GOTFaction.QOHOR), NORVOS(id++, "norvos", GOTFaction.NORVOS), TYROSH(id++, "tyrosh", GOTFaction.TYROSH), VOLANTIS(id++, "volantis", GOTFaction.VOLANTIS), TIGERS(id++, "tigers", GOTFaction.VOLANTIS), ELEPHANTS(id++, "elephants", GOTFaction.VOLANTIS), LHAZAR(id++, "lhazar", GOTFaction.LHAZAR), GHISCAR(id++, "ghiscar", GOTFaction.GHISCAR), ASTAPOR(id++, "astapor", GOTFaction.GHISCAR), YUNKAI(id++, "yunkai", GOTFaction.GHISCAR), MEEREEN(id++, "meereen", GOTFaction.GHISCAR), NEWGHIS(id++, "newghis", GOTFaction.GHISCAR), LORAQ(id++, "loraq", GOTFaction.GHISCAR), QARTH(id++, "qarth", GOTFaction.QARTH), IBBEN(id++, "ibben", GOTFaction.IBBEN), SUMMER(id++, "summer", GOTFaction.SUMMER_ISLANDS), YITI(id++, "yiti", GOTFaction.YI_TI), YITIBU(id++, "yitiBu", GOTFaction.YI_TI), YITICHOQ(id++, "yitiChoq", GOTFaction.YI_TI), YITIHAR(id++, "yitiHar", GOTFaction.YI_TI), YITIJAR(id++, "yitiJar", GOTFaction.YI_TI), YITILO(id++, "yitiLo", GOTFaction.YI_TI), YITIMAROON(id++, "yitiMaroon", GOTFaction.YI_TI), YITIMENGO(id++, "yitiMengo", GOTFaction.YI_TI), YITIPEARL(id++, "yitiPearl", GOTFaction.YI_TI), YITIPURPLE(id++, "yitiPurple", GOTFaction.YI_TI), YITIQO(id++, "yitiQo", GOTFaction.YI_TI), LENG(id++, "leng", GOTFaction.YI_TI), SOTHORYOS(id++, "sothoryos", GOTFaction.SOTHORYOS), WILDLING(id++, "wildling", GOTFaction.WILDLING), THENN(id++, "thenn", GOTFaction.WILDLING), REDBEARD(id++, "redbeard", GOTFaction.WILDLING), HILLMEN(id++, "hillmen", GOTFaction.HILL_TRIBES), NIGHT(id++, "night", GOTFaction.NIGHT_WATCH), SPARROW(id++, "sparrow", GOTFaction.UNALIGNED), MAESTERS(id++, "maesters", GOTFaction.UNALIGNED), SECONDSONS(id++, "secondsons", GOTFaction.UNALIGNED), GOLDENCOMPANY(id++, "goldencompany", GOTFaction.UNALIGNED), WINDBLOWN(id++, "windblown", GOTFaction.UNALIGNED), STORMCROWS(id++, "stormcrows", GOTFaction.UNALIGNED), BRAVECOMPANIONS(id++, "bravecompanions", GOTFaction.UNALIGNED), WARRIORSSONS(id++, "warriorsSons", GOTFaction.UNALIGNED), POORFELLOWS(id++, "poorFellows", GOTFaction.UNALIGNED), ANDAL(id++, "andal", GOTFaction.UNALIGNED), SEPT(id++, "sept", GOTFaction.UNALIGNED), FACELESS(id++, "faceless", GOTFaction.UNALIGNED), KINGSGUARD(id++, "kingsguard", GOTFaction.CROWNLANDS), RENLYGUARD(id++, "renlyguard", GOTFaction.STORMLANDS), BROTHERHOOD(id++, "brotherhood", GOTFaction.UNALIGNED), RHLLOR(id++, "rhllor", GOTFaction.UNALIGNED), WHITE(id++, "white", GOTFaction.UNALIGNED), TARGARYEN2(id++, "targaryen2", GOTFaction.UNALIGNED), TARGARYENBOOK(id++, "targaryenBook", GOTFaction.UNALIGNED), BLACKFYRE(id++, "blackfyre", GOTFaction.UNALIGNED), BLACKFYREBOOK(id++, "blackfyreBook", GOTFaction.UNALIGNED), BLACKS(id++, "blacks", GOTFaction.UNALIGNED), BLACKSBOOK(id++, "blacksBook", GOTFaction.UNALIGNED), GREENS(id++, "greens", GOTFaction.UNALIGNED), GREENSBOOK(id++, "greensBook", GOTFaction.UNALIGNED), AEMON(id++, "aemon", GOTFaction.UNALIGNED), AEMONBOOK(id++, "aemonBook", GOTFaction.UNALIGNED), AERION(id++, "aerion", GOTFaction.UNALIGNED), AERIONBOOK(id++, "aerionBook", GOTFaction.UNALIGNED), VALARR(id++, "valarr", GOTFaction.UNALIGNED), VALARRBOOK(id++, "valarrBook", GOTFaction.UNALIGNED), BITTERSTEEL(id++, "bittersteel", GOTFaction.UNALIGNED), BLOODRAVEN(id++, "bloodraven", GOTFaction.UNALIGNED), BLOODRAVENBOOK(id++, "bloodravenBook", GOTFaction.UNALIGNED), SEASTAR(id++, "seastar", GOTFaction.UNALIGNED), MAEKAR(id++, "maekar", GOTFaction.UNALIGNED), MAEKARBOOK(id++, "maekarBook", GOTFaction.UNALIGNED), DAEMON(id++, "daemon", GOTFaction.UNALIGNED), TRUEFYRE(id++, "truefyre", GOTFaction.UNALIGNED), DAERON(id++, "daeron", GOTFaction.UNALIGNED), DAERONBOOK(id++, "daeronBook", GOTFaction.UNALIGNED), RHAEGAR(id++, "rhaegar", GOTFaction.UNALIGNED), YOUNGGRIFF(id++, "youngGriff", GOTFaction.UNALIGNED), JONCONNINGTON(id++, "jonconnington", GOTFaction.UNALIGNED), UNDERLEAF(id++, "underleaf", GOTFaction.UNALIGNED), ROSSART(id++, "rossart", GOTFaction.UNALIGNED), DARKLYN(id++, "darklyn", GOTFaction.CROWNLANDS), HOLLARD(id++, "hollard", GOTFaction.CROWNLANDS), CARGYLE(id++, "cargyle", GOTFaction.CROWNLANDS), CRABB(id++, "crabb", GOTFaction.CROWNLANDS), GREYSTARK(id++, "greystark", GOTFaction.NORTH), FISHERSS(id++, "fisherSS", GOTFaction.NORTH), FLINTBH(id++, "flintBH", GOTFaction.NORTH), FROST(id++, "frost", GOTFaction.NORTH), WOODFOOT(id++, "woodfoot", GOTFaction.NORTH), FIRSTMEN(id++, "firstmen", GOTFaction.NORTH), JUSTMAN(id++, "justman", GOTFaction.RIVERLANDS), QOHERYS(id++, "qoherys", GOTFaction.RIVERLANDS), TOWERS(id++, "towers", GOTFaction.RIVERLANDS), TEAGUE(id++, "teague", GOTFaction.RIVERLANDS), FISHER(id++, "fisher", GOTFaction.RIVERLANDS), HARROWAY(id++, "harroway", GOTFaction.RIVERLANDS), MUDD(id++, "mudd", GOTFaction.RIVERLANDS), ARLAN(id++, "arlan", GOTFaction.RIVERLANDS), ROBERTBLACKWOOD(id++, "robertblackwood", GOTFaction.RIVERLANDS), ROLANDBLACKWOOD(id++, "rolandblackwood", GOTFaction.RIVERLANDS), ROGERBLACKWOOD(id++, "rogerblackwood", GOTFaction.RIVERLANDS), SHELL(id++, "shell", GOTFaction.ARRYN), BRIGHTSTONE(id++, "brightstone", GOTFaction.ARRYN), GREYIRON(id++, "greyiron", GOTFaction.IRONBORN), HOARE(id++, "hoare", GOTFaction.IRONBORN), DALTON(id++, "dalton", GOTFaction.IRONBORN), CASTERLY(id++, "casterly", GOTFaction.WESTERLANDS), PARREN(id++, "parren", GOTFaction.WESTERLANDS), REYNE(id++, "reyne", GOTFaction.WESTERLANDS), REYNEBOOK(id++, "reyneBook", GOTFaction.WESTERLANDS), TARBECK(id++, "tarbeck", GOTFaction.WESTERLANDS), TYBOLT(id++, "tybolt", GOTFaction.WESTERLANDS), OAKENFIST(id++, "oakenfist", GOTFaction.DRAGONSTONE), SEASMOKE(id++, "seasmoke", GOTFaction.DRAGONSTONE), DURRANDON(id++, "durrandon", GOTFaction.STORMLANDS), COLE(id++, "cole", GOTFaction.STORMLANDS), TOYNE(id++, "toyne", GOTFaction.STORMLANDS), GARDENER(id++, "gardener", GOTFaction.REACH), GARDENERBOOK(id++, "gardenerBook", GOTFaction.REACH), FIREBALL(id++, "fireball", GOTFaction.REACH), STRICKLAND(id++, "strickland", GOTFaction.REACH), ROBERTASHFORD(id++, "robertashford", GOTFaction.REACH), BENNIS(id++, "bennis", GOTFaction.REACH), DERRICKFOSSOWAY(id++, "derrickfossoway", GOTFaction.REACH), MORS(id++, "mors", GOTFaction.DORNE), MORSBOOK(id++, "morsBook", GOTFaction.DORNE), NYMERIA(id++, "nymeria", GOTFaction.DORNE), DRYLAND(id++, "dryland", GOTFaction.DORNE), VULTUREKING(id++, "vultureking", GOTFaction.DORNE), BELAERYS(id++, "belaerys", GOTFaction.UNALIGNED), GALTRY(id++, "galtry", GOTFaction.UNALIGNED), ILLIFER(id++, "illifer", GOTFaction.UNALIGNED), CREIGHTON(id++, "creighton", GOTFaction.UNALIGNED), DUNCAN(id++, "duncan", GOTFaction.CROWNLANDS), LAUGHINGTREE(id++, "laughingTree", GOTFaction.NORTH), LEATHER(id++, "leather", GOTFaction.UNALIGNED), TARGARYEN(id++, "targaryen", GOTFaction.UNALIGNED), STARKHD(id++, "starkHD", GOTFaction.NORTH), TULLYHD(id++, "tullyHD", GOTFaction.RIVERLANDS), ARRYNHD(id++, "arrynHD", GOTFaction.ARRYN), GREYJOYHD(id++, "greyjoyHD", GOTFaction.IRONBORN), LANNISTERHD(id++, "lannisterHD", GOTFaction.WESTERLANDS), JOFFREYHD(id++, "joffreyHD", GOTFaction.CROWNLANDS), STANNISHD(id++, "stannisHD", GOTFaction.DRAGONSTONE), BARATHEONHD(id++, "baratheonHD", GOTFaction.STORMLANDS), TYRELLHD(id++, "tyrellHD", GOTFaction.REACH), MARTELLHD(id++, "martellHD", GOTFaction.DORNE), WALKER(id++, "walker", GOTFaction.WHITE_WALKER), ASSHAI(id++, "asshai", GOTFaction.ASSHAI), MOSSOVY(id++, "mossovy", GOTFaction.MOSSOVY), JOGOS(id++, "jogos", GOTFaction.JOGOS), NULL(id++, "null", GOTFaction.UNALIGNED);

		public static List<BannerType> bannerTypes;
		public static Map<Integer, BannerType> bannerForID;
		static {
			bannerTypes = new ArrayList<>();
			bannerForID = new HashMap<>();
			for (BannerType t : BannerType.values()) {
				bannerTypes.add(t);
				bannerForID.put(t.bannerID, t);
			}
		}
		public int bannerID;
		public String bannerName;

		public GOTFaction faction;

		BannerType(int i, String s, GOTFaction f) {
			bannerID = i;
			bannerName = s;
			faction = f;
			faction.factionBanners.add(this);
		}

		public static BannerType forID(int ID) {
			if (bannerForID.get(ID) == null) {
				return NULL;
			}
			return bannerForID.get(ID);
		}
	}

}