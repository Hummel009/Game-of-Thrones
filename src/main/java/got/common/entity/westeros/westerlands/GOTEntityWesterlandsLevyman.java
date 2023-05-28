package got.common.entity.westeros.westerlands;

import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.other.GOTEntityUtils;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityWesterlandsLevyman extends GOTEntityWesterlandsMan {
	public static ItemStack[] militiaWeapons = {new ItemStack(GOTItems.westerosSword), new ItemStack(GOTItems.westerosHammer), new ItemStack(GOTItems.westerosPike), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(GOTItems.ironBattleaxe), new ItemStack(GOTItems.ironPike), new ItemStack(GOTItems.bronzeSword), new ItemStack(GOTItems.bronzeAxe), new ItemStack(GOTItems.bronzeBattleaxe)};

	public GOTEntityWesterlandsLevyman(World world) {
		super(world);
		canBeMarried = false;
		addTargetTasks(true);
	}

	@Override
	public EntityAIBase createWesterlandsAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.4, true);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "standard/civilized/hired_soldier";
			}
			return "standard/civilized/usual_friendly";
		}
		return "standard/civilized/usual_hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(militiaWeapons.length);
		npcItemsInv.setMeleeWeapon(militiaWeapons[i].copy());
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		GOTEntityUtils.setLevymanArmor(this, rand);
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
