package com.lagou.edu.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ip防爆刷filter
 */
@Component
@RefreshScope
public class IpRequestGlobalFilter implements GlobalFilter, Ordered {

    @Value("${config.request.apis}")
    private String apiPath;

    @Value("${config.request.intervalMinutes}")
    private int intervalMinutes;

    @Value("${config.request.maxRequestCount}")
    private int maxRequestCount;


    /**
     * String为key，存储对应的ip
     * List<Long>为value，存储多次次请求的时间
     */
    private static ConcurrentHashMap<String, List<Long>> ipRegistryMap = new ConcurrentHashMap<>();

    private static List<Long> ipRegistryList = new ArrayList<>();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 从上下文中取出request和response对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 从request对象中获取客户端ip
        String clientIp = request.getRemoteAddress().getHostString();
        String path = request.getURI().getPath();
        if (path.startsWith(apiPath)) {
            //当前时间往前推1分钟
            Long currentTime = System.currentTimeMillis();
            Long beforeOneMinuteTime = currentTime - intervalMinutes * 60 * 1000;
            List<Long> list = ipRegistryMap.get(clientIp);
            Integer count = 0;
            if (list != null && list.size() > 0) {
                for (Long time : list) {
                    if (beforeOneMinuteTime <= time && time <= currentTime) {
                        count++;
                    }
                }
            }
            if (count > maxRequestCount) {
                // 状态码-303
                response.setStatusCode(HttpStatus.SEE_OTHER);
                String data = "您频繁进⾏注册，请求已被拒绝！";
                DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
                return response.writeWith(Mono.just(wrap));
            }

            ipRegistryList.add(System.currentTimeMillis());
            System.out.println("=====添加ip请求时间戳，ipRegistryList=" + ipRegistryList.size() + "=======");
            ipRegistryMap.put(clientIp, ipRegistryList);
        }
        // 合法请求，放行，执行后续的过滤器
        return chain.filter(exchange);
    }

    /**
     * 返回值表示当前过滤器的顺序(优先级)，数值越小，优先级越高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
