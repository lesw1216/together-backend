package LESW.Together.security.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class SecretKeyGenerator {

    private static final String BASE64_BASED_ENCODING_SECRET_KEY = "yib1jso9C1L10AY0qVyIKTYkNyBxCh2AFVF3CKQk8XQ=";

    public static SecretKey getSigningKey() {
        byte[] decodeKey = Decoders.BASE64.decode(BASE64_BASED_ENCODING_SECRET_KEY);
        return Keys.hmacShaKeyFor(decodeKey);
    }
}
