package bg.softuni.cardealerexercise.service.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierLocalRootDTO implements Serializable {

    @XmlElement(name = "supplier")
    private List<SupplierLocalDTO> supplierLocalDTOList;

    public List<SupplierLocalDTO> getSupplierLocalDTOList() {
        return supplierLocalDTOList;
    }

    public void setSupplierLocalDTOList(List<SupplierLocalDTO> supplierLocalDTOList) {
        this.supplierLocalDTOList = supplierLocalDTOList;
    }
}
