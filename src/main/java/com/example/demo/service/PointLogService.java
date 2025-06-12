package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.model.dto.PointLogDTO;
import com.example.demo.model.dto.PointLogHistoryDTO;
import com.example.demo.model.dto.PointLogViewDTO;
import com.example.demo.model.entity.Admin;
import com.example.demo.model.entity.Member;
import com.example.demo.model.entity.PointType;

public interface PointLogService {
	void addLog(PointLogDTO pointLogDTO); // 新增單筆點數紀錄
	List<PointLogViewDTO> getLogsViewByMemberIdToAdmin(String memberId); // 管理者查詢單一會員的點數紀錄
	List<PointLogViewDTO> getLogByAllMemberToAdmin(); // 管理者查詢所有會員的點數紀錄
	List<PointLogHistoryDTO> getLogsByMemberIdToMember(String memberId); // 會員查詢自己的點數紀錄
}
