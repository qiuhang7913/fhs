package com.self.framework.bill.action;

import com.self.framework.annotation.SysLog;
import com.self.framework.base.BaseAction;
import com.self.framework.bill.bean.IncomeBillBean;
import com.self.framework.bill.service.IncomeBillService;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(value = "/bill/income")
public class IncomeBillAction extends BaseAction<IncomeBillBean> {

    @Autowired
    private IncomeBillService incomeBillService;

    /**
     * 更新账单数据:用于将已启用的账单生成代缴费的账单
     * @return
     */
    @RequestMapping(value = "updateBillAtGenerate", method = {RequestMethod.POST})
    @PreAuthorize("hasAnyAuthority('super','/bill/income:update')")
    @SysLog(logOptDes = "更新账单数据:用于将已启用的账单生成代缴费的账单")
    @ResponseBody
    public HttpResult<Map> updateBillAtGenerate(@RequestBody @Valid IncomeBillBean incomeBillBean){
        Integer addCode = incomeBillService.addOrUpdata(incomeBillBean);
        if (addCode.equals(BusinessCommonConstamt.ZERO_CODE)){
            return HttpResult.errorResult();
        }
        return HttpResult.okResult();
    }

    /**
     * 更新账单数据:用于将代缴费的账单确认缴费
     * @return
     */
    @RequestMapping(value = "updateBillAtConfirm", method = {RequestMethod.POST})
    @PreAuthorize("hasAnyAuthority('super','/bill/income:update')")
    @SysLog(logOptDes = "更新账单数据:用于将代缴费的账单确认缴费")
    @ResponseBody
    public HttpResult<Map> updateBillAtConfirm(@RequestBody @Valid IncomeBillBean incomeBillBean){
        Integer addCode = incomeBillService.updateBillAtConfirm(incomeBillBean);
        if (addCode.equals(BusinessCommonConstamt.ZERO_CODE)){
            return HttpResult.errorResult();
        }
        return HttpResult.okResult();
    }
}
