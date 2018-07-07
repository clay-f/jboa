package com.f.api;

import com.f.core.common.ResponseJsonResult;
import com.f.controller.BaseController;
import com.f.core.enums.EventType;
import com.f.core.event.EventContent;
import com.f.core.event.EventExecutor;
import com.f.core.pojo.Employee;
import com.f.core.pojo.Voucher;
import com.f.services.impl.AbstractGenericService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping(value = "/api/vouchers")
@RestController(value = "voucherController")
public class VoucherController extends BaseController<Voucher, Integer> {
    @Autowired
    public VoucherController(@Qualifier("voucherService") AbstractGenericService abstractGenericService) {
        super(abstractGenericService);
    }

    @PutMapping(value = "/{id}")
    public ResponseJsonResult update(@PathVariable(value = "id") Integer id, @RequestBody Map<String, String> params, @SessionAttribute(required = true) Employee currentUser) throws JsonProcessingException {
        Voucher previousVoucher = (Voucher) getAbstractGenericService().get(params.get("id").toString());
        Map<String, Object> map = Maps.newHashMap();
        switch (currentUser.getOaPosition().getId()) {
            case 1:
                previousVoucher.getVoucherDetail().setDes(params.get("des"));
                previousVoucher.setItem(params.get("item"));
                previousVoucher.setAccount(Float.parseFloat(params.get("account")));
                break;
            case 2:
            case 3:
                map.put("type", Voucher.VoucherType.UPDATE_RESULT_STATE);
                map.put("id", params.get("id"));
                map.put("state", params.get("state"));
                EventExecutor.fireEvent(new EventContent(getUserId().toString(), EventType.CHANGE_VOUCHER, map));
                break;
            case 4:
                map.put("type", Voucher.VoucherType.UPDATE_VOUCHER_STATE);
                map.put("id", params.get("id"));
                map.put("state", params.get("state"));
                EventExecutor.fireEvent(new EventContent(getUserId().toString(), EventType.CHANGE_VOUCHER, map));
                break;
        }
        getAbstractGenericService().update(previousVoucher);
        return ResponseJsonResult.successResponse("ok");
    }

    @PostMapping(value = "/save", consumes = "application/json")
    public ResponseJsonResult save(@RequestBody Map<String, String> map) {
        Voucher voucher = new Voucher(map.get("item"), Float.parseFloat(map.get("account")));
        voucher.getVoucherDetail().setDes(map.get("des"));
        voucher.getEmployee().setId(19);
        getAbstractGenericService().save(voucher);
        return ResponseJsonResult.successResponse("create voucher success");
    }
}
