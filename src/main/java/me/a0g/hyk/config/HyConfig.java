package me.a0g.hyk.config;

import gg.essential.api.utils.GuiUtil;
import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;
import lombok.Getter;
import lombok.Setter;
import me.a0g.hyk.gui.EditLocationsGui;

import java.awt.*;
import java.io.File;
import java.util.Locale;
import java.util.Objects;

@Getter @Setter
public class HyConfig extends Vigilant {

    //General

    @Property(
            type = PropertyType.BUTTON,
            name = "Gui Edit",
            category = "General",
            description = "Edit mod gui."
    )
    private void editgui(){
        GuiUtil.open(Objects.requireNonNull(new EditLocationsGui()));
    }

    @Property(
            type = PropertyType.SWITCH,
            name = "AutoSprint",
            category = "General",
            subcategory = "General",
            description = "Toggle AutoSprint."
    )
    private boolean autoSprintEnabled = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Time",
            category = "General",
            subcategory = "General",
            description = "Time display."
    )
    private boolean timed = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "DANreal",
            category = "General",
            subcategory = "General",
            description = "Danreal mute."
    )
    private boolean daynreal = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "ArmorHud",
            category = "General",
            subcategory = "General",
            description = "Armor display."
    )
    private boolean armorh = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "FPS",
            category = "General",
            subcategory = "General",
            description = "FPS display."
    )
    private boolean fpsd = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "CPS",
            category = "General",
            subcategory = "General",
            description = "CPS display."
    )
    private boolean cpsd = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Background",
            category = "General",
            subcategory = "General",
            description = "Background display."
    )
    private boolean backgroung = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "NameChange",
            category = "General",
            subcategory = "Form",
            description = "Toggle custom name."
    )
    private boolean namechanger = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "SWHook",
            category = "General",
            subcategory = "Hook",
            description = "Sw game stats."
    )
    private boolean swhook = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "BankHook",
            category = "General",
            subcategory = "Hook",
            description = "Bank with/dep coins."
    )
    private boolean bankhook = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "RPC",
            category = "General",
            subcategory = "General",
            description = "Discord RPC."
    )
    private boolean rpc = false;



    //Skyblock

    @Property(
            type = PropertyType.SWITCH,
            name = "Rarity",
            category = "SkyBlock",
            subcategory = "Rarity",
            description = "Rarity display"
    )
    private boolean sbrarity = false;

    @Property(
            type = PropertyType.SLIDER,
            name = "Alpha",
            category = "SkyBlock",
            subcategory = "Rarity",
            description = "Rarity alpha",
            max = 100
    )
    private int sbrarityalpha = 50;

    @Property(
            type = PropertyType.SWITCH,
            name = "Cakes display",
            category = "SkyBlock",
            subcategory = "SkyBlock",
            description = "Hours to cakes end."
    )
    private boolean cakedisplay = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Dungeon stared",
            category = "SkyBlock",
            subcategory = "SkyBlock",
            description = "Mini bosses in names in dungeons."
    )
    private boolean dungeonstared = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Commissions display",
            category = "SkyBlock",
            subcategory = "SkyBlock",
            description = "Display active Commissions."
    )
    private @Getter boolean commsdispl = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Damage overwrite",
            category = "SkyBlock",
            subcategory = "SkyBlock",
            description = "Make damage look cool."
    )
    private boolean damagesb = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Diana",
            category = "SkyBlock",
            subcategory = "SkyBlock",
            description = "Burrows waypoints."
    )
    private boolean Diana = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "SbUtils",
            category = "SkyBlock",
            subcategory = "SkyBlock",
            description = "1)AutoPet messages hide.",
            hidden = true
    )
    private boolean SbUtils = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Auto Game",
            category = "SkyBlock",
            subcategory = "Utils",
            description = "auto game start.",
            hidden = true
    )
    private boolean autogame = false;


    @Property(
            type = PropertyType.SWITCH,
            name = "Sizer",
            category = "SkyBlock",
            subcategory = "SkyBlock",
            description = "size changer",
            hidden = true
    )
    private boolean sizer = false;





    // Private

    @Property(
            type = PropertyType.SWITCH,
            name = "ChestSp",
            category = "Private",
            subcategory = "General",
            description = "Toggle ChestSp."
    )
    private boolean chestsp = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Xr",
            category = "Private",
            subcategory = "General",
            description = "Toggle XR."
    )
    private boolean xr = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "HitFix",
            category = "Private",
            subcategory = "General",
            description = "Toggle HitFix."
    )
    private boolean hitfix = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Blidness",
            category = "Private",
            subcategory = "General",
            description = "Toggle antiblidness."
    )
    private boolean antiblidness = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "NoFog",
            category = "Private",
            subcategory = "General",
            description = "Toggle antifog."
    )
    private boolean antifog = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Fairy",
            category = "Private",
            subcategory = "General",
            description = "Toggle Fairy Sp."
    )
    private boolean fairysp = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Presents",
            category = "Private",
            subcategory = "General",
            description = "Toggle Presents Sp."
    )
    private boolean presentssp = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "SkullSp",
            category = "Private",
            subcategory = "General",
            description = "Toggle skull Sp."
    )
    private boolean skullsp = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Bats",
            category = "Private",
            subcategory = "General",
            description = "Toggle Bats Sp."
    )
    private boolean batsp = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "PSP",
            category = "Private",
            subcategory = "General",
            description = "Toggle PSP."
    )
    private boolean psp = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "NameT",
            category = "Private",
            subcategory = "Name",
            description = "Toggle NameT."
    )
    private boolean namet = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Health",
            category = "Private",
            subcategory = "Name",
            description = "Toggle Health."
    )
    private boolean nameh = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "HealthMM",
            category = "Private",
            subcategory = "Name",
            description = "For MurderMystery, BedWars and when u dont see names."
    )
    private boolean namemm = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "NameBack",
            category = "Private",
            subcategory = "Name",
            description = "Render back panel."
    )
    private boolean nameback = false;

    @Property(
            type = PropertyType.SLIDER,
            name = "NameS",
            description = "NameSc - x / 10 ",
            category = "Private",
            subcategory = "Name",
            max = 100
    )
    private int namescale = 10;


    //Strings
    @Property(
            type = PropertyType.TEXT,
            name = "Api key",
            description = "ApiKey for stats parsing.",
            category = "Strings"
    )
    private String apikeyy = "";

    @Property(
            type = PropertyType.TEXT,
            name = "CustomName",
            description = "Name",
            category = "General",
            subcategory = "Form"
    )
    private String customname = "";




    //config hidden

    @Property(
            type = PropertyType.TEXT,
            name = "sizescake",
            description = "Need",
            category = "z",
            hidden = true
    )
    private String sizescale = "0.25";

    @Property(
            type = PropertyType.SLIDER,
            name = "coordsx",
            description = "Need",
            category = "z",
            max = 1920,
            hidden = true
    )
    private int mainx = 2;

    @Property(
            type = PropertyType.SLIDER,
            name = "coordsy",
            description = "Need",
            category = "z",
            max = 1920,
            hidden = true
    )
    private int mainy = 2;

    @Property(
            type = PropertyType.SLIDER,
            name = "cakex",
            description = "Need",
            category = "z",
            max = 1920,
            hidden = true
    )
    private int cakex = 22;

    @Property(
            type = PropertyType.SLIDER,
            name = "cakey",
            description = "Need",
            category = "z",
            max = 1920,
            hidden = true
    )
    private int cakey = 7;

    @Property(
            type = PropertyType.SLIDER,
            name = "armorx",
            description = "Need",
            category = "z",
            max = 1920,
            hidden = true
    )
    private int armorx = 1;

    @Property(
            type = PropertyType.SLIDER,
            name = "armory",
            description = "Need",
            category = "z",
            max = 1920,
            hidden = true
    )
    private int armory = 50;

    @Property(
            type = PropertyType.SLIDER,
            name = "commsx",
            description = "Need",
            category = "z",
            max = 1920,
            hidden = true
    )
    private int commsx = 22;

    @Property(
            type = PropertyType.SLIDER,
            name = "commsy",
            description = "Need",
            category = "z",
            max = 1920,
            hidden = true
    )
    private int commsy = 45;

    @Property(
            type = PropertyType.TEXT,
            name = "Cakes",
            description = "Needed.",
            category = "z",
            hidden = true
    )
    private String cakepicked = "";

    @Property(
            type = PropertyType.TEXT,
            name = "Scale",
            description = "Needed.",
            category = "z",
            hidden = true
    )
    private String scale = "1.0";

    @Property(
            type = PropertyType.COLOR,
            name = "TestColor",
            description = "color",
            category = "z",
            hidden = true
    )
    private Color testcolor = Color.white;



    public HyConfig() {
        super(new File("./config/hyk.toml"));
        initialize();

        setCategoryDescription("Strings","Some text to be stored in config");
        setCategoryDescription("General","All general features");
        setCategoryDescription("Private","o_0");
        setCategoryDescription("SkyBlock","Features that used only in skyblock");
        setSubcategoryDescription("SkyBlock","Rarity","Draw a circle of item rarity color");
        setSubcategoryDescription("General","Form","Allows you to rename yourself. Colors support &[0-9a-f]{name}");
    }

}

