package nic.vahan.db.connection;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import nic.esign.EsignRequest;
import nic.org.apache.log4j.Logger;

public class ConnectionPooling {

	private static final Logger LOGGER = Logger.getLogger(ConnectionPooling.class);
	private static DataSource ds;
	private static DataSource ds1;
	private static DataSource dsAlone;

	private static DataSource dsAddOn;

	public static Connection getDBConnection() {
		Connection con = null;
		try {
			if (ds != null) {
				con = ds.getConnection();
			} else {
				String dsString = "java:/comp/env/jdbc/vow4";
				ds = (DataSource) new InitialContext().lookup(dsString);
				con = ds.getConnection();

			}
		} catch (NullPointerException | SQLException | NamingException ex) {
			LOGGER.error(ex);
		} catch (Exception ex) {
			LOGGER.error(ex);
		}
		return con;
	}

	public static Connection getDBConnectionStandAlone() {
		Connection con = null;
		try {
			if (dsAlone != null) {
				con = dsAlone.getConnection();
			} else {
				String dsString = "java:/comp/env/jdbc/vow4dash";
				dsAlone = (DataSource) new InitialContext().lookup(dsString);
				con = dsAlone.getConnection();
			}
		} catch (NullPointerException | SQLException | NamingException ex) {
			LOGGER.error(ex);
		} catch (Exception ex) {
			LOGGER.error(ex);
		}
		return con;
	}

	public static Connection getDBConnectionForAddOn() {
		Connection con = null;
		try {
			if (dsAddOn != null) {
				con = dsAddOn.getConnection();
			} else {
				String dsString = "java:/comp/env/jdbc/vahanserviceaddon";
				dsAddOn = (DataSource) new InitialContext().lookup(dsString);
				con = dsAddOn.getConnection();

			}
		} catch (NullPointerException | SQLException | NamingException ex) {
			LOGGER.error(ex);
		} catch (Exception ex) {
			LOGGER.error(ex);
		}
		return con;
	}

	public static Connection getDBConnectionInsert() {
		Connection con = null;
		try {
			if (ds1 != null) {
				con = ds1.getConnection();
			} else {
				String dsString = "java:/comp/env/jdbc/vahanserviceaddon";
				ds1 = (DataSource) new InitialContext().lookup(dsString);
				con = ds1.getConnection();

			}
		} catch (Exception e) {

			// Debug.logexc(e, "ConnectionPooling.getDBConnection()");
		}
		return con;
	}
}
