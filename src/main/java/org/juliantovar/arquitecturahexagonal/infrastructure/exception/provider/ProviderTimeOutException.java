package org.juliantovar.arquitecturahexagonal.infrastructure.exception.provider;

import org.juliantovar.arquitecturahexagonal.infrastructure.exception.ExternalServiceException;

public class ProviderTimeOutException extends ExternalServiceException {
    public ProviderTimeOutException(String message) {
        super(message);
    }
}
