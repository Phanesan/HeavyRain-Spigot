package tmlust.heavyrain;

import org.bukkit.scheduler.BukkitRunnable;
import tmlust.heavyrain.threads.LoopTask;
import tmlust.heavyrain.threads.TimerTask;

import java.io.IOException;

public class ThreadManager {

    private HeavyRain instance;
    private BukkitRunnable loopTask;
    private BukkitRunnable timerTask;

    public ThreadManager(HeavyRain instance) {
        this.instance = instance;
    }

    /**
     * Cuando se inicia la Heavy Rain, se ejecuta este metodo.
     * Todas las tareas repetitivas que se hagan durante la heavy rain se
     * realizan en este metodo.
     * NOTA: Este Task no ocupa un metodo para cancelar la Task, se cancela por si mismo
     */
    public void startLoop() {
        loopTask = new LoopTask(instance);
        loopTask.runTaskTimer(instance, 0, 60);
    }

    /**
     * Cancela la task loopTask
     */
    public void stopLoop() {
        loopTask.cancel();
        loopTask = null;
    }

    /**
     * Cuando se llama a este metodo, inicia el temporizador para
     * la siguiente heavy rain. Todas las acciones principales que
     * involucren al temporizador, se realizan en este metodo.
     * NOTA: Es necesario que en algun punto se ocupe cancelar el Task con
     * el metodo stopTimer.
     */
    public void startTimer() {
        timerTask = new TimerTask(instance);
        timerTask.runTaskTimer(instance,0,20);
    }

    /**
     * Cancela la Task timerTask
     */
    public void stopTimer() {
        timerTask.cancel();
        timerTask = null;
        try {
            instance.getData().saveData(instance,"/HeavyRainData.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return true si el timerTask esta activado, false si esta
     * desactivado.
     */
    public boolean isTimerRun() {
        return timerTask != null;
    }

    /**
     * @return true si el loopTask esta activado, false si esta
     * desactivado.
     */
    public boolean isLoopRun() {
        return loopTask != null;
    }

    public BukkitRunnable getLoopTask() {
        return loopTask;
    }

    public BukkitRunnable getTimerTask() {
        return timerTask;
    }
}
