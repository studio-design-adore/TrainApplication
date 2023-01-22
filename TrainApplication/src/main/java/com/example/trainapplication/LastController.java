package com.example.trainapplication;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import static com.example.trainapplication.JavaPostgresSql.getConnection;

public class LastController {
    private Scene scene;
    private Stage stage;
        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private ImageView image;

        @FXML
        private Button previousPageButton;

        @FXML
        private TableColumn<train, String> sortedCarColumn;

        @FXML
        private TableColumn<train, Integer> sortedClassColumn;

        @FXML
        private TableView<train> sortedTableView;

        @FXML
        private Label totalCarsLabel;

        @FXML
        private Label totalLuggageLabel;

        @FXML
        private Label totalSeatsLabel;

        @FXML
        void initialize(ActionEvent event) {

        }




    @FXML
    public void initialize() {
        showTrain();
    }
    public void SwitchToMainPage(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("TrainApplication");
        stage.setScene(scene);
        stage.show();
    }

    public ObservableList<train> getTraintList() {
        ObservableList<train> trainlist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT  * FROM train ORDER BY car_class ASC";
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
    public void luggagecount() throws SQLException {
        Connection conn = getConnection();
        Statement s = conn.createStatement();
        ResultSet r = s.executeQuery("SELECT SUM(luggage) AS luggagecount FROM train");
        r.next();
        int countlaggage = r.getInt("luggagecount");
        totalLuggageLabel.setText(String.valueOf(countlaggage));
    }
    public void SumCars() throws SQLException {
        Connection conn = getConnection();
        Statement s = conn.createStatement();
        ResultSet r = s.executeQuery("SELECT COUNT(*) AS carscount FROM train");
        r.next();
        int count = r.getInt("carscount");
        totalCarsLabel.setText(String.valueOf(count));
    }
    public void SumSeats() throws SQLException {
        Connection conn = getConnection();
        Statement s = conn.createStatement();
        ResultSet r = s.executeQuery("SELECT SUM(seats) AS seatscount FROM train");
        r.next();
        int count = r.getInt("seatscount");
        totalSeatsLabel.setText(String.valueOf(count));
        luggagecount();
        SumCars();
    }
    public void showTrain() {
        ObservableList<train> list = getTraintList();


        sortedCarColumn.setCellValueFactory(new PropertyValueFactory<train, String>("car_type"));
        sortedClassColumn.setCellValueFactory(new PropertyValueFactory<train, Integer>("car_class"));

        sortedTableView.setItems(list);
        try {
            SumSeats();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}