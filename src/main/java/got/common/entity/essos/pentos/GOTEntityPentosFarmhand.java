package got.common.entity.essos.pentos;

import got.common.entity.ai.GOTEntityAIFarm;
import got.common.entity.other.iface.GOTFarmhand;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class GOTEntityPentosFarmhand extends GOTEntityPentosMan implements GOTFarmhand {
	private Item seedsItem;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityPentosFarmhand(World world) {
		super(world);
		tasks.addTask(3, new GOTEntityAIFarm(this, 1.0, 1.0f));
		targetTasks.taskEntries.clear();
	}

	@Override
	public IPlantable getSeedsItem() {
		if (seedsItem == null) {
			return (IPlantable) Items.wheat_seeds;
		}
		return (IPlantable) seedsItem;
	}

	@Override
	public void setSeedsItem(Item seed) {
		seedsItem = seed;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_hoe));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		return entityData;
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
