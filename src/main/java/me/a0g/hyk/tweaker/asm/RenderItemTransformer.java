package me.a0g.hyk.tweaker.asm;

import me.a0g.hyk.tweaker.transformer.ITransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class RenderItemTransformer implements ITransformer {

    /**
     * {@link net.minecraft.client.renderer.entity.RenderItem}
     */

    @Override
    public String[] getClassName() {
        // assign the full class name
        //return new String[]{TransformerClass.FontRenderer.getTransformerName(), "club.sk1er.patcher.hooks.FontRendererHook"};
        return new String[]{"net.minecraft.client.renderer.entity.RenderItem"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        // loop through all methods
        for (MethodNode method : classNode.methods) {
            // map the method to searge
            String methodName = mapMethodName(classNode, method);

            if (methodName.equalsIgnoreCase("renderItemIntoGUI")
                            || methodName.equalsIgnoreCase("func_175042_a")) {
                              // insert an instruction list before final return

                method.instructions.insertBefore(method.instructions.getFirst(), sayBruh());

                method.instructions.insertBefore(method.instructions.getLast().getPrevious(), sayBruh2());
                          // stop looping through methods as we've found the one we need

                //break;
            }


        }
    }

    //nickColors
    private InsnList sayBruh() {
            // create a new instruction list
        InsnList list = new InsnList();

        list.add(new VarInsnNode(Opcodes.ALOAD, 1));// text
        list.add(new VarInsnNode(Opcodes.ILOAD, 2));// text
        list.add(new VarInsnNode(Opcodes.ILOAD, 3));// text

        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, // FontRendererHook.changeTextColor(text);
                "me/a0g/hyk/tweaker/asm/hooks/RenderItemHook",
                "renderRarity",
                "(Lnet/minecraft/item/ItemStack;II)V",  // return text
                false));


        return list;
    }

    private InsnList sayBruh2() {
        // create a new instruction list
        InsnList list = new InsnList();

        list.add(new VarInsnNode(Opcodes.ALOAD, 1));// text
        list.add(new VarInsnNode(Opcodes.ILOAD, 2));// text
        list.add(new VarInsnNode(Opcodes.ILOAD, 3));// text

        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, // FontRendererHook.changeTextColor(text);
                "me/a0g/hyk/tweaker/asm/hooks/RenderItemHook",
                "renderE",
                "(Lnet/minecraft/item/ItemStack;II)V",  // return text
                false));


        return list;
    }

}
