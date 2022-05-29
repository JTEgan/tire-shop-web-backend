package tireshop.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "work_order", indexes = {
        @Index(name = "customer_id_for_order_idx", columnList = "customer_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "number_UNIQUE", columnNames = {"number"})
})
public class WorkOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "number", nullable = false)
    private Integer id;

    @Column(name = "total_cost", length = 45)
    private String totalCost;

    @Column(name = "status", length = 45)
    private String status;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "note", length = 500)
    private String note;

    @Column(name = "time_create")
    private Instant timeCreate;

    @Column(name = "time_update_status")
    private Instant timeUpdateStatus;

    @Column(name = "id")
    private Integer id1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(Instant timeCreate) {
        this.timeCreate = timeCreate;
    }

    public Instant getTimeUpdateStatus() {
        return timeUpdateStatus;
    }

    public void setTimeUpdateStatus(Instant timeUpdateStatus) {
        this.timeUpdateStatus = timeUpdateStatus;
    }

    public Integer getId1() {
        return id1;
    }

    public void setId1(Integer id1) {
        this.id1 = id1;
    }

}