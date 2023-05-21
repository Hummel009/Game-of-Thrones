package got.common.entity.sothoryos.sothoryos;

import got.common.database.GOTRegistry;
import got.common.entity.ai.GOTEntityAIFarm;
import got.common.entity.other.GOTFarmhand;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class GOTEntitySothoryosFarmhand extends GOTEntitySothoryosMan implements GOTFarmhand {
	public Item seedsItem;

	public GOTEntitySothoryosFarmhand(World world) {
		super(world);
		canBeMarried = false;
		tasks.addTask(3, new GOTEntityAIFarm(this, 1.0, 1.2f));
		targetTasks.taskEntries.clear();
		addTargetTasks(false);
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
			return "standard/civilized/hired_farmhand";
		}
		return super.getSpeechBank(entityplayer);
	}

	@Override
	public IPlantable getUnhiredSeeds() {
		if (seedsItem == null) {
			return (IPlantable) Items.potato;
		}
		return (IPlantable) seedsItem;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.sothoryosHoe));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		Item item;
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("SeedsID") && (item = Item.getItemById(nbt.getInteger("SeedsID"))) != null && item instanceof IPlantable) {
			seedsItem = item;
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		if (seedsItem != null) {
			nbt.setInteger("SeedsID", Item.getIdFromItem(seedsItem));
		}
	}
}
