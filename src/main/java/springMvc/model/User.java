package springMvc.model;

/**
 * Created by hyeondeok on 2018. 3. 5..
 */
public class User {
    int id;
    String name;
    String email;
    String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override public String toString() {
        return Long.toString(this.id) + "," + this.name + "," + this.email;
    }
}
