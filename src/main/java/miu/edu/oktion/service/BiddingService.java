package miu.edu.oktion.service;

import miu.edu.oktion.domain.Bidding;
import miu.edu.oktion.domain.BiddingPayment;
import miu.edu.oktion.domain.Product;
import miu.edu.oktion.domain.User;
import miu.edu.oktion.dto.BiddingDTO;
import miu.edu.oktion.dto.BiddingResponseDTO;
import miu.edu.oktion.dto.MakePaymentDTO;

import java.util.List;

public interface BiddingService {
    Integer totalBiddingProductCountByCustomer(User customer);

    Bidding create(BiddingDTO bidding, User user) throws Exception;

    Bidding makeWinningBidPayment(MakePaymentDTO makePaymentDTO, User user) throws Exception;


    Bidding getBiddingByCustomerId(String customerId);

    BiddingPayment depositAmountByCustomer(Product product, User user) throws Exception;

    List<BiddingResponseDTO> getBiddingByProduct(String productId);
}
