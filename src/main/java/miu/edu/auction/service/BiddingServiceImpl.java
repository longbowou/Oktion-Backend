package miu.edu.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.auction.domain.Bidding;
import miu.edu.auction.domain.BiddingPayment;
import miu.edu.auction.domain.Product;
import miu.edu.auction.domain.User;
import miu.edu.auction.dto.BiddingDTO;
import miu.edu.auction.dto.BiddingResponseDTO;
import miu.edu.auction.dto.CustomerDTO;
import miu.edu.auction.dto.MakePaymentDTO;
import miu.edu.auction.repository.BiddingPaymentRepository;
import miu.edu.auction.repository.BiddingRepository;
import miu.edu.auction.repository.ProductRepository;
import miu.edu.auction.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static miu.edu.auction.util.AppConstant.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BiddingServiceImpl implements BiddingService {
    private final BiddingRepository biddingRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final BiddingPaymentRepository biddingPaymentRepository;

    @Override
    public Integer totalBiddingProductCountByCustomer(User customer) {
        return biddingRepository.countDistinctByCustomer(customer);
    }

   /* @Override
    public Integer depositAmountByCustomer(User customer) {
        return biddingRepository.depositAmountByCustomer(customer);
    }*/

    @Override
    @Transactional
    public Bidding create(BiddingDTO biddingDTO, User user) throws Exception {
        // call deposit money when first bid placed by customer
        Product product = productRepository.findById(biddingDTO.getProductId()).get();
        if (product.getStatus().equals(PRODUCT_SOLD_STATUS)) {
            log.error("Product is sold. Unable to save bidding");
            throw new Exception("Product is sold. Unable to save bidding");
        }
        if (product.getHighestBidAmount() > biddingDTO.getAmount()) {
            throw new Exception("Bidding amount low than the highest bid");
        }
        // 1. is there any existing bid by customer on the product
        if (biddingRepository.countBiddingByProductAndCustomer(product, user) == 0) {
            try {
                depositAmountByCustomer(product, user);
            } catch (Exception ex) {
                log.error("Unable to save the bidding deposit amount");
                throw ex;
            }
            log.info("deposit amount saved");
        }

        Bidding bidding = new Bidding();
        bidding.setStatus(BID_RUNNING);
        bidding.setProduct(productRepository.findById(biddingDTO.getProductId()).get());
        bidding.setCustomer(user);
        bidding.setAmount(biddingDTO.getAmount());
        bidding.setCreatedOn(LocalDateTime.now());
        // update product with highest bid amount
        product.setHighestBidUser(user);
        product.setHighestBidAmount(biddingDTO.getAmount());
        productRepository.save(product);
        return biddingRepository.save(bidding);
    }

    @Override
    @Transactional
    public Bidding makeWinningBidPayment(MakePaymentDTO makePaymentDTO, User user) throws Exception {
        //TODO
        Product product = productRepository.findById(makePaymentDTO.getProductId()).get();
        if (!product.getHighestBidUser().getId().equals(user.getId())) {
            throw new Exception("User not authorized to make payment");
        }
        // Get winning price & subtract from balance
        Bidding winingBid = biddingRepository.findTopByProductAndCustomerOrderByAmountDesc(product, user);

        double remainingPaymentAmount = winingBid.getAmount() - product.getDepositAmount();
        // winning customer payment
        BiddingPayment winningPayment = new BiddingPayment();
        winningPayment.setUser(user);
        winningPayment.setAmount(remainingPaymentAmount);
        winningPayment.setProduct(product);
        winningPayment.setStatus(BIDDING_PAYMENT_WINNING);
        winningPayment.setCreatedOn(LocalDateTime.now());
        biddingPaymentRepository.save(winningPayment);

        // deduct in customer end
        user.setBalance(user.getBalance() - remainingPaymentAmount);
        // save customer
        userRepository.save(user);

        User seller = product.getSeller();

        // deposit in seller end
        seller.setBalance(seller.getBalance() + winingBid.getAmount());
        userRepository.save(seller);

        //update the product status
        product.setStatus(PRODUCT_SOLD_AND_PAID_STATUS);
        productRepository.save(product);

        return winingBid;
    }


    @Override
    public Bidding getBiddingByCustomerId(String customerId) {
        return biddingRepository.getBiddingByCustomerId(customerId);
    }

    public BiddingPayment getBiddingByCustomerAndProductAndStatus(User customer, Product product) {
        return biddingPaymentRepository.findBiddingPaymentByUserAndProductAndStatus(customer, product, BIDDING_PAYMENT_DEPOSIT);
    }

    @Override
    public BiddingPayment depositAmountByCustomer(Product product, User customer) throws Exception {
        BiddingPayment existingBiddingPayment = getBiddingByCustomerAndProductAndStatus(customer, product);

        if (existingBiddingPayment == null) {
            double depositAmount = product.getPrice() * (product.getDeposit() / 100);
            if (depositAmount > customer.getBalance()) {
                throw new Exception("Not sufficient balance available");
            }
            BiddingPayment biddingPayment = new BiddingPayment();
            biddingPayment.setUser(customer);
            biddingPayment.setAmount(depositAmount);
            biddingPayment.setStatus(BIDDING_PAYMENT_DEPOSIT);
            biddingPayment.setProduct(product);
            biddingPayment.setCreatedOn(LocalDateTime.now());

            biddingPaymentRepository.save(biddingPayment);

            customer.setBalance(customer.getBalance() - depositAmount);
            userRepository.save(customer);

            return biddingPayment;
        } else {
            return existingBiddingPayment;
        }
    }

    @Override
    public List<BiddingResponseDTO> getBiddingByProduct(String productId) {
        Product product = productRepository.findById(productId).get();

        List<Bidding> biddingList = biddingRepository.findAllByProductOrderByCreatedOnDesc(product);
        List<BiddingResponseDTO> biddingResponseDTOS = new ArrayList<>();

        biddingList.stream().forEach(bidding -> {
            BiddingResponseDTO biddingResponseDTO = new BiddingResponseDTO();
            biddingResponseDTO.setId(bidding.getId());
            biddingResponseDTO.setAmount(bidding.getAmount());
            biddingResponseDTO.setCreatedOn(bidding.getCreatedOn());
            biddingResponseDTO.setStatus(bidding.getStatus());

            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(bidding.getCustomer().getId());
            customerDTO.setName(bidding.getCustomer().getName());
            customerDTO.setEmail(bidding.getCustomer().getEmail());

            biddingResponseDTO.setCustomer(customerDTO);
            biddingResponseDTOS.add(biddingResponseDTO);
        });

        return biddingResponseDTOS;
    }

}
