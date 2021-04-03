package com.microservice.paymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.paymentservice.entiry.Payment;
import com.microservice.paymentservice.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;
@Slf4j
@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Payment doPayment(Payment payment) throws JsonProcessingException {
        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionId(UUID.randomUUID().toString());
        log.info("PaymentService request : {}", new ObjectMapper().writeValueAsString(payment));
        return paymentRepository.save(payment);
    }

    public String paymentProcessing() {
        //  api should be third party payment gateway (paypal, paytm...)
        return new Random().nextBoolean()? "success" : "false";
    }

    public Payment findPaymentHistoryByOrderId(int orderId) throws JsonProcessingException {
        Payment payment = paymentRepository.findByOrderId(orderId);
        log.info("PaymentService findPaymentHistoryByOrderId : {}", new ObjectMapper().writeValueAsString(payment));
        return payment;
    }
}
