package fr.blueslime.roguecraft.network;

import fr.blueslime.roguecraft.RogueCraft;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketListener implements Runnable
{
    private RogueCraft plugin;

    private ServerSocket socketserver;
    private Socket socket;
    private BufferedReader in = null;
    private PrintWriter out = null;

    private String Auth;
    private String buff;

    private boolean run = true;

    public SocketListener(RogueCraft plugin, ServerSocket sock)
    {
        this.plugin = plugin;
        socketserver = sock;
    }

    public void run()
    {
        try
        {
            while (run)
            {
                socket = socketserver.accept(); // Un client se connecte on l'accepte

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream());

                Auth = in.readLine();
                
                if (!Auth.equalsIgnoreCase("56465894869dsfg")) 
                {
                    socket.close();
                    continue;
                }

                buff = in.readLine();

                out.println(plugin.getNetworkManager().InputMessage(buff));
                out.flush();

                socket.close();
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void stop()
    {
        run = false;
    }
}
