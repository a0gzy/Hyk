package me.a0g.hyk.tweaker.asm;

import me.a0g.hyk.tweaker.transformer.ITransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class FontRendererTransformer implements ITransformer {

    /**
     * {@link net.minecraft.client.gui.FontRenderer}
     */

    @Override
    public String[] getClassName() {
        // assign the full class name
        //return new String[]{TransformerClass.FontRenderer.getTransformerName(), "club.sk1er.patcher.hooks.FontRendererHook"};
        return new String[]{"net.minecraft.client.gui.FontRenderer"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        // loop through all methods
        for (MethodNode method : classNode.methods) {
            // map the method to searge
            String methodName = mapMethodName(classNode, method);

           /* if (methodName.equalsIgnoreCase("renderStringAtPos")
                    || methodName.equalsIgnoreCase("func_78255_a")) {

                method.instructions.insertBefore(method.instructions.getFirst(), sayBruh3());
            }*/


                     if (methodName.equalsIgnoreCase("renderString")
                            || methodName.equalsIgnoreCase("func_180455_b")) {
                              // insert an instruction list before final return

                method.instructions.insertBefore(method.instructions.getFirst(), sayBruh());
                          // stop looping through methods as we've found the one we need

                break;
            }

            /*if (methodName.equalsIgnoreCase("renderChar")
                    || methodName.equalsIgnoreCase("func_181559_a")) {
                method.instructions.insertBefore(method.instructions.getFirst(),
                     new MethodInsnNode(Opcodes.INVOKESTATIC, "me/a0g/hyk/tweaker/asm/hooks/FontRendererHook", "changeColor", "()V", false));
              //  break;
            }*/

        }
    }

    //nickColors
    private InsnList sayBruh() {
            // create a new instruction list
        InsnList list = new InsnList();

        list.add(new VarInsnNode(Opcodes.ALOAD, 1));// text

        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, // FontRendererHook.changeTextColor(text);
                "me/a0g/hyk/tweaker/asm/hooks/FontRendererHook",
                "changeTextColor",
                "(Ljava/lang/String;)Ljava/lang/String;",  // return text
                false));

        // return the new set of instructions
        list.add(new VarInsnNode(Opcodes.ASTORE,1)); //store text to renderString

        return list;
    }

    //newsymbol
    private InsnList sayBruh3() {
        // create a new instruction list
        InsnList list = new InsnList();

        list.add(new VarInsnNode(Opcodes.ALOAD, 1));//String text
        list.add(new VarInsnNode(Opcodes.ILOAD, 2));//Boolean shadow

        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, // FontRendererHook.testchroma(text,shadow);
                "me/a0g/hyk/tweaker/asm/hooks/FontRendererHook",
                "testchroma",
                "(Ljava/lang/String;Z)V",  // void
                false));

        list.add(new InsnNode(Opcodes.RETURN)); // return;

        return list;
    }

}
