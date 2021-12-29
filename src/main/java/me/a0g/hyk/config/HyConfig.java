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
            name = "AutoUpdate",
            category = "General",
            description = "Auto update the mod when new version released."
    )
    private boolean autoUpdate = true;




    @Property(
            type = PropertyType.SWITCH,
            name = "AutoSprint",
            category = "General",
            subcategory = "Sprint",
            description = "Toggle AutoSprint."
    )
    private boolean autoSprintEnabled = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "AutoSprint Display",
            category = "General",
            subcategory = "Sprint",
            description = "Display AutoSprint."
    )
    private boolean autoSprintDisplay = true;

    @Property(
            type = PropertyType.TEXT,
            name = "AutoSprint Text",
            category = "General",
            subcategory = "Sprint",
            description = "AutoSprint text.",
            placeholder = "Sprint"
    )
    private String autoSprintText = "Sprint";

    @Property(
            type = PropertyType.COLOR,
            name = "AutoSprint Color",
            category = "General",
            subcategory = "Sprint",
            description = "AutoSprint display color."
    )
    private Color autoSprintColor = Color.WHITE;


    @Property(
            type = PropertyType.SWITCH,
            name = "Time",
            category = "General",
            subcategory = "General",
            description = "Time display."
    )
    private boolean timed = false;

    @Property(
            type = PropertyType.COLOR,
            name = "Time Color",
            category = "General",
            subcategory = "General",
            description = "Time display color."
    )
    private Color timeColor = Color.WHITE;



    @Property(
            type = PropertyType.SWITCH,
            name = "Mute button",
            category = "General",
            subcategory = "zElse",
            description = "Button in inventory for game muting."
    )
    private boolean gamemute = false;



    @Property(
            type = PropertyType.PERCENT_SLIDER,
            name = "Gamemutefloat",
            category = "General",
            subcategory = "General",
            description = "Button in inventory for game muting.",
            hidden = true
    )
    private float gamemutefloat = 0.03f;




    @Property(
            type = PropertyType.SWITCH,
            name = "ArmorHud",
            category = "General",
            subcategory = "General",
            description = "Armor display."
    )
    private boolean armorh = false;



    @Property(
            type = PropertyType.SWITCH,
            name = "FPS",
            category = "General",
            subcategory = "General",
            description = "FPS display."
    )
    private boolean fpsd = false;

    @Property(
            type = PropertyType.COLOR,
            name = "FPS Color",
            category = "General",
            subcategory = "General",
            description = "FPS display color."
    )
    private Color fpsColor = Color.WHITE;



    @Property(
            type = PropertyType.SWITCH,
            name = "CPS",
            category = "General",
            subcategory = "General",
            description = "CPS display."
    )
    private boolean cpsd = false;

    @Property(
            type = PropertyType.COLOR,
            name = "CPS Color",
            category = "General",
            subcategory = "General",
            description = "CPS display color."
    )
    private Color cpsColor = Color.WHITE;



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
            subcategory = "zElse",
            description = "Sw game stats."
    )
    private boolean swhook = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "BankHook",
            category = "General",
            subcategory = "zElse",
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
            name = "Price",
            category = "SkyBlock",
            subcategory = "Price",
            description = "Display price per each on bin"
    )
    private boolean sbPriceEach = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Rarity",
            category = "SkyBlock",
            subcategory = "Rarity",
            description = "Rarity display"
    )
    private boolean sbrarity = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "IsEnchanted",
            category = "SkyBlock",
            subcategory = "Rarity",
            description = "Enchanted display"
    )
    private boolean sbIsEnch = false;

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
            name = "Auto Melody",
            category = "SkyBlock",
            subcategory = "Cool",
            description = "Auto Melody's Harp."
    )
    private boolean autoMelody = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Auto Powder Chest",
            category = "SkyBlock",
            subcategory = "Cool",
            description = "Auto Powder Chest."
    )
    private boolean autoPowderChest = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Auto Powder",
            category = "SkyBlock",
            subcategory = "Cool",
            description = "Auto Powder."
    )
    private boolean autoPowder = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Cakes display",
            category = "SkyBlock",
            subcategory = "SkyBlock",
            description = "Hours to cakes end."
    )
    private boolean cakedisplay = false;

    @Property(
            type = PropertyType.COLOR,
            name = "Cakes Color",
            category = "SkyBlock",
            subcategory = "SkyBlock",
            description = "Cakes display Color."
    )
    private Color cakedisplayColor = Color.WHITE;


    @Property(
            type = PropertyType.SWITCH,
            name = "Dungeon stared",
            category = "Dungeons",
            subcategory = "Mob",
            description = "Mini bosses in names in dungeons."
    )
    private boolean dungeonstared = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Dungeon stared distance",
            category = "Dungeons",
            subcategory = "Mob",
            description = "Show distance to mobs."
    )
    private boolean dungeonstareddistance = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "NoStonk",
            category = "Dungeons",
            subcategory = "Cool",
            description = "Interact through walls."
    )
    private boolean noStonk = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "AutoCloseChest",
            category = "Dungeons",
            subcategory = "Cool",
            description = "Auto close chests in dungeons."
    )
    private boolean autoCloseChest = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "GhostBlocks",
            category = "Dungeons",
            subcategory = "Cool",
            description = "Create ghost blocks."
    )
    private boolean ghostBlocks = false;



    @Property(
            type = PropertyType.SWITCH,
            name = "Commissions Display",
            category = "SkyBlock",
            subcategory = "SkyBlock",
            description = "Display active Commissions."
    )
    private @Getter boolean commsdispl = false;

    @Property(
            type = PropertyType.COLOR,
            name = "Commissions Color",
            category = "SkyBlock",
            subcategory = "SkyBlock",
            description = "Commissions Display Color."
    )
    private Color commsdisplColor = Color.WHITE;



    @Property(
            type = PropertyType.SWITCH,
            name = "Damage overwrite",
            category = "SkyBlock",
            subcategory = "SkyBlock",
            description = "Make damage look cool."
    )
    private boolean damagesb = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Diana",
            category = "SkyBlock",
            subcategory = "SkyBlock",
            description = "Burrows waypoints.",
            hidden = true
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
            category = "Other",
            subcategory = "Utils",
            description = "auto game start."
    )
    private boolean autogame = false;

    @Property(
            type = PropertyType.TEXT,
            name = "AutoGame Text",
            category = "Other",
            subcategory = "Utils",
            description = "AutoGame text.",
            placeholder = "/play build_battle_guess_the_build"
    )
    private String autoGameText = "/play build_battle_guess_the_build";


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
            name = "BuildBattle",
            category = "Private",
            subcategory = "General",
            description = "Guess the build words /hyk bbh."
    )
    private boolean bbh = false;

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
            name = "Iksrey",
            category = "Private",
            subcategory = "General",
            description = "Allows to toggle iksrey with x when enabled."
    )
    private boolean xr = false;


    @Property(
            type = PropertyType.SWITCH,
            name = "r",
            category = "Private",
            subcategory = "General",
            description = "R."
    )
    private boolean rech = false;

    @Property(
            type = PropertyType.DECIMAL_SLIDER,
            name = "rr",
            category = "Private",
            subcategory = "General",
            description = "Rr.",
            minF = 3.0f,
            maxF = 6.0f,
            decimalPlaces = 2
    )
    private float rechr = 3.0f;

    @Property(
            type = PropertyType.SWITCH,
            name = "HitFix",
            category = "Private",
            subcategory = "General",
            description = "Toggle HitFix."
    )
    private boolean hitfix = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Blidness",
            category = "Private",
            subcategory = "General",
            description = "Toggle antiblidness.",
            hidden = true
    )
    private boolean antiblidness = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "NoFog",
            category = "Private",
            subcategory = "General",
            description = "Toggle antifog."
    )
    private boolean antifog = false;

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
            description = "Toggle Presents Sp. Shift + K to delete founded"
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
            name = "NameTag",
            category = "Private",
            subcategory = "Name",
            description = "Toggle NameTag.",
            hidden = true
    )
    private boolean nameTag = false;

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
            category = "Strings",
            protectedText = true
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
            name = "Cakes",
            description = "Needed.",
            category = "z",
            hidden = true
    )
    private String cakepicked = "";





    public HyConfig(File file) {
        super(file);
        initialize();

        setCategoryDescription("Strings","Some text to be stored in config");
        setCategoryDescription("General","All general features");
        setCategoryDescription("Private","o_0");
        setCategoryDescription("SkyBlock","Features that used only in skyblock");
        setSubcategoryDescription("SkyBlock","Rarity","Draw a circle of item rarity color");
        setSubcategoryDescription("General","Form","Allows you to rename yourself. Colors support &[0-9a-f]{name}");
    }

}

