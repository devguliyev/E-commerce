package com.example.demo.config;

import com.example.demo.domain.enums.FileSize;
import com.example.demo.domain.enums.FileType;
import com.example.demo.domain.enums.UploadContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UploadPolicy {
    private final Map<UploadContext,UploadRule> rules= Map.of(
            UploadContext.PRODUCT_IMAGE,new UploadRule(1, FileSize.MB, FileType.IMAGE),
            UploadContext.SLIDE_IMAGE,new UploadRule(2,FileSize.MB,FileType.IMAGE,"png","jpeg"),
            UploadContext.USER_AVATAR,new UploadRule(512,FileSize.KB,FileType.IMAGE)
    );
    public UploadRule getRule(UploadContext context){
        return rules.get(context);
    }
}
