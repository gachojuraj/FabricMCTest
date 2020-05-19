package sk.jurij.fabrictest;

//TODO: make ports selectable in runtime
public class SerialPort {
    public static int prevNum = 0;
    public static void sendSerial(int num) {
        if (num != prevNum && port != null) {
            String s = String.valueOf(num);
            port.writeBytes(s.getBytes(), s.getBytes().length);
            prevNum = num;
        }
    }

    public static com.fazecast.jSerialComm.SerialPort openSerial(){
        try {
            com.fazecast.jSerialComm.SerialPort comPort = com.fazecast.jSerialComm.SerialPort.getCommPorts()[1];
            comPort.setComPortParameters(9600, 8, 1, com.fazecast.jSerialComm.SerialPort.NO_PARITY);
            comPort.setComPortTimeouts(com.fazecast.jSerialComm.SerialPort.TIMEOUT_SCANNER, 0, 0);
            comPort.openPort();
            return comPort;
        }
        catch (Exception ignored) {}
        return null;
    }

    public static com.fazecast.jSerialComm.SerialPort port = openSerial();
}
