package dao;

import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:00 PM
 * @project JavaEE POS Backend
 */

public interface CrudDAO<S,Id> extends SuperDAO {
    boolean add(S s) throws SQLException;
    boolean delete(String id) throws SQLException;
    boolean update(S s) throws SQLException;
}
