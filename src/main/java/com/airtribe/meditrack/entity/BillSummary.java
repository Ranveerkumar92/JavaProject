package com.airtribe.meditrack.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
        return "BillSummary{" +
                "billId='" + billId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", appointmentId='" + appointmentId + '\'' +
                ", totalAmount=" + totalAmount +
                ", paid=" + paid +
                ", billDate=" + billDate +
                ", paidDate=" + paidDate +
                '}';
    }
}
