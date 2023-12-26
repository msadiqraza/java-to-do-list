package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
     @Override
     public void start(Stage stage) {
          try {
               Parent root = FXMLLoader.load(getClass().getResource("Table.fxml"));
               Scene scene = new Scene(root);

               stage.setTitle("TASK TODOLIST");
               stage.setScene(scene);
               try {
                    stage.getIcons().add(new Image("application//images//todolist.jpeg"));
               } catch (Exception e) {
                    System.out.println(e);
               }

               // Add scrolling functionality
               ScrollPane scrollPane = new ScrollPane();
               scrollPane.setContent(root);

               Region region = new Region();
               VBox.setVgrow(region, Priority.ALWAYS);

               // stage.setFullScreen(true);
               stage.show();
          } catch (Exception e) {
               e.printStackTrace();
          }
     }

     public static void main(String[] args) {
          launch(args);
     }
}
