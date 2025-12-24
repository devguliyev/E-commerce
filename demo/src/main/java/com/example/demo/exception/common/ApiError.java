package com.example.demo.exception.common;

import java.time.Instant;

public record ApiError(
        String code,
        String message,
        int status,
        Instant timestamp,
        String path
) { }
