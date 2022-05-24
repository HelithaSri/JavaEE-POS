package bo;

import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBOImpl;
import bo.custom.impl.OrderDetailsBOImpl;
import bo.custom.impl.OrdersBOImpl;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:51 PM
 * @project JavaEE POS Backend
 */

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBoFactory(){
        if(boFactory == null){
            boFactory= new BOFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BoTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case ORDER:
                return new OrdersBOImpl();
            case ORDER_DETAILS:
                return new OrderDetailsBOImpl();
            default:
                return null;
        }
    }
    public enum  BoTypes{
        CUSTOMER,ITEM,ORDER,ORDER_DETAILS
    }
}
