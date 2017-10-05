/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.util;

/**
 *
 * @author Dineli
 */
public class Constants {

    public static final String DIRECTORY_LOCATION = "A:\\Workspace\\Pathogen Genomics Data Browser\\UserLogs\\";
    public static final String SUBMIT = "0";
    public static final String EXECUTE = "1";
    public static final String SUCCESS = "2";
    public static final String FAIL = "-1";
    public static final String DOWNLOADED = "3";
    public static final String PYTHON_LOCATION = "C:\\Python27\\python.exe";
    public static final String PYTHON_SCRIPT_LOCATION = "A:\\Workspace\\Pathogen Genomics Data Browser\\PGDB\\src\\main\\webapp\\scripts\\run_workflow.py";
    public static final String PYTHON_ARGUMENT = "-u";
    public static final String GALAXY_DICTIONARY_PATH = "A:\\Workspace\\Pathogen Genomics Data Browser\\PGDB\\src\\main\\webapp\\scripts\\workflow\\params_dict.json";
    public static final String JAR_PATH = "A:\\Workspace\\Pathogen Genomics Data Browser\\PGDB\\src\\main\\resources\\tools\\trimmomatic-0.32.jar";
    public static final String ADAPTER_PATH = "\\Workspace\\Pathogen Genomics Data Browser\\PGDB\\src\\main\\resources\\tools\\adapters.fa";
    public static final String TRIMMO_ARG_1 = "CROP:75";
    public static final String TRIMMO_ARG_2 = "ILLUMINACLIP:".concat(ADAPTER_PATH).concat(":2:30:10");
    public static final String TRIMMO_ARG_3 = "LEADING:10";
    public static final String TRIMMO_ARG_4 = "TRAILING:10";
    public static final String GMAIL_MAIL_SMTP_HOST = "smtp.gmail.com";
    public static final String GMAIL_MAIL_SMTP_PORT = "587";
    public static final String GMAIL_MAIL_USERNAME = "pgdb.info";
    public static final String GMAIL_MAIL_PASSWORD = "admin_0517";
    public static final String MAIL_SENDER = "pgdb.info@gmail.com";
    public static final String HOME_REDIRECT_URL = "http://localhost:9090/pgdb/home.jsp";
    public static final String SITE_URL = "http://localhost:9090/pgdb";
    public static final String SITE_URL_WITH_TOKEN = "http://localhost:9090/pgdb/change?token=";
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";

/*
    settings for production server
    
    public static final String DIRECTORY_LOCATION = "/data/pgdb/users/";
    public static final String SUBMIT = "0";
    public static final String EXECUTE = "1";
    public static final String SUCCESS = "2";
    public static final String FAIL = "-1";
    public static final String DOWNLOADED = "3";
    public static final String PYTHON_LOCATION = "/usr/bin/python";
    public static final String PYTHON_SCRIPT_LOCATION = "/data/pgdb/scripts/run_workflow.py";
    public static final String PYTHON_ARGUMENT = "-u";
    public static final String GALAXY_DICTIONARY_PATH = "/data/pgdb/scripts/workflow/params_dict.json";
    public static final String JAR_PATH = "/data/pgdb/tools/trimmomatic-0.32.jar";
    public static final String ADAPTER_PATH = "/data/pgdb/tools/adapters.fa";
    public static final String TRIMMO_ARG_1 = "CROP:75";
    public static final String TRIMMO_ARG_2 = "ILLUMINACLIP:".concat(ADAPTER_PATH).concat(":2:30:10");
    public static final String TRIMMO_ARG_3 = "LEADING:10";
    public static final String TRIMMO_ARG_4 = "TRAILING:10";
    public static final String GMAIL_MAIL_SMTP_HOST = "smtp.gmail.com";
    public static final String GMAIL_MAIL_SMTP_PORT = "587";
    public static final String GMAIL_MAIL_USERNAME = "pgdb.info";
    public static final String GMAIL_MAIL_PASSWORD = "admin_0517";
    public static final String MAIL_SENDER = "pgdb.info@gmail.com";
    public static final String HOME_REDIRECT_URL = "http://phg.nus.edu.sg/pgdb/home.jsp";
    public static final String SITE_URL = "http://phg.nus.edu.sg/pgdb";
    public static final String SITE_URL_WITH_TOKEN = "http://phg.nus.edu.sg/pgdb/change?token=";
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
*/

}
