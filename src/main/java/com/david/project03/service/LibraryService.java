package com.david.project03.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.david.project03.model.Member;
import com.david.project03.model.MemberStatus;
import com.david.project03.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LibraryService {

	private final MemberRepository memberRepository;

	// Purpose of this method: To return the total number of active members
	// of the library.
	//
	// Question : Review this method and assess if any of the following is required:
	// 1. Any wrong implementation that needs to be corrected?
	// 2. Any performance related optimizations that can be done?
	// 3. Any other change that can done so that the code is clean, concise, optimal and error free?
	// Note: Make sure that the original purpose is still satisfied.
	private long getNumberOfActiveMembers() {
		List<Member> members = memberRepository.findAll();
		long activeMembersCount = 0;

		for (Member member : members) {
			try {
				Member m = getMemberIfActive(member.getId());
				long activeMember = 1;
				if (m.getStatus() == MemberStatus.ACTIVE)
					activeMembersCount = activeMembersCount + activeMember;
			} catch (EntityNotFoundException e) {
				// Member not present in the database
				e.printStackTrace();
			}
		}

		return activeMembersCount;
	}

	// The purpose of this method : To find out if a given memberId belongs to
	// an active member. If yes, then return the Member object.
	//
	// Question : Review this method and assess if any of the following is required:
	// 1. Any wrong implementation that needs to be corrected?
	// 2. Any performance related optimizations that can be done?
	// 3. Any other change that can done so that the code is clean, concise, optimal and error free?
	// Note: Make sure that the original purpose is still satisfied.
	private Member getMemberIfActive(long memberId) {
		List<Member> members = memberRepository.findAll();

		for (Member member : members) {

			if (member.getId() == memberId) {
				if (member.getStatus() != MemberStatus.ACTIVE) {
					throw new RuntimeException("Member is not active.");
				}

				return member;
			}
		}
	}

}
