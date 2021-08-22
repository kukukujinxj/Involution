package com.lagou.edu.filter;

import com.lagou.edu.filter.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component // 让该过滤器被扫描到
public class Filter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 从上下文中取出request和response对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 获取连接地址
        String path = request.getURI().getPath();
        if (path.equals("/api/user/page/welcome")) {
            List<HttpCookie> token = request.getCookies().get("token");
            boolean flag = true;
            if (CollectionUtils.isEmpty(token)) {
                flag = false;
            } else {
                HttpCookie httpCookie = token.get(0);
                String value = httpCookie.getValue();
                // 检验token是否正确
                if (!JwtUtil.verify(value)) {
                    flag = false;
                }
            }
            // 根据flag的状态判断是否回到登录页面
            if (!flag) {
                //重定向(redirect)到登录页面
                String url = "/api/user/page/login";
                //303状态码表示由于请求对应的资源存在着另一个URI，应使用GET方法定向获取请求的资源
                response.setStatusCode(HttpStatus.SEE_OTHER);
                response.getHeaders().set(HttpHeaders.LOCATION, url);
                return response.setComplete();
            }
        }
        //如果是/api/user/请求或/api/code/请求放行
        // 合法请求，放行，执行后续的过滤器
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
