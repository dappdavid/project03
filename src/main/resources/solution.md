#List of potential Optimizations 

##getNumberOfActiveMembers
- Avoid using the unnecessary try-catch block for flow control.
- Utilize the getMemberIfActive() method to check if each member is active, reducing unnecessary iterations.
- Use int instead of long for the count since we only need to store a smaller value.
- Remove the unnecessary variable Member m.
- Remove the unnecessary variable long activeMember.
- Instead of incrementing activeMembersCount by 1 for each active member, do a post-increment of activeMembersCount
```
private int getNumberOfActiveMembers() {
    List<Member> members = memberRepository.findAll();
    int activeMembersCount = 0;

    for (Member member : members) {
        if (getMemberIfActive(member.getId())!=null) {
            activeMembersCount++;
        }
    }

    return activeMembersCount;
}
```

advanced:
- Use a more efficient query in memberRepository to fetch only active members instead of fetching all members and filtering them afterward.
- Directly use the size() method on the collection of active members instead of maintaining a separate counter variable.
```
private int getNumberOfActiveMembers() {
    List<Member> activeMembers = memberRepository.findByStatus(MemberStatus.ACTIVE);
    return activeMembers.size();
}
```

##getMemberIfActive
- Avoid fetching all members from the database to find a single member by ID.
- Use a more efficient query in memberRepository to directly fetch the member with the given ID and check their status.
- Handle the case when the member with the specified ID is not found in the database more gracefully.
- If the member is not found, return null.
```
private Member getMemberIfActive(long memberId) {
    Optional<Member> optionalMember = memberRepository.findById(memberId);

    // Return null if the member is not found
    Member member = optionalMember.orElse(null);

    if (member == null) {
        return null;
    }

    if (member.getStatus() != MemberStatus.ACTIVE) {
        throw new RuntimeException("User is not active to proceed with lending.");
    }

    return member;
}
```
