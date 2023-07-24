package application;

import db.DB;
import db.DbExeption;
import db.DbIntegrityException;

import java.sql.*;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Program {
    public static void main(String[] args) {
        // Transaction
        Connection conn = null;
        Statement st = null;

        try {
            conn = DB.getConnection();
            conn.setAutoCommit(false);

            st = conn.createStatement();

            int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");

            /*
            int x = 1;
            if(x < 2){
                throw new SQLException("Fake error");
            }
             */

            int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");

            conn.commit();

            System.out.println("Rows1 = " + rows1);
            System.out.println("Rows2 = " + rows2);

        }
        catch (SQLException e){
            try {
                conn.rollback();
                throw new DbExeption("Transaction rolled back! Caused by: " + e.getMessage());
            } catch (SQLException e1) {
                throw new DbExeption("Error trying to rollback! Caused by : " + e1.getMessage());
            }
        }
        finally {
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }
}
