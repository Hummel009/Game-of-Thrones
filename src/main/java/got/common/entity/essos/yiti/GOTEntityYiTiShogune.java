package got.common.entity.essos.yiti;

import got.common.GOTLevelData;
import got.common.database.GOTInvasions;
import got.common.database.GOTItems;
import got.common.database.GOTSpeech;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.iface.GOTUnitTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYiTiShogune extends GOTEntityYiTiSamurai implements GOTUnitTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityYiTiShogune(World world) {
		super(world);
		addTargetTasks(false);
		spawnRidingHorse = false;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityPlayer) {
		return GOTLevelData.getData(entityPlayer).getAlignment(getFaction()) >= 50.0f && isFriendly(entityPlayer);
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityPlayer) {
		return GOTSpeech.getCaptainSpeech(this, entityPlayer);
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.YITI;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return null;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.yitiSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.yitiBootsSamurai));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.yitiLeggingsSamurai));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.yitiChestplateSamurai));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.yitiHelmetShogune));
		return entityData;
	}
}