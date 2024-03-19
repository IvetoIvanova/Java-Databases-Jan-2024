package bg.softuni.cardealerexercise.service.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarToyotaRootDTO implements Serializable {

    @XmlElement(name = "car")
    private List<CarToyotaDTO> carToyotaDTOList;

    public List<CarToyotaDTO> getCarToyotaDTOList() {
        return carToyotaDTOList;
    }

    public void setCarToyotaDTOList(List<CarToyotaDTO> carToyotaDTOList) {
        this.carToyotaDTOList = carToyotaDTOList;
    }
}
