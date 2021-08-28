package me.a0g.hyk.core.features;

import me.a0g.hyk.HypixelKentik;
import net.minecraftforge.fml.common.FMLLog;

import java.util.ArrayList;
import java.util.List;

public class BuildBattleHelper {

    private final HypixelKentik main = HypixelKentik.getInstance();

    private String theme;

    public BuildBattleHelper(String theme){
        this.theme = theme.toLowerCase();
        main.getInGameBbhWords().clear();
    }

    public List<String> getWords(){

        List<String> words = new ArrayList<>();
        int themeLength = theme.length();
        boolean themeHasProbels = theme.contains(" ");

        for(String bbhWord : main.getBbhWords() ){
            int bbhWordLength = bbhWord.length();

            boolean bbhHasProbels = bbhWord.contains(" ");

            if(bbhHasProbels != themeHasProbels) continue;

            if(themeLength == bbhWordLength){
               // FMLLog.info(theme + " length");
               /* char[] bbhWordChars = bbhWord.toCharArray();
                for(Character character : bbhWordChars){

                }*/
                String themeNoUnder = theme.replaceAll("[_]","");
                int themeNoUnderLength = themeNoUnder.length();
                int themeIntCounter = 0;

               // FMLLog.info(themeNoUnder + " " + themeNoUnderLength);

                for(int i=0;i<themeLength;i++){
                    char themeChar = theme.charAt(i);
                    char bbhWordChar = bbhWord.charAt(i);

                    if(themeChar != '_' && themeChar == bbhWordChar){

                        themeIntCounter++;
                        if(themeIntCounter == themeNoUnderLength){
                            if(!words.contains(bbhWord)) {
                                // FMLLog.info("added" + bbhWord);
                                words.add(bbhWord);
                            }
                        }

                    }

                }


            }

        }


        return words;
    }
}
