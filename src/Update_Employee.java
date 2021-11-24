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

public class Update_Employee extends Application{

    @Override
    public void start(Stage AddStage) throws Exception {
        Label emp_id_label = new Label("Employee ID");
        Label emp_username_label = new Label("Username");
        Label emp_password_label = new Label("Password");
        Label emp_role_label = new Label("Role");

        TextField emp_id = new TextField();
        TextField emp_username = new TextField();
        PasswordField emp_password = new PasswordField();

        HBox radio_buttons = new HBox();
        RadioButton admin = new RadioButton("Admin");
        RadioButton employee = new RadioButton("Employee");
        ToggleGroup tg = new ToggleGroup();
        admin.setToggleGroup(tg);
        employee.setToggleGroup(tg);

        radio_buttons.setSpacing(10);
        radio_buttons.getChildren().addAll(admin,employee);
        GridPane gp = new GridPane();
        gp.setVgap(10);
        gp.setHgap(10);

        gp.add(emp_id_label,1,1);
        gp.add(emp_id,2,1);
        gp.add(emp_username_label,1,2);
        gp.add(emp_username,2,2);
        gp.add(emp_password_label,1,3);
        gp.add(emp_password,2,3);
        gp.add(emp_role_label,1,4);
        gp.add(radio_buttons,2,4);

        Button update_btn = new Button("Modify");
        Button close_btn = new Button("Close");
        Button delete_button = new Button("Delete");
        gp.add(update_btn,1,6);
        gp.add(close_btn,2,6);
        gp.add(delete_button,3,6);

        update_btn.setOnAction(e -> {
            try {
                String User_name = "root";
                String password = "monkey2$";
                String database_name = "blood_bank";

                String role = "";
                if (admin.isSelected()) {
                    role = "admin";
                } else if (employee.isSelected()) {
                    role = "employee";
                }


//                String query = "insert into employee values (?,?,?,?)";
                String query1 = "update employee set username='"+emp_username.getText()+"',password='"+emp_password.getText()+"',role='"+role+"' where Employee_id = " + emp_id.getText() ;
                System.out.println(query1);
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + database_name, User_name, password);
                PreparedStatement stmt = con.prepareStatement(query1);
//                stmt.setInt(1,Integer.parseInt(emp_id.getText()));
//                stmt.setString(2,emp_username.getText());
//                stmt.setString(3,emp_password.getText());
//                stmt.setString(4,role);
                int rs = stmt.executeUpdate();

                if(rs>0)
                {
                    Alert_Box.display("Success","Data Modified Successfully");
                    emp_id.clear();
                    emp_username.clear();
                    emp_password.clear();
                    admin.setSelected(false);
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });

        close_btn.setOnAction(e ->{AddStage.close();
            Admin_Access adm = new Admin_Access();
            try {
                adm.start(AddStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }});

        Scene add_scene = new Scene(gp,400,400);
        add_scene.getStylesheets().add("style.css");
        AddStage.setTitle("Manage Employee");
        AddStage.setScene(add_scene);
        AddStage.show();
    }
}
