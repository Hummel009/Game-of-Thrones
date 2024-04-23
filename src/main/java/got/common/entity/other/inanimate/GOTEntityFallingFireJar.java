package got.common.entity.other.inanimate;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GOTEntityFallingFireJar extends EntityFallingBlock implements IEntityAdditionalSpawnData {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityFallingFireJar(World world) {
		super(world);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityFallingFireJar(World world, double d, double d1, double d2, Block block) {
		super(world, d, d1, d2, block);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityFallingFireJar(World world, double d, double d1, double d2, Block block, int meta) {
		super(world, d, d1, d2, block, meta);
	}

	@Override
	public void readSpawnData(ByteBuf data) {
		double x = data.readDouble();
		double y = data.readDouble();
		double z = data.readDouble();
		Block block = Block.getBlockById(data.readInt());
		byte meta = data.readByte();
		EntityFallingBlock proxy = new EntityFallingBlock(worldObj, x, y, z, block, meta);
		NBTTagCompound nbt = new NBTTagCompound();
		proxy.writeToNBT(nbt);
		readFromNBT(nbt);
	}

	@Override
	public void writeSpawnData(ByteBuf data) {
		data.writeDouble(prevPosX);
		data.writeDouble(prevPosY);
		data.writeDouble(prevPosZ);
		data.writeInt(Block.getIdFromBlock(func_145805_f()));
		data.writeByte(field_145814_a);
	}
}