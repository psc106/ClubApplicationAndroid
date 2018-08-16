package teamproject.com.clubapplication.utils.bus;

import com.squareup.otto.Bus;

public class BusProvider {
    private Bus bus;
    private static BusProvider busProvider;
    private BusProvider() {
        if(this.bus==null) {
            bus = new Bus();
        }
    }

    public static BusProvider getInstance() {
        if(busProvider==null) {
            busProvider = new BusProvider();
        }
        return busProvider;
    }

    public Bus getBus() {
        return bus;
    }
}
