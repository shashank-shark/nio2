package selectors;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SelectorServer {

    public static int port = 9999;
    public static ByteBuffer timeByteBuffer = ByteBuffer.allocateDirect(8);

    public static void main(String[] args) throws IOException {

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ServerSocket sc = ssc.socket();
        sc.bind(new InetSocketAddress(port));;
        ssc.configureBlocking(false);

        Selector s = Selector.open();
        ssc.register(s,
                SelectionKey.OP_ACCEPT);


        while (true) {

            int numberOfChannelsSelected = s.select();
            if (numberOfChannelsSelected == 0)
                continue;

            Iterator<SelectionKey> it = s.selectedKeys().iterator();

            while (it.hasNext()) {
                SelectionKey selectionKey = it.next();

                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
                    if (socketChannel == null)
                        continue;

                    System.out.println("Recieving Connection ..");
                    timeByteBuffer.clear();
                    timeByteBuffer.putLong(System.currentTimeMillis());
                    timeByteBuffer.flip();
                    System.out.println ("Writing time stream to client .");

                    while (timeByteBuffer.hasRemaining()) {
                        socketChannel.write(timeByteBuffer);
                    }

                    System.out.println("Time sent to client.");
                    socketChannel.close();
                }

                it.remove();
            }
        }
    }
}
