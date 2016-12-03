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

   private static byte[] respo = new byte[]{22,23,24,25,26,27,28,29};
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
}