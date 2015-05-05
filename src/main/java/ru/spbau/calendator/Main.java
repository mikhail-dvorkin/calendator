package ru.spbau.calendator;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    public static Logger logger;
    static {
        try {
            File file = new File("log/");
            if (!file.isDirectory())
                file.delete();
            file.mkdir();
            FileHandler fh = new FileHandler("log/all_log.log", true);
            fh.setFormatter(new SimpleFormatter());
            logger = Logger.getLogger("Logger");
            logger.addHandler(fh);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        File file = new File("data/ical");
        if (!file.isDirectory())
            file.delete();
        file.mkdirs();

        while (true) {
            try {
                Tasks.run();
                logger.info("Calendars updated");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            TimeUnit.MINUTES.sleep(1);
        }
    }

}
