import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class LogIn_page extends Application {
    @Override
    public void start(Stage login_stage) throws Exception
    {
        Label user_label = new Label("Username: ");
        Label password_label = new Label("Password: ");
        TextField username = new TextField();
        username.setPromptText("Enter username");
        PasswordField password = new PasswordField();
        password.setPromptText("Enter password");
        Button login = new Button("Login");
        Button close_button = new Button("Close");

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
        Scene login_scene = new Scene(loginLayout,250,150);
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
