import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class Show_Inventory_Report  extends Application{

    @Override
    public void start(Stage tableStage) throws Exception
    {
        TableColumn<Blood_Inventory, String> BloodGorup = new TableColumn<>("BloodGroup");
        BloodGorup.setMinWidth(100);
        BloodGorup.setCellValueFactory(new PropertyValueFactory<>("BloodGroup"));

        TableColumn<Blood_Inventory, Integer> units = new TableColumn<>("units");
        units.setMinWidth(100);
        units.setCellValueFactory(new PropertyValueFactory<>("units"));

        TableView<Blood_Inventory> table;
        table = new TableView<>();
        table.setItems(getReport());
        table.getColumns().addAll(BloodGorup,units);

        VBox vBox = new VBox();
        Button close = new Button("Close");
        vBox.getChildren().addAll(table,close);
        close.setOnAction(e->{
            Admin_Access adm = new Admin_Access();
            try {
                adm.start(tableStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        tableStage.setTitle("Blood Inventory Report");
        Scene scene = new Scene(vBox);
        tableStage.setScene(scene);
        tableStage.show();
    }

    public ObservableList<Blood_Inventory> getReport()

    {
        String  User_name = "root";
        String password = "monkey2$";
        String database_name = "blood_bank";
        ObservableList<Blood_Inventory> report = FXCollections.observableArrayList();

        try
        {
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/"+database_name,User_name,password);
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from inventory");


            while (rs.next())
            {
                report.add(new Blood_Inventory(rs.getString(1),rs.getInt(2)));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return report;
    }
}