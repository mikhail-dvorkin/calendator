package plugins.processors.default_processors;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import ru.spbau.calendator.Processor;
import ru.spbau.calendator.Tools;

public class MergeProcessor implements Processor {
    public Calendar process(Object[] args) throws Exception {
        Calendar cal1 = Tools.read_calendar((String) args[0]);
        Calendar cal2 = Tools.read_calendar((String) args[1]);

        Calendar output_cal = Tools.make_default_ical();

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