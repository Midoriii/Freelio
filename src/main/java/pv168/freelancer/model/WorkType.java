package pv168.freelancer.model;

public class WorkType {
    private String name;
    private double hourlyRate;
    private String description;

    public WorkType(){}

    public WorkType(String name, double hourlyRate, String description) {
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.description = description;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public double getHourlyRate() { return hourlyRate; }

    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
