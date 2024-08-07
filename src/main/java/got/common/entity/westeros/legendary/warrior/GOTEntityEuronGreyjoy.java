package got.common.entity.westeros.legendary.warrior;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetBasic;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.westeros.legendary.quest.GOTEntityBalonGreyjoy;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityEuronGreyjoy extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityEuronGreyjoy(World world) {
		super(world);
		addTargetTasks();
		setupLegendaryNPC(true);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.IRONBORN;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killEuronGreyjoy;
	}

	private void addTargetTasks() {
		int target = addTargetTasks(true);
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityBalonGreyjoy.class, 0, true));
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTItems.valyrianChainmailBoots, 1);
		dropItem(GOTItems.valyrianChainmailChestplate, 1);
		dropItem(GOTItems.valyrianChainmailLeggings, 1);
		dropItem(GOTItems.valyrianChainmailHelmet, 1);
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		if (npc.isFriendly(entityPlayer)) {
			return "legendary/euron_friendly";
		}
		return "legendary/euron_hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.alloySteelSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}