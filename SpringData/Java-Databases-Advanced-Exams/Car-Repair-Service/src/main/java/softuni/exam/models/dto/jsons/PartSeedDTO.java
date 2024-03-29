package softuni.exam.models.dto.jsons;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.*;
import java.io.Serializable;

public class PartSeedDTO implements Serializable {

    @Expose
    @NotNull
    @Size(min = 2, max = 19)
    private String partName;
    @Expose
    @NotNull
    @DecimalMin(value = "10.00", inclusive = true)
    @DecimalMax(value = "2000.00", inclusive = true)
    private double price;
    @Expose
    @NotNull
    @Positive
    private int quantity;

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
