import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;

public class Events {

    public static Connection connect() {
        try {
            // db parameters
            String url = "jdbc:sqlite:events.db";
            // create a connection to the database
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.err.println(e);
        }
        return null;
    }

    public static boolean setUp() {
        Connection conn = null;
        try {
            conn = connect();
            if (conn == null)
                return false;

            Statement s = conn.createStatement();
            s.execute("CREATE TABLE IF NOT EXISTS event(\n" +
                    "id INTEGER PRIMARY KEY, \n" +
                    "title TEXT UNIQUE NOT NULL, \n" +
                    "description TEXT NOT NULL, \n" +
                    "target REAL NOT NULL, \n" +
                    "currentAmount REAL NOT NULL, \n" +
                    "deadline TEXT NOT NULL)");
            return true;
        }
        catch (SQLException ex){
            System.err.println(ex);
            return false;
        }
        finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception e){
                System.err.println(e);
            }

        }
    }

    public static Event getEvent(String title) {
        Connection conn = null;
        try {
            conn = connect();
            if (conn == null)
                return null;

            PreparedStatement s = conn.prepareStatement("SELECT * from event WHERE title = ?");
            s.setString(1, title);
            ResultSet r = s.executeQuery();

            r.next();
            Event e = new Event(
                    r.getInt("id"),
                    r.getString("title"),
                    r.getString("description"),
                    r.getDouble("target"),
                    r.getString("deadline")
            );
            e.getAccount().deposit(r.getDouble("currentAmount"));
            return e;

        }
        catch (SQLException | ParseException ex){
            return null;
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception e){
                System.err.println(e);
            }

        }
    }

    public static Event getEvent(int id) {
        Connection conn = null;
        try {
            conn = connect();
            if (conn == null)
                return null;

            PreparedStatement s = conn.prepareStatement("SELECT * from event WHERE id = ?");
            s.setInt(1, id);
            ResultSet r = s.executeQuery();

            r.next();
            Event e = new Event(
                    r.getInt("id"),
                    r.getString("title"),
                    r.getString("description"),
                    r.getDouble("target"),
                    r.getString("deadline")
            );
            e.getAccount().deposit(r.getDouble("currentAmount"));
            return e;

        }
        catch (SQLException | ParseException ex){
            return null;
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception e){
                System.err.println(e);
            }

        }
    }

    public static ArrayList<Event> getEvents() {
        Connection conn = null;
        try {
            conn = connect();
            if (conn == null)
                return null;

            PreparedStatement s = conn.prepareStatement("SELECT * from event");
            ResultSet r = s.executeQuery();

            ArrayList<Event> toReturn = new ArrayList<>();
            Event e = null;
            while(r.next()){
                e = new Event(
                        r.getInt("id"),
                        r.getString("title"),
                        r.getString("description"),
                        r.getDouble("target"),
                        r.getString("deadline")
                );
                e.getAccount().deposit(r.getDouble("currentAmount"));
            }
            return toReturn;
        }
        catch (SQLException | ParseException ex){
            return null;
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception e){
                System.err.println(e);
            }

        }
    }

    public static Event updateEvent(Event event) {
        Connection conn = null;
        try {
            conn = connect();
            if (conn == null)
                return null;

            PreparedStatement s = conn.prepareStatement("UPSERT INTO event (id, title, description, target, currentAmount, deadline) VALUES (?, ?, ?, ?, ?, ?)");
            s.setInt(1, event.getId());
            s.setString(2, event.getTitle());
            s.setString(3, event.getDescription());
            s.setDouble(4, event.getTarget());
            s.setDouble(5, event.getAccount().getBalance());
            s.setString(6, event.getDeadlineString());
            s.executeUpdate();
            return getEvent(event.getId());
        }
        catch (SQLException ex){
            return null;
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception e){
                System.err.println(e);
            }

        }
    }
}
