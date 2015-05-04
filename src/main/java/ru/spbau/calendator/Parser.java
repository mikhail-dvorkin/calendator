package ru.spbau.calendator;

import net.fortuna.ical4j.model.Calendar;

public interface Parser {
    Calendar parse() throws Exception;
}
