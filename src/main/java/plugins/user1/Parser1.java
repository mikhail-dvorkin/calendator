package plugins.user1;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import ru.spbau.calendator.Parser;
import java.lang.Exception;
import java.net.InetAddress;
import java.util.UUID;

public class Parser1 implements Parser {
    public Calendar parse() throws Exception {
        Calendar output_cal = new Calendar();
        output_cal.getProperties().add(new ProdId("-//Calendator//Calendar 1.0//EN"));
        output_cal.getProperties().add(Version.VERSION_2_0);
        output_cal.getProperties().add(CalScale.GREGORIAN);

        VEvent event = new VEvent(new Date(), "test_event");

        Uid uid = new Uid();
        event.getProperties().add(uid);
        event.getProperty("UID").setValue(UUID.randomUUID() + "@" + InetAddress.getLocalHost().getHostName());
        output_cal.getComponents().add(event);

        return output_cal;
    }
}