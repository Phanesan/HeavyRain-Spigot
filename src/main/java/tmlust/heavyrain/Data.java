package tmlust.heavyrain;

import com.google.gson.Gson;
import tmlust.heavyrain.files.SerializableData;

import java.io.*;


public class Data implements Serializable {

    private static final long serialVersionUID = -2799314134072944951L;
    private HeavyRain instance;
    private boolean timerEnabled;
    private long secondsTimer;
    private long secondsMax;
    private boolean HeavyRainActivated;

    public Data(HeavyRain instance){
        this.instance = instance;
    }

    /**
     * Guarda los datos del plugin
     * @param instance Instancia del plugin
     * @param filePath Direccion donde se guardara el archivo
     * @throws IOException cuando no encuentre el archivo
     */
    protected void saveData(HeavyRain instance, String filePath) throws IOException {
        Gson gson = new Gson();
        SerializableData data = new SerializableData(timerEnabled,secondsTimer,secondsMax,HeavyRainActivated);
        File file = new File(instance.getDataFolder().getAbsolutePath() + filePath);
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson.toJson(data, writer);
        writer.flush();
        writer.close();
    }

    /**
     * Carga los datos del plugin a partir de un archivo JSON
     * @param instance Instancia del plugin
     * @param filePath Direccion en donde se encuentra el archivo de datos JSON
     * @return Un objeto de tipo {@link SerializableData} donde se almacena
     * los datos del plugin
     * @throws FileNotFoundException Si no encuentra el archivo
     */
    protected static SerializableData loadData(HeavyRain instance, String filePath) throws FileNotFoundException {
        Gson gson = new Gson();
        File file = new File(instance.getDataFolder().getAbsolutePath() + filePath);
        Reader reader = new FileReader(file);
        return gson.fromJson(reader, SerializableData.class);
    }

    /**
     * Inserta los datos a partir de una clase {@link SerializableData}
     * @param data el objeto donde se encuentran los datos
     */
    protected void insertData(SerializableData data) {
        this.timerEnabled = data.isTimerEnabled();
        this.secondsTimer = data.getSecondsTimer();
        this.secondsMax = data.getSecondsMax();
        this.HeavyRainActivated = data.isHeavyRainActivated();
    }

    /**
     * Inserta los datos por defecto
     */
    protected void createDataDefault() {
        this.timerEnabled = true;
        this.secondsTimer = 1;
        this.secondsMax = 28800;
        this.HeavyRainActivated = false;
    }

    /**
     * Al llamar a este metodo, se activaran los eventos del objeto
     * {@link SerializableData} que necesiten activarse.
     */
    protected void applyData() {
        if(instance.getData().isHeavyRainActivated())
            instance.getThreadManager().startLoop();
        if(instance.getData().isTimerEnabled()) {
            instance.getThreadManager().startTimer();
        }
    }

    /**
     * @return true si el temporizador esta activado, false si
     * esta desactivado.
     */
    public boolean isTimerEnabled() {
        return timerEnabled;
    }

    /**
     * Cambia el estado del temporizador
     * @param timerEnabled true para activarlo, false para desactivarlo
     */
    public void setTimerEnabled(boolean timerEnabled) {
        this.timerEnabled = timerEnabled;
    }

    /**
     * Obten el tiempo del temporizador
     * @return el tiempo en segundos que lleva el temporizador para la
     * heavy rain
     */
    public long getSecondsTimer() {
        return secondsTimer;
    }

    /**
     * Añade segundos al Timer
     * @param secondsTimer Tiempo en segundos que quieres añadir al Timer
     */
    public void addSecondsTimer(long secondsTimer) {
        this.secondsTimer += secondsTimer;
    }

    /**
     * Cambia el valor de los segundos al Timer
     * @param secondsTimer Tiempo en segundos que quieres cambiar al Timer
     */
    public void setSecondsTimer(long secondsTimer) {
        this.secondsTimer = secondsTimer;
    }

    /**
     * Obten el tiempo maximo del temporizador
     * @return Los segundos que hara activar la heavy rain
     */
    public long getSecondsMax() {
        return secondsMax;
    }

    /**
     * Cambia el valor de los segundos maximos para activar
     * la heavy rain
     * @param secondsMax Tiempo en segundos que quieres cambiar al tiempo maximo
     */
    public void setSecondsMax(long secondsMax) {
        this.secondsMax = secondsMax;
    }

    /**
     * @return true si la Heavy Rain esta activada, false
     * si esta desactivada
     */
    public boolean isHeavyRainActivated() {
        return HeavyRainActivated;
    }

    /**
     * Cambia el estado de la Heavy Rain
     * @param heavyRainActivated true para activarlo, false para desactivarlo
     */
    public void setHeavyRainActivated(boolean heavyRainActivated) {
        HeavyRainActivated = heavyRainActivated;
    }
}
