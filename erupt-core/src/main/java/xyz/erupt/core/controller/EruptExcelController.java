package xyz.erupt.core.controller;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.erupt.annotation.fun.PowerObject;
import xyz.erupt.annotation.query.Condition;
import xyz.erupt.core.annotation.EruptRecordOperate;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.exception.EruptWebApiRuntimeException;
import xyz.erupt.core.invoke.DataProxyInvoke;
import xyz.erupt.core.naming.EruptOperateConfig;
import xyz.erupt.core.prop.EruptProp;
import xyz.erupt.core.service.EruptCoreService;
import xyz.erupt.core.service.EruptExcelService;
import xyz.erupt.core.service.EruptService;
import xyz.erupt.core.service.I18NTranslateService;
import xyz.erupt.core.util.EruptUtil;
import xyz.erupt.core.util.Erupts;
import xyz.erupt.core.util.SecurityUtil;
import xyz.erupt.core.view.EruptApiModel;
import xyz.erupt.core.view.EruptModel;
import xyz.erupt.core.view.Page;
import xyz.erupt.core.view.TableQueryVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * 对Excel数据的处理
 *
 * @author YuePeng
 * date 10/15/18.
 */
@RestController
@RequestMapping(EruptRestPath.ERUPT_EXCEL)
@RequiredArgsConstructor
public class EruptExcelController {

    private final EruptProp eruptProp;

    private final EruptExcelService dataFileService;

    private final EruptModifyController eruptModifyController;

    private final EruptService eruptService;

    private final I18NTranslateService i18NTranslateService;

    //模板下载
    @RequestMapping(value = "/template/{erupt}")
    @EruptRouter(authIndex = 2, verifyType = EruptRouter.VerifyType.ERUPT)
    public void getExcelTemplate(@PathVariable("erupt") String eruptName,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        if (eruptProp.isCsrfInspect() && SecurityUtil.csrfInspect(request, response)) return;
        EruptModel eruptModel = EruptCoreService.getErupt(eruptName);
        Erupts.powerLegal(eruptModel, PowerObject::isImportable);
        dataFileService.createExcelTemplate(eruptModel).write(EruptUtil.downLoadFile(request, response,
                eruptModel.getErupt().name() + "_template" + EruptExcelService.XLS_FORMAT));
    }

    //导出
    @PostMapping("/export/{erupt}")
    @EruptRecordOperate(value = "导出Excel", dynamicConfig = EruptOperateConfig.class)
    @EruptRouter(authIndex = 2, verifyType = EruptRouter.VerifyType.ERUPT)
    public void exportData(@PathVariable("erupt") String eruptName,
                           @RequestBody(required = false) List<Condition> conditions,
                           HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        if (eruptProp.isCsrfInspect() && SecurityUtil.csrfInspect(request, response)) {
            return;
        }
        EruptModel eruptModel = EruptCoreService.getErupt(eruptName);
        Erupts.powerLegal(eruptModel, PowerObject::isExport);
        TableQueryVo tableQueryVo = new TableQueryVo();
        tableQueryVo.setPageIndex(1);
        tableQueryVo.setDataExport(true);
        Optional.ofNullable(conditions).ifPresent(tableQueryVo::setCondition);
        Page page = eruptService.getEruptData(eruptModel, tableQueryVo, null);
        Workbook wb = dataFileService.exportExcel(eruptModel, page);
        DataProxyInvoke.invoke(eruptModel, (dataProxy -> dataProxy.excelExport(wb)));
        wb.write(EruptUtil.downLoadFile(request, response, eruptModel.getErupt().name() + EruptExcelService.XLSX_FORMAT));
    }

    //导入
    @PostMapping("/import/{erupt}")
    @EruptRecordOperate(value = "导入Excel", dynamicConfig = EruptOperateConfig.class)
    @EruptRouter(authIndex = 2, verifyType = EruptRouter.VerifyType.ERUPT)
    @Transactional(rollbackOn = Exception.class)
    public EruptApiModel importExcel(@PathVariable("erupt") String eruptName, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        EruptModel eruptModel = EruptCoreService.getErupt(eruptName);
        Erupts.powerLegal(eruptModel, PowerObject::isImportable, "No import permission");
        if (file.isEmpty()) {
            return EruptApiModel.errorApi("上传失败，请选择文件");
        }
        String fileName = file.getOriginalFilename();
        List<JsonObject> list;
        int i = 1;
        try {
            i++;
            if (fileName.endsWith(EruptExcelService.XLS_FORMAT)) {
                list = dataFileService.excelToEruptObject(eruptModel, new HSSFWorkbook(file.getInputStream()));
            } else if (fileName.endsWith(EruptExcelService.XLSX_FORMAT)) {
                list = dataFileService.excelToEruptObject(eruptModel, new XSSFWorkbook(file.getInputStream()));
            } else {
                throw new EruptWebApiRuntimeException("上传文件格式必须为Excel");
            }
        } catch (Exception e) {
            throw new EruptWebApiRuntimeException("Excel解析异常，出错行数：" + i + "，原因：" + e.getMessage(), e);
        }
        for (int j = 0; j < list.size(); j++) {
            EruptApiModel eruptApiModel = eruptModifyController.addEruptData(eruptName, list.get(j), null, request);
            if (eruptApiModel.getStatus() == EruptApiModel.Status.ERROR) {
                throw new EruptWebApiRuntimeException("数据入库异常，出错行数：" + (j + 1) + "，原因：" + eruptApiModel.getMessage());
            }
        }
        return EruptApiModel.successApi();
    }


}
