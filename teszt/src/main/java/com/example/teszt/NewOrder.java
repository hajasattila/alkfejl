package com.example.teszt;

import dao.DAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Order;

import java.net.URL;
import java.util.ResourceBundle;

public class NewOrder implements Initializable {
    @FXML private TextField name;
    @FXML private ComboBox<String> title;
    @FXML private ComboBox<Integer> portion;
    @FXML private DatePicker delivery;
    @FXML private ToggleGroup payment;

    public void createOrder(ActionEvent actionEvent) {
        String name = this.name.getText();
        String title = this.title.getValue();
        int portion = this.portion.getValue();
        var delivery = this.delivery.getValue();
        String payment = ((RadioButton)this.payment.getSelectedToggle()).getText();
        System.out.println(name);
        System.out.println(title);
        System.out.println(portion);
        System.out.println(delivery);
        System.out.println(payment);
        try{
            var order = new DAOImpl().save(new Order(name,title,portion,delivery,payment));
            cancel(actionEvent);
        } catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cím");
            alert.setHeaderText("Az ablak tartalom felső header része");
            alert.setContentText("Részletesebb leírás a header text alatt");
            alert.showAndWait();
        }
    }

    public void cancel(ActionEvent actionEvent) {
        HelloApplication.loadFXML("hello-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> days = FXCollections.observableArrayList(
                "Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday");
        title.setItems(days); //értékek beállítása
        title.setValue("Monday");

        Integer[] nums = {1,2,3,4,5,6};
        portion.setItems(FXCollections.observableArrayList(nums)); //értékek beállítása
        portion.setValue(1);
    }
}
