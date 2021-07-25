package me.a0g.hyk.gui;

import lombok.Setter;
import me.a0g.hyk.HypixelKentik;
import me.a0g.hyk.commands.Move;
import me.a0g.hyk.events.TextRenderer;
import me.a0g.hyk.gui.buttons.ButtonArrow;
import me.a0g.hyk.gui.buttons.ButtonEditLocs;
import me.a0g.hyk.gui.buttons.ButtonText;
import me.a0g.hyk.gui.buttons.ButtonToggle;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLLog;

public class HykGui extends GuiScreen {

    public static final ResourceLocation LOGO = new ResourceLocation("hyk", "logo.png");


    private HypixelKentik main = HypixelKentik.getInstance();
    private int page;
    private String selection;

    private int maxpage = 2;
//    private int maxpage = selection.contains( "SkyBlock" ) ? 1 : 2;

    private ButtonText general;
    private ButtonText skyblock;
    private ButtonEditLocs editLocs;
    private ButtonArrow nextpage;
    private ButtonArrow oldpage;

    private ButtonToggle bt1;
    private ButtonToggle bt2;
    private ButtonToggle bt3;
    private ButtonToggle bt4;
    private ButtonToggle bt5;
    private ButtonToggle bt6;
//    private ButtonToggle bt7;
//    private ButtonToggle bt8;
//    private ButtonToggle bt9;



    public HykGui(int page,String selection) {
        this.page = page;
        this.selection = selection;

    }

    @Override
    public void initGui() {
        super.initGui();

        if(selection.equals("SkyBlock")){
            maxpage =  1;
        }

        ScaledResolution sr = new ScaledResolution(mc);

        String generalt = "General";
        general = new ButtonText(0, (int) (sr.getScaledWidth()/2 - mc.fontRendererObj.getStringWidth(generalt)/2 - mc.fontRendererObj.getStringWidth(generalt) * 1.5 ) , (int) (sr.getScaledHeight()/4.7),40,mc.fontRendererObj.FONT_HEIGHT,1,generalt );

        String sbtext = "SkyBlock";
        skyblock = new ButtonText(0, (int) (sr.getScaledWidth()/2 - mc.fontRendererObj.getStringWidth(sbtext)/2) , (int) (sr.getScaledHeight()/4.7),40,mc.fontRendererObj.FONT_HEIGHT,1,sbtext );

        editLocs = new ButtonEditLocs(2,sr.getScaledHeight() - 30 - 2);

        if(page == maxpage) {
            nextpage = new ButtonArrow(sr.getScaledWidth() / 2 + sr.getScaledWidth() / 16, sr.getScaledHeight() / 1.2, main, ButtonArrow.ArrowType.RIGHT, true);
        }
        else {
            nextpage = new ButtonArrow(sr.getScaledWidth() / 2 + sr.getScaledWidth() / 16, sr.getScaledHeight() / 1.2, main, ButtonArrow.ArrowType.RIGHT, false);
        }

        if(page == 1) {
            oldpage = new ButtonArrow(sr.getScaledWidth() / 2 - sr.getScaledWidth() / 16 - 30, sr.getScaledHeight() / 1.2, main, ButtonArrow.ArrowType.LEFT, true);
        }
        else {
            oldpage = new ButtonArrow(sr.getScaledWidth() / 2 - sr.getScaledWidth() / 16 - 30, sr.getScaledHeight() / 1.2, main, ButtonArrow.ArrowType.LEFT, false);
        }


        switch (selection){
            case "General":
                switch (page) {
                    case 1:

                        bt1 = new ButtonToggle(sr.getScaledWidth() / 2 - sr.getScaledWidth() / 4 + 31, sr.getScaledHeight() / 2 - sr.getScaledHeight() / 4 + mc.fontRendererObj.FONT_HEIGHT + 5, main, main.getHyConfig().isAutoSprintEnabled());
                        this.buttonList.add(bt1);

                        bt2 = new ButtonToggle(sr.getScaledWidth() / 2 - 15, sr.getScaledHeight() / 2 - sr.getScaledHeight() / 4 + mc.fontRendererObj.FONT_HEIGHT + 5, main,main.getHyConfig().isArmorh());
                        this.buttonList.add(bt2);

                        bt3 = new ButtonToggle(sr.getScaledWidth() / 2 + sr.getScaledWidth() / 4 - 62 , sr.getScaledHeight() / 2 - sr.getScaledHeight() / 4 + mc.fontRendererObj.FONT_HEIGHT + 5, main,main.getHyConfig().isTimed());
                        this.buttonList.add(bt3);

                        bt4 = new ButtonToggle(sr.getScaledWidth() / 2 - sr.getScaledWidth() / 4 + 31, sr.getScaledHeight() / 2 - mc.fontRendererObj.FONT_HEIGHT, main,main.getHyConfig().isFpsd());
                        this.buttonList.add(bt4);

                        bt5 = new ButtonToggle(sr.getScaledWidth() / 2 - 15, sr.getScaledHeight() / 2 - mc.fontRendererObj.FONT_HEIGHT, main,main.getHyConfig().isBackgroung());
                        this.buttonList.add(bt5);

                        bt6 = new ButtonToggle(sr.getScaledWidth() / 2 + sr.getScaledWidth() / 4 - 62, sr.getScaledHeight() / 2 - mc.fontRendererObj.FONT_HEIGHT , main,main.getHyConfig().isCpsd());
                        this.buttonList.add(bt6);

                        /*bt7 = new ButtonToggle(sr.getScaledWidth() / 2 - sr.getScaledWidth() / 4 + 31, sr.getScaledHeight() / 2 + sr.getScaledHeight() / 4 - mc.fontRendererObj.FONT_HEIGHT - 15, main);
                        this.buttonList.add(bt7);

                        bt8 = new ButtonToggle(sr.getScaledWidth() / 2 - 15, sr.getScaledHeight() / 2 + sr.getScaledHeight() / 4 - mc.fontRendererObj.FONT_HEIGHT - 15, main);
                        this.buttonList.add(bt8);

                        bt9 = new ButtonToggle(sr.getScaledWidth() / 2 + sr.getScaledWidth() / 4 - 62, sr.getScaledHeight() / 2 + sr.getScaledHeight() / 4 - mc.fontRendererObj.FONT_HEIGHT - 15 , main);
                        this.buttonList.add(bt9);*/

                        break;

                    case 2:



                        bt2 = new ButtonToggle(sr.getScaledWidth() / 2 - 15, sr.getScaledHeight() / 2 - sr.getScaledHeight() / 4 + mc.fontRendererObj.FONT_HEIGHT + 5, main,main.getHyConfig().isNamechanger());
                        this.buttonList.add(bt2);


                        bt4 = new ButtonToggle(sr.getScaledWidth() / 2 - sr.getScaledWidth() / 4 + 31, sr.getScaledHeight() / 2 - mc.fontRendererObj.FONT_HEIGHT, main,main.getHyConfig().isSwhook());
                        this.buttonList.add(bt4);



                        break;
                }
                break;
            case "SkyBlock":
                if(page == 1) {

                    bt1 = new ButtonToggle(sr.getScaledWidth() / 2 - sr.getScaledWidth() / 4 + 31, sr.getScaledHeight() / 2 - sr.getScaledHeight() / 4 + mc.fontRendererObj.FONT_HEIGHT + 5, main,main.getHyConfig().isCommsdispl());
                    this.buttonList.add(bt1);

                    bt2 = new ButtonToggle(sr.getScaledWidth() / 2 - 15, sr.getScaledHeight() / 2 - sr.getScaledHeight() / 4 + mc.fontRendererObj.FONT_HEIGHT + 5, main,main.getHyConfig().isCakedisplay());
                    this.buttonList.add(bt2);

                    bt3 = new ButtonToggle(sr.getScaledWidth() / 2 + sr.getScaledWidth() / 4 - 62 , sr.getScaledHeight() / 2 - sr.getScaledHeight() / 4 + mc.fontRendererObj.FONT_HEIGHT + 5, main,main.getHyConfig().isDiana());
                    this.buttonList.add(bt3);

                    bt4 = new ButtonToggle(sr.getScaledWidth() / 2 - sr.getScaledWidth() / 4 + 31, sr.getScaledHeight() / 2 - mc.fontRendererObj.FONT_HEIGHT, main,main.getHyConfig().isSbUtils());
                    this.buttonList.add(bt4);

                    break;
                }

                break;
        }


        this.buttonList.add(general);
        this.buttonList.add(skyblock);
        this.buttonList.add(nextpage);
        this.buttonList.add(oldpage);
        this.buttonList.add(editLocs);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        ScaledResolution sr = new ScaledResolution(mc);


        mc.getTextureManager().bindTexture(LOGO);
        Gui.drawModalRectWithCustomSizedTexture(sr.getScaledWidth()/2 - sr.getScaledWidth()/12, sr.getScaledHeight()/32 , 0, 0, sr.getScaledWidth()/6, sr.getScaledHeight()/6, sr.getScaledWidth()/6, sr.getScaledHeight()/6);

        if(selection.equals("General")){
            new TextRenderer(mc,selection,sr.getScaledWidth()/2 - mc.fontRendererObj.getStringWidth(selection)/2, (int) (sr.getScaledHeight()/1.3), 1,0xFFFF8A00);
        }
        if(selection.equals("SkyBlock")){
            new TextRenderer(mc,selection,sr.getScaledWidth()/2 - mc.fontRendererObj.getStringWidth(selection)/2, (int) (sr.getScaledHeight()/1.3), 1,0xFFFF8A00);
        }

        drawRect(sr.getScaledWidth()/2 - sr.getScaledWidth()/4,sr.getScaledHeight()/2 - sr.getScaledHeight()/4, sr.getScaledWidth()/2 + sr.getScaledWidth()/4 , sr.getScaledHeight()/2 + sr.getScaledHeight()/4, 0x69000000);

        String pagetext = page + "/" + maxpage;
        new TextRenderer(mc,pagetext, sr.getScaledWidth()/2 - mc.fontRendererObj.getStringWidth(pagetext)/2,(int)(sr.getScaledHeight()/1.2 + sr.getScaledHeight()/30) ,1 );

        switch (selection) {
            case "General":

                String general1 = "AutoSprint";
                String general2 = "ArmorHud";
                String general3 = "Time";
                String general4 = "FPS";
                String general5 = "Background";
                String general6 = "CPS";

                String general21 = "Transformers";
                String general22 = "NameChange";
                String general23 = "ChromaName";
                String general24 = "SwHook";

                switch (page) {
                    case 1:
//                         "AutoSprint ArmorHud Time FPS Background CPS ";

                        new TextRenderer(mc, general1, sr.getScaledWidth() / 2 - sr.getScaledWidth() / 4 + 46 - mc.fontRendererObj.getStringWidth(general1)/2, sr.getScaledHeight() / 2 - sr.getScaledHeight() / 4 + 2, 1);

                        new TextRenderer(mc, general2, sr.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(general2) / 2, sr.getScaledHeight() / 2 - sr.getScaledHeight() / 4 + 2, 1);

                        new TextRenderer(mc, general3, sr.getScaledWidth() / 2 + sr.getScaledWidth() / 4 - 62 + 15 - mc.fontRendererObj.getStringWidth(general3)/2, sr.getScaledHeight() / 2 - sr.getScaledHeight() / 4 + 2 , 1);

                        new TextRenderer(mc, general4, sr.getScaledWidth() / 2 - sr.getScaledWidth() / 4 + 46 - mc.fontRendererObj.getStringWidth(general4)/2, sr.getScaledHeight() / 2 - mc.fontRendererObj.FONT_HEIGHT * 2 - 4, 1);

                        new TextRenderer(mc, general5, sr.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(general5) / 2, sr.getScaledHeight() / 2 - mc.fontRendererObj.FONT_HEIGHT * 2 - 4, 1);

                        new TextRenderer(mc, general6, sr.getScaledWidth() / 2 + sr.getScaledWidth() / 4 - 62 + 15 - mc.fontRendererObj.getStringWidth(general6)/2 , sr.getScaledHeight() / 2 - mc.fontRendererObj.FONT_HEIGHT * 2 - 4 , 1);


                        break;
                    case 2:

//                        "transformers namechange chromaname swhook ";

                        new TextRenderer(mc, general21, sr.getScaledWidth() / 2 - sr.getScaledWidth() / 4 + 46 - mc.fontRendererObj.getStringWidth(general21)/2, sr.getScaledHeight() / 2 - sr.getScaledHeight() / 4 + 2, 1);

                        new TextRenderer(mc, general22, sr.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(general22) / 2, sr.getScaledHeight() / 2 - sr.getScaledHeight() / 4 + 2, 1);

                        new TextRenderer(mc, general23, sr.getScaledWidth() / 2 + sr.getScaledWidth() / 4 - 62 + 15 - mc.fontRendererObj.getStringWidth(general23)/2, sr.getScaledHeight() / 2 - sr.getScaledHeight() / 4 + 2 , 1);

                        new TextRenderer(mc, general24, sr.getScaledWidth() / 2 - sr.getScaledWidth() / 4 + 46 - mc.fontRendererObj.getStringWidth(general24)/2, sr.getScaledHeight() / 2 - mc.fontRendererObj.FONT_HEIGHT * 2 - 4, 1);

                        break;
                }
                break;
            case "SkyBlock":

                String skyb1 = "Commissions";
                String skyb2 = "Cakes";
                String skyb3 = "Diana";
                String skyb4 = "Utils";

                if(page == 1) {

                    new TextRenderer(mc, skyb1, sr.getScaledWidth() / 2 - sr.getScaledWidth() / 4 + 46 - mc.fontRendererObj.getStringWidth(skyb1)/2, sr.getScaledHeight() / 2 - sr.getScaledHeight() / 4 + 2, 1);

                    new TextRenderer(mc, skyb2, sr.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(skyb2) / 2, sr.getScaledHeight() / 2 - sr.getScaledHeight() / 4 + 2, 1);

                    new TextRenderer(mc, skyb3, sr.getScaledWidth() / 2 + sr.getScaledWidth() / 4 - 62 + 15 - mc.fontRendererObj.getStringWidth(skyb3)/2, sr.getScaledHeight() / 2 - sr.getScaledHeight() / 4 + 2 , 1);

                    new TextRenderer(mc, skyb4, sr.getScaledWidth() / 2 - sr.getScaledWidth() / 4 + 46 - mc.fontRendererObj.getStringWidth(skyb4)/2, sr.getScaledHeight() / 2 - mc.fontRendererObj.FONT_HEIGHT * 2 - 4, 1);

                }
                break;
        }


        super.drawScreen(mouseX, mouseY, partialTicks); // Draw buttons.
    }

    @Override
    public void actionPerformed(GuiButton button) {



        if (button == skyblock) {
            HypixelKentik.guiToOpen = "hykguiSkyBlock1";
            //selection = "SkyBlock";
        }
        if (button == general) {
            HypixelKentik.guiToOpen = "hykguiGeneral1";
        }
        if (button == editLocs) {
            HypixelKentik.guiToOpen = "editlocations";
        }
        if (button == nextpage) {
            if (page + 1 <= maxpage) {
                HypixelKentik.guiToOpen = "hykgui" + selection + (page+1);
                //page++;
            }
        }
        if (button == oldpage) {
            if (page - 1 >= 1) {
                HypixelKentik.guiToOpen = "hykgui" + selection + (page-1);
                //page--;
            }
        }

        /*switch (selection) {
            case "General":
                switch (page) {
                    case 1:

                        if (button == bt1) {
                            main.getHyConfig().setAutoSprintEnabled(!main.getHyConfig().isAutoSprintEnabled());
                            main.getHyConfig().markDirty();
                            main.getHyConfig().writeData();
                            HypixelKentik.guiToOpen = "hykgui" + selection + page;
                        }

                        if (button == bt2) {
                            main.getHyConfig().setArmorh(!main.getHyConfig().isArmorh());
                            main.getHyConfig().markDirty();
                            main.getHyConfig().writeData();
                            HypixelKentik.guiToOpen = "hykgui" + selection + page;
                        }

                        if (button == bt3) {
                            main.getHyConfig().setTimed(!main.getHyConfig().isTimed());
                            main.getHyConfig().markDirty();
                            main.getHyConfig().writeData();
                            HypixelKentik.guiToOpen = "hykgui" + selection + page;
                        }

                        if (button == bt4) {
                            main.getHyConfig().setFpsd(!main.getHyConfig().isFpsd());
                            main.getHyConfig().markDirty();
                            main.getHyConfig().writeData();
                            HypixelKentik.guiToOpen = "hykgui" + selection + page;
                        }
                        if (button == bt5) {
                            main.getHyConfig().setBackgroung(!main.getHyConfig().isBackgroung());
                            main.getHyConfig().markDirty();
                            main.getHyConfig().writeData();
                            HypixelKentik.guiToOpen = "hykgui" + selection + page;
                        }
                        if (button == bt6) {
                            main.getHyConfig().setCpsd(!main.getHyConfig().isCpsd());
                            main.getHyConfig().markDirty();
                            main.getHyConfig().writeData();
                            HypixelKentik.guiToOpen = "hykgui" + selection + page;
                        }


                        break;
                    case 2:

                        if (button == bt1) {
                            main.getHyConfig().setTransform(!main.getHyConfig().isTransform());
                            main.getHyConfig().markDirty();
                            main.getHyConfig().writeData();
                            HypixelKentik.guiToOpen = "hykgui" + selection + page;
                        }
                        if (button == bt2) {
                            main.getHyConfig().setNamechanger(!main.getHyConfig().isNamechanger());
                            main.getHyConfig().markDirty();
                            main.getHyConfig().writeData();
                            HypixelKentik.guiToOpen = "hykgui" + selection + page;
                        }
                        if (button == bt3) {
                            main.getHyConfig().setChromaname(!main.getHyConfig().isChromaname());
                            main.getHyConfig().markDirty();
                            main.getHyConfig().writeData();
                            HypixelKentik.guiToOpen = "hykgui" + selection + page;
                        }
                        if (button == bt4) {
                            main.getHyConfig().setSwhook(!main.getHyConfig().isSwhook());
                            main.getHyConfig().markDirty();
                            main.getHyConfig().writeData();
                            HypixelKentik.guiToOpen = "hykgui" + selection + page;
                        }

                        break;
                }
                break;
            case "SkyBlock":

                if (button == bt1) {
                    main.getHyConfig().setCommsdispl(!main.getHyConfig().isCommsdispl());
                    main.getHyConfig().markDirty();
                    main.getHyConfig().writeData();
                    HypixelKentik.guiToOpen = "hykgui" + selection + page;
                }
                if (button == bt2) {
                    main.getHyConfig().setCakedisplay(!main.getHyConfig().isCakedisplay());
                    main.getHyConfig().markDirty();
                    main.getHyConfig().writeData();
                    HypixelKentik.guiToOpen = "hykgui" + selection + page;
                }
                if (button == bt3) {
                    main.getHyConfig().setDiana(!main.getHyConfig().isDiana());
                    main.getHyConfig().markDirty();
                    main.getHyConfig().writeData();
                    HypixelKentik.guiToOpen = "hykgui" + selection + page;
                }

                if (button == bt4) {
                    main.getHyConfig().setSbUtils(!main.getHyConfig().isSbUtils());
                    main.getHyConfig().markDirty();
                    main.getHyConfig().writeData();
                    HypixelKentik.guiToOpen = "hykgui" + selection + page;
                }

                break;
        }*/

        switch (selection) {
            case "General":
                switch (page) {
                    case 1:

                        if (button == bt1) {
                            main.getHyConfig().setAutoSprintEnabled(!main.getHyConfig().isAutoSprintEnabled());
                            HypixelKentik.guiToOpen = "hykgui" + selection + page;
                        }

                        if (button == bt2) {
                            main.getHyConfig().setArmorh(!main.getHyConfig().isArmorh());
                            HypixelKentik.guiToOpen = "hykgui" + selection + page;
                        }

                        if (button == bt3) {
                            main.getHyConfig().setTimed(!main.getHyConfig().isTimed());
                            HypixelKentik.guiToOpen = "hykgui" + selection + page;
                        }

                        if (button == bt4) {
                            main.getHyConfig().setFpsd(!main.getHyConfig().isFpsd());
                            HypixelKentik.guiToOpen = "hykgui" + selection + page;
                        }
                        if (button == bt5) {
                            main.getHyConfig().setBackgroung(!main.getHyConfig().isBackgroung());
                            HypixelKentik.guiToOpen = "hykgui" + selection + page;
                        }
                        if (button == bt6) {
                            main.getHyConfig().setCpsd(!main.getHyConfig().isCpsd());
                            HypixelKentik.guiToOpen = "hykgui" + selection + page;
                        }


                        break;
                    case 2:


                        if (button == bt2) {
                            main.getHyConfig().setNamechanger(!main.getHyConfig().isNamechanger());
                            HypixelKentik.guiToOpen = "hykgui" + selection + page;
                        }
                        if (button == bt4) {
                            main.getHyConfig().setSwhook(!main.getHyConfig().isSwhook());
                            HypixelKentik.guiToOpen = "hykgui" + selection + page;
                        }

                        break;
                }
                break;
            case "SkyBlock":

                if (button == bt1) {
                    main.getHyConfig().setCommsdispl(!main.getHyConfig().isCommsdispl());
                    HypixelKentik.guiToOpen = "hykgui" + selection + page;
                }
                if (button == bt2) {
                    main.getHyConfig().setCakedisplay(!main.getHyConfig().isCakedisplay());
                    HypixelKentik.guiToOpen = "hykgui" + selection + page;
                }
                if (button == bt3) {
                    main.getHyConfig().setDiana(!main.getHyConfig().isDiana());
                    HypixelKentik.guiToOpen = "hykgui" + selection + page;
                }

                if (button == bt4) {
                    main.getHyConfig().setSbUtils(!main.getHyConfig().isSbUtils());
                    HypixelKentik.guiToOpen = "hykgui" + selection + page;
                }

                break;
        }

        main.getHyConfig().markDirty();
        main.getHyConfig().writeData();

    }

}
