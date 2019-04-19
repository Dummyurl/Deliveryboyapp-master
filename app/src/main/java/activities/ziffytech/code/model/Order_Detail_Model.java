package activities.ziffytech.code.model;

/**
 * Created by Ziffy on 12/17/2018.
 */

public class Order_Detail_Model
{

    String prescription_id;
    String order_id;
    String address;
    String depature_time;
    String delivery_date;
    String delivery_status;
    String reason;
    String user_signature;
    String medicine_name;
    String strength;
    String quantity;
    String selling_price;
    String amount;
    String delivery_boy_id;



    public String getMedicine_name() {
        return medicine_name;
    }
    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getPrescription_id() {
        return prescription_id;
    }

    public void setPrescription_id(String prescription_id) {
        this.prescription_id = prescription_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public String getDepature_time() {
        return depature_time;
    }

    public void setDepature_time(String depature_time) {
        this.depature_time = depature_time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setSelling_price(String selling_price) {
        this.selling_price = selling_price;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public void setUser_signature(String user_signature) {
        this.user_signature = user_signature;
    }

    public String getUser_signature() {
        return user_signature;
    }

    public String getDelivery_boy_id() {
        return delivery_boy_id;
    }

    public void setDelivery_boy_id(String delivery_boy_id) {
        this.delivery_boy_id = delivery_boy_id;
    }
}
