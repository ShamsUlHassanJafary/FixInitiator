package com.example.fixinitiator.Initiator;

import org.springframework.stereotype.Component;

import quickfix.Application;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;

@Component
public class InitiatorApplication implements Application {

    public static volatile SessionID sessionID;

    @Override
    public void onCreate(SessionID sessionID) {
        System.out.println(String.format("Initiator created : {%s}", sessionID));
    }

    @Override
    public void onLogon(SessionID sessionID) {

        InitiatorApplication.sessionID = sessionID;
        System.out.println(String.format("Initiator onLogon : {%s}", sessionID));
    }

    @Override
    public void onLogout(SessionID sessionID) {

        InitiatorApplication.sessionID = null;
        System.out.println(String.format("Initiator onLogout : {%s}", sessionID));
    }

    @Override
    public void toAdmin(Message msg, SessionID sessionID) {

        System.out.println("Initiator toAdmin : " + msg);

    }

    @Override
    public void fromAdmin(Message msg, SessionID sessionID)
            throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        System.out.println(String.format("Initiator fromAdmin: {%s}, {%s}", msg, sessionID));
    }

    @Override
    public void toApp(Message msg, SessionID sessionID) throws DoNotSend {

        System.out.println(String.format("Initiator toApp: {%s}, {%s}", msg, sessionID));
    }

    @Override
    public void fromApp(Message msg, SessionID sessionID)
            throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {

        System.out.println(String.format("Initiator fromApp: {%s}, {%s}", msg, sessionID));
    }
}
