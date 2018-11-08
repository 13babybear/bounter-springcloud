package cn.bounter.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

	/** JWT签名密钥 */
    private static final String JWT_SECRET_KEY = "U2ltb25Mb3ZlU3VzYW4=";
    /** accessToken失效时间，单位'秒' */
    private static final int EXPIRES_IN = 1800;

    /**
     * 生成accessToken
     * @param userId
     * @return
     */
    public static String generateAccessToken(String userId) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(userId)
                .setExpiration(DateUtil.getExpiresIn(EXPIRES_IN))		//30分钟后过期
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY);
        return builder.compact();
    }


    /**
     * 解析JWT，并获取用户ID
     * @param jwt
     * @return
     */
    public static String parseToken(String jwt){
        if (StringUtils.isEmpty(jwt)) {
            return null;
        }

        try {
            Claims claims = Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(jwt).getBody();
            //获取用户身份信息
            return claims.getSubject();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
    
}
