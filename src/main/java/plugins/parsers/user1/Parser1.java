package plugins.parsers.user1;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Uid;
import ru.spbau.calendator.Parser;
import ru.spbau.calendator.Tools;

import java.lang.Exception;
import java.net.InetAddress;
import java.util.UUID;

public class Parser1 implements Parser {
    public Calendar parse() throws Exception {
        Calendar output_cal = Tools.make_default_ical();

        VEvent event = new VEvent(new Date(), "test_event");

        Uid uid = new Uid();
        event.getProperties().add(uid);
        event.getProperty("UID").setValue(UUID.randomUUID() + "@" + InetAddress.getLocalHost().getHostName());
        output_cal.getComponents().add(event);

        return output_cal;
    }
}