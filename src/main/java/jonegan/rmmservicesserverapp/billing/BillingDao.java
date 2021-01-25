package jonegan.rmmservicesserverapp.billing;

import jonegan.rmmservicesserverapp.entities.DeviceType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class BillingDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BillingDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public long getDeviceCount(String customerId) {
        String sql = "select count(*) from device where customer_id = :customer_id";
        Long deviceCount = namedParameterJdbcTemplate.queryForObject(sql, paramMap(customerId), Long.class);
        return deviceCount == null ? 0L : deviceCount;
    }

    public List<ServiceChargeDetail> getServiceCharges(String customerId) {
        String sql = "with jointable as (\n" +
                "    select d.id device_id, d.device_type, d.system_name, s.id service_id, s.service_name, s.monthly_cost\n" +
                "    from device d,\n" +
                "         customer_subscribed_services css,\n" +
                "         service s\n" +
                "    where css.subscribed_services_id = s.id\n" +
                "      and d.customer_id = :customer_id\n" +
                ")\n" +
                "select j.service_id, j.service_name, sdtc.device_type, count(*) as device_count,\n" +
                "       sum(coalesce(sdtc.monthly_cost, j.monthly_cost, 0)) as monthly_cost\n" +
                "from jointable j\n" +
                "    left outer join service_device_type_cost sdtc\n" +
                "        on sdtc.service_id = j.service_id\n" +
                "        and sdtc.device_type = j.device_type\n" +
                "group by j.service_id, j.service_name, sdtc.device_type\n" +
                "order by j.service_id, j.service_name, sdtc.device_type";

        return namedParameterJdbcTemplate.query(sql, paramMap(customerId), this::mapServiceChargeRow);
    }

    private ServiceChargeDetail mapServiceChargeRow(ResultSet resultSet, int i) throws SQLException {
        String deviceType = resultSet.getString("device_type");

        return ServiceChargeDetail.builder()
                .serviceId(resultSet.getString("service_id"))
                .serviceName(resultSet.getString("service_name"))
                .deviceType(deviceType == null ? null : DeviceType.valueOf(deviceType))
                .deviceCount(resultSet.getInt("device_count"))
                .monthlyCost(resultSet.getBigDecimal("monthly_cost"))
                .build();
    }

    private Map<String, String> paramMap(String customerId) {
        return Collections.singletonMap("customer_id", customerId);
    }
}
