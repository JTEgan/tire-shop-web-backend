package tireshop.repo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import tireshop.controller.dto.OrderLineItemDto;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class OrderLineItemDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrderLineItemDao(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<OrderLineItemDto> getOrderLineItems(Integer orderId) {
        String sql = "select o.count count_ordered, o.product_id, aspectratio, brand, diameter, model_number, sale_price, size, width\n" +
                "from order_product_relation o, inventory i\n" +
                "where o.product_id = i.id\n" +
                "and o.order_id = :orderId";

        final Map<String, String> paramMap = Collections.singletonMap("orderId", String.valueOf(orderId));
        return namedParameterJdbcTemplate.query(sql, paramMap, this::mapLineItemRow);
    }

    private OrderLineItemDto mapLineItemRow(ResultSet resultSet, int i) throws SQLException {
        return OrderLineItemDto.builder()
                .countOrdered(resultSet.getInt("count_ordered"))
                .productId(resultSet.getInt("product_id"))
                .aspectratio(resultSet.getInt("aspectratio"))
                .diameter(resultSet.getInt("diameter"))
                .modelNumber(resultSet.getString("model_number"))
                .salePrice(resultSet.getDouble("sale_price"))
                .size(resultSet.getString("size"))
                .width(resultSet.getInt("width"))
                .build();
    }
}
