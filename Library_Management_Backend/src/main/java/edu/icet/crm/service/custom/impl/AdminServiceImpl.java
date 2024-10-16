package edu.icet.crm.service.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.repository.AdminRepository;
import edu.icet.crm.service.custom.AdminService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final ObjectMapper mapper;
}
