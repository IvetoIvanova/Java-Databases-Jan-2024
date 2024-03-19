package bg.softuni.cardealerexercise.service.dtos.exports;

import jakarta.xml.bind.annotation.*;

import java.io.Serializable;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarAndPartsDTO implements Serializable {

    @XmlAttribute
    private String make;
    @XmlAttribute
    private String model;
    @XmlAttribute(name = "travelled-distance")
    private long travelledDistance;
    @XmlElement(name = "parts")
    private PartRootDTO partRootDTO;

//    @XmlElementWrapper(name = "parts")
//    private List<PartDTO> parts;


    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public PartRootDTO getPartRootDTO() {
        return partRootDTO;
    }

    public void setPartRootDTO(PartRootDTO partRootDTO) {
        this.partRootDTO = partRootDTO;
    }
}
