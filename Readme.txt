


Project Name :
------------------------------------------------------------------------------------------------

Implementation of FTP client and server

Description :
------------------------------------------------------------------------------------------------

The project involves developing a simplified version of FTP client/server software designed to facilitate file transfers between a server and a client.

Execution(Step by Step):
------------------------------------------------------------------------------------------------

1. Compile Server File:
Open a terminal or command prompt and navigate to the directory containing the “ftpserver.java” file. Compile the ftpserver.java file using the following command:

javac ftpserver.java

2. Compile Client File:
Open another terminal or command prompt and navigate to the directory containing the “ftpclient.java” file. Compile the ftpclient.java file using the following command:

javac ftpclient.java

3. Execute Server:
Start the server by running the compiled class file along with providing the port number as a command-line argument. 

java ftpserver 8000
( Replace 8000 with your desired port number. )

4. Execute Client:
Run the client program by executing the compiled class file along with providing the server's port number as command-line arguments. 

java ftpclient  8000
( 8000 is the port number on which the server is running with respect to the previous step )

4. Interacting with the Client:
After executing the client program, you will see a menu displaying a list of files available on the server. You will be prompted to choose an option to either upload or download a file to/from the server.

To upload a file, use the following command: upload file_name
To download a file, use the following command: get file_name

5. Terminating the Connection:
Upon completing the operation, the connection will be automatically terminated.
If you wish to perform another operation, you need to restart the server connect it to the client again and follow the above steps 
The program is designed to terminate automatically after each operation to  ensure security, thereby reducing the risk of potential cyberattacks and increasing efficiency 

Acknowledgement:
------------------------------------------------------------------------------------------------

This project is done solely for educational purposes, and I would like to express my gratitude to Professor Shigang Chen for providing me with the opportunity to work on this project and acquire valuable insights from it.

Contact :
------------------------------------------------------------------------------------------------

For any suggestions or queries, please feel free to reach out to:
Sreenadh Singamaneni
UFID: 19481205
Email: singamaneni.s@ufl.edu