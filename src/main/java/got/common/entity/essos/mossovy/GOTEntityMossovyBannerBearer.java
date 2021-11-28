package got.common.entity.essos.mossovy;

import got.common.GOTLevelData;
import got.common.database.*;
import got.common.entity.other.*;
import got.common.item.other.GOTItemBanner;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityMossovyBannerBearer extends GOTEntityMossovyMan implements GOTBannerBearer, GOTMercenary {
	public GOTEntityMossovyBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return GOTLevelData.getData(entityplayer).getAlignment(getFaction()) >= 0.0f && isFriendly(entityplayer);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.NIGHT;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.KILL_WITCHER;
	}

	@Override
	public float getMercAlignmentRequired() {
		return 10.0f;
	}

	@Override
	public int getMercBaseCost() {
		return GOTUnitTradeEntries.SOLDIER + 25;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.mossovySword));
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.mossovyBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.mossovyLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.mossovyChestplate));
		setCurrentItemOrArmor(4, null);
		return data;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.TRADE);
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
