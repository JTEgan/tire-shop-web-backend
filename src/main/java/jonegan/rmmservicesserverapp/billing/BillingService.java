package jonegan.rmmservicesserverapp.billing;

import jonegan.rmmservicesserverapp.entities.*;
import jonegan.rmmservicesserverapp.repositories.CustomerRepository;
import jonegan.rmmservicesserverapp.repositories.DeviceRepository;
import jonegan.rmmservicesserverapp.repositories.ServiceCostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BillingService {
    private static final BigDecimal PER_DEVICE_CHARGE = new BigDecimal("4.00");
    private final CustomerRepository customerRepository;
    private final ServiceCostRepository serviceCostRepository;
    private final DeviceRepository deviceRepository;



    public BillingService(CustomerRepository customerRepository, ServiceCostRepository serviceCostRepository, DeviceRepository deviceRepository) {
        this.customerRepository = customerRepository;
        this.serviceCostRepository = serviceCostRepository;
        this.deviceRepository = deviceRepository;
    }

    public Bill calculateBill(String customerId) {
        Map<ServiceDeviceTypeCostId, BigDecimal> costByDeviceTypeMap = buildServiceCostMap();
        Customer customer = customerRepository.getOne(customerId);
        List<Device> customerDevices = deviceRepository.findDevicesByCustomer_Id(customerId);

        BigDecimal devicesCost = PER_DEVICE_CHARGE.multiply(new BigDecimal(customerDevices.size()));
        log.info("Devices cost: " + devicesCost);

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
        BigDecimal totalBill = totalServicesCost.add(devicesCost);
        log.info("totalBill: " + totalBill);

        // todo, call repos, calculate the bill
        return new Bill(totalBill, devicesCost, costDetails);
    }


/*
    public Bill calculateBillJPA1(String customerId) {
        Map<ServiceDeviceTypeCostId, BigDecimal> costMap = buildServiceCostMap();
        Customer customer = customerRepository.getOne(customerId);
        List<Device> customerDevices = deviceRepository.findDevicesByCustomer_Id(customerId);
        BigDecimal devicesCost = PER_DEVICE_CHARGE.multiply(new BigDecimal(customerDevices.size()));
        log.info("Devices cost: " + devicesCost);

        Set<jonegan.rmmservicesserverapp.entities.Service> subscribedServices = customer.getSubscribedServices();
        Map<String, BigDecimal> costDetails = new HashMap<>();
        subscribedServices.forEach(service -> {
            BigDecimal defaultCost = costMap.getOrDefault(new ServiceDeviceTypeCostId(service.getId(), null), BigDecimal.ZERO);
            customerDevices.forEach(device -> {
                ServiceDeviceTypeCostId serviceDeviceTypeCostId = new ServiceDeviceTypeCostId(service.getId(), device.getDeviceType());
                BigDecimal costPerDevice = costMap.getOrDefault(serviceDeviceTypeCostId, defaultCost);
                costDetails.computeIfPresent(service.getId(), (k, v) -> v.add(costPerDevice));
            });

            BigDecimal serviceCost = costDetails.get(service.getId());
            log.info(service.getServiceName() + " cost: $" + serviceCost);
        });
        BigDecimal totalServicesCost = costDetails.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("totalServicesCost: " + totalServicesCost);
        BigDecimal totalBill = totalServicesCost.add(devicesCost);
        log.info("totalBill: " + totalBill);

        costDetails.put("Devices", devicesCost);

        // todo, call repos, calculate the bill
        return new Bill(totalBill, Collections.emptyMap());
    }
*/

    private Map<ServiceDeviceTypeCostId, BigDecimal> buildServiceCostMap() {
        return serviceCostRepository.findAll().stream()
                .collect(Collectors.toMap(
                        ServiceDeviceTypeCost::getServiceDeviceTypeCostId,
                        sc -> sc.getMonthlyCost().setScale(2, RoundingMode.HALF_EVEN)));
    }
}
