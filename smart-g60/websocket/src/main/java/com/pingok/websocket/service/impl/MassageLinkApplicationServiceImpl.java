package com.pingok.websocket.service.impl;

import com.pingok.websocket.netty.Global;
import com.pingok.websocket.service.MassageLinkApplicationService;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Service
@Slf4j
public class MassageLinkApplicationServiceImpl implements MassageLinkApplicationService {

    @Override
    public boolean sendMassage(String model, String massage) {
        ChannelGroup group = Global.groupMap.get(model);
        boolean tOf;
        if (group != null && group.size() > 0) {
            TextWebSocketFrame txt = new TextWebSocketFrame(massage);
            Global.groupMap.get(model).writeAndFlush(txt);
            tOf=true;
        }else{
            tOf=false;
        }
        return tOf;
    }

    @Override
    public void sendMassage(String massage) {
        TextWebSocketFrame txt = new TextWebSocketFrame(massage);
        Global.group.writeAndFlush(txt);
    }

    @Override
    public void cleanCacle() {
        if (Global.groupMap.size() > 0) {
            Set<String> keyset = Global.groupMap.keySet();
            Set<String> delectkeys = new HashSet<String>();
            for (String key : keyset) {
                if (Global.groupMap.get(key).isEmpty()) {
                    delectkeys.add(key);
                }
            }
            for (String key : delectkeys) {
                Global.groupMap.remove(key);
            }
            log.info("清除无用缓存房间" + delectkeys.toString());
        }
    }
}
