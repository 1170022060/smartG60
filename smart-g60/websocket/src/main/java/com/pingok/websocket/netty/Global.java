/**
 * <p>Title: Global.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: www.7ebit.com</p>
 *
 * @author danwenjin
 * @date 2018年4月26日
 * @version 1.0
 */
package com.pingok.websocket.netty;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * <p>Title: Global</p>
 * <p>Description: </p>
 * @author lvjunhua
 * @date 2020/11/19
 */
public class Global {
    public static Map<String, ChannelGroup> groupMap = new ConcurrentHashMap<String, ChannelGroup>();
    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
