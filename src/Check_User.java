import java.sql.*;

public class Check_User {

    public static String Check_Credentials(String username , String password) throws SQLException {
        String db_username = "root";
        String db_password = "monkey2$";
        String db_name = "blood_bank";
        String role = "";
        String check_query = "SELECT * FROM Employee WHERE Username =\'"+ username+"\'AND Password =\'"+password+"\'" ;
//        System.out.println(check_query);
        ResultSet rs = null;
        try
        {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/"+db_name,db_username,db_password);
            Statement stmt=con.createStatement();
            rs = stmt.executeQuery(check_query);
            rs.next();

            if(rs != null)
            {
                role = rs.getString(4);
            }

        } catch (SQLException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }

        return role;
    }
}