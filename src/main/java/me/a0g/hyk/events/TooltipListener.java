package me.a0g.hyk.events;

import me.a0g.hyk.Hyk;
import me.a0g.hyk.utils.TextUtils;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.StringUtils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public class TooltipListener {

    private final Hyk main = Hyk.getInstance();


   // public static String hitReach = "Last hit 0.00";

    /*@SubscribeEvent
    public void onEntityDamaged(AttackEntityEvent e){
        FMLLog.info("Event");
        System.out.println("hasda");
        if(e.target instanceof EntityPlayerSP){
            //Vec3 vec3 = Minecraft.getMinecraft().getRenderViewEntity().getPositionEyes(1.0F);
          //  double range = Minecraft.getMinecraft().objectMouseOver.hitVec.distanceTo(vec3);
            FMLLog.info("Hit");
           hitReach = "Last hit " + Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e.entity);
        }
    }*/


    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent e) {
        if (e.itemStack == null || e.toolTip == null || e.entityPlayer == null) {
            return;
        }

        if (main.getUtils().checkForSkyblock() && main.getHyConfig().isSbPriceEach()) {
            int itemAmount = e.itemStack.stackSize;
            if (itemAmount > 1) {
                List<String> toolTip = e.toolTip;

                /*if (e.itemStack.hasTagCompound() && e.itemStack.getTagCompound().getCompoundTag("display").getTag("Lore").toString().contains("Buy it now:"))
                    FMLLog.info("BIN YEAST");*/

                for (int i = 1; i < toolTip.size(); i++) {
                    String line = toolTip.get(i);
                    String unformatedline = TextUtils.stripColor(line);
                    if (unformatedline.startsWith("Buy it now: ")) {

                        try {
                            NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
                            numberFormatter.setMaximumFractionDigits(0);

                            long price = numberFormatter.parse(StringUtils.substringBetween(unformatedline, ": ", " coins")).longValue();
                            double priceEach = price / (double) itemAmount;
                            String priceEachString = main.getUtils().nicenumbers((int) priceEach);

                            String pricePerItem = " ??e(" + priceEachString + ") each";

                            toolTip.set(i, line + pricePerItem);

                            return;
                        } catch (ParseException ex) {
                            return;
                        }

                    }
                }
            }
        }
    }

}
