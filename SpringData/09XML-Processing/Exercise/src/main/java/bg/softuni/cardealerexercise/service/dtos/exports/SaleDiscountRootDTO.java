package bg.softuni.cardealerexercise.service.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleDiscountRootDTO implements Serializable {

    @XmlElement(name = "sale")
    private List<SaleDiscountDTO> saleDiscountDTOList;

    public List<SaleDiscountDTO> getSaleDiscountDTOList() {
        return saleDiscountDTOList;
    }

    public void setSaleDiscountDTOList(List<SaleDiscountDTO> saleDiscountDTOList) {
        this.saleDiscountDTOList = saleDiscountDTOList;
    }
}
