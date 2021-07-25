package me.a0g.hyk.tweaker.asm;

import me.a0g.hyk.tweaker.asm.utils.InjectionHelper;
import me.a0g.hyk.tweaker.asm.utils.InstructionBuilder;
import me.a0g.hyk.tweaker.asm.utils.TransformerClass;
import me.a0g.hyk.tweaker.asm.utils.TransformerMethod;
import me.a0g.hyk.tweaker.transformer.ITransformer;
import net.minecraftforge.fml.common.FMLLog;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class BlockTransformer implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.block.Block"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode  : classNode.methods) {
            // map the method to searge
           // String methodName = mapMethodName(classNode, method);

           /* if (methodName.equalsIgnoreCase("shouldSideBeRendered")
                    || methodName.equalsIgnoreCase("func_176225_a")) {*/

                if (InjectionHelper.matches(methodNode, TransformerMethod.shouldSideBeRendered)) {

                /*InjectionHelper.start()
                        .matchMethodHead()

                        .startCode()

                            .callStaticMethod("me/a0g/hyk/tweaker/asm/hooks/BlockHook","shouldBe","(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z")
                            .startIfEqual()
                                .reeturn()
                            .endIf()
                        .endCode()

                        .finish();
                return;*/
                    methodNode.instructions.insertBefore(methodNode.instructions.getFirst(),sayBruh());


               // FMLLog.info(method.instructions.iterator().toString()+"");
            }
        }
    }

    private InsnList sayBruh() {
        // create a new instruction list
        InsnList list = new InsnList();


        /*list.add(new VarInsnNode(Opcodes.ALOAD, 0));//
          list.add(new VarInsnNode(Opcodes.ALOAD, 1));//
          list.add(new VarInsnNode(Opcodes.ALOAD, 2));//
          list.add(new VarInsnNode(Opcodes.ALOAD, 3));//
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, //
                "me/a0g/hyk/tweaker/asm/hooks/BlockHook",
                "shouldBe2",
                "(Lnet/minecraft/block/Block;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z",  // void
                false));

        LabelNode notCancelled2 = new LabelNode();
        list.add(new JumpInsnNode(Opcodes.IFEQ, notCancelled2)); // if( shouldBe(Block block,IBlockAccess worldIn, BlockPos pos, EnumFacing side) ){

        list.add(new InsnNode(Opcodes.ICONST_1)); // return false;
        list.add(new InsnNode(Opcodes.IRETURN)); // return;
        list.add(notCancelled2);*/



        list.add(new VarInsnNode(Opcodes.ALOAD, 0));//
        list.add(new VarInsnNode(Opcodes.ALOAD, 1));//
        list.add(new VarInsnNode(Opcodes.ALOAD, 2));//
        list.add(new VarInsnNode(Opcodes.ALOAD, 3));//
        /*list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, //
                "me/a0g/hyk/tweaker/asm/hooks/BlockHook",
                "shouldBe",
                "(Lnet/minecraft/block/Block;)Z",  // void
                false));*/
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, //
                "me/a0g/hyk/tweaker/asm/hooks/BlockHook",
                "shouldBe",
                "(Lnet/minecraft/block/Block;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z",  // void
                false));

        LabelNode notCancelled = new LabelNode();
        list.add(new JumpInsnNode(Opcodes.IFEQ, notCancelled)); // if( shouldBe(Block block,IBlockAccess worldIn, BlockPos pos, EnumFacing side) ){

        list.add(new InsnNode(Opcodes.ICONST_0)); // return false;
        list.add(new InsnNode(Opcodes.IRETURN)); // return;


       // list.add(new JumpInsnNode(Opcodes.IFNE, notCancelled));
        list.add(new VarInsnNode(Opcodes.ALOAD, 0));//
        list.add(new VarInsnNode(Opcodes.ALOAD, 1));//
        list.add(new VarInsnNode(Opcodes.ALOAD, 2));//
        list.add(new VarInsnNode(Opcodes.ALOAD, 3));//
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, //
                "me/a0g/hyk/tweaker/asm/hooks/BlockHook",
                "shouldBe2",
                "(Lnet/minecraft/block/Block;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z",  // void
                false));
        list.add(new InsnNode(Opcodes.IRETURN)); // return;


        list.add(notCancelled);


        return list;
    }
}
