package ru.spbau.calendator;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;

public class Tasks {
    public static void run() throws Exception {
        File F = new File("data/queries/");
        File []files = F.listFiles();
        for (File file : files) {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(file);
            JSONObject json = (JSONObject) parser.parse(reader);
            make_cal_from_json(json);
            reader.close();
        }
    }

    public static void make_cal_from_json(JSONObject json) throws Exception{
        String type = (String) json.get("type");
        if (type.equals("get_from_url"))
            Tools.get_cal_from_url((String) json.get("url"), (String) json.get("output_cal_name"));

        if (type.equals("name_filter"))
            Tools.print_calendar(Tools.filter_by_name((String) json.get("pattern"), Tools.read_calendar((String) json.get("input_cal_name"))),
                    (String) json.get("output_cal_name"));

        if (type.equals("merge"))
            Tools.print_calendar(Tools.merge_cals(Tools.read_calendar((String) json.get("cal1")), Tools.read_calendar((String) json.get("cal2"))),
                    (String) json.get("output_cal_name"));

        if (type.equals("long_to_bounds"))
            Tools.print_calendar(Tools.long_events_to_bounds(Tools.read_calendar((String) json.get("input_cal_name")), (Long) json.get("time")),
                    (String) json.get("output_cal_name"));
    }

}
