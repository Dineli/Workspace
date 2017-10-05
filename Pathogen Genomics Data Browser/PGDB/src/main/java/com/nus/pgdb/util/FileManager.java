/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dineli
 */
public class FileManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileManager.class);

    public static String getFileName(int userId, String currentDateTime) {
        String fileName = userId + "_" + currentDateTime.concat(".txt");
        return fileName;
    }

    // Get a File Path and create the file as per the given name and return the fully qualified file path
    public static String createFile(int userId, String fileName, String location) {
        File dir = new File(Constants.DIRECTORY_LOCATION + userId);

        File file = (!location.isEmpty()) ? new File(location.concat(File.separator).concat(fileName)) : new File(dir + File.separator + fileName);
        try {
            // if dir not exists
            if (!dir.exists()) {
                if (file.getParentFile().mkdir()) { //creates dir
                    file.createNewFile(); //creates file
                    LOGGER.info("Directory and file created successfully");
                }
            } else {
                file.createNewFile();
                LOGGER.info("Directory exists. File got created successfully");
            }
        } catch (IOException ex) { //creates file
            LOGGER.error("Failed to create directory" + ex.getMessage());
        }
        return file.toString();
    }

    //folder will contain exactly 1 file when executing the check
    public static String getFileName(File folderPath) {
        String fileName = folderPath.listFiles()[0].getName();
        return fileName;
    }

    public static String createDir2StoreFastqs(int userId, String sampleName) {
        File userSampleDir = new File(Constants.DIRECTORY_LOCATION + userId + File.separator + sampleName);
        LOGGER.info("Checking if the folder path exists to store the files....");
        if (!userSampleDir.exists()) {
            LOGGER.info("Directory does not exists...creating a new directory to store the files");
            userSampleDir.mkdirs(); //creates the missing directories
        } else {
            LOGGER.info("Folder path already exists");
        }
        return userSampleDir.toString();
    }

    public static int countFiles(String folderPath) {
        int count = (new File(folderPath).listFiles() != null) ? new File(folderPath).listFiles().length : 0;
        return count;
    }

    public static void deleteFile(String folderPath) {
        File file = new File(folderPath);

        if (file.delete()) {
            LOGGER.info("File deleted successfully");
        } else {
            LOGGER.error("Error occured while removing file from path");
        }
    }

    public static void copyFiles(File source, File destination) {
        try {
            FileUtils.copyFileToDirectory(source, destination);
            LOGGER.info("Input files copied successfully to the new folder");
        } catch (IOException e) {
            LOGGER.error("Error occured while coping files to new folder" + e.getMessage());
        }
    }

    public static String createDir(int userId, String folderName, String location) {
        File newDir = null;
        //creates folder on a given location
        if (!location.isEmpty()) {
            newDir = new File(location + File.separator + folderName);
            if (newDir.mkdir()) {
                LOGGER.info("Folder created successfully");
            }
        } else {
            newDir = new File(Constants.DIRECTORY_LOCATION + userId + File.separator + folderName);
            if (newDir.mkdir()) {
                LOGGER.info("Folder created successfully");
            } else if (newDir.exists()) {
                LOGGER.info("Folder already exists, deleting files if exists");
                deleteFolderContent(newDir);
            } else {
                LOGGER.error("Error occured while creating folder");
            }
        }
        return newDir.toString();
    }

    public static void deleteFolderContent(File folderName) {
        String files[] = folderName.list();
        for (String temp : files) {
            File fileDelete = new File(folderName, temp);
            fileDelete.delete();
        }
    }

    public static File[] fetchPairedFiles(String path) {
        File fileTrimmomatic = new File(path);
        File[] foundFiles = fileTrimmomatic.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith("paired_");
            }
        });
        return foundFiles;
    }

    //deletes files, folders, sub folders with content
    public static void deleteFolder(String absolutePath) {
        File directory = new File(absolutePath);
        if (directory.isDirectory()) {
            //directory is empty, then delete it
            if (directory.list().length == 0) {
                directory.delete();
                LOGGER.info("Folder deleted successfully" + directory.getAbsolutePath());
            } else {
                String files[] = directory.list();
                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(directory, temp);
                    //recursive delete
                    deleteFolder(fileDelete.toString());
                }
                //check the directory again, if empty then delete it
                if (directory.list().length == 0) {
                    directory.delete();
                    LOGGER.info("Folder deleted successfully" + directory.getAbsolutePath());
                }
            }
        } else {
            //if file, then delete it
            directory.delete();
            LOGGER.info("File deleted successfully" + directory.getAbsolutePath());
        }
    }

    public static void removeFilesNFoldersFromPhysicalLocation(String absoluteFilePath) {
        //returns the path upto sample folder -> Ex: A:\\Workspace\\Pathogen Genomics Data Browser\\UserLogs\\40053\\Sample_1
        int lastIndex = absoluteFilePath.lastIndexOf(File.separator);
        FileManager.deleteFolder(absoluteFilePath.substring(0, lastIndex));
    }

    public static boolean moveFile(String fileToMove, String location) {
        File from = new File(fileToMove);
        boolean moveStatus = false;
        try{
        if (from.renameTo(new File(location + File.separator + from.getName()))) {
            moveStatus = true;
            LOGGER.info("File " + from.getName() + " moved successfully ");
        } else {
            LOGGER.error("Failed to move file!!!");
        }
        }catch(Exception e){
    		e.printStackTrace();
    	}
        return moveStatus;
    }
}
