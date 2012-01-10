package voIPpackage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Transmitter extends Thread{
	public boolean thread_active;
	public DatagramSocket transmittersocket;
	public static String remoteIP;
	public static int Portremotehost;
	public TargetDataLine mic;
	public ByteArrayOutputStream micOut;
	public DatagramPacket transmitterpacket;
	
	public Transmitter(boolean e, int p, String k) {
		if (e = true) {
			try {
				transmittersocket = new DatagramSocket(p);
				k = remoteIP;
				p = Portremotehost;
				thread_active = true;
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			
		}
		}
	}
	
	
	public void run() {
		thread_active = true;
	
		while(thread_active) {
			try {
//				transmittersocket.connect(InetAddress.getByName(remoteIP),50210);
				AudioFormat format = Audio.getFormat();
				byte micBuffer[] = new byte[10000];
				final DataLine.Info dataLineInfo = new DataLine.Info(
						TargetDataLine.class, format);
				
				mic = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
				
				mic.open(format, micBuffer.length);
				mic.start();
				System.out.println(micBuffer.length+"her");
				System.out.println(mic.read(micBuffer, 0, micBuffer.length));
//				micOut.write(micBuffer,0,5000);
				System.out.println(micBuffer.length+"test");
				
//				micOut.write(micBuffer,0,mic.read(micBuffer, 0, micBuffer.length));
				
				transmitterpacket = new DatagramPacket(micBuffer,0,micBuffer.length);
				transmitterpacket.setData(micBuffer,0,micBuffer.length);
//				if (!transmitterpacket.getAddress().equals(InetAddress.getByName(remoteIP))) {
				transmitterpacket.setAddress(InetAddress.getByName(remoteIP));
				transmitterpacket.setPort(Portremotehost);
				System.out.println("connecting");
//				}
				transmittersocket.send(transmitterpacket);
				
				System.out.println(transmittersocket.getRemoteSocketAddress()+"TEST");
				
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
