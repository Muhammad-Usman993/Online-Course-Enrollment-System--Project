package usman_package;

public class Instructor {
    private String id;    // Instructor ka unique ID
    private String name;  // Instructor ka naam

    public Instructor(String id, String name) {
        this.id = id;      // ID set karna
        this.name = name;  // Naam set karna
    }

    // Getters (zarurat par)
    public String getId() {
        return id;         // ID return karta hai
    }

    public String getName() {
        return name;       // Naam return karta hai
    }
}
