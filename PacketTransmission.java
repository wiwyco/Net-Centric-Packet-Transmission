/*
 * Winslow Conneen
 * 9-15-21
 * COSC 4360 Assignment 1
 * The goal of this assignment is to simulate the transmission of a packet of information between two users
 * through the transport, network, and link layers, with the inclusion of encoding and decoding said 
 * message to and from bytes.
 */


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class PacketTransmission {

	public static void main( String [] args)
	{
		
		String senderInput = "";
		String senderTransportSegment = "";
		String senderNetworkDatagram = "";
		String senderLinkFrame = "";
		byte [] physicalBits;
		String recieverLinkFrame = "";
		String recieverNetworkDatagram = "";
		String recieverTransportSegment = "";
		String recieverOutput = "";
		
		senderInput = "My name is Winslow Conneen";
		
		//calling of each method for encoding, translation, and decoding in sequential order
		senderTransportSegment = createTransportSegment(senderInput);
		senderNetworkDatagram = createNetworkDatagram(senderTransportSegment);
		senderLinkFrame = createLinkFrame(senderNetworkDatagram);
		physicalBits = createPhysicalBits(senderLinkFrame);
		recieverLinkFrame = extractLinkFrame(physicalBits);
		recieverNetworkDatagram = extractNetworkDatagram(recieverLinkFrame);
		recieverTransportSegment = extractTransportSegment(recieverNetworkDatagram);
		recieverOutput = extractApplicationMessage(recieverTransportSegment);
		
		//Printing The data from the senders side
		System.out.println("Sender's Data From Different Layers\n"
				+ "===================================\n"
				+ "Application layer message: \"" + senderInput + "\" of length " + senderInput.length() + "\n"
				+ "Transport layer segment: " + senderTransportSegment + "\n"
				+ "Network layer datagram: " + senderNetworkDatagram + "\n"
				+ "Link layer frame: " + senderLinkFrame + "\n"
				+ "Physical layer bits:" 
				);
		
		//This section is for parceling the byte string into multiple 20 element chunks in an array
		//definition of array
		byte [][] physicalBitsSegments = new byte [(int) Math.floor((physicalBits.length)/20) + 1][20];
		
		//fills the first n-1 lines of bytes
		int x = 0;
		for (int i = 0; i < (int) Math.floor((physicalBits.length)/20); i++)
		{
			for (int j = 0; j < 20; j++)
			{
				physicalBitsSegments[i][j] = physicalBits[x];
				x++;
			}
		}
		
		//fills the last line, line n
		for (int i = 0; i < (physicalBits.length % 20); i++)
		{
			physicalBitsSegments[(int) Math.floor((physicalBits.length)/20)][i] = physicalBits[x];
			x++;
		}
		
		//Prints bit arrays up to final one
		for (int i = 0; i < physicalBitsSegments.length - 1; i++)
		{
			for (int j = 0; j < 20; j++)
			{
				System.out.print(physicalBitsSegments[i][j] + " ");
			}
			System.out.print("\n");
		}
		
		//Prints last bit array line
		for (int i = 0; i < (physicalBits.length % 20); i++)
		{
			System.out.print(physicalBitsSegments[(int) Math.floor((physicalBits.length)/20)][i] + " ");
		}
		
		//Begins Reciever's send and recieve report
		System.out.println("\n\nReciever's Data From Different Layers\n"
				+ "===================================\n"
				+ "Physical layer bits:");
		
		//Prints first n-1 lines of bits
		for (int i = 0; i < physicalBitsSegments.length - 1; i++)
		{
			for (int j = 0; j < 20; j++)
			{
				System.out.print(physicalBitsSegments[i][j] + " ");
			}
			System.out.print("\n");
		}
		
		//Prints last line of bits
		for (int i = 0; i < (physicalBits.length % 20); i++)
		{
			System.out.print(physicalBitsSegments[(int) Math.floor((physicalBits.length)/20)][i] + " ");
		}
		
		
		System.out.println("\nLink layer frame: " + recieverLinkFrame + "\n"
				+ "Network layer datagram: " + recieverNetworkDatagram + "\n"
				+ "Transport layer segment: " + recieverTransportSegment + "\n"
				+ "Application layer message: \"" + recieverOutput + "\" of length " + recieverOutput.length());
	}
	
	//generates the transport segment 
	public static String createTransportSegment(String input)
	{
		int sumLength;
		String layerName = "TRANSPORT";
		
		//allows for strings up to 1000 characters in length
		//avoids issue with different sized in-string length quantifiers
		if (input.length() + layerName.length() > 996)
		{
			sumLength = 4;
		}
		else if (input.length() + layerName.length() > 97)
		{
			sumLength = 3;
		}
		else if (input.length() + layerName.length() > 8)
		{
			sumLength = 2;
		}
		else
		{
			sumLength = 1;
		}
		
		return Integer.toString(input.length() + sumLength + layerName.length()) + layerName + input;
	}
	//generates network datagram
	public static String createNetworkDatagram(String input)
	{
		int sumLength;
		String layerName = "NETWORK";

		//allows for strings up to 1000 characters in length
		//avoids issue with different sized in-string length quantifiers
		if (input.length() + layerName.length() > 996)
		{
			sumLength = 4;
		}
		else if (input.length() + layerName.length() > 97)
		{
			sumLength = 3;
		}
		else if (input.length() + layerName.length() > 8)
		{
			sumLength = 2;
		}
		else
		{
			sumLength = 1;
		}
		
		return Integer.toString(input.length() + sumLength + layerName.length()) + layerName + input;
	}
	//generates link frame
	public static String createLinkFrame(String input)
	{
		int sumLength;
		String layerName = "LINK";

		//allows for strings up to 1000 characters in length
		//avoids issue with different sized in-string length quantifiers
		if (input.length() + layerName.length() > 996)
		{
			sumLength = 4;
		}
		else if (input.length() + layerName.length() > 97)
		{
			sumLength = 3;
		}
		else if (input.length() + layerName.length() > 8)
		{
			sumLength = 2;
		}
		else
		{
			sumLength = 1;
		}
		
		return Integer.toString(input.length() + sumLength + layerName.length()) + layerName + input;
	}

	//converts link layer frame to bits
	public static byte[] createPhysicalBits(String input)
	{
		Charset cs = StandardCharsets.US_ASCII;
		
		byte [] output = cs.encode(input).array();
		return output;
	}
	
	//Translates the bits back to US_ASCII
	public static String extractLinkFrame(byte[] input)
	{
		String output = new String(input);
		return output;
	}
	
	//Removes Link layer portion of message
	public static String extractNetworkDatagram(String input)
	{
		int index = input.indexOf("LINK") + 4;
		return input.substring(index);
	}
	
	//Removes Network portion of message
	public static String extractTransportSegment(String input)
	{
		int index = input.indexOf("NETWORK") + 7;
		return input.substring(index);
	}
	
	//Removes transport portion of message
	public static String extractApplicationMessage(String input)
	{
		int index = input.indexOf("TRANSPORT") + 9;
		return input.substring(index);
	}
}
