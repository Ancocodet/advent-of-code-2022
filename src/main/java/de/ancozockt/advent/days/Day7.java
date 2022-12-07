package de.ancozockt.advent.days;

import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AInputData(day = 7, year = 2022)
public class Day7 implements IAdventDay {

    @Override
    public String part1(BufferedReader bufferedReader) {
        HashMap<String, Directory> directories = new HashMap<>();
        List<String> lines = bufferedReader.lines().toList();

        String currentDirectory = "";
        for(String line : lines){
            if(line.startsWith("$")){
                String command = line.replace("$ ", "");
                String[] commandData = command.split(" ");
                if(commandData[0].equalsIgnoreCase("cd")){
                    if(commandData[1].equalsIgnoreCase("..")){
                        currentDirectory = currentDirectory.substring(0, currentDirectory.lastIndexOf("/"));
                    } else {
                        String lastDirectory = currentDirectory;
                        Directory directory = directories.getOrDefault(currentDirectory, new Directory(currentDirectory));
                        if(currentDirectory.isEmpty()){
                            currentDirectory = commandData[1];
                        } else {
                            currentDirectory = currentDirectory + "/" + commandData[1];

                            Directory newDirectory = new Directory(commandData[1]);
                            directories.put(currentDirectory, newDirectory);
                            directory.addSubDirectory(newDirectory);

                            directories.put(lastDirectory, directory);
                        }
                    }
                }
            } else {
                if(!line.startsWith("dir")){
                    String[] fileData = line.split(" ");

                    int fileSize = Integer.parseInt(fileData[0]);
                    String fileName = fileData[1];

                    Directory directory = directories.getOrDefault(currentDirectory, new Directory(currentDirectory));

                    directory.addFileData(new FileData(fileName, fileSize));

                    directories.put(currentDirectory, directory);
                }
            }
        }

        return String.valueOf(directories.values().stream().filter(directory -> directory.getSize() <= 100000).mapToInt(Directory::getSize).sum());
    }

    @Override
    public String part2(BufferedReader bufferedReader) {
        HashMap<String, Directory> directories = new HashMap<>();
        List<String> lines = bufferedReader.lines().toList();

        String currentDirectory = "";
        for(String line : lines){
            if(line.startsWith("$")){
                String command = line.replace("$ ", "");
                String[] commandData = command.split(" ");
                if(commandData[0].equalsIgnoreCase("cd")){
                    if(commandData[1].equalsIgnoreCase("..")){
                        currentDirectory = currentDirectory.substring(0, currentDirectory.lastIndexOf("/"));
                    } else {
                        String lastDirectory = currentDirectory;
                        Directory directory = directories.getOrDefault(currentDirectory, new Directory(currentDirectory));
                        if(currentDirectory.isEmpty()){
                            currentDirectory = commandData[1];
                        } else {
                            currentDirectory = currentDirectory + "/" + commandData[1];

                            Directory newDirectory = new Directory(commandData[1]);
                            directories.put(currentDirectory, newDirectory);
                            directory.addSubDirectory(newDirectory);

                            directories.put(lastDirectory, directory);
                        }
                    }
                }
            } else {
                if(!line.startsWith("dir")){
                    String[] fileData = line.split(" ");

                    int fileSize = Integer.parseInt(fileData[0]);
                    String fileName = fileData[1];

                    Directory directory = directories.getOrDefault(currentDirectory, new Directory(currentDirectory));

                    directory.addFileData(new FileData(fileName, fileSize));

                    directories.put(currentDirectory, directory);
                }
            }
        }

        int currentSpace = 70000000 - directories.get("/").getSize();
        int neededSpace = 30000000 - currentSpace;

        Directory toDelete = directories.values().stream().filter(directory -> directory.getSize() >= neededSpace).sorted((o1, o2) -> {
            if(o2.getSize() == o1.getSize()){
                return 0;
            }else if(o2.getSize() > o1.getSize()) {
                return -1;
            } else {
                return 1;
            }
        }).findFirst().get();

        return String.valueOf(toDelete.getSize());
    }

    @Getter
    public static class Directory {

        private String name;
        private List<Directory> subDirectories;
        private List<FileData> files;

        public Directory(String name){
            this.name = name;
            this.subDirectories = new ArrayList<>();
            this.files = new ArrayList<>();
        }

        public void addSubDirectory(Directory directory){
            this.subDirectories.add(directory);
        }

        public void addFileData(FileData fileData){
            this.files.add(fileData);
        }

        public int getSize(){
            int size = 0;
            for(FileData fileData : files){
                size += fileData.getSize();
            }
            for(Directory directory : subDirectories){
                size += directory.getSize();
            }
            return size;
        }

    }

    @AllArgsConstructor @Getter
    public static class FileData {

        private String name;
        private int size;

    }
}
