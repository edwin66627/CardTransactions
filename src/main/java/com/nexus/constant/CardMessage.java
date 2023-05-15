package com.nexus.constant;

public class CardMessage {
    // EXCEPTION MESSAGES
    public static final String NO_SUCH_ELEMENT = "Card with %s: %s does not exist";
    public static final String MIN_RECHARGE_AMOUNT = "Minimum balance amount to recharge is 1 USD";
    public static final String NOT_ACTIVATED = "Card is not activated. The transaction cannot be performed";
    public static final String BLOCKED = "Card is blocked. The transaction cannot be performed";
    public static final String EXPIRED = "Card has expired. The transaction cannot be performed";

    // SUCCESSFUL MESSAGES
    public static final String ACTIVATION_DONE = "Card was successfully activated";
    public static final String BLOCK_DONE = "Card was successfully blocked";
    public static final String RECHARGE_DONE = "Balance recharge was done successfully. New Balance is %s USD";
    public static final String CARD_BALANCE = "Card Balance is %S USD";
    public static final String CARD_NUMBER_FIELD = "cardNumber";

}
