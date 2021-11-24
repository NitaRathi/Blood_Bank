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
import java.util.concurrent.atomic.AtomicInteger;


public class Update_Acceptor extends Application{
    public static void main(String args[])
    {
        launch(args);
    }

    @Override
    public void start(Stage update_acceptor) throws Exception
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

        Button B1 = new Button("Modify Acceptor");
        Button B2 = new Button("Cancel");
        Button B3 = new Button("Delete");
        Button B4 = new Button("Search");

        gp.add(AcceptorID_label,0,0);
        gp.add(AcceptorID,1,0);
        gp.add(B4,2,0);
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
        gp.add(B3,2,7);
        gp.add(B2,3,7);
        int req_units= 0;

        B4.setOnAction(e->{
            try {
                String User_name = "root";
                String password = "monkey2$";
                String database_name = "blood_bank";

                String search_query = "Select * from acceptor where acceptor_id = "+AcceptorID.getText();
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + database_name, User_name, password);
                PreparedStatement ps = con.prepareStatement(search_query);
                ResultSet r = ps.executeQuery();
                int acceptor_id = 0;
                String acceptor_name = "";
                String acceptor_address = "";
                String phone_number = "";
                String gender = "";
                String blood_group = "";
                int age = 0;
                int local_req_units = 0;
                r.next();
                acceptor_id = r.getInt(1);
                acceptor_name = r.getString(2);
                acceptor_address = r.getString(4);
                phone_number = r.getString(3);
                gender = r.getString(5);
                blood_group = r.getString(6);
                age = r.getInt(7);
                local_req_units = r.getInt(8);
                Units.setUnits(local_req_units);
//                req_units = local_req_units;
                AcceptorName.setText(acceptor_name);
                AcceptorAddress.setText(acceptor_address);
                AcceptorContact.setText(phone_number);
                if(gender.equals("Male"))
                {
                    R1.setSelected(true);
                }
                else if(gender.equals("Female"))
                {
                    R2.setSelected(true);
                }
                else
                {
                    R3.setSelected(true);
                }

                cb.setValue(blood_group);
                AcceptorAge.setText(String.valueOf(age));
                AcceptorUnits.setText(String.valueOf(local_req_units));
                con.close();
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });

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
                String query1 = "update acceptor set acceptor_name='"+AcceptorName.getText()+"',phone_number='"+AcceptorContact.getText()+"',acceptor_address='"+AcceptorAddress.getText()+"',gender='"+gender+"',Blood_Group='"+blood_group+"',Age="+AcceptorAge.getText()+",Required_Units="+AcceptorUnits.getText() + " where acceptor_id = " + AcceptorID.getText();
                System.out.println(query1);
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + database_name, User_name, password);
                PreparedStatement stmt = con.prepareStatement(query1);

                int rs = stmt.executeUpdate();
//                Connection con2 = DriverManager.getConnection(
//                        "jdbc:mysql://localhost:3306/" + database_name, User_name, password);
                String s = blood_group;

                if(rs>0)
                {
                    int existing_units = 0;

                    String fetch_units  = "Select Units from inventory where BloodGroup = '"+s+"'";
                    System.out.println(fetch_units);
                    PreparedStatement fetch_statement = con.prepareStatement(fetch_units);
                    ResultSet rs2 = fetch_statement.executeQuery();

                    while(rs2.next())
                    {
                        existing_units = rs2.getInt("Units");
                    }
                    int units_original = Units.getUnits();
                    System.out.println(units_original);
                    int new_units =  units_original - Integer.parseInt(AcceptorUnits.getText());
                    System.out.println(new_units);
//                    existing_units = existing_units - new_units;
                    existing_units = existing_units +new_units - units_original;
                    String update = "Update inventory set Units = "+existing_units+" where BloodGroup = '"+s+"'";
                        System.out.println(update);
                        PreparedStatement stmt3 = con.prepareStatement(update);
                        int rs3 = stmt3.executeUpdate();

                        if(rs3>0)
                        {
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
                            Alert_Box.display("Success","Successfully Updated Data");
                        }

                    else {
                        Alert_Box.display("Error","Not Enough BLood Available");
                    }


                con.close();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });

        B2.setOnAction(e->{
            Admin_Access adm = new Admin_Access();
            try {
                adm.start(update_acceptor);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        B3.setOnAction(e->{
            try {
                String User_name = "root";
                String password = "monkey2$";
                String database_name = "blood_bank";
                String query = "Delete from acceptor where Acceptor_ID="+AcceptorID.getText();
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

                if(rs>0)
                {
                    Alert_Box.display("Success","Data deleted Successfully");
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

            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        main_layout.getChildren().add(gp);
        main_layout.getChildren().add(imgView);
        Scene sc = new Scene(main_layout,1000,450);
        sc.getStylesheets().add("style.css");
        update_acceptor.setScene(sc);
        update_acceptor.setResizable(false);
        update_acceptor.setTitle("Update Acceptor");
        update_acceptor.show();

    }


}
