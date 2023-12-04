package got.coremod;

import got.common.util.GOTModChecker;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

import static org.objectweb.asm.Opcodes.*;

public class GOTClassTransformer implements IClassTransformer {
	public static String cls_AABB = "net/minecraft/util/AxisAlignedBB";
	public static String cls_AABB_obf = "azt";
	public static String cls_AttributeModifier = "net/minecraft/entity/ai/attributes/AttributeModifier";
	public static String cls_AttributeModifier_obf = "tj";
	public static String cls_Block = "net/minecraft/block/Block";
	public static String cls_Block_obf = "aji";
	public static String cls_BlockDoor = "net/minecraft/block/BlockDoor";
	public static String cls_BlockDoor_obf = "akn";
	public static String cls_BlockPistonBase = "net/minecraft/block/BlockPistonBase";
	public static String cls_BlockPistonBase_obf = "app";
	public static String cls_Blocks = "net/minecraft/init/Blocks";
	public static String cls_Blocks_obf = "ajn";
	public static String cls_CreativeTabs = "net/minecraft/creativetab/CreativeTabs";
	public static String cls_CreativeTabs_obf = "abt";
	public static String cls_DamageSource = "net/minecraft/util/DamageSource";
	public static String cls_DamageSource_obf = "ro";
	public static String cls_Entity = "net/minecraft/entity/Entity";
	public static String cls_Entity_obf = "sa";
	public static String cls_EntityLivingBase = "net/minecraft/entity/EntityLivingBase";
	public static String cls_EntityLivingBase_obf = "sv";
	public static String cls_EntityPlayer = "net/minecraft/entity/player/EntityPlayer";
	public static String cls_EntityPlayer_obf = "yz";
	public static String cls_EnumCreatureAttribute = "net/minecraft/entity/EnumCreatureAttribute";
	public static String cls_EnumCreatureAttribute_obf = "sz";
	public static String cls_IBlockAccess = "net/minecraft/world/IBlockAccess";
	public static String cls_IBlockAccess_obf = "ahl";
	public static String cls_Item = "net/minecraft/item/Item";
	public static String cls_Item_obf = "adb";
	public static String cls_ItemArmor = "net/minecraft/item/ItemArmor";
	public static String cls_ItemArmor_obf = "abb";
	public static String cls_ItemStack = "net/minecraft/item/ItemStack";
	public static String cls_ItemStack_obf = "add";
	public static String cls_Packet = "net/minecraft/network/Packet";
	public static String cls_Packet_obf = "ft";
	public static String cls_PacketS14 = "net/minecraft/network/play/server/S14PacketEntity";
	public static String cls_PacketS14_obf = "hf";
	public static String cls_PacketS18 = "net/minecraft/network/play/server/S18PacketEntityTeleport";
	public static String cls_PacketS18_obf = "ik";
	public static String cls_PathPoint = "net/minecraft/pathfinding/PathPoint";
	public static String cls_PathPoint_obf = "aye";
	public static String cls_World = "net/minecraft/world/World";
	public static String cls_World_obf = "ahb";
	public static String cls_WorldServer = "net/minecraft/world/WorldServer";
	public static String cls_WorldServer_obf = "mt";

	public static <N extends AbstractInsnNode> N findNodeInMethod(MethodNode method, N target) {
		return findNodeInMethod(method, target, 0);
	}

	public static <N extends AbstractInsnNode> N findNodeInMethod(MethodNode method, N targetAbstract, int skip) {
		int skipped = 0;
		ListIterator<AbstractInsnNode> it = method.instructions.iterator();
		while (it.hasNext()) {
			AbstractInsnNode nextAbstract = it.next();
			boolean matched = false;
			if (nextAbstract.getClass() == targetAbstract.getClass()) {
				AbstractInsnNode next;
				AbstractInsnNode target;
				if (targetAbstract.getClass() == InsnNode.class) {
					next = nextAbstract;
					target = targetAbstract;
					if (next.getOpcode() == target.getOpcode()) {
						matched = true;
					}
				} else if (targetAbstract.getClass() == VarInsnNode.class) {
					next = nextAbstract;
					target = targetAbstract;
					if (next.getOpcode() == target.getOpcode() && ((VarInsnNode) next).var == ((VarInsnNode) target).var) {
						matched = true;
					}
				} else if (targetAbstract.getClass() == LdcInsnNode.class) {
					next = nextAbstract;
					target = targetAbstract;
					if (((LdcInsnNode) next).cst.equals(((LdcInsnNode) target).cst)) {
						matched = true;
					}
				} else if (targetAbstract.getClass() == TypeInsnNode.class) {
					next = nextAbstract;
					target = targetAbstract;
					if (next.getOpcode() == target.getOpcode() && ((TypeInsnNode) next).desc.equals(((TypeInsnNode) target).desc)) {
						matched = true;
					}
				} else if (targetAbstract.getClass() == FieldInsnNode.class) {
					next = nextAbstract;
					target = targetAbstract;
					if (next.getOpcode() == target.getOpcode() && ((FieldInsnNode) next).owner.equals(((FieldInsnNode) target).owner) && ((FieldInsnNode) next).name.equals(((FieldInsnNode) target).name) && ((FieldInsnNode) next).desc.equals(((FieldInsnNode) target).desc)) {
						matched = true;
					}
				} else if (targetAbstract.getClass() == MethodInsnNode.class) {
					next = nextAbstract;
					target = targetAbstract;
					if (next.getOpcode() == target.getOpcode() && ((MethodInsnNode) next).owner.equals(((MethodInsnNode) target).owner) && ((MethodInsnNode) next).name.equals(((MethodInsnNode) target).name) && ((MethodInsnNode) next).desc.equals(((MethodInsnNode) target).desc) && ((MethodInsnNode) next).itf == ((MethodInsnNode) target).itf) {
						matched = true;
					}
				}
			}
			if (matched) {
				if (skipped >= skip) {
					return (N) nextAbstract;
				}
				++skipped;
			}
		}
		return null;
	}

	public byte[] patchArmorProperties(String name, byte[] bytes) {
		boolean isCauldron = GOTModChecker.isCauldronServer();
		String targetMethodName = "ApplyArmor";
		String targetMethodSign = "(Lnet/minecraft/entity/EntityLivingBase;[Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/DamageSource;D)F";
		if (isCauldron) {
			targetMethodName = "ApplyArmor";
			targetMethodSign = "(Lnet/minecraft/entity/EntityLivingBase;[Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/DamageSource;DZ)F";
		}
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if (method.name.equals(targetMethodName) && method.desc.equals(targetMethodSign)) {
				AbstractInsnNode nodeFound = null;
				block1:
				for (boolean armorObf : new boolean[]{false, true}) {
					for (int dmgObf = 0; dmgObf < 3; ++dmgObf) {
						String _armor = armorObf ? cls_ItemArmor_obf : cls_ItemArmor;
						String _dmg = new String[]{"field_77879_b", "damageReduceAmount", "c"}[dmgObf];
						FieldInsnNode nodeDmg = new FieldInsnNode(GETFIELD, _armor, _dmg, "I");
						nodeFound = findNodeInMethod(method, nodeDmg);
						if (nodeFound != null) {
							break block1;
						}
					}
				}
				AbstractInsnNode nodePrev;
				if (!((nodePrev = nodeFound.getPrevious()) instanceof VarInsnNode) || nodePrev.getOpcode() != 25 || ((VarInsnNode) nodePrev).var != 9) {
					System.out.println("WARNING! Expected ALOAD 9! Instead got " + nodePrev);
					System.out.println("WARNING! Things may break!");
				}
				method.instructions.remove(nodePrev);
				InsnList newIns = new InsnList();
				if (isCauldron) {
					newIns.add(new VarInsnNode(ALOAD, 8));
				} else {
					newIns.add(new VarInsnNode(ALOAD, 7));
				}
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Enchants", "getDamageReduceAmount", "(Lnet/minecraft/item/ItemStack;)I", false));
				method.instructions.insert(nodeFound, newIns);
				method.instructions.remove(nodeFound);
				if (isCauldron) {
					System.out.println("Hummel009: Patched method " + method.name + " for Cauldron");
				} else {
					System.out.println("Hummel009: Patched method " + method.name);
				}
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockAnvil(String name, byte[] bytes) {
		String targetMethodDesc;
		String targetMethodSign;
		boolean isObf = !name.startsWith("net.minecraft");
		String targetMethodDescObf = targetMethodDesc = "(Lnet/minecraft/world/World;III)Lnet/minecraft/util/AxisAlignedBB;";
		String targetMethodSignObf = targetMethodSign = "Lnet/minecraft/world/World;III";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodNameObf = "func_149668_a";
		String targetMethodName = "getCollisionBoundingBoxFromPool";
		MethodNode newMethod = isObf ? new MethodNode(ACONST_NULL, targetMethodNameObf, targetMethodDescObf, targetMethodSignObf, null) : new MethodNode(ACONST_NULL, targetMethodName, targetMethodDesc, targetMethodSign, null);
		newMethod.instructions.add(new VarInsnNode(ALOAD, 0));
		newMethod.instructions.add(new VarInsnNode(ALOAD, 1));
		newMethod.instructions.add(new VarInsnNode(ILOAD, 2));
		newMethod.instructions.add(new VarInsnNode(ILOAD, 3));
		newMethod.instructions.add(new VarInsnNode(ILOAD, 4));
		newMethod.instructions.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Anvil", "getCollisionBoundingBoxFromPool", "(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;III)Lnet/minecraft/util/AxisAlignedBB;", false));
		newMethod.instructions.add(new InsnNode(ARETURN));
		classNode.methods.add(newMethod);
		System.out.println("Hummel009: Added method " + newMethod.name);
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockCauldron(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodSign = "()I";
		String targetMethodNameObf = "func_149645_b";
		String targetMethodName = "getRenderType";
		for (MethodNode method : classNode.methods) {
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && method.desc.equals(targetMethodSign)) {
				method.instructions.clear();
				InsnList newIns = new InsnList();
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Cauldron", "getRenderType", "()I", false));
				newIns.add(new InsnNode(IRETURN));
				method.instructions.insert(newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockDirt(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodSignObf4 = "(Lahb;III)I";
		String targetMethodSign4 = "(Lnet/minecraft/world/World;III)I";
		String targetMethodNameObf4 = "func_149643_k";
		String targetMethodName4 = "getDamageValue";
		String targetMethodSignObf3 = "(Ladb;Labt;Ljava/util/List;)V";
		String targetMethodSign3 = "(Lnet/minecraft/item/Item;Lnet/minecraft/creativetab/CreativeTabs;Ljava/util/List;)V";
		String targetMethodNameObf3 = "func_149666_a";
		String targetMethodName3 = "getSubBlocks";
		String targetMethodSignObf2 = "(I)Ladd;";
		String targetMethodSign2 = "(I)Lnet/minecraft/item/ItemStack;";
		String targetMethodNameObf2 = "func_149644_j";
		String targetMethodName2 = "createStackedBlock";
		String targetMethodSign = "(I)I";
		String targetMethodNameObf = "func_149692_a";
		String targetMethodName = "damageDropped";
		for (MethodNode method : classNode.methods) {
			if ("<clinit>".equals(method.name)) {
				LdcInsnNode nodeNameIndex1 = findNodeInMethod(method, new LdcInsnNode("default"), 1);
				method.instructions.set(nodeNameIndex1, new LdcInsnNode(GOTReplacedMethods.Dirt.nameIndex1));
				System.out.println("Hummel009: Patched method " + method.name);
			}
			InsnList newIns;
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && method.desc.equals(targetMethodSign)) {
				method.instructions.clear();
				newIns = new InsnList();
				newIns.add(new VarInsnNode(ILOAD, 1));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Dirt", "damageDropped", "(I)I", false));
				newIns.add(new InsnNode(IRETURN));
				method.instructions.insert(newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName2) || method.name.equals(targetMethodNameObf2)) && (method.desc.equals(targetMethodSign2) || method.desc.equals(targetMethodSignObf2))) {
				method.instructions.clear();
				newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 0));
				newIns.add(new VarInsnNode(ILOAD, 1));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Dirt", "createStackedBlock", "(Lnet/minecraft/block/Block;I)Lnet/minecraft/item/ItemStack;", false));
				newIns.add(new InsnNode(ARETURN));
				method.instructions.insert(newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName3) || method.name.equals(targetMethodNameObf3)) && (method.desc.equals(targetMethodSign3) || method.desc.equals(targetMethodSignObf3))) {
				method.instructions.clear();
				newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 0));
				newIns.add(new VarInsnNode(ALOAD, 1));
				newIns.add(new VarInsnNode(ALOAD, 2));
				newIns.add(new VarInsnNode(ALOAD, 3));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Dirt", "getSubBlocks", "(Lnet/minecraft/block/Block;Lnet/minecraft/item/Item;Lnet/minecraft/creativetab/CreativeTabs;Ljava/util/List;)V", false));
				newIns.add(new InsnNode(RETURN));
				method.instructions.insert(newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName4) || method.name.equals(targetMethodNameObf4)) && (method.desc.equals(targetMethodSign4) || method.desc.equals(targetMethodSignObf4))) {
				method.instructions.clear();
				newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 1));
				newIns.add(new VarInsnNode(ILOAD, 2));
				newIns.add(new VarInsnNode(ILOAD, 3));
				newIns.add(new VarInsnNode(ILOAD, 4));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Dirt", "getDamageValue", "(Lnet/minecraft/world/World;III)I", false));
				newIns.add(new InsnNode(IRETURN));
				method.instructions.insert(newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockFence(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodSignObf2 = "(Laji;)Z";
		String targetMethodSign2 = "(Lnet/minecraft/block/Block;)Z";
		String targetMethodName2 = "func_149825_a";
		String targetMethodSignObf = "(Lahl;III)Z";
		String targetMethodSign = "(Lnet/minecraft/world/IBlockAccess;III)Z";
		String targetMethodNameObf = "func_149826_e";
		String targetMethodName = "canConnectFenceTo";
		for (MethodNode method : classNode.methods) {
			InsnList newIns;
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				method.instructions.clear();
				newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 1));
				newIns.add(new VarInsnNode(ILOAD, 2));
				newIns.add(new VarInsnNode(ILOAD, 3));
				newIns.add(new VarInsnNode(ILOAD, 4));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Fence", "canConnectFenceTo", "(Lnet/minecraft/world/IBlockAccess;III)Z", false));
				newIns.add(new InsnNode(IRETURN));
				method.instructions.insert(newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
			if (method.name.equals(targetMethodName2) && (method.desc.equals(targetMethodSign2) || method.desc.equals(targetMethodSignObf2))) {
				method.instructions.clear();
				newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 0));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Fence", "canPlacePressurePlate", "(Lnet/minecraft/block/Block;)Z", false));
				newIns.add(new InsnNode(IRETURN));
				method.instructions.insert(newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockGrass(String name, byte[] bytes) {

		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);

		String targetMethodSignObf = "(Lahb;IIILjava/util/Random;)V";
		String targetMethodSign = "(Lnet/minecraft/world/World;IIILjava/util/Random;)V";
		String targetMethodNameObf = "func_149674_a";
		String targetMethodName = "updateTick";
		for (MethodNode method : classNode.methods) {
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				method.instructions.clear();
				InsnList newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 1));
				newIns.add(new VarInsnNode(ILOAD, 2));
				newIns.add(new VarInsnNode(ILOAD, 3));
				newIns.add(new VarInsnNode(ILOAD, 4));
				newIns.add(new VarInsnNode(ALOAD, 5));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Grass", "updateTick", "(Lnet/minecraft/world/World;IIILjava/util/Random;)V", false));
				newIns.add(new InsnNode(RETURN));
				method.instructions.insert(newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
		}

		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockPistonBase(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			int skip = 0;
			do {
				MethodInsnNode nodeFound = null;
				block2:
				for (boolean pistonObf : new boolean[]{false, true}) {
					for (boolean canPushObf : new boolean[]{false, true}) {
						for (boolean blockObf : new boolean[]{false, true}) {
							for (boolean worldObf : new boolean[]{false, true}) {
								String _piston = pistonObf ? cls_BlockPistonBase_obf : cls_BlockPistonBase;
								String _canPush = canPushObf ? "func_150080_a" : "canPushBlock";
								String _block = blockObf ? cls_Block_obf : cls_Block;
								String _world = worldObf ? cls_World_obf : cls_World;
								MethodInsnNode nodeInvokeCanPush = new MethodInsnNode(INVOKESTATIC, _piston, _canPush, "(L" + _block + ";L" + _world + ";IIIZ)Z", false);
								nodeFound = findNodeInMethod(method, nodeInvokeCanPush, skip);
								if (nodeFound != null) {
									break block2;
								}
							}
						}
					}
				}
				if (nodeFound == null) {
					break;
				}
				nodeFound.setOpcode(184);
				nodeFound.owner = "got/coremod/GOTReplacedMethods$Piston";
				nodeFound.name = "canPushBlock";
				nodeFound.desc = "(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;IIIZ)Z";
				nodeFound.itf = false;
				++skip;
			} while (true);
			if (skip > 0) {
				System.out.println("Hummel009: Patched method " + method.name + ' ' + skip + " times");
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockStaticLiquid(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodSignObf = "(Lahb;IIILjava/util/Random;)V";
		String targetMethodSign = "(Lnet/minecraft/world/World;IIILjava/util/Random;)V";
		String targetMethodNameObf = "func_149674_a";
		String targetMethodName = "updateTick";
		for (MethodNode method : classNode.methods) {
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				method.instructions.clear();
				InsnList newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 0));
				newIns.add(new VarInsnNode(ALOAD, 1));
				newIns.add(new VarInsnNode(ILOAD, 2));
				newIns.add(new VarInsnNode(ILOAD, 3));
				newIns.add(new VarInsnNode(ILOAD, 4));
				newIns.add(new VarInsnNode(ALOAD, 5));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$StaticLiquid", "updateTick", "(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;IIILjava/util/Random;)V", false));
				newIns.add(new InsnNode(RETURN));
				method.instructions.insert(newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockStone(String name, byte[] bytes) {
		boolean isObf = !name.startsWith("net.minecraft");
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodName = "getIcon";
		String targetMethodNameObf = "func_149673_e";
		String targetMethodDesc;
		String targetMethodDescObf = targetMethodDesc = "(Lnet/minecraft/world/IBlockAccess;IIII)Lnet/minecraft/util/IIcon;";
		MethodNode newMethod = isObf ? new MethodNode(ACONST_NULL, targetMethodNameObf, targetMethodDescObf, null, null) : new MethodNode(ACONST_NULL, targetMethodName, targetMethodDesc, null, null);
		newMethod.instructions.add(new VarInsnNode(ALOAD, 0));
		newMethod.instructions.add(new VarInsnNode(ALOAD, 1));
		newMethod.instructions.add(new VarInsnNode(ILOAD, 2));
		newMethod.instructions.add(new VarInsnNode(ILOAD, 3));
		newMethod.instructions.add(new VarInsnNode(ILOAD, 4));
		newMethod.instructions.add(new VarInsnNode(ILOAD, 5));
		newMethod.instructions.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Stone", "getIconWorld", "(Lnet/minecraft/block/Block;Lnet/minecraft/world/IBlockAccess;IIII)Lnet/minecraft/util/IIcon;", false));
		newMethod.instructions.add(new InsnNode(ARETURN));
		classNode.methods.add(newMethod);
		System.out.println("Hummel009: Added method " + newMethod.name);
		targetMethodName = "getIcon";
		targetMethodNameObf = "func_149691_a";
		targetMethodDescObf = targetMethodDesc = "(II)Lnet/minecraft/util/IIcon;";
		newMethod = isObf ? new MethodNode(ACONST_NULL, targetMethodNameObf, targetMethodDescObf, null, null) : new MethodNode(ACONST_NULL, targetMethodName, targetMethodDesc, null, null);
		newMethod.instructions.add(new VarInsnNode(ALOAD, 0));
		newMethod.instructions.add(new FieldInsnNode(GETFIELD, cls_Block, isObf ? "field_149761_L" : "blockIcon", "Lnet/minecraft/util/IIcon;"));
		newMethod.instructions.add(new VarInsnNode(ASTORE, 3));
		newMethod.instructions.add(new VarInsnNode(ALOAD, 0));
		newMethod.instructions.add(new VarInsnNode(ALOAD, 3));
		newMethod.instructions.add(new VarInsnNode(ILOAD, 1));
		newMethod.instructions.add(new VarInsnNode(ILOAD, 2));
		newMethod.instructions.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Stone", "getIconSideMeta", "(Lnet/minecraft/block/Block;Lnet/minecraft/util/IIcon;II)Lnet/minecraft/util/IIcon;", false));
		newMethod.instructions.add(new InsnNode(ARETURN));
		classNode.methods.add(newMethod);
		System.out.println("Hummel009: Added method " + newMethod.name);
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockTrapdoor(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodSign3 = "()I";
		String targetMethodNameObf3 = "func_149645_b";
		String targetMethodName3 = "getRenderType";
		String targetMethodSignObf2 = "(Laji;)Z";
		String targetMethodSign2 = "(Lnet/minecraft/block/Block;)Z";
		String targetMethodName2 = "func_150119_a";
		String targetMethodSignObf = "(Lahb;IIII)Z";
		String targetMethodSign = "(Lnet/minecraft/world/World;IIII)Z";
		String targetMethodNameObf = "func_149707_d";
		String targetMethodName = "canPlaceBlockOnSide";
		for (MethodNode method : classNode.methods) {
			InsnList newIns;
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				method.instructions.clear();
				newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 1));
				newIns.add(new VarInsnNode(ILOAD, 2));
				newIns.add(new VarInsnNode(ILOAD, 3));
				newIns.add(new VarInsnNode(ILOAD, 4));
				newIns.add(new VarInsnNode(ILOAD, 5));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Trapdoor", "canPlaceBlockOnSide", "(Lnet/minecraft/world/World;IIII)Z", false));
				newIns.add(new InsnNode(IRETURN));
				method.instructions.insert(newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
			if (method.name.equals(targetMethodName2) && (method.desc.equals(targetMethodSign2) || method.desc.equals(targetMethodSignObf2))) {
				method.instructions.clear();
				newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 0));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Trapdoor", "isValidSupportBlock", "(Lnet/minecraft/block/Block;)Z", false));
				newIns.add(new InsnNode(IRETURN));
				method.instructions.insert(newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName3) || method.name.equals(targetMethodNameObf3)) && method.desc.equals(targetMethodSign3)) {
				method.instructions.clear();
				newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 0));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Trapdoor", "getRenderType", "(Lnet/minecraft/block/Block;)I", false));
				newIns.add(new InsnNode(IRETURN));
				method.instructions.insert(newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockWall(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodSignObf = "(Lahl;III)Z";
		String targetMethodSign = "(Lnet/minecraft/world/IBlockAccess;III)Z";
		String targetMethodNameObf = "func_150091_e";
		String targetMethodName = "canConnectWallTo";
		for (MethodNode method : classNode.methods) {
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				method.instructions.clear();
				InsnList newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 1));
				newIns.add(new VarInsnNode(ILOAD, 2));
				newIns.add(new VarInsnNode(ILOAD, 3));
				newIns.add(new VarInsnNode(ILOAD, 4));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Wall", "canConnectWallTo", "(Lnet/minecraft/world/IBlockAccess;III)Z", false));
				newIns.add(new InsnNode(IRETURN));
				method.instructions.insert(newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchDoorInteract(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodSignObf = "(III)Lakn;";
		String targetMethodSign = "(III)Lnet/minecraft/block/BlockDoor;";
		String targetMethodName = "func_151503_a";
		for (MethodNode method : classNode.methods) {
			if (method.name.equals(targetMethodName) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				FieldInsnNode nodeFound = null;
				block1:
				for (boolean blocksObf : new boolean[]{false, true}) {
					for (boolean doorObf : new boolean[]{false, true}) {
						String _blocks = blocksObf ? cls_Blocks_obf : cls_Blocks;
						String _door = doorObf ? "field_150466_ao" : "wooden_door";
						FieldInsnNode nodeGetDoor = new FieldInsnNode(GETSTATIC, _blocks, _door, "Lnet/minecraft/block/Block;");
						nodeFound = findNodeInMethod(method, nodeGetDoor);
						if (nodeFound != null) {
							break block1;
						}
					}
				}
				MethodInsnNode nodeCheckDoor = new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$PathFinder", "isWoodenDoor", "(Lnet/minecraft/block/Block;)Z", false);
				method.instructions.set(nodeFound, nodeCheckDoor);
				JumpInsnNode nodeIf = (JumpInsnNode) nodeCheckDoor.getNext();
				if (nodeIf.getOpcode() != 165) {
					System.out.println("WARNING! WARNING! THIS OPCODE SHOULD HAVE BEEN IF_ACMPEQ!");
					System.out.println("WARNING! INSTEAD IT WAS " + nodeIf.getOpcode());
					System.out.println("WARNING! Setting it to IF_NE anyway");
				}
				nodeIf.setOpcode(154);
				System.out.println("Hummel009: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchEnchantmentHelper(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodSignObf8 = "(Lsv;)I";
		String targetMethodSign8 = "(Lnet/minecraft/entity/EntityLivingBase;)I";
		String targetMethodNameObf8 = "func_90036_a";
		String targetMethodName8 = "getFireAspectModifier";
		String targetMethodSignObf7 = "([Ladd;Lro;)I";
		String targetMethodSign7 = "([Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/DamageSource;)I";
		String targetMethodNameObf7 = "func_77508_a";
		String targetMethodName7 = "getEnchantmentModifierDamage";
		String targetMethodSignObf6 = "(Lsv;)I";
		String targetMethodSign6 = "(Lnet/minecraft/entity/EntityLivingBase;)I";
		String targetMethodNameObf6 = "func_77519_f";
		String targetMethodName6 = "getLootingotifier";
		String targetMethodSignObf5 = "(Lsv;)I";
		String targetMethodSign5 = "(Lnet/minecraft/entity/EntityLivingBase;)I";
		String targetMethodNameObf5 = "func_77517_e";
		String targetMethodName5 = "getFortuneModifier";
		String targetMethodSignObf4 = "(Lsv;Lsv;)I";
		String targetMethodSign4 = "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/entity/EntityLivingBase;)I";
		String targetMethodNameObf4 = "func_77507_b";
		String targetMethodName4 = "getKnockbackModifier";
		String targetMethodSignObf3 = "(Lsv;)Z";
		String targetMethodSign3 = "(Lnet/minecraft/entity/EntityLivingBase;)Z";
		String targetMethodNameObf3 = "func_77502_d";
		String targetMethodName3 = "getSilkTouchModifier";
		String targetMethodSignObf2 = "(Ladd;Lsz;)F";
		String targetMethodSign2 = "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EnumCreatureAttribute;)F";
		String targetMethodName2 = "func_152377_a";
		String targetMethodSignObf = "(Lsv;Lsv;)F";
		String targetMethodSign = "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/entity/EntityLivingBase;)F";
		String targetMethodNameObf = "func_77512_a";
		String targetMethodName = "getEnchantmentModifierLiving";
		for (MethodNode method : classNode.methods) {
			InsnNode nodeReturn;
			InsnList extraIns;
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				nodeReturn = findNodeInMethod(method, new InsnNode(FRETURN));
				extraIns = new InsnList();
				extraIns.add(new VarInsnNode(ALOAD, 0));
				extraIns.add(new VarInsnNode(ALOAD, 1));
				extraIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Enchants", "getEnchantmentModifierLiving", "(FLnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/entity/EntityLivingBase;)F", false));
				method.instructions.insertBefore(nodeReturn, extraIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
			if (method.name.equals(targetMethodName2) && (method.desc.equals(targetMethodSign2) || method.desc.equals(targetMethodSignObf2))) {
				nodeReturn = findNodeInMethod(method, new InsnNode(FRETURN));
				extraIns = new InsnList();
				extraIns.add(new VarInsnNode(ALOAD, 0));
				extraIns.add(new VarInsnNode(ALOAD, 1));
				extraIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Enchants", "func_152377_a", "(FLnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EnumCreatureAttribute;)F", false));
				method.instructions.insertBefore(nodeReturn, extraIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName3) || method.name.equals(targetMethodNameObf3)) && (method.desc.equals(targetMethodSign3) || method.desc.equals(targetMethodSignObf3))) {
				nodeReturn = findNodeInMethod(method, new InsnNode(IRETURN));
				extraIns = new InsnList();
				extraIns.add(new VarInsnNode(ALOAD, 0));
				extraIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Enchants", "getSilkTouchModifier", "(ZLnet/minecraft/entity/EntityLivingBase;)Z", false));
				method.instructions.insertBefore(nodeReturn, extraIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName4) || method.name.equals(targetMethodNameObf4)) && (method.desc.equals(targetMethodSign4) || method.desc.equals(targetMethodSignObf4))) {
				nodeReturn = findNodeInMethod(method, new InsnNode(IRETURN));
				extraIns = new InsnList();
				extraIns.add(new VarInsnNode(ALOAD, 0));
				extraIns.add(new VarInsnNode(ALOAD, 1));
				extraIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Enchants", "getKnockbackModifier", "(ILnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/entity/EntityLivingBase;)I", false));
				method.instructions.insertBefore(nodeReturn, extraIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName5) || method.name.equals(targetMethodNameObf5)) && (method.desc.equals(targetMethodSign5) || method.desc.equals(targetMethodSignObf5))) {
				nodeReturn = findNodeInMethod(method, new InsnNode(IRETURN));
				extraIns = new InsnList();
				extraIns.add(new VarInsnNode(ALOAD, 0));
				extraIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Enchants", "getFortuneModifier", "(ILnet/minecraft/entity/EntityLivingBase;)I", false));
				method.instructions.insertBefore(nodeReturn, extraIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName6) || method.name.equals(targetMethodNameObf6)) && (method.desc.equals(targetMethodSign6) || method.desc.equals(targetMethodSignObf6))) {
				nodeReturn = findNodeInMethod(method, new InsnNode(IRETURN));
				extraIns = new InsnList();
				extraIns.add(new VarInsnNode(ALOAD, 0));
				extraIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Enchants", "getLootingotifier", "(ILnet/minecraft/entity/EntityLivingBase;)I", false));
				method.instructions.insertBefore(nodeReturn, extraIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName7) || method.name.equals(targetMethodNameObf7)) && (method.desc.equals(targetMethodSign7) || method.desc.equals(targetMethodSignObf7))) {
				nodeReturn = findNodeInMethod(method, new InsnNode(IRETURN));
				extraIns = new InsnList();
				extraIns.add(new VarInsnNode(ALOAD, 0));
				extraIns.add(new VarInsnNode(ALOAD, 1));
				extraIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Enchants", "getSpecialArmorProtection", "(I[Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/DamageSource;)I", false));
				method.instructions.insertBefore(nodeReturn, extraIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName8) || method.name.equals(targetMethodNameObf8)) && (method.desc.equals(targetMethodSign8) || method.desc.equals(targetMethodSignObf8))) {
				nodeReturn = findNodeInMethod(method, new InsnNode(IRETURN));
				extraIns = new InsnList();
				extraIns.add(new VarInsnNode(ALOAD, 0));
				extraIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Enchants", "getFireAspectModifier", "(ILnet/minecraft/entity/EntityLivingBase;)I", false));
				method.instructions.insertBefore(nodeReturn, extraIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchEnchantmentProtection(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodSignObf = "(Lsa;I)I";
		String targetMethodSign = "(Lnet/minecraft/entity/Entity;I)I";
		String targetMethodNameObf = "func_92093_a";
		String targetMethodName = "getFireTimeForEntity";
		for (MethodNode method : classNode.methods) {
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				VarInsnNode nodeIStore = findNodeInMethod(method, new VarInsnNode(ISTORE, 2));
				InsnList newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 0));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Enchants", "getMaxFireProtectionLevel", "(ILnet/minecraft/entity/Entity;)I", false));
				method.instructions.insertBefore(nodeIStore, newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchEntityClientPlayerMP(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodSign = "()V";
		String targetMethodName = "func_110318_g";
		for (MethodNode method : classNode.methods) {
			if (method.name.equals(targetMethodName) && method.desc.equals(targetMethodSign)) {
				method.instructions.clear();
				InsnList newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 0));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$ClientPlayer", "horseJump", "(Lnet/minecraft/client/entity/EntityClientPlayerMP;)V", false));
				newIns.add(new InsnNode(RETURN));
				method.instructions.insert(newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchEntityHorse(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodSign = "(FF)V";
		String targetMethodNameObf = "func_70612_e";
		String targetMethodName = "moveEntityWithHeading";
		for (MethodNode method : classNode.methods) {
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && method.desc.equals(targetMethodSign)) {
				AbstractInsnNode nodeIsRemote = null;
				block1:
				for (boolean worldObf : new boolean[]{false, true}) {
					boolean[] arrbl = {false, true};
					for (boolean b : arrbl) {
						String _world = worldObf ? cls_World_obf : cls_World;
						nodeIsRemote = findNodeInMethod(method, new FieldInsnNode(GETFIELD, _world, b ? "field_72995_K" : "isRemote", "Z"));
						if (nodeIsRemote != null) {
							break block1;
						}
					}
				}
				VarInsnNode nodeLoadThisEntity = (VarInsnNode) nodeIsRemote.getPrevious().getPrevious();
				for (int l = 0; l < 2; ++l) {
					method.instructions.remove(nodeLoadThisEntity.getNext());
				}
				JumpInsnNode nodeIfTest = (JumpInsnNode) nodeLoadThisEntity.getNext();
				if (nodeIfTest.getOpcode() == 154) {
					nodeIfTest.setOpcode(153);
				} else {
					System.out.println("WARNING! Expected IFNE! Instead got " + nodeIfTest.getOpcode());
					System.out.println("WARNING! Things may break!");
				}
				InsnList newIns = new InsnList();
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$MountFunctions", "canRiderControl_elseNoMotion", "(Lnet/minecraft/entity/EntityLiving;)Z", false));
				method.instructions.insert(nodeLoadThisEntity, newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	@SuppressWarnings("all")
	public byte[] patchEntityLivingBase(String name, byte[] bytes) {
		String targetMethodName = "getTotalArmorValue";
		String targetMethodNameObf = "func_70658_aO";
		String targetMethodSign = "()I";
		String targetMethodName2 = "onDeath";
		String targetMethodNameObf2 = "func_70645_a";
		String targetMethodSign2 = "(Lnet/minecraft/util/DamageSource;)V";
		String targetMethodSignObf2 = "(Lro;)V";
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && method.desc.equals(targetMethodSign)) {
				VarInsnNode nodeStore = findNodeInMethod(method, new VarInsnNode(ISTORE, 6));
				for (int l = 0; l < 3; ++l) {
					method.instructions.remove(nodeStore.getPrevious());
				}
				AbstractInsnNode newPrev = nodeStore.getPrevious();
				if (!(newPrev instanceof VarInsnNode) || newPrev.getOpcode() != 25 || ((VarInsnNode) newPrev).var != 5) {
					System.out.println("WARNING! Expected ALOAD 5! Instead got " + newPrev);
					System.out.println("WARNING! Things may break!");
				}
				InsnList newIns22 = new InsnList();
				newIns22.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Enchants", "getDamageReduceAmount", "(Lnet/minecraft/item/ItemStack;)I", false));
				method.instructions.insertBefore(nodeStore, newIns22);
				System.out.println("Hummel009: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName2) || method.name.equals(targetMethodNameObf2)) && (method.desc.equals(targetMethodSign2) || method.desc.equals(targetMethodSignObf2))) {
				AbstractInsnNode nodeIsInstance = null;
				boolean[] newPrev = {false, true};
				int newIns22 = newPrev.length;
				for (int i = 0; i < newIns22 && (nodeIsInstance = findNodeInMethod(method, new TypeInsnNode(INSTANCEOF, newPrev[i] ? cls_EntityPlayer_obf : cls_EntityPlayer))) == null; ++i) {
				}
				VarInsnNode nodeLoadEntity = (VarInsnNode) nodeIsInstance.getPrevious();
				method.instructions.remove(nodeIsInstance);
				InsnList newIns221 = new InsnList();
				newIns221.add(new VarInsnNode(ALOAD, 1));
				newIns221.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Enchants", "isPlayerMeleeKill", "(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/DamageSource;)Z", false));
				method.instructions.insert(nodeLoadEntity, newIns221);
				System.out.println("Hummel009: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchEntityPlayer(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodSign = "(Z)Z";
		String targetMethodNameObf = "func_71043_e";
		String targetMethodName = "canEat";
		for (MethodNode method : classNode.methods) {
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && method.desc.equals(targetMethodSign)) {
				method.instructions.clear();
				InsnList newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 0));
				newIns.add(new VarInsnNode(ILOAD, 1));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Player", "canEat", "(Lnet/minecraft/entity/player/EntityPlayer;Z)Z", false));
				newIns.add(new InsnNode(IRETURN));
				method.instructions.insert(newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchFMLNetworkHandler(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodSignObf = "(Lsa;)Lft;";
		String targetMethodSign = "(Lnet/minecraft/entity/Entity;)Lnet/minecraft/network/Packet;";
		String targetMethodName = "getEntitySpawningPacket";
		for (MethodNode method : classNode.methods) {
			if (method.name.equals(targetMethodName) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				method.instructions.clear();
				InsnList newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 0));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$EntityPackets", "getMobSpawnPacket", "(Lnet/minecraft/entity/Entity;)Lnet/minecraft/network/Packet;", false));
				newIns.add(new InsnNode(ARETURN));
				method.instructions.insert(newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchFoodStats(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodSign = "(F)V";
		String targetMethodNameObf = "func_75113_a";
		String targetMethodName = "addExhaustion";
		for (MethodNode method : classNode.methods) {
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && method.desc.equals(targetMethodSign)) {
				InsnList newIns = new InsnList();
				newIns.add(new VarInsnNode(FLOAD, 1));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Food", "getExhaustionFactor", "()F", false));
				newIns.add(new InsnNode(FMUL));
				newIns.add(new VarInsnNode(FSTORE, 1));
				VarInsnNode nodeAfter = findNodeInMethod(method, new VarInsnNode(ALOAD, 0));
				method.instructions.insertBefore(nodeAfter, newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchGui(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);

		String targetMethodName = "drawCenteredStringWithoutShadow";
		String targetMethodDesc = "(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V";

		MethodNode newMethod = new MethodNode(ACC_PUBLIC, targetMethodName, targetMethodDesc, null, null);

		newMethod.instructions.add(new VarInsnNode(ALOAD, 1));
		newMethod.instructions.add(new VarInsnNode(ALOAD, 2));
		newMethod.instructions.add(new VarInsnNode(ILOAD, 3));
		newMethod.instructions.add(new VarInsnNode(ILOAD, 4));
		newMethod.instructions.add(new VarInsnNode(ILOAD, 5));
		newMethod.instructions.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Gui", "drawCenteredStringWithoutShadow", "(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V", false));
		newMethod.instructions.add(new InsnNode(RETURN));

		classNode.methods.add(newMethod);
		System.out.println("Hummel009: Added method " + newMethod.name);

		ClassWriter writer = new ClassWriter(0);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	private byte[] patchGuiButton(String name, byte[] bytes) {

		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);

		String targetMethodSign2Obf = "(Lbao;II)V";
		String targetMethodSign2 = "(Lnet/minecraft/client/Minecraft;II)V";
		String targetMethodName2Obf = "func_146112_a";
		String targetMethodName2 = "drawButton";
		for (MethodNode method : classNode.methods) {
			if ((method.name.equals(targetMethodName2) || method.name.equals(targetMethodName2Obf)) && (method.desc.equals(targetMethodSign2) || method.desc.equals(targetMethodSign2Obf))) {
				InsnList instructions = method.instructions;
				AbstractInsnNode currentNode = instructions.getFirst();
				while (currentNode != null) {
					if (currentNode instanceof LdcInsnNode) {
						LdcInsnNode ldcInsnNode = (LdcInsnNode) currentNode;
						if (ldcInsnNode.cst.equals(14737632) || ldcInsnNode.cst.equals(10526880) || ldcInsnNode.cst.equals(16777120)) {
							ldcInsnNode.cst = 0x5E1C15;
						}
					}
					if (currentNode.getOpcode() == INVOKEVIRTUAL) {
						MethodInsnNode methodInsnNode = (MethodInsnNode) currentNode;
						if ("(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V".equals(methodInsnNode.desc)) {
							methodInsnNode.name = "drawCenteredStringWithoutShadow";
						}
					}
					currentNode = currentNode.getNext();
				}
				System.out.println("Hummel009: Patched method " + method.name);
				break;
			}
		}

		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchItemStack(String name, byte[] bytes) {
		String targetMethodSign;
		boolean isCauldron = GOTModChecker.isCauldronServer();
		String targetMethodName = "attemptDamageItem";
		String targetMethodNameObf = "func_96631_a";
		String targetMethodSignObf = targetMethodSign = "(ILjava/util/Random;)Z";
		if (isCauldron) {
			targetMethodNameObf = "isDamaged";
			targetMethodName = "isDamaged";
			targetMethodSign = "(ILjava/util/Random;Lnet/minecraft/entity/EntityLivingBase;)Z";
			targetMethodSignObf = "(ILjava/util/Random;Lsv;)Z";
		}
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for (MethodNode method : classNode.methods) {
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				if (isCauldron) {
					for (AbstractInsnNode n : method.instructions.toArray()) {
						if (n.getOpcode() == 100) {
							InsnList insns = new InsnList();
							insns.add(new VarInsnNode(ALOAD, 0));
							insns.add(new VarInsnNode(ILOAD, 1));
							insns.add(new VarInsnNode(ALOAD, 2));
							insns.add(new VarInsnNode(ALOAD, 3));
							insns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Enchants", "c_attemptDamageItem", "(ILnet/minecraft/item/ItemStack;ILjava/util/Random;Lnet/minecraft/entity/EntityLivingBase;)I", false));
							method.instructions.insert(n, insns);
							System.out.println("Hummel009: Patched method " + method.name + " for Cauldron");
						}
					}
				} else {
					method.instructions.clear();
					InsnList newIns = new InsnList();
					newIns.add(new VarInsnNode(ALOAD, 0));
					newIns.add(new VarInsnNode(ILOAD, 1));
					newIns.add(new VarInsnNode(ALOAD, 2));
					newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Enchants", "attemptDamageItem", "(Lnet/minecraft/item/ItemStack;ILjava/util/Random;)Z", false));
					newIns.add(new InsnNode(IRETURN));
					method.instructions.insert(newIns);
					System.out.println("Hummel009: Patched method " + method.name);
				}
			}

		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchNetHandlerClient(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodSignObf2 = "(Lhf;)V";
		String targetMethodSign2 = "(Lnet/minecraft/network/play/server/S14PacketEntity;)V";
		String targetMethodNameObf2 = "func_147259_a";
		String targetMethodName2 = "handleEntityMovement";
		String targetMethodSignObf = "(Lik;)V";
		String targetMethodSign = "(Lnet/minecraft/network/play/server/S18PacketEntityTeleport;)V";
		String targetMethodNameObf = "func_147275_a";
		String targetMethodName = "handleEntityTeleport";
		for (MethodNode method : classNode.methods) {
			InsnList newIns;
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				method.instructions.clear();
				newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 0));
				newIns.add(new VarInsnNode(ALOAD, 1));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$NetHandlerClient", "handleEntityTeleport", "(Lnet/minecraft/client/network/NetHandlerPlayClient;Lnet/minecraft/network/play/server/S18PacketEntityTeleport;)V", false));
				newIns.add(new InsnNode(RETURN));
				method.instructions.insert(newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
			if ((method.name.equals(targetMethodName2) || method.name.equals(targetMethodNameObf2)) && (method.desc.equals(targetMethodSign2) || method.desc.equals(targetMethodSignObf2))) {
				method.instructions.clear();
				newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 0));
				newIns.add(new VarInsnNode(ALOAD, 1));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$NetHandlerClient", "handleEntityMovement", "(Lnet/minecraft/client/network/NetHandlerPlayClient;Lnet/minecraft/network/play/server/S14PacketEntity;)V", false));
				newIns.add(new InsnNode(RETURN));
				method.instructions.insert(newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	@SuppressWarnings("ConditionalCanBePushedInsideExpression")
	public byte[] patchPathFinder(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodSignObf = "(Lsa;IIILaye;ZZZ)I";
		String targetMethodSign = "(Lnet/minecraft/entity/Entity;IIILnet/minecraft/pathfinding/PathPoint;ZZZ)I";
		String targetMethodName = "func_82565_a";
		for (MethodNode method : classNode.methods) {
			if (method.name.equals(targetMethodName) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				FieldInsnNode nodeFound1 = null;
				FieldInsnNode nodeFound2 = null;
				block1:
				for (int pass = 0; pass <= 1; ++pass) {
					for (boolean blocksObf : new boolean[]{false, true}) {
						for (boolean doorObf : new boolean[]{false, true}) {
							String _blocks = blocksObf ? "ajn" : cls_Blocks;
							String _door = doorObf ? "field_150466_ao" : "wooden_door";
							FieldInsnNode nodeGetDoor = new FieldInsnNode(GETSTATIC, _blocks, _door, "Lnet/minecraft/block/Block;");
							if (pass == 0 ? (nodeFound1 = findNodeInMethod(method, nodeGetDoor, 0)) != null : (nodeFound2 = findNodeInMethod(method, nodeGetDoor, 1)) != null) {
								continue block1;
							}
						}
					}
				}
				MethodInsnNode nodeCheckDoor1 = new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$PathFinder", "isWoodenDoor", "(Lnet/minecraft/block/Block;)Z", false);
				method.instructions.set(nodeFound1, nodeCheckDoor1);
				JumpInsnNode nodeIf1 = (JumpInsnNode) nodeCheckDoor1.getNext();
				nodeIf1.setOpcode(153);
				MethodInsnNode nodeCheckDoor2 = new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$PathFinder", "isWoodenDoor", "(Lnet/minecraft/block/Block;)Z", false);
				method.instructions.set(nodeFound2, nodeCheckDoor2);
				JumpInsnNode nodeIf2 = (JumpInsnNode) nodeCheckDoor2.getNext();
				if (nodeIf2.getOpcode() == 165) {
					nodeIf2.setOpcode(154);
				} else {
					System.out.println("WARNING! WARNING! THIS OPCODE SHOULD HAVE BEEN IF_ACMPEQ!");
					System.out.println("WARNING! INSTEAD IT WAS " + nodeIf2.getOpcode());
					if (nodeIf2.getOpcode() == 166) {
						System.out.println("WARNING! Opcode is IF_ACMPNE instead of expected IF_ACMPEQ, so setting it to IFEQ instead of IFNE");
						System.out.println("WARNING! Hopefully this works...");
						nodeIf2.setOpcode(153);
					} else {
						System.out.println("WARNING! NOT SURE WHAT TO DO HERE! THINGS MIGHT BREAK!");
					}
				}
				FieldInsnNode nodeFoundGate = null;
				block5:
				for (boolean blocksObf : new boolean[]{false, true}) {
					for (boolean gateObf : new boolean[]{false, true}) {
						String _blocks2 = blocksObf ? cls_Blocks_obf : cls_Blocks;
						String _gate = gateObf ? "field_150396_be" : "fence_gate";
						FieldInsnNode nodeGetGate = new FieldInsnNode(GETSTATIC, _blocks2, _gate, "Lnet/minecraft/block/Block;");
						nodeFoundGate = findNodeInMethod(method, nodeGetGate, 0);
						if (nodeFoundGate != null) {
							break block5;
						}
					}
				}
				MethodInsnNode nodeCheckGate = new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$PathFinder", "isFenceGate", "(Lnet/minecraft/block/Block;)Z", false);
				method.instructions.set(nodeFoundGate, nodeCheckGate);
				JumpInsnNode nodeIfGate = (JumpInsnNode) nodeCheckGate.getNext();
				if (nodeIfGate.getOpcode() == 165) {
					nodeIfGate.setOpcode(154);
				} else {
					System.out.println("WARNING! WARNING! THIS OPCODE SHOULD HAVE BEEN IF_ACMPEQ!");
					System.out.println("WARNING! INSTEAD IT WAS " + nodeIfGate.getOpcode());
					System.out.println("WARNING! NOT SURE WHAT TO DO HERE! THINGS MIGHT BREAK!");
				}
				System.out.println("Hummel009: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchPotionDamage(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodSignObf = "(ILtj;)D";
		String targetMethodSign = "(ILnet/minecraft/entity/ai/attributes/AttributeModifier;)D";
		String targetMethodName = "func_111183_a";
		for (MethodNode method : classNode.methods) {
			if (method.name.equals(targetMethodName) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				method.instructions.clear();
				InsnList newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 0));
				newIns.add(new VarInsnNode(ILOAD, 1));
				newIns.add(new VarInsnNode(ALOAD, 2));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Potions", "getStrengthModifier", "(Lnet/minecraft/potion/Potion;ILnet/minecraft/entity/ai/attributes/AttributeModifier;)D", false));
				newIns.add(new InsnNode(DRETURN));
				method.instructions.insert(newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchSpawnerAnimals(String name, byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		String targetMethodSignObf = "(Lmt;ZZZ)I";
		String targetMethodSign = "(Lnet/minecraft/world/WorldServer;ZZZ)I";
		String targetMethodNameObf = "func_77192_a";
		String targetMethodName = "findChunksForSpawning";
		for (MethodNode method : classNode.methods) {
			if ((method.name.equals(targetMethodName) || method.name.equals(targetMethodNameObf)) && (method.desc.equals(targetMethodSign) || method.desc.equals(targetMethodSignObf))) {
				method.instructions.clear();
				method.tryCatchBlocks.clear();
				method.localVariables.clear();
				InsnList newIns = new InsnList();
				newIns.add(new VarInsnNode(ALOAD, 1));
				newIns.add(new VarInsnNode(ILOAD, 2));
				newIns.add(new VarInsnNode(ILOAD, 3));
				newIns.add(new VarInsnNode(ILOAD, 4));
				newIns.add(new MethodInsnNode(INVOKESTATIC, "got/coremod/GOTReplacedMethods$Spawner", "performSpawning_optimised", "(Lnet/minecraft/world/WorldServer;ZZZ)I", false));
				newIns.add(new InsnNode(IRETURN));
				method.instructions.insert(newIns);
				System.out.println("Hummel009: Patched method " + method.name);
			}
		}
		ClassWriter writer = new ClassWriter(1);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if ("anv".equals(name) || "net.minecraft.block.BlockStone".equals(name)) {
			return patchBlockStone(name, basicClass);
		}
		if ("alh".equals(name) || "net.minecraft.block.BlockGrass".equals(name)) {
			return patchBlockGrass(name, basicClass);
		}
		if ("akl".equals(name) || "net.minecraft.block.BlockDirt".equals(name)) {
			return patchBlockDirt(name, basicClass);
		}
		if ("ant".equals(name) || "net.minecraft.block.BlockStaticLiquid".equals(name)) {
			return patchBlockStaticLiquid(name, basicClass);
		}
		if ("akz".equals(name) || "net.minecraft.block.BlockFence".equals(name)) {
			return patchBlockFence(name, basicClass);
		}
		if ("aoe".equals(name) || "net.minecraft.block.BlockTrapDoor".equals(name)) {
			return patchBlockTrapdoor(name, basicClass);
		}
		if ("aoi".equals(name) || "net.minecraft.block.BlockWall".equals(name)) {
			return patchBlockWall(name, basicClass);
		}
		if (name.equals(cls_BlockPistonBase_obf) || "net.minecraft.block.BlockPistonBase".equals(name)) {
			return patchBlockPistonBase(name, basicClass);
		}
		if ("ajw".equals(name) || "net.minecraft.block.BlockCauldron".equals(name)) {
			return patchBlockCauldron(name, basicClass);
		}
		if ("ajb".equals(name) || "net.minecraft.block.BlockAnvil".equals(name)) {
			return patchBlockAnvil(name, basicClass);
		}
		if (name.equals(cls_EntityPlayer_obf) || "net.minecraft.entity.player.EntityPlayer".equals(name)) {
			return patchEntityPlayer(name, basicClass);
		}
		if (name.equals(cls_EntityLivingBase_obf) || "net.minecraft.entity.EntityLivingBase".equals(name)) {
			return patchEntityLivingBase(name, basicClass);
		}
		if ("wi".equals(name) || "net.minecraft.entity.passive.EntityHorse".equals(name)) {
			return patchEntityHorse(name, basicClass);
		}
		if ("net.minecraftforge.common.ISpecialArmor$ArmorProperties".equals(name)) {
			return patchArmorProperties(name, basicClass);
		}
		if ("zr".equals(name) || "net.minecraft.util.FoodStats".equals(name)) {
			return patchFoodStats(name, basicClass);
		}
		if ("aho".equals(name) || "net.minecraft.world.SpawnerAnimals".equals(name)) {
			return patchSpawnerAnimals(name, basicClass);
		}
		if ("ayg".equals(name) || "net.minecraft.pathfinding.PathFinder".equals(name)) {
			return patchPathFinder(name, basicClass);
		}
		if ("uc".equals(name) || "net.minecraft.entity.ai.EntityAIDoorInteract".equals(name)) {
			return patchDoorInteract(name, basicClass);
		}
		if ("afv".equals(name) || "net.minecraft.enchantment.EnchantmentHelper".equals(name)) {
			return patchEnchantmentHelper(name, basicClass);
		}
		if (name.equals(cls_ItemStack_obf) || "net.minecraft.item.ItemStack".equals(name)) {
			return patchItemStack(name, basicClass);
		}
		if ("agi".equals(name) || "net.minecraft.enchantment.EnchantmentProtection".equals(name)) {
			return patchEnchantmentProtection(name, basicClass);
		}
		if ("rs".equals(name) || "net.minecraft.potion.PotionAttackDamage".equals(name)) {
			return patchPotionDamage(name, basicClass);
		}
		if ("bjk".equals(name) || "net.minecraft.client.entity.EntityClientPlayerMP".equals(name)) {
			return patchEntityClientPlayerMP(name, basicClass);
		}
		if ("bjb".equals(name) || "net.minecraft.client.network.NetHandlerPlayClient".equals(name)) {
			return patchNetHandlerClient(name, basicClass);
		}
		if ("bcb".equals(name) || "net.minecraft.client.gui.GuiButton".equals(name)) {
			return patchGuiButton(name, basicClass);
		}
		if ("bbw".equals(name) || "net.minecraft.client.gui.Gui".equals(name)) {
			return patchGui(name, basicClass);
		}
		if ("cpw.mods.fml.common.network.internal.FMLNetworkHandler".equals(name)) {
			return patchFMLNetworkHandler(name, basicClass);
		}
		return basicClass;
	}
}