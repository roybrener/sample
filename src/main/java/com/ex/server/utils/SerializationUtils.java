package com.ex.server.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by roy on 04/02/2015.
 */
public class SerializationUtils {

    public static String serialize(Object state){

        ByteArrayOutputStream bos = new ByteArrayOutputStream(512);
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)){
            oos.writeObject(state);
            oos.flush();

            return bos.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T deserialize(String s){
        final byte[] bytes = s.getBytes();

        ObjectInputStream oip = null;
        try {
            oip = new ObjectInputStream(new ByteArrayInputStream(bytes));
            @SuppressWarnings("unchecked")
            T result = (T) oip.readObject();
            return result;
        }
        catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (oip != null) {
                try {
                    oip.close();
                }
                catch (IOException e) {
                    // eat it
                }
            }
        }
    }

    public static byte[] serializeToByteArray(Object state){

        ByteArrayOutputStream bos = new ByteArrayOutputStream(512);
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)){
            oos.writeObject(state);
            oos.flush();

            return bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T deserializeFromByte(byte [] bytes){
        try (ObjectInputStream oip = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            @SuppressWarnings("unchecked")
            T result = (T) oip.readObject();

            return result;
        }
        catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
