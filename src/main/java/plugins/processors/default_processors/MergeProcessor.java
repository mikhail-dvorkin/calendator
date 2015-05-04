package plugins.processors.default_processors;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import ru.spbau.calendator.Processor;
import ru.spbau.calendator.Tools;

public class MergeProcessor implements Processor {
    public Calendar process(Object[] args) throws Exception {
        Calendar cal1 = Tools.read_calendar((String) args[0]);
        Calendar cal2 = Tools.read_calendar((String) args[1]);

        Calendar output_cal = new Calendar();
        output_cal.getProperties().add(new ProdId("-//Calendator//Calendar 1.0//EN"));
        output_cal.getProperties().add(Version.VERSION_2_0);
        output_cal.getProperties().add(CalScale.GREGORIAN);

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
}