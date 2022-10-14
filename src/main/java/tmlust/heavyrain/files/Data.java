package tmlust.heavyrain.files;

import org.bukkit.Bukkit;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Data implements Serializable {

    private static transient final long serialVersionUID = 1L;
    public boolean counterOn;
    public long secondsTimer;
    public long secondsMax;

    public Data(boolean counterOn, long secondsTimer, long secondsMax){
        this.counterOn = counterOn;
        this.secondsTimer = secondsTimer;
        this.secondsMax = secondsMax;
    }

    public boolean saveData(String filePath){
        try {
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(Bukkit.getServer().getPluginManager().getPlugin("HeavyRain").getDataFolder().getAbsolutePath() + "/" + filePath)));
            out.writeObject(this);
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Data loadData(String filePath) {
        try {
            BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(Bukkit.getServer().getPluginManager().getPlugin("HeavyRain").getDataFolder().getAbsolutePath() + "/"  + filePath)));
            Data data = (Data) in.readObject();
            in.close();
            return data;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
