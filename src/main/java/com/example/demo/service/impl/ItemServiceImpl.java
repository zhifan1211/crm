package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.ItemMapper;
import com.example.demo.model.dto.ItemDTO;
import com.example.demo.model.entity.Item;
import com.example.demo.repository.ItemRepository;
import com.example.demo.service.IdGeneratorService;
import com.example.demo.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private IdGeneratorService idGeneratorService;
	
	@Override
	public List<ItemDTO> getAllItems() {
		return itemRepository.findAll()
							 .stream()
							 .map(itemMapper::toDto)
							 .toList();
	}

	@Override
	public List<ItemDTO> getItemsByActive(Boolean active) {
	    return itemRepository.findByActive(active)
                .stream()
                .map(itemMapper::toDto)
                .toList();
	}

	@Override
	public ItemDTO getItemById(String itemId) {
		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new NotFoundException("ITEM_NOT_FOUND","查無商品類型"));
		return itemMapper.toDto(item);
	}

	@Override
	public ItemDTO addItem(ItemDTO itemDTO) {
		itemDTO.setItemId(idGeneratorService.generateId("IT"));
		Item entity = itemMapper.toEntity(itemDTO);
		Item saved = itemRepository.saveAndFlush(entity);
		return itemMapper.toDto(saved);
	}

	@Override
	public ItemDTO updateItem(String itemId, ItemDTO itemDTO) {
		if(!itemRepository.existsById(itemId)) {
			throw new NotFoundException("ITEM_NOT_FOUND","修改失敗，商品不存在");
		}
		Item entity = itemMapper.toEntity(itemDTO);
		Item saved = itemRepository.saveAndFlush(entity);
		return itemMapper.toDto(saved);
	}

	@Override
	public void deleteItem(String itemId) {
		if(!itemRepository.existsById(itemId)) {
			throw new NotFoundException("ITEM_NOT_FOUND","刪除失敗，商品不存在");
		}
	    itemRepository.deleteById(itemId);
	}
	
}
