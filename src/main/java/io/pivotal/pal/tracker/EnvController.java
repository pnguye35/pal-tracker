package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class EnvController {
    private final String port;
    private final String memoryLimit;
    private final String cfInstanceIndex;
    private final String cfInstanceAddr;

    public EnvController(  @Value("${port:NOT SET}") String port,
                           @Value("${memory.limit:NOT SET}") String memoryLimit,
                            @Value("${cf.instance.index:NOT SET}") String cfInstanceIndex,
                            @Value("${cf.instance.addr:NOT SET}") String cfInstanceAddr
) {
        this.port   =   port;
        this.memoryLimit    =   memoryLimit;
        this.cfInstanceIndex    =   cfInstanceIndex;
        this.cfInstanceAddr =   cfInstanceAddr;
    }

    @GetMapping("/env")
    public HashMap<String, String> getEnv( ) {
        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("PORT", port);
        properties.put("MEMORY_LIMIT", memoryLimit);
        properties.put("CF_INSTANCE_INDEX", cfInstanceIndex);
        properties.put("CF_INSTANCE_ADDR", cfInstanceAddr);
        return properties;
    }
}
