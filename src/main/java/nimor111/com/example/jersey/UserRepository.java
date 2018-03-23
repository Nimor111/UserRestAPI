package nimor111.com.example.jersey;

import javax.naming.*;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    Connection conn = null;

    public UserRepository() {
        try {
            Context initialContext = new InitialContext();
            Context env = (Context)initialContext.lookup("java:/comp/env/");
            DataSource dataSource = (DataSource)env.lookup("jdbc/javaUserService");
            conn = dataSource.getConnection();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public List<User> getUsers() {
       List<User> users = new ArrayList<User>();
       String sql = "SELECT * FROM USER;";

       try {
           Statement st = conn.createStatement();
           ResultSet rs = st.executeQuery(sql);
           System.out.println(rs);
           while (rs.next()) {
               User user = new User();
               user.setFirstName(rs.getString(1));
               user.setLastName(rs.getString(2));
               user.setEmail(rs.getString(3));
               user.setRole(Role.valueOf(rs.getString(4)));
               user.setPassword(rs.getString(5));

               users.add(user);
           }
       } catch (SQLException ex){
           // handle any errors
           System.out.println("SQLException: " + ex.getMessage());
           System.out.println("SQLState: " + ex.getSQLState());
           System.out.println("VendorError: " + ex.getErrorCode());
       }

       return users;
    }

    public User getUser(String email) {
        User user = new User();
        String sql = String.format("SELECT * FROM USER WHERE EMAIL=\"%s\";", email);
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                user.setFirstName(rs.getString(1));
                user.setLastName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setRole(Role.valueOf(rs.getString(4)));
                user.setPassword(rs.getString(5));
            }
        } catch (SQLException ex) {
           System.out.println("SQLException: " + ex.getMessage());
           System.out.println("SQLState: " + ex.getSQLState());
           System.out.println("VendorError: " + ex.getErrorCode());
        }

        return user;
    }
}
