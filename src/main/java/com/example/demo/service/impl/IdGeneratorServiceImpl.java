package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.entity.IdSequence;
import com.example.demo.repository.IdSequenceRepository;
import com.example.demo.service.IdGeneratorService;

@Service
public class IdGeneratorServiceImpl implements IdGeneratorService {

    @Autowired
    private IdSequenceRepository idSequenceRepository;

    @Override
    @Transactional // 確保+1與儲存是同一個交易，避免錯誤
    public String generateId(String prefix) {
        IdSequence idSequence = idSequenceRepository.findById(prefix) // 從資料庫找 prefix 對應的前綴詞
                .orElse(new IdSequence(prefix, 0)); // 若尚未建立則預設從 0 開始
        
        // +1，取得新的流水號
        Integer nextValue = idSequence.getCurrentValue() + 1;
        idSequence.setCurrentValue(nextValue);
        // 儲存新的流水號
        idSequenceRepository.save(idSequence);

        // 例如 TP00001, MB00042
        return String.format("%s%05d", prefix, nextValue);
    }
}
