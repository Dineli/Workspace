/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dineli
 */
public class PBuilder {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String pythonScriptPath = "A:\\Workspace\\Pathogen Genomics Data Browser\\PGDB\\src\\main\\webapp\\scripts\\run_workflow.py";
        String[] cmd = new String[8];
        String line, errorStr = "";
        cmd[0] = "C:\\Python27\\python.exe"; //Python location
        cmd[2] = "-u"; //flushes the .py script in order to get the print statements as and when it happens
        cmd[1] = pythonScriptPath; //Python script location
        cmd[3] = "C:\\Users\\EPHAADK\\Desktop\\PythonTest\\input_1"; //Param 1
        cmd[4] = "C:\\Users\\EPHAADK\\Desktop\\PythonTest\\output"; //Param 2
        cmd[5] = "C:\\Users\\EPHAADK\\Desktop\\PythonTest\\log.txt"; //Param 3
        cmd[6] = "C:\\Users\\EPHAADK\\Desktop\\PythonTest\\params.json"; //Param 4
        cmd[7] = "A:\\Workspace\\Pathogen Genomics Data Browser\\PGDB\\src\\main\\webapp\\scripts\\workflow\\params_dict.json"; //Param 5
        System.out.println("==============cmd[7] ========="+cmd[7] );
        ProcessBuilder probuilder = new ProcessBuilder(cmd[0], cmd[2], cmd[1], cmd[3], cmd[4], cmd[5], cmd[6], cmd[7]);
        try {
            Process process = probuilder.start();
              System.out.println("---------aaaaaaaaa---33---------");
              System.out.println("---------aaaaaaaaa------------");
            System.out.println(probuilder.command());
            // retrieve output from python script
            BufferedReader bfr = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            // display each output line form python script
            while ((line = bfr.readLine()) != null) {
//                System.out.println(line);
                stringBuilder.append(line).append("\n");
            }
            System.out.print(stringBuilder.toString());
            //Error handling
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((errorStr = stdError.readLine()) != null) {
                System.out.println(errorStr);
            }
        } catch (IOException ex) {
            Logger.getLogger(PBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
