package com.ddam.damda.group.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ddam.damda.common.util.GPageRequest;
import com.ddam.damda.group.model.GroupInfo;
import com.ddam.damda.group.model.mapper.GroupInfoMapper;
import com.ddam.damda.images.model.service.ImagesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GroupInfoServiceImpl implements GroupInfoService {
	
	@Autowired
	private GroupInfoMapper groupInfoMapper;
	
	@Autowired
	private ImagesService imageService;

	@Override
	@Transactional
	public PageInfo<GroupInfo> selectAllGroupInfos(GPageRequest preq) {
		PageHelper.startPage(preq.getPageNum(), preq.getPageSize());
		List<GroupInfo> list = groupInfoMapper.selectAllGroupInfos(preq);
		PageInfo<GroupInfo> page = new PageInfo<GroupInfo>(list);
		return page;
	}

	@Override
	@Transactional
	public GroupInfo selectGroupInfo(int groupId) {
		// 여기는 일단 불러오고 이미지 후에 id로 처리 하면 될듯
		return groupInfoMapper.selectGroupInfo(groupId);
	}

	@Override
	@Transactional
    public int insertGroupInfo(GroupInfo groupInfo, MultipartFile imageFile) throws IOException {
        try {
            // 1. 이미지 저장
            int imageId = imageService.saveProfileImage(imageFile);
            
            // 2. 그룹 정보에 이미지 ID 설정
            groupInfo.setGroupImg(imageId);
            
            // 3. 그룹 정보 저장
            int isS = groupInfoMapper.insertGroupInfo(groupInfo);
            
            if(isS > 0) insertAdminMembers(groupInfo);
            
            return isS;
            
        } catch (Exception e) {
            log.error("그룹 생성 실패", e);
            throw e;
        }
    }

	@Override
	@Transactional
	public int deleteGroupInfo(int groupId) {
		return groupInfoMapper.deleteGroupInfo(groupId);
	}

	@Override
	@Transactional
    public int updateGroupInfo(GroupInfo groupInfo, MultipartFile imageFile) throws IOException {
        try {
            // 현재 멤버 수 체크
            int currentMembers = groupInfo.getCurrentMembers();
            int memberCount = groupInfo.getMemberCount();
            
            if(memberCount < currentMembers) {
                return 0;
            }
            
            if(memberCount == currentMembers) {
                groupInfo.setMateStatus("마감");
            }
            
            // 새 이미지가 있는 경우 처리
            if (imageFile != null && !imageFile.isEmpty()) {
                int imageId = imageService.saveProfileImage(imageFile);
                groupInfo.setGroupImg(imageId);
            }
            
            return groupInfoMapper.updateGroupInfo(groupInfo);
        } catch (Exception e) {
            log.error("그룹 수정 실패", e);
            throw e;
        }
    }

	@Override
	@Transactional
	public int increaseCurrentMembers(int groupId) {
		return groupInfoMapper.increaseCurrentMembers(groupId);
	}

	@Override
	@Transactional
	public int decreaseCurrentMembers(int groupId) {
		return groupInfoMapper.decreaseCurrentMembers(groupId);
	}

	@Override
	@Transactional
	public List<GroupInfo> selectUserGroupInfos(int userId) {
		return groupInfoMapper.selectUserGroupInfos(userId);
	}

	@Override
	@Transactional
	public int updateMateStatus(int groupId) {
		return groupInfoMapper.updateMateStatus(groupId);
	}

	@Override
	@Transactional
	public int insertAdminMembers(GroupInfo groupInfo) {
		return groupInfoMapper.insertAdminMembers(groupInfo);
	}

}
