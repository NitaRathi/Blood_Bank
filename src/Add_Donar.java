import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.sql.*;


public class Add_Donar extends Application{
    public static void main(String args[])
    {
        launch(args);
    }

    @Override
    public void start(Stage add_donar) throws Exception
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

        Button B1 = new Button("Add Donar");
        Button B2 = new Button("Cancel");

        gp.add(DonarID_label,0,0);
        gp.add(DonarID,1,0);
        gp.add(DonarName_label,0,1);
        gp.add(DonarName,1,1);
        gp.add(DonarContact_label,0,2);
        gp.add(DonarContact,1,2);
        gp.add(DonarAddress_label,0,3);
        gp.add(DonarAddress,1,3);
        gp.add(DonarGender_label,0,4);
        gp.add(R_button_layout,1,4);
        gp.add(DonarBG_label,0,5);
        gp.add(cb,1,5);
        gp.add(DonarAge_label,0,6);
        gp.add(DonarAge,1,6);
        gp.add(DonarUnits_label,2,6);
        gp.add(DonarUnits,3,6);
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

                String query = "insert into donor values (?,?,?,?,?,?,?,?)";
                String find ="select * from inventory where BloodGroup = '"+blood_group+"'";

                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + database_name, User_name, password);
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1,Integer.parseInt(DonarID.getText()));
                stmt.setString(2,DonarName.getText());
                stmt.setString(3,DonarContact.getText());
                stmt.setString(4,DonarAddress.getText());
                stmt.setString(5,gender);
                stmt.setString(6,blood_group);
                stmt.setInt(7, Integer.parseInt(DonarAge.getText()));
                stmt.setInt(8,Integer.parseInt(DonarUnits.getText()));
                System.out.println(query);
                int rs = stmt.executeUpdate();
                PreparedStatement stmt1 = con.prepareStatement(find);
                ResultSet r = stmt1.executeQuery();
                r.next();
                int Units = r.getInt("Units");
                int new_bg_units = Units + Integer.parseInt(DonarUnits.getText());
                String update ="update inventory set Units =  "+new_bg_units+" where BloodGroup = '"+blood_group+"'";
                PreparedStatement stmt2 = con.prepareStatement(update);
                int rq = stmt2.executeUpdate();


                if(rs>0 && rq>0)
                {
                    Alert_Box.display("Success","Data Added Successfully");
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
                else
                {
                    Alert_Box.display("Error","Error!");
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });

        B2.setOnAction(e->{
            Employee_Access emp_access = new Employee_Access();
            try {
                emp_access.start(add_donar);
                }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });

        main_layout.getChildren().add(gp);
        main_layout.getChildren().add(imgView);
        Scene sc = new Scene(main_layout,1000,450);
        sc.getStylesheets().add("style.css");
        add_donar.setScene(sc);
        add_donar.setResizable(false);
        add_donar.setTitle("Add new Donor");
        add_donar.show();
    }
}
