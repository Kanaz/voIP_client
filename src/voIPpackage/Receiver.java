package voIPpackage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Receiver extends Thread {

	public static boolean thread_active;
	public DatagramSocket receiversocket;
	public DatagramPacket receiverpacket;
	public ByteArrayOutputStream byteArrayOutputStream;
	public SourceDataLine sourceDataLine;
	public AudioInputStream audioInputStream;

	public Receiver(boolean e, int p) {
		if (e = true) {
			try {
				receiversocket = new DatagramSocket(p);
				thread_active = true;
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void run() {
		byte tempBuffer[] = new byte[10000];

		while (thread_active) {
			try {
				receiverpacket = new DatagramPacket(tempBuffer,
						tempBuffer.length);
				receiversocket.receive(receiverpacket);

				byte audioData[] = receiverpacket.getData();
				System.out.println(audioData.length
						+ ": audioData bytearray l¾ngde, burde v¾re fyldt.");

				InputStream byteArrayInputStream = new ByteArrayInputStream(
						audioData);
				AudioFormat audioFormat = Audio.getFormat();
				audioInputStream = new AudioInputStream(byteArrayInputStream,
						audioFormat, audioData.length
								/ audioFormat.getFrameSize());
				DataLine.Info dataLineInfo = new DataLine.Info(
						SourceDataLine.class, audioFormat);
				sourceDataLine = (SourceDataLine) AudioSystem
						.getLine(dataLineInfo);
				sourceDataLine.open(audioFormat);
				sourceDataLine.start();
				System.out.println(sourceDataLine.isRunning()
						+ ": k¿rer sourceDataLine?");
				byte bufferinput[] = new byte[audioData.length];
				System.out.println(bufferinput.length
						+ ": st¿rrelsen af bufferinput f¿r indl¾sning");
				audioInputStream.read(bufferinput);
				System.out.println(bufferinput.length
						+ ": st¿rrelsen af bufferinput efter indl¾sning");
				sourceDataLine.write(bufferinput, 0, bufferinput.length);
				System.out.println(sourceDataLine.available()
						+ ": nummer af bytes ledige f¿r flush.");
				sourceDataLine.flush();
				System.out.println(sourceDataLine.available()
						+ ": nummer af bytes ledige efter flush.");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
