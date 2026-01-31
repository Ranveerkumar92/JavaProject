package com.airtribe.meditrack.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Immutable class representing a summary of a Bill.
 * This class provides a read-only view of bill information.
 */
public final class BillSummary implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final String billId;
    private final String patientId;
    private final String appointmentId;
    private final double totalAmount;
    private final boolean paid;
    private final LocalDateTime billDate;
    private final LocalDateTime paidDate;
    
    /**
     * Constructs a BillSummary from a Bill object.
     *
     * @param bill the Bill to summarize
     */
    public BillSummary(Bill bill) {
        this.billId = bill.getBillId();
        this.patientId = bill.getPatientId();
        this.appointmentId = bill.getAppointmentId();
        this.totalAmount = bill.getTotalAmount();
        this.paid = bill.isPaid();
        this.billDate = bill.getBillDate();
        this.paidDate = bill.getPaidDate();
    }
    
    // Getters only (immutable)
    public String getBillId() {
        return billId;
    }
    
    public String getPatientId() {
        return patientId;
    }
    
    public String getAppointmentId() {
        return appointmentId;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public boolean isPaid() {
        return paid;
    }
    
    public LocalDateTime getBillDate() {
        return billDate;
    }
    
    public LocalDateTime getPaidDate() {
        return paidDate;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BillSummary{");
        sb.append("billId=\"").append(billId).append('"');
        sb.append(", patientId=\"").append(patientId).append('"');
        sb.append(", appointmentId=\"").append(appointmentId).append('"');
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", paid=").append(paid);
        sb.append(", billDate=").append(billDate);
        sb.append(", paidDate=").append(paidDate);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BillSummary)) return false;
        BillSummary that = (BillSummary) o;
        return Double.compare(that.totalAmount, totalAmount) == 0 &&
                paid == that.paid &&
                Objects.equals(billId, that.billId) &&
                Objects.equals(patientId, that.patientId) &&
                Objects.equals(appointmentId, that.appointmentId) &&
                Objects.equals(billDate, that.billDate) &&
                Objects.equals(paidDate, that.paidDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(billId, patientId, appointmentId, totalAmount, paid, billDate, paidDate);
    }
}
