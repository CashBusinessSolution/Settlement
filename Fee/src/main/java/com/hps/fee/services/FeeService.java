package com.hps.fee.services;

import com.hps.DTOS.FeeDTO;
import com.hps.DTOS.TransactionDTO;
import com.hps.fee.kafkaProducer.FeeProducer;
import com.hps.fee.mappers.FeeMapper;
import com.hps.fee.mappers.MerchantMapper;
import com.hps.fee.models.Fee;
import com.hps.fee.models.Merchant;
import com.hps.fee.repositories.FeeRepository;
import com.hps.fee.repositories.MerchantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeeService {


    private final MerchantRepository merchantRepository;
    private final FeeRepository feeRepository;
    private final MerchantMapper merchantMapper;
    private final FeeProducer feeProducer;


    public Fee createFees(Fee fee) {
        return feeRepository.save(fee);
    }

    // Récupération de tous les frais
    public List<Fee> getAllFees() {
        return feeRepository.findAll();
    }

    // Récupération des frais par ID
    public Fee getFeesById(Long id) {
        return feeRepository.findById(id).orElse(null);
    }

    // Suppression des frais par ID
    public void deleteFees(Long id) {
        feeRepository.deleteById(id);
    }

    public BigDecimal calculateFee(BigDecimal amount, BigDecimal taxRate) {
        return amount.multiply(taxRate);
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
                BigDecimal feeAmount = calculateFee(amount, taxRate);

                Fee detailedFee = Fee.builder()
                        .Amount(amount)
                        .feeAmount(feeAmount)
                        .TaxRate(taxRate)
                        .MerchantId(merchant.getMerchantId())
                        .TransactionId(transactionDTO.getTransactionId())
                        .FeeStructure(merchant.getFeeStructure())
                        .recipient(merchant.getRecipient())
                        .SettlementOption(merchant.getSettlementOption())
                        .build();

                // Traitement en fonction de la structure des frais
                switch (merchant.getFeeStructure()) {
                    case "reduce_directly":
                        // Réduit directement du montant
                        BigDecimal reducedAmount = amount.subtract(feeAmount);

                        // Met à jour le montant dans la base de données avec le montant réduit
                        Fee reducedFee = Fee.builder()
                                .Amount(reducedAmount)
                                .feeAmount(feeAmount)
                                .TaxRate(taxRate)
                                .MerchantId(merchant.getMerchantId())
                                .TransactionId(transactionDTO.getTransactionId())
                                .FeeStructure(merchant.getFeeStructure())
                                .recipient(merchant.getRecipient())
                                .SettlementOption(merchant.getSettlementOption())
                                .build();

                        // Enregistrement de l'objet Fee détaillé avec le montant réduit
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

                // Créer un FeeDTO et l'envoyer à Kafka
                // feeDTO = new FeeDTO(transactionDTO.getTransactionId(), fee.getFeeAmount());
                //feeProducer.sendFee(feeDTO);
                } else {
                    log.warn("Settlement option and type message don't match ");
                }
            } else {
                log.error("Merchant with ID {} not found", transactionDTO.getMerchantId());
            }
        } else {
            log.warn("Amount or merchant ID is null in the transaction message.");
        }
    }}
