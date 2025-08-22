package com.training.service;

import com.training.dao.BillDAO;
import com.training.dao.BillDAOImpl;
import com.training.dao.PaymentDAO;
import com.training.dao.PaymentDAOImpl;
import com.training.model.Bill;
import com.training.model.Payment;
import com.training.model.PaymentMethod;
import com.training.utility.DateUtil;

import java.util.List;
import java.util.UUID;

public class PaymentServiceImpl implements PaymentService {
    private final BillDAO billDAO = new BillDAOImpl();
    private final PaymentDAO paymentDAO = new PaymentDAOImpl();

    @Override
    public void processPayment(Bill bill, double amount, String paymentMethod, String transactionId) throws Exception {
        bill = billDAO.findBillById(bill.getBillId());
        if (bill == null) {
            throw new IllegalArgumentException("Bill not found");
        }
        if (amount <= 0 || amount > bill.getAmount()) {
            throw new IllegalArgumentException("Invalid payment amount");
        }
        Payment payment = new Payment();
        payment.setPaymentId(UUID.randomUUID().toString());
        payment.setBillId(bill.getBillId());
        payment.setAmount(amount);
        payment.setMethod(PaymentMethod.valueOf(paymentMethod));
        payment.setPaymentDate(DateUtil.getCurrentDate());
        payment.setTransactionId(transactionId);
        paymentDAO.createPayment(payment);
        bill.getPayments().add(payment);
        if (bill.getAmount() <= getTotalPaidAmount(bill.getBillId())) {
            bill.setStatus("PAID");
            billDAO.updateBill(bill);
        }
    }

    @Override
    public Payment getPaymentById(String paymentId) throws Exception {
        return paymentDAO.findPaymentById(paymentId);
    }

    @Override
    public List<Payment> getPaymentsByBillId(String billId) throws Exception {
        return paymentDAO.findPaymentsByBillId(billId);
    }

    private double getTotalPaidAmount(String billId) throws Exception {
        List<Payment> payments = paymentDAO.findPaymentsByBillId(billId);
        return payments.stream().mapToDouble(Payment::getAmount).sum();
    }
}