package com.pingok.vocational.controller.customer;

import com.pingok.vocational.domain.customer.CustomerProblemsEx;
import com.pingok.vocational.domain.customer.TblCustomerProblems;
import com.pingok.vocational.domain.customer.vo.CustomerProblemsVo;
import com.pingok.vocational.service.customer.ICustomerProblemsService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客户投诉与咨询记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/customerProblems")
public class CustomerProblemsController extends BaseController {

    @Autowired
    private ICustomerProblemsService customerProblemsService;

    @RequiresPermissions("vocational:customerProblems:idInfo")
    @Log(title = "客户投诉与咨询记录-根据ID查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/idInfo")
    public AjaxResult idInfo(@RequestParam(name = "id") Long id)
    {
        TblCustomerProblems idInfo = customerProblemsService.selectCustomerProblemsById(id);
        return AjaxResult.success(idInfo);
    }

//    @RequiresPermissions("vocational:customerProblems:info")
    @Log(title = "客户投诉与咨询记录-分页查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/info")
    public TableDataInfo info(CustomerProblemsVo customerProblemsVo){
        startPage();
        List<Map> info = customerProblemsService.selectInfo(customerProblemsVo);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:customerProblems:add")
    @Log(title = "新增客户投诉与咨询", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblCustomerProblems tblCustomerProblems)
    {
        return toAjax(customerProblemsService.insertCustomer(tblCustomerProblems));
    }

    @RequiresPermissions("vocational:customerProblems:edit")
    @Log(title = "客户投诉与咨询回复", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TblCustomerProblems tblCustomerProblems)
    {
        return toAjax(customerProblemsService.updateCustomer(tblCustomerProblems));
    }

    @Log(title = "咨询管理",businessType = BusinessType.EXPORT)
    @RequiresPermissions("vocational:customerProblems:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, CustomerProblemsVo customerProblemsVo){
        List<CustomerProblemsEx> list=customerProblemsService.selectCustomerProblems(customerProblemsVo);
        ExcelUtil<CustomerProblemsEx> util = new ExcelUtil<>(CustomerProblemsEx.class);
        util.exportExcel(response,list,"咨询投诉单");
    }
}
