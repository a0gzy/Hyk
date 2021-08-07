package me.a0g.hyk.core;

import lombok.Getter;
import lombok.Setter;
import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.config.HykPos;
import me.a0g.hyk.events.Render;
import me.a0g.hyk.events.TextRenderer;
import me.a0g.hyk.gui.buttons.ButtonLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.*;

@Getter
public enum Feature {


    TIME(0,"12:00", HypixelKentik.getInstance().getHykPos().time),
    FPS(1,"FPS 555",HypixelKentik.getInstance().getHykPos().fps),
    SPRINT(2,"Sprint",HypixelKentik.getInstance().getHykPos().sprint),
    CPS(3,"CPS: 0",HypixelKentik.getInstance().getHykPos().cps),
    CAKES(4,"1d5h",HypixelKentik.getInstance().getHykPos().cakes),
    ARMORHUD(5,"",HypixelKentik.getInstance().getHykPos().armorhud),
    COMMISSIONS(6,"§9 Commissions\n" +
            "Mithrill miner 80%\n" +
            "Mithrill miner 80%\n" +
            "Mithrill miner 80%\n" +
            "Mithrill miner 80%\n" +
            "\n" +
            "Mithrill powder 10,243",HypixelKentik.getInstance().getHykPos().commissions);


    @Getter
    private static final Set<Feature> guiFeatures = new LinkedHashSet<>(Arrays.asList(TIME,FPS,SPRINT,CPS,CAKES,ARMORHUD,COMMISSIONS));

    private final int id;

   /* public String getText() {
        if(text != null) {
            return text;
        }else return "";
    }*/

    @Setter private String text;
    @Setter private HykPos.PosTweak posTweak;
    private int x;
    private int y;
    private float scale;

    Feature(int id,String text,HykPos.PosTweak posTweak){
        this.id = id;
        this.text = text;
        this.posTweak = posTweak;
        this.x = posTweak.x;
        this.y = posTweak.y;
        this.scale = posTweak.scale;
    }

    Feature(int id,HykPos.PosTweak posTweak){
        this.id = id;
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    public static void setFromConfig(){

        TIME.posTweak = HypixelKentik.getInstance().getHykPos().time;
        SPRINT.posTweak = HypixelKentik.getInstance().getHykPos().sprint;
        CPS.posTweak = HypixelKentik.getInstance().getHykPos().cps;
        FPS.posTweak = HypixelKentik.getInstance().getHykPos().fps;
        CAKES.posTweak = HypixelKentik.getInstance().getHykPos().cakes;
        COMMISSIONS.posTweak = HypixelKentik.getInstance().getHykPos().commissions;
        ARMORHUD.posTweak = HypixelKentik.getInstance().getHykPos().armorhud;
    }

    public boolean isGuiFeature() {
        return guiFeatures.contains(this);
    }

    public void draw(Minecraft mc){

        HypixelKentik main = HypixelKentik.getInstance();

        if(this ==  SPRINT){
            if(main.getHyConfig().isAutoSprintDisplay()) {
                new TextRenderer(mc, main.getHyConfig().getAutoSprintText().replaceAll("&", "§"), posTweak.x, posTweak.y, posTweak.scale, main.getHyConfig().getAutoSprintColor().getRGB());
            }
        }
        else if(this ==  TIME){
            if(main.getHyConfig().isTimed()) {
                new TextRenderer(mc, Render.getrenderTime(), posTweak.x, posTweak.y, posTweak.scale, main.getHyConfig().getTimeColor().getRGB());
            }
        }
        else if(this ==  FPS){
            if(main.getHyConfig().isFpsd()) {
                new TextRenderer(mc, "FPS: " + Minecraft.getDebugFPS(), posTweak.x, posTweak.y, posTweak.scale, main.getHyConfig().getFpsColor().getRGB());
            }
        }
        else if(this ==  CPS){
            if(main.getHyConfig().isCpsd()) {
                new TextRenderer(mc, "CPS: " + Render.getPlayerLeftCPS() + "", posTweak.x, posTweak.y, posTweak.scale, main.getHyConfig().getCpsColor().getRGB());
            }
        }
        else if(this ==  CAKES){

            if(!main.getHyConfig().isCakedisplay()) return;

            if(!main.getUtils().checkForSkyblock()) return;

            String cakeget = main.getHyConfig().getCakepicked();

            if (cakeget.isEmpty()) {
                return;
            }

            double timeNow = System.currentTimeMillis() / 1000;
            double caketime = (Long.parseLong(cakeget) + 172800000 ) / 1000 ;
            String fdate;
            if(timeNow < caketime) {
                fdate = EnumChatFormatting.LIGHT_PURPLE +  main.getUtils().getTimeBetween(timeNow, caketime);
            }
            else fdate = EnumChatFormatting.LIGHT_PURPLE + "You dont have cakes";

            main.getUtils().enableStandardGLOptions();
            mc.getTextureManager().bindTexture(Render.CAKE_ICON);
            GlStateManager.pushMatrix();
            GlStateManager.scale(posTweak.scale, posTweak.scale,  posTweak.scale);
            Gui.drawModalRectWithCustomSizedTexture((int) (posTweak.x/posTweak.scale), (int) (posTweak.y/posTweak.scale), 0, 0, (int) (18 ), (int)( 18 ), 18 , 18);
            GlStateManager.popMatrix();
            main.getUtils().restoreGLOptions();

           /* main.getUtils().enableStandardGLOptions();
            mc.getTextureManager().bindTexture(Render.CAKE_ICON);
            Gui.drawModalRectWithCustomSizedTexture(posTweak.x,posTweak.y, 0, 0, 16, 16, 16, 16);
            main.getUtils().restoreGLOptions();*/
            new TextRenderer(mc,fdate,(int) (posTweak.x + 18 * posTweak.scale ), (int) (posTweak.y + 18/4* posTweak.scale),posTweak.scale,main.getHyConfig().getCakedisplayColor().getRGB());
            //new TextRenderer(mc,fdate,posTweak.x + 18,posTweak.y + 3,posTweak.scale,main.getHyConfig().getCakedisplayColor().getRGB());
        }
        else if(this ==  COMMISSIONS){

            if(!main.getHyConfig().isCommsdispl()) return;

            String coms = main.getUtils().getCommissions(mc);
            new TextRenderer(mc,coms,posTweak.x,posTweak.y,posTweak.scale,main.getHyConfig().getCommsdisplColor().getRGB());
        }
        else if(this ==  ARMORHUD){

            if(!main.getHyConfig().isArmorh()) return;

            GlStateManager.pushMatrix();
            GlStateManager.scale(posTweak.scale, posTweak.scale,  posTweak.scale);

            for(int item = 0; item < mc.thePlayer.inventory.armorInventory.length; item++) {

                ItemStack armor = mc.thePlayer.inventory.armorInventory[item];
                if(armor == null){

                }
                else {
                    int offset = (-16 * item) + 48;

                    HypixelKentik.getInstance().getUtils().renderArmor(armor, posTweak.x/posTweak.scale, posTweak.y/posTweak.scale + offset, -100);
                 //   main.getUtils().renderArmor(armor, Move.armorXY[0], Move.armorXY[1] + offset, -100);
                }

            }



            GlStateManager.popMatrix();
        }

    }


    public void drawForPos(Minecraft mc,ButtonLocation buttonLocation){

        HypixelKentik main = HypixelKentik.getInstance();
        FontRenderer rendererer = Minecraft.getMinecraft().fontRendererObj;

        if(this ==  SPRINT){
            if(!main.getHyConfig().isAutoSprintDisplay()) return;

            buttonLocation.width = rendererer.getStringWidth(text);
            buttonLocation.height = rendererer.FONT_HEIGHT;

            buttonLocation.checkHoveredAndDrawBox(posTweak.x /posTweak.scale,posTweak.x/posTweak.scale+buttonLocation.width,posTweak.y/posTweak.scale,posTweak.y/posTweak.scale+buttonLocation.height,posTweak.scale);

            new TextRenderer(mc,text,posTweak.x,(posTweak.y),posTweak.scale,main.getHyConfig().getAutoSprintColor().getRGB());
        }
        else if(this ==  TIME){
            if(!main.getHyConfig().isTimed()) return;

            buttonLocation.width = rendererer.getStringWidth(text);
            buttonLocation.height = rendererer.FONT_HEIGHT;

            buttonLocation.checkHoveredAndDrawBox(posTweak.x /posTweak.scale,posTweak.x/posTweak.scale+buttonLocation.width,posTweak.y/posTweak.scale,posTweak.y/posTweak.scale+buttonLocation.height,posTweak.scale);

            new TextRenderer(mc,text,posTweak.x,posTweak.y,posTweak.scale,main.getHyConfig().getTimeColor().getRGB());
        }
        else if(this ==  FPS){
            if(!main.getHyConfig().isFpsd()) return;

            buttonLocation.width = rendererer.getStringWidth(text);
            buttonLocation.height = rendererer.FONT_HEIGHT;

            buttonLocation.checkHoveredAndDrawBox(posTweak.x /posTweak.scale,posTweak.x/posTweak.scale+buttonLocation.width,posTweak.y/posTweak.scale,posTweak.y/posTweak.scale+buttonLocation.height,posTweak.scale);


            new TextRenderer(mc,text,posTweak.x,posTweak.y,posTweak.scale,main.getHyConfig().getFpsColor().getRGB());
        }
        else if(this ==  CPS){
            if(!main.getHyConfig().isCpsd()) return;

            buttonLocation.width = rendererer.getStringWidth(text);
            buttonLocation.height = rendererer.FONT_HEIGHT;

            buttonLocation.checkHoveredAndDrawBox(posTweak.x /posTweak.scale,posTweak.x/posTweak.scale+buttonLocation.width,posTweak.y/posTweak.scale,posTweak.y/posTweak.scale+buttonLocation.height,posTweak.scale);


            new TextRenderer(mc,text,posTweak.x,posTweak.y,posTweak.scale,main.getHyConfig().getCpsColor().getRGB());
        }
        else if(this ==  CAKES){
            if(!main.getHyConfig().isCakedisplay()) return;

            main.getUtils().enableStandardGLOptions();
            mc.getTextureManager().bindTexture(Render.CAKE_ICON);
            GlStateManager.pushMatrix();
            GlStateManager.scale(posTweak.scale, posTweak.scale,  posTweak.scale);
            Gui.drawModalRectWithCustomSizedTexture((int) (posTweak.x/posTweak.scale), (int) (posTweak.y/posTweak.scale), 0, 0, (int) (18 ), (int)( 18 ), 18 , 18);
            GlStateManager.popMatrix();
            main.getUtils().restoreGLOptions();

            buttonLocation.width = 18 + rendererer.getStringWidth(text);
            new TextRenderer(mc,text,(int) (posTweak.x + 18 * posTweak.scale ), (int) (posTweak.y + 18/4* posTweak.scale),posTweak.scale,main.getHyConfig().getCakedisplayColor().getRGB());
            buttonLocation.height = 18;

            buttonLocation.checkHoveredAndDrawBox(posTweak.x /posTweak.scale,posTweak.x/posTweak.scale+buttonLocation.width,posTweak.y/posTweak.scale,posTweak.y/posTweak.scale+buttonLocation.height,posTweak.scale);

        }
        else if(this ==  COMMISSIONS){
            if(!main.getHyConfig().isCommsdispl()) return;

            buttonLocation.width = 120;
            new TextRenderer(mc,text,posTweak.x,posTweak.y,posTweak.scale,main.getHyConfig().getCommsdisplColor().getRGB());
            buttonLocation.height = 64;

            buttonLocation.checkHoveredAndDrawBox(posTweak.x /posTweak.scale,posTweak.x/posTweak.scale+buttonLocation.width,posTweak.y/posTweak.scale,posTweak.y/posTweak.scale+buttonLocation.height,posTweak.scale);

        }
        else if(this ==  ARMORHUD){
            if(!main.getHyConfig().isArmorh()) return;

            GlStateManager.pushMatrix();
            GlStateManager.scale(posTweak.scale, posTweak.scale,  posTweak.scale);

            for(int i = 0;i<4;i++) {
                ItemStack armorr = new ItemStack(Item.getItemById(313-i));
                int offset = (-16 * i) + 48;
                HypixelKentik.getInstance().getUtils().renderArmor(armorr, posTweak.x/posTweak.scale, posTweak.y/posTweak.scale + offset, -100);

            }
            GlStateManager.popMatrix();

            buttonLocation.width = 18;
            buttonLocation.height = 64;

            buttonLocation.checkHoveredAndDrawBox(posTweak.x /posTweak.scale,posTweak.x/posTweak.scale+buttonLocation.width,posTweak.y/posTweak.scale,posTweak.y/posTweak.scale+buttonLocation.height,posTweak.scale);


        }

    }





}
