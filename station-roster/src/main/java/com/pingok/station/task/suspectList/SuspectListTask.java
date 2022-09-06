package com.pingok.station.task.suspectList;

import com.pingok.station.mapper.tracer.ListTracerMapper;
import com.pingok.station.service.suspectList.ISuspectListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Slf4j
@Configuration
@EnableScheduling
@Component("suspectListTask")
public class SuspectListTask {
    @Autowired
    private ISuspectListService suspectListService;
    @Autowired
    private ListTracerMapper listTracerMapper;
}
