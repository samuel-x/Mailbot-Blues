package exceptions;

/**
 * This exception is thrown when a MailItem is added to a StorageTube which does not have the
 * capacity to hold said MailItem
 */
public class StorageFullException extends Exception {
    public StorageFullException(){
        super("Not enough space in storage!");
    }
}
