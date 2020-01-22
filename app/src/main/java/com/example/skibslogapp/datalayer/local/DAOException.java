package com.example.skibslogapp.datalayer.local;

/**
 * A custom exception class used in all the DAO classes
 */
public class DAOException extends RuntimeException {
    public DAOException(String msg){
        super(msg);
    }
}
