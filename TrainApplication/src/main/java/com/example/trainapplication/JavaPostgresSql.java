package com.example.trainapplication;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class JavaPostgresSql {
    static String url= "jdbc:postgresql://localhost:5432/nastyabaryliak";
    static String user = "postgres";
    static String password = "123321";

    public static void writeToDatabase(Integer trainId, String trainCar_type, Integer trainLuggage, Integer trainSeats, Integer trainCar_class){
        Integer id_TextField = trainId;
        String typeComboBox = trainCar_type;
        Integer luggage_TextField = trainLuggage;
        Integer seats_TextField = trainSeats;
        Integer car_class_TextField = trainCar_class;

        String query = "INSERT INTO train(id, car_type, luggage, seats, car_class) VALUES(?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id_TextField);
//            pst.setString(2, car_type_TextField);
            pst.setString(2, typeComboBox);

            pst.setInt(3, luggage_TextField);
            pst.setInt(4, seats_TextField);
            pst.setInt(5, car_class_TextField);
            pst.executeUpdate();
            System.out.println("sucessfully created.");
        } catch (SQLException ex){
            Logger lgr = Logger.getLogger(JavaPostgresSql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }

}
