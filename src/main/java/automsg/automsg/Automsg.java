package automsg.automsg;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


public final class Automsg extends JavaPlugin {
    private File f;

    private FileConfiguration data;

    private int compteur = 0;

    @Override
    public void onEnable() {
        System.out.println("Plugin demaré");
        this.f = new File(getDataFolder() + "/config.yml");
        recharger();
        Broadcast();
    }

    public void recharger() {
        saveDefaultConfig();
        saveConfig();
        this.data = (FileConfiguration) YamlConfiguration.loadConfiguration(this.f);
    }

    public String ligneSuivante() {
        if (this.data.getStringList("messages").size() == 0)
            return "";
        if (this.compteur == this.data.getStringList("messages").size())
            this.compteur = 0;
        return ((String)this.data.getStringList("messages").get(this.compteur)).replaceAll("&", "§");
    }

    public void Broadcast() {
        int temps = this.data.getInt("delay") * 20;
        Bukkit.getScheduler().runTaskTimer((Plugin)this, new Runnable() {
            public void run() {
                String message = Automsg.this.ligneSuivante();
                if (message.equalsIgnoreCase(""))
                    return;
                Automsg.this.compteur = Automsg.this.compteur + 1;
                Bukkit.broadcastMessage(Automsg.this.ligneSuivante());
            }
        },  temps, temps);
    }

}
