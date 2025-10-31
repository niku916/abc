package nic.vahan.db.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sun.rowset.CachedRowSetImpl;

import nic.java.util.CommonUtils;
import nic.org.apache.log4j.Logger;
import nic.vahan.CommonUtils.CachedRowSetImpl_New;

public class TransactionManager implements TransactionManagerInterface {

	private String whereiam;
	private int transactionID;
	private static int transactionCounter = 0;
	private static int releasedTransactionCounter = 0;
	private Connection con;
	private Statement stmt;
	private PreparedStatement prstmt;
	private List sqlList = new ArrayList();
	private boolean transactionSuccessfull;
	private boolean commitHasBeenCalledAlready;
	private boolean released;
	private int size;
	private static final Logger LOGGER = Logger.getLogger(TransactionManager.class);

	public javax.sql.RowSet fetchDetachedRowSet() throws SQLException {

		ResultSet rs = null;
		CachedRowSetImpl_New rowSet = null;
		try {
			rowSet = new CachedRowSetImpl_New();
			rs = prstmt.executeQuery();
			rowSet.populate(rs);
			setSize(rowSet.size());
		} finally {
			try {
				release();
			} catch (Exception ex) {
				LOGGER.error(ex);
			}
		}

		return rowSet;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public javax.sql.RowSet fetchDetachedRowSetWithoutTrim() throws SQLException {

		ResultSet rs = null;
		CachedRowSetImpl rowSet = null;
		try {
			rowSet = new CachedRowSetImpl();
			rs = prstmt.executeQuery();
			rowSet.populate(rs);

		} finally {
			try {
				release();
			} catch (Exception ex) {
				LOGGER.error(ex);
			}
		}

		return rowSet;
	}

	public javax.sql.RowSet fetchDetachedRowSet_No_release() {

		ResultSet rs = null;
		CachedRowSetImpl_New rowSet = null;
		try {

			rowSet = new CachedRowSetImpl_New();
			rs = prstmt.executeQuery();
			rowSet.populate(rs);

		} catch (Exception ex) {

			LOGGER.error(ex);
		}
		return rowSet;
	}

	public javax.sql.RowSet fetchDetachedRowSetWithoutTrim_No_release() {

		ResultSet rs = null;
		CachedRowSetImpl rowSet = null;
		try {

			rowSet = new CachedRowSetImpl();
			rs = prstmt.executeQuery();
			rowSet.populate(rs);

		} catch (Exception ex) {
			LOGGER.error(prstmt);
			LOGGER.error(ex);
		}
		return rowSet;
	}

	public TransactionManager(String whereiam) throws SQLException {
		initialize(whereiam);

	}

	@Override
	public void finalize() throws Throwable {
		if (!released) {
			final String devError = "DEV_ERROR-vahanservice online  :  TransactionManager.release()"
					+ " called. Developer has not called tmgr.release() explicitly."
					+ " Please call tmgr.release() explicitly : " + whereiam;

		}
		super.finalize();
	}

	private void initialize(String whereiam) throws SQLException {
		transactionID = ++transactionCounter;
		try {
			con = ConnectionPooling.getDBConnection();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		con.setAutoCommit(false);
		setStmt(con.createStatement());
		sqlList.clear();
		this.whereiam = whereiam;
		transactionSuccessfull = false;
		// Initialize the already committed transaction flag
		commitHasBeenCalledAlready = false;
		// Initialize flag that the resoureces are assigned
		released = false;

	}

	public void commit() throws SQLException {
		// Check that this method is called once only
		if (commitHasBeenCalledAlready) {
			final String msg = "DEV_ERROR : Please call TransactionManager.commit()"
					+ " only once. You already have called it on the"
					+ " same object of TransactionManager. If you want to"
					+ " call commit() again then create a fresh object" + " -OR- call reset() on this object first.";
			throw new SQLException(msg);
		}

		// If the commit has been called only when the transaction invoves
		// only one DML, then inform the developer by logging the message.
		// if (sqlList.size() <= 1) {
		// LOGGER.info(" 0.o.^.o.0 This transaction" + " has only one DML.
		// Better use" + " TransactionManager.executeDMLIndividualTnx(sql)" + "
		// 0.o.^.o.0 " + whereiam);
		// }
		// Commit has been called, so set the commitHasBeenCalledAlready
		commitHasBeenCalledAlready = true;

		// Commit now
		con.commit();

		// Control reaches here means transaction is successfull
		transactionSuccessfull = true;
		con.setAutoCommit(true);
	}

	public void release() throws Exception {
		// Increment the release counter
		releasedTransactionCounter++;

		// Set the flag that the release method has been called
		released = true;

		// If the PreparedStatement is created by the user then close it.

		try {

		} finally {

			if (prstmt != null) {
				prstmt.close();
			}
			if (con != null) {
				con.close();
			}

		}

	}

	public PreparedStatement prepareStatement(final String sql) throws SQLException {
		if (con == null) {
			initialize(whereiam);
		}

		prstmt = null;
		if (!CommonUtils.isNullOrBlank(sql)) {
			prstmt = con.prepareStatement(sql);
		}
		return prstmt;
	}

	public PreparedStatement prepareStatement(String sql, int scroll, int conqyirency) throws SQLException {

		prstmt = null;
		if (!CommonUtils.isNullOrBlank(sql)) {
			prstmt = con.prepareStatement(sql, scroll, conqyirency);
		}
		return prstmt;
	}

	public void reset(String whereIAm) throws SQLException, Exception {
		// Release the resources if it is not released when the reset is called
		if (!released) {
			release();
		}

	}

	/**
	 * Return the transactionSuccessfull flag.
	 *
	 * @return True if the transaction was comitted successfully else false.
	 */
	public boolean isTransactionSuccessfull() {
		return transactionSuccessfull;
	}

	public boolean isReleased() {
		return released;
	}

	public String getWhereiam() {
		return whereiam;
	}

	public int getTransactionID() {
		return transactionID;
	}

	public static int getReleasedTransactionCounter() {
		return releasedTransactionCounter;
	}

	public static int getTransactionCounter() {
		return transactionCounter;
	}

	public Connection getConnectionRef() {
		return con;
	}

	@Override
	public String toString() {

		StringBuffer sqls = new StringBuffer();
		int sqlCount = sqlList.size();
		for (int i = 0; i < sqlCount; i++) {
			sqls.append((String) sqlList.get(i));
			if (i != sqlCount - 1) {
				sqls.append("\n");
			}
		}

		// Make toString
		String str = "whereiam=" + whereiam + " transactionSuccessfull=" + transactionSuccessfull
				+ " commitHasBeenCalledAlready=" + commitHasBeenCalledAlready + " transactionID=" + transactionID
				+ " transactionCounter=" + transactionCounter + " releasedTransactionCounter="
				+ releasedTransactionCounter + " sqlCount=" + sqlCount + "\n" + sqls.toString();
		return str;
	}

	/**
	 * @return the stmt
	 */
	public Statement getStmt() {
		return stmt;
	}

	/**
	 * @param stmt
	 *            the stmt to set
	 */
	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
}