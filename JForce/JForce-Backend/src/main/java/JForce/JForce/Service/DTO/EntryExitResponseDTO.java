package JForce.JForce.Service.DTO;

import java.time.LocalDate;

public class EntryExitResponseDTO {

    private Integer id;
    private String name;
    private String surname;
    private String username;
    private LocalDate entryDate;
    private String position;
    private String title;
    private LocalDate exitDate;
    private String exitReason;

    // Constructor
    public EntryExitResponseDTO(Integer id, String name, String surname, String username,
                                LocalDate entryDate, String position, String title,
                                LocalDate exitDate, String exitReason) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.entryDate = entryDate;
        this.position = position;
        this.title = title;
        this.exitDate = exitDate;
        this.exitReason = exitReason;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public LocalDate getEntryDate() { return entryDate; }
    public void setEntryDate(LocalDate entryDate) { this.entryDate = entryDate; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getExitDate() { return exitDate; }
    public void setExitDate(LocalDate exitDate) { this.exitDate = exitDate; }

    public String getExitReason() { return exitReason; }
    public void setExitReason(String exitReason) { this.exitReason = exitReason; }
}
