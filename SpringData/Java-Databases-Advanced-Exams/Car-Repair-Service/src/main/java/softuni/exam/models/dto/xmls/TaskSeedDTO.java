package softuni.exam.models.dto.xmls;

import softuni.exam.util.LocalDateTimeAdaptor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskSeedDTO implements Serializable {

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdaptor.class)
    @NotNull
    private LocalDateTime date;
    @XmlElement
    @NotNull
    @Positive
    private BigDecimal price;
    @XmlElement(name = "car")
    private CarDTO car;
    @XmlElement(name = "mechanic")
    private MechanicDTO mechanic;
    @XmlElement(name = "part")
    private PartDTO part;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }

    public MechanicDTO getMechanic() {
        return mechanic;
    }

    public void setMechanic(MechanicDTO mechanic) {
        this.mechanic = mechanic;
    }

    public PartDTO getPart() {
        return part;
    }

    public void setPart(PartDTO part) {
        this.part = part;
    }
}
