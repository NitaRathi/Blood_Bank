import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.sql.*;


public class Add_Acceptor extends Application{
    public static void main(String args[])
    {
        launch(args);
    }

    @Override
    public void start(Stage add_acceptor) throws Exception
    {

        HBox main_layout = new HBox();
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(10, 10, 10, 10));
        gp.setVgap(25);
        gp.setHgap(5);

        Image img = new Image(new FileInputStream("Image.jpg"));

        ImageView imgView = new ImageView(img);

        imgView.setX(150);
        imgView.setFitHeight(300);
        imgView.setFitWidth(300);
        imgView.setPreserveRatio(true);
//        imgView.setStyle("-fx-border-color: #0000;");

        Label AcceptorID_label = new Label("Acceptor ID");
        Label AcceptorName_label = new Label("Name");
        Label AcceptorContact_label = new Label("Contact no.");
        Label AcceptorAddress_label = new Label("Address");
        Label AcceptorGender_label = new Label("Gender");
        Label AcceptorBG_label = new Label("Blood Group");
        Label AcceptorAge_label = new Label("Age");
        Label AcceptorUnits_label = new Label("Units");

        TextField AcceptorID = new TextField();
        TextField AcceptorName = new TextField();
        TextField AcceptorContact = new TextField();
        TextField AcceptorAge = new TextField();
        TextField AcceptorUnits = new TextField();

        TextArea AcceptorAddress = new TextArea();

        AcceptorID.setMaxWidth(50);
        AcceptorName.setMaxWidth(300);
        AcceptorContact.setMaxWidth(300);
        AcceptorAddress.setMaxSize(300,50);
        AcceptorAge.setMaxWidth(100);
        HBox R_button_layout = new HBox();

        ToggleGroup tg = new ToggleGroup();
        RadioButton R1 = new RadioButton("Male");
        RadioButton R2 = new RadioButton("Female");
        RadioButton R3 = new RadioButton("Others");
        R1.setToggleGroup(tg);
        R2.setToggleGroup(tg);
        R3.setToggleGroup(tg);

        R_button_layout.setSpacing(10);
        R_button_layout.setMinWidth(300);
        R_button_layout.setPrefWidth(300);
        R_button_layout.getChildren().addAll(R1,R2,R3);

        ComboBox cb = new ComboBox();
        cb.getItems().add("A+");
        cb.getItems().add("A-");
        cb.getItems().add("B+");
        cb.getItems().add("B-");
        cb.getItems().add("O+");
        cb.getItems().add("O-");
        cb.getItems().add("AB+");
        cb.getItems().add("AB-");

        Button B1 = new Button("Add Acceptor");
        Button B2 = new Button("Cancel");

        gp.add(AcceptorID_label,0,0);
        gp.add(AcceptorID,1,0);
        gp.add(AcceptorName_label,0,1);
        gp.add(AcceptorName,1,1);
        gp.add(AcceptorContact_label,0,2);
        gp.add(AcceptorContact,1,2);
        gp.add(AcceptorAddress_label,0,3);
        gp.add(AcceptorAddress,1,3);
        gp.add(AcceptorGender_label,0,4);
        gp.add(R_button_layout,1,4);
        gp.add(AcceptorBG_label,0,5);
        gp.add(cb,1,5);
        gp.add(AcceptorAge_label,0,6);
        gp.add(AcceptorAge,1,6);
        gp.add(AcceptorUnits_label,2,6);
        gp.add(AcceptorUnits,3,6);
        gp.add(B1,1,7);
        gp.add(B2,2,7);

        B1.setOnAction(e->{
            try {
                String User_name = "root";
                String password = "monkey2$";
                String database_name = "blood_bank";

                String gender = "";

                if(R1.isSelected())
                {
                    gender = "Male";
                }

                else if (R2.isSelected())
                {
                    gender = "Female";
                }

                else if(R3.isSelected())
                {
                    gender = "Other";
                }

                String blood_group = "";
                blood_group +=  cb.getValue();
                int req_unit = Integer.parseInt(AcceptorUnits.getText());
                String query = "insert into acceptor values (?,?,?,?,?,?,?,?)";
                String find ="select * from inventory where BloodGroup = '"+blood_group+"'";


                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + database_name, User_name, password);
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1,Integer.parseInt(AcceptorID.getText()));
                stmt.setString(2,AcceptorName.getText());
                stmt.setString(3,AcceptorContact.getText());
                stmt.setString(4,AcceptorAddress.getText());
                stmt.setString(5,gender);
                stmt.setString(6,blood_group);
                stmt.setInt(7, Integer.parseInt(AcceptorAge.getText()));
                stmt.setInt(8,Integer.parseInt(AcceptorUnits.getText()));
                int rs = stmt.executeUpdate();
                PreparedStatement stmt1 = con.prepareStatement(find);
                ResultSet r = stmt1.executeQuery();
                r.next();
                int Units = r.getInt("Units");
                int new_bg_units = Units - Integer.parseInt(AcceptorUnits.getText());
                String update ="update inventory set Units =  "+new_bg_units+" where BloodGroup = '"+blood_group+"'";
                PreparedStatement stmt2 = con.prepareStatement(update);
                int rq = stmt2.executeUpdate();


                if(rs>0 && rq > 0)
                {
                    Alert_Box.display("Success","Successfully Added data.");
                    AcceptorID.clear();
                    AcceptorName.clear();
                    AcceptorContact.clear();
                    AcceptorAddress.clear();
                    R1.setSelected(false);
                    R2.setSelected(false);
                    R3.setSelected(false);
                    cb.setValue(null);
                    AcceptorAge.clear();
                    AcceptorUnits.clear();
                }
                con.close();
//                Connection con2 = DriverManager.getConnection(
//                        "jdbc:mysql://localhost:3306/" + database_name, User_name, password);
//                String s = blood_group;
//                String sql = "Select Units from Inventory where Blood_Group = '"+s+"'";
//                PreparedStatement stmt2 = con2.prepareStatement(sql);
//                ResultSet rs2 = stmt2.executeQuery();
//                Integer Avail_units = rs2.getInt(2);
//                Avail_units = Avail_units - req_unit;
//
//                String query2 = "Update inventory set Units = "+Avail_units+"where Blood_group = '"+s+"'";
//                PreparedStatement stmt3 = con2.prepareStatement(query2);
//                int rs3 = stmt3.executeUpdate();


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        });

        B2.setOnAction(e->{
            Employee_Access emp_access = new Employee_Access();
            try {
                emp_access.start(add_acceptor);
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });

        main_layout.getChildren().add(gp);
        main_layout.getChildren().add(imgView);
        Scene sc = new Scene(main_layout,1000,450);
        sc.getStylesheets().add("style.css");
        add_acceptor.setScene(sc);
        add_acceptor.setResizable(false);
        add_acceptor.setTitle("Add new Acceptor");
        add_acceptor.show();

    }


}
