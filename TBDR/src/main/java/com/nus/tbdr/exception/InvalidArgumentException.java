/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.exception;

/**
 *
 * @author Dineli
 */
public class InvalidArgumentException extends TBDRException {

    private String argName = null;

    public InvalidArgumentException() {
        super();
    }

    public InvalidArgumentException(Exception ex) {
        super(ex);
    }

    public InvalidArgumentException(String argName, Exception ex) {
        super(ex);
        setArgName(argName);
    }

    public String getArgName() {
        return argName;
    }

    public void setArgName(String argName) {
        this.argName = argName;
    }

}
