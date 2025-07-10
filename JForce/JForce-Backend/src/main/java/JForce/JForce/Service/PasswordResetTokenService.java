package JForce.JForce.Service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PasswordResetTokenService {

    private static class TokenInfo {
        String username;
        long createdAt; // timestamp

        TokenInfo(String username, long createdAt) {
            this.username = username;
            this.createdAt = createdAt;
        }
    }

    private final Map<String, TokenInfo> tokenStore = new HashMap<>();
    private final Map<String, Long> lastRequestTime = new HashMap<>();
    private final long tokenValidityMillis = 15 * 60 * 1000;
    private final long dailyLimitMillis = 24 * 60 * 60 * 1000;

    public String createPasswordResetToken(String username) {
        long now = System.currentTimeMillis();

        // Check if user already requested today
        if (lastRequestTime.containsKey(username)) {
            long lastRequest = lastRequestTime.get(username);
            if (now - lastRequest < dailyLimitMillis) {
                throw new RuntimeException("You can only request one reset per day.");
            }
        }

        // Create new token
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, new TokenInfo(username, now));
        lastRequestTime.put(username, now);

        return token;
    }

    public String getUsernameFromToken(String token) {
        TokenInfo info = tokenStore.get(token);
        if (info == null) return null;

        // Check expiry
        long now = System.currentTimeMillis();
        if (now - info.createdAt > tokenValidityMillis) {
            tokenStore.remove(token); // invalidate
            return null;
        }

        return info.username;
    }

    public boolean invalidateToken(String token) {
        return tokenStore.remove(token) != null;
    }
}
