package uniquecode.study.frame;

import uniquecode.study.Main;
import uniquecode.study.api.service.Service;
import uniquecode.study.api.service.ServiceInjector;

public class FrameManager implements Service {
    private final Main main;
    private final ServiceInjector serviceInjector;
    public FrameManager(Main main, ServiceInjector serviceInjector) {
        this.main = main;
        this.serviceInjector = serviceInjector;
        this.serviceInjector.register("frame-manager", this);
    }
    @Override
    public void setupCache() {

    }

}
