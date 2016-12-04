import java.net.* ;

/**
 *  A simple datagram server
 *  Shows how to send and receive UDP packets in Java
 *
 *  @author  P. Tellenbach, http://www.heimetli.ch
 *  @version V1.01
 */

public class DatagramServer
{
   private final static int PACKETSIZE = 1024 ;
   private static String[] hex = { "59", "45", "52", "43", "20", "00", "34", "00", "03", "01", "01", "00", "00", "00", "00", "80",
		"39", "39", "39", "39", "39", "39", "39", "39", "81", "00", "00", "00", "00", "00", "00", "00", "10",
		"00", "00", "00", "00", "00", "00", "00", "01", "00", "00", "00", "00", "00", "00", "00", "00", "00",
		"00", "00", "fc", "c7", "0e", "00", "7c", "ce", "00", "00", "6b", "19", "05", "00", "ff", "0d", "ed",
		"ff", "d4", "45", "f2", "ff", "b4", "7a", "f7", "ff", "00", "00", "00", "00", "00", "00", "00", "00" };
   private static int[] intCommand = new int[]{};
   private static byte[] respo = new byte[]{};
   public static void main( String args[] )
   {
       //data part
       intCommand = HextoInt(hex);
       
       
      // Check the arguments
      if( args.length != 1 )
      {
         System.out.println( "usage: DatagramServer port" ) ;
         return ;
      }

      try
      {
	  
         // Convert the argument to ensure that is it valid
         int port = Integer.parseInt( args[0] ) ;

         // Construct the socket
         DatagramSocket socket = new DatagramSocket( port ) ;

         System.out.println( "The server is ready..." ) ;
         

         for( ;; )
         {
            // Create a packet
            DatagramPacket packet = new DatagramPacket( new byte[PACKETSIZE], PACKETSIZE ) ;

            // Receive a packet (blocking)
            socket.receive( packet ) ;

            // Print the packet
            System.out.println( packet.getAddress() + " " + packet.getPort() + ": " + new String(packet.getData()) ) ;

            // Return the packet to the sender
            
            //retrive packet's information to send back response
            InetAddress clientIP = packet.getAddress();
            int clientPort = packet.getPort();
            DatagramPacket response = new DatagramPacket(respo,respo.length,clientIP,clientPort );
            socket.send( response ) ;
            System.out.println("Send to client IP : port are "+ clientIP.toString() +" @"+ clientPort);
            System.out.println("Send response is : " + response.toString());
        }  
     }
     catch( Exception e )
     {
        System.out.println( e ) ;
     }
  }
   public static int[] HextoInt(String[] input) {
	String[] hex = input;
	int[] outcome = new int[hex.length];
	for (int i = 0; i < input.length; i++) {
	    int value = Integer.parseInt(input[i], 16);
	    outcome[i] = value;
	}

	return outcome;
   }
   public byte[] swap(byte[] ibytes) {
	byte[] obytes = new byte[ibytes.length];
	for (int i = 0; i < ibytes.length; i++) {
	    if ((i + 1) % 4 == 0 && i != 0) {
		byte[] first = { ibytes[i - 3], ibytes[i - 2] };
		byte[] second = { ibytes[i - 1], ibytes[i] };
		obytes[i - 3] = second[1];
		obytes[i - 2] = second[0];
		obytes[i - 1] = first[1];
		obytes[i] = first[0];
		System.out.println("OBYTES" + i + " : " + obytes[i - 3] + " " + obytes[i - 2] + " " + obytes[i - 1]
			+ " " + obytes[i]);
	    }
	}

	return obytes;
   }

   public int[] stringTointArray(String[] commandByt) {
       DatagramServer datagramServer = new DatagramServer();
	byte[] commandbyteArray = new byte[commandByt.length];
	for (int i = 0; i < commandByt.length; i++) {
	    byte[] e = datagramServer.hexStringToByteArray(commandByt[i]);
	    commandbyteArray[i] = e[0];
	}
	byte[] changedCommandbyteArray = datagramServer.swap(commandbyteArray);
	/*Debug purpose
	Byte[] commandByteArray = byteString.toObjects(changedCommandbyteArray);
	List<Byte> byteList2 = Arrays.asList(commandByteArray);
	System.out.println(byteList2);
	//another swap function : Collections.reverse(byteList2);
	System.out.println(Arrays.toString(commandByteArray));
	 * 
	 */
	// byte[] into int32
	int[] haha = datagramServer.byteToint32(changedCommandbyteArray);
	for (int i : haha) {
	    System.out.println(i);
	}
	return haha;
   }

   public byte[] hexStringToByteArray(String s) {
	int len = s.length();
	byte[] data = new byte[len / 2];
	for (int i = 0; i < len; i += 2) {
	    data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
	}
	return data;
   }

   public int[] byteToint32(byte[] inputByteArray) {
	int[] x = new int[(inputByteArray.length) / 4];
	// System.out.println(x);
	for (int i = 1; i < (inputByteArray.length); i = i + 4) {
	    int offSet = (i - 1);
	    int length = 4;
	    int a = Math.floorDiv(i, 4);
	    // public static ByteBuffer wrap(byte[] array, int offset, int
	    // length) Wraps a byte array into a buffer.
	    x[a] = java.nio.ByteBuffer.wrap(inputByteArray, offSet, length).getInt();
	}
	return x;
   }

}