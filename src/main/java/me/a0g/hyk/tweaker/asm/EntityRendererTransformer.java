package me.a0g.hyk.tweaker.asm;

import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.tweaker.transformer.ITransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class EntityRendererTransformer implements ITransformer {

    /**
     * {@link net.minecraft.client.renderer.EntityRenderer}
     */

    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.client.renderer.EntityRenderer"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode method : classNode.methods) {
            // map the method to searge
            String methodName = mapMethodName(classNode, method);

            if (methodName.equalsIgnoreCase("setupFog")
                    || methodName.equalsIgnoreCase("func_78468_a")) {

                method.instructions.insertBefore(method.instructions.getFirst(), sayBruh());

            }
        }
    }

    private InsnList sayBruh() {
        // create a new instruction list
        InsnList list = new InsnList();

        list.add(new VarInsnNode(Opcodes.ILOAD, 1));//int startCoords
        list.add(new VarInsnNode(Opcodes.FLOAD, 2));//float partialTicks

        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, // FontRendererHook.testchroma(text,shadow);
                "me/a0g/hyk/tweaker/asm/hooks/EntityRendererHook",
                "setupFog",
                "(IF)V",  // void
                false));

        list.add(new InsnNode(Opcodes.RETURN)); // return;

        return list;
    }
}
