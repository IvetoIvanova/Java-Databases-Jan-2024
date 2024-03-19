package bg.softuni.cardealerexercise.service.dtos.imports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerSeedRootDTO implements Serializable {

    @XmlElement(name = "customer")
    private List<CustomerSeedDTO> customerSeedDTOList;

    public List<CustomerSeedDTO> getCustomerSeedDTOList() {
        return customerSeedDTOList;
    }

    public void setCustomerSeedDTOList(List<CustomerSeedDTO> customerSeedDTOList) {
        this.customerSeedDTOList = customerSeedDTOList;
    }
}
