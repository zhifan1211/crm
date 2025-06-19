package com.example.demo.service.impl;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.MemberAlreadyExistException;
import com.example.demo.exception.MemberNotFoundException;
import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.dto.ChangePasswordDTO;
import com.example.demo.model.dto.MemberDTO;
import com.example.demo.model.dto.MemberEditDTO;
import com.example.demo.model.dto.MemberInfoDTO;
import com.example.demo.model.dto.MemberRegisterDTO;
import com.example.demo.model.dto.MemberViewDTO;
import com.example.demo.model.entity.Level;
import com.example.demo.model.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.IdGeneratorService;
import com.example.demo.service.MemberService;
import com.example.demo.service.PointCollectionService;
import com.example.demo.util.Hash;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PointCollectionService pointCollectionService;
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private IdGeneratorService idGeneratorService;
	
	// 查詢所有會員
	@Override
	public List<MemberViewDTO> getAllMembers() {
	    return memberRepository.findAll()
	        .stream()
	        .map(member -> {
	            MemberViewDTO dto = memberMapper.toViewDto(member); 
	            int remain = pointCollectionService.getMemberRemainingPoint(member.getMemberId());
	            dto.setRemainPoint(remain);
	            return dto;
	        })
	        .toList();
	}

	// 管理者用 id 查詢得到指定會員
	@Override
	public MemberViewDTO getMemberViewById(String memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("ID:"+ memberId +"找不到會員"));
		MemberViewDTO dto = memberMapper.toViewDto(member);
		return dto;
	}
	
	// 用 電話 查詢指定會員
	@Override
	public MemberDTO getMemberByPhoneNumber(String phoneNumber) {
		Member member = memberRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new MemberNotFoundException("電話:"+ phoneNumber +"找不到會員"));
		MemberDTO dto = memberMapper.toDto(member);
		return dto;
	}

	// 新增會員(會員註冊)
	@Override
	public void addMember(MemberRegisterDTO memberRegisterDTO) {
	    // 自動產生會員 ID
	    String newMemberId = idGeneratorService.generateId("MB");

	    // 檢查 phone 是否重複
	    if (memberRepository.findByPhoneNumber(memberRegisterDTO.getPhoneNumber()).isPresent()) {
	        throw new MemberAlreadyExistException("電話已存在，無法註冊");
	    }

	    // 加鹽並雜湊密碼
	    String defPassword = memberRegisterDTO.getBirthDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	    String salt = Hash.getSalt();
	    String passwordHash = Hash.getHash(defPassword, salt);

	    // 組裝 Member 實體
	    Member member = new Member();
	    member.setMemberId(newMemberId);
	    member.setLastName(memberRegisterDTO.getLastName());
	    member.setFirstName(memberRegisterDTO.getFirstName());
	    member.setPhoneNumber(memberRegisterDTO.getPhoneNumber());
	    member.setGender(memberRegisterDTO.getGender());
	    member.setPasswordHash(passwordHash);
	    member.setSalt(salt);
	    member.setLevel(Level.PASSER); // 預設為未認證
	    member.setConfirmEmail(false); // 預設未驗證
	    member.setActive(true);
	    member.setBirthDate(memberRegisterDTO.getBirthDate());

	    // 其餘 email、region等欄位可以先不設定（null）

	    // 存入資料庫
	    memberRepository.saveAndFlush(member);
	}
	
	// 會員更新個人資訊
	@Override
	public MemberEditDTO updateMemberByMember(String memberId, MemberEditDTO memberEditDTO) {
	    // 先查詢原本的會員資料
	    Member member = memberRepository.findById(memberId)
	        .orElseThrow(() -> new MemberNotFoundException("找不到會員 ID: " + memberId));

	    // 若電話有變更，需檢查是否與其他會員重複
	    String newPhoneNumber = memberEditDTO.getPhoneNumber();
	    if (!member.getPhoneNumber().equals(newPhoneNumber)) {
	        memberRepository.findByPhoneNumber(newPhoneNumber).ifPresent(existing -> {
	            throw new MemberAlreadyExistException("電話已被其他會員使用");
	        });
	        member.setPhoneNumber(newPhoneNumber);
	    }

	    // 更新其他基本資訊
	    member.setLastName(memberEditDTO.getLastName());
	    member.setFirstName(memberEditDTO.getFirstName());
	    member.setRegion(memberEditDTO.getRegion());
	    member.setEmail(memberEditDTO.getEmail());

	    // 更新資料
	    memberRepository.saveAndFlush(member);
	    
	    // 判斷條件自動升級成正式會員
	    upgradeMemberLevelIfQualified(member);
	    
	    return new MemberEditDTO(
	    		member.getMemberId(),
	            member.getLastName(),
	            member.getFirstName(),
	            member.getGender().name(),
	            member.getPhoneNumber(),
	            member.getLevel().name(),
	            member.getEmail(),
	            member.getRegion(),
	            member.getBirthDate(),
	            member.getConfirmEmail()
	    );
	}
	
	// 用 id 查詢得到指定會員
	@Override
	public MemberEditDTO getMemberById(String memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("ID:"+ memberId +"找不到會員"));
		MemberEditDTO dto = memberMapper.toEditDto(member);
		return dto;
	}
	
	// 信箱驗證後，將 confirmEmail 轉為 true
	@Override
	@Transactional
	public void setEmailConfirmed(String memberId) {
	    Member member = memberRepository.findById(memberId)
	        .orElseThrow(() -> new RuntimeException("找不到會員"));
	    member.setConfirmEmail(true);
	    memberRepository.save(member);
	}
	
	// 修改密碼
    @Override
    public void changePassword(String memberId, ChangePasswordDTO dto) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("會員不存在"));

        String salt = member.getSalt();
        String passwordHash = member.getPasswordHash();

        String inputHash = Hash.getHash(dto.getOldPassword(), salt);

        if (!inputHash.equals(passwordHash)) {
            throw new RuntimeException("舊密碼錯誤");
        }

        // 產生新密碼雜湊
        String newSalt = Hash.getSalt();
        String newHash = Hash.getHash(dto.getNewPassword(), newSalt);

        member.setSalt(newSalt);
        member.setPasswordHash(newHash);
        memberRepository.save(member);
    }
    
    // 忘記密碼要重設密碼
    @Override
    public void changePasswordByPhone(String phoneNumber, String newPassword) {
        Member member = memberRepository.findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new RuntimeException("會員不存在"));

        String newSalt = Hash.getSalt();
        String newHash = Hash.getHash(newPassword, newSalt);

        member.setSalt(newSalt);
        member.setPasswordHash(newHash);
        memberRepository.save(member);
    }
	
    // 當欄位填妥時，自動升級成正式會員
    @Override
    public void upgradeMemberLevelIfQualified(Member member) {
        if (isFormalQualified(member) && member.getLevel() == Level.PASSER) {
            member.setLevel(Level.FORMAL);
            memberRepository.saveAndFlush(member); // 記得要存檔
        }
    }
    
    // 判斷是否欄位填妥
    @Override
    public boolean isFormalQualified(Member member) {
        return isNotBlank(member.getLastName())
                && isNotBlank(member.getFirstName())
                && member.getGender() != null
                && isNotBlank(member.getPhoneNumber())
                && isNotBlank(member.getPasswordHash())
                && isNotBlank(member.getSalt())
                && isNotBlank(member.getEmail())
                && isNotBlank(member.getRegion())
                && member.getBirthDate() != null
                && Boolean.TRUE.equals(member.getConfirmEmail());
    }
    
    @Override
    public boolean isNotBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }
    
    // 管理者切換會員的啟用/停用狀態
    @Override
    public void toggleActive(String memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException("找不到會員：" + memberId));

        member.setActive(!Boolean.TRUE.equals(member.getActive())); // 安全地切換 true/false
        memberRepository.save(member);
    }
    
    // 會員用 id 查詢取得自己資料
    @Override
    public MemberInfoDTO getMemberInfo(String memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("ID:"+ memberId +"找不到會員"));
		MemberInfoDTO dto = memberMapper.toInfoDto(member);
		dto.setTotalPoints(pointCollectionService.getMemberRemainingPoint(memberId));
		dto.setNearestExpiryDate(pointCollectionService.getMemberNearestExpiryDate(memberId));
		return dto;
    }

	
}