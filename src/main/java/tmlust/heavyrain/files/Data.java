package tmlust.heavyrain.files;

import com.google.gson.Gson;
import tmlust.heavyrain.HeavyRain;

import java.io.*;

public class Data implements Serializable {

    private transient final long serialVersionUID = -5971835126318915170L;
    public boolean counterEnabled;
    public long secondsTimer;
    public long secondsMax;
    public boolean HeavyRainActivated;

    public Data(boolean counterEnabled, long secondsTimer, long secondsMax, boolean HeavyRainActivated){
        this.counterEnabled = counterEnabled;
        this.secondsTimer = secondsTimer;
        this.secondsMax = secondsMax;
        this.HeavyRainActivated = HeavyRainActivated;
    }

    public void saveData(HeavyRain instance, String filePath) throws IOException {
        Gson gson = new Gson();
        File file = new File(instance.getDataFolder().getAbsolutePath() + filePath);
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson.toJson(this, writer);
        writer.flush();
        writer.close();
    }

    public static Data loadData(HeavyRain instance, String filePath) throws FileNotFoundException {
        Gson gson = new Gson();
        File file = new File(instance.getDataFolder().getAbsolutePath() + filePath);
        Reader reader = new FileReader(file);
        return gson.fromJson(reader, Data.class);
    }

}
