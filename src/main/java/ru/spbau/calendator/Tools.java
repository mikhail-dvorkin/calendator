package ru.spbau.calendator;


import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.*;

public class Tools {
    public static Calendar make_default_ical() {
        Calendar output_cal = new Calendar();
        output_cal.getProperties().add(new ProdId("-//Calendator//Calendar 1.0//EN"));
        output_cal.getProperties().add(Version.VERSION_2_0);
        output_cal.getProperties().add(CalScale.GREGORIAN);
        return output_cal;
    }

    public static void get_cal_from_url(String url_name, String output_name) throws Exception{
        URL url = new URL(url_name);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream("data/ical/" + output_name);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        rbc.close();
        fos.close();
    }

    public static Calendar read_calendar(String input_name) throws Exception {
        FileInputStream fin = new FileInputStream("data/ical/" + input_name);
        CalendarBuilder builder = new CalendarBuilder();
        Calendar cal = builder.build(fin);
        fin.close();
        return cal;
    }

    public static void print_calendar(Calendar cal, String output_name) throws Exception {
        FileOutputStream fout = new FileOutputStream("data/ical/" + output_name);
        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(cal, fout);
        fout.close();
    }

    public static Calendar filter_by_name(String p, Calendar input_cal) {
        Calendar output_cal = make_default_ical();

        for (Object o : input_cal.getComponents()) {
            Component component = (Component) o;

            if (component.getName().equals("VEVENT")) {
                if (component.getProperty("SUMMARY").toString().matches(p))
                    output_cal.getComponents().add(component);
            } else
                output_cal.getComponents().add(component);
        }

        return output_cal;
    }

    public static Calendar merge_cals(Calendar cal1, Calendar cal2) {
        Calendar output_cal = make_default_ical();

        for (Object o : cal1.getComponents()) {
            Component component = (Component) o;
            output_cal.getComponents().add(component);
        }
        for (Object o : cal2.getComponents()) {
            Component component = (Component) o;
            output_cal.getComponents().add(component);
        }

        return output_cal;
    }

    public static Calendar long_events_to_bounds(Calendar input_cal, long time_in_seconds) throws Exception {
        Calendar output_cal = make_default_ical();

        for (Object o : input_cal.getComponents()) {
            Component component = (Component) o;

            if (component.getName().equals("VEVENT")) {

                Date start = ((DtStart) component.getProperty(Property.DTSTART)).getDate();
                Date end = ((DtEnd) component.getProperty(Property.DTEND)).getDate();

                if (end.getTime() - start.getTime() < time_in_seconds)
                    output_cal.getComponents().add(component);
                else {
                    Component start_component = component.copy();
                    Component end_component = component.copy();

                    start_component.getProperty("UID").setValue(UUID.randomUUID() + "@" + InetAddress.getLocalHost().getHostName());
                    end_component.getProperty("UID").setValue(UUID.randomUUID() + "@" + InetAddress.getLocalHost().getHostName());
                    start_component.getProperty("SUMMARY").setValue((component.getProperty("SUMMARY")).getValue() + "_start");
                    end_component.getProperty("SUMMARY").setValue((component.getProperty("SUMMARY")).getValue() + "_end");
                    start_component.getProperty("DTEND").setValue(start.toString());
                    end_component.getProperty("DTSTART").setValue(end.toString());

                    output_cal.getComponents().add(start_component);
                    output_cal.getComponents().add(end_component);
                }
            } else
                output_cal.getComponents().add(component);
        }

        return output_cal;
    }

    public static Calendar make_simple_ical(Set<?> keys, ArrayList<Set<?>> events) throws Exception {
        Calendar output_cal = make_default_ical();
        for (Object o: keys) {
            @SuppressWarnings("unchecked")
			Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) o;
            Property property = PropertyFactoryImpl.getInstance().createProperty((String) entry.getKey());
            property.setValue((String) entry.getValue());
            output_cal.getProperties().add(property);
        }

        for (Set<?> s: events) {
            VEvent event = new VEvent();
            Uid uid = new Uid();
            event.getProperties().add(uid);
            event.getProperty("UID").setValue(UUID.randomUUID() + "@" + InetAddress.getLocalHost().getHostName());
            for (Object o: s) {
                @SuppressWarnings("unchecked")
				Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) o;
                Property property = PropertyFactoryImpl.getInstance().createProperty((String) entry.getKey());
                property.setValue((String) entry.getValue());
                event.getProperties().add(property);

            }
            output_cal.getComponents().add(event);
        }


        return output_cal;
    }
}
