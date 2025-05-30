package uniquecode.study.frame;

import uniquecode.study.Main;
import uniquecode.study.api.service.Service;
import uniquecode.study.api.service.ServiceInjector;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrameCache implements Service {
    private final Main main;
    private final ServiceInjector serviceInjector;
    private final Map<String, List<JComponent>> componentCache;
    public FrameCache(Main main, ServiceInjector serviceInjector) {
        this.main = main;
        this.serviceInjector = serviceInjector;
        this.componentCache = new HashMap<>();
        this.serviceInjector.register("frame-cache", this);
    }
    @Override
    public void setupCache() {

    }
    public void addComponent(String key, JComponent component) {
        this.componentCache.computeIfAbsent(key, k -> new ArrayList<>()).add(component);
    }
    public List<JComponent> getComponents(String key) {
        return this.componentCache.getOrDefault(key, new ArrayList<>());
    }

}
