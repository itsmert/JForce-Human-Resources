package JForce.JForce.Domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {
    Admin,
    Human_Resources,
    Inventory_Manager;

    @JsonCreator
    public static UserRole fromString(String key) {
        if (key == null) return null;
        return switch (key.toUpperCase()) {
            case "ADMIN" -> Admin;
            case "HUMAN_RESOURCES", "HUMAN RESOURCES" -> Human_Resources;
            case "INVENTORY_MANAGER", "INVENTORY MANAGER" -> Inventory_Manager;
            default -> throw new IllegalArgumentException("Invalid role: " + key);
        };
    }

    @JsonValue
    public String toJson() {
        return this.name();
    }
}