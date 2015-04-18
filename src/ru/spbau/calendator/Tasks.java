package ru.spbau.calendator;


public class Tasks {
    public static void run() throws Exception {
        Tools.get_cal_from_url("https://www.google.com/calendar/ical/appirio.com_bhga3musitat85mhdrng9035jg%40group.calendar.google.com/public/basic.ics", "topcoder.ics");
        Tools.get_cal_from_url("https://www.google.com/calendar/ical/br1o1n70iqgrrbc875vcehacjg%40group.calendar.google.com/public/basic.ics", "codeforces.ics");

        Tools.filter_by_name("^[\\s\\S]*SRM[\\s\\S]*$", "topcoder.ics", "srms.ics");
        Tools.filter_by_name("^[\\s\\S]*Div\\. 1[\\s\\S]*$", "codeforces.ics", "cf_div1.ics");

        Tools.merge_cals("srms.ics", "cf_div1.ics", "srms&cf_div1.ics");
    }


}
