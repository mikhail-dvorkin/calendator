package ru.spbau.calendator;

import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) throws Exception {
        while (true) {
            Tasks.run();
            //System.out.println("UPDATED");
            TimeUnit.MINUTES.sleep(1);
        }
	}

}
