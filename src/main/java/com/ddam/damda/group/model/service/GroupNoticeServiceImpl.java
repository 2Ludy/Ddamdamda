package com.ddam.damda.group.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ddam.damda.common.util.GNPageRequest;
import com.ddam.damda.group.model.GroupNotice;
import com.ddam.damda.group.model.mapper.GroupNoticeMapper;
import com.ddam.damda.images.model.GnoticeImage;
import com.ddam.damda.images.model.service.GnoticeImageService;
import com.ddam.damda.user.model.Notice;
import com.ddam.damda.user.model.User;
import com.ddam.damda.user.model.service.NoticeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class GroupNoticeServiceImpl implements GroupNoticeService {
	
	@Autowired
	private GroupNoticeMapper groupNoticeMapper;
	
	@Autowired
	private GroupMembersService groupMembersService;
	
	@Autowired
	private GroupInfoService groupInfoService;
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private GnoticeImageService gnoticeImageService;

	@Override
	@Transactional
	public PageInfo<GroupNotice> selectAllGroupNotice(GNPageRequest preq) {
		PageHelper.startPage(preq.getPageNum(), preq.getPageSize());
		List<GroupNotice> list = groupNoticeMapper.selectAllGroupNotice(preq);
		PageInfo<GroupNotice> page = new PageInfo<GroupNotice>(list);
		return page;
	}

	@Override
	@Transactional
    public GroupNotice selectGroupNotice(int gnoticeId) {
        GroupNotice notice = groupNoticeMapper.selectGroupNotice(gnoticeId);
        if (notice != null) {
            notice.setImages(gnoticeImageService.findByGnoticeId(gnoticeId));
        }
        return notice;
    }

	@Override
	@Transactional
    public int insertGroupNotice(GroupNotice groupNotice, List<MultipartFile> images) throws IOException {
        // 1. 공지사항 저장
        int result = groupNoticeMapper.insertGroupNotice(groupNotice);
        if (result <= 0) {
            throw new RuntimeException("공지사항 저장 실패");
        }

        // 2. 이미지 처리
        if (images != null && !images.isEmpty()) {
            List<GnoticeImage> savedImages = new ArrayList<>();
            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    GnoticeImage savedImage = gnoticeImageService.saveGnoticeImage(image, groupNotice.getGnoticeId());
                    savedImages.add(savedImage);
                }
            }
            groupNotice.setImages(savedImages);
        }

        // 3. 알림 처리
        String groupName = groupInfoService.selectGroupInfo(groupNotice.getGroupId()).getGroupName();
        Notice notice = new Notice();
        notice.setReferenceId(groupNotice.getGnoticeId());
        notice.setReferenceType("group_notice");
        notice.setContent("\"" + groupName + "\" 그룹에 새 공지사항이 등록되었습니다.");
        
        List<User> users = groupMembersService.selectAllGroupMembers(groupNotice.getGroupId());
        for (User user : users) {
            notice.setUserId(user.getId());
            noticeService.insertNotice(notice);
        }

        return result;
    }

	@Override
	@Transactional
    public int deleteGroupNotice(int gnoticeId) throws IOException {
        // 1. 이미지 삭제
        gnoticeImageService.deleteAllByGnoticeId(gnoticeId);
        // 2. 공지사항 삭제
        return groupNoticeMapper.deleteGroupNotice(gnoticeId);
    }
	@Override
	@Transactional
    public int updateGroupNotice(GroupNotice groupNotice, List<MultipartFile> newImages, List<Integer> deleteImageIds) throws IOException {
        // 1. 공지사항 수정
        int result = groupNoticeMapper.updateGroupNotice(groupNotice);
        if (result <= 0) {
            throw new RuntimeException("공지사항 수정 실패");
        }

        // 2. 이미지 삭제 처리
        if (deleteImageIds != null && !deleteImageIds.isEmpty()) {
            for (Integer imageId : deleteImageIds) {
                gnoticeImageService.deleteImage(imageId);
            }
        }

        // 3. 새 이미지 추가
        if (newImages != null && !newImages.isEmpty()) {
            List<GnoticeImage> currentImages = groupNotice.getImages() != null ? 
                groupNotice.getImages() : new ArrayList<>();
                
            for (MultipartFile image : newImages) {
                if (!image.isEmpty()) {
                    GnoticeImage savedImage = gnoticeImageService.saveGnoticeImage(image, groupNotice.getGnoticeId());
                    currentImages.add(savedImage);
                }
            }
            groupNotice.setImages(currentImages);
        }

        return result;
    }

	@Override
	@Transactional
	public String selectLatestGroupNotice(int groupId) {
		return groupNoticeMapper.selectLatestGroupNotice(groupId);
	}
	

}
