/*
 * 
 */
package com.francetelecom.admindm.getRPCMethods;
import java.util.ArrayList;
import java.util.List;
import com.francetelecom.admindm.api.EventBehavior;
import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.RPCMethodMngService;

/**
 * The Class MockRPCMethodsManagement.
 */
public class MockRPCMethodsManagement implements RPCMethodMngService {
    
    /**
     * Gets the rpc method.
     * @return the RPC method
     */
    public List getRPCMethod() {
        List lsMethods = new ArrayList();
        lsMethods.add("test1");
        lsMethods.add("test2");
        return lsMethods;
    }
    
    /**
     * Register event behavior.
     * @param name the name
     * @param eventBehavior the event behavior
     */
    public void registerEventBehavior(String name, EventBehavior eventBehavior) {
    }
    
    /**
     * Register rpc decoder.
     * @param name the name
     * @param decoder the decoder
     */
    public void registerRPCDecoder(String name, RPCDecoder decoder) {
    }
    
    /**
     * Register rpc encoder.
     * @param name the name
     * @param encoder the encoder
     */
    public void registerRPCEncoder(String name, RPCEncoder encoder) {
    }
    
    /**
     * Register rpc method.
     * @param name the name
     */
    public void registerRPCMethod(String name) {
    }
    
    /**
     * Unregister event behavior.
     * @param name the name
     */
    public void unregisterEventBehavior(String name) {
    }
    
    /**
     * Unregister rpc decoder.
     * @param name the name
     */
    public void unregisterRPCDecoder(String name) {
    }
    
    /**
     * Unregister rpc encoder.
     * @param name the name
     */
    public void unregisterRPCEncoder(String name) {
    }
    
    /**
     * Unregister rpc method.
     * @param name the name
     */
    public void unregisterRPCMethod(String name) {
    }
    
    /**
     * Find rpc method decoder.
     * @param value the value
     * @return the RPC decoder
     */
    public RPCDecoder findRPCMethodDecoder(String value) {
        return null;
    }
    
    /**
     * Find rpc method encoder.
     * @param method the method
     * @return the RPC encoder
     */
    public RPCEncoder findRPCMethodEncoder(RPCMethod method) {
        return null;
    }
    
    /**
     * Find rpc method encoder.
     * @param name the name
     * @return the RPC encoder
     */
    public RPCEncoder findRPCMethodEncoder(String name) {
        return null;
    }
}
