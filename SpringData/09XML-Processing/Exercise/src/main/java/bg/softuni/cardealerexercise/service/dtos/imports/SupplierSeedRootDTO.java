package bg.softuni.cardealerexercise.service.dtos.imports;

import bg.softuni.cardealerexercise.service.dtos.imports.SupplierSeedDTO;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierSeedRootDTO implements Serializable {

    @XmlElement(name = "supplier")
    private List<SupplierSeedDTO> supplierSeedDtoList;

    public List<SupplierSeedDTO> getSupplierSeedDtoList() {
        return supplierSeedDtoList;
    }

    public void setSupplierSeedDtoList(List<SupplierSeedDTO> supplierSeedDtoList) {
        this.supplierSeedDtoList = supplierSeedDtoList;
    }
}
