package com.example.demo22.service;

import com.example.demo22.Model.Voucher;
import com.example.demo22.Rep.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// VoucherService.java
@Service
public class VoucherService {

    @Autowired
    private VoucherRepository repository;

    public List<Voucher> getAllVouchers() {
        return repository.findAll();
    }

    public Optional<Voucher> getVoucherById(int id) {
        return repository.findById(id);
    }

    public Voucher createVoucher(Voucher voucher) {
        return repository.save(voucher);
    }

    public Voucher updateVoucher(int id, Voucher voucher) {
        if (repository.existsById(id)) {
            voucher.setId(id);
            return repository.save(voucher);
        }
        return null;
    }

    public void deleteVoucher(int id) {
        repository.deleteById(id);
    }

    public Voucher useVoucher(int id) throws Exception {
        Optional<Voucher> voucherOpt = repository.findById(id);
        if (voucherOpt.isPresent()) {
            Voucher voucher = voucherOpt.get();
            if ("active".equals(voucher.getStatus())) {
                voucher.setStatus(Voucher.Status.valueOf("inactive"));
                return repository.save(voucher);
            } else {
                throw new Exception("Voucher is not active");
            }
        }
        throw new Exception("Voucher not found");
    }
}