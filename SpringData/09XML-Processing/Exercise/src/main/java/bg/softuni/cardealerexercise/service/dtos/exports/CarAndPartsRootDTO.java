package bg.softuni.cardealerexercise.service.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarAndPartsRootDTO implements Serializable {

    @XmlElement(name = "car")
    private List<CarAndPartsDTO> carAndPartsDTOList;

    public List<CarAndPartsDTO> getCarAndPartsDTOList() {
        return carAndPartsDTOList;
    }

    public void setCarAndPartsDTOList(List<CarAndPartsDTO> carAndPartsDTOList) {
        this.carAndPartsDTOList = carAndPartsDTOList;
    }
}
