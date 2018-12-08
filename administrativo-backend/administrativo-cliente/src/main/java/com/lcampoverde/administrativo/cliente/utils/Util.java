package com.lcampoverde.administrativo.cliente.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class Util {
    private Util() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static String getJwtFromRequest(HttpServletRequest request, String tokenHeader, String tokenPrefix) {
        String bearerToken = request.getHeader(tokenHeader);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenPrefix.concat(" "))) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
