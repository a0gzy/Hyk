package me.a0g.hyk.tweaker.asm;

import me.a0g.hyk.tweaker.transformer.ITransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class NetworkManagerTransformer implements ITransformer {

    /**
     * {@link net.minecraft.network.NetworkManager}
     */

    @Override
    public String[] getClassName() {
        // assign the full class name
        return new String[]{"net.minecraft.network.NetworkManager"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        // loop through all methods
        for (MethodNode method : classNode.methods) {
            // map the method to searge
            String methodName = mapMethodName(classNode, method);

            if(methodName.equals("channelRead0")) {//shutdown
                method.instructions.insertBefore(method.instructions.getFirst(), sayBruh());
            }


        }

    }

    private InsnList sayBruh() {
        InsnList list = new InsnList();

        list.add(new VarInsnNode(Opcodes.ALOAD, 2)); // Packet

        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, //
                "me/a0g/hyk/tweaker/asm/hooks/NetworkManagerHook",
                "onReceivePacket",
                "(Lnet/minecraft/network/Packet;)V",
                false));

        return list;
    }
}
