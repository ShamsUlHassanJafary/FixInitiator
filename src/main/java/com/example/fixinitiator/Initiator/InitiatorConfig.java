package com.example.fixinitiator.Initiator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.FileLogFactory;
import quickfix.FileStoreFactory;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.SessionSettings;
import quickfix.ThreadedSocketInitiator;
import quickfix.field.ApplVerID;

@Configuration
@Slf4j
public class InitiatorConfig {

    private static final String configFile = "config/fix/rtn.cfg";
    private static SessionSettings settings;

    @Autowired
    InitiatorApplication application;

    @Bean
    public ThreadedSocketInitiator threadedSocketInitiator() {
        // System.out.println("going through RNTConfig");
        ThreadedSocketInitiator threadedSocketInitiator = null;

        try {
            settings = new SessionSettings(new FileInputStream(configFile));
            // System.out.println("SETTINGS : " + settings);
            MessageStoreFactory storeFactory = new FileStoreFactory(settings);
            LogFactory logFactory = new FileLogFactory(settings);
            MessageFactory messageFactory = new DefaultMessageFactory(ApplVerID.FIX50SP1);
            threadedSocketInitiator = new ThreadedSocketInitiator(
                    application, storeFactory, settings, logFactory,
                    messageFactory);
            threadedSocketInitiator.start();
        } catch (ConfigError configError) {
            log.error("RTNConfig configError", configError.getMessage());
        } catch (FileNotFoundException e) {
            log.error("RTNConfig FileNotFoundException", e.getMessage());
        }

        return threadedSocketInitiator;
    }

}
