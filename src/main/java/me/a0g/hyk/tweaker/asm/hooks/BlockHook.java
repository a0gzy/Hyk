package me.a0g.hyk.tweaker.asm.hooks;

import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.tweaker.asm.utils.ReturnValue;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.FMLLog;

import java.util.ArrayList;

public class BlockHook {

    public static void  shouldBe2(Block block,ReturnValue<Boolean> returnValue){
        HypixelKentik main = HypixelKentik.getInstance();

        if(main.getHyConfig().isXr() && main.hray){
            returnValue.cancel( (block == Blocks.stone || block == Blocks.dirt || block == Blocks.grass) ? false : true);
        }
    }

    public static void getBright(ReturnValue<Integer> returnValue){
        HypixelKentik main = HypixelKentik.getInstance();

        if(main.getHyConfig().isXr() && main.hray){
            returnValue.cancel( Integer.MAX_VALUE );
        }
    }

}
