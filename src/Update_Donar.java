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


public class Update_Donar extends Application{
    public static void main(String args[])
    {
        launch(args);
    }

    @Override
    public void start(Stage update_donar) throws Exception
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

        Label DonarID_label = new Label("Donar ID");
        Label DonarName_label = new Label("Name");
        Label DonarContact_label = new Label("Contact no.");
        Label DonarAddress_label = new Label("Address");
        Label DonarGender_label = new Label("Gender");
        Label DonarBG_label = new Label("Blood Group");
        Label DonarAge_label = new Label("Age");
        Label DonarUnits_label = new Label("Units");

        TextField DonarID = new TextField();
        TextField DonarName = new TextField();
        TextField DonarContact = new TextField();
        TextField DonarAge = new TextField();
        TextField DonarUnits = new TextField();

        TextArea DonarAddress = new TextArea();

        DonarID.setMaxWidth(50);
        DonarName.setMaxWidth(300);
        DonarContact.setMaxWidth(300);
        DonarAddress.setMaxSize(300,50);
        DonarAge.setMaxWidth(100);
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

        Button B1 = new Button("Update Donar");
        Button B2 = new Button("Cancel");
        Button B3 = new Button("Delete");
        Button B4 = new Button("Search");

        gp.add(DonarID_label,0,0);
        gp.add(DonarID,1,0);
        gp.add(B4,2,0);
        gp.add(DonarName_label,0,1);
        gp.add(DonarName,1,1);
        gp.add(DonarContact_label,0,2);
        gp.add(DonarContact,1,2);
        gp.add(DonarAddress_label,0,3);
        gp.add(DonarAddress,1,3);
        gp.add(DonarGender_label,0,4);
        gp.add(R_button_layout,1,4);
//        gp.add(R1,1,4);
//        gp.add(R2,2,4);
//        gp.add(R3,3,4);
        gp.add(DonarBG_label,0,5);
        gp.add(cb,1,5);
        gp.add(DonarAge_label,0,6);
        gp.add(DonarAge,1,6);
        gp.add(DonarUnits_label,2,6);
        gp.add(DonarUnits,3,6);
        gp.add(B1,1,7);
        gp.add(B3,2,7);
        gp.add(B2,3,7);

        B4.setOnAction(e->{
            try {
                String User_name = "root";
                String password = "monkey2$";
                String database_name = "blood_bank";

                String search_query = "Select * from donor where donor_id = "+DonarID.getText();
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + database_name, User_name, password);
                PreparedStatement ps = con.prepareStatement(search_query);
                ResultSet r = ps.executeQuery();
                int donar_id = 0;
                String donar_name = "";
                String donar_address = "";
                String phone_number = "";
                String gender = "";
                String blood_group = "";
                int age = 0;
                int local_req_units = 0;
                r.next();
                donar_id = r.getInt(1);
                donar_name = r.getString(2);
                donar_address = r.getString(4);
                phone_number = r.getString(3);
                gender = r.getString(5);
                blood_group = r.getString(6);
                age = r.getInt(7);
                local_req_units = r.getInt(8);
                Units.setUnits(local_req_units);
//                req_units = local_req_units;
                DonarName.setText(donar_name);
                DonarAddress.setText(donar_address);
                DonarContact.setText(phone_number);
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
                DonarAge.setText(String.valueOf(age));
                DonarUnits.setText(String.valueOf(local_req_units));
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

               // String query = "insert into Employee values (?,?,?,?,?,?,?,?)";
                String query1 = "update donor set donor_name='"+DonarName.getText()+"',phone_number='"+DonarContact.getText()+"',donor_address='"+DonarAddress.getText()+"',gender='"+gender+"',Blood_Group='"+blood_group+"',Age="+DonarAge.getText()+",Units="+DonarUnits.getText() + " where donor_id =" + DonarID.getText();
                System.out.println(query1);
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + database_name, User_name, password);
                PreparedStatement stmt = con.prepareStatement(query1);

                int rs = stmt.executeUpdate();
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
                    int new_units = Integer.parseInt(DonarUnits.getText());
                    System.out.println(new_units);

                        existing_units = existing_units + new_units - units_original;
                        String update = "Update inventory set Units = "+existing_units+" where BloodGroup = '"+s+"'";
                        System.out.println(update);
                        PreparedStatement stmt3 = con.prepareStatement(update);
                        int rs3 = stmt3.executeUpdate();

                        if(rs3>0)
                        {
                            DonarID.clear();
                            DonarName.clear();
                            DonarContact.clear();
                            DonarAddress.clear();
                            R1.setSelected(false);
                            R2.setSelected(false);
                            R3.setSelected(false);
                            cb.setValue(null);
                            DonarAge.clear();
                            DonarUnits.clear();
                            Alert_Box.display("Success","Successfully Updated Data");

                        }
                    else {

                        Alert_Box.display("Error","Not Enough Blood Available");
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
                adm.start(update_donar);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        B3.setOnAction(e->{
            try {
                String User_name = "root";
                String password = "Hacker@HG123";
                String database_name = "store";
                String query = "Delete from donar where Donar_ID="+DonarID.getText();
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + database_name, User_name, password);
                PreparedStatement stmt = con.prepareStatement(query);
//                stmt.setInt(1,Integer.parseInt(DonarID.getText()));
//                stmt.setString(2,DonarName.getText());
//                stmt.setString(3,DonarContact.getText());
//                stmt.setString(4,DonarAddress.getText());
//                stmt.setString(5,gender);
//                stmt.setString(6,blood_group);
//                stmt.setInt(7, Integer.parseInt(DonarAge.getText()));
//                stmt.setInt(8,Integer.parseInt(DonarUnits.getText()));
                int rs = stmt.executeUpdate();

                if(rs>0)
                {
                    Alert_Box.display("Success","Data Deleted Successfully");
                    DonarID.clear();
                    DonarName.clear();
                    DonarContact.clear();
                    DonarAddress.clear();
                    R1.setSelected(false);
                    R2.setSelected(false);
                    R3.setSelected(false);
                    cb.setValue(null);
                    DonarAge.clear();
                    DonarUnits.clear();
                }

            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        main_layout.getChildren().add(gp);
        main_layout.getChildren().add(imgView);
        Scene sc = new Scene(main_layout,1000,450);
        sc.getStylesheets().add("style.css");
        update_donar.setScene(sc);
        update_donar.setResizable(false);
        update_donar.setTitle("Update Donor");
        update_donar.show();

    }


}
