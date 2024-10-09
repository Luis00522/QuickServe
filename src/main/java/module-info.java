module com.projecterestaurant {
    requires javafx.controls;
    requires transitive javafx.fxml;
    requires java.sql;

    opens com.projecterestaurant.address.controller to javafx.fxml;
    opens com.projecterestaurant.address.model to javafx.fxml;
    opens com.projecterestaurant.address.enums to javafx.fxml;
    opens com.projecterestaurant to javafx.fxml;

    exports com.projecterestaurant.address.controller;
    exports com.projecterestaurant.address.model;
    exports com.projecterestaurant.address.enums;
    exports com.projecterestaurant;
}
