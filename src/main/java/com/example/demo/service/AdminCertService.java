package com.example.demo.service;

import com.example.demo.exception.CertException;
import com.example.demo.model.dto.AdminCert;

public interface AdminCertService {
	AdminCert getAdminCert(String username, String password) throws CertException;
}
