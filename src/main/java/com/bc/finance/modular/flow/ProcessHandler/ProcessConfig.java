package com.bc.finance.modular.flow.ProcessHandler;

import com.bc.finance.modular.flow.handler.ProcessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class ProcessConfig {
    @Bean
    public List<ProcessHandler> processHandlerList(List<ProcessHandler> handlerList){
        return handlerList;
    }
}