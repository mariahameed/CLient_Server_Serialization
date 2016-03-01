import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Maria on 1/03/2016.
 */
public class server_class {
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
        }
        catch(Exception e){
            System.out.println("Error:"+e.getMessage());
        }
    }

    public static void main(String args[]) throws IOException
    {
    ServerSocket listener = new ServerSocket(9000);
    try{
        while(true){
            Socket socket = listener.accept();
            try{

                Note n;
                ObjectInputStream serverInputStream = new
                        ObjectInputStream(socket.getInputStream());

                ObjectOutputStream serverOutputStream = new
                        ObjectOutputStream(socket.getOutputStream());


                Object o = serverInputStream.readObject();

                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

                System.out.print(o.getClass());

                if (o.getClass().toString().matches("class Note"))
                {
                    n = (Note )o;
                    recordingData(n) ;
                    //PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                    out.println("Data archived successfully");
                }
                else
                {

                    infor_for_server ifs = (infor_for_server)o;
                    String options = ifs.author+"__"+ifs.name;
                    //String segments[] = options.split("__");

                    Note e = null;
                    try{
                        FileInputStream fileIn = new FileInputStream(options+".ser");
                        ObjectInputStream in = new ObjectInputStream(fileIn);
                        e = (Note) in.readObject();
                        in.close();
                        fileIn.close();

                        serverOutputStream.writeObject(e);
                    }catch(IOException i){
                        System.out.println("File not found");
                        return;
                    }catch(ClassNotFoundException c)
                    {
                        System.out.println("info class not found");
                        return;
                    }

                    System.out.println("ERROR"+e.toString());
                }
/*                n = (Note )o;


                //serializing the object recieved from the client
                recordingData(n) ;


                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                out.println("Data archived successfully");
*/
                serverInputStream.close();
                serverOutputStream.close();
            }
            catch (Exception e)
            {
                System.out.println("qqqqq"+e.getMessage());
            }
            finally{
                socket.close();
            }
        }
    }
    finally{
        listener.close();
    }
    }
}