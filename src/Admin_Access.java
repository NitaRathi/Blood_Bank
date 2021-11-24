import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Admin_Access extends Application{

    @Override
    public void start(Stage stage) throws Exception {
//        Label greeting = new Label("Welcome, ");
        HBox options = new HBox();
        options.setPadding(new Insets(15, 12, 15, 12));
        options.setSpacing(10);
        Button add_employee = new Button("Add Employee");
        Button Update_donor = new Button("Update Donor");
        Button Update_acceptor = new Button("Update Acceptor");
        Button Update_inventory = new Button("Update Inventory");
        Button Manage_Employee = new Button("Manage Employee");
        Button View_Inventory = new Button("Inventory Report");

        options.getChildren().addAll(add_employee,Update_donor,Update_acceptor,Update_inventory,Manage_Employee,View_Inventory);

        add_employee.setOnAction(e->{
            Add_Employee ademp = new Add_Employee();
            try {
                ademp.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Update_donor.setOnAction(e->{
            Update_Donar upd = new Update_Donar();
            try {
                upd.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Update_acceptor.setOnAction(e->{
            Update_Acceptor upa = new Update_Acceptor();
            try {
                upa.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Manage_Employee.setOnAction(e->{
            Update_Employee upe = new Update_Employee();
            try {
                upe.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        View_Inventory.setOnAction(e->{
           Show_Inventory_Report sir = new Show_Inventory_Report();
            try {
                sir.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Update_inventory.setOnAction(e->{
            Update_Inventory ui = new Update_Inventory();
            try
            {
                ui.start(stage);
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });



        Scene sc = new Scene(options,700,400);
        sc.getStylesheets().add("style.css");
        stage.setScene(sc);
        stage.setTitle("Admin Access Life Saviours");
        stage.setResizable(false);
        stage.show();

    }
}