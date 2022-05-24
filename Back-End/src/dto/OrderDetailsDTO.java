package dto;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:38 PM
 * @project JavaEE POS Backend
 */

public class OrderDetailsDTO {
    private String oid;
    private String itemCode;
    private int qty;
    private int unitPrice;
    private double total;

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(String oid, String itemCode, int qty, int unitPrice, double total) {
        this.setOid(oid);
        this.setItemCode(itemCode);
        this.setQty(qty);
        this.setUnitPrice(unitPrice);
        this.setTotal(total);
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetailsDTO{" +
                "oid='" + oid + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                '}';
    }
}
