package com.example.demo.pointType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Unit;
import com.example.demo.service.PointTypeService;

@SpringBootTest
public class PointTypeJPAAddTest {
	
	@Autowired
	private PointTypeService pointTypeService;
	
	@Test
	public void testPointTypeAdd() {
		pointTypeService.addType("TP001", "消費積點", Category.add, 1, "客人每消費滿$100即可累積1點", true);
		System.out.println("點數類型建立成功！");
	}
}
