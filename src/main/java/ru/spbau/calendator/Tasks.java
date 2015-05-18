package ru.spbau.calendator;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class Tasks {
    public static void run() throws Exception {
        File F = new File("data/queries/");
        File []files = F.listFiles();
        if (files == null) {
            Main.logger.warning("No json queries found");
        } else {
            Arrays.sort(files);
            for (File file : files) {
                JSONParser parser = new JSONParser();
                FileReader reader = new FileReader(file);
                JSONObject json = (JSONObject) parser.parse(reader);
                make_cal_from_json(json, file.getName());
                reader.close();
            }
        }
    }

    public static void make_cal_from_json(JSONObject json, String file_name) {
        try {
            if (json.containsKey("disabled") && (Boolean) json.get("disabled")) {
                return;
            }
            String type = (String) json.get("type");
            String out_cal_name;
            if (json.containsKey("output_cal_name")) {
                out_cal_name = (String) json.get("output_cal_name");
            } else {
                out_cal_name = file_name + ".ics";
            }
            if (type.equals("get_from_url"))
                Tools.get_cal_from_url((String) json.get("url"), out_cal_name);

            if (type.equals("name_filter"))
                Tools.print_calendar(Tools.filter_by_name((String) json.get("pattern"), Tools.read_calendar((String) json.get("input_cal_name"))),
                        out_cal_name);

            if (type.equals("merge"))
                Tools.print_calendar(Tools.merge_cals(Tools.read_calendar((String) json.get("cal1")), Tools.read_calendar((String) json.get("cal2"))),
                        out_cal_name);

            if (type.equals("long_to_bounds"))
                Tools.print_calendar(Tools.long_events_to_bounds(Tools.read_calendar((String) json.get("input_cal_name")), (Long) json.get("time")),
                        out_cal_name);

            if (type.equals("parse")) {
                Class<?> cls = Class.forName((String) json.get("path"));
                Parser parser = (Parser) cls.newInstance();
                Tools.print_calendar(parser.parse(),
                        out_cal_name);
            }
            if (type.equals("process")) {
                Class<?> cls = Class.forName((String) json.get("path"));
                Processor processor = (Processor) cls.newInstance();
                Tools.print_calendar(processor.process(((JSONArray) json.get("args")).toArray()),
                        out_cal_name);
            }
            if (type.equals("ical")) {
                Object a[] = ((JSONArray) json.get("events")).toArray();
                ArrayList<Set<?>> arr = new ArrayList<Set<?>>();
                for (Object o: a)
                    arr.add(((JSONObject) o).entrySet());
                Tools.print_calendar(Tools.make_simple_ical(((JSONObject) json.get("properties")).entrySet(), arr),
                        out_cal_name);
            }
        } catch (Exception ex) {
            Main.logger.warning("Error in json " + file_name);
        }
    }

}
