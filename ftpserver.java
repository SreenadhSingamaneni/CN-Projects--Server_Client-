import java.io.*;
import java.net.*;

public class ftpserver {

    ServerSocket server; // awaits for incoming connection
    Socket connection = null; // deals with connected devices for data transfer
    ObjectInputStream sis; //
    ObjectOutputStream sos;
    String receivedfile;
    String updatedname;
    String ch;

    void run(int portnumber) {
        try {
            server = new ServerSocket(portnumber, 1); // accepts only one connection at a time
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Waiting for connection on port number " + portnumber);

        try {
            connection = server.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Connection received from " + connection.getInetAddress().getHostName());

        try {
            sis = new ObjectInputStream(connection.getInputStream()); // Server side output stream - used to accept data
                                                                      // from the client over the network
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            sos = new ObjectOutputStream(connection.getOutputStream()); // Server side output stream - used to send data
                                                                        // to the client over the network

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ch = (String) sis.readObject(); // reads the data received from client and stores in a string variable
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] split = ch.split(" "); // splits the string into string array

        switch (split[0].toLowerCase()) {
            case "get":

                // Program to send files(similar to client uploading)

                sendfname(split[1]); // send the name of the file i.e going to be downloaded by client to client
                // main logic
                try (
                        FileInputStream sfis = new FileInputStream(split[1]);
                        BufferedInputStream sbis = new BufferedInputStream(sfis);
                        BufferedOutputStream sbos = new BufferedOutputStream(sos);) {
                    byte[] chunksize = new byte[1024];
                    int bytesread;

                    while ((bytesread = sbis.read(chunksize)) > 0) {

                        sbos.write(chunksize, 0, bytesread);

                    }

                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                finally {

                    try {
                        server.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("File sent and connection closed.\n \n");
                }

                break;

            case "upload":

                // program to accept files (similar to client downloading)

                updatedname = "new" + split[1];
                // main logic

                try (
                        BufferedInputStream sbis = new BufferedInputStream(sis);
                        FileOutputStream sfos = new FileOutputStream(updatedname);
                        BufferedOutputStream sbos = new BufferedOutputStream(sfos);) {
                    byte[] chunks = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = sbis.read(chunks)) > 0) {
                        sbos.write(chunks, 0, bytesRead);
                    }
                    sbos.flush();
                    System.out.println("File received and saved as " + updatedname);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                finally {
                    try {
                        server.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        }
    }

    public void sendfname(String filename) {

        try {
            sos.writeObject(filename); // writes the filename via object output stream to client
            sos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        int portnumber = Integer.valueOf(args[0]); // command line arguments
        ftpserver server1 = new ftpserver();
        server1.run(portnumber);
    }

}