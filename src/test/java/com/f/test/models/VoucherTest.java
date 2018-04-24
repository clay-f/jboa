package com.f.test.models;

import com.f.pojo.Voucher;
import com.f.services.VoucherService;
import com.f.test.TestHelper;
import org.junit.Test;

import java.util.Objects;

public class VoucherTest {
    private VoucherService voucherService = (VoucherService) TestHelper.getInstance().getBean("voucherServiceImpl");

    @Test
    public void getVouchersList() {
        assert voucherService != null;
        assert voucherService.getAllVouchers().size() > 0;
    }

    @Test
    public void getVoucherById() {
        assert voucherService.getVoucherById(1).getId() != null;
    }

    @Test
    public void getSize() {
        assert voucherService.size() > 0;
    }

    @Test
    public void addVoucher() {
        Voucher voucher = new Voucher("foo" + Objects.toString(System.currentTimeMillis()), (float) 0.00, 1);
        assert voucherService.saveVoucher(voucher);
    }

    @Test
    public void deleteVoucher() {
        int lastVal = voucherService.size();
        assert voucherService.deleteVoucherById(lastVal);
    }
}
