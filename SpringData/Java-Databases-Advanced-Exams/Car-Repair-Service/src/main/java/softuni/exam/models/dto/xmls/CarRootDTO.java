package softuni.exam.models.dto.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarRootDTO implements Serializable {

    @XmlElement(name = "car")
    private List<CarSeedDTO> carSeedDTOs;

    public List<CarSeedDTO> getCarSeedDTOs() {
        return carSeedDTOs;
    }

    public void setCarSeedDTOs(List<CarSeedDTO> carSeedDTOs) {
        this.carSeedDTOs = carSeedDTOs;
    }
}
