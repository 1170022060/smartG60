package com.ruoyi.job.task.kafka;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * kafka定时任务调度
 *
 * @author qiumin
 */
@Slf4j
@Component("kafkaTask")
public class KafkaTask {
    @Autowired
    private RemoteKafkaService remoteKafkaService;

    public void repeat() {
        R<List<KafkaEnum>> r = remoteKafkaService.findAll();
        if (r.getCode() == 200) {
            if (r.getData() != null) {
                log.info("kafkaTask定时任务repeat，开始执行，待处理数据：" + r.getData().size());
                List<KafkaEnum> list = r.getData();
                R send;
                for (KafkaEnum t : list) {
                    send = remoteKafkaService.send(t);
                    if (send.getCode() == 200) {
                        remoteKafkaService.delete(t.getId());
                    }
                }
            }
        }
    }
}
