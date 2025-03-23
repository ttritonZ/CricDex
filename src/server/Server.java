package server;

import data.database.Database;
import data.database.Player;
import util.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {
    volatile Database db;
    private FileOperations fileOperations;
    volatile private List<Player> transferPlayerList;
    volatile HashMap<String, ClientInfo> clientMap;

    public static void main(String[] args) {
        int port = 45045;
        new Server(port);
    }

    public Server(int port) {
        clientMap = new HashMap<>();
        transferPlayerList = new ArrayList<>();
        ServerSocket serverSocket = null;
        try {
            loadDatabase();
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    synchronized public List<Player> getTransferPlayerList() {
        return transferPlayerList;
    }

    private void loadDatabase() throws IOException {
        db = new Database();
        fileOperations = new FileOperations();
        fileOperations.readFromFile(db);
        for (Player player : db.getPlayerList())
        {
            System.out.println(player);
        }
    }

    private void serve(Socket socket) throws IOException {
        NetworkUtil networkUtil = new NetworkUtil(socket);
        new ThreadServer(networkUtil, this);
    }

    synchronized public boolean sellPlayer(String playerName, String newClubName) {
        boolean b = false;
        try {
            Player player = db.searchByName(playerName);
            if (player.isInTransferList()) {
                transferPlayerList.remove(player);
                player.setInTransferList(false);
                player.setClub(newClubName);
                b = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    synchronized public boolean addToTransferWindow(String playerName, int playerPrice) {
        boolean b = false;
        try {
            db.removePlayerFromClub(playerName);
            Player player = db.searchByName(playerName);
            player.setPrice(playerPrice);
            player.setInTransferList(true);
            transferPlayerList.add(player);
            b = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    synchronized public boolean registerClub(String clubName, String password, NetworkUtil networkUtil) {
                for (String clientName: clientMap.keySet()) {
                    if (clientName.equalsIgnoreCase(clubName)) {
                        return false;
                    }
                }
                ClientInfo clientInfo = new ClientInfo();
                clientInfo.setClubName(clubName);
                clientInfo.setPassword(password);
                clientInfo.setNetworkUtil(networkUtil);
                clientInfo.setLoggedIn(false);
                clientMap.put(clubName, clientInfo);
                return true;
            }

    synchronized public boolean loginClub(String username, String password) {
        if (clientMap.containsKey(username) && clientMap.get(username).getPassword().equals(password) && !clientMap.get(username).isLoggedIn()) {
            clientMap.get(username).setLoggedIn(true);
            return true;
        }
        return false;
    }

    synchronized public boolean changePassword(String username, String oldPassword, String newPassword) {
        if (clientMap.containsKey(username) && clientMap.get(username).getPassword().equals(oldPassword) && clientMap.get(username).isLoggedIn()) {
            clientMap.get(username).setPassword(newPassword);
            return true;
        }
        return false;
    }

    synchronized public boolean logoutClub(String username) {
        if (clientMap.containsKey(username) && clientMap.get(username).isLoggedIn()) {
            clientMap.get(username).setLoggedIn(false);
            return true;
        }
        return false;
    }

    synchronized public List<String> sendClubList() {
        List<String> clubList = new ArrayList<>();
        db.getClubList().forEach(e -> clubList.add(e.getName()));
        return clubList;
    }
}
