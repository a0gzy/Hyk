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
            String methodName = mapMethodName(classNode, methodNode);

            if (methodName.equalsIgnoreCase("shouldSideBeRendered")
                    || methodName.equalsIgnoreCase("func_176225_a")) {

                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(),sayBruh());
            }
            if (methodName.equalsIgnoreCase("getMixedBrightnessForBlock")
                    || methodName.equalsIgnoreCase("func_176207_c")) {
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), sayBruh2());
            }

               /* if (InjectionHelper.matches(methodNode, TransformerMethod.shouldSideBeRendered)) {

                    *//*InjectionHelper.start()
                            .matchMethodHead()

                            .startCode()

                            .newInstance("me/a0g/hyk/tweaker/asm/utils/ReturnValue")
                            .storeAuto(4)


                            .load(InstructionBuilder.VariableType.OBJECT, 0)
                            .loadAuto(4)
                            .callStaticMethod("me/a0g/hyk/tweaker/asm/hooks/BlockHook", "shouldBe2",
                                    "(Lnet/minecraft/block/Block;Lme/a0g/hyk/tweaker/asm/utils/ReturnValue;)V")

                            .loadAuto(4)
                            .invokeInstanceMethod("me/a0g/hyk/tweaker/asm/utils/ReturnValue", "isCancelled", "()Z")
                            .startIfEqual()
                            .constantBooleanValue() // немног не то так ток опред значения чтоб возвращать
                            .reeturn()
                            .endIf()

                            .endCode()

                            .finish();
                    return;*//*


                } else if (InjectionHelper.matches(methodNode, TransformerMethod.getMixedBrightnessForBlock)) {
                    methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), sayBruh2());
                }*/
        }
    }

    private InsnList sayBruh() {
        // create a new instruction list
        InsnList list = new InsnList();

        list.add(new TypeInsnNode(Opcodes.NEW, "me/a0g/hyk/tweaker/asm/utils/ReturnValue"));
        list.add(new InsnNode(Opcodes.DUP)); // ReturnValue returnValue = new ReturnValue();
        list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "me/a0g/hyk/tweaker/asm/utils/ReturnValue", "<init>", "()V", false));
        list.add(new VarInsnNode(Opcodes.ASTORE, 4)); //

        list.add(new VarInsnNode(Opcodes.ALOAD, 0)); // Block
        list.add(new VarInsnNode(Opcodes.ALOAD, 4)); // BlockHook.shouldBe2(block, returnValue);
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "me/a0g/hyk/tweaker/asm/hooks/BlockHook", "shouldBe2",
                "(Lnet/minecraft/block/Block;Lme/a0g/hyk/tweaker/asm/utils/ReturnValue;)V", false));

        list.add(new VarInsnNode(Opcodes.ALOAD, 4));
        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "me/a0g/hyk/tweaker/asm/utils/ReturnValue", "isCancelled",
                "()Z", false));
        LabelNode notCancelled = new LabelNode(); // if (returnValue.isCancelled())
        list.add(new JumpInsnNode(Opcodes.IFEQ, notCancelled));

        list.add(new VarInsnNode(Opcodes.ALOAD, 4));
        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "me/a0g/hyk/tweaker/asm/utils/ReturnValue", "getReturnValue",
                "()Ljava/lang/Object;", false));
        list.add(new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Boolean"));
        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue",
                "()Z", false));
        list.add(new InsnNode(Opcodes.IRETURN)); // return returnValue.getValue();
        list.add(notCancelled);


        return list;
    }

    private InsnList sayBruh2() {
        // create a new instruction list
        InsnList list = new InsnList();

        list.add(new TypeInsnNode(Opcodes.NEW, "me/a0g/hyk/tweaker/asm/utils/ReturnValue"));
        list.add(new InsnNode(Opcodes.DUP)); // ReturnValue returnValue = new ReturnValue();
        list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "me/a0g/hyk/tweaker/asm/utils/ReturnValue", "<init>", "()V", false));
        list.add(new VarInsnNode(Opcodes.ASTORE, 4)); //

        list.add(new VarInsnNode(Opcodes.ALOAD, 4)); // BlockHook.shouldBe2(block, returnValue);
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "me/a0g/hyk/tweaker/asm/hooks/BlockHook", "getBright",
                "(Lme/a0g/hyk/tweaker/asm/utils/ReturnValue;)V", false));

        list.add(new VarInsnNode(Opcodes.ALOAD, 4));
        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "me/a0g/hyk/tweaker/asm/utils/ReturnValue", "isCancelled",
                "()Z", false));
        LabelNode notCancelled = new LabelNode(); // if (returnValue.isCancelled())
        list.add(new JumpInsnNode(Opcodes.IFEQ, notCancelled));

        list.add(new VarInsnNode(Opcodes.ALOAD, 4));
        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "me/a0g/hyk/tweaker/asm/utils/ReturnValue", "getReturnValue",
                "()Ljava/lang/Object;", false));
      //  Integer
        list.add(new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Integer"));
        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue",
                "()I", false));
        list.add(new InsnNode(Opcodes.IRETURN)); // return returnValue.getValue();
        list.add(notCancelled);


        return list;
    }

}
