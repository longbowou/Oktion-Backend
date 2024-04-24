package miu.edu.auction.service;

import miu.edu.auction.domain.Bidding;
import miu.edu.auction.domain.BiddingPayment;
import miu.edu.auction.domain.Product;
import miu.edu.auction.domain.User;
import miu.edu.auction.dto.BiddingDTO;
import miu.edu.auction.dto.BiddingResponseDTO;
import miu.edu.auction.dto.MakePaymentDTO;

import java.util.List;

public interface BiddingService {
    Integer totalBiddingProductCountByCustomer(User customer);

    Bidding create(BiddingDTO bidding, User user) throws Exception;

    Bidding makeWinningBidPayment(MakePaymentDTO makePaymentDTO, User user) throws Exception;


    Bidding getBiddingByCustomerId(String customerId);

    BiddingPayment depositAmountByCustomer(Product product, User user) throws Exception;

    List<BiddingResponseDTO> getBiddingByProduct(String productId);
}
