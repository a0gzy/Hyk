package me.a0g.hyk.tweaker.asm;

import me.a0g.hyk.tweaker.transformer.ITransformer;
import net.minecraftforge.fml.common.FMLLog;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class MinecraftTransformer implements ITransformer {


    /**
     * {@link net.minecraft.client.Minecraft}
     */

    @Override
    public String[] getClassName() {
        // assign the full class name
        return new String[]{"net.minecraft.client.Minecraft"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        // loop through all methods
        for (MethodNode method : classNode.methods) {
            // map the method to searge
            String methodName = mapMethodName(classNode, method);

            // check qualifying names (mcp, srg)
            if (methodName.equals("clickMouse") || methodName.equals("func_147116_af")) {
                // insert an instruction list before final return
                method.instructions.insertBefore(method.instructions.getFirst(), sayBruh2());
                //method.instructions.insertBefore(method.instructions.getFirst(), sayBruh());
                // stop looping through methods as we've found the one we need
                //break;
            }
            if(methodName.equals("shutdown") || methodName.equals("func_71400_g")) {//shutdown
                method.instructions.insertBefore(method.instructions.getFirst(), rpcOff());
            }
            /*if(method.name.equals("run") && method.desc.equals("()V")){
                    FMLLog.info("MINE20");
                    method.instructions.insertBefore(method.instructions.getFirst(),minenew());*/
                /*AbstractInsnNode mcversionNode = null;
                for (AbstractInsnNode insnNode : method.instructions.toArray()) {
                    if (insnNode.getOpcode() == Opcodes.PUTFIELD
                            && ((FieldInsnNode) insnNode).desc == "Ljava/lang/String;"
                            && ((FieldInsnNode) insnNode).name == "launchedVersion") {
                        mcversionNode = insnNode;
                        FMLLog.info("MINE2020");
                    }
                }
                if (mcversionNode != null) {
                    //  method.instructions.remove(mcversionNode.getPrevious());
                    // method.instructions.remove(mcversionNode);
                    method.instructions.insertBefore(mcversionNode.getPrevious(), new LdcInsnNode("2.0"));

                }*/
                //mv.visitFieldInsn(PUTFIELD, "net/minecraft/client/Minecraft", "launchedVersion", "Ljava/lang/String;");

            /*}*/
           /* if (methodName.equals("startGame") || methodName.equals("func_71384_a")) {
                // insert an instruction list before final return
                method.instructions.insertBefore(method.instructions.getLast().getPrevious(), sayBruh());
                // stop looping through methods as we've found the one we need
               // break;
            }*/

        }

    }

    // creates "System.out.println("bruh");"
    private InsnList sayBruh() {
        // create a new instruction list
        InsnList list = new InsnList();
        // get the field "out" from the class "System"
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
        // add "bruh" to the println parameters
        list.add(new LdcInsnNode("lo1d gey"));
        // invoke System.out.println(String)
        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));
        // return the new set of instructions
        return list;
    }

    private InsnList sayBruh2() {
        // create a new instruction list
        InsnList list = new InsnList();

        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, //
                "me/a0g/hyk/tweaker/asm/hooks/MinecraftHook",
                "hitfix",
                "()V",
                false));

        return list;
    }

    private InsnList rpcOff() {
        // create a new instruction list
        InsnList list = new InsnList();

        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, //
                "me/a0g/hyk/tweaker/asm/hooks/MinecraftHook",
                "disableRpc",
                "()V",
                false));


        return list;
    }
    private InsnList minenew() {
        // create a new instruction list
        InsnList list = new InsnList();

        list.add(new VarInsnNode(Opcodes.ALOAD,0));
        list.add(new LdcInsnNode("2.0"));
        list.add(new FieldInsnNode(Opcodes.PUTFIELD, //
                "net/minecraft/client/Minecraft",
                "launchedVersion",
                "Ljava/lang/String;"));


        return list;
    }
}
