package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.ItemDTO;

public interface ItemService {
    List<ItemDTO> getAllItems();    // 查全部
    List<ItemDTO> getItemsByActive(Boolean active);    // 查啟用/停用
    ItemDTO getItemById(String itemId);    // 查單一
    ItemDTO addItem(ItemDTO itemDTO);    // 新增
    ItemDTO updateItem(String itemId, ItemDTO itemDTO);    // 修改
    void deleteItem(String itemId);    // 刪除
}
