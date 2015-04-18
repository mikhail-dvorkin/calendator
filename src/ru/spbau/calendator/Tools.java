package ru.spbau.calendator;


import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Tools {
    public static void get_cal_from_url(String url_name, String file_name) throws Exception{
        URL url = new URL(url_name);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream("data/" + file_name);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
    }

    public static Calendar read_calendar(String input_name) throws Exception {
        FileInputStream fin = new FileInputStream("data/" + input_name);
        CalendarBuilder builder = new CalendarBuilder();
        Calendar cal = builder.build(fin);
        fin.close();
        return cal;
    }

    public static void print_calendar(Calendar cal, String output_name) throws Exception {
        FileOutputStream fout = new FileOutputStream("data/" + output_name);
        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(cal, fout);
        fout.close();
    }

    public static void filter_by_name(String p, String input_name, String output_name) throws Exception {
        Calendar input_calendar = read_calendar(input_name);

        Calendar output_calendar = new Calendar();
        output_calendar.getProperties().add(new ProdId("-//Calendator//Calendar 1.0//EN"));
        output_calendar.getProperties().add(Version.VERSION_2_0);
        output_calendar.getProperties().add(CalScale.GREGORIAN);

        for (Object o : input_calendar.getComponents()) {
            Component component = (Component) o;

            if (component.getName().equals("VEVENT")) {
                if (component.getProperty("SUMMARY").toString().matches(p))
                    output_calendar.getComponents().add(component);
            } else
                output_calendar.getComponents().add(component);
        }

        print_calendar(output_calendar, output_name);
    }

    public static void merge_cals(String name1, String name2, String output_name) throws Exception {
        Calendar cal1 = read_calendar(name1);
        Calendar cal2 = read_calendar(name2);

        Calendar output_calendar = new Calendar();
        output_calendar.getProperties().add(new ProdId("-//Calendator//Calendar 1.0//EN"));
        output_calendar.getProperties().add(Version.VERSION_2_0);
        output_calendar.getProperties().add(CalScale.GREGORIAN);

        for (Object o : cal1.getComponents()) {
            Component component = (Component) o;
            output_calendar.getComponents().add(component);
        }
        for (Object o : cal2.getComponents()) {
            Component component = (Component) o;
            output_calendar.getComponents().add(component);
        }

        print_calendar(output_calendar, output_name);
    }
}
