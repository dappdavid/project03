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

	//The purpose of this method is to return the total number of active members
	//of the library.
	// Review this method
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

	//The purpose of this method is to find out if a given memberId belongs to
	//an active member. If yes, return the Member object
	// Review this method
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
