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

}
