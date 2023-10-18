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
            s.execute("""
                    CREATE TABLE IF NOT EXISTS event(
                    id INTEGER PRIMARY KEY,\s
                    title TEXT NOT NULL,\s
                    description TEXT NOT NULL,\s
                    target REAL NOT NULL,\s
                    currentAmount REAL NOT NULL,\s
                    deadline TEXT NOT NULL)""");
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

    public static Event getEvent(int id) throws SQLException, ParseException {
        Connection conn = null;
        try {
            conn = connect();
            if (conn == null)
                return null;

            PreparedStatement s = conn.prepareStatement("SELECT * from event WHERE id = ?");
            s.setInt(1, id);
            ResultSet r = s.executeQuery();

            r.next();
            return new Event(
                    r.getInt("id"),
                    r.getString("title"),
                    r.getString("description"),
                    r.getDouble("target"),
                    r.getDouble("currentAmount"),
                    r.getString("deadline")
            );

        }finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception e){
                System.err.println(e);
            }

        }
    }

    public static ArrayList<Event> getEvents() throws SQLException, ParseException {
        Connection conn = null;
        try {
            conn = connect();
            if (conn == null)
                return null;

            PreparedStatement s = conn.prepareStatement("SELECT * from event");
            ResultSet r = s.executeQuery();

            ArrayList<Event> toReturn = new ArrayList<>();
            while(r.next())
                toReturn.add(new Event(
                        r.getInt("id"),
                        r.getString("title"),
                        r.getString("description"),
                        r.getDouble("target"),
                        r.getDouble("currentAmount"),
                        r.getString("deadline")
                ));
            return toReturn;
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception e){
                System.err.println(e);
            }

        }
    }

    public static Event createEvent(Event event) throws SQLException, ParseException {
        Connection conn = null;
        try {
            conn = connect();
            if (conn == null)
                return null;

            PreparedStatement s = conn.prepareStatement("INSERT INTO event (title, description, target, currentAmount, deadline) VALUES (?, ?, ?, ?, ?)");
            s.setString(1, event.getTitle());
            s.setString(2, event.getDescription());
            s.setDouble(3, event.getTarget());
            s.setDouble(4, event.getBalance());
            s.setString(5, event.getDeadlineString());
            s.executeUpdate();
            ResultSet rs = conn.prepareStatement("select last_insert_rowid();").executeQuery();
            rs.next();
            int x = rs.getInt("last_insert_rowid()");
            return getEvent(x);
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception e){
                System.err.println(e);
            }

        }
    }

    public static Event updateEvent(Event event) throws ParseException, SQLException {
        Connection conn = null;
        try {
            conn = connect();
            if (conn == null)
                return null;

            PreparedStatement s = conn.prepareStatement("UPDATE event SET title = ?, description = ?, target = ?, currentAmount = ?, deadline = ? WHERE id = ?");
            s.setString(1, event.getTitle());
            s.setString(2, event.getDescription());
            s.setDouble(3, event.getTarget());
            s.setDouble(4, event.getBalance());
            s.setString(5, event.getDeadlineString());
            s.setInt(6, event.getId());
            s.executeUpdate();
            return getEvent(event.getId());
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
