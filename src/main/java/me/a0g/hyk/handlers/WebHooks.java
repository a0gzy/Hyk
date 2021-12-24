package me.a0g.hyk.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebHooks {

    private static final boolean debug = false;

    private static String standartWebhook = "https://discord.com/api/webhooks/790534376613347350/gSNOTzlYo3poM_u-GvpC8DFIXVQ0mdHZowIK7mrys4L8duz2Gv70lsmVjql_l8FIwDB3";

    public static void sendData(String toSend) throws IOException {
        //toSend = "```" + toSend + "```";
        URL obj = new URL(standartWebhook);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        String POST_PARAMS = "{ \"username\": \"KTO ia?\", \"avatar_url\": \"https://i.imgur.com/oBPXx0D.png\", \"content\": \""
                + toSend + "\" }";

        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();

        int resp = con.getResponseCode();
        if(debug) System.out.println(resp);

        if(resp==429) {
            try {
                Thread.sleep(5000);
                sendData(toSend,standartWebhook);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public static void sendData(String toSend,String webhook) throws IOException {
        //toSend = "```" + toSend + "```";
        URL obj = new URL(webhook);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        String POST_PARAMS = "{ \"username\": \"KTO ia?\", \"avatar_url\": \"https://i.imgur.com/oBPXx0D.png\", \"content\": \""
                + toSend + "\" }";

        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();

        int resp = con.getResponseCode();
        if(debug) System.out.println(resp);

        if(resp==429) {
            try {
                Thread.sleep(5000);
                sendData(toSend,webhook);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
