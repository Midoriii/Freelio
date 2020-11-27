package pv168.freelancer.model;

public class WorkType {
    private long id;
    private String name;
    private double hourlyRate;
    private String description;

    public WorkType(String name, double hourlyRate, String description) {
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.description = description;
    }

    public WorkType(long id, String name, double hourlyRate, String description) {
        this.id = id;
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public double getHourlyRate() { return hourlyRate; }

    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return name;
    }
}
