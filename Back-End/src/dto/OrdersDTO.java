package dto;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:38 PM
 * @project JavaEE POS Backend
 */

public class OrdersDTO {
    private String oid;
    private String date;
    private String customerID;
    private int discount;
    private double total;
    private double subTotal;

    public OrdersDTO() {
    }

    public OrdersDTO(String oid, String date, String customerID, int discount, double total, double subTotal) {
        this.setOid(oid);
        this.setDate(date);
        this.setCustomerID(customerID);
        this.setDiscount(discount);
        this.setTotal(total);
        this.setSubTotal(subTotal);
    }


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public String toString() {
        return "OrdersDTO{" +
                "oid='" + oid + '\'' +
                ", date='" + date + '\'' +
                ", customerID='" + customerID + '\'' +
                ", discount=" + discount +
                ", total=" + total +
                ", subTotal=" + subTotal +
                '}';
    }
}
