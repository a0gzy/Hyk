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


    public static boolean shouldBe(Block block,IBlockAccess worldIn, BlockPos pos, EnumFacing side){

        HypixelKentik main = HypixelKentik.getInstance();

        if(main.getHyConfig().isXr() && (block == Blocks.stone || block == Blocks.dirt || block == Blocks.grass)){
                return true;
        }
       /* else if(worldIn.getBlockState(pos).getBlock().doesSideBlockRendering(worldIn,pos,side)){
            return true;
        }*/


        //return worldIn.getBlockState(pos).getBlock().doesSideBlockRendering(worldIn, pos, side);

        return false;




       // return !(side == EnumFacing.DOWN && block.minY > 0.0D ? true : (side == EnumFacing.UP && block.maxY < 1.0D ? true : (side == EnumFacing.NORTH && block.minZ > 0.0D ? true : (side == EnumFacing.SOUTH && block.maxZ < 1.0D ? true : (side == EnumFacing.WEST && block.minX > 0.0D ? true : (side == EnumFacing.EAST && block.maxX < 1.0D ? true : !worldIn.getBlockState(pos).getBlock().doesSideBlockRendering(worldIn, pos, side)))))));
    }

    public static boolean shouldBe2(Block block,IBlockAccess worldIn, BlockPos pos, EnumFacing side){

        return side == EnumFacing.DOWN && block.minY > 0.0D ? true : (side == EnumFacing.UP && block.maxY < 1.0D ? true : (side == EnumFacing.NORTH && block.minZ > 0.0D ? true : (side == EnumFacing.SOUTH && block.maxZ < 1.0D ? true : (side == EnumFacing.WEST && block.minX > 0.0D ? true : (side == EnumFacing.EAST && block.maxX < 1.0D ? true : !worldIn.getBlockState(pos).getBlock().doesSideBlockRendering(worldIn, pos, side))))));
    }







}
