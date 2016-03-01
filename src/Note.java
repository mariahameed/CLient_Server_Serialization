
public class Note implements java.io.Serializable
{
    public String author;
    public String obj_name;
    public char archive_record;
    public String obj_val;

    public void present_Data()
    {
        System.out.println("Note Name: " + obj_name);
        System.out.println("Author: " + author);
        System.out.println("Note: " + obj_val);
    }
}