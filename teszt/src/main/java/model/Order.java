package model;

import java.security.InvalidParameterException;
import java.time.LocalDate;

public class Order {
    private int ID;
    private String customer;
    private String title;
    private int portion;
    private LocalDate delivery;
    private String payment;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPortion() {
        return portion;
    }

    public void setPortion(int portion) {
        if(portion < 1 || portion > 6){
            throw new InvalidParameterException("Az adag csak 1-6 között lehet");
        }
        this.portion = portion;
    }

    public LocalDate getDelivery() {
        return delivery;
    }

    public void setDelivery(LocalDate delivery) {
        this.delivery = delivery;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Order(String customer, String title, int portion, LocalDate delivery, String payment) {
        this.customer = customer;
        this.title = title;
        this.portion = portion;
        this.delivery = delivery;
        this.payment = payment;
    }

    public Order(int ID, String customer, String title, int portion, LocalDate delivery, String payment) {
        this.ID = ID;
        this.customer = customer;
        this.title = title;
        this.portion = portion;
        this.delivery = delivery;
        this.payment = payment;
    }
}
