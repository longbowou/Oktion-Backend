package miu.edu.oktion.service;

import lombok.RequiredArgsConstructor;
import miu.edu.oktion.domain.BiddingPayment;
import miu.edu.oktion.domain.User;
import miu.edu.oktion.repository.BiddingPaymentRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BiddingPaymentServiceImpl implements BiddingPaymentService {
    private final BiddingPaymentRepository biddingPaymentRepository;


    @Override
    public BiddingPayment getBiddingPaymentByUser(User customer) {
        return biddingPaymentRepository.findBiddingPaymentByUser(customer);
    }

    @Override
    public BiddingPayment create(BiddingPayment biddingPayment) {
        return biddingPaymentRepository.save(biddingPayment);
    }


}

