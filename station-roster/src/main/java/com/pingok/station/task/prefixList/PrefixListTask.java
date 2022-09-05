package com.pingok.station.task.prefixList;

import com.pingok.station.mapper.tracer.ListTracerMapper;
import com.pingok.station.service.prefixList.IPrefixListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Slf4j
@Configuration
@EnableScheduling
@Component("prefixListTask")
public class PrefixListTask {
    @Autowired
    private IPrefixListService prefixListService;
    @Autowired
    private ListTracerMapper listTracerMapper;
}
