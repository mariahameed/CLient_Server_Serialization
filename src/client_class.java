import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by Maria on 1/03/2016.
 */
public class client_class {

    public static void recordingData(Note n)
    {

        try{
            String fileName = n.author+"__"+n.obj_name+".ser";
            File f = new File(fileName);

            if(!f.createNewFile())
                System.out.println("An error occoured. Object Couldn't be saved");
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(n);
            oos.close();
            fos.close();
            System.out.println("Data Recorded successfully");
        }
        catch(Exception e){
            System.out.println("Error:"+e.getMessage());
        }
    }

    public static void archive_object(Note n)
    {

        try {
            Socket sender = new Socket("localhost", 9000);

            ObjectOutputStream clientOutputStream = new
                    ObjectOutputStream(sender.getOutputStream());
            ObjectInputStream clientInputStream = new
                    ObjectInputStream(sender.getInputStream());


            clientOutputStream.writeObject(n);


            //n = (Note) clientInputStream.readObject();
            BufferedReader input = new BufferedReader(new InputStreamReader(sender.getInputStream()));
            System.out.println(input.readLine());


            clientOutputStream.close();
            clientInputStream.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void search_object(String name, String obj)
    {

        infor_for_server ifs = new infor_for_server(obj,name);

        try {
            Socket sender = new Socket("localhost", 9000);

            ObjectOutputStream clientOutputStream = new
                    ObjectOutputStream(sender.getOutputStream());
            ObjectInputStream clientInputStream = new
                    ObjectInputStream(sender.getInputStream());

            clientOutputStream.writeObject(ifs);

            //n = (Note) clientInputStream.readObject();

            Note o = (Note) clientInputStream.readObject();

            System.out.println("*"+o.toString()+"*");

            if (o == null)
            {
                System.out.println("No such object found");
            }
            else {
                System.out.print("HERE");
                System.out.println("Object name: " + o.obj_name);
                System.out.println("Object data: " + o.obj_val);
            }
            clientOutputStream.close();
            clientInputStream.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws UnknownHostException, IOException {

        Note n = new Note();

        Scanner sc = new Scanner(System.in);

        System.out.println("Your Name:");
        n.author = sc.nextLine();//"Maria";

        System.out.println("Object Name:");
        n.obj_name = sc.nextLine();//"first object";

        System.out.println("For search enter(s), for record enter(r) and for archiving enter(a)");
        char option = sc.nextLine().charAt(0);

        switch (option)
        {
            case 's':
                search_object(n.author,n.obj_name);
                break;

            case 'a':
                n.archive_record = option;
                System.out.println("Data:");
                n.obj_val = sc.nextLine();//"this is the first object created";
                archive_object(n);
                break;

            case 'r':
                n.archive_record = option;
                System.out.println("Data:");
                n.obj_val = sc.nextLine();//"this is the first object created";
                recordingData(n);
                break;
        }
    }
}