package uniquecode.study.api.service;


import lombok.Getter;
import lombok.Setter;
import uniquecode.study.Main;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ServiceInjector {
    private final Main main;
    private final Map<String, Service> cacheMap;
    public ServiceInjector(Main main) {
        this.main = main;
        this.cacheMap = new HashMap<>();
    }
    public void load(){

    }
    public void register(String name, Service cache){
        this.cacheMap.put(name, cache);
    }
    public Service get(String name){
        return this.cacheMap.get(name);
    }
    public void activate(){
        for(Service cache:this.cacheMap.values()){
            cache.setupCache();
        }
    }

}
