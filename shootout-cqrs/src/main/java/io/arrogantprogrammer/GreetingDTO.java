package io.arrogantprogrammer;

import java.time.Instant;

public record GreetingDTO(String text, boolean isFamilyFriendly, Instant createdAt) {
}
