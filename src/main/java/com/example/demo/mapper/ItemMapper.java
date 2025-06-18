package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.dto.ItemDTO;
import com.example.demo.model.entity.Item;

@Component
public class ItemMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ItemDTO toDto(Item item) {
		return modelMapper.map(item, ItemDTO.class);
	}
	
	public Item toEntity(ItemDTO itemDTO) {
		return modelMapper.map(itemDTO, Item.class);
	}
}
