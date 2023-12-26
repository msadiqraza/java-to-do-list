package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class Controller implements Initializable {

     @FXML
     private TableView<Product> tableview;
     @FXML
     private TableColumn<Product, LocalDate> colDate;
     @FXML
     private TableColumn<Product, String> colTime;
     @FXML
     private TableColumn<Product, String> colSubject;
     @FXML
     private TableColumn<Product, String> colPlace;
     @FXML
     private TableColumn<Product, String> colDescription;

     @FXML
     private Button addbtn;
     @FXML
     private Label warning;
     @FXML
     private DatePicker datepick;
     @FXML
     private TextField timeField;
     @FXML
     private TextField subjectField;
     @FXML
     private TextField Placefield;
     @FXML
     private TextField Descriptionfield;

     ObservableList<Product> products = FXCollections.observableArrayList();
     ObservableList<Product> newList = FXCollections.observableArrayList();

     @Override
     public void initialize(URL arg0, ResourceBundle arg1) {

          subjectField.setText("");
          timeField.setText("");
          Placefield.setText("");
          Descriptionfield.setText("");
          datepick.setEditable(true);

          warning.setVisible(false);

          addbtn.setOnMouseEntered(event -> {
               if (subjectField.getText() == "" || timeField.getText() == "" ||
                         Placefield.getText() == "" || Descriptionfield.getText() == "")

                    warning.setVisible(true);
          });
          addbtn.setOnMouseExited(event1 -> {
               warning.setVisible(false);
          });

          datepick.setEditable(false);

          load(products);

          tableview.setEditable(true);

          colPlace.setCellFactory(TextFieldTableCell.forTableColumn());
          colTime.setCellFactory(TextFieldTableCell.forTableColumn());
          colSubject.setCellFactory(TextFieldTableCell.forTableColumn());
          colDescription.setCellFactory(TextFieldTableCell.forTableColumn());

          datepick.setValue(LocalDate.now());

          try {
               readFile();
          } catch (FileNotFoundException e) {
          }

     }

     public void load(ObservableList<Product> list) {

          colDate.setCellValueFactory(new PropertyValueFactory<Product, LocalDate>("productDate"));
          colTime.setCellValueFactory(new PropertyValueFactory<Product, String>("productTime"));
          colSubject.setCellValueFactory(new PropertyValueFactory<Product, String>("productSubject"));
          colPlace.setCellValueFactory(new PropertyValueFactory<Product, String>("productPlace"));
          colDescription.setCellValueFactory(new PropertyValueFactory<Product, String>("productDescription"));

          tableview.setItems(list);

     }

     // -----------------FILE WRITING----------------
     public static void saveToFile(String fileName, String text, boolean append) throws IOException {

          File file = new File(fileName);
          FileWriter fw = new FileWriter(file, append);
          BufferedWriter bw = new BufferedWriter(fw);
          bw.write(text);
          bw.close();

     }

     // =======================FILE READ---------------

     public void readFile() throws FileNotFoundException {
          File file = new File("text.txt");
          Scanner sc = new Scanner(file);

          while (sc.hasNextLine()) {
               String line = sc.nextLine();

               if (line != null) {

                    String[] pipe = line.split("\\|");

                    String sub = new String(pipe[0]);
                    LocalDate date = LocalDate.parse(pipe[1]);
                    String time = new String(pipe[2]);
                    String place = new String(pipe[3]);
                    String description = new String(pipe[4]);

                    Product listme = new Product(sub, date, time, place, description);

                    products.add(listme);

               }
          }
          sc.close();
     }

     // -------------------------------INSERT-------------------------------
     public void addbtn() {

          if (subjectField.getText() != "" && timeField.getText() != "" && Placefield.getText() != ""
                    && Descriptionfield.getText() != "") {
               Product newproduct = new Product(subjectField.getText(), datepick.getValue(), timeField.getText(),
                         Placefield.getText(), Descriptionfield.getText());
               // manipulate product
               if (subjectField != null && datepick != null && Placefield != null && Descriptionfield != null
                         && timeField != null) {
                    products.add(newproduct);

                    String text = subjectField.getText() + "|" + datepick.getValue() +
                              "|" + timeField.getText() + "|" + Placefield.getText() +
                              "|" + Descriptionfield.getText() + "\n";

                    try {
                         saveToFile("text.txt", text, true);
                    } catch (IOException e) {
                         e.printStackTrace();
                    }

                    subjectField.setText("");
                    timeField.setText("");
                    Placefield.setText("");
                    Descriptionfield.setText("");
               }
          }
     }

     // --------------------RESET-----------------
     public void reset() throws Throwable {
          saveToFile("text.txt", "", false);
          products.clear();
     }

     // -----------------DELETE--------------------
     public void deletebtn() throws IOException {

          Product product = tableview.getSelectionModel().getSelectedItem();
          products.remove(product);
          String text = null;

          for (int i = 0; i < products.size(); i++)
               text = products.get(i).getProductSubject() + "|" + products.get(i).getProductDate() + "|"
                         + products.get(i).getProductTime() +
                         "|" + products.get(i).getProductPlace() + "|" + products.get(i).getProductDescription() + "\n";

          if (products.isEmpty() != true)
               saveToFile("text.txt", text, false);
          else
               saveToFile("text.txt", "", false);
     }

     // ---------------------SHOWING ALL TASK--------------
     public void ShowAll(ActionEvent e) throws Throwable {
          products.clear();
          readFile();
     }

     // -------------------SEARCHING-------------------

     public void searchbtn() {

          String Subject = subjectField.getText();
          String time = timeField.getText();
          String place = Placefield.getText();
          String description = Descriptionfield.getText();

          ObservableList<Product> delete = tableview.getItems();
          delete.removeIf(newList -> !(Subject.equalsIgnoreCase(newList.getProductSubject())
                    || time.equalsIgnoreCase(newList.getProductTime()) ||
                    place.equalsIgnoreCase(newList.getProductPlace()) ||
                    description.equalsIgnoreCase(newList.getProductDescription())));

          subjectField.setText("");
          timeField.setText("");
          Placefield.setText("");
          Descriptionfield.setText("");
          datepick.getEditor().clear();
     }
}