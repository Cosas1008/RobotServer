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
   private static int[] tool = new int[]{32142,321312,32325,6565,65464,6546}; //randomly generate position
   
   public static void main( String args[] )
   {
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
            System.out.println("IP address : "+ packet.getAddress() + " port :" + packet.getPort() + " / data : " + new String(packet.getData()) ) ;
            InetAddress IPAddress = packet.getAddress();
            int receivedPort = packet.getPort();
            DatagramPacket sendpacket = new DatagramPacket(InttoByteArray(tool), InttoByteArray(tool).length,IPAddress,receivedPort);
            // Return the tool to the sender
            socket.send( sendpacket ) ;
            
            System.out.println("send back "+ sendpacket.getData().toString());
        }  
     }
     catch( Exception e )
     {
        System.out.println( e ) ;
     }
  }
   
   private static int[] byteArrayToInt(byte[] b) {
	if(b != null){
	int[] transfered = new int[(b.length) / 4];
	for (int j = 0; j < (b.length / 4); j++) {
	    transfered[j] = b[(j * 4) + 3] & 0xFF | (b[(j * 4) + 2] & 0xFF) << 8 | (b[(j * 4) + 1] & 0xFF) << 16
		    | (b[(j * 4) + 0] & 0xFF) << 24;
	    
	}
	return transfered;}
	else{
	    return null;
	}
   }

   private static byte[] InttoByteArray(int[] inputIntArray) {
	byte[] transfered = new byte[(inputIntArray.length * 4)];
	for (int j = 0; j < inputIntArray.length; j++) {
	    transfered[(j * 4)] = (byte) (inputIntArray[j] >> 24);
	    transfered[(j * 4) + 1] = (byte) (inputIntArray[j] >> 24);
	    transfered[(j * 4) + 2] = (byte) (inputIntArray[j] >> 24);
	    transfered[(j * 4) + 3] = (byte) (inputIntArray[j] >> 24);
	}
	return transfered;
   }
}