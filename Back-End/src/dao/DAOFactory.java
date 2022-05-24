package dao;

import dao.custom.impl.CustomerDAOImpl;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.OrderDetailsDAOImpl;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:01 PM
 * @project JavaEE POS Backend
 */

public class DAOFactory{
    private static DAOFactory daoFactory;

    public static DAOFactory getDAOFactory() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public SuperDAO getDAO(DAOTypes type){
        switch (type){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM :
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAILS:
                return new OrderDetailsDAOImpl();
            default:
                return null;
        }
    }

    public enum DAOTypes {
        CUSTOMER,ITEM,ORDER,ORDER_DETAILS
    }
}
