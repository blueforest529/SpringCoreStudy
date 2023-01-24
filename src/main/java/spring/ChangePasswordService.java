package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class ChangePasswordService {

    @Autowired
    private MemberDBDao memberDao;

    @Transactional
    public void changePassword(String email, String oldPwd, String newPwd) {
        Member member = memberDao.selectByEmail(email);

        if (member == null) {
            throw new MemberNotFoundException();
        }

        member.changePassword(oldPwd, newPwd);

        memberDao.update(member);
    }

    public void setMemberDao(MemberDBDao memberDao) {
        this.memberDao = memberDao;
    }
}
