package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher extends User {

    private BigDecimal salaryPerHour;
    private String email;
    private Set<Course> coursesSet;

    public Teacher() {
    }

    @Column
    public BigDecimal getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(BigDecimal salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    @Column
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @OneToMany(mappedBy = "teacher")
    public Set<Course> getCoursesSet() {
        return coursesSet;
    }

    public void setCoursesSet(Set<Course> coursesSet) {
        this.coursesSet = coursesSet;
    }
}
