package pv168.freelancer.model;

import java.math.BigDecimal;
import java.util.Objects;


/**
 * --Description here--
 *
 * @author xparoul
 */
public class WorkType {
    private Long id;
    private String name;
    private BigDecimal hourlyRate;
    private String description;

    public WorkType(String name, BigDecimal hourlyRate, String description) {
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.description = description;
    }

    public WorkType(Long id, String name, BigDecimal hourlyRate, String description) {
        this.id = id;
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public BigDecimal getHourlyRate() { return hourlyRate; }

    public void setHourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkType workType = (WorkType) o;
        return Objects.equals(id, workType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name;
    }
}
