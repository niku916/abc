package nic.vahan.db.connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface TransactionManagerInterface {
	public javax.sql.RowSet fetchDetachedRowSet() throws SQLException;
	public javax.sql.RowSet fetchDetachedRowSetWithoutTrim() throws SQLException;
	public javax.sql.RowSet fetchDetachedRowSet_No_release() throws SQLException;
	public javax.sql.RowSet fetchDetachedRowSetWithoutTrim_No_release() throws SQLException;
	public void commit() throws SQLException;
	public void release() throws Exception;
	public PreparedStatement prepareStatement(final String sql) throws SQLException;
	public PreparedStatement prepareStatement(String sql, int scroll, int conqyirency) throws SQLException;
	public void reset(String whereIAm) throws SQLException, Exception;
	
	
}
