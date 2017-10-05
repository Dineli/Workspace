/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb;

//import static com.nus.pgdb.entity.Users_.salt;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
//import org.bouncycastle.crypto.tls.SignatureAlgorithm;

/**
 *
 * @author Dineli
 */
public class Test {

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        String[] cmd = new String[12];
//        String line, errorStr = "";
//        cmd[0] = "A:\\Workspace\\Pathogen Genomics Data Browser\\PGDB\\src\\main\\resources\\tools\\trimmomatic-0.32.jar"; //jar path
//        cmd[1] = "A:\\Workspace\\Pathogen Genomics Data Browser\\PGDB\\src\\main\\resources\\tools\\adapters.fa";
//        cmd[2] = "PE -phred33 -threads 5";
//        cmd[3] = "CROP:75";
//        cmd[4] = "ILLUMINACLIP:".concat(cmd[1]).concat(":2:30:10");
//        cmd[5] = "LEADING:10 TRAILING:10";
//        cmd[6] = "C:\\Users\\EPHAADK\\Desktop\\PythonTest\\input\\WBB283_S29_L001_R1_001.fastq";
//        cmd[7] = "C:\\Users\\EPHAADK\\Desktop\\PythonTest\\input\\WBB283_S29_L001_R2_001.fastq";
//        cmd[8] = "C:\\Users\\EPHAADK\\Desktop\\PythonTest\\input\\output_forward_paired.fq.gz";
//        cmd[9] = "C:\\Users\\EPHAADK\\Desktop\\PythonTest\\input\\output_forward_unpaired.fq.gz";
//        cmd[10] = "C:\\Users\\EPHAADK\\Desktop\\PythonTest\\input\\output_reverse_paired.fq.gz";
//        cmd[11] = "C:\\Users\\EPHAADK\\Desktop\\PythonTest\\input\\output_reverse_unpaired.fq.gz";
////        ProcessBuilder probuilder = new ProcessBuilder("java ", "-jar", cmd[0], cmd[2], cmd[6], cmd[7], cmd[8], cmd[9], cmd[10], cmd[11], cmd[3], cmd[4], cmd[5]);
//        ProcessBuilder probuilder = new ProcessBuilder("java", "-jar", "A:\\Workspace\\Pathogen Genomics Data Browser\\PGDB\\src\\main\\resources\\tools\\trimmomatic-0.32.jar", "PE",  "-threads", "5", "-phred33",
//                cmd[6], cmd[7], cmd[8], cmd[9], cmd[10], cmd[11], 
//                "CROP:75", "ILLUMINACLIP:\\Workspace\\Pathogen Genomics Data Browser\\PGDB\\src\\main\\resources\\tools\\adapters.fa:2:30:10","LEADING:10", "TRAILING:10");
//
//        try {
//            System.out.println("-------------start-------xx------------");
//            Process process = probuilder.start();
////             System.out.println(probuilder.command());
//            // retrieve output from python script
//            BufferedReader bfr = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            StringBuilder stringBuilder = new StringBuilder();
//            // display each output line form python script
//            while ((line = bfr.readLine()) != null) {
////                System.out.println(line);
//                stringBuilder.append(line).append("\n");
//            }
//            System.out.print(stringBuilder.toString());
//            //Error handling
//            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//            while ((errorStr = stdError.readLine()) != null) {
//                System.out.println(errorStr);
//            }
//            System.out.println("-------------end-------------------");
//        } catch (IOException ex) {
//            Logger.getLogger(PBuilder.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
    public static void main(String[] args) throws FileNotFoundException, IOException {

        String zipFile = "C:\\Users\\Dineli\\Desktop\\archive.zip";

        String[] srcFiles = {"C:\\Users\\Dineli\\Desktop\\dk_R1.txt", "C:\\Users\\Dineli\\Desktop\\dk_R2.txt", "C:\\Users\\Dineli\\Desktop\\hello_R1.txt"};
        System.out.println("-------------xxxx-----------www----------");
        try {

            // create byte buffer
            byte[] buffer = new byte[1024];

            FileOutputStream fos = new FileOutputStream(zipFile);

            ZipOutputStream zos = new ZipOutputStream(fos);

            for (int i = 0; i < srcFiles.length; i++) {

                File srcFile = new File(srcFiles[i]);

                FileInputStream fis = new FileInputStream(srcFile);

                // begin writing a new ZIP entry, positions the stream to the start of the entry data
                zos.putNextEntry(new ZipEntry(srcFile.getName()));

                int length;

                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }

                zos.closeEntry();

                // close the InputStream
                fis.close();

            }

            // close the ZipOutputStream
            zos.close();

        } catch (IOException ioe) {
           ioe.printStackTrace();
//            System.out.println("Error creating zip file: " + ioe);
        }

    }



//    public String createToken(String mail) {
//        Claims claims = Jwts.claims().setSubject(mail);
//        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
//        claims.put("mailId", mail);
//        Date currentTime = new Date();
//        currentTime.setTime(currentTime.getTime() + tokenExpiration * 60000);
//        return Jwts.builder()
//                .setClaims(claims)
//                .setExpiration(currentTime)
//                .signWith(SignatureAlgorithm.HS512, salt.getBytes())
//                .compact();
//    }
//
//    public String readMailIdFromToken(String token) {
//        Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(token).getSignature();
//        Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(token);
//        return parseClaimsJws.getBody().getSubject();
//    }
}
