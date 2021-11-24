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

public class Employee_Access extends Application{

    @Override
    public void start(Stage stage) throws Exception
    {
        HBox options = new HBox();
        options.setPadding(new Insets(15, 12, 15, 12));
        options.setSpacing(10);
        Button add_donar = new Button("Add Donar");
        Button add_Acceptor = new Button("Add Acceptor");
        Button View_Inventory = new Button("Inventory Report");

        options.getChildren().addAll(add_donar,add_Acceptor,View_Inventory);

        add_donar.setOnAction(e->{
            Add_Donar ademp = new Add_Donar();
            try {
                ademp.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        add_Acceptor.setOnAction(e->{
           Add_Acceptor adac = new Add_Acceptor();
            try {
                adac.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        View_Inventory.setOnAction(e->{
            Show_Inventory_Report_Emp sir = new Show_Inventory_Report_Emp();
            try {
                sir.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Scene sc = new Scene(options,600,400);
        sc.getStylesheets().add("style.css");
        stage.setScene(sc);
        stage.setTitle("Employee Access Life Saviours");
        stage.setResizable(false);
        stage.show();

    }

}
