package com.nexus.constant;

public class TransactionMessage {
    // EXCEPTION MESSAGES
    public static final String NO_ELEMENT_BY_ID_AND_NUMBER = "Transaction with Id %s and card number %s does not exist";
    public static final String NO_ELEMENT_BY_ID = "Transaction with Id %s does not exist";
    public static final String INVALID_TIME_TO_CANCEL = "Transactions can only be canceled within the first 24 hours after it's accepted";
    public static final String NOT_ENOUGH_BALANCE = "Insufficient balance";

    // SUCCESSFUL MESSAGES
    public static final String SAVE_TRANSACTION_DONE = "Transaction was successful";
    public static final String CANCEL_TRANSACTION_DONE = "Transaction was canceled successfully";


}
