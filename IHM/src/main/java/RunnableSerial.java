import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Observable;

public class RunnableSerial extends Observable implements Runnable {

    private SerialPort comPort;
    private boolean exit = false;
    OutputStream out;
    InputStream in;

    RunnableSerial() {
        comPort = SerialPort.getCommPorts()[0];
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);

        out = comPort.getOutputStream();
        in = comPort.getInputStream();
    }

    @Override
    public void run() {

        try
        {
            String res = "";
            Boolean incoming = false;

            while (!exit) {
                char c = (char)in.read();
                switch (c) {
                    case '<':
                        res = "";
                        incoming = true;
                        break;
                    case '>':
                        incoming = false;
                        setChanged();
                        notifyObservers(extractData(res));
                        break;
                    default:
                        if (incoming) {
                            res += c;
                        }
                }
            }
            shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void write(String s) {
        try {
            out.write(s.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void stop() {
        exit = true;
    }

    private void shutdown() throws IOException {
        out.close();
        in.close();
        deleteObservers();
        comPort.closePort();
        System.out.println("port closed");
    }

    private HashMap<String, Double> extractData(String packet) {
        HashMap<String, Double> hashMap = new HashMap<>();

        for (String s: packet.split("\\|")) {
            String[] kv = s.split(":");
            if (kv.length == 2) {
                hashMap.put(kv[0], Double.parseDouble(kv[1]));
            }
        }

        return hashMap;
    }
}
