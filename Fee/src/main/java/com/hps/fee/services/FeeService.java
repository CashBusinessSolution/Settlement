package com.hps.fee.services;

import com.hps.DTOS.FeeDTO;
import com.hps.DTOS.MerchantDTO;
import com.hps.DTOS.TransactionDTO;
import com.hps.fee.kafkaProducer.FeeProducer;
import com.hps.fee.mappers.FeeMapper;
import com.hps.fee.models.Fee;
import com.hps.fee.models.Merchant;
import com.hps.fee.repositories.FeeRepository;
import com.hps.fee.repositories.MerchantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeeService {

    private final MerchantRepository merchantRepository;
    private final FeeRepository feeRepository;
    private final FeeProducer feeProducer;
    private static final BigDecimal FIXED_AMOUNT = new BigDecimal("11000.00");
    private static final BigDecimal FIRST_RANGE_MAX_AMOUNT = new BigDecimal("2000000.00");


    private static final BigDecimal BREAKPOINT_1 = new BigDecimal("2000000.00");
    private static final BigDecimal BREAKPOINT_2 = new BigDecimal("5000000.00");
    private static final BigDecimal BREAKPOINT_3 = new BigDecimal("10000000.00");

    private static final BigDecimal RATE_1 = new BigDecimal("0.05");
    private static final BigDecimal RATE_2 = new BigDecimal("0.04");
    private static final BigDecimal RATE_3 = new BigDecimal("0.03");
    private static final BigDecimal RATE_4 = new BigDecimal("0.02");

    public BigDecimal calculateStandardCdf(BigDecimal amount, BigDecimal taxRate) {
        return amount.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);

    }

    public static BigDecimal calculateBulkedPrice(BigDecimal amount, BigDecimal taxRate) {
        if (amount.compareTo(FIRST_RANGE_MAX_AMOUNT) <= 0) {
            return FIXED_AMOUNT;
        } else {
            BigDecimal excessAmount = amount.subtract(FIRST_RANGE_MAX_AMOUNT);
            BigDecimal secondRangeFee = calculateSecondRangeFee(excessAmount, taxRate);
            return FIXED_AMOUNT.add(secondRangeFee);
        }
    }

    public static BigDecimal calculateSecondRangeFee(BigDecimal excessAmount, BigDecimal taxRate) {
        if (excessAmount.compareTo(BigDecimal.ZERO) > 0) {
            return excessAmount.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public static BigDecimal calculateTieredCdf(BigDecimal amount) {

        BigDecimal feeAtBreakpoint1 = BREAKPOINT_1.multiply(RATE_1).setScale(2, RoundingMode.HALF_UP);

        BigDecimal feeBetweenBreakpoints1And2 = BREAKPOINT_2.subtract(BREAKPOINT_1)
                .multiply(RATE_2).setScale(2, RoundingMode.HALF_UP);

        BigDecimal feeBetweenBreakpoints2And3 = BREAKPOINT_3.subtract(BREAKPOINT_2)
                .multiply(RATE_3).setScale(2, RoundingMode.HALF_UP);

        if (amount.compareTo(BREAKPOINT_1) <= 0) {
            return amount.multiply(RATE_1).setScale(2, RoundingMode.HALF_UP);

        } else if (amount.compareTo(BREAKPOINT_2) <= 0) {
            return feeAtBreakpoint1.add(amount.subtract(BREAKPOINT_1)
                    .multiply(RATE_2).setScale(2, RoundingMode.HALF_UP));

        } else if (amount.compareTo(BREAKPOINT_3) <= 0) {
            return feeAtBreakpoint1.add(feeBetweenBreakpoints1And2).add(amount.subtract(BREAKPOINT_2)
                    .multiply(RATE_3).setScale(2, RoundingMode.HALF_UP));
        } else {
            return feeAtBreakpoint1.add(feeBetweenBreakpoints1And2).add(feeBetweenBreakpoints2And3)
                    .add(amount.subtract(BREAKPOINT_3).multiply(RATE_4).setScale(2, RoundingMode.HALF_UP));
        }

    }

    public void processTransaction(TransactionDTO transactionDTO) {
        if (transactionDTO.getAmount() != null && transactionDTO.getMerchantId() != null) {
            Merchant merchant = merchantRepository.findById(transactionDTO.getMerchantId()).orElse(null);

            if (merchant != null) {
                if (
                        ("on_drop".equals(merchant.getSettlementOption()) && "drop".equals(transactionDTO.getTypeMessage())) ||
                                ("on_removal".equals(merchant.getSettlementOption()) && "removal".equals(transactionDTO.getTypeMessage())) ||
                                ("on_verification".equals(merchant.getSettlementOption()) && "verification".equals(transactionDTO.getTypeMessage()))
                ) {
                    BigDecimal taxRate = merchant.getTaxRate();
                    BigDecimal amount = transactionDTO.getAmount();
                    BigDecimal feeAmount = switch (merchant.getCdfType()) {
                        case "Standard CDF" -> calculateStandardCdf(amount, taxRate);
                        case "Bulked Price" -> calculateBulkedPrice(amount, taxRate);
                        case "Tiered CDF" -> calculateTieredCdf(amount);
                        default -> throw new IllegalArgumentException("Unknown CDF type: " + merchant.getCdfType());
                    };

                    Fee detailedFee = Fee.builder()
                            .Amount(amount)
                            .feeAmount(feeAmount)
                            .TaxRate(taxRate)
                            .MerchantId(merchant.getMerchantId())
                            .TransactionId(transactionDTO.getTransactionId())
                            .FeeStructure(merchant.getFeeStructure())
                            .bankAccountNumber(merchant.getBankAccountNumber())
                            .SettlementOption(merchant.getSettlementOption())
                            .accountBalance(merchant.getAccountBalance())
                            .transactionDate(LocalDate.now())
                            .build();

                    LocalDate currentDate = LocalDate.now();
                    LocalDate lastTransactionDate = merchant.getLastTransactionDate();

                    if (lastTransactionDate != null &&
                            lastTransactionDate.getYear() == currentDate.getYear() &&
                            lastTransactionDate.getMonth() == currentDate.getMonth()) {

                        merchant.setTotalAmount(merchant.getTotalAmount().add(amount));
                    } else {
                        merchant.setTotalAmount(amount);
                    }

                    merchant.setLastTransactionDate(currentDate);


                    log.info("Updated total amount for merchant {}: {}", merchant.getMerchantId(), merchant.getTotalAmount());

                    merchantRepository.save(merchant);

                    switch (merchant.getFeeStructure()) {
                        case "reduce_directly":
                            BigDecimal reducedAmount = amount.subtract(feeAmount);
                            Fee reducedFee = Fee.builder()
                                    .Amount(reducedAmount)
                                    .feeAmount(feeAmount)
                                    .TaxRate(taxRate)
                                    .MerchantId(merchant.getMerchantId())
                                    .TransactionId(transactionDTO.getTransactionId())
                                    .FeeStructure(merchant.getFeeStructure())
                                    .bankAccountNumber(merchant.getBankAccountNumber())
                                    .SettlementOption(merchant.getSettlementOption())
                                    .accountBalance(merchant.getAccountBalance())
                                    .transactionDate(LocalDate.now())
                                    .build();

                            feeRepository.save(reducedFee);
                            FeeDTO feeDTO = FeeMapper.INSTANCE.feeToFeeDTO(reducedFee);
                            feeDTO.setAmount(reducedAmount);
                            feeProducer.sendFeeMessage(feeDTO);
                            log.info("Amount after fee reduction: {}", reducedAmount);
                            break;
                        case "invoice_later":
                            log.info("Invoice will be sent later for fee amount: {}", feeAmount);
                            feeRepository.save(detailedFee);
                            break;
                        default:
                            log.warn("Unknown FeeStructure: {}", detailedFee.getFeeStructure());
                    }
                    log.info("Calculated fee for amount {} is {}", amount, feeAmount);
                } else {
                    log.warn("Settlement option and type message don't match");
                }
            } else {
                log.error("Merchant with ID {} not found", transactionDTO.getMerchantId());
            }
        } else {
            log.warn("Amount or merchant ID is null in the transaction message.");
        }
    }

    public void updateMerchant(MerchantDTO merchantDTO) {
        Merchant existingMerchant = merchantRepository.findById(merchantDTO.getMerchantId())
                .orElseThrow(() -> new RuntimeException("Merchant not found with ID: " + merchantDTO.getMerchantId()));

        existingMerchant.setSettlementOption(merchantDTO.getSettlementOption());
        existingMerchant.setFeeStructure(merchantDTO.getFeeStructure());
        existingMerchant.setTaxRate(merchantDTO.getTaxRate());
        existingMerchant.setBankAccountNumber(merchantDTO.getBankAccountNumber());
        existingMerchant.setAccountBalance(merchantDTO.getAccountBalance());

        merchantRepository.save(existingMerchant);
    }

    public void deleteMerchant(MerchantDTO merchantDTO) {
        Merchant existingMerchant = merchantRepository.findById(merchantDTO.getMerchantId())
                .orElseThrow(() -> new RuntimeException("Merchant not found with ID: " + merchantDTO.getMerchantId()));

        merchantRepository.delete(existingMerchant);
    }
}
