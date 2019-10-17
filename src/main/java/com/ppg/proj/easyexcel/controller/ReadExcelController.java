package com.ppg.proj.easyexcel.controller;

import com.ppg.proj.contants.ApiConst;
import com.ppg.proj.easyexcel.service.ReadExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = ApiConst.TAG_READ)
@RestController
@RequestMapping("/proj")
public class ReadExcelController {

    @Autowired
    private ReadExcelService readExcelService;

    @ApiOperation("读取大文件excel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true)
    })
    @PostMapping("/large-excel-read")
    public ResponseEntity largeExcelRead(@RequestPart(value = "file") MultipartFile file) {
        readExcelService.largeExcelRead(file);
        return ResponseEntity.ok(1);
    }
}
