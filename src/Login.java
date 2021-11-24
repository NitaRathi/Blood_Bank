import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.sql.SQLException;

public class Login extends Application {
    @Override
    public void start(Stage login_stage) throws Exception
    {
        VBox sp = new VBox();
        Image img = new Image(new FileInputStream("Logo_Blood_Bank.png"));
        ImageView imgView = new ImageView(img);
        imgView.setX(0);
        imgView.setFitHeight(150);
        imgView.setFitWidth(150);
        imgView.setPreserveRatio(true);

        Label user_label = new Label("Username: ");
        Label password_label = new Label("Password: ");
        TextField username = new TextField();
        username.setPromptText("Enter username");
        PasswordField password = new PasswordField();
        password.setPromptText("Enter password");
        Button login = new Button("Login");
        Button close_button = new Button("Close");

        login.setOnAction(e->{
            try {
                String role = Check_User.Check_Credentials(username.getText(),password.getText());
                if(role.equalsIgnoreCase("admin"))
                {
                    System.out.println("login success as admin");
                    Admin_Access adm = new Admin_Access();
                    adm.start(login_stage);
                }
                else if(role.equalsIgnoreCase("employee"))
                {
                    System.out.println("Successfully logged in as Employee");
                    Employee_Access emp = new Employee_Access();
                    emp.start(login_stage);
                }

            } catch (SQLException ex) {
                System.out.println(ex.getLocalizedMessage());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        close_button.setOnAction(e->{
            login_stage.close();
        });

        GridPane loginLayout = new GridPane();
        loginLayout.setPadding(new Insets(10,10,10,10));
        loginLayout.setVgap(10);
        loginLayout.setHgap(5);
        loginLayout.add(user_label,0,0);
        loginLayout.add(username,1,0);
        loginLayout.add(password_label,0,1);
        loginLayout.add(password,1,1);
        loginLayout.add(login,0,2);
        loginLayout.add(close_button,1,2);
        sp.getChildren().addAll(imgView,loginLayout);
        Scene login_scene = new Scene(sp,300,250);
        login_scene.getStylesheets().add("style.css");
        login_stage.setScene(login_scene);
        login_stage.setTitle("LifeSaviours Login");
        login_stage.setResizable(false);
        login_stage.show();
    }
    public static void main(String args[])
    {
        launch(args);
    }
}
