import java.io.Serializable;

/**
 * Created by Maria on 1/03/2016.
 */
public class infor_for_server implements Serializable{
    public String name;
    public String author;

    infor_for_server(String n, String a)
    {
        name=n;
        author=a;
    }
}
