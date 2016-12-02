package ru.loadtest.app.LoadTest.AppCore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Progress implements Runnable {
    public static final Logger logger = LogManager.getLogger(Progress.class.getName());
    private long timeout;

    public Progress(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public void run() {
        long time = 0;
        long step = timeout / 50;
        while (time < timeout) {
            try {
                time += step;
                Thread.sleep(step);
                System.out.print("\rProgress: " + (time * 100) / timeout + " %");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
