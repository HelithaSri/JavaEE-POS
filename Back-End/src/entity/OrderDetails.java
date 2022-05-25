package entity;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 3:46 PM
 * @project JavaEE POS Backend
 */

public class OrderDetails {
    private String oid;
    private String itemCode;
    private int qty;
    private double unitPrice;
    private double total;

    public OrderDetails() {
    }

    public OrderDetails(String oid, String itemCode, int qty, double unitPrice, double total) {
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
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
        return "OrderDetails{" +
                "oid='" + oid + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                '}';
    }
}
