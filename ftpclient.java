import java.io.*;
import java.net.*;
import java.util.Arrays;

public class ftpclient {

    Socket client;
    ObjectInputStream cis;
    ObjectOutputStream cos;
    String receivedfilename;
    String updatedname;
    String ch;

    void run(int portnumber) {

        try {
            client = new Socket("localhost", portnumber);

            System.out.println("\n connected to server via portnumber : " + portnumber);

            listfiles(); // returns the list of files present in the folder
            try {
                cos = new ObjectOutputStream(client.getOutputStream()); // client side output stream - used to send data
                                                                        // to the server over the network connection
                                                                        // established by the client
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                cos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                cis = new ObjectInputStream(client.getInputStream()); // client side input stream - used to accept data
                                                                      // to server over the network connection
                                                                      // established by the client
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader cbr = new BufferedReader(new InputStreamReader(System.in)); // client side buffer reader -
                                                                                       // used to read the input from
                                                                                       // the user

            System.out.println(
                    "\n --- Enter the following command for uploading/downloading ---\n For Uploading: upload file_name \n For Downloading: get file_name \n");

            try {
                ch = cbr.readLine(); // stores the data read from user into a string variable using bufferreader
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] splitarray = ch.split(" "); // splits the user input in to string arrays
            File checkfile = new File(splitarray[1]); // return a file object from the splitarray[1]

            if (!checkfile.exists()) {
                System.out.println("\n File does not exist!!! \n");

            }

            /*
             * NOTE: The program checks for the file first.. if it exists then only it moves
             * to the next step and checks for the correct command... since file plays a key
             * role in
             * file transfer ...
             */
            else {

                switch (splitarray[0].toLowerCase()) {

                    case "get":
                        // Program to Download Files From the Server

                        sendfname(ch); // sends the name of the file to the server i.e be downloaded

                        // saves the name of the file it receives from the server in a variable in order
                        // to append it later with "new"
                        try {
                            receivedfilename = (String) cis.readObject();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        updatedname = "new" + receivedfilename;

                        // main logic

                        try (
                                BufferedInputStream cbis = new BufferedInputStream(cis);
                                FileOutputStream cfos = new FileOutputStream(updatedname);
                                BufferedOutputStream cbos = new BufferedOutputStream(cfos);) {
                            byte[] chunks = new byte[1024];
                            int bytesRead;
                            while ((bytesRead = cbis.read(chunks)) > 0) {
                                cbos.write(chunks, 0, bytesRead);
                            }
                            cbos.flush();
                            System.out.println("\n File received and saved as " + updatedname);
                            listfiles();

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                client.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        break;

                    case "upload":
                        // Program to upload Files to server

                        sendfname(ch); // sends the file name to the server that is going to be uploaded

                        // main logic
                        try (
                                FileInputStream cfis = new FileInputStream(splitarray[1]);
                                BufferedInputStream cbis = new BufferedInputStream(cfis);
                                BufferedOutputStream cbos = new BufferedOutputStream(cos);) {
                            byte[] chunksize = new byte[1024];
                            int bytesread;

                            while ((bytesread = cbis.read(chunksize)) > 0) {

                                cbos.write(chunksize, 0, bytesread);

                            }

                        }

                        catch (IOException e) {
                            e.printStackTrace();
                        }

                        finally {

                            try {
                                client.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            System.out.println("\n File sent and connection closed. ");
                            listfiles();

                        }

                        break;

                    default:
                        System.out.println(
                                "\nYou Entered an Incorrect command..\nSorry I cannot initiate Your Request..\n BYE !!!!\n");

                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(
                    "\n The specified portnumber " + portnumber + " is not available to connect to the server \n ");

        }

    }

    public void sendfname(String filename) {

        try {
            cos.writeObject(filename); // writes the filename via object output stream to server
            cos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void listfiles() {
        File directory = new File("."); // Use the current directory
        File[] Files = directory.listFiles();
        System.out.println(" \n --- Files present in the current directory are --- :\n");
        for (File i : Files) {
            if (i.isFile()) {
                System.out.println(i.getName());
            }

        }
        System.out.println("\n");

    }

    public static void main(String[] args) {
        ftpclient client1 = new ftpclient();
        int portnumber = Integer.valueOf(args[0]);// command line arguments
        client1.run(portnumber);
    }

}