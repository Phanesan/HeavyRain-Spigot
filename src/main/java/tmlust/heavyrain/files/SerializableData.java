package tmlust.heavyrain.files;

import java.io.Serializable;

public class SerializableData implements Serializable {

    private boolean timerEnabled;
    private long secondsTimer;
    private long secondsMax;
    private boolean HeavyRainActivated;

    public SerializableData(boolean timerEnabled, long secondsTimer, long secondsMax, boolean HeavyRainActivated) {
        this.timerEnabled = timerEnabled;
        this.secondsTimer = secondsTimer;
        this.secondsMax = secondsMax;
        this.HeavyRainActivated = HeavyRainActivated;
    }

    public boolean isTimerEnabled() {
        return timerEnabled;
    }

    public long getSecondsTimer() {
        return secondsTimer;
    }

    public long getSecondsMax() {
        return secondsMax;
    }

    public boolean isHeavyRainActivated() {
        return HeavyRainActivated;
    }
}
