import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update_Inventory extends Application{
    @Override
    public void start(Stage stage) throws Exception {

        GridPane gp = new GridPane();
        gp.setVgap(12);
        Label Blood_Group_label = new Label("Bloood Group");
        Label Units_Label = new Label("Units");
        ComboBox cb = new ComboBox();
        cb.getItems().add("A+");
        cb.getItems().add("A-");
        cb.getItems().add("B+");
        cb.getItems().add("B-");
        cb.getItems().add("O+");
        cb.getItems().add("O-");
        cb.getItems().add("AB+");
        cb.getItems().add("AB-");

        TextField Units = new TextField();
        gp.add(Blood_Group_label,1,1);
        gp.add(cb,2,1);
        gp.add(Units_Label,1,2);
        gp.add(Units,2,2);
        Button B1 = new Button("Modify");
        Button B3 = new Button("Close");

        gp.add(B1,1,3);
//        gp.add(B2,2,3);
        gp.add(B3,2,3);

        Scene sc = new Scene(gp,300,250);
        sc.getStylesheets().add("style.css");
        stage.setScene(sc);
        stage.setTitle("Update Inventory");
        stage.show();

        B1.setOnAction(e->{
            try{
                String User_name = "root";
                String password = "monkey2$";
                String database_name = "blood_bank";
                String blood_group = "";
                blood_group +=  cb.getValue();
                String query = "Update inventory set Units = "+Units.getText()+" where BloodGroup='"+blood_group+"'";
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + database_name, User_name, password);
                PreparedStatement stmt = con.prepareStatement(query);
//                stmt.setInt(1,Integer.parseInt(AcceptorID.getText()));
//                stmt.setString(2,AcceptorName.getText());
//                stmt.setString(3,AcceptorContact.getText());
//                stmt.setString(4,AcceptorAddress.getText());
//                stmt.setString(5,gender);
//                stmt.setString(6,blood_group);
//                stmt.setInt(7, Integer.parseInt(AcceptorAge.getText()));
//                stmt.setInt(8,Integer.parseInt(AcceptorUnits.getText()));
                int rs = stmt.executeUpdate();

                if(rs>0) {
                    Alert_Box.display("Success","Modified Successfully");
                    cb.setValue(null);
                    Units.clear();
                }

        } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        B3.setOnAction(e->{
            Admin_Access adm = new Admin_Access();
            try {
                adm.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }
}
