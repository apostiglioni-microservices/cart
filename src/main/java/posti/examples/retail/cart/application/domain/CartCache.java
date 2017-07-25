package posti.examples.retail.cart.application.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import lombok.NonNull;

public class CartCache {
    private final Map<UUID, Cart> cache = new HashMap<>();

    Optional<Cart> get(@NonNull UUID key) {
        return Optional.ofNullable(cache.get(key));
    }

    void put(@NonNull UUID key, @NonNull Cart cart) {
        Cart current = cache.get(key);
        if (current == null || cart.getVersion() > current.getVersion()) {
            synchronized (cache) {
                Cart locked = cache.get(key);
                if (locked == null || cart.getVersion() > locked.getVersion()) {
                    cache.put(key, cart);
                }
            }
        }
    }
}
