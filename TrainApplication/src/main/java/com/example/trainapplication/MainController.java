
package com.example.trainapplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static com.example.trainapplication.JavaPostgresSql.*;
public class MainController {
    private Scene scene;
    private Stage stage;
    int clas = 0;

    ObservableList<String> typeComboBoxList = FXCollections.observableArrayList("Compartment", "Non-compartment", "Delux");

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button CalculateButton;
    @FXML
    private Button AddButton;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button UpdateButton;
    @FXML
    private Button nextPageButton;
    @FXML
    private TextField id_TextField;

    @FXML
    private TextField seats_TextField;
    @FXML
    private TextField car_class_TextField;
    @FXML
    private TextField luggage_TextField;
    @FXML
    private TableView<train> train_TableView;
    @FXML
    private TableColumn<train, Integer> car_class_col;
    @FXML
    private TableColumn<train, String> car_type_col;
    @FXML
    private TableColumn<train, Integer> id_col;
    @FXML
    private TableColumn<train, Integer> luggage_col;
    @FXML
    private TableColumn<train, Integer> seats_col;
    @FXML
    private Label weightLabel;
    @FXML
    private Label seatsLabel;
    @FXML
    private ComboBox typeComboBox;
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView image;
    @FXML
    private RadioButton FirstClass;
    @FXML
    private RadioButton SecondClass;
    @FXML
    private RadioButton ThirdClass ;
    @FXML
    private ToggleGroup tgClass ;

    @FXML
    private TextField fromTextField;
    @FXML
    private TextField toTextField;

    @FXML
    public void initialize() {
        showTrain();
        typeComboBox.setValue("Compartment");
        typeComboBox.setItems(typeComboBoxList);
    }


    public void getData(ActionEvent actionEvent) {  //ADD button
        if(FirstClass.isSelected()){
            clas = Integer.parseInt(FirstClass.getText());
        }
        if(SecondClass.isSelected()){
            clas = Integer.parseInt(SecondClass.getText());
        }
        if(ThirdClass.isSelected()){
            clas = Integer.parseInt(ThirdClass.getText());
        }


        try {
            writeToDatabase(Integer.valueOf(id_TextField.getText()), String.valueOf(typeComboBox.getValue()), Integer.valueOf(luggage_TextField.getText()), Integer.valueOf(seats_TextField.getText()), clas);
            showTrain();
            clearTextFields();
        } catch (NumberFormatException e) {
              errorLabel.setVisible(true);
        }
    }

    public ObservableList<train> getTraintList() {
        ObservableList<train> trainlist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT  * FROM train ORDER BY id ASC";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            train train;
            while (rs.next()) {
                train = new train(rs.getInt("id"), rs.getString("car_type"), rs.getInt("luggage"), rs.getInt("seats"), rs.getInt("car_class"));
                trainlist.add(train);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return trainlist;
    }

    public void showTrain() { //The whole train button
        ObservableList<train> list = getTraintList();

        id_col.setCellValueFactory(new PropertyValueFactory<train, Integer>("id"));
        car_type_col.setCellValueFactory(new PropertyValueFactory<train, String>("car_type"));
        luggage_col.setCellValueFactory(new PropertyValueFactory<train, Integer>("luggage"));
        seats_col.setCellValueFactory(new PropertyValueFactory<train, Integer>("seats"));
        car_class_col.setCellValueFactory(new PropertyValueFactory<train, Integer>("car_class"));

        train_TableView.setItems(list);
        try {
            seatscount();
            luggagecount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void seatscount() throws SQLException {
        Connection conn = getConnection();
        Statement s = conn.createStatement();
        ResultSet r = s.executeQuery("SELECT SUM(seats) AS seatscount FROM train");
        r.next();
        int count = r.getInt("seatscount");
        seatsLabel.setText(String.valueOf(count));
    }
    public void luggagecount() throws SQLException {
        Connection conn = getConnection();
        Statement s = conn.createStatement();
        ResultSet r = s.executeQuery("SELECT SUM(luggage) AS luggagecount FROM train");
        r.next();
        int countlaggage = r.getInt("luggagecount");
        weightLabel.setText(String.valueOf(countlaggage));
    }

    public void deleteButton() { //Delete button
        String query = "DELETE FROM train WHERE id =" + id_TextField.getText() + "";
        executeQuery(query);
        showTrain();

    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void clearTextFields() {
        id_TextField.clear();
        seats_TextField.clear();
        luggage_TextField.clear();
        FirstClass.setSelected(false);
        SecondClass.setSelected(false);
        ThirdClass.setSelected(false);
        errorLabel.setVisible(false);
    }


    public void showTrainInRange() { //show Train In Range button

        ObservableList<train> list2 = getTraintList2();

        id_col.setCellValueFactory(new PropertyValueFactory<train, Integer>("id"));
        car_type_col.setCellValueFactory(new PropertyValueFactory<train, String>("car_type"));
        luggage_col.setCellValueFactory(new PropertyValueFactory<train, Integer>("luggage"));
        seats_col.setCellValueFactory(new PropertyValueFactory<train, Integer>("seats"));
        car_class_col.setCellValueFactory(new PropertyValueFactory<train, Integer>("car_class"));

        train_TableView.setItems(list2);
        try {
            seatscount();
            luggagecount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }}

    public ObservableList<train> getTraintList2() {
            ObservableList<train> trainlist2 = FXCollections.observableArrayList();
            Connection conn = getConnection();

            String query = "SELECT  * FROM train WHERE seats BETWEEN "+Integer.valueOf(fromTextField.getText())+ " and "+Integer.valueOf(toTextField.getText()) ;
            Statement st;
            ResultSet rs;

            try {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                train train;
                while (rs.next()) {
                    train = new train(rs.getInt("id"), rs.getString("car_type"), rs.getInt("luggage"), rs.getInt("seats"), rs.getInt("car_class"));
                    trainlist2.add(train);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return trainlist2;
        }


    public void SwitchToLastPage(ActionEvent event) throws IOException{ //next page button
        Parent root = FXMLLoader.load(getClass().getResource("last-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("TrainApplication");
        stage.setScene(scene);
        stage.show();
     }

    }


