package miu.edu.auction.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.auction.dto.BiddingDTO;
import miu.edu.auction.dto.MakePaymentDTO;
import miu.edu.auction.service.BiddingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static miu.edu.auction.util.AppConstant.ERROR;
import static miu.edu.auction.util.AppConstant.SUCCESS;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/bidding")
public class BiddingController extends BaseController {
    private final BiddingService biddingService;

    /*
    @Deprecated
    @PostMapping("/start")
    public ResponseEntity<?> depositAmountByCustomer(@RequestBody BiddingPaymentDTO biddingPaymentDTO) {
        try {
            return getResponse(SUCCESS, biddingService.depositAmountByCustomer(biddingPaymentDTO, getUser()), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());
            return getResponse(ERROR, "Unable to get products", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    */
    @PostMapping
    public ResponseEntity<?> biddingByCustomers(@RequestBody BiddingDTO biddingDTO) {
        try {
            return getResponse(SUCCESS, biddingService.create(biddingDTO, getUser()), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());
            if (e.getMessage().equals("Not sufficient balance available")) {
                return getResponse(ERROR, e.getMessage(), HttpStatus.PRECONDITION_FAILED);
            }
            if (e.getMessage().equals("Bidding amount low than the highest bid")) {
                return getResponse(ERROR, e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
            }
            return getResponse(ERROR, "Unable to get products", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("/full-payment")
    public ResponseEntity<?> makeWinningBidPayment(@RequestBody MakePaymentDTO makePaymentDTO) {
        try {
            return getResponse(SUCCESS, biddingService.makeWinningBidPayment(makePaymentDTO, getUser()), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return getResponse(ERROR, "Unable to get products", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getBiddingProduct(@PathVariable("id") String productId) {
        try {
            return getResponse(SUCCESS, biddingService.getBiddingByProduct(productId), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return getResponse(ERROR, "Unable to get products", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
