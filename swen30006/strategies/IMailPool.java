package strategies;

import automail.BuildingSector;
import automail.MailItem;
import automail.Storage;

/**
 * addToPool is called when there are mail items newly arrived at the building to add to the MailPool or
 * if a robot returns with some undelivered items - these are added back to the MailPool.
 * The data structure and algorithms used in the MailPool is your choice.
 * 
 */
public interface IMailPool {

    /**
     * Adds an item to the mail pool
     * @param mailItem the mail item being added.
     */
    void addToPool(MailItem mailItem);
    
    /**
     * @param storage refers to the pack the robot uses to deliver mail.
     * @param sector is where the robot serves in the building.
     */
    void fillStorage(Storage storage, BuildingSector sector);
}
