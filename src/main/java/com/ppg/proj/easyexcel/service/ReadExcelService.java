package com.ppg.proj.easyexcel.service;

import org.springframework.web.multipart.MultipartFile;


public interface ReadExcelService {
    void largeExcelRead(MultipartFile file);
}
