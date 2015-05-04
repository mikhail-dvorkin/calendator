package ru.spbau.calendator;

import net.fortuna.ical4j.model.Calendar;

public interface Processor {
    Calendar process(Object args[]) throws Exception;
}
