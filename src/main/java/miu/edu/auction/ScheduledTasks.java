package miu.edu.auction;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import miu.edu.auction.domain.BiddingPayment;
import miu.edu.auction.domain.Product;
import miu.edu.auction.domain.User;
import miu.edu.auction.repository.BiddingPaymentRepository;
import miu.edu.auction.repository.ProductRepository;
import miu.edu.auction.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static miu.edu.auction.util.AppConstant.*;

@Component
@Slf4j
@Data
public class ScheduledTasks {
    private final ProductRepository productRepository;
    private final BiddingPaymentRepository biddingPaymentRepository;
    private final UserRepository userRepository;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 9000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        // disbursed money from bidding payment to customer account for looser account
        // 1. get all products & check bid time after the bidding time close the bid
        List<Product> productList = productRepository.findAllByStatusAndBidDueDateBefore(SAVE_AND_RELEASE, LocalDateTime.now());
        productList.forEach(product -> {
            product.setStatus(PRODUCT_SOLD_STATUS);
            if (product.getHighestBidUser() == null) {
                product.setStatus(PRODUCT_CLOSED_STATUS);
            } else {
                // disbursed money
                // get all from bidding payment
                List<BiddingPayment> biddingPayments = biddingPaymentRepository.findAllByProductAndStatus(product, BIDDING_PAYMENT_DEPOSIT);
                biddingPayments.forEach(biddingPayment -> {
                    if (!biddingPayment.getUser().getId().equals(product.getHighestBidUser().getId())) {
                        //deposit on customer
                        User customer = biddingPayment.getUser();
                        customer.setBalance(customer.getBalance() + biddingPayment.getAmount());
                        userRepository.save(customer);
                        // change the bidding payment status
                        biddingPayment.setStatus(BIDDING_PAYMENT_DISBURSED);
                        biddingPaymentRepository.save(biddingPayment);
                    }
                });
            }
            log.info("::id" + product.getId() + "::name::" + product.getName() + "::due date::" + product.getBidDueDate());

            productRepository.save(product);
        });
    }
}