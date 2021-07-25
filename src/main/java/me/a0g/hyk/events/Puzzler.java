package me.a0g.hyk.events;

public class Puzzler {

    /*private final HypixelUtils main = HypixelUtils.getInstance();

    int glowx = 181;
    int glowy = 195;
    int glowz = 135;
    boolean solution = false;
    BlockPos bp;

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        String msg = event.message.getUnformattedText() ;
        if(msg.contains("Puzzler")) msg = TextUtils.stripColor( msg );
        if (msg.contains("▶▲Come▲ ◀back◀ ▼▶tomorrow▶▶")) return;
        if (msg.contains("▶▲Wrong▲ ◀block◀ ▼▶try again▶▶")) return;
        if ( msg.startsWith("[NPC] Puzzler: ") && msg.contains("Nice!")) {
            String fdate = new Date().getTime() + 86400000 + "";
            main.getHyConfig().setPuzzler(fdate);
            main.getUtils().sendMessage(main.getHyConfig().getPuzzler());
            main.getHyConfig().markDirty();
            main.getHyConfig().writeData();
            solution = false;
            bp = null;
            return;
        }
        if(msg.contains("[NPC] Puzzler:")){
            BlockPos bpp = new BlockPos(glowx,glowy,glowz);
            if(Minecraft.getMinecraft().theWorld.getBlockState(bpp).getBlock().getRegistryName().contains("minecraft:glowstone")){
                msg = msg.replaceAll(".NPC. Puzzler: ","");
                FMLLog.info(msg);
                int x = glowx;
                int z = glowz;

                for(int i = 0;i<msg.length();i++){
                    int i1 = "▲▼◀▶".indexOf(msg.charAt(i));
                    if (i1 == 0) {
                        z++;
                    }
                    if (i1 == 1) {
                        z--;
                    }
                    if (i1 == 2) {
                        x++;
                    }
                    if (i1 == 3) {
                        x--;
                    }
                }
                solution = true;
                bp = new BlockPos(x, glowy, z);

            }
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event){
        if(bp != null && solution ){

            ChestTest.blockESPBox(bp,0xFF3cff00);

        }
    }*/

}
