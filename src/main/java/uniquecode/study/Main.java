package uniquecode.study;

import lombok.Getter;
import uniquecode.study.api.service.ServiceInjector;
import uniquecode.study.frame.FrameCache;
import uniquecode.study.frame.FrameLoader;
import uniquecode.study.frame.FrameManager;

@Getter
public class Main {
    public static Main INSTANCE; //Singleton instance
    private final ServiceInjector serviceInjector;
    public Main() {
        this.serviceInjector = new ServiceInjector(this);
        this.registerServices();
        this.registerApplication();

    }
    public void registerApplication(){
        new FrameLoader(this);
    }
    public void registerServices(){
        new FrameManager(this, serviceInjector);
        new FrameCache(this, serviceInjector);
        serviceInjector.activate();
    }

    public static void main(String[] args) {
        INSTANCE = new Main();
    }
}