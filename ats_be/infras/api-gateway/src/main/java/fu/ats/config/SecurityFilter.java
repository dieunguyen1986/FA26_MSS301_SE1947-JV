package fu.ats.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class SecurityFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<SecurityContext> filter(ServerWebExchange exchange, GatewayFilterChain chain) {


//        ServerHttpRequest request = exchange.getRequest();// Imutable
//
//        ServerHttpRequest newRequest = request.mutate().header("user", "admin").build();

        return ReactiveSecurityContextHolder.getContext()
                .map((ctx) -> {
                            return ctx.getAuthentication();
                        }
                )
                .filter(auth -> auth instanceof JwtAuthenticationToken)
                .map(auth -> ((JwtAuthenticationToken) auth).getToken())
                .map(jwt -> {
                    ServerHttpRequest request = exchange.getRequest().mutate()
                            .headers(headers -> {
                                // Never let a client forge identity headers
                                headers.remove("X-User-Id");
                                headers.remove("X-User-Email");
                                // Downstream services do not need the token
                                headers.remove(HttpHeaders.AUTHORIZATION);

                                headers.set("X-User-Id", jwt.getSubject());
                                String email = jwt.getClaimAsString("email");
                                if (email != null) {
                                    headers.set("X-User-Email", email);
                                }
                            })
                            .build();
                    return exchange.mutate().request(request).build();
                }).defaultIfEmpty(exchange)
                .flatMap(chain::filter);
    }

    @Override
    public int getOrder() {
        return 200;
    }
}
