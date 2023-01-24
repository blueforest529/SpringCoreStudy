package assembler;

import spring.ChangePasswordService;
import spring.MemberDBDao;
import spring.MemberDao;
import spring.MemberRegisterService;

public class Assembler {

    private MemberDBDao memberDao;
    private MemberRegisterService reqSvc;
    private ChangePasswordService pwdSvc;

    public Assembler() {
        // 의존 객체를 변경 하려면 assembler의 코드만 수정 하면 됨.
        memberDao = new MemberDBDao();
        reqSvc = new MemberRegisterService(memberDao);
        pwdSvc = new ChangePasswordService();
        pwdSvc.setMemberDao(memberDao);
    }

    public MemberDBDao getMemberDao() {
        return memberDao;
    }

    public MemberRegisterService getMemberRegisterService() {
        return reqSvc;
    }

    public ChangePasswordService getChangePasswordService() {
        return pwdSvc;
    }
}
