package ru.spbau.calendator;


import net.fortuna.ical4j.model.Calendar;

public class Tasks {
    public static void run() throws Exception {
        Tools.get_cal_from_url("https://www.google.com/calendar/ical/appirio.com_bhga3musitat85mhdrng9035jg%40group.calendar.google.com/public/basic.ics", "topcoder.ics");
        Tools.get_cal_from_url("https://www.google.com/calendar/ical/br1o1n70iqgrrbc875vcehacjg%40group.calendar.google.com/public/basic.ics", "codeforces.ics");

        Calendar topcoder = Tools.read_calendar("topcoder.ics");
        Calendar codeforces = Tools.read_calendar("codeforces.ics");
        Calendar srms = Tools.filter_by_name("^[\\s\\S]*SRM[\\s\\S]*$", topcoder);
        Calendar div1 = Tools.filter_by_name("^[\\s\\S]*Div\\. 1[\\s\\S]*$", codeforces);

        Calendar srms_cf_div1 = Tools.merge_cals(srms, div1);
        Tools.print_calendar(srms_cf_div1, "srms&cf_div1.ics");

        Calendar cf_start_end_long_events = Tools.long_events_to_bounds(codeforces, 2*24*60*60*1000);
        Tools.print_calendar(cf_start_end_long_events, "cf_start_end_long_events.ics");
    }


}
