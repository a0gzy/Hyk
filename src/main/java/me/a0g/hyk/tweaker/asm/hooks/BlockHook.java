package me.a0g.hyk.tweaker.asm.hooks;

import me.a0g.hyk.Hyk;
import me.a0g.hyk.tweaker.asm.utils.ReturnValue;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class BlockHook {

    public static void  shouldBe2(Block block,ReturnValue<Boolean> returnValue){
        Hyk main = Hyk.getInstance();

        //block.set

        if(main.getHyConfig().isXr() && main.hray){
            returnValue.cancel( (block == Blocks.stone || block == Blocks.dirt || block == Blocks.grass) ? false : true);
        }
    }

    public static void getBright(ReturnValue<Integer> returnValue){
        Hyk main = Hyk.getInstance();

        if(main.getHyConfig().isXr() && main.hray){
            returnValue.cancel( Integer.MAX_VALUE );
        }
    }

}
