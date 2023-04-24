package com.ambisiss.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtils {

    /**
     * 秘钥
     */
    private String secret;

    /**
     * 有效时间
     */
    private Long expire;

    /**
     * 用户凭证
     */
    private String header;

    /**
     * 签发者
     */
    private String issuer;

    /**
     * 生成token签名
     *
     * @param subject 用户ID（唯一）
     * @return
     */
    public String createToken(Long subject) {
        Date now = new Date();
        // 过期时间
        Date expireDate = new Date(now.getTime() + expire * 1000);

        //创建Signature SecretKey
        final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        //header参数
        final Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("alg", "HS256");
        headerMap.put("typ", "JWT");

        //生成token
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(String.valueOf(subject))
//                .setIssuedAt(nowDate)
//                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        log.info("JWT[" + token + "]");
        return token;
    }

    /**
     * 解析token
     *
     * @param token token
     * @return
     */
    public Claims parseToken(String token) {

        Claims claims = null;
        try {
            //创建Signature SecretKey
            final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

            claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            log.info("Parse JWT token success");
        } catch (JwtException e) {
            log.info("Parse JWT errror " + e.getMessage());
            return null;
        }
        return claims;
    }

    /**
     * 通过token获取用户ID
     *
     * @param token
     * @return
     */
    public Long getUserIdFromToken(String token) {
        return Long.valueOf(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject());
    }

    /**
     * 判断token是否过期
     *
     * @param expiration
     * @return
     */
    public boolean isExpired(Date expiration) {
        return expiration.before(new Date());
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * 用于其他地方获取Header配置信息
     *
     * @return
     */
    public String getHeader() {
        return header;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}