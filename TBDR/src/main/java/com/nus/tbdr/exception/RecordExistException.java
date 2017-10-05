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
public final class RecordExistException extends TBDRException {

    private String name;

    public String getName() {
        return name;
    }

    public RecordExistException() {

    }

    public RecordExistException(String recrodName) {
        setName(recrodName);
    }

    public void setName(String name) {
        this.name = name;
    }

}
