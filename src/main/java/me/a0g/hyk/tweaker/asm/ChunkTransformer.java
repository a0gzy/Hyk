package me.a0g.hyk.tweaker.asm;

import me.a0g.hyk.tweaker.transformer.ITransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class ChunkTransformer implements ITransformer {

    /**
     * {@link net.minecraft.world.chunk.Chunk}
     */

    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.world.chunk.Chunk"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode  : classNode.methods) {
            // map the method to searge
            String methodName = mapMethodName(classNode, methodNode);

            if (methodName.equalsIgnoreCase("setBlockState")
                    || methodName.equalsIgnoreCase("func_177436_a")) {

                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(),sayBruh());
            }
        }
    }

    private InsnList sayBruh() {
        // create a new instruction list
        InsnList list = new InsnList();

        list.add(new VarInsnNode(Opcodes.ALOAD, 0)); // Chunk
        list.add(new VarInsnNode(Opcodes.ALOAD, 1)); // BlockPos
        list.add(new VarInsnNode(Opcodes.ALOAD, 2)); // IBlockState
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "me/a0g/hyk/tweaker/asm/hooks/ChunkHook", "onBlockChange",
                "(Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)V", false));


        return list;
    }
}
