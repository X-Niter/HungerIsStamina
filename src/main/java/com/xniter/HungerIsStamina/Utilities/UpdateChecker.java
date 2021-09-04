package com.xniter.HungerIsStamina.Utilities;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UpdateChecker {

    private URL checkURL;
    private String newVersion;
    private final JavaPlugin plugin;

    public UpdateChecker(JavaPlugin plugin, int projectID) {
        this.plugin = plugin;
        this.newVersion = plugin.getDescription().getVersion();
        try {
            this.checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + projectID);
        } catch (MalformedURLException ignored) {
        }
    }

    public String getLatestVersion() {
        return newVersion;
    }

    public boolean checkForUpdates() throws Exception {
        URLConnection con = checkURL.openConnection();

        newVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
        int newVersionValue = Integer.parseInt(newVersion.replace(".", ""));
        int currentVersion = Integer.parseInt(plugin.getDescription().getVersion().split("-")[0].replace(".", ""));

        return newVersionValue > currentVersion;
    }
}
