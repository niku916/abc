package nic.org.apache.log4j;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.EntryMessage;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.log4j.util.MessageSupplier;
import org.apache.logging.log4j.util.Supplier;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



//import org.apache.logging.log4j.Logger;

//import org.apache.log4j.LogManager;
/**
 *
 * @author admin
 */
public class Logger implements org.apache.logging.log4j.Logger {

    private org.apache.logging.log4j.Logger log = null;

    public Logger() {
        super();
    }

    private Logger(org.apache.logging.log4j.Logger log) {
        this.log = log;
    }

    public static Logger getLogger(final Class<?> clazz) {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        org.apache.logging.log4j.Logger log = context.getLogger(clazz);
        return new Logger(log);
    }

    public static Logger getLogger(org.apache.logging.log4j.Logger logger) {
        return new Logger(logger);

    }

    @Override
    public void catching(Level level, Throwable thrwbl) {
        log.catching(level, thrwbl);
    }

    @Override
    public void catching(Throwable thrwbl) {
        log.catching(thrwbl);
    }

    @Override
    public void debug(Marker marker, Message msg) {
        log.debug(marker, msg);
    }

    @Override
    public void debug(Marker marker, Message msg, Throwable thrwbl) {
        log.debug(marker, msg, thrwbl);
    }

    @Override
    public void debug(Marker marker, MessageSupplier ms) {
        log.debug(marker, ms);
    }

    @Override
    public void debug(Marker marker, MessageSupplier ms, Throwable thrwbl) {
        log.debug(marker, ms, thrwbl);
    }

    @Override
    public void debug(Marker marker, CharSequence cs) {
        log.debug(marker, cs);
    }

    @Override
    public void debug(Marker marker, CharSequence cs, Throwable thrwbl) {
        log.debug(marker, cs, thrwbl);
    }

    @Override
    public void debug(Marker marker, Object o) {
        log.debug(marker, o);
    }

    @Override
    public void debug(Marker marker, Object o, Throwable thrwbl) {
        log.debug(marker, o, thrwbl);
    }

    @Override
    public void debug(Marker marker, String string) {
        log.debug(marker, string);
    }

    @Override
    public void debug(Marker marker, String string, Object... os) {
        log.debug(marker, string, os);
    }

    @Override
    public void debug(Marker marker, String string, Supplier<?>... splrs) {
        log.debug(marker, string, splrs);
    }

    @Override
    public void debug(Marker marker, String string, Throwable thrwbl) {
        log.debug(marker, string, thrwbl);
    }

    @Override
    public void debug(Marker marker, Supplier<?> splr) {
        log.debug(marker, splr);
    }

    @Override
    public void debug(Marker marker, Supplier<?> splr, Throwable thrwbl) {
        log.debug(marker, splr, thrwbl);
    }

    @Override
    public void debug(Message msg) {
        log.debug(msg);
    }

    @Override
    public void debug(Message msg, Throwable thrwbl) {
        log.debug(msg, thrwbl);
    }

    @Override
    public void debug(MessageSupplier ms) {
        log.debug(ms);
    }

    @Override
    public void debug(MessageSupplier ms, Throwable thrwbl) {
        log.debug(ms, thrwbl);
    }

    @Override
    public void debug(CharSequence cs) {
        log.debug(cs);
    }

    @Override
    public void debug(CharSequence cs, Throwable thrwbl) {
        log.debug(cs, thrwbl);
    }

    @Override
    public void debug(Object o) {
        log.debug(o);
    }

    @Override
    public void debug(Object o, Throwable thrwbl) {
        log.debug(o, thrwbl);
    }

    @Override
    public void debug(String string) {
        log.debug(string);
    }

    @Override
    public void debug(String string, Object... os) {
        log.debug(string, os);
    }

    @Override
    public void debug(String string, Supplier<?>... splrs) {
        log.debug(string, splrs);
    }

    @Override
    public void debug(String string, Throwable thrwbl) {
        log.debug(string, thrwbl);
    }

    @Override
    public void debug(Supplier<?> splr) {
        log.debug(splr);
    }

    @Override
    public void debug(Supplier<?> splr, Throwable thrwbl) {
        log.debug(splr, thrwbl);
    }

    @Override
    public void debug(Marker marker, String string, Object o) {
        log.debug(marker, string, o);
    }

    @Override
    public void debug(Marker marker, String string, Object o, Object o1) {
        log.debug(marker, string, o, o1);
    }

    @Override
    public void debug(Marker marker, String string, Object o, Object o1, Object o2) {
        log.debug(marker, string, o, o1, o2);
    }

    @Override
    public void debug(Marker marker, String string, Object o, Object o1, Object o2, Object o3) {
        log.debug(marker, string, o, o1, o2, o3);
    }

    @Override
    public void debug(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4) {
        log.debug(marker, string, o, o1, o2, o3, o4);
    }

    @Override
    public void debug(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5) {
        log.debug(marker, string, o, o1, o2, o3, o4, o5);
    }

    @Override
    public void debug(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        log.debug(marker, string, o, o1, o2, o3, o4, o5, o6);
    }

    @Override
    public void debug(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        log.debug(marker, string, o, o1, o2, o3, o4, o5, o6, o7);
    }

    @Override
    public void debug(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
        log.debug(marker, string, o, o1, o2, o3, o4, o5, o6, o7, o8);
    }

    @Override
    public void debug(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
        log.debug(marker, string, o, o1, o2, o3, o4, o5, o6, o7, o8, o9);
    }

    @Override
    public void debug(String string, Object o) {
        log.debug(string, o);
    }

    @Override
    public void debug(String string, Object o, Object o1) {
        log.debug(string, o, o1);
    }

    @Override
    public void debug(String string, Object o, Object o1, Object o2) {
        log.debug(string, o, o1, o2);
    }

    @Override
    public void debug(String string, Object o, Object o1, Object o2, Object o3) {
        log.debug(string, o, o1, o2, o3);
    }

    @Override
    public void debug(String string, Object o, Object o1, Object o2, Object o3, Object o4) {
        log.debug(string, o, o1, o2, o3, o4);
    }

    @Override
    public void debug(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5) {
        log.debug(string, o, o1, o2, o3, o4, o5);
    }

    @Override
    public void debug(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        log.debug(string, o, o1, o2, o3, o4, o5, o6);
    }

    @Override
    public void debug(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        log.debug(string, o, o1, o2, o3, o4, o5, o6, o7);
    }

    @Override
    public void debug(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
        log.debug(string, o, o1, o2, o3, o4, o5, o6, o7, o8);
    }

    @Override
    public void debug(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
        log.debug(string, o, o1, o2, o3, o4, o5, o6, o7, o8, o9);
    }

    @Override
    public void entry() {
        log.entry();
    }

    @Override
    public void entry(Object... os) {
        log.entry(os);
    }

    @Override
    public void error(Marker marker, Message msg) {
        log.error(marker, msg);
    }

    @Override
    public void error(Marker marker, Message msg, Throwable thrwbl) {
        log.error(marker, msg, thrwbl);
    }

    @Override
    public void error(Marker marker, MessageSupplier ms) {
        log.error(marker, ms);
    }

    @Override
    public void error(Marker marker, MessageSupplier ms, Throwable thrwbl) {
        log.error(marker, ms, thrwbl);
    }

    @Override
    public void error(Marker marker, CharSequence cs) {
        log.error(marker, cs);
    }

    @Override
    public void error(Marker marker, CharSequence cs, Throwable thrwbl) {
        log.error(marker, cs, thrwbl);
    }

    @Override
    public void error(Marker marker, Object o) {
        log.error(marker, o);
    }

    @Override
    public void error(Marker marker, Object o, Throwable thrwbl) {
        log.error(marker, o, thrwbl);
    }

    @Override
    public void error(Marker marker, String string) {
        log.error(marker, string);
    }

    @Override
    public void error(Marker marker, String string, Object... os) {
        log.error(marker, string, os);
    }

    @Override
    public void error(Marker marker, String string, Supplier<?>... splrs) {
        log.error(marker, string, splrs);
    }

    @Override
    public void error(Marker marker, String string, Throwable thrwbl) {
        log.error(marker, string, thrwbl);
    }

    @Override
    public void error(Marker marker, Supplier<?> splr) {
        log.error(marker, splr);
    }

    @Override
    public void error(Marker marker, Supplier<?> splr, Throwable thrwbl) {
        log.error(marker, splr, thrwbl);
    }

    @Override
    public void error(Message msg) {
        log.error(msg);
    }

    @Override
    public void error(Message msg, Throwable thrwbl) {
        log.error(msg, thrwbl);
    }

    @Override
    public void error(MessageSupplier ms) {
        log.error(ms);
    }

    @Override
    public void error(MessageSupplier ms, Throwable thrwbl) {
        log.error(ms, thrwbl);
    }

    @Override
    public void error(CharSequence cs) {
        log.error(cs);
    }

    @Override
    public void error(CharSequence cs, Throwable thrwbl) {
        log.error(cs, thrwbl);
    }

    @Override
    public void error(Object o) {
        log.error(o);
    }

    @Override
    public void error(Object o, Throwable thrwbl) {
        log.error(o, thrwbl);
    }

    @Override
    public void error(String string) {

        log.error(log.getName() + "-" + string);
    }

    @Override
    public void error(String string, Object... os) {
        log.error(string, os);
    }

    @Override
    public void error(String string, Supplier<?>... splrs) {
        log.error(string, splrs);
    }

    @Override
    public void error(String string, Throwable thrwbl) {
        log.error(string, thrwbl);
    }

    @Override
    public void error(Supplier<?> splr) {
        log.error(splr);
    }

    @Override
    public void error(Supplier<?> splr, Throwable thrwbl) {
        log.error(splr, thrwbl);
    }

    @Override
    public void error(Marker marker, String string, Object o) {
        log.error(marker, string, o);
    }

    @Override
    public void error(Marker marker, String string, Object o, Object o1) {
        log.error(marker, string, o, o1);
    }

    @Override
    public void error(Marker marker, String string, Object o, Object o1, Object o2) {
        log.error(marker, string, o, o1, o2);
    }

    @Override
    public void error(Marker marker, String string, Object o, Object o1, Object o2, Object o3) {
        log.error(marker, string, o, o1, o2, o3);
    }

    @Override
    public void error(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4) {
        log.error(marker, string, o, o1, o2, o3, o4);
    }

    @Override
    public void error(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5) {
        log.error(marker, string, o, o1, o2, o3, o4, o5);
    }

    @Override
    public void error(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        log.error(marker, string, o, o1, o2, o3, o4, o5, o6);
    }

    @Override
    public void error(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        log.error(marker, string, o, o1, o2, o3, o4, o5, o6, o7);
    }

    @Override
    public void error(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
        log.error(marker, string, o, o1, o2, o3, o4, o5, o6, o7, o8);
    }

    @Override
    public void error(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
        log.error(marker, string, o, o1, o2, o3, o4, o5, o6, o7, o8, o9);
    }

    @Override
    public void error(String string, Object o) {
        log.error(string, o);
    }

    @Override
    public void error(String string, Object o, Object o1) {
        log.error(string, o, o1);
    }

    @Override
    public void error(String string, Object o, Object o1, Object o2) {
        log.error(string, o, o1, o2);
    }

    @Override
    public void error(String string, Object o, Object o1, Object o2, Object o3) {
        log.error(string, o, o1, o2, o3);
    }

    @Override
    public void error(String string, Object o, Object o1, Object o2, Object o3, Object o4) {
        log.error(string, o, o1, o2, o3, o4);
    }

    @Override
    public void error(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5) {
        log.error(string, o, o1, o2, o3, o4, o5);
    }

    @Override
    public void error(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        log.error(string, o, o1, o2, o3, o4, o5, o6);
    }

    @Override
    public void error(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        log.error(string, o, o1, o2, o3, o4, o5, o6, o7);
    }

    @Override
    public void error(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
        log.error(string, o, o1, o2, o3, o4, o5, o6, o7, o8);
    }

    @Override
    public void error(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
        log.error(string, o, o1, o2, o3, o4, o5, o6, o7, o8, o9);
    }

    @Override
    public void exit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <R> R exit(R r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(Marker marker, Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(Marker marker, Message msg, Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(Marker marker, MessageSupplier ms) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(Marker marker, MessageSupplier ms, Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(Marker marker, CharSequence cs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(Marker marker, CharSequence cs, Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(Marker marker, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(Marker marker, Object o, Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(Marker marker, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(Marker marker, String string, Object... os) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(Marker marker, String string, Supplier<?>... splrs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(Marker marker, String string, Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(Marker marker, Supplier<?> splr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(Marker marker, Supplier<?> splr, Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(Message msg, Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(MessageSupplier ms) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(MessageSupplier ms, Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(CharSequence cs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(CharSequence cs, Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatal(Object o, Throwable thrwbl) {
        log.fatal(o, thrwbl);
    }

    @Override
    public void fatal(String string) {
        log.fatal(string);
    }

    @Override
    public void fatal(String string, Object... os) {
        log.fatal(string, os);
    }

    @Override
    public void fatal(String string, Supplier<?>... splrs) {
        log.fatal(string, splrs);
    }

    @Override
    public void fatal(String string, Throwable thrwbl) {
        log.fatal(string, thrwbl);
    }

    @Override
    public void fatal(Supplier<?> splr) {
        log.fatal(splr);
    }

    @Override
    public void fatal(Supplier<?> splr, Throwable thrwbl) {
        log.fatal(splr, thrwbl);
    }

    @Override
    public void fatal(Marker marker, String string, Object o) {
        log.fatal(marker, string, o);
    }

    @Override
    public void fatal(Marker marker, String string, Object o, Object o1) {
        log.fatal(marker, string, o, o1);
    }

    @Override
    public void fatal(Marker marker, String string, Object o, Object o1, Object o2) {
        log.fatal(marker, string, o, o1, o2);
    }

    @Override
    public void fatal(Marker marker, String string, Object o, Object o1, Object o2, Object o3) {
        log.fatal(marker, string, o, o1, o2, o3);
    }

    @Override
    public void fatal(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4) {
        log.fatal(marker, string, o, o1, o2, o3, o4);
    }

    @Override
    public void fatal(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5) {
        log.fatal(marker, string, o, o1, o2, o3, o4, o5);
    }

    @Override
    public void fatal(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        log.fatal(marker, string, o, o1, o2, o3, o4, o5, o6);
    }

    @Override
    public void fatal(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        log.fatal(marker, string, o, o1, o2, o3, o4, o5, o6, o7);
    }

    @Override
    public void fatal(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
        log.fatal(marker, string, o, o1, o2, o3, o4, o5, o6, o7, o8);
    }

    @Override
    public void fatal(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
        log.fatal(marker, string, o, o1, o2, o3, o4, o5, o6, o7, o8, o9);
    }

    @Override
    public void fatal(String string, Object o) {
        log.fatal(string, o);
    }

    @Override
    public void fatal(String string, Object o, Object o1) {
        log.fatal(string, o, o1);
    }

    @Override
    public void fatal(String string, Object o, Object o1, Object o2) {
        log.fatal(string, o, o1, o2);
    }

    @Override
    public void fatal(String string, Object o, Object o1, Object o2, Object o3) {
        log.fatal(string, o, o1, o2, o3);
    }

    @Override
    public void fatal(String string, Object o, Object o1, Object o2, Object o3, Object o4) {
        log.fatal(string, o, o1, o2, o3, o4);
    }

    @Override
    public void fatal(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5) {
        log.fatal(string, o, o1, o2, o3, o4, o5);
    }

    @Override
    public void fatal(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        log.fatal(string, o, o1, o2, o3, o4, o5, o6);
    }

    @Override
    public void fatal(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        log.fatal(string, o, o1, o2, o3, o4, o5, o6, o7);
    }

    @Override
    public void fatal(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
        log.fatal(string, o, o1, o2, o3, o4, o5, o6, o7, o8);
    }

    @Override
    public void fatal(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
        log.fatal(string, o, o1, o2, o3, o4, o5, o6, o7, o8, o9);
    }

    @Override
    public Level getLevel() {
        return log.getLevel();
    }

    @Override
    public <MF extends MessageFactory> MF getMessageFactory() {
        return log.getMessageFactory();
    }

    @Override
    public String getName() {
        return log.getName();
    }

    @Override
    public void info(Marker marker, Message msg) {
        log.info(marker, msg);
    }

    @Override
    public void info(Marker marker, Message msg, Throwable thrwbl) {
        log.info(marker, msg, thrwbl);
    }

    @Override
    public void info(Marker marker, MessageSupplier ms) {
        log.info(marker, ms);
    }

    @Override
    public void info(Marker marker, MessageSupplier ms, Throwable thrwbl) {
        log.info(marker, ms, thrwbl);
    }

    @Override
    public void info(Marker marker, CharSequence cs) {
        log.info(marker, cs);
    }

    @Override
    public void info(Marker marker, CharSequence cs, Throwable thrwbl) {
        log.info(marker, cs, thrwbl);
    }

    @Override
    public void info(Marker marker, Object o) {
        log.info(marker, o);
    }

    @Override
    public void info(Marker marker, Object o, Throwable thrwbl) {
        log.info(marker, o, thrwbl);
    }

    @Override
    public void info(Marker marker, String string) {
        log.info(marker, string);
    }

    @Override
    public void info(Marker marker, String string, Object... os) {
        log.info(marker, string, os);
    }

    @Override
    public void info(Marker marker, String string, Supplier<?>... splrs) {
        log.info(marker, string, splrs);
    }

    @Override
    public void info(Marker marker, String string, Throwable thrwbl) {
        log.info(marker, string, thrwbl);
    }

    @Override
    public void info(Marker marker, Supplier<?> splr) {
        log.info(marker, splr);
    }

    @Override
    public void info(Marker marker, Supplier<?> splr, Throwable thrwbl) {
        log.info(marker, splr, thrwbl);
    }

    @Override
    public void info(Message msg) {
        log.info(msg);
    }

    @Override
    public void info(Message msg, Throwable thrwbl) {
        log.info(msg, thrwbl);
    }

    @Override
    public void info(MessageSupplier ms) {
        log.info(ms);
    }

    @Override
    public void info(MessageSupplier ms, Throwable thrwbl) {
        log.info(ms, thrwbl);
    }

    @Override
    public void info(CharSequence cs) {
        log.info(cs);
    }

    @Override
    public void info(CharSequence cs, Throwable thrwbl) {
        log.info(cs, thrwbl);
    }

    @Override
    public void info(Object o) {
        log.info(o);
    }

    @Override
    public void info(Object o, Throwable thrwbl) {
        log.info(o, thrwbl);
    }

    @Override
    public void info(String string) {
        log.info(string);
    }

    @Override
    public void info(String string, Object... os) {
        log.info(string, os);
    }

    @Override
    public void info(String string, Supplier<?>... splrs) {
        log.info(string, splrs);
    }

    @Override
    public void info(String string, Throwable thrwbl) {
        log.info(string, thrwbl);
    }

    @Override
    public void info(Supplier<?> splr) {
        log.info(splr);
    }

    @Override
    public void info(Supplier<?> splr, Throwable thrwbl) {
        log.info(splr, thrwbl);
    }

    @Override
    public void info(Marker marker, String string, Object o) {
        log.info(marker, string, o);
    }

    @Override
    public void info(Marker marker, String string, Object o, Object o1) {
        log.info(marker, string, o, o1);
    }

    @Override
    public void info(Marker marker, String string, Object o, Object o1, Object o2) {
        log.info(marker, string, o, o1, o2);
    }

    @Override
    public void info(Marker marker, String string, Object o, Object o1, Object o2, Object o3) {
        log.info(marker, string, o, o1, o2, o3);
    }

    @Override
    public void info(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4) {
        log.info(marker, string, o, o1, o2, o3, o4);
    }

    @Override
    public void info(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5) {
        log.info(marker, string, o, o1, o2, o3, o4, o5);
    }

    @Override
    public void info(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        log.info(marker, string, o, o1, o2, o3, o4, o5, o6);
    }

    @Override
    public void info(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        log.info(marker, string, o, o1, o2, o3, o4, o5, o6, o7);
    }

    @Override
    public void info(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
        log.info(marker, string, o, o1, o2, o3, o4, o5, o6, o7, o8);
    }

    @Override
    public void info(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
        log.info(marker, string, o, o1, o2, o3, o4, o5, o6, o7, o8, o9);
    }

    @Override
    public void info(String string, Object o) {
        log.info(string, o);
    }

    @Override
    public void info(String string, Object o, Object o1) {
        log.info(string, o, o1);
    }

    @Override
    public void info(String string, Object o, Object o1, Object o2) {
        log.info(string, o, o1, o2);
    }

    @Override
    public void info(String string, Object o, Object o1, Object o2, Object o3) {
        log.info(string, o, o1, o2, o3);
    }

    @Override
    public void info(String string, Object o, Object o1, Object o2, Object o3, Object o4) {
        log.info(string, o, o1, o2, o3, o4);
    }

    @Override
    public void info(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5) {
        log.info(string, o, o1, o2, o3, o4, o5);
    }

    @Override
    public void info(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        log.info(string, o, o1, o2, o3, o4, o5, o6);
    }

    @Override
    public void info(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        log.info(string, o, o1, o2, o3, o4, o5, o6, o7);
    }

    @Override
    public void info(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
        log.info(string, o, o1, o2, o3, o4, o5, o6, o7, o8);
    }

    @Override
    public void info(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
        log.info(string, o, o1, o2, o3, o4, o5, o6, o7, o8, o9);
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return log.isDebugEnabled(marker);
    }

    @Override
    public boolean isEnabled(Level level) {
        return log.isEnabled(level);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker) {
        return log.isEnabled(level, marker);
    }

    @Override
    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return log.isErrorEnabled(marker);
    }

    @Override
    public boolean isFatalEnabled() {
        return log.isFatalEnabled();
    }

    @Override
    public boolean isFatalEnabled(Marker marker) {
        return log.isFatalEnabled(marker);
    }

    @Override
    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return log.isInfoEnabled(marker);
    }

    @Override
    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return log.isTraceEnabled(marker);
    }

    @Override
    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return log.isWarnEnabled(marker);
    }

    @Override
    public void log(Level level, Marker marker, Message msg) {
        log.log(level, marker, msg);
    }

    @Override
    public void log(Level level, Marker marker, Message msg, Throwable thrwbl) {
        log.log(level, marker, msg, thrwbl);
    }

    @Override
    public void log(Level level, Marker marker, MessageSupplier ms) {
        log.log(level, marker, ms);
    }

    @Override
    public void log(Level level, Marker marker, MessageSupplier ms, Throwable thrwbl) {
        log.log(level, marker, ms, thrwbl);
    }

    @Override
    public void log(Level level, Marker marker, CharSequence cs) {
        log.log(level, marker, cs);
    }

    @Override
    public void log(Level level, Marker marker, CharSequence cs, Throwable thrwbl) {
        log.log(level, marker, cs, thrwbl);
    }

    @Override
    public void log(Level level, Marker marker, Object o) {
        log.log(level, marker, o);
    }

    @Override
    public void log(Level level, Marker marker, Object o, Throwable thrwbl) {
        log.log(level, marker, o, thrwbl);
    }

    @Override
    public void log(Level level, Marker marker, String string) {
        log.log(level, marker, string);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object... os) {
        log.log(level, marker, string, os);
    }

    @Override
    public void log(Level level, Marker marker, String string, Supplier<?>... splrs) {
        log.log(level, marker, string, splrs);
    }

    @Override
    public void log(Level level, Marker marker, String string, Throwable thrwbl) {
        log.log(level, marker, string, thrwbl);
    }

    @Override
    public void log(Level level, Marker marker, Supplier<?> splr) {
        log.log(level, marker, splr);
    }

    @Override
    public void log(Level level, Marker marker, Supplier<?> splr, Throwable thrwbl) {
        log.log(level, marker, splr, thrwbl);
    }

    @Override
    public void log(Level level, Message msg) {
        log.log(level, msg);
    }

    @Override
    public void log(Level level, Message msg, Throwable thrwbl) {
        log.log(level, msg, thrwbl);
    }

    @Override
    public void log(Level level, MessageSupplier ms) {
        log.log(level, ms);
    }

    @Override
    public void log(Level level, MessageSupplier ms, Throwable thrwbl) {
        log.log(level, ms, thrwbl);
    }

    @Override
    public void log(Level level, CharSequence cs) {
        log.log(level, cs);
    }

    @Override
    public void log(Level level, CharSequence cs, Throwable thrwbl) {
        log.log(level, cs, thrwbl);
    }

    @Override
    public void log(Level level, Object o) {
        log.log(level, o);
    }

    @Override
    public void log(Level level, Object o, Throwable thrwbl) {
        log.log(level, o, thrwbl);
    }

    @Override
    public void log(Level level, String string) {
        log.log(level, string);
    }

    @Override
    public void log(Level level, String string, Object... os) {
        log.log(level, string, os);
    }

    @Override
    public void log(Level level, String string, Supplier<?>... splrs) {
        log.log(level, string, splrs);
    }

    @Override
    public void log(Level level, String string, Throwable thrwbl) {
        log.log(level, string, thrwbl);
    }

    @Override
    public void log(Level level, Supplier<?> splr) {
        log.log(level, splr);
    }

    @Override
    public void log(Level level, Supplier<?> splr, Throwable thrwbl) {
        log.log(level, splr, thrwbl);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object o) {
        log.log(level, marker, string, o);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object o, Object o1) {
        log.log(level, marker, string, o, o1);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object o, Object o1, Object o2) {
        log.log(level, marker, string, o, o1, o2);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object o, Object o1, Object o2, Object o3) {
        log.log(level, marker, string, o, o1, o2, o3);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4) {
        log.log(level, marker, string, o, o1, o2, o3, o4);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5) {
        log.log(level, marker, string, o, o1, o2, o3, o4, o5);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        log.log(level, marker, string, o, o1, o2, o3, o4, o5, o6);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        log.log(level, marker, string, o, o1, o2, o3, o4, o5, o6, o7);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
        log.log(level, marker, string, o, o1, o2, o3, o4, o5, o6, o7, o8);
    }

    @Override
    public void log(Level level, Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
        log.log(level, marker, string, o, o1, o2, o3, o4, o5, o6, o7, o8, o9);
    }

    @Override
    public void log(Level level, String string, Object o) {
        log.log(level, string, o);
    }

    @Override
    public void log(Level level, String string, Object o, Object o1) {
        log.log(level, string, o, o1);
    }

    @Override
    public void log(Level level, String string, Object o, Object o1, Object o2) {
        log.log(level, string, o, o1, o2);
    }

    @Override
    public void log(Level level, String string, Object o, Object o1, Object o2, Object o3) {
        log.log(level, string, o, o1, o2, o3);
    }

    @Override
    public void log(Level level, String string, Object o, Object o1, Object o2, Object o3, Object o4) {
        log.log(level, string, o, o1, o2, o3, o4);
    }

    @Override
    public void log(Level level, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5) {
        log.log(level, string, o, o1, o2, o3, o4, o5);
    }

    @Override
    public void log(Level level, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        log.log(level, string, o, o1, o2, o3, o4, o5, o6);
    }

    @Override
    public void log(Level level, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        log.log(level, string, o, o1, o2, o3, o4, o5, o6, o7);
    }

    @Override
    public void log(Level level, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
        log.log(level, string, o, o1, o2, o3, o4, o5, o6, o7, o8);
    }

    @Override
    public void log(Level level, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
        log.log(level, string, o, o1, o2, o3, o4, o5, o6, o7, o8, o9);
    }

    @Override
    public void printf(Level level, Marker marker, String string, Object... os) {
        log.printf(level, marker, string, os);
    }

    @Override
    public void printf(Level level, String string, Object... os) {
        log.printf(level, string, os);
    }

    @Override
    public <T extends Throwable> T throwing(Level level, T t) {
        return log.throwing(level, t);
    }

    @Override
    public <T extends Throwable> T throwing(T t) {
        return log.throwing(t);
    }

    @Override
    public void trace(Marker marker, Message msg) {
        log.trace(marker, msg);
    }

    @Override
    public void trace(Marker marker, Message msg, Throwable thrwbl) {
        log.trace(marker, msg, thrwbl);
    }

    @Override
    public void trace(Marker marker, MessageSupplier ms) {
        log.trace(marker, ms);
    }

    @Override
    public void trace(Marker marker, MessageSupplier ms, Throwable thrwbl) {
        log.trace(marker, ms, thrwbl);
    }

    @Override
    public void trace(Marker marker, CharSequence cs) {
        log.trace(marker, cs);
    }

    @Override
    public void trace(Marker marker, CharSequence cs, Throwable thrwbl) {
        log.trace(marker, cs, thrwbl);
    }

    @Override
    public void trace(Marker marker, Object o) {
        log.trace(marker, o);
    }

    @Override
    public void trace(Marker marker, Object o, Throwable thrwbl) {
        log.trace(marker, o, thrwbl);
    }

    @Override
    public void trace(Marker marker, String string) {
        log.trace(marker, string);
    }

    @Override
    public void trace(Marker marker, String string, Object... os) {
        log.trace(marker, string, os);
    }

    @Override
    public void trace(Marker marker, String string, Supplier<?>... splrs) {
        log.trace(marker, string, splrs);
    }

    @Override
    public void trace(Marker marker, String string, Throwable thrwbl) {
        log.trace(marker, string, thrwbl);
    }

    @Override
    public void trace(Marker marker, Supplier<?> splr) {
        log.trace(marker, splr);
    }

    @Override
    public void trace(Marker marker, Supplier<?> splr, Throwable thrwbl) {
        log.trace(marker, splr, thrwbl);
    }

    @Override
    public void trace(Message msg) {
        log.trace(msg);
    }

    @Override
    public void trace(Message msg, Throwable thrwbl) {
        log.trace(msg, thrwbl);
    }

    @Override
    public void trace(MessageSupplier ms) {
        log.trace(ms);
    }

    @Override
    public void trace(MessageSupplier ms, Throwable thrwbl) {
        log.trace(ms, thrwbl);
    }

    @Override
    public void trace(CharSequence cs) {
        log.trace(cs);
    }

    @Override
    public void trace(CharSequence cs, Throwable thrwbl) {
        log.trace(cs, thrwbl);
    }

    @Override
    public void trace(Object o) {
        log.trace(o);
    }

    @Override
    public void trace(Object o, Throwable thrwbl) {
        log.trace(o, thrwbl);
    }

    @Override
    public void trace(String string) {
        log.trace(string);
    }

    @Override
    public void trace(String string, Object... os) {
        log.trace(string, os);
    }

    @Override
    public void trace(String string, Supplier<?>... splrs) {
        log.trace(string, splrs);
    }

    @Override
    public void trace(String string, Throwable thrwbl) {
        log.trace(string, thrwbl);
    }

    @Override
    public void trace(Supplier<?> splr) {
        log.trace(splr);
    }

    @Override
    public void trace(Supplier<?> splr, Throwable thrwbl) {
        log.trace(splr, thrwbl);
    }

    @Override
    public void trace(Marker marker, String string, Object o) {
        log.trace(marker, string, o);
    }

    @Override
    public void trace(Marker marker, String string, Object o, Object o1) {
        log.trace(marker, string, o, o1);
    }

    @Override
    public void trace(Marker marker, String string, Object o, Object o1, Object o2) {
        log.trace(marker, string, o, o1, o2);
    }

    @Override
    public void trace(Marker marker, String string, Object o, Object o1, Object o2, Object o3) {
        log.trace(marker, string, o, o1, o2, o3);
    }

    @Override
    public void trace(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4) {
        log.trace(marker, string, o, o1, o2, o3, o4);
    }

    @Override
    public void trace(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5) {
        log.trace(marker, string, o, o1, o2, o3, o4, o5);
    }

    @Override
    public void trace(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        log.trace(marker, string, o, o1, o2, o3, o4, o5, o6);
    }

    @Override
    public void trace(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        log.trace(marker, string, o, o1, o2, o3, o4, o5, o6, o7);
    }

    @Override
    public void trace(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
        log.trace(marker, string, o, o1, o2, o3, o4, o5, o6, o7, o8);
    }

    @Override
    public void trace(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
        log.trace(marker, string, o, o1, o2, o3, o4, o5, o6, o7, o8, o9);
    }

    @Override
    public void trace(String string, Object o) {
        log.trace(string, o);
    }

    @Override
    public void trace(String string, Object o, Object o1) {
        log.trace(string, o, o1);
    }

    @Override
    public void trace(String string, Object o, Object o1, Object o2) {
        log.trace(string, o, o1, o2);
    }

    @Override
    public void trace(String string, Object o, Object o1, Object o2, Object o3) {
        log.trace(string, o, o1, o2, o3);
    }

    @Override
    public void trace(String string, Object o, Object o1, Object o2, Object o3, Object o4) {
        log.trace(string, o, o1, o2, o3, o4);
    }

    @Override
    public void trace(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5) {
        log.trace(string, o, o1, o2, o3, o4, o5);
    }

    @Override
    public void trace(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        log.trace(string, o, o1, o2, o3, o4, o5, o6);
    }

    @Override
    public void trace(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        log.trace(string, o, o1, o2, o3, o4, o5, o6, o7);
    }

    @Override
    public void trace(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
        log.trace(string, o, o1, o2, o3, o4, o5, o6, o7, o8);
    }

    @Override
    public void trace(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
        log.trace(string, o, o1, o2, o3, o4, o5, o6, o7, o8, o9);
    }

    @Override
    public EntryMessage traceEntry() {
        return log.traceEntry();
    }

    @Override
    public EntryMessage traceEntry(String string, Object... os) {
        return log.traceEntry(string, os);
    }

    @Override
    public EntryMessage traceEntry(Supplier<?>... splrs) {
        return log.traceEntry(splrs);
    }

    @Override
    public EntryMessage traceEntry(String string, Supplier<?>... splrs) {
        return log.traceEntry(string, splrs);
    }

    @Override
    public EntryMessage traceEntry(Message msg) {
        return log.traceEntry(msg);
    }

    @Override
    public void traceExit() {
        log.traceExit();
    }

    @Override
    public <R> R traceExit(R r) {
        return log.traceExit(r);
    }

    @Override
    public <R> R traceExit(String string, R r) {
        return log.traceExit(string, r);
    }

    @Override
    public void traceExit(EntryMessage em) {
        log.traceExit(em);
    }

    @Override
    public <R> R traceExit(EntryMessage em, R r) {
        return log.traceExit(em, r);
    }

    @Override
    public <R> R traceExit(Message msg, R r) {
        return log.traceExit(msg, r);
    }

    @Override
    public void warn(Marker marker, Message msg) {
        log.warn(marker, msg);
    }

    @Override
    public void warn(Marker marker, Message msg, Throwable thrwbl) {
        log.warn(marker, msg, thrwbl);
    }

    @Override
    public void warn(Marker marker, MessageSupplier ms) {
        log.warn(marker, ms);
    }

    @Override
    public void warn(Marker marker, MessageSupplier ms, Throwable thrwbl) {
        log.warn(marker, ms, thrwbl);
    }

    @Override
    public void warn(Marker marker, CharSequence cs) {
        log.warn(marker, cs);
    }

    @Override
    public void warn(Marker marker, CharSequence cs, Throwable thrwbl) {
        log.warn(marker, cs, thrwbl);
    }

    @Override
    public void warn(Marker marker, Object o) {
        log.warn(marker, o);
    }

    @Override
    public void warn(Marker marker, Object o, Throwable thrwbl) {
        log.warn(marker, o, thrwbl);
    }

    @Override
    public void warn(Marker marker, String string) {
        log.warn(marker, string);
    }

    @Override
    public void warn(Marker marker, String string, Object... os) {
        log.warn(marker, string, os);
    }

    @Override
    public void warn(Marker marker, String string, Supplier<?>... splrs) {
        log.warn(marker, string, splrs);
    }

    @Override
    public void warn(Marker marker, String string, Throwable thrwbl) {
        log.warn(marker, string, thrwbl);
    }

    @Override
    public void warn(Marker marker, Supplier<?> splr) {
        log.warn(marker, splr);
    }

    @Override
    public void warn(Marker marker, Supplier<?> splr, Throwable thrwbl) {
        log.warn(marker, splr, thrwbl);
    }

    @Override
    public void warn(Message msg) {
        log.warn(msg);
    }

    @Override
    public void warn(Message msg, Throwable thrwbl) {
        log.warn(msg, thrwbl);
    }

    @Override
    public void warn(MessageSupplier ms) {
        log.warn(ms);
    }

    @Override
    public void warn(MessageSupplier ms, Throwable thrwbl) {
        log.warn(ms, thrwbl);
    }

    @Override
    public void warn(CharSequence cs) {
        log.warn(cs);
    }

    @Override
    public void warn(CharSequence cs, Throwable thrwbl) {
        log.warn(cs, thrwbl);
    }

    @Override
    public void warn(Object o) {
        log.warn(o);
    }

    @Override
    public void warn(Object o, Throwable thrwbl) {
        log.warn(o, thrwbl);
    }

    @Override
    public void warn(String string) {
        log.warn(string);
    }

    @Override
    public void warn(String string, Object... os) {
        log.warn(string, os);
    }

    @Override
    public void warn(String string, Supplier<?>... splrs) {
        log.warn(string, splrs);
    }

    @Override
    public void warn(String string, Throwable thrwbl) {
        log.warn(string, thrwbl);
    }

    @Override
    public void warn(Supplier<?> splr) {
        log.warn(splr);
    }

    @Override
    public void warn(Supplier<?> splr, Throwable thrwbl) {
        log.warn(splr, thrwbl);
    }

    @Override
    public void warn(Marker marker, String string, Object o) {
        log.warn(marker, string, o);
    }

    @Override
    public void warn(Marker marker, String string, Object o, Object o1) {
        log.warn(marker, string, o, o1);
    }

    @Override
    public void warn(Marker marker, String string, Object o, Object o1, Object o2) {
        log.warn(marker, string, o, o1, o2);
    }

    @Override
    public void warn(Marker marker, String string, Object o, Object o1, Object o2, Object o3) {
        log.warn(marker, string, o, o1, o2, o3);
    }

    @Override
    public void warn(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4) {
        log.warn(marker, string, o, o1, o2, o3, o4);
    }

    @Override
    public void warn(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5) {
        log.warn(marker, string, o, o1, o2, o3, o4, o5);
    }

    @Override
    public void warn(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        log.warn(marker, string, o, o1, o2, o3, o4, o5, o6);
    }

    @Override
    public void warn(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        log.warn(marker, string, o, o1, o2, o3, o4, o5, o6, o7);
    }

    @Override
    public void warn(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
        log.warn(marker, string, o, o1, o2, o3, o4, o5, o6, o7, o8);
    }

    @Override
    public void warn(Marker marker, String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
        log.warn(marker, string, o, o1, o2, o3, o4, o5, o6, o7, o8, o9);
    }

    @Override
    public void warn(String string, Object o) {
        log.warn(string, o);
    }

    @Override
    public void warn(String string, Object o, Object o1) {
        log.warn(string, o, o1);
    }

    @Override
    public void warn(String string, Object o, Object o1, Object o2) {
        log.warn(string, o, o1, o2);
    }

    @Override
    public void warn(String string, Object o, Object o1, Object o2, Object o3) {
        log.warn(string, o, o1, o2, o3);
    }

    @Override
    public void warn(String string, Object o, Object o1, Object o2, Object o3, Object o4) {
        log.warn(string, o, o1, o2, o3, o4);
    }

    @Override
    public void warn(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5) {
        log.warn(string, o, o1, o2, o3, o4, o5);
    }

    @Override
    public void warn(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        log.warn(string, o, o1, o2, o3, o4, o5, o6);
    }

    @Override
    public void warn(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        log.warn(string, o, o1, o2, o3, o4, o5, o6, o7);
    }

    @Override
    public void warn(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
        log.warn(string, o, o1, o2, o3, o4, o5, o6, o7, o8);
    }

    @Override
    public void warn(String string, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
        log.warn(string, o, o1, o2, o3, o4, o5, o6, o7, o8, o9);
    }
}
