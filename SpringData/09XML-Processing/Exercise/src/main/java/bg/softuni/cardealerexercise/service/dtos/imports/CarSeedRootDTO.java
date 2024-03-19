package bg.softuni.cardealerexercise.service.dtos.imports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarSeedRootDTO implements Serializable {

    @XmlElement(name = "car")
    private List<CarSeedDTO> carSeedDTOList;

    public List<CarSeedDTO> getCarSeedDTOList() {
        return carSeedDTOList;
    }

    public void setCarSeedDTOList(List<CarSeedDTO> carSeedDTOList) {
        this.carSeedDTOList = carSeedDTOList;
    }
}
