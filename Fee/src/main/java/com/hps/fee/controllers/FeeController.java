package com.hps.fee.controllers;

import com.hps.fee.models.Fee;
import com.hps.fee.services.FeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/fee")
public class FeeController {
    private final FeeService feeService;


    }


