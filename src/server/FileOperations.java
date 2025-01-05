package server;

import data.database.Database;
import data.database.Player;

import java.io.*;
import java.util.List;

public class FileOperations {
    private String inputFileName;
    private String outputFileName;

    public FileOperations() {
        inputFileName = "players.txt";
        outputFileName = "players.txt";
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public void readFromFile(Database db) throws IOException 
    {
        BufferedReader br = new BufferedReader(new FileReader(inputFileName));

        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String[] tokens = line.split(",");

            Player player = new Player();
            player.setName(tokens[0]);
            player.setCountry(tokens[1]);
            player.setAge(Integer.parseInt(tokens[2]));
            player.setHeight(Double.parseDouble(tokens[3]));
            player.setClub(tokens[4]);
            player.setPosition(tokens[5]);
            if(tokens[6] != "")
            {
                player.setJerseyNum(Integer.parseInt(tokens[6]));                
            }
            else
            {
                player.setJerseyNum(-1);
            }
            player.setWeeklySalary(Integer.parseInt(tokens[7]));

            int check = db.addPlayer(player);
        }
        br.close();
    }

    public void writeToFile(List<Player> players) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName));

        for (Player player : players) {
            bw.write(player.toFileFormat());
            bw.newLine();
        }
        bw.close();
    }
}

