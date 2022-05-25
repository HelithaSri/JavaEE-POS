package bo.custom;

import bo.SuperBO;

import javax.json.JsonArrayBuilder;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:55 PM
 * @project JavaEE POS Backend
 */

public interface OrderDetailsBO extends SuperBO {
    JsonArrayBuilder fetchAllOrderDetails();
}
