package me.a0g.hyk.tweaker.asm.hooks;

import me.a0g.hyk.events.BlockChangeEvent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ChunkHook {

    public static void onBlockChange(Chunk chunk, BlockPos position, IBlockState newBlock){
        IBlockState oldBlock = chunk.getBlockState(position);
        if (oldBlock != newBlock && Minecraft.getMinecraft().theWorld != null)
            try {
                MinecraftForge.EVENT_BUS.post((Event)new BlockChangeEvent(position, oldBlock, newBlock));
            } catch (Exception exception) {}
    }

}
