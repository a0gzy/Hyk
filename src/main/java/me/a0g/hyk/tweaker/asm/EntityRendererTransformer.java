package me.a0g.hyk.tweaker.asm;

import me.a0g.hyk.tweaker.asm.utils.TransformerMethod;
import me.a0g.hyk.tweaker.transformer.ITransformer;
import net.minecraftforge.fml.common.FMLLog;
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
            if (TransformerMethod.setupFog.matches(method)) {
               // method.instructions.insertBefore(method.instructions.getFirst(), sayBruh2());
               method.instructions.insertBefore(method.instructions.getLast().getPrevious(), sayBruh2());
            }

            /*if (methodName.equalsIgnoreCase("setupFog")
                    || methodName.equalsIgnoreCase("func_78468_a")) {
                method.instructions.insertBefore(method.instructions.getFirst(), sayBruh2());
                method.instructions.insertBefore(method.instructions.getLast().getPrevious(), sayBruh2());
            }*/
            /*if (InjectionHelper.matches(method, TransformerMethod.setupFog)) {
                InjectionHelper.start()
                        .matchMethodHead()

                        .callStaticMethod("me/a0g/hyk/tweaker/asm/hooks/EntityRendererHook", "setupFog2", "()Z")
                        .startIfEqual()
                        .reeturn()
                        .endIf()
                        .endCode()

                        .finish();
                return;
            }*/

            if(methodName.equalsIgnoreCase("getMouseOver") ||
                methodName.equalsIgnoreCase("func_78473_a")){

                AbstractInsnNode reachNode = null;
                AbstractInsnNode jumpInsnNode = null;
                LabelNode rL = null;
                for (AbstractInsnNode insnNode : method.instructions.toArray()) {
                    if (insnNode.getOpcode() == Opcodes.INVOKEVIRTUAL && TransformerMethod.distanceTo.matches((MethodInsnNode) insnNode)
                            && insnNode.getNext().getOpcode() == Opcodes.LDC) {
                           // && ((MethodInsnNode) insnNode).owner.equalsIgnoreCase("net/minecraft/util/Vec3")
                           // && ( ((MethodInsnNode) insnNode).name.equalsIgnoreCase("distanceTo") || ((MethodInsnNode) insnNode).name.equalsIgnoreCase("f"))
                          //  && ((MethodInsnNode) insnNode).desc.equalsIgnoreCase("(Lnet/minecraft/util/Vec3;)D")
                           // && ((MethodInsnNode) insnNode).itf == false

                        /* mv.visitJumpInsn(IFNULL, l51);
                        mv.visitVarInsn(ILOAD, 8); to getfield
                        mv.visitJumpInsn(IFEQ, l51);
                        mv.visitVarInsn(ALOAD, 7);
                        mv.visitVarInsn(ALOAD, 12);
                        mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/util/Vec3", "distanceTo", "(Lnet/minecraft/util/Vec3;)D", false);
                        mv.visitLdcInsn(new Double("3.0"));
                        mv.visitInsn(DCMPL);*/

                        reachNode = insnNode.getNext();

                        if(insnNode.getPrevious().getPrevious().getPrevious().getOpcode() == Opcodes.IFEQ){
                            jumpInsnNode = insnNode.getPrevious().getPrevious().getPrevious();
                            if(jumpInsnNode != null) {
                                rL = ((JumpInsnNode) jumpInsnNode).label;
                            }
                        }

                        FMLLog.info("MINE4.5");
                    }
                }
                if(reachNode != null && rL != null){
                    //reachNode = new LdcInsnNode(new Double("4.5"));
                   // method.instructions.remove(reachNode.getPrevious().getPrevious().getPrevious().getPrevious().getPrevious());
                   // method.instructions.remove(reachNode.getPrevious().getPrevious().getPrevious().getPrevious());

                  //  method.instructions.set(reachNode,new LdcInsnNode(new Double("6.0")));

                    method.instructions.insertBefore(reachNode.getNext().getNext().getNext(),sayBruh23(rL));


                  //  method.instructions.remove(reachNode);
                   // method.instructions.insertBefore(reachNode,new LdcInsnNode(new Double("4.5")));
                }
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

    private InsnList sayBruh2() {
        // create a new instruction list
        InsnList list = new InsnList();


        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, // FontRendererHook.testchroma(text,shadow);
                "me/a0g/hyk/tweaker/asm/hooks/EntityRendererHook",
                "setupFog2",
                "()Z",  // void
                false));

        LabelNode notCancelled = new LabelNode();
        list.add(new JumpInsnNode(Opcodes.IFEQ, notCancelled)); // if (GuiScreenHook.onRenderTooltip(stack, x, y)) {
        list.add(new InsnNode(Opcodes.RETURN)); // return;
        list.add(notCancelled); // }

        //list.add(new InsnNode(Opcodes.RETURN)); // return;

        return list;
    }

    private InsnList sayBruh23(LabelNode rL) {
        // create a new instruction list
        InsnList list = new InsnList();

       /* mv.visitJumpInsn(IFNULL, l51);
        mv.visitVarInsn(ILOAD, 8); to getfield
        mv.visitJumpInsn(IFEQ, l51);
        mv.visitVarInsn(ALOAD, 7);
        mv.visitVarInsn(ALOAD, 12);
        mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/util/Vec3", "distanceTo", "(Lnet/minecraft/util/Vec3;)D", false);
        mv.visitLdcInsn(new Double("3.0"));
        mv.visitInsn(DCMPL);*/

//        list.add(new FieldInsnNode(Opcodes.GETFIELD, Type.getInternalName(HyConfig.class), "rech",
//                "Z")); // Owner mozhno tak zhe Type.getInternalName(ReturnValue.class)
//        list.add(new JumpInsnNode( Opcodes.IF_ACMPEQ, rL));
//
//        list.add(new FieldInsnNode(Opcodes.GETFIELD, Type.getInternalName(HyConfig.class), "rechr",
//                "D")); // Owner mozhno tak zhe Type.getInternalName(ReturnValue.class)

        list.add(new VarInsnNode(Opcodes.ALOAD,7));
        list.add(new VarInsnNode(Opcodes.ALOAD,12));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"me/a0g/hyk/tweaker/asm/hooks/EntityRendererHook", "rech",
                "(Lnet/minecraft/util/Vec3;Lnet/minecraft/util/Vec3;)Z", false));
        list.add(new JumpInsnNode( Opcodes.IFEQ, rL));

        return list;
    }
}
