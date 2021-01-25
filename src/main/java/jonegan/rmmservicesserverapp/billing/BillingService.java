package jonegan.rmmservicesserverapp.billing;

import jonegan.rmmservicesserverapp.entities.*;
import jonegan.rmmservicesserverapp.repositories.CustomerRepository;
import jonegan.rmmservicesserverapp.repositories.DeviceRepository;
import jonegan.rmmservicesserverapp.repositories.ServiceCostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BillingService {
    private static final BigDecimal PER_DEVICE_CHARGE = new BigDecimal("4.00");
    private final CustomerRepository customerRepository;
    private final ServiceCostRepository serviceCostRepository;
    private final DeviceRepository deviceRepository;
    private final BillingDao billingDao;

    public BillingService(CustomerRepository customerRepository, ServiceCostRepository serviceCostRepository, DeviceRepository deviceRepository, BillingDao billingDao) {
        this.customerRepository = customerRepository;
        this.serviceCostRepository = serviceCostRepository;
        this.deviceRepository = deviceRepository;
        this.billingDao = billingDao;
    }

    public Bill calculateBill(String customerId) {
        Map<ServiceDeviceTypeCostId, BigDecimal> costByDeviceTypeMap = buildServiceCostMap();
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(IllegalArgumentException::new);
        List<Device> customerDevices = deviceRepository.findDevicesByCustomer_Id(customerId);

        Set<Service> subscribedServices = customer.getSubscribedServices();
        Map<Service, BigDecimal> costDetails = new HashMap<>();
        subscribedServices.forEach(service -> {
            customerDevices.forEach(device -> {
                ServiceDeviceTypeCostId serviceDeviceTypeCostId = new ServiceDeviceTypeCostId(service.getId(), device.getDeviceType());
                BigDecimal costForDevice = costByDeviceTypeMap.getOrDefault(serviceDeviceTypeCostId, service.getMonthlyCost());
                costDetails.merge(service, costForDevice, BigDecimal::add);
            });

            BigDecimal serviceCost = costDetails.get(service);
            log.info(service.getServiceName() + " cost: $" + serviceCost);
        });
        BigDecimal totalServicesCost = costDetails.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("totalServicesCost: " + totalServicesCost);

        BigDecimal devicesCost = PER_DEVICE_CHARGE.multiply(new BigDecimal(customerDevices.size()));
        log.info("Devices cost: " + devicesCost);

        BigDecimal totalBill = totalServicesCost.add(devicesCost);
        log.info("totalBill: " + totalBill);

        return new Bill(totalBill, devicesCost, costDetails);
    }

    public Bill calculateBillJdbc(String customerId) {
        List<ServiceChargeDetail> serviceCharges = billingDao.getServiceCharges(customerId);
        Map<Service, BigDecimal> serviceChargeSummary = new HashMap<>();

        serviceCharges.forEach(scd -> {
            log.info("Adding charge {} for service {} and deviceType {}", scd.monthlyCost, scd.serviceId, scd.deviceType);
            Service service = new Service(scd.serviceId, scd.serviceName, null);
            serviceChargeSummary.merge(service, scd.monthlyCost, BigDecimal::add);
        });
        BigDecimal totalServicesCost = serviceChargeSummary.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("totalServicesCost: " + totalServicesCost);

        long deviceCount = billingDao.getDeviceCount(customerId);
        BigDecimal devicesCost = PER_DEVICE_CHARGE.multiply(new BigDecimal(deviceCount));
        log.info("Devices cost: " + devicesCost);

        BigDecimal totalBill = totalServicesCost.add(devicesCost);
        log.info("totalBill: " + totalBill);

        return new Bill(totalBill, devicesCost, serviceChargeSummary);
    }

    private Map<ServiceDeviceTypeCostId, BigDecimal> buildServiceCostMap() {
        return serviceCostRepository.findAll().stream()
                .collect(Collectors.toMap(
                        ServiceDeviceTypeCost::getServiceDeviceTypeCostId,
                        sc -> sc.getMonthlyCost().setScale(2, RoundingMode.HALF_EVEN)));
    }
}
