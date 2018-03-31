package nimor111.com.example.jersey.database;

import nimor111.com.example.jersey.models.Role;
import nimor111.com.example.jersey.models.User;

import javax.naming.*;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private Connection conn = null;

    public UserRepository() {
        try {
            Context initialContext = new InitialContext();
            Context env = (Context)initialContext.lookup("java:/comp/env/");
            DataSource dataSource = (DataSource)env.lookup("jdbc/javaUserService");
            conn = dataSource.getConnection();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getUsers() {
       List<User> users = new ArrayList<User>();
       String sql = "SELECT FIRSTNAME, LASTNAME, EMAIL, role, PASSWORD FROM USER;";
       Statement st = null;
       ResultSet rs = null;

       try {
           st = conn.createStatement();
           rs = st.executeQuery(sql);
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
           return null;
       } finally {
           try {
               if (st != null) st.close();
               if (conn != null) conn.close();
               if(rs != null) rs.close();
           } catch (Exception e) {
               System.out.println(e);
           }
       }

       return users;
    }

    public User getUser(String email) {
        User user = null;
        String sql = "SELECT * FROM USER WHERE EMAIL=?;";
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(sql);
            st.setString(1, email);
            rs = st.executeQuery();
            if (rs.next()) {
                user = new User();
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
        } finally {
           try {
               if (st != null) st.close();
               if(rs != null) rs.close();
           } catch (Exception e) {
               System.out.println(e);
           }
       }

        return user;
    }

    public User createUser(User user) {
        String sql = "INSERT INTO USER (FIRSTNAME, LASTNAME, EMAIL, ROLE) VALUES (?, ?, ?, ?)";
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(sql);
            st.setString(1, user.getFirstName());
            st.setString(2, user.getLastName());
            st.setString(3, user.getEmail());
            st.setString(4, user.getRole().toString());
            st.executeUpdate();

            return user;
        } catch (SQLException ex) {
           System.out.println("SQLException: " + ex.getMessage());
           System.out.println("SQLState: " + ex.getSQLState());
           System.out.println("VendorError: " + ex.getErrorCode());
           return null;
        } catch (Exception ex) {
            return null;
        } finally {
           try {
               if (st != null) st.close();
           } catch (Exception e) {
               System.out.println(e);
           }
       }
    }

    // TODO add permissions on this, only admin should be able to edit password
    public void updateUser(User user) {
        String sql = "UPDATE USER SET FIRSTNAME=?, LASTNAME=?, ROLE=?, PASSWORD=? WHERE EMAIL=?";
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(sql);
            st.setString(1, user.getFirstName());
            st.setString(2, user.getLastName());
            st.setString(3, user.getRole().toString());
            st.setString(4, user.getPassword());
            st.setString(5, user.getEmail());
            st.executeUpdate();
        } catch (SQLException ex) {
           System.out.println("SQLException: " + ex.getMessage());
           System.out.println("SQLState: " + ex.getSQLState());
           System.out.println("VendorError: " + ex.getErrorCode());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
           try {
               if(st != null) st.close();
           } catch (Exception e) {
               System.out.println(e);
           }
       }
    }

    public void deleteUser(String email) {
        String sql = "DELETE FROM USER WHERE EMAIL=?";
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(sql);

            st.setString(1, email);
            st.executeUpdate();
        } catch (SQLException ex) {
           System.out.println("SQLException: " + ex.getMessage());
           System.out.println("SQLState: " + ex.getSQLState());
           System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
           try {
               if(st != null) st.close();
           } catch (Exception e) {
               System.out.println(e);
           }
       }
    }
}
