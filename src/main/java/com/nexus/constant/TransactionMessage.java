package com.nexus.constant;

public class TransactionMessage {
    // EXCEPTION MESSAGES
    public static final String NO_ELEMENT_BY_TRANSACTION_NUMBER_AND_NUMBER = "Transaction with number %s and card number %s does not exist";
    public static final String NO_ELEMENT_BY_ID = "Transaction with Id %s does not exist";
    public static final String NO_ELEMENT_BY_TRANSACTION_NUMBER = "Transaction with number %s does not exist";
    public static final String INVALID_TIME_TO_CANCEL = "Transactions can only be canceled within the first 24 hours after it's accepted";
    public static final String NOT_ENOUGH_BALANCE = "Insufficient balance";
    public static final String TRANSACTION_ALREADY_CANCELED = "Transaction is already canceled and funds returned to the card";

    // SUCCESSFUL MESSAGES
    public static final String SAVE_TRANSACTION_DONE = "Transaction was successful. For more information, use transaction number: %s";
    public static final String CANCEL_TRANSACTION_DONE = "Transaction was canceled successfully";


}
