package me.a0g.hyk.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class APIHandler {
	public static JsonObject getResponse(String urlString) {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String input;
				StringBuffer response = new StringBuffer();
				
				while ((input = in.readLine()) != null) {
					response.append(input);
				}
				in.close();
				
				Gson gson = new Gson();
				JsonObject object = gson.fromJson(response.toString(), JsonObject.class);
				
				return object;
			} else {
			//	player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Request failed. HTTP Error Code: " + conn.getResponseCode()));
			}
		} catch (MalformedURLException ex) {
		//	player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "An error has occured. See logs for more details."));
			System.err.println(ex);
		} catch (IOException ex) {
		//	player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "An error has occured. See logs for more details."));
			System.err.println(ex);
		}
		
		return new JsonObject();
	}
	
	// Only used for UUID => Username
	public static JsonArray getArrayResponse(String urlString) {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String input;
				StringBuffer response = new StringBuffer();
				
				while ((input = in.readLine()) != null) {
					response.append(input);
				}
				in.close();
				
				Gson gson = new Gson();
				JsonArray array = gson.fromJson(response.toString(), JsonArray.class);
				
				return array;
			} else {
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Request failed. HTTP Error Code: " + conn.getResponseCode()));
			}
		} catch (MalformedURLException ex) {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "An error has occured. See logs for more details."));
			System.err.println(ex);
		} catch (IOException ex) {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "An error has occured. See logs for more details."));
			System.err.println(ex);
		}
		
		return new JsonArray();
	}
	
	public static String getUUID(String username) {
		Gson gson = new Gson();
		
		JsonObject uuidResponse = getResponse("https://api.mojang.com/users/profiles/minecraft/" + username);
		String UUID = uuidResponse.get("id").getAsString();
		return UUID;
	}
	
	public static String getLatestProfileID(String UUID, String key) {
		Gson gson = new Gson();
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		// Get profiles
		System.out.println("Fetching profiles...");
		
		JsonObject profilesResponse = getResponse("https://api.hypixel.net/skyblock/profiles?uuid=" + UUID + "&key=" + key);
		if (!profilesResponse.get("success").getAsBoolean()) {
			String reason = profilesResponse.get("cause").getAsString();
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Failed with reason: " + reason));
			return null;
		}
		if (profilesResponse.get("profiles").isJsonNull()) {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "This player doesn't appear to have played SkyBlock."));
			return null;
		}
		
		// Loop through profiles to find latest
		System.out.println("Looping through profiles...");
		String latestProfile = "";
		long latestSave = 0;
		JsonArray profilesArray = profilesResponse.get("profiles").getAsJsonArray();
		
		for (JsonElement profile : profilesArray) {
			JsonObject profileJSON = profile.getAsJsonObject();
			long profileLastSave = 1;
			if (profileJSON.get("members").getAsJsonObject().get(UUID).getAsJsonObject().has("last_save")) {
				profileLastSave = profileJSON.get("members").getAsJsonObject().get(UUID).getAsJsonObject().get("last_save").getAsLong();
			}
			
			if (profileLastSave > latestSave) {
				latestProfile = profileJSON.get("profile_id").getAsString();
				latestSave = profileLastSave;
			}
		}
		
		return latestProfile;
	}

	public URLConnection prepareConnection(String url) throws IOException {
		URLConnection urlConnection = (new URL(url)).openConnection();
		if (urlConnection instanceof HttpURLConnection) {
			HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setUseCaches(true);
			httpURLConnection.setReadTimeout(3000);
			httpURLConnection.setReadTimeout(3000);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Essential Initializer)");
		}
		return urlConnection;
	}

	public boolean downloadFile(String url, Path target) {
		try {
			URLConnection connection = prepareConnection(url);
			Files.copy(connection.getInputStream(), target, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
		} catch (IOException e) {
			//LOGGER.error("Error occurred when downloading file '{}'.", new Object[] { meta.url, e });
			return false;
		}
		//String actualHash = getChecksum(target);
		//if (!meta.checksum.equals(actualHash)) {
			//LOGGER.warn("Downloaded Essential file checksum did not match what we expected (actual={}, expected={}", new Object[] { actualHash, meta.checksum });
		//	return false;
		//}
		return true;
	}
}
