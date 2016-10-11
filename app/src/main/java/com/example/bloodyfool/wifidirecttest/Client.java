package com.example.bloodyfool.wifidirecttest;


import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by bloodyfool on 11-10-16.
 */

public class Client extends AsyncTask<Void, Void, Void> {
    String dstAddress;
    int dstPort;
    String response = "";
    TextView textResponse;
    String message;

    Client(String addr, int port, TextView textResponse, String theMessage) {
        dstAddress = addr;
        dstPort = port;
        this.textResponse = textResponse;
        message = theMessage;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);

            /**ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                    1024);
            byte[] buffer = new byte[1024];

            int bytesRead;
            InputStream inputStream = socket.getInputStream();


            //notice: inputStream.read() will block if no data return

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
            }**/

            PrintStream printStream = new PrintStream(socket.getOutputStream());
            printStream.print(message);
            printStream.close();

            /**BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            for (String data; (data = bufferedReader.readLine()) != null; ) {
                response = data;
            }

            bufferedReader.close();**/

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        textResponse.setText(response);
        super.onPostExecute(result);
    }
}
