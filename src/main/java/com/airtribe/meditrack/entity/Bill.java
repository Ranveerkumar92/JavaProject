package com.airtribe.meditrack.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.airtribe.meditrack.interface_impl.Payable;

/**
 * Represents a Bill for services rendered to a patient.
 */
public class Bill implements Payable, Serializable {
    private static final long serialVersionUID = 1L;
    
    private String billId;
    private String patientId;
    private String appointmentId;
    private double consultationFee;
    private double labCharges;
    private double otherCharges;
    private double totalAmount;
    private boolean paid;
    private LocalDateTime billDate;
    private LocalDateTime paidDate;
    
    /**
     * Constructs a Bill with the specified details.
     *
     * @param billId the unique bill identifier
     * @param patientId the patient's ID
     * @param appointmentId the appointment's ID
     * @param consultationFee the consultation fee
     * @param labCharges the lab charges
     * @param otherCharges other charges
     */
    public Bill(String billId, String patientId, String appointmentId,
                double consultationFee, double labCharges, double otherCharges) {
        if (consultationFee < 0 || labCharges < 0 || otherCharges < 0) {
            throw new IllegalArgumentException("Fees and charges cannot be negative");
        }
        this.billId = billId;
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.consultationFee = consultationFee;
        this.labCharges = labCharges;
        this.otherCharges = otherCharges;
        this.totalAmount = consultationFee + labCharges + otherCharges;
        this.paid = false;
        this.billDate = LocalDateTime.now();
        this.paidDate = null;
    }
    
    // Getters and Setters
    public String getBillId() {
        return billId;
    }
    
    public void setBillId(String billId) {
        this.billId = billId;
    }
    
    public String getPatientId() {
        return patientId;
    }
    
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    
    public String getAppointmentId() {
        return appointmentId;
    }
    
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }
    
    public double getConsultationFee() {
        return consultationFee;
    }
    
    public void setConsultationFee(double consultationFee) {
        if (consultationFee < 0) {
            throw new IllegalArgumentException("Consultation fee cannot be negative");
        }
        this.consultationFee = consultationFee;
        recalculateTotalAmount();
    }
    
    public double getLabCharges() {
        return labCharges;
    }
    
    public void setLabCharges(double labCharges) {
        if (labCharges < 0) {
            throw new IllegalArgumentException("Lab charges cannot be negative");
        }
        this.labCharges = labCharges;
        recalculateTotalAmount();
    }
    
    public double getOtherCharges() {
        return otherCharges;
    }
    
    public void setOtherCharges(double otherCharges) {
        if (otherCharges < 0) {
            throw new IllegalArgumentException("Other charges cannot be negative");
        }
        this.otherCharges = otherCharges;
        recalculateTotalAmount();
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    private void recalculateTotalAmount() {
        this.totalAmount = this.consultationFee + this.labCharges + this.otherCharges;
    }
    
    public LocalDateTime getBillDate() {
        return billDate;
    }
    
    public void setBillDate(LocalDateTime billDate) {
        this.billDate = billDate;
    }
    
    public LocalDateTime getPaidDate() {
        return paidDate;
    }
    
    public void setPaidDate(LocalDateTime paidDate) {
        this.paidDate = paidDate;
    }
    
    @Override
    public double getAmount() {
        return totalAmount;
    }
    
    @Override
    public void markAsPaid() {
        this.paid = true;
        this.paidDate = LocalDateTime.now();
    }
    
    @Override
    public boolean isPaid() {
        return paid;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bill{");
        sb.append("billId=\"").append(billId).append('"');
        sb.append(", patientId=\"").append(patientId).append('"');
        sb.append(", appointmentId=\"").append(appointmentId).append('"');
        sb.append(", consultationFee=").append(consultationFee);
        sb.append(", labCharges=").append(labCharges);
        sb.append(", otherCharges=").append(otherCharges);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", paid=").append(paid);
        sb.append(", billDate=").append(billDate);
        sb.append(", paidDate=").append(paidDate);
        sb.append('}');
        return sb.toString();
    }
}
