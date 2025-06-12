package com.example.demo.service;

import com.example.demo.exception.CertException;
import com.example.demo.model.dto.MemberCert;

public interface MemberCertService {
	MemberCert getMemberCert(String phoneNumber, String password) throws CertException;
}
